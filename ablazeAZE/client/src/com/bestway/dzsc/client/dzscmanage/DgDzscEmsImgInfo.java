package com.bestway.dzsc.client.dzscmanage;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.awt.FlowLayout;

public class DgDzscEmsImgInfo extends JDialogBase{

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel					tableModel					= null;
	private JButton btnOk = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgDzscEmsImgInfo() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes 
	 * 
	 */
	public DgDzscEmsImgInfo(List imgs) {
		super();
		initialize();
		this.initTable(imgs);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(603, 259));
        this.setContentPane(getJPanel());
		this.setTitle("成品耗用料件进出口数量，金额情况");	
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout());
			jPanel1.setPreferredSize(new Dimension(1, 40));
			jPanel1.add(getBtnOk(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					DgDzscEmsImgInfo.this.dispose();
				}
			});
		}
		return btnOk;
	}
	
	/**
	 * 初始化数据Table
	 */
	private void initTable(List imgs) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("备案序号", "img.seqNum", 60));
				list.add(addColumn("海关编码", "img.complex.code", 100));
				list.add(addColumn("料件名称", "img.name", 100));
				list.add(addColumn("型号规格", "img.spec", 100));
				list.add(addColumn("进口数量", "amount", 100));
				list.add(addColumn("进口金额", "money", 100));
				
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, imgs, jTableListModelAdapter);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
