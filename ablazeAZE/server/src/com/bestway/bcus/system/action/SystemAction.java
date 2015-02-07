/*
 * Created on 2004-8-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.CommonCheckNull;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.JarFile;
import com.bestway.bcus.system.entity.JarFileObject;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public interface SystemAction {

	/**
	 * 查找所有公司资料
	 * 
	 * @return List 是Company型，公司资料
	 */
	List findCompanies();
	
	/**
	 * 查找启用的所有公司资料
	 */
	List findEnableCompanies();

	/**
	 * 查找当前公司的资料
	 * 
	 * @return List 是Company型，公司资料
	 */
	public List findCompaniesCurr();

	/**
	 * 保存公司资料
	 * 
	 * @param request
	 *            请求控制
	 * @param company
	 *            公司资料
	 * @return Company 要保存的公司资料
	 */
	Company saveCompany(Request request, Company company);
	
	/**
	 * 保存公司资料
	 * 
	 * @param request
	 *            请求控制
	 * @param company
	 *            公司资料
	 * @return Company 要保存的公司资料
	 */
	Company saveCompany(Company company);

	/**
	 * 删除公司资料
	 * 
	 * @param request
	 *            请求控制
	 * @param company
	 *            是Company型，公司资料
	 */
	void deleteCompany(Request request, Company company);

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	List findnameValues(Request request, int type);

	/**
	 * 根据选项查找Bcus参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	List findBcusnameValues(Request request, int type);

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	BcsParameterSet findBcsParameterSet(Request request);

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	void saveValues();
	void saveValues(Request request, ParameterSet parameterSet);
	void saveValues1(Request request, ParameterSet parameterSet);
	/**
	 * 保存BCuS参数
	 * 
	 * @param parameter
	 */

	void saveBcusValues(Request request, int type, String sValue);

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	void saveBcsParameterSet(Request request, BcsParameterSet parameter);
    /**
     * 检查公共查询权限
     * 
     * */
	
	public void checkQueryAuthority(Request request);
	/**
	 * 
	 * @see com.bestway.bcus.system.action.SystemAction#generateAutoNo(java.lang.Class)
	 */
	String generateAutoNo(Class entityClass);

	/**
	 * 保存报文路径到参数表
	 * 
	 * @param request
	 *            请求控制
	 * @param sendDir
	 *            发送目录
	 * @param recvDir
	 *            回收目录
	 * @param finallyDir
	 *            处理完目录
	 * @param logDir
	 *            日志目录
	 */
	void saveDir(Request request, String sendDir, String recvDir,
			String finallyDir, String logDir, String recvBillDir);

	/**
	 * 创建目录
	 * 
	 * @param request
	 *            请求控制
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
	void createDir(Request request, boolean isBegin, String sendDir,
			String recvDir, String finallyDir, String logDir, String recvBillDir);

	/**
	 * 删除系统参数设置
	 * 
	 * @param request
	 *            请求控制
	 * @param parameterSet
	 *            系统参数设置
	 */
	void deleteParameterSet(Request request, ParameterSet parameterSet);

	/**
	 * 保存年度到参数表
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年份
	 */
	void saveYear(Request request, String year);

	/**
	 * 读取文本路径到变量
	 * 
	 * @param request
	 *            请求控制
	 */
	void sysBeginSaveToCommon(Request request);

	/**
	 * 保存文本路径
	 * 
	 * @param request
	 *            请求控制
	 * @param input
	 *            导入路径
	 * @param inputFinally
	 *            导入成功路径
	 * @param inputFail
	 *            导入失败路径
	 * @param inputLog
	 *            导入日志存放路径
	 */
	void saveTxtDir(Request request, String input, String inputFinally,
			String inputFail, String inputLog);

	/**
	 * 根据经营单位名称查找公司资料
	 * 
	 * @param buName
	 *            经营单位名称
	 * @return Company 公司资料
	 */
	Company findCompaniesByName(String buName);

	/**
	 * 浏览公司权限控制
	 * 
	 * @param request
	 *            请求控制
	 */
	void checkFindCompanyAuthority(Request request);

	/**
	 * 浏览系统参数设置权限控制
	 * 
	 * @param request
	 *            请求控制
	 */
	void checkSystemParameterAuthority(Request request);

	/**
	 * 根据经营单位海关编码查找公司资料
	 * 
	 * @param buCode
	 *            经营单位海关编码
	 * @return Company 公司资料
	 */
	Company findCompaniesByCode(String buCode);

	/**
	 * 保存其他参数设置
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	List findCompanyOther(Request request);
	
	
	/**
	 * 保存其他参数设置
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	List findCompanyOtherBcus(Request request);

	/**
	 * 保存其他参数设置
	 * 
	 * @param obj
	 *            是CompanyOther型，其他参数设置资料
	 */
	CompanyOther saveCompanyOther(Request request, CompanyOther obj);

	/**
	 * 保存其他参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @return CompanyOther 其他参数设置资料
	 */
	CompanyOther saveCommonUtilsOther(Request request);

	/**
	 * 保存用户资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            是AclUser型，用户资料
	 */
	void saveAclUser(Request request, AclUser obj);

	/**
	 * 删除操作日志
	 * 
	 * @param request
	 *            请求控制
	 * @param logs
	 *            多个操作日志
	 */
	void deleteOperationLogs(Request request, List logs);

	/**
	 * 删除操作日志
	 * 
	 * @param request
	 *            请求控制
	 * @param user
	 *            用户
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            计算日期
	 */
	void deleteOperationLogs(Request request, AclUser user, Date beginDate,
			Date endDate);

	/**
	 * 查询操作日志
	 * 
	 * @param request
	 *            请求控制
	 * @param user
	 *            用户
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            计算日期
	 * @return List 是OperationLogs型，查找日志
	 */
	List findOperationLogs(Request request, AclUser user, Date beginDate,
			Date endDate);

	/**
	 * 自动升级
	 * 
	 */
	void autoUpgradeJBCUS(Request request);

	/**
	 * 升级成功
	 * 
	 * @return boolean true为成功
	 */
	boolean getAutoUpgradeState(Request request);

	/**
	 * 
	 * 升级成功，重新tomcat
	 * 
	 */
	void restart(Request request);

	/**
	 * 保存输出报文是否繁转简
	 * 
	 * @param request
	 *            请求控制
	 * @param values
	 */
	public void saveisBigToGbk(Request request, String values);

	/**
	 * 保存列信息并存入 自定义 cache map 中
	 * 
	 * @param dataSource
	 * @return
	 */
	List<RenderDataColumn> saveRenderDataColumn(Request request,
			List<RenderDataColumn> dataSource);

	/**
	 * 获得对应的值
	 * 
	 * @param className
	 * @param companyId
	 * @param key
	 * @return
	 */
	List<RenderDataColumn> findRenderDataColumn(Request request,
			String className, String companyId, String key);

	/**
	 * 保存数据到文件
	 * 
	 * @param fileNameNoPrefixSuffix
	 *            没有前缀的文件名
	 * @param keyString
	 * @param list
	 */
	void saveJarFile(Request request, String fileNameNoPrefixSuffix,
			String keyString, boolean isKeyString, List<JarFileObject> list);

	/**
	 * 打开文件
	 * 
	 * @param fileName
	 * @param keyString
	 * @return
	 */
	List<JarFileObject> openJarFile(Request request, String fileNameNoPrefix,
			String hashCode);

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	List<JarFile> getJarFileName(Request request, String keyString);

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	List<JarFile> getJarFileName(Request request);

	boolean deleteFile(Request request, String fileNameNoPrefix, String hashCode);

	/**
	 * 查找所有存在的 Render Data Column
	 * 
	 * @return
	 */
	ApplyCustomBillParameter findApplyCustomBillParameter(Request request);

	/**
	 * 保存呈现数据列表
	 * 
	 * @param list
	 */
	ApplyCustomBillParameter saveApplyCustomBillParameter(Request request,
			ApplyCustomBillParameter a);

	public List findCustomsDeclarationSet(Request request);

	public CustomsDeclarationSet saveCustomsSet(Request request,
			CustomsDeclarationSet obj);

	public String commonCheckNull(Request request, Object obj);

	public void deleteCustomsSet(Request request, List list);

	public CustomsDeclarationSet findCustomsSet(Request request, Integer type);

	/** 下载 java chinese ime jar */
	byte[] downloadJAVAIME(Request request);

	public String getServerIp(Request request);

	public String getServerPort(Request request);

	public CommonCheckNull saveCommonCheckNull(Request request,
			CommonCheckNull obj);

	public List findCommonCheckNull(Request request);

	public void deleteLs(Request request, List ls);

	List findInputTableColumnSet(Request request, String tableFlag);

	void saveInputTableColumnSet(Request request, List list, String tableFlag);
	
	void saveReportControl(Request request,ReportControl reportControl);

	List findReportControl(Request request);

	void checkLogsAuthority(Request request);
	
	/**
	 * 获得tomcat日志信息
	 * @return
	 */
	String getLogInfo();
	/**
	 * 测试HTTP报文传输通道是否能连接
	 * @param httpMsgTransType
	 * @return
	 */
	boolean httpTestConnect(Request request,String httpMsgTransType);
}
