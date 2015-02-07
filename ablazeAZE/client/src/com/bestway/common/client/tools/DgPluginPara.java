package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.PluginInfo;
import com.bestway.common.tools.entity.PluginParameter;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgPluginPara extends JDialogBase {

	private JPanel			jContentPane	= null;
	private JToolBar		jToolBar		= null;
	private JButton			btnSave			= null;
	private JButton			btnClose		= null;
	private JCheckBox		cbIsAutoRun		= null;
	private JScrollPane		jScrollPane		= null;
	private JTable			jTable			= null;
	private ToolsAction		toolsAction		= null;
	private PluginInfo		pluginInfo		= null;
	private JTableListModel	tableModel		= null;
	private boolean			isOk			= false;

	/**
	 * This method initializes
	 * 
	 */
	public DgPluginPara() {
		super();
		initialize();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(483, 385));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("服务端参数设置");

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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
			jToolBar.add(getCbIsAutoRun());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
					isOk = true;
					dispose();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes cbIsAutoRun
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsAutoRun() {
		if (cbIsAutoRun == null) {
			cbIsAutoRun = new JCheckBox();
			cbIsAutoRun.setText("是否是自动运行");
		}
		return cbIsAutoRun;
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

	public PluginInfo getPluginInfo() {
		return pluginInfo;
	}

	public void setPluginInfo(PluginInfo pluginInfo) {
		this.pluginInfo = pluginInfo;
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

	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("名称", "cnName", 120));
				list.add(addColumn("值", "value", 350));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		//
		// 设置列的修改和修改 enter key event
		//

		jTable.getColumnModel().getColumn(2).setCellEditor(
				new TableTextFieldEditor(new JTextField(), event));
		jTableListModelAdapter.setEditableColumn(2);
	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent	event	= new TableTextFieldEditorEvent() {
													public Object saveObject(
															Object obj) {
														return obj;
													}
												};

	private void fillData() {
		this.pluginInfo.setIsAutoRun(this.cbIsAutoRun.isSelected());
		List<PluginParameter> paras = (List<PluginParameter>) this.tableModel
				.getList();
		Map<String, PluginParameter> map = new HashMap<String, PluginParameter>();
		for (PluginParameter p : paras) {
			map.put(p.getKey(), p);
		}
		pluginInfo.setParameter(map);
	}

	private void saveData() {
		fillData();
		pluginInfo = this.toolsAction.savePluginInfo(new Request(CommonVars
				.getCurrUser()), pluginInfo);
		if (pluginInfo.getIsAutoRun().booleanValue()) {
			Thread thread = new Thread() {
				public void run() {
					toolsAction.runPlugin(
							new Request(CommonVars.getCurrUser()), pluginInfo);
				}
			};
			thread.start();
		}
	}

	private void showData() {
		boolean isAutoRun = this.pluginInfo.getIsAutoRun() == null ? false
				: this.pluginInfo.getIsAutoRun();
		this.cbIsAutoRun.setSelected(isAutoRun);

		Map<String, PluginParameter> map = this.pluginInfo.getParameter();
		List<PluginParameter> list = new ArrayList<PluginParameter>();
		list.addAll(map.values());

		initTable(list);
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	
}
