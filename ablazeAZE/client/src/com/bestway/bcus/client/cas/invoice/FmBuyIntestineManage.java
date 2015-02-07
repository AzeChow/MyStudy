package com.bestway.bcus.client.cas.invoice;

/**
 *海关帐----- 发票管理
 * 
 * 刘民添加部分注释 修改时间: 2009-4-7
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import sun.java2d.pipe.TextRenderer;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.TempEmsImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmBuyIntestineManage extends JInternalFrameBase {

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDel = null;

	private JButton btnCopy = null;

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	// private JLabel jLabel = null;

	private JLabel lbDeclareGoods1 = null;

	private JLabel lbSupplier = null;

	private JLabel lbListBeginDate = null;

	private JLabel lbListEndDate = null;

	private JCalendarComboBox cbbListBeginDate = null;

	private JCalendarComboBox cbbListEndDate = null;

	private JButton btnQuery = null;

	private JButton btnFecav = null;

	private JComboBox cbbSupplier = null;

	private JTextField tfCustomsGoods = null;

	private JButton btnHsName = null;

	private JLabel lbEmsNo = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JPanel jPanel3 = null;

	private JSplitPane spnReport = null;

	private JPanel jPanel4 = null;

	private JSplitPane spnInvoiceManage = null;

	private JToolBar jToolBar1 = null;

	private JLabel lbReportTypes = null;

	private JComboBox cbbReport = null;

	private JFooterScrollPane jScrollPane2 = null;

	private JFooterTable jTable2 = null;

	private JPanel jPanel5 = null;

	private JLabel lbSCNo = null;

	private JComboBox cbbEMSNo = null;

	private JLabel lbManufacturerName = null;

	private JLabel lbDeclareGoods = null;

	private JTextField tfDeclareGoods = null;

	private JComboBox cbbManufacturerName = null;

	private JButton btnImport1 = null;

	private JButton btnQuery1 = null;

	private JButton btnPrint = null;

	private JButton btnExit1 = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelFecav = null;

	// private tableModelReport = null;

	private CasAction casAction = null;

	private Map map = new HashMap(); // @jve:decl-index=0:

	public MaterialManageAction materialManageAction = null;
	/** 默认行 **/
	// private int oldRow = -1;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	// 以下数据为测试数据
	// private List<BillTemp> listTmp = new ArrayList(); // @jve:decl-index=0:

	private JComboBox cbbEmsNo = null;

	// private TextRenderer textRenderer = null; //
	// @jve:decl-index=0:visual-constraint="822,325"

	private JCheckBox cbIsCav = null;

	private JButton btnRCancel = null;

	private JButton btnImport = null;

	private JButton btnReflash = null;

	private JRadioButton rbSelectAll = null;

	private JRadioButton rbNotSelect = null;

	private JButton btnExit = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBuyIntestineManage() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("国内购买发票管理");
		this.setSize(new Dimension(777, 453));
		this.setContentPane(getJTabbedPane());

	}

	/**
	 * 初始化初始化UI控件
	 */

	private void initUIComponents() {
		initFirstTable();
		initTableFecav(new ArrayList());
		initTableReport1(new ArrayList());
		// ScmCoc 初始化供应商
		List list = new ArrayList();
		list = this.materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				customerList.add(scmCoc);
			}
		}
		this.cbbSupplier.setModel(new DefaultComboBoxModel(customerList
				.toArray()));
		this.cbbManufacturerName.setModel(new DefaultComboBoxModel(customerList
				.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbSupplier,
				"code", "name", 250);
		this.cbbSupplier.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		cbbManufacturerName.setSelectedIndex(-1);
		cbbSupplier.setSelectedIndex(-1);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbManufacturerName, "code", "name", 250);
		this.cbbManufacturerName.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));

		List<BaseEmsHead> lists = casAction.findAllEmsHeadH2k(new Request(
				CommonVars.getCurrUser()));
		for (BaseEmsHead data : lists) {
			map.put(data.getEmsNo(), data);
		}

		this.cbbEmsNo
				.setModel(new DefaultComboBoxModel(map.values().toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsNo,
				"emsNo", "emsNo", 250);
		this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
				"emsNo", "emsNo", 0, 150));
		cbbEmsNo.setSelectedIndex(-1);
		this.cbbEMSNo
				.setModel(new DefaultComboBoxModel(map.values().toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEMSNo, "",
				"emsNo", 250);
		this.cbbEMSNo.setRenderer(CustomBaseRender.getInstance().getRender("",
				"emsNo", 0, 150));
		cbbEMSNo.setSelectedIndex(-1);
		getButtonGroup();
		addListeners();
	}

	/**
	 * 添加监听器
	 */

	private void addListeners() {

		btnHsName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ScmCoc p1 = (ScmCoc) getCbbSupplier().getSelectedItem();
				String obj = (String) getAllName(p1);
				if (obj == null) {
					getTfCustomsGoods().setText("");
				} else {
					getTfCustomsGoods().setText(obj);
				}
			}
		});
		btnQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (getTfCustomsGoods().getText() == null
						|| getTfCustomsGoods().getText().equals("")) {
					JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
							"请选择商品名称！", "提示！", JOptionPane.WARNING_MESSAGE);
					return;
				}
				new findInvoiceThread().execute();
			}
		});
		btnRCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				List list = getSelectedData();
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
							"没有选择数据！", "提示！", 2);
					return;
				}
				if (JOptionPane.showConfirmDialog(FmBuyIntestineManage.this,
						"您确认要取消核销数据吗？", "提示信息!", 0) == JOptionPane.YES_OPTION) {
					List ls = casAction.invoiceRCancel(new Request(CommonVars
							.getCurrUser()), list, null);
					tableModelFecav.updateRows(ls);
					initFirstTable();
					new findInvoiceThread().execute();
				}
			}
		});
		btnFecav.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getRbNotSelect().setSelected(true);
				jTable1.getColumnModel().getColumn(7).getCellEditor()
						.stopCellEditing();

				BaseEmsHead head = (BaseEmsHead) cbbEmsNo.getSelectedItem();
				if (head == null) {
					JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
							"请选择手册号！", "提示！", 2);
					return;
				}
				List list = getSelectedData();
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
							"没有选择数据！", "提示！", 2);
					return;
				}
				for (int i = 0; i < list.size(); i++) {
					CasInvoiceInfo info = (CasInvoiceInfo) list.get(i);
					if (info.getCanceledNum() == null
							|| info.getCanceledNum() == 0) {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "核销数量不能为0！", "提示！",
								2);
						return;
					}
				}
				if (JOptionPane.showConfirmDialog(FmBuyIntestineManage.this,
						"您确认要核销所选数据吗？", "提示信息!", 0) == JOptionPane.YES_OPTION) {
					List ls = new ArrayList();
					try {
						ls = casAction.invoiceCancel(new Request(CommonVars
								.getCurrUser()), list, head);

					} catch (RuntimeException ex) {
						ex.printStackTrace();
						if (JOptionPane.showConfirmDialog(
								FmBuyIntestineManage.this, ex.getMessage()
										+ "\n您确认要强制核销所选的数据吗？", "提示信息!", 0) == JOptionPane.YES_OPTION) {
							TempEmsImg temp = (TempEmsImg) getBaseEmsImg(head);
							if (temp == null) {
								return;
							}
							if (temp.getSeqNum() == null) {
								JOptionPane.showMessageDialog(
										FmBuyIntestineManage.this,
										"手册料件序号不存在，不能核销！", "提示！", 2);
								return;
							}
							ls = casAction.invoiceCancel(new Request(CommonVars
									.getCurrUser()), list, temp.getSeqNum(),
									head);
							tableModelFecav.updateRows(ls);
							initFirstTable();
							new findInvoiceThread().execute();
						} else {
							return;
						}
					}
					tableModelFecav.updateRows(ls);
					initFirstTable();
					new findInvoiceThread().execute();
				}
			}

		});
		rbSelectAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (getRbSelectAll().isSelected()) {
					selectAllOrNull(true);
				}
			}
		});
		rbNotSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (getRbNotSelect().isSelected()) {
					selectAllOrNull(false);
				}
			}
		});
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("发票管理", null, getJPanel(), null);
			jTabbedPane.addTab("发票核销", null, getJPanel1(), null);
			jTabbedPane.addTab("报表", null, getJPanel3(), null);
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

		}
		return jTabbedPane;
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH); // Generated
			jPanel.add(getJScrollPane(), BorderLayout.CENTER); // Generated
		}
		return jPanel;
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
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnReflash());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						DgBuyIntestine dgBuyIntestine = new DgBuyIntestine();
						dgBuyIntestine.setState(DataState.BROWSE);
						dgBuyIntestine.setCasInvoice((CasInvoice) tableModel
								.getCurrentRow());
						dgBuyIntestine.setVisible(true);
					}
				}
			});
		}
		return jTable;
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
				public void actionPerformed(ActionEvent e) {
					DgBuyIntestine dgBuyIntestine = new DgBuyIntestine();
					dgBuyIntestine.setState(DataState.ADD);
					dgBuyIntestine.setVisible(true);
					if (dgBuyIntestine.isOk) {
						CasInvoice inv = dgBuyIntestine.getCasInvoice();
						if (inv != null) {
							tableModel.addRow(inv);
						}
					}
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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "请选择数据", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBuyIntestine dgBuyIntestine = new DgBuyIntestine();
					dgBuyIntestine.setState(DataState.EDIT);
					CasInvoice inv = (CasInvoice) tableModel.getCurrentRow();
					dgBuyIntestine.setCasInvoice(inv);
					dgBuyIntestine.setVisible(true);
					tableModel.updateRow(inv);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除");
			btnDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CasInvoice obj = (CasInvoice) tableModel.getCurrentRow();
					if (obj == null) {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "请选择数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.CANCEL_OPTION == JOptionPane
							.showConfirmDialog(FmBuyIntestineManage.this,
									"您确定要删除吗?", "提示",
									JOptionPane.OK_CANCEL_OPTION)) {
						return;
					}

					if (casAction.deleteCasInvoice(new Request(CommonVars
							.getCurrUser()), obj)) {
						tableModel.deleteRow(obj);
					} else {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "该发票存在核销的商品,不能删除！",
								"提示！", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return btnDel;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("浏览");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "请选择数据", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBuyIntestine dgBuyIntestine = new DgBuyIntestine();
					dgBuyIntestine.setState(DataState.BROWSE);
					dgBuyIntestine.setCasInvoice((CasInvoice) tableModel
							.getCurrentRow());
					dgBuyIntestine.setVisible(true);
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
			jPanel1.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(75);
			jSplitPane1.setTopComponent(getJPanel2());
			jSplitPane1.setBottomComponent(getJScrollPane1());
			jSplitPane1.setDividerSize(3);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new Rectangle(441, 17, 43, 22));
			lbEmsNo.setText("手册号:");
			lbListEndDate = new JLabel();
			lbListEndDate.setBounds(new Rectangle(263, 43, 83, 22));
			lbListEndDate.setText("开票结束结束:");
			lbListBeginDate = new JLabel();
			lbListBeginDate.setBounds(new Rectangle(263, 17, 83, 22));
			lbListBeginDate.setText("开票起始日期:");
			lbSupplier = new JLabel();
			lbSupplier.setBounds(new Rectangle(21, 43, 62, 22));
			lbSupplier.setText("供  应  商:");
			lbDeclareGoods1 = new JLabel();
			lbDeclareGoods1.setBounds(new Rectangle(21, 17, 62, 22));
			lbDeclareGoods1.setText("报关商品:");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "", javax.swing.border.TitledBorder.ABOVE_BOTTOM,
					javax.swing.border.TitledBorder.RIGHT, null, null));
			jPanel2.add(lbDeclareGoods1, null);
			jPanel2.add(lbSupplier, null);
			jPanel2.add(lbListBeginDate, null);
			jPanel2.add(lbListEndDate, null);
			jPanel2.add(getCbbListBeginDate(), null);
			jPanel2.add(getCbbListEndDate(), null);
			jPanel2.add(getBtnQuery(), null);
			jPanel2.add(getBtnFecav(), null);
			jPanel2.add(getCbbSupplier(), null);
			jPanel2.add(getTfCustomsGoods(), null);
			jPanel2.add(getBtnHsName(), null);
			jPanel2.add(lbEmsNo, null);
			jPanel2.add(getCbbEmsNo(), null);
			jPanel2.add(getCbIsCav(), null); // Generated
			jPanel2.add(getBtnRCancel(), null); // Generated
			jPanel2.add(getRbSelectAll(), null);
			jPanel2.add(getRbNotSelect(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbListBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListBeginDate() {
		if (cbbListBeginDate == null) {
			cbbListBeginDate = new JCalendarComboBox();
			cbbListBeginDate.setBounds(new Rectangle(345, 17, 91, 22));
			cbbListBeginDate.setDate(null);
		}
		return cbbListBeginDate;
	}

	/**
	 * This method initializes cbbListEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListEndDate() {
		if (cbbListEndDate == null) {
			cbbListEndDate = new JCalendarComboBox();
			cbbListEndDate.setBounds(new Rectangle(345, 43, 91, 22));
		}
		return cbbListEndDate;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(604, 17, 65, 22));
			btnQuery.setText("查询");

		}
		return btnQuery;
	}

	/**
	 * This method initializes btnFecav
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFecav() {
		if (btnFecav == null) {
			btnFecav = new JButton();
			btnFecav.setBounds(new Rectangle(679, 17, 65, 22));
			btnFecav.setText("核销");
		}
		return btnFecav;
	}

	/**
	 * This method initializes cbbSupplier
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSupplier() {
		if (cbbSupplier == null) {
			cbbSupplier = new JComboBox();
			cbbSupplier.setBounds(new Rectangle(86, 43, 169, 22));
		}
		return cbbSupplier;
	}

	/**
	 * This method initializes tfCustomsGoods
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsGoods() {
		if (tfCustomsGoods == null) {
			tfCustomsGoods = new JTextField();
			tfCustomsGoods.setBounds(new Rectangle(86, 17, 149, 22));
			// tfCustomsGoods.setHorizontalAlignment(JTextField.RIGHT);

		}
		return tfCustomsGoods;
	}

	/**
	 * This method initializes btnHsName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setBounds(new Rectangle(233, 17, 22, 22));
			btnHsName.setText("...");

		}
		return btnHsName;
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
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getSpnReport(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes spnReport
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSpnReport() {
		if (spnReport == null) {
			spnReport = new JSplitPane();
			spnReport.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spnReport.setDividerSize(3);
			spnReport.setTopComponent(getJPanel4());
			spnReport.setBottomComponent(getJScrollPane2());
			spnReport.setDividerLocation(100);
		}
		return spnReport;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getSpnInvoiceManage(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes spnInvoiceManage
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSpnInvoiceManage() {
		if (spnInvoiceManage == null) {
			spnInvoiceManage = new JSplitPane();
			spnInvoiceManage
					.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spnInvoiceManage.setTopComponent(getJToolBar1());
			spnInvoiceManage.setDividerSize(3);
			// spnInvoiceManage.setBottomComponent(getJScrollPane2());
			spnInvoiceManage.setBottomComponent(getJPanel5());
			spnInvoiceManage.setDividerLocation(30);
		}
		return spnInvoiceManage;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			lbReportTypes = new JLabel();
			lbReportTypes.setText("报表类型:");
			jToolBar1 = new JToolBar();
			jToolBar1.add(lbReportTypes);
			jToolBar1.add(getCbbReport());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes cbbReport
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReport() {
		if (cbbReport == null) {
			cbbReport = new JComboBox();
			cbbReport.addItem("(1)国内购买原材料统计明细表");
			cbbReport.addItem("(2)国内购买原材料发票统计明细表");
			cbbReport.addItem("(3)国内购料发票核销明细表");
			cbbReport.addItem("(4)国内购买原材料收货与开票统计分表");
			cbbReport.addItem("(5)国内购买原材料开票与核销统计分表");
			cbbReport.addItem("(6)国内购买原材料收货与开票统计总表");
			cbbReport.addItem("(7)国内购买原材料开票与核销统计总表");
			cbbReport.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(ItemEvent e1) {
					final ItemEvent e = e1;

					jScrollPane2.setViewportView(getJTable2());

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (e.getItem().equals("(1)国内购买原材料统计明细表")) {
								initTableReport1(new ArrayList());
							} else if (e.getItem().equals("(2)国内购买原材料发票统计明细表")) {
								initTableReport2(new ArrayList());
							} else if (e.getItem().equals("(3)国内购料发票核销明细表")) {
								initTableReport3(new ArrayList());
							} else if (e.getItem()
									.equals("(4)国内购买原材料收货与开票统计分表")) {
								initTableReport4(new ArrayList());
							} else if (e.getItem()
									.equals("(5)国内购买原材料开票与核销统计分表")) {
								initTableReport5(new ArrayList());
							} else if (e.getItem()
									.equals("(6)国内购买原材料收货与开票统计总表")) {
								initTableReport6(new ArrayList());
							} else if (e.getItem()
									.equals("(7)国内购买原材料开票与核销统计总表")) {
								initTableReport7(new ArrayList());
							}
							// initTableReport2(tempList);
						}
					});
					setScomVisible(true);
				}

			});
		}
		return cbbReport;
	}

	private void setScomVisible(boolean value) {
		lbManufacturerName.setVisible(value);
		cbbManufacturerName.setVisible(value);
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JFooterScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getJTable2() {
		// if (jTable2 == null) {
		jTable2 = new JFooterTable() {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};
		// }
		return jTable2;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			lbDeclareGoods = new JLabel();
			lbDeclareGoods.setBounds(new Rectangle(200, 4, 59, 22));
			lbDeclareGoods.setText("报关商品:");
			lbManufacturerName = new JLabel();
			lbManufacturerName.setBounds(new Rectangle(11, 35, 58, 22));
			lbManufacturerName.setText("厂商名称:");
			lbSCNo = new JLabel();
			lbSCNo.setBounds(new Rectangle(11, 5, 51, 22));
			lbSCNo.setText("手册号:");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.add(lbSCNo, null);
			jPanel5.add(getCbbEMSNo(), null);
			jPanel5.add(lbManufacturerName, null);
			jPanel5.add(lbDeclareGoods, null);
			jPanel5.add(getTfDeclareGoods(), null);
			jPanel5.add(getCbbManufacturerName(), null);
			jPanel5.add(getBtnImport1(), null);
			jPanel5.add(getBtnQuery1(), null);
			jPanel5.add(getBtnPrint(), null);
			jPanel5.add(getBtnExit1(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes cbbEMSNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEMSNo() {
		if (cbbEMSNo == null) {
			cbbEMSNo = new JComboBox();
			cbbEMSNo.setBounds(new Rectangle(66, 5, 127, 22));
		}
		return cbbEMSNo;
	}

	/**
	 * This method initializes tfDeclareGoods
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareGoods() {
		if (tfDeclareGoods == null) {
			tfDeclareGoods = new JTextField();
			tfDeclareGoods.setBounds(new Rectangle(257, 5, 160, 22));
		}
		return tfDeclareGoods;
	}

	/**
	 * This method initializes cbbManufacturerName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManufacturerName() {
		if (cbbManufacturerName == null) {
			cbbManufacturerName = new JComboBox();
			cbbManufacturerName.setBounds(new Rectangle(71, 35, 364, 22));
		}
		return cbbManufacturerName;
	}

	/**
	 * This method initializes btnImport1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport1() {
		if (btnImport1 == null) {
			btnImport1 = new JButton();
			btnImport1.setBounds(new Rectangle(417, 5, 18, 22));
			btnImport1.setText("...");
			btnImport1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc p1 = (ScmCoc) getCbbSupplier().getSelectedItem();
					String obj = (String) getAllName(p1);
					if (obj == null) {
						getTfDeclareGoods().setText("");
					} else {
						getTfDeclareGoods().setText(obj);
					}
				}
			});
		}
		return btnImport1;
	}

	/**
	 * 获得所有发票名称
	 * 
	 * @param p1
	 * @return
	 */

	public Object getAllName(final ScmCoc p1) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		DgCommonQuery.setTableColumns(list);
		list.add(new JTableListColumn("发票名称", "cusName", 300));
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List datalist = casAction.findInvoiceInfoCusName(new Request(CommonVars
				.getCurrUser()), p1);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(datalist);
		dgCommonQuery.setTitle("选择发票资料");
		dgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获取基础帐册资料
	 * 
	 * @param head
	 * @return
	 */

	public Object getBaseEmsImg(final BaseEmsHead head) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		DgCommonQuery.setTableColumns(list);
		list.add(new JTableListColumn("帐册序号", "seqNum", 70));
		list.add(new JTableListColumn("商品名称", "cusName", 150));
		list.add(new JTableListColumn("商品编码", "complex.code", 150));
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List datalist = casAction.findBaseEmsImg(new Request(CommonVars
				.getCurrUser()), head);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(datalist);
		dgCommonQuery.setTitle("选择对应核销资料!");
		dgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes btnQuery1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery1() {
		if (btnQuery1 == null) {
			btnQuery1 = new JButton();
			btnQuery1.setBounds(new Rectangle(454, 5, 67, 22));
			btnQuery1.setText("查询");
			btnQuery1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new searchThread().start();
				}
			});
		}
		return btnQuery1;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(527, 5, 67, 22));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModelReport = (JTableListModel) jTable2
							.getModel();
					if (tableModelReport == null
							|| tableModelReport.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								FmBuyIntestineManage.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Integer item = cbbReport.getSelectedIndex();
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModelReport.getList());
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());// 公司名称
					InputStream reportStream = null;
					if (item == 0) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/BuyIntestineMaterielDetail.jasper");
					} else if (item == 1) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/BuyIntestineMaterielInvoiceDetail.jasper");
					} else if (item == 2) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/IntestineInvoiceFecavDetail.jasper");
					} else if (item == 3) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/BuyIntestineMaterielInAndInvoice.jasper");
					} else if (item == 4) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/IntestineMaterielInvoiceAndFecav.jasper");
					} else if (item == 5) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/IntestineMaterielInAndInvoiceTotal.jasper");
					} else if (item == 6) {
						reportStream = FmBuyIntestineManage.class
								.getResourceAsStream("report/IntestineInvoiceAndFecavTotal.jasper");
					}
					try {
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});

		}
		return btnPrint;
	}

	/**
	 * This method initializes btnExit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit1() {
		if (btnExit1 == null) {
			btnExit1 = new JButton();
			btnExit1.setBounds(new Rectangle(599, 5, 67, 22));
			btnExit1.setText("退出");
		}
		return btnExit1;
	}

	/**
	 * 初始化Table
	 */

	private void initFirstTable() {
		List rlist = casAction.findCasInvoice(new Request(CommonVars
				.getCurrUser()));
		tableModel = new JTableListModel(jTable, rlist,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("发票号码", "invoiceNo", 120));
						list.add(addColumn("手册号", "emsNo", 120));
						list.add(addColumn("供应商", "customer.name", 180));
						list.add(addColumn("开票日期", "validDate", 150));
						return list;
					}
				});
	}

	/**
	 * 初始化Table
	 * 
	 * @param list
	 */

	private void initTableFecav(final List list) {
		JTableListModelAdapter jt = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 30));
				list.add(addColumn("发票号码", "casInvoice.invoiceNo", 100));
				list.add(addColumn("发票名称", "cuName", 150));
				list.add(addColumn("发票规格", "cuSpec", 150));
				list.add(addColumn("发票编码", "complex.code", 150));
				list.add(addColumn("发票数量", "amount", 60));
				list.add(addColumn("核销数量", "canceledNum", 100));
				list.add(addColumn("单价(RMB)", "price", 100));
				list.add(addColumn("金额(RMB)", "totalMoney", 100));
				list.add(addColumn("报关单位", "unit.name", 100));
				list.add(addColumn("商品重量", "totalWeight", 100));
				list.add(addColumn("开票日期", "casInvoice.validDate", 100));
				list.add(addColumn("手册序号", "seqNum", 100));
				list.add(addColumn("手册号", "casInvoice.emsNo", 100));
				list.add(addColumn("来源", "projectName", 100));
				list.add(addColumn("核销", "canceled", 30));
				return list;
			}
		};
		tableModelFecav = new JTableListModel(jTable1, list, jt,
				ListSelectionModel.SINGLE_SELECTION);
		jt.setEditableColumn(1);
		jt.setEditableColumn(7);
		tableModelFecav.setAllowSetValue(true);
		jTable1.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());

		JCheckBox cb = new JCheckBox();
		cb.setHorizontalAlignment(JLabel.CENTER);
		jTable1.getColumnModel().getColumn(1).setCellEditor(
				new DefaultCellEditor(cb));
		jTable1.getColumnModel().getColumn(7).setCellEditor(
				new JTextFieldEditor());
		jTable1.getColumnModel().getColumn(7).setCellRenderer(
				new ForcedEditTableCellRenderer());
		jTable1.getColumnModel().getColumn(16).setCellRenderer(
				new TableCheckBoxRender());
		jTable1.setRowHeight(22);
	}

	/**
	 * 国内购买原材料统计明细表
	 * 
	 * @param list
	 */
	private void initTableReport1(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("交货日期",
								"billDetail.billMaster.validDate", 100));
						list.add(addColumn("单据类型",
								"billDetail.billMaster.billType.name", 100));
						list.add(addColumn("交货日期",
								"billDetail.billMaster.validDate", 100));
						list.add(addColumn("工厂料号", "billDetail.ptPart", 100));
						list.add(addColumn("品名规格", "billDetail.ptSpec", 100));
						list
								.add(addColumn("订单号码", "billDetail.oderBillNo",
										100));// 此为错误字段，以应付17号检查
						list
								.add(addColumn("送货单号", "billDetail.takeBillNo",
										100));// //此为错误字段，以应付17号检查
						list.add(addColumn("数量", "billDetail.ptAmount", 100));
						list
								.add(addColumn("单位", "billDetail.ptUnit.name",
										100));
						list
								.add(addColumn("折算报关数量", "billDetail.hsAmount",
										100));
						list.add(addColumn("报关单位", "billDetail.hsUnit.name",
								100));
						// list.add(addColumn("单重(KG)", "", 100));
						list.add(addColumn("重量(KG)", "billDetail.netWeight",
								100));
						list.add(addColumn("核销否", "iscancel", 100));

						return list;
					}
				});
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
		jTable2.getColumnModel().getColumn(13).setCellRenderer(
				new TableCheckBoxRender());
	}

	/**
	 * 国内购买原材料发票统计明细表
	 * 
	 * @param list
	 */
	private void initTableReport2(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("厂商名称", "casInvoice.customer.name",
								100));
						list.add(addColumn("报关商品名称", "cuName", 100));
						list
								.add(addColumn("开票日期", "casInvoice.validDate",
										100));
						list
								.add(addColumn("发票号码", "casInvoice.invoiceNo",
										100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("开票数量", "amount", 100));
						list.add(addColumn("重量(KG)", "totalWeight", 100));
						list.add(addColumn("单价", "price", 100));
						list.add(addColumn("金额(RMB)", "totalMoney", 100));
						list.add(addColumn("合同号码", "casInvoice.emsNo", 100));
						list.add(addColumn("核销否", "canceled", 100));

						return list;
					}
				});
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		jTable2.getColumnModel().getColumn(11).setCellRenderer(
				new TableCheckBoxRender());
	}

	/**
	 * 国内购料发票核销明细表
	 * 
	 * @param list
	 */
	private void initTableReport3(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						// list.add(addColumn("合同号码", "", 100));
						list.add(addColumn("手册号码", "casInvoice.emsNo", 100));
						list.add(addColumn("厂商名称", "casInvoice.customer.name",
								100));
						list.add(addColumn("报关商品名称", "cuName", 100));
						list
								.add(addColumn("开票日期", "casInvoice.validDate",
										100));
						list
								.add(addColumn("发票号码", "casInvoice.invoiceNo",
										100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("开票数量", "amount", 100));
						list.add(addColumn("重量(KG)", "totalWeight", 100));
						list.add(addColumn("金额(RMB)", "totalMoney", 100));

						return list;
					}
				});
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
	}

	/**
	 * 国内购买原材料收货与开票统计分表
	 * 
	 * @param list
	 */
	private void initTableReport4(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter(6) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("厂商名称", "bill1", 100));
						list.add(addColumn("报关商品名称", "bill2", 100));
						list.add(addColumn("单位", "bill3", 100));

						list.add(addColumn("数量", "billSum1", 100));
						list.add(addColumn("重量(KG)", "billSum2", 100));

						list.add(addColumn("数量", "billSum3", 100));
						list.add(addColumn("重量(KG)", "billSum4", 100));
						list.add(addColumn("金额(RMB)", "billSum5", 100));

						list.add(addColumn("数量", "billSum6", 100));
						list.add(addColumn("重量(KG)", "billSum7", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable2.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("收货");
		g_in.add(cm.getColumn(4));
		g_in.add(cm.getColumn(5));

		ColumnGroup g_already = new ColumnGroup("已开票");
		g_already.add(cm.getColumn(6));
		g_already.add(cm.getColumn(7));
		g_already.add(cm.getColumn(8));

		ColumnGroup g_not = new ColumnGroup("未开票");
		g_not.add(cm.getColumn(9));
		g_not.add(cm.getColumn(10));

		GroupableTableHeader header = (GroupableTableHeader) jTable2
				.getTableHeader();
		header.setColumnModel(jTable2.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_already);
		header.addColumnGroup(g_not);
		jTable2.repaint();
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		// tableModelReport.addFooterTypeInfo(new TableFooterType(3,
		// TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
	}

	/**
	 * 国内购买原材料开票与核销统计分表
	 * 
	 * @param list
	 */
	private void initTableReport5(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter(6) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("厂商名称", "bill1", 100));
						list.add(addColumn("报关商品名称", "bill2", 100));
						list.add(addColumn("单位", "bill3", 100));

						list.add(addColumn("数量", "billSum1", 100));
						list.add(addColumn("重量(KG)", "billSum2", 100));
						list.add(addColumn("金额(RMB)", "billSum3", 100));

						list.add(addColumn("数量", "billSum4", 100));
						list.add(addColumn("重量(KG)", "billSum5", 100));
						list.add(addColumn("金额(RMB)", "billSum6", 100));

						list.add(addColumn("数量", "billSum7", 100));
						list.add(addColumn("重量(KG)", "billSum8", 100));
						list.add(addColumn("金额(RMB)", "billSum9", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable2.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("开票");
		g_in.add(cm.getColumn(4));
		g_in.add(cm.getColumn(5));
		g_in.add(cm.getColumn(6));

		ColumnGroup g_already = new ColumnGroup("已核销");
		g_already.add(cm.getColumn(7));
		g_already.add(cm.getColumn(8));
		g_already.add(cm.getColumn(9));

		ColumnGroup g_not = new ColumnGroup("未核销");
		g_not.add(cm.getColumn(10));
		g_not.add(cm.getColumn(11));
		g_not.add(cm.getColumn(12));

		GroupableTableHeader header = (GroupableTableHeader) jTable2
				.getTableHeader();
		header.setColumnModel(jTable2.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_already);
		header.addColumnGroup(g_not);
		jTable2.repaint();
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
	}

	/**
	 * 国内购买原材料收货与开票统计总表
	 * 
	 * @param list
	 */
	private void initTableReport6(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter(6) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("报关商品名称", "bill2", 100));
						list.add(addColumn("单位", "bill3", 100));

						list.add(addColumn("数量", "billSum1", 100));
						list.add(addColumn("重量(KG)", "billSum2", 100));

						list.add(addColumn("数量", "billSum3", 100));
						list.add(addColumn("重量(KG)", "billSum4", 100));
						list.add(addColumn("金额(RMB)", "billSum5", 100));

						list.add(addColumn("数量", "billSum6", 100));
						list.add(addColumn("重量(KG)", "billSum7", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable2.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("收货");
		g_in.add(cm.getColumn(3));
		g_in.add(cm.getColumn(4));

		ColumnGroup g_already = new ColumnGroup("已开票");
		g_already.add(cm.getColumn(5));
		g_already.add(cm.getColumn(6));
		g_already.add(cm.getColumn(7));

		ColumnGroup g_not = new ColumnGroup("未开票");
		g_not.add(cm.getColumn(8));
		g_not.add(cm.getColumn(9));

		GroupableTableHeader header = (GroupableTableHeader) jTable2
				.getTableHeader();
		header.setColumnModel(jTable2.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_already);
		header.addColumnGroup(g_not);
		jTable2.repaint();
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(3,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
	}

	/**
	 * 国内购买原材料开票与核销统计总表
	 * 
	 * @param list
	 */
	private void initTableReport7(final List list) {
		JTableListModel tableModelReport = new JTableListModel(jTable2, list,
				new JTableListModelAdapter(6) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("报关商品名称", "bill2", 100));
						list.add(addColumn("单位", "bill3", 100));

						list.add(addColumn("数量", "billSum1", 100));
						list.add(addColumn("重量(KG)", "billSum2", 100));
						list.add(addColumn("金额(RMB)", "billSum3", 100));

						list.add(addColumn("数量", "billSum4", 100));
						list.add(addColumn("重量(KG)", "billSum5", 100));
						list.add(addColumn("金额(RMB)", "billSum6", 100));

						list.add(addColumn("数量", "billSum7", 100));
						list.add(addColumn("重量(KG)", "billSum8", 100));
						list.add(addColumn("金额(RMB)", "billSum9", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable2.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("开票");
		g_in.add(cm.getColumn(3));
		g_in.add(cm.getColumn(4));
		g_in.add(cm.getColumn(5));

		ColumnGroup g_already = new ColumnGroup("已核销");
		g_already.add(cm.getColumn(6));
		g_already.add(cm.getColumn(7));
		g_already.add(cm.getColumn(8));

		ColumnGroup g_not = new ColumnGroup("未核销");
		g_not.add(cm.getColumn(9));
		g_not.add(cm.getColumn(10));
		g_not.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) jTable2
				.getTableHeader();
		header.setColumnModel(jTable2.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_already);
		header.addColumnGroup(g_not);
		jTable2.repaint();
		tableModelReport.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelReport.addFooterTypeInfo(new TableFooterType(3,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelReport.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
	}

	/**
	 * date->日期型"yyyy-MM-dd"
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}

	/**
	 * 得到后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateAfter(Date date) {
		if (date == null) {
			return null;
		}
		date = dateToStandDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, +1);
		return calendar.getTime();
	}

	/**
	 * 多线程
	 * 
	 * @author Administrator
	 * 
	 */

	class findInvoiceThread extends SwingWorker<Object, Object> {
		List ls = null;

		protected Object doInBackground() throws Exception {
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			Date begindate = dateToStandDate(cbbListBeginDate.getDate());// 开票开始日期
			try {
				CommonProgress.showProgressDialog();
				Date enddate = getDateAfter(cbbListEndDate.getDate());// 开票结束日期
				ls = casAction.findCasInvoiceInfo(new Request(CommonVars
						.getCurrUser()),
						cbbEmsNo.getSelectedItem() == null ? ""
								: ((BaseEmsHead) cbbEmsNo.getSelectedItem())
										.getEmsNo(), (ScmCoc) cbbSupplier
								.getSelectedItem(), getTfCustomsGoods()
								.getText().trim(), begindate, enddate,
						getCbIsCav().isSelected());

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			initTableFecav(ls);
		}
	}

	/**
	 * 发票核销查询多现成
	 * 
	 * @author Administrator
	 * 
	 */

	class searchInvoiceThread extends Thread {
		public void run() {
			List ls = null;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date begindate = dateToStandDate(cbbListBeginDate.getDate());// 开票开始日期
				Date enddate = getDateAfter(cbbListEndDate.getDate());// 开票结束日期
				ls = casAction.findCasInvoiceInfo(new Request(CommonVars
						.getCurrUser()),
						cbbEmsNo.getSelectedItem() == null ? ""
								: ((BaseEmsHead) cbbEmsNo.getSelectedItem())
										.getEmsNo(), (ScmCoc) cbbSupplier
								.getSelectedItem(), getTfCustomsGoods()
								.getText().trim(), begindate, enddate,
						getCbIsCav().isSelected());

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTableFecav(ls);
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 报表查询多线程
	 * 
	 * @author Administrator
	 * 
	 */

	class searchThread extends Thread {
		public void run() {
			btnQuery1.setEnabled(false);
			Integer item = cbbReport.getSelectedIndex();
			List ls = null;

			jScrollPane2.setViewportView(getJTable2());

			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				// 手册号
				String emsNo = cbbEMSNo.getSelectedItem() == null ? ""
						: ((BaseEmsHead) cbbEMSNo.getSelectedItem()).getEmsNo();
				// 客户供应商
				ScmCoc scmCoc = (ScmCoc) cbbManufacturerName.getSelectedItem();
				// 报关名称
				String hsName = tfDeclareGoods.getText().trim();
				if (item == 0) {
					ls = casAction.findChinBuyMaterielReport(new Request(
							CommonVars.getCurrUser()), emsNo, scmCoc, hsName);
				} else if (item == 1) {
					ls = casAction.findCasInvoiceInfo(new Request(CommonVars
							.getCurrUser()), emsNo, scmCoc, hsName);
				} else if (item == 2) {
					ls = casAction.findCasInvoiceInfoCancel(new Request(
							CommonVars.getCurrUser()), emsNo, scmCoc, hsName);
				} else if (item == 3) {
					ls = casAction.findSumBillAndInvoice(new Request(CommonVars
							.getCurrUser()), emsNo, scmCoc, hsName);
				} else if (item == 4) {
					ls = casAction.SumCasInvoiceAndCancel(new Request(
							CommonVars.getCurrUser()), emsNo, scmCoc, hsName);
					System.out.println("ls.size()="+ls.size());
				} else if (item == 5) {
					ls = casAction.findSumBillAndInvoice(new Request(CommonVars
							.getCurrUser()), emsNo, scmCoc, hsName);
				} else if (item == 6) {
					ls = casAction.SumCasInvoiceAndCancel(new Request(
							CommonVars.getCurrUser()), emsNo, scmCoc, hsName);
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				System.out.println("ls="+ls);
//				System.out.println("ls.size()="+ls.size());
				final List tempList = (ls == null ? new ArrayList() : ls);
				if (item == 0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport1(tempList);
						}
					});
				} else if (item == 1) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport2(tempList);
						}
					});
				} else if (item == 2) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport3(tempList);
						}
					});

				} else if (item == 3) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport4(tempList);
						}
					});
				} else if (item == 4) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport5(tempList);
						}
					});
				} else if (item == 5) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport6(tempList);
						}
					});
				} else if (item == 6) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTableReport7(tempList);
						}
					});
				}
				CommonProgress.closeProgressDialog();
				if (ls == null || ls.size() < 1) {
					JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery1.setEnabled(true);
		}
	}

	/**
	 * 内部类
	 * 
	 * @author Administrator
	 * 
	 */

	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {
		public ForcedEditTableCellRenderer() {
			super();
		}

		@Override
		protected void setValue(Object value) {
			Double vl = null;
			try {
				vl = Double.valueOf(value == null ? "0.0" : value.toString());
			} catch (Exception e) {
				e.printStackTrace();
				this.setBackground(Color.BLUE);
			}
			if (vl == 0.0) {
				this.setBackground(Color.BLUE);
			} else {
				this.setBackground(UIManager
						.getColor("Table.dropCellBackground"));
			}
			setText((value == null) ? "" : value.toString());
		}

	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(485, 17, 104, 22));
			cbbEmsNo.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
						cbbEmsNo.setSelectedIndex(-1);
					}
				}
			});
		}
		return cbbEmsNo;
	}

	/**
	 * 改变值
	 * 
	 * @param row
	 */

	public void changeValue(int row) {
		CasInvoiceInfo info = (CasInvoiceInfo) tableModelFecav
				.getDataByRow(row);
		if (info == null) {
			return;
		}

	}

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor {
		private Double dou = null;

		private CasInvoiceInfo info = null;

		public JTextFieldEditor() {
			super(new JTextField());
			this.setClickCountToStart(1);
		}

		@Override
		/* Implements the <code>TableCellEditor</code> interface. */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			info = null;
			dou = null;
			delegate.setValue(value);
			info = (CasInvoiceInfo) ((JTableListModel) table.getModel())
					.getDataByRow(row);
			dou = Double.valueOf(value == null ? "0.0" : value.toString());
			if (info != null
					&& (info.isCanceled() == null ? false : info.isCanceled())) {
				editorComponent.setEnabled(false);
			}
			return editorComponent;
		}

		@Override
		public Object getCellEditorValue() {
			String str = delegate.getCellEditorValue() == null ? "0.0"
					: delegate.getCellEditorValue().toString();
			try {
				Double.valueOf(str.trim().equals("") ? "0.0" : str);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
						"输入非法数字！", "", JOptionPane.WARNING_MESSAGE);
				return dou;
			}
			Double nval = Double.valueOf(str.trim().equals("") ? "0.0" : str);
			;
			if (nval > (info.getAmount() == null ? 0.0 : info.getAmount())) {
				JOptionPane.showMessageDialog(FmBuyIntestineManage.this,
						"核销数量不能大于报关数量！", "", JOptionPane.WARNING_MESSAGE);
				return dou;
			}
			return nval;
		}
	}

	/**
	 * This method initializes cbIsCav
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCav() {
		if (cbIsCav == null) {
			cbIsCav = new JCheckBox();
			cbIsCav.setBounds(new Rectangle(443, 43, 79, 22)); // Generated
			cbIsCav.setText("是否核销"); // Generated
			cbIsCav.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!getCbIsCav().isSelected()) {
						getCbbEmsNo().setSelectedIndex(-1);
					}
				}
			});
		}
		return cbIsCav;
	}

	/**
	 * This method initializes btnRCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRCancel() {
		if (btnRCancel == null) {
			btnRCancel = new JButton();
			btnRCancel.setBounds(new Rectangle(655, 43, 89, 22)); // Generated
			btnRCancel.setText("取消核销"); // Generated

		}
		return btnRCancel;
	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("从文件导入");
			btnImport.setForeground(Color.BLUE);
			btnImport.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					DgCasInvoiceDataImport dg = new DgCasInvoiceDataImport();
					dg.setVisible(true);
				}

			});
		}
		return btnImport;
	}

	/**
	 * This method initializes btnReflash
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReflash() {
		if (btnReflash == null) {
			btnReflash = new JButton();
			btnReflash.setText("刷新");
			btnReflash.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					initFirstTable();
				}

			});
		}
		return btnReflash;
	}

	/**
	 * 编辑列
	 */

	/**
	 * This method initializes rbSelectAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSelectAll() {
		if (rbSelectAll == null) {
			rbSelectAll = new JRadioButton();
			rbSelectAll.setBounds(new Rectangle(539, 43, 54, 22));
			rbSelectAll.setText("全选");

		}
		return rbSelectAll;
	}

	/**
	 * This method initializes rbNotSelect
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNotSelect() {
		if (rbNotSelect == null) {
			rbNotSelect = new JRadioButton();
			rbNotSelect.setBounds(new Rectangle(601, 43, 52, 22));
			rbNotSelect.setText("不选");
			rbNotSelect.setSelected(true);

		}
		return rbNotSelect;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbSelectAll());
			buttonGroup.add(getRbNotSelect());
		}
		return buttonGroup;
	}

	public void setButtonGroup(ButtonGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	/**
	 * 全选
	 * 
	 * @param isselected
	 */

	private void selectAllOrNull(boolean isselected) {
		List list = tableModelFecav.getList();
		for (int i = 0; i < list.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) list.get(i);
			info.setIsSelected(isselected);
		}
		tableModelFecav.getTable().repaint();
	}

	/**
	 * 获取所选中内容的值
	 * 
	 * @return
	 */

	private List getSelectedData() {
		List rlist = new ArrayList();
		List list = tableModelFecav.getList();
		for (int i = 0; i < list.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) list.get(i);
			if (info.getIsSelected() != null && info.getIsSelected()) {
				rlist.add(info);
			}
		}
		return rlist;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
