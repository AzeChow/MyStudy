/*
 * Created on 2004-8-5
 * 报文回执处理
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;

import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelCusImgResult;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.message.MessageFunctions;
import com.bestway.bcus.message.dao.ReceiptResultDao;
import com.bestway.bcus.message.entity.EMSDataType;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.message.entity.ReceiptResult;
import com.bestway.common.CommonUtils;
import com.bestway.common.MyFile;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ImgExgMergerType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessMessageLogic {
	ReceiptResultDao receiptResultDao = null;

	ExportMessageLogic exportMessageLogic = null;

	EmsEdiTrDao emsEdiTrDao = null;

	private String sFileName = "";

	/**
	 * @return Returns the receiptResultDao.
	 */
	public ReceiptResultDao getReceiptResultDao() {
		return receiptResultDao;
	}

	/**
	 * @param receiptResultDao
	 *            The receiptResultDao to set.
	 */
	public void setReceiptResultDao(ReceiptResultDao receiptResultDao) {
		this.receiptResultDao = receiptResultDao;
	}

	/**
	 * 电子帐册--报文回执处理
	 * 
	 * @param messageStream
	 * @return CheckInfo
	 */
	public String processMessage(File file) {
		long  begin = System.currentTimeMillis();
		InputStream messageStream;
		try {
			messageStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		String copEmsNo = "";
		List<String> messageLines = getMessageLines(messageStream);
		Receipt receipt = new Receipt(messageLines);
		
		String sMessageType = receipt.getMessageType();

		ResultLine resultLine = receipt.getResultLine();

		System.out.println(" -- 读取文件完毕！");
		
		// -------------------------------------------------------------------------
		if (!sMessageType.equals("EMS330") && !sMessageType.equals("EMS331")) { // 330核查,331报核
			copEmsNo = receipt.getResultLine().getCopEmsNo();
		}
		
		// 记录回执信息(保存回执信息到数据库)
		writeReceiveInfo(resultLine, file.getName());

		int declareType = 0;
		int emsMergerType = 0;

		if (!sMessageType.equals("EMS317")) {// 因为清单中的declareType是B
			declareType = getDeclareType(messageLines);
			emsMergerType = getEmsMergerHeadH2kType(messageLines);// 区分归并关系还是帐册
		}

		InputStream formatStream = getFormatFile(sMessageType, declareType);// 取得各报文的对应的格式
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		// --------------------------------------------------------------------------
		// 1,7：入库成功
		// 2：审批通过
		// 3: 退单,4:重复申报(退单)
		// --------------------------------------------------------------------------
		String checkMark = receipt.getResultLine().getChkMark();// 处理结果

		if (checkMark.matches("[0-9]+") == false) {
			checkMark = "0"; // 判断checkMark中是否有非数字
		}
		
		// 处理结果返回的信息
		String msg = null;

		String messageType = receipt.getMessageType().toUpperCase();// 取得报类型如Ems317等
//		MessageFormat messageFormat = new MessageFormat(formatStream);
		String emsNo = receipt.getResultLine().getEmsNo();
		// ---Add by xxm 2005-11-4----------- [处理核销] ---------------------------
		if (sMessageType.equals("EMS331")) {
			List list = modifyMarkDcr(messageType, checkMark, copEmsNo, emsNo);
			for (int i = 0; i < list.size(); i++) {
				this.getReceiptResultDao().commonSaveObject(list.get(i));
			}
			String stu = receiptResultDao.getBpara(BcusParameter.RESULT);
			if(stu!=null&&"1".equals(stu)&&checkMark.equals("2")){
				//获取料件信息
				List<String> imgs = receipt.getImgs();
				if(imgs!=null&&imgs.size()>0){
					updateCancelCusImgResult(imgs, emsNo);
				}
			}
			
		} else {// [处理其他]--经营范围，归并关系，电子帐册
			// **********************************by xxm modify
			// 2004-12-10*********************
			if (!checkMark.equals("2") && !messageType.equals("EMS317")) { // 即未审批通过
				List list = modifyMarkNoPass(receipt, messageType, checkMark,
						copEmsNo, emsNo, emsMergerType);
				for (int i = 0; i < list.size(); i++) {
					this.getReceiptResultDao().commonSaveObject(list.get(i));
				}
			} else { // 审批通过
				if ((messageType.equals("EMS315"))
						|| (messageType.equals("EMS325"))
						|| // 归并关系(现在不存在)
						(messageType.equals("EMS314"))
						|| (messageType.equals("EMS324"))) { // 分册
					List list = modifyMarkPass(receipt, messageType, checkMark,
							copEmsNo, emsNo, 0);
					for (int i = 0; i < list.size(); i++) {
						this.getReceiptResultDao()
								.commonSaveObject(list.get(i));
					}
				} else if ((messageType.equals("EMS311"))
						|| (messageType.equals("EMS321"))) {// 经营范围
					List list = modifyMarkEmsTrPass(receipt, messageType,
							checkMark, copEmsNo, emsNo, emsMergerType);
					for (int i = 0; i < list.size(); i++) {
						this.getReceiptResultDao()
								.commonSaveObject(list.get(i));
					}
				} else if (messageType.equals("EMS317")) {// 报关清单
					System.out.println(" -- 开始处理清单！");

					List list = modifyMarkEmsBillPass(receipt, messageType,
							checkMark, copEmsNo, emsNo);

					for (int i = 0; i < list.size(); i++) {
						this.getReceiptResultDao()
								.commonSaveObject(list.get(i));
					}
				} else { // 电子帐册(归并关系)（313，323）
					EMSMessageUtil util = EMSMessageUtil.create(file);

					if (emsMergerType == 5) {// 归并关系
						System.out.println(" -- 开始处理归并关系！");
						
						List list = modifyMarkEmsMergerPass(util, receipt,
								messageType, checkMark, copEmsNo, emsNo,
								emsMergerType);
						for (int i = 0; i < list.size(); i++) {
							this.getReceiptResultDao().commonSaveObject(
									list.get(i));
						}
						
					} else {// 电子帐册
						System.out.println(" -- 开始处理电子帐册！");
						
						List list = modifyMarkEmsH2kPass(util, receipt, messageType,
								checkMark, copEmsNo, emsNo, emsMergerType);
						
						Object o = null;
						for (int i = 0; i < list.size(); i++) {
							o = list.get(i);
							if(o instanceof String) {
								msg = (String) o;
								continue;
							}
							
							this.getReceiptResultDao().commonSaveObject(o);
						}
						
					}
				}
			}
		}
		
		
		exportMessageLogic.saveMessageQuery(MessageQuery.RECVTYPE, getEdiType(
				sMessageType, emsMergerType), getDelcareType(sMessageType),
				file.getName(), copEmsNo, sMessageType, Integer
						.parseInt(checkMark));
		long  end = System.currentTimeMillis();
		System.out.println("totaltime:processMessage(File file)"+(end-begin)/1000);
		
		if(msg == null) {
			msg = receipt.getCheckInfo();
		}
		
		return msg;
		
	}
	
	public void updateCancelCusImgResult(List<String> imgs,String emsNo){
		
		String cancelTimes = stringToInteger(imgs.get(0).substring(12, 21).trim()).toString();
		
		Hashtable<String, Object> headConditions = new Hashtable<String, Object>();
		headConditions.put("emsNo",emsNo);
		headConditions.put("cancelTimes",cancelTimes);
		headConditions.put("company.id", CommonUtils.getCompany().getId());
		List lsHead = this.receiptResultDao.commonSearch("CancelCusHead",headConditions);
		if(lsHead.size()>0){
			if(lsHead.get(0) instanceof CancelCusHead){
				CancelCusHead head = (CancelCusHead)lsHead.get(0);
				Hashtable<String, Object> conditions = new Hashtable<String, Object>();
				conditions.put("cancelHead.id",head.getId());
				conditions.put("company.id", CommonUtils.getCompany().getId());
				Map<Integer, CancelCusImgResult> mapImg = new HashMap<Integer, CancelCusImgResult>(); 
				List lsImg = this.receiptResultDao.commonSearch("CancelCusImgResult",conditions);
				for (int i = 0; i < lsImg.size(); i++) {
					if(lsImg.get(i) instanceof CancelCusImgResult){
						CancelCusImgResult result = (CancelCusImgResult)lsImg.get(i);
						mapImg.put(result.getEmsSeqNum(), result);
					}
				}
				List list = new ArrayList();
				for (int i = 0; i < imgs.size(); i++) {
					Double resultNum = stringToDouble(imgs.get(i).substring(222, 241).trim());
					Double resultSumPrice = stringToDouble(imgs.get(i).substring(241, 260).trim());
					Integer seqNum = stringToInteger(imgs.get(i).substring(22, 31).trim());
					CancelCusImgResult result = mapImg.get(seqNum);
					if(result!=null){
						result.setResultNum(resultNum);
						result.setResultSumPrice(resultSumPrice);
						list.add(result);
					}
				}
				
				this.receiptResultDao.batchSaveOrUpdate(list);
			}
		}
	}
	
	public Double stringToDouble(String stu){
		double d = 0.0d;
		try {
			d = Double.valueOf(stu);
		} catch (Exception e) {
			System.out.println("转换失败！");
		}
		return d;
	}
	public Integer stringToInteger(String stu){
		Integer i = 0;
		try {
			i = Integer.valueOf(stu);
		} catch (Exception e) {
			System.out.println("转换失败！");
		}
		return i;
	}
	
	/**
	 * 对比回执单耗
	 */
	public Map<String, String> compareBomMessage() {
		// 查询全部未修改的电子账册单耗key
		List<Object[]> keys = receiptResultDao.findEmsBomKey();
		Map<String, String> bomKeyMap = new HashMap<String, String>(keys.size());
		Object[] tmps = null;
		for (int i = 0; i < keys.size(); i++) {
			tmps = keys.get(i);
			bomKeyMap.put(tmps[0].toString(), tmps[1].toString());
		}
		
		// 查询报文中的单耗key
		Set<String> msgBomKeySet = getBomKeyInReceiptMessage();
		
		// 循环比对(删除相同的key，剩下的key就是异常的单耗，可能是海关没有回来回执的)
		Iterator<String> it = msgBomKeySet.iterator();
		while(it.hasNext()) {
			bomKeyMap.remove(it.next());
		}
		
		return bomKeyMap;
	}
	
	/**
	 * // 获取报文中的单耗key
	 * @return
	 */
	private Set<String> getBomKeyInReceiptMessage() {
		File bakDir = new File(CommonUtils.getFinallyDir());
		if(!bakDir.isDirectory()) {
			throw new RuntimeException("单耗回执对比时，路径：" + CommonUtils.getFinallyDir() + "不是目录");
		}
		
		FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().matches("^ems3(1|2)3h2k.*\\.ems");
			}
		};
		File[] files = bakDir.listFiles(ff);
		
		if(files == null || files.length == 0) {
			throw new RuntimeException("单耗回执对比时，目录：" + CommonUtils.getFinallyDir() + "为空");
		}
		Set<String> bomKeys = new HashSet<String>();
		File file = null;
		EMSMessageUtil util = null;
		String tag = null;
		String exgSeqNum = null;
		String version = null;
		String imgSeqNum = null;
		int size = 0;
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			util = EMSMessageUtil.create(file);
			
			size = util.getTags().size();
			for (int j = 0; j < size; j++) {
				tag = util.getTags().get(j);
				if(EMSDataType.EMS_EDI_CM.equals(tag)) {
					exgSeqNum = util.get(j, 22 + 12 + 9, 22 + 12 + 9 + 9);
					version = util.get(j, 22 + 12 + 9 + 9 + 21, 22 + 12 + 9 + 9 + 21 + 9);
					imgSeqNum = util.get(j, 22 + 12 + 9 + 9 + 21 + 9, 22 + 12 + 9 + 9 + 21 + 9 + 9);
					bomKeys.add(Integer.parseInt(exgSeqNum.trim()) 
							+ "/" + Integer.parseInt(version.trim()) 
							+ "/" + Integer.parseInt(imgSeqNum.trim()));
				}
			}
		}
		
		return bomKeys;
	}
	
	/**
	 * 更新bom
	 * @param keys
	 */
	public void updateEmsHead2kBomStateByBomKeys(List<Object[]> keys) {
		
		if(keys == null) {
			return ;
		}
		Object[] key = null;
		List<String> ids = new ArrayList<String>(1000);
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			ids.add((String) key[3]);
			if((i + 1)%1000 == 0) {
				receiptResultDao.updateEmsHead2kBomStateByBomKey((Object[]) ids.toArray());
				ids.clear();
			}
		}
		receiptResultDao.updateEmsHead2kBomStateByBomKey((Object[]) ids.toArray());
	}
	

	// 处理报关清单
	private List modifyMarkEmsBillPass(Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo) {
		try {
			String className = getClassName(messageType, 0);// 取得清单的对应表
			List<Object> saveObjects = new Vector<Object>();
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("listNo", copEmsNo); // 内部清单编号
			conditions.put("emsHeadH2k", emsNo); 
			conditions.put("listState",1); 
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理清单回执时找到多条【清单编号一样】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理清单回执时找到0条【清单编号】的记录，可能是数据不正确。");
			} 
			Object exeHead = result.get(0);
			BeanUtils.setProperty(exeHead, "listQpBillNo", receipt.resultLine.getSeqNo());//Qp上清单编号
			BeanUtils.setProperty(exeHead, "listUniteNo", receipt
					.getBillseqNo());//QP上的统一编号
			BeanUtils.setProperty(exeHead, "listState", 2);
			saveObjects.add(exeHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 处理数据报核
	private List modifyMarkDcr(String messageType, String checkMark,
			String copEmsNo, String emsNo) {
		try {
			String className = getClassName(messageType, 0);
			List<Object> saveObjects = new Vector<Object>();
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("declareState", DeclareState.WAIT_EAA); // 等待审批状态
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理核销回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理核销回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			Object exeHead = result.get(0);
			BeanUtils.setProperty(exeHead, "emsNo", emsNo);
			BeanUtils.setProperty(exeHead, "checkMark", checkMark);
			BeanUtils.setProperty(exeHead, "exeMark", checkMark);
			if (checkMark.equals("1")) { // 入库成功
				BeanUtils.setProperty(exeHead, "declareState",
						DeclareState.WAIT_EAA);
			} else if (checkMark.equals("3")) { // 退单
				BeanUtils.setProperty(exeHead, "declareState",
						DeclareState.APPLY_POR);
			} else if (checkMark.equals("2")) { // 审批通过
				BeanUtils.setProperty(exeHead, "declareState",
						DeclareState.PROCESS_EXE);
			}
			saveObjects.add(exeHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add by xxm 处理未审批通过记录 修改标记 checkMark <> 2
	 * 
	 * @param messageType
	 * @param checkMark
	 */
	private List modifyMarkNoPass(Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo, int emsMergerType) {
		try {
			if (messageType.equals("EMS315") || messageType.equals("EMS325")) {
				return null; // 归并关系归并后不处理
			}
			List<Object> saveObjects = new Vector<Object>();
			String className = getClassName(messageType, emsMergerType);
			// 查找等待审批状态的记录
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("declareState", DeclareState.WAIT_EAA);
			conditions.put("historyState", Boolean.valueOf(false));
			conditions.put("company.id", CommonUtils.getCompany().getId());
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			String sCheckMark = "";
			String sExeMark = "";
			if (checkMark.equals("3")) { // 退单
				sCheckMark = "0";
				sExeMark = "2";
			} else if ((checkMark.equals("1")) || (checkMark.equals("7"))) {// 入库成功或重复申报
				sCheckMark = "1";
				sExeMark = "0";
			} else if (checkMark.equals("4")){
				sCheckMark = "2";
				sExeMark = "0";
			}
			Object waitHead = result.get(0);
			if (!sCheckMark.equals("0") && emsNo !=null && !emsNo.equals("")) { // 当不为退单时才回写帐册号
				BeanUtils.setProperty(waitHead, "emsNo", emsNo);
			}
			BeanUtils.setProperty(waitHead, "checkMark", sCheckMark);//审批标志
			BeanUtils.setProperty(waitHead, "exeMark", sExeMark);
			if (sCheckMark.equals("1")) { // 入库成功
				BeanUtils.setProperty(waitHead, "declareState",
						DeclareState.WAIT_EAA);
			} else if (sCheckMark.equals("0") || sCheckMark.equals("2")) { // 退单
				BeanUtils.setProperty(waitHead, "declareState",
						DeclareState.APPLY_POR);
			}
			BeanUtils.setProperty(waitHead, "historyState", Boolean
					.valueOf(false));
			saveObjects.add(waitHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add by xxm 修改标记 处理归并关系审批通过记录
	 * 
	 * @param messageType
	 * @param checkMark
	 */
	private List modifyMarkEmsMergerPass(EMSMessageUtil util, Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo, int emsMergerType) {
		try {
			if (messageType.equals("EMS315") || messageType.equals("EMS325")) {
				return null; // 归并关系归并后不处理
			}
			List<Object> saveObjects = new Vector<Object>();
			String className = getClassName(messageType, emsMergerType);
			if (getDelcareType(messageType).equals(DelcareType.MODIFY)) { // 变更
				// 查找正在执行状态的记录
				Hashtable<String, Object> conditions = new Hashtable<String, Object>();
				conditions.put("declareState", DeclareState.PROCESS_EXE);
				conditions.put("historyState", Boolean.valueOf(false));
				conditions.put("company.id", CommonUtils.getCompany().getId());
				List result = this.receiptResultDao.commonSearch(className,
						conditions);

				// 分批申报
				String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsSEND);
				if (isSend == null || !"1".equals(isSend)) {
					if (result.size() > 1) {
						throw new RuntimeException(
								"在处理归并关系回执时找到多条【正在执行】的记录，可能是数据不正确。");
					} else if (result.size() == 0) {
						throw new RuntimeException(
								"在处理归并关系回执时找到0条【正在执行】的记录，可能是数据不正确。");
					}
				}
				if (result.size() > 0) {
					Object exeHead = result.get(0);
					BeanUtils.setProperty(exeHead, "historyState", Boolean
							.valueOf(true)); // 将变更记录改为历史记录
					saveObjects.add(exeHead);
				}
			}
			// 查找等待审批状态的记录
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("declareState", DeclareState.WAIT_EAA);
			conditions.put("historyState", Boolean.valueOf(false));
			conditions.put("company.id", CommonUtils.getCompany().getId());
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理归并关系回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理归并关系回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			String sCheckMark = "2";
			String sExeMark = "2";
			Object waitHead = result.get(0);
			BeanUtils.setProperty(waitHead, "emsNo", emsNo);
			BeanUtils.setProperty(waitHead, "checkMark", sCheckMark);
			BeanUtils.setProperty(waitHead, "exeMark", sExeMark);
			if (sCheckMark.equals("2")) { // 审批通过
				// 修改批准日期
				String snewApprDate = receipt.getResultLine().getNoticeDate();
				BeanUtils.setProperty(waitHead, "newApprDate",
						strToStandDate(snewApprDate));
				// 分批申报，修改所有准备申报为已申报
				String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsSEND);
				if (isSend != null && "1".equals(isSend)) {// 分批发送
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.APPLY_POR);
					BeanUtils.setProperty(waitHead, "declareType",
							DelcareType.MODIFY); // 申请变更
					try {
						Integer modifytimes = BeanUtils.getProperty(waitHead,
								"modifyTimes") == null ? 0 : Integer
								.valueOf(BeanUtils.getProperty(waitHead,
										"modifyTimes"));
						BeanUtils.setProperty(waitHead, "modifyTimes",
								modifytimes + 1);// 变更次数要加 1
					} catch (Exception e) {
					}
					System.out.println(" -- 分批申报处理表体数据！");
					long  begin = System.currentTimeMillis();
					initSendState((EmsEdiMergerHead) waitHead);
					long  end = System.currentTimeMillis();
					System.out.println("totaltime:initSendState========"+(end-begin)/1000);
				} else {
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.PROCESS_EXE);
				}
			}
			BeanUtils.setProperty(waitHead, "historyState", Boolean
					.valueOf(false));
			saveObjects.add(waitHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add by xxm 修改标记 处理电子帐册审批通过记录
	 * 
	 * @param messageType
	 * @param checkMark
	 */
	private List modifyMarkEmsH2kPass(EMSMessageUtil util, Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo, int emsMergerType) {
		try {
			List<Object> saveObjects = new Vector<Object>();
			String className = getClassName(messageType, emsMergerType);
			if (getDelcareType(messageType).equals(DelcareType.MODIFY)) { // 变更
				// 查找正在执行状态的记录
				Hashtable<String, Object> conditions = new Hashtable<String, Object>();
				conditions.put("declareState", DeclareState.PROCESS_EXE);
				conditions.put("historyState", Boolean.valueOf(false));
				conditions.put("company.id", CommonUtils.getCompany().getId());
				List result = this.receiptResultDao.commonSearch(className,
						conditions);

				// 分批申报
				String isSend = emsEdiTrDao
						.getBpara(BcusParameter.EmsEdiH2kSend);
				if (isSend == null || !"1".equals(isSend)) {
					if (result.size() > 1) {
						throw new RuntimeException(
								"在处理电子帐册回执时找到多条【正在执行】的记录，可能是数据不正确。");
					} else if (result.size() == 0) {
						throw new RuntimeException(
								"在处理电子帐册回执时找到0条【正在执行】的记录，可能是数据不正确。");
					}
				}
				if (result.size() > 0) {
					Object exeHead = result.get(0);
					BeanUtils.setProperty(exeHead, "historyState", Boolean
							.valueOf(true)); // 将变更记录改为历史记录
					saveObjects.add(exeHead);
				}
			}
			// 查找等待审批状态的记录
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("declareState", DeclareState.WAIT_EAA);
			conditions.put("historyState", Boolean.valueOf(false));
			conditions.put("company.id", CommonUtils.getCompany().getId());
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理电子帐册回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理电子帐册回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			String sCheckMark = "2";
			String sExeMark = "2";
			Object waitHead = result.get(0);
			if(emsNo != null && !emsNo.equals("")){
			BeanUtils.setProperty(waitHead, "emsNo", emsNo);
			}
			BeanUtils.setProperty(waitHead, "checkMark", sCheckMark);
			BeanUtils.setProperty(waitHead, "exeMark", sExeMark);
			if (sCheckMark.equals("2")) { // 审批通过
				// 修改批准日期
				String snewApprDate = receipt.getResultLine().getNoticeDate();
				BeanUtils.setProperty(waitHead, "newApprDate",
						strToStandDate(snewApprDate));
				// 分批申报，修改所有准备申报为已申报
				String isSend = emsEdiTrDao
						.getBpara(BcusParameter.EmsEdiH2kSend);
				if (isSend != null && "1".equals(isSend)) {// 分批发送
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.APPLY_POR);
					BeanUtils.setProperty(waitHead, "declareType",
							DelcareType.MODIFY); // 申请变更
					try {
						Integer modifytimes = BeanUtils.getProperty(waitHead,
								"modifyTimes") == null ? 0 : Integer
								.valueOf(BeanUtils.getProperty(waitHead,
										"modifyTimes"));
						BeanUtils.setProperty(waitHead, "modifyTimes",
								modifytimes + 1);// 变更次数要加 1
					} catch (Exception e) {
						e.printStackTrace();
					}
					String msg = updateEmsSendState((EmsHeadH2k) waitHead, util);
					saveObjects.add(msg);
				} else {
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.PROCESS_EXE);
				}
			}
			BeanUtils.setProperty(waitHead, "historyState", Boolean
					.valueOf(false));
			saveObjects.add(waitHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add by xxm 修改标记
	 * 
	 * @param messageType
	 * @param checkMark
	 */
	private List modifyMarkPass(Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo, int emsMergerType) {
		try {
			if (messageType.equals("EMS315") || messageType.equals("EMS325")) {
				return null; // 归并关系归并后不处理
			}
			List saveObjects = new Vector();
			String className = getClassName(messageType, emsMergerType);
			if (checkMark.equals("2")) { // 审批通过
				if (getDelcareType(messageType).equals(DelcareType.MODIFY)) { // 变更
					// 查找正在执行状态的记录
					Hashtable<String, Object> conditions = new Hashtable<String, Object>();
					conditions.put("declareState", DeclareState.PROCESS_EXE);
					conditions.put("historyState", Boolean.valueOf(false));
					conditions.put("company.id", CommonUtils.getCompany()
							.getId());
					List result = this.receiptResultDao.commonSearch(className,
							conditions);

					// 分批申报
					String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsSEND);
					if (isSend == null || !"1".equals(isSend)) {
						if (result.size() > 1) {
							throw new RuntimeException(
									"在处理回执时找到多条【正在执行】的记录，可能是数据不正确。");
						} else if (result.size() == 0) {
							throw new RuntimeException(
									"在处理回执时找到0条【正在执行】的记录，可能是数据不正确。");
						}
					}
					if (result.size() > 0) {
						Object exeHead = result.get(0);
						BeanUtils.setProperty(exeHead, "historyState", Boolean
								.valueOf(true)); // 将变更记录改为历史记录
						saveObjects.add(exeHead);
					}
				}
			}
			// 查找等待审批状态的记录
			Hashtable conditions = new Hashtable();
			conditions.put("declareState", DeclareState.WAIT_EAA);
			conditions.put("historyState", Boolean.valueOf(false));
			conditions.put("company.id", CommonUtils.getCompany().getId());
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理归并关系回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理归并关系回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			String sCheckMark = "";
			String sExeMark = "";
			if (checkMark.equals("3")) { // 退单
				sCheckMark = "0";
				sExeMark = "2";
			} else if ((checkMark.equals("1")) || (checkMark.equals("7"))
					|| (checkMark.equals("4"))) {// 入库成功或重复申报
				sCheckMark = "1";
				sExeMark = "0";
			} else if (checkMark.equals("2")) { // 审批通过
				sCheckMark = "2";
				sExeMark = "2";
			}
			Object waitHead = result.get(0);
			BeanUtils.setProperty(waitHead, "emsNo", emsNo);
			BeanUtils.setProperty(waitHead, "checkMark", sCheckMark);
			BeanUtils.setProperty(waitHead, "exeMark", sExeMark);
			if (sCheckMark.equals("1")) { // 入库成功
				BeanUtils.setProperty(waitHead, "declareState",
						DeclareState.WAIT_EAA);
			} else if (sCheckMark.equals("0")) { // 退单
				BeanUtils.setProperty(waitHead, "declareState",
						DeclareState.APPLY_POR);
			} else if (sCheckMark.equals("2")) { // 审批通过
				// 修改批准日期
				String snewApprDate = receipt.getResultLine().getNoticeDate();
				BeanUtils.setProperty(waitHead, "newApprDate",
						strToStandDate(snewApprDate));
				// 分批申报，修改所有准备申报为已申报
				String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsSEND);
				if (isSend != null && "1".equals(isSend)) {
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.APPLY_POR);
					BeanUtils.setProperty(waitHead, "declareType",
							DelcareType.MODIFY); // 申请变更
					try {
						Integer modifytimes = BeanUtils.getProperty(waitHead,
								"modifyTimes") == null ? 0 : Integer
								.valueOf(BeanUtils.getProperty(waitHead,
										"modifyTimes"));
						BeanUtils.setProperty(waitHead, "modifyTimes",
								modifytimes + 1);// 变更次数要加 1
					} catch (Exception e) {
					}
					initSendState((EmsEdiMergerHead) waitHead);
				} else {
					BeanUtils.setProperty(waitHead, "declareState",
							DeclareState.PROCESS_EXE);
				}
			}
			BeanUtils.setProperty(waitHead, "historyState", Boolean
					.valueOf(false));
			saveObjects.add(waitHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add by xxm 更新记录
	 * 
	 * @param receipt
	 * @param messageFormat
	 */
	private void modifyData(Receipt receipt, MessageFormat messageFormat,
			String declareType) {
		try {
			List heads = getheads(receipt, messageFormat, declareType); // 当前一条或两条记录
			List bodies = processBodySection(heads, receipt, messageFormat,
					declareType);
			heads = processHeadSection(receipt, messageFormat, declareType); // 表头数据处理

			for (int i = 0; i < heads.size(); i++) {
				this.getReceiptResultDao().commonSaveObject(heads.get(i));
			}
			for (int i = 0; i < bodies.size(); i++) {
				this.getReceiptResultDao().commonSaveObject(bodies.get(i));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<String> getMessageLines(InputStream messageStream) {
		List<String> messageLines = new ArrayList<String>();
		try {
			InputStreamReader fr = new InputStreamReader(messageStream, "GBK"); // by
			// luosheng
			// gbk
			// update
			// 2005/11/29
			LineNumberReader lnr = new LineNumberReader(fr);
			String line;
			while ((line = lnr.readLine()) != null) {
				if (line.trim().equals("")) {
					continue;
				}
				messageLines.add(line);
				// messageLines.add(new String(line.getBytes(),"GBK"));
			}
			fr.close();
			lnr.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return messageLines;
	}

	/**
	 * 处理所有的表头元素(注意表头在一个回执里面只有一个) modifyData 中调用
	 * 
	 * @param sLine
	 * @return 返回更新后的表头对象列表（持久化对象）（在变更的情况下为多条,第一条为处理前变更的，第二条为处理前正在执行的）
	 */
	private List processHeadSection(Receipt receipt,
			MessageFormat messageFormat, String declareType) {
		for (int i = 0; i < receipt.getLines().size(); i++) {
			if (i == 0) {
				continue;
			}
			String sLine = (String) receipt.getLines().get(i);
			String sSectionFlag = sLine.substring(0, 22);
			Section section = messageFormat.getSection(sSectionFlag);
			if (section == null) {
				continue;
			}
			if (isHeadSection(sSectionFlag)) {
				Hashtable lineValues = getLineValues(sLine, messageFormat);
				List saveObjects = new Vector();
				String filterState = null;
				String declareState = null;
				String historyState = null;
				String sCheckMark = null;
				String sExeMark = null;
				String modifyMark = "";
				filterState = DeclareState.WAIT_EAA;
				modifyMark = "0";
				declareState = DeclareState.PROCESS_EXE;
				historyState = "0";
				sCheckMark = "2";
				sExeMark = "2";
				try {
					// 分批申报
					String isSend = emsEdiTrDao
							.getBpara(BcusParameter.EmsEdiH2kSend);
					if (isSend == null || !"1".equals(isSend)) {
						if (receipt.getResultLine().getDeclareType()
								.equals("2")) { // 正在执行
							Object exeHead = findObject(lineValues, section,
									declareState, declareType).get(0);
							BeanUtils.setProperty(exeHead, "historyState", "1");
							saveObjects.add(exeHead);
							System.out.println("-- 已修改正在执行");
						}
					}
					Object sourceHead = findObject(lineValues, section, // 等待审批
							filterState, declareType).get(0);
					doHeadCustomWrite(sourceHead, lineValues, section);
					if (isSend != null && "1".equals(isSend)) {
						BeanUtils.setProperty(sourceHead, "declareState",
								DeclareState.APPLY_POR);
						try {
							Integer modifytimes = BeanUtils.getProperty(
									sourceHead, "modifyTimes") == null ? 0
									: Integer.valueOf(BeanUtils.getProperty(
											sourceHead, "modifyTimes"));
							BeanUtils.setProperty(sourceHead, "modifyTimes",
									modifytimes + 1);// 变更次数要加 1
						} catch (Exception e) {
						}
						initEmsSendState((EmsHeadH2k) sourceHead);
					} else {
						BeanUtils.setProperty(sourceHead, "declareState",
								DeclareState.PROCESS_EXE);
					}
					BeanUtils.setProperty(sourceHead, "historyState",
							historyState);
					BeanUtils.setProperty(sourceHead, "checkMark", sCheckMark);
					BeanUtils.setProperty(sourceHead, "exeMark", sExeMark);
					String snewApprDate = receipt.getResultLine()
							.getNoticeDate();
					// 备案批准日期
					BeanUtils.setProperty(sourceHead, "newApprDate",
							strToStandDate(snewApprDate));
					saveObjects.add(sourceHead);
					List resultHeads = new Vector();// 保证第0个head为等待审批的那一条
					for (int j = saveObjects.size() - 1; j >= 0; j--) {
						resultHeads.add(saveObjects.get(j));
					}
					return resultHeads;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

	/**
	 * 字符转日期
	 * 
	 * @param input
	 * @return
	 */
	public static Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		String yyyy = input.substring(0, 4);
		String mm = input.substring(4, 6);
		String dd = input.substring(6, 8);
		input = yyyy + "-" + mm + "-" + dd + " 00:00:00";
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 */
	private void doHeadCustomWrite(Object changedHead, Hashtable lineValues,
			Section section) {
		for (int i = 0; i < section.getFields().size(); i++) {
			Field field = (Field) section.getFields().get(i);
			try {
				if (field.getCustomWriteFlag().equals("1")) {
					if (field.getFieldName().indexOf(".") >= 0) {
						throw new RuntimeException("在处理回执时,未能对字段:"
								+ field.getFieldName() + "进行回写。");
					}
					BeanUtils.setProperty(changedHead, field.getFieldName(),
							lineValues.get(field.getFieldName()));

				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 */
	private void doBodyCustomWrite(Object changedBody, Hashtable lineValues, // 更新表体
			Section section, Object head) {
		for (int i = 0; i < section.getFields().size(); i++) {
			Field field = (Field) section.getFields().get(i);
			try {
				if (field.getCustomWriteFlag().equals("1")) {
					if (field.getFieldName().equals("emsEdiTrHead.emsNo")) {
						BeanUtils
								.setProperty(changedBody, "emsEdiTrHead", head);
					} else if (field.getFieldName().equals("")) {

					} else if (field.getFieldName().equals("")) {

					} else {
						if (field.getFieldName().indexOf(".") >= 0) {
							throw new RuntimeException("在处理回执时,未能对字段:"
									+ field.getFieldName() + "进行回写。");
						}
						BeanUtils.setProperty(changedBody,
								field.getFieldName(), lineValues.get(field
										.getFieldName()));
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	private List findObject(Hashtable lineValues, Section section,
			String declareState, String declareType) {
		return findObject(lineValues, section, declareState, declareType,
				Boolean.valueOf(false));
	}

	private List findObject(Hashtable lineValues, Section section,
			String declareState, String declareType, Boolean isHistory) {
		return findObject(lineValues, section, declareState, declareType,
				isHistory, false);
	}

	/**
	 */
	private List<Object> findObject(Hashtable lineValues, Section section,
			String declareState, String declareType, Boolean isHistory,
			boolean allowNull) {
		Hashtable conditions = new Hashtable();
		try {
			String className = section.getClassName();
			for (int j = 0; j < section.getUniqueField().size(); j++) {
				String sKey = ((Field) section.getUniqueField().get(j))
						.getFieldName();
				Object oValue = lineValues.get(sKey);
				conditions.put(sKey, oValue);
			}
			System.out.println("-- section.getSectionFlag()"
					+ section.getSectionFlag());
			if (isHeadSection(section.getSectionFlag())) {
				conditions.put("declareState", declareState);
				conditions.put("historyState", isHistory);
				conditions.put("company.id", CommonUtils.getCompany().getId());
			} else {
				addBodyCondition(className, conditions, declareState,
						isHistory, declareType);
			}
			List<Object> result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() <= 0 && allowNull == false)
				throw new RuntimeException(
						"在处理回执时，未能为回执数据找到指定的记录，可能是数据库中的数据不正确。");
			if (result.size() > 1 && allowNull == false)
				throw new RuntimeException(
						"在处理回执时，找到多条对应于同一回执记录的数据，可能是主键设置不正确。");
			return result;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void addBodyCondition(String className, Hashtable conditions,
			String declareState, Boolean isHistory, String declareType) {
		if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg")
				|| className
						.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg")) {
			conditions.put("emsEdiTrHead.declareState", declareState);
			conditions.put("emsEdiTrHead.historyState", isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("emsEdiTrHead.declareType", declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter")
				|| className
						.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter")) {
			conditions.put("emsEdiMergerHead.declareState", declareState);
			conditions.put("emsEdiMergerHead.historyState", isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("emsEdiMergerHead.declareType", declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore")) {
			conditions.put(
					"emsEdiMergerImgAfter.emsEdiMergerHead.declareState",
					declareState);
			conditions.put(
					"emsEdiMergerImgAfter.emsEdiMergerHead.historyState",
					isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("emsEdiMergerImgAfter.emsEdiMergerHead.declareType",
					declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore")) {
			conditions.put(
					"emsEdiMergerExgAfter.emsEdiMergerHead.declareState",
					declareState);
			conditions.put(
					"emsEdiMergerExgAfter.emsEdiMergerHead.historyState", "0");
			conditions.put("emsEdiMergerExgAfter.emsEdiMergerHead.declareType",
					declareType);
			conditions.put("company.id", CommonUtils.getCompany().getId());
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom")) {
			conditions
					.put(
							"emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState",
							declareState);
			conditions
					.put(
							"emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState",
							isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions
					.put(
							"emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareType",
							declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg")
				|| className
						.equals("com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg")) {
			conditions.put("emsHeadH2k.declareState", declareState);
			conditions.put("emsHeadH2k.historyState", isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("emsHeadH2k.declareType", declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom")) {
			conditions.put(
					"emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState",
					declareState);
			conditions.put(
					"emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState",
					isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put(
					"emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareType",
					declareType);
		} else if (className
				.equals("com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg")
				|| className
						.equals("com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg")) {
			conditions.put("emsHeadH2kFas.declareState", declareState);
			conditions.put("emsHeadH2kFas.historyState", isHistory);
			conditions.put("company.id", CommonUtils.getCompany().getId());
			conditions.put("emsHeadH2kFas.declareType", declareType);
		}
	}

	/**
	 * @return
	 */
	private Hashtable getLineValues(String sObjectLine,
			MessageFormat messageFormat) {
		Hashtable lineValues = new Hashtable();
		try {
			String sSectionFlag = sObjectLine.substring(0, 22);
			lineValues.put("sectionFlag", sSectionFlag);

			Section section = messageFormat.getSection(sSectionFlag);
			String tempLine = new String(sObjectLine.getBytes(), 22,
					sObjectLine.getBytes().length - 22);
			for (int i = 0; i < section.getFields().size(); i++) {
				Field field = (Field) section.getFields().get(i);
				int fieldLength = MessageFunctions.GetFormatLength(field
						.getFormat());
				String sValue = new String(tempLine.getBytes(), 0, fieldLength);
				tempLine = new String(tempLine.getBytes(), fieldLength,
						tempLine.getBytes().length - fieldLength);
				try {
					Object oValue = MessageFunctions.GetFormatObject(sValue,
							field.getFormat());
					if (oValue != null) { // 注意Hashtable中不能存入null,但不用担心读取时读不到当前field的值
						lineValues.put(field.getFieldName(), oValue);
					}
				} catch (Exception e) {
					System.out.print("FieldName:" + field.getFieldName()
							+ ";Format:" + field.getFormat());
					throw new RuntimeException(e);
				}

				// }
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return lineValues;
	}

	private List getheads(Receipt receipt, MessageFormat messageFormat,
			String declareType) {
		List resultObjects = new Vector();
		String sHeadLine = null;
		String sHeadSectionFlag = null;
		for (int i = 0; i < receipt.getLines().size(); i++) {
			String sTemp = (String) receipt.getLines().get(i);
			String sSectionFlag = sTemp.substring(0, 22);
			if (isHeadSection(sSectionFlag)) {
				sHeadLine = sTemp;
				sHeadSectionFlag = sSectionFlag;
			}
		}
		Hashtable lineValues = getLineValues(sHeadLine, messageFormat);
		Section section = messageFormat.getSection(sHeadSectionFlag);
		if (receipt.getResultLine().getDeclareType().equals("2")) { // 正在执行
			Object exeHead = findObject(lineValues, section, "3", declareType)
					.get(0);
			resultObjects.add(exeHead);
		}
		Object sourceHead = findObject(lineValues, section, "2", declareType)
				.get(0);
		resultObjects.add(sourceHead); // 等待审批
		return resultObjects;
	}

	private List processBodySection(List heads, Receipt receipt,// 处理表体
			MessageFormat messageFormat, String declareType) {
		List saveObjects = new Vector();
		for (int i = 0; i < receipt.getLines().size(); i++) {
			String sLine = (String) receipt.getLines().get(i);
			String sSectionFlag = sLine.substring(0, 22);
			Section section = messageFormat.getSection(sSectionFlag);
			if (section == null) {
				continue;
			}
			if (!isHeadSection(sSectionFlag)) {
				Hashtable lineValues = getLineValues(sLine, messageFormat);
				String declareState = null;
				String historyState = null;
				try {
					String filterState = "2";
					Object sourceBody = findObject(lineValues, section, // 等待审批
							filterState, declareType).get(0);
					doBodyCustomWrite(sourceBody, lineValues, section, heads
							.get(0));
					saveObjects.add(sourceBody);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return saveObjects;
	}

	private boolean isHeadSection(String sSectionFlag) {
		if (sSectionFlag.equals("<EMS_EDI_TR_HEAD     >")) {
			return true;
		}
		if (sSectionFlag.equals("<EMS_EDI_HEAD_H2K    >")) {
			return true;
		}
		if (sSectionFlag.equals("<EMS_EDI_FAS_HEAD    >")) {
			return true;
		}
		if (sSectionFlag.equals("<EMS_EDI_COL_HEAD    >")) {
			return true;
		}
		if (sSectionFlag.equals("<EMS_EDI_DCR_HEAD    >")) {
			return true;
		}
		return false;
	}

	private InputStream getFormatFile(String sMessageType, int declareType) {
		String sFormatFile = null;
		if (declareType == 1) {
			if (sMessageType.equals("EMS311")) { // 经营范围
				sFormatFile = "Msg_EMS211.xml";
			} else if (sMessageType.equals("EMS313")) { // 电子帐册(归并后部分)
				sFormatFile = "Msg_EMS213.xml";
			} else if (sMessageType.equals("EMS314")) { // 电子分册
				sFormatFile = "Msg_EMS214.xml";
			} else if (sMessageType.equals("EMS312")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS212.xml";
			} else if (sMessageType.equals("EMS315")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS215.xml";
			} else if (sMessageType.equals("EMS331")) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else if (declareType == 2) { // 1为备案, 2为变更
			if (sMessageType.equals("EMS321")) { // 经营范围
				sFormatFile = "Msg_EMS221.xml";
			} else if (sMessageType.equals("EMS323")) { // 电子帐册(归并后部分)
				sFormatFile = "Msg_EMS223.xml";
			} else if (sMessageType.equals("EMS324")) { // 电子分册
				sFormatFile = "Msg_EMS224.xml";
			} else if (sMessageType.equals("EMS322")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS222.xml";
			} else if (sMessageType.equals("EMS325")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS225.xml";
			} else if (sMessageType.equals("EMS331")) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else if (sMessageType.equals("EMS330")
				&& sMessageType.equals("EMS331")) {
			if (sMessageType.equals("EMS330")) { // 电子账册联网核查(只有备案,没有变更)
				sFormatFile = "Msg_EMS230.xml";
			} else if (sMessageType.equals("EMS331")) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else {
			if (sMessageType.equals("EMS317")) {
				sFormatFile = "Msg_EMS217.xml"; // 取得清单的报文
			}
		}
		if (sFormatFile == null) {
			throw new RuntimeException("未知的申报类型：" + declareType + "及报文类型："
					+ sMessageType);
		}
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				"com/bestway/bcus/message/messageformat/" + sFormatFile);
		return is;
	}

	private int getDeclareType(List lines) {
		String str = null;
		for (int i = 0; i < lines.size(); i++) {
			if (((String) lines.get(i)).indexOf("<EMS_EDI_RESULT      >") > -1) {
				str = (String) lines.get(i);
				String sDeclareType = new String(str.getBytes(), (1 + 22 + 10
						+ 20 + 18 + 12 + 1) - 1, 1);
				try {
					return Integer.parseInt(sDeclareType);
				} catch (Exception ex) {
					throw new RuntimeException("获取回执的申报类型时出错,可能是报文格式不正确");
				}
			}
		}
		throw new RuntimeException("未能发现标记为<EMS_EDI_RESULT      >的行，可能是回执文件不正确");
	}

	// 区分归并关系还是电子帐册
	private int getEmsMergerHeadH2kType(List lines) {
		String str = null;
		for (int i = 0; i < lines.size(); i++) {
			if (((String) lines.get(i)).indexOf("<EMS_EDI_RESULT      >") > -1) {
				str = (String) lines.get(i);
				String sDeclareType = new String(str.getBytes(), (1 + 22 + 10
						+ 20 + 18 + 12 + 1) - 1 - 1, 1);
				try {
					return Integer.parseInt(sDeclareType);
				} catch (Exception ex) {
					throw new RuntimeException(
							"获取回执的申报类型时出错(归并关系与电子帐册区分),可能是报文格式不正确");
				}
			}
		}
		throw new RuntimeException("未能发现标记为<EMS_EDI_RESULT      >的行，可能是回执文件不正确");
	}

	/**
	 * 记录回执信息(保存回执信息到数据库)
	 * @param resultLine
	 * @param fileName
	 */
	private void writeReceiveInfo(ResultLine resultLine, String fileName) {
		ReceiptResult receiptResult = new ReceiptResult();
		try {
			receiptResult.setApplType((String) MessageFunctions
					.GetFormatObject(resultLine.getApplType(), "X"));
			receiptResult.setChkMark((String) MessageFunctions.GetFormatObject(
					resultLine.getChkMark(), "X"));
			receiptResult.setCompany(CommonUtils.getCompany());
			receiptResult.setCopEmsNo((String) MessageFunctions
					.GetFormatObject(resultLine.getCopEmsNo(), "X(20)"));
			receiptResult.setDeclareType((String) MessageFunctions
					.GetFormatObject(resultLine.getDeclareType(), "X"));
			receiptResult.setEmsNo((String) MessageFunctions.GetFormatObject(
					resultLine.getEmsNo(), "X(12)"));
			receiptResult.setNote((String) MessageFunctions.GetFormatObject(
					resultLine.getNote(), "X(255)"));

			// 备案批准日期
			String snewApprDate = resultLine.getNoticeDate();
			receiptResult.setNoticeDate(strToStandDate(snewApprDate));

			receiptResult.setNoticeTime((Date) MessageFunctions
					.GetFormatObject(resultLine.getNoticeTime(), "HHMMSS"));
			receiptResult.setSeqNo(resultLine.getSeqNo());
			receiptResult.setTradeCode((String) MessageFunctions
					.GetFormatObject(resultLine.getTradeCode(), "X(10)"));
			receiptResult.setUserName(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptResult.setCompany(CommonUtils.getCompany());
			receiptResult.setFileName(fileName);
			receiptResultDao.saveReceiptResult(receiptResult);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<MyFile> getUnProcessFiles(String type) {
		File path = new File("Bill".equals(type) ? CommonUtils.getRecvBillDir()
				: CommonUtils.getRecvDir());
		File[] files = path.listFiles();
		
		List<MyFile> fileList = new ArrayList<MyFile>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()
						&& (files[i].getName().trim().endsWith(".EMS") || files[i]
								.getName().trim().endsWith(".ems"))) {
					if ((type.equals("EmsHeadH2k") || type
							.equals("EmsEdiMergerHead")) // 电子帐册，归并关系
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS313") || files[i]
									.getName().toUpperCase().substring(0, 6)
									.equals("EMS323"))) {
						fileList.add(new MyFile(files[i]));
					} else if (type.equals("EmsEdiTrHead") // 经营范围
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS311") || files[i]
									.getName().toUpperCase().substring(0, 6)
									.equals("EMS321"))) {
						fileList.add(new MyFile(files[i]));
					} else if (type.equals("CancelHead") // 核销
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS331"))) {
						fileList.add(new MyFile(files[i]));
					} else if (type.equals("EmsHeadH2kFas") // 分册
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS314") || files[i]
									.getName().toUpperCase().substring(0, 6)
									.equals("EMS324"))) {
						fileList.add(new MyFile(files[i]));
					} else if (type.equals("CheckHead") // 中期核查
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS330"))) {
						fileList.add(new MyFile(files[i]));
					} else if (type.equals("Bill") // 清单
							&& (files[i].getName().toUpperCase()
									.substring(0, 6).equals("EMS317"))) {// 显示处理回执应是317
						// by
						// lxr
						fileList.add(new MyFile(files[i]));
					}

				}
			}
		}
		return fileList;
	}

	/**
	 * 客户端-将处理完的回执移动到处理完的目录下
	 * 
	 * @param file
	 * @return
	 */
	public boolean moveFileToProcessedDir(File file) {
		try {
			InputStream is = new FileInputStream(file);
			InputStreamReader fr = new InputStreamReader(is);
			LineNumberReader lnr = new LineNumberReader(fr, 1);
			String line = lnr.readLine();
			lnr.close();
			fr.close();
			is.close();
			String msgType = line.substring(0, 6);
			/*
			 * if (msgType.toUpperCase().equals("EMS312")||
			 * msgType.toUpperCase().equals("EMS322")) { //归并关系 File path = new
			 * File(CommonUtils.getRecvDir() + File.separator); FilenameFilter
			 * filter = new FilenameFilter() { public boolean accept(File arg0,
			 * String arg1) { if (arg1.matches("EMS3.5.*")) { return true; }
			 * else return false; } }; File[] file3x5 = path.listFiles(filter);
			 * for (int i = 0; i < file3x5.length; i++) { File f = file3x5[i];
			 * //f.renameTo(new File(CommonUtils.getFinallyDir() // +
			 * File.separator + f.getName())); File fDest = new
			 * File(CommonUtils.getFinallyDir() + File.separator + f.getName());
			 * moveFile(f,fDest); } }
			 */
			File destFile = new File(CommonUtils.getFinallyDir()
					+ File.separator + file.getName());
			if (destFile.exists()) {
				destFile.delete();
			}
			// if (file.renameTo(destFile)) {
			// return true;
			// }
			if (moveFile(file, destFile)) {
				return true;
			}

		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @return Returns the exportMessageLogic.
	 */
	public ExportMessageLogic getExportMessageLogic() {
		return exportMessageLogic;
	}

	/**
	 * @param exportMessageLogic
	 *            The exportMessageLogic to set.
	 */
	public void setExportMessageLogic(ExportMessageLogic exportMessageLogic) {
		this.exportMessageLogic = exportMessageLogic;
	}

	private int getEdiType(String sMessageType, int emsMergerType) {
		int yy = 0;
		if ((sMessageType.equals("EMS311")) || (sMessageType.equals("EMS321"))) {
			yy = EdiType.EMS_EDI_TR;
		} else if ((sMessageType.equals("EMS312"))
				|| (sMessageType.equals("EMS322"))) {
			yy = EdiType.EMS_EDI_MERGER_BEFORE;
		} else if ((sMessageType.equals("EMS315"))
				|| (sMessageType.equals("EMS325"))) {
			yy = EdiType.EMS_EDI_MERGER_AFTER;
		} else if ((sMessageType.equals("EMS313"))
				|| (sMessageType.equals("EMS323"))) {
			if (emsMergerType == 5) {
				yy = EdiType.EMS_EDI_MERGER_BEFORE;
			} else {
				yy = EdiType.EMS_EDI_H2K;
			}
		} else if ((sMessageType.equals("EMS314"))
				|| (sMessageType.equals("EMS324"))) {
			yy = EdiType.EMS_EDI_H2K_CLEFT;
		} else if (sMessageType.equals("EMS330")) {
			yy = EdiType.CHECK;
		} else if (sMessageType.equals("EMS331")) {
			yy = EdiType.CANCEL;
		}

		return yy;
	}

	/**
	 * 得到申报类型 在保存处理回执信息中使用
	 * 
	 * @param delcareType
	 * @return
	 */
	private String getDelcareType(String delcareType) {
		String yy = "";
		if ((delcareType.equals("EMS311")) || (delcareType.equals("EMS312"))
				|| (delcareType.equals("EMS313"))
				|| (delcareType.equals("EMS314"))
				|| (delcareType.equals("EMS315"))
				|| (delcareType.equals("EMS331"))
				|| (delcareType.equals("EMS330"))) {
			yy = DelcareType.APPLICATION;
		}
		if ((delcareType.equals("EMS321")) || (delcareType.equals("EMS322"))
				|| (delcareType.equals("EMS323"))
				|| (delcareType.equals("EMS324"))
				|| (delcareType.equals("EMS325"))) {
			yy = DelcareType.MODIFY;
		}
		return yy;
	}

	// 移动文件
	private boolean moveFile(File fSrc, File fDest) {
		try {
			FileUtils.copyFile(fSrc, fDest);
			if (!fSrc.delete()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getClassName(String msgType, int emsMergerType) {
		if (msgType.toUpperCase().equals("EMS312")
				|| msgType.toUpperCase().equals("EMS322")
				|| msgType.toUpperCase().equals("EMS315")
				|| msgType.toUpperCase().equals("EMS325")) { // 归并关系
			return "com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead";
		} else if (msgType.toUpperCase().equals("EMS311")
				|| msgType.toUpperCase().equals("EMS321")) {// 经营范围
			return "com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead";
		} else if (msgType.toUpperCase().equals("EMS313")
				|| msgType.toUpperCase().equals("EMS323")) {// 电子帐册,归并关系
			if (emsMergerType == 5) {
				return "com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead";
			} else {
				return "com.bestway.bcus.manualdeclare.entity.EmsHeadH2k";
			}
		} else if (msgType.toUpperCase().equals("EMS314")
				|| msgType.toUpperCase().equals("EMS324")) {// 电子分册
			return "com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas";
		} else if (msgType.toUpperCase().equals("EMS331")) {// 数据核销
			return "com.bestway.bcus.checkcancel.entity.CancelCusHead";
		} else if (msgType.toUpperCase().equals("EMS317")) {// 清单
			return "com.bestway.bcus.enc.entity.ApplyToCustomsBillList";
		}
		return null;
	}

	// 初始化申报状态
	private void initSendState(EmsEdiMergerHead head) {
		// 料件归并后
//		List list = emsEdiTrDao.findEmsEdiMergerImgAfterForProcessMess(head);
//		for (int i = 0; i < list.size(); i++) {
//			EmsEdiMergerImgAfter obj = (EmsEdiMergerImgAfter) list.get(i);
//			if (obj.getSendState() != null
//					&& obj.getSendState().equals(
//							Integer.valueOf(SendState.WAIT_SEND))) {
//				obj.setSendState(Integer.valueOf(SendState.SEND));
//				if (obj.getModifyMark() != null
//						&& !obj.getModifyMark()
//								.equals(ModifyMarkState.UNCHANGE)) {
//					obj.setModifyMark(ModifyMarkState.UNCHANGE);
//				}
//			}
//			emsEdiTrDao.saveEmsEdiMergerImgAfter(obj);
//		}
		emsEdiTrDao.updateEmsEdiMergerImgExgBomSendStateModifyMark(head,
				SendState.WAIT_SEND, ImgExgMergerType.MERGERIMG_AFTER);
		// 成品归并后
//		list = emsEdiTrDao.findEmsEdiMergerExgAfterForProceMess(head);
//		for (int i = 0; i < list.size(); i++) {
//			EmsEdiMergerExgAfter obj = (EmsEdiMergerExgAfter) list.get(i);
//			if (obj.getSendState() != null
//					&& obj.getSendState().equals(
//							Integer.valueOf(SendState.WAIT_SEND))) {
//				obj.setSendState(Integer.valueOf(SendState.SEND));
//				if (obj.getModifyMark() != null
//						&& !obj.getModifyMark()
//								.equals(ModifyMarkState.UNCHANGE)) {
//					obj.setModifyMark(ModifyMarkState.UNCHANGE);
//				}
//			}
//			emsEdiTrDao.saveEmsEdiMergerExgAfter(obj);
//		}
		emsEdiTrDao.updateEmsEdiMergerImgExgBomSendStateModifyMark(head,
				SendState.WAIT_SEND, ImgExgMergerType.MERGEREXG_AFTER);
		// 料件归并前
//		list = emsEdiTrDao.findEmsEdiMergerImgBeforeForProceMess(head);
//		for (int i = 0; i < list.size(); i++) {
//			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
//			if (obj.getSendState() != null
//					&& obj.getSendState().equals(
//							Integer.valueOf(SendState.WAIT_SEND))) {
//				obj.setSendState(Integer.valueOf(SendState.SEND));
//				if (obj.getModifyMark() != null
//						&& !obj.getModifyMark()
//								.equals(ModifyMarkState.UNCHANGE)) {
//					obj.setModifyMark(ModifyMarkState.UNCHANGE);
//				}
//			}
//			emsEdiTrDao.saveEmsEdiMergerImgBefore(obj);
//		}
		emsEdiTrDao.updateEmsEdiMergerImgExgBomSendStateModifyMark(head,
				SendState.WAIT_SEND, ImgExgMergerType.MERGERIMG_BEFORE);
		// 成品归并前
//		list = emsEdiTrDao.findEmsEdiMergerExgBeforeForProceMess(head);
//		for (int i = 0; i < list.size(); i++) {
//			EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list.get(i);
//			if (obj.getSendState() != null
//					&& obj.getSendState().equals(
//							Integer.valueOf(SendState.WAIT_SEND))) {
//				obj.setSendState(Integer.valueOf(SendState.SEND));
//				if (obj.getModifyMark() != null
//						&& !obj.getModifyMark()
//								.equals(ModifyMarkState.UNCHANGE)) {
//					obj.setModifyMark(ModifyMarkState.UNCHANGE);
//				}
//			}
//			emsEdiTrDao.saveEmsEdiMergerExgBefore(obj);
//		}
		emsEdiTrDao.updateEmsEdiMergerImgExgBomSendStateModifyMark(head,
				SendState.WAIT_SEND, ImgExgMergerType.MERGEREXG_BEFORE);
		// 成品归并BOM
//		list = emsEdiTrDao.findEmsEdiMergerBomForProceMess(head);
//		for (int i = 0; i < list.size(); i++) {
//			EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list.get(i);
//			if (obj.getSendState() != null
//					&& obj.getSendState().equals(
//							Integer.valueOf(SendState.WAIT_SEND))) {
//				obj.setSendState(Integer.valueOf(SendState.SEND));
//				if (obj.getModifyMark() != null
//						&& !obj.getModifyMark()
//								.equals(ModifyMarkState.UNCHANGE)) {
//					obj.setModifyMark(ModifyMarkState.UNCHANGE);
//				}
//			}
//			emsEdiTrDao.saveEmsEdiMergerExgBom(obj);
//		}
		emsEdiTrDao.updateEmsEdiMergerImgExgBomSendStateModifyMark(head,
				SendState.WAIT_SEND, ImgExgMergerType.MERGEREXG_BOM);
	}
	
	
	private String updateEmsSendState(EmsHeadH2k head, EMSMessageUtil util) {
		
		// 料件序号集
		Set<Integer> imgSeqNums = new HashSet<Integer>();
		
		// 成品序号集
		Set<Integer> exgSeqNums = new HashSet<Integer>();
		
		String tag = null;// 报文标签
		String seqNum = null;
		String version = null;
		String imgSeqNum = null;
		Integer intSeqNum = null;
		Integer intVersion = null;
		Map<Integer, Set<Integer>> versionMap = null;
		Set<Integer> cmSubSeqNums = null; // 单耗子件序号集
		Map<Integer, Map<Integer, Set<Integer>>> cm = new HashMap<Integer, Map<Integer, Set<Integer>>>();
		for (int i = 0; i < util.getTags().size(); i++) {
			tag = util.getTags().get(i);
			if(EMSDataType.EMS_EDI_IMG.equals(tag)) {
				seqNum = util.get(i, 22 + 12 + 9, 22 + 12 + 9 + 9);
				if(CommonUtils.notEmpty(seqNum)) {
					imgSeqNums.add(Integer.parseInt(seqNum.trim()));
				}
			} else if(EMSDataType.EMS_EDI_EXG.equals(tag)) {
				seqNum = util.get(i, 22 + 12 + 9, 22 + 12 + 9 + 9);
				if(CommonUtils.notEmpty(seqNum)) {
					exgSeqNums.add(Integer.parseInt(seqNum.trim()));
				}
			} else if(EMSDataType.EMS_EDI_CM.equals(tag)) {
				seqNum = util.get(i, 22 + 12 + 9, 22 + 12 + 9 + 9);
				version = util.get(i, 22 + 12 + 9 + 9 + 21, 22 + 12 + 9 + 9 + 21 + 9);
				imgSeqNum = util.get(i, 22 + 12 + 9 + 9 + 21 + 9, 22 + 12 + 9 + 9 + 21 + 9 + 9);
				
				intSeqNum = Integer.parseInt(seqNum.trim());
				intVersion = Integer.parseInt(version.trim());
				versionMap = cm.get(intSeqNum);
				if(versionMap == null) {
					versionMap = new HashMap<Integer, Set<Integer>>();
					
					cm.put(intSeqNum, versionMap);
				}
				
				cmSubSeqNums = versionMap.get(intVersion);
				
				if(cmSubSeqNums == null) {
					cmSubSeqNums = new HashSet<Integer>();
					
					versionMap.put(intVersion, cmSubSeqNums);
				}
				
				// 添加料件序号
				cmSubSeqNums.add(Integer.parseInt(imgSeqNum.trim()));
			}
		}
		
		
		// 更新信息
		StringBuilder info = new StringBuilder();
		
		// 失败信息
		StringBuilder fail = new StringBuilder();
		
		// 信息记录
		StringBuilder log = new StringBuilder();
		
		// 更新成功数目
		int success = 0;
		
		// 总共大小
		int size = 0;
		
		log.append("\n================开始更新【料件】================\n");
		if (imgSeqNums.size() > 0) {
			
			// 料件归并后
			List<EmsHeadH2kImg> list = emsEdiTrDao.findEmsHeadH2kImgForProceMess(head);
			EmsHeadH2kImg img = null;
			size = list.size();
			for (int i = 0; i < size; i++) {
				img = list.get(i);
				
				if(!imgSeqNums.contains(img.getSeqNum())) {
					fail.append("料件更新失败，回执不存在记录，料件序号：" + img.getSeqNum() + "。\n");
					continue;
				}
				
				if (img.getSendState() != null
						&& img.getSendState().equals(
								Integer.valueOf(SendState.WAIT_SEND))) {
					img.setSendState(Integer.valueOf(SendState.SEND));
					if (img.getModifyMark() != null
							&& !img.getModifyMark()
									.equals(ModifyMarkState.UNCHANGE)) {
						img.setModifyMark(ModifyMarkState.UNCHANGE);
						info.append("【料件更新成功】，已更新修改标志和状态，料件序号：" + img.getSeqNum() + "。\n");
						success ++;
					} else {
						fail.append("料件更新失败，料件修改标志已经是‘未修改’或null，料件序号：" + img.getSeqNum() + "。\n");
					}
				} else {
					fail.append("料件更新失败，料件状态不是‘准备发送’，料件序号：" + img.getSeqNum() + "。\n");
				}
				
				emsEdiTrDao.saveEmsHeadH2kImg(img);
			}
			log.append("需要更新的记录" + size + "条，成功更新" + success + "条，更新失败"
					+ (size - success) + "条。\n更新日志：\n");
			log.append(fail);
			log.append(info);
			fail.setLength(0);
			info.setLength(0);
		} else {
			log.append("回执中没有需要更新【料件】记录！\n");
		}
		log.append("================结束更新【料件】================\n");
		
		
		log.append("\n================开始更新【成品】================\n");
		if (exgSeqNums.size() > 0) {
			success = 0;
			// 成品归并后
			List<EmsHeadH2kExg> list = emsEdiTrDao.findEmsHeadH2kExgForProceMess(head);
			EmsHeadH2kExg exg = null;
			size = list.size();
			for (int i = 0; i < size; i++) {
				exg = (EmsHeadH2kExg) list.get(i);
				
				if(!exgSeqNums.contains(exg.getSeqNum())) {
					fail.append("成品更新失败，回执不存在记录，成品序号：" + exg.getSeqNum() + "。\n");
					continue;
				}
				
				if (exg.getSendState() != null
						&& exg.getSendState().equals(
								Integer.valueOf(SendState.WAIT_SEND))) {
					exg.setSendState(Integer.valueOf(SendState.SEND));
					if (exg.getModifyMark() != null
							&& !exg.getModifyMark()
									.equals(ModifyMarkState.UNCHANGE)) {
						exg.setModifyMark(ModifyMarkState.UNCHANGE);
						info.append("【成品更新成功】，已更新修改标志和状态，成品序号：" + exg.getSeqNum() + "。\n");
						success ++;
					} else {
						fail.append("成品更新失败，成品修改标志已经是‘未修改’或null，成品序号：" + exg.getSeqNum() + "。\n");
					}
				} else {
					fail.append("成品更新失败，成品状态不是‘准备发送’，成品序号：" + exg.getSeqNum() + "。\n");
				}
				emsEdiTrDao.saveEmsHeadH2kExg(exg);
			}
			log.append("需要更新的记录" + size + "条，成功更新" + success + "条，更新失败"
					+ (size - success) + "条。\n更新日志：\n");
			log.append(fail);
			log.append(info);
			fail.setLength(0);
			info.setLength(0);
		} else {
			log.append("回执中没有需要更新【成品】记录！\n");
		}
		log.append("================结束更新【成品】================\n");
		
		
		log.append("\n================开始更新【单耗】================\n");
		if (cm.size() > 0) {
			success = 0;
			// 成品归并BOM
			List<EmsHeadH2kBom> list = emsEdiTrDao.findEmsHeadH2kBomForProceMess(head);
			EmsHeadH2kBom bom = null;
			size = list.size();
			for (int i = 0; i < size; i++) {
				bom = list.get(i);
				intSeqNum = bom.getEmsHeadH2kExg().getSeqNum();
				intVersion = bom.getEmsHeadH2kVersion().getVersion();
				versionMap = cm.get(intSeqNum);
				
				if (versionMap == null) {
					fail.append("单耗更新失败，回执不存在记录，成品序号：" + intSeqNum + 
							" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
					continue;
				}
				
				cmSubSeqNums = versionMap.get(intVersion);
				
				if(cmSubSeqNums == null) {
					fail.append("单耗更新失败，回执不存在记录，成品序号：" + intSeqNum + 
							" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
					continue;
				}
				
				if(cmSubSeqNums.contains(bom.getSeqNum())) {
					if (bom.getSendState() != null
							&& bom.getSendState().equals(
									Integer.valueOf(SendState.WAIT_SEND))) {
						bom.setSendState(Integer.valueOf(SendState.SEND));
						if (bom.getModifyMark() != null
								&& !bom.getModifyMark()
										.equals(ModifyMarkState.UNCHANGE)) {
							bom.setModifyMark(ModifyMarkState.UNCHANGE);
							info.append("【单耗更新成功】，已更新修改标志和状态，成品序号：" + intSeqNum + 
									" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
							success ++;
						} else {
							fail.append("单耗更新失败，BOM修改标志已经是‘未修改’或null，成品序号：" + intSeqNum + 
									" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
						}
						emsEdiTrDao.saveEmsHeadH2kBom(bom);
					} else {
						fail.append("单耗更新失败，BOM状态不是‘准备发送’，成品序号：" + intSeqNum + 
								" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
					}
				} else {
					fail.append("单耗更新失败，回执不存在记录，成品序号：" + intSeqNum + 
							" 版本号：" + intVersion + " 料件序号：" + bom.getSeqNum() + "。\n");
				}
			}
			log.append("需要更新的记录" + size + "条，成功更新" + success + "条，更新失败"
				+ (size - success) + "条。\n更新日志：\n");
			log.append(fail);
			log.append(info);
			fail.setLength(0);
			info.setLength(0);
		} else {
			log.append("回执中没有需要更新【单耗】记录！\n");
		}
		log.append("================结束更新【单耗】================\n");
		
		return log.toString();
	}

	// 初始化申报状态
	private void initEmsSendState(EmsHeadH2k head) {
		// 料件归并后
		List list = emsEdiTrDao.findEmsHeadH2kImgForProceMess(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg obj = (EmsHeadH2kImg) list.get(i);
			if (obj.getSendState() != null
					&& obj.getSendState().equals(
							Integer.valueOf(SendState.WAIT_SEND))) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
				if (obj.getModifyMark() != null
						&& !obj.getModifyMark()
								.equals(ModifyMarkState.UNCHANGE)) {
					obj.setModifyMark(ModifyMarkState.UNCHANGE);
				}
			}
			emsEdiTrDao.saveEmsHeadH2kImg(obj);
		}
		// 成品归并后
		list = emsEdiTrDao.findEmsHeadH2kExgForProceMess(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg obj = (EmsHeadH2kExg) list.get(i);
			if (obj.getSendState() != null
					&& obj.getSendState().equals(
							Integer.valueOf(SendState.WAIT_SEND))) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
				if (obj.getModifyMark() != null
						&& !obj.getModifyMark()
								.equals(ModifyMarkState.UNCHANGE)) {
					obj.setModifyMark(ModifyMarkState.UNCHANGE);
				}
			}
			emsEdiTrDao.saveEmsHeadH2kExg(obj);
		}

		// 成品归并BOM
		list = emsEdiTrDao.findEmsHeadH2kBomForProceMess(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
			if (obj.getSendState() != null
					&& obj.getSendState().equals(
							Integer.valueOf(SendState.WAIT_SEND))) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
				if (obj.getModifyMark() != null
						&& !obj.getModifyMark()
								.equals(ModifyMarkState.UNCHANGE)) {
					obj.setModifyMark(ModifyMarkState.UNCHANGE);
				}
			}
			emsEdiTrDao.saveEmsHeadH2kBom(obj);
		}
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * Add by xxm 修改标记 处理经营范围审批通过记录
	 * 
	 * @param messageType
	 * @param checkMark
	 */
	private List modifyMarkEmsTrPass(Receipt receipt, String messageType,
			String checkMark, String copEmsNo, String emsNo, int emsMergerType) {
		try {
			List<Object> saveObjects = new Vector<Object>();
			String className = getClassName(messageType, emsMergerType);
			if (getDelcareType(messageType).equals(DelcareType.MODIFY)) { // 变更
				// 查找正在执行状态的记录
				Hashtable<String, Object> conditions = new Hashtable<String, Object>();
				conditions.put("declareState", DeclareState.PROCESS_EXE);
				conditions.put("historyState", Boolean.valueOf(false));
				conditions.put("company.id", CommonUtils.getCompany().getId());
				List result = this.receiptResultDao.commonSearch(className,
						conditions);
				if (result.size() > 1) {
					throw new RuntimeException(
							"在处理经营范围回执时找到多条【正在执行】的记录，可能是数据不正确。");
				} else if (result.size() == 0) {
					throw new RuntimeException(
							"在处理经营范围回执时找到0条【正在执行】的记录，可能是数据不正确。");
				}
				if (result.size() > 0) {
					Object exeHead = result.get(0);
					BeanUtils.setProperty(exeHead, "historyState", Boolean
							.valueOf(true)); // 将变更记录改为历史记录
					saveObjects.add(exeHead);
				}
			}
			// 查找等待审批状态的记录
			Hashtable<String, Object> conditions = new Hashtable<String, Object>();
			conditions.put("declareState", DeclareState.WAIT_EAA);
			conditions.put("historyState", Boolean.valueOf(false));
			conditions.put("company.id", CommonUtils.getCompany().getId());
			List result = this.receiptResultDao.commonSearch(className,
					conditions);
			if (result.size() > 1) {
				throw new RuntimeException("在处理经营范围回执时找到多条【等待审批】的记录，可能是数据不正确。");
			} else if (result.size() == 0) {
				throw new RuntimeException("在处理经营范围回执时找到0条【等待审批】的记录，可能是数据不正确。");
			}
			String sCheckMark = "2";
			String sExeMark = "2";
			Object waitHead = result.get(0);
			BeanUtils.setProperty(waitHead, "emsNo", emsNo);
			BeanUtils.setProperty(waitHead, "checkMark", sCheckMark);
			BeanUtils.setProperty(waitHead, "exeMark", sExeMark);
			if (sCheckMark.equals("2")) { // 审批通过
				// 修改批准日期
				String snewApprDate = receipt.getResultLine().getNoticeDate();
				BeanUtils.setProperty(waitHead, "newApprDate",
						strToStandDate(snewApprDate));
				BeanUtils.setProperty(waitHead, "declareState",
						DeclareState.PROCESS_EXE);
			}
			BeanUtils.setProperty(waitHead, "historyState", Boolean
					.valueOf(false));
			saveObjects.add(waitHead);
			return saveObjects;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
