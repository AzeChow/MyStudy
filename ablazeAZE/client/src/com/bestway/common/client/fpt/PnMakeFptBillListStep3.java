/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.TempFptBillItemForMakeBGD;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeFptBillListStep3 extends JPanelBase {
	private FptManageAction fptManageAction = null;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private List fptItemList = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private String inOutFlag = ""; // @jve:decl-index=0:

	private String sysType = "";// 是否收退货

	private FptBillItem fptItem = null;

	private FptBillItem fptItemReturn = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBillListStep3() {
		super();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		initialize();
	}

	private void initUIComponents() {
//		List allReturnInfoList = new ArrayList();
//		List allReceiveInfoList = new ArrayList();
//		List<FptBillItem> allInfoList = new ArrayList<FptBillItem>();
//		allReceiveInfoList = this.fptManageAction
//				.findFptBillCommodityInfoByHeadItem(
//						new Request(CommonVars.getCurrUser()), this.fptItemList,
//						"2", this.inOutFlag);
//		allReturnInfoList = this.fptManageAction
//				.findFptBillCommodityInfoByHeadItem(
//						new Request(CommonVars.getCurrUser()), this.fptItemList,
//						"3", this.inOutFlag);
//		Set<String> _receiveSet = new HashSet<String>();
//		Set<String> _returnSet = new HashSet<String>();
//		List<FptBillItem> sumReceiveList = new ArrayList<FptBillItem>();
//		List<FptBillItem> sumRetrunList = new ArrayList<FptBillItem>();
//		for (int j = 0; j < allReceiveInfoList.size(); j++) {
//			if (allReceiveInfoList.get(j) instanceof FptBillItem) {
//				FptBillItem t = (FptBillItem) allReceiveInfoList.get(j);
//				String keyReceive = t.getFptBillHead().getAppNo() + "|"
//						+ t.getTrGno();
//				_receiveSet.add(keyReceive);
//			}
//
//		}
//
//		for (int j = 0; j < allReturnInfoList.size(); j++) {
//			if (allReturnInfoList.get(j) instanceof FptBillItem) {
//				FptBillItem t = (FptBillItem) allReturnInfoList.get(j);
//				String keyReceive = t.getFptBillHead().getAppNo() + "|"
//						+ t.getTrGno();
//				_returnSet.add(keyReceive);
//			}
//		}
//
//		for (Iterator it = _receiveSet.iterator(); it.hasNext();) {
//			String key = (String) it.next();
//			Double receiveqty = 0.0;
//			Double receiveTradeQty = 0.0;
//			// System.out.println("key:"+key);
//			int k = -1;
//			for (int i = 0; i < allReceiveInfoList.size(); i++) {
//				fptItem = (FptBillItem) allReceiveInfoList.get(i);
//				String tmpKey = fptItem.getFptBillHead().getAppNo() + "|"
//						+ fptItem.getTrGno();
//				// System.out.println("tmpKey:"+tmpKey);
//				if (key.equals(tmpKey)) {
//					k = i;
//					receiveqty += fptItem.getQty();
//					// System.out.println("收货："+receiveqty);
//					receiveTradeQty += fptItem.getTradeQty();
//				}
//				if (i == allReceiveInfoList.size() - 1 && k > -1) {
//					fptItem = (FptBillItem) allReceiveInfoList.get(k);
//					fptItem.setQty(receiveqty);
//					fptItem.setTradeQty(receiveTradeQty);
//					sumReceiveList.add(fptItem);
//					// System.out.println("最终："+fptItem.getQty());
//				}
//			}
//
//		}
//		Double returnTradeQty = 0.0;
//		Double returnqty = 0.0;
//
//		for (Iterator it = _returnSet.iterator(); it.hasNext();) {
//			String key = (String) it.next();
//			// System.out.println("退key:"+key);
//			int k = -1;
//			for (int i = 0; i < allReturnInfoList.size(); i++) {
//				fptItemReturn = (FptBillItem) allReturnInfoList.get(i);
//				String tmpKey = fptItemReturn.getFptBillHead().getAppNo() + "|"
//						+ fptItemReturn.getTrGno();
//				// System.out.println("   tmpKey:"+tmpKey);
//				if (key.equals(tmpKey)) {
//					k = i;
//					returnqty += fptItemReturn.getQty();
//					// System.out.println("申请退货："+returnqty);
//					returnTradeQty += fptItemReturn.getTradeQty();
//					// System.out.println("实践退货："+returnTradeQty);
//				}
//				if (i == allReturnInfoList.size() - 1 && k > -1) {
//					fptItemReturn = (FptBillItem) allReturnInfoList.get(k);
//					fptItemReturn.setQty(returnqty);
//					fptItemReturn.setTradeQty(returnTradeQty);
//					sumRetrunList.add(fptItemReturn);
//					// System.out.println("最终退货："+fptItemReturn.getQty());
//				}
//			}
//			returnTradeQty = 0.0;
//			returnqty = 0.0;
//		}
//		// 收发货-退货数量 以收发货为准
//		for (int i = 0; i < sumReceiveList.size(); i++) {
//			fptItem = sumReceiveList.get(i);
//			String key1 = fptItem.getFptBillHead().getAppNo() + "|"
//					+ fptItem.getTrGno();
//			System.out.println("key1:" + key1);
//			for (int j = 0; j < sumRetrunList.size(); j++) {
//				fptItemReturn = sumRetrunList.get(j);
//				String key2 = fptItemReturn.getFptBillHead().getAppNo() + "|"
//						+ fptItemReturn.getTrGno();
//				System.out.println("    key2:" + key2);
//				if (key1.equals(key2)) {
//					Double qty = 0.0;
//					Double tradeQty = 0.0;
//					fptItem = sumReceiveList.get(i);
//					qty = fptItem.getQty() - fptItemReturn.getQty();
//					tradeQty = fptItem.getTradeQty()
//							- fptItemReturn.getTradeQty();
//					System.out.println("qty:" + fptItem.getQty() + "  相差："
//							+ qty);
//					fptItem.setQty(qty);
//					fptItem.setTradeQty(tradeQty);
//					break;
//				} else {
//					allInfoList.add(fptItemReturn);
//				}
//			}
//			allInfoList.add(fptItem);
//		}
//
//		initTable(removeDuplicateWithOrder(allInfoList));
        List plusItemList = new ArrayList();//加数量
        List minusItemList = new ArrayList();//减数量
        Map<String, TempFptBillItemForMakeBGD> itemMap = new HashMap();

        for (int i = 0; i < fptItemList.size(); i++) {
            FptBillItem fptBillItem = (FptBillItem) fptItemList.get(i);
//            if(FptInOutFlag.OUT.equals(this.inOutFlag)){
            if (FptBusinessType.FPT_BILL.equals(fptBillItem.getFptBillHead().getSysType())) {
                plusItemList.add(fptBillItem);
            } else if (FptBusinessType.FPT_BILL_BACK.equals(fptBillItem.getFptBillHead().getSysType())) {
                minusItemList.add(fptBillItem);
            }
//            }else if(FptInOutFlag.IN.equals(this.inOutFlag)){
//                if(FptBusinessType.FPT_BILL.equals(fptBillItem.getFptBillHead().getSysType())){
//                    plusItemList.add(fptBillItem);
//                }else if(FptBusinessType.FPT_BILL_BACK.equals(fptBillItem.getFptBillHead().getSysType())){
//                    minusItemList.add(fptBillItem);
//                }
//            }
        }
        for (int i = 0; i < plusItemList.size(); i++) {
            FptBillItem fptBillItem = (FptBillItem) plusItemList.get(i);
            String key = fptBillItem.getFptBillHead().getAppNo() + "|" + fptBillItem.getTrGno();
            TempFptBillItemForMakeBGD tempItem = itemMap.get(key);
            if (tempItem == null) {
                tempItem = new TempFptBillItemForMakeBGD();
                try {
                    PropertyUtils.copyProperties(tempItem, fptBillItem);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                itemMap.put(key, tempItem);
            } else {                
                tempItem.setQty(CommonVars.getDoubleExceptNull(tempItem.getQty()) + 
                		CommonVars.getDoubleExceptNull(fptBillItem.getQty()));
                tempItem.setTradeQty(CommonVars.getDoubleExceptNull(tempItem.getTradeQty()) +
                		CommonVars.getDoubleExceptNull(fptBillItem.getTradeQty()));
            }
            tempItem.getFptBillItemSet().add(fptBillItem);
        }
        for (int i = 0; i < minusItemList.size(); i++) {
            FptBillItem fptBillItem = (FptBillItem) minusItemList.get(i);
            String key = fptBillItem.getFptBillHead().getAppNo() + "|" + fptBillItem.getTrGno();
            TempFptBillItemForMakeBGD tempItem = itemMap.get(key);
            if (tempItem == null) {
                continue;
            } else {
                tempItem.setQty(tempItem.getQty() - fptBillItem.getQty());
                tempItem.setTradeQty(tempItem.getTradeQty() - fptBillItem.getTradeQty());
                tempItem.getFptBillItemSet().add(fptBillItem);
            }
        }
        List<TempFptBillItemForMakeBGD> list = new ArrayList<TempFptBillItemForMakeBGD>();
        list.addAll(itemMap.values());
        //HH 2013.11.5 合并后删除 退货单数据 不显示在table 中。 3：退货单
        for (TempFptBillItemForMakeBGD obj : list) {
			if("3".equals(obj.getFptBillHead().getSysType()))
			{
				list.remove(obj);
			}
		}
        
        initTable(list);
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUIComponents();
		}
		super.setVisible(isFlag);
	}

	public List removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
		return list;
	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getCommodityList() {
//		if (this.tableModel == null) {
//			return null;
//		}
//		List list = tableModel.getList();
//		List newList = new ArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i) instanceof FptBillItem) {
//				FptBillItem temp = (FptBillItem) list.get(i);
//				if (temp.getIsSelected().booleanValue() == true) {
//					newList.add(temp);
//				}
//			}
//		}
//		return newList;
        if (this.tableModel == null) {
            return null;
        }
        List list = tableModel.getList();
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempFptBillItemForMakeBGD) {
                TempFptBillItemForMakeBGD temp = (TempFptBillItemForMakeBGD) list.get(i);
                if (temp.getIsSelected().booleanValue() == true) {
                    newList.add(temp);
                }
            }
        }
        return newList;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(654, 352);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getFptItemList() {
		return fptItemList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setFptItemList(List parentList) {
		this.fptItemList = parentList;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof FptBillItem) {
				FptBillItem temp = (FptBillItem) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	@SuppressWarnings("serial")
	private void initTable(@SuppressWarnings("rawtypes") List allcommondityInfoList) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public List InitColumns() {
				List list = new Vector();
				// list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("序号", "listNo", 20));
				list.add(addColumn("单据类型", "fptBillHead.sysType", 80));
				if (FptInOutFlag.IN.equals(inOutFlag)) {
					list.add(addColumn("手册/账册号", "inEmsNo", 90));
					list.add(addColumn("转入", "billSort", 60));
				} else {
					list.add(addColumn("转出", "billSort", 60));
				}
				list.add(addColumn("项号", "trGno", 50));
				list.add(addColumn("商品编码", "complex.code", 120));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("商品规格", "commSpec", 60));
				list.add(addColumn("交易单位", "tradeUnit.name", 60));
				list.add(addColumn("交易数量", "tradeQty", 60));
				list.add(addColumn("计量单位", "unit.name", 60));
				list.add(addColumn("申报数量", "qty", 60));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, allcommondityInfoList,
				jTableListModelAdapter);

		int conBillSort = 0;
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			conBillSort = 4;
		} else {
			conBillSort = 3;
		}

		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = FptBusinessType
								.getFptBusinessTypeDesc(value.toString());
						return returnValue;
					}
				});
		
		/**
		 * 进出口标志中的进口标志 0 出口1 进口
		 * 
		 * 备注 :发货与收退货是0 ;收货与发退货是1
		 */
		jTable.getColumnModel().getColumn(conBillSort).setCellRenderer(
				new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "0".equals(value) ? "出口":"进口";//单据类型标志 收发货单 2 退货单 3
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(1);
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
		}
		return jPanel;
	}

	public String getIsImport() {
		return inOutFlag;
	}

	public void setIsImport(String isImport) {
		this.inOutFlag = isImport;
	}

	public boolean vaildateData(String cm) {
		return fptManageAction.checkmakeFptToBgdEmsH2kBill(cm,
				tableModel.getList());
	}
}
