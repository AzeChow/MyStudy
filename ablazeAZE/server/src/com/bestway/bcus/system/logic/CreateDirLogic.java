/*
 * Created on 2004-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.logic;

import java.io.File;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.dao.CreateDirDao;
import com.bestway.bcus.system.entity.CommonCheckNull;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ParameterType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * com.bestway.bcus.system.logic.CreateDirLogic
 * 
 * @author 001
 */
public class CreateDirLogic {
	private CreateDirDao createDirDao = null;

	private CompanyDao companyDao = null;

	public void init() {

	}

	/**
	 * 创建发送目录
	 * 
	 * @param sendDir
	 *            发送目录
	 * @return String 发送目录绝对路径
	 */
	private String createSendDir(String sendDir) {
		File isendDir = new File(sendDir);
		if (!isendDir.exists()) {
			isendDir.mkdir();
		}
		File isbgd = new File(sendDir + "/sendBgd");
		if (!isbgd.exists()) {
			isbgd.mkdir();
		}
		return isendDir.getAbsolutePath();
	}

	/**
	 * 创建回收目录
	 * 
	 * @param recvDir
	 *            回收目录
	 * @return String 回收目录绝对路径
	 */
	private String createRecvDir(String recvDir) {
		File irecvDir = new File(recvDir);
		if (!irecvDir.exists()) {
			irecvDir.mkdir();
		}
		File isbgd = new File(recvDir + "/recvBgd");
		if (!isbgd.exists()) {
			isbgd.mkdir();
		}
		return irecvDir.getAbsolutePath();

	}

	/**
	 * 创建回收目录
	 * 
	 * @param recvBillDir
	 *            回收目录
	 * @return String 回收目录绝对路径
	 */
	private String createBillRecvDir(String recvBillDir) {
		File irecvBillDir = new File(recvBillDir);
		if (!irecvBillDir.exists()) {
			irecvBillDir.mkdir();
		}
		return irecvBillDir.getAbsolutePath();

	}

	/**
	 * 创建处理完目录
	 * 
	 * @param finallyDir
	 *            处理完目录
	 * @return String 处理完目录绝对路径
	 */
	private String createFinallyDir(String finallyDir) {
		File ifinallyDir = new File(finallyDir);
		if (!ifinallyDir.exists()) {
			ifinallyDir.mkdir();
		}
		File isbgd = new File(finallyDir + "/finallyBgd");
		if (!isbgd.exists()) {
			isbgd.mkdir();
		}
		return ifinallyDir.getAbsolutePath();
	}

	/**
	 * 创建日志目录
	 * 
	 * @param logDir
	 *            日志目录
	 * @return String 日志目录绝对路径
	 */
	private String createLogDir(String logDir) {
		File ilogDir = new File(logDir);
		if (!ilogDir.exists()) {
			ilogDir.mkdir();
		}
		File isbgd = new File(logDir + "/logBgd");
		if (!isbgd.exists()) {
			isbgd.mkdir();
		}
		return ilogDir.getAbsolutePath();
	}

	/**
	 * 删除系统参数设置
	 * 
	 * @param parameterSet
	 *            系统参数设置
	 */
	public void deleteParameterSet(ParameterSet parameterSet) {
		createDirDao.deleteParameterSet(parameterSet);
	}

	/**
	 * 保存值到系统参数设置表
	 * 
	 * @param paraType
	 *            选项
	 * @param dirName
	 *            type对应的值
	 */
	private void savePara(int paraType, String dirName) {
		ParameterSet para = new ParameterSet();
		para.setType(paraType);
		para.setNameValues(dirName);
		para.setCompany(CommonUtils.getCompany());
		createDirDao.saveValues(para);
	}

	private void editPara(ParameterSet para, String dirName) {
		para.setNameValues(dirName);
		createDirDao.saveValues(para);
	}

	private void initProjectDept() {
		/*
		 * ProjectDept obj = createDirDao.getProjectDept(); if (obj == null){
		 * ProjectDept pro = new ProjectDept(); pro.setCode("");
		 * pro.setName("默认事业部"); createDirDao.saveDept(pro); }
		 */
	}

	/**
	 * 创建目录
	 * 
	 * @param isBegin
	 *            判断
	 * @param sendDir
	 *            发送目录
	 * @param recvDir
	 *            回收目录
	 * @param finallyDir
	 *            处理完目录
	 * @param logDir
	 *            日志目录
	 */
	public void createDir(boolean isBegin, String sendDir, String recvDir,
			String finallyDir, String logDir, String recvBillDir) {

		initProjectDept();

		List list = null;

		list = createDirDao.findnameValues(ParameterType.sendDir);

		if (list.isEmpty()) {

			savePara(ParameterType.sendDir, createSendDir(sendDir));

		} else if (!list.isEmpty() && isBegin == false) {

			ParameterSet para = (ParameterSet) list.get(0);

			if (!para.getNameValues().equals(sendDir)) {

				para.setNameValues(sendDir);

				createDirDao.saveValues(para);

				createSendDir(sendDir);

			} else if (para.getNameValues().equals(sendDir)) {

				createSendDir(sendDir); // 数据库存在，实际目录被删除

			}
		} else if (!list.isEmpty() && isBegin == true) {
			createSendDir(sendDir); // 数据库存在，实际目录被删除
		}

		list = createDirDao.findnameValues(ParameterType.recvDir);
		if (list.isEmpty()) {
			savePara(ParameterType.recvDir, createRecvDir(recvDir));
		} else if (!list.isEmpty() && isBegin == false) {
			ParameterSet para = (ParameterSet) list.get(0);
			if (!para.getNameValues().equals(recvDir)) {
				para.setNameValues(recvDir);
				createDirDao.saveValues(para);
				createRecvDir(recvDir);
			} else if (para.getNameValues().equals(recvDir)) {
				createRecvDir(recvDir); // 数据库存在，实际目录被删除
			}
		} else if (!list.isEmpty() && isBegin == true) {
			createRecvDir(recvDir); // 数据库存在，实际目录被删除
		}

		if (recvBillDir != null && !"".equals(recvBillDir)) {
			list = createDirDao.findnameValues(ParameterType.recvBillDir);
			if (list.isEmpty()) {
				savePara(ParameterType.recvBillDir,
						createBillRecvDir(recvBillDir));
			} else if (!list.isEmpty() && isBegin == false) {
				ParameterSet para = (ParameterSet) list.get(0);
				if (!para.getNameValues().equals(recvBillDir)) {
					para.setNameValues(recvBillDir);
					createDirDao.saveValues(para);
					createBillRecvDir(recvBillDir);
				} else if (para.getNameValues().equals(recvBillDir)) {
					createBillRecvDir(recvBillDir); // 数据库存在，实际目录被删除
				}
			} else if (!list.isEmpty() && isBegin == true) {
				createBillRecvDir(recvBillDir); // 数据库存在，实际目录被删除
			}
		}

		list = createDirDao.findnameValues(ParameterType.finallyDir);
		if (list.isEmpty()) {
			savePara(ParameterType.finallyDir, createFinallyDir(finallyDir));
		} else if (!list.isEmpty() && isBegin == false) {
			ParameterSet para = (ParameterSet) list.get(0);
			if (!para.getNameValues().equals(finallyDir)) {
				para.setNameValues(finallyDir);
				createDirDao.saveValues(para);
				createFinallyDir(finallyDir);
			} else if (para.getNameValues().equals(finallyDir)) {
				createFinallyDir(finallyDir); // 数据库存在，实际目录被删除
			}
		} else if (!list.isEmpty() && isBegin == true) {
			createFinallyDir(finallyDir); // 数据库存在，实际目录被删除
		}

		list = createDirDao.findnameValues(ParameterType.logDir);
		if (list.isEmpty()) {
			savePara(ParameterType.logDir, createLogDir(logDir));
		} else if (!list.isEmpty() && isBegin == false) {
			ParameterSet para = (ParameterSet) list.get(0);
			if (!para.getNameValues().equals(logDir)) {
				para.setNameValues(logDir);
				createDirDao.saveValues(para);
				createLogDir(logDir);
			} else if (para.getNameValues().equals(logDir)) {
				createLogDir(logDir); // 数据库存在，实际目录被删除
			}
		} else if (!list.isEmpty() && isBegin == true) {
			createLogDir(logDir); // 数据库存在，实际目录被删除
		}

	}

	/**
	 * 保存报文路径到参数表
	 * 
	 * @param sendDir
	 *            发送目录
	 * @param recvDir
	 *            回收目录
	 * @param finallyDir
	 *            处理完目录
	 * @param logDir
	 *            日志目录
	 */
	public void saveDir(String sendDir, String recvDir, String finallyDir,
			String logDir, String recvBillDir) {
		CommonUtils.setSendDir(sendDir);
		CommonUtils.setRecvDir(recvDir);
		CommonUtils.setRecvBillDir(recvBillDir);
		CommonUtils.setFinallyDir(finallyDir);
		CommonUtils.setLogDir(logDir);
	}

	/**
	 * 保存其他参数设置资料
	 * 
	 * @return CompanyOther 其他参数设置资料
	 */
	public CompanyOther saveCommonUtilsOther() {
		List list = companyDao.findCompanyOther();
		CompanyOther other = null;
		if (list != null && list.size() > 0) {
			other = (CompanyOther) list.get(0);
		}
		CommonUtils.setOther(other);
		return other;
	}

	/**
	 * 保存年度到参数表
	 * 
	 * @param year
	 *            年份
	 */
	public void saveYear(String year) {
		CommonUtils.setYear(year);
	}

	/**
	 * 保存输出报文是否繁转简
	 * 
	 * @param values
	 */
	public void saveisBigToGbk(String values) {
		CommonUtils.setIsBigToGBK(values);
	}

	/**
	 * 保存BCuS参数
	 * 
	 * @param parameter
	 */
	public void saveBcusValues(int type, String sValue) {
		BcusParameter obj = null;
		List list = this.createDirDao.getBparaList(type);
		if (list != null && list.size() > 0) {
			obj = (BcusParameter) list.get(0);
		} else {
			obj = new BcusParameter();
			obj.setType(type);
			obj.setCompany(CommonUtils.getCompany());
		}
		obj.setStrValue(sValue);
		this.createDirDao.saveBcusParameterSet(obj);
	}

	/**
	 * 获取createDirDao
	 * 
	 * @return String Returns the createDirDao.
	 */
	public CreateDirDao getCreateDirDao() {
		return createDirDao;
	}

	/**
	 * 设置createDirDao
	 * 
	 * @param createDirDao
	 *            The createDirDao to set.
	 */
	public void setCreateDirDao(CreateDirDao createDirDao) {
		this.createDirDao = createDirDao;
	}

	/**
	 * 保存文本路径
	 * 
	 * @param input
	 *            导入路径
	 * @param inputFinally
	 *            导入成功路径
	 * @param inputFail
	 *            导入失败路径
	 * @param inputLog
	 *            导入日志存放路径
	 */
	public void saveTxtDir(String input, String inputFinally, String inputFail,
			String inputLog) {
		List list = null;
		list = createDirDao.findnameValues(ParameterType.txtInput);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			editPara(para, input);
		} else {
			savePara(ParameterType.txtInput, input);
		}
		list = createDirDao.findnameValues(ParameterType.txtInputFinally);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			editPara(para, inputFinally);
		} else {
			savePara(ParameterType.txtInputFinally, inputFinally);
		}
		list = createDirDao.findnameValues(ParameterType.txtInputFail);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			editPara(para, inputFail);
		} else {
			savePara(ParameterType.txtInputFail, inputFail);
		}
		list = createDirDao.findnameValues(ParameterType.txtlog);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			editPara(para, inputLog);
		} else {
			savePara(ParameterType.txtlog, inputLog);
		}
		saveTxtDirToCommon(input, inputFinally, inputFail, inputLog);
	}

	/**
	 * 保存文本路径到变量
	 * 
	 * @param input
	 *            导入路径
	 * @param inputFinally
	 *            导入成功路径
	 * @param inputFail
	 *            导入失败路径
	 * @param inputLog
	 *            导入日志存放路径
	 */
	public void saveTxtDirToCommon(String input, String inputFinally,
			String inputFail, String inputLog) {
		CommonUtils.setTxtInput(input);
		CommonUtils.setTxtInputFinally(inputFinally);
		CommonUtils.setTxtInputFail(inputFail);
		CommonUtils.setTxtInputLog(inputLog);
	}

	/**
	 * 读取文本路径到变量
	 */
	public void sysBeginSaveToCommon() {
		List list = null;
		list = createDirDao.findnameValues(ParameterType.txtInput);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			CommonUtils.setTxtInput(para.getNameValues());
		} else {
			CommonUtils.setTxtInput("");
		}
		list = createDirDao.findnameValues(ParameterType.txtInputFinally);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			CommonUtils.setTxtInputFinally(para.getNameValues());
		} else {
			CommonUtils.setTxtInputFinally("");
		}
		list = createDirDao.findnameValues(ParameterType.txtInputFail);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			CommonUtils.setTxtInputFail(para.getNameValues());
		} else {
			CommonUtils.setTxtInputFail("");
		}
		list = createDirDao.findnameValues(ParameterType.txtlog);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			CommonUtils.setTxtInputLog(para.getNameValues());
		} else {
			CommonUtils.setTxtInputLog("");
		}

	}

	/**
	 * 获取companyDao
	 * 
	 * @return Returns the companyDao.
	 */
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	/**
	 * 设置companyDao
	 * 
	 * @param companyDao
	 *            The companyDao to set.
	 */
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public String commonCheckNull(Object obj) {
		if (obj == null) {
			return "被保存对象不可为空！";
		}
		String className = obj.getClass().getName();
		List ls = this.companyDao.findClassByName(className);
		for (int i = 0; i < ls.size(); i++) {
			CommonCheckNull check = (CommonCheckNull) ls.get(i);
			try {
				Object objvalue = BeanUtils.getProperty(obj,
						check.getFieldName());
				if (obj instanceof BaseCustomsDeclaration) {
					BaseCustomsDeclaration bgd = (BaseCustomsDeclaration) obj;
					if (bgd.getImpExpFlag() == ImpExpFlag.IMPORT
							&& check.getFieldName().equals("invoiceCode")) {
						continue;
					}
				}
				String fieldType = check.getFieldType();
				if (fieldType.equals("String")
						&& (objvalue == null || "".equals(objvalue))) {
					return check.getFieldMemo() + "不可为空！";
				}
				if (fieldType.equals("Double")
						&& (objvalue == null || Double.valueOf(0.0).equals(
								Double.valueOf(objvalue.toString())))) {
					return check.getFieldMemo() + "不可为空！";
				}
				if (fieldType.equals("Integer")
						&& (objvalue == null || Integer.valueOf(0).equals(
								Integer.valueOf(objvalue.toString())))) {
					return check.getFieldMemo() + "不可为空！";
				}
				if ((fieldType.equals("Boolean") || fieldType.equals("Date"))
						&& objvalue == null) {
					return check.getFieldMemo() + "不可为空！";
				}
			} catch (Exception e) {
				return ("基本参数设置-自定义保存选项设置有错误！\n字段名称：" + check.getFieldName());
			}
		}
		return null;
	}

}