/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.DgMakeApplyToCustoms;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeBcsCustomsDeclaration3 extends JPanelBase {
	private PnMakeBcsCustomsDeclaration3 pnMakeCustomsEnvelopBill2 = null;
	private JTableListModel tableModel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private List parentList = null;
	// private List colorList = null;
	private JButton btnSelectAll = null;
	private JButton btnNotSelectAll = null;
	private ContractExeAction contractExeAction = null;
	private int materielProductType = -1;
	// private Contract contract = null; // @jve:decl-index=0:
	private Map<String, JComboBox> map = new HashMap<String, JComboBox>(); // @jve:decl-index=0:
	private boolean isProduct = false;
	// private boolean isAutoSelectContract = false;
	private JPanel jPanel = null;
	CompanyOther other = CommonVars.getOther();
	/**
	 * This is the default constructor
	 */
	public PnMakeBcsCustomsDeclaration3() {
		contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			isProduct = this.materielProductType == Integer
					.parseInt(MaterielType.FINISHED_PRODUCT);
			initTable();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(630, 248);
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeBcsCustomsDeclaration3 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeBcsCustomsDeclaration3 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setParentList(List parentList) {
		this.parentList = parentList;
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
			jScrollPane.setBounds(2, 39, 630, 277);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setBounds(new Rectangle(478, 6, 60, 22));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setBounds(new Rectangle(547, 6, 60, 21));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
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
			if (list.get(i) instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
						.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		List list = new ArrayList();
		// if (this.isAutoSelectContract) {
		// list = this.contractExeAction
		// .findTempBcsImpExpCommodityInfoByParent(new Request(
		// CommonVars.getCurrUser()), null, null, false,
		// this.parentList, false);
		// } else {
		// list = this.contractExeAction
		// .findTempBcsImpExpCommodityInfoByParent(new Request(
		// CommonVars.getCurrUser()), contract.getEmsNo(),
		// contract.getCorrEmsNo(), contract
		// .getIsContractEms() == null ? false
		// : contract.getIsContractEms(),
		// this.parentList, false);
		list = this.contractExeAction
				.findTempImpExpRequestBillByImpExpTypeToATC(new Request(
						CommonVars.getCurrUser()), parentList);
		// }

		final boolean isProduct = this.isProduct;
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 50));//1
				list.add(addColumn("单据号","impExpCommodityInfo.impExpRequestBill.billNo", 90));
				list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo",90));
				list.add(addColumn("名称","impExpCommodityInfo.materiel.factoryName", 120));
				list.add(addColumn("规格型号","impExpCommodityInfo.materiel.factorySpec", 120));
				list.add(addColumn("单位","impExpCommodityInfo.materiel.calUnit.name", 50));
				list.add(addColumn("数量", "impExpCommodityInfo.quantity", 60));
				list.add(addColumn("毛重", "impExpCommodityInfo.grossWeight",60));
				list.add(addColumn("净重", "impExpCommodityInfo.netWeight", 60));
				list.add(addColumn("件数", "impExpCommodityInfo.piece", 60));
				list.add(addColumn("币制", "impExpCommodityInfo.currency.name",100));
				list.add(addColumn("制单号", "impExpCommodityInfo.makeBillNo",100));
				list.add(addColumn("箱号", "impExpCommodityInfo.boxNo",100));
				list.add(addColumn("表体备注", "impExpCommodityInfo.extendMemo",150));
				list.add(addColumn("商品编码", "impExpCommodityInfo.complexcode",100));
				list.add(addColumn("扩展备注1", "impExpCommodityInfo.extendMemo1",100));
				list.add(addColumn("扩展备注2", "impExpCommodityInfo.extendMemo2",100));
				list.add(addColumn("仓库名称", "impExpCommodityInfo.impExpRequestBill.wareSet.name",100));
				list.add(addColumn("生效日期","impExpCommodityInfo.impExpRequestBill.beginAvailability", 70));
				list.add(addColumn("客户/供应商名称", "impExpCommodityInfo.impExpRequestBill.scmCoc.name",150));
				list.add(addColumn("集装箱号码", "impExpCommodityInfo.impExpRequestBill.containerCode", 100));
				list.add(addColumn("运输工具", "impExpCommodityInfo.impExpRequestBill.conveyance", 100));
				list.add(addColumn("运输方式", "impExpCommodityInfo.impExpRequestBill.transfMode.name", 80));
				list.add(addColumn("进出口岸", "impExpCommodityInfo.impExpRequestBill.iePort.name", 60));
				list.add(addColumn("表头备注", "impExpCommodityInfo.impExpRequestBill.memos", 150));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		// jTableListModelAdapter.setEditableColumn(6);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		//
		// 初始化所有的表中的编辑的ComboBox对象
		//
		// for (int i = 0; i < list.size(); i++) {
		// TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
		// .get(i);
		// JComboBox cbb = new JComboBox();
		// //
		// // 用进出口明细做Id
		// //
		// map.put(temp.getImpExpCommodityInfo().getId(), cbb);
		// }
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		// jTable.getColumnModel().getColumn(10).setCellRenderer(
		// new JComboBoxRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), isProduct, map));
		// jTable.getColumnModel().getColumn(6).setCellEditor(
		// new JComboBoxEditor(new JComboBox(), isProduct, map));
		//数量
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if(other != null){
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), other.getCustomAmountNum()));
							}
						} else {
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), 5));
						}
						return this;
					}
				});
	}

	/**
	 * render table JchcckBox 列
	 */
	class JComboBoxRenderer extends DefaultTableCellRenderer {

		public JComboBoxRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JComboBox jComboBox = new JComboBox();
			jComboBox.addItem(value);
			jComboBox.setSelectedItem(value);
			jComboBox.setBackground(table.getBackground());
			if (isSelected) {
				jComboBox.setForeground(table.getSelectionForeground());
				jComboBox.setBackground(table.getSelectionBackground());
			} else {
				jComboBox.setForeground(table.getForeground());
				jComboBox.setBackground(table.getBackground());
			}
			return jComboBox;
		}
	}

	// /**
	// * 编辑列
	// */
	// class JComboBoxEditor extends DefaultCellEditor implements ItemListener {
	//
	// private JTable table = null;
	// private boolean isProduct = true;
	// Map<String, JComboBox> map = new HashMap<String, JComboBox>();
	// JComboBox jComboBox = null;
	//
	// public JComboBoxEditor(JComboBox cbb, boolean isProduct,
	// Map<String, JComboBox> map) {
	// super(cbb);
	// this.isProduct = isProduct;
	// this.map = map;
	// }
	//
	// public Component getTableCellEditorComponent(JTable table,
	// Object value, boolean isSelected, int row, int column) {
	// table.setRowSelectionInterval(row, row);
	// JTableListModel tableModel = (JTableListModel) table.getModel();
	// Object obj = tableModel.getCurrentRow();
	// String key = "";
	// if (obj instanceof TempBcsImpExpCommodityInfo) {
	// TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) obj;
	// key = temp.getImpExpCommodityInfo().getId();
	// }
	// jComboBox = map.get(key);
	// if (jComboBox == null) {
	// return null;
	// }
	// jComboBox.setBackground(table.getBackground());
	// if (isSelected) {
	// jComboBox.setForeground(table.getSelectionForeground());
	// jComboBox.setBackground(table.getSelectionBackground());
	// } else {
	// jComboBox.setForeground(table.getForeground());
	// jComboBox.setBackground(table.getBackground());
	// }
	// jComboBox.addItemListener(this);
	// this.table = table;
	// return jComboBox;
	// }
	//
	// public Object getCellEditorValue() {
	// jComboBox.removeItemListener(this);
	// return jComboBox;
	// }
	//
	// public void itemStateChanged(java.awt.event.ItemEvent e) {
	// JTableListModel tableModel = (JTableListModel) this.table
	// .getModel();
	// Object obj = tableModel.getCurrentRow();
	// if (obj instanceof TempBcsImpExpCommodityInfo) {
	// TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) obj;
	// JComboBox cbb = map.get(temp.getImpExpCommodityInfo().getId());
	// if (isProduct) {
	// temp.setContractExg((ContractExg) cbb.getSelectedItem());
	// } else {
	// temp.setContractImg((ContractImg) cbb.getSelectedItem());
	// }
	// tableModel.updateRow(obj);
	// }
	// fireEditingStopped();
	// }
	// }

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;
		private boolean isProduct = true;
		Map<String, JComboBox> map = new HashMap<String, JComboBox>();

		public CheckBoxEditor(JCheckBox checkBox, boolean isProduct,
				Map<String, JComboBox> map) {
			super(checkBox);
			this.isProduct = isProduct;
			this.map = map;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				JComboBox cbb = map.get(temp.getImpExpCommodityInfo().getId());
				//
				// 只能第一次进行查询
				//
				// if (cbb != null && cbb.getItemCount() <= 0) {
				// List list = null;
				// if (isProduct) {
				// // 初始化cbb方法 (成品)
				// list = contractExeAction.findContractExgByPtNo(
				// new Request(CommonVars.getCurrUser(), true),
				// temp.getImpExpCommodityInfo().getMateriel()
				// .getPtNo(), contract.getId());
				// cbb.setRenderer(new MultiColumnTextListCellRenderer());
				// } else {
				// // 初始化cbb方法 (料件)
				// list = contractExeAction.findContractImgByPtNo(
				// new Request(CommonVars.getCurrUser(), true),
				// temp.getImpExpCommodityInfo().getMateriel()
				// .getPtNo(), contract.getId());
				// cbb.setRenderer(new MultiColumnTextListCellRenderer());
				// }
				// cbb.setModel(new DefaultComboBoxModel(list.toArray()));
				// cbb.setUI(new CustomBaseComboBoxUI(280));
				// cbb.setSelectedItem(null);
				// }
				tableModel.updateRow(obj);
				jTable.getColumnModel().getColumn(1).setCellRenderer(
						new TableCheckBoxRender());
				jTable.getColumnModel().getColumn(1).setCellEditor(
						new CheckBoxEditor(new JCheckBox(), isProduct, map));
			}
			fireEditingStopped();
		}
	}

	/**
	 * 多列文本数据呈现 //
	 */
	// class MultiColumnTextListCellRenderer extends DefaultListCellRenderer {
	// private JLabel lbCode = new JLabel();
	//
	// private JLabel lbName = new JLabel();
	//
	// private JLabel lbSrtUsing = new JLabel();
	//
	// private JLabel lbSrtWeight = new JLabel();
	//
	// private JLabel lbMemo = new JLabel();
	//
	// public MultiColumnTextListCellRenderer() {
	// lbCode.setBounds(0, 0, 50, 20);
	// this.add(lbCode);
	// lbName.setBounds(50, 0, 80, 20);
	// this.add(lbName);
	// lbSrtUsing.setBounds(130, 0, 100, 20);
	// this.add(lbSrtUsing);
	// lbSrtWeight.setBounds(230, 0, 50, 20);
	// this.add(lbSrtWeight);
	// lbMemo.setBounds(280, 0, 80, 20);
	// this.add(lbMemo);
	// }
	//
	// public Component getListCellRendererComponent(JList list, Object value,
	// int index, boolean isSelected, boolean cellHasFocus) {
	//
	// super.getListCellRendererComponent(list, value, index, isSelected,
	// cellHasFocus);
	// if (value != null) {
	// if (value instanceof ContractExg) {
	// ContractExg obj = (ContractExg) value;
	// this.lbCode.setText(obj.getSeqNum() == null ? "" : String
	// .valueOf(obj.getSeqNum()));
	// this.lbName.setText(obj.getComplex().getCode());
	// this.lbSrtUsing.setText(obj.getName());
	// this.lbSrtWeight.setText(obj.getUnit() == null ? "" : obj
	// .getUnit().getName());
	// // this.lbMemo.setText(obj.getBracketEnglishName());
	// } else if (value instanceof ContractImg) {
	// // DzbaEmsImgBill obj = (DzbaEmsImgBill) value;
	// ContractImg obj = (ContractImg) value;
	// this.lbCode.setText(obj.getSeqNum() == null ? "" : String
	// .valueOf(obj.getSeqNum()));
	// this.lbName.setText(obj.getComplex().getCode());
	// this.lbSrtUsing.setText(obj.getName());
	// this.lbSrtWeight.setText(obj.getUnit() == null ? "" : obj
	// .getUnit().getName());
	// // this.lbMemo.setText();
	// }
	// } else {
	// this.lbCode.setText("");
	// this.lbName.setText("");
	// this.lbSrtUsing.setText("");
	// this.lbSrtWeight.setText("");
	// }
	// this.setText("                     ");
	// return this;
	// }
	// }
	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getCommodityList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
						.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
//	public List getCommodityList1() {
//		if (this.tableModel == null) {
//			return null;
//		}
//		List newList = new ArrayList();
//		List tableSelectList = new ArrayList();
//		List list = tableModel.getList();
//		// 获取所勾选的料件
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i) instanceof TempBcsImpExpCommodityInfo) {
//				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
//						.get(i);
//				if (temp.getIsSelected()) {
//					tableSelectList.add(temp);
//				}
//			}
//		}
//		if (tableSelectList.size() == 0) {
//			return new ArrayList();
//		}

		// // 根据所选料件，查找有没合同满足条件
		// if (this.isAutoSelectContract) {
		// int tpye = ImpExpFlag.IMPORT;
		// if (isProduct) {
		// tpye = ImpExpFlag.EXPORT;
		// }
		// List glist = this.contractExeAction.getRContract(new Request(
		// CommonVars.getCurrUser()), tableSelectList, tpye);
		// if (glist.size() == 0) {
		// JOptionPane.showMessageDialog(this, "最后结果，没有合同满足条件！", "提示！",
		// JOptionPane.INFORMATION_MESSAGE);
		// return new ArrayList();
		// }
		// contract = (Contract) glist.get(0);
		// }
		// 根据合同获取料件备案信息
//		List tempBcsImpExpCommodityInfo = this.contractExeAction
//				.findTempBcsImpExpCommodityInfoByParent(new Request(CommonVars
//						.getCurrUser()), contract.getEmsNo(), contract
//						.getCorrEmsNo(),
//						contract.getIsContractEms() == null ? false : contract
//								.getIsContractEms(), tableSelectList, true);
//		for (int i = 0; i < tempBcsImpExpCommodityInfo.size(); i++) {
//			TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) tempBcsImpExpCommodityInfo
//					.get(i);
//			newList.add(temp);
//		}
//		return newList;
//	}

	// private boolean checkTableSelect(List list) {
	// if (list.size() == 0) {
	// JOptionPane.showMessageDialog(this, "请选择商品信息！", "提示！",
	// JOptionPane.INFORMATION_MESSAGE);
	// return false;
	// }
	// return true;
	// }
	//
	// /**
	// * 设置无效数据着色
	// */
	// private void setValidateDataColor(List checkList) {
	// this.colorList = checkList;
	// for (int i = 2; i < this.tableModel.getColumnCount(); i++) {
	// jTable.getColumnModel().getColumn(i).setCellRenderer(
	// new ColorTableCellRenderer());
	// }
	// jTable.repaint();
	// jTable.validate();
	// }

	// /**
	// * render table color row
	// */
	// class ColorTableCellRenderer extends DefaultTableCellRenderer {
	// public Component getTableCellRendererComponent(JTable table,
	// Object value, boolean isSelected, boolean hasFocus, int row,
	// int column) {
	// Component c = super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// if (checkValue(table, row, column)) {
	// c.setBackground(Color.BLUE);
	// c.setForeground(Color.WHITE);
	// } else {
	// if (isSelected) {
	// c.setForeground(table.getSelectionForeground());
	// c.setBackground(table.getSelectionBackground());
	// } else {
	// c.setForeground(table.getForeground());
	// c.setBackground(table.getBackground());
	// }
	// }
	// return c;
	// }
	// }
	//
	// private boolean checkValue(JTable table, int row, int column) {
	// if (colorList == null) {
	// return false;
	// }
	// TempBcsImpExpCommodityInfo data = (TempBcsImpExpCommodityInfo) tableModel
	// .getObjectByRow(row);
	// for (int i = 0; i < colorList.size(); i++) {
	// TempBcsImpExpCommodityInfo c = (TempBcsImpExpCommodityInfo) colorList
	// .get(i);
	// if (data.getImpExpCommodityInfo().getId().equals(
	// c.getImpExpCommodityInfo().getId()) == true) {
	// return true;
	// }
	// }
	// return false;
	// }

//	/**
//	 * 验证
//	 * 
//	 * @return
//	 */
//	public boolean vaildateData() {
//		boolean isProduct = this.materielProductType == Integer
//				.parseInt(MaterielType.FINISHED_PRODUCT);
//
//		List selectCommodityList = contractExeAction
//				.checkExistTempBcsImpExpCommodityInfoBcsTenInnerMergeByParent(
//						new Request(CommonVars.getCurrUser()),
//						getCommodityList());
//		if (selectCommodityList == null || selectCommodityList.size() < 1) {
//			JOptionPane.showMessageDialog(this,
//					"请选择转成报关单的商品信息或该料件在物料于报关对应表中不存在！", "提示!",
//					JOptionPane.INFORMATION_MESSAGE);
//			return false;
//		}
//		List checkList = new ArrayList();
//		for (int i = 0; i < selectCommodityList.size(); i++) {
//			TempBcsImpExpCommodityInfo t = (TempBcsImpExpCommodityInfo) selectCommodityList
//					.get(i);
//			if (isProduct == true && t.getContractExg() == null) {
//				checkList.add(t);
//			} else if (isProduct == false && t.getContractImg() == null) {
//				checkList.add(t);
//			}
//		}
//		if (checkList.size() > 0) {
//			String tishi = "";
//			for (int i = 0; i < checkList.size(); i++) {
//				TempBcsImpExpCommodityInfo obj = (TempBcsImpExpCommodityInfo) checkList
//						.get(i);
//				String ptNo = obj.getImpExpCommodityInfo().getMateriel() == null ? ""
//						: obj.getImpExpCommodityInfo().getMateriel().getPtNo();
//				String ptName = obj.getImpExpCommodityInfo().getMateriel() == null ? ""
//						: obj.getImpExpCommodityInfo().getMateriel()
//								.getFactoryName();
//				tishi = tishi + ptNo + "         " + ptName + "\n";
//			}
//			JOptionPane.showMessageDialog(this, "共选中"
//					+ selectCommodityList.size() + "条，" + "  未备案共"
//					+ checkList.size() + "条" + "以下未对应十码或者合同中未找到备案资料库对应的记录号\n"
//					+ tishi, "提示", 0);
//			return false;
//		}
//		return true;
//	}

	/**
	 * @return Returns the contract.
	 */
	// public Contract getContract() {
	// return contract;
	// }
	//
	// /**
	// * @param contract
	// * The contract to set.
	// */
	// public void setContract(Contract contract) {
	// this.contract = contract;
	// }
	//
	// public boolean isAutoSelectContract() {
	// return isAutoSelectContract;
	// }
	//
	// public void setAutoSelectContract(boolean isAutoSelectContract) {
	// this.isAutoSelectContract = isAutoSelectContract;
	// }
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(50, 35));
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
