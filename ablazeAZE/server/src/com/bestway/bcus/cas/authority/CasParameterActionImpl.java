package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//参数设置
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasParameterActionImpl extends BaseActionImpl implements CasParameterAction{
	
	
	@AuthorityFunctionAnnotation(caption = "参数设置--浏览", index = 1)
	public void checkCasParameterByBrowse(Request request){
		
	}

	
	@AuthorityFunctionAnnotation(caption = "参数设置--修改", index = 1)
	public void checkCasParameterByUpdate(Request request){
		
	}
}
