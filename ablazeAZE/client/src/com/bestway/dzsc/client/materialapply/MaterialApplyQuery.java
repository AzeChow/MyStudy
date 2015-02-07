package com.bestway.dzsc.client.materialapply;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;

public class MaterialApplyQuery {
	private static MaterialApplyQuery query = null;

	private MaterialApplyQuery() {

	}

	public static MaterialApplyQuery getInstance() {
		if (query == null) {
			query = new MaterialApplyQuery();
		}
		return query;
	}

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterialNotInApply(String scmCoiCode) {
		MaterialApplyAction materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		List lsDataSource = materialApplyAction.findMaterialNotInApply(
				new Request(CommonVars.getCurrUser(), true), scmCoiCode);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
		// list.add(new JTableListColumn("报关商品名称", "ptName", 100));
		// list.add(new JTableListColumn("报关型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		// list.add(new JTableListColumn("版本号", "ptVersion", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("没有备案的物料(来自于报关常用工厂物料)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterialApplied(String materialType) {
		MaterialApplyAction materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		List lsDataSource = materialApplyAction.findMaterialApplied(
				new Request(CommonVars.getCurrUser(), true), materialType);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
		// list.add(new JTableListColumn("报关商品名称", "ptName", 100));
		// list.add(new JTableListColumn("报关型号规格", "ptSpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		// list.add(new JTableListColumn("版本号", "ptVersion", 50));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("备案的物料(来自于物料备案)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}
	// /**
	// * 查询未生效的物料（已申请未生效）
	// *
	// * @param materielType
	// * @return
	// */
	// public List findNotEffectiveMaterial(String scmCoiCode) {
	// MaterialApplyAction materialApplyAction = (MaterialApplyAction)
	// CommonVars
	// .getApplicationContext().getBean("materialApplyAction");
	// List lsDataSource = materialApplyAction.findNotEffectiveMaterial(
	// new Request(CommonVars.getCurrUser(), true), scmCoiCode);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
	// list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
	// list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
	// list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
	// list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
	// // list.add(new JTableListColumn("报关商品名称", "materiel.ptName", 100));
	// // list.add(new JTableListColumn("报关型号规格", "materiel.ptSpec", 100));
	// list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
	// list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
	// list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
	// // list.add(new JTableListColumn("版本号", "ptVersion", 50));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(lsDataSource);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.setTitle("未生效的物料");
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return null;
	// }

	// /**
	// * 查询原始的物料（未申请）
	// *
	// * @param materielType
	// * @return
	// */
	// public List findOriginalMaterial(String scmCoiCode) {
	// MaterialApplyAction materialApplyAction = (MaterialApplyAction)
	// CommonVars
	// .getApplicationContext().getBean("materialApplyAction");
	// List lsDataSource = materialApplyAction.findOriginalMaterial(
	// new Request(CommonVars.getCurrUser(), true), scmCoiCode);
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
	// list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
	// list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
	// list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
	// list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
	// // list.add(new JTableListColumn("报关商品名称", "materiel.ptName", 100));
	// // list.add(new JTableListColumn("报关型号规格", "materiel.ptSpec", 100));
	// list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
	// list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
	// list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
	// // list.add(new JTableListColumn("版本号", "ptVersion", 50));
	// DgCommonQuery.setTableColumns(list);
	// final DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(lsDataSource);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.setTitle("原始的物料（未申请） ");
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return null;
	// }

	/**
	 * 抓取还没有备案的单耗
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterialBomNotInApply() {
		MaterialApplyAction materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		List lsDataSource = materialApplyAction
				.findMaterialBomNotInApply(new Request(
						CommonVars.getCurrUser(), true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
		list.add(new JTableListColumn("货号", "materiel.ptNo", 100));
		// list.add(new JTableListColumn("商品编码", "master.materiel.complex.code",
		// 80));
		list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
		// list.add(new JTableListColumn("商品名称", "master.materiel.ptName",
		// 100));
		// list.add(new JTableListColumn("版本号", "version", 50));
		// list.add(new JTableListColumn("开始有效日期", "beginDate", 100));
		// list.add(new JTableListColumn("结束有效日期", "endDate", 100));
		// list.add(new JTableListColumn("型号规格", "master.materiel.ptSpec",
		// 100));
		// list
		// .add(new JTableListColumn("单位", "master.materiel.calUnit.name",
		// 50));
		// list.add(new JTableListColumn("单价", "master.materiel.ptPrice", 50));
		// list.add(new JTableListColumn("净重", "master.materiel.ptNetWeight",
		// 50));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("没有备案的BOM成品(来自于报关常用工厂BOM)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询没有在BOM备案中的Bom版本
	 * 
	 * @param master
	 * @return
	 */
	public List findMaterialBomVersionNotInApply(MaterialBomMasterApply master) {
		MaterialApplyAction materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		List lsDataSource = materialApplyAction
				.findMaterialBomVersionNotInApply(new Request(CommonVars
						.getCurrUser(), true), master);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("类别", "master.materiel.scmCoi.name",
		// 80));
		// list.add(new JTableListColumn("货号", "master.materiel.ptNo", 100));
		// list.add(new JTableListColumn("商品编码", "master.materiel.complex.code",
		// 80));
		// list.add(new JTableListColumn("商品名称", "master.materiel.ptName",
		// 100));
		list.add(new JTableListColumn("版本号", "version", 50));
		list.add(new JTableListColumn("开始有效日期", "beginDate", 100));
		list.add(new JTableListColumn("结束有效日期", "endDate", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("没有备案的BOM版本");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查询没有在BOM备案的BOM料件
	 * 
	 * @param version
	 * @return
	 */
	public List findMaterialBomDetailNotInApply(MaterialBomVersionApply version) {
		MaterialApplyAction materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		List lsDataSource = materialApplyAction
				.findMaterialBomDetailNotInApply(new Request(CommonVars
						.getCurrUser(), true), version);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("版本号", "version.version", 50));
		list.add(new JTableListColumn("开始有效日期", "version.beginDate", 100));
		list.add(new JTableListColumn("结束有效日期", "version.endDate", 100));
		list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
		list.add(new JTableListColumn("货号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("单耗", "unitWaste", 100));
		list.add(new JTableListColumn("损耗", "waste", 50));
		list.add(new JTableListColumn("单项用量", "unitUsed", 50));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(lsDataSource);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("没有备案的BOM料件");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
