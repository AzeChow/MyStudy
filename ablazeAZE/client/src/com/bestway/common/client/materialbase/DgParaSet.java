/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Parame;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgParaSet extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private ParaSet obj = null; //

	private JTextField tfName = null;

	private JTextField tfSign = null;

	private JComboBox cbbCustoms = null;

	/**
	 * This is the default constructor
	 */
	public DgParaSet() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(395, 205);
		this.setTitle("基本参数对应设置");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				
			}
		});
	}

	@Override
	public void setVisible(boolean b){
		if(b){
			initUICompoments();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					obj = (ParaSet) tableModel.getCurrentRow();
				}
				fillWindow();
			}
		}
		super.setVisible(b);
	}
	private void initUICompoments() {
		
		this.cbbCustoms.removeAllItems();
        this.cbbCustoms.addItem(new ItemProperty(
                String.valueOf(Parame.jizhuangxiangcode), Parame.jizhuangxiangname));
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
        this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
        this.cbbCustoms.setSelectedIndex(0);
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("类型");
			jLabel.setBounds(40, 16, 33, 23);
			jLabel2.setText("原始值");
			jLabel2.setBounds(40, 41, 57, 23);
			jLabel5.setText("对应值");
			jLabel5.setBounds(40, 67, 59, 23);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfName(), null);
			jPanel2.add(getTfSign(), null);
			jPanel2.add(getCbbCustoms(), null);
		}
		return jPanel2;
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
			jButton.setText("确定(S)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setBounds(84, 119, 75, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgParaSet.this.dispose();
					}

				}
			});

		}
		return jButton;
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
			jButton1.setText("取消(X)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_X);
			jButton1.setBounds(186, 119, 74, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgParaSet.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (obj != null) {
			this.tfName.setText(obj.getBeginValue());
			this.tfSign.setText(obj.getAfterValue());
			this.cbbCustoms.setSelectedIndex(ItemProperty.getIndexByCode(String
                    .valueOf(this.obj.getType()),
                    cbbCustoms));
			

		}
	}



	private void fillCurrCode(ParaSet obj) {
	    obj.setBeginValue(tfName.getText());
	    obj.setAfterValue(tfSign.getText());
		if (this.cbbCustoms.getSelectedItem() != null) {
			obj.setType(Integer
                    .valueOf(((ItemProperty) this.cbbCustoms
                            .getSelectedItem()).getCode()));
		} else {
			obj.setType(0);
		}
		obj.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private void clearData() {
		this.tfName.setText("");
		this.tfSign.setText("");		
		this.cbbCustoms.setSelectedItem(null);
		
	}

	class CustomDocument extends PlainDocument {
		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 10 || str.length() > 10
					|| super.getLength() + str.length() > 10) {
				return;
			}
			super.insertString(offs, str, a);
		}
	}

	private void addData() {
		ParaSet obj = new ParaSet();
		fillCurrCode(obj);

		obj = materialManageAction.saveParaSet(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.addRow(obj);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCurrCode(obj);
		obj = materialManageAction.saveParaSet(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.updateRow(obj);
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
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * 
	 * This method initializes tfName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(130, 43, 162, 22);
		}
		return tfName;
	}

	/**
	 * 
	 * This method initializes tfSign
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSign() {
		if (tfSign == null) {
			tfSign = new JTextField();
			tfSign.setBounds(130, 69, 162, 22);
		}
		return tfSign;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new java.awt.Rectangle(131,12,161,24));
		}
		return cbbCustoms;
	}
} // @jve:decl-index=0:visual-constraint="100,100"
