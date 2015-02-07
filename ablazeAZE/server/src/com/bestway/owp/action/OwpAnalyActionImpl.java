package com.bestway.owp.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.owp.dao.OwpAnalyDao;
import com.bestway.owp.dao.OwpMessageDao;
import com.bestway.owp.entity.OwpBomDetail;
import com.bestway.owp.entity.OwpBomMaster;
import com.bestway.owp.entity.OwpBomVersion;
import com.bestway.owp.logic.OwpAnalyLogic;
import com.bestway.owp.logic.OwpMessageLogic;

public class OwpAnalyActionImpl implements OwpAnalyAction {
	private OwpAnalyDao owpAnalyDao;
	
	private OwpAnalyLogic owpAnalyLogic;
	
	public void updateMaterialBomWaste(Request request, Integer versionNo,
			String fourCode, String fourCode2, Double waste,
			boolean isByUnitUsed) {
		// TODO Auto-generated method stub
		
	}
	
	public OwpAnalyDao getOwpAnalyDao() {
		return owpAnalyDao;
	}

	public void setOwpAnalyDao(OwpAnalyDao owpAnalyDao) {
		this.owpAnalyDao = owpAnalyDao;
	}

	public OwpAnalyLogic getOwpAnalyLogic() {
		return owpAnalyLogic;
	}

	public void setOwpAnalyLogic(OwpAnalyLogic owpAnalyLogic) {
		this.owpAnalyLogic = owpAnalyLogic;
	}

	public void deleteOwpBomManage(Request request,
			OwpBomDetail materielBomDetail) {
		owpAnalyDao.delete(materielBomDetail);
		
	}

	public void deleteMaterielBomMaster(Request request, OwpBomMaster master) {
		
		owpAnalyLogic.deleteMaterielBomMaster(master);
		
	}

	public void deleteOwpBomVersion(Request request, OwpBomVersion version) {
		owpAnalyLogic.deleteOwpBomVersion(version);
		
	}

	public List findMaterielBomDetail(Request request, OwpBomVersion version) {
		return  owpAnalyDao.findMaterielBomDetail(version);
		
	}

	public List findMaterielBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return  owpAnalyDao.findMaterielBomMaster(index, length,
				property, value, isLike);
		
	}

	public List findMaterielBomVersion(Request request, OwpBomMaster master) {
		return owpAnalyDao.findMaterielBomVersion(master);
	}

	public List findOwpBomVersionByVersion(Request request,
			OwpBomMaster master, Integer version) {
		return owpAnalyDao.findOwpBomVersionByVersion(master,
				version);
	}
	
	public List findNotInBomDetailMaterial(Request request,
			OwpBomVersion version) {
		return owpAnalyDao.findNotInBomDetailMaterial(version);
	}

	public List saveOwpBomManage(Request request, OwpBomVersion version,
			List lsCustomDetail) {
		return  owpAnalyLogic.saveOwpBomManage(version,
				lsCustomDetail);
		
	}

	public OwpBomDetail saveOwpBomManage(Request request,
			OwpBomDetail detail) {
		owpAnalyDao.saveOrUpdate(detail);
		return detail;
	}

	public List saveOwpBomMaster(Request request, List lsCustomMaterial) {
		
		return owpAnalyLogic.saveOwpBomMaster(lsCustomMaterial);
	}

	public OwpBomDetail saveMaterielBomDetail(Request request,
			OwpBomDetail materielBomDetail) {
		owpAnalyDao.saveOrUpdate(materielBomDetail);
		return materielBomDetail;
	}

	public OwpBomVersion saveOwpBomVersion(Request request,
			OwpBomVersion owpBomVersion) {
		 owpAnalyDao.saveOrUpdate(owpBomVersion);
		 return owpBomVersion;
	}


/**
 * 新增外发加工BOM父级商品
 */
	public List findBomMaster(Request request) {
		return owpAnalyDao.findBomMaster();
	}
	/**
	 * 保存List
	 * @param listTo
	 */
	public void saveList(List listTo) {
		owpAnalyLogic.saveList(listTo);
	}
	/**
	 * 料件修改成品单耗、损耗。查找料件信息
	 */
	public List findOwpBomMateriel(Request request, int index, int length,
			String property, Object value, boolean isLike) {
		return this.owpAnalyDao.findMaterialBomMateriel(index, length,
				property, value, isLike);
	}
	/**
	 * 料件修改成品单耗、损耗。查找成品信息
	 */
	public List findMaterialBomByDetail(Request request, OwpBomDetail materiel) {
		return this.owpAnalyDao.findMaterialBomByDetail(materiel);
	}
	/**
	 * 申请外发加工发货明细表查询
	 * @param appConditions
	 * @return
	 */
	public List findOwpApplyOutGoods(List<Condition> appConditions,List<Condition> billConditions) {
		return owpAnalyLogic.findOwpApplyOutGoods(appConditions,billConditions);
	}
	/**
	 * 申请外发加工收货明细表查询
	 * @param conditions条件
	 * @return List
	 * hcl
	 */
	public List findOwpApplyInGoods(List<Condition> appConditions,List<Condition> billConditions) {
		return owpAnalyLogic.findOwpApplyInGoods(appConditions,billConditions);
	}
	/**
	  * 
	  * hcl
	  */
		public List findOwpStockAll(List<Condition> conditions) {
			return this.owpAnalyLogic.findOwpStockAll(conditions);
		}
		 /**
		  * 查找所有申请表
		  * hcl
		  */
		public List findAllApplyNo() {
			return this.owpAnalyLogic.findAllApplyNo();
		}

		/**
		 * 外发加工发货明细表查询
		 * @param conditions
		 * @return
		 */
		public List findOwpOutGoods(List<Condition> conditions) {
			return owpAnalyLogic.findOwpOutGoods(conditions);
		}
		/**
		 * 外发加工收货明细表查询
		 * @param conditions
		 * @return
		 */
		public List findOwpInGoods(List<Condition> conditions) {
			return owpAnalyLogic.findOwpInGoods(conditions);
		}
		/**
		 * 返回折料情况表
		 * @param conditions
		 * @return
		 */
		public List findReturnToBom(List<Condition> conditions) {
			return owpAnalyLogic.findReturnToBom(conditions);
		}
}
