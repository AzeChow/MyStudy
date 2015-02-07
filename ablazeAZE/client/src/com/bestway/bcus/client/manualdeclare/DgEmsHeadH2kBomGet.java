/*
 * Created on 2004-7-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTabbedPane;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsHeadH2kBomGet extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private EmsHeadH2kExg emsHead2kExg = null; // 归并后成品

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelMergerBom = null;// BOM

	private JTableListModel tableModel = null; // 表头table

	private ManualDeclareAction manualdeclearAction = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;
	private JPanel jPanel3 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JTable tbTop = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane2 = null;

	// ----分页----
	private int firstIndex = 0;
	private List exgBomList = new Vector();
	private List dmList = new Vector(); // @jve:decl-index=0:

	private JButton jButton = null;
	private Integer version = null; // @jve:decl-index=0:

	private JLabel jLabel = null;

	private JComboBox cbPoNo = null;

	private Map mapPtNo = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kBomGet() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(745, 520);
		this.setTitle("从归并关系归并前BOM拷贝单耗资料");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initTableMergerBom(new Vector());
				if (tableModel.getCurrentRow() != null) {
					emsHead2kExg = (EmsHeadH2kExg) tableModel.getCurrentRow(); // 获得归并后成品信息
				}
				firstIndex = 0;
				String emsFrom = CommonVars.getEmsFrom();
				String declareState = null;
				if (emsFrom != null && emsFrom.equals("2")) { // 来自归并关系备案
					declareState = DeclareState.APPLY_POR;
				} else {// 归并关系正在执行
					declareState = DeclareState.PROCESS_EXE;
				}
				List list = manualdeclearAction.findEmsEdiMergerBomPtNoByAfter(
						new Request(CommonVars.getCurrUser()), emsHead2kExg
								.getSeqNum(), declareState, version);
				getCbPoNo().setModel(new DefaultComboBoxModel(list.toArray()));
				getpage();
			}
		});
	}

	private void getpage() {
		new fillBom().execute();
	}

	class fillBom extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			try {
				// CommonProgress.showProgressDialog(DgEmsHeadH2kBomGet.this);
				// CommonProgress.setMessage("系统正在获取数据，请稍后...");
				initData();
				// CommonProgress.closeProgressDialog();
				return null;
			} catch (Exception e) {
				initTableExg(new Vector());
				initTableMergerBom(new Vector());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kBomGet.this, "获取失败：！"
						+ e.getMessage(), "提示", 2);
				return null;
			}
		}
	}

	private void initData() {
		String emsFrom = CommonVars.getEmsFrom();
		String declareState = null;
		if (emsFrom != null && emsFrom.equals("2")) { // 来自归并关系备案
			declareState = DeclareState.APPLY_POR;
		} else {// 归并关系正在执行
			declareState = DeclareState.PROCESS_EXE;
		}
		String str = "";
		if (getCbPoNo().getSelectedItem() == null) {
			str = null;
		} else {
			str = (String) getCbPoNo().getSelectedItem();
		}
		exgBomList = manualdeclearAction.findEmsEdiMergerBomByAfterPage(
				new Request(CommonVars.getCurrUser()),
				emsHead2kExg.getSeqNum(), firstIndex, declareState, version,
				str);

		if (exgBomList != null && exgBomList.size() > 0) {
			initTableExg(exgBomList);
		} else {
			initTableExg(new Vector());
		}
	}

	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(tbTop, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();

						list.add(addColumn("归并前成品货号",
								"emsEdiMergerVersion.emsEdiMergerBefore.ptNo",
								100));
						list.add(addColumn("归并前成品名称",
								"emsEdiMergerVersion.emsEdiMergerBefore.name",
								150));
						list.add(addColumn("归并前成品规格",
								"emsEdiMergerVersion.emsEdiMergerBefore.spec",
								100));

						list.add(addColumn("归并BOM版本号",
								"emsEdiMergerVersion.version", 80));
						list.add(addColumn("BOM料件货号", "ptNo", 100));
						list.add(addColumn("BOM料件名称", "name", 100));
						list.add(addColumn("BOM料件规格", "spec", 100));
						list.add(addColumn("BOM计量单位", "unit.name", 80));
						list.add(addColumn("BOM单耗", "unitWaste", 80));
						list.add(addColumn("BOM损耗率(%)", "waste", 100));
						return list;
					}
				});
	}

	private void initTableMergerBom(final List list) {
		tableModelMergerBom = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list
								.add(addColumn("版本号", "version", 60,
										Integer.class));
						list.add(addColumn("料件序号", "bom.seqNum", 60));
						list.add(addColumn("商品编码", "bom.complex.code", 80));
						list.add(addColumn("商品名称", "bom.name", 100));
						list.add(addColumn("型号规格", "bom.spec", 100));
						list.add(addColumn("申报单位", "bom.unit.name", 80));
						list.add(addColumn("单耗", "bom.unitWear", 80));
						list.add(addColumn("损耗", "bom.wear", 80));
						return list;
					}
				});
		tableModelMergerBom.setColumnsFractionCount(7, 9);
		tableModelMergerBom.setColumnsFractionCount(8, 6);
		tableModelMergerBom.sortByColumn(1);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(3);
			jSplitPane.setDividerLocation(250);
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setTopComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
		}
		return jToolBar;
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
			jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel = new JLabel();
			jLabel.setText("归并前成品货号：");
			jLabel.setBounds(new Rectangle(14, 10, 99, 25));
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(jLabel, null);
			jPanel3.add(getCbPoNo(), null);
			jPanel3.add(getJButton2(), null);
			jPanel3.add(getJButton3(), null);
			jPanel3.add(getJButton4(), null);
			jPanel3.setPreferredSize(new Dimension(0, 45));
			jPanel3.add(getJButton(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("转换成单耗表");
			jButton2.setBounds(new Rectangle(247, 10, 106, 28));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new tranBom().start();

				}
			});
		}
		return jButton2;
	}

	class tranBom extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2kBomGet.this);
				CommonProgress.setMessage("系统正在转换单耗数据，请稍后...");

				String emsFrom = CommonVars.getEmsFrom();
				String declareState = null;
				if (emsFrom != null && emsFrom.equals("2")) { // 来自归并关系备案
					declareState = DeclareState.APPLY_POR;
				} else {// 归并关系正在执行
					declareState = DeclareState.PROCESS_EXE;
				}
				dmList = manualdeclearAction.addEmsH2kDm(new Request(CommonVars
						.getCurrUser()), emsHead2kExg.getSeqNum(),
						declareState, version, (String) getCbPoNo()
								.getSelectedItem());
				initTableMergerBom(dmList);

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				initTableMergerBom(new Vector());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kBomGet.this, "转换失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("上一页");
			jButton3.setBounds(new Rectangle(358, 10, 70, 28));
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (firstIndex >= 100) {
						firstIndex = firstIndex - 100;
						getpage();
					}
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("下一页");
			jButton4.setBounds(new Rectangle(433, 10, 70, 28));
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getRowCount() >= 100) {
						firstIndex = firstIndex + 100;
						getpage();
					}
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes tbTop
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbTop() {
		if (tbTop == null) {
			tbTop = new JTable();
		}
		return tbTop;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbTop());
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
			jScrollPane2.setViewportView(getJTable1());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(new Rectangle(513, 10, 70, 28));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dmList == null) {
						return;
					}
					if (dmList.size() > 0) {
						new SaveFileDataRunnable(dmList).start();
					}
				}
			});
		}
		return jButton;
	}

	class SaveFileDataRunnable extends Thread {
		List dmList = null;

		private SaveFileDataRunnable(List dmList) {
			this.dmList = dmList;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2kBomGet.this);
				CommonProgress.setMessage("系统正在保存数据资料，请稍后...");
				int[] x = manualdeclearAction.saveEmsBomFromMergerBom(
						new Request(CommonVars.getCurrUser()), dmList,
						emsHead2kExg);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kBomGet.this,
						"导入结束!\n\n" + "总记录数：" + x[4] + "\n\n" + "存在但无任何变化："
								+ x[5] + "\n" + "单耗存在修改记录数：" + x[1] + "\n"
								+ "版本存在新增记录数：" + x[2] + "\n" + "版本不存在新增记录数："
								+ x[3] + "", "提示", 2);
				DgEmsHeadH2kBomGet.this.dispose();
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kBomGet.this,
						"数据执行失败！", "提示！", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void setVersion(Integer version) {
		this.version = version;

	}

	/**
	 * This method initializes cbPoNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbPoNo() {
		if (cbPoNo == null) {
			cbPoNo = new JComboBox();
			cbPoNo.setBounds(new Rectangle(125, 10, 117, 27));

			cbPoNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					firstIndex = 0;
					getpage();
				}
			});
		}
		return cbPoNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
