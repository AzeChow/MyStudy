package com.bestway.common.client.fpt;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
* @author Administrator
* 
* // change the template for this generated type comment go to Window -
* Preferences - Java - Code Style - Code Templates
*/
public class DgFptAppCancel extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;
	
	private JTextArea tfNote;
	private FptManageAction fptManageAction = null;
	
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * This is the default constructor
	 */
	public DgFptAppCancel() {
		super();
		setTitle("申请表作废原因");
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();

	}

	private void initUIComponents() {

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(427, 192);
		fptManageAction.findTransferPlan(new Request(CommonVars
				.getCurrUser()));
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				initUIComponents();
			}
		});
	}

	
	public JButton btnOk;
	public JButton btnCancel;
	
	
	

	
	private JButton getBtnOk(){
		if(btnOk == null ){
			btnOk = new JButton("确认");
			btnOk.setBounds(90, 114, 93, 23);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if( tfNote.getText()!=null && !"".equals(tfNote.getText().trim())){
						setNote(tfNote.getText().trim());
						DgFptAppCancel.this.dispose();
					}else{
						JOptionPane.showMessageDialog(DgFptAppCancel.this, "请输入作废原因", "提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			});
		}
		
		return btnOk;
	}
	
	
	
	
	private JButton getBtnCancel(){
		if(btnCancel == null ){
			btnCancel = new JButton("取消");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgFptAppCancel.this.dispose();
				}
			});
			btnCancel.setBounds(294, 114, 93, 23);
			
		}
		return btnCancel;
	}
	
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			
			
			
			JLabel label = new JLabel("作废原因：");
			label.setForeground(Color.RED);
			label.setFont(new Font("SimSun", Font.BOLD, 12));
			label.setBounds(10, 35, 70, 21);
			jContentPane.add(label);
			
			jContentPane.add(getBtnCancel());
			jContentPane.add(getBtnOk());
			
			tfNote = new JTextArea();
			tfNote.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			tfNote.setBounds(90, 34, 297, 55);
			jContentPane.add(tfNote);
		}
		return jContentPane;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
