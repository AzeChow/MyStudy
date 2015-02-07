/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscFourInnerMerge extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private DzscInnerMergeData innerMergeData = null;

	// private DzscInnerMergeHead head = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTextField tfFourPtName = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JTextField tfFourSeqNum = null;

	private JTextField tfFourPtSpec = null;

	private JComboBox cbbFourUnit = null;

	private NumberFormatter numberFormatter = null;

	private String materialType = null;

	private MultiSpanCellTable jTable = null;

	private List currentRows = null; // @jve:decl-index=0:

	private boolean isCombineRows = false;

	private JLabel jLabel3 = null;

	private JTextField tfFourComplex = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscFourInnerMerge() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
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
		this.setSize(313, 272);
		this.setTitle("归并类别设置");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (currentRows != null) {
					initUI();
					DzscInnerMergeData data = (DzscInnerMergeData) currentRows
							.get(0);
					tfFourSeqNum.setText(String.valueOf(data
							.getDzscTenInnerMerge().getDzscFourInnerMerge()
							.getFourSeqNum()));
					// if (data.getLevyMode() != null) {
					// jComboBox1.setSelectedItem(data.getLevyMode());
					// } else {
					// jComboBox1.setSelectedIndex(-1);
					// }
					tfFourComplex.setText(String.valueOf(data
							.getDzscTenInnerMerge().getDzscFourInnerMerge()
							.getFourComplex()));
					tfFourPtName.setText(data.getDzscTenInnerMerge()
							.getDzscFourInnerMerge().getFourPtName());
					tfFourPtSpec.setText(data.getDzscTenInnerMerge()
							.getDzscFourInnerMerge().getFourPtSpec());
					if (data.getDzscTenInnerMerge().getDzscFourInnerMerge()
							.getFourUnit() != null) {
						cbbFourUnit.setSelectedItem(data.getDzscTenInnerMerge()
								.getDzscFourInnerMerge().getFourUnit());
					} else {
						cbbFourUnit.setSelectedIndex(-1);
					}
					// jFormattedTextField9
					// .setText(doubleToStr(data.getLowPrice()));
				}
			}
		});
	}

	private void initUI() {
		// 初始化单位
		List list = CustomBaseList.getInstance().getUnits();
		this.cbbFourUnit.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbFourUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFourUnit, "code", "name");
		// 初始化征免性质(数据来自征免方式)
		List list1 = CustomBaseList.getInstance().getLevymode();
		// this.jComboBox1.setModel(new DefaultComboBoxModel(list1.toArray()));
		// this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
		// "code", "name"));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.jComboBox1, "code", "name");

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
			jLabel.setText("归并类别设置");
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(18, 44, 52, 15));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("商品编码");
			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel1.setText("序号");
			jLabel1.setBounds(17, 12, 43, 18);
			jLabel1.setForeground(new java.awt.Color(0, 0, 204));
			jLabel2.setBounds(19, 71, 59, 19);
			jLabel2.setText("商品名称");
			jLabel2.setForeground(new java.awt.Color(0, 0, 204));
			jLabel7.setBounds(19, 100, 57, 19);
			jLabel7.setText("型号规格");
			jLabel8.setBounds(19, 130, 51, 19);
			jLabel8.setText("单位");
			jLabel8.setForeground(new java.awt.Color(0, 0, 204));
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getTfFourPtName(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getTfFourSeqNum(), null);
			jPanel1.add(getTfFourPtSpec(), null);
			jPanel1.add(getCbbFourUnit(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfFourComplex(), null);
		}
		return jPanel1;
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
	private JTextField getTfFourPtName() {
		if (tfFourPtName == null) {
			tfFourPtName = new JTextField();
			tfFourPtName.setBounds(79, 70, 193, 22);
		}
		return tfFourPtName;
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(66, 168, 66, 23);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					if (currentRows.size() <= 0) {
						return;
					}
					DzscInnerMergeData data = (DzscInnerMergeData) currentRows
							.get(0);
					DzscFourInnerMerge fourInnerMerge = data
							.getDzscTenInnerMerge().getDzscFourInnerMerge();
					fourInnerMerge.setFourComplex(tfFourComplex.getText()
							.trim());
					fourInnerMerge.setFourPtName(tfFourPtName.getText().trim());
					fourInnerMerge.setFourPtSpec(tfFourPtSpec.getText().trim());
					fourInnerMerge.setFourUnit((Unit) cbbFourUnit
							.getSelectedItem());
					List ls = dzscInnerMergeAction.editFourInnerMerge(
							new Request(CommonVars.getCurrUser()),
							fourInnerMerge);
					refreshTable(ls);
					DgDzscFourInnerMerge.this.dispose();
				}
			});

		}
		return jButton;
	}

//	private void refreshTable(List ls) {
//		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
//				.getModel();
//		tableModel.updateRows(ls);
//		if (this.isCombineRows) {
//			((MultiSpanCellTable) jTable).combineRows(new int[] { 12, 7 },
//					new int[] { 12, 13, 14, 15, 16 });// , 17, 18
//			((MultiSpanCellTable) jTable).combineRows(7, new int[] { 7, 8, 9,
//					10, 11 });
//		} else {
//			((MultiSpanCellTable) jTable).splitRows(new int[] { 12, 7 });
//		}
//	}
	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
				.getModel();
		tableModel.updateRows(ls);
		if (this.isCombineRows) {
			((MultiSpanCellTable) jTable).combineRows(new int[] { 14, 9 },
					new int[] { 14, 15, 16, 17, 18 });
			((MultiSpanCellTable) jTable).combineRows(9, new int[] { 8, 9,
					10, 11, 12, 13 });
		} else {
			((MultiSpanCellTable) jTable).splitRows(new int[] { 9, 14 });
		}
	}
	/*
	 * private boolean isRe(DzscInnerMergeData data) { if
	 * (dzscAction.isReMerger(new Request(CommonVars.getCurrUser()), head,
	 * data)) { JOptionPane.showMessageDialog(this, "序号重复！", "提示", 2); return
	 * true; } return false; }
	 */

	private boolean isEmpty() {
		/*
		 * if (jTextField1.getText().equals("")) {
		 * JOptionPane.showMessageDialog(this, "序号不能为空", "提示", 2); return true; }
		 */
		if (tfFourComplex.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品编码不能为空", "提示", 2);
			return true;
		}
		if (tfFourPtName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空", "提示", 2);
			return true;
		}
		if (cbbFourUnit.getSelectedItem() == null) {
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(169, 168, 64, 23);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscFourInnerMerge.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourSeqNum() {
		if (tfFourSeqNum == null) {
			tfFourSeqNum = new JTextField();
			tfFourSeqNum.setBounds(77, 13, 193, 22);
			tfFourSeqNum.setEditable(false);
		}
		return tfFourSeqNum;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourPtSpec() {
		if (tfFourPtSpec == null) {
			tfFourPtSpec = new JTextField();
			tfFourPtSpec.setBounds(79, 100, 193, 22);
		}
		return tfFourPtSpec;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFourUnit() {
		if (cbbFourUnit == null) {
			cbbFourUnit = new JComboBox();
			cbbFourUnit.setBounds(79, 128, 193, 22);
		}
		return cbbFourUnit;
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

	// /**
	// * @return Returns the head.
	// */
	// public DzscInnerMergeHead getHead() {
	// return head;
	// }
	//
	// /**
	// * @param head
	// * The head to set.
	// */
	// public void setHead(DzscInnerMergeHead head) {
	// this.head = head;
	// }

	/**
	 * @return Returns the currentRows.
	 */
	public List getCurrentRows() {
		return currentRows;
	}

	/**
	 * @param currentRows
	 *            The currentRows to set.
	 */
	public void setCurrentRows(List currentRows) {
		this.currentRows = currentRows;
	}

	/**
	 * @return Returns the jTable.
	 */
	public MultiSpanCellTable getJTable() {
		return jTable;
	}

	/**
	 * @param table
	 *            The jTable to set.
	 */
	public void setJTable(MultiSpanCellTable table) {
		jTable = table;
	}

	public boolean isCombineRows() {
		return isCombineRows;
	}

	public void setCombineRows(boolean isCombineRows) {
		this.isCombineRows = isCombineRows;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFourComplex() {
		if (tfFourComplex == null) {
			tfFourComplex = new JTextField();
			tfFourComplex.setBounds(new Rectangle(77, 39, 194, 23));
		}
		return tfFourComplex;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
