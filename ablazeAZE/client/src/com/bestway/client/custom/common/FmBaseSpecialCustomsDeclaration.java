package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.message.DgProcessCustomsMessageTCS;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.action.BCSSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.SpecialCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 特殊报关单的父类窗体(贺巍添加部分注释) 2008年10月28日
 */
public abstract class FmBaseSpecialCustomsDeclaration extends
		JInternalFrameBase {
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
	protected JButton jButton = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private SpecialCustomsAuthorityAction specialCustomsAuthorityAction = null;
	private DZSCSpecialCustomsAuthorityAction dZSCSpecialCustomsAuthorityAction = null;
	private BCSSpecialCustomsAuthorityAction bCSSpecialCustomsAuthorityAction = null;
	private JButton jButton3 = null;
	private JButton btnQuery = null;
	private JButton btnTcs = null;
	private JButton btnBrowse = null;
	private JButton btnShowMoney = null;
	private JPanel jPanel2 = null;
	private JButton btnLoadBGD = null;
	private JPopupMenu pmInputBGD = null;
	private JMenuItem miInputDirect = null;

	private JPopupMenu pmCustomsBroker = null; // @jve:decl-index=0:visual-constraint="901,100"

	private JMenuItem miCustomsBrokerDeclaretion = null; // @jve:decl-index=0:visual-constraint="899,141"

	private JMenuItem miCustomsBrokerProcess = null; // @jve:decl-index=0:visual-constraint="899,172"

	private JButton btnPrint;

	// private JButton btnReSet = null;

	/**
	 * 构造方法，初始化部分控件
	 * 
	 */
	public FmBaseSpecialCustomsDeclaration() {
		super();
		specialCustomsAuthorityAction = (SpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"specialCustomsAuthorityAction");
		dZSCSpecialCustomsAuthorityAction = (DZSCSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCSpecialCustomsAuthorityAction");
		bCSSpecialCustomsAuthorityAction = (BCSSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"bCSSpecialCustomsAuthorityAction");
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
				specialCustomsAuthorityAction.brownCustoms(new Request(
						CommonVars.getCurrUser()));
			} else if (projectType == ProjectType.DZSC) {
				dZSCSpecialCustomsAuthorityAction.brownCustoms(new Request(
						CommonVars.getCurrUser()));
			} else if (projectType == ProjectType.BCS) {
				bCSSpecialCustomsAuthorityAction.brownCustoms(new Request(
						CommonVars.getCurrUser()));
			}
		}
		super.setVisible(b);
	}

	/**
	 * 初始化方法初始化部分控件
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setTitle("特殊报关单");
		this.setContentPane(getJContentPane());
		this.setSize(817, 323);
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(CommonVars.getEndDate(new Date()));
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initUIComponents();
				initTable(null);
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
			jToolBar.add(getBtnCustomsBrokerDeclare());
			// lyh 2013.1.30需求不明确，暂时此功能不用。
			// jToolBar.add(getBtnLoadBGD());

			jToolBar.add(getJButton3());
			jToolBar.add(getBtnClose());
			jToolBar.add(getBtnPrint());
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
			btnCopy.setPreferredSize(new Dimension(40, 30));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction.copyCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.copyCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.copyCustoms(new Request(CommonVars
										.getCurrUser()));
					}
					copyData();
				}
			});
		}
		return btnCopy;
	}

	private JButton getBtnShowMoney() {
		if (btnShowMoney == null) {
			btnShowMoney = new JButton();
			btnShowMoney.setText("显示金额");
			btnShowMoney.setPreferredSize(new Dimension(60, 30));
			btnShowMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Map<String, Double> map = baseEncAction
							.findCustomsDeclarationMoney(
									new Request(CommonVars.getCurrUser()),
									ImpExpFlag.SPECIAL);
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration base = (BaseCustomsDeclaration) (list
								.get(i));
						base.setTotalMoney(map.get(base.getId()));
					}
					tableModel.fireTableDataChanged();
				}
			});
		}
		return btnShowMoney;
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
						// EmsHeadH2k ems = getEmsHeadH2k();
						// DgSpecialCustomsDeclaration dg = new
						// DgSpecialCustomsDeclaration();
						// dg.setCustomsDeclarationModel(tableModel);
						// dg.setDataState(DataState.BROWSE);
						// dg.setEmsHeadH2k(ems);
						// dg.setVisible(true);
						showCustomsDeclarationData(DataState.BROWSE);
					}
				}
			});
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (!CommonVars.isManager()) {
						return;
					}
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) tableModel
								.getCurrentRow();
						if (customsDeclaration.getEffective() != null
								&& customsDeclaration.getEffective().equals(
										new Boolean(true))) {
							customsDeclaration.setEffective(new Boolean(false));
						} else {
							customsDeclaration.setEffective(new Boolean(true));
						}
						customsDeclaration = baseEncAction
								.saveCustomsDeclaration(
										new Request(CommonVars.getCurrUser()),
										customsDeclaration);
						tableModel.updateRow(customsDeclaration);
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
			btnAdd.setPreferredSize(new Dimension(40, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EmsHeadH2k ems = getEmsHeadH2k();
					// if (ems == null) {
					// JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!",
					// "提示", 1);
					// return;
					// }
					// if(ems.getEmsNo()==null||ems.getEmsNo().trim().equals("")){
					// JOptionPane.showMessageDialog(
					// FmBaseSpecialCustomsDeclaration.this,
					// "电子帐册的编号不能为空", "提示", 0);
					// return;
					// }
					// DgSpecialCustomsDeclaration dg = new
					// DgSpecialCustomsDeclaration();
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
			btnCancel.setPreferredSize(new Dimension(40, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelCustomsDeclarationData();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 作废报关单资料
	 * 
	 * @param dataState
	 */
	protected void cancelCustomsDeclarationData() {
		if (tableModel.getCurrentRows() == null) {
			JOptionPane.showMessageDialog(FmBaseSpecialCustomsDeclaration.this,
					"请选择要作废的数据", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(FmBaseSpecialCustomsDeclaration.this,
				"是否确定作废数据!!!", "提示", 0) != 0) {
			return;
		}
		if (projectType == ProjectType.BCUS) {
			specialCustomsAuthorityAction.genericDelete(new Request(CommonVars
					.getCurrUser()));
		} else if (projectType == ProjectType.DZSC) {
			dZSCSpecialCustomsAuthorityAction.genericDelete(new Request(
					CommonVars.getCurrUser()));
		} else if (projectType == ProjectType.BCS) {
			bCSSpecialCustomsAuthorityAction.genericDelete(new Request(
					CommonVars.getCurrUser()));
		}
		List list = tableModel.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
					.get(i);
			if (customsDeclaration.getEffective() != null
					&& customsDeclaration.getEffective()) {
				JOptionPane.showMessageDialog(
						FmBaseSpecialCustomsDeclaration.this,
						"所选报关单其中有生效的，请回卷后再执行！", "提示！",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				baseEncAction.deleteCustomsDeclaration(
						new Request(CommonVars.getCurrUser()), list);
				tableModel.deleteRows(list);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		// BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration)
		// tableModel
		// .getCurrentRows();
		// baseEncAction.deleteCustomsDeclaration(new Request(CommonVars
		// .getCurrUser()), customsDeclaration);
		// tableModel.deleteRows(customsDeclaration);
		setState();
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
			btnEdit.setPreferredSize(new Dimension(40, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(40, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBaseSpecialCustomsDeclaration.this.doDefaultCloseAction();
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
		// BaseEmsHead emsHead=(BaseEmsHead)this.cbbEmshead.getSelectedItem();
		// if(emsHead==null){
		// return new ArrayList();
		// }
		// String emsNo=emsHead.getEmsNo();
		return baseEncAction.findSpecialCustomsDeclaration(new Request(
				CommonVars.getCurrUser()), jCalendarComboBox.getDate(),
				jCalendarComboBox1.getDate());
	}

	/**
	 * 是进口还是出口
	 */
	protected boolean isImport(int billType) {
		boolean isImport = false;
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.EQUIPMENT_IMPORT:
			isImport = true;
			break;
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
		case ImpExpType.GENERAL_TRADE_EXPORT:
		case ImpExpType.EQUIPMENT_BACK_PORT:
		case ImpExpType.BACK_PORT_REPAIR:
			isImport = false;
			break;
		}
		return isImport;
	}

	/**
	 * 初始化表体
	 */
	protected void initTable(List date) {
		List list = new ArrayList();
		if (date == null) {
			list = this.getDataSource();
		} else {
			list = date;
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						String emsSpec = "";
						switch (projectType) {
						case ProjectType.BCS:
							emsSpec = "手册编号";
							break;
						case ProjectType.BCUS:
							emsSpec = "帐册编号";
							break;
						case ProjectType.DZSC:
							emsSpec = "手册编号";
							break;
						}
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "serialNumber", 40,
								Integer.class));// 1
						list.add(addColumn("报关单号", "customsDeclarationCode",
								140));// 2
						list.add(addColumn("申报日期", "declarationDate", 80));// 3
						list.add(addColumn("是否检查", "isCheck", 60));// 4
						list.add(addColumn("是否生效", "effective", 60));// 5
						// list.add(addColumn("转关确认", "confirmTransferCIQ",
						// 80));
						list.add(addColumn("是否申报", "isSend", 60));// 6
						list.add(addColumn("是否已核销", "isEmsCav", 60)); // 7
						list.add(addColumn("金额", "totalMoney", 80));
						list.add(addColumn("回执信息", "tcsResultMessage", 100));// 8

						list.add(addColumn("备注", "memos", 100)); // 9
						list.add(addColumn("进出口类型", "impExpType", 90));// 10
						list.add(addColumn("件数", "commodityNum", 80)); // 11
						list.add(addColumn("毛重", "grossWeight", 80));// 12
						list.add(addColumn("净重", "netWeight", 80));// 13
						list.add(addColumn("币制", "currency.currSymb", 50)); // 14
						list.add(addColumn("汇率", "exchangeRate", 50));// 15
						list.add(addColumn("进出口岸", "customs.name", 100));
						list.add(addColumn("起运国",
								"countryOfLoadingOrUnloading.name", 100));
						list.add(addColumn("运输方式", "transferMode.name", 100));
						list.add(addColumn("运输工具", "conveyance", 100));
						list.add(addColumn("提运单号", "billOfLading", 100));
						list.add(addColumn("收货单位", "acceptGoodsCompany.name",
								100));
						list.add(addColumn("贸易方式", "tradeMode.name", 100));
						list.add(addColumn("装货港",
								"portOfLoadingOrUnloading.name", 100));
						list.add(addColumn("境内目的地",
								"domesticDestinationOrSource.name", 100));
						list.add(addColumn("集装箱号", "containerNum", 100));
						list.add(addColumn("经营单位", "tradeName", 100));
						list.add(addColumn("申报单位", "declarationCompany.name",
								100));
						list.add(addColumn("清单号码", "billListId", 100));
						list.add(addColumn("预录入号", "preCustomsDeclarationCode",
								100));

						list.add(addColumn("统一编号", "unificationCode", 100));
						list.add(addColumn("批准文号", "authorizeFile", 80));

						// 发票号
						list.add(addColumn("发票号", "invoiceCode", 100));

						list.add(addColumn("生效时间", "effectiveDate", 80));
						list.add(addColumn("生效人", "effectiveMan", 80));
						list.add(addColumn("录入时间", "createDate", 80));
						list.add(addColumn("录入人员", "creater.userName", 80));
						list.add(addColumn("修改时间", "modifyDate", 80));
						list.add(addColumn("修改人员", "modifyPeople", 80));
						list.add(addColumn(emsSpec, "emsHeadH2k", 100));// "帐册编号"
						list.add(addColumn("车次编号", "extendField1", 100));// 车次号
						list.add(addColumn("进出口号", "extendField2", 100));// 进出口编号
						return list;
					}
				});
		jTable.getColumnModel().removeColumn(
				jTable.getColumnModel().getColumn(0));
		if (projectType == ProjectType.BCS || projectType == ProjectType.DZSC) {
			jTable.getColumnModel().removeColumn(
					jTable.getColumnModel().getColumn(6));
		}
		jTable.getColumnModel().getColumn(3)
				.setCellRenderer(new checkBoxRenderer());
		jTable.getColumnModel().getColumn(4)
				.setCellRenderer(new checkBoxRenderer());
		jTable.getColumnModel().getColumn(5)
				.setCellRenderer(new checkBoxRenderer());
		if (projectType == ProjectType.BCUS) {
			jTable.getColumnModel().getColumn(6)
					.setCellRenderer(new checkBoxRenderer());
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
		} else {
			jTable.getColumnModel().getColumn(9)
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
	}

	/**
	 * checkBox的渲染器
	 * 
	 * @author sanvi
	 * 
	 */
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

	protected boolean getBoolean(Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	/**
	 * 设置组件的状态
	 */
	protected void setState() {
		if (tableModel.getCurrentRow() == null) {
			return;
		}
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		boolean isEffective = getBoolean(customsDeclaration.getEffective());
		boolean isCancel = getBoolean(customsDeclaration.getCancel());
		this.btnEdit.setEnabled((!isEffective) && (!isCancel));
		// this.btnEffect.setEnabled((!isEffective) && (!isCancel));
		// this.btnUnreel.setEnabled((isEffective) && (!isCancel));
		this.btnCancel.setEnabled((!isEffective) && (!isCancel));
		// this.btnDeclare.setEnabled((isEffective) && (!isCancel));
	}

	/**
	 * 转抄报关单
	 * 
	 */
	protected void copyData() {
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
			jToolBar1.add(getJPanel1());

		}
		return jToolBar1;
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setHgap(1);
			f.setVgap(2);
			jPanel1.setLayout(f);
			jPanel1.setPreferredSize(new Dimension(848, 28));
			jLabel.setBounds(3, 5, 72, 22);
			jLabel.setText("申报日期 从");
			jLabel1.setBounds(160, 4, 21, 20);
			jLabel1.setText("到");
			jPanel1.add(jLabel, null);
			jPanel1.add(getJCalendarComboBox(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJCalendarComboBox1(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getBtnQuery());
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setPreferredSize(new Dimension(120, 24));
			jCalendarComboBox
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTable(null);
							setState();
						}
					});
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setPreferredSize(new Dimension(120, 24));
			jCalendarComboBox1
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTable(null);
							setState();
						}
					});
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setPreferredSize(new Dimension(75, 24));
			jButton1.setText("刷新");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable(null);
					setState();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("刷新");
			jButton2.setPreferredSize(new Dimension(40, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable(null);
					setState();
				}
			});
		}
		return jButton2;
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("强制");
			jButton3.setPreferredSize(new Dimension(40, 30));
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.getSpecialControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.getSpecialControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.getSpecialControlCustomsDeclaretion(new Request(
										CommonVars.getCurrUser()));
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseSpecialCustomsDeclaration.this,
								"请选择要强制操作的数据！", "提示", 0);
						return;
					}
					// BaseCustomsDeclaration customsDeclaration =
					// (BaseCustomsDeclaration) tableModel
					// .getCurrentRow();

					Boolean isEffective = null;
					if (JOptionPane.showConfirmDialog(
							FmBaseSpecialCustomsDeclaration.this,
							"强制生效请按【是】，强制检查通过请按【否】！", "提示", 0) == 0) {
						if (JOptionPane.showConfirmDialog(
								FmBaseSpecialCustomsDeclaration.this,
								"是否确定强制生效数据！", "提示", 0) != 0) {
							return;
						}
						isEffective = true;
					} else {
						if (JOptionPane.showConfirmDialog(
								FmBaseSpecialCustomsDeclaration.this,
								"是否确定海关强制检查通过！", "提示", 0) != 0) {
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmBaseSpecialCustomsDeclaration.this,
								"此按扭为强制检查通过功能，非专业人员请慎用！", "提示", 0) != 0) {
							return;
						}
						isEffective = false;
					}

					List list = tableModel.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) list
								.get(i);
						if (isEffective == null) {
							return;
						}

						if (isEffective) {
							if (customsDeclaration.getEffective() != null
									&& customsDeclaration.getEffective()
											.equals(new Boolean(true))) {
								customsDeclaration.setEffective(new Boolean(
										false));
								customsDeclaration.setEffectiveDate(null);
								customsDeclaration.setEffectiveMan(null);
							} else {
								customsDeclaration.setEffective(new Boolean(
										true));
								customsDeclaration.setEffectiveDate(new Date());
								customsDeclaration.setEffectiveMan(CommonVars
										.getCurrUser().getUserName());
							}
							customsDeclaration = baseEncAction
									.saveCustomsDeclaration(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration);
							tableModel.updateRow(customsDeclaration);
						} else {
							customsDeclaration.setIsCheck(new Boolean(true));
							customsDeclaration = (BaseCustomsDeclaration) baseEncAction
									.saveCustomsDeclaration(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration);
							tableModel.updateRow(customsDeclaration);
						}
					}

				}
			});
		}
		return jButton3;
	}

	/**
	 * 打印套打海关保税区进境货物备案清单
	 */
	public void printCoverPrintReport() {
		try {
			List list = tableModel.getCurrentRows();
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			System.out.println("bcd=" + bcd);
			Integer s = bcd.getImpExpFlag();
			System.out.println("s=" + s);
			List list2 = baseEncAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), bcd);
			InputStream subReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list2);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingBill.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							list));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打海关保税区进境货物备案清单
	 */
	public void printNonCoverPrintReport() {
		try {
			List list = tableModel.getCurrentRows();
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			System.out.println("bcd=" + bcd);
			Integer s = bcd.getImpExpFlag();
			System.out.println("s=" + s);
			List list2 = baseEncAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), bcd);
			InputStream subReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list2);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingBill.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							list));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
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
			btnQuery.setPreferredSize(new Dimension(85, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgQueryQpSpecial dg = new DgQueryQpSpecial();
					dg.setBaseEncAction(baseEncAction);
					dg.setVisible(true);
				}
			});
		}
		return btnQuery;
	}

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("查看");
			btnBrowse.setPreferredSize(new Dimension(40, 30));
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTcs() {
		if (btnTcs == null) {
			btnTcs = new JButton();
			btnTcs.setText("集成通申报");
			btnTcs.setPreferredSize(new Dimension(75, 30));
			btnTcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.checkAuthorityDeclarationByTcs(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.checkAuthorityDeclarationByTcs(new Request(
										CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.checkAuthorityDeclarationByTcs(new Request(
										CommonVars.getCurrUser()));
					}
					// specialCustomsAuthorityAction.checkAuthorityDeclarationByTcs(new
					// Request(
					// CommonVars.getCurrUser()));
					declareCustomsDeclarationTcs();
				}
			});
		}
		return btnTcs;
	}

	protected void declareCustomsDeclarationTcs() {
		try {
			DgProcessCustomsMessageTCS dgEdiMessage = new DgProcessCustomsMessageTCS();
			dgEdiMessage.setBaseEncAction(this.baseEncAction);
			dgEdiMessage.setTableModelCustoms(tableModel);
			dgEdiMessage.setProjectType(projectType);
			dgEdiMessage.setVisible(true);
		} finally {

		}
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
	 * This method initializes jPopupMenu1
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInputBGD() {
		if (pmInputBGD == null) {
			pmInputBGD = new JPopupMenu();
			pmInputBGD.setSize(new Dimension(67, 38));
			pmInputBGD.add(getMiInputDirect());
		}
		return pmInputBGD;
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
						}
					});
		}
		return miInputDirect;
	}

	/**
	 * 导入特殊报关单
	 * 
	 * @param importFromFile
	 */
	private void inputExportBGDFromQP() {
		if (JOptionPane.showConfirmDialog(FmBaseSpecialCustomsDeclaration.this,
				"是否确定导入报关单?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
			return;
		}
		try {
			ImportBGDCondition condition = null;
			DgInputBGDFromQP dg = new DgInputBGDFromQP();
			dg.setBaseEncAction(baseEncAction);
			dg.setProjectType(projectType);
			dg.setImportBGD(false);
			dg.setSpecialDeclaration(true);
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
						FmBaseSpecialCustomsDeclaration.this, "没有导入任何报关单！",
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
			JOptionPane.showMessageDialog(FmBaseSpecialCustomsDeclaration.this,
					"导入出口报关单失败。" + ex.getMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * This method initializes btnReSet
	 * 
	 * @return javax.swing.JButton
	 */
	// private JButton getBtnReSet() {
	// if (btnReSet == null) {
	// btnReSet = new JButton();
	// btnReSet.setText("流水号重排");
	// btnReSet.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// specialCustomsAuthorityAction.reSortAutoOrder(new Request(
	// CommonVars.getCurrUser()));
	// if (JOptionPane
	// .showConfirmDialog(
	// FmBaseSpecialCustomsDeclaration.this,
	// "是否确定要将报关单流水号重新排序?如果选择'是',\n请先终止其他的客户端程序对报关单的操作,\n排序完成后再进行相关操作,以免造成数据不一致!",
	// "提示", 0) == 0) {
	// new ExectAutoOrder().start();
	// }
	// }
	// });
	// }
	// btnReSet.setVisible(false);//2010-11-12屏蔽
	// return btnReSet;
	// }

	class ExectAutoOrder extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(FmBaseSpecialCustomsDeclaration.this);
				CommonProgress.setMessage("系统正在排序数据，请稍后...");
				resortSpecialCustomsDeclarationSerialNumber();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						FmBaseSpecialCustomsDeclaration.this,
						"排序数据失败：" + e.getMessage(), "提示", 2);
			} finally {
				initTable(null);
				setState();
			}
		}
	}

	public void resortSpecialCustomsDeclarationSerialNumber() {
		baseEncAction.resortSpecialCustomsDeclarationSerialNumber(new Request(
				CommonVars.getCurrUser()));
	}

	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton("批量打印");
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// BaseEmsHead emsHead = (BaseEmsHead)
					// cbbEmshead.getSelectedItem();
					DgBatchPrint dg = new DgBatchPrint(ImpExpFlag.SPECIAL,
							projectType);
					dg.setVisible(true);
				}
			});
		}
		return btnPrint;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
