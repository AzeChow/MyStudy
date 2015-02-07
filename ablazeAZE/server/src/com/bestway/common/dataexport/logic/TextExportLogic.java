package com.bestway.common.dataexport.logic;

import java.util.List;

import com.bestway.common.dataexport.dao.TextExportDao;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;
/**
 * @author luosheng 2006/9/1
 * 
 */
public class TextExportLogic {
	private TextExportDao	textExportDao	= null;

	public TextExportDao getTextExportDao() {
		return textExportDao;
	}

	public void setTextExportDao(TextExportDao textExportDao) {
		this.textExportDao = textExportDao;
	}

	/** 转抄 TxtToDBRegion */
	public TxtToDBRegion copyTxtToDBRegion(TxtToDBRegion srcTxtToDBRegion) {

		TxtToDBRegion newTxtToDBRegion = srcTxtToDBRegion;
		String txtToDBRegion = newTxtToDBRegion.getId();
		newTxtToDBRegion.setId(null);
		newTxtToDBRegion.setRegionName("copy_"
				+ newTxtToDBRegion.getRegionName());
		this.textExportDao.saveTxtToDBRegion(newTxtToDBRegion);

		List<TxtToDBRegionSetup> jdbcRegionSetupList = this.textExportDao
				.findTxtToDBRegionSetup(txtToDBRegion);
		for (TxtToDBRegionSetup txtToDBRegionSetup : jdbcRegionSetupList) {
			txtToDBRegionSetup.setId(null);
			txtToDBRegionSetup.setTxtToDBRegion(newTxtToDBRegion);
			this.textExportDao.saveTxtToDBRegionSetup(txtToDBRegionSetup);
		}

		return newTxtToDBRegion;
	}

	/** 转抄 DBToTxtRegion */
	public DBToTxtRegion copyDBToTxtRegion(DBToTxtRegion srcDBToTxtRegion) {
		DBToTxtRegion newDBToTxtRegion = srcDBToTxtRegion;
		newDBToTxtRegion.setId(null);
		newDBToTxtRegion.setRegionName("copy_"
				+ newDBToTxtRegion.getRegionName());
		this.textExportDao.saveDBToTxtRegion(newDBToTxtRegion);
		return newDBToTxtRegion;
	}

	/** 删除 TxtTask */
	public void deleteTxtDBTask(TxtDBTask jdbcTask) {
		List<TxtDBTaskDetail> listDetail = this.textExportDao
				.findTxtDBTaskDetail(jdbcTask.getId());
		for (TxtDBTaskDetail jdbcTaskDetail : listDetail) {
			this.textExportDao.deleteTxtDBTaskDetail(jdbcTaskDetail);
		}
		this.textExportDao.deleteTxtDBTask(jdbcTask);
	}

}
