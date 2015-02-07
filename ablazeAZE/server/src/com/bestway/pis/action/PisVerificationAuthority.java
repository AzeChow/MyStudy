package com.bestway.pis.action;

import com.bestway.common.Request;

public interface PisVerificationAuthority {
	
	/**
	 * 检查同步资料权限
	 * @param request
	 */
	public void checkPisSync(Request request);
	/**
	 * 检查同步代理公司-BCGS同步至JBCUS权限
	 * @param request
	 */
	public void checkExecute(Request request);
	/**
	 * 检查同步授权代理公司-选择代理公司权限
	 * @param request
	 */
	public void checkSelectBrokerCorp(Request request);
	/**
	 * 检查同步授权代理公司-删除代理公司权限
	 * @param request
	 */
	public void checkDelSelect(Request request);
	/**
	 * 检查同步授权代理公司-JBCUS同步至ESP权限
	 * @param request
	 */
	public void checkExecuteSyncThird(Request request);
	/**
	 * 检查同步授权代理公司-授权代理公司明细新增权限
	 * @param request
	 */
	public void checkAddThird(Request request);
	/**
	 * 检查同步授权代理公司-授权代理公司明细修改权限
	 * @param request
	 */
	public void checkEditThird(Request request);
	/**
	 * 检查同步授权代理公司-授权代理公司明细删除权限
	 * @param request
	 */
	public void checkDeleteThird(Request request);
	/**
	 * 检查同步授权代理公司-授权代理公司明细保存权限
	 * @param request
	 */
	public void checkSaveThird(Request request);
	/**
	 * 检查同步企业登录账号-JBCUS同步至ESP权限
	 * @param request
	 */
	public void checkExecuteSyncUser(Request request);
	/**
	 * 检查同步代理公司的申报单位-JBCUS同步至ESP权限
	 * @param request
	 */
	public void checkExecuteSyncAgentCorp(Request request);
	
	/**
	 * 检查进口报关单权限
	 * @param request
	 */
	public void checkPisImpDec(Request request);
	/**
	 * 检查进口报关单-发送报关行权限
	 * @param request
	 */
	public void checkPisImpDecUpload(Request request);
	/**
	 * 检查进口报关单-提取报关单号权限
	 * @param request
	 */
	public void checkPisImpDecDownload(Request request);
	/**
	 * 检查进口报关单-撤销权限
	 * @param request
	 */
	public void checkPisImpDecCancel(Request request);
	
	/**
	 * 检查出口报关单权限
	 * @param request
	 */
	public void checkPisExpDec(Request request);
	/**
	 * 检查出口报关单-发送报关行权限
	 * @param request
	 */
	public void checkPisExpDecUpload(Request request);
	/**
	 * 检查出口报关单-提取报关单号权限
	 * @param request
	 */
	public void checkPisExpDecDownload(Request request);
	/**
	 * 检查出口报关单-撤销权限
	 * @param request
	 */
	public void checkPisExpDecCancel(Request request);
	
	/**
	 * 检查特殊报关单权限
	 * @param request
	 */
	public void checkPisSpecialDec(Request request);
	/**
	 * 检查特殊报关单-发送报关行权限
	 * @param request
	 */
	public void checkPisSpecialDecUpload(Request request);
	/**
	 * 检查特殊报关单-提取报关单号权限
	 * @param request
	 */
	public void checkPisSpecialDecDownload(Request request);
	/**
	 * 检查特殊报关单-撤销权限
	 * @param request
	 */
	public void checkPisSpecialDecCancel(Request request);
	
	/**
	 * 检查报关单进度查看权限
	 * @param request
	 */
	public void checkDecProcessQuery(Request request);
	/**
	 * 检查报关单进度查看-进度查看权限
	 * @param request
	 */
	public void checkShowProcess(Request request);
	
}
