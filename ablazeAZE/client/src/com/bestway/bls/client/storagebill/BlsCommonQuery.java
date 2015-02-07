package com.bestway.bls.client.storagebill;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JToolBar;

import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.action.EntranceMessageAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

public class BlsCommonQuery {

	private static BlsCommonQuery blsCommonQuery = null;
	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
	.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:
	private BlsCommonQuery() {

	}
	public synchronized static BlsCommonQuery getInstance() {
		if (blsCommonQuery == null) {
			blsCommonQuery = new BlsCommonQuery();
		}
		return blsCommonQuery;
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
				"factoryName", 100));
		list.add(new JTableListColumn("型号规格", "materiel.factorySpec",
				"factorySpec", 100));
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
					return blsInOutStockBillAction.findMateriel(new Request(CommonVars
							.getCurrUser()));

				} else {
					return contractExeAction.findMaterielByBcsRequestBill(
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
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}
	
	
	public Object getMateriel(Object object) {
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// dgCommonQuery
		// .setDataSource(materialManageAction
		// .findScmManufacturer(new Request(CommonVars
		// .getCurrUser(), true)));
		dgCommonQuery.setDataSource(blsInOutStockBillAction.findMateriel(new Request(CommonVars
						.getCurrUser())));
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
	
	public Object getMateriel() {
		return getMateriel(null);
	}
	
	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getMaterielNotInBlsInnerMerge() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 50));
		list.add(new JTableListColumn("净重", "ptNetWeight", 50));
		list.add(new JTableListColumn("毛重", "ptOutWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				BlsInnerMergeAction blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("blsInnerMergeAction");
				return blsInnerMergeAction.findMaterielForBlsInnerMerge(
						new Request(CommonVars.getCurrUser(), true),
						 index, length, property, value, isLike);
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
	 * 获得车次对象
	 */
	public Object getDelivery() {
		List list = new Vector();
		list.add(new JTableListColumn("企业编码", "tradeCo.code", 80));
		list.add(new JTableListColumn("业务类型", "operType", 80));
		list.add(new JTableListColumn("车次号", "deliveryNo", 100));
		list.add(new JTableListColumn("车牌号", "vehicleLicense", 100));
		list.add(new JTableListColumn("承运单位名称", "carrierCode", 100));
		list.add(new JTableListColumn("仓单数", "billCount", 50));
		list.add(new JTableListColumn("预计到达日期", "schedularArrivalDate", 100));
		list.add(new JTableListColumn("该车载货物件数", "packNo", 50));
		list.add(new JTableListColumn("毛重", "grossWeight", 50));
		list.add(new JTableListColumn("净重", "netWeight", 50));
		list.add(new JTableListColumn("第1个集装箱号", "containerNo1", 100));
		list.add(new JTableListColumn("第2个集装箱号", "containerNo2", 100));
		list.add(new JTableListColumn("第1关锁号", "sealNo1", 100));
		list.add(new JTableListColumn("第2关锁号", "sealNo2", 100));
		list.add(new JTableListColumn("修改标志", "modifyMark", 50));
		list.add(new JTableListColumn("报文发送时间", "messTimeStamp", 100));
		list.add(new JTableListColumn("申报状态", "declareState", 50));
		DgCommonQuery.setTableColumns(list);
		EntranceMessageAction entranceMessageAction = (EntranceMessageAction) CommonVars
				.getApplicationContext().getBean("entranceMessageAction");
		List dataSource = entranceMessageAction.findProcessDelivery(new Request(
				CommonVars.getCurrUser()));
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("车次查询");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
