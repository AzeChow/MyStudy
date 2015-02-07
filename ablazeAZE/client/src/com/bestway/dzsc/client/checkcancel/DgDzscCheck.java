/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.ApplyType;
import com.bestway.dzsc.checkcancel.entity.CheckRange;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.message.logic.DzscMessageLogic;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCheck extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jbAdd = null;

	private JButton jbEdit = null;

	private JButton jbDelete = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField6 = null;

	private JButton jButton3 = null;

	private JTable jTableImg = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton4 = null;

	private JButton jbSave = null;

	private Customs custom = null; // 海关关区

	private DzscContractCavAction dzscContractCavAction = null;

	private DzscAction dzscAction = null;

	private DzscCheckHead dzscCheckHead = null; // 中期核查表头

	private int dataState = DataState.BROWSE;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel lbApplyType = null;

	private JTextField jTextField7 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JComboBox cbbCheckRange = null;

	private JComboBox cbbApplyType = null;

	private DzscMessageLogic dzscExportMessageLogic = null;

	private MaterialApplyAction materialApplyAction = null;

	private JLabel jLabel7 = null;

	private JTextField tfCopEmsNo = null;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscCheck() {
		super();
		dzscContractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			if (tableModel.getCurrentRow() != null) {
				dzscCheckHead = (DzscCheckHead) tableModel.getCurrentRow();
				custom = dzscCheckHead.getMasterCustoms();
			}
			showData();
			List dataSourceImg = null;
			dataSourceImg = dzscContractCavAction.findDzscCheckImg(new Request(
					CommonVars.getCurrUser()), dzscCheckHead);
			if (dataSourceImg != null && !dataSourceImg.isEmpty())
				initTableImg(dataSourceImg);
			else
				initTableImg(new Vector());
			// List dataSourceExg = null;
			// dataSourceExg = checkCancelAction.findCheckExg(
			// new Request(CommonVars.getCurrUser()), checkHead);
			// if (dataSourceExg!=null && !dataSourceExg.isEmpty())
			// initTableExg(dataSourceExg);
			// else
			// initTableExg(new Vector());
			// dataState = DataState.EDIT;
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("中期核查维护");
		this.setSize(527, 345);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

			}
		});
	}

	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "seqNum", 50,Integer.class));
						list.add(addColumn("1.料号", "ptNum", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("备案计量单位", "unit.name", 80));
						list.add(addColumn("2.原料库存数量", "materStock", 100));
						list.add(addColumn("3.原料在途数量", "materByWay", 100));
						list.add(addColumn("4.成品折料数量", "passExgUse", 100));
						list.add(addColumn("5.在线数量", "onlines", 100));
						list.add(addColumn("6.废品,残次品折料数量", "otherConvert", 120));
						list.add(addColumn("7.废料数量", "depose", 100));
						list.add(addColumn("8.转进未报数量", "turnInNoReport", 100));						
						return list;
					}
				});
	}

	private void showData() {
		// String copEmsNo = "";
		// if (dzscCheckHead.getCopEmsNo() == null
		// || dzscCheckHead.getCopEmsNo().trim().equals("")) {
		// DzscMaterielHead dzscMH = materialApplyAction
		// .findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
		// copEmsNo = dzscMH.getCopEntNo() == null ? "" : dzscMH.getCopEntNo();
		// } else {
		// copEmsNo = dzscCheckHead.getCopEmsNo();
		// }
		// ert

		jTextField.setText(dzscCheckHead.getEmsNo());
		jTextField1.setText(dzscCheckHead.getTradeCode());
		jTextField3.setText(dzscCheckHead.getMachCode());
		jTextField2.setText(dzscCheckHead.getTradeName());
		jTextField4.setText(dzscCheckHead.getMachName());
		tfCopEmsNo.setText(dzscCheckHead.getCopEmsNo());
		jTextField7.setText(dzscCheckHead.getColShdulTime() == null ? ""
				: dzscCheckHead.getColShdulTime().toString());
		if (dzscCheckHead.getColRange() != null) {
			this.cbbCheckRange.setSelectedIndex(dzscCheckHead.getColRange()
					.equals(CheckRange.EMS) ? 0 : 1);
		} else
			cbbCheckRange.setSelectedIndex(-1);
		if (dzscCheckHead.getFlagHG() != null) {
			this.cbbApplyType.setSelectedIndex(dzscCheckHead.getFlagHG()
					.equals(ApplyType.CHECK) ? 0 : 1);
		} else
			cbbApplyType.setSelectedIndex(-1);
		this.jCalendarComboBox1
				.setDate(dzscCheckHead.getCtockData() == null ? new Date()
						: dzscCheckHead.getCtockData());
		jCalendarComboBox.setDate(dzscCheckHead.getBeginDate());
		if (dzscCheckHead.getMasterCustoms() != null)
			jTextField6.setText(dzscCheckHead.getMasterCustoms().getName()+"("+
					dzscCheckHead.getMasterCustoms().getCode()+")");
		else
			jTextField6.setText("");
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jToolBar.add(getJbAdd());
			jToolBar.add(getJbEdit());
			jToolBar.add(getJbDelete());
			jToolBar.add(getJbSave());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jbAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbAdd() {
		if (jbAdd == null) {
			jbAdd = new JButton();
			jbAdd.setText("新增");
			jbAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						DzscCheckImg checkImg = null;
						List list = (List) DzscCheckCancelQuery.getInstance()
								.getImgFromInnerMerge( // 来自内部归并关系已备案
										dzscCheckHead);
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							checkImg = (DzscCheckImg) list.get(i);
							checkImg.setCheckHead(dzscCheckHead);
							checkImg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							checkImg = dzscContractCavAction.saveDzscCheckImg(
									new Request(CommonVars.getCurrUser()),
									checkImg);
							tableModelImg.addRow(checkImg);
						}
					}
					// else if (jTabbedPane.getSelectedIndex() == 2) //成品
					// {
					// CheckExg checkExg = null;
					// List list = (List) CommonQuery.getInstance()
					// .getExgFromInnerMerge( // 来自内部归并关系已备案
					// false, checkHead);
					// if (list == null || list.isEmpty())
					// return;
					// for (int i = 0; i < list.size(); i++) {
					// checkExg = (CheckExg) list.get(i);
					// checkExg.setCheckHead(checkHead);
					// checkExg.setCompany(CommonVars.getCurrUser()
					// .getCompany());
					// checkExg = dzscContractCavAction
					// .saveCheckExg(new Request(CommonVars
					// .getCurrUser()), checkExg);
					// tableModelExg.addRow(checkExg);
					// }
					// }
					setState();
				}
			});

		}
		return jbAdd;
	}

	/**
	 * 
	 * This method initializes jbEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setText("修改");
			jbEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscCheck.this,
									"请选择你将要修改的记录", "提示！", 0);
							return;
						}
						DgDzscCheckImg dgCheckImg = new DgDzscCheckImg();
						dgCheckImg.setTableModel(tableModelImg);
						dgCheckImg.setVisible(true);
					}
					// else if (jTabbedPane.getSelectedIndex() == 2) {//成品
					// DgCheckExg dgCheckExg = new DgCheckExg();
					// if (tableModelExg.getCurrentRow() == null){
					// JOptionPane.showMessageDialog(DgCheck.this,
					// "请选择你将要修改的记录",
					// "提示！", 0);
					// return;
					// }
					// dgCheckExg.setTableModel(tableModelExg);
					// dgCheckExg.setVisible(true);
					// }
				}
			});

		}
		return jbEdit;
	}

	/**
	 * 
	 * This method initializes jbDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbDelete() {
		if (jbDelete == null) {
			jbDelete = new JButton();
			jbDelete.setText("删除");
			jbDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscCheck.this,
									"请选择你要删除的资料", "确认", 1);
							return;
						}

						DzscCheckImg checkImg = (DzscCheckImg) tableModelImg
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(DgDzscCheck.this,
								"你确定要删除此记录吗?", "确认", 0) == 0) {
							dzscContractCavAction.deleteDzscCheckImg(
									new Request(CommonVars.getCurrUser()),
									checkImg);

							tableModelImg.deleteRow(checkImg);
						}
					}
					// else if (jTabbedPane.getSelectedIndex() == 2) {//成品
					// if (tableModelExg.getCurrentRow() == null) {
					// JOptionPane.showMessageDialog(DgCheck.this,
					// "请选择你要删除的资料", "确认", 1);
					// return;
					// }
					// CheckExg checkExg = (CheckExg) tableModelExg
					// .getCurrentRow();
					// if (JOptionPane.showConfirmDialog(DgCheck.this,
					// "你确定要删除此记录吗?", "确认", 0) == 0) {
					// dzscContractCavAction.deleteCheckExg(new Request(
					// CommonVars.getCurrUser()), checkExg);
					// tableModelExg.deleteRow(checkExg);
					// }
					// }
					setState();
				}
			});

		}
		return jbDelete;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("中期核查表头", null, getJPanel(), null);
			jTabbedPane.addTab("中期核查料件", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (DgDzscCheck.this.jTabbedPane.getSelectedIndex() == 1) {
								List dataSourceImg = dzscContractCavAction
										.findDzscCheckImg(new Request(
												CommonVars.getCurrUser()),
												dzscCheckHead);
								if (dataSourceImg != null
										&& !dataSourceImg.isEmpty())
									initTableImg(dataSourceImg);
								else
									initTableImg(new Vector());
							}
							setState();

						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(258, 35, 83, 18));
			jLabel7.setText("企业内部编号");
			lbApplyType = new JLabel();
			lbApplyType.setBounds(new java.awt.Rectangle(258, 162, 76, 21));
			lbApplyType.setText("申报类型");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(258, 194, 80, 21));
			jLabel10.setText("库存截止日期");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(34, 131, 76, 19));
			jLabel9.setText("核查期数");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(34, 164, 76, 19));
			jLabel8.setText("核查范围");
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(34, 35, 62, 18);
			jLabel.setText("电子帐册号");
			jLabel1.setBounds(34, 68, 78, 19);
			jLabel1.setText("经营单位代码");
			jLabel2.setBounds(34, 102, 75, 20);
			jLabel2.setText("经营单位名称");
			jLabel3.setBounds(258, 68, 78, 22);
			jLabel3.setText("加工单位代码");
			jLabel4.setBounds(258, 103, 74, 20);
			jLabel4.setText("加工单位名称");
			jLabel5.setBounds(34, 193, 77, 21);
			jLabel5.setText("本期起始日期");
			jLabel6.setBounds(258, 131, 77, 20);
			jLabel6.setText("主管海关代码");
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(lbApplyType, null);
			jPanel.add(getJTextField7(), null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getCbbCheckRange(), null);
			jPanel.add(getCbbApplyType(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfCopEmsNo(), null);
		}
		return jPanel;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(116, 35, 115, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(116, 68, 116, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(116, 102, 117, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(345, 67, 115, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(345, 102, 115, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(345, 130, 94, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(440, 130, 21, 21);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Customs c = (Customs) CommonQuery.getInstance()
							.getCustoms();
					if (c != null) {
						custom = c;
						getJTextField6().setText(c.getName()+"("+c.getCode()+")");
					}
				}
			});

		}
		return jButton3;
	}

	/**
	 * 
	 * This method initializes jTableImg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImg() {
		if (jTableImg == null) {
			jTableImg = new JTable();
			jTableImg.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						DgDzscCheckImg dgCheckImg = new DgDzscCheckImg();
						dgCheckImg.setTableModel(tableModelImg);
						dgCheckImg.setVisible(true);
					}
				}
			});
		}
		return jTableImg;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableImg());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("关闭");
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscCheck.this.dispose();

				}
			});

		}
		return jButton4;
	}

	/**
	 * 
	 * This method initializes jbSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fillData()) {
						dzscCheckHead = dzscContractCavAction
								.saveDzscCheckHead(new Request(CommonVars
										.getCurrUser()), dzscCheckHead);
						tableModel.updateRow(dzscCheckHead);
						dataState = DataState.BROWSE;
						setState();
					}
				}
			});

		}
		return jbSave;
	}

	private boolean fillData() {
		try {
			new Integer(this.jTextField7.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "核查期数必须填入整数！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (jCalendarComboBox1.getDate() == null) {
			JOptionPane.showMessageDialog(this, "库存截止日期必须填写！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		dzscCheckHead.setBeginDate(jCalendarComboBox.getDate());
		dzscCheckHead.setMasterCustoms(custom);
		dzscCheckHead.setColShdulTime(Integer.parseInt(this.jTextField7
				.getText()));
		dzscCheckHead.setCtockData(this.jCalendarComboBox1.getDate());
		dzscCheckHead.setCopEmsNo(this.tfCopEmsNo.getText().trim());
		if (cbbCheckRange.getSelectedIndex() == 0) {
			dzscCheckHead.setColRange(CheckRange.EMS);
		} else if (cbbCheckRange.getSelectedIndex() == 1) {
			dzscCheckHead.setColRange(CheckRange.ENT);
		}
		if (this.cbbApplyType.getSelectedIndex() == 0) {
			dzscCheckHead.setFlagHG(ApplyType.CHECK);
		} else if (this.cbbApplyType.getSelectedIndex() == 1) {
			dzscCheckHead.setFlagHG(ApplyType.ADJUST);
		}
		// showData();
		return true;
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
	 * @return Returns the tableModelExg.
	 */
	public JTableListModel getTableModelExg() {
		return tableModelExg;
	}

	/**
	 * @param tableModelExg
	 *            The tableModelExg to set.
	 */
	public void setTableModelExg(JTableListModel tableModelExg) {
		this.tableModelExg = tableModelExg;
	}

	/**
	 * @return Returns the tableModelImg.
	 */
	public JTableListModel getTableModelImg() {
		return tableModelImg;
	}

	/**
	 * @param tableModelImg
	 *            The tableModelImg to set.
	 */
	public void setTableModelImg(JTableListModel tableModelImg) {
		this.tableModelImg = tableModelImg;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(116, 195, 115, 22);
		}
		return jCalendarComboBox;
	}

	private void setState() {
		String state = DzscState.ORIGINAL;
		if (dzscCheckHead != null) {
			state = dzscCheckHead.getDeclareState();
		}
		this.jTextField7.setEditable(dataState != DataState.BROWSE);
		this.cbbApplyType.setEnabled(dataState != DataState.BROWSE);
		this.cbbCheckRange.setEnabled(dataState != DataState.BROWSE);
		this.jCalendarComboBox1.setEnabled(dataState != DataState.BROWSE);
		jbAdd.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (DzscState.ORIGINAL.equals(state))); // 新增
		jbEdit.setEnabled(((dataState == DataState.BROWSE && (jTabbedPane
				.getSelectedIndex() == 0)) || (isImgExgPageAndExistData()))
				&& (DzscState.ORIGINAL.equals(state)));
		jbDelete.setEnabled(isImgExgPageAndExistData()
				&& (DzscState.ORIGINAL.equals(state))); // 删除
		jbSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE
				&& (DzscState.ORIGINAL.equals(state))); // 保存
		jCalendarComboBox.setEnabled(dataState != DataState.BROWSE);
		jButton3.setEnabled(dataState != DataState.BROWSE);
	}

	private boolean isImgExgPageAndExistData() {
		if ((jTabbedPane.getSelectedIndex() == 1)
				&& (tableModelImg.getRowCount() > 0)) {
			return true;
		}
		if ((jTabbedPane.getSelectedIndex() == 2)
				&& (tableModelExg.getRowCount() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(new java.awt.Rectangle(116, 130, 115, 22));
		}
		return jTextField7;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(new java.awt.Rectangle(345, 195, 115,
					22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCheckRange() {
		if (cbbCheckRange == null) {
			cbbCheckRange = new JComboBox();
			cbbCheckRange.setBounds(new java.awt.Rectangle(116, 162, 115, 22));
			cbbCheckRange.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED
							&& dzscCheckHead != null) {
						List lsDzscEmsPorHead = dzscAction
								.findDzscEmsPorHeadExcu(new Request(CommonVars
										.getCurrUser()), dzscCheckHead
										.getEmsNo());
						DzscEmsPorHead dzscEmsPorHead = null;
						if (lsDzscEmsPorHead.size() > 0) {
							dzscEmsPorHead = (DzscEmsPorHead) lsDzscEmsPorHead
									.get(0);
						}
						if (cbbCheckRange.getSelectedIndex() == 0) {
							if (dzscEmsPorHead != null) {
								tfCopEmsNo.setText(dzscEmsPorHead.getCopTrNo());
							}
						} else if (cbbCheckRange.getSelectedIndex() == 1) {
							if (dzscEmsPorHead != null) {
								tfCopEmsNo
										.setText(dzscEmsPorHead.getCopEntNo());
							}
						}
					}
				}
			});
			cbbCheckRange.addItem(new ItemProperty(CheckRange.EMS, "按手册"));
			cbbCheckRange.addItem(new ItemProperty(CheckRange.ENT, "按企业"));

		}
		return cbbCheckRange;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbApplyType() {
		if (cbbApplyType == null) {
			cbbApplyType = new JComboBox();
			cbbApplyType.setBounds(new java.awt.Rectangle(345, 162, 115, 22));
			cbbApplyType.addItem(new ItemProperty(ApplyType.CHECK, "中期核查"));
			cbbApplyType.addItem(new ItemProperty(ApplyType.ADJUST, "调整库存"));

		}
		return cbbApplyType;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(new java.awt.Rectangle(345, 35, 115, 22));
			tfCopEmsNo.setEditable(false);
		}
		return tfCopEmsNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
