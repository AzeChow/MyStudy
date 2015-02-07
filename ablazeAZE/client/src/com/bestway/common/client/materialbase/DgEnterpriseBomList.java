package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
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
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;

public class DgEnterpriseBomList extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButtonAllBom = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private MaterialManageAction materialManageAction = null;

	private JTableListModel tableModel = null;

	private String parentNo = null; // @jve:decl-index=0:

	private JButton jButton = null;

	private JToolBar jToolBar1 = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEnterpriseBomList() {
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
		this.setSize(new Dimension(797, 477));
		this.setContentPane(getJContentPane());
		this.setTitle("工厂BOM列表");
		//this.pnCommonQueryPage_1.setInitState();
        pnCommonQueryPage.setVisible(false);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			List list = materialManageAction.findEnterpriseBomDetail(
					new Request(CommonVars.getCurrUser()), parentNo);
			this.initTable(list);
			 getJContentPane();
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
			jContentPane.setLayout(null);
			jContentPane.add(getJToolBar());
			jContentPane.add(getJScrollPane());

			pnCommonQueryPage = new PnCommonQueryPage() {
//				public JTableListModel initTable(List dataSource) {
//					initTable(dataSource);
//					return tableModel;
//				}
				@Override 
				public JTableListModel initTable(List dataSource) {
				DgEnterpriseBomList.this.initTable(dataSource); 
				return DgEnterpriseBomList.this.tableModel; }

				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					// 查出BOM表中的所有数据
					return DgEnterpriseBomList.this.getDataSource(index,
							length, property, value, isLike);
				}

				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike) {
					// 总条数
					return materialManageAction
							.findAllEnterpriseBomDetailCount(new Request(
									CommonVars.getCurrUser()), index, length,
									property, value, isLike);
				}
			};
			pnCommonQueryPage.setBounds(10, 20, 781, 30);
			jContentPane.add(pnCommonQueryPage);
			pnCommonQueryPage.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}
		return jContentPane;
	}

	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		
		List list = materialManageAction.findAllEnterpriseBomDetail(new Request(
				CommonVars.getCurrUser()), index, length,
				property, value, isLike);

		return list;
	}
	
	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(0, 0, 519, 23);
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton());
			jToolBar.add(getJButtonAllBom());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("将空的版本号改为“0”版本号");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						EnterpriseBomManage bomManage = (EnterpriseBomManage) list
								.get(i);
						if (bomManage.getVersionNo() == null) {
							bomManage.setVersionNo("0");
							bomManage = materialManageAction
									.saveEnterpriseBomManage(new Request(
											CommonVars.getCurrUser()),
											bomManage);
						}
					}
					tableModel.setList(list);
				}
			});
		}
		return jButton1;
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(10, 48, 771, 391);
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

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("修改");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEnterpriseBomList.this,
								"请选择你要修改的料件", "提示", JOptionPane.OK_OPTION);
						return;
					}
					EnterpriseBomManage detail = (EnterpriseBomManage) tableModel
							.getCurrentRow();
					DgEnterpriseBomDetail dg = new DgEnterpriseBomDetail();
					dg.setTableModel(tableModel);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					if (dg.isOk()) {
						detail = dg.getDetail();
						tableModel.updateRow(detail);
					}
				}
			});
		}
		return jButton;
	}

	private JButton getJButtonAllBom() {
		if (jButtonAllBom == null) {
			jButtonAllBom = new JButton();
			jButtonAllBom.setText("显示所有BOM");
			jButtonAllBom.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							System.out.print("===========================");
							pnCommonQueryPage.setVisible(true);
							pnCommonQueryPage.setInitState();
						}
					});
		}
		return jButtonAllBom;
	}

	public PnCommonQueryPage getPnCommonQueryPage() {
		return pnCommonQueryPage;
	}

	public void setPnCommonQueryPage_1(PnCommonQueryPage pnCommonQueryPage) {
		this.pnCommonQueryPage = pnCommonQueryPage;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
