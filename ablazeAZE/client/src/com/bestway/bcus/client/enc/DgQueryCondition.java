/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbCustomer = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private EncAction encAction = null;

	private List lsResult = null;

	private boolean isImport;

	private JTextField jTextField = null;

	private JLabel jLabel = null;

	private JLabel jLabel2 = null;

	private JCheckBox jCheckBox = null;

	private int type = -1;

	private MaterialManageAction materialManageAction = null;

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgQueryCondition() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
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
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		cbbCustomer.setSelectedIndex(-1);
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
		this.setSize(379, 248);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(41, 25, 93, 24);
			jLabel1.setText("客户/供应商");
			jLabel3.setBounds(41, 87, 93, 24);
			jLabel3.setText("生效日期");
			jLabel4.setBounds(231, 89, 13, 18);
			jLabel4.setText("至");
			jLabel.setBounds(41, 56, 93, 20);
			jLabel.setText("单据号");
			jLabel2.setBounds(41, 126, 92, 22);
			jLabel2.setText("是否已全转清单");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJCheckBox(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(139, 25, 197, 22);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(139, 88, 89, 24);
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
			cbbEndDate.setBounds(245, 88, 90, 23);
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
			jButton.setBounds(106, 166, 81, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String customer = null;
					Boolean iscustoms = null;
					if (cbbCustomer.getSelectedItem() != null) {
						customer = ((ScmCoc) cbbCustomer.getSelectedItem())
								.getId();
					}
					if (jCheckBox.isSelected()) {
						iscustoms = Boolean.valueOf(true);
					} else {
						iscustoms = Boolean.valueOf(false);
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					lsResult = encAction.findImpExpRequestBillByType(
							new Request(CommonVars.getCurrUser()), type,
							iscustoms, jTextField.getText().trim(), beginDate,
							endDate, customer);
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(214, 166, 83, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(139, 56, 197, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(144, 124, 27, 28);
		}
		return jCheckBox;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
