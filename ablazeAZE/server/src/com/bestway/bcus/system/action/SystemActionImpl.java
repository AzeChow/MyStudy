/***
 *  浏览公司资料 1
 *  保存公司资料 2
 *  删除公司资料 3
 */

package com.bestway.bcus.system.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.dao.SysCodeDao;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.CommonCheckNull;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.bcus.system.entity.JarFile;
import com.bestway.bcus.system.entity.JarFileObject;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.bcus.system.logic.CreateDirLogic;
import com.bestway.bcus.system.logic.GenerateCodeLogic;
import com.bestway.bcus.system.logic.JAVAIMELogic;
import com.bestway.bcus.system.logic.JarFileStoreLogic;
import com.bestway.bcus.system.logic.JbcusHelpLogic;
import com.bestway.bcus.system.logic.JbucsUpgradeLogic;
import com.bestway.bcus.system.logic.RenderDataColumnLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.MessageHttpUtil;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.logic.AuthorityLogic;

@AuthorityClassAnnotation(caption = "系统管理", index = 0)
public class SystemActionImpl extends BaseActionImpl implements SystemAction {
	private CompanyDao companyDao = null;

	private SysCodeDao sysCodeDao = null;

	private GenerateCodeLogic generateCodeLogic = null;

	private CreateDirLogic createDirLogic = null;

	private AuthorityLogic authorityLogic = null;

	private RenderDataColumnLogic renderDataColumnLogic = null;

	private JarFileStoreLogic jarFileStoreLogic = null;

	private JAVAIMELogic javaIMELogic = null;

	private JbcusHelpLogic jbcusHelpLogic = null;

	public RenderDataColumnLogic getRenderDataColumnLogic() {
		return renderDataColumnLogic;
	}

	public JAVAIMELogic getJavaIMELogic() {
		return javaIMELogic;
	}

	public void setJavaIMELogic(JAVAIMELogic javaIMELogic) {
		this.javaIMELogic = javaIMELogic;
	}

	public void setRenderDataColumnLogic(
			RenderDataColumnLogic renderDataColumnLogic) {
		this.renderDataColumnLogic = renderDataColumnLogic;
	}

	public JarFileStoreLogic getJarFileStoreLogic() {
		return jarFileStoreLogic;
	}

	public void setJarFileStoreLogic(JarFileStoreLogic jarFileStoreLogic) {
		this.jarFileStoreLogic = jarFileStoreLogic;
	}

	public JbcusHelpLogic getJbcusHelpLogic() {
		return jbcusHelpLogic;
	}

	public void setJbcusHelpLogic(JbcusHelpLogic jbcusHelpLogic) {
		this.jbcusHelpLogic = jbcusHelpLogic;
	}

	/**
	 * 设置companyDao
	 * 
	 * @param companyDao
	 */
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	/**
	 * 获取companyDao
	 * 
	 * @return companyDao
	 */
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	/**
	 * 保存输出报文是否繁转简
	 * 
	 * @param request
	 *            请求控制
	 * @param values
	 */
	public void saveisBigToGbk(Request request, String values) {
		this.createDirLogic.saveisBigToGbk(values);
	}

	/**
	 * 获取authorityLogic
	 * 
	 * @return authorityLogic
	 */
	public AuthorityLogic getAuthorityLogic() {
		return authorityLogic;
	}

	/**
	 * 设置authorityLogic
	 * 
	 * @param authorityLogic
	 */
	public void setAuthorityLogic(AuthorityLogic authorityLogic) {
		this.authorityLogic = authorityLogic;
	}

	/**
	 * 查找所有公司资料
	 * 
	 * @return List 是Company型，公司资料
	 * @throws HibernateException
	 *             抛出的异常
	 */
	public List findCompanies() {
		try {
			return companyDao.findCompanies();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 查找启用的所有公司资料
	 */
	public List findEnableCompanies() {
		try {
			return companyDao.findEnableCompanies();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 查找当前公司的资料
	 * 
	 * @return List 是Company型，公司资料
	 * @throws HibernateException
	 *             抛出的异常
	 */
	public List findCompaniesCurr() {
		try {
			return companyDao.findCompaniesCurr(); // 显示
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 保存公司资料
	 * 
	 * @param company
	 *            公司资料
	 * @return Company 要保存的公司资料
	 */
	@AuthorityFunctionAnnotation(caption = "保存公司资料", index = 7)
	public Company saveCompany(Request request, Company company) {
		return (Company) companyDao.saveCompany(company);
	} // 保存

	/**
	 * 删除公司资料
	 * 
	 * @param company
	 *            是Company型，公司资料
	 */
	@AuthorityFunctionAnnotation(caption = "删除公司资料", index = 8)
	public void deleteCompany(Request request, Company company) {
		companyDao.deleteCompany(company); // 删除
	}

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(Request request, int type) {
		return createDirLogic.getCreateDirDao().findnameValues(type);
	}

	/**
	 * 根据选项查找Bcus参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findBcusnameValues(Request request, int type) {
		return createDirLogic.getCreateDirDao().findBcusnameValues(type);
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet(Request request) {
		return createDirLogic.getCreateDirDao().findBcsParameterSet();
	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	@AuthorityFunctionAnnotation(caption = "保存系统参数", index = 2)
	public void saveValues() {
	}

	public void saveValues(Request request, ParameterSet parameterSet) {
		createDirLogic.getCreateDirDao().saveValues(parameterSet);
	}

	public void saveValues1(Request request, ParameterSet parameterSet) {
		createDirLogic.getCreateDirDao().saveValues(parameterSet);
	}

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	public void saveBcsParameterSet(Request request, BcsParameterSet parameter) {
		createDirLogic.getCreateDirDao().saveBcsParameterSet(parameter);
	}

	/**
	 * 保存BCuS参数
	 * 
	 * @param parameter
	 */
	public void saveBcusValues(Request request, int type, String sValue) {
		this.createDirLogic.saveBcusValues(type, sValue);
	}

	/**
	 * 
	 * @see com.bestway.bcus.system.action.SystemAction#generateAutoNo(java.lang.Class)
	 */
	public String generateAutoNo(Class entityClass) {
		return generateCodeLogic.generateAutoNo(entityClass);
	}

	/**
	 * 删除系统参数设置
	 * 
	 * @param parameterSet
	 *            系统参数设置
	 */
	@AuthorityFunctionAnnotation(caption = "删除系统参数", index = 3)
	public void deleteParameterSet(Request request, ParameterSet parameterSet) {
		createDirLogic.deleteParameterSet(parameterSet);
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
	public void createDir(Request request, boolean isBegin, String sendDir,
			String recvDir, String finallyDir, String logDir, String recvBillDir) {
		createDirLogic.createDir(isBegin, sendDir, recvDir, finallyDir, logDir,
				recvBillDir);
	}

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
	public void saveDir(Request request, String sendDir, String recvDir,
			String finallyDir, String logDir, String recvBillDir) {
		createDirLogic.saveDir(sendDir, recvDir, finallyDir, logDir,
				recvBillDir);
	}

	/**
	 * 保存年度到参数表
	 * 
	 * @param request
	 *            请求控制
	 * @param year
	 *            年份
	 */
	public void saveYear(Request request, String year) {
		createDirLogic.saveYear(year);
	}

	/**
	 * 
	 */
	public SystemActionImpl() {
		super.setModuleName("System");
	}

	/**
	 * 获取generateCodeLogic
	 * 
	 * @return Returns the generateCodeLogic.
	 */
	public GenerateCodeLogic getGenerateCodeLogic() {
		return generateCodeLogic;
	}

	/**
	 * 设置generateCodeLogic
	 * 
	 * @param generateCodeLogic
	 *            The generateCodeLogic to set.
	 */
	public void setGenerateCodeLogic(GenerateCodeLogic generateCodeLogic) {
		this.generateCodeLogic = generateCodeLogic;
	}

	/**
	 * 获取createDirLogic
	 * 
	 * @return Returns the createDirLogic.
	 */
	public CreateDirLogic getCreateDirLogic() {
		return createDirLogic;
	}

	/**
	 * 设置createDirLogic
	 * 
	 * @param createDirLogic
	 *            The createDirLogic to set.
	 */
	public void setCreateDirLogic(CreateDirLogic createDirLogic) {
		this.createDirLogic = createDirLogic;
	}

	/**
	 * 获取sysCodeDao
	 * 
	 * @return Returns the sysCodeDao.
	 */
	public SysCodeDao getSysCodeDao() {
		return sysCodeDao;
	}

	/**
	 * 设置
	 * 
	 * @param sysCodeDao
	 *            The sysCodeDao to set.
	 */
	public void setSysCodeDao(SysCodeDao sysCodeDao) {
		this.sysCodeDao = sysCodeDao;
	}

	/**
	 * 读取文本路径到变量
	 * 
	 * @param request
	 *            请求控制
	 */
	public void sysBeginSaveToCommon(Request request) {
		createDirLogic.sysBeginSaveToCommon();
	}

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
	public void saveTxtDir(Request request, String input, String inputFinally,
			String inputFail, String inputLog) {
		createDirLogic.saveTxtDir(input, inputFinally, inputFail, inputLog);
	}

	/**
	 * 根据经营单位名称查找公司资料
	 * 
	 * @param buName
	 *            经营单位名称
	 * @return Company 公司资料
	 */
	public Company findCompaniesByName(String buName) {
		return this.companyDao.findCompaniesByName(buName);
	}

	/**
	 * 根据经营单位海关编码查找公司资料
	 * 
	 * @param buCode
	 *            经营单位海关编码
	 * @return Company 公司资料
	 */
	public Company findCompaniesByCode(String buCode) {
		return this.companyDao.findCompaniesByCode(buCode);
	}

	/**
	 * 浏览公司权限控制
	 * 
	 * @param request
	 *            请求控制
	 * @see com.bestway.bcus.system.action.SystemAction#checkFindCompanyAuthority(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "浏览公司资料", index = 6)
	public void checkFindCompanyAuthority(Request request) {

	}

	/**
	 * 浏览系统参数设置权限控制
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "浏览系统参数", index = 1)
	public void checkSystemParameterAuthority(Request request) {

	}

	/**
	 * 查找其他参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	@AuthorityFunctionAnnotation(caption = "浏览系统参数", index = 1)
	public List findCompanyOther(Request request) {
		return this.companyDao.findCompanyOther();
	}

	public List findCompanyOtherBcus(Request request) {
		return this.companyDao.findCompanyOther();
	}

	/**
	 * 保存其他参数设置
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            是CompanyOther型，其他参数设置资料
	 */
	public CompanyOther saveCompanyOther(Request request, CompanyOther obj) {
		this.companyDao.saveCompanyOther(obj);
		return obj;
	}

	/**
	 * 保存其他参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @return CompanyOther 其他参数设置资料
	 */
	public CompanyOther saveCommonUtilsOther(Request request) {
		return createDirLogic.saveCommonUtilsOther();
	}

	/**
	 * 保存用户资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            是AclUser型，用户资料
	 */
	public void saveAclUser(Request request, AclUser obj) {
		this.companyDao.saveAclUser(obj);
	}

	/**
	 * 删除操作日志
	 * 
	 * @param request
	 *            请求控制
	 * @param logs
	 *            多个操作日志
	 */
	@AuthorityFunctionAnnotation(caption = "删除操作日志", index = 5)
	public void deleteOperationLogs(Request request, List logs) {
		this.authorityLogic.deleteOperationLogs(logs);
	}

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
	@AuthorityFunctionAnnotation(caption = "删除操作日志", index = 5)
	public void deleteOperationLogs(Request request, AclUser user,
			Date beginDate, Date endDate) {
		this.authorityLogic.deleteOperationLogs(user, beginDate, endDate);
	}

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

	public List findOperationLogs(Request request, AclUser user,
			Date beginDate, Date endDate) {
		return authorityLogic.findOperationLogs(user, beginDate, endDate);
	}

	@AuthorityFunctionAnnotation(caption = "公共查询", index = 14)
	public void checkQueryAuthority(Request request) {

	}

	/**
	 * 自动升级
	 */
	@AuthorityFunctionAnnotation(caption = "自动升级", index = 15)
	public void autoUpgradeJBCUS(Request request) {
		JbucsUpgradeLogic.autoUpgradeJBCUS();
	}

	/**
	 * 升级成功
	 * 
	 * @return boolean true为成功
	 */
	public boolean getAutoUpgradeState(Request request) {
		return JbucsUpgradeLogic.getAutoUpgradeState();
	}

	/**
	 * 升级成功，重新tomcat
	 */
	@AuthorityFunctionAnnotation(caption = "重启服务器", index = 16)
	public void restart(Request request) {
		JbucsUpgradeLogic.restart();
	}

	/**
	 * 保存列信息并存入 自定义 cache map 中
	 * 
	 * @param dataSource
	 * @return
	 */
	public List<RenderDataColumn> saveRenderDataColumn(Request request,
			List<RenderDataColumn> dataSource) {
		return this.renderDataColumnLogic.saveRenderDataColumn(dataSource);
	}

	/**
	 * 获得对应的值
	 * 
	 * @param className
	 * @param companyId
	 * @param key
	 * @return
	 */
	public List<RenderDataColumn> findRenderDataColumn(Request request,
			String className, String companyId, String key) {
		return this.renderDataColumnLogic.findRenderDataColumn(className,
				companyId, key);
	}

	/**
	 * 保存数据到文件
	 * 
	 * @param fileNameNoPrefixSuffix
	 *            没有前缀的文件名
	 * @param keyString
	 * @param list
	 */
	public void saveJarFile(Request request, String fileNameNoPrefixSuffix,
			String keyString, boolean isKeyString, List<JarFileObject> list) {
		this.jarFileStoreLogic.saveJarFile(fileNameNoPrefixSuffix, keyString,
				isKeyString, list);
	}

	/**
	 * 打开文件
	 * 
	 * @param fileName
	 * @param keyString
	 * @return
	 */
	public List<JarFileObject> openJarFile(Request request,
			String fileNameNoPrefix, String hashCode) {
		return this.jarFileStoreLogic.openJarFile(fileNameNoPrefix, hashCode);
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarFile> getJarFileName(Request request, String keyString) {
		return this.jarFileStoreLogic.getJarFileName(keyString);
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarFile> getJarFileName(Request request) {
		return this.jarFileStoreLogic.getJarFileName();
	}

	public boolean deleteFile(Request request, String fileNameNoPrefix,
			String hashCode) {
		return this.jarFileStoreLogic.deleteFile(fileNameNoPrefix, hashCode);
	}

	public List findCustomsDeclarationSet(Request request) {
		return this.companyDao.findCustomsDeclarationSet();
	}

	@AuthorityFunctionAnnotation(caption = "保存系统参数", index = 2)
	public CustomsDeclarationSet saveCustomsSet(Request request,
			CustomsDeclarationSet obj) {
		this.companyDao.saveCustomsSet(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "删除系统参数", index = 3)
	public void deleteCustomsSet(Request request, List list) {
		this.companyDao.deleteCustomsSet(list);
	}

	public CustomsDeclarationSet findCustomsSet(Request request, Integer type) {
		return companyDao.findCustomsSet(type);
	}

	/**
	 * 查找所有存在的 Render Data Column
	 * 
	 * @return
	 */
	public ApplyCustomBillParameter findApplyCustomBillParameter(Request request) {
		return this.sysCodeDao.findApplyCustomBillParameter();
	}

	/**
	 * 保存呈现数据列表
	 * 
	 * @param list
	 */
	public ApplyCustomBillParameter saveApplyCustomBillParameter(
			Request request, ApplyCustomBillParameter a) {
		this.sysCodeDao.saveApplyCustomBillParameter(a);
		return a;
	}

	public CommonCheckNull saveCommonCheckNull(Request request,
			CommonCheckNull obj) {
		this.companyDao.saveCommonCheckNull(obj);
		return obj;
	}

	public List findCommonCheckNull(Request request) {
		return this.companyDao.findCommonCheckNull();
	}

	public void deleteLs(Request request, List ls) {
		this.companyDao.deleteLs(ls);
	}

	public String commonCheckNull(Request request, Object obj) {
		return this.createDirLogic.commonCheckNull(obj);
	}

	/**
	 * 下载 java chinese ime jar
	 * 
	 */
	public byte[] downloadJAVAIME(Request request) {
		return this.javaIMELogic.downloadJAVAIME();
	}

	public String getServerIp(Request request) {
		//
		return this.jbcusHelpLogic.getServerIP();
	}

	public String getServerPort(Request request) {
		//
		return this.jbcusHelpLogic.getServerPort();
	}

	public List findInputTableColumnSet(Request request, String tableFlag) {
		return this.companyDao.findInputTableColumnSet(tableFlag);
	}

	public void saveInputTableColumnSet(Request request, List list,
			String tableFlag) {
		this.companyDao.deleteAll(this.companyDao
				.findInputTableColumnSet(tableFlag));
		for (int i = 0; i < list.size(); i++) {
			InputTableColumnSet columnSet = (InputTableColumnSet) list.get(i);
			columnSet.setId(null);
			columnSet.setTableFlag(tableFlag);
			this.companyDao.saveOrUpdate(columnSet);
		}
	}

	/**
	 * 保存公司资料
	 * 
	 * @param request
	 *            请求控制
	 * @param company
	 *            公司资料
	 * @return Company 要保存的公司资料
	 */
	public Company saveCompany(Company company) {
		return (Company) companyDao.saveCompany(company);
	}

	/**
	 * 保存报关栏位设置参数
	 * 
	 * @param reportControl
	 */
	public void saveReportControl(Request request, ReportControl reportControl) {
		this.companyDao.saveReportControl(reportControl);
	}

	/**
	 * 查找报关栏位设置参数
	 * 
	 * @param reportControl
	 */
	public List findReportControl(Request request) {
		return this.companyDao.findReportControl();
	}

	@AuthorityFunctionAnnotation(caption = "查询操作日志", index = 4)
	public void checkLogsAuthority(Request request) {
		// TODO Auto-generated method stub

	}

	public String getLogInfo() {
		try {
			// (1).Test.class.getResource("")
			// 得到的是当前类FileTest.class文件的URI目录。不包括自己！
			System.out.println(SystemActionImpl.class.getResource("/"));
			;
			// 得到的是当前的classpath的绝对URI路径。
			System.out.println(Thread.currentThread().getContextClassLoader()
					.getResource(""));
			// 得到的也是当前ClassPath的绝对URI路径。
			System.out.println(SystemActionImpl.class.getClassLoader()
					.getResource(""));
			// 得到的也是当前ClassPath的绝对URI路径。
			System.out.println(ClassLoader.getSystemResource(""));
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		}
		System.out.println(":)");
		// jbcus_log.log
		java.net.URL url = SystemActionImpl.class.getClassLoader().getResource(
				"");
		System.out.println(":)");
		System.out.println(url.getPath());
		System.out.println(url.getPath().replace("/lib", "")
				+ "webapps/ROOT/jbcus_logs/log_invoke/invoke_log.log");
		try {
			String log = FileUtils.readFileToString(new File(url.getPath()
					.replace("/lib", "")
					+ "webapps/ROOT/jbcus_logs/log_invoke/invoke_log.log"),
					"GBK");
			System.out.println(log);
			return log;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试HTTP报文传输通道是否能连接
	 * 
	 * @param httpMsgTransType
	 * @return
	 */
	public boolean httpTestConnect(Request request, String httpMsgTransType) {
		return MessageHttpUtil.testConnect(httpMsgTransType);
	}

}