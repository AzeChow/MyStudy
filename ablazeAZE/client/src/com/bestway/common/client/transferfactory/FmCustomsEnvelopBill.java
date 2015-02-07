package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu.Separator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.transferfactory.report.CustomsEnvelopSubReportDataSource;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import java.awt.Dimension;

/**
 * by 2009-1-10 lm checked
 * 
 * @author Administrator 关封管理
 */
public class FmCustomsEnvelopBill extends FmCommon {
	private JPanel contentPn = null;
	private JTabbedPane tabPn = null;
	private JToolBar tb = null;
	/**
	 * 新增
	 */
	private JButton btnAdd = null;
	/**
	 * 编辑
	 */
	private JButton btnEdit = null;
	/**
	 * 删除
	 */
	private JButton btnDelete = null;
	/**
	 * 打印
	 */
	private JButton btnPrint = null;
	/**
	 * 关闭
	 */
	private JButton btnExit = null;
	/**
	 * 关封进货表格
	 */
	private JTable tbImport = null;
	private JScrollPane spn = null;
	/**
	 * 关封出货表格
	 */
	private JTable tbExport = null;
	private JScrollPane spn1 = null;
	private JPopupMenu pmPrint = null;
	private JMenuItem miOverprintNotPrintCustomsEnvelopNo = null;
	private JMenuItem miOverprintPrintCustomsEnvelopNo = null;
	private JMenuItem miOverprintPrintCustomsEnvelopNoForOld = null;
	private JMenuItem miNotOverprintCustomsEnvelopReport = null;
	private JMenuItem miTransferDescriptionRegister = null;
	/**
	 * 套打不打印关封号
	 */
	private final static int OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO = 1;
	/**
	 * 套打打印关封号
	 */
	private final static int OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO = 2;
	/**
	 * 非套打打印关封
	 */
	private final static int NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT = 3;
	/**
	 * 套打打印关封号ForOld
	 */
	private final static int OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld = 5;
	/**
	 * 关封编辑注册
	 */
	private final static int TRANSFER_DESCRIPTION_REGISTER = 4;
	/**
	 * 关封单据管理
	 */
	private CustomsEnvelopBill customsEnvelopBill = null; // @jve:decl-index=0:
	private List list = null; // @jve:decl-index=0:
	/**
	 *表格模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 是进货还是出货
	 */
	private boolean impExpGoodsFlag = true;
	/**
	 * 是否是第一时间
	 * 
	 */
	private boolean isFirstTime = true;
	/**
	 * 关封报表时间
	 */
	private List subReportData = null;
	/**
	 * 查询
	 */
	private JButton btnQuerry = null;
	private JButton btnRefresh = null;
	/**
	 * 关封操作接口
	 */
	private ManualDeclareAction manualDecleareAction = null;
	private JButton btnShow = null;
	private JPopupMenu pmShow = null; // @jve:decl-index=0:visual-constraint="512,2"
	private JMenuItem miShowNoClosed = null;
	private JMenuItem miShowClose = null;
	private JMenuItem miShowAll = null;
	private JButton btnCopy = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmCustomsEnvelopBill() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		List list = super.transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser()), impExpGoodsFlag, null,
				null, null, null, false);
		initTable(list, tbImport);
		tableModel = (JTableListModel) (tbImport.getModel());
		// tpn.setEnabledAt(1,false);//明门暂时需求，其他需要使用必须修改
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getContentPn());
		this.setSize(626, 335);
		this.setTitle("关封单据");
	}

	/**
	 * This method initializes contentPn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContentPn() {
		if(contentPn == null) {
			contentPn = new JPanel();
			contentPn.setLayout(new BorderLayout());
			contentPn.add(getTb(), java.awt.BorderLayout.NORTH);
			contentPn.add(getTabPn(), java.awt.BorderLayout.CENTER);
		}
		return contentPn;
	}

	/**
	 * @return Returns the customsEnvelopBill.
	 */
	public CustomsEnvelopBill getCustomsEnvelopBill() {
		return customsEnvelopBill;
	}

	/**
	 * @param customsEnvelopBill
	 *            The customsEnvelopBill to set.
	 */
	public void setCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		this.customsEnvelopBill = customsEnvelopBill;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return impExpGoodsFlag;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.impExpGoodsFlag = isImportGoods;
	}

	/**
	 * This method initializes tabPn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabPn() {
		if(tabPn == null) {
			tabPn = new JTabbedPane();
			tabPn.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			tabPn.addTab("进货", null, getSpn(), null);
			tabPn.addTab("出货", null, getSpn1(), null);
			tabPn.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					JTabbedPane jTabPane = (JTabbedPane) e.getSource();
					if(jTabPane.getSelectedIndex() == 0) {
						tableModel = (JTableListModel) (tbImport.getModel());
						impExpGoodsFlag = true;
					}
					else if(jTabPane.getSelectedIndex() == 1) {
						impExpGoodsFlag = false;
						if(isFirstTime == true) {
							List list = transferFactoryManageAction
									.findCustomsEnvelopBill(new Request(
											CommonVars.getCurrUser()),
											impExpGoodsFlag, null, null, null,
											null, false);
							initTable(list, tbExport);
							isFirstTime = false;
						}
						tableModel = (JTableListModel) (tbExport.getModel());
					}
				}
			});
		}
		return tabPn;
	}

	/**
	 * This method initializes tb
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTb() {
		if(tb == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			tb = new JToolBar();
			tb.setLayout(f);
			tb.add(getBtnAdd());
			tb.add(getBtnEdit());
			tb.add(getBtnDelete());
			tb.add(getBtnQuerry());
			tb.add(getBtnRefresh());
			tb.add(getBtnPrint());
			tb.add(getBtnShowAll());
			tb.add(getBtnCopy());
			tb.add(getBtnExit());
		}
		return tb;
	}

	/**
	 * This method initializes tbExport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if(tbExport == null) {
			tbExport = new JTable();
			tbExport.addMouseListener(new MouseListenerClass());
			tbExport.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbExport
									.getModel();
							if(tableModel == null) {
								return;
							}
							try {
								if(tableModel.getCurrentRow() != null) {
									setCustomsEnvelopBill((CustomsEnvelopBill) tableModel
											.getCurrentRow());
									setState();
								}
							}
							catch(Exception cx) {
							}
						}
					});
		}
		return tbExport;
	}

	/**
	 * This method initializes spn1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn1() {
		if(spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getTbExport());
		}
		return spn1;
	}

	/**
	 * This method initializes pmPrint
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmPrint() {
		if(pmPrint == null) {
			pmPrint = new JPopupMenu();
			pmPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmPrint.add(getMiOverprintPrintCustomsEnvelopNoForOld());
			Separator separator0 = new Separator();
			separator0.setForeground(Color.gray);
			pmPrint.add(getMiOverprintPrintCustomsEnvelopNo());
			Separator separator1 = new Separator();
			separator1.setForeground(Color.gray);
			pmPrint.add(separator1);
			pmPrint.add(getMiOverprintNotPrintCustomsEnvelopNo());
			Separator separator2 = new Separator();
			separator2.setForeground(Color.GRAY);
			pmPrint.add(separator2);
			pmPrint.add(getMiNotOverprintCustomsEnvelopReport());
			Separator separator3 = new Separator();
			separator3.setForeground(Color.GRAY);
			pmPrint.add(separator3);
			pmPrint.add(getMiTransferDescriptionRegister());
		}
		return pmPrint;
	}

	/**
	 * This method initializes miOverprintNotPrintCustomsEnvelopNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintNotPrintCustomsEnvelopNo() {
		if(miOverprintNotPrintCustomsEnvelopNo == null) {
			miOverprintNotPrintCustomsEnvelopNo = new JMenuItem();
			miOverprintNotPrintCustomsEnvelopNo.setText("套打关封(不打印关封号)");
			miOverprintNotPrintCustomsEnvelopNo
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							printReport(FmCustomsEnvelopBill.OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO);
						}
					});
		}
		return miOverprintNotPrintCustomsEnvelopNo;
	}

	/**
	 * This method initializes miOverprintPrintCustomsEnvelopNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintPrintCustomsEnvelopNo() {
		if(miOverprintPrintCustomsEnvelopNo == null) {
			miOverprintPrintCustomsEnvelopNo = new JMenuItem();
			miOverprintPrintCustomsEnvelopNo.setText("套打关封(打印关封号)");
			miOverprintPrintCustomsEnvelopNo
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							printReport(FmCustomsEnvelopBill.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO);
						}
					});
		}
		return miOverprintPrintCustomsEnvelopNo;
	}

	/**
	 * This method initializes miOverprintPrintCustomsEnvelopNoForOld
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintPrintCustomsEnvelopNoForOld() {
		if(miOverprintPrintCustomsEnvelopNoForOld == null) {
			miOverprintPrintCustomsEnvelopNoForOld = new JMenuItem();
			miOverprintPrintCustomsEnvelopNoForOld.setText("套打关封.旧(打印关封号)");
			miOverprintPrintCustomsEnvelopNoForOld
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							printReport(FmCustomsEnvelopBill.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld);
						}
					});
		}
		return miOverprintPrintCustomsEnvelopNoForOld;
	}

	/**
	 * This method initializes miOverprintCustomsEnvelopReport
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotOverprintCustomsEnvelopReport() {
		if(miNotOverprintCustomsEnvelopReport == null) {
			miNotOverprintCustomsEnvelopReport = new JMenuItem();
			miNotOverprintCustomsEnvelopReport.setText("非套打关封");
			miNotOverprintCustomsEnvelopReport
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							printReport(FmCustomsEnvelopBill.NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT);
						}
					});
		}
		return miNotOverprintCustomsEnvelopReport;
	}

	/**
	 * This method initializes miTransferDescriptionRegister
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiTransferDescriptionRegister() {
		if(miTransferDescriptionRegister == null) {
			miTransferDescriptionRegister = new JMenuItem();
			miTransferDescriptionRegister.setText("实际结转情况登记表");
			miTransferDescriptionRegister
					.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							printReport(FmCustomsEnvelopBill.TRANSFER_DESCRIPTION_REGISTER);
						}
					});
		}
		return miTransferDescriptionRegister;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(65, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
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
		if(btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(65, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
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
		if(btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if(btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(65, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmPrint().show(btnPrint, 0, btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if(btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(65, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmCustomsEnvelopBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tbImport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if(tbImport == null) {
			tbImport = new JTable();
			tbImport.addMouseListener(new MouseListenerClass());
			tbImport.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbImport
									.getModel();
							if(tableModel == null) {
								return;
							}
							try {
								if(tableModel.getCurrentRow() != null) {
									setCustomsEnvelopBill((CustomsEnvelopBill) tableModel
											.getCurrentRow());
									setState();
								}
							}
							catch(Exception cx) {
							}
						}
					});
		}
		return tbImport;
	}

	/**
	 * 表格鼠标事件
	 */
	class MouseListenerClass extends MouseAdapter {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if(e.getClickCount() == 2) {
				editData();
			}
		}
	}

	/**
	 * This method initializes spn
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn() {
		if(spn == null) {
			spn = new JScrollPane();
			spn.setViewportView(getTbImport());
		}
		return spn;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
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
	 * 初始化数据参数 tb
	 */
	private void initTable(List list, JTable tb) {
		tableModel = new JTableListModel(tb, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("生效", "isAvailability", 50));
						list.add(addColumn("关封号", "customsEnvelopBillNo", 100));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("购销合同编号",
								"purchaseAndSaleContractNo", 80));
						list.add(addColumn("生效日期", "beginAvailability", 80));
						list.add(addColumn("有效截止日期", "endAvailability", 80));
						list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
						list.add(addColumn("审批海关", "customs.name", 60));
						list.add(addColumn("是否结案", "isEndCase", 50));
						list.add(addColumn("结案日期", "endCaseDate", 80));
						list.add(addColumn("结转报关单号",
								"carryForwardApplyToCustomsBillNo", 80));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});
		tb.getColumnModel().getColumn(1).setCellRenderer(new MultiRenderer());
		tb.getColumnModel().getColumn(9).setCellRenderer(new MultiRenderer());
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if(isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			}
			else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			if(Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				return checkBox;
			}
			else if(value == null) {
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 新增
	 */
	private void addData() {
		DgCustomsEnvelopBill dg = new DgCustomsEnvelopBill();
		dg.setImportGoods(this.isImportGoods());
		dg.setCustomsEnvelopModel(this.tableModel);
		dg.setDataState(DataState.ADD);
		dg.setVisible(true);
	}

	/**
	 * 修改
	 */
	private void editData() {
		if(tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// if(((ImpExpRequestBill)tableModel.getCurrentRow()).getIsCustomsBill().booleanValue()){
		// JOptionPane.showMessageDialog(this,
		// "已生成报关清单不能修改!!", "提示", 0);
		// return;
		// }
		DgCustomsEnvelopBill dg = new DgCustomsEnvelopBill();
		dg.setImportGoods(this.isImportGoods());
		dg.setCustomsEnvelopModel(this.tableModel);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if(tableModel.getCurrentRow() != null) {
			if(JOptionPane.showConfirmDialog(this, "是否确定删除数据!!!", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			super.transferFactoryManageAction.deleteCustomsEnvelopBill(
					new Request(CommonVars.getCurrUser()),
					(CustomsEnvelopBill) tableModel.getCurrentRow());
			tableModel.deleteRow(tableModel.getCurrentRow());
		}
		else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		if(getCustomsEnvelopBill() != null) {
			// btnEdit.setEnabled(!customsEnvelopBill.getIsEndCase()
			// .booleanValue());
			btnDelete.setEnabled(!customsEnvelopBill.getIsAvailability()
					.booleanValue()
					&& !customsEnvelopBill.getIsEndCase().booleanValue());
		}
	}

	/**
	 * 报表打印
	 */
	private void printReport(int flag) {
		if(this.tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要打印的关封!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = new ArrayList();
		list.add(this.tableModel.getCurrentRow());
		CustomReportDataSource ds = new CustomReportDataSource(list);
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		switch(flag) {
			case FmCustomsEnvelopBill.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld:
				printReportByCustomsEnvelopForOld(ds, false, true);
				break;
			case FmCustomsEnvelopBill.OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO:
				printReportByCustomsEnvelop(ds, false, true);
				break;
			case FmCustomsEnvelopBill.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO:
				printReportByCustomsEnvelop(ds, true, true);
				break;
			case FmCustomsEnvelopBill.NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT:
				printReportByCustomsEnvelop(ds, true, false);
				break;
			case FmCustomsEnvelopBill.TRANSFER_DESCRIPTION_REGISTER:
				printTransferDescriptionRegister(ds);
				break;
			default:
				break;
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 结转情况登记表打印
	 */
	private void printTransferDescriptionRegister(CustomReportDataSource ds) {
		try {
			InputStream masterReportStream = null;
			InputStream exportSubReportStream = null;
			InputStream importSubReportStream = null;
			masterReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/TransferGoodsRecords.jasper");
			// JasperReport exportSubReport = (JasperReport) JRLoader
			// .loadObject(exportSubReportStream);
			// JasperReport importSubReport = (JasperReport) JRLoader
			// .loadObject(importSubReportStream);
			Map parameters = new HashMap();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		}
		catch(JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 关封报表打印
	 */
	private void printReportByCustomsEnvelop(CustomReportDataSource ds,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint) {
		String transferCode = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.TransferCode);
		try {
			// if (subReportData == null) {
			this.subReportData = this.transferFactoryManageAction
					.findCustomsEnvelopCommodityInfo(new Request(CommonVars
							.getCurrUser()));
			// }
			CustomsEnvelopSubReportDataSource
					.setSubReportData(this.subReportData);
			InputStream masterReportStream = null;
			InputStream exportSubReportStream = null;
			InputStream importSubReportStream = null;
			masterReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopExportReport.jasper");
			//
			// 出口公司
			//
			exportSubReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopExportSubReport.jasper");
			//
			// 进口公司
			//
			importSubReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopImportSubReport.jasper");
			JasperReport exportSubReport = (JasperReport) JRLoader
					.loadObject(exportSubReportStream);
			JasperReport importSubReport = (JasperReport) JRLoader
					.loadObject(importSubReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			String exportCompanyTel = "", exportCorporation = "";// 转出
			String exportYear = "", exportMonth = "", exportDay = "";// 转出
			String importCompanyTel = "", importCorporation = "";// 转入
			String importYear = "", importMonth = "", importDay = "";// 转入
			CustomsEnvelopBill customsEnvelopBill = (CustomsEnvelopBill) this.tableModel
					.getCurrentRow();
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String defaultDate = bartDateFormat.format(customsEnvelopBill
					.getBeginAvailability());
			String[] a = defaultDate.split("-");
			if(impExpGoodsFlag) {
				parameters.put("isImportGoods", new Boolean(true));
				importCorporation = ((Company) customsEnvelopBill.getCompany())
						.getOwner();// 法人
				importCompanyTel = ((Company) customsEnvelopBill.getCompany())
						.getTel();// 电话
				importYear = a[0];
				importMonth = a[1];
				importDay = a[2];
			}
			else {
				parameters.put("isImportGoods", new Boolean(false));
				exportCorporation = ((Company) customsEnvelopBill.getCompany())
						.getOwner();// 法人
				exportCompanyTel = ((Company) customsEnvelopBill.getCompany())
						.getTel();// 电话
				exportYear = a[0];
				exportMonth = a[1];
				exportDay = a[2];
			}
			parameters.put("isCustomsEnvelopNoPrint", new Boolean(
					isCustomsEnvelopNoPrint));
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("exportSubReport", exportSubReport);
			parameters.put("importSubReport", importSubReport);
			parameters.put("traCode", transferCode);
			parameters.put("importCorporation", importCorporation);
			parameters.put("importCompanyTel", importCompanyTel);
			parameters.put("exportCorporation", exportCorporation);
			parameters.put("exportCompanyTel", exportCompanyTel);
			parameters.put("importYear", importYear);
			parameters.put("importMonth", importMonth);
			parameters.put("importDay", importDay);
			parameters.put("exportYear", exportYear);
			parameters.put("exportMonth", exportMonth);
			parameters.put("exportDay", exportDay);
			boolean istins = CommonVars.isCompany("田氏化工");
			if(istins) {
				parameters.put("istins", "1");
			}
			else {
				parameters.put("istins", "0");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		}
		catch(JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 关封报表打印
	 */
	private void printReportByCustomsEnvelopForOld(CustomReportDataSource ds,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint) {
		String transferCode = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.TransferCode);
		try {
			// if (subReportData == null) {
			this.subReportData = this.transferFactoryManageAction
					.findCustomsEnvelopCommodityInfo(new Request(CommonVars
							.getCurrUser()));
			// }
			CustomsEnvelopSubReportDataSource
					.setSubReportData(this.subReportData);
			InputStream masterReportStream = null;
			InputStream exportSubReportStream = null;
			InputStream importSubReportStream = null;
			masterReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopExportReportForOld.jasper");
			//
			// 出口公司
			//
			exportSubReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopExportSubReportForOld.jasper");
			//
			// 进口公司
			//
			importSubReportStream = FmCustomsEnvelopBill.class
					.getResourceAsStream("report/CustomsEnvelopImportSubReportForOld.jasper");
			JasperReport exportSubReport = (JasperReport) JRLoader
					.loadObject(exportSubReportStream);
			JasperReport importSubReport = (JasperReport) JRLoader
					.loadObject(importSubReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(impExpGoodsFlag) {
				parameters.put("isImportGoods", new Boolean(true));
			}
			else {
				parameters.put("isImportGoods", new Boolean(false));
			}
			parameters.put("isCustomsEnvelopNoPrint", new Boolean(
					isCustomsEnvelopNoPrint));
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("exportSubReport", exportSubReport);
			parameters.put("importSubReport", importSubReport);
			parameters.put("traCode", transferCode);
			boolean istins = CommonVars.isCompany("田氏化工");
			if(istins) {
				parameters.put("istins", "1");
			}
			else {
				parameters.put("istins", "0");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		}
		catch(JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes btnQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuerry() {
		if(btnQuerry == null) {
			btnQuerry = new JButton();
			btnQuerry.setText("查询");
			btnQuerry.setPreferredSize(new Dimension(65, 30));
			btnQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsEnvelopBillQuerry dg = new DgCustomsEnvelopBillQuerry();
					dg.isImportGoods = isImportGoods();
					dg.setVisible(true);
					if(dg.isOK) {
						if(impExpGoodsFlag) {
							initTable(dg.getResultList(), tbImport);
						}
						else {
							initTable(dg.getResultList(), tbExport);
						}
					}
				}
			});
		}
		return btnQuerry;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if(btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setPreferredSize(new Dimension(65, 30));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tabPn.getSelectedIndex() == 0) {
						impExpGoodsFlag = true;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, null);
						initTable(list, tbImport);
						tableModel = (JTableListModel) (tbImport.getModel());
					}
					else if(tabPn.getSelectedIndex() == 1) {
						impExpGoodsFlag = false;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, null);
						initTable(list, tbExport);
						tableModel = (JTableListModel) (tbExport.getModel());
					}
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes btnShowAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowAll() {
		if(btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("显示关封");
			btnShow.setPreferredSize(new Dimension(65, 30));
			btnShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmShow().show(btnShow, 0, btnShow.getHeight());
				}
			});
		}
		return btnShow;
	}

	/**
	 * This method initializes pmShow
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmShow() {
		if(pmShow == null) {
			pmShow = new JPopupMenu();
			pmShow.add(getMiShowNoClosed());
			pmShow.add(getMiShowClose());
			pmShow.add(getMiShowAll());
		}
		return pmShow;
	}

	/**
	 * This method initializes miShowNoClosed
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowNoClosed() {
		if(miShowNoClosed == null) {
			miShowNoClosed = new JMenuItem();
			miShowNoClosed.setText("未结案关封");
			miShowNoClosed
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(tabPn.getSelectedIndex() == 0) {
								impExpGoodsFlag = true;
								List list = transferFactoryManageAction
										.findCustomsEnvelopBill(new Request(
												CommonVars.getCurrUser()),
												impExpGoodsFlag, null, null,
												null, null, false);
								initTable(list, tbImport);
							}
							else if(tabPn.getSelectedIndex() == 1) {
								impExpGoodsFlag = false;
								List list = transferFactoryManageAction
										.findCustomsEnvelopBill(new Request(
												CommonVars.getCurrUser()),
												impExpGoodsFlag, null, null,
												null, null, false);
								initTable(list, tbExport);
							}
						}
					});
		}
		return miShowNoClosed;
	}

	/**
	 * This method initializes miShowClose
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowClose() {
		if(miShowClose == null) {
			miShowClose = new JMenuItem();
			miShowClose.setText("已结案关封");
			miShowClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tabPn.getSelectedIndex() == 0) {
						impExpGoodsFlag = true;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, true);
						initTable(list, tbImport);
					}
					else if(tabPn.getSelectedIndex() == 1) {
						impExpGoodsFlag = false;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, true);
						initTable(list, tbExport);
					}
				}
			});
		}
		return miShowClose;
	}

	/**
	 * This method initializes miShowAll
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowAll() {
		if(miShowAll == null) {
			miShowAll = new JMenuItem();
			miShowAll.setText("显示所有关封");
			miShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tabPn.getSelectedIndex() == 0) {
						impExpGoodsFlag = true;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, null);
						initTable(list, tbImport);
					}
					else if(tabPn.getSelectedIndex() == 1) {
						impExpGoodsFlag = false;
						List list = transferFactoryManageAction
								.findCustomsEnvelopBill(new Request(CommonVars
										.getCurrUser()), impExpGoodsFlag, null,
										null, null, null, null);
						initTable(list, tbExport);
					}
				}
			});
		}
		return miShowAll;
	}

	/**
	 * This method initializes btnCopy	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setPreferredSize(new Dimension(65, 30));
			btnCopy.setText("转抄");
			btnCopy.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if(tableModel.getCurrentRow()==null){
						JOptionPane.showMessageDialog(null, "请选择一笔关封数据", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomsEnvelopBill customsEnvelopBill = (CustomsEnvelopBill) tableModel
							.getCurrentRow();
					int res;
					res = JOptionPane.showConfirmDialog(
							FmCustomsEnvelopBill.this, "是否同时转抄表体?", "提示",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						CustomsEnvelopBill newCustomsEnvelopBill = transferFactoryManageAction
								.copyCustomsEnvelopBillAndCommodityInfo(
										new Request(CommonVars.getCurrUser()),
										customsEnvelopBill, true, null);
						tableModel.addRow(newCustomsEnvelopBill);
					} else if (res == JOptionPane.NO_OPTION) {
						CustomsEnvelopBill newCustomsEnvelopBill = transferFactoryManageAction
								.copyCustomsEnvelopBillAndCommodityInfo(
										new Request(CommonVars.getCurrUser()),
										customsEnvelopBill, false, null);
						tableModel.addRow(newCustomsEnvelopBill);
					}
				}
			});
		}
		return btnCopy;
	}
}  //  @jve:decl-index=0:visual-constraint="10,43"