package com.bestway.client.fixasset;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 *
 */
public class DgChangeCommInfoSeqNum extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JFormattedTextField tfOldSeqNum = null;

	private JFormattedTextField tfNewSeqNum = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private NumberFormatter numberFormatter = null; 
	
	private FixAssetAction fixAssetAction = null;
	
	private AgreementCommInfo obj=null;
	
	private JTableListModel tableModel=null;

	private boolean isOk = false;
	
	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModelImg) {
		this.tableModel = tableModelImg;
	}

	public FixAssetAction getFixAssetAction() {
		return fixAssetAction;
	}

	public void setFixAssetAction(FixAssetAction contractAction) {
		this.fixAssetAction = contractAction;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgChangeCommInfoSeqNum() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
		.getBean("fixAssetAction");
	}

	public void setVisible(boolean b){
		if(b){
			this.obj=(AgreementCommInfo)tableModel.getCurrentRow();
			this.tfOldSeqNum.setValue(obj.getMainNo());
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(205, 158));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("变更设备序号");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(26, 49, 40, 22));
			jLabel1.setText("新序号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(26, 17, 40, 23));
			jLabel.setText("旧序号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfOldSeqNum(), null);
			jContentPane.add(getTfNewSeqNum(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(25, 82, 63, 26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfNewSeqNum.getValue() == null) {
						JOptionPane.showMessageDialog(DgChangeCommInfoSeqNum.this,
								"请输入新序号", "提示", JOptionPane.OK_OPTION);
						return;
					}
					int oldSeqNum = (tfOldSeqNum.getValue() == null ? 0
							: Integer
									.valueOf(tfOldSeqNum.getValue().toString()));
					int newSeqNum = (tfNewSeqNum.getValue() == null ? 0
							: Integer
									.valueOf(tfNewSeqNum.getValue().toString()));
					if(newSeqNum==oldSeqNum){
						JOptionPane.showMessageDialog(DgChangeCommInfoSeqNum.this,
								"新序号不能与旧序号相等", "提示", JOptionPane.OK_OPTION);
						return;
					}
					fixAssetAction.changeAgreementCommInfoSeqNum(new Request(CommonVars
							.getCurrUser()), obj,Integer.valueOf(tfNewSeqNum.getValue().toString()));
					isOk=true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(109, 82, 63, 26));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfOldSeqNum() {
		if (tfOldSeqNum == null) {
			tfOldSeqNum = new JFormattedTextField();
			tfOldSeqNum.setBounds(new java.awt.Rectangle(68, 17, 98, 26));
			tfOldSeqNum.setEditable(false);
		}
		return tfOldSeqNum;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNewSeqNum() {
		if (tfNewSeqNum == null) {
			tfNewSeqNum = new JFormattedTextField();
			tfNewSeqNum.setBounds(new java.awt.Rectangle(68, 47, 98, 26));
			tfNewSeqNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNewSeqNum;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
