package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgParseEdiCustomsDeclaration extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbPreface = null;

	private JTable tbHead = null;

	private JTable tbBody = null;

	private String content = null; // @jve:decl-index=0:

	private JScrollPane jScrollPane3 = null;

	private JTable jfreeTextTable = null;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgParseEdiCustomsDeclaration() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(754, 522));
		this.setTitle("EDI报文内容解析");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			// if(content==null){
			// return;
			// }
			//Map mapPreface = ParseEdiCustomsDeclaration.getInstance(content)
					//.parsePreface();
			//showTable(tbPreface, mapPreface);
			//Map mapHead = ParseEdiCustomsDeclaration.getInstance(content)
			//		.parseHead();
			//showTable(tbHead, mapHead);
			//Map mapBody = ParseEdiCustomsDeclaration.getInstance(content)
			//		.parseBody();
			//showTable(tbBody, mapBody);
		}
		super.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打开文件");
		}
		return jButton;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("报文头", null, getJScrollPane(), null);
			jTabbedPane.addTab("报关单表头", null, getJScrollPane1(), null);
			jTabbedPane.addTab("报关单表体", null, getJScrollPane2(), null);
			jTabbedPane.addTab("自由文本", null, getJScrollPane3(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbPreface());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbHead());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbBody());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbPreface() {
		if (tbPreface == null) {
			tbPreface = new JTable();
		}
		return tbPreface;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
		}
		return tbHead;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBody() {
		if (tbBody == null) {
			tbBody = new JTable();
		}
		return tbBody;
	}

	private void showTable(JTable table, Map map) {
		DefaultTableModel tableModel = new DefaultTableModel();
		String[] tableHead = (String[]) map.get("tableHead");
		for (int i = 0; i < tableHead.length; i++) {
			tableModel.addColumn(tableHead[i]);
		}
		List list = (List) map.get("tableBody");
		for (int i = 0; i < list.size(); i++) {
			tableModel.addRow((String[]) list.get(i));
		}
		table.setModel(tableModel);
	}

	/**
	 * This method initializes jScrollPane3	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJfreeTextTable());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jfreeTextTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJfreeTextTable() {
		if (jfreeTextTable == null) {
			jfreeTextTable = new JTable();
		}
		return jfreeTextTable;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
