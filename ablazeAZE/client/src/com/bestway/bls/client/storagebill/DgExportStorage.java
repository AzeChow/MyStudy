package com.bestway.bls.client.storagebill;

/**
 * 仓单管理--出仓checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class DgExportStorage extends DgBaseStorage {
	@Override
	public void setVisible(boolean f) {
		if (f) {
			this.setTitle("转出仓单管理");
			super.setVisible(f);
		}
	}
}
