/*
 * Created on 2004-6-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonQuery.checkBoxRenderer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.fecav.action.FecavAction;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonQueryPage {
	private static CommonQueryPage commonQuery = null;

	private CommonQueryPage() {

	}

	public synchronized static CommonQueryPage getInstance() {
		if (commonQuery == null) {
			commonQuery = new CommonQueryPage();
		}
		return commonQuery;
	}

	// /**
	// * 获得分页查询的物料(来自内部归并的过滤)
	// *
	// * @param dataSource
	// * @return
	// */
	// public List getMaterielNotInDzbaInnerMerge(final String materielType,
	// final DzbaInnerMergeHead head) {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(new JTableListColumn("料号(BOM编号)", "ptNo", 120));
	// //list.add(new JTableListColumn("物料编码", "complex.code", 100));
	// list.add(new JTableListColumn("商品名称", "factoryName", 100));
	// list.add(new JTableListColumn("商品规格", "factorySpec", 100));
	// list.add(new JTableListColumn("单位", "calUnit.name", 50));
	// list.add(new JTableListColumn("单价", "ptPrice", 50));
	// list.add(new JTableListColumn("净重", "ptNetWeight", 50));
	// DgCommonQueryPage.setTableColumns(list);
	// // DgCommonQueryPage.setLength(200);
	// DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
	// @Override public List getDataSource(int index, int length,
	// String property, Object value, boolean isLike) {
	// //
	// // 分页查询的方法
	// //
	// DzbaAction dzbaAction = (DzbaAction) CommonVars
	// .getApplicationContext().getBean("dzbaAction");
	// return dzbaAction.findMaterielForDzbaInnerMerge(new Request(
	// CommonVars.getCurrUser(), true), materielType, head,
	// index, length, property, value, isLike);
	// }
	// };
	//
	// dgCommonQuery.setTitle("物料查询");
	// DgCommonQueryPage.setSingleResult(false);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return null;
	// }
	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getMaterielNotInDzscInnerMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("货号", "ptNo", 120));
		// list.add(new JTableListColumn("物料编码", "complex.code", 100));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				DzscInnerMergeAction dzscAction = (DzscInnerMergeAction) CommonVars
						.getApplicationContext()
						.getBean("dzscInnerMergeAction");
				return dzscAction.findMaterielForDzscInnerMerge(new Request(
						CommonVars.getCurrUser(), true), materielType, index,
						length, property, value, isLike);
			}
		};
		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
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
	public List getMaterielByTypeBcs(String title, final String materielType,
			final ImpExpRequestBill head) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code",
				"complex.code", 100));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName",
				"factoryName", 200));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec",
				"factorySpec", 200));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name",
				"calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight",
				"ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public JCheckBox jCheckBox = null;
			public boolean isShowAll = false;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				ContractExeAction contractExeAction = (ContractExeAction) CommonVars
						.getApplicationContext().getBean("contractExeAction");
				if (isShowAll) {
					return contractExeAction.findMaterielByBcsRequestBill(
							new Request(CommonVars.getCurrUser(), true),
							materielType, head, index, length, property, value,
							isLike, false);

				} else {
					CompanyOther other = CommonVars.getOther();
					boolean isFilter = false;
					if(other.getIsFilter() == null ||
							other.getIsFilter() == true){
							isFilter = true;
						}else{
							isFilter =false;
						}
					return contractExeAction.findMaterielByBcsRequestBill(
							new Request(CommonVars.getCurrUser(), true),
							materielType, head, index, length, property, value,
							isLike, isFilter);
				}
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

			/**
			 * 显示
			 */
			@Override
			public void setVisible(boolean b) {
				if (b) {
					pnCommonQueryPage.setInitState();
					doSomethingBeforeVisable(getJTable());
					pnCommonQueryPage.getBtnQuery().setVisible(true);
				}
				super.setVisible(b);
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setVisible(false);
					CompanyOther other = CommonVars.getOther();
					if(other.getIsFilter() == null ||
							other.getIsFilter() == false){
					jCheckBox.setSelected(false);
					}else{
						jCheckBox.setSelected(true);
					}
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
		dgCommonQuery.setTitle(title);
		dgCommonQuery.setSize(820, 457);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	/**
	 * 获得物料与报关对应表的物料对象,而不是像原来来自于物料主档（联胜）
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List getMaterielByTypeBcsLS(String title, final String materielType,
			final ImpExpRequestBill head) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code",
				"complex.code", 100));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName",
				"factoryName", 200));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec",
				"factorySpec", 200));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name",
				"calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight",
				"ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public JCheckBox jCheckBox = null;
			public boolean isShowAll = false;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				ContractExeAction contractExeAction = (ContractExeAction) CommonVars
						.getApplicationContext().getBean("contractExeAction");
				if (isShowAll) {
					return contractExeAction.findMaterielByBcsRequestBillLS(
							new Request(CommonVars.getCurrUser(), true),
							materielType, head, index, length, property, value,
							isLike, false);

				} else {
					return contractExeAction.findMaterielByBcsRequestBillLS(
							new Request(CommonVars.getCurrUser(), true),
							materielType, head, index, length, property, value,
							isLike, true);
				}
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

			/**
			 * 显示
			 */
			@Override
			public void setVisible(boolean b) {
				if (b) {
					pnCommonQueryPage.setInitState();
					doSomethingBeforeVisable(getJTable());
					pnCommonQueryPage.getBtnQuery().setVisible(true);
				}
				super.setVisible(b);
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
		dgCommonQuery.setTitle(title);
		dgCommonQuery.setSize(820, 457);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return getMaterielNotInDzbaInnerMerge
	 */
	public List getMaterielByBcsRequestBill(final String materielType,
			final ImpExpRequestBill head, String title) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(new JTableListColumn("是否备案", "isMemo", "", 50));
		list.add(new JTableListColumn("料号", "materiel.ptNo", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code",
				"complex.code", 100));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName",
				"factoryName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec",
				"factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name",
				"calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight",
				"ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		final DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				ContractExeAction contractExeAction = (ContractExeAction) CommonVars
						.getApplicationContext().getBean("contractExeAction");
				return contractExeAction.findMaterielByBcsRequestBill(
						new Request(CommonVars.getCurrUser(), true),
						materielType, head, index, length, property, value,
						isLike, false);
			}
		};
		// dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// JTable table = dgCommonQuery.getJTable();
		// table.getColumnModel().getColumn(1).setCellRenderer(
		// new TableCheckBoxRender());
		// }
		// });
		dgCommonQuery.setTitle(title);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的提运单号
	 */
	public Object getMotorCode() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("司机本海关编码 ", "complex", 200));
		list.add(new JTableListColumn("代码 ", "code", 200));
		list.add(new JTableListColumn("司机名称", "name", 100));
		list.add(new JTableListColumn("国内车牌", "homeCard", 100));
		DgCommonQueryPage.setTableColumns(list);

		final DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//          
				MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
						.getApplicationContext()
						.getBean("materialManageAction");
				return materialManageAction.findMotorCode(new Request(
						CommonVars.getCurrUser(), true), index, length);
			}
		};
		dgCommonQuery.setTitle("提运单号");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得分页查询的外汇核消单
	 * 
	 * @return
	 */
	public Object getFecavBillNotCustomsDeclaration() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("核销单号 ", "code", 200));
		list.add(new JTableListColumn("领单日期", "innerObtainDate", 100));
		list.add(new JTableListColumn("出口口岸", "impExpCIQ.name", 100));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(50);
		final DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//          
				FecavAction fecavAction = (FecavAction) CommonVars
						.getApplicationContext().getBean("fecavAction");
				return fecavAction.findFecavBillNotCustomsDeclaration(
						new Request(CommonVars.getCurrUser(), true), index,
						length, property, value, isLike);
			}
		};
		dgCommonQuery.setTitle("分页查找外汇核消---内部领单");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getMaterielNotInInnerMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 150));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 200));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				List list = commonBaseCodeAction.findMaterielNotInInnerMerge(
						new Request(CommonVars.getCurrUser(), true),
						materielType, index, length, property, value, isLike);
				return list;
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 
	 * 分页查询来自电子帐申请的归并关系
	 */
	public List getEmsEdiImgbeforeAll() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("料号", "ptNo", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("成品序号",
				"emsEdiMergerVersion.emsEdiMergerBefore.seqNum", 50));
		list.add(new JTableListColumn("成品货号",
				"emsEdiMergerVersion.emsEdiMergerBefore.ptNo", 80));
		list.add(new JTableListColumn("成品名称",
				"emsEdiMergerVersion.emsEdiMergerBefore.name", 100));
		list.add(new JTableListColumn("成品规格",
				"emsEdiMergerVersion.emsEdiMergerBefore.spec", 100));
		list
				.add(new JTableListColumn("版本号", "emsEdiMergerVersion.version",
						50));
		list.add(new JTableListColumn("单耗", "unitWaste", 100));
		list.add(new JTableListColumn("损耗", "waste", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				return commonBaseCodeAction.findEmsEdiImgbefore(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public List getEmsEdiImgBombeforeAll() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("料号", "ptNo", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("单位", "unit.name", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				return commonBaseCodeAction.findEmsEdiBomImgbefore(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getMaterielAll(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				return commonBaseCodeAction.findMaterielAll(new Request(
						CommonVars.getCurrUser(), true), materielType, index,
						length, property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
	
	/**
	 * 获得分页查询的物料--单据中心
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getMaterielAllOfCustomsRalation(final String materielType,final Boolean isFilt) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
						.getApplicationContext()
						.getBean("commonBaseCodeAction");
				return commonBaseCodeAction.findMaterielAllOfCustomsRalation(new Request(
						CommonVars.getCurrUser(), true), materielType, index,
						length, property, value, isLike,isFilt);
			}
		};

		dgCommonQuery.setTitle("物料查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的进口集装箱
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getMaterielFromContainer(final Date beginDate,
			final Date endDate, final Hashtable billhs) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("报关单帐册号", "customs.emsHeadH2k", 100));
		list.add(new JTableListColumn("报关单流水号", "customs.serialNumber", 100));
		list.add(new JTableListColumn("报关单号", "customs.customsDeclarationCode",
				100));
		list.add(new JTableListColumn("集装箱号", "containerCode", 200));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				EncAction encAction = (EncAction) CommonVars
						.getApplicationContext().getBean("encAction");
				return encAction.findCustomsDeclarationContainer(new Request(
						CommonVars.getCurrUser(), true), beginDate, endDate,
						index, length, property, value, isLike, billhs);
			}
		};

		dgCommonQuery.setTitle("集装箱查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getComplex() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findComplex(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
			}
		};

		dgCommonQuery.setTitle("商品编码查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自Dzsc内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getOrderMaterielByDzsc() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("工厂品名", "materiel.factoryName", 150));
		list.add(new JTableListColumn("工厂规格", "materiel.factorySpec", 150));
		list.add(new JTableListColumn("报关名称", "dzscTenInnerMerge.tenPtName",
				150));
		list.add(new JTableListColumn("报关规格", "dzscTenInnerMerge.tenPtSpec",
				100));
		list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name", 80));
		list.add(new JTableListColumn("报关单位", "dzscTenInnerMerge.tenUnit.name",
				80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				OrderCommonAction orderCommonAction = (OrderCommonAction) CommonVars
						.getApplicationContext().getBean("orderCommonAction");
				return orderCommonAction.findDzscInnerMergeData(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike);
			}
		};

		dgCommonQuery.setTitle("手册商品归并");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自Bcs内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public List getOrderMaterielByBcs(final int orderType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("工厂品名", "materiel.factoryName", 150));
		list.add(new JTableListColumn("工厂规格", "materiel.factorySpec", 150));
		list.add(new JTableListColumn("报关名称", "bcsTenInnerMerge.name", 150));
		list.add(new JTableListColumn("报关规格", "bcsTenInnerMerge.spec", 100));
		list.add(new JTableListColumn("工厂单位", "materiel.calUnit.name", 80));
		list.add(new JTableListColumn("报关单位", "bcsTenInnerMerge.comUnit.name",
				80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				OrderCommonAction orderCommonAction = (OrderCommonAction) CommonVars
						.getApplicationContext().getBean("orderCommonAction");
				return orderCommonAction.findBcsInnerMergea(new Request(
						CommonVars.getCurrUser(), true), index, length,
						property, value, isLike, orderType);
			}
		};
		String title = "";
		if (orderType == 1) {
			title="来源于物料与报关对应表的料件";
		}else{
			title="来源于物料与报关对应表的成品";
		}
		dgCommonQuery.setTitle(title);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

}