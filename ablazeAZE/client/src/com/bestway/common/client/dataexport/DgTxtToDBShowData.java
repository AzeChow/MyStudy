/*
 * Created on 2004-11-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.FileReading;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.common.constant.FileType;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtToDBShowData extends JDialogBase {
	private static final long	serialVersionUID	= 1L;
	private javax.swing.JPanel	jContentPane		= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JToolBar			jToolBar1			= null;
	private JButton				btnClose			= null;
	private TxtToDBRegion		txtToDBRegion		= null; 

	/**
	 * This is the default constructor
	 */
	public DgTxtToDBShowData() {
		super();
		initialize();
		initUIComponents();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本导出到 DB 的源文件数据显示");
		this.setSize(657, 482);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar1(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jToolBar1
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnClose());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgTxtToDBShowData.this.dispose();
				}
			});

		}
		return btnClose;
	}

	private void initUIComponents() {

	}

	private void showData() {
		String filePath = txtToDBRegion.getSrcFilePath();
		int importRowNumber = txtToDBRegion.getImportRowNumber();
		int fileType = txtToDBRegion.getFileType();
		String encoding = txtToDBRegion.getEncoding();
		String[] columnNames = new String[0];
		List<List> list = new ArrayList<List>();
		if (fileType == FileType.XLS) {
			columnNames = FileReading.readExcelCaption(filePath,
					importRowNumber - 1, encoding);
			list = FileReading.readExcel(filePath, importRowNumber, encoding);
		} else {
			columnNames = FileReading.readTxtCaption(filePath,
					importRowNumber - 1, encoding);
			list = FileReading.readTxt(filePath, importRowNumber, encoding);
		}

		new JTableJDBCModel(jTable, columnNames,list);
	}

	public TxtToDBRegion getTxtToDBRegion() {
		return txtToDBRegion;
	}

	public void setTxtToDBRegion(TxtToDBRegion txtToDBRegion) {
		this.txtToDBRegion = txtToDBRegion;
	}

}
