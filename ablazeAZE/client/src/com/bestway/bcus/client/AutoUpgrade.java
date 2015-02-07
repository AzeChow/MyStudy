package com.bestway.bcus.client;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.upgrade.DgUpgrade;
import com.bestway.client.upgrade.UpgradeServiceService;
import com.bestway.client.upgrade.httpconfig.UpdateHttpConfigServiceService;
import com.bestway.client.windows.form.Upgrade;
import com.bestway.common.Request;

public class AutoUpgrade implements Upgrade {

	private UpgradeServiceService upgradeService = null;
	
	public void run() {
		DgUpgrade dg = new DgUpgrade();
		dg.setVisible(true);
//		try {
//			SystemAction systemAction = (SystemAction) CommonVars
//					.getApplicationContext().getBean("systemAction");			
//			systemAction.autoUpgradeJBCUS(new Request(CommonVars
//					.getCurrUser()));
//			new CheckAutoUpgradeState(" 服务器升级 ").start();
//		} catch (Exception ex) {
//			
//			ex.printStackTrace();
//			throw new RuntimeException(ex.getMessage());
//		}

	}

	public void restart() {
		String serverHostAddress = CommonVars.getServerName();
		upgradeService = UpgradeServiceService
				.getUpgradeServiceService(serverHostAddress);
		upgradeService.getUpgradeServicePort().restartTomcat();
//		try {
//			SystemAction systemAction = (SystemAction) CommonVars
//					.getApplicationContext().getBean("systemAction");
//			systemAction.restart(new Request(CommonVars
//					.getCurrUser()));
//			new CheckAutoUpgradeState(" 重启服务器 ").start();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException(ex.getMessage());
//		}

	}

	
	
}
