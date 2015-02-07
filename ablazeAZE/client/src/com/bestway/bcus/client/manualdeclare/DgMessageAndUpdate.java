package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgMessageAndUpdate extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private List<?> list;
	private List<JTableListColumn> columns;
	private JTableListModel tableModel;
	private JPanel panel;
	private JButton btnUpdate;
	private JButton btnClose;
	private MessageAction messageAction = null;

	

	private DgMessageAndUpdate() {
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		this.setTitle("错误信息");
		this.setBounds(100, 100, 500, 400);
		initialize();
	}

	private void initialize() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}

	private void initJTable() {
		JTableListModelAdapter jlma = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				return columns;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}


	public static void show(String title,List<JTableListColumn> columns, List<?> list) {
		DgMessageAndUpdate dg = new DgMessageAndUpdate();
		if(title != null) {
			dg.setTitle(title);
		}
		
		if(list == null) {
			list = new ArrayList<String>();
		}
		dg.list = list;
		
		if(columns == null) {
			columns = new ArrayList<JTableListColumn>();
			columns.add(new JTableListColumn("料号", "title", 130));
			columns.add(new JTableListColumn("错误信息", "errorMessage", 320));
		}
		dg.columns = columns;
		
		
		dg.initJTable();
		dg.setVisible(true);
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnUpdate());
			panel.add(getBtnClose());
		}
		return panel;
	}
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("更新选中单耗为‘新增’");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new UpdateBom().start();
				}
			});
		}
		return btnUpdate;
	}
	
	class UpdateBom extends Thread {

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgMessageAndUpdate.this);
				CommonProgress.setMessage("系统正在更新单耗状态，请稍后...");
				List<Object[]> keys = (List<Object[]>)(tableModel.getCurrentRows());
				messageAction.updateEmsHead2kBomStateByBomKeys(keys);
				tableModel.deleteRows(keys);
				CommonProgress.closeProgressDialog();
			} catch (RuntimeException e) {
				CommonProgress.closeProgressDialog();
				throw e;
			}
			JOptionPane.showMessageDialog(DgMessageAndUpdate.this,
					"更新成功！", "提示！", 0);
		}
	}
	
	
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
}
