package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import sun.misc.BASE64Decoder;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.message.DgProcessCustomsMessageTCS;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.action.BCSExpCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCExpCustomsAuthorityAction;
import com.bestway.customs.common.action.ExpCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.EntityShowCustomerError;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.bsw.core.client.eport.DgQPImport;
import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 出口报关单的父类窗体（2008年10月28日） 贺巍添加部分注释
 */
@SuppressWarnings("unchecked")
public abstract class FmBaseExportCustomsDeclaration extends JInternalFrameBase {
	protected InvoiceAction invoiceAction = (InvoiceAction) CommonVars
			.getApplicationContext().getBean("invoiceAction");

	protected JPanel jContentPane = null;

	protected JToolBar jToolBar = null;

	protected JTable jTable = null;

	protected JScrollPane jScrollPane = null;

	protected JButton btnAdd = null;

	protected JButton btnCancel = null;

	protected JButton btnEdit = null;

	protected JButton btnCustomsBrokerDeclare = null;

	protected JButton btnClose = null;

	protected JTableListModel tableModel = null;

	protected BaseEncAction baseEncAction = null;

	protected int projectType;

	// protected ManualDeclareAction manualDeclareAction = null;

	protected JButton btnCopy = null;

	protected JToolBar jToolBar1 = null;

	protected JPanel jPanel = null;

	protected JButton jButton = null;

	protected JLabel lbEmsHead = null;

	protected JComboBox cbbEmshead = null;

	private JPanel jPanel1 = null;
	/**
	 * 申报日期始
	 */
	private JCalendarComboBox jCalendarComboBox = null;

	private JButton jButton1 = null;

	protected JLabel jLabel = null;

	protected JLabel jLabel1 = null;
	/**
	 * 申报日期止
	 */
	private JCalendarComboBox jCalendarComboBox1 = null;

	private JButton jButton2 = null;

	protected JButton btnForcePass = null;

	private ExpCustomsAuthorityAction expCustomsAuthorityAction = null;

	private DZSCExpCustomsAuthorityAction dZSCExpCustomsAuthorityAction = null;

	private BCSExpCustomsAuthorityAction bCSExpCustomsAuthorityAction = null;

	protected JButton btnShowMoney = null;

	private JButton btnLoadBGD = null;

	private JButton btnExportBGD = null;

	private JButton btnShowAll = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="931,39"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="948,94"

	private JMenuItem jMenuItem1 = null; // @jve:decl-index=0:visual-constraint="953,132"

	private JPopupMenu pmInputBGD = null; // @jve:decl-index=0:visual-constraint="920,36"

	private JMenuItem miInputFromFile = null;

	private JMenuItem miInputDirect = null;
	
	private JMenuItem miInputFromQp = null;

	private JButton btnBrowse = null;

	private JButton btnSort = null;

	private JButton btnTcs = null;

	private JPanel jPanel2 = null;

	private JButton btnImportQP = null;

	private JPopupMenu pmCustomsBroker = null; // @jve:decl-index=0:visual-constraint="901,100"

	private JMenuItem miCustomsBrokerDeclaretion = null; // @jve:decl-index=0:visual-constraint="899,141"

	private JMenuItem miCustomsBrokerProcess = null; // @jve:decl-index=0:visual-constraint="899,172"
	private JButton btnPrint;


	/**
	 * 构造方法，初始化一些组件
	 * 
	 */
	public FmBaseExportCustomsDeclaration() {
		super();
		expCustomsAuthorityAction = (ExpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean("expCustomsAuthorityAction");
		dZSCExpCustomsAuthorityAction = (DZSCExpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCExpCustomsAuthorityAction");
		bCSExpCustomsAuthorityAction = (BCSExpCustomsAuthorityAction) CommonVars
				.getApplicationContext()
				.getBean("bCSExpCustomsAuthorityAction");
		initialize();

		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) jTable
								.getModel();
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

	public void setVisible(boolean b) {
		if (b) {
			if (projectType == ProjectType.BCUS) {
				expCustomsAuthorityAction.brownCustoms(new Request(CommonVars
						.getCurrUser()));
			} else if (projectType == ProjectType.DZSC) {
				dZSCExpCustomsAuthorityAction.brownCustoms(new Request(
						CommonVars.getCurrUser()));
			} else if (projectType == ProjectType.BCS) {
				bCSExpCustomsAuthorityAction.brownCustoms(new Request(
						CommonVars.getCurrUser()));
			}
		}
		super.setVisible(b);
	}

	/**
	 * 初始组件方法
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setTitle("出口报关单");
		this.setContentPane(getJContentPane());
		this.setSize(881, 381);
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(CommonVars.getEndDate(new Date()));
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initUIComponents();
				initTable(getDataSource());
				setState();
			}
		});
		// baseEncAction = (EncAction)
		// CommonVars.getApplicationContext().getBean(
		// "encAction");
		// this.manualDeclareAction = (ManualDeclareAction) CommonVars
		// .getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnShowMoney());
			// zyy 2012.3.15
			jToolBar.add(getBtnTcs());
//			jToolBar.add(getBtnCustomsBrokerDeclare());
//			jToolBar.add(getBtnImportQP());
			jToolBar.add(getBtnLoadBGD());
			jToolBar.add(getBtnExportBGD());
			jToolBar.add(getBtnShowAll());
			jToolBar.add(getBtnForcePass());
			jToolBar.add(getBtnSort());
			jToolBar.add(getBtnClose());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getJPanel1());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.setPreferredSize(new Dimension(45, 30));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.copyCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.copyCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.copyCustoms(new Request(
								CommonVars.getCurrUser()));
					}
					copyCustomsDeclarationData();
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	protected JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						// EmsHeadH2k ems = getEmsHead();
						// DgExportCustomsDeclaration dg = new
						// DgExportCustomsDeclaration();
						// dg.setCustomsDeclarationModel(tableModel);
						// dg.setDataState(DataState.BROWSE);
						// dg.setEmsHeadH2k(ems);
						// dg.setVisible(true);
						showCustomsDeclarationData(DataState.BROWSE);
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 查看报关单资料
	 * 
	 * @param dataState
	 */
	protected abstract void showCustomsDeclarationData(int dataState);

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	protected JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(45, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EmsHeadH2k ems = getEmsHead();
					// if (ems == null) {
					// JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!",
					// "提示", 1);
					// return;
					// }
					// if (ems.getEmsNo() == null
					// || ems.getEmsNo().trim().equals("")) {
					// JOptionPane.showMessageDialog(
					// FmBaseExportCustomsDeclaration.this, "电子帐册的编号不能为空",
					// "提示", 0);
					// return;
					// }
					// DgExportCustomsDeclaration dg = new
					// DgExportCustomsDeclaration();
					// dg.setCustomsDeclarationModel(tableModel);
					// dg.setDataState(DataState.ADD);
					// dg.setEmsHeadH2k(ems);
					// dg.setVisible(true);
					addCustomsDeclarationData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 新增报关单资料
	 * 
	 * @param dataState
	 */
	protected abstract void addCustomsDeclarationData();

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("作废");
			btnCancel.setPreferredSize(new Dimension(45, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenu().show(
							getBtnCancel(),
							getBtnCancel().getWidth()
									- getBtnCancel().getPreferredSize().width,
							getBtnCancel().getY()
									+ getBtnCancel().getPreferredSize().height);
					setState();
				}
			});
		}
		return btnCancel;
	}

	// /**
	// * 作废报关单资料
	// *
	// * @param dataState
	// */
	// protected void cancelCustomsDeclarationData(boolean isDelete) {
	// if (tableModel.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,
	// "请选择数据！", "提示", 0);
	// return;
	// }
	// BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration)
	// tableModel
	// .getCurrentRow();
	// // if (JOptionPane.showConfirmDialog(FmBaseExportCustomsDeclaration.this,
	// // "一般作废请按【是】，海关删单请按【否】！", "提示", 0) == 0) {
	// if (isDelete) {
	// if (JOptionPane.showConfirmDialog(
	// FmBaseExportCustomsDeclaration.this, "是否确定作废数据！", "提示", 0) != 0) {
	// return;
	// }
	// expCustomsAuthorityAction.genericDelete(new Request(CommonVars
	// .getCurrUser()));
	// baseEncAction.deleteCustomsDeclaration(new Request(CommonVars
	// .getCurrUser()), customsDeclaration);
	// tableModel.deleteRow(customsDeclaration);
	// //
	// // 删除出口发票信息
	// //
	// List list = baseEncAction
	// .findExportInvoiceItemByCustomsDeclarationId(new Request(
	// CommonVars.getCurrUser()), customsDeclaration
	// .getId());
	// if (list.size() > 0) {
	// if (list.get(0) instanceof ExportInvoiceItem) {
	// baseEncAction.deleteExportInvoiceItem(new Request(
	// CommonVars.getCurrUser()), (ExportInvoiceItem) list
	// .get(0));
	// }
	// }
	// this.invoiceAction.saveInvoiceforcustoms(new Request(CommonVars
	// .getCurrUser()), -1, ActionState.DELETE,
	// customsDeclaration, null);
	// setState();
	//
	// } else {
	// if (JOptionPane.showConfirmDialog(
	// FmBaseExportCustomsDeclaration.this, "是否确定海关删单！", "提示", 0) != 0) {
	// return;
	// }
	// expCustomsAuthorityAction.customsDelete(new Request(CommonVars
	// .getCurrUser()));
	// baseEncAction.deleteCustomsDeclarationDelete(new Request(CommonVars
	// .getCurrUser()), customsDeclaration, CommonVars
	// .getCurrUser());
	// // 删单
	// tableModel.deleteRow(customsDeclaration);
	// //
	// // 删除出口发票信息
	// //
	// List list = baseEncAction
	// .findExportInvoiceItemByCustomsDeclarationId(new Request(
	// CommonVars.getCurrUser()), customsDeclaration
	// .getId());
	// if (list.size() > 0) {
	// if (list.get(0) instanceof ExportInvoiceItem) {
	// baseEncAction.deleteExportInvoiceItem(new Request(
	// CommonVars.getCurrUser()), (ExportInvoiceItem) list
	// .get(0));
	// }
	// }
	// this.invoiceAction.saveInvoiceforcustoms(new Request(CommonVars
	// .getCurrUser()), -1, ActionState.DELETE,
	// customsDeclaration, null);
	// setState();
	// }
	// }
	/**
	 * 作废报关单资料
	 * 
	 * @param dataState
	 */
	protected void cancelCustomsDeclarationData(boolean isDelete) {
		if (tableModel.getCurrentRows() == null
				|| tableModel.getCurrentRows().isEmpty()) {
			JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,
					"请选择要作废的数据！", "提示", 0);
			return;
		}
		List list = tableModel.getCurrentRows();

		List ls = baseEncAction.findMakeBillCorrespondingInfoBase(new Request(
				CommonVars.getCurrUser()), list);
		if (ls != null && ls.size() > 0) {
			JOptionPane
					.showMessageDialog(FmBaseExportCustomsDeclaration.this,
							"结转报关单已经与海关单据作了对应,不能删除！", "提示",
							JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (isDelete) {
			if (projectType == ProjectType.BCUS) {
				expCustomsAuthorityAction.genericDelete(new Request(CommonVars
						.getCurrUser()));
			} else if (projectType == ProjectType.DZSC) {
				dZSCExpCustomsAuthorityAction.genericDelete(new Request(
						CommonVars.getCurrUser()));
			} else if (projectType == ProjectType.BCS) {
				bCSExpCustomsAuthorityAction.genericDelete(new Request(
						CommonVars.getCurrUser()));
			}
			if (JOptionPane.showConfirmDialog(
					FmBaseExportCustomsDeclaration.this, "是否确定作废数据！", "提示", 0) != 0) {
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
						.get(i);
				if (customsDeclaration.getEffective() != null
						&& customsDeclaration.getEffective()) {
					JOptionPane.showMessageDialog(
							FmBaseExportCustomsDeclaration.this,
							"所选报关单其中有生效的，请回卷后再执行！", "提示！",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					baseEncAction.deleteCustomsDeclaration(new Request(
							CommonVars.getCurrUser()), list);
					baseEncAction.updateTransferFactoryBill(new Request(
							CommonVars.getCurrUser()), list);
					tableModel.deleteRows(list);
					setState();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			if (projectType == ProjectType.BCUS) {
				expCustomsAuthorityAction.customsDelete(new Request(CommonVars
						.getCurrUser()));
			} else if (projectType == ProjectType.DZSC) {
				dZSCExpCustomsAuthorityAction.customsDelete(new Request(
						CommonVars.getCurrUser()));
			} else if (projectType == ProjectType.BCS) {
				bCSExpCustomsAuthorityAction.customsDelete(new Request(
						CommonVars.getCurrUser()));
			}
			if (JOptionPane.showConfirmDialog(
					FmBaseExportCustomsDeclaration.this, "是否确定海关删单！", "提示", 0) != 0) {
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
						.get(i);
				if (customsDeclaration.getEffective() != null
						&& customsDeclaration.getEffective()) {
					JOptionPane.showMessageDialog(
							FmBaseExportCustomsDeclaration.this,
							"所选报关单其中有生效的，请回卷后再执行！", "提示！",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					baseEncAction.deleteCustomsDeclarationDelete(new Request(
							CommonVars.getCurrUser()), customsDeclaration,
							CommonVars.getCurrUser());
					baseEncAction.updateTransferFactoryBill(new Request(
							CommonVars.getCurrUser()), list);
					// 删单
					tableModel.deleteRows(list);
					setState();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(45, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EmsHeadH2k ems = getEmsHead();
					// if (ems == null) {
					// JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!",
					// "提示", 1);
					// return;
					// }
					//
					// DgExportCustomsDeclaration dg = new
					// DgExportCustomsDeclaration();
					// dg.setCustomsDeclarationModel(tableModel);
					// dg.setDataState(DataState.EDIT);
					// dg.setEmsHeadH2k(ems);
					// dg.setVisible(true);
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要修改的资料!", "提示",
								1);
						return;
					}
					showCustomsDeclarationData(DataState.EDIT);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnCustomsBrokerDeclare
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCustomsBrokerDeclare() {
		if (btnCustomsBrokerDeclare == null) {
			btnCustomsBrokerDeclare = new JButton();
			btnCustomsBrokerDeclare.setText("报关行申报");
			btnCustomsBrokerDeclare.setPreferredSize(new Dimension(75, 30));
			btnCustomsBrokerDeclare.setForeground(java.awt.Color.blue);
			btnCustomsBrokerDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Component comp = (Component) e.getSource();
							getPmCustomBroker().show(comp, 0, comp.getHeight());
						}
					});
		}
		return btnCustomsBrokerDeclare;
	}

	/**
	 * 报关行申报
	 * 
	 */
	protected void customsBrokerDeclareCustomsDeclaration() {
		try {
			DgCustomsBrokerDeclare dgCustomsBrokerDeclare = new DgCustomsBrokerDeclare();
			dgCustomsBrokerDeclare.baseEncAction = baseEncAction;
			dgCustomsBrokerDeclare.projectType = projectType;
			dgCustomsBrokerDeclare.tableModel = tableModel;
			dgCustomsBrokerDeclare.setVisible(true);
		} finally {
			initTable(getDataSource());
		}
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmCustomBroker() {
		if (pmCustomsBroker == null) {
			pmCustomsBroker = new JPopupMenu();
			pmCustomsBroker.setSize(new Dimension(67, 38));
			pmCustomsBroker.add(getMiCustomsBrokerDeclaretion());
			pmCustomsBroker.add(getMiCustomsBrokerProcess());
		}
		return pmCustomsBroker;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCustomsBrokerDeclaretion() {
		if (miCustomsBrokerDeclaretion == null) {
			miCustomsBrokerDeclaretion = new JMenuItem();
			miCustomsBrokerDeclaretion.setSize(new Dimension(66, 22));
			miCustomsBrokerDeclaretion.setText("报关行申报");
			miCustomsBrokerDeclaretion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							customsBrokerDeclareCustomsDeclaration();
						}
					});
		}
		return miCustomsBrokerDeclaretion;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCustomsBrokerProcess() {
		if (miCustomsBrokerProcess == null) {
			miCustomsBrokerProcess = new JMenuItem();
			miCustomsBrokerProcess.setSize(new Dimension(70, 25));
			miCustomsBrokerProcess.setText("回执处理");
			miCustomsBrokerProcess
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgProcessCustomsMessage dg = new DgProcessCustomsMessage();
							dg.projectType = projectType;
							dg.setVisible(true);
						}
					});
		}
		return miCustomsBrokerProcess;
	}

	protected void declareCustomsDeclarationTcs() {
		try {
			DgProcessCustomsMessageTCS dgEdiMessage = new DgProcessCustomsMessageTCS();
			dgEdiMessage.setBaseEncAction(this.baseEncAction);
			dgEdiMessage.setTableModelCustoms(tableModel);
			dgEdiMessage.setProjectType(projectType);
			dgEdiMessage.setVisible(true);
		} finally {
			initTable(getDataSource());
		}

	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(45, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBaseExportCustomsDeclaration.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 获取表体的数据源
	 * 
	 * @return
	 */
	protected List getDataSource() {
		BaseEmsHead emsHead = (BaseEmsHead) this.cbbEmshead.getSelectedItem();
		if (emsHead == null) {
			return new ArrayList();
		}
		String emsNo = emsHead.getEmsNo();
		return baseEncAction.findExportCustomsDeclaration(new Request(
				CommonVars.getCurrUser()), emsNo, jCalendarComboBox.getDate(),
				jCalendarComboBox1.getDate());
	}

	public void resortExportCustomsDeclarationSerialNumber() {
		BaseEmsHead emsHead = (BaseEmsHead) this.cbbEmshead.getSelectedItem();
		if (emsHead == null) {
			new ArrayList();
		}
		String emsNo = emsHead.getEmsNo();
		baseEncAction.resortExportCustomsDeclarationSerialNumber(new Request(
				CommonVars.getCurrUser()), emsNo);
	}

	/**
	 * 初始化表体
	 * 
	 * @param list
	 */
	protected void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						String emsSpec = "";
						switch (projectType) {
						case ProjectType.BCS:
							emsSpec = "手册编号";
							break;
						case ProjectType.BCUS:
							emsSpec = "账册编号";
							break;
						case ProjectType.DZSC:
							emsSpec = "手册编号";
							break;
						}
						list.add(addColumn("流水号", "serialNumber", 40,
								Integer.class));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								140));
						list.add(addColumn("申报日期", "declarationDate", 80));
						list.add(addColumn("是否检查", "isCheck", 60));
						list.add(addColumn("是否生效", "effective", 60));
						list.add(addColumn("是否申报", "isSend", 60));
						if (projectType != ProjectType.BCS
								&& projectType != ProjectType.DZSC) {
							list.add(addColumn("是否已核销", "isEmsCav", 60));
						}
						list.add(addColumn("回执信息", "tcsResultMessage", 100));
						list.add(addColumn("金额", "totalMoney", 80));
						list.add(addColumn("备注", "memos", 100));
						list.add(addColumn("出口类型", "impExpType", 90));
						list.add(addColumn("件数", "commodityNum", 80,
								Integer.class));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("币制", "currency.currSymb", 50));
						list.add(addColumn("汇率", "exchangeRate", 50));
						list.add(addColumn("进出口岸", "customs.name", 100));
						list.add(addColumn("起运国",
								"countryOfLoadingOrUnloading.name", 100));
						list.add(addColumn("运输方式", "transferMode.name", 100));
						list.add(addColumn("运输工具", "conveyance", 100));
						list.add(addColumn("发货单位", "acceptGoodsCompany.name",
								100));
						list.add(addColumn("贸易方式", "tradeMode.name", 100));
						list.add(addColumn("装货港",
								"portOfLoadingOrUnloading.name", 100));
						list.add(addColumn("境内目的地",
								"domesticDestinationOrSource.name", 100));
						list.add(addColumn("集装箱号", "containerNum", 100));
						list.add(addColumn("用途", "uses.name", 100));
						list.add(addColumn("经营单位", "tradeName", 100));
						list.add(addColumn("申报单位", "declarationCompany.name",
								100));
						list.add(addColumn("客户名称", "customer.name", 100));
						list.add(addColumn("清单号码", "billListId", 200));
						list.add(addColumn("预录入号", "preCustomsDeclarationCode",
								100));
						list.add(addColumn("统一编号", "unificationCode", 100));
						list.add(addColumn("批准文号", "authorizeFile", 80));
						list.add(addColumn("发票号", "invoiceCode", 80));
						list.add(addColumn("提运单号", "billOfLading", 100));
						list.add(addColumn("装箱号", "wlserialNumber", 60));
						list.add(addColumn("KR#", "kr", 80));
						list.add(addColumn("生效时间", "effectiveDate", 80));
						list.add(addColumn("生效人", "effectiveMan", 80));
						list.add(addColumn("录入时间", "createDate", 80));
						list.add(addColumn("录入人员", "creater.userName", 80));
						list.add(addColumn("修改时间", "modifyDate", 80));
						list.add(addColumn("修改人员", "modifyPeople", 80));
						list.add(addColumn("报关行", "declaraCustomsBroker", 80));
						list.add(addColumn(emsSpec, "emsHeadH2k", 100));// "帐册编号"
						list.add(addColumn("扩展1栏位", "extendField1", 80));
						return list;
					}
				}

		);

		jTable.getColumnModel().getColumn(4)
				.setCellRenderer(new checkBoxRenderer());
		jTable.getColumnModel().getColumn(5)
				.setCellRenderer(new checkBoxRenderer());
		jTable.getColumnModel().getColumn(6)
				.setCellRenderer(new checkBoxRenderer());

		if (projectType == ProjectType.BCUS) {
			jTable.getColumnModel().getColumn(7)
					.setCellRenderer(new checkBoxRenderer());
			jTable.getColumnModel().getColumn(11)
					.setCellRenderer(new DefaultTableCellRenderer() {
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

		} else {
			jTable.getColumnModel().getColumn(10)
					.setCellRenderer(new DefaultTableCellRenderer() {
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

		}
		jTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * checkBox的渲染器类
	 * 
	 * @author sanvi
	 * 
	 */
	public class checkBoxRenderer extends DefaultTableCellRenderer {
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

	protected boolean getBoolean(Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	/**
	 * 设置窗体部分组件的状态
	 */
	protected void setState() {
		if (tableModel.getCurrentRow() == null) {
			return;
		}
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		boolean isEffective = getBoolean(customsDeclaration.getEffective());
		boolean isCancel = getBoolean(customsDeclaration.getCancel());
		boolean isSend = getBoolean(customsDeclaration.getIsSend());
		boolean isCheck = getBoolean(customsDeclaration.getIsCheck());
		
		this.btnEdit.setEnabled((!isEffective) && (!isCancel));
		this.btnCancel.setEnabled((!isEffective) && (!isCancel));
		this.jMenuItem1.setEnabled(isSend||isCheck);
		// this.btnDeclare.setEnabled((isEffective) && (!isCancel));
		/*
		 * AclUser user = CommonVars.getCurrUser(); String isManager =
		 * user.getUserFlag() == null ? "" : user.getUserFlag();
		 * btnForcePass.setVisible(isManager.equals("S"));
		 */
	}

	/**
	 * 转抄报关单
	 * 
	 */
	protected void copyCustomsDeclarationData() {
		if (JOptionPane.showConfirmDialog(this, "确定要转抄报关单吗?", "确认", 0) == 0) {
			if (tableModel.getCurrentRow() != null) {
				BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) tableModel
						.getCurrentRow();
				boolean isCopyComm = false;
				if (JOptionPane.showConfirmDialog(this, "是否同时转抄商品信息?", "确认", 0) == 0) {
					isCopyComm = true;
				}
				customsDeclaration = (BaseCustomsDeclaration) this.baseEncAction
						.copyCustomsDeclaration(
								new Request(CommonVars.getCurrUser()),
								customsDeclaration, isCopyComm);
				this.tableModel.addRow(customsDeclaration);
			} else {
				JOptionPane.showMessageDialog(this, "请选择要转抄报关单!!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
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
			lbEmsHead = new JLabel();
			jPanel = new JPanel();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setHgap(1);
			f.setVgap(2);
			jPanel.setLayout(f);
			lbEmsHead.setText("帐册号+合同号");
			jPanel.add(jLabel, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(lbEmsHead, null);
			jPanel.add(getCbbEmshead(), null);
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
			cbbEmshead.setSelectedItem(null);
			cbbEmshead.setPreferredSize(new Dimension(200, 24));
			cbbEmshead.setUI(new CustomBaseComboBoxUI(300));
			cbbEmshead.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable(getDataSource());
					}
				}
			});
		}
		return cbbEmshead;
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected abstract void initUIComponents();

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(80, 34));
			jLabel.setText("申报日期 从");
			jLabel1.setText("到");
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	protected JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setPreferredSize(new Dimension(120, 24));
			jCalendarComboBox
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTable(getDataSource());
							setState();
						}
					});
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("辅助查询");
			jButton1.setPreferredSize(new Dimension(85, 24));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					BaseEmsHead emsHead = (BaseEmsHead) cbbEmshead
							.getSelectedItem();
					if (emsHead != null) {
						String emsNo = emsHead.getEmsNo();
						DgCustomsDeclarationQueryCondition dg = new DgCustomsDeclarationQueryCondition();
						dg.setBaseEncAction(baseEncAction);
						dg.setImpExpFlag(ImpExpFlag.EXPORT);
						dg.setEmsNo(emsNo);
						dg.setVisible(true);
						if (dg.isOk()) {
							list = dg.getLsResult();
							initTable(list);
							setState();
						}
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	protected JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setPreferredSize(new Dimension(120, 24));
			jCalendarComboBox1
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTable(getDataSource());
							setState();
						}
					});
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("刷新");
			jButton2.setPreferredSize(new Dimension(75, 24));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable(getDataSource());
					setState();
				}
			});
		}
		return jButton2;
	}

	/**
	 * 检查必填栏位是否已填入值
	 * 
	 * @return javax.swing.JButton
	 */
	private List checkExportCustomsDeclarationData(
			BaseCustomsDeclaration customsDeclaration, List templist,
			List saveErrList, Component component) {
		if (customsDeclaration == null) {
			return null;
		}
		EntityShowCustomerError showerr = null;
		String err = "";
		if (customsDeclaration.getCustomsDeclarationCode() == null
				|| customsDeclaration.getCustomsDeclarationCode().equals("")) {
			showerr = new EntityShowCustomerError();
			showerr.setError("存在报关单号为空的报关单");
			saveErrList.add(showerr);
			return saveErrList;
		}

		if (customsDeclaration.getImpExpType() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("进出口类型不能为空");
			saveErrList.add(showerr);
		}
		if (customsDeclaration.getCustoms() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("出口口岸不能为空");
			saveErrList.add(showerr);
		}
		if (customsDeclaration.getCustomsDeclarationCode() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("报关单号不能为空");
			saveErrList.add(showerr);
		}

		if (customsDeclaration.getImpExpDate() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("出口日期不能为空");
			saveErrList.add(showerr);
		}

		if (customsDeclaration.getDeclarationDate() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("申报日期不能为空");
			saveErrList.add(showerr);
		}

		if (customsDeclaration.getTradeMode() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("贸易方式不能为空");
			saveErrList.add(showerr);
		}

		// edit by xxm 熟悉业务后做
		/*
		 * if (customsDeclaration.getAuthorizeFile() == null ||
		 * customsDeclaration.getAuthorizeFile().length() <9){ showerr = new
		 * EntityShowCustomerError(); showerr.setErrid(list.size() +
		 * 1);showerr.setError("抵运国不能为空", "提示", JOptionPane.YES_NO_OPTION);
		 * return false; }
		 */
		int pieceNum = customsDeclaration.getCommodityNum() == null ? 0
				: customsDeclaration.getCommodityNum().intValue();
		if (pieceNum <= 0) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("件数不能小于1");
			saveErrList.add(showerr);
		}
		double grossWeight = customsDeclaration.getGrossWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getGrossWeight()
						.toString());
		if (grossWeight <= 0) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("毛重不能小于或等于零");
			saveErrList.add(showerr);
		}
		// if (grossWeight < 1) {
		// showerr = new EntityShowCustomerError();
		// showerr.setCustomsDeclarationCode(customsDeclaration
		// .getCustomsDeclarationCode());
		// showerr.setError("毛重不能小于1");
		// saveErrList.add(showerr);
		// }
		double netWeight = customsDeclaration.getNetWeight() == null ? 0
				: Double.parseDouble(customsDeclaration.getNetWeight()
						.toString());
		if (netWeight <= 0) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("净重不能小于或等于零");
			saveErrList.add(showerr);
		}
		// if (netWeight < 1) {
		// showerr = new EntityShowCustomerError();
		// showerr.setCustomsDeclarationCode(customsDeclaration
		// .getCustomsDeclarationCode());
		// showerr.setError("净重不能小于1");
		// saveErrList.add(showerr);
		// }
		if (netWeight > grossWeight) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("净重不能大于毛重");
			saveErrList.add(showerr);
		}

		if (customsDeclaration.getCurrency() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("币制不能为空");
			saveErrList.add(showerr);
		}

		if (customsDeclaration.getCustomer() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("客户名称不能为空");
			saveErrList.add(showerr);
		}
		if (customsDeclaration.getInvoiceCode() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("发票号码不能为空");
			saveErrList.add(showerr);
		}

		// if (customsDeclaration.getUses() == null) {
		// showerr = new EntityShowCustomerError();
		// showerr.setErrid(list.size() + 1);
		// showerr.setError("用途不能为空", "提示",
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		if (customsDeclaration.getContainerNum() == null
				|| customsDeclaration.getContainerNum().trim().equals("")) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("集装箱号不能为空");
			saveErrList.add(showerr);
		}
		
		if (customsDeclaration.getDeclarationCustoms() == null) {
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError("报送海关");
			saveErrList.add(showerr);
		}

		System.out.println("templist:" + templist.size());
		if (templist.size() == 0) {
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			err = "无任何商品在里面";
			showerr = new EntityShowCustomerError();
			showerr.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			showerr.setError(err);
			saveErrList.add(showerr);
		}

		for (int i = 0; i < templist.size(); i++) {
			BaseCustomsDeclarationCommInfo bcustomsDeclarationCommInfo = new BaseCustomsDeclarationCommInfo();
			bcustomsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) templist
					.get(i);
			if (bcustomsDeclarationCommInfo.getCommAmount() == null) {
				err = err + "商品数量不能为空 ";
			}
			if (bcustomsDeclarationCommInfo.getCommUnitPrice() == null) {
				err = err + "商品单价不能为空 ";
			}
			if (bcustomsDeclarationCommInfo.getLegalUnit() == null) {
				err = err + "商品单位不能为空 ";
			}
			if (!err.equals("")
					&& bcustomsDeclarationCommInfo.getSerialNumber() != null) {
				err = "[项号:" + bcustomsDeclarationCommInfo.getSerialNumber()
						+ "(商品序号"
						+ bcustomsDeclarationCommInfo.getCommSerialNo() + ")]:"
						+ err;

				showerr = new EntityShowCustomerError();
				showerr.setCustomsDeclarationCode(customsDeclaration
						.getCustomsDeclarationCode());
				showerr.setError(err);
				err = "";
				saveErrList.add(showerr);
			}
		}

		// Edit by xxm 2006-1-3
		/*
		 * if (customsDeclaration.getCustomer() == null) { showerr = new
		 * EntityShowCustomerError(); showerr.setErrid(list.size() +
		 * 1);showerr.setError("客户名称不能为空", "提示", JOptionPane.YES_NO_OPTION);
		 * return false; }
		 */
		// edit by xxm
		// double exchangeRate = customsDeclaration.getExchangeRate() == null ?
		// 0
		// : Double.parseDouble(customsDeclaration.getExchangeRate()
		// .toString());
		// if (exchangeRate <= 0) {
		// showerr = new EntityShowCustomerError();
		// showerr.setErrid(list.size() + 1);
		// showerr.setError("汇率不能小于或等于零"
		// JOptionPane.YES_NO_OPTION);
		// return false;
		// }
		return saveErrList;
	}

	private JButton getBtnForcePass() {
		if (btnForcePass == null) {
			btnForcePass = new JButton();
			btnForcePass.setText("强制");
			btnForcePass.setPreferredSize(new Dimension(45, 30));
			btnForcePass.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction
								.getExpControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.getExpControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction
								.getExpControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					}

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseExportCustomsDeclaration.this,
								"请选择要强制操作的数据！", "提示", 0);
						return;
					}
					List choiceList = tableModel.getCurrentRows();
					List saveErrList = new ArrayList();

					for (int i = 0; i < choiceList.size(); i++) {
						BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) choiceList
								.get(i);
						
						List templist = baseEncAction
								.findCustomsDeclarationCommInfo(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration);
						saveErrList = checkExportCustomsDeclarationData(
								customsDeclaration, templist, saveErrList, null);
					}
					DgShowCustomerError dgShowCustomerError = new DgShowCustomerError(
							saveErrList);
					if (dgShowCustomerError.flag == 3) {
						dgShowCustomerError.dispose();
						return;
					}

					if (dgShowCustomerError.flag == 1) {
						if (JOptionPane.showConfirmDialog(
								FmBaseExportCustomsDeclaration.this,
								"是否确定强制生效数据！", "提示", 0) != 0) {
							return;
						}
						for (int j = 0; j < choiceList.size(); j++) {
							BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) choiceList
									.get(j);
							customsDeclaration.setEffective(new Boolean(true));
							customsDeclaration.setEffectiveDate(new Date());
							customsDeclaration.setEffectiveMan(CommonVars
									.getCurrUser().getUserName());
							customsDeclaration = baseEncAction
									.saveCustomsDeclaration(new Request(
											CommonVars.getCurrUser()),
											(BaseCustomsDeclaration) choiceList
													.get(j));
							tableModel.updateRow(customsDeclaration);
						}
					}

					if (dgShowCustomerError.flag == 2) {
						if (JOptionPane.showConfirmDialog(
								FmBaseExportCustomsDeclaration.this,
								"是否确定海关强制检查通过！", "提示", 0) != 0) {
							return;
						} else {
							if (JOptionPane.showConfirmDialog(
									FmBaseExportCustomsDeclaration.this,
									"此按扭为强制检查通过功能，非专业人员请慎用！", "提示", 0) != 0) {
								return;
							}
							for (int j = 0; j < choiceList.size(); j++) {
								BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) choiceList
										.get(j);
								customsDeclaration
										.setIsCheck(new Boolean(true));
								customsDeclaration = (BaseCustomsDeclaration) baseEncAction
										.saveCustomsDeclaration(new Request(
												CommonVars.getCurrUser()),
												customsDeclaration);
								tableModel.updateRow(customsDeclaration);
							}
						}
					}
					if (dgShowCustomerError.flag == 5) {
						if (JOptionPane.showConfirmDialog(
								FmBaseExportCustomsDeclaration.this,
								"是否确定返检查通过！", "提示", 0) != 0) {
							return;
						} else {
							if (JOptionPane.showConfirmDialog(
									FmBaseExportCustomsDeclaration.this,
									"此按扭为返强制检查通过功能，非专业人员请慎用！", "提示", 0) != 0) {
								return;
							}
							for (int j = 0; j < choiceList.size(); j++) {
								BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) choiceList
										.get(j);
								customsDeclaration
										.setIsCheck(new Boolean(false));
								customsDeclaration = (BaseCustomsDeclaration) baseEncAction
										.saveCustomsDeclaration(new Request(
												CommonVars.getCurrUser()),
												customsDeclaration);
								tableModel.updateRow(customsDeclaration);
							}
						}
					}
					if (dgShowCustomerError.flag == 4) {
						if (JOptionPane.showConfirmDialog(
								FmBaseExportCustomsDeclaration.this,
								"是否确定强制取消生效数据！", "提示", 0) != 0) {
							return;
						}

						for (int j = 0; j < choiceList.size(); j++) {
							BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) choiceList
									.get(j);
							customsDeclaration.setEffective(new Boolean(false));
							customsDeclaration.setEffectiveDate(null);
							customsDeclaration.setEffectiveMan(null);
							customsDeclaration = baseEncAction
									.saveCustomsDeclaration(new Request(
											CommonVars.getCurrUser()),
											(BaseCustomsDeclaration) choiceList
													.get(j));
							tableModel.updateRow(customsDeclaration);
						}
					}

				}
			});
		}
		return btnForcePass;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes btnShowMoney
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnShowMoney() {
		if (btnShowMoney == null) {
			btnShowMoney = new JButton();
			btnShowMoney.setText("显示金额");
			btnShowMoney.setPreferredSize(new Dimension(60, 30));
			btnShowMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map<String, Double> map = baseEncAction
							.findCustomsDeclarationMoney(
									new Request(CommonVars.getCurrUser()),
									ImpExpFlag.EXPORT);
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration base = (BaseCustomsDeclaration) list
								.get(i);
						base.setTotalMoney(map.get(base.getId()));
					}
					tableModel.fireTableDataChanged();
				}
			});
		}
		return btnShowMoney;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLoadBGD() {
		if (btnLoadBGD == null) {
			btnLoadBGD = new JButton();
			btnLoadBGD.setText("导入");
			btnLoadBGD.setPreferredSize(new Dimension(45, 30));
			btnLoadBGD.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmInputBGD()
							.show(getBtnLoadBGD(),
									getBtnLoadBGD().getWidth()
											- getBtnLoadBGD()
													.getPreferredSize().width,
									getBtnLoadBGD().getY()
											+ getBtnLoadBGD()
													.getPreferredSize().height);
				}
			});
		}
		return btnLoadBGD;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnExportBGD() {
		if (btnExportBGD == null) {
			btnExportBGD = new JButton();
			btnExportBGD.setText("导出");
			btnExportBGD.setPreferredSize(new Dimension(45, 30));
			btnExportBGD.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationExport dg = new DgCustomsDeclarationExport();
					dg.setBaseEncAction(baseEncAction);
					dg.setImpExpFlag(ImpExpFlag.EXPORT);
					dg.setVisible(true);
				}
			});
		}
		return btnExportBGD;
	}

	/**
	 * This method initializes btnShowAll
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnShowAll() {
		if (btnShowAll == null) {
			btnShowAll = new JButton();
			btnShowAll.setText("显示所有");
			btnShowAll.setPreferredSize(new Dimension(60, 30));
			btnShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJCalendarComboBox().setDate(null);
					getJCalendarComboBox1().setDate(null);
					initTable(getDataSource());
					setState();
				}
			});
		}
		return btnShowAll;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(41, 22));
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setSize(new Dimension(55, 34));
			jMenuItem.setText("一般作废");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelCustomsDeclarationData(true);
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setSize(new Dimension(54, 34));
			jMenuItem1.setText("海关删单");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelCustomsDeclarationData(false);
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jPopupMenu1
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInputBGD() {
		if (pmInputBGD == null) {
			pmInputBGD = new JPopupMenu();
			pmInputBGD.setSize(new Dimension(67, 38));
			pmInputBGD.add(getMiInputFromFile());
			pmInputBGD.add(getMiInputDirect());
			pmInputBGD.add(getMiInputFromQp());
		}
		return pmInputBGD;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiInputFromFile() {
		if (miInputFromFile == null) {
			miInputFromFile = new JMenuItem();
			miInputFromFile.setSize(new Dimension(66, 22));
			miInputFromFile.setText("从文件中导入");
			miInputFromFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							inputExportBGDFromXML();
						}
					});
		}
		return miInputFromFile;
	}

	/**
	 * This method initializes jMenuItem11
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiInputDirect() {
		if (miInputDirect == null) {
			miInputDirect = new JMenuItem();
			miInputDirect.setSize(new Dimension(70, 25));
			miInputDirect.setText("直接导入");
			miInputDirect
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							 inputExportBGDFromQP();
							// inputExportBGDFromPY();
						}
					});
		}
		return miInputDirect;
	}
	private JMenuItem getMiInputFromQp(){
		if(miInputFromQp == null){
			miInputFromQp = new JMenuItem();
			miInputFromQp.setText("从qp直接导入");
			miInputFromQp.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e){
					DgQPImport dg = new DgQPImport(ImpExpFlag.EXPORT,projectType);
					dg.setVisible(true);
					initTable(getDataSource());
					setState();
//					importCustoms();
//					DgChooseInquireDate cid = new DgChooseInquireDate();
//					cid.setVisible(true);
//					SimpleDateFormat sdf = new SimpleDateFormat(
//							"yyyy-MM-dd");
//					Date beginDate = cid.getBeginDate();
//					Date endDate = cid.getEndDate();
//					if(beginDate == null)
//						return;
//					if(endDate == null)
//						return;
//					String strBeginDate = sdf.format(beginDate);
//					String strEndDate = sdf.format(endDate);
//					try {
//						chooseDay(strBeginDate,strEndDate);
//					} catch (ParseException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
////					Date date = cid.getBeginDate();
////					String strDate = sdf.format(date);
////					try {
////						getExportDeclaration(strDate);
////					} catch (ParseException e1) {
////						// TODO Auto-generated catch block
////						e1.printStackTrace();
////					}
				}
			});
		}
		return miInputFromQp;
	}
//	 private void chooseDay(String strBeginDate,String strEndDate) throws ParseException{
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		  Date beginDate = sdf.parse(strBeginDate);
//		  Date endDate = sdf.parse(strEndDate);
//		  SimpleDateFormat sdf1 = new SimpleDateFormat(
//					"yyyyMMdd");
//		  String strBeginDate1 = sdf1.format(beginDate);
//		  String strEndDate1 = sdf1.format(endDate);
//		  SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
//	      Calendar start = Calendar.getInstance();
//	      Calendar end = Calendar.getInstance();
//	      try {
//	          start.setTime(format.parse(strBeginDate1));
//	          end.setTime(format.parse(strEndDate1));
//	      } catch (ParseException e) {
//	          // TODO Auto-generated catch block
//	          e.printStackTrace();
//	      }
//	      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//	      String strStart2 = sdf2.format(start.getTime());
//	      getExportDeclaration(strStart2);
//	      while(start.before(end))
//	      {
////	          System.out.println(format.format(start.getTime()));
//	    	  start.add(Calendar.DATE ,1);
//	          SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
//	          String strStart0 = sdf0.format(start.getTime());
//	          try {
//	        	  getExportDeclaration(strStart0);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	      }
//	  }
	// private void inputExportBGDFromPY(){
	//
	// // 1.创建py文件
	// File dir = new File(System.getProperty("java.io.tmpdir") + File.separator
	// + "pytemp");
	// if (!dir.isDirectory()) {
	// dir.mkdirs();
	// }
	//
	// // String path = "/com/bestway/client/custom/common/出口报关单查询.py";
	// InputStream in=
	// FmBaseExportCustomsDeclaration.class.getResourceAsStream("出口报关单查询.py");
	//
	// // 3.写入文件
	// FileOutputStream out = null;
	// File fileTmp = null;
	// try {
	// fileTmp = File.createTempFile("出口报关单查询", ".py", dir);
	// out = new FileOutputStream(fileTmp);
	// byte[] bytes = new byte[2048];
	// int len = -1;
	// while((len = in.read(bytes)) > 0){
	// out.write(bytes,0,len);
	// }
	// out.flush();
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }finally{
	// try {
	// out.close();
	// in.close();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	//
	// }
	// int exitVal = 1;
	// // 4.执行py文件
	// File f=new File("C:\\\\Python27\\python.exe");
	// String cmd="";
	// if(f.exists()){
	// cmd = "C:\\\\Python27\\python.exe  \"" +fileTmp.getAbsolutePath() + "\"";
	// }else{
	// cmd = "python  \"" + fileTmp.getAbsolutePath() + "\"";
	// }
	// String resultStr = "";
	// String resultErrorStr = "";
	// try {
	// Runtime rt = Runtime.getRuntime();
	// Process subprocess = rt.exec(cmd);
	//
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(subprocess.getInputStream()));
	// BufferedReader ebr = new BufferedReader(new
	// InputStreamReader(subprocess.getErrorStream()));
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// resultStr += line;
	// }
	//
	// line = null;
	// while ((line = ebr.readLine()) != null) {
	// resultErrorStr += line;
	// }
	// exitVal = subprocess.waitFor();
	// System.out.println("Process exitValue: " + exitVal);
	// System.out.println("resultErrorStr----->"+resultErrorStr);
	// System.out.println("resultStr------->" + resultStr);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// // File file = new File(
	// // "C:\\Users\\Administrator\\Desktop\\chukoubaoguandan.txt");
	// // FileInputStream fis;
	// // String sTotalString = "";
	// // try {
	// // fis = new FileInputStream(file);
	// // BufferedReader in = new BufferedReader(new InputStreamReader(fis));
	// // String line;
	// // while ((line = in.readLine()) != null) {
	// // sTotalString += line;
	// // }
	// // } catch (FileNotFoundException e) {
	// // e.printStackTrace();
	// // } catch (IOException e) {
	// // e.printStackTrace();
	// // }
	//
	// if (!resultErrorStr.isEmpty()) {
	//
	// baseEncAction.importQp(CommonVars.getRequst(), resultErrorStr);
	// setState();
	// }
	//
	// }

//	private void getExportDeclaration(String strDate) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = sdf.parse(strDate);
//		List declaration = baseEncAction.findCustomsDeclaration(
//				CommonVars.getRequst(), projectType,date,ImpExpFlag.EXPORT);
//	   Set<String> setDeclaration = new HashSet<String>();
//	   setDeclaration.addAll(declaration);
//	   SimpleDateFormat sdf1 = new SimpleDateFormat(
//				"yyyyMMdd");
//	    String strDeclarationDate = sdf1.format(date);
//		String execExportResult = getExecResult("出口单据查询.py", "出口单据查询",
//				"ReplaceDateOrCustomsDeclarationCode", strDeclarationDate);
//		List list = (List)gsonToJavaType(execExportResult, List.class);
//		if(list==null){
//			JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this, "未能从QP中获取到数据,请确认QP已经打开且有数据");
//			return;
//		}
//		for (int i = 0; i < list.size(); i++) {
//			String declarationNo = list.get(i).toString();
//			if (!setDeclaration.contains(declarationNo)) {
//				String execExportDeclaratonResult = getExecResult("出口报关单查询.py", "出口报关单查询",
//						"ReplaceDateOrCustomsDeclarationCode", declarationNo);
//				Map map = (Map) baseEncAction.transferExportDeclaration(
//						CommonVars.getRequst(), execExportDeclaratonResult,ImpExpFlag.EXPORT,ProjectType.BCS);
//			}
//		}
//	}

//	/**
//	 * 导入报关单
//	 */
//	private void importCustoms(){
//		
//		JTraceFileChooser fileChooser = new JTraceFileChooser("importCustomOut");
//		fileChooser.setDialogTitle("请选择文件的存放路径!");
//		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		int state = fileChooser.showDialog(FmBaseExportCustomsDeclaration.this,"确定");
//		File file = null;//选择的路径
//		File importFailureOut = null;//导入失败的路径
//		File importSuccessOut = null;//导入成功的路径
//		
//		if (state == JFileChooser.APPROVE_OPTION) {
//			file = fileChooser.getSelectedFile();
//			
//			importFailureOut = new File(file.getParent()+File.separator+"出口导入失败文件");
//			if(!importFailureOut.exists()){
//				//当导入失败的目录不存在时,生成导入失败的目录
//				importFailureOut.mkdir();
//			}
//			
//			importSuccessOut = new File(file.getParent()+File.separator+"出口导入成功文件");
//			if(!importSuccessOut.exists()){
//				//当导入成功的目录不存在时,生成导入成功的目录
//				importSuccessOut.mkdir();
//			}
//		} else {
//			return;
//		}
//		System.out.println(file.getPath());
//		
//		//查询
//		List declaration = baseEncAction.findAllCustomsDeclaration(CommonVars.getRequst(), projectType, ImpExpFlag.EXPORT);
//		Set<String> setDeclaration = new HashSet<String>();
//		setDeclaration.addAll(declaration);
//		
//		//开始导入
//		ExecuteImport executeImport = new ExecuteImport(file,importFailureOut,importSuccessOut,setDeclaration);
//		executeImport.execute();
//	}
	
//	public class ExecuteImport extends SwingWorker {
//
//		private File file = null;
//		private File importFailureOut = null;
//		private File importSuccessOut = null;
//		private Set setDeclaration = null;
//		
//		/**
//		 * 执行导入
//		 * @param file 文件源路径
//		 * @param importFailureIn 进口失败路径
//		 * @param importSuccessIn 进口成功路径
//		 * @param setDeclaration 已经存在的报关单号
//		 */
//		public ExecuteImport(File file,File importFailureOut,File importSuccessOut,Set setDeclaration){
//			this.file = file;
//			this.importFailureOut = importFailureOut;
//			this.importSuccessOut = importSuccessOut;
//			this.setDeclaration = setDeclaration;
//		}
//		
//		@Override
//		protected Object doInBackground() throws Exception {
//			CommonProgress.showProgressDialog(FmBaseExportCustomsDeclaration.this);
//			CommonProgress.setMessage("系统正在导入数据，请稍后...");
//			btnLoadBGD.setEnabled(false);
//		
//			if(file==null||importFailureOut==null||importSuccessOut==null){
//				System.out.println("路径为空");
//				return null;
//			}
//			
//			File[] files = file.listFiles();
//			for (File f : files) {
//				if(!f.isDirectory()){
//					System.out.println(f.getPath());
//					String stu  = gerFromBASE64(f);
//					System.out.println(stu);
//					Gson gson = new Gson();
//					Map map = null;
//					try {
//						map = (Map) gson.fromJson(stu, Map.class);
//					} catch (Exception e) {
//						CommonProgress.closeProgressDialog();
//						JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,"文件格式无法解析！");
//						return null;
//					}
//					Map<String, String> bgdhead = (Map<String, String>) map.get("报关单表头");
//					String customsDeclarationCode = bgdhead.get("海关编号");
//					if(customsDeclarationCode==null){
//						System.out.println("报关单号为空");
//						copyFile(f, new File(importFailureOut.getPath()+File.separator+f.getName()));
//						
//					}else if(!setDeclaration.contains(customsDeclarationCode)){
//						CommonProgress.setMessage("系统正在导入报关单号为："+customsDeclarationCode+"的报关单,请稍后...");
//						try {
//							baseEncAction.transferExportDeclaration(CommonVars.getRequst(),
//									stu, ImpExpFlag.EXPORT,projectType);
//							System.out.println("导入成功报关单号："+customsDeclarationCode);
//							copyFile(f, new File(importSuccessOut.getPath()+File.separator+f.getName()));
//						} catch (Exception e) {
//							CommonProgress.closeProgressDialog();
//							JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,"导入出错！");
//						}
//					}else{
//						System.out.println("已经存在报关单号："+customsDeclarationCode);
//						copyFile(f, new File(importFailureOut.getPath()+File.separator+f.getName()));
//					}
//					//删除数据文件
//					f.delete();
//				}
//			}
//			return null;
//		}
//		
//		@Override
//		protected void done() {
//			initTable(getDataSource());
//			btnLoadBGD.setEnabled(true);
//			CommonProgress.closeProgressDialog();
//		}
//	}
	
	
	public boolean copyFile(File srcFile, File destFile) {
		boolean result = false;
		try {
			InputStream in = new FileInputStream(srcFile);
			try {
				result = copyToFile(in, destFile);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			result = false;
		}
		return result;
	}

	/***
	 * Copy data from a source stream to destFile. Return true if succeed,
	 * return false if failed.
	 */
	public boolean copyToFile(InputStream inputStream, File destFile) {
		try {
			if (destFile.exists()) {
				destFile.delete();
			}
			OutputStream out = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) >= 0) {
					out.write(buffer, 0, bytesRead);
				}
			} finally {
				out.close();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * 64解码
	 * @param file
	 * @return
	 */
	public String gerFromBASE64(File file){
		
		InputStream in = null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] by = null;
		try {
			in = new FileInputStream(file);
			by = decoder.decodeBuffer(in);
			return new String(by,"GBK").trim();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Object gsonToJavaType(String execExportResult, Class clazz) {
		Gson gson = new Gson();
	    return gson.fromJson(execExportResult, clazz);
     }
	
	public String getExecResult(String path, String fileName, String oldStr,
			String newStr) {
		// 1.创建py文件
		File dir = new File(System.getProperty("java.io.tmpdir")
				+ File.separator + "pytemp");
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		InputStream in = FmBaseExportCustomsDeclaration.class
				.getResourceAsStream(path);

		// 3.写入文件
		FileOutputStream out = null;
		File fileTmp = null;
		OutputStreamWriter buff = null;
		InputStreamReader ir = null;
		try {
			fileTmp = File.createTempFile(fileName, ".py", dir);
			out = new FileOutputStream(fileTmp);
			ir = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(ir);
			buff = new OutputStreamWriter(out, "UTF-8");
			String str = null;
			while ((str = br.readLine()) != null) {
				str = str.replace(oldStr, newStr);
				buff.write(13);
				buff.write(10);
				buff.write(str);
			}
			buff.flush();
			// out = new FileOutputStream(fileTmp);
			//
			// OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
			// byte[] bytes = new byte[1024];
			// int len = 0;
			// while ((len = in.read(bytes)) > 0) {
			// //System.out.print(""+new String(bytes,0,len));
			// String str = new String(new
			// String(bytes,0,len).getBytes(),"UTF-8");
			// System.out.println(str);
			// str = str.replace(oldStr, newStr).trim();
			// //out.write(str.getBytes());
			// writer.write(str);
			// }
			// writer.flush();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				out.close();
				ir.close();
				buff.close();
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		int exitVal = 1;
		// 4.执行py文件
		File f = new File("C:\\\\Python27\\python.exe");
		String cmd = "";
		if (f.exists()) {
			cmd = "C:\\\\Python27\\python.exe  \"" + fileTmp.getAbsolutePath()
					+ "\"";
		} else {
			cmd = "python  \"" + fileTmp.getAbsolutePath() + "\"";
		}
		String resultStr = "";
		String resultErrorStr = "";
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + cmd);
		try {
			Runtime rt = Runtime.getRuntime();
			Process subprocess = rt.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					subprocess.getInputStream()));
			BufferedReader ebr = new BufferedReader(new InputStreamReader(
					subprocess.getErrorStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				resultStr += line;
			}

			line = null;
			while ((line = ebr.readLine()) != null) {
				resultErrorStr += line;
			}
			exitVal = subprocess.waitFor();
			System.out.println("Process exitValue: " + exitVal);
			System.out.println("resultErrorStr----->" + resultErrorStr);
			System.out.println("resultStr------->" + resultStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultStr;
	}

	/**
	 * 导入出口报关单
	 * 
	 * @param importFromFile
	 */
	private void inputExportBGDFromXML() {
		if (JOptionPane.showConfirmDialog(FmBaseExportCustomsDeclaration.this,
				"是否确定导入报关单?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
			return;
		}
		try {
			ImportBGDCondition condition = null;
			DgInputImportBGDDate dg = new DgInputImportBGDDate();
			dg.setBaseEncAction(baseEncAction);
			dg.setImportBGD(false);
			dg.setVisible(true);
			if (dg.isOK()) {
				condition = dg.getCondition();
			} else {
				return;
			}
			Map mapBGD = baseEncAction.loadBGDFromQPXml(
					new Request(CommonVars.getCurrUser()), condition);
			initTable(getDataSource());
			setState();
			List lsSuccess = (List) mapBGD.get(0);
			List lsError = (List) mapBGD.get(-1);
			if ((lsSuccess == null || lsSuccess.size() <= 0)
					&& (lsError == null || lsError.size() <= 0)) {
				JOptionPane.showMessageDialog(
						FmBaseExportCustomsDeclaration.this, "没有导入任何报关单！",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				DgImportedBGDInfo dgImportedBGDInfo = new DgImportedBGDInfo();
				dgImportedBGDInfo.setLsSuccess(lsSuccess);
				dgImportedBGDInfo.setLsError(lsError);
				dgImportedBGDInfo.setVisible(true);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,
					"导入出口报关单失败。" + ex.getMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 导入出口报关单
	 * 
	 * @param importFromFile
	 */
	private void inputExportBGDFromQP() {
		if (JOptionPane.showConfirmDialog(FmBaseExportCustomsDeclaration.this,
				"是否确定导入报关单?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
			return;
		}
		try {
			ImportBGDCondition condition = null;
			DgInputBGDFromQP dg = new DgInputBGDFromQP();
			dg.setBaseEncAction(baseEncAction);
			dg.setProjectType(projectType);
			dg.setImportBGD(false);
			dg.setSpecialDeclaration(false);
			dg.setVisible(true);
			if (dg.isOK()) {
				condition = dg.getCondition();
			} else {
				return;
			}
			Map mapBGD = baseEncAction.loadBGDFromQPDirect(new Request(
					CommonVars.getCurrUser()), condition);
			initTable(getDataSource());
			setState();
			List lsSuccess = (List) mapBGD.get(0);
			List lsError = (List) mapBGD.get(-1);
			if ((lsSuccess == null || lsSuccess.size() <= 0)
					&& (lsError == null || lsError.size() <= 0)) {
				JOptionPane.showMessageDialog(
						FmBaseExportCustomsDeclaration.this, "没有导入任何报关单！",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				DgImportedBGDInfo dgImportedBGDInfo = new DgImportedBGDInfo();
				dgImportedBGDInfo.setLsSuccess(lsSuccess);
				dgImportedBGDInfo.setLsError(lsError);
				dgImportedBGDInfo.setVisible(true);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(FmBaseExportCustomsDeclaration.this,
					"导入出口报关单失败。" + ex.getMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("查看");
			btnBrowse.setPreferredSize(new Dimension(45, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要查看的资料!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					showCustomsDeclarationData(DataState.BROWSE);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSort() {
		if (btnSort == null) {
			btnSort = new JButton();
			btnSort.setText("流水号重排");
			btnSort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.reSortAutoOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.reSortAutoOrder(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction
								.reSortAutoOrder(new Request(CommonVars
										.getCurrUser()));
					}
					if (JOptionPane
							.showConfirmDialog(
									FmBaseExportCustomsDeclaration.this,
									"是否确定要将报关单流水号重新排序?如果选择'是',\n请先终止其他的客户端程序对报关单的操作,\n排序完成后再进行相关操作,以免造成数据不一致!",
									"提示", 0) == 0) {
						new ExectAutoOrder().start();

					}

				}
			});
		}
		btnSort.setVisible(false);// 2010-11-12屏蔽
		return btnSort;
	}

	class ExectAutoOrder extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(FmBaseExportCustomsDeclaration.this);
				CommonProgress.setMessage("系统正在排序数据，请稍后...");
				resortExportCustomsDeclarationSerialNumber();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						FmBaseExportCustomsDeclaration.this,
						"排序数据失败：" + e.getMessage(), "提示", 2);
			} finally {
				initTable(getDataSource());
				setState();
			}
		}
	}

	/**
	 * This method initializes btnTcs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTcs() {
		if (btnTcs == null) {
			btnTcs = new JButton();
		}
		btnTcs.setText("集成通申报");
		btnTcs.setPreferredSize(new Dimension(75, 30));
		btnTcs.setForeground(java.awt.Color.blue);

		btnTcs.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (projectType == ProjectType.BCUS) {
					expCustomsAuthorityAction
							.checkAuthorityDeclarationByTcs(new Request(
									CommonVars.getCurrUser()));
				} else if (projectType == ProjectType.DZSC) {
					dZSCExpCustomsAuthorityAction
							.checkAuthorityDeclarationByTcs(new Request(
									CommonVars.getCurrUser()));
				} else if (projectType == ProjectType.BCS) {
					bCSExpCustomsAuthorityAction
							.checkAuthorityDeclarationByTcs(new Request(
									CommonVars.getCurrUser()));
				}

				declareCustomsDeclarationTcs();
			}
		});
		return btnTcs;
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
			jPanel2.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnImportQP
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportQP() {
		if (btnImportQP == null) {
			btnImportQP = new JButton();
			btnImportQP.setText("导入QP");
			btnImportQP.setForeground(java.awt.Color.blue);
		}
		String path = "c://python27";
		File dirname = new File(path);
		// 目录存在
		if (dirname.isDirectory()) {
			btnImportQP.setVisible(true);
			btnImportQP.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					importQP();
				}
			});
		} else {
			btnImportQP.setVisible(false);
		}

		return btnImportQP;
	}

	private void importQP() {
		try {
			// 1.创建py文件
			File dir = new File("c:\\python27\\tmp");
			if (!dir.isDirectory()) {
				dir.mkdir();
			}

			// 2.生成py文件内容
			BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) tableModel
					.getCurrentRow();
			if (declaration == null) {
				JOptionPane.showMessageDialog(
						FmBaseExportCustomsDeclaration.this, "您必须选择一条报关单！",
						"提示", 2);
			}
			String context = this.buildContext(declaration);

			// 3.写入文件
			File fileTmp = null;
			fileTmp = File.createTempFile("import", ".py", dir);
			FileOutputStream fos = new FileOutputStream(fileTmp);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(context);
			out.close();
			fos.close();

			// 4.执行py文件
			String cmd = "C:\\python27\\python.exe C:\\python27\\tmp\\"
					+ fileTmp.getName();
			Runtime.getRuntime().exec(cmd);
			System.out.println(cmd);

			// 5.清空临时目录
			// FileUtils.deleteDirectory(dir);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据报关单生成py文件内容
	 * 
	 * @param declaration
	 * @return
	 */
	private String buildContext(BaseCustomsDeclaration declaration) {
		List<BaseCustomsDeclarationCommInfo> commInfoList = baseEncAction
				.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()), declaration);

		List<BaseCustomsDeclarationContainer> containers = baseEncAction
				.findContainerByCustomsDeclaration(
						new Request(CommonVars.getCurrUser()), declaration);

		StringBuilder sb = new StringBuilder();
		/*
		 * 输入表头信息
		 */
		sb.append("# -*- coding: utf-8 -*-\n")
				.append("from pywinauto import application\n")
				.append("import pywinauto\n")
				.append("import time\n")
				.append("app = application.Application().connect(title_re=u'中国电子口岸预录入系统.*')\n")
				.append("dlg = app.window_(title_re = u'中国电子口岸预录入系统.*')\n")
				.append("dlg.Menu().Items()[0].SubMenu().Items()[1].Select()\n")
				.append("ctrls = pywinauto.application._resolve_control(dlg.criteria)\n")
				.append("dialog_controls = ctrls[-1].Children()\n")
				.append("name_control_map = pywinauto.findbestmatch.build_unique_dict(dialog_controls)\n")
				.append("ctrls={}\n")
				.append("for ctrlname,c in name_control_map.items():\n")
				.append("	if c.friendlyclassname=='Edit':\n")
				.append("		ctrls[ctrlname]=c\n")
				.append("\n")
				.append("if ctrls['Edit82'].IsEnabled(): ")
				.append("ctrls['Edit82'].TypeKeys(u'"
						+ declaration.getDeclarationCustoms().getCode()
						+ "{ENTER}')\n")
				// Edit82 申报地海关
				.append("if ctrls['Edit2'].IsEnabled(): ")
				.append("ctrls['Edit2'].TypeKeys(u'"
						+ declaration.getMachCode() + "{ENTER}')\n")
				// Edit2 经营单位1 --代码
				.append("if ctrls['Edit43'].IsEnabled(): ")
				.append("ctrls['Edit43'].TypeKeys(u'"
						+ declaration.getPreCustomsDeclarationCode()
						+ "{ENTER}')\n")
				// #Edit43 预录入编号
				.append("if ctrls['Edit41'].IsEnabled(): ")
				.append("ctrls['Edit41'].TypeKeys(u'"
						+ getCode(declaration.getCustoms()) + "{ENTER}')\n")
				// #Edit41 出口口岸
				.append("if ctrls['Edit40'].IsEnabled(): ")
				.append("ctrls['Edit40'].TypeKeys(u'"
						+ declaration.getEmsHeadH2k() + "{ENTER}')\n")
				// #Edit40 备案号
				.append("if ctrls['Edit39'].IsEnabled(): ")
				.append("ctrls['Edit39'].TypeKeys(u'"
						+ declaration.getContract() + "{ENTER}')\n")
				// #Edit39 合同协议号
				.append("if ctrls['Edit38'].IsEnabled(): ")
				.append("ctrls['Edit38'].TypeKeys(u'"
						+ CommonUtils.getDate(declaration.getImpExpDate(),
								"yyyyMMdd") + "{ENTER}')\n")
				// #Edit38 出口日期
				// .append("if ctrls['Edit36'].IsEnabled(): ")
				// .append("ctrls['Edit36'].TypeKeys(u'" +
				// declaration.getTradeCode() + "{ENTER}')\n")//#Edit36 经营单位2
				// --代码
				// .append("if ctrls['Edit35'].IsEnabled(): ")
				// .append("ctrls['Edit35'].TypeKeys(u'" +
				// declaration.getTradeName() + "{ENTER}')\n")//#Edit35 经营单位3
				// --名称
				.append("if ctrls['Edit34'].IsEnabled(): ")
				.append("ctrls['Edit34'].TypeKeys(u'"
						+ getCode(declaration.getTransferMode())
						+ "{ENTER}')\n")
				// #Edit34 运输方式
				.append("if ctrls['Edit33'].IsEnabled(): ")
				.append("ctrls['Edit33'].TypeKeys(u'"
						+ declaration.getMachCode() + "{ENTER}')\n")
				// #Edit33 发货单位1--代码
				// .append("if ctrls['Edit32'].IsEnabled(): ")
				// .append("ctrls['Edit32'].TypeKeys(u'" +
				// declaration.getMachName() + "{ENTER}')\n")//#Edit32 发货单位2--名称
				.append("if ctrls['Edit31'].IsEnabled(): ")
				.append("ctrls['Edit31'].TypeKeys(u'"
						+ declaration.getConveyance() + "{ENTER}')\n")
				// #Edit31 运输工具名称
				.append("if ctrls['Edit30'].IsEnabled(): ")
				.append("ctrls['Edit30'].TypeKeys(u'"
						+ declaration.getDeclarationCompany().getCode()
						+ "{ENTER}')\n")
				// #Edit30 申报单位1
				// .append("if ctrls['Edit29'].IsEnabled(): ")
				// .append("ctrls['Edit29'].TypeKeys(u'" +
				// declaration.getDeclarationCompany().getName() +
				// "{ENTER}')\n")//#Edit29 申报单位2
				// .append("if ctrls['Edit28'].IsEnabled(): ")
				// .append("ctrls['Edit28'].TypeKeys(u'" + +
				// "{ENTER}')\n")//#Edit28 航次号
				.append("if ctrls['Edit27'].IsEnabled(): ")
				.append("ctrls['Edit27'].TypeKeys(u'"
						+ declaration.getBillOfLading() + "{ENTER}')\n")
				// #Edit27 提运单号
				.append("if ctrls['Edit26'].IsEnabled(): ")
				.append("ctrls['Edit26'].TypeKeys(u'"
						+ getCode(declaration.getTradeMode()) + "{ENTER}')\n")
				// #Edit26 监管方式
				.append("if ctrls['Edit25'].IsEnabled(): ")
				.append("ctrls['Edit25'].TypeKeys(u'"
						+ getCode(declaration.getLevyKind()) + "{ENTER}')\n")
				// #Edit25 征免性质
				.append("if ctrls['Edit45'].IsEnabled(): ")
				.append("ctrls['Edit45'].TypeKeys(u'" + declaration.getPerTax()
						+ "{ENTER}')\n")
				// #Edit45 结汇方式
				// .append("if ctrls['Edit4'].IsEnabled(): ")
				// .append("ctrls['Edit4'].TypeKeys(u'" + +
				// "{ENTER}')\n")//#Edit4 纳税单位
				.append("if ctrls['Edit46'].IsEnabled(): ")
				.append("ctrls['Edit46'].TypeKeys(u'"
						+ declaration.getLicense() + "{ENTER}')\n")
				// #Edit46 许可证编号
				.append("if ctrls['Edit24'].IsEnabled(): ")
				.append("ctrls['Edit24'].TypeKeys(u'"
						+ Integer.parseInt(getCode(declaration
								.getCountryOfLoadingOrUnloading()))
						+ "{ENTER}')\n")
				// #Edit24 运抵国
				.append("time.sleep(1)\n")
				// 暂停0.5秒
				.append("if ctrls['Edit22'].IsEnabled(): ")
				.append("ctrls['Edit22'].TypeKeys(u'"
						+ Integer.parseInt(getCode(declaration
								.getPortOfLoadingOrUnloading()))
						+ "{ENTER}')\n")
				// #Edit22 指运港
				.append("time.sleep(0.3)\n")
				// 暂停0.5秒
				.append("if ctrls['Edit23'].IsEnabled(): ")
				.append("ctrls['Edit23'].TypeKeys(u'"
						+ getCode(declaration.getDomesticDestinationOrSource())
						+ "{ENTER}')\n")
				// #Edit23 境内货源地
				.append("if ctrls['Edit21'].IsEnabled(): ")
				.append("ctrls['Edit21'].TypeKeys(u'"
						+ declaration.getAuthorizeFile() + "{ENTER}')\n")
				// #Edit21 批准文号
				.append("if ctrls['Edit20'].IsEnabled(): ")
				.append("ctrls['Edit20'].TypeKeys(u'"
						+ getCode(declaration.getTransac()) + "{ENTER}')\n")
				// #Edit20 成交方式
				.append("time.sleep(0.3)\n")
				// 暂停0.5秒
				.append("if ctrls['Edit17'].IsEnabled(): ")
				.append("ctrls['Edit17'].TypeKeys(u'"
						+ declaration.getFreightageType() + "{ENTER}')\n")
				// #Edit17 运费1-运费类型
				.append("if ctrls['Edit16'].IsEnabled(): ")
				.append("ctrls['Edit16'].TypeKeys(u'"
						+ declaration.getFreightage() + "{ENTER}')\n")
				// #Edit16 运费2-运费金额
				.append("if ctrls['Edit15'].IsEnabled(): ")
				.append("ctrls['Edit15'].TypeKeys(u'"
						+ getCode(declaration.getFeeCurr()) + "{ENTER}')\n")
				// #Edit15 运费3-运费币制
				.append("if ctrls['Edit14'].IsEnabled(): ")
				.append("ctrls['Edit14'].TypeKeys(u'"
						+ declaration.getInsuranceType() + "{ENTER}')\n")
				// #Edit14 保费1-类型
				.append("if ctrls['Edit13'].IsEnabled(): ")
				.append("ctrls['Edit13'].TypeKeys(u'"
						+ declaration.getInsurance() + "{ENTER}')\n")
				// #Edit13 保费2-金额
				.append("if ctrls['Edit12'].IsEnabled(): ")
				.append("ctrls['Edit12'].TypeKeys(u'"
						+ getCode(declaration.getInsurCurr()) + "{ENTER}')\n")
				// #Edit12 保费3-币制
				.append("if ctrls['Edit11'].IsEnabled(): ")
				.append("ctrls['Edit11'].TypeKeys(u'"
						+ declaration.getIncidentalExpensesType()
						+ "{ENTER}')\n")
				// #Edit11 杂费1-类型
				.append("if ctrls['Edit10'].IsEnabled(): ")
				.append("ctrls['Edit10'].TypeKeys(u'"
						+ declaration.getIncidentalExpenses() + "{ENTER}')\n")
				// #Edit10 杂费2-金额
				.append("if ctrls['Edit9'].IsEnabled(): ")
				.append("ctrls['Edit9'].TypeKeys(u'"
						+ getCode(declaration.getOtherCurr()) + "{ENTER}')\n")
				// #Edit9 杂费3-币制
				.append("if ctrls['Edit19'].IsEnabled(): ")
				.append("ctrls['Edit19'].TypeKeys(u'"
						+ declaration.getCommodityNum() + "{ENTER}')\n")
				// #Edit19 件数
				.append("if ctrls['Edit18'].IsEnabled(): ")
				.append("ctrls['Edit18'].TypeKeys(u'"
						+ getCode(declaration.getWrapType()) + "{ENTER}')\n")
				// #Edit18 包装种类
				.append("if ctrls['Edit8'].IsEnabled(): ")
				.append("ctrls['Edit8'].TypeKeys(u'"
						+ declaration.getGrossWeight() + "{ENTER}')\n")
				// #Edit8 毛重
				.append("if ctrls['Edit7'].IsEnabled(): ")
				.append("ctrls['Edit7'].TypeKeys(u'"
						+ declaration.getNetWeight() + "{ENTER}')\n")
				// #Edit7 净重
				.append("if ctrls['Edit1'].IsEnabled(): ")
				.append("ctrls['Edit1'].TypeKeys(u'"
						+ declaration.getTcsEntryType() + "{ENTER}')\n")
				// #Edit1 报关单类型
				.append("if ctrls['Edit3'].IsEnabled(): ")
				.append("ctrls['Edit3'].TypeKeys(u'" + declaration.getMemos()
						+ "{ENTER}')\n")// #Edit3 备注
		;

		/*
		 * 输入表体信息
		 */
		if (commInfoList != null && commInfoList.size() > 0) {
			sb.append("#---------商品信息----------#\n\n");
			BaseCustomsDeclarationCommInfo info = null;
			String fullCode = null;
			String code = null;
			String lastCode = null;
			for (int i = 0; i < commInfoList.size(); i++) {
				info = commInfoList.get(i);

				fullCode = info.getComplex().getCode();
				if (fullCode != null && fullCode.length() == 10) {
					code = fullCode.substring(0, 8);
					lastCode = fullCode.substring(8);
				} else {
					code = fullCode;
					lastCode = "00";
				}

				// #Edit79 商品序号
				sb.append("if ctrls['Edit78'].IsEnabled(): ")
						.append("ctrls['Edit78'].TypeKeys(u'"
								+ info.getCommSerialNo() + "{ENTER}')\n")
						// #Edit78 备案序号
						// .append("if ctrls['Edit77'].IsEnabled(): ")
						// .append("ctrls['Edit77'].TypeKeys(u'" +
						// info.getMemos() + "{ENTER}')\n")//#Edit77 附加编号
						.append("if ctrls['Edit59'].IsEnabled(): ")
						.append("ctrls['Edit59'].TypeKeys(u'"
								+ info.getCommName() + "{ENTER}')\n")
						// #Edit59 商品名称
						.append("if ctrls['Edit58'].IsEnabled(): ")
						.append("ctrls['Edit58'].TypeKeys(u'" + code
								+ "{ENTER}')\n")
						// #Edit58 商品编号
						.append("sp=app.window_(title_re = u'商品列表')\n")
						.append("if sp.Exists():\n")
						.append("    findCode=False\n")
						.append("    for i in range(sp.ListView.ItemCount()):\n")
						.append("        if sp.ListView.GetItem(i,1)['text']==u'"
								+ lastCode + "':\n")
						.append("            findCode=True\n")
						.append("            sp.ListView.Select(i)\n")
						.append("            sp.Button2.Click()\n")
						.append("            break\n")
						.append("    if findCode==False:\n")
						.append("        while sp.Exists(): time.sleep(1)\n")
						.append("time.sleep(0.5)\n")
						.append("gf=app.window_(title_re = u'商品规范申报.*')\n")
						.append("if gf.Exists(): gf.Button1.Click()\n\n")
						.append("if ctrls['Edit76'].IsEnabled(): ")
						.append("ctrls['Edit76'].TypeKeys(u'"
								+ info.getCommSpec() + "{ENTER}')\n")
						// #Edit76 规格型号
						.append("if ctrls['Edit75'].IsEnabled(): ")
						.append("ctrls['Edit75'].TypeKeys(u'"
								+ info.getCommAmount() + "{ENTER}')\n")
						// #Edit75 成交数量
						.append("if ctrls['Edit74'].IsEnabled(): ")
						.append("ctrls['Edit74'].TypeKeys(u'"
								+ getCode(info.getUnit()) + "{ENTER}')\n")
						// #Edit74 成交单位
						.append("if ctrls['Edit73'].IsEnabled(): ")
						.append("ctrls['Edit73'].TypeKeys(u'"
								+ info.getCommUnitPrice() + "{ENTER}')\n")
						// #Edit73 成交单价
						.append("if ctrls['Edit72'].IsEnabled(): ")
						.append("ctrls['Edit72'].TypeKeys(u'"
								+ info.getCommTotalPrice() + "{ENTER}')\n")
						// #Edit72 成交总价
						.append("if ctrls['Edit71'].IsEnabled(): ")
						.append("ctrls['Edit71'].TypeKeys(u'"
								+ getCode(declaration.getCurrency())
								+ "{ENTER}')\n")
						// #Edit71 币制
						.append("if ctrls['Edit70'].IsEnabled(): ")
						.append("ctrls['Edit70'].TypeKeys(u'"
								+ info.getFirstAmount() + "{ENTER}')\n")
						// #Edit70 法定数量
						.append("if ctrls['Edit69'].IsEnabled(): ")
						.append("ctrls['Edit69'].TypeKeys(u'"
								+ getCode(info.getLegalUnit()) + "{ENTER}')\n")
						// #Edit69 法定单位
						.append("if ctrls['Edit68'].IsEnabled(): ")
						.append("ctrls['Edit68'].TypeKeys(u'"
								+ info.getVersion() + "{ENTER}')\n")
						// #Edit68 版本号
						.append("if ctrls['Edit67'].IsEnabled(): ")
						.append("ctrls['Edit67'].TypeKeys(u'"
								+ info.getMaterielNo() + "{ENTER}')\n")
						// #Edit67 货号
						// .append("if ctrls['Edit66'].IsEnabled(): ")
						// .append("ctrls['Edit66'].TypeKeys(u'" +
						// info.getMaterielNo() + "{ENTER}')\n")//#Edit66 生成厂家
						.append("if ctrls['Edit65'].IsEnabled(): ")
						.append("ctrls['Edit65'].TypeKeys(u'"
								+ info.getSecondAmount() + "{ENTER}')\n")
						// #Edit65 第二数量
						.append("if ctrls['Edit64'].IsEnabled(): ")
						.append("ctrls['Edit64'].TypeKeys(u'"
								+ getCode(info.getSecondLegalUnit())
								+ "{ENTER}')\n")
						// #Edit64 第二单位
						.append("if ctrls['Edit62'].IsEnabled(): ")
						.append("ctrls['Edit62'].TypeKeys(u'"
								+ getCode(info.getCountry()) + "{ENTER}')\n")
						// #Edit62 目的地
						.append("time.sleep(0.5)\n")
						// 暂停0.5秒
						.append("if ctrls['Edit60'].IsEnabled(): ")
						.append("ctrls['Edit60'].TypeKeys(u'"
								+ getCode(info.getLevyMode()) + "{ENTER}')\n")
						// #Edit60 征免
						.append("time.sleep(0.5)\n")
						// 暂停0.5秒
						.append("if ctrls['Edit63'].IsEnabled(): ")
						.append("ctrls['Edit63'].TypeKeys(u'"
								+ info.getWorkUsd() + "{ENTER}')\n\n")// #Edit63
																		// 工缴费
				// .append("if ctrls['Edit61'].IsEnabled(): ")
				// .append("ctrls['Edit61'].TypeKeys(u'{ENTER}')\n")//#Edit61 未知
				;
			}
		}

		/*
		 * 输入集装箱信息
		 */
		if (containers != null && containers.size() > 0) {
			BaseCustomsDeclarationContainer container = null;
			for (int i = 0; i < containers.size(); i++) {
				container = containers.get(i);
				// 集装箱规格 customsdeclarationcontainer 20#柜填S、40及45#柜填L(关联表srtjzx）
				// a1
				String style, weight = null;
				if (container.getSrtJzx() != null) {
					if (Integer.parseInt(container.getSrtJzx().getName()
							.substring(0, 2)) >= 40) {
						style = "L";
					} else {
						style = "S";
					}
					if (container.getSrtJzx().getSrtWeight() != null) {
						weight = container.getSrtJzx().getSrtWeight()
								.toString();
					} else {
						weight = "";
					}
				} else {
					style = "";
					weight = "";
				}
				sb.append("if ctrls['Edit57'].IsEnabled(): ")
						.append("ctrls['Edit57'].TypeKeys(u'"
								+ container.getContainerCode() + "{ENTER}')\n")
						// #Edit57 集装箱号
						.append("if ctrls['Edit56'].IsEnabled(): ")
						.append("ctrls['Edit56'].TypeKeys(u'" + style
								+ "{ENTER}')\n")
						// #Edit56 集装箱规格
						.append("if ctrls['Edit55'].IsEnabled(): ")
						.append("ctrls['Edit55'].TypeKeys(u'" + weight
								+ "{ENTER}')\n");// #Edit55 集装箱自重
			}
		}
		/*
		 * 输入随附单证
		 */
		// 单证代码 DocumentAttachedCode 是 customsdeclaration an1 海关标准，随附单证代码表
		// 单证编号 DocumentAttachedNo customsdeclaration certificateCode an..32
		// 都取certificateCode，'：'或'*'前是CODE,后面是NO,按分号区分多个
		if (declaration.getCertificateCode() != null) {
			String split = declaration.getCertificateCode().contains(",") ? ","
					: ";";

			String[] cards = declaration.getCertificateCode() != null ? declaration
					.getCertificateCode().split(split) : new String[0];
			for (String str : cards) {
				String cardName = null;
				String cardNo = null;
				String sp = "";
				if (str != null) {
					if (str.contains(":")) {
						sp = ":";
					} else if (str.contains("*")) {
						sp = "*";
					} else {
						continue;
					}
				} else {
					continue;
				}
				int p = str.indexOf(sp);
				cardName = str.substring(0, p);
				cardNo = str.substring(p + 1);
				sb.append("if ctrls['Edit54'].IsEnabled(): ")
						.append("ctrls['Edit54'].TypeKeys(u'" + cardNo
								+ "{ENTER}')\n")
						// #Edit54 随附单证代码
						.append("if ctrls['Edit53'].IsEnabled(): ")
						.append("ctrls['Edit53'].TypeKeys(u'" + cardName
								+ "{ENTER}')\n");// #Edit53 随附单证编号
			}
		}

		return sb.toString()
				.replace("TypeKeys(u'null{ENTER}')", "TypeKeys(u'{ENTER}')")
				.replace("TypeKeys(u'0.0{ENTER}')", "TypeKeys(u'{ENTER}')");
	}

	static String getCode(CustomBaseEntity entity) {
		if (entity == null) {
			return "";
		} else {
			return entity.getCode();
		}
	}

	static String getName(CustomBaseEntity entity) {
		if (entity == null) {
			return "";
		} else {
			return entity.getName();
		}
	}

	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton("批量打印");
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					BaseEmsHead emsHead = (BaseEmsHead) cbbEmshead.getSelectedItem();
					DgBatchPrint dg = new DgBatchPrint(ImpExpFlag.EXPORT, projectType,emsHead.getEmsNo());
					dg.setVisible(true);
				}
			});
		}
		return btnPrint;
	}
} // @jve:decl-index=0:visual-constraint="8,6"
