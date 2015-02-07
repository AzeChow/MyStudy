/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.client.custom.common.DgMakeApplyToCustoms;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 报关清单
 * 
 * 刘民添加部分注释
 * 
 * 修改时间： 2009-7-31
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmApplyToCustomsBillList extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JTable tbImport = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExport = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnGenerateCustomsDeclaration = null;

	private JButton btnClose = null;

	private EncAction encAction = null;

	private EmsHeadH2k emsHeadH2k = null;

	private JTableListModel tableModel = null;

	private ManualDeclareAction manualDeclareAction = null;

	private JButton btnQuery = null;

	private JButton btnAllShow = null;

	private JButton btnUpdate = null;

	private JButton btnCustomDeclare = null;

	private JButton btnReceiptProcessing = null;
	/** 报关清单 */
	private ApplyToCustomsBillList billHead = null;

	private MessageAction messageAction = null;

	private JButton btnListOfAdjusted = null;

	private JPanel jPanel = null;

	private JLabel lbEntryDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbTo = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnRefresh = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="855,117"

	private JButton btnSource = null;

	private JMenuItem menuAdded = null; // @jve:decl-index=0:visual-constraint="639,343"

	private JMenuItem menuSingleApplicationInto = null; // @jve:decl-index=0:visual-constraint="699,341"

	private JMenuItem menuImport = null; // @jve:decl-index=0:visual-constraint="814,337"
	
	private JMenuItem menuImportXML = null;

	private JButton btnToCustoms = null;

	private JButton btnApplyToCustomsCheckup = null;

	private JButton btnCopy = null;// 转抄

	private JPanel jPanel1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmApplyToCustomsBillList() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");

		emsHeadH2k = getEmsHeadH2k();
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
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
		this.getJPopupMenu();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单");
		this.setContentPane(getJContentPane());
		this.setSize(816, 296);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						List list = getDataSource();
						initTable(list);
						setState();
					}
				});
		initComponents();
	}

	/**
	 * 初始化控件
	 */

	private void initComponents() {
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnSource());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnGenerateCustomsDeclaration());
			jToolBar.add(getBtnAllShow());
			jToolBar.add(getBtnUpdate());
			jToolBar.add(getBtnCustomDeclare());
			jToolBar.add(getBtnReceiptProcessing());
			jToolBar.add(getBtnListOfAdjusted());
			jToolBar.add(getBtnToCustoms());
			jToolBar.add(getBtnApplyToCustomsCheckup());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnClose());
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
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("进口", null, getJScrollPane(), null);
			jTabbedPane.addTab("出口", null, getJScrollPane1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							FmApplyToCustomsBillList.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							List list = getDataSource();
							initTable(list);
							FmApplyToCustomsBillList.this.setCursor(new Cursor(
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
			tbImport.setRowHeight(25);
			tbImport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1) {
						setState();
					}
					if (e.getClickCount() == 2) {
						// EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
						if (emsHeadH2k == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgApplyToCustomsBillList dgApplyToCustomsBillList = new DgApplyToCustomsBillList();
						billHead = (ApplyToCustomsBillList) tableModel
								.getCurrentRow();
						if (billHead.getListState().intValue() == ApplyToCustomsBillList.PASSED)
							dgApplyToCustomsBillList
									.setDataState(DataState.BROWSE);
						else
							dgApplyToCustomsBillList
									.setDataState(DataState.EDIT);
						dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
								.getSelectedIndex());
						dgApplyToCustomsBillList
								.setBillListTableModel(tableModel);
						dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
						dgApplyToCustomsBillList.setVisible(true);

					}
				}
			});
			tbImport.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						ApplyToCustomsBillList obj = (ApplyToCustomsBillList) tableModel
								.getCurrentRow();
						if (obj.getListState().equals(
								ApplyToCustomsBillList.DRAFT)) {
							obj.setListState(ApplyToCustomsBillList.Effect);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.Effect)) {
							obj
									.setListState(ApplyToCustomsBillList.ALREADY_SEND);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.ALREADY_SEND)) {
							obj.setListState(ApplyToCustomsBillList.PASSED);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.PASSED)) {
							// obj.setListState(ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION);
							// } else if(obj
							// .getListState()
							// .equals(
							// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION)){
							obj.setListState(ApplyToCustomsBillList.DRAFT);
						}
						obj = encAction.saveApplyToCustomsBillList(new Request(
								CommonVars.getCurrUser()), obj);
						tableModel.updateRow(obj);
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
			tbExport.setRowHeight(25);
			tbExport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1) {
						setState();
					}
					if (e.getClickCount() == 2) {
						if (emsHeadH2k == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgApplyToCustomsBillList dgApplyToCustomsBillList = new DgApplyToCustomsBillList();
						dgApplyToCustomsBillList.setDataState(DataState.BROWSE);
						dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
								.getSelectedIndex());
						dgApplyToCustomsBillList
								.setBillListTableModel(tableModel);
						dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
						dgApplyToCustomsBillList.setVisible(true);
						setState();
					}
				}
			});
			tbExport.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						ApplyToCustomsBillList obj = (ApplyToCustomsBillList) tableModel
								.getCurrentRow();
						if (obj.getListState().equals(
								ApplyToCustomsBillList.DRAFT)) {
							obj.setListState(ApplyToCustomsBillList.Effect);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.Effect)) {
							obj
									.setListState(ApplyToCustomsBillList.ALREADY_SEND);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.ALREADY_SEND)) {
							// obj
							// .setListState(ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION);
							// } else if (obj
							// .getListState()
							// .equals(
							// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION))
							// {
							obj.setListState(ApplyToCustomsBillList.PASSED);
						} else if (obj.getListState().equals(
								ApplyToCustomsBillList.PASSED)) {
							obj.setListState(ApplyToCustomsBillList.DRAFT);
						}
						obj = encAction.saveApplyToCustomsBillList(new Request(
								CommonVars.getCurrUser()), obj);
						tableModel.updateRow(obj);
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
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this,
								"系统没有建立正在执行的电子帐册", "提示", 0);
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this, "请选择要修改的数据",
								"提示", 0);
						return;
					}
					DgApplyToCustomsBillList dgApplyToCustomsBillList = new DgApplyToCustomsBillList();
					billHead = (ApplyToCustomsBillList) tableModel
							.getCurrentRow();
					if (billHead.getListState().intValue() == ApplyToCustomsBillList.Effect)
						dgApplyToCustomsBillList.setDataState(DataState.BROWSE);
					else
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
			btnDelete.setText("作废");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this, "请选择要修改的数据",
								"提示", 0);
						return;
					}
					deleteData();// new deleteApplyBill().start();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 清空表数据
	 */

	private void deleteData() {
		List lsit = new ArrayList();
		if (tableModel.getCurrentRows() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			List<ApplyToCustomsBillList> list = tableModel.getCurrentRows();
			for (ApplyToCustomsBillList data : list) {
				lsit.add(data);
				this.encAction.deleteApplyToCustomsBillList(new Request(
						CommonVars.getCurrUser()), data);

			}
			tableModel.deleteRows(lsit);
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示", 0);
		}
	}

	// class deleteApplyBill extends Thread {
	// public void run() {
	// try {
	// CommonProgress
	// .showProgressDialog(FmApplyToCustomsBillList.this);
	// CommonProgress.setMessage("系统正在删除资料，请稍后...");
	// // ApplyToCustomsBillList billList = (ApplyToCustomsBillList)
	// // tableModel
	// // .getCurrentRow();
	// // List list = encAction.findAtcMergeAfterComInfoNotToCustoms(
	// // new Request(CommonVars.getCurrUser()), billList,
	// // new Boolean(true));
	// // if (list != null && list.size() > 0) {
	// // CommonProgress.closeProgressDialog();
	// // JOptionPane.showMessageDialog(
	// // FmApplyToCustomsBillList.this,
	// // "该清单归并后有已转报关单，不可直接全部删除，请先删除报关单！", "提示", 0);
	// // return;
	// // }
	// // encAction.deleteApplyToCustomsBillList(new Request(CommonVars
	// // .getCurrUser()), billList);
	// // tableModel.deleteRow(billList);
	// setState();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmApplyToCustomsBillList.this,
	// "删除失败：！" + e.getMessage(), "提示", 2);
	// }
	// }
	// }

	/**
	 * This method initializes btnGenerateCustomsDeclaration
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGenerateCustomsDeclaration() {
		if (btnGenerateCustomsDeclaration == null) {
			btnGenerateCustomsDeclaration = new JButton();
			btnGenerateCustomsDeclaration.setText("生成报关单");
			btnGenerateCustomsDeclaration.setVisible(false);
			btnGenerateCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMakeCustomsDeclaration dg = new DgMakeCustomsDeclaration();
							if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
								dg.setImpexpflat(ApplyToCustomsBillList.IMPORT);
							} else {
								dg.setImpexpflat(ApplyToCustomsBillList.EXPORT);
							}

							dg.setVisible(true);
							if (dg.isOk()) {
								List list = getDataSource();
								initTable(list);
								setState();
							}
							
							btnRefresh.doClick();
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
			btnClose.setPreferredSize(new Dimension(60, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmApplyToCustomsBillList.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */

	private List getDataSource() {
		List list = null;
		if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
			list = encAction.findApplyToCustomsBillListByType(new Request(
					CommonVars.getCurrUser()), ApplyToCustomsBillList.IMPORT,
					this.cbbBeginDate.getDate(), this.cbbEndDate.getDate());
		} else {
			list = encAction.findApplyToCustomsBillListByType(new Request(
					CommonVars.getCurrUser()), ApplyToCustomsBillList.EXPORT,
					this.cbbBeginDate.getDate(), this.cbbEndDate.getDate());
		}
		return list;
	}

	/**
	 * 获取进出口表数据
	 * 
	 * @return
	 */

	private JTable getCurrentTable() {
		if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
			return tbImport;
		} else {
			return tbExport;
		}
	}

	/**
	 * 初始化进口表
	 * 
	 * @param list
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(getCurrentTable(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("进出口类型", "impExpType", 100));
						list.add(addColumn("清单状态", "listState", 50));
						list.add(addColumn("电子帐册编号", "emsHeadH2k", 100));
						list.add(addColumn("已转报关单", "effectstate", 50));
						list.add(addColumn("企业内部清单编号", "listNo", 100));
						list.add(addColumn("经营单位编码", "tradeCode", 100));
						list.add(addColumn("客户", "scmCoc.name", 100));
						list.add(addColumn("统一编号", "listUniteNo", 100));
						list.add(addColumn("清单编号", "listQpBillNo", 100));
						list.add(addColumn("经营单位名称", "tradeName", 150));
						list.add(addColumn("商品项数", "materialNum", 50));
						list.add(addColumn("清单申报日期", "listDeclareDate", 100));
						list.add(addColumn("报关单申报日期", "customsDeclarationDate",
								120));
						list.add(addColumn("车次编号", "catNo", 100));
						list.add(addColumn("报关单流水号", "customsDeclarationCode",
								120));
						list.add(addColumn("录入日期", "createdDate", 80));
						list
								.add(addColumn("录入员名称", "createdUser.userName",
										80));
						list.add(addColumn("进出口岸", "impExpCIQ.name", 80));
						list.add(addColumn("申报地海关", "declareCIQ.name", 80));
						list
								.add(addColumn("料件/成品标志",
										"materielProductFlag", 80));
						list.add(addColumn("运输方式", "transportMode.name", 80));
						list.add(addColumn("监管方式", "tradeMode.name", 80));
						list
								.add(addColumn("录入单位代号", "createdCompany.code",
										80));
						list
								.add(addColumn("录入单位名称", "createdCompany.name",
										80));
						list.add(addColumn("备注", "memos", 50));

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
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
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
							switch (Integer.parseInt(value.toString())) {
							case ApplyToCustomsBillList.DRAFT:
								str = "草稿";
								break;
							case ApplyToCustomsBillList.ALREADY_SEND:
								str = "已经申报";
								break;
							case ApplyToCustomsBillList.PASSED:
								str = "审批通过";
								break;
							// case
							// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION:
							// str = "已转报关单";
							// break;
							case ApplyToCustomsBillList.Effect:
								str = "生效";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});

		getCurrentTable().getColumnModel().getColumn(19).setCellRenderer(
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
		getCurrentTable().getColumnModel().getColumn(4).setCellRenderer(
				new MultiRenderer());
	}

	/**
	 * 表格渲染器
	 * 
	 * @author Administrator
	 * 
	 */

	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
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
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
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
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */

	private EmsHeadH2k getEmsHeadH2k() {
		EmsHeadH2k ems = null;
		List list = manualDeclareAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		for (int i = 0; i < list.size(); i++) {
			ems = (EmsHeadH2k) list.get(i);
			if (ems.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				return ems;
			}
		}
		return ems;
	}

	/**
	 * 设置清单状态
	 */

	private void setState() {
		if (tableModel.getCurrentRow() != null) {
			ApplyToCustomsBillList billList = (ApplyToCustomsBillList) tableModel
					.getCurrentRow();
			boolean isTransgerAtc = billList.getListState().intValue() == ApplyToCustomsBillList.PASSED;
			boolean isEffect = billList.getListState().intValue() == ApplyToCustomsBillList.Effect;
			boolean isSend = billList.getListState().intValue() == ApplyToCustomsBillList.ALREADY_SEND;
			this.btnEdit.setEnabled(!isTransgerAtc);
			this.btnDelete.setEnabled(!isTransgerAtc && !isEffect && !isSend);
		}
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("辅助查询");
			btnQuery.setBounds(new Rectangle(423, 5, 89, 25));
			btnQuery.setPreferredSize(new Dimension(60, 30));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer flat = -1;
					if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
						flat = ApplyToCustomsBillList.IMPORT;
					} else {
						flat = ApplyToCustomsBillList.EXPORT;
					}
					DgQueryConditionForList dg = new DgQueryConditionForList();
					dg.setType(flat);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						initTable(list);
					}
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnAllShow
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAllShow() {
		if (btnAllShow == null) {
			btnAllShow = new JButton();
			btnAllShow.setText("全部显示");
			btnAllShow.setVisible(false);
			btnAllShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
						list = encAction.findApplyBillList(new Request(
								CommonVars.getCurrUser()),
								ApplyToCustomsBillList.DRAFT, null,
								ApplyToCustomsBillList.IMPORT, null, null);
					} else {
						list = encAction.findApplyBillList(new Request(
								CommonVars.getCurrUser()),
								ApplyToCustomsBillList.DRAFT, null,
								ApplyToCustomsBillList.EXPORT, null, null);
					}
					initTable(list);
				}
			});
		}
		return btnAllShow;
	}

	/**
	 * 执行更新多线程类
	 * 
	 * @author Administrator
	 * 
	 */
	class ExeUpdate extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在更新数据，请稍后...");
				if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
					encAction.changeApplyBillState(new Request(CommonVars
							.getCurrUser()), ApplyToCustomsBillList.IMPORT);
				} else {
					encAction.changeApplyBillState(new Request(CommonVars
							.getCurrUser()), ApplyToCustomsBillList.EXPORT);
				}
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmApplyToCustomsBillList.this,
						"更新成功", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmApplyToCustomsBillList.this,
						"更新失败" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setText("更新");
			btnUpdate.setVisible(false);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ExeUpdate().start();
				}
			});
		}
		return btnUpdate;
	}

	/**
	 * This method initializes btnCustomDeclare
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomDeclare() {
		if (btnCustomDeclare == null) {
			btnCustomDeclare = new JButton();
			btnCustomDeclare.setText("海关申报");
			btnCustomDeclare.setPreferredSize(new Dimension(60, 30));
			btnCustomDeclare.setForeground(java.awt.Color.blue);
			btnCustomDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							encAction.customDeclare(new Request(CommonVars.getCurrUser()));
							billHead = (ApplyToCustomsBillList) tableModel
									.getCurrentRow();
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyToCustomsBillList.this,
										"请选择你将要申报的记录", "提示！", 0);
								return;
							}
							if (billHead.getListState().intValue() == ApplyToCustomsBillList.DRAFT) {
								JOptionPane.showMessageDialog(
										FmApplyToCustomsBillList.this,
										"此清单是草稿状态不能进行审报", "提示！", 0);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmApplyToCustomsBillList.this,
									"是否确定要将【电子帐册清单】向海关申报吗？", "确认", 0) == 0) {
								new Chelonian().start();
							}
						}
					});
		}
		return btnCustomDeclare;
	}

	/**
	 * 执行海关申报多线程类
	 * 
	 * @author Administrator
	 * 
	 */

	class Chelonian extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				billHead = (ApplyToCustomsBillList) tableModel.getCurrentRow();
				CommonProgress.setMessage("系统正在进行检查数据，请稍后...");
				List errlist = encAction.checkIsNull(new Request(CommonVars
						.getCurrUser()), billHead);
				if (errlist != null && errlist.size() > 0) {
					CommonProgress.closeProgressDialog();
					String str = "";
					boolean checkFT = true;
					String str1 = "";
					for (int j = 0; j < errlist.size(); j++) {
						str1 = (String) errlist.get(j);
						str += (String) errlist.get(j);
						checkFT = (str1.substring(5, str1.length()).equals(
								"法定数量不可为空")
								|| str1.substring(5, str1.length()).equals(
										"法定数量不可为空,法定数量二不可为空") || str1
								.substring(5, str1.length())
								.equals("法定数量二不可为空"))
								&& checkFT;
					}
					// 因为施耐德法定数量在系统中不能确认。而需要在Qp上录入。所以对于法定数量栏位只做提示！
					if (!str.equals("") && checkFT) {
						if (JOptionPane.showConfirmDialog(
								FmApplyToCustomsBillList.this, str + " ,是否继续!",
								"提示", 0) != 0) {
							return;
						}
					} else {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this, str + " ,不能生效",
								"提示", 2);
						return;
					}

				}
				CommonProgress.setMessage("系统正在进行海关申报，请稍后...");
				String messageName = null;
				Date now = new Date();
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String defaultDate = bartDateFormat.format(now);

				billHead.setListDeclareDate(java.sql.Date.valueOf(defaultDate));// 申报日期
				billHead = encAction.saveApplyToCustomsBillList(new Request(
						CommonVars.getCurrUser()), billHead);
				tableModel.updateRow(billHead);

				List list = CustomBaseList.getInstance().getGbtobigs();

				messageName = messageAction.exportMessage(new Request(
						CommonVars.getCurrUser()), billHead, 1, list)[0];
				messageAction.saveMessageQuery(new Request(CommonVars
						.getCurrUser()), MessageQuery.SENDTYPE,
						EdiType.CUSTOMS_BILL, DelcareType.APPLICATION,
						messageName, emsHeadH2k.getCopEmsNo(), "EMS217", 0);

				billHead.setListState(ApplyToCustomsBillList.ALREADY_SEND); // 已经申报
				billHead = encAction.saveApplyToCustomsBillList(new Request(
						CommonVars.getCurrUser()), billHead);
				tableModel.updateRow(billHead);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmApplyToCustomsBillList.this,
						"报文已经生成，位置在中间服务器的：" + messageName, "报文已生成", 1);
				setState();

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmApplyToCustomsBillList.this,
						"海关申报失败！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnReceiptProcessing
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReceiptProcessing() {
		if (btnReceiptProcessing == null) {
			btnReceiptProcessing = new JButton();
			btnReceiptProcessing.setText("回执处理");
			btnReceiptProcessing.setPreferredSize(new Dimension(60, 30));
			btnReceiptProcessing
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
							dgProcessMessage.setType("Bill");
							dgProcessMessage.setVisible(true);
							List dataSource = getDataSource();
							initTable(dataSource);
							setState();
						}
					});
		}
		return btnReceiptProcessing;
	}

	/**
	 * This method initializes btnListOfAdjusted
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListOfAdjusted() {
		if (btnListOfAdjusted == null) {
			btnListOfAdjusted = new JButton();
			btnListOfAdjusted.setText("清单调整");
			btnListOfAdjusted.setPreferredSize(new Dimension(60, 30));
			btnListOfAdjusted.setForeground(java.awt.Color.blue);
			btnListOfAdjusted
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgApplyToCustomsBillChange dg = new DgApplyToCustomsBillChange();
							if (jTabbedPane.getSelectedIndex() == ApplyToCustomsBillList.IMPORT) {
								dg.setFlat(ApplyToCustomsBillList.IMPORT);
							} else {
								dg.setFlat(ApplyToCustomsBillList.EXPORT);
							}
							dg.setVisible(true);
							List list = getDataSource();
							initTable(list);
							setState();
						}
					});
		}
		return btnListOfAdjusted;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(205, 8, 13, 18));
			lbTo.setText("到");
			lbEntryDate = new JLabel();
			lbEntryDate.setBounds(new Rectangle(16, 9, 52, 18));
			lbEntryDate.setText("录入日期");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(500, 34));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel.add(lbEntryDate, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(lbTo, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnRefresh(), null);
			jPanel.add(getBtnQuery(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(75, 5, 123, 25));
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
			cbbEndDate.setBounds(new Rectangle(223, 5, 123, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(new Rectangle(355, 5, 60, 25));
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = getDataSource();
					initTable(list);
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(70, 28));
			jPopupMenu.add(getMenuAdded());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getMenuSingleApplicationInto());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getMenuImport());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getMenuImportXML());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes btnSource
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSource() {
		if (btnSource == null) {
			btnSource = new JButton();
			btnSource.setText("清单来源");
			btnSource.setPreferredSize(new Dimension(60, 30));
			btnSource.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenu().show(getBtnSource(), 0,
							getBtnSource().getHeight());
				}
			});
		}
		return btnSource;
	}

	/**
	 * This method initializes menuAdded
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuAdded() {
		if (menuAdded == null) {
			menuAdded = new JMenuItem();
			menuAdded.setSize(new Dimension(47, 24));
			menuAdded.setText("新增");
			menuAdded.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this,
								"系统没有建立正在执行的电子帐册", "提示", 0);
						return;
					}
					if (emsHeadH2k.getEmsNo() == null
							|| emsHeadH2k.getEmsNo().trim().equals("")) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this, "电子帐册的编号不能为空",
								"提示", 0);
						return;
					}
					DgApplyToCustomsBillList dgApplyToCustomsBillList = new DgApplyToCustomsBillList();
					dgApplyToCustomsBillList.setDataState(DataState.ADD);
					dgApplyToCustomsBillList.setImpExpFlag(jTabbedPane
							.getSelectedIndex());
					dgApplyToCustomsBillList.setBillListTableModel(tableModel);
					dgApplyToCustomsBillList.setEmsHeadH2k(emsHeadH2k);
					dgApplyToCustomsBillList.setVisible(true);
				}
			});
		}
		return menuAdded;
	}

	/**
	 * This method initializes menuSingleApplicationInto
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuSingleApplicationInto() {
		if (menuSingleApplicationInto == null) {
			menuSingleApplicationInto = new JMenuItem();
			menuSingleApplicationInto.setSize(new Dimension(99, 23));
			menuSingleApplicationInto.setText("申请单转入");
			menuSingleApplicationInto.setForeground(java.awt.Color.blue);
			menuSingleApplicationInto
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							boolean isportgoods = jTabbedPane
									.getSelectedIndex() == ApplyToCustomsBillList.IMPORT;
							Integer materielProductType = -1;
							Integer type = -1;
							if (isportgoods) {
								materielProductType = Integer
										.parseInt(MaterielType.MATERIEL);
								type = ImpExpType.DIRECT_IMPORT;
							} else {
								materielProductType = Integer
										.parseInt(MaterielType.FINISHED_PRODUCT);
								type = ImpExpType.DIRECT_EXPORT;
							}
							MakeCusomsDeclarationParam para = new MakeCusomsDeclarationParam();
							para.setImpExpType(type);
							para.setMaterielType(EncCommon
									.getMaterielTypeByBillType(type));
							para.setImportFlag(isportgoods);
							DgMakeApplyToCustoms dialog = new DgMakeApplyToCustoms();
							dialog.setPara(para);
							dialog.setVisible(true);
							List list = getDataSource();
							initTable(list);
							setState();

						}
					});
		}
		return menuSingleApplicationInto;
	}

	/**
	 * This method initializes menuImport
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuImport() {
		if (menuImport == null) {
			menuImport = new JMenuItem();
			menuImport.setSize(new Dimension(69, 28));
			menuImport.setText("清单导入");
			menuImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportApplyToCustomsBillListDefault dgImportApplyToCustomsBillList = new DgImportApplyToCustomsBillListDefault();
					dgImportApplyToCustomsBillList.setVisible(true);
					List list = getDataSource();
					initTable(list);
				}
			});
		}
		return menuImport;
	}
	/**
	 * This method initializes menuImport
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuImportXML() {
		if (menuImportXML == null) {
			menuImportXML = new JMenuItem();
			menuImportXML.setSize(new Dimension(69, 28));
			menuImportXML.setText("清单导入(XML)");
			menuImportXML.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportedApplyToCustomsBillXML dgImportedApplyToCustomsBillXML = new DgImportedApplyToCustomsBillXML();
					if(jTabbedPane.getSelectedIndex()==0)
						dgImportedApplyToCustomsBillXML.setIn(true);
					else
						dgImportedApplyToCustomsBillXML.setIn(false);
					dgImportedApplyToCustomsBillXML.setVisible(true);
					List list = getDataSource();
					initTable(list);
				}
			});
		}
		return menuImportXML;
	}
	/**
	 * This method initializes btnToCustoms
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnToCustoms() {
		if (btnToCustoms == null) {
			btnToCustoms = new JButton();
			btnToCustoms.setText("转报关单");
			btnToCustoms.setPreferredSize(new Dimension(60, 30));
			btnToCustoms.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this,
								"系统没有建立正在执行的电子帐册", "提示", 0);
						return;
					}
//					if (tableModel.getCurrentRow() == null) {
//						JOptionPane.showMessageDialog(
//								FmApplyToCustomsBillList.this, "请先选择清单！",
//								"提示", 0);
//						return;
//					}
//					billHead = (ApplyToCustomsBillList) tableModel.getCurrentRow();
					
//					if (getCurrentTable().getRowCount() == 0) {
//						JOptionPane.showMessageDialog(
//										FmApplyToCustomsBillList.this, "没有清单！",
//										"提示", 0);
//						return;
//					}
//					ApplyToCustomsBillList billList = (ApplyToCustomsBillList)
//							((JTableListModel)getCurrentTable().getModel()).getCurrentRow();
					DgMakeBillToCustoms dm = new DgMakeBillToCustoms();
					if (getJTabbedPane().getSelectedIndex() == 0) {
						dm.setImpExpFlag(ImpExpFlag.IMPORT);
					} else {
						dm.setImpExpFlag(ImpExpFlag.EXPORT);
					}
//					dm.setBillList(billHead);
					dm.setVisible(true);
					List list = getDataSource();
					initTable(list);
					setState();
				}
			});
		}
		return btnToCustoms;
	}

	/**
	 * This method initializes btnApplyToCustomsCheckup
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApplyToCustomsCheckup() {
		if (btnApplyToCustomsCheckup == null) {
			btnApplyToCustomsCheckup = new JButton();
			btnApplyToCustomsCheckup.setBounds(new Rectangle(318, 4, 58, 21));
			btnApplyToCustomsCheckup.setText("检查");
			btnApplyToCustomsCheckup.setPreferredSize(new Dimension(60, 30));
			btnApplyToCustomsCheckup.setForeground(Color.blue);
			btnApplyToCustomsCheckup
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModel.getCurrentRows();
							if (list == null || list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmApplyToCustomsBillList.this,
										"请选择清单！", "提示", 0);
								return;
							}
							List arrayList = encAction
									.applyToCustomsCheckup(new Request(
											CommonVars.getCurrUser()), list);
							DgShowApplyCheckup dgShowApplyCheckup = new DgShowApplyCheckup();
							dgShowApplyCheckup.setList(arrayList);
							dgShowApplyCheckup.setVisible(true);
						}
					});
		}
		return btnApplyToCustomsCheckup;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.setPreferredSize(new Dimension(60, 30));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmApplyToCustomsBillList.this, "请选择清单!", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					ApplyToCustomsBillList applyToCustomsBillList = (ApplyToCustomsBillList) tableModel
							.getCurrentRow();
					if (JOptionPane.showConfirmDialog(
							FmApplyToCustomsBillList.this, "是否也转抄表体资料?", "提示",
							0) == 0) {
						ApplyToCustomsBillList newApplyToCustomsBillList = encAction
								.copyApplyToCustomsBillListAndCommInfo(
										new Request(CommonVars.getCurrUser()),
										applyToCustomsBillList, true);
						tableModel.addRow(newApplyToCustomsBillList);
					} else {
						ApplyToCustomsBillList newApplyToCustomsBillList = encAction
								.copyApplyToCustomsBillListAndCommInfo(
										new Request(CommonVars.getCurrUser()),
										applyToCustomsBillList, false);
						tableModel.addRow(newApplyToCustomsBillList);
					}
				}
			});
		}
		return btnCopy;
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
			jPanel1.add(getJPanel(), BorderLayout.NORTH);
			jPanel1.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	public EncAction getEncAction() {
		return encAction;
	}

	public void setEncAction(EncAction encAction) {
		this.encAction = encAction;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}
	
}
