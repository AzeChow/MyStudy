
package com.bestway.common.client.materialbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

public class MaterialQuery {
	private static MaterialQuery query=null;
	
	private MaterialQuery(){
		
	}
	public static MaterialQuery getInstance(){
		if(query==null){
			query=new MaterialQuery();
		}
		return query;
	}
	/**
	 * 抓取不在报关用物料的企业原始物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMaterialNotInMaster() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findEnterpriseMaterialNotInMaster(new Request(CommonVars
						.getCurrUser(),true));
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	
	/**
	 * hw
	 * 抓取不在报关用物料的企业原始物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMaterialNotInMasterMaterial() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findEnterpriseMaterialNotInMasterMateriel(new Request(CommonVars
						.getCurrUser(),true));
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * hw
	 * 抓取所有报关用物料的企业原始物料
	 * 成品半成品
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMaterial() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List type=new ArrayList<String>();
		type.add(MaterielType.FINISHED_PRODUCT);
		type.add(MaterielType.SEMI_FINISHED_PRODUCT);
		List lsDataSource= materialManageAction
				.findEnterpriseMaterial(new Request(CommonVars
						.getCurrUser(),true),type);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * hw
	 * 抓取所有报关用物料的企业原始物料
	 * 成品半成品
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMasterMaterial() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List type=new ArrayList<String>();
		type.add(MaterielType.MATERIEL);
		type.add(MaterielType.SEMI_FINISHED_PRODUCT);
		List lsDataSource= materialManageAction
				.findEnterpriseMaterial(new Request(CommonVars
						.getCurrUser(),true),type);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * 抓取不在报关用物料的企业原始物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMaterialNotInDetail(String parentNo,
			Integer version) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findEnterpriseMaterialNotInDetail(new Request(CommonVars
						.getCurrUser(),true),parentNo,version);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
        
        list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * 抓取不在报关用物料的企业原始物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findEnterpriseMaterialForMaterial(
			String materielType) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findEnterpriseMaterialForMaterial(new Request(CommonVars
						.getCurrUser(),true),materielType);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * 查询未在BOM表头的物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findNotInBomMasterMaterial() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findNotInBomMasterMaterial(new Request(CommonVars
						.getCurrUser(),true));
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("报关商品名称", "ptName", 100));
//		list.add(new JTableListColumn("报关型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	
	/**
	 * 查询未在BOM表头的物料(半成品)
	 * 
	 * @param materielType
	 * @return
	 */
	public List findNotInBomMasterMaterial2() {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findNotInBomMasterMaterial2(new Request(CommonVars
						.getCurrUser(),true));
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("报关商品名称", "ptName", 100));
//		list.add(new JTableListColumn("报关型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	/**
	 * 查询未在BOM表头的物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findNotInBomDetailMaterial(MaterialBomVersion version) {
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List lsDataSource= materialManageAction
				.findNotInBomDetailMaterial(new Request(CommonVars
						.getCurrUser(),true),version);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
//		list.add(new JTableListColumn("商品名称", "ptName", 100));
//		list.add(new JTableListColumn("型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
//		list.add(new JTableListColumn("版本号", "ptVersion", 50)); 
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("企业原始物料资料");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	
	/**
	 * 查询通关合同备案料件
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterialInContract(List listBomDetail) {
		ContractAction contractAction = (ContractAction) CommonVars
		.getApplicationContext().getBean("contractAction");
		List lsDataSource= contractAction
				.findMaterialInContract(new Request(CommonVars
						.getCurrUser(),true),listBomDetail);
		System.out.println("lsDataSource=="+lsDataSource.size());
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格型号", "spec", 80));
		list.add(new JTableListColumn("单位", "unit.name", 100));
		list.add(new JTableListColumn("单价", "declarePrice", 50));
		list.add(new JTableListColumn("净重", "unitWeight", 50));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("通关合同备案料件");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
}
