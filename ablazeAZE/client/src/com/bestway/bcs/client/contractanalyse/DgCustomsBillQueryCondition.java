/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
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
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.bestway.bcs.client.contractanalyse.DgCommInfoQueryCondition.CheckBoxEditor;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.GridLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCustomsBillQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private ContractAction contractAction = null;

	private List lsResult = null;

	// private boolean isImport;

	private JScrollPane jScrollPane = null; // @jve:decl-index=0:visual-constraint="16,69"

	private JTable tbContract = null;

	private JTableListModel contractTableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="619,68"

	private JTextField tfCustomsDeclarationCode = null;

	private JLabel jLabel4 = null;

	private JTextField tfFecavBillCode = null;

	private JTextField tfContainer = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbTradeMode = null;

	private boolean isOk = false;

	private Integer impExpFlag = null;

	private JPanel jPanel2 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JPanel jPanel3 = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="601,127"

	private JPanel jPanel4 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public boolean isOk() {
		return isOk;
	}

	// /**
	// * @param isImport
	// * The isImport to set.
	// */
	// public void setImport(boolean isImport) {
	// this.isImport = isImport;
	// }

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgCustomsBillQueryCondition() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 初始化合同
		// this.cbbContract.removeAllItems();
		List list = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
		// if (list != null && list.size() > 0) {
		// for (int i = 0; i < list.size(); i++) {
		// this.cbbContract.addItem((Contract) list.get(i));
		// }
		// this.cbbContract.setRenderer(CustomBaseRender.getInstance()
		// .getRender("expContractNo", "emsNo", 100, 100));
		// }
		// this.cbbContract.setSelectedIndex(-1);
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选中", "isSelected", 50));
				list.add(addColumn("合同号", "impContractNo", 100));
				list.add(addColumn("帐册号 ", "emsNo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel.dataBind(tbContract, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		contractTableModel = (JTableListModel) tbContract.getModel();

		TableColumn column = tbContract.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		// contractTableModel = new JTableListModel(tbContract, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(addColumn("是否选中", "isSelected", 100));
		// list.add(addColumn("合同号", "impContractNo", 100));
		// list.add(addColumn("帐册号 ", "emsNo", 100));
		// return list;
		// }
		// });

		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (ImpExpFlag.IMPORT == impExpFlag) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
		} else if (ImpExpFlag.EXPORT == impExpFlag) {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));

		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);

		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
			cb = new JCheckBox();
			cb.addActionListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				// cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			// cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			// cb.removeActionListener(this);
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof Contract) {
				Contract temp = (Contract) obj;
				temp.setSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
				// showCount(tableModel.getList());
				// showCommCustomerInfo();
			}
			// System.out.println("aaaaaaaaaaaaaaaaaa");
			 fireEditingStopped();
		}
	}

	private void showCommCustomerInfo() {
		List<Contract> lsContract = getSelectContract();

		// if (contract != null) {
		// cbbBeginDate.setDate(contract.getBeginDate());
		// } else {
		// cbbBeginDate.setDate(null);
		// }
	}

	private List<Contract> getSelectContract() {
		List list = contractTableModel.getList();
		List<Contract> lsContract = new ArrayList<Contract>();
		for (int i = 0; i < list.size(); i++) {
			Contract contract = (Contract) list.get(i);
			if (contract.isSelected()) {
				lsContract.add(contract);
			}
		}
		return lsContract;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(576, 387);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		this.getButtonGroup1();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new java.awt.Rectangle(91, 114, 170, 22));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(93, 187, 170, 20));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(93, 217, 170, 19));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(new java.awt.Rectangle(107, 313, 67, 24));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return jButton;
	}

	private void find() {
		// Integer impExpFlag = null;
		Integer impExpType = null;
		List lsContract = getSelectContract();
		if (lsContract.size() <= 0) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgCustomsBillQueryCondition.this,
					"请选择合同", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// if (isImport) {
		// impExpFlag = ImpExpFlag.IMPORT;
		// } else {
		// impExpFlag = ImpExpFlag.EXPORT;
		// }
		if (cbbImpExpType.getSelectedItem() != null) {
			impExpType = Integer.valueOf(((ItemProperty) cbbImpExpType
					.getSelectedItem()).getCode());
		}
		Date beginDate = cbbBeginDate.getDate();
		Date endDate = cbbEndDate.getDate();
		int state = -1;
		if (rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else if (rbAll.isSelected()) {
			state = CustomsDeclarationState.ALL;
		}
		lsResult = contractAnalyseAction.findImpExpCustomsDeclarationList(
				new Request(CommonVars.getCurrUser()), lsContract, impExpFlag,
				impExpType, tfCustomsDeclarationCode.getText().trim(),
				tfFecavBillCode.getText().trim(), tfContainer.getText().trim(),
				(Trade) cbbTradeMode.getSelectedItem(), beginDate, endDate,
				state);
		isOk = true;
		dispose();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setBounds(new java.awt.Rectangle(194, 313, 67, 24));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setSize(new Dimension(276, 254));
			jScrollPane.setLocation(new Point(0, 69));
			jScrollPane.setViewportView(getTbContract());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbContract() {
		if (tbContract == null) {
			tbContract = new JTable();
		}
		return tbContract;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(19, 151, 73, 19));
			jLabel6.setText("贸易方式");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(19, 86, 73, 19));
			jLabel4.setText("集装箱号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(19, 22, 73, 19));
			jLabel3.setText("报关单号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(19, 54, 73, 19));
			jLabel2.setText("核销单号");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(79, 219, 15, 16));
			jLabel5.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(19, 189, 73, 19));
			jLabel1.setText("申报日期从");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(19, 116, 73, 19));
			jLabel.setText("进出口类型");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getTfCustomsDeclarationCode(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfFecavBillCode(), null);
			jPanel.add(getTfContainer(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getCbbTradeMode(), null);
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
			jSplitPane.setDividerLocation(280);
			jSplitPane.setDividerSize(6);
			// jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setLeftComponent(getJPanel3());
			jSplitPane.setRightComponent(getJPanel());
		}
		return jSplitPane;
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
			jPanel1.setBounds(new java.awt.Rectangle(17, 250, 249, 43));
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getRbYes(), null);
			jPanel1.add(getRbNo(), null);
			jPanel1.add(getRbAll(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new java.awt.Rectangle(9, 13, 71, 20));
			rbYes.setText("已生效");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new java.awt.Rectangle(95, 13, 86, 20));
			rbNo.setText("未生效");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new java.awt.Rectangle(188, 13, 52, 20));

			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
			buttonGroup.add(this.rbAll);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(new java.awt.Rectangle(91, 21,
					170, 22));
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFecavBillCode() {
		if (tfFecavBillCode == null) {
			tfFecavBillCode = new JTextField();
			tfFecavBillCode.setBounds(new java.awt.Rectangle(91, 55, 170, 22));
		}
		return tfFecavBillCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContainer() {
		if (tfContainer == null) {
			tfContainer = new JTextField();
			tfContainer.setBounds(new java.awt.Rectangle(91, 86, 170, 22));
		}
		return tfContainer;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new java.awt.Rectangle(91, 148, 170, 22));
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new java.awt.Rectangle(75, 1, 134, 27));
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(0, 2, 61, 24));
			jRadioButton.setText("全选");
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
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
			jRadioButton1.setBounds(new java.awt.Rectangle(80, 2, 55, 25));
			jRadioButton1.setText("全否");
			jRadioButton1.setSelected(true);
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return jRadioButton1;
	}

	private void selectAll(boolean isSelected) {
		if (contractTableModel == null) {
			return;
		}
		List list = contractTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Contract) {
				Contract temp = (Contract) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		contractTableModel.updateRows(list);
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
			jPanel3.add(getJPanel2(), null);
			jPanel3.add(getJScrollPane());
			jPanel3.add(getJPanel4(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton());
			buttonGroup1.add(getJRadioButton1());
		}
		return buttonGroup1;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			find();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			jPanel4 = new JPanel();
			jPanel4.setLayout(gridLayout);
			jPanel4.setBounds(new Rectangle(72, 30, 109, 38));
			jPanel4.add(getCbContractExe(), null);
			jPanel4.add(getCbContractCancel(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setSelected(true);
			cbContractExe.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c");
			cbContractExe
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						selectContract(true,true);
					} else if (cbContractExe.isSelected()
							&& !cbContractCancel.isSelected()) {
						selectContract(true,false);
					} else if (!cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						selectContract(false,true);
					} else {
						selectContract(false,false);
					}
				}
			});
		}
		return cbContractExe;
	}
	/**
	 * 选择正在执行或核销的合同
	 * @param exe
	 * @param cancel
	 */
	protected void selectContract(boolean exe,boolean cancel) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选中", "isSelected", 50));
				list.add(addColumn("合同号", "impContractNo", 100));
				list.add(addColumn("帐册号 ", "emsNo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		List list = new ArrayList();
		if (exe) {
			list.addAll(contractAction.findContractByProcessExe(new Request(
					CommonVars.getCurrUser(), true)));
		}
		if (cancel) {
			
			list.addAll(contractAction.findContract(new Request(CommonVars
					.getCurrUser(), true), true));
		}
		tbContract.removeAll();
		JTableListModel.dataBind(tbContract, list, jTableListModelAdapter);
		contractTableModel = (JTableListModel) tbContract.getModel();

		TableColumn column = tbContract.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		
	}

	/**
	 * This method initializes cbContractCancel	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("\u6838\u9500\u7684\u5408\u540c");
			cbContractCancel
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						selectContract(true,true);
					} else if (cbContractExe.isSelected()
							&& !cbContractCancel.isSelected()) {
						selectContract(true,false);
					} else if (!cbContractExe.isSelected()
							&& cbContractCancel.isSelected()) {
						selectContract(false,true);
					} else {
						selectContract(false,false);
					}
				}
			});
		}
		return cbContractCancel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
