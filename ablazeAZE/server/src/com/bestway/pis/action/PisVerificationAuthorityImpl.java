package com.bestway.pis.action;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

@AuthorityClassAnnotation(caption="发送报关行",index = 26)
public class PisVerificationAuthorityImpl extends BaseActionImpl implements PisVerificationAuthority{

	@AuthorityFunctionAnnotation(caption="同步资料",index=26.1)
	public void checkPisSync(Request request){}
	@AuthorityFunctionAnnotation(caption="同步代理公司-BCGS同步至JBCUS",index=26.101)
	public void checkExecute(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-选择代理公司",index=26.102)
	public void checkSelectBrokerCorp(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-删除代理公司",index=26.103)
	public void checkDelSelect(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-JBCUS同步至ESP",index=26.104)
	public void checkExecuteSyncThird(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-授权代理公司明细新增",index=26.105)
	public void checkAddThird(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-授权代理公司明细修改",index=26.106)
	public void checkEditThird(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-授权代理公司明细删除",index=26.107)
	public void checkDeleteThird(Request request){}
	@AuthorityFunctionAnnotation(caption="同步授权代理公司-授权代理公司明细保存",index=26.108)
	public void checkSaveThird(Request request){}
	@AuthorityFunctionAnnotation(caption="同步企业登录账号-JBCUS同步至ESP",index=26.109)
	public void checkExecuteSyncUser(Request request){}
	@AuthorityFunctionAnnotation(caption="同步代理公司的申报单位-JBCUS同步至ESP",index=26.11)
	public void checkExecuteSyncAgentCorp(Request request){}
	
	@AuthorityFunctionAnnotation(caption="进口报关单",index=26.2)
	public void checkPisImpDec(Request request){}
	@AuthorityFunctionAnnotation(caption="进口报关单-发送报关行",index=26.201)
	public void checkPisImpDecUpload(Request request){}
	@AuthorityFunctionAnnotation(caption="进口报关单-提取报关单号",index=26.202)
	public void checkPisImpDecDownload(Request request){}
	@AuthorityFunctionAnnotation(caption="进口报关单-撤销",index=26.203)
	public void checkPisImpDecCancel(Request request){}
	
	@AuthorityFunctionAnnotation(caption="出口报关单",index=26.3)
	public void checkPisExpDec(Request request){}
	@AuthorityFunctionAnnotation(caption="出口报关单-发送报关行",index=26.301)
	public void checkPisExpDecUpload(Request request){}
	@AuthorityFunctionAnnotation(caption="出口报关单-提取报关单号",index=26.302)
	public void checkPisExpDecDownload(Request request){}
	@AuthorityFunctionAnnotation(caption="出口报关单-撤销",index=26.303)
	public void checkPisExpDecCancel(Request request){}
	
	@AuthorityFunctionAnnotation(caption="特殊报关单",index=26.4)
	public void checkPisSpecialDec(Request request){}
	@AuthorityFunctionAnnotation(caption="特殊报关单-发送报关行",index=26.401)
	public void checkPisSpecialDecUpload(Request request){}
	@AuthorityFunctionAnnotation(caption="特殊报关单-提取报关单号",index=26.402)
	public void checkPisSpecialDecDownload(Request request){}
	@AuthorityFunctionAnnotation(caption="特殊报关单-撤销",index=26.403)
	public void checkPisSpecialDecCancel(Request request){}
	
	@AuthorityFunctionAnnotation(caption="报关单进度查看",index=26.5)
	public void checkDecProcessQuery(Request request){}
	@AuthorityFunctionAnnotation(caption="报关单进度查看-进度查看",index=26.501)
	public void checkShowProcess(Request request){}
	
}
