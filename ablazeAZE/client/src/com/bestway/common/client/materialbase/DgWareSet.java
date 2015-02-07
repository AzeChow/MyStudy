/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgWareSet extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtcode = null;

	private JTextField jtwaretel = null;

	private JTextField jtname = null;

	private JTextField jtwarefax = null;

	private JTextField jtwareaddr = null;

	private JTextField jtwareemail = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private WareSet ware = null;  //  @jve:decl-index=0:

	private JComboBox cbbWareProperty = null;

	/**
	 * This is the default constructor
	 */
	public DgWareSet() {
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
		this.setSize(395, 260);
		this.setTitle("仓库设置编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						ware = (WareSet) tableModel.getCurrentRow();
					}
					fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 4;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 2;
			
			
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("仓库编号");
			jLabel.setBounds(new Rectangle(65, 27, 60, 23));
			jLabel1.setText("联系电话");
			jLabel1.setBounds(new Rectangle(0, 0, 0, 0));
			jLabel2.setText("仓库名称");
			jLabel2.setBounds(new Rectangle(65, 60, 57, 23));
			jLabel3.setText("传真");
			jLabel3.setBounds(new Rectangle(0, 0, 0, 0));
			jLabel4.setText("仓库地址");
			jLabel4.setBounds(new Rectangle(65, 96, 57, 21));
			jLabel5.setText("E-MAIL");
			jLabel5.setBounds(new Rectangle(0, 0, 0, 0));
			jLabel6.setText(" 属  性  ");
			jLabel6.setBounds(new Rectangle(69, 137, 48, 21));
			
			jPanel2.add(jLabel, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField4(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField5(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getCbbWareProperty(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			
			
			
			jPanel2.add(getCbbWareProperty(), gridBagConstraints);
		}
		return jPanel2;
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
	private JTextField getJTextField() {
		if (jtcode == null) {
			jtcode = new JTextField();
			jtcode.setBounds(new Rectangle(137, 27, 198, 22));
		}
		return jtcode;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField1() {
		if (jtwaretel == null) {
			jtwaretel = new JTextField();
			jtwaretel.setBounds(new Rectangle(0, 0, 0, 0));
		}
		return jtwaretel;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (jtname == null) {
			jtname = new JTextField();
			jtname.setBounds(new Rectangle(137, 61, 197, 22));
		}
		return jtname;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField3() {
		if (jtwarefax == null) {
			jtwarefax = new JTextField();
			jtwarefax.setBounds(new Rectangle(0, 0, 0, 0));
		}
		return jtwarefax;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField4() {
		if (jtwareaddr == null) {
			jtwareaddr = new JTextField();
			jtwareaddr.setBounds(new Rectangle(137, 95, 196, 22));
		}
		return jtwareaddr;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField5() {
		if (jtwareemail == null) {
			jtwareemail = new JTextField();
			jtwareemail.setBounds(new Rectangle(0, 0, 0, 0));
		}
		return jtwareemail;
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
			jButton.setText("确定");
			jButton.setBounds(new Rectangle(79, 183, 69, 26));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgWareSet.this.dispose();
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
			jButton1.setText("取消");
			jButton1.setBounds(new Rectangle(217, 183, 73, 25));
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgWareSet.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (ware != null) {
			this.jtcode.setText(ware.getCode());
			this.jtname.setText(ware.getName());
			this.jtwaretel.setText(ware.getWaretel());
			this.jtwarefax.setText(ware.getWarefax());
			this.jtwareaddr.setText(ware.getWareaddr());
			this.jtwareemail.setText(ware.getWareemail());
			
			//默认就为保税
			if(ware.getWareProperty() == null || "".equals(ware.getWareProperty())){
				this.cbbWareProperty.setSelectedIndex(0);
			}else{
				this.cbbWareProperty.setSelectedIndex(ItemProperty.getIndexByCode(ware.getWareProperty(),cbbWareProperty));
			}
			
		}
	}

	private void fillWareSet(WareSet ware) {
		ware.setCode(this.jtcode.getText().trim());
		ware.setName(this.jtname.getText().trim());
		ware.setWaretel(this.jtwaretel.getText().trim());
		ware.setWarefax(this.jtwarefax.getText().trim());
		ware.setWareaddr(this.jtwareaddr.getText().trim());
		ware.setWareemail(this.jtwareemail.getText().trim());
		if(cbbWareProperty.getSelectedItem()!= null){
			ware.setWareProperty(((ItemProperty)cbbWareProperty.getSelectedItem()).getCode());//属性
		}
		ware.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private void clearData() {
		this.jtwaretel.setText("");
		this.jtname.setText("");
		this.jtwarefax.setText("");
		this.jtwareaddr.setText("");
		this.jtwareemail.setText("");
		this.jtcode.setText("");
		this.cbbWareProperty.setSelectedIndex(0);
	}

	private boolean checkNull() {
		if (this.jtcode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "仓库编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.jtname.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "仓库名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		/*if (this.jtwareaddr.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "仓库地址不能为空,请输入!", "提示!", 1);
			return true;
		}*/
		return false;
	}

	private boolean checkMultiple() {
		WareSet warecode = materialManageAction.findWarerByCode(new Request(
				CommonVars.getCurrUser()), this.jtcode.getText().trim());
		if (warecode != null) {
			if (!isAdd) {
				if (!warecode.getId().equals(ware.getId())) {
					JOptionPane.showMessageDialog(this, "仓库编号不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane
						.showMessageDialog(this, "仓库编号不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		WareSet warename = materialManageAction.findWarerByName(new Request(
				CommonVars.getCurrUser()), this.jtname.getText().trim());
		if (warename != null) {
			if (!isAdd) {
				if (!warename.getId().equals(ware.getId())) {
					JOptionPane.showMessageDialog(this, "仓库名称不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane
						.showMessageDialog(this, "仓库名称不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		return false;
	}

	private void addData() {
		WareSet ware = new WareSet();
		fillWareSet(ware);

		ware = materialManageAction.saveWare(
				new Request(CommonVars.getCurrUser()), ware);
		tableModel.addRow(ware);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillWareSet(ware);
		ware = materialManageAction.saveWare(
				new Request(CommonVars.getCurrUser()), ware);
		tableModel.updateRow(ware);

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
	 * @return Returns the ware.
	 */
	public WareSet getWare() {
		return ware;
	}

	/**
	 * @param ware
	 *            The ware to set.
	 */
	public void setWare(WareSet ware) {
		this.ware = ware;
	}

	/**
	 * This method initializes cbbWareProperty	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbWareProperty() {
		if (cbbWareProperty == null) {
			cbbWareProperty = new JComboBox();
			cbbWareProperty.addItem(new ItemProperty("0", "保税"));
			cbbWareProperty.addItem(new ItemProperty("1", "非保税"));

			cbbWareProperty.setRenderer(CustomBaseRender.getInstance().getRender());
			cbbWareProperty.setBounds(new Rectangle(137, 134, 195, 27));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbWareProperty);
			cbbWareProperty.setSelectedIndex(0);
		}
		return cbbWareProperty;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
