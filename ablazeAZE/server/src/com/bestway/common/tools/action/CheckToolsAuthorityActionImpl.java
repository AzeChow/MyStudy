/*
 * Created on 2004-9-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.tools.action;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "二次开发平台", index = 25)
public class CheckToolsAuthorityActionImpl extends BaseActionImpl implements
		CheckToolsAuthorityAction {

	@AuthorityFunctionAnnotation(caption = "数据查询", index = 1)
	public void checkToolsAuthority(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "数据字典", index = 1.1)
	public void checkToolsAuthorityDiary(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "执行插件", index = 2)
	public void checkGroovyServiceAuthority(Request request) {

	}
}
