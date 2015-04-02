package com.bestway.bcus.message.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.message.dao.MessageQueryDao;
import com.bestway.bcus.message.entity.CustomSendMessage;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.system.dao.TcsParametersDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.CommonServerBig5GBConverter;
import com.bestway.common.CommonUtils;
import com.bestway.common.ErrorMessage;
import com.bestway.common.MessageHttpUtil;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.HttpMsgTransType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.CustomsBrokerDetail;
import com.bestway.jptds.common.AppException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 生成和解析集成通报文信息。
 * 
 * @author 陈宏亮
 */
@SuppressWarnings("rawtypes")
public class TCSMessageLogic {

	private EncDao encDao;
	private TcsParametersDao tcsParametersDao;
	private MessageQueryDao messageQueryDao;
	private int supplmentType;
	private static final Logger logger = Logger
			.getLogger(TCSMessageLogic.class);

	/**
	 * 报关行申报
	 * 
	 * @param id
	 * @param projectType
	 * @param supplementListId
	 */
	@SuppressWarnings("unchecked")
	public void customsBrokerDeclaretion(String id, String supplementListId,
			int projectType, CustomsBroker customsBroker) {
		// 准备生成xml的数据，放入args
		Map<String, Object> args = new HashMap<String, Object>();

		// 报关单表头
		BaseCustomsDeclaration declaration = encDao.findCustomsDeclaration(
				projectType, id);
		args.put("declaration", declaration);

		// 补充申报单类型
		supplmentType = declaration.getSupplmentType() == null ? SupplmentType.NO_SYPPLMENT
				: declaration.getSupplmentType();
		args.put("supplmentType", supplmentType);

		// 联系人

		TcsLinkMan tcsLinkMan = tcsParametersDao.getTcsLinkMan();
		Assert.notNull(tcsLinkMan, "tcs联系人未设置");
		args.put("tcsLinkMan", tcsLinkMan);

		// tcs参数
		TcsParameters tcsParameters = tcsParametersDao.getTcsParameters();
		Assert.notNull(tcsParameters, "tcs参数未设置");
		args.put("tcsParameters", tcsParameters);

		// 报关单商品明细
		List<BaseCustomsDeclarationCommInfo> list = encDao
				.findCustomsDeclarationCommInfo(declaration, projectType);
		args.put("goodsList", list);

		List<DecSupplementList> decSupplementLists = new ArrayList<DecSupplementList>();

		// 当被动补充申报时，获得被动补充申报单
		if (supplmentType == SupplmentType.PASSIVITY_SYPPLMENT) {
			decSupplementLists = findDecSupplementListById(supplementListId);
		} else {
			// 查询报关单底下所有主动补充申报单
			decSupplementLists = findDecSupplementListByCustomsDeclarationComminfoIds(list);
		}

		if (supplmentType == SupplmentType.PASSIVITY_SYPPLMENT
				|| supplmentType == SupplmentType.INITIATIVE_SYPPLMENT) {
			if (decSupplementLists == null || decSupplementLists.size() == 0) {
				throw new RuntimeException("没有找到对应补充申报单！");
			}
		}

		args.put("decSupplementLists", decSupplementLists);

		// 集装箱信息
		List<BaseCustomsDeclarationContainer> containers = encDao
				.findContainerByCustomsDeclaration(declaration, projectType);
		args.put("containerList", containers);

		// 当前业务时间

		args.put("currentDate", new Date());

		// 生成xml字符串 写入服务器目录
		String msg = buildXMLMessage(args);

		// 繁体转化为简体
		msg = CommonServerBig5GBConverter.getInstance().big5ConvertToGB(msg);

		declaration.setTcsTaskId((String) args.get("messageId"));
		declaration.setTcsSendTime((Date) args.get("currentDate"));
		declaration.setIsCheck(false);

		// 改变发送状态
		declaration.setIsSend(true);
		// 发送报关行
		declaration.setDeclaraCustomsBroker(customsBroker.getName());

		// 保存报关单
		this.encDao.saveCustomsDeclaration(declaration);

		// 改变补充报关单发送状态
		Iterator<DecSupplementList> it = decSupplementLists.iterator();
		while (it.hasNext()) {
			DecSupplementList decSupplementList = it.next();
			decSupplementList.setIsSend("1");
			this.encDao.saveDecSupplementList(decSupplementList);
		}

		// 文件存放路径
		String fileName = CommonUtils.getSendBgd() + File.separator
				+ (isImport(declaration.getImpExpType()) ? "I_" : "E_")
				+ declaration.getTcsTaskId() + ".xml";

		// 保存文件
		File file = new File(fileName);
		try {
			FileUtils.writeStringToFile(file, msg, "UTF8");
		} catch (IOException e) {
			System.out.println("把报文xml文件写入服务器目录失败！文件名：" + fileName);
			throw new RuntimeException("把报文xml文件写入服务器目录失败！文件名：" + fileName, e);
		}

		try {
			Company company = (Company) CommonUtils.getCompany();
			// 发送到平台
			Object[] parameters = { company.getCode(), company.getName(),
					customsBroker.getCode(), FptBusinessType.FPT_OTHER,
					file.getName(), msg.getBytes("utf-8") };

			String res = (String) webServiceCall(
					tcsParameters.getBtplsAddress(),
					tcsParameters.getBtplsPort() + "", "uploadEmsFile",
					new Class[] { String.class }, parameters);

			if (!"1".equals(res)) {
				// 上传平台失败
				throw new RuntimeException(res);
			}

			// 保存发送记录
			CustomsBrokerDetail cbd = new CustomsBrokerDetail();
			cbd.setCustomDeclarationId(declaration.getId());
			cbd.setCompany(CommonUtils.getCompany());
			cbd.setProjectType(projectType);
			cbd.setCreateDate((Date) args.get("currentDate"));
			cbd.setDeclarationDate((Date) args.get("currentDate"));
			cbd.setCreatePeople(CommonUtils.getRequest().getUser().getName());
			cbd.setCustomserName(declaration.getCustomser());
			cbd.setEmsNo(declaration.getEmsHeadH2k());
			cbd.setTradeMode(declaration.getTradeMode().getName());
			cbd.setCustomsBrokerCode(customsBroker.getCode());
			cbd.setCustomsBrokerName(customsBroker.getName());
			cbd.setImpExpFlag(declaration.getImpExpFlag());

			this.encDao.saveObject(cbd);
		} catch (DataAccessException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			file.delete();
		}
	}

	/**
	 * 根据ID查询报关单 构建报文xml文件， 并且写入服务器文件夹
	 * 
	 * @param describeObj
	 *            报文数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buildMessage(String id, int projectType,
			String supplementListId) {

		StringBuilder result = new StringBuilder();

		// 准备生成xml的数据，放入args
		Map<String, Object> args = new HashMap<String, Object>();

		// 报关单表头
		BaseCustomsDeclaration declaration = encDao.findCustomsDeclaration(
				projectType, id);
		args.put("declaration", declaration);

		result.append("正在发送报关单，预录入号："
				+ declaration.getPreCustomsDeclarationCode() + "，报关单流水号："
				+ declaration.getSerialNumber() + "\n");

		if (declaration.getIsCheck() == null
				|| Boolean.FALSE.equals(declaration.getIsCheck())) {
			if (declaration.getIsSend() == null
					|| Boolean.FALSE.equals(declaration.getIsSend())) {
				return "报关单：" + declaration.getPreCustomsDeclarationCode()
						+ "未检查，不允许发送。";
			} else {
				return "报关单：" + declaration.getPreCustomsDeclarationCode()
						+ "已发送。如果需要重新发送，请重新检查报关单后再发送。";
			}
		}

		// 补充申报单类型
		supplmentType = declaration.getSupplmentType() == null ? SupplmentType.NO_SYPPLMENT
				: declaration.getSupplmentType();
		args.put("supplmentType", supplmentType);

		// 联系人
		TcsLinkMan tcsLinkMan = tcsParametersDao.getTcsLinkMan();
		Assert.notNull(tcsLinkMan, "tcs联系人未设置");
		args.put("tcsLinkMan", tcsLinkMan);

		// tcs参数
		TcsParameters tcsParameters = tcsParametersDao.getTcsParameters();
		Assert.notNull(tcsParameters, "tcs参数未设置");
		args.put("tcsParameters", tcsParameters);

		// 报关单商品明细
		List<BaseCustomsDeclarationCommInfo> list = encDao
				.findCustomsDeclarationCommInfo(declaration, projectType);
		args.put("goodsList", list);

		List<DecSupplementList> decSupplementLists = new ArrayList<DecSupplementList>();

		// 当被动补充申报时，获得被动补充申报单
		if (supplmentType == SupplmentType.PASSIVITY_SYPPLMENT) {
			decSupplementLists = findDecSupplementListById(supplementListId);
		} else {
			// 查询报关单底下所有主动补充申报单
			decSupplementLists = findDecSupplementListByCustomsDeclarationComminfoIds(list);
		}

		if (supplmentType == SupplmentType.PASSIVITY_SYPPLMENT
				|| supplmentType == SupplmentType.INITIATIVE_SYPPLMENT) {
			if (decSupplementLists == null || decSupplementLists.size() == 0) {
				throw new RuntimeException("没有找到对应补充申报单！");
			}
		}

		args.put("decSupplementLists", decSupplementLists);

		// 集装箱信息
		List<BaseCustomsDeclarationContainer> containers = encDao
				.findContainerByCustomsDeclaration(declaration, projectType);
		args.put("containerList", containers);

		// 当前业务时间
		args.put("currentDate", new Date());

		// 生成xml字符串 写入服务器目录
		String msg = buildXMLMessage(args);

		// 繁体转化为简体
		msg = CommonServerBig5GBConverter.getInstance().big5ConvertToGB(msg);

		// 文件存放路径
		String fileName = CommonUtils.getSendBgd() + File.separator
				+ (isImport(declaration.getImpExpType()) ? "I_" : "E_")
				+ (String) args.get("messageId") + ".xml";

		File file = new File(fileName);
		try {
			FileUtils.writeStringToFile(file, msg, "UTF8");
		} catch (IOException e) {
			System.out.println("把报文xml文件写入服务器目录失败！" + "报关单流水号：" + declaration.getSerialNumber() + " 报关单预录入号："
					+ declaration.getPreCustomsDeclarationCode());
			throw new RuntimeException("把报文xml文件写入服务器目录失败！" + "报关单流水号：" + declaration.getSerialNumber() + " 报关单预录入号："
					+ declaration.getPreCustomsDeclarationCode(), e);
		}
		if (declaration.getTcsTaskId() == null
				|| !declaration.getTcsTaskId().equals(args.get("messageId"))) {
			// declaration.setTcsTaskId(null); // 集成通任务ID
			// declaration.setTcsEntryType(null);// 报关单类型
			// declaration.setTcsDeclareProperty(null);// 报关形式类型
			// declaration.setTcsNote(null);// 协同任务备注
			declaration.setTcsResultMessage(null);// 集成通回执结果信息
			// declaration.setTcsResultTime(null);// 集成通回执通知时间
			// declaration.setTcsSendTime(null);// 报文传输时间
			// declaration.setTcsEntryTransitType(null);
		}
		declaration.setTcsTaskId((String) args.get("messageId"));
		declaration.setTcsSendTime((Date) args.get("currentDate"));
		declaration.setIsCheck(false);
		
		//如果没有填写 申报人员 那么这里将设置为登录的当前用户 
		declaration.setDeclarant(
				declaration.getDeclarant()==null?
						CommonUtils.getRequest().getUser():declaration.getDeclarant());
		// 改变发送状态
		declaration.setIsSend(true);

		this.encDao.saveCustomsDeclaration(declaration);

		// 改变补充报关单发送状态
		Iterator it = decSupplementLists.iterator();
		while (it.hasNext()) {
			DecSupplementList decSupplementList = (DecSupplementList) it.next();
			decSupplementList.setIsSend("1");
			this.encDao.saveDecSupplementList(decSupplementList);
		}

		// 上传文件
		try {
			this.httpUpload(projectType, file);
		} catch (Exception e) {
			throw new RuntimeException("报关单流水号：" + declaration.getSerialNumber() + "，报关单预录入号："
					+ declaration.getPreCustomsDeclarationCode(), e);
		}

		result.append("成功上传文件：" + (isImport(declaration.getImpExpType()) ? "I_" : "E_")
				+ (String) args.get("messageId") + ".xml" + "报关单发送成功！\n");
		// 保存发送记录
		CustomSendMessage sendMsg = new CustomSendMessage();
		sendMsg.setTcsTaskId(declaration.getTcsTaskId());
		sendMsg.setCompany(declaration.getCompany());
		sendMsg.setTcsTaskId(declaration.getTcsTaskId());
		// sendMsg.setContext(msg);
		sendMsg.setCreateDate((Date) args.get("currentDate"));
		sendMsg.setCreatePeople(CommonUtils.getAclUser().getUserName());
		sendMsg.setEmsNo(declaration.getEmsHeadH2k());
		sendMsg.setFileName(fileName);
		sendMsg.setProjectType(projectType);
		sendMsg.setSendDate((Date) args.get("currentDate"));
		sendMsg.setSendUser(CommonUtils.getAclUser().getUserName());
		sendMsg.setSeqNo(declaration.getId());
		sendMsg.setYlrh(declaration.getPreCustomsDeclarationCode());

		messageQueryDao.saveCustomSendMessage(sendMsg);
		return result.toString();
	}

	// public String[] buildMessage(String[] ids, int projectType,
	// String[] supplementListIds) {
	// String[] res = new String[ids.length];
	// for (int i = 0; i < ids.length; i++) {
	// try {
	// res[i] = buildMessage(ids[i], projectType, supplementListIds[i]);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// return res;
	// }

	public List<ErrorMessage> checkTcsParameter() {
		List<ErrorMessage> listError = new ArrayList<ErrorMessage>();
		// tcs参数
		Map<String, Object> args = new HashMap<String, Object>();
		TcsParameters tcsParameters = tcsParametersDao.getTcsParameters();
		Assert.notNull(tcsParameters, "tcs参数未设置");
		TcsLinkMan tcsLinkMan = tcsParametersDao.getTcsLinkMan();
		Assert.notNull(tcsLinkMan, "tcs联系人未设置");
		args.put("tcsLinkMan", tcsLinkMan);
		args.put("tcsParameters", tcsParameters);
		TcsParameters p = (TcsParameters) args.get("tcsParameters");
		// TcsParameters t = (TcsParameters) args.get("tcsLinkMan");
		if (p.getIcCardNo() == null || "".equals(p.getIcCardNo())) {// 操作员IC卡号
			ErrorMessage error = new ErrorMessage();
			error.setCheckDate(new Date());
			error.setTitle("tcs用户参数");
			error.setMessage("操作员IC卡号不能为空");
			listError.add(error);
		}
		if (p.getOperatorName() == null || "".equals(p.getOperatorName())) {// 操作员姓名
			ErrorMessage error = new ErrorMessage();
			error.setCheckDate(new Date());
			error.setTitle("tcs用户参数");
			error.setMessage("操作员姓名不能为空");
			listError.add(error);
		}
		if (p.getOganizationCode() == null || "".equals(p.getOganizationCode())) {// 操作企业组织机构代码
			ErrorMessage error = new ErrorMessage();
			error.setCheckDate(new Date());
			error.setTitle("tcs用户参数");
			error.setMessage("组织机构代码不能为空");
			listError.add(error);
		}
		return listError;
	}

	public void setTcsParametersDao(TcsParametersDao tcsParametersDao) {
		this.tcsParametersDao = tcsParametersDao;
	}

	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	/**
	 * 构建报文xml字符串
	 * 
	 * @param describeObj
	 *            报文数据
	 * @return
	 */
	private String buildXMLMessage(Map<String, Object> args) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		LabelValue l = new LabelValue("TCS101Message",
				TCSMessageBuilderLogic.buildRootMassage(args));
		sb.append(coverter(l, null));
		return sb.toString().replace("<TaskNote xsi:nil=\"true\" />",
				"<TaskNote/>");
	}

	public static void main(String[] args) {

	}

	static class LabelValue {
		String label;
		Object value;

		public LabelValue(String label, Object value) {
			super();
			this.label = label;
			this.value = value;
		}

		public LabelValue() {
			super();
		}

	}

	@SuppressWarnings("unchecked")
	private static String coverter(LabelValue object, String tab) {
		tab = tab == null ? "" : tab + "  ";
		StringBuilder sb = new StringBuilder();
		if (object == null) {

		} else if (isList(object.value)) {
			List<LabelValue> list = (List<LabelValue>) (object.value);
			if (list.size() > 0) {
				sb.append(tab + "<" + object.label + ">\r\n");
				for (LabelValue labelValue : list) {
					sb.append(coverter(labelValue, tab));
				}
				if (object.label.contains(" ")) {
					sb.append(tab + "</" + object.label.split(" ")[0] + ">\r\n");
				} else {
					sb.append(tab + "</" + object.label + ">\r\n");
				}
			}

		} else if (object.value == null || "".equals(object.value)) {
			sb.append(tab + "<" + object.label + " xsi:nil=\"true\" />\r\n");
			// sb.append(tab + "<" + object.label + " />\r\n");
		} else {
			sb.append(tab + "<" + object.label + ">");
			sb.append(toXMLString(object.value.toString()));
			sb.append("</" + object.label + ">\r\n");
		}

		return sb.toString();
	}

	/**
	 * 转译字符串，变成xml字符串
	 * 
	 * @param str
	 * @return
	 */
	private static String toXMLString(String str) {
		// &lt; < 小于号
		// &gt; > 大于号
		// &amp; & 和
		// &apos; ' 单引号
		// &quot; " 双引号
		return str.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;").replace("'", "&apos;")
				.replace("\"", "&quot;");
	}

	/**
	 * Checks if is object.
	 * 
	 * @param obj
	 *            the obj
	 * 
	 * @return true, if is object
	 */
	private static boolean isList(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof List)
			return true;
		return false;
	}

	/**
	 * 是进口还是出口
	 */
	public static boolean isImport(int billType) {
		boolean isImport = false;
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.EQUIPMENT_IMPORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:// 边角料内销
		case ImpExpType.MATERIAL_DOMESTIC_SALES:// 料件内销
		case ImpExpType.REMAIN_FORWARD_IMPORT: //
			isImport = true;
			break;
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
		case ImpExpType.EQUIPMENT_BACK_PORT:
		case ImpExpType.BACK_PORT_REPAIR:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			isImport = false;
			break;
		}
		return isImport;
	}

	/**
	 * 上传报文
	 * 
	 * @param projectType
	 */
	public void httpUpload(int projectType, File file) {

		// TcsParameters p = tcsParametersDao.getTcsParameters();
		// 系统参数
		// CompanyOther companyOther = messageQueryDao.findCompanyOther();

		// Boolean isFtpPASV = true;// 默认为PASV被动模式
		// Integer ftpTimeOut = 300 * 1000;// 默认时间为5分钟
		//
		// if (companyOther != null) {
		// if(companyOther.getFtpTimeOut() != null){
		// ftpTimeOut = companyOther.getFtpTimeOut() == 0 ? 300 * 1000
		// : companyOther.getFtpTimeOut() * 1000;
		// }
		// }

		// String addree = p(); // EDI地址
		// String ediuser = p.getFtpName(); // EDI用户名
		// String edipassword = p.getFtpPwd(); // EDI密码
		// Assert.notNull(addree, "ftp地址为空！");
		// Assert.notNull(ediuser, "ftp用户为空！");
		// Assert.notNull(edipassword, "ftp密码为空！");

		// String tempDir = "UploadTemp";
		String uploadDir = "Upload";

		// File path = new File(CommonUtils.getSendBgd());
		// File[] files = path.listFiles();
		// FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21,
		// ediuser,
		// edipassword,companyOther.getIsFtpPASV(),CommonUtils.getAclUser().getId());//new
		// FTPClient();
		try {
			if (file == null) {
				logger.info("上传文件为空！，操作被终止。!");
				throw new RuntimeException("上传文件为空！，操作被终止。");
			}
			// ftpClient.enterLocalPassiveMode();
			// logger.info("---------enterLocalPassiveMode");
			// tempDir的文件
			// FTPFile[] fs = null;

			// for (int i = 0; i < files.length; i++) {
			// File file = (File) files[i];
			// if(!file.isFile()){
			// continue;
			// }
			// logger.info("开始切换目录：" + tempDir);
			// ftpClient.changeWorkingDirectory(tempDir);
			// logger.info(ftpClient.getReplyString());
			// logger
			// .info("结束切换目录，当前工作目录"
			// + ftpClient.printWorkingDirectory());
			// file = (File) files[i];
			// InputStream is = new FileInputStream(file);
			// if(fs == null) {
			// fs = ftpClient.listFiles();
			// }
			// if(isDirExist(file.getName(), fs)) {
			// logger.info("删除已存在的重复文件" + file.getName());
			// ftpClient.deleteFile(file.getName());
			// }
			// logger.info("开始保存文件" + file.getName());
			// ftpClient.storeFile(file.getName(), is);
			// logger.info(ftpClient.getReplyString());
			// is.close();
			// logger.info("结束保存文件" + file.getName());
			// if (ftpClient.changeToParentDirectory()) {

			try {
				MessageHttpUtil.upload(HttpMsgTransType.TCS, uploadDir,
						file.getName(), CommonUtils.getSendBgd());
				System.out.println("-----------------fileName:"
						+ file.getName());
			} catch (Exception e) {
				FileUtils.forceDelete(file);
				throw new RuntimeException("上传报文失败！" + e);
			}
			// logger.info(ftpClient.getReplyString());
			// logger.info("切换到父级目录，当前工作目录："
			// + ftpClient.printWorkingDirectory());
			//
			// String src = tempDir + "/" + file.getName();
			// String dest = uploadDir + "/" + file.getName();
			//
			// logger.info("把上传文件" + file.getName() + "从临时目录" + tempDir
			// + "移动到正式上次目录" + uploadDir);
			// if (!ftpClient.rename(src, dest)) {
			//
			// logger.info(ftpClient.getReplyString());
			// logger.info("移动上传文件时失败。");
			// throw new RuntimeException("移动上传文件时失败。");
			// }
			//
			// logger.info(ftpClient.getReplyString());
			// logger.info("移动上传文件成功。");
			// logger.info("成功上传了文件：" + file.getName() + "。");

			// 移动文件 Edit by xxm 2006-11-24 将发送的报文备份到（回执处理完的目录下）
			File destFile = new File(CommonUtils.getFinallyBgd()
					+ File.separator + file.getName());
			logger.info("将发送的报文：" + file.getName() + "备份到（处理完的目录下）"
					+ CommonUtils.getFinallyBgd());
			System.out.println("-----------------destFile:" + file.getName());
			moveFile(file, destFile);
			logger.info("备份成功！");
			// } else {
			// logger.info("changeToParentDirectory error 未能切换到父级目录，上传文件");
			// throw new RuntimeException("未能切换到父级目录，上传文件："
			// + file.getName() + "时失败。");
			// }
			// }

			// logger.info("注销ftp连接");
			// logger.info(ftpClient.getReplyString());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	// 判断是否有重复文件
	public static boolean isDirExist(String fileName, FTPFile[] fs) {
		logger.info("判断是否存在重复文件！");
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				logger.info("存在重复文件！");
				return true; // 如果存在返回 正确信号
			}
		}
		logger.info("不存在重复文件！");
		return false; // 如果不存在返回错误信号
	}

	/**
	 * 处理集成通回执报文
	 * 
	 * @param file
	 * @param projectType
	 */
	public void proccessTCSReturnMessage(File file, int projectType) {
		logger.info("处理回执：" + file.getName());
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			doc = sb.build(inputStream);
			Element root = doc.getRootElement();
			// 信息头
			Element elemHead = root.getChild("MessageHead");
			if (elemHead == null) {
				throw new RuntimeException("回执报文中没有包含：MessageHead");
			}

			// 信息头-信息类型
			Element elemMessageType = elemHead.getChild("MessageType");
			if (elemMessageType == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageHead-->MessageType");
			}
			String messageType = elemMessageType.getTextTrim();

			// 信息头-信息时间
			Element elemMessageTime = elemHead.getChild("MessageTime");
			Date messageTime = null;
			if (elemMessageTime != null) {
				String strMessageTime = elemMessageTime.getTextTrim();
				if (strMessageTime.length() == 14) {
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyyMMddHHmmSS");
					messageTime = dateFormat.parse(strMessageTime);
				}
			}

			// 处理
			if ("TcsFlow201Response".equalsIgnoreCase(messageType)) {
				TCSProccessReturnLogic
						.proccessTcsFlow201Response(root, projectType, file,
								messageTime, encDao, messageQueryDao);
			} else if ("TcsFlow201".equalsIgnoreCase(messageType)) {
				TCSProccessReturnLogic.proccessTcsFlow201(root, projectType,
						file, messageTime, encDao, messageQueryDao);
			} else {
				throw new RuntimeException("不能识别的MessageType：" + messageType);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			File destFile = new File(CommonUtils.getFinallyBgd()
					+ File.separator + file.getName());
			moveFile(file, destFile);
		}
	}

	/**
	 * 下载回执
	 * 
	 * @param projectType
	 * @return
	 */
	public synchronized List httpDownload(int projectType, Set<String> taskIds) {

		// 没有指定要下载的报文taskId。
		if (taskIds == null || taskIds.isEmpty()) {
			return null;
		}

		// TcsParameters p = tcsParametersDao.getTcsParameters();
		// // 系统参数
		// CompanyOther companyOther = messageQueryDao.findCompanyOther();

		// Boolean isFtpPASV = true;// 默认为PASV被动模式
		// Integer ftpTimeOut = 300 * 1000;// 默认时间为5分钟

		// if (companyOther != null) {
		//
		// // if (companyOther.getIsFtpPASV() != null
		// // && !companyOther.getIsFtpPASV()) {
		// // isFtpPASV = false;
		// // }
		// if(companyOther.getFtpTimeOut() != null){
		// ftpTimeOut = companyOther.getFtpTimeOut() == 0 ? 300 * 1000
		// : companyOther.getFtpTimeOut() * 1000;
		// }
		// }
		// String addree = p.getFtpAddress(); // EDI地址
		// String ediuser = p.getFtpName(); // EDI用户名
		// String edipassword = p.getFtpPwd(); // EDI密码
		StringBuilder sb = new StringBuilder();// 日志
		// String replyString = null;

		// ftp下载回执目录
		String downDir = "Download";
		List downloadFiles = new Vector();
		// FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21,
		// ediuser,
		// edipassword,companyOther.getIsFtpPASV(),CommonUtils.getAclUser().getId());//new
		// FTPClient();
		try {
			// ftpClient.enterLocalPassiveMode();
			// logger.info("---------enterLocalPassiveMode");

			// logger.info("切换工作目录：" + downDir + " ...");
			// sb.append("\n" + "切换工作目录：" + downDir + " ...");
			// if (ftpClient.changeWorkingDirectory(downDir)) {
			// replyString = ftpClient.getReplyString();
			// logger.info(replyString);
			// sb.append("\n" + replyString);
			//
			// logger.info("列出所有文件：");
			// sb.append("\n" + "列出所有文件：");
			// FTPFile[] ftpFiles = ftpClient.listFiles();
			List<String> listAllFiles = MessageHttpUtil.listFiles(
					HttpMsgTransType.TCS, downDir);
			// replyString = ftpClient.getReplyString();
			// logger.info(replyString);
			// sb.append("\n" + replyString);
			//
			logger.info("发现:"
					+ (listAllFiles != null ? listAllFiles.size() : 0)
					+ "个报文需要下载");
			// sb.append("\n" + "发现:" + (ftpFiles != null ? ftpFiles.length : 0)
			// + "个报文需要下载");

			// logger.info("列出所有文件名：");
			// sb.append("\n" + "列出所有文件名：");
			//
			// for (String fileName: listAllFiles){
			// logger.info(fileName);
			// sb.append("\n" +fileName);
			//
			// }

			// String[] names = ftpClient.listNames();
			// replyString = ftpClient.getReplyString();
			// logger.info(replyString);
			// sb.append("\n" + replyString);
			//
			// logger.info("显示需要下载的所有文件名：");
			// sb.append("\n" + "显示需要下载的所有文件名：");
			// if(names == null) {
			// names = new String[]{};
			// }
			// for (String string : names) {
			// logger.info(string);
			// sb.append("\n" + string);
			// }

			// Thread.currentThread().sleep(3000);

			logger.info("下载文件：");
			sb.append("\n" + "下载文件：");
			for (int i = 0; i < listAllFiles.size(); i++) {

				// // 不是文件跳过
				// if (!ftpFiles[i].isFile()) {
				// continue;
				// }

				// ftp上的文件名
				String ftpFileName = listAllFiles.get(i);

				boolean unContains = true;
				for (String taskId : taskIds) {
					if (ftpFileName.contains(taskId)) {
						unContains = false;
						break;
					}
				}
				// 文件名不在要下载的set中。跳过这个文件继续。
				if (unContains) {
					continue;
				}
				MessageHttpUtil.download(HttpMsgTransType.TCS, downDir,
						ftpFileName, CommonUtils.getRecvBgd());
				MessageHttpUtil.delete(HttpMsgTransType.TCS, downDir,
						ftpFileName);
				logger.info("成功下载" + ftpFileName + "文件。");
				sb.append("\n" + "成功下载" + ftpFileName + "文件。");
				// File file = new File(CommonUtils.getRecvBgd()
				// + File.separator + ftpFileName);
				//
				// logger.info("准备在本地建立回执文件：" + ftpFileName + " ...");
				// sb.append("\n" + "准备在本地建立回执文件：" + file.getName() + " ...");
				// // 回执文件不存在就下载
				// if (!file.exists()) {
				// if (!file.createNewFile()) {
				// throw new RuntimeException("未能建立新文件："
				// + file.getName() + "。");
				// }
				// } else {
				// logger.info("本地已经存在回执文件，不再建立回执文件！");
				// sb.append("\n" + "本地已经存在回执文件，不再建立回执文件！");
				// }
				// FileOutputStream fos = new FileOutputStream(file);
				// // 文件下载
				// int num = 1;// 下载次数，下载回执不合要求，请求三次
				// logger.info("开始下载回执文件,每个回执不合要求，请求三次...");
				// sb.append("\n" + "开始下载回执文件,每个回执不合要求，请求三次...");
				//
				// try {
				// do {
				// logger.info("第" + bum + "次下载请求...");
				// sb.append("\n" + "第" + num + "次下载请求...");
				// if (ftpClient.retrieveFile(ftpFileName, fos)) {
				// replyString = ftpClient.getReplyString();
				// logger.info(replyString);
				// sb.append("\n" + replyString);
				// // 判断下载成功的回执行数是否大于要求行5
				// if (getFileCount(file) < 5) {
				// num++;
				// } else {
				// num = 4;
				// logger.info("已下载回执文件！");
				// sb.append("\n" + "已下载回执文件！");
				// logger.info("删除ftp服务器的回执文件" + ftpFileName
				// + "...");
				// sb.append("\n" + "删除ftp服务器的回执文件" + ftpFileName
				// + "...");
				// ftpClient.deleteFile(ftpFileName);
				// replyString = ftpClient.getReplyString();
				// logger.info(replyString);
				// sb.append("\n" + replyString);
				//
				// downloadFiles.add(file);
				//
				// logger.info("成功下载" + ftpFileName + "文件。");
				// sb.append("\n" + "成功下载" + ftpFileName + "文件。");
				// break;
				// }
				// } else {
				// logger.info("第" + num + "次请求下载回执文件 失败！");
				// sb.append("\n" + "第" + num + "次请求下载回执文件 失败！");
				// replyString = ftpClient.getReplyString();
				// logger.info(replyString);
				// sb.append("\n" + replyString);
				// num++;
				// }
				//
				// if (num == 3) {
				// logger.info("回执下载失败：" + ftpFileName);
				// sb.append("\n" + "回执下载失败：" + ftpFileName);
				// }
				// } while (num < 3);
				// } catch (Exception e) {
				// throw e;
				// } finally {
				// fos.close();
				// if (getFileCount(file) < 5) {
				// logger.info("回执文件太小，为无效文件，删除文件。");
				// file.delete();
				// }
				// }
			}
			// } else {
			// logger.info("未能变更目录到：" + downDir);
			// sb.append("\n" + "未能变更目录到：" + downDir);
			// replyString = ftpClient.getReplyString();
			// logger.info(replyString);
			// sb.append("\n" + replyString);
			// throw new RuntimeException("未能变更目录到：" + downDir);
			// }
			downloadFiles.add(sb);
			return downloadFiles;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public synchronized String customsBrokerDownload() {

		StringBuilder sb = new StringBuilder();

		sb.append("\n开始下载回执！");
		logger.info("开始下载回执！");

		// tcs参数
		TcsParameters p = tcsParametersDao.getTcsParameters();
		Assert.notNull(p, "tcs参数未设置");

		String fileJson = (String) webServiceCall(p.getBtplsAddress(),
				p.getBtplsPort() + "", "downLoadEmsFile",
				new Class[] { String.class },
				new Object[] { ((Company) CommonUtils.getCompany()).getCode() });

		if (CommonUtils.isEmpty(fileJson)) {
			throw new RuntimeException("平台返回信息为空！");
		} else if (fileJson.startsWith("0")) {
			throw new RuntimeException(fileJson);
		}

		Gson gson = new Gson();

		Map<String, byte[]> files = gson.fromJson(fileJson,
				new TypeToken<Map<String, byte[]>>() {
				}.getType());

		sb.append("\n回执下载成功！");
		logger.info("回执下载成功！");

		sb.append("\n开始本地保存回执！");
		logger.info("开始本地保存回执！");

		int count = 0;
		Set<String> fileSet = files.keySet();
		for (String fileName : fileSet) {
			File file = new File(CommonUtils.getRecvBgd() + File.separator
					+ fileName);

			logger.info("准备在本地建立回执文件：" + file.getName() + " ...");
			sb.append("\n" + "准备在本地建立回执文件：" + file.getName() + " ...");
			// 回执文件不存在就下载
			if (!file.exists()) {
				try {
					if (!file.createNewFile()) {
						throw new RuntimeException("未能建立新文件：" + file.getName()
								+ "。");
					}

					logger.info("把回执信息写入xml文件！");
					sb.append("\n把回执信息写入xml文件！");
					// 写把信息写入文件
					FileUtils.writeStringToFile(file,
							new String(files.get(fileName), "UTF8"), "UTF8");
					count++;

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.info("本地已经存在回执文件，不再建立回执文件！");
				sb.append("\n" + "本地已经存在回执文件，不再建立回执文件！");
			}
		}

		sb.append("\n保存成功文件数：" + count);
		sb.append("\n保存失败文件数：" + (fileSet.size() - count));

		logger.info("回执保存文件！");
		System.out.println(sb);

		return sb.toString();
	}

	/**
	 * 取得文件行数
	 * 
	 * @param file
	 * @return
	 */
	public int getFileCount(File file) {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
			int count = 0;
			while (fileReader.readLine() != null) {
				count++;
			}
			return count;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// 移动文件
	private void moveFile(File fSrc, File fDest) {
		logger.info("移动文件：" + fSrc.getName());
		try {
			if (fSrc.exists()) {
				if (fDest.exists()) {
					fDest.delete();
				}
				FileUtils.copyFile(fSrc, fDest);
				// fSrc.delete();
				FileUtils.forceDelete(fSrc);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setMessageQueryDao(MessageQueryDao messageQueryDao) {
		this.messageQueryDao = messageQueryDao;
	}

	// public static String getNetConnection(String addree, String ediuser,
	// String edipassword, int port) {
	// StringBuilder sb = new StringBuilder();// 日志
	// String replyString = null;
	// // 系统参数
	// CompanyOther companyOther = messageQueryDao.findCompanyOther();
	// // ftp下载回执目录
	// String downDir = "Download";
	// FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21, ediuser,
	// edipassword,companyOther.getIsFtpPASV(),CommonUtils.getAclUser().getId());//new
	// FTPClient();
	// try {
	//
	// logger.info("切换工作目录：" + downDir + " ...");
	// sb.append("\n" + "切换工作目录：" + downDir + " ...");
	// if (ftpClient.changeWorkingDirectory(downDir)) {
	// replyString = ftpClient.getReplyString();
	// logger.info(replyString);
	// sb.append("\n" + replyString);
	//
	// logger.info("列出所有文件：");
	// sb.append("\n" + "列出所有文件：");
	// FTPFile[] ftpFiles = ftpClient.listFiles();
	// replyString = ftpClient.getReplyString();
	// logger.info(replyString);
	// sb.append("\n" + replyString);
	//
	// logger.info("发现:" + (ftpFiles != null ? ftpFiles.length : 0)
	// + "个报文需要下载");
	// sb.append("\n" + "发现:" + (ftpFiles != null ? ftpFiles.length : 0)
	// + "个报文需要下载");
	//
	// logger.info("列出所有文件名：");
	// sb.append("\n" + "列出所有文件名：");
	// String[] names=ftpClient.listNames();
	// replyString = ftpClient.getReplyString();
	// logger.info(replyString);
	// sb.append("\n" + replyString);
	//
	// logger.info("显示需要下载的所有文件名：");
	// sb.append("显示需要下载的所有文件名：");
	// for (String string : names) {
	// logger.info(string);
	// sb.append("\n" + string);
	// }
	//
	// logger.info("网络连接正常！");
	// sb.append("\n网络连接正常！");
	// } else {
	// logger.info("未能变更目录到：" + downDir);
	// sb.append("\n" + "未能变更目录到：" + downDir);
	// replyString = ftpClient.getReplyString();
	// logger.info(replyString);
	// sb.append("\n" + replyString);
	// logger.info("网络连接失败！");
	// sb.append("网络连接失败！");
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.info("网络连接失败！");
	// sb.append("\n网络连接失败！");
	// }
	//
	// return sb.toString();
	// }
	//
	/**
	 * 
	 * @param serverIP
	 *            服务地址
	 * @param serverPort
	 *            端口
	 * @param methodName
	 *            调用方法
	 * @param clazz
	 *            指定add方法返回值的数据类型的Class对象
	 * @param parameters
	 *            指定方法的参数值
	 * @return
	 */
	public static Object webServiceCall(String serverIP, String serverPort,
			String methodName, Class[] clazz, Object[] parameters) {
		String objs = null;
		try {

			// 指定调用WebService的URL
			String urlPath = "http://" + serverIP + ":" + serverPort
					+ "/bcgs-ejb/BghWS";

			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();

			serviceClient.getOptions()
					.setProperty(HTTPConstants.CHUNKED, false);

			Options options = serviceClient.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(urlPath);

			options.setTo(targetEPR);

			QName opAddEntry = new QName(
					"http://service.webservice.bcgs.bsw.com.cn/", methodName);

			objs = (String) serviceClient.invokeBlocking(opAddEntry,
					parameters, clazz)[0];
		} catch (Exception e) {
			throw new AppException("连接不上服务器!", e);
		}

		return objs;
	}

	/**
	 * 查询报关单底下所有主动补充申报单
	 * 
	 * @return
	 */
	private List<DecSupplementList> findDecSupplementListByCustomsDeclarationComminfoIds(
			List<BaseCustomsDeclarationCommInfo> list) {
		if (list == null) {
			return new ArrayList<DecSupplementList>();
		}
		String[] ids = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getId();
		}

		return encDao.findDecSupplementListByCustomsDeclarationComminfoIds(ids);
	}

	/**
	 * 查询补充报关单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<DecSupplementList> findDecSupplementListById(String id) {
		return encDao.getDecSupplementListById(id);
	}

	/**
	 * 查询补充报关单表体
	 * 
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo findBaseCustomsDeclarationCommInfoById(
			String id) {
		return encDao.getBaseCustomsDeclarationCommInfoById(id);
	}

	public Boolean testConnect() {
		return MessageHttpUtil.testConnect(HttpMsgTransType.TCS);
	}
}
