package com.bestway.common.fpt.btplsinput.action;

import java.util.List;

import com.bestway.common.fpt.btplsinput.dao.BtplsDownloadDao;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.materialbase.entity.ScmCoc;

public class BtplsDownloadParaActionImpl implements BtplsDownloadParaAction{
	private BtplsDownloadDao btplsDownloadDao ;

	
	public void setBtplsDownloadDao(BtplsDownloadDao btplsDownloadDao) {
		this.btplsDownloadDao = btplsDownloadDao;
	}
	/**
	 * 删除深加工下载配置参数
	 */
	@Override
	public void deleteBtplsDownloadPara(BtplsDownloadPara dowloadPara) {
		btplsDownloadDao.delete(dowloadPara);
	}
	/**
	 * 获得所有深加工下载配置参数
	 */
	@Override
	public List findAllBtplsDownloadPara() {
		return btplsDownloadDao.findAllBtplsDownloadPara();
	}
	/**
	 * 获得某个深加工下载配置参数
	 */
	@Override
	public BtplsDownloadPara findBtplsDownloadPara(BtplsDownloadPara dowloadPara) {
		return btplsDownloadDao.findBtplsDownloadPara(dowloadPara);
	}
	/**
	 * 保存深加工下载配置参数
	 */
	@Override
	public BtplsDownloadPara saveBtplsDownloadPara(BtplsDownloadPara dowloadPara) {
		return btplsDownloadDao.saveBtplsDownloadPara(dowloadPara);
	}
	/**
	 * 转厂客户供应商
	 * @return
	 */
	public List findScmManuFactory() // 转厂客户供应商
	{
		return btplsDownloadDao.findScmManuFactory();
	}
	/**
	 * 获得深加工下载转厂客户供应商
	 * @return
	 */
	public List findScmManuFactoryFromBtplsDownloadPara()
	{
		return btplsDownloadDao.findScmManuFactoryFromBtplsDownloadPara();
	}
	/**
	 *根据 客户/供应商查找深加工下载配置参数
	 * @return
	 */
	public List findBtplsDownloadParaByScm(ScmCoc sc) // 转厂客户供应商
	{
		List list = btplsDownloadDao.findBtplsDownloadParaByScm(sc);
		return list;
	}
}
