/*
 * Created on 2005-6-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmFactoryCustoms extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel2 = null;

	private JTextField jTextField = null;

	private JButton btnSearch = null;

	private JComboBox jComboBox1 = null;

	private JButton btnClose = null;

	private JTable tbHead = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JTable tbDetail = null;

	private JScrollPane jScrollPane1 = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private JButton jButton8 = null;

	private String type = "";

	private InnerMergeData innerdata = null; // @jve:decl-index=0:

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JTextField jTextField1 = null;

	/**
	 * This is the default constructor
	 */
	public FmFactoryCustoms() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		commonBaseCodeAction.checkFactoryCustomsBrowseAuthority(new Request(
				CommonVars.getCurrUser()));
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(819, 527);
		this.setContentPane(getJContentPane());
		this.setTitle("报关与工厂资料对照");
		initData();

	}

	private void initData() {
		type = ((ItemProperty) this.jComboBox1.getSelectedItem()).getCode();
		String tenName = jTextField1.getText() == null
				|| "".equals(jTextField1.getText()) ? null : jTextField1
				.getText().trim();
		String seqNum = jTextField.getText() == null
				|| "".equals(jTextField.getText()) ? null : jTextField
				.getText().trim();

		List list = commonBaseCodeAction.getInnerMergeDataToCollate(
				new Request(CommonVars.getCurrUser()), type, seqNum, tenName);
		if (list != null && list.size() > 0) {
			initTable(list);
			String tenNo = ((BillTemp) list.get(0)).getBill1();
			List listdetail = commonBaseCodeAction.findInnerMergeDataByTenNo(
					new Request(CommonVars.getCurrUser()), type, tenNo);
			if (listdetail != null && listdetail.size() > 0) {
				innerdata = (InnerMergeData) listdetail.get(0);
				initTableDetail(listdetail);
			} else {
				initTableDetail(new Vector());
			}
		} else {
			initTable(new Vector());
			initTableDetail(new Vector());
		}
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(tbHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("归并序号", "bill1", 80, Integer.class)); // 十位归并序号
						list.add(addColumn("商品编码", "bill2", 100));
						list.add(addColumn("报关商品名称", "bill3", 200));
						list.add(addColumn("报关商品规格", "bill4", 200));
						list.add(addColumn("报关单位", "bill5", 80));
						return list;
					}
				});
	}

	private void initTableDetail(final List list) {
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 60));
				list.add(addColumn("工厂料号", "materiel.ptNo", 100));
				list.add(addColumn("品名", "materiel.factoryName", 150));
				list.add(addColumn("规格型号", "materiel.factorySpec", 150));
				list.add(addColumn("企业单位", "materiel.calUnit.name", 80));
				list.add(addColumn("单位折算", "materiel.unitConvert", 80));
				list.add(addColumn("单重", "materiel.ptNetWeight", 80));

				list.add(addColumn("十位商品序号", "hsAfterTenMemoNo", 100));
				list.add(addColumn("十位商品编码", "hsAfterComplex.code", 100));
				list.add(addColumn("十位商品名称", "hsAfterMaterielTenName", 100));
				list.add(addColumn("十位商品规格", "hsAfterMaterielTenSpec", 100));
				list.add(addColumn("备案单位", "hsAfterMemoUnit.name", 80));

				list.add(addColumn("四码序号", "hsFourNo", 80));
				list.add(addColumn("四码编码", "hsFourCode", 80));
				list.add(addColumn("四码名称", "hsFourMaterielName", 80));
				return list;
			}
		};
		tbDetail.getTableHeader().setReorderingAllowed(false);
		JTableListModel.dataBind(tbDetail, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModelDetail = (JTableListModel) tbDetail.getModel();
		tableModelDetail.setAllowSetValue(true);
		tableModelAdapter.setEditableColumn(1);
		tbDetail.getColumnModel().getColumn(1).setHeaderRenderer(
				new CheckBoxHeader(false));
		tbDetail.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(3);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setPreferredSize(new Dimension(850, 34));
			jToolBar.setLayout(f);
			jToolBar.add(getJPanel2());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(436, 9, 52, 18));
			jLabel11.setText("报关名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(197, 8, 148, 18));
			jLabel1
					.setText("\u8f93\u5165\u67e5\u8be2\u6761\u4ef6      \u5f52\u5e76\u5e8f\u53f7");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(5, 9, 53, 18));
			jLabel.setText("物料类别");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(640, 34));
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJComboBox1(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(getJTextField1(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(348, 4, 83, 25));
		}
		return jTextField;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查找");
			btnSearch.setPreferredSize(new Dimension(65, 30));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initData();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
					"成品"));
			jComboBox1.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			jComboBox1.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
			jComboBox1.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			jComboBox1.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
					"边角料"));
			jComboBox1
					.addItem(new ItemProperty(MaterielType.BAD_PRODUCT, "残次品"));
			jComboBox1.setSelectedIndex(2);
			jComboBox1.setBounds(new Rectangle(62, 4, 132, 25));
		}
		return jComboBox1;
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
			btnClose.setPreferredSize(new Dimension(65, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFactoryCustoms.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes tbHead
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
			tbHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null) {
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								return;
							}
							String tenNo = ((BillTemp) tableModel
									.getCurrentRow()).getBill1();
							String type = ((ItemProperty) jComboBox1
									.getSelectedItem()).getCode();
							List listdetail = commonBaseCodeAction
									.findInnerMergeDataByTenNo(new Request(
											CommonVars.getCurrUser()), type,
											tenNo);
							if (listdetail != null && listdetail.size() > 0) {
								innerdata = (InnerMergeData) listdetail.get(0);
								initTableDetail(listdetail);
							} else {
								initTableDetail(new Vector());
							}

						}
					});
		}
		return tbHead;
	}

	//
	// class getMateriel extends Thread {
	// private String tenNo;
	//
	// public getMateriel(String tenNo) {
	// this.tenNo = tenNo;
	// }
	//
	// public void run() {
	// List listdetail = null;
	// try {
	// // CommonProgress.showProgressDialog(FmFactoryCustoms.this);
	// // CommonProgress.setMessage("系统正在加载资料，请稍后...");
	// String type = ((ItemProperty) jComboBox1.getSelectedItem())
	// .getCode();
	// listdetail = commonBaseCodeAction.findInnerMergeDataByTenNo(
	// new Request(CommonVars.getCurrUser()), type, tenNo);
	// innerdata = (InnerMergeData) listdetail.get(0);
	// // CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// // CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmFactoryCustoms.this, "加载失败：！"
	// + e.getMessage(), "提示", 2);
	// } finally {
	// if (listdetail != null && listdetail.size() > 0) {
	// initTableDetail(listdetail);
	// } else {
	// initTableDetail(new Vector());
	// }
	// }
	// }
	// }

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbHead());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.setLayout(f);
			jToolBar1.add(getJButton4());
			jToolBar1.add(getJButton6());
			jToolBar1.add(getJButton7());
			jToolBar1.add(getJButton8());
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton5());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("修改单位折算与单重 ");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelDetail
							.getSelectedDataByBoolProperty("isSelected");
					if (list.size() <= 0) {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择要修改的数据行!", "提示", 0);
						return;
					}
					DgFactoryUnitConver dg = new DgFactoryUnitConver();
					dg.setPasslist(list);
					dg.setVisible(true);
					if (dg.getIsOk()) {
						tableModelDetail.updateRows(dg.getPasslist());
					}
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
			jButton5.setText("默认系数");
			jButton5.setVisible(false);
			jButton5.setPreferredSize(new Dimension(64, 30));
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择数据！", "提示！", 1);
						return;
					}
					new getDefault().start();

				}
			});
		}
		return jButton5;
	}

	class getDefault extends Thread {
		public void run() {
			try {
				// CommonProgress.showProgressDialog(FmFactoryCustoms.this);
				// CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
				commonBaseCodeAction.initUnitDedault(new Request(CommonVars
						.getCurrUser()));
				// CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				// CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCustoms.this, "加载失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initData();
			}
		}
	}

	/**
	 * This method initializes tbDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDetail() {
		if (tbDetail == null) {
			tbDetail = new JTable();
		}
		return tbDetail;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("更新十码序号");
			jButton6.setVisible(false);
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择数据！", "提示！", 1);
						return;
					}
					DgChangeSeqNum obj = new DgChangeSeqNum();
					obj.setVisible(true);
					if (obj.isOk()) {
						Integer newValue = obj.getDValue();
						BillTemp data = (BillTemp) tableModel.getCurrentRow();
						Integer oldValue = innerdata.getHsAfterTenMemoNo();
						new find(type, oldValue, newValue).start();
					}

				}
			});
		}
		return jButton6;
	}

	class find extends Thread {
		private String imrtype;

		private Integer oldseqnum;

		private Integer newseqnum;

		public find(String imrtype, Integer oldseqnum, Integer newseqnum) {
			this.imrtype = imrtype;
			this.oldseqnum = oldseqnum;
			this.newseqnum = newseqnum;
		}

		public void run() {
			try {
				// CommonProgress.showProgressDialog(FmFactoryCustoms.this);
				// CommonProgress.setMessage("系统正更新数据，请稍后...");
				commonBaseCodeAction.changeCustomsSeqNum(new Request(CommonVars
						.getCurrUser()), imrtype, oldseqnum, newseqnum);
				// CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				// CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCustoms.this, "更新数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initData();
			}
		}
	}

	class findFourNo extends Thread {
		private String imrtype;

		private Integer oldseqnum;

		private Integer newseqnum;

		public findFourNo(String imrtype, Integer oldseqnum, Integer newseqnum) {
			this.imrtype = imrtype;
			this.oldseqnum = oldseqnum;
			this.newseqnum = newseqnum;
		}

		public void run() {
			try {
				// CommonProgress.showProgressDialog(FmFactoryCustoms.this);
				// CommonProgress.setMessage("系统正更新数据，请稍后...");
				commonBaseCodeAction.changeCustomsSeqNumFourNo(new Request(
						CommonVars.getCurrUser()), imrtype, oldseqnum,
						newseqnum);
				// CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				// CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFactoryCustoms.this, "更新数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initData();
			}
		}
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("更新十码与四码报关资料");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelDetail
							.getSelectedDataByBoolProperty("isSelected");

					if (list.size() <= 0) {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择要修改的数据行!", "提示", 0);
						return;
					}
					DgFactoryCustoms dg = new DgFactoryCustoms();
					dg.setTableModel(tableModelDetail);
					dg.setPasslist(list);
					dg.setVisible(true);
					if (dg.getIsOk()) {
//						System.out.println("==delete="
//								+ dg.getReturnList()[0].size() + "=update="
//								+ dg.getReturnList()[1].size());
						if (dg.getReturnList() != null
								&& dg.getReturnList()[0].size() > 0) {
							tableModelDetail.deleteRows(dg.getReturnList()[0]);
						} else if (dg.getReturnList() != null
								&& dg.getReturnList()[1].size() > 0) {
							List listU = dg.getReturnList()[1];
							for (int i=0;i<listU.size(); i++) {
								InnerMergeData data =(InnerMergeData)listU.get(i);
								tableModelDetail.updateRow(data);
							}
						}
					}
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("更新四码序号");
			jButton8.setVisible(false);
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRow() != null) {
						innerdata = (InnerMergeData) tableModelDetail
								.getCurrentRow();
					} else {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择数据！", "提示！", 1);
						return;
					}
					DgChangeSeqNum obj = new DgChangeSeqNum();
					obj.setVisible(true);
					if (obj.isOk()) {
						Integer newValue = obj.getDValue();
						BillTemp data = (BillTemp) tableModel.getCurrentRow();
						Integer oldValue = innerdata.getHsFourNo();
						new findFourNo(type, oldValue, newValue).start();
					}
				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("删除归并物料");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRows() != null) {
						if (JOptionPane.showConfirmDialog(
								FmFactoryCustoms.this, "确定要删处这些物料的归并？", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
						List ls = tableModelDetail.getCurrentRows();
						for (int i = 0; i < ls.size(); i++) {
							innerdata = (InnerMergeData) ls.get(i);
							commonBaseCodeAction.deleteInner(new Request(
									CommonVars.getCurrUser()), innerdata);
						}
					} else {
						JOptionPane.showMessageDialog(FmFactoryCustoms.this,
								"请选择数据！", "提示！", 1);
						return;
					}
					initData();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(493, 4, 138, 25));
			jTextField1.setPreferredSize(new Dimension(4, 25));
		}
		return jTextField1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
