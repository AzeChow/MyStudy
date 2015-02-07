package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgShowAllBom extends JDialogBase {

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private MaterialManageAction materialManageAction = null;

	private JTableListModel tableModel = null;



	/**
	 * This method initializes
	 * 
	 */
	public DgShowAllBom() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(535, 477));
		this.setContentPane(getJContentPane());
		this.setTitle("工厂所有BOM列表");

	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			List list = materialManageAction.findAllEnterpriseBomManage(
					new Request(CommonVars.getCurrUser()));
			this.initTable(list);
		}
		super.setVisible(b);
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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

	private synchronized void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("父件料号", "parentNo", 100));
						list.add(addColumn("版本号", "versionNo", 100));
						list.add(addColumn("料件料号", "compentNo", 100));
						list.add(addColumn("料件名称", "compentName", 150));
						// list.add(addColumn("工厂单位", "", 80));
						list.add(addColumn("单位用量", "unitDosage", 80));
						list.add(addColumn("单耗", "unitWare", 80));
						list.add(addColumn("损耗", "ware", 80));
						list.add(addColumn("子件版本号", "childVersionNo", 80));
						list.add(addColumn("子件生效日期", "childEffectDate", 80));
						list.add(addColumn("子件失效日期", "childEndDate", 80));
						// list.add(addColumn("是否主料", "isMainMateriel", 80));
						return list;
					}
				});
	}

	

} // @jve:decl-index=0:visual-constraint="10,10"
