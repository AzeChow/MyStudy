package com.bestway.bls.dao;

import java.util.List;

import com.bestway.bls.entity.GoodsList;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

/**
 * 保税物料进口申请DAO
 * @author chen
 *
 */
public class MaterielImportApplyDao extends BaseDao{

	/**
	 * 保存,修改保税物料进口申请表头
	 */
	public void saveMaterielImportApply(MaterielImportApply materielImportApply){
		this.saveOrUpdate(materielImportApply);
	}
	
	/**
	 * 删除保税物料进口申请表头
	 */
	public void deleteMaterielImportApply(MaterielImportApply materielImportApply){
		this.delete(materielImportApply);		
	}
	
	/**
	 * 查询所有MaterielImportApply
	 * @return
	 */
	public List findMaterielImportApplyAll(){
		return this.find("from MaterielImportApply as a where a.company.id = ? ",new Object[] { CommonUtils.getCompany().getId()
				});
	}
	

	/**
	 * 保存保税物料进口申请商品信息
	 */
	public void saveGoodsList(GoodsList goodsList){
		this.saveOrUpdate(goodsList);
	}
	
	/**
	 * 删除保税物料进口申请商品信息
	 */
	public void deleteGoodsList(GoodsList goodsList){
		this.delete(goodsList);
	}
	
	/**
	 * 根据表头查询商品
	 * @return
	 */
	public List findMaterielImportApplyListByHead(MaterielImportApply materielImportApply){
		String hsql = "from GoodsList as a where a.materielImportApply.id=? and a.company.id = ?";
		return this.find(hsql, new Object[]{materielImportApply.getId(),CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 商品类别
	 */
	public List findComplex(String sFields, String sValue) {
		String hsql = "select c from Complex c left join fetch c.firstUnit b left join fetch c.secondUnit ";
		// + " left join fetch c.comUnit"
		// + " left join fetch c.curr"
		// + " left join fetch c.country";
		List list = null;
		if (sFields == null || sFields.equals("")) {
			list = this.getHibernateTemplate().find(
					hsql + " where (c.isOut <> '1' or c.isOut is null)");
		} else {
			hsql += " where (c.isOut <> '1' or c.isOut is null) and c."
					+ sFields + " like ? ";
			list = this.find(hsql, new Object[] { "%" + sValue + "%" });
		}
		return list;
	}
	
	/**
	 * 查询仓库编码
	 * @return
	 */
	 public List findDepotNo(){
		 String hsql = "from GoodsList as a where a.company.id = ?";
			return this.find(hsql, new Object[]{CommonUtils.getCompany().getId()}); 
	 }
}
