package com.bestway.common.client.materialbase;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.ComponentOrientation;
import java.util.List;
import java.util.Vector;


public class DgCheckEnterpriseBomManage extends JDialogBase {

	private JPanel pnMain = null;
	private JScrollPane spnTable = null;
	private JTable tbMessage = null;
	private JPanel pnToolBar = null;
	private JToolBar tbToolBar = null;
	private JButton btnExit = null;

	public List dataSource=null;
	/**
	 * 料件/成品表格模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 初始化方法
	 * 
	 */
	public DgCheckEnterpriseBomManage() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(600, 400));
        this.setTitle("工厂BOM检查错误信息");
        this.setContentPane(getPnMain());
			
	}

	/**
	 * This method initializes pnMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.add(getSpnTable(), BorderLayout.CENTER);
			pnMain.add(getPnToolBar(), BorderLayout.NORTH);
		}
		return pnMain;
	}

	/**
	 * This method initializes spnTable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSpnTable() {
		if (spnTable == null) {
			spnTable = new JScrollPane();
			spnTable.setViewportView(getTbMessage());
		}
		return spnTable;
	}

	/**
	 * This method initializes tbMessage	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbMessage() {
		if (tbMessage == null) {
			tbMessage = new JTable();
		}
		return tbMessage;
	}

	/**
	 * This method initializes pnToolBar	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnToolBar() {
		if (pnToolBar == null) {
			pnToolBar = new JPanel();
			pnToolBar.setLayout(new BorderLayout());
			pnToolBar.add(getTbToolBar(), BorderLayout.NORTH);
		}
		return pnToolBar;
	}

	/**
	 * This method initializes tbToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getTbToolBar() {
		if (tbToolBar == null) {
			tbToolBar = new JToolBar();
			tbToolBar.setComponentOrientation(ComponentOrientation.UNKNOWN);
			tbToolBar.add(getBtnExit());
		}
		return tbToolBar;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();
				}
			});
		}
		return btnExit;
	}
	
	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
		initTable(dataSource);
	}

	/**
	 * 初始化表格
	 * 
	 * @param list
	 * @return
	 */
	private  JTableListModel initTable(List list) {
		new JTableListModel(tbMessage, list, new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("父级料号", "parentNo",100));
				list.add(addColumn("子级料号", "compentNo", 100));
				list.add(addColumn("父件版本号", "versionNo", 100));
				list.add(addColumn("子级版本号", "childVersionNo", 100));
				list.add(addColumn("错误原因", "erro", 250));
				return list;
			}
		});
		tableModel = (JTableListModel) tbMessage.getModel();
		return tableModel;
	}

}
