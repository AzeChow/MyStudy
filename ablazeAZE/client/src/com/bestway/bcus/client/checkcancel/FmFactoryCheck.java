/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccount;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.ImportDataForFactoryPd;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmFactoryCheck extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private CheckOwnerAccount checkOwnerHead = null;

	private CheckCancelAction checkCancelAction = null;

	private ManualDeclareAction manualDeclearAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelVersion = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;
	
	private JTableListModel tableModelImgForBg = null;

	private JTableListModel tableModelExgToImg = null;

	private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	private MessageAction messageAction = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTable2 = null;

	private CheckParameter head = null;

	private JPanel jPanel6 = null;

	private JButton jButton1 = null;

	private JButton jButton3 = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel9 = null;

	private JScrollPane jScrollPane5 = null;

	private JTable jTable5 = null;

	private JTableListModel tableModelImgResult = null;

	private JScrollPane jScrollPane3 = null;

	private JTable jTable3 = null;

	private List arrayList = new Vector();

	private JButton jButton2 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;
	private Hashtable gbHash = null;
	private File file1 = null;

	private JButton jButton6 = null;

	private JPanel jPanel7 = null;

	private JScrollPane jScrollPane4 = null;

	private JTable jTable4 = null;

	private JButton jButton7 = null;

	private JButton jButton8 = null;
	private JButton btnEdit;
	/**
	 * This is the default constructor
	 */
	public FmFactoryCheck() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(909, 589);
		this.setTitle("工厂盘点-表头");
		this.setContentPane(getJContentPane());

		List dataSource = new Vector();
		checkCancelAction.controlFactoryCehck(new Request(CommonVars
				.getCurrUser()));
		dataSource = checkCancelAction.findCheckParameter(new Request(
				CommonVars.getCurrUser(), true));
		if (dataSource != null && dataSource.size() > 0) {
			initTableCustom(dataSource);
			head = (CheckParameter) dataSource.get(0);
			inittable();
		} else {
			initTableCustom(new Vector());
			initTableImg(new Vector());
			initTableExg(new Vector());
			initTableExgToImg(new Vector());
			initTableImgResult(new Vector());
			initTableImgForBg(new Vector());
		}
		setstate();

	}

	private void inittable() {
		// 初始化料件
		List dataSource = checkCancelAction.findFactoryCheckImg(new Request(
				CommonVars.getCurrUser(), true), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTableImg(dataSource);
		} else {
			initTableImg(new Vector());
		}
		// 初始化成品
		dataSource = checkCancelAction.findFactoryCheckExg(new Request(
				CommonVars.getCurrUser(), true), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTableExg(dataSource);
		} else {
			initTableExg(new Vector());
		}

		// 原材料折料
		dataSource = checkCancelAction.findFactoryCheckExgConverImg(
				new Request(CommonVars.getCurrUser(), true), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTableExgToImg(dataSource);
		} else {
			initTableExgToImg(new Vector());
		}

		// 初始化料件汇总
		dataSource = checkCancelAction.findFactoryCheckImgCollect(new Request(
				CommonVars.getCurrUser()), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTableImgResult(dataSource);
		} else {
			initTableImgResult(new Vector());
		}
		
		//折算为报关数量
		dataSource = checkCancelAction.findFactoryCheckImgForBgAll(new Request(
				CommonVars.getCurrUser()), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTableImgForBg(dataSource);
		} else {
			initTableImgForBg(new Vector());
		}
	}

	/**
	 * 料件
	 * 
	 * @param list
	 */
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("1.料号", "ptNo", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("工厂计量单位", "calUnit.name", 100));
						list.add(addColumn("2.原料库存数量", "materStockNum", 100));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("海关折算库存数量", "bgNum", 120));
						return list;
					}
				});
	}

	/**
	 * 成品
	 * 
	 * @param list
	 */
	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("1.料号", "ptNo", 100));
						list.add(addColumn("2.版本号", "version", 120));
						list.add(addColumn("成品名称", "name", 120));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("工厂计量单位", "calUnit.name", 100));
						list.add(addColumn("3.成品库存数量", "materStockNum", 100));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("海关折算库存数量", "bgNum", 120));
						return list;
					}
				});
	}
	
	
	private void initTableImgForBg(final List list) {
		tableModelImgForBg = new JTableListModel(jTable4, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "seqNum", 80, Integer.class));
						list.add(addColumn("名称", "name", 120));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("海关库存数量", "bgNum", 120));
						return list;
					}
				});
	}
	/**
	 * 成品折料数量
	 * 
	 * @param list
	 */
	private void initTableExgToImg(final List list) {
		tableModelExgToImg = new JTableListModel(jTable3, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("海关折算数量", "bgNum", 120));
						return list;
					}
				});
	}

	private void initTableCustom(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("核查次数", "emsNo", 60));
						list.add(addColumn("核查起始日", "beginDate", 120));
						list.add(addColumn("核查结束日", "endDate", 120));
						return list;
					}
				});
	}

	private void initTableImgResult(final List list) {
		tableModelImgResult = new JTableListModel(jTable5, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("海关单位", "unit.name", 100));
						list.add(addColumn("海关折算数量", "materielStockNum",
										100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return Returns the checkCancelAction.
	 */
	public CheckCancelAction getCheckCancelAction() {
		return checkCancelAction;
	}

	/**
	 * @param checkCancelAction
	 *            The checkCancelAction to set.
	 */
	public void setCheckCancelAction(CheckCancelAction checkCancelAction) {
		this.checkCancelAction = checkCancelAction;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("工厂盘点");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 24));
			jPanel = new JPanel();
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(jLabel, null);
		}
		return jPanel;
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
			jToolBar.add(getJPanel6());
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
			jButton.setText(" ");
		}
		return jButton;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setLeftComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								return;
							}
							head = (CheckParameter) tableModel.getCurrentRow();
							inittable();
						}
					});
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("料件", null, getJPanel3(), null);
			jTabbedPane.addTab("成品", null, getJPanel5(), null);
			jTabbedPane.addTab("成品折为料件", null, getJPanel4(), null);			
			jTabbedPane.addTab("料件汇总", null, getJPanel9(), null);
			jTabbedPane.addTab("折算为报关料件", null, getJPanel7(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setstate();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.add(getJButton1(), null);
			jPanel6.add(getJButton3(), null);
			jPanel6.add(getJButton2(), null);
			jPanel6.add(getJButton4(), null);
			jPanel6.add(getJButton5(), null);
			jPanel6.add(getJButton6(), null);
			jPanel6.add(getJButton7(), null);
			jPanel6.add(getJButton8(), null);
			jPanel6.add(getBtnEdit());
		}
		return jPanel6;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(6,2,62,26));
			jButton1.setText("新增");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) { // 料件
						String materielType = MaterielType.MATERIEL;
						List list = (List) CommonQueryPage.getInstance()
								.getMaterielAll(materielType);
						if (list == null || list.size() <= 0 || head == null) {
							return;
						}
						List arrayList = checkCancelAction.getFactoryCheckImg(
								new Request(CommonVars.getCurrUser()), list,
								head);
						tableModelImg.addRows(arrayList);
					}
					if (jTabbedPane.getSelectedIndex() == 1) { // 成品
						String materielType = MaterielType.FINISHED_PRODUCT;
						List list = (List) CommonQueryPage.getInstance()
								.getMaterielAll(materielType);
						if (list == null || list.size() <= 0 || head == null) {
							return;
						}
						List arrayList = checkCancelAction.getFactoryCheckExg(
								new Request(CommonVars.getCurrUser()), list,
								head);
						tableModelExg.addRows(arrayList);
					} else {

					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(208, 3, 102, 25));
			jButton3.setText("折算为料件");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmFactoryCheck.this,
							"确定要重新折算成品吗?", "确认", 0) == 0) {
						new exgconvertimg().start();
					}
				}
			});
		}
		return jButton3;
	}

	class exgconvertimg extends Thread {

		public void run() {
			List vlist = null;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在核销计算，请稍后...");

				// 删除已经折算料件
				checkCancelAction.deleteFactoryCheckExgConverImg(new Request(
						CommonVars.getCurrUser(), true), head);
				List arrayList = new Vector();
				// 开始折算料件
				List list = tableModelExg.getList();// 成品List
				if (list != null && list.size() > 0) {
					arrayList = checkCancelAction.factoryExgConvertImg(
							new Request(CommonVars.getCurrUser(), true), list);
				}
				if (arrayList != null && arrayList.size() > 0) {
					initTableExgToImg(arrayList);
				} else {
					initTableExgToImg(new Vector());
				}

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {

			}
		}
	}

	class totalimg extends Thread {

		public void run() {
			List vlist = null;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在汇总计算计算，请稍后...");
				checkCancelAction.deleteTotalImg(new Request(CommonVars
						.getCurrUser(), true), head);
				List totalList = checkCancelAction.totalMateriel(new Request(
						CommonVars.getCurrUser()), tableModelImg.getList(),
						tableModelExgToImg.getList());
				if (totalList != null && totalList.size() > 0) {
					initTableImgResult(totalList);
				} else {
					initTableImgResult(new Vector());
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {

			}
		}
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getJTable5());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jTable5
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable5() {
		if (jTable5 == null) {
			jTable5 = new JTable();
		}
		return jTable5;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
		}
		return jTable3;
	}

	private void setstate() {
		jButton1.setEnabled(jTabbedPane.getSelectedIndex() == 0
				|| jTabbedPane.getSelectedIndex() == 1);
		jButton3.setEnabled(jTabbedPane.getSelectedIndex() == 2);
		jButton2.setEnabled(jTabbedPane.getSelectedIndex() == 3);
		jButton5.setEnabled(jTabbedPane.getSelectedIndex() == 0
				|| jTabbedPane.getSelectedIndex() == 1);
		jButton7.setEnabled(jTabbedPane.getSelectedIndex() == 4);
		jButton8.setEnabled(jTabbedPane.getSelectedIndex() == 3);
		btnEdit.setEnabled(jTabbedPane.getSelectedIndex() == 0
				|| jTabbedPane.getSelectedIndex() == 1);
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(315, 3, 89, 25));
			jButton2.setText("料件汇总");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new totalimg().start();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(730, 3, 79, 25));
			jButton4.setText("退出");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFactoryCheck.this.dispose();
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(new Rectangle(409, 3, 90, 25));
			jButton5.setForeground(java.awt.Color.blue);
			jButton5.setText("文本导入");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materielType = "";
					file1 = getFile();
					if(file1==null)
						return;
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0){ //料件
					    list = parseTxtFile(file1,true);
					    materielType = MaterielType.MATERIEL;
					} else  if (jTabbedPane.getSelectedIndex() == 1){ //成品
						list = parseTxtFile(file1,false);
						materielType = MaterielType.FINISHED_PRODUCT;
					}
					new TxtDataRunnable(list,materielType).start();
					
				}
			});
		}
		return jButton5;
	}
    
	
	class TxtDataRunnable extends Thread {
		private List list = null;
		private String materielType = null;
		public TxtDataRunnable(List list,String materielType){
			this.list = list;
			this.materielType = materielType;
		}		
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				List sList = checkCancelAction.importDataToFactoryCheck(new Request(CommonVars.getCurrUser()),list,materielType,head);
				List errorlist = (List)sList.get(1);
				List rightlist = (List) sList.get(0);
				BillTemp b = (BillTemp) sList.get(2);				
				String ErrorStr = "";
				if (errorlist != null && errorlist.size()>0){					
					ErrorStr = "温馨提示：以上物料有："+b.getBill2()+"    笔记录因不存在报关常用工厂物料即不可导入，请先增加报关常用工厂物料！\n\n";
				}
				ErrorStr += "读入文本总数："+b.getBill1()+"        可更新到核查表数："+b.getBill3()+"        可新增到核查表数："+b.getBill4();
				CommonProgress.closeProgressDialog();
				DgCheckError dg = new DgCheckError();
				dg.setErrorType("0");
				dg.setList(errorlist);
				dg.setErrorStr(ErrorStr);
				dg.setVisible(true);
				if (dg.isOk()){
					CommonProgress.showProgressDialog();
					if (rightlist != null || rightlist.size()>0){
					   checkCancelAction.saveDataToFactoryCheck(new Request(CommonVars.getCurrUser()),rightlist,head,materielType);
					   CommonProgress.closeProgressDialog();
					   JOptionPane.showMessageDialog(FmFactoryCheck.this,"导入完毕！\n\n" +
							    "文本总数：    "+b.getBill1()+"        \n" +
						   		"未导入数：    "+b.getBill2()+"        \n"+
						   		"已更新到核查表数："+b.getBill3()+"        \n" +
						   		"已新增到核查表数："+b.getBill4(),"提示",2);
					   inittable();
					}
				}
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this,"导入出错，请与百思维客服人员联系！","提示",2);
			}
		}
	}
	//调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				String suffix = getSuffix(f);
				if (f.isDirectory() == true) {
					return true;
				}
				if (suffix != null) {
					if (suffix.toLowerCase().equals("txt")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "*.txt";
			}

			private String getSuffix(File f) {
				String s = f.getPath(), suffix = null;
				int i = s.lastIndexOf('.');
				if (i > 0 && i < s.length() - 1)
					suffix = s.substring(i + 1).toLowerCase();
				return suffix;
			}
		});
		int state = fileChooser.showOpenDialog(FmFactoryCheck.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}
	
//	
//	
//	private void infTojHsTable() {
//		if (gbHash == null) {
//			gbHash = new Hashtable();
//		}
//		List list = CustomBaseList.getInstance().getGbtobigs();
//		for (int i = 0; i < list.size(); i++) {
//			Gbtobig gb = (Gbtobig) list.get(i);
//			gbHash.put(gb.getBigname(), gb.getName());
//		}
//	}
//	private String changeStr(String s) { // 简转繁
//		String yy = "";
//		char[] xxx = s.toCharArray();
//		for (int i = 0; i < xxx.length; i++) {
//			String z = String.valueOf(xxx[i]);
//			if (String.valueOf(xxx[i]).getBytes().length == 2) {
//				if (gbHash.get(String.valueOf(xxx[i])) != null) {
//					z = (String) gbHash.get(String.valueOf(xxx[i]));
//				}
//			}
//			yy = yy + z;
//		}
//		return yy;
//	}
	
	
	
	// 读取并检查数据
	private List parseTxtFile(File file,boolean isLj) {
		/*boolean ischange = true;
		infTojHsTable();*/
		BufferedReader in;
		String[] strs = null;
		List <ImportDataForFactoryPd>list = new ArrayList<ImportDataForFactoryPd>();  // 字段所有的值
		try {
			in = new BufferedReader(new FileReader(file));
			String s = new String();
			try {
				if (isLj){
					while ((s = in.readLine()) != null) {
						ImportDataForFactoryPd data = new ImportDataForFactoryPd(); // 每个字段对应的值
						if (s.trim().equals("")) {
							continue;
						}
						/*if (ischange) {
							s = changeStr(s);
						}*/
						strs = s.split(String.valueOf((char) 9));
						data.setPtNo(getStr(strs,0));
				    	try{
				           data.setMaterStockNum(Double.valueOf(getStr(strs,1)));
				    	} catch (Exception e){
				    	}
						list.add(data);
					}
				} else {
					while ((s = in.readLine()) != null) {
						ImportDataForFactoryPd data = new ImportDataForFactoryPd(); // 每个字段对应的值
						if (s.trim().equals("")) {
							continue;
						}
						/*if (ischange) {
							s = changeStr(s);
						}*/
						strs = s.split(String.valueOf((char) 9));
						data.setPtNo(getStr(strs,0));
						data.setVersion(getStr(strs,1));
				    	try{
				           data.setMaterStockNum(Double.valueOf(getStr(strs,2)));
				    	} catch (Exception e){
				    	}						
						list.add(data);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	private String getStr(String[] strs,int i){
		int len = strs.length;
		if (i<=len-1){
			return strs[i];
		}
		return "";
	}
	
	
	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(new Rectangle(140, 2, 64, 26));
			jButton6.setText("删除");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new deleteAll().start();
				}
			});
		}
		return jButton6;
	}
	
	
	class deleteAll extends Thread{
		public void run(){
			try{
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在删除数据，请稍候.....");
				if (jTabbedPane.getSelectedIndex() == 0) { // 料件
					List list = tableModelImg.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelImg.deleteRows(list);
				} else  if (jTabbedPane.getSelectedIndex() == 1){ //成品
					List list = tableModelExg.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelExg.deleteRows(list);
				} else if (jTabbedPane.getSelectedIndex() == 2){ //成品折算料件
					List list = tableModelExgToImg.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelExgToImg.deleteRows(list);
				} else if (jTabbedPane.getSelectedIndex() == 3){ //料件汇总
					List list = tableModelImgResult.getCurrentRows();
					checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
					tableModelImgResult.deleteRows(list);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this,"删除失败：！"+e.getMessage(),"提示",2);
			}
		}
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jScrollPane4	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTable4());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jTable4	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable4() {
		if (jTable4 == null) {
			jTable4 = new JTable();
		}
		return jTable4;
	}

	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(new Rectangle(503, 3, 102, 25));
			jButton7.setText("折算为报关");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmFactoryCheck.this,
							"确定要重新折算报关料件吗?", "确认", 0) == 0) {
						new convertBgLj().start();
					}
				}
			});
		}
		return jButton7;
	}
		
	class convertBgLj extends Thread {

		public void run() {
			List vlist = null;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在折算报关料件计算，请稍后...");

				// 删除已经折算料件
				checkCancelAction.deleteFactoryCheckImgForBg(new Request(
						CommonVars.getCurrUser(), true), head);
                List arrayList = new Vector();
				// 开始折算料件
				List list = tableModelImgResult.getList();// 料件汇总
				if (list != null && list.size() > 0) {
					arrayList = checkCancelAction.convertBgImg(
							new Request(CommonVars.getCurrUser(), true), list, head);					
				}
				if (arrayList != null && arrayList.size() > 0) {
					initTableImgForBg(arrayList);
				} else {
					initTableImgForBg(new Vector());
				}

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {

			}
		}
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setBounds(new Rectangle(608, 2, 118, 26));
			jButton8.setText("海关核销导入");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new importForBg().start();
				}
			});
		}
		return jButton8;
	}	
	
	
	class importForBg extends Thread {

		public void run() {
			List vlist = null;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在汇总计算计算，请稍后...");
				checkCancelAction.deleteTotalImg(new Request(CommonVars
						.getCurrUser(), true), head);
				List totalList = checkCancelAction.importImgForBg(new Request(
						CommonVars.getCurrUser()),head);
				if (totalList != null && totalList.size() > 0) {
					initTableImgResult(totalList);
				} else {
					initTableImgResult(new Vector());
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCheck.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {

			}
		}
	}
		
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setBounds(73, 2, 64, 26);
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) { // 料件
						DgFactoryCheckImg img = new DgFactoryCheckImg(tableModelImg);
						img.setDataState(DataState.EDIT);
						img.setVisible(true);
					}else if(jTabbedPane.getSelectedIndex() == 1){
						DgFactoryCheckExg exg = new DgFactoryCheckExg(tableModelExg);
						exg.setDataState(DataState.EDIT);
						exg.setVisible(true);
					}
					
				}
			});
		}
		return btnEdit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
