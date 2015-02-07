package com.bestway.common.plugin;

import org.springframework.context.ApplicationContext;

import com.bestway.common.BaseDao;
import com.bestway.common.tools.entity.PluginInfo;

/** 服务器端插件 */
public interface JbcusServerPlugin {
	static int	ISRUN	= 0;
	static int	ISSTOP	= 1;

	void run(PluginInfo pluginInfo);

	void dispose();

	void init(ApplicationContext ctxt, BaseDao baseDao);

	void stop();

	int getState();
}
