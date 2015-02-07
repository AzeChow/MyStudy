package com.bestway.bcus.client.cas.invoice;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.InvoiceAndBillCorresponding;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.DgCommon;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
@SuppressWarnings("unchecked")
public class DgBuyIntestine extends DgCommon {

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField jTextField = null;

	private JCalendarComboBox cmbDate = null;

	private JComboBox jComboBox = null;

	private JComboBox jComboBox1 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	private JButton jbAdd = null;

	private JButton jbEdit = null;

	private JButton jbDel = null;

	private JPanel jPanel3 = null;

	private JSplitPane jSplitPane1 = null;

	private JToolBar jToolBar1 = null; // @jve:decl-index=0:visual-constraint="102,-44"

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JButton jbRelationSingle = null;

	private JButton jbCancel = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModel1 = null;

	private JTableListModel tableModel2 = null;

	private JTableListModel tableModelRelation = null;

	private JPanel jPanel4 = null;

	private JSplitPane jSplitPane2 = null;

	private int state = DataState.ADD;

	private JTable jTable = null;

	private CasInvoice casInvoice = null; // @jve:decl-index=0:

	private CasAction casAction = null;

	private Map map = new HashMap(); // @jve:decl-index=0:

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JButton btnBro = null;

	private JButton btnEdit = null;

	private JPanel jPanel5 = null;

	private JToolBar jToolBar2 = null;

	private JButton btnAddBill = null;

	private JButton btnDeleBill = null;

	private JScrollPane jScrollPane3 = null;

	private JTable jTable2 = null;

	private JPanel jPanel6 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="7,103"

	private JScrollPane jScrollPane = null;
	public boolean isOk = false;

	/**
	 * This method initializes
	 * 
	 */
	public DgBuyIntestine() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		initFirstTable1(new ArrayList());
		initUiComponents();
		getButtonGroup();
	}

	private void showData() {
		if (state == DataState.ADD) {
			return;
		}
		this.jTextField.setText(this.casInvoice.getInvoiceNo());
		this.jComboBox.setSelectedItem(this.casInvoice.getCustomer());
		this.jComboBox1.setSelectedItem(map.get(this.casInvoice.getEmsNo()));
		this.cmbDate.setDate(this.casInvoice.getValidDate());
	}

	private boolean fillData() {
		if (jTextField.getText() == null || jTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(DgBuyIntestine.this, "请填写发票号！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (jComboBox.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBuyIntestine.this, "请选择客户供应商！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// if (jComboBox1.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(DgBuyIntestine.this, "请选择手册号！",
		// "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// List list=casAction.findCasInvoiceByNo(casInvoice.getInvoiceNo());
		// if(casInvoice){
		// CasInvoice hd
		// }
		// CasInvoice hd=casAction.findCasInvoiceByNo(jTextField.getText());
		if (this.casInvoice == null) {
			List list = casAction.findCasInvoiceByNo(jTextField.getText());
			if (list.size() > 0) {
				JOptionPane.showMessageDialog(DgBuyIntestine.this, "发票号有重复！",
						"提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			this.casInvoice = new CasInvoice();
		}
		casInvoice.setInvoiceNo(jTextField.getText());
		casInvoice.setEmsNo(jComboBox1.getSelectedItem() == null ? ""
				: ((BaseEmsHead) jComboBox1.getSelectedItem()).getEmsNo());
		casInvoice.setCustomer((ScmCoc) jComboBox.getSelectedItem());
		casInvoice.setValidDate(cmbDate.getDate());
		return true;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(744, 537));
		this.setContentPane(getJPanel2());
		this.setTitle("发票新增");

	}

	private void setState() {
//		jTextField.setEditable(jTextField.getText() == null
//				|| jTextField.getText().equals(""));
		jTextField.setEditable(this.state != DataState.BROWSE);
		jComboBox.setEnabled(this.state != DataState.BROWSE);
		if (tableModel != null) {
			if (tableModel.getList().size() > 0) {
				CasInvoiceInfo info = (CasInvoiceInfo) tableModel
						.getObjectByRow(0);
				List list = casAction
						.findInvoiceAndBillCorrespondingByCasInvoiceInfo(
								new Request(CommonVars.getCurrUser()), info);
				if (list != null && list.size() > 0) {
					jComboBox.setEnabled(false);
				}
			}
		}
		// jComboBox1.setEnabled(this.state == DataState.EDIT);
		cmbDate.setEnabled(this.state != DataState.BROWSE);

		// btnSave.setEnabled(this.state != DataState.BROWSE);
		// btnEdit.setEnabled(this.state != DataState.EDIT);

	}

	public void setVisible(boolean f) {
		if (f) {
			showData();
			getBtnEdit().setEnabled(false);
			getBtnSave().setEnabled(true);
			setState();
			List rlist = casAction.findCasInvoiceInfo(new Request(CommonVars
					.getCurrUser()), this.casInvoice, null);
			if (this.casInvoice == null) {
				rlist = new ArrayList();
			}
			if (state == DataState.ADD || state == DataState.EDIT) {
				initFirstTable(rlist);
				getBtnEdit().setEnabled(false);
				getBtnSave().setEnabled(true);
				getBtnDeleBill().setEnabled(true);
				getBtnAddBill().setEnabled(true);
				getJbEdit().setEnabled(true);
				getJbAdd().setEnabled(true);
				getJbDel().setEnabled(true);
			} else {
				initFirstTable(rlist);
				getBtnEdit().setEnabled(false);
				getBtnSave().setEnabled(false);
				getBtnDeleBill().setEnabled(false);
				getBtnAddBill().setEnabled(false);
				getJbEdit().setEnabled(false);
				getJbAdd().setEnabled(false);
				getJbDel().setEnabled(false);
			}

			super.setVisible(f);
		}
	}

	private void initFirstTable(List rlist) {
		tableModel = new JTableListModel(jTable, rlist,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("发票名称", "cuName", 100));
						list.add(addColumn("发票规格", "cuSpec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("数量", "amount", 80));
						list.add(addColumn("单价", "price", 80));
						list.add(addColumn("总金额", "totalMoney", 80));
						list.add(addColumn("税额", "impost", 80));
						list.add(addColumn("价税总额", "allMoney", 80));
						list.add(addColumn("重量", "totalWeight", 80));
						list.add(addColumn("是完全对应", "toBillsAll", 100));
						list.add(addColumn("是否核销", "canceled", 100));
						return list;
					}
				});
		List<JTableListColumn> coms=tableModel.getColumns();
		coms.get(8).setFractionCount(3);
		coms.get(9).setFractionCount(3);
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new TableCheckBoxRender());
		if (tableModel.getList().size() > 0) {
			CasInvoiceInfo info = (CasInvoiceInfo) tableModel
					.getObjectByRow(0);
			List list = casAction
					.findInvoiceAndBillCorrespondingByCasInvoiceInfo(
							new Request(CommonVars.getCurrUser()), info);
			if (list != null && list.size() > 0) {
				jComboBox.setEnabled(false);
			}
		}
	
	}

	private void initFirstTable1(List rlist) {
		tableModel1 = new JTableListModel(jTable1, rlist,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("客户/供应商",
								"casInvoice.customer.name", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("报关名称", "cuName", 100));
						list.add(addColumn("所关规格", "cuSpec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("数量", "amount", 80));
						list.add(addColumn("已对应数量", "toBillNum", 80));
						list.add(addColumn("单价", "price", 80));
						list.add(addColumn("总金额", "totalMoney", 80));
						list.add(addColumn("重量", "totalWeight", 80));
						list.add(addColumn("是否核销", "checkCancel", 100));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(10).setCellRenderer(
				new TableCheckBoxRender());
	}

	private void initTable2(CasInvoiceInfo info) {
		List rlist = new ArrayList();
		if (info != null) {
			rlist = casAction.findInvoiceAndBillCorrespondingByCasInvoiceInfo(
					new Request(CommonVars.getCurrUser()), info);
		}
		tableModel2 = new JTableListModel(jTable2, rlist,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("单据号码","billNo",100));   
						list.add(addColumn("单据数量", "billDetailNum", 100));
						list.add(addColumn("对应数量", "invoiceNum", 100));
						list.add(addColumn("名称", "cusName", 100));
						list.add(addColumn("规格", "cusSpce", 80));
						list.add(addColumn("单位", "cusUnit.name", 80));
						list.add(addColumn("客户", "customer.name", 80));
						list.add(addColumn("发票单价", "invoicePrice", 80));
						list.add(addColumn("对应重量", "invoiceWeight", 100));
						list.add(addColumn("单据重量", "billWeight", 100));
						list.add(addColumn("手册号", "emsNo", 100));
						return list;
					}
				});
	}

	private void initUiComponents() {
		// ScmCoc 初始化供应商
		List list = new ArrayList();
		list = super.getManufacturer();
		this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name", 250);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		List<BaseEmsHead> lists = casAction.findAllEmsHeadH2k(new Request(
				CommonVars.getCurrUser()));
		for (BaseEmsHead data : lists) {
			map.put(data.getEmsNo(), data);
		}

		this.jComboBox1.setModel(new DefaultComboBoxModel(map.values()
				.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox1,
				"", "emsNo", 250);
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"", "emsNo", 0, 150));
		jComboBox1.setSelectedIndex(-1);

	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("发票管理", null, getJPanel4(), null);
			jTabbedPane.addTab("单据对应", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (getJTabbedPane().getSelectedIndex() == 1) {
								List rlist = casAction.findCasInvoiceInfo(
										new Request(CommonVars.getCurrUser()),
										casInvoice, null);
								initFirstTable1(rlist);
								CasInvoiceInfo info = (CasInvoiceInfo) tableModel1
										.getCurrentRow();
								initTable2(info);
							}
						}
					});
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(16, 48, 54, 18));
			jLabel3.setText("手  册  号:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(327, 17, 42, 18));
			jLabel2.setText("供应商:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(327, 48, 42, 18));
			jLabel1.setText("日    期:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 17, 54, 18));
			jLabel.setText("发票号码:");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getCmbDate(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(getBtnSave(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getBtnEdit(), null);
			jPanel.add(getJRadioButton(), null); // Generated
			jPanel.add(getJRadioButton1(), null); // Generated
			jPanel.add(getJRadioButton2(), null); // Generated
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(75, 15, 227, 21));
		}
		return jTextField;
	}

	/**
	 * This method initializes cmbDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCmbDate() {
		if (cmbDate == null) {
			cmbDate = new JCalendarComboBox();
			cmbDate.setBounds(new Rectangle(378, 47, 227, 21));
		}
		return cmbDate;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(378, 15, 227, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(75, 47, 227, 21));
			jComboBox1.setEnabled(false);
		}
		return jComboBox1;
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
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJbAdd());
			jToolBar.add(getJbEdit());
			jToolBar.add(getBtnBro());
			jToolBar.add(getJbDel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jbAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbAdd() {
		if (jbAdd == null) {
			jbAdd = new JButton();
			jbAdd.setText("新增");
			jbAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (getBtnSave().isEnabled()) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"请先保存数据！", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBuyIntestineDetail detail = new DgBuyIntestineDetail();
					detail.state = DataState.ADD;
					detail.head = casInvoice;
					detail.setBh((BaseEmsHead) getJComboBox1()
							.getSelectedItem());
					detail.setVisible(true);
					if (detail.isOk) {
						tableModel.addRow(detail.info);
					}
				}
			});
		}
		return jbAdd;
	}

	/**
	 * This method initializes jbEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setText("修改");
			jbEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"请选择数据!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					CasInvoiceInfo info = (CasInvoiceInfo) tableModel
							.getCurrentRow();
					List list = casAction
							.findInvoiceAndBillCorrespondingByCasInvoiceInfo(
									new Request(CommonVars.getCurrUser()), info);
					if (list != null && list.size() > 0) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"该项发票已产生对应，不能修改!", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBuyIntestineDetail detail = new DgBuyIntestineDetail();
					detail.state = DataState.EDIT;
					detail.head = casInvoice;
					// CasInvoiceInfo info = (CasInvoiceInfo) tableModel
					// .getCurrentRow();
					detail.info = info;
					detail.setBh((BaseEmsHead) getJComboBox1()
							.getSelectedItem());
					detail.setVisible(true);
					if (detail.isOk) {
						tableModel.updateRow(info);
					}
				}

			});
		}
		return jbEdit;
	}

	/**
	 * This method initializes jbDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbDel() {
		if (jbDel == null) {
			jbDel = new JButton();
			jbDel.setText("删除");
			jbDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"请选择数据!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					CasInvoiceInfo info = (CasInvoiceInfo) tableModel
							.getCurrentRow();
					List list = casAction
							.findInvoiceAndBillCorrespondingByCasInvoiceInfo(
									new Request(CommonVars.getCurrUser()), info);
					if (list != null && list.size() > 0) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"该项发票已产生对应，不能删除!", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					casAction.deleteOject(
							new Request(CommonVars.getCurrUser()), info);
					tableModel.deleteRow(info);
				}
			});
		}
		return jbDel;
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
			jPanel3.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel3;
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
			jSplitPane1.setDividerSize(3);
			jSplitPane1.setTopComponent(getJPanel6());
			jSplitPane1.setBottomComponent(getJPanel5());
			jSplitPane1.setDividerLocation(130);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();

			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1.setLayout(f);
			jToolBar1.add(getJbRelationSingle());
			jToolBar1.add(getJbCancel());
		}
		return jToolBar1;
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
			jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					CasInvoiceInfo info = (CasInvoiceInfo) tableModel1
							.getCurrentRow();
					initTable2(info);
				}
			});
		}
		return jTable1;
	}

	/**
	 * This method initializes jbRelationSingle
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbRelationSingle() {
		if (jbRelationSingle == null) {
			jbRelationSingle = new JButton();
			jbRelationSingle.setText("关联单项数据资料");
			jbRelationSingle.setPreferredSize(new Dimension(120, 26));

			jbRelationSingle
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// InvoiceAndBillCorresponding data = new
							// InvoiceAndBillCorresponding();
							// CasInvoiceInfo info = (CasInvoiceInfo)
							// tableModel1
							// .getCurrentRow();
							// data.setInvoiceInfokey(info.getId());
							// casAction.savaOrUpdateObject(new
							// Request(CommonVars
							// .getCurrUser()), data);
						}
					});
		}
		return jbRelationSingle;
	}

	/**
	 * This method initializes jbCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbCancel() {
		if (jbCancel == null) {
			jbCancel = new JButton();
			jbCancel.setText("取消关联");
			jbCancel.setPreferredSize(new Dimension(70, 26));
		}
		return jbCancel;
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
			jPanel4.add(getJSplitPane2(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerLocation(100);
			jSplitPane2.setDividerSize(3);
			jSplitPane2.setTopComponent(getJPanel());
			jSplitPane2.setBottomComponent(getJPanel1());
			jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane2;
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
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					CasInvoiceInfo info = (CasInvoiceInfo) tableModel
							.getCurrentRow();
					if (info.isCanceled() != null && info.isCanceled()) {
						getJbEdit().setEnabled(false);
					} else {
						getJbEdit().setEnabled(true);
					}
				}
			});
		}
		return jTable;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public CasInvoice getCasInvoice() {
		return casInvoice;
	}

	public void setCasInvoice(CasInvoice casInvoice) {
		this.casInvoice = casInvoice;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(653, 39, 58, 23));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!fillData()) {
						return;
					}
					casInvoice = (CasInvoice) casAction.savaOrUpdateObject(
							new Request(CommonVars.getCurrUser()), casInvoice);
					state = DataState.BROWSE;
					setState();
					getBtnEdit().setEnabled(true);
					getBtnSave().setEnabled(false);
					isOk = true;
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(653, 69, 58, 23));
			btnCancel.setText("退出");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnBro
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBro() {
		if (btnBro == null) {
			btnBro = new JButton();
			btnBro.setText("浏览");
			btnBro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"请选择数据", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBuyIntestineDetail detail = new DgBuyIntestineDetail();
					detail.state = DataState.BROWSE;
					detail.info = (CasInvoiceInfo) tableModel.getCurrentRow();
					detail.setVisible(true);
				}
			});
		}
		return btnBro;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(653, 9, 58, 23));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					state = DataState.EDIT;
					setState();
					getBtnEdit().setEnabled(false);
					getBtnSave().setEnabled(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJToolBar2(), BorderLayout.NORTH);
			jPanel5.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar2.setLayout(f);
			jToolBar2.add(getBtnAddBill());
			jToolBar2.add(getBtnDeleBill());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes btnAddBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddBill() {
		if (btnAddBill == null) {
			btnAddBill = new JButton();
			btnAddBill.setText("新增对应");
			btnAddBill.setPreferredSize(new Dimension(70, 26));
			btnAddBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"没选择有发票商品，不能新增对应！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					CasInvoiceInfo invoice = (CasInvoiceInfo) tableModel1
							.getCurrentRow();
					if (invoice.getToBillsAll() != null
							&& invoice.getToBillsAll()) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"发票数量已全部对应完,没有数量可对应", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;// 如果发票数量已全部对应完，则返回
					}
					double invoiceAmount = (invoice.getAmount() == null ? 0
							: invoice.getAmount());
					double invoiceNum = invoiceAmount
							- (invoice.getToBillNum() == null ? 0 : invoice
									.getToBillNum());
					if (invoiceNum <= 0) {
						JOptionPane.showMessageDialog(DgBuyIntestine.this,
								"可对应的数量已大于等于总数量，不能对应！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;//
					}

					List rlist = (List) getBcsTempEmsImg(invoice.getId(),
							invoice.getComplex() == null ? "" : invoice
									.getComplex().getCode(), invoice
									.getCasInvoice().getCustomer());
					if (rlist == null || rlist.size() == 0) {
						return;
					}
					
					Double ptAmount = 0.0;	
					for(int i=0;i<rlist.size();i++){
						BillDetailMateriel b = (BillDetailMateriel)rlist.get(i);
						if(!"1107".equals(b.getBillMaster().getBillType().getCode())){//其它料件退货单
						   ptAmount += (b.getHsAmount() == null?0.0:b.getHsAmount());
						}else{
						   ptAmount -= (b.getHsAmount() == null?0.0:b.getHsAmount());
						}
					}
					
					List<InvoiceAndBillCorresponding> invoiceList = tableModel2.getList();
					for(InvoiceAndBillCorresponding i : invoiceList){
						if(!"1107".equals(i.getTypeCode())){
							ptAmount += (i.getBillDetailNum() == null?0.0:i.getBillDetailNum());
						}
						
					}
					
					double canInvoiceNum = invoiceNum - ptAmount ;
					if (canInvoiceNum < 0) {
						if (JOptionPane.showConfirmDialog(DgBuyIntestine.this,
								"可对应的数量["+invoiceNum+"] < 所有单据的总数量["+ptAmount+"]，是否对应!!!", "提示", 0) != 0) {
							return;
						}						
					}
					
					List glist = casAction.makeCasInvoiceAndBillRalation(
							new Request(CommonVars.getCurrUser()), invoice,
							rlist);
					tableModel2.addRows(glist);
					List slist = casAction.findCasInvoiceInfo(new Request(
							CommonVars.getCurrUser()), casInvoice, null);
					initFirstTable(slist);
					 rlist = casAction.findCasInvoiceInfo(
							new Request(CommonVars.getCurrUser()),
							casInvoice, null);
					initFirstTable1(rlist);
				}
			});
		}
		return btnAddBill;
	}

	public Object getBcsTempEmsImg(final String id, final String name,
			final ScmCoc scmCoc) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();

		list.add(new JTableListColumn("单据号", "billMaster.billNo", 100));
		list.add(new JTableListColumn("供应商", "billMaster.scmCoc.name", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 70));
		list.add(new JTableListColumn("商品名称", "ptName", 100));
		list.add(new JTableListColumn("规格型号", "ptSpec", 100));

		list.add(new JTableListColumn("数量", "ptAmount", 100));
		list.add(new JTableListColumn("对应数量", "customNum", 100));
		list.add(new JTableListColumn("单价", "ptPrice", 100));
		list.add(new JTableListColumn("金额", "money", 100));
		list.add(new JTableListColumn("单位", "ptUnit.name", 100));
		list.add(new JTableListColumn("单据类型", "billMaster.billType.name", 100));
		list.add(new JTableListColumn("单据日期", "billMaster.validDate", 100));
		list.add(new JTableListColumn("供应商", "billMaster.scmCoc.name", 100));

		list.add(new JTableListColumn("报关名称", "hsName", 100));
		list.add(new JTableListColumn("报关规格", "hsSpec", 100));
		list.add(new JTableListColumn("报关单位", "hsUnit.name", 100));
		list.add(new JTableListColumn("折算报关数量", "hsAmount", 100));
		list.add(new JTableListColumn("折算报关单价", "hsPrice", 100));

		list.add(new JTableListColumn("单位折算", "unitConvert", 100));
		// list.add(new JTableListColumn("对应数量", "customNum", 100));
		// list.add(new JTableListColumn("未对应数量", "noCustomNum", 100));
		list.add(new JTableListColumn("手册号", "emsNo", 100));

		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				CasAction casAction = (CasAction) CommonVars
						.getApplicationContext().getBean("casAction");

				return casAction
						.findBillDetailMaterielByNameAndScmCoc(new Request(
								CommonVars.getCurrUser()), id, name, scmCoc);

			}
		};

		dgCommonQuery.setTitle("请选择所要对应的单据！");
		final JTable table =  dgCommonQuery.getJTable();
		final JLabel lbMessage = dgCommonQuery.getLbMessage();
		
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
//						e.g
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel)table
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								List<BillDetailMateriel> list = 
									tableModel.getCurrentRows();
								String msg = "";
								Double ptAmount = 0.0;
								Double hsAmount = 0.0;
								for(BillDetailMateriel b : list){
									ptAmount += (b.getPtAmount() == null?0.0:b.getPtAmount());									
									hsAmount += (b.getHsAmount() == null?0.0:b.getHsAmount());									
								}
								msg = "工厂数量总计:("+ptAmount+")" ;
								msg += " 报关数量总计:("+hsAmount+")" ;
								lbMessage.setText(msg);
							}
						} catch (Exception cx) {

						}
					}
				});
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * This method initializes btnDeleBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleBill() {
		if (btnDeleBill == null) {
			btnDeleBill = new JButton();
			btnDeleBill.setText("删除对应");
			btnDeleBill.setPreferredSize(new Dimension(70, 26));
			btnDeleBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List obj = tableModel2.getCurrentRows();
					if (obj == null || obj.size() == 0) {
						JOptionPane.showConfirmDialog(DgBuyIntestine.this,
								"请选择数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					casAction.deleteInvoiceAndBillCorresponding(new Request(
							CommonVars.getCurrUser()), obj);
					CasInvoiceInfo info = (CasInvoiceInfo) tableModel1
							.getCurrentRow();
					initTable2(info);
					
					List slist = casAction.findCasInvoiceInfo(new Request(
							CommonVars.getCurrUser()), casInvoice, null);
					initFirstTable(slist);
					List rlist = casAction.findCasInvoiceInfo(
							new Request(CommonVars.getCurrUser()),
							casInvoice, null);
					initFirstTable1(rlist);
					// tableModel2.deleteRow(obj);
					//					

				}
			});
		}
		return btnDeleBill;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable2());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			try {
				jRadioButton = new JRadioButton();
				jRadioButton.setBounds(new Rectangle(399, 74, 72, 21)); // Generated
				jRadioButton.setText("所有"); // Generated
				jRadioButton.setSelected(true);
				jRadioButton
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								showdifData();
							}
						});
			} catch (java.lang.Throwable e) {
			}
		}
		return jRadioButton;
	}

	private void showdifData() {
		if (getJRadioButton().isSelected()) {
			List rlist = casAction.findCasInvoiceInfo(new Request(CommonVars
					.getCurrUser()), casInvoice, null);
			initFirstTable(rlist);
		} else if (getJRadioButton1().isSelected()) {
			List rlist = casAction.findCasInvoiceInfo(new Request(CommonVars
					.getCurrUser()), casInvoice, true);
			initFirstTable(rlist);
		} else if (getJRadioButton2().isSelected()) {
			List rlist = casAction.findCasInvoiceInfo(new Request(CommonVars
					.getCurrUser()), casInvoice, false);
			initFirstTable(rlist);
		}
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			try {
				jRadioButton1 = new JRadioButton();
				jRadioButton1.setBounds(new Rectangle(471, 74, 72, 21)); // Generated
				jRadioButton1.setText("已核销"); // Generated
				jRadioButton1
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								showdifData();
							}
						});
			} catch (java.lang.Throwable e) {
			}
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			try {
				jRadioButton2 = new JRadioButton();
				jRadioButton2.setBounds(new Rectangle(551, 74, 72, 21)); // Generated
				jRadioButton2.setText("未核销"); // Generated
				jRadioButton2
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								showdifData();
							}
						});
			} catch (java.lang.Throwable e) {
			}
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
			buttonGroup.add(getJRadioButton2());
		}
		return buttonGroup;
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

} // @jve:decl-index=0:visual-constraint="109,24"
