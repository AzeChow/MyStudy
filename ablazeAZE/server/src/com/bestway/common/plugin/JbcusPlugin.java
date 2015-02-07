package com.bestway.common.plugin;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bestway.common.BaseDao;

/** 
 * 这个是用于客户的插件调用服务器的方法用到服务器插件  
 */

public interface JbcusPlugin {

	List run(Map parameters);

	void dispose();

	void init(ApplicationContext ctxt, BaseDao baseDao);

}
