/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DocumentControlByGbkByte;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import java.lang.Double;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author yp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgContractMateriel extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfSeqNum = null;

	private JTextField tfName = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel13 = null;

	private JTextField tfComplex = null;

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfUnitWeight = null;

	private JTableListModel tableModel = null;

	private int dataState = -1;

	private ContractAction contractAction = null; // 合同

	private ContractImg contractImg = null; // 合同料件对象

	private JFormattedTextField tfMaterielAmount = null;

	private JLabel jLabel14 = null;

	private JTextField tfMemo = null;

	private NumberFormatter numberFormatter1 = null;

	private JFormattedTextField tfTotalMoney = null;

	private JComboBox cbbCountry = null;

	private JLabel jLabel3 = null;

	private JFormattedTextField tfCerdenceNo = null;

	private JTable tbUnitWaste = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel8 = null;

	private JTableListModel tableModelUnitWaste = null;

	private JComboBox cbbLevyMode = null;

	private JLabel jLabel5 = null;

	private Contract contract = null; // 合同对象

	private Complex complex = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbUnit = null;

	private JLabel jLabel12 = null;

	private JTextField jTextField = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnEdit = null;

	private JButton btnCancel = null;

	private JCheckBox cbIsMainImg = null;

	private JLabel jLabel51 = null;

	private JButton btnComplex = null;

	private BcsParameterSet parameterSet = null; // @jve:decl-index=0:

	private JLabel jLabel15 = null;

	private JTextField tfModeNote = null;

	private JLabel jLabel151 = null;

	private JCustomFormattedTextField tflegalUnitGene = null;

	private JLabel jLabel1511 = null;

	private JCustomFormattedTextField tflegalUnit2Gene = null;

	private JLabel jLabel1512 = null;

	private JLabel jLabel15121 = null;

	private JTextField tfLegalUnit = null;

	private JTextField tfLegalUnit2 = null;

	private JLabel jLabel16 = null;

	private JFormattedTextField tfDutyRate = null;

	private JLabel jLabel17 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgContractMateriel() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initialize();
		initUIComponents();

		if (parameterSet.getIsControlLength() != null
				&& parameterSet.getIsControlLength()) {
			tfName.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
			tfSpec.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("料件修改");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(610, 571);

	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(508, 255, 16, 22));
			jLabel17.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel17.setText("%");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(283, 259, 84, 18));
			jLabel16.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel16.setText("征税比例");
			jLabel15121 = new JLabel();
			jLabel15121.setBounds(new Rectangle(27, 231, 74, 18));
			jLabel15121.setText("第二法定单位");
			jLabel15121.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1512 = new JLabel();
			jLabel1512.setBounds(new Rectangle(27, 209, 74, 18));
			jLabel1512.setText("第一法定单位");
			jLabel1512.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1511 = new JLabel();
			jLabel1511.setBounds(new Rectangle(271, 229, 96, 18));
			jLabel1511.setText("第二法定比例因子");
			jLabel1511.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel151 = new JLabel();
			jLabel151.setBounds(new Rectangle(271, 208, 96, 18));
			jLabel151.setText("第一法定比例因子");
			jLabel151.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(27, 259, 74, 18));
			jLabel15.setText("方式");
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(283, 185, 84, 18));
			jLabel51.setText("主料/辅料");
			jLabel51.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel5 = new JLabel();
			jLabel8 = new JLabel();
			jLabel3 = new JLabel();
			jLabel14 = new JLabel();
			jContentPane = new JPanel();
			jLabel = new JLabel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel4 = new JLabel();
			jLabel6 = new JLabel();
			jLabel7 = new JLabel();
			jLabel9 = new JLabel();
			jLabel10 = new JLabel();
			jLabel13 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setText("序号");
			jLabel.setLocation(new Point(27, 40));
			jLabel.setSize(new Dimension(74, 18));
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(27, 64, 74, 18);
			jLabel1.setText("商品名称");
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setBounds(27, 109, 74, 18);
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("单价");
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(283, 41, 84, 18);
			jLabel4.setText("商品编码");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(283, 86, 84, 18);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setText("单位");
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(283, 64, 84, 18);
			jLabel7.setText("型号规格");
			jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(27, 182, 74, 18);
			jLabel9.setText("单位净重");
			jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(27, 86, 74, 18);
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setText("数量");
			jLabel10
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setBounds(283, 109, 84, 18);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setText("总金额");
			jLabel13
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel14.setBounds(283, 135, 84, 18);
			jLabel14.setText("征免方式");
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setBounds(27, 160, 74, 18);
			jLabel3.setText("归并序号");
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(0, 308, 301, 17);
			jLabel8.setText("使用该料件的成品列表:");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel8
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel5.setBounds(27, 281, 74, 18);
			jLabel5.setText("说明");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setBounds(27, 135, 74, 18);
			jLabel11.setText("原产国");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setBounds(283, 160, 84, 18);
			jLabel12.setText("总耗用量");
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfUnitWeight(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getTfMaterielAmount(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getTfTotalMoney(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfCerdenceNo(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getCbbLevyMode(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getCbIsMainImg(), null);
			jContentPane.add(jLabel51, null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getTfModeNote(), null);
			jContentPane.add(jLabel151, null);
			jContentPane.add(getTflegalUnitGene(), null);
			jContentPane.add(jLabel1511, null);
			jContentPane.add(getTflegalUnit2Gene(), null);
			jContentPane.add(jLabel1512, null);
			jContentPane.add(jLabel15121, null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(getTfLegalUnit2(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(getTfDutyRate(), null);
			jContentPane.add(jLabel17, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(4, 2, 607, 33);
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					fillData();
					if(check()){
					   saveData();
					   dataState = DataState.BROWSE;
					   setState();
					}					
				}
			});
		}
		return btnSave;
	}
	
	private boolean check(){
//		if(((Number)this.tfDutyRate.getValue()).doubleValue()>99 || ((Number)this.tfDutyRate.getValue()).doubleValue()<0){
//			JOptionPane.showMessageDialog(this, "非保税料件比例范围大于等于0且小于等于99","警告！",JOptionPane.INFORMATION_MESSAGE);			
//			return false;
//		}
		return true;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgContractMateriel.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfCredenceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(111, 41, 111, 22);
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(111, 64, 111, 22);
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(377, 64, 145, 22);
			tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(377, 41, 127, 22);
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			// cbbUnit.setEnabled(false);
			cbbUnit.setBounds(377, 86, 145, 22);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(111, 109, 111, 22);
			tfUnitPrice.setValue(new Double(0));
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes tfUnitWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWeight() {
		if (tfUnitWeight == null) {
			tfUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWeight.setBounds(111, 182, 111, 22);
			tfUnitWeight.setValue(new Double(0));

		}
		return tfUnitWeight;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMaterielAmount() {
		if (tfMaterielAmount == null) {
			tfMaterielAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaterielAmount.setBounds(111, 86, 111, 22);
			tfMaterielAmount.setValue(new Double(0));
		}
		return tfMaterielAmount;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(111, 281, 416, 25);
		}
		return tfMemo;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new JFormattedTextField();
			tfTotalMoney.setBounds(377, 109, 145, 22);
			tfTotalMoney.setValue(new Double(0));
			tfTotalMoney.setEditable(false);
		}
		return tfTotalMoney;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(111, 135, 111, 22);
		}
		return cbbCountry;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfCerdenceNo() {
		if (tfCerdenceNo == null) {
			tfCerdenceNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCerdenceNo.setBounds(111, 160, 111, 22);

			tfCerdenceNo.setEditable(false);
			tfCerdenceNo.setValue(new Double(0));
		}
		return tfCerdenceNo;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbUnitWaste == null) {
			tbUnitWaste = new JTable();
		}
		return tbUnitWaste;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(0, 325, 607, 209);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes numberFormatter1
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter1() {
		if (numberFormatter1 == null) {
			numberFormatter1 = new NumberFormatter();
		}
		return numberFormatter1;
	}

	/**
	 * 初始化单耗表
	 */
	private void initTbUnitWaste(List dataSource) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("是否选中", "isSelected", 100));
				list.add(addColumn("成品序号", "contractExg.seqNum", 100));
				list.add(addColumn("商品编码", "contractExg.complex.code", 100));
				list.add(addColumn("商品名称", "contractExg.name", 120));
				list.add(addColumn("规格型号", "contractExg.spec", 100));
				list.add(addColumn("计量单位", "contractExg.unit.name", 100));
				list.add(addColumn("出口数量", "contractExg.exportAmount", 80));
				list.add(addColumn("单价", "declarePrice", 100));
				list.add(addColumn("净耗", "unitWaste", 100));
				list.add(addColumn("损耗率%", "waste", 100));
				list.add(addColumn("单耗", "unitDosage", 100));
				list.add(addColumn("金额", "totalPrice", 60));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelUnitWaste = new JTableListModel(tbUnitWaste, dataSource,
				jTableListModelAdapter);
		TableColumn column = tbUnitWaste.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		tbUnitWaste
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof ContractBom) {
				ContractBom temp = (ContractBom) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 初始化国
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry);
		this.cbbCountry.setSelectedItem(null);
		// 征减免税方式
		this.cbbLevyMode.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyMode);
		this.cbbLevyMode.setSelectedItem(null);
		// 单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		this.cbbUnit.setSelectedItem(null);

		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfName);
		components.add(null);
		components.add(this.tfMemo);
		components.add(this.btnSave);
		components.add(this.btnNext);
		this.setComponentFocusList(components);
		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();
		int priceBit = parameterSet.getPriceBit() == null ? 5 : parameterSet
				.getPriceBit();
		int moneyBit = parameterSet.getMoneyBit() == null ? 5 : parameterSet
				.getMoneyBit();

		// 数量
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfMaterielAmount, countBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfMaterielAmount,
				new AutoCalcListener() {
					public void run() {
						tfTotalMoney.setValue(getTotalPrice());
					}
				});

		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
				priceBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfUnitPrice,
				new AutoCalcListener() {
					public void run() {
						tfTotalMoney.setValue(getTotalPrice());
					}
				});
		// 金额
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfTotalMoney,
				moneyBit);
		// 单位重量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWeight, 5);
		// 法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tflegalUnitGene,
				9);
		// 第二法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tflegalUnit2Gene, 9);		
		//非保税料件比例
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfDutyRate, 5);		
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		contractImg = (ContractImg) tableModel.getCurrentRow();
		if (this.dataState == DataState.ADD) {
			int seqNum = this.contractAction.getMaxContractImgSeqNum(
					new Request(CommonVars.getCurrUser()), contract.getId()) + 1;
			this.tfSeqNum.setText(String.valueOf(seqNum));
			initTbUnitWaste(new ArrayList());

		} else if (this.dataState == DataState.EDIT
				|| this.dataState == DataState.BROWSE) {
			this.contractImg = (ContractImg) this.tableModel.getCurrentRow();
			this.contract = contractImg.getContract();
			List list = this.contractAction.findContractBomByCredenceNo(
					new Request(CommonVars.getCurrUser()), contractImg);
			initTbUnitWaste(list);
			this.cbbUnit.setSelectedItem((Unit) contractImg.getUnit());

			// 序号
			if (this.contractImg.getSeqNum() != null) {
				this.tfSeqNum.setText(this.contractImg.getSeqNum().toString());
			} else {
				this.tfSeqNum.setText("");
			}
			// 商品名称
			if (this.contractImg.getName() != null) {
				this.tfName.setText(this.contractImg.getName());
			} else {
				this.tfName.setText("");
			}
			// 商品规格
			if (this.contractImg.getSpec() != null) {
				this.tfSpec.setText(this.contractImg.getSpec());
			} else {
				this.tfSpec.setText("");
			}
			// 商品编码
			if (this.contractImg.getComplex() != null) {
				complex = this.contractImg.getComplex();
				this.tfComplex.setText(this.contractImg.getComplex().getCode());
			} else {
				this.tfComplex.setText("");
			}
			// 法定单位
			if (this.contractImg.getComplex() != null
					&& this.contractImg.getComplex().getFirstUnit() != null) {
				this.tfLegalUnit.setText(this.contractImg.getComplex()
						.getFirstUnit().getName());
			} else {
				this.tfLegalUnit.setText("");
			}
			if (this.contractImg.getComplex() != null
					&& this.contractImg.getComplex().getSecondUnit() != null) {
				tfLegalUnit2.setText(contractImg.getComplex() == null ? ""
						: contractImg.getComplex().getSecondUnit().getName());
			} else {
				this.tfLegalUnit2.setText("");
			}

			// 单位净重
			if (this.contractImg.getUnitWeight() != null) {
				this.tfUnitWeight.setValue(this.contractImg.getUnitWeight());
			} else {
				this.tfUnitWeight.setValue(new Double(0));
			}
			// 原产地
			this.cbbCountry.setSelectedItem(this.contractImg.getCountry());
			// 料件数量
			if (this.contractImg.getAmount() != null) {
				this.tfMaterielAmount.setValue(this.contractImg.getAmount());
			} else {
				this.tfMaterielAmount.setValue(new Double(0));
			}
			// 申报单价
			if (this.contractImg.getDeclarePrice() != null) {
				this.tfUnitPrice.setValue(this.contractImg.getDeclarePrice());
			} else {
				this.tfUnitPrice.setValue(new Double(0));
			}
			// 总金额
			if (this.contractImg.getTotalPrice() != null) {
				this.tfTotalMoney.setValue(this.contractImg.getTotalPrice());
			} else {
				this.tfTotalMoney.setValue(new Double(0));
			}
			// 征减免税方式
			this.cbbLevyMode.setSelectedItem(this.contractImg.getLevyMode());

			// 凭证号
			if (this.contractImg.getCredenceNo() != null) {
				this.tfCerdenceNo.setValue(this.contractImg.getCredenceNo());
			} else {
				this.tfCerdenceNo.setValue(new Double(0));
			}
			// 说明
			if (this.contractImg.getNote() != null) {
				this.tfMemo.setText(this.contractImg.getNote());
			} else {
				this.tfMemo.setText(null);
			}
			// 第一比例因子
			if (this.contractImg.getLegalUnitGene() != null) {
				this.tflegalUnitGene.setValue(this.contractImg
						.getLegalUnitGene());
			} else {
				this.tflegalUnitGene.setValue(new Double(0));
			}
			// 第二比例因子
			if (this.contractImg.getLegalUnit2Gene() != null) {
				this.tflegalUnit2Gene.setValue(this.contractImg
						.getLegalUnit2Gene());
			} else {
				this.tflegalUnit2Gene.setValue(new Double(0));
			}
			// 方式
			if (this.contractImg.getMannerNote() != null) {
				this.tfModeNote.setText(this.contractImg.getMannerNote());
			} else {
				this.tfModeNote.setText(null);
			}
			if(contractImg.getDutyRate() != null){
				this.tfDutyRate.setValue(contractImg.getDutyRate());
			}else{
				tfDutyRate.setValue(new Double(0));
			}

			// 主料/辅料
			this.cbIsMainImg
					.setSelected(this.contractImg.getIsMainImg() == null ? false
							: this.contractImg.getIsMainImg());
		}
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		if (this.contractImg == null) {
			contractImg = new ContractImg();
			contractImg.setContract(contract);
			contractImg.setCompany(CommonVars.getCurrUser().getCompany());
		}
		this.contractImg.setUnit((Unit) this.cbbUnit.getSelectedItem());
		// 商品编码
		this.contractImg.setComplex(complex);
		// 商品名称
		contractImg.setName(this.tfName.getText());
		// 商品规格
		contractImg.setSpec(this.tfSpec.getText());
		// 单位净重
		this.contractImg.setUnitWeight(Double.valueOf(this.tfUnitWeight
				.getValue().toString()));
		// 原产地
		this.contractImg
				.setCountry((Country) this.cbbCountry.getSelectedItem());
		// 料件数量
		this.contractImg.setAmount(Double.valueOf(this.tfMaterielAmount
				.getValue().toString()));
		// 申报单价
		this.contractImg.setDeclarePrice(Double.valueOf(this.tfUnitPrice
				.getValue().toString()));
		// 凭证号
		this.contractImg.setCredenceNo(Double.valueOf(
				this.tfCerdenceNo.getValue().toString()).intValue());
		// 合同总价
		this.contractImg.setTotalPrice(Double.valueOf(this.tfTotalMoney
				.getValue().toString()));
		// 征减免税方式
		this.contractImg.setLevyMode((LevyMode) this.cbbLevyMode
				.getSelectedItem());
		// 说明
		this.contractImg.setNote(this.tfMemo.getText());
		// 第一比例因子
		this.contractImg.setLegalUnitGene(Double.valueOf(this.tflegalUnitGene
				.getValue().toString()));
		// 第二比例因子
		this.contractImg.setLegalUnit2Gene(Double.valueOf(this.tflegalUnit2Gene
				.getValue().toString()));
		// 方式
		this.contractImg.setMannerNote(this.tfModeNote.getText());
		// this.contractImg.setNote(this..getText());
		//非保税料件比例
		this.contractImg.setDutyRate(Double.valueOf(this.tfDutyRate.getValue().toString()));
		// 主料/辅料
		this.contractImg.setIsMainImg(this.cbIsMainImg.isSelected());
		if (ModifyMarkState.UNCHANGE.equals(this.contractImg.getModifyMark())) {
			this.contractImg.setModifyMark(ModifyMarkState.MODIFIED);
		}
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {
		if (this.dataState == DataState.ADD) {
			int seqNum = this.contractAction.getMaxContractImgSeqNum(
					new Request(CommonVars.getCurrUser()), contract.getId()) + 1;
			contractImg.setSeqNum(seqNum);
			contractImg = this.contractAction.saveContractImg(new Request(
					CommonVars.getCurrUser()), this.contractImg);
			this.tableModel.addRow(contractImg);
		} else if (this.dataState == DataState.EDIT) {
			List<ContractBom> list = this.getSelectedData();
			contractImg = this.contractAction.saveContractImg(new Request(
					CommonVars.getCurrUser()), this.contractImg, list);
			this.tableModel.updateRow(contractImg);
		}
	}

	private List<ContractBom> getSelectedData() {
		List<ContractBom> lsResult = new ArrayList<ContractBom>();
		List list = tableModelUnitWaste.getList();
		for (int i = 0; i < list.size(); i++) {
			ContractBom contractBom = (ContractBom) list.get(i);
			if (contractBom.getIsSelected() != null
					&& contractBom.getIsSelected()) {
				lsResult.add(contractBom);
			}
		}
		return lsResult;
	}

	/**
	 * 验证数据
	 * 
	 * @return
	 */
	private boolean validateData() {
		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();
		if (this.complex == null) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.tfUnitPrice.getValue() == null) {
			JOptionPane.showMessageDialog(this, "单价不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// else if (validate(this.tfUnitPrice.getValue().toString().trim(),
		// parameterSet.getPriceBit())) {
		// JOptionPane.showMessageDialog(this,
		// "单价小数位不能超过海关允许最大小数位（海关允许最大值为５）!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		if (this.tfMaterielAmount.getValue() == null) {
			JOptionPane.showMessageDialog(this, "数量不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (validate(this.tfMaterielAmount.getValue().toString().trim(),
				parameterSet.getCountBit())) {
			JOptionPane.showMessageDialog(this,
					"数量小数位不能超过海关允许最大小数位（海关允许最大值为５）!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// if (this.cbbMaterielType.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "主料辅料不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// if (this.cbbCountry.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "产销国不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }

		// if (this.cbbCountry.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "产销国不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }

		if (dataState == DataState.EDIT) {
			List<ContractBom> list = this.getSelectedData();
			double oldAmount = this.contractImg.getAmount();
			double newAmount = (this.tfMaterielAmount.getValue() == null ? 0.0
					: Double.valueOf(this.tfMaterielAmount.getValue()
							.toString()));
			if (oldAmount != newAmount) {
				BcsParameterSet parameter = this.contractAction
						.findBcsParameterSet(new Request(CommonVars
								.getCurrUser(), true));
				if (parameter != null
						&& parameter.getUpdateContractImgWriteBackBomExg() != null
						&& parameter.getUpdateContractImgWriteBackBomExg()
						&& list.size() <= 0
						&& tableModelUnitWaste.getList().size() > 0) {
					JOptionPane.showMessageDialog(this,
							"由于料件数量已发生变化，请选择需要反写的BOM成品!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断输入数值的小数是否与参数设定中小数位一致
	 */
	private boolean validate(String text, Integer bitValue) {
		if (bitValue == null) {
			bitValue = 5;
		} else if (bitValue == 0) {
			bitValue++;
		}

		Double value = Double.valueOf(text);
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(999);
		String test = format.format(value);
		String[] strs = test.split("\\.");// 正则表达式
		// BigDecimal bd=new BigDecimal(String.valueOf(value.doubleValue()));
		// int count1 = test.length();
		// int count2 = test.indexOf(".") + 1;
		// int margin = count1 - count2;
		if (strs.length >= 2) {
			int margin = strs[1].length();
			if (margin > bitValue.intValue()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(377, 135, 145, 22);
		}
		return cbbLevyMode;
	}

	/**
	 * 设置料件总金额
	 */
	private double getTotalPrice() {
		try {
			int moneyBit = parameterSet.getMoneyBit() == null ? 5
					: parameterSet.getMoneyBit();
			double amount = Double
					.parseDouble(this.tfMaterielAmount.getValue() == null ? "0"
							: this.tfMaterielAmount.getValue().toString())
					* Double.parseDouble(tfUnitPrice.getValue() == null ? "0"
							: tfUnitPrice.getValue().toString());
			BigDecimal bd = new BigDecimal(amount);
			return bd.setScale(moneyBit, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} catch (Exception ex) {
		}
		return 0;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(377, 160, 145, 22);
		}
		return jTextField;
	}

	private String formatNum(Double num) {
		String bdStr = "";
		try {
			BigDecimal bd = new BigDecimal(num);
			bdStr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception e) {
		}
		return bdStr;

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	private void setState() {
		ContractImg contractImg = (ContractImg) tableModel.getCurrentRow();
		if (contractImg == null) {
			return;
		}
		boolean isChanging = false;
		if (this.contract != null) {
			if (DeclareState.CHANGING_EXE.equals(this.contract
					.getDeclareState())) {
				isChanging = true;
			}
		}
		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
//		this.tfName.setEnabled(this.dataState != DataState.BROWSE);
//		this.tfSpec.setEnabled(this.dataState != DataState.BROWSE);
		this.btnComplex.setEnabled(this.dataState != DataState.BROWSE);
//		this.tfName.setEditable(ModifyMarkState.ADDED.equals(contractImg
//				.getModifyMark())
//				&& (this.dataState == DataState.BROWSE));
//		this.tfSpec.setEditable(ModifyMarkState.ADDED.equals(contractImg
//				.getModifyMark())
//				&& this.dataState == DataState.BROWSE);
		this.tfDutyRate.setEditable(false);
		this.tfMaterielAmount.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitWeight.setEditable(this.dataState != DataState.BROWSE);
		this.tfMemo.setEditable(this.dataState != DataState.BROWSE);
		this.tfModeNote.setEditable(this.dataState != DataState.BROWSE);
		this.cbbCountry.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbLevyMode.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbUnit.setEnabled(this.dataState != DataState.BROWSE);
		this.cbIsMainImg.setEnabled(this.dataState != DataState.BROWSE);
		this.tflegalUnitGene.setEnabled(this.dataState != DataState.BROWSE);
		this.tflegalUnit2Gene.setEnabled(this.dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsMainImg() {
		if (cbIsMainImg == null) {
			cbIsMainImg = new JCheckBox();
			cbIsMainImg.setBounds(new Rectangle(377, 182, 145, 22));
			cbIsMainImg.setText("是否主料");
		}
		return cbIsMainImg;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(503, 41, 21, 23));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (obj != null) {
						complex = obj;
						tfComplex.setText(complex.getCode());
						tfLegalUnit.setText(complex.getFirstUnit().getName());
						tfLegalUnit2
								.setText(complex.getSecondUnit() == null ? ""
										: complex.getSecondUnit().getName());
						ContractImg img = (ContractImg) tableModel
								.getCurrentRow();
						String modifyMark = img.getModifyMark();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfName.setText(complex.getName());
							tfSpec.setText(complex.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes tfModeNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfModeNote() {
		if (tfModeNote == null) {
			tfModeNote = new JTextField();
			tfModeNote.setBounds(new Rectangle(111, 256, 111, 22));
		}
		return tfModeNote;
	}

	/**
	 * This method initializes tflegalUnitGene
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTflegalUnitGene() {
		if (tflegalUnitGene == null) {
			tflegalUnitGene = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tflegalUnitGene.setBounds(new Rectangle(378, 208, 145, 22));
			tflegalUnitGene.setValue(new Double(0));
		}
		return tflegalUnitGene;
	}

	/**
	 * This method initializes tflegalUnit2Gene
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTflegalUnit2Gene() {
		if (tflegalUnit2Gene == null) {
			tflegalUnit2Gene = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tflegalUnit2Gene.setBounds(new Rectangle(378, 230, 145, 22));
			tflegalUnit2Gene.setValue(new Double(0));
		}
		return tflegalUnit2Gene;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(new Rectangle(111, 209, 111, 22));
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes tfLegalUnit2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit2() {
		if (tfLegalUnit2 == null) {
			tfLegalUnit2 = new JTextField();
			tfLegalUnit2.setBounds(new Rectangle(111, 230, 111, 22));
			tfLegalUnit2.setEditable(false);
		}
		return tfLegalUnit2;
	}

	/**
	 * This method initializes tfDutyRate	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getTfDutyRate() {
		if (tfDutyRate == null) {
			tfDutyRate = new JFormattedTextField();
			tfDutyRate.setEditable(false);
			tfDutyRate.setBounds(new Rectangle(378, 255, 132, 22));
		}
		return tfDutyRate;
	}
}  //  @jve:decl-index=0:visual-constraint="9,12"
