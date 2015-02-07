/*
 * Created on 2005-6-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmBcsFactoryCustoms extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel2 = null;

	private JTextField jTextField = null;

	private JButton btnQurrey = null;

	private JComboBox jComboBox1 = null;

	private JButton btnClose = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;

	private String type = "";

	private InnerMergeData innerdata = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

	private JButton btnImport = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField1 = null;

	/**
	 * This is the default constructor
	 */
	public FmBcsFactoryCustoms() {
		super();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(935, 527);
		this.setContentPane(getJContentPane());
		this.setHelpId("bcsFactoryCustoms");
		this.setTitle("报关与工厂资料对照");
		initFirstDate();
		final Component component = this;
		final String helpId = this.getHelpId();
	}

	private void initFirstDate() {
		type = ((ItemProperty) this.jComboBox1.getSelectedItem()).getCode();
		List list = bcsInnerMergeAction.findBcsTenInnerMergeByInnerMerge(
				new Request(CommonVars.getCurrUser()), type);
		if (list != null && list.size() > 0) {
			initTable(list);
		} else {
			initTable(new Vector());
			initTableDetail(new Vector());
		}

		showDetailData();
	}

	private void initData() {
		type = ((ItemProperty) this.jComboBox1.getSelectedItem()).getCode();
		
		String tenCode = jTextField1.getText() == null ||"".equals(jTextField1.getText())? null : jTextField1
				.getText().trim();
		Integer seqNum = jTextField.getText() == null ||"".equals(jTextField.getText())? null :Integer.valueOf(jTextField
				.getText().trim()) ;
		
		List list = bcsInnerMergeAction.findBcsTenInnerMergeByInnerMerge(
				new Request(CommonVars.getCurrUser()), type, tenCode, seqNum);
		
		if (list != null && list.size() > 0) {
			initTable(list);
			List listDetail = bcsInnerMergeAction
					.findBcsInnerMergeByTenInnerMerge(new Request(CommonVars
							.getCurrUser()), ((BcsTenInnerMerge) tableModel
							.getCurrentRow()));
			if (listDetail != null && listDetail.size() > 0) {
				initTableDetail(listDetail);
			}
		} else {
			initTable(new Vector());
			initTableDetail(new Vector());
		}
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("归并序号", "seqNum", 70)); // 十位归并序号
						list.add(addColumn("报关商品编码", "complex.code", 100));
						list.add(addColumn("报关名称", "name", 180));
						list.add(addColumn("商品规格", "spec", 150));
						list.add(addColumn("报关单位", "comUnit.name", 80));
						return list;
					}
				});
	}

	private void initTableDetail(final List list) {
		tableModelDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list
								.add(addColumn("商品编码", "materiel.complex.code",
										100));
						list
								.add(addColumn("工厂名称", "materiel.factoryName",
										100));
						list.add(addColumn("规格", "materiel.factorySpec", 100));
						list
								.add(addColumn("企业单位", "materiel.calUnit.name",
										80));
						list.add(addColumn("申报单位",
								"bcsTenInnerMerge.comUnit.name", 80));
						list.add(addColumn("折算系数", "unitConvert", 80));
						list.add(addColumn("单价", "materiel.ptPrice", 80));
						list.add(addColumn("净重", "materiel.ptNetWeight", 80));
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(300);
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
			jToolBar.setLayout(f);
			jToolBar.add(getJPanel2());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(453, 4, 63, 22));
			jLabel1.setText("商品编码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(199, 5, 148, 22));
			jLabel.setText("输入查询条件      归并序号");
			JLabel lb = new JLabel();
			lb.setBounds(new Rectangle(4, 1, 61, 27));
			lb.setText("物料类别");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(650, 30));
			jPanel2.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			
			jPanel2.add(lb, null);
			jPanel2.add(getJComboBox1());
			jPanel2.add(getJTextField());
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
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
			jTextField = new JFormattedTextField();
			jTextField.setSize(new Dimension(100, 25));
			jTextField.setLocation(new Point(349, 3));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnQurrey == null) {
			btnQurrey = new JButton();
			btnQurrey.setText("查找");
			btnQurrey.setPreferredSize(new Dimension(65, 30));
			btnQurrey.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initData();
				}
			});
		}
		return btnQurrey;
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
			// 取消这里是为了与物料与报关单中物料类别一致现只取了成品，料件，设置。是客户要求的
			// jComboBox1.addItem(new ItemProperty(
			// MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			jComboBox1.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
			jComboBox1.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			// jComboBox1.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
			// "边角料"));
			// jComboBox1
			// .addItem(new ItemProperty(MaterielType.BAD_PRODUCT, "残次品"));
			jComboBox1.setSelectedIndex(2);
			jComboBox1.setBounds(new Rectangle(66, 2, 127, 25));
			jComboBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					initData();
				}
			});
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(65, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBcsFactoryCustoms.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showDetailData();

						}
					});
		}
		return jTable;
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
			jToolBar1.setLayout(f);
			jToolBar1.add(getJButton4());
			jToolBar1.add(getJButton5());
			jToolBar1.add(getBtnImport());
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
			jButton4.setText("修改单位折算与更新报关商品十码 ");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsFactoryCustoms.this,
								"请选择报关资料!", "提示", 2);
						return;
					}
					if (tableModelDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsFactoryCustoms.this,
								"请选择工厂资料!", "提示", 2);
						return;
					}
					DgBcsFactoryCustoms dg = new DgBcsFactoryCustoms();
					dg.setTableModel(tableModel);
					dg.setTableModelDetail(tableModelDetail);
					dg.setVisible(true);
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
			jButton5.setPreferredSize(new Dimension(65, 30));
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// new getDefault().start();
					SwingWorker worker = new SwingWorker() {

						@Override
						protected Object doInBackground() throws Exception {
							try {
								CommonProgress.showProgressDialog(); // @jve:decl-index=0:
								CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
								String type = ((ItemProperty) jComboBox1
										.getSelectedItem()).getCode();
								// jComboBox1.addItem(new
								// ItemProperty(MaterielType.MATERIEL, "料件"));
								int option = JOptionPane.showConfirmDialog(
										FmBcsFactoryCustoms.this,
										"您确认根据单位折算对照表更新折算系数吗？", "提示",
										JOptionPane.YES_NO_CANCEL_OPTION);
								if (option == JOptionPane.YES_OPTION) {
									bcsInnerMergeAction.initBcsUnitDedault(
											new Request(CommonVars
													.getCurrUser()), type);
								}
								// System.out.println("查询数据");
							} catch (Exception e) {
								JOptionPane.showMessageDialog(
										FmBcsFactoryCustoms.this, "加载失败：！"
												+ e.getMessage(), "提示", 2);
							} finally {
								CommonProgress.closeProgressDialog();
							}
							return null;
						}

						@Override
						protected void done() {
							// System.out.println("done");
							initData();
						}
					};
					worker.execute();
					// isss++;
					// System.out.println("-----------"+isss);
				}
			});
		}
		return jButton5;
	}

	// int isss=0;

	// class getDefault extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
	// bcsInnerMergeAction.initBcsUnitDedault(new Request(CommonVars
	// .getCurrUser()));
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmBcsFactoryCustoms.this,
	// "加载失败：！" + e.getMessage(), "提示", 2);
	// } finally {
	// initData();
	// }
	// }
	// }

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setRowHeight(25);
		}
		return jTable1;
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

//	class find extends Thread {
//		private String imrtype;
//
//		private Integer oldseqnum;
//
//		private Integer newseqnum;
//
//		public find(String imrtype, Integer oldseqnum, Integer newseqnum) {
//			this.imrtype = imrtype;
//			this.oldseqnum = oldseqnum;
//			this.newseqnum = newseqnum;
//		}
//
//		public void run() {
//			try {
//				CommonProgress.showProgressDialog();
//				CommonProgress.setMessage("系统正更新数据，请稍后...");
//				commonBaseCodeAction.changeCustomsSeqNum(new Request(CommonVars
//						.getCurrUser()), imrtype, oldseqnum, newseqnum);
//				CommonProgress.closeProgressDialog();
//			} catch (Exception e) {
//				CommonProgress.closeProgressDialog();
//				JOptionPane.showMessageDialog(FmBcsFactoryCustoms.this,
//						"更新数据失败：！" + e.getMessage(), "提示", 2);
//			} finally {
//				initData();
//			}
//		}
//	}

	class findFourNo extends Thread {
		private String imrtype;

		private Integer oldseqnum;

		private Integer newseqnum;

		public findFourNo(String imrtype, Integer oldseqnum, Integer newseqnum) {
			this.imrtype = imrtype;
			this.oldseqnum = oldseqnum;
			this.newseqnum = newseqnum;
		}

	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.setVisible(false);
			btnImport.setPreferredSize(new Dimension(65, 30));
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = bcsInnerMergeAction.findBcsInnerMerge(
							new Request(CommonVars.getCurrUser()), "2");

					DgCustomInnerMergeImport dgimport = new DgCustomInnerMergeImport();
					dgimport.setTableModelCommInfo(tableModelDetail);
					dgimport.setListTemp(list);
					// dgimport.setTableModelTemp(tableModelDetail);
					dgimport.setVisible(true);
				}

			});
			// btnImport.addFocusListener(new java.awt.event.FocusAdapter(){
			// public void focusGained(FocusEvent arg0) {
			// if(tableModel.getCurrentRow()==null) return;
			// BcsTenInnerMerge tenInnerMerge = (BcsTenInnerMerge) tableModel
			// .getCurrentRow();
			// //
			// // List listdetail =
			// bcsInnerMergeAction.fi.findBcsInnerMerge(request)
			// // findDzscInnerMergeData(new Request(
			// // CommonVars.getCurrUser()),
			// // tenInnerMerge);
			// // if (listdetail != null && listdetail.size() > 0) {
			// // initTableDetail(listdetail);
			// // } else {
			// // initTableDetail(new Vector());
			// // }
			// }
			// });
		}
		return btnImport;
	}

	private void showDetailData() {
		if (tableModel == null)
			return;
		if (tableModel.getCurrentRow() == null)
			return;
		List listdetail = bcsInnerMergeAction.findBcsInnerMergeByTenInnerMerge(
				new Request(CommonVars.getCurrUser()),
				((BcsTenInnerMerge) tableModel.getCurrentRow()));
		if (listdetail != null && listdetail.size() > 0) {
			initTableDetail(listdetail);
		} else {
			initTableDetail(new Vector());
		}
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(518, 2, 131, 26));
			jTextField1.setPreferredSize(new Dimension(4, 25));
		}
		return jTextField1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
