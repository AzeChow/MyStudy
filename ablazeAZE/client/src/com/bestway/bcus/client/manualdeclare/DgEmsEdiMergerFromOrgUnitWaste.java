/*
 * Created on 2004-12-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 归并关系从产品BOM中转入归并关系Dg
 * 
 * Checked by lxr
 * 
 * @author lxr
 */
public class DgEmsEdiMergerFromOrgUnitWaste extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane spnExgBefor = null;
	private JScrollPane spnConverBom = null;

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;

	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JButton btnConversion = null;

	private JTable tbExgBefor = null;
	private JTable tbConverBom = null;
	/**
	 * 归并前成品tableMode
	 */
	private JTableListModel tableModelExgBefor = null;
	/**
	 * 转换为工厂BOM的tableModel
	 */
	private JTableListModel tableModelConverBom = null;
	/**
	 * 归并后成品Entity
	 */
	private EmsEdiMergerExgBefore emsExgBefore = null; // @jve:decl-index=0:
	/**
	 * 归并关系版本Entity
	 */
	private EmsEdiMergerVersion ediMergerVersion = null; // @jve:decl-index=0:

	private ManualDeclareAction manualdeclearAction = null;
	/**
	 * 返回确定或返回取消参数
	 */
	private boolean isOk = false;

	private List detailList = null; // @jve:decl-index=0:
	public List returnList; // @jve:decl-index=0:
	private List masterList = null; // @jve:decl-index=0:

	private JLabel jLabel = null;

	public EmsEdiMergerExgBefore getEmsExgBefore() {
		return emsExgBefore;
	}

	public void setEmsExgBefore(EmsEdiMergerExgBefore emsExgBefore) {
		this.emsExgBefore = emsExgBefore;
	}

	public EmsEdiMergerVersion getEdiMergerVersion() {
		return ediMergerVersion;
	}

	public void setEdiMergerVersion(EmsEdiMergerVersion ediMergerVersion) {
		this.ediMergerVersion = ediMergerVersion;
	}

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMergerFromOrgUnitWaste() {
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
		this.setTitle("从报关常用工厂BOM拷贝BOM资料");
		this.setSize(650, 497);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				masterList = new ArrayList();
				masterList.add(emsExgBefore);
				if (masterList != null && !masterList.isEmpty()) {
					initTable(masterList);
				} else {
					initTable(new Vector());
				}
				initTable2(new Vector());
			}
		});
	}

	/**
	 * 转换成BOM过程
	 * 
	 * @author ower
	 * 
	 */
	class fillBom extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgEmsEdiMergerFromOrgUnitWaste.this);
				CommonProgress.setMessage("系统正在获取数据，请稍后...");
				initData();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				initTable(new Vector());
				initTable2(new Vector());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgEmsEdiMergerFromOrgUnitWaste.this, "获取失败：！", "提示", 2);
			}
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		if (masterList != null && !masterList.isEmpty()) {
			EmsEdiMergerExgBefore exg = (EmsEdiMergerExgBefore) masterList
					.get(0);
			System.out.println("id="+ediMergerVersion.getVersion());
			detailList = manualdeclearAction.addBomFromCustomsBom(new Request(
					CommonVars.getCurrUser()), exg, ediMergerVersion.getVersion());
			if (detailList != null && detailList.size() > 0) {
				initTable2(detailList);
			} else {
				initTable2(new Vector());
				JOptionPane.showMessageDialog(
						DgEmsEdiMergerFromOrgUnitWaste.this, "新增加的版本号["
								+ ediMergerVersion.getVersion()
								+ "]在报关常用工厂BOM中不存在！", "提示", 2);
			}
		}
	}

	/**
	 * 初始化成品Table
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		tableModelExgBefor = new JTableListModel(tbExgBefor, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("归并序号", "seqNum", 58));
						list.add(addColumn("成品货号", "ptNo", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("成品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 150));
						list.add(addColumn("单位", "unit.name", 60));
						return list;
					}
				});
	}

	/**
	 * 初始化Bom资料Table
	 * 
	 * @param list
	 */
	private void initTable2(final List list) {
		tableModelConverBom = new JTableListModel(tbConverBom, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("是否备案", "isMerger", 60));
						list.add(addColumn("版本号", "version", 50));
						list.add(addColumn("版本单重", "unitWeight", 50));
						list.add(addColumn("料件货号", "bom.ptNo", 100));
						list.add(addColumn("商品编码", "bom.complex.code", 80));
						list.add(addColumn("料件名称", "bom.name", 200));
						list.add(addColumn("型号规格", "bom.spec", 200));
						list.add(addColumn("单耗", "bom.unitWaste", 80));
						list.add(addColumn("损耗率(%)", "bom.waste", 80));
						list.add(addColumn("计量单位", "bom.unit.name", 80));
						list.add(addColumn("材料来源", "materialSource", 80));
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
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 返回BOM结果
	 */
	private void getResult() {
		setReturnList(((JTableListModel) getJTable().getModel())
				.getCurrentRows());
		this.setOk(true);
		this.dispose();
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJPanel5(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "产品结构BOM表--已经归并(并备案)料件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(252, 134, 16)));
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (tbExgBefor == null) {
			tbExgBefor = new JTable();
			tbExgBefor.addMouseListener(new java.awt.event.MouseAdapter() {
			});
		}
		return tbExgBefor;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (spnExgBefor == null) {
			spnExgBefor = new JScrollPane();
			spnExgBefor.setViewportView(getJTable());
		}
		return spnExgBefor;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel = new JLabel();
			jLabel.setText("国内采购的材料是否需要转BOM，请到参数设定中进行设置！");
			jLabel.setBounds(new Rectangle(7, 11, 326, 18));
			jLabel.setBackground(java.awt.Color.blue);
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4

					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel4.setBackground(java.awt.Color.white);
			jPanel4.setPreferredSize(new Dimension(72, 40));
			jPanel4.add(jLabel, null);
			jPanel4.add(getBtnConversion(), null);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel5.setBackground(java.awt.Color.white);
			jPanel5.setPreferredSize(new Dimension(135, 40));
			jPanel5.add(getJButton1(), null);
			jPanel5.add(getJButton2(), null);
		}
		return jPanel5;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getResult();
				}
			});

		}
		return btnOk;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * 
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable2() {
		if (tbConverBom == null) {
			tbConverBom = new JTable();
		}
		return tbConverBom;
	}

	public void setVisible(boolean b) {
		if (b) {
			// 设置tbExgBefor第一行为选中
			this.tbExgBefor
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		super.setVisible(b);
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane2() {
		if (spnConverBom == null) {
			spnConverBom = new JScrollPane();
			spnConverBom.setViewportView(getJTable2());
			spnConverBom
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"成品BOM表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(250, 137, 24)));
		}
		return spnConverBom;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList() {
		return returnList;
	}

	/**
	 * @param returnList
	 *            The returnList to set.
	 */
	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	/**
	 * This method initializes btnConversion
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConversion() {
		if (btnConversion == null) {
			btnConversion = new JButton();
			btnConversion.setText("转换");
			btnConversion.setBounds(new Rectangle(335, 6, 104, 28));
			btnConversion.setName("btnConversion");
			btnConversion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							new fillBom().start();
						}
					});
		}
		return btnConversion;
	}

} // @jve:decl-index=0:visual-constraint="10,9"
