/*
 * Created on 2005-6-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.client.dzscmanage.DgDzscFactoryCustoms;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author xxm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscFactoryCustoms extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel2 = null;

	private JComboBox cbbField = null;

	private JTextField tfValue = null;

	private JButton btnQurrey = null;

	private JComboBox cbbMaterialType = null;

	private JButton btnCancel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;

	private String type = "";

	private JButton jbImport = null;

	/**
	 * This is the default constructor
	 */
	public FmDzscFactoryCustoms() {
		super();
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		dzscInnerMergeAction.checkfindInnerMergedTenSeqNum(new Request(CommonVars
				.getCurrUser()));
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
		this.setTitle("报关与工厂资料对照");

		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
				initData();
			}
		});
	}

//	private void initFirstDate() {
//		type = ((ItemProperty) this.cbbMaterialType.getSelectedItem())
//				.getCode();
//		List list = dzscInnerMergeAction.findInnerMergedTenSeqNum(new Request(
//				CommonVars.getCurrUser()), type);
//		if (list != null && list.size() > 0) {
//			initTable(list);
//		} else {
//			initTable(new Vector());
//			initTableDetail(new Vector());
//		}
//	}

	private void initData() {
		type = ((ItemProperty) this.cbbMaterialType.getSelectedItem())
				.getCode();
		String sFields = "No";
		String sValues = null;
		Object obj = null;
		sValues = tfValue.getText().trim() == null ? "" : tfValue.getText()
				.trim();
		if (cbbField.getSelectedItem() != null
				&& cbbField.getSelectedItem().equals("序号")) {
			sFields = "No";
			if (!sValues.equals("")) {
				try {
					obj = new Integer(sValues);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "输入不合法，请输入数字！", "提示！",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			} else {
				obj = null;
			}
		} else if (cbbField.getSelectedItem() != null
				&& cbbField.getSelectedItem().equals("商品编码")) {
			sFields = "code";
			if (!sValues.equals("")) {
				obj = sValues;
			} else {
				obj = null;
			}
		}

		List list = dzscInnerMergeAction.findInnerMergedTenSeqNum(new Request(
				CommonVars.getCurrUser()), type, sFields, obj);
		if (list != null && list.size() > 0) {
			initTable(list);
			if (tableModel.getCurrentRow() == null)
				return;
			DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) tableModel
					.getCurrentRow();
			List listdetail = dzscInnerMergeAction
					.findDzscInnerMergeData(new Request(
							CommonVars.getCurrUser()),
							tenInnerMerge);
			initTableDetail(listdetail);			
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
						list.add(addColumn("归并序号", "tenSeqNum", 50)); // 十位归并序号
						list.add(addColumn("商品编码", "tenComplex.code", 100));
						list.add(addColumn("报关商品名称", "tenPtName", 200));
						list.add(addColumn("报关商品规格", "tenPtSpec", 200));
						list.add(addColumn("报关单位", "tenUnit.name", 80));
						return list;
					}
				});
	}

	private void initTableDetail(final List list) {
		tableModelDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("工厂料号", "materiel.ptNo", 100));
						list.add(addColumn("品名", "materiel.factoryName", 150));
						list
								.add(addColumn("规格型号", "materiel.factorySpec",
										150));
						list
								.add(addColumn("工厂单位", "materiel.calUnit.name",
										80));
						list
						.add(addColumn("申报单位", "materiel.ptUnit.name",
								80));
						list.add(addColumn("单重", "materiel.ptNetWeight", 80));
						list.add(addColumn("单位折算", "unitConvert", 80));

						// list.add(addColumn("十位商品序号", "tenSeqNum", 100));
						// list.add(addColumn("十位商品编码", "tenComplex.code",
						// 100));
						// list.add(addColumn("十位商品名称", "tenPtName", 100));
						// list.add(addColumn("十位商品规格", "tenPtSpec", 100));
						// list.add(addColumn("备案单位", "tenUnit.name", 80));
						// list.add(addColumn("四码序号", "fourSeqNum", 80));
						// list.add(addColumn("四码编码", "fourComplex", 80));
						// list.add(addColumn("四码名称", "fourPtName", 80));
						// list.add(addColumn("四码规格", "fourPtSpec", 80));
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
			gridBagConstraints4.insets = new Insets(2, 3, 3, 276);
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 2;
			gridBagConstraints4.ipady = -3;
			gridBagConstraints4.gridx = 4;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.ipadx = 65;
			gridBagConstraints3.ipady = -2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(2, 1, 3, 1);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(2, 2, 3, 3);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 2;
			gridBagConstraints2.ipady = -3;
			gridBagConstraints2.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 85;
			gridBagConstraints1.ipady = 3;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(2, 1, 3, 1);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 70;
			gridBagConstraints.ipady = -2;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.insets = new Insets(2, 2, 3, 1);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getCbbField(), gridBagConstraints);
			jPanel2.add(getTfValue(), gridBagConstraints1);
			jPanel2.add(getBtnQuery(), gridBagConstraints2);
			jPanel2.add(getCbbMaterialType(), gridBagConstraints3);
			jPanel2.add(getBtnCancel(), gridBagConstraints4);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbField() {
		if (cbbField == null) {
			cbbField = new JComboBox();
			cbbField.addItem("序号");
			cbbField.addItem("商品编码");
			cbbField.setSelectedIndex(-1);
		}
		return cbbField;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfValue() {
		if (tfValue == null) {
			tfValue = new JFormattedTextField();
		}
		return tfValue;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
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
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialType() {
		if (cbbMaterialType == null) {
			cbbMaterialType = new JComboBox();
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterialType
					.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbbMaterialType.setSelectedIndex(2);
		}
		return cbbMaterialType;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscFactoryCustoms.this.dispose();
				}
			});
		}
		return btnCancel;
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
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;
							DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) tableModel
									.getCurrentRow();
							List listdetail = dzscInnerMergeAction
									.findDzscInnerMergeData(new Request(
											CommonVars.getCurrUser()),
											tenInnerMerge);
							if (listdetail != null && listdetail.size() > 0) {
								initTableDetail(listdetail);
							} else {
								initTableDetail(new Vector());
							}

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
			jToolBar1.add(getJbImport());
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
					DgDzscFactoryCustoms dg = new DgDzscFactoryCustoms();
					dg.setTableModel(tableModelDetail);
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
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new getDefault().start();

				}
			});
		}
		return jButton5;
	}

	class getDefault extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
				dzscInnerMergeAction.initDzscUnitDedault(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmDzscFactoryCustoms.this,
						"加载失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initData();
			}
		}
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
	 * This method initializes jbImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbImport() {
		if (jbImport == null) {
			jbImport = new JButton();
			jbImport.setText("导入");
			jbImport.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(MouseEvent arg0) {
					// 
					if(tableModel.getCurrentRow()==null){
						JOptionPane.showMessageDialog(FmDzscFactoryCustoms.this,
								"请选择报关料件", "提示", JOptionPane.OK_OPTION);
						return;
					}else if(tableModelDetail==null||tableModelDetail.getSize()==0){
						JOptionPane.showMessageDialog(FmDzscFactoryCustoms.this,
								"没有与您所选择的报关料件相对应的工厂料件，请做好对照关系", "提示", JOptionPane.OK_OPTION);
						return;						
					}else{
						DgCustomInnerMergeImport dgimport = new DgCustomInnerMergeImport();
						dgimport.setTableModelCommInfo(tableModelDetail);
						dgimport.setListTemp(tableModelDetail.getList());
						//dgimport.setTableModelTemp(tableModelDetail);
						dgimport.setVisible(true);
					}
				}
				
			});
			jbImport.addFocusListener(new java.awt.event.FocusAdapter(){
				public void focusGained(FocusEvent arg0) {
					// 
					if(tableModel.getCurrentRow()==null) return;
					DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) tableModel
					.getCurrentRow();
					List listdetail = dzscInnerMergeAction.
									  findDzscInnerMergeData(new Request(
															CommonVars.getCurrUser()),
															tenInnerMerge);
					if (listdetail != null && listdetail.size() > 0) {
						initTableDetail(listdetail);
					} else {
						initTableDetail(new Vector());
					}
				}	
			});
		}
		return jbImport;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
