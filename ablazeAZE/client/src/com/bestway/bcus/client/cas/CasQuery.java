/*
 * Created on 2005-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JToolBar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempRelationCommInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 2009年7月9日 贺巍补加注释
 * 海关帐公共查询
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"unchecked","serial"})
public class CasQuery {
	/**
	 * 静态的实例
	 */
	private static CasQuery casQuery = null;

	/**
	 * 构造方法（无参数）
	 */
	private CasQuery() {

	}

	/**
	 * 获取实例 供外部调用（同步）
	 * @return
	 */
	public static synchronized CasQuery getInstance() {
		if (casQuery == null) {
			casQuery = new CasQuery();
		}
		return casQuery;
	}

	/**
	 * 新增商品大类时,进行大类编码查询
	 * 
	 * @param request
	 * @param materialType
	 * @return
	 */
	public Object findComplex(Request request, String materialType) {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findComplex(new Request(CommonVars
				.getCurrUser(), true), materialType);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param materialType
	 * @return
	 */
	public List<TempRelationCommInfo> findMaterialForRelation(
			String materialType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料名称", "factoryName", 100));
		list.add(new JTableListColumn("型号规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		DgCommonQuery.setTableColumns(list);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findMaterialForRelation(new Request(
				CommonVars.getCurrUser(), true), materialType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("物料查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 新增对照关系时，选择报关商品 卫生
	 * 
	 * @param materialType
	 * @return
	 */
	public List<TempRelationCommInfo> findComplexForRelation(String materialType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品序号", "seqNum", 80));
		list.add(new JTableListColumn("商品号码", "cusCode", 100));
		list.add(new JTableListColumn("商品名称", "cusName", 100));
		list.add(new JTableListColumn("商品规格", "cusSpec", 100));
		list.add(new JTableListColumn("单位", "cusUnit.name", 50));
		DgCommonQuery.setTableColumns(list);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List dataSource = casAction.findComplexForRelation(new Request(
				CommonVars.getCurrUser(), true), materialType);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关商品查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List<StatCusNameRelationMt> getStatCusNameRelationMt(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", "a.materiel.ptNo",
				100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "materiel.factoryName",
				"a.materiel.factoryName", 100));
		list
				.add(new JTableListColumn("规格", "materiel.factorySpec", 120,
						false));
		list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name", 100,
				false));
		list
				.add(new JTableListColumn("海关单位", "materiel.unit.name", 100,
						false));
		list.add(new JTableListColumn("单位换算率", "unitConvert", 100, false));
		list.add(new JTableListColumn("十码编号",
				"statCusNameRelation.complex.code", "s.complex.code", 100));// s is
		// StatCusNameRelation
		// 别名
		list.add(new JTableListColumn("十码名称", "statCusNameRelation.cusName",
				"s.cusName", 100));
		list.add(new JTableListColumn("十码规格", "statCusNameRelation.cusSpec",
				"s.cusSpec", 100));
		list.add(new JTableListColumn("十码单位",
				"statCusNameRelation.cusUnit.name", "s.cusUnit.name", 50));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<StatCusNameRelationMt> dataSource = casAction
						.findStatCusNameRelationMt(new Request(CommonVars
								.getCurrUser(), true), materielType, index,
								length, property, value, isLike);
				for (int i = 0; i < dataSource.size(); i++) {
					StatCusNameRelationMt s = (StatCusNameRelationMt) dataSource
							.get(i);
					if (s.getMateriel().getPtNo() == null) {
						continue;
					}
					if (map.get(s.getMateriel().getPtNo()) != null) {
						dataSource.remove(s);
						i--;
					}
				}
				return dataSource;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		DgCommonQueryPage.setSingleResult(isSingle);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 按照物料与报关商品对照表选取物料资料
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getFactoryAndFactualCustomsRalation(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
//				List<TempObject> list2=new ArrayList();
//				if(materielType.equals("0")){
//					System.out.println("进入成品");
//					list2 = casAction
//					.findFactoryAndFactualCustomsRalation(new Request(
//							CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
//							index, length, property, value, isLike);
//					
//				}
//				else{
//				list2 = casAction
//				.findFactoryAndFactualCustomsRalation(new Request(
//						CommonVars.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT,
//						index, length, property, value, isLike);
//				List<TempObject> list3 = casAction
//				.findFactoryAndFactualCustomsRalation(new Request(
//						CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
//						index, length, property, value, isLike);
				List<TempObject> dataSource = casAction
						.findFactoryAndFactualCustomsRalation(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
//				list2.addAll(list3);
//				}
				for (int i = 0; i < dataSource.size(); i++) {
					TempObject s = (TempObject) dataSource.get(i);
					Materiel m = (Materiel) s.getObject();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	 * 自我核查工厂料号查询
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 * hcl
	 */
	public List<TempObject> getWuLiaoProductptNo(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource=new ArrayList<TempObject>();
				List<TempObject> dataSource1=new ArrayList<TempObject>();
				List<TempObject> dataSource2=new ArrayList<TempObject>();
				if(!materielType.equals(MaterielType.BAD_PRODUCT)){
				dataSource = casAction
						.findFactoryAndFactualCustomsRalation(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
				}else{
					dataSource = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
							index, length, property, value, isLike);
					dataSource1 = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
							index, length, property, value, isLike);
					dataSource2 = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT,
							index, length, property, value, isLike);
					dataSource.addAll(dataSource1);
					dataSource.addAll(dataSource2);
				}
				for (int i = 0; i < dataSource.size(); i++) {
					TempObject s = (TempObject) dataSource.get(i);
					Materiel m = (Materiel) s.getObject();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}
			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}
			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};
		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	 * 自我核查工厂料号查询
	 * 得到料件、成品、半成品料号信息
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 * hcl
	 */
	public List<TempObject> getAllWuLiaoProductptNo(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "在产品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource=new ArrayList<TempObject>();
				List<TempObject> dataSource1=new ArrayList<TempObject>();
				List<TempObject> dataSource2=new ArrayList<TempObject>();
					dataSource = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
							index, length, property, value, isLike);
					dataSource1 = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
							index, length, property, value, isLike);
					dataSource2 = casAction
					.findFactoryAndFactualCustomsRalation(new Request(
							CommonVars.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT,
							index, length, property, value, isLike);
					System.out.println("dataSource2="+dataSource2.size());
					dataSource.addAll(dataSource1);
					dataSource.addAll(dataSource2);
				return dataSource;
			}
			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}
			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};
		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	 * 返回原料、半成品、成品信息
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getFactoryMaterialPtno(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findFactoryAndFactualCustomsRalation(new Request(
								CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
								index, length, property, value, isLike);
				
				for (int i = 0; i < list1.size(); i++) {
					TempObject s = (TempObject) list1.get(i);
					Materiel m = (Materiel) s.getObject();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							list1.remove(s);
							i--;
						}
					}
				}
				return list1;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	 * 返回原料、半成品、成品信息
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getAllFactoryAndFactualCustomsRalation(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "在成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findPtNameFromBillDetail(new Request(
								CommonVars.getCurrUser(), true), MaterielType.MATERIEL,
								index, length, property, value, isLike);
				List<TempObject> list2 =casAction					// 获取半成品
					.findPtNameFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT, index,
							length, property, value, isLike);
				List<TempObject> list3 = casAction
				.findPtNameFromBillDetail(new Request(
						CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
						index, length, property, value, isLike);
				System.out.println("list1="+list1.size());
				System.out.println("list2="+list2.size());
				System.out.println("list3="+list3.size());

				list1.addAll(list2);
				list1.addAll(list3);
				
//				for (int i = 0; i < list1.size(); i++) {
//					TempObject s = (TempObject) list1.get(i);
//					Materiel m = (Materiel) s.getObject();
//					if (m.getPtNo() == null) {
//						continue;
//					}
//					if (!isShowAll) {// 过滤
//						if (map.get(m.getPtNo()) != null) {
//							list1.remove(s);
//							i--;
//						}
//					}
//				}
				return list1;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	/**
	 * 查找出对应关系中一对多的料号
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getOneToMoreMaterielInfo(
			boolean isSingle, final String materielType, final boolean isFilt) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findFactoryAndFactualCustomsRalation(new Request(
								CommonVars.getCurrUser(), true), materielType,
								index, length, property, value, isLike);
				if(isFilt){
					List matchList = casAction.findOneToMorePtNo(new Request(
							CommonVars.getCurrUser(), true), materielType);
					for (int i = 0; i < dataSource.size(); i++) {
						TempObject s = (TempObject) dataSource.get(i);
						Materiel m = (Materiel) s.getObject();
						if (m.getPtNo() == null) {
							continue;
						}
						if(!matchList.contains(m.getPtNo())){
							dataSource.remove(s);
							i--;
						}
					}
				}
				return dataSource;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}	

	/**
	 * 返回所有工厂名称资料
	 * 
	 * @param kindCode
	 *            商品种类
	 * @return Materiel
	 */
	public Object getFactoryName(String kindCode) {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List<JTableListColumn> columns = new ArrayList<JTableListColumn>();
		columns.add(new JTableListColumn("BOM编号", "ptVersion", 120));
		columns.add(new JTableListColumn("商品名称", "factoryName", 120));
		columns.add(new JTableListColumn("型号规格", "factorySpec", 120));
		columns.add(new JTableListColumn("单位", "calUnit.name", 60));
		DgCommonQuery.setTableColumns(columns);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		String tempStr = kindCode.trim();
		String str = null;
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		dgCommonQuery.setTitle(str);
		dgCommonQuery.setDataSource((casAction.findFactoryNames(new Request(
				CommonVars.getCurrUser()), kindCode)));
		dgCommonQuery.setSingleResult(true);
		dgCommonQuery.setAlwaysOnTop(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtNameFromBillDetail(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "ptName",
				DataType.STRING, 300));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtNameFromBillDetail(new Request(CommonVars
								.getCurrUser(), true), materielType, index,
								length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	/**
	 * 获得工厂名称(残次品的话：料件+成品+半成品，其他返回其物料类型)
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 * hcl
	 */
	public Object findWuLiaoPtNameFromBillDetail(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "ptName",
				DataType.STRING, 300));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource=new ArrayList<TempObject>();
				List<TempObject> dataSource1=new ArrayList<TempObject>();
				List<TempObject> dataSource2=new ArrayList<TempObject>();
				if(!materielType.equals(MaterielType.BAD_PRODUCT)){//非残次品情况
				 dataSource = casAction
						.findPtNameFromBillDetail(new Request(CommonVars
								.getCurrUser(), true), materielType, index,
								length, property, value, isLike);
				}else{
					dataSource = casAction
					.findPtNameFromBillDetail(new Request(CommonVars  //获取料件
							.getCurrUser(), true), MaterielType.MATERIEL, index,
							length, property, value, isLike);
					dataSource1 = casAction					// 获取成品
					.findPtNameFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.FINISHED_PRODUCT, index,
							length, property, value, isLike);
					dataSource2 = casAction					// 获取半成品
					.findPtNameFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT, index,
							length, property, value, isLike);
					
					dataSource.addAll(dataSource1);//成品＋料件
					dataSource.addAll(dataSource2);//成品＋料件+半成品
				}
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	/**
	 * 获得所有工厂名称(Type类型)
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 * hcl
	 */
	public Object findAllWuLiaoPtNameFromBillDetail(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "ptName",
				DataType.STRING, 300));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource=new ArrayList<TempObject>();
				List<TempObject> dataSource1=new ArrayList<TempObject>();
				List<TempObject> dataSource2=new ArrayList<TempObject>();
					dataSource = casAction
					.findPtNameFromBillDetail(new Request(CommonVars  //获取料件
							.getCurrUser(), true), MaterielType.MATERIEL, index,
							length, property, value, isLike);
					dataSource1 = casAction					// 获取成品
					.findPtNameFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.FINISHED_PRODUCT, index,
							length, property, value, isLike);
					dataSource2 = casAction					// 获取半成品
					.findPtNameFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT, index,
							length, property, value, isLike);
					
					dataSource.addAll(dataSource1);//成品＋料件
					dataSource.addAll(dataSource2);//成品＋料件+半成品
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	/**
	 * 获得所有工厂规格
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 * hcl
	 */
	public Object findAllPtNameFromBillDetail(final String ptName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "ptName",
				DataType.STRING, 300));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource=new ArrayList<TempObject>();
				List<TempObject> dataSource1=new ArrayList<TempObject>();
				List<TempObject> dataSource2=new ArrayList<TempObject>();
					dataSource = casAction
					.findPtSpecFromBillDetail(new Request(CommonVars  //获取料件
							.getCurrUser(), true), MaterielType.MATERIEL, index,
							length, property, value, isLike,ptName);
					dataSource1 = casAction					// 获取成品
					.findPtSpecFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.FINISHED_PRODUCT, index,
							length, property, value, isLike,ptName);
					dataSource2 = casAction					// 获取半成品
					.findPtSpecFromBillDetail(new Request(CommonVars
							.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT, index,
							length, property, value, isLike,ptName);
					
					dataSource.addAll(dataSource1);//成品＋料件
					dataSource.addAll(dataSource2);//成品＋料件+半成品
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	/**
	 * 从发票里找出工厂物料名称
	 * @return
	 */
	public Object findPtNameFromInvoice() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("物料名称", "object", "cuName",
				DataType.STRING, 200));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction.findPtNameFromInvoice(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【物料名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	public Object findBillDetailProductNo(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list = casAction
				.findPtNameFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						materielType, index, length, property, value,
						isLike);
				
			
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsNameFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关编码", "object3",
				"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
		List<TempObject> list = casAction
		.findHsNameFromFactoryAndFactualCustomsRalation(
				new Request(CommonVars.getCurrUser(), true),
				materielType, index, length, property, value,
				isLike);
		return list;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsNameFromTransferFactoryBill(final boolean isImp, final boolean isName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		
		if(isName) {
			list.add(new JTableListColumn("报关编码", "object",
					"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
			list.add(new JTableListColumn("报关名称", "object3",
					"a.statCusNameRelationHsn.cusName", DataType.STRING, 200));
		} else {
			list.add(new JTableListColumn("报关规格", "object1",
					"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 300));
			list.add(new JTableListColumn("报关单位", "object2.name",
					"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		}
		

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
		List<TempObject> list = casAction
		.findHsNameFromTransferFactoryBill(
				new Request(CommonVars.getCurrUser(), true),
				isImp, index, length, property, value,
				isLike);
		return list;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 查询关封里商品名称对应表
	 * 
	 * @param single
	 * @param cusName
	 * @return
	 */
	public Object findHsNameFromCustomsEnvelopCommodityInfo(final boolean isImp, final boolean isName, final String cusName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		if(isName) {
			list.add(new JTableListColumn("报关名称", "object",
					"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		} else {
			list.add(new JTableListColumn("报关规格", "object",
					"a.statCusNameRelationHsn.complex.code", DataType.STRING, 200));
		}

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
		List<TempObject> list = casAction
		.findHsNameFromCustomsEnvelopCommodityInfo(
				new Request(CommonVars.getCurrUser(), true),
				cusName, null, index, length, property, value, isImp,
				isName, isLike);
		return list;
			}
		};
		dgCommonQuery.setTitle("选择【商品名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 查找StatCusNameRelationHsn的名称和规格组成的key
	 * 
	 * @param single
	 * @param cusName
	 * @return
	 */
	public Object findStatCusNameRelationHsnNameAndSpec(final boolean isImp,
			final boolean isName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		String title = "";
		if (isName) {
			title = "选择【商品名称】";
		} else {
			title = "选择【商品规格】";
		}
		list.add(new JTableListColumn("报关名称", "object", 200));
		list.add(new JTableListColumn("报关规格", "object1", 200));
		
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");

				List<TempObject> list = casAction
						.findStatCusNameRelationHsnNameAndSpec(new Request(
								CommonVars.getCurrUser(), true),
								isImp ? MaterielType.MATERIEL
										: MaterielType.FINISHED_PRODUCT);
				return list;
			}
		};
		dgCommonQuery.setTitle(title);
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 * hcl
	 */
	public Object findWuLiaoHsNameFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关编码", "object3",
				"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list =new ArrayList<TempObject>();
				List<TempObject> list1 =new ArrayList<TempObject>();
				List<TempObject> list2 =new ArrayList<TempObject>();
				if(!materielType.equals(MaterielType.BAD_PRODUCT)){
					list = casAction
				.findHsNameFromFactoryAndFactualCustomsRalation(
				new Request(CommonVars.getCurrUser(), true),
				materielType, index, length, property, value,
				isLike);
				}else{
					list = casAction
					.findHsNameFromFactoryAndFactualCustomsRalation(
					new Request(CommonVars.getCurrUser(), true),
					MaterielType.MATERIEL, index, length, property, value,
					isLike);
					list1 = casAction
					.findHsNameFromFactoryAndFactualCustomsRalation(
					new Request(CommonVars.getCurrUser(), true),
					MaterielType.FINISHED_PRODUCT, index, length, property, value,
					isLike);
					list2 = casAction
					.findHsNameFromFactoryAndFactualCustomsRalation(
					new Request(CommonVars.getCurrUser(), true),
					MaterielType.SEMI_FINISHED_PRODUCT, index, length, property, value,
					isLike);
					list.addAll(list1);
					list.addAll(list2);
				}
		return list;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	* 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 * @author 石小凯
	 */
	public Object findHsNameFromStatCusNameRelationHsnmByXiaoK(
			final String materielType) {
		
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object",
				"a.hsSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 =new ArrayList();
				List<TempObject> list2 =new ArrayList();
				List<TempObject> list3 =new ArrayList();
				
				String hsName="";
				if("1".equals(materielType)||"outSource".equals(materielType)){
					list3 = casAction
					.findDetailProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
					list2 = casAction
					.findDetailMaterielHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
					list3.addAll(list2);
				}
				else if("0".equals(materielType)){
					list3 = casAction
					.findDetailProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
				}
				else if("5".equals(materielType)){
					list3 = casAction
					.findDetailRemainProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
				}
//				list3 = casAction
//				.findHalfProductHsSpec(
//						new Request(CommonVars.getCurrUser(), true),
//						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
//						value, isLike);
//				list2 = casAction
//				.findDetailProductHsSpec(
//						new Request(CommonVars.getCurrUser(), true),
//						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
//						value, isLike);
//				list3.addAll(list2);
				return list3;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	public Object findHsNoFromStatCusNameRelationHsnmByXiaoK(
			final String materielType) {
		
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编号", "object",
				"a.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关名称", "object1",
				"a.hsSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 =new ArrayList();
				List<TempObject> list2 =new ArrayList();
				List<TempObject> list3 =new ArrayList();
				
				String hsName="";
				if("1".equals(materielType)||"outSource".equals(materielType)){
					list3 = casAction
					.findDetailProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
					list2 = casAction
					.findDetailMaterielHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
					list3.addAll(list2);
				}
				else if("0".equals(materielType)){
					list3 = casAction
					.findDetailProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
				}
				else if("5".equals(materielType)){
					list3 = casAction
					.findDetailRemainProductHsName(
							new Request(CommonVars.getCurrUser(), true),
							"", hsName, index, length, property,
							value, isLike);
				}
//				list3 = casAction
//				.findHalfProductHsSpec(
//						new Request(CommonVars.getCurrUser(), true),
//						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
//						value, isLike);
//				list2 = casAction
//				.findDetailProductHsSpec(
//						new Request(CommonVars.getCurrUser(), true),
//						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
//						value, isLike);
//				list3.addAll(list2);
				return list3;
			}
		};
		dgCommonQuery.setTitle("选择【报关编号】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 获得对照表的报关编码
	 * hcl
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsComplexFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "object3",
				"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
		List<TempObject> list = casAction
		.findHsNameFromFactoryAndFactualCustomsRalation(
				new Request(CommonVars.getCurrUser(), true),
				materielType, index, length, property, value,
				isLike);
		return list;
			}
		};
		dgCommonQuery.setTitle("选择【报关编码】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 获得所有对照表的报关名称,料件、半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findMertailHs(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "object3",
				"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findHsNameFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, index, length, property, value,
								isLike);
				
			
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 获得所有对照表的报关名称,料件、半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findAllHsNameFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "object3",
				"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findHsNameFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, index, length, property, value,
								isLike);
				List<TempObject> list2 = casAction
				.findHsNameFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.SEMI_FINISHED_PRODUCT, index, length, property, value,
						isLike);
				List<TempObject> list3 = casAction
				.findHsNameFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.FINISHED_PRODUCT, index, length, property, value,
						isLike);
				list1.addAll(list2);
				list1.addAll(list3);
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 获得对照表的报关单位
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsUnitFromStatCusNameRelationHsn(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object1",
				"a.statCusNameRelationHsn.cusUnit", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findHsNameFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, index, length, property, value,
								isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关单位】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 获得对照表的报关规格
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsSpecFromStatCusNameRelationHsn(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findHsSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, hsName, index, length, property,
								value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 获得对照表的报关规格
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findWuLiaoHsSpecFromStatCusNameRelationHsn(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource =new ArrayList<TempObject>();
				if(!materielType.equals(MaterielType.BAD_PRODUCT)){
				dataSource = casAction
						.findHsSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, hsName, index, length, property,
								value, isLike);
				}else{
					dataSource = casAction
					.findHsSpecFromFactoryAndFactualCustomsRalation(
							new Request(CommonVars.getCurrUser(), true),
							MaterielType.MATERIEL, hsName, index, length, property,
							value, isLike);
					if(dataSource==null||dataSource.size()==0)
						dataSource = casAction
						.findHsSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.FINISHED_PRODUCT, hsName, index, length, property,
								value, isLike);
					if(dataSource==null||dataSource.size()==0)
						dataSource = casAction
						.findHsSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.SEMI_FINISHED_PRODUCT, hsName, index, length, property,
								value, isLike);
						
				}
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}


/**
 * 获得对照表的报关名称
 * 
 * @param single
 * @param materielType
 * @return
 * @author 石小凱
 */
public Object findHsSpecFromStatCusNameRelationHsnByXiaok(
		final String materielType, final String hsName) {
	List<JTableListColumn> list = new ArrayList<JTableListColumn>();
	list.add(new JTableListColumn("报关名称", "object",
			"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
	DgCommonQueryPage.setTableColumns(list);
	// DgCommonQueryPage.setLength(100);
	DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
		@Override
		public List getDataSource(int index, int length, String property,
				Object value, boolean isLike) {
			//
			// 分页查询的方法
			//
			CasAction casAction = (CasAction) CommonVars
					.getApplicationContext().getBean("casAction");
			List<TempObject> dataSource = casAction
					.findHsSpecFromFactoryAndFactualCustomsRalation(
							new Request(CommonVars.getCurrUser(), true),
							materielType, hsName, index, length, property,
							value, isLike);
			return dataSource;
		}
	};
	dgCommonQuery.setTitle("选择【报关名称】资料");
	DgCommonQueryPage.setSingleResult(true);
	dgCommonQuery.setVisible(true);
	if (dgCommonQuery.isOk()) {
		return dgCommonQuery.getReturnValue();
	}
	return null;

}
	/**
	 * 获得对照表的报关,料件、半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findAllHsSpecFromStatCusNameRelationHsn(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findHsSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, hsName, index, length, property,
								value, isLike);
				List<TempObject> list2 = casAction
				.findHsSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.SEMI_FINISHED_PRODUCT, hsName, index, length, property,
						value, isLike);
				List<TempObject> list3 = casAction
				.findHsSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.FINISHED_PRODUCT, hsName, index, length, property,
						value, isLike);
				list1.addAll(list2);
				list1.addAll(list3);
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得对照表的报关单位
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsUnitFromStatCusNameRelationHsn(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关单位", "object",
				"a.statCusNameRelationHsn.cusUnit", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findHsUnitFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, hsName, index, length, property,
								value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【报关单位】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 返回返回工厂商品名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtNameFromStatCusNameRelationMt(final String materielType) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂名称", "object",
				"a.materiel.factoryName", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtNameFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, index, length, property, value,
								isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 返回返回工厂商品名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtSpecFromStatCusNameRelationMt(
			final String materielType, final String ptName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂规格", "object",
				"a.materiel.factorySpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = casAction
						.findPtSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								materielType, ptName, index, length, property,
								value, isLike);
				return dataSource;
			}
		};
		dgCommonQuery.setTitle("选择【工厂规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 返回所有工厂商品名称、料件、半成品、成品规格
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findAllPtSpecFromStatCusNameRelationMt(
			 final String ptName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂规格", "object",
				"a.materiel.factorySpec", DataType.STRING, 300));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findPtSpecFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, ptName, index, length, property,
								value, isLike);
				List<TempObject> list2 = casAction
				.findPtSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.FINISHED_PRODUCT, ptName, index, length, property,
						value, isLike);
				List<TempObject> list3 = casAction
				.findPtSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.SEMI_FINISHED_PRODUCT, ptName, index, length, property,
						value, isLike);
				list1.addAll(list2);
				list1.addAll(list3);
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【工厂规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 返回工厂商品名称，料件、半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findAllPtNameFromStatCusNameRelationMt() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂名称", "object",
				"a.materiel.factoryName", DataType.STRING, 200));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findPtNameFromFactoryAndFactualCustomsRalation(
								new Request(CommonVars.getCurrUser(), true),
								MaterielType.MATERIEL, index, length, property, value,
								isLike);
				List<TempObject> list2 = casAction
				.findPtNameFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.SEMI_FINISHED_PRODUCT, index, length, property, value,
						isLike);
				List<TempObject> list3 = casAction
				.findPtNameFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						MaterielType.FINISHED_PRODUCT, index, length, property, value,
						isLike);
				list1.addAll(list2);
				list1.addAll(list3);
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 返回工厂商品名称，料件、半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findAllWorkOrder() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> list1 = casAction
						.findAllWrokOrder(
								new Request(CommonVars.getCurrUser(), true),
								 index, length, property, value,
								isLike);
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 变更物料所对应的报关商品,弹出的此物料所对应的报关商品列表(适用于盘点平衡表)
	 * 
	 * @param isSingle
	 * @param ptNo
	 * @param complex
	 * @return
	 */
	public List<StatCusNameRelationHsn> getStatCusNameRelationHsnForCheckBalance(
			boolean isSingle, final String ptNo) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "statCusNameRelationHsn.complex.code",
				"a.statCusNameRelationHsn.complex.code", 100));
		list.add(new JTableListColumn("报关名称", "statCusNameRelationHsn.cusName",
				"a.statCusNameRelationHsn.cusName", 100));
		list.add(new JTableListColumn("报关规格", "statCusNameRelationHsn.cusmSpec",
				"a.statCusNameRelationHsn.cusSpec", 100));
		list.add(new JTableListColumn("报关单位", "statCusNameRelationHsn.cusUnit.name",
				"a.statCusNameRelationHsn.cusUnit.name", 50));
		list.add(new JTableListColumn("归并序号", "statCusNameRelationHsn.seqNum",
				"a.statCusNameRelationHsn.seqNum", 50));
		list.add(new JTableListColumn("版本号", "statCusNameRelationHsn.version",
				"a.statCusNameRelationHsn.version", 80));
		list.add(new JTableListColumn("手册号", "statCusNameRelationHsn.emsNo",
				"a.statCusNameRelationHsn.emsNo", 120));
		list.add(new JTableListColumn("类别", "statCusNameRelationHsn.projectName", 80, false));
		list.add(new JTableListColumn("开始日期", "statCusNameRelationHsn.emsBeginDate",
				"a.statCusNameRelationHsn.emsBeginDate", 80));
		list.add(new JTableListColumn("结束日期", "statCusNameRelationHsn.emsEndDate",
				"a.statCusNameRelationHsn.emsEndDate", 80));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<FactoryAndFactualCustomsRalation> dataSource = casAction
						.findFactualCustoms(new Request(CommonVars
								.getCurrUser(), true), ptNo, index, length,
								property, value, isLike);
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
				}
				return jToolBar;
			}
		};

		dgCommonQuery.setTitle("选择物料所对应的【报关商品】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	/**
	 * 变更物料所对应的报关商品,弹出的此物料所对应的报关商品列表(适用于盘点平衡表)
	 * 
	 * @param isSingle
	 * @param ptNo
	 * @param complex
	 * @return
	 */
	public List<StatCusNameRelationHsn> getStatCusNameRelationHsn(
			boolean isSingle,final String materielType) {

		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关编码", "complex.code", 100));
		list.add(new JTableListColumn("报关名称", "cusName", 100));
		list.add(new JTableListColumn("报关规格", "cusSpec", 100));
		list.add(new JTableListColumn("报关单位", "cusUnit.name", 50));
		list.add(new JTableListColumn("归并序号", "seqNum", 50));
		list.add(new JTableListColumn("版本号", "version", 80));
		list.add(new JTableListColumn("手册号", "emsNo", 120));
		list.add(new JTableListColumn("类别", "projectName", 80, false));
		list.add(new JTableListColumn("开始日期", "emsBeginDate", 80));
		list.add(new JTableListColumn("结束日期", "emsEndDate", 80));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<StatCusNameRelationHsn> dataSource = casAction.findStatCusNameRelationHsn(new Request(CommonVars.getCurrUser()),
						index, length,
						property, 
						value,
						isLike,materielType);
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
				}
				return jToolBar;
			}
		};

		dgCommonQuery.setTitle("选择物料所对应的【报关商品】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}	

	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findTransFactRecvSendCommName(final boolean isImport,
			final ScmCoc scmCoc, final Date beginDate, final Date endDate) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object1", 100));
		// list.add(new JTableListColumn("报关规格", "object1",
		// "a.statCusNameRelationHsn.cusSpec",DataType.STRING, 100));
		DgCommonQuery.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List<TempObject> dataSource = casAction.findTransFactRecvSendCommName(
				new Request(CommonVars.getCurrUser(), true), isImport, scmCoc,
				beginDate, endDate);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		//去除重复报关名称。
		for (int i = 0; i < dataSource.size() - 1; i++) {
			for (int j = i + 1; j < dataSource.size(); ) {
				if(dataSource.get(i).getObject1()
						.equals(dataSource.get(j).getObject1())) {
					dataSource.remove(j);
				} else {
					j++;
				}
			}
		}
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findTransFactRecvSendCommName(final boolean isImport,
			final ScmCoc scmCoc, final String beginMonth, final String endMonth) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object1", 150));
		list.add(new JTableListColumn("报关规格", "object2",150));
		// "a.statCusNameRelationHsn.cusSpec",DataType.STRING, 100));
		DgCommonQuery.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List<TempObject> dataSource = casAction.findTransFactRecvSendCommName(
				new Request(CommonVars.getCurrUser(), true), isImport, scmCoc,
				beginMonth, endMonth);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 获得对照表的报关名称
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List<EnterpriseMaterial> findEnterpriseMaterial() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("工厂品名", "factoryName", 100));
		list.add(new JTableListColumn("工厂规格", "factorySpec", 100));
		list.add(new JTableListColumn("工厂单位", "calUnit.name", 100));
		list.add(new JTableListColumn("物料类别", "scmCoi.name", 100));
		DgCommonQuery.setTableColumns(list);
//		 DgCommonQueryPage.setLength(100);
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext()
				.getBean("materialManageAction");
		List<String> names = new ArrayList<String>();
		names.add("1");//1
		names.add("2");//2
		List<TempObject> dataSource = materialManageAction.findEnterpriseMaterialByType(names);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	* 获得对照表的报关,半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findHsSpecFromStatCusNameRelationHsnm(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object",
				"a.hsSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				
				List<TempObject> list3 = casAction
				.findHalfProductHsSpec(
						new Request(CommonVars.getCurrUser(), true),
						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
						value, isLike);
				List<TempObject> list2 = casAction
				.findDetailProductHsSpec(
						new Request(CommonVars.getCurrUser(), true),
						"SEMI_FINISHED_PRODUCT", hsName, index, length, property,
						value, isLike);
				list3.addAll(list2);
				return list3;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 返回半成品、成品信息
	 * 
	 * @param isSingle
	 *            是否多选
	 * @param materielType
	 *            物料类型
	 * @param billDetailList
	 *            单据体明细列表（用于数据过滤）
	 * @return 选取的物料资料与单位换算率
	 */
	public List<TempObject> getPTFactoryAndFactualCustomsRalation(
			boolean isSingle, final String materielType, List billDetailList) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		// dataSource.re
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
				DataType.STRING, 100));// a is StatCusNameRelationMt 别名
		list.add(new JTableListColumn("物料名称", "object.factoryName",
				"a.materiel.factoryName", DataType.STRING, 100));
		list.add(new JTableListColumn("规格", "object.factorySpec",
				"a.materiel.factorySpec", DataType.STRING, 120));
		list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
				"a.materiel.calUnit.name", DataType.STRING, 100));
		list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
				DataType.DOUBLE, 100));
		String tempStr = materielType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			str = "半成品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals(MaterielType.REMAIN_MATERIEL)) {
			str = "边角料";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100 + billDetailList.size());
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
			
				List<TempObject> list2 = casAction
				.findFactoryAndFactualCustomsRalation(new Request(
						CommonVars.getCurrUser(), true), MaterielType.SEMI_FINISHED_PRODUCT,
						index, length, property, value, isLike);
				List<TempObject> list3 = casAction
				.findFactoryAndFactualCustomsRalation(new Request(
						CommonVars.getCurrUser(), true), MaterielType.FINISHED_PRODUCT,
						index, length, property, value, isLike);
			
				list2.addAll(list3);
				
				for (int i = 0; i < list2.size(); i++) {
					TempObject s = (TempObject) list2.get(i);
					Materiel m = (Materiel) s.getObject();
					if (m.getPtNo() == null) {
						continue;
					}
					if (!isShowAll) {// 过滤
						if (map.get(m.getPtNo()) != null) {
							list2.remove(s);
							i--;
						}
					}
				}
				return list2;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	/**
	 * 返回工厂商品名称，残次品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findBWorkOrder() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list = casAction.findBWrokOrder(
						new Request(CommonVars.getCurrUser(), true), index, length, property, value,
						isLike);
		
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【制单号】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 返回工厂商品名称，半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findMPWorkOrder() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list = casAction
				.findMPWrokOrder(
						new Request(CommonVars.getCurrUser(), true),index, length, property, value,
						isLike);
		
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【制单号】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 返回半成品制单号
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findMWorkOrder() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list = casAction
				.findMWrokOrder(
						new Request(CommonVars.getCurrUser(), true),index, length, property, value,
						isLike);
		
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【制单号】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 返回工厂商品名称，成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPWorkOrder() {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("制单号", "object",
				"a.productNo", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list = casAction
				.findPWrokOrder(
						new Request(CommonVars.getCurrUser(), true),index, length, property, value,
						isLike);
		
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【制单号】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 返回所有工厂商品名称、半成品、成品规格
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findPtFromStatCusNameRelationMt(
			 final String ptName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("工厂规格", "object",
				"a.ptSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				
				List<TempObject> list2 = casAction
				.findPtSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						"FINISHED_PRODUCT", ptName, index, length, 

property,
						value, isLike);
				List<TempObject> list3 = casAction
				.findPtSpecFromFactoryAndFactualCustomsRalation(
						new Request(CommonVars.getCurrUser(), true),
						"SEMI_FINISHED_PRODUCT", ptName, index, 

length, property,
						value, isLike);
				list2.addAll(list3);
				return list2;
			}
		};
		dgCommonQuery.setTitle("选择【工厂名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	/**
	 * 按照所选合同和类型
	 * 获得报关名称,料件、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findMertailOrFinishedProductHs(final String materielType,final int type,final String[] contracts) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关名称", "object",
				"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
		list.add(new JTableListColumn("报关规格", "object1",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		list.add(new JTableListColumn("报关单位", "object2.name",
				"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasCheckAction casCheckAction = (CasCheckAction) CommonVars
						.getApplicationContext().getBean("casCheckAction");
				List<TempObject> list1 = casCheckAction
						.findMertailOrFinishedProductHs(
								new Request(CommonVars.getCurrUser(), true),
								materielType,type, contracts);
				
			
				return list1;
			}
		};
		dgCommonQuery.setTitle("选择【报关名称】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	* 获得对照表的报关,半成品、成品
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public Object findMertailOrFinishedProductSpecHs(
			final String materielType, final String hsName) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("报关规格", "object",
				"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(100);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasCheckAction casCheckAction = (CasCheckAction) CommonVars
						.getApplicationContext().getBean("casCheckAction");
				
				List<TempObject> list = casCheckAction
				.findMertailOrFinishedProductSpecHs(materielType,hsName);
				return list;
			}
		};
		dgCommonQuery.setTitle("选择【报关规格】资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	/**
	 * 按照  物料与报关商品对照表选取  物料资料  (自我核查模块)
	 * 
	 * @param isSingle 是否多选
	 * @param balanceType 结存类型 
	 * @param billDetailList   单据体明细列表（用于数据过滤）
	 * @param selectedType 选择的类型
	 * @param name 用于查询报关规格时 传入报关名称
	 * @return 选取的物料资料   与单位换算率
	 * @author wss 2010.05.13
	 */
	public List<TempObject> getFactoryAndFactualCustomsRalationForBalance(
			boolean isSingle, final String balanceType, List billDetailList,
			final String selectType) {

		final Map<String, BillDetail> map = new HashMap<String, BillDetail>();// 用于过滤的MAP
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			map.put(billDetail.getPtPart(), billDetail);
		}
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();

		if("ptNo".equals(selectType)){//工厂料号
			list.add(new JTableListColumn("工厂料号", "object.ptNo", "a.materiel.ptNo",
					DataType.STRING, 100));// a is StatCusNameRelationMt 别名
			list.add(new JTableListColumn("物料名称", "object.factoryName",
					"a.materiel.factoryName", DataType.STRING, 100));
			list.add(new JTableListColumn("规格", "object.factorySpec",
					"a.materiel.factorySpec", DataType.STRING, 120));
			list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
					"a.materiel.calUnit.name", DataType.STRING, 100));
			list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
					DataType.DOUBLE, 100));
			
		}else if("ptName".equals(selectType)){//工厂名称
			list.add(new JTableListColumn("工厂名称", "object.factoryName",
					"a.materiel.factoryName", DataType.STRING, 100));
			list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
					DataType.STRING, 100));// a is StatCusNameRelationMt 别名
			list.add(new JTableListColumn("规格", "object.factorySpec",
					"a.materiel.factorySpec", DataType.STRING, 120));
			list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
					"a.materiel.calUnit.name", DataType.STRING, 100));
			list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
					DataType.DOUBLE, 100));

			
		}else if("ptSpec".equals(selectType)){//工厂规格			
			list.add(new JTableListColumn("工厂规格", "object.factorySpec",
					"a.materiel.factorySpec", DataType.STRING, 120));
			list.add(new JTableListColumn("料号", "object.ptNo", "a.materiel.ptNo",
					DataType.STRING, 100));// a is StatCusNameRelationMt 别名
			list.add(new JTableListColumn("物料名称", "object.factoryName",
					"a.materiel.factoryName", DataType.STRING, 100));
			
			list.add(new JTableListColumn("工厂单位", "object.calUnit.name",
					"a.materiel.calUnit.name", DataType.STRING, 100));
			list.add(new JTableListColumn("单位换算率", "object1", "a.unitConvert",
					DataType.DOUBLE, 100));

			
		}else if("hsNo".equals(selectType)){//海关编码
			list.add(new JTableListColumn("报关编码", "object3",
					"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
			list.add(new JTableListColumn("报关名称", "object",
					"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
			list.add(new JTableListColumn("报关规格", "object1",
					"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
			list.add(new JTableListColumn("报关单位", "object2.name",
					"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
			
			
		}else if("hsName".equals(selectType)){//报关名称
			list.add(new JTableListColumn("报关名称", "object",
					"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));
			list.add(new JTableListColumn("报关编码", "object3",
					"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
			list.add(new JTableListColumn("报关规格", "object1",
					"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
			list.add(new JTableListColumn("报关单位", "object2.name",
					"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
			
		}else if("hsSpec".equals(selectType)){//报关规格
			list.add(new JTableListColumn("报关规格", "object1",
					"a.statCusNameRelationHsn.cusSpec", DataType.STRING, 100));
			list.add(new JTableListColumn("报关编码", "object3",
					"a.statCusNameRelationHsn.complex.code", DataType.STRING, 100));
			list.add(new JTableListColumn("报关名称", "object",
					"a.statCusNameRelationHsn.cusName", DataType.STRING, 100));

			list.add(new JTableListColumn("报关单位", "object2.name",
					"a.statCusNameRelationHsn.cusUnit.name", DataType.STRING, 100));
		}
		
		
		String tempStr = balanceType.trim();
		String str = "";
		if (tempStr.equals(MaterielType.MATERIEL)) {
			str = "料件";
		} else if (tempStr.equals(MaterielType.FINISHED_PRODUCT)) {
			str = "成品";
		} else if (tempStr.equals("currentTotal")) {
			str = "在产品";
		} else if (tempStr.equals(MaterielType.MACHINE)) {
			str = "设备";
		} else if (tempStr.equals("outSource")) {
			str = "外发加工";
		} else if (tempStr.equals(MaterielType.BAD_PRODUCT)) {
			str = "残次品";
		}
		DgCommonQueryPage.setTableColumns(list);

		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;

			public JCheckBox jCheckBox = null; // @jve:decl-index=0:visual-constraint="580,304"

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");
				List<TempObject> dataSource = new ArrayList<TempObject>();
				String[] types;
				if(MaterielType.MATERIEL.equals(balanceType)){//料件
					types = new String[]{MaterielType.MATERIEL};
					
				}else if(MaterielType.FINISHED_PRODUCT.equals(balanceType)){//成品
					types = new String[]{MaterielType.FINISHED_PRODUCT,};

				}else if(MaterielType.BAD_PRODUCT.equals(balanceType)){//残次品
//					types = new String[]{MaterielType.BAD_PRODUCT};
					types = new String[]{MaterielType.MATERIEL,
										MaterielType.FINISHED_PRODUCT,
										MaterielType.SEMI_FINISHED_PRODUCT,
										MaterielType.BAD_PRODUCT};

				}else if("currentTotal".equals(balanceType)){//在产品
					
					types = new String[]{MaterielType.MATERIEL,
										MaterielType.FINISHED_PRODUCT,
										MaterielType.SEMI_FINISHED_PRODUCT};
					
				}else if("outSource".equals(balanceType)){//外发加工(只留料件)
					types = new String[]{MaterielType.MATERIEL,
//										MaterielType.FINISHED_PRODUCT,
//										MaterielType.SEMI_FINISHED_PRODUCT,
//										MaterielType.BAD_PRODUCT
										};

				}else if(MaterielType.REMAIN_MATERIEL.equals(balanceType)){//外发加工
					types = new String[]{MaterielType.REMAIN_MATERIEL};
					
				}else{
					types = new String[]{MaterielType.MATERIEL};
				}
				
				for(String type: types){
					if("ptNo".equals(selectType) 
							|| "ptName".equals(selectType) 
							||"ptSpec".equals(selectType)){//工厂料号
						List<TempObject> ls  =  casAction
										.findFactoryAndFactualCustomsRalation(new Request(
												CommonVars.getCurrUser(), true), type,
												index, length, property, value, isLike);
						dataSource.addAll(ls);
					}else if("hsNo".equals(selectType) 
							|| "hsName".equals(selectType)
							|| "hsSpec".equals(selectType)){//海关编码
						List<TempObject> ls = casAction
										.findHsFromFactoryAndFactualCustomsRalation(
												new Request(CommonVars.getCurrUser(), true),
												type, index, length, property, value,isLike);
						dataSource.addAll(ls);
					}
					
					
				}
				if("ptNo".equals(selectType)){
					for (int i = 0; i < dataSource.size(); i++) {
						TempObject s = (TempObject) dataSource.get(i);
						Materiel m = (Materiel) s.getObject();
						if (m.getPtNo() == null) {
							continue;
						}
						if (!isShowAll) {// 过滤
							if (map.get(m.getPtNo()) != null) {
								dataSource.remove(s);
								i--;
							}
						}
					}
				}
				
				return dataSource;
			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox.setEnabled("ptNo".equals(selectType));
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}
		};

		dgCommonQuery.setTitle("选择【" + str + "】资料");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(isSingle);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	
	/**
	 * 返回关封号
	 * @param single
	 * @return
	 * @author wss
	 */
	public Object findEnvelopNoByScmCoc(String billCode,ScmCoc scmCoc) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		
		list.add(new JTableListColumn("结转申请单(关封)号", "object","", DataType.STRING, 200));
		list.add(new JTableListColumn("生效日期", "object1","", DataType.DATE, 150));
		list.add(new JTableListColumn("客户/供应商", "object2","", DataType.STRING, 200));
		
		DgCommonQuery.setTableColumns(list);
		
		CasAction casAction = (CasAction) CommonVars.getApplicationContext().getBean("casAction");
		
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		
		List resultData = casAction.findEnvelopNoByScmCoc(new Request(CommonVars.getCurrUser(),true),billCode,scmCoc);
		
		dgCommonQuery.setDataSource(resultData);
		dgCommonQuery.setTitle("结转申请单(关封)号");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}
	
	

}
