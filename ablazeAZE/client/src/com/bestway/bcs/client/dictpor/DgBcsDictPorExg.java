package com.bestway.bcs.client.dictpor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DocumentControlByGbkByte;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBcsDictPorExg extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfSeqNum = null;

	private JLabel jLabel1 = null;

	private JTextField tfComplex = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JTextField tfCommName = null;

	private JTextField tfCommSpec = null;

	private JToolBar jToolBar = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JButton btnUndo = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnClose = null;

	private JLabel jLabel111 = null;

	private JLabel jLabel112 = null;

	private JLabel jLabel113 = null;

	private JLabel jLabel114 = null;

	private JLabel jLabel115 = null;

	private JLabel jLabel116 = null;

	private JLabel jLabel117 = null;

	private JTextField tfFirstUnit = null;

	private JTextField tfModifyMark = null;

	private JTextField tfMemo = null;

	private JComboBox cbbUnit = null;

	private JComboBox cbbCurr = null;

	private JFormattedTextField tfUnitPrice = null;

	private int dataState = DataState.BROWSE;

	private JTableListModel tableModel = null;

	private Complex complex = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private ContractAction contractAction = null; // 合同

	private JButton btnComplex = null;

	private BcsDictPorHead bcsDictPorHead = null;

	private Complex tmpComplex = null; // @jve:decl-index=0:

	private JLabel jLabel2 = null;

	private JFormattedTextField tfInnerMergeSeqNum = null;

	public BcsDictPorHead getBcsDictPorHead() {
		return bcsDictPorHead;
	}

	public void setBcsDictPorHead(BcsDictPorHead bcsDictPorHead) {
		this.bcsDictPorHead = bcsDictPorHead;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBcsDictPorExg() {
		super();
		initialize();
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initUIComponents();
		BcsParameterSet parameterSet = contractAction
				.findBcsParameterSet(new Request(CommonVars.getCurrUser(), true));

		if (parameterSet.getIsControlLength() != null
				&& parameterSet.getIsControlLength()) {
			tfCommName.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
			tfCommSpec.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
		}
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(526, 309));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("备案资料库成品");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(245, 180, 75, 24));
			jLabel2.setText("归并序号");
			jLabel117 = new JLabel();
			jLabel117.setBounds(new Rectangle(30, 215, 51, 22));
			jLabel117.setText("备注");
			jLabel116 = new JLabel();
			jLabel116.setBounds(new Rectangle(29, 179, 54, 22));
			jLabel116.setText("修改标志");
			jLabel116.setForeground(Color.blue);
			jLabel115 = new JLabel();
			jLabel115.setBounds(new Rectangle(244, 147, 76, 22));
			jLabel115.setText("币制");
			jLabel114 = new JLabel();
			jLabel114.setBounds(new Rectangle(28, 145, 54, 22));
			jLabel114.setText("申报单价");
			jLabel113 = new JLabel();
			jLabel113.setBounds(new Rectangle(29, 116, 54, 22));
			jLabel113.setText("计量单位");
			jLabel113.setForeground(Color.blue);
			jLabel112 = new JLabel();
			jLabel112.setBounds(new Rectangle(244, 117, 76, 22));
			jLabel112.setText("法定计量单位");
			jLabel112.setForeground(Color.blue);
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(-95, 104, 48, 15));
			jLabel111.setText("商品名称");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(244, 84, 76, 22));
			jLabel12.setText("型号规格");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(29, 85, 54, 22));
			jLabel11.setText("商品名称");
			jLabel11.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(244, 51, 76, 22));
			jLabel1.setText("商品编码");
			jLabel1.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(29, 52, 54, 22));
			jLabel.setText("成品序号");
			jLabel.setForeground(Color.blue);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getTfCommName(), null);
			jContentPane.add(getTfCommSpec(), null);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel111, null);
			jContentPane.add(jLabel112, null);
			jContentPane.add(jLabel113, null);
			jContentPane.add(jLabel114, null);
			jContentPane.add(jLabel115, null);
			jContentPane.add(jLabel116, null);
			jContentPane.add(jLabel117, null);
			jContentPane.add(getTfFirstUnit(), null);
			jContentPane.add(getTfModifyMark(), null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(getCbbCurr(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfInnerMergeSeqNum(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(new Rectangle(82, 51, 147, 25));
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(321, 50, 143, 24));
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new Rectangle(82, 83, 147, 25));
		}
		return tfCommName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(new Rectangle(321, 81, 165, 24));
		}
		return tfCommSpec;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(1, 1, 514, 32));
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BcsDictPorExg exg = (BcsDictPorExg) tableModel
							.getCurrentRow();
					if (exg == null) {
						return;
					}
					fillData(exg);
					exg = bcsDictPorAction.saveBcsDictPorExg(new Request(
							CommonVars.getCurrUser()), exg);
					tableModel.updateRow(exg);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton3
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
	 * This method initializes jButton4
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

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jTextField31
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFirstUnit() {
		if (tfFirstUnit == null) {
			tfFirstUnit = new JTextField();
			tfFirstUnit.setBounds(new Rectangle(321, 114, 165, 24));
			tfFirstUnit.setEditable(false);
		}
		return tfFirstUnit;
	}

	/**
	 * This method initializes jTextField32
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfModifyMark() {
		if (tfModifyMark == null) {
			tfModifyMark = new JTextField();
			tfModifyMark.setBounds(new Rectangle(82, 179, 147, 25));
			tfModifyMark.setEditable(false);
		}
		return tfModifyMark;
	}

	/**
	 * This method initializes jTextField33
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(new Rectangle(82, 212, 407, 24));
			tfMemo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (super.getLength() >= 10 || str.getBytes().length > 10
							|| (super.getLength() + str.getBytes().length) > 10) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfMemo;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(82, 114, 147, 25));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(321, 145, 165, 24));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new JFormattedTextField();
			tfUnitPrice.setBounds(new Rectangle(82, 146, 147, 25));
		}
		return tfUnitPrice;
	}

	private void setState() {
		BcsDictPorExg exg = (BcsDictPorExg) tableModel.getCurrentRow();
		if (exg == null) {
			return;
		}
		String modifyMark = exg.getModifyMark();
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnUndo.setEnabled(dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.tfCommName.setEditable(dataState != DataState.BROWSE
				&& ModifyMarkState.ADDED.equals(modifyMark));
		this.tfCommSpec.setEditable(dataState != DataState.BROWSE);
		this.cbbUnit.setEnabled(dataState != DataState.BROWSE);
		this.cbbCurr.setEnabled(dataState != DataState.BROWSE);
		this.tfUnitPrice.setEditable(dataState != DataState.BROWSE);
		this.tfMemo.setEditable(dataState != DataState.BROWSE);
		this.tfInnerMergeSeqNum.setEditable(dataState != DataState.BROWSE);
	}

	private void showData() {
		BcsDictPorExg exg = (BcsDictPorExg) tableModel.getCurrentRow();
		if (exg == null) {
			return;
		}
		this.tfSeqNum.setText(exg.getSeqNum().toString());
		this.complex = exg.getComplex();
		this.tfComplex.setText(exg.getComplex().getCode());
		this.tfCommName.setText(exg.getCommName());
		this.tfCommSpec.setText(exg.getCommSpec());
		this.cbbUnit.setSelectedItem(exg.getComUnit());
		this.tfFirstUnit.setText(exg.getComplex().getFirstUnit().getName());
		this.cbbCurr.setSelectedItem(exg.getCurr());
		this.tfUnitPrice.setValue(exg.getUnitPrice());
		this.tfMemo.setText(exg.getMemo());
		this.tfModifyMark.setText(ModifyMarkState.getModifyMarkSpec(exg
				.getModifyMark()));
		this.tfInnerMergeSeqNum.setValue(exg.getInnerMergeSeqNum());
	}

	private void fillData(BcsDictPorExg exg) {
		if (exg == null) {
			return;
		}
		exg.setComplex(this.complex);
		exg.setCommName(this.tfCommName.getText().trim());
		exg.setCommSpec(this.tfCommSpec.getText().trim());
		exg.setComUnit((Unit) this.cbbUnit.getSelectedItem());
		exg.setCurr((Curr) this.cbbCurr.getSelectedItem());
		exg.setUnitPrice(this.tfUnitPrice.getValue() == null ? 0.0 : Double
				.valueOf(this.tfUnitPrice.getValue().toString()));
		exg.setMemo(this.tfMemo.getText().trim());
		if (this.tfInnerMergeSeqNum.getValue() != null) {
			exg.setInnerMergeSeqNum(Double.valueOf(
					this.tfInnerMergeSeqNum.getValue().toString()).intValue());
		} else {
			exg.setInnerMergeSeqNum(null);
		}
		if (ModifyMarkState.UNCHANGE.equals(exg.getModifyMark())
				|| ModifyMarkState.DELETED.equals(exg.getModifyMark())) {
			exg.setModifyMark(ModifyMarkState.MODIFIED);
		}
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 初始化国
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr);
		this.cbbCurr.setSelectedItem(null);

		// 单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		this.cbbUnit.setSelectedItem(null);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfCommName);
		components.add(null);
		components.add(this.tfMemo);
		components.add(this.btnSave);
		components.add(new Component[] { this.btnNext, this.btnClose });
		this.setComponentFocusList(components);

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice, 5);

		// 归并序号
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfInnerMergeSeqNum, 0);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(464, 50, 21, 24));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (obj != null) {
						complex = obj;
						tfComplex.setText(complex.getCode());
						BcsDictPorExg exg = (BcsDictPorExg) tableModel
								.getCurrentRow();
						String modifyMark = exg.getModifyMark();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfCommName.setText(complex.getName());
							tfCommSpec.setText(complex.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes tfInnerMergeSeqNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInnerMergeSeqNum() {
		if (tfInnerMergeSeqNum == null) {
			tfInnerMergeSeqNum = new JFormattedTextField();
			tfInnerMergeSeqNum.setBounds(new Rectangle(321, 179, 165, 24));
		}
		return tfInnerMergeSeqNum;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
