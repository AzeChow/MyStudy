/*
 * Created on 2005-6-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
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
import javax.swing.JLabel;

/**
 * @author yp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBlsFactoryCustoms extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel2 = null;

	private JComboBox jComboBox = null;

	private JTextField jTextField = null;

	private JButton btnQurrey = null;

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

	private InnerMergeData innerdata = null;

	private BlsInnerMergeAction blsInnerMergeAction = null;

	private JButton btnImport = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	/**
	 * This is the default constructor
	 */
	public FmBlsFactoryCustoms() {
		super();
		blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("blsInnerMergeAction");
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
		this.setSize(793, 527);
		this.setContentPane(getJContentPane());
		this.setHelpId("blsFactoryCustoms");
		this.setTitle("报关与工厂资料对照");
		initFirstDate();
		final Component component = this;
		final String helpId = this.getHelpId();
	}

	private void initFirstDate() {
		List list = blsInnerMergeAction.findBlsTenInnerMergeByInnerMerge(
				new Request(CommonVars.getCurrUser()));
		if (list != null && list.size() > 0) {
			initTable(list);
		} else {
			initTable(new Vector());
			initTableDetail(new Vector());
		}
		
		showDetailData();
	}

	private void initData() {		
		String tenCode = null;
		Integer seqNum = null;
		String sValues = null;
//		type = ((ItemProperty) this.jComboBox1.getSelectedItem()).getCode();
		sValues = jTextField.getText().trim() == null ? "" : jTextField
				.getText().trim();
		if (jComboBox.getSelectedItem() != null
				&& jComboBox.getSelectedItem().equals("归并序号")) {
			tenCode = null;
			if (!sValues.equals("")) {
				try {
					seqNum = new Integer(sValues);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "输入不合法，请输入整数！", "提示！",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			} else {
				seqNum = null;
			}
		} else if (jComboBox.getSelectedItem() != null
				&& jComboBox.getSelectedItem().equals("商品编码")) {
			seqNum = null;
			if (!sValues.equals("")) {
				tenCode = sValues;
			} else {
				tenCode = null;
			}
		}
		List list = blsInnerMergeAction.findBlsTenInnerMergeByInnerMerge(
				new Request(CommonVars.getCurrUser()), tenCode, seqNum);
//		System.out.println("显示数据");
		if (list != null && list.size() > 0) {
			initTable(list);
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
								"blsTenInnerMerge.comUnit.name", 80));
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
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel2());
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
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("查询栏位");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("查询值");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(3, 3, 3, 297);
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.ipadx = 2;
			gridBagConstraints5.ipady = -4;
			gridBagConstraints5.gridx = 5;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(3, 2, 3, 3);
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.ipadx = 2;
			gridBagConstraints3.ipady = -4;
			gridBagConstraints3.gridx = 4;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 3;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 100;
			gridBagConstraints2.ipady = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(3, 1, 3, 1);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 100;
			gridBagConstraints1.ipady = -3;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(3, 0, 3, 1);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJComboBox(), gridBagConstraints1);
			jPanel2.add(getJTextField(), gridBagConstraints2);
			jPanel2.add(getJButton2(), gridBagConstraints3);
			jPanel2.add(getJButton3(), gridBagConstraints5);
			jPanel2.add(jLabel, gridBagConstraints);
			jPanel2.add(jLabel1, gridBagConstraints4);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addItem("归并序号");
			jComboBox.addItem("商品编码");
			jComboBox.setSelectedIndex(-1);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JFormattedTextField();
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
			btnQurrey.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initData();
				}
			});
		}
		return btnQurrey;
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
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBlsFactoryCustoms.this.dispose();
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
			jToolBar1 = new JToolBar();
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
					if(tableModel.getCurrentRow()==null){
						JOptionPane.showMessageDialog(FmBlsFactoryCustoms.this, "请选择报关资料!","提示",2);
						return;
					}
					if(tableModelDetail.getCurrentRow()==null){
						JOptionPane.showMessageDialog(FmBlsFactoryCustoms.this, "请选择工厂资料!","提示",2);
						return;
					}
					DgBlsFactoryCustoms dg = new DgBlsFactoryCustoms();
					dg.setTableModel(tableModelDetail);
					dg.setVisible(true);
					tableModel.updateRow(tableModel.getCurrentRow());
					tableModelDetail
							.updateRow(tableModelDetail.getCurrentRow());
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
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

//					new getDefault().start();
					SwingWorker worker = new SwingWorker() {
						
						@Override
						protected Object doInBackground() throws Exception {
							try {
								CommonProgress.showProgressDialog();  //  @jve:decl-index=0:
								CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
								blsInnerMergeAction.initBlsUnitDedault(new Request(CommonVars
										.getCurrUser()));
//								System.out.println("查询数据");								
							} catch (Exception e) {
								JOptionPane.showMessageDialog(FmBlsFactoryCustoms.this,
										"加载失败：！" + e.getMessage(), "提示", 2);
							} finally {
								CommonProgress.closeProgressDialog();
							}
							return null;
						}
						
						 @Override
						 protected void done() {							 
//							 System.out.println("done");
							 initData();
						 }
						};
					worker.execute();
//					isss++;
//					System.out.println("-----------"+isss);
				}
			});
		}
		return jButton5;
	}
	
//	int isss=0;

//	class getDefault extends Thread {
//		public void run() {
//			try {
//				CommonProgress.showProgressDialog();
//				CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
//				blsInnerMergeAction.initBlsUnitDedault(new Request(CommonVars
//						.getCurrUser()));
//				CommonProgress.closeProgressDialog();
//			} catch (Exception e) {
//				CommonProgress.closeProgressDialog();
//				JOptionPane.showMessageDialog(FmBlsFactoryCustoms.this,
//						"加载失败：！" + e.getMessage(), "提示", 2);
//			} finally {
//				initData();
//			}
//		}
//	}
	
	

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
//				JOptionPane.showMessageDialog(FmBlsFactoryCustoms.this,
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
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = blsInnerMergeAction.findBlsInnerMerge(new Request(CommonVars
							.getCurrUser()));//, "2"

					DgCustomInnerMergeImport dgimport = new DgCustomInnerMergeImport();
					dgimport.setTableModelCommInfo(tableModelDetail);
					dgimport.setListTemp(list);
					//dgimport.setTableModelTemp(tableModelDetail);
					dgimport.setVisible(true);
					}
				
			});
//			btnImport.addFocusListener(new java.awt.event.FocusAdapter(){
//				public void focusGained(FocusEvent arg0) {
//					if(tableModel.getCurrentRow()==null) return;
//					BlsTenInnerMerge tenInnerMerge = (BlsTenInnerMerge) tableModel
//					.getCurrentRow();
////				
////					List listdetail = blsInnerMergeAction.fi.findBlsInnerMerge(request)
////									  findDzscInnerMergeData(new Request(
////															CommonVars.getCurrUser()),
////															tenInnerMerge);
////					if (listdetail != null && listdetail.size() > 0) {
////						initTableDetail(listdetail);
////					} else {
////						initTableDetail(new Vector());
////					}
//				}	
//			});
		}
		return btnImport;
	}

	private void showDetailData() {
		if (tableModel == null)
			return;
		if (tableModel.getCurrentRow() == null)
			return;
		List list = blsInnerMergeAction
				.findBlsInnerMergeByTenInnerMerge(
						new Request(CommonVars
								.getCurrUser()),
						((BlsTenInnerMerge) tableModel
								.getCurrentRow()));
		List listdetail = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				listdetail.add((BlsInnerMerge) list.get(i));
			}
		}

		if (listdetail != null && listdetail.size() > 0) {
			initTableDetail(listdetail);
		} else {
			initTableDetail(new Vector());
		}
	}


} // @jve:decl-index=0:visual-constraint="10,10"
