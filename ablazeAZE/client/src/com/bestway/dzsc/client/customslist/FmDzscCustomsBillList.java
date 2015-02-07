/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.customslist;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscListType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.client.contractexe.DgDzscExportCustomsDeclaration;
import com.bestway.dzsc.client.contractexe.DgDzscImportCustomsDeclaration;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscCustomsBillList extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JTable tbImport = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExport = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnGenerateCustomsDeclaration = null;

	private JButton btnClose = null;

	private DzscListAction dzscListAction = null;

	private DzscEmsPorHead emsHeadH2k = null;

	private JTableListModel tableModel = null;

	private DzscAction dzscAction = null;

	protected JToolBar jToolBar1 = null;

	protected JPanel jPanel = null;

	protected JButton jButton = null;

	protected JLabel lbEmsHead = null;

	protected JComboBox cbbEmshead = null;

	private JButton btnApply = null;

	private JButton btnProcess = null;

	private JButton btnRefresh = null;

	private JButton btnShowCustomsDeclaration = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton7 = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmDzscCustomsBillList() {
		super();
		initialize();
		dzscListAction = (DzscListAction) CommonVars.getApplicationContext()
				.getBean("dzscListAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		dzscAction.checkDzscCustomsBillListAuthority(new Request(CommonVars
				.getCurrUser()));
		initUIComponents();
		// emsHeadH2k = getEmsHeadH2k();
		tbImport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
						}
					}
				});
		tbExport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单");
		this.setContentPane(getJContentPane());
		this.setSize(847, 530);

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initTable();
						setState();
					}
				});
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar1(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getBtnGenerateCustomsDeclaration());
			jToolBar.add(getBtnShowCustomsDeclaration());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnClose());
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
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
			jTabbedPane.addTab("进口", null, getJScrollPane(), null);
			jTabbedPane.addTab("出口", null, getJScrollPane1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							FmDzscCustomsBillList.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							initTable();
							setState();
							FmDzscCustomsBillList.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes tbImport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if (tbImport == null) {
			tbImport = new JTable();
			tbImport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						// EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
						if (emsHeadH2k == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgDzscCustomsBillList dgApplyToCustomsBillList = new DgDzscCustomsBillList();
						dgApplyToCustomsBillList.setDataState(DataState.BROWSE);
						dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
								.getSelectedIndex());
						dgApplyToCustomsBillList
								.setBillListTableModel(tableModel);
						dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
						dgApplyToCustomsBillList.setVisible(true);
					}
				}
			});
		}
		return tbImport;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImport());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbExport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if (tbExport == null) {
			tbExport = new JTable();
			tbExport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						// EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
						if (emsHeadH2k == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgDzscCustomsBillList dgApplyToCustomsBillList = new DgDzscCustomsBillList();
						dgApplyToCustomsBillList.setDataState(DataState.BROWSE);
						dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
								.getSelectedIndex());
						dgApplyToCustomsBillList
								.setBillListTableModel(tableModel);
						dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
						dgApplyToCustomsBillList.setVisible(true);
					}
				}
			});
		}
		return tbExport;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExport());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "系统没有建立正在执行的电子手册",
								"提示", 0);
						return;
					}
					if (emsHeadH2k.getEmsNo() == null
							|| emsHeadH2k.getEmsNo().trim().equals("")) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "电子手册的编号不能为空",
								"提示", 0);
						return;
					}
					DgDzscCustomsBillList dgApplyToCustomsBillList = new DgDzscCustomsBillList();
					dgApplyToCustomsBillList.setDataState(DataState.ADD);
					dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
							.getSelectedIndex());
					dgApplyToCustomsBillList.setBillListTableModel(tableModel);
					dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
					dgApplyToCustomsBillList.setVisible(true);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "系统没有建立正在执行的电子手册",
								"提示", 0);
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "请选择要修改的数据", "提示",
								0);
						return;
					}
					DgDzscCustomsBillList dgApplyToCustomsBillList = new DgDzscCustomsBillList();
					dgApplyToCustomsBillList.setDataState(DataState.EDIT);
					dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
							.getSelectedIndex());
					dgApplyToCustomsBillList.setBillListTableModel(tableModel);
					dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
					dgApplyToCustomsBillList.setVisible(true);
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "请选择要修改的数据", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmDzscCustomsBillList.this, "是否确定删除数据!!!", "提示", 0) != 0) {
						return;
					}
					DzscCustomsBillList billList = (DzscCustomsBillList) tableModel
							.getCurrentRow();
					dzscListAction.deleteApplyToCustomsBillList(new Request(
							CommonVars.getCurrUser()), billList);
					tableModel.deleteRow(billList);
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnGenerateCustomsDeclaration
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGenerateCustomsDeclaration() {
		if (btnGenerateCustomsDeclaration == null) {
			btnGenerateCustomsDeclaration = new JButton();
			btnGenerateCustomsDeclaration.setText("生成报关单");
			btnGenerateCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (emsHeadH2k == null) {
								JOptionPane.showMessageDialog(
										FmDzscCustomsBillList.this, "请选择手册",
										"提示", JOptionPane.OK_OPTION);
							}
							DgDzscMakeCustomsDeclaration dg = new DgDzscMakeCustomsDeclaration();
							dg.setEmsNo(emsHeadH2k.getEmsNo());
							if (jTabbedPane.getSelectedIndex() == 0) {
								dg.setImpExpFlag(ImpExpFlag.IMPORT);
							} else {
								dg.setImpExpFlag(ImpExpFlag.EXPORT);
							}
							dg.setVisible(true);
							if (dg.isOk()) {
								initTable();
								setState();
							}
						}
					});
		}
		return btnGenerateCustomsDeclaration;
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
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscCustomsBillList.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	private List getDataSource() {
		List list = new ArrayList();
		if (this.cbbEmshead.getSelectedItem() == null) {
			return list;
		}
		this.emsHeadH2k = (DzscEmsPorHead) this.cbbEmshead.getSelectedItem();
		if (jTabbedPane.getSelectedIndex() == ImpExpFlag.IMPORT) {
			list = dzscListAction.findApplyToCustomsBillListByType(new Request(
					CommonVars.getCurrUser()), ImpExpFlag.IMPORT, emsHeadH2k,
					this.cbbBeginDate.getDate(), this.cbbEndDate.getDate());
		} else {
			list = dzscListAction.findApplyToCustomsBillListByType(new Request(
					CommonVars.getCurrUser()), ImpExpFlag.EXPORT, emsHeadH2k,
					this.cbbBeginDate.getDate(), this.cbbEndDate.getDate());
		}
		return list;
	}

	private JTable getCurrentTable() {
		if (jTabbedPane.getSelectedIndex() == ImpExpFlag.IMPORT) {
			return tbImport;
		} else {
			return tbExport;
		}
	}

	private void initTable() {
		List list = this.getDataSource();
		tableModel = new JTableListModel(getCurrentTable(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("进出口类型", "impExpType", 100));
						list.add(addColumn("清单状态", "listState", 100));
						list.add(addColumn("清单类型", "listType", 100));
						list.add(addColumn("是否已生成报关单", "isGenerateDeclaration",
								100));
						list.add(addColumn("电子手册编号", "emsHeadH2k", 80));
						list.add(addColumn("清单编号", "listNo", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("经营单位编码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));
						list.add(addColumn("商品项数", "materialNum", 100));
						list.add(addColumn("清单申报日期", "listDeclareDate", 100));
						// list.add(addColumn("报关单申报日期",
						// "customsDeclarationDate",
						// 100));
						list.add(addColumn("报关单统一编号", "customsSeqNo", 100));
						list.add(addColumn("录入日期", "createdDate", 100));
						list
								.add(addColumn("录入员名称", "createdUser.userName",
										100));
						list.add(addColumn("进出口岸", "impExpCIQ.name", 100));
						list.add(addColumn("申报地海关", "declareCIQ.name", 100));
						list.add(addColumn("料件/成品标志", "materielProductFlag",
								100));
						list.add(addColumn("运输方式", "transportMode.name", 100));
						list.add(addColumn("监管方式", "tradeMode.name", 100));
						list
								.add(addColumn("录入单位代号", "createdCompany.code",
										100));
						list
								.add(addColumn("录入单位名称", "createdCompany.name",
										100));
						list.add(addColumn("备注", "memos", 100));
						return list;
					}
				});
		getCurrentTable().getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpType.DIRECT_IMPORT:
								str = "料件进口";
								break;
							case ImpExpType.TRANSFER_FACTORY_IMPORT:
								str = "料件转厂";
								break;
							case ImpExpType.BACK_FACTORY_REWORK:
								str = "退厂返工";
								break;
							case ImpExpType.GENERAL_TRADE_IMPORT:
								str = "一般贸易进口";
								break;
							case ImpExpType.DIRECT_EXPORT:
								str = "成品出口";
								break;
							case ImpExpType.TRANSFER_FACTORY_EXPORT:
								str = "转厂出口";
								break;
							case ImpExpType.BACK_MATERIEL_EXPORT:
								str = "退料出口";
								break;
							case ImpExpType.REWORK_EXPORT:
								str = "返工复出";
								break;
							case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
								str = "边角料退港";
								break;
							case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
								str = "边角料内销";
								break;
							case ImpExpType.GENERAL_TRADE_EXPORT:
								str = "一般贸易出口";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
		getCurrentTable().getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							String listState = value.toString();
							if (listState.equals(DzscState.ORIGINAL)) {
								str = "原始状态";
							} else if (listState.equals(DzscState.APPLY)) {
								str = "正在申请";
							} else if (listState.equals(DzscState.EXECUTE)) {
								str = "正在执行";
							} else if (listState.equals(DzscState.CHANGE)) {
								str = "正在变更";
							} else if (listState.equals(DzscState.BACK_BILL)) {
								str = "退单状态";
							}
						}
						this.setText(str);
						return this;
					}
				});
		getCurrentTable().getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							int type = Integer.parseInt(value.toString());
							str = DzscListType.getListTypeDesc(type);
						}
						this.setText(str);
						return this;
					}
				});
		getCurrentTable().getColumnModel().getColumn(4).setCellRenderer(
				new checkBoxRenderer());
		getCurrentTable().getColumnModel().getColumn(17).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							String tempStr = value.toString();
							if (tempStr.equals(MaterielType.MATERIEL)) {
								str = "料件";
							} else if (tempStr
									.equals(MaterielType.FINISHED_PRODUCT)) {
								str = "成品";
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	class checkBoxRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
			} else if (value.equals("")) {
				checkBox.setSelected(false);
			} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			}
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
	}

	// private DzscEmsPorHead getEmsHeadH2k() {
	// DzscEmsPorHead ems = null;
	// List list = dzscAction.findExeEmsPorHead(new Request(CommonVars
	// .getCurrUser(), true));
	// for (int i = 0; i < list.size(); i++) {
	// ems = (DzscEmsPorHead) list.get(i);
	// if (ems.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
	// return ems;
	// }
	// }
	// return ems;
	// }

	private void setState() {
		if (tableModel.getCurrentRow() != null) {
			DzscCustomsBillList billList = (DzscCustomsBillList) tableModel
					.getCurrentRow();
			String billState = String.valueOf(billList.getListState());
			this.btnEdit.setEnabled(DzscState.ORIGINAL.equals(billState)
					|| DzscState.BACK_BILL.equals(billState));
			this.btnDelete.setEnabled(DzscState.ORIGINAL.equals(billState));
			this.btnApply.setEnabled(DzscState.ORIGINAL.equals(billState)
					|| DzscState.BACK_BILL.equals(billState));
			this.btnProcess.setEnabled(DzscState.APPLY.equals(billState));
		} else {
			this.btnEdit.setEnabled(false);
			this.btnDelete.setEnabled(false);
			this.btnApply.setEnabled(false);
			this.btnProcess.setEnabled(false);
		}
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	protected JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(getJButton());
			jToolBar1.add(getJPanel());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			lbEmsHead = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			lbEmsHead.setText("执行的合同(合同号+手册号)");
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 7;
			gridBagConstraints1.ipady = 4;
			gridBagConstraints1.insets = new java.awt.Insets(4, 10, 4, 2);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 338;
			gridBagConstraints2.ipady = -4;
			gridBagConstraints2.insets = new java.awt.Insets(4, 3, 3, 28);
			jPanel.add(lbEmsHead, gridBagConstraints1);
			jPanel.add(getCbbEmshead(), gridBagConstraints2);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("a");
			jButton.setVisible(false);
		}
		return jButton;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbEmshead() {
		if (cbbEmshead == null) {
			cbbEmshead = new JComboBox();
			cbbEmshead.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable();
					}
				}
			});
		}
		return cbbEmshead;
	}

	private void initUIComponents() {
		// 电子手册
		cbbEmshead.removeAllItems();
		List contracts = dzscAction.findExeEmsPorHead(new Request(CommonVars
				.getCurrUser()));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmshead.addItem((DzscEmsPorHead) contracts.get(i));
			}
			this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
					.getRender("ieContractNo", "emsNo", 100, 100));
		}
		if (this.cbbEmshead.getItemCount() > 0) {
			this.cbbEmshead.setSelectedIndex(0);
		}

		lbEmsHead.setText("执行的合同(合同号+手册号):");

		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "请选择要申报的数据", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmDzscCustomsBillList.this, "是否确定申报数据!!!", "提示", 0) != 0) {
						return;
					}
					DzscCustomsBillList billList = (DzscCustomsBillList) tableModel
							.getCurrentRow();
					try {
						DeclareFileInfo fileInfo = dzscListAction
								.applyDzscCustomsBillList(new Request(
										CommonVars.getCurrUser()), billList);
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, fileInfo
										.getFileInfoSpec(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						billList = dzscListAction.findDzscCustomsBillListById(
								new Request(CommonVars.getCurrUser()), billList
										.getId());
						tableModel.updateRow(billList);
						setState();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "申报失败,"
										+ ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnApply;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "请选择要处理的数据", "提示",
								0);
						return;
					}
					// if (JOptionPane.showConfirmDialog(
					// FmDzscCustomsBillList.this, "是否确定处理数据!!!", "提示",
					// JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					// return;
					// }
					DzscCustomsBillList billList = (DzscCustomsBillList) tableModel
							.getCurrentRow();
					List lsReturnFile=DzscCommon.getInstance().showDzscReceiptFile(
							DzscBusinessType.CUSTOMS_BILL_LIST,
							billList.getCopEmsNo());
					if (lsReturnFile.size()<=0) {
						return;
					}
					try {
						String result = dzscListAction
								.processDzscCustomsBillList(new Request(
										CommonVars.getCurrUser()), billList,lsReturnFile);
						billList = dzscListAction.findDzscCustomsBillListById(
								new Request(CommonVars.getCurrUser()), billList
										.getId());
						tableModel.updateRow(billList);
						setState();
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "处理成功\n" + result,
								"提示", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								FmDzscCustomsBillList.this, "处理失败"
										+ ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscCustomsBillList.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					initTable();
					FmDzscCustomsBillList.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowCustomsDeclaration() {
		if (btnShowCustomsDeclaration == null) {
			btnShowCustomsDeclaration = new JButton();
			btnShowCustomsDeclaration.setText("查看报关单");
			btnShowCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (emsHeadH2k == null) {
								JOptionPane.showMessageDialog(
										FmDzscCustomsBillList.this,
										"系统没有建立正在执行的电子手册", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmDzscCustomsBillList.this,
										"请选择要要查看报关单的清单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DzscCustomsBillList billList = (DzscCustomsBillList) tableModel
									.getCurrentRow();
							List list = dzscListAction
									.findCustomsDeclarationByBillList(
											new Request(CommonVars
													.getCurrUser()), billList
													.getListNo());
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(
										FmDzscCustomsBillList.this, "清单"
												+ billList.getListNo()
												+ "没有其生成的报关单", "提示", 0);
								return;
							}
							JTableListModel tableModel = new JTableListModel(
									new JTable(), list,
									new JTableListModelAdapter() {
										public List InitColumns() {
											List<JTableListColumn> list = new Vector<JTableListColumn>();
											return list;
										}
									});
							DzscCustomsDeclaration dc = (DzscCustomsDeclaration) list
									.get(0);
							if (dc.getImpExpFlag() != null) {
								if (dc.getImpExpFlag() == ImpExpFlag.IMPORT) {
									DgDzscImportCustomsDeclaration dg = new DgDzscImportCustomsDeclaration();
									dg.setCustomsDeclarationModel(tableModel);
									dg.setDataState(DataState.BROWSE);
									dg.setEmsHead(emsHeadH2k);
									dg.setVisible(true);
								} else {
									DgDzscExportCustomsDeclaration dg = new DgDzscExportCustomsDeclaration();
									dg.setCustomsDeclarationModel(tableModel);
									dg.setDataState(DataState.BROWSE);
									dg.setEmsHead(emsHeadH2k);
									dg.setVisible(true);
								}
							}
						}
					});
		}
		return btnShowCustomsDeclaration;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(147, 1, 17, 28));
			jLabel1.setText("\u5230");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(2, 1, 55, 28));
			jLabel.setText("\u5f55\u5165\u65e5\u671f");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getJButton7(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(57, 1, 88, 28));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(164, 1, 87, 28));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(new Rectangle(254, 1, 61, 28));
			jButton7.setText("\u5237\u65b0");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return jButton7;
	}
} // @jve:decl-index=0:visual-constraint="4,4"
