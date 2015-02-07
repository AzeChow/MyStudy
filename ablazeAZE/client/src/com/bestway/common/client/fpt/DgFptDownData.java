package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DownLoadState;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptDownData;
import com.bestway.jptds.common.AppException;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptDownData extends JDialogBase {

	private JPanel jPanel = null;

	private JTextField tfCustomUniteNumber = null;

	private JTextField tfTradeNumber = null;

	private JToolBar jJToolBarBar = null;

	private JComboBox cbbDownLoadState = null;

	private JLabel jLabel2 = null;

	private JButton btnSave = null;

	private String seqNo = null;

	private String tradeCode = null;

	private JTableListModel tableModel = null;

	private FptManageAction fptManageAction = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	// private int dataState2 = DataState.BROWSE;

	private FptDownData data = null;

	private int isSaveormodfiy = 1; // @jve:decl-index=0:

	private JButton btnUndo = null;

	private JButton btnClose = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfCopBillNo = null;

	private JTextField tfAppNo = null;

	private JLabel jLabel5 = null;

	private JTextField tfNote = null;

	private JButton btnEdit = null;

	private SystemAction service = null; // @jve:decl-index=0:

	private String downLoadState = null; // @jve:decl-index=0:

	private String inOutFlag = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFptDownData() {
		super();
		initialize();
		initUIComponents();
	}

	public DgFptDownData(String downLoadState, String inOutFlag) {
		super();
		this.downLoadState = downLoadState;
		this.inOutFlag = inOutFlag;
		initialize();
		initUIComponents();

	}

	private void initUIComponents() {
		cbbDownLoadState.removeAllItems();
		if (this.downLoadState != null) {
			cbbDownLoadState.addItem(new ItemProperty(downLoadState,
					DownLoadState.getDownLoadStateSpec(this.downLoadState)));
		} else {
			cbbDownLoadState.addItem(new ItemProperty("A", "结转申请表"));
			cbbDownLoadState.addItem(new ItemProperty("B", "退货单"));
			cbbDownLoadState.addItem(new ItemProperty("C", "收发货单")); 
		}

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDownLoadState);
		this.cbbDownLoadState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbDownLoadState.setSelectedIndex(0);
		// 本企业编码
		List list = null;
		list = service.findCompaniesCurr();
		if (list != null && list.size() >=1) {
			Company company = (Company) list.get(0);
			tfTradeNumber.setText(company.getCode());
		}else{
			throw new AppException("请在【企业设置】中设置当前公司！");
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(443, 322));
		this.setContentPane(getJPanel());
		this.setTitle("请输入要下载的对方单据信息");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		service = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(24, 226, 111, 18));
			jLabel5.setText("备注");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(24, 193, 111, 18));
			jLabel4.setText("申请表编号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(24, 159, 111, 18));
			jLabel3.setText("对方企业内部编号");
			jLabel3.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(24, 122, 111, 18));
			jLabel1.setText("本企业编号");
			jLabel1.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 85, 127, 18));
			jLabel.setText("对方电子口岸统一编号");
			jLabel.setForeground(Color.blue);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(24, 50, 111, 18));
			jLabel2.setText("数据类型");
			jLabel2.setForeground(Color.blue);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getTfCustomUniteNumber(), BorderLayout.WEST);
			jPanel.add(getTfTradeNumber(), BorderLayout.EAST);
			jPanel.add(getJJToolBarBar(), null);
			jPanel.add(getCbbDownLoadState(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfCopBillNo(), null);
			jPanel.add(getTfAppNo(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfNote(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfCustomUniteNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomUniteNumber() {
		if (tfCustomUniteNumber == null) {
			tfCustomUniteNumber = new JTextField();
			tfCustomUniteNumber.setBounds(new Rectangle(150, 86, 194, 20));
		}
		tfCustomUniteNumber.setDocument(new CustomDocument(18));
		return tfCustomUniteNumber;
	}
	
	 class CustomDocument extends PlainDocument {

	        int len;

	        public CustomDocument(int len) {
	            this.len = len;
	        }

	        public void insertString(int offs, String str, AttributeSet a)
	                throws BadLocationException {
	            if (str == null) {
	                return;
	            }
	            if (super.getLength() >= len || str.length() > len
	                    || super.getLength() + str.length() > len) {
	                return;
	            }

	            super.insertString(offs, str, a);
	        }
	    }


	/**
	 * This method initializes tfTradeNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeNumber() {
		if (tfTradeNumber == null) {
			tfTradeNumber = new JTextField();
			tfTradeNumber.setBounds(new Rectangle(150, 121, 194, 20));
		}
		return tfTradeNumber;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setBounds(new Rectangle(2, 0, 509, 31));
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnSave());
			jJToolBarBar.add(getBtnUndo());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes cbbDownLoadState
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDownLoadState() {
		if (cbbDownLoadState == null) {
			cbbDownLoadState = new JComboBox();
			cbbDownLoadState.setBounds(new Rectangle(150, 47, 194, 23));
			cbbDownLoadState.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
				}
			});
		}
		return cbbDownLoadState;
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
					saveData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	protected boolean validateData() {
		if (cbbDownLoadState.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgFptDownData.this, "【数据类型】 不能为空",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (CommonUtils.isEmpty(tfCustomUniteNumber.getText())) {
			JOptionPane.showMessageDialog(DgFptDownData.this,
					"【对方电子口岸统一编号】 不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (CommonUtils.isEmpty(tfTradeNumber.getText())) {
			JOptionPane.showMessageDialog(DgFptDownData.this, "【本企业编号】 不能为空",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (CommonUtils.isEmpty(tfCopBillNo.getText())) {
			JOptionPane.showMessageDialog(DgFptDownData.this,
					"【对方企业内部编号】 不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (cbbDownLoadState.getSelectedItem() != null) {
			if ((tfCustomUniteNumber.getText() == null || ""
					.equals(tfCustomUniteNumber.getText()))) {
				JOptionPane.showMessageDialog(DgFptDownData.this,
						"当选择了数据类型后请输入统一编号", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

		}
		return true;
	}

	private void saveData() {
		if (dataState == DataState.ADD) {
			FptDownData data = new FptDownData();
			fillData(data);
			data = fptManageAction.saveFptDownData(
					new Request(CommonVars.getCurrUser()), data);
			tableModel.addRow(data);
		} else if (dataState == DataState.EDIT) {
			fillData(data);
			data = fptManageAction.saveFptDownData(
					new Request(CommonVars.getCurrUser()), data);
			tableModel.updateRow(data);
		}
	}

	private void fillData(FptDownData data) {
		data.setSeqNo(this.tfCustomUniteNumber.getText());
		data.setTradeCode(this.tfTradeNumber.getText());
		if (data.getId() == null || "".equals(data.getId().trim())) {
			data.setDeclareState(DeclareState.APPLY_POR);
		}
		if (cbbDownLoadState.getSelectedItem() != null) {
			data.setDownLoadState(((ItemProperty) this.cbbDownLoadState
					.getSelectedItem()).getCode());
		} else {
			data.setDownLoadState(null);
		}
		data.setInOutFlag(inOutFlag);
		data.setAppNo(this.tfAppNo.getText().trim());
		data.setOutCopNo(this.tfCopBillNo.getText().trim());
		data.setNote(this.tfNote.getText().trim());
	}

	public void doModify(FptDownData data) {
		fillData(data);
		data = fptManageAction.saveFptDownData(
				new Request(CommonVars.getCurrUser()), data);
		tableModel.updateRow(data);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (dataState != DataState.ADD) {
				if (tableModel != null) {
					data = (FptDownData) tableModel.getCurrentRow();
				}
				showHeadData(data);
			}
			setState();
		}
		super.setVisible(b);
	}

	public void showHeadData(FptDownData data) {
		if (data == null) {
			return;
		}
		this.tfCustomUniteNumber.setText(data.getSeqNo());
		this.tfTradeNumber.setText(data.getTradeCode());
		this.cbbDownLoadState.setSelectedIndex(ItemProperty.getIndexByCode(
				data.getDownLoadState(), cbbDownLoadState));
		this.cbbDownLoadState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.tfCopBillNo.setText(data.getOutCopNo());
		this.tfAppNo.setText(data.getAppNo());
		this.tfNote.setText(data.getNote());
	}

	private void setState() {
		if (data != null) {
			this.btnEdit
					.setEnabled(dataState == DataState.BROWSE
							&& (DeclareState.APPLY_POR.equals(data
									.getDeclareState()) || DeclareState.CHANGING_EXE
									.equals(data.getDeclareState())));
		} else {
			this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		}
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnUndo.setEnabled(dataState != DataState.BROWSE);
		this.tfCustomUniteNumber.setEditable(dataState != DataState.BROWSE);
		this.tfTradeNumber.setEditable(dataState != DataState.BROWSE);
		// this.cbbDownLoadState.setEnabled(dataState != DataState.BROWSE);
		this.cbbDownLoadState.setEnabled(false);
		this.tfCopBillNo.setEnabled(dataState != DataState.BROWSE);
		this.tfAppNo.setEnabled(dataState != DataState.BROWSE);
		this.tfNote.setEnabled(dataState != DataState.BROWSE);
	}

	public void clearData() {
		this.tfCustomUniteNumber.setText("");
		this.tfTradeNumber.setText("");
		this.cbbDownLoadState.setSelectedItem(null);
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public String getBusinessType() {
		return downLoadState;
	}

	public void setBusinessType(String businessType) {
		this.downLoadState = businessType;
	}

	/**
	 * This method initializes btnUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData(data);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("\u5173\u95ed");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFptDownData.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes tfCopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopBillNo() {
		if (tfCopBillNo == null) {
			tfCopBillNo = new JTextField();
			tfCopBillNo.setBounds(new Rectangle(150, 156, 194, 22));
		}
		return tfCopBillNo;
	}

	/**
	 * This method initializes tfAppNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(150, 189, 194, 22));
		}
		return tfAppNo;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(150, 224, 254, 22));
		}
		return tfNote;
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

} // @jve:decl-index=0:visual-constraint="10,10"
