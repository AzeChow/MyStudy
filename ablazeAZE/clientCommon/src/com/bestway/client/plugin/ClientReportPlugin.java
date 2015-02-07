package com.bestway.client.plugin;

import java.awt.Component;

/** 客户端报表插件 */
public interface ClientReportPlugin {
	/** component is JDialogBase or JInternalFrameBase */
	public void execute(Component component);
}
