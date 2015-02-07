/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeFptBill extends PnCommon {

	private PnMakeFptBill pnMakeFptBill = null;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	// private List billMasterList = null; // @jve:decl-index=0:

	private JLabel jLabel;

	// private ScmCoc scmCoc = null;

	private CasAction casAction = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbFptApp = null;

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JComboBox cbbScmCoc = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JPanel jPanel2 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton11 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="672,84"

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="676,158"

	private JPanel jPanel3 = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbEmsNo = null;

	private JPanel jPanel4 = null;

	private JRadioButton rbNewBillHead = null;

	private JRadioButton rbAppendBillHead = null;

	private JTextField tfFptBillHead = null;

	private JButton btnFptBillHead = null;

	private boolean isNewFptBillHead = true;

	/**
	 * 只导入明细资料
	 */
	private boolean isOnlyImportDetail = false;

	private FptBillHead fptBillHead = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup2 = null; // @jve:decl-index=0:visual-constraint="756,69"

	private JButton btnQuery = null;

	private JLabel jLabel5 = null;

	public boolean isNewFptBillHead() {
		return isNewFptBillHead;
	}

	public void setNewFptBillHead(boolean isNewFptBillHead) {
		this.isNewFptBillHead = isNewFptBillHead;
	}

	public boolean isOnlyImportDetail() {
		return isOnlyImportDetail;
	}

	public void setOnlyImportDetail(boolean isOnlyImportDetail) {
		this.isOnlyImportDetail = isOnlyImportDetail;
	}

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBill() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();
		initTable(new ArrayList());
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (this.isNewFptBillHead) {
				this.rbNewBillHead.setSelected(true);
			} else {
				this.rbAppendBillHead.setSelected(true);
				if (this.fptBillHead != null) {
					showFptBillHeadData();
				}
			}
			this.setState();
			// System.out.println("-------------------");
		}
		super.setVisible(isFlag);
	}

	private void showFptBillHeadData() {
		if (FptBusinessType.FPT_BILL.equals(this.fptBillHead.getSysType())) {
			if (FptInOutFlag.IN.equals(this.fptBillHead.getBillSort())) {
				this.jRadioButton.setSelected(true);
			} else {
				this.jRadioButton2.setSelected(true);
			}
		} else {
			if (FptInOutFlag.IN.equals(this.fptBillHead.getBillSort())) {
				this.jRadioButton1.setSelected(true);
			} else {
				this.jRadioButton3.setSelected(true);
			}
		}
		if (this.fptBillHead != null) {
			this.tfFptBillHead.setText(this.fptBillHead.getCopNo());
		}
		if (this.fptBillHead.getCustomer() != null) {
			this.cbbScmCoc.setSelectedItem(this.fptBillHead.getCustomer());
		}
		if (this.fptBillHead.getAppNo() != null) {
			int count = this.cbbFptApp.getItemCount();
			for (int i = 0; i < count; i++) {
				FptAppHead fptAppHead = (FptAppHead) this.cbbFptApp
						.getItemAt(i);
				if (this.fptBillHead.getAppNo().equals(fptAppHead.getAppNo())) {
					this.cbbFptApp.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel1 = new JLabel();
		jLabel1.setText("申请表号码");
		jLabel1.setBounds(new Rectangle(452, 35, 69, 21));
		jLabel = new JLabel();
		this.setLayout(new BorderLayout());
		this.setSize(725, 390);
		this.add(getJSplitPane(), BorderLayout.CENTER);
		jLabel.setText("客户供应商");
		jLabel.setBounds(new Rectangle(452, 7, 69, 21));
		this.getButtonGroup();
		this.getButtonGroup1();
		this.getButtonGroup2();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeFptBill getPnMakeFptBill() {
		return pnMakeFptBill;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeFptBill(PnMakeFptBill pnMakeCustomsEnvelopBill2) {
		this.pnMakeFptBill = pnMakeCustomsEnvelopBill2;
	}

	// /**
	// * @return Returns the parentList.
	// */
	// public List getBillMasterList() {
	// return billMasterList;
	// }
	//
	// /**
	// * @param parentList
	// * The parentList to set.
	// */
	// public void setBillMasterList(List parentList) {
	// this.billMasterList = parentList;
	// }

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
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setBounds(new Rectangle(541, 92, 60, 25));
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
			btnNotSelectAll.setBounds(new Rectangle(618, 92, 60, 25));
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
			if (list.get(i) instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 编辑列
	 */

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
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
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获取选中的结转单据类型
	 */
	public String getSelectedCasBillCode() {
		String billCode = "";
		if (this.jRadioButton.isSelected()) {
			billCode = "1004";
		} else if (this.jRadioButton1.isSelected()) {
			billCode = "1106";
		} else if (this.jRadioButton2.isSelected()) {
			billCode = "2102";
		} else if (this.jRadioButton3.isSelected()) {
			billCode = "2009";
		}
		return billCode;
	}

	/**
	 * 获得选中的海关商品单据
	 */
	public List getSelectedMasterList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * 返回选中的结转申请单
	 * 
	 * @return
	 */
	public FptAppHead getSelectedFptAppHead() {
		return (FptAppHead) this.cbbFptApp.getSelectedItem();
	}

	/**
	 * 返回选中的手册/账册号
	 * 
	 * @return
	 */
	public String getSelectedEmsNo() {
		if (this.cbbEmsNo.getSelectedItem() != null) {
			return this.cbbEmsNo.getSelectedItem().toString();
		}
		return null;
	}

	/**
	 * 返回收送货单据类型
	 * 
	 * @return
	 */
	public boolean isComplex() {
		return this.jRadioButton11.isSelected();
	}

	// /**
	// * @return Returns the scmCoc.
	// */
	// public ScmCoc getScmCoc() {
	// return scmCoc;
	// }
	//
	// /**
	// * @param scmCoc
	// * The scmCoc to set.
	// */
	// public void setScmCoc(ScmCoc scmCoc) {
	// this.scmCoc = scmCoc;
	// }

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFptApp() {
		if (cbbFptApp == null) {
			cbbFptApp = new JComboBox();
			cbbFptApp.setBounds(new Rectangle(522, 34, 155, 25));
			cbbFptApp.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initEmsNo();
						initTable(new ArrayList());
					}
				}
			});
		}
		return cbbFptApp;
	}

	private void initEmsNo() {
		this.cbbEmsNo.removeAllItems();
		if (this.cbbFptApp.getSelectedItem() != null) {
			String inOutFlag = "";
			if (this.jRadioButton.isSelected()
					|| this.jRadioButton1.isSelected()) {
				inOutFlag = FptInOutFlag.IN;
			} else if (this.jRadioButton2.isSelected()
					|| this.jRadioButton3.isSelected()) {
				inOutFlag = FptInOutFlag.OUT;
			}
			FptAppHead fptAppHead = ((FptAppHead) this.cbbFptApp
					.getSelectedItem());
			List list = this.fptManageAction.findEmsNoFromFptApp(new Request(
					CommonVars.getCurrUser()), fptAppHead, inOutFlag);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) != null) {
					this.cbbEmsNo.addItem(list.get(i).toString());
				}
			}
		}
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("类别", "billMaster.billType.name", 100));
				list.add(addColumn("单据号", "billMaster.billNo", 100));
				// list.add(addColumn("仓库", "wareSet.name", 80));
				list.add(addColumn("对应报关单号", "billMaster.customNo", 100));
				list.add(addColumn("结转申请单号", "billMaster.envelopNo", 100));
				list.add(addColumn("有效", "billMaster.isValid", 50));
				list.add(addColumn("生效日期", "billMaster.validDate", 80));
				list.add(addColumn("是否记帐", "billMaster.keepAccounts", 50));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
	}

	private List getDataSource() {
		List list = new ArrayList();
		String appNo = null;
		// 1004:结转进口
		// 1106:结转料件退货单
		// 2102:结转出口
		// 2009:结转成品退货单
		String billCode = "";
		if (this.jRadioButton.isSelected()) {
			billCode = "1004";
		} else if (this.jRadioButton1.isSelected()) {
			billCode = "1106";
		} else if (this.jRadioButton2.isSelected()) {
			billCode = "2102";
		} else if (this.jRadioButton3.isSelected()) {
			billCode = "2009";
		}
		if (this.cbbScmCoc.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(PnMakeFptBill.this, "请选择客户供应商!");
			return new ArrayList();
			// return;
		} else {
			ScmCoc scmCoc = (ScmCoc) this.cbbScmCoc.getSelectedItem();
			if (this.cbbFptApp.getSelectedItem() != null) {
				appNo = ((FptAppHead) this.cbbFptApp.getSelectedItem())
						.getAppNo();
			}else{
				JOptionPane.showMessageDialog(PnMakeFptBill.this, "请选择申请表号码!");
				return new ArrayList();
			}
			if (appNo != null && !"".equals(appNo)) {
				list = this.casAction.findTempBillMasterIsAvailabilityToFpt(
						new Request(CommonVars.getCurrUser()), scmCoc.getId(),
						appNo, billCode, this.cbbBeginDate.getDate(),
						this.cbbEndDate.getDate());
			}
		}
		return list;
	}

	private void initUIComponents() {
		this.initCbbScmCoc();
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(452, 64, 69, 21));
			jLabel4.setText("手册/账册号");
			jLabel3 = new JLabel();
			jLabel3.setText("结束日期");
			jLabel3.setBounds(new Rectangle(15, 53, 60, 15));
			jLabel2 = new JLabel();
			jLabel2.setText("开始日期");
			jLabel2.setBounds(new Rectangle(15, 26, 60, 15));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbFptApp(), null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getJPanel2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(1);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(160);
		}
		return jSplitPane;
	}

	private void initCbbScmCoc() {
		// 1004:结转进口
		// 1106:结转料件退货单
		// 2102:结转出口
		// 2009:结转成品退货单
		String billCode = "";
		if (this.jRadioButton.isSelected()) {
			billCode = "1004";
		} else if (this.jRadioButton1.isSelected()) {
			billCode = "1106";
		} else if (this.jRadioButton2.isSelected()) {
			billCode = "2102";
		} else if (this.jRadioButton3.isSelected()) {
			billCode = "2009";
		}
		List list = this.casAction.findScmCocToFpt(new Request(CommonVars
				.getCurrUser()), billCode);
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 270);
		this.cbbScmCoc.setSelectedIndex(-1);
		initTable(new ArrayList());
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(522, 5, 155, 25));
			cbbScmCoc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						if (!isOnlyImportDetail) {
							fptBillHead = null;
							tfFptBillHead.setText(null);
						}
						initCbbFptApp();
						initTable(new ArrayList());
					}
				}
			});
		}
		return cbbScmCoc;
	}

	private void initCbbFptApp() {
		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		String inOutFlag = null;
		if (jRadioButton.isSelected() || jRadioButton1.isSelected()) {
			inOutFlag = FptInOutFlag.IN;
		} else if (jRadioButton2.isSelected() || jRadioButton3.isSelected()) {
			inOutFlag = FptInOutFlag.OUT;
		}
		List list = fptManageAction.findFptAppHeadByScmCoc(new Request(
				CommonVars.getCurrUser(), true), inOutFlag,
				DeclareState.PROCESS_EXE, scmCoc,null);
		cbbFptApp.removeAllItems();
		this.cbbFptApp.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbFptApp,
				"appNo", "appNo");
		cbbFptApp.setRenderer(CustomBaseRender.getInstance().getRender("appNo",
				"appNo", 200, 0));
		cbbFptApp.setSelectedIndex(-1);
	}

	private void setState() {
		this.rbAppendBillHead.setEnabled(!this.isOnlyImportDetail);
		this.rbNewBillHead.setEnabled(!this.isOnlyImportDetail);
		this.tfFptBillHead
				.setText(this.rbNewBillHead.isSelected() ? ""
						: (this.fptBillHead == null ? "" : this.fptBillHead
								.getCopNo()));
		this.btnFptBillHead.setEnabled(!this.rbNewBillHead.isSelected()
				&& !this.isOnlyImportDetail);
		this.jRadioButton.setEnabled(!this.isOnlyImportDetail);
		this.jRadioButton1.setEnabled(!this.isOnlyImportDetail);
		this.jRadioButton2.setEnabled(!this.isOnlyImportDetail);
		this.jRadioButton3.setEnabled(!this.isOnlyImportDetail);

		this.cbbScmCoc.setEnabled(!this.isOnlyImportDetail);
		this.cbbFptApp.setEnabled(!this.isOnlyImportDetail);
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(25, 6, 222, 83));
			jPanel1
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5355\u636e\u4e2d\u5fc3\u7ed3\u8f6c\u5355\u636e\u7c7b\u578b",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									new Color(51, 51, 51)));
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJRadioButton1(), null);
			jPanel1.add(getJRadioButton2(), null);
			jPanel1.add(getJRadioButton3(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("结转进口");
			jRadioButton.setSelected(true);
			jRadioButton.setBounds(new Rectangle(10, 22, 80, 26));
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbScmCoc();
					}
				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("结转料件退货单");
			jRadioButton1.setBounds(new Rectangle(92, 22, 116, 26));
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbScmCoc();
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(10, 47, 80, 21));
			jRadioButton2.setText("结转出口");
			jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbScmCoc();
					}
				}
			});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(new Rectangle(92, 47, 116, 21));
			jRadioButton3.setText("结转成品退货单");
			jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbScmCoc();
					}
				}
			});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(8, 4, 98, 21));
			jLabel5.setText("结转单据类别:");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createLineBorder(new Color(153,
					204, 255), 1));
			jPanel2.setBounds(new Rectangle(25, 92, 420, 28));
			jPanel2.add(getJRadioButton4(), null);
			jPanel2.add(getJRadioButton11(), null);
			jPanel2.add(jLabel5, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("\u6599\u53f7\u7ea7");
			jRadioButton4.setBounds(new Rectangle(151, 3, 70, 22));
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setText("\u7f16\u7801\u7ea7");
			jRadioButton11.setSelected(true);
			jRadioButton11.setBounds(new Rectangle(253, 4, 70, 22));
		}
		return jRadioButton11;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(77, 23, 100, 21));
			cbbBeginDate.setEnabled(true);
			cbbBeginDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					initTable(new ArrayList());
				}

			});
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(77, 50, 100, 21));
			cbbEndDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					initTable(new ArrayList());
				}

			});
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
			buttonGroup.add(this.getJRadioButton2());
			buttonGroup.add(this.getJRadioButton3());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getJRadioButton4());
			buttonGroup1.add(this.getJRadioButton11());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(256, 6, 188, 83));
			jPanel3
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5355\u636e\u4e2d\u5fc3\u7ed3\u8f6c\u5355\u636e\u751f\u6548\u65e5\u671f",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									new Color(51, 51, 51)));
			jPanel3.add(jLabel2, null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(getCbbBeginDate(), null);
			jPanel3.add(getCbbEndDate(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(522, 62, 155, 25));
			cbbEmsNo.setEnabled(true);
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(25, 124, 653, 30));
			jPanel4.setBorder(BorderFactory.createLineBorder(new Color(153,
					204, 255), 1));
			jPanel4.add(getRbNewBillHead(), null);
			jPanel4.add(getRbAppendBillHead(), null);
			jPanel4.add(getTfFptBillHead(), null);
			jPanel4.add(getBtnFptBillHead(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNewBillHead() {
		if (rbNewBillHead == null) {
			rbNewBillHead = new JRadioButton();
			rbNewBillHead.setBounds(new Rectangle(7, 5, 169, 18));
			rbNewBillHead.setSelected(true);
			rbNewBillHead.setEnabled(true);
			rbNewBillHead.setText("生成新的结转收发货单据");
			rbNewBillHead.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						isNewFptBillHead = true;
						setState();
					}
				}
			});
		}
		return rbNewBillHead;
	}

	/**
	 * This method initializes jRadioButton6
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAppendBillHead() {
		if (rbAppendBillHead == null) {
			rbAppendBillHead = new JRadioButton();
			rbAppendBillHead.setBounds(new Rectangle(180, 4, 209, 21));
			rbAppendBillHead.setEnabled(true);
			rbAppendBillHead.setText("追加加到原有的结转收发货单据");
			rbAppendBillHead.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						isNewFptBillHead = false;
						setState();
					}
				}
			});
		}
		return rbAppendBillHead;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFptBillHead() {
		if (tfFptBillHead == null) {
			tfFptBillHead = new JTextField();
			tfFptBillHead.setBounds(new Rectangle(389, 4, 233, 22));
			tfFptBillHead.setEditable(false);
		}
		return tfFptBillHead;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFptBillHead() {
		if (btnFptBillHead == null) {
			btnFptBillHead = new JButton();
			btnFptBillHead.setBounds(new Rectangle(623, 4, 25, 22));
			btnFptBillHead.setText("...");
			btnFptBillHead
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbScmCoc.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										PnMakeFptBill.this, "请选择客户供应商");
								return;
							}
							if (cbbFptApp.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										PnMakeFptBill.this, "请选择申请表");
								return;
							}
							String billCode = "";
							if (jRadioButton.isSelected()) {
								billCode = "1004";
							} else if (jRadioButton1.isSelected()) {
								billCode = "1106";
							} else if (jRadioButton2.isSelected()) {
								billCode = "2102";
							} else if (jRadioButton3.isSelected()) {
								billCode = "2009";
							}
							String appNo = ((FptAppHead) cbbFptApp
									.getSelectedItem()).getAppNo();
							FptBillHead billHead = FptQuery.getInstance()
									.findFptBillHead(
											billCode,
											DeclareState.APPLY_POR,
											(ScmCoc) cbbScmCoc
													.getSelectedItem(), appNo);
							if (billHead != null) {
								fptBillHead = billHead;
								showFptBillHeadData();
							}
						}
					});
		}
		return btnFptBillHead;
	}

	/**
	 * This method initializes buttonGroup2
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(this.getRbNewBillHead());
			buttonGroup2.add(this.getRbAppendBillHead());
		}
		return buttonGroup2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(463, 92, 60, 25));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = getDataSource();
					initTable(list);
				}
			});
			btnQuery.setText("查询");
		}
		return btnQuery;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
