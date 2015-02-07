package com.bestway.bcus.system.action;

import java.util.List;

import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.bcus.system.logic.CreateDirLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.AclUser;

@AuthorityClassAnnotation(caption = "电子化手册", index =5)
public class ContractSystemActionImpl extends BaseActionImpl implements
		ContractSystemAction {
	private CompanyDao companyDao = null;
	private CreateDirLogic createDirLogic = null;

	/**
	 * 浏览系统参数设置权限控制
	 * 
	 * @param request
	 *            请求控制
	 */
	/**
	 * 权限控制重复
	 */
//	@AuthorityFunctionAnnotation(caption = "系统参数设置--浏览", index = 0)
//	public void checkSystemParameterAuthority(Request request) {
//		// TODO Auto-generated method stub
//
//	}

	public List findnameValues(Request request, int type) {
		return createDirLogic.getCreateDirDao().findnameValues(type);
	}

	public void saveAclUser(Request request, AclUser obj) {
		this.companyDao.saveAclUser(obj);// TODO Auto-generated method stub

	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	@AuthorityFunctionAnnotation(caption = "系统参数设置--保存", index = 0)
	public void saveValues(Request request, ParameterSet parameterSet) {
		createDirLogic.getCreateDirDao().saveValues(parameterSet);
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public CreateDirLogic getCreateDirLogic() {
		return createDirLogic;
	}

	public void setCreateDirLogic(CreateDirLogic createDirLogic) {
		this.createDirLogic = createDirLogic;
	}

}