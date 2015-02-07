package com.bestway.common.fpt.btplsinput.action;

import java.util.List;

import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.materialbase.entity.ScmCoc;


public interface BtplsDownloadParaAction{
	
	public BtplsDownloadPara saveBtplsDownloadPara(BtplsDownloadPara dowloadPara);

	public void deleteBtplsDownloadPara(BtplsDownloadPara dowloadPara);
	
	public BtplsDownloadPara findBtplsDownloadPara(BtplsDownloadPara dowloadPara);
	
	public List findAllBtplsDownloadPara();
	/**
	 * 获得转厂客户供应商
	 */
	public List findScmManuFactory();
	
	/**
	 *根据 客户/供应商查找深加工下载配置参数
	 * @return
	 */
	public List findBtplsDownloadParaByScm(ScmCoc sc);
	/**
	 * 获得深加工下载转厂客户供应商
	 */
	public List findScmManuFactoryFromBtplsDownloadPara();
}
