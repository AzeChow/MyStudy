/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscInnerMergeEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private DzscInnerMergeData innerMergeData = null;

	// private DzscInnerMergeHead head = null;
	private MaterialManageAction materialManageAction = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JFormattedTextField tfPtUnitPrice = null;

	private JTextField tfPtName = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JLabel jLabel = null;

	private JTextField tfSeqNum = null;

	private JTextField tfPtSpec = null;

	private JComboBox cbbPtUnit = null;

	private boolean isAdd = true;

	private JTextField tfPtNo = null;

	private NumberFormatter numberFormatter = null;

	private String materialType = null; // @jve:decl-index=0:

	private MultiSpanCellTable jTable = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscInnerMergeEdit() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(381, 250);
		this.setTitle("企业物料设定");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) { // 编辑
					if (tableModel.getCurrentRow() != null) {
						innerMergeData = (DzscInnerMergeData) tableModel
								.getCurrentRow();
					}
					initUI(); // 初始化控件
					initUIData();// 初始化数据
				} else { // 新增
					initUI(); // 初始化控
					tfSeqNum.setText(String.valueOf(dzscInnerMergeAction
							.findMaxInnerMergeNo(new Request(CommonVars
									.getCurrUser()), getMaterialType(), "seqNum")));
				}
			}
		});
	}

	private void initUI() {
		// 工厂单位
		List listunit = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser()));
		this.cbbPtUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.cbbPtUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPtUnit, "code", "name");
		cbbPtUnit.setSelectedIndex(-1);
	}

	private void initUIData() {
		// tfSeqNum.setText(String.valueOf(innerMergeData.getSeqNum())); //序号
		tfPtNo.setText(innerMergeData.getMateriel().getPtNo()); // BOM编号
		// tfPtName.setText(innerMergeData.getFactoryName());//商品名称
		// tfPtSpec.setText(innerMergeData.getFactorySpec());//商品规格
		// tfPtUnitPrice.setText(doubleToStr(innerMergeData.getPtPrice())); //单价
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	public void fillData(DzscInnerMergeData inner) { // 保存
		// inner.setHead(head);
		inner.setImrType(this.getMaterialType());
		// inner.setSeqNum(Integer.valueOf(tfSeqNum.getText()));
		// inner.setPtNo(jTextField3.getText());
		// inner.setFactoryName(tfPtName.getText());
		// inner.setFactorySpec(tfPtSpec.getText());
		// inner.setUnit((CalUnit) cbbPtUnit.getSelectedItem());
		// inner.setPtPrice(strToDouble(tfPtUnitPrice.getText()));
		if (inner.getStateMark().equals(DzscState.CHANGE)) {
			if (inner.getBeforeModifyMark().equals(ModifyMarkState.ADDED)) {
				inner.setBeforeModifyMark(ModifyMarkState.ADDED);
			} else {
				inner.setBeforeModifyMark(ModifyMarkState.MODIFIED);
			}
		} else {
			inner.setBeforeModifyMark(ModifyMarkState.ADDED);
		}
		inner.setCompany(CommonVars.getCurrUser().getCompany());
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
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel.setText("企业物料设定");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(0, 102, 51));
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel1.setText("序号");
			jLabel1.setBounds(17, 12, 43, 18);
			jLabel1.setForeground(new java.awt.Color(0, 0, 204));
			jLabel2.setBounds(17, 41, 59, 19);
			jLabel2.setText("商品名称");
			jLabel2.setForeground(new java.awt.Color(0, 0, 204));
			jLabel3.setBounds(194, 12, 58, 20);
			jLabel3.setText("BOM编号");
			jLabel3.setForeground(new java.awt.Color(0, 0, 204));
			jLabel7.setBounds(17, 70, 57, 19);
			jLabel7.setText("型号规格");
			jLabel8.setBounds(17, 100, 51, 19);
			jLabel8.setText("单位");
			jLabel8.setForeground(new java.awt.Color(0, 0, 204));
			jLabel10.setBounds(194, 101, 39, 21);
			jLabel10.setText("单价");
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getTfPtUnitPrice(), null);
			jPanel1.add(getTfPtName(), null);
			jPanel1.add(getBtnOk(), null);
			jPanel1.add(getBtnCancel(), null);
			jPanel1.add(getTfSeqNum(), null);
			jPanel1.add(getTfPtSpec(), null);
			jPanel1.add(getCbbPtUnit(), null);
		}
		jPanel1.add(getTfPtNo(), null);

		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getTfPtUnitPrice() {
		if (tfPtUnitPrice == null) {
			tfPtUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPtUnitPrice.setText("0");
			tfPtUnitPrice.setBounds(254, 101, 104, 22);
		}
		return tfPtUnitPrice;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(77, 40, 281, 22);
		}
		return tfPtName;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(99, 141, 66, 23);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					if (isAdd == true) {
						DzscInnerMergeData innerMergeData = new DzscInnerMergeData();
						fillData(innerMergeData);
						// if (isRe(innerMergeData)){
						// return;
						// }
						innerMergeData = dzscInnerMergeAction
								.saveDzscInnerMergeData(new Request(CommonVars
										.getCurrUser()), innerMergeData);
						tableModel.addRow(innerMergeData);
					} else {
						fillData(innerMergeData);
						// if (isRe(innerMergeData)){
						// return;
						// }
						innerMergeData = dzscInnerMergeAction
								.saveDzscInnerMergeData(new Request(CommonVars
										.getCurrUser()), innerMergeData);
						tableModel.updateRow(innerMergeData);
					}
					DgDzscInnerMergeEdit.this.dispose();
				}
			});

		}
		return btnOk;
	}

	// private boolean isRe(DzscInnerMergeData data){
	// if (dzscAction.isReMerger(new
	// Request(CommonVars.getCurrUser()),head,data)){
	// JOptionPane.showMessageDialog(this, "序号重复！", "提示",2);
	// return true;
	// }
	// return false;
	// }
	private boolean isEmpty() {
		// if (tfSeqNum.getText().equals("")){
		// JOptionPane.showMessageDialog(this,"序号不能为空","提示",2);
		// return true;
		// }
		if (tfPtNo.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "BOM编号不能为空", "提示", 2);
			return true;
		}
		if (tfPtName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空", "提示", 2);
			return true;
		}
		if (cbbPtUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不能为空", "提示", 2);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(202, 141, 64, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscInnerMergeEdit.this.dispose();

				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(77, 13, 97, 22);
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(77, 70, 281, 22);
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPtUnit() {
		if (cbbPtUnit == null) {
			cbbPtUnit = new JComboBox();
			cbbPtUnit.setBounds(77, 98, 97, 22);
		}
		return cbbPtUnit;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(255, 12, 103, 22);
			tfPtNo.setEditable(false);
		}
		return tfPtNo;
	}

	/**
	 * @return Returns the type.
	 */
	public String getMaterialType() {
		return materialType;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setMaterialType(String type) {
		this.materialType = type;
	}
} // @jve:decl-index=0:visual-constraint="10,-12"
