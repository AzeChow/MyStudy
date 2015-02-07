package com.bestway.bls.client.storagebill;

/**
 * 车次管理--出仓checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class DgExportDelivery extends DgBaseDelivery{
	@Override
	public void setVisible(boolean f) {
		if (f) {
			this.setTitle("转出车次管理");
			super.setVisible(f);
		}
	}
}
