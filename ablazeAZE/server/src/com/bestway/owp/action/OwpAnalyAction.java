package com.bestway.owp.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.owp.entity.OwpBomDetail;
import com.bestway.owp.entity.OwpBomMaster;
import com.bestway.owp.entity.OwpBomVersion;

public interface OwpAnalyAction {
	/**
	 * 批量更新外发加工BOM料件的损耗率
	 * 
	 * @param request
	 *            请求控制
	 * @param versionNo
	 *            版本号
	 * @param pFourCode
	 *            成品四码
	 * @param mFourCode
	 *            料件四码
	 * @param waste
	 *            损耗率
	 * @param isByUnitUsed
	 *            true为更新单耗，false为更新单项用量
	 */
	void updateMaterialBomWaste(Request request, Integer versionNo,
			String pFourCode, String mFourCode, Double waste,
			boolean isByUnitUsed);
	/**
	 * 根据条件查找外发加工BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是OwpBomMaster型，外发加工BOM成品
	 * hcl
	 */
	List findMaterielBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike);
	/**
	 * 根据外发加工BOM版本查找外发加工BOM里的料件
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是OwpBomManage型，外发加工BOM料件
	 * hcl
	 */
	List findMaterielBomDetail(Request request, OwpBomVersion version);
	/**
	 * 根据外发加工BOM成品查找外发加工BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *           外发加工BOM成品
	 * @return List 是OwpBomVersion型，外发加工BOM版本
	 * hcl
	 */
	List findMaterielBomVersion(Request request,OwpBomMaster master);
	/**
	 * 根据外发加工BOM版本删除外发加工BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 */
	void deleteOwpBomVersion(Request request, OwpBomVersion version);
	
	/**
	 * 根据外发加工BOM版本、外发加工物料保存外发加工BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 */
	List saveOwpBomManage(Request request, OwpBomVersion version,
			List lsCustomDetail);
	/**
	 * 删除外发加工BOM料件
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomDetail
	 *            外发加工BOM料件
	 */
	void deleteOwpBomManage(Request request,
			OwpBomDetail materielBomDetail);
	/**
	 * 保存外发加工BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            外发加工BOM成品资料
	 */
	void deleteMaterielBomMaster(Request request, OwpBomMaster master);
	/**
	 * 保存外发加工BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 */
	OwpBomDetail saveOwpBomManage(Request request,
			OwpBomDetail detail);
	/**
	 * 新增BOM或者从BOM中插入BOM料件;
	 * 
	 * @param list
	 *            要插入的料件
	 * 
	 */
	List saveOwpBomMaster(Request request, List lsCustomMaterial);
	/**
	 * 根据外发加工BOM成品、版本号查找外发加工BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            外发加工BOM成品
	 * @param version
	 *            版本号
	 * @return List 是型，外发加工BOM版本
	 */
	List findOwpBomVersionByVersion(Request request,
			OwpBomMaster master, Integer version);
	/**
	 * 保存外发加工BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomVersion
	 *            外发加工BOM版本
	 */
	OwpBomVersion saveOwpBomVersion(Request request,
			OwpBomVersion materielBomVersion);
	/**
	 * 根据版本号查找物料里主档的资料，但是不在外发加工BOM料件里查找(以父级料号、父级版本过滤)
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是物料主档型
	 */
	List findNotInBomDetailMaterial(Request request, OwpBomVersion version);
	/**
	 * 保存外发加工BOM料件
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomDetail
	 *            外发加工BOM料件
	 */
	OwpBomDetail saveMaterielBomDetail(Request request,
			OwpBomDetail materielBomDetail);
	/**
	 * 查找外发加工物料里的成品资料，但是不在外发加工BOM成品里查找
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Materiel型，外发加工物料
	 */
	List findBomMaster(Request request);

		/**
		 * 保持List
		 * @param listTo
		 */
		void saveList(List listTo);

		List findOwpBomMateriel(Request request, int index, int length,
				String property, Object value, boolean isLike);

		List findMaterialBomByDetail(Request request, OwpBomDetail materiel);

		/**
		 * 申请外发加工发货明细表查询
		 * @param conditions
		 * @return
		 */
		List findOwpApplyOutGoods(List<Condition> appConditions,List<Condition> billConditions);
		/**
		 * 申请外发加工收货明细表查询
		 * @param conditions
		 * @return
		 */
		List findOwpApplyInGoods(List<Condition> appConditions,List<Condition> billConditions);
		/**
		 * 外发加工发货明细表查询
		 * @param conditions
		 * @return
		 */
		List findOwpOutGoods(List<Condition> conditions);
		/**
		 * 外发加工收货明细表查询
		 * @param conditions
		 * @return
		 */
		List findOwpInGoods(List<Condition> conditions);
		/**
		 * 查找外发加工货物库存汇总表
		 * @param conditions
		 * @return
		 */
		List findOwpStockAll(List<Condition> conditions);
		/**
		 * 查找所有正在执行的申请表编号
		 * @return
		 */
		List findAllApplyNo();
		/**
		 * 返回折料情况表
		 * @param conditions
		 * @return
		 */
		List findReturnToBom(List<Condition> conditions);

}
