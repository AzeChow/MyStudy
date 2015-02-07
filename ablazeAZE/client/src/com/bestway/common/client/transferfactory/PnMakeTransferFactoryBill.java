/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class PnMakeTransferFactoryBill extends PnCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PnMakeTransferFactoryBill pnMakeCustomsEnvelopBill2 = null;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private List billMasterList = null;

	private boolean isImportGoods = false;

	private JLabel jLabel;

	private ScmCoc scmCoc = null;

	private CasAction casAction = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbEnvelopCode = null;
	
	private JComboBox cbbCustomers = null;

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;
	
	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel22;
	/**
	 * This is the default constructor
	 */
	public PnMakeTransferFactoryBill() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUIComponents();
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel1 = new JLabel();
		jLabel1.setText("关封号码");
		jLabel1.setBounds(new Rectangle(78, 36, 68, 25));
		jLabel = new JLabel();
		this.setLayout(new BorderLayout());
		this.setSize(546, 288);
		this.add(getJSplitPane(), BorderLayout.CENTER);
		jLabel.setText("<html><body>可供选择的<br>海关单据</body></html>");
		jLabel.setBounds(new Rectangle(9, 9, 72, 79));
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeTransferFactoryBill getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeTransferFactoryBill pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getBillMasterList() {
		return billMasterList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setBillMasterList(List parentList) {
		this.billMasterList = parentList;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
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
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setBounds(new Rectangle(460, 9, 60, 25));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setBounds(new Rectangle(460, 36, 60, 25));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		List list = new ArrayList();
		String envelopNo = null;
		if (this.cbbEnvelopCode.getSelectedItem() != null) {
			envelopNo = this.cbbEnvelopCode.getSelectedItem().toString().trim();
		}
		if (isImportGoods == true) {
			list = this.casAction
					.findTempBillMasterIsAvailabilityToTFBByTfi(
					new Request(CommonVars.getCurrUser()), cbbCustomers
							.getSelectedIndex() > -1 ? ((ScmCoc) cbbCustomers
							.getSelectedItem()).getId() : null, envelopNo,
					getCbbBeginDate().getDate(), getCbbEndDate().getDate());
		} else {
			list = this.casAction.findTempBillMasterIsAvailabilityToTFBByTfe(
					new Request(CommonVars.getCurrUser()), cbbCustomers
							.getSelectedIndex() > -1 ? ((ScmCoc) cbbCustomers
							.getSelectedItem()).getId() : null, envelopNo,
					getCbbBeginDate().getDate(), getCbbEndDate().getDate());
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("类别", "billMaster.billType.name", 100));
				list.add(addColumn("单据号", "billMaster.billNo", 100));
				//list.add(addColumn("仓库", "wareSet.name", 80));
				list.add(addColumn("对应报关单号", "billMaster.customNo", 100));
				list.add(addColumn("关封号", "billMaster.envelopNo", 100));
				list.add(addColumn("有效", "billMaster.isValid", 50));
				list.add(addColumn("生效日期", "billMaster.validDate", 80));
				list.add(addColumn("是否记帐", "billMaster.keepAccounts", 50));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
	}

	/**
	 * 编辑列
	 */

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获取选中的关封号
	 */
	public String getSelectedEnvelopCode() {
		if (this.cbbEnvelopCode.getSelectedIndex() >= 0) {
			return this.cbbEnvelopCode.getSelectedItem().toString();
		}
		return "";
	}

	/**
	 * 获取选中的手册/账册号
	 */
	public String getSelectedEmsNo() {
		if (this.cbbEmsNo.getSelectedIndex() >= 0) {
			return this.cbbEmsNo.getSelectedItem().toString().trim();
		}
		return "";
	}

	/**
	 * 获得选中的海关商品单据
	 */
	public List getSelectedMasterList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBillMaster) {
				TempBillMaster temp = (TempBillMaster) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	/**
	 * @return Returns the scmCoc.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * @param scmCoc
	 *            The scmCoc to set.
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEnvelopCode() {
		if (cbbEnvelopCode == null) {
			cbbEnvelopCode = new JComboBox();
			cbbEnvelopCode.setBounds(new Rectangle(150, 36, 135, 25));
			cbbEnvelopCode.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						String envelopNo = cbbEnvelopCode.getSelectedItem()
								.toString().trim();
						List list = transferFactoryManageAction
								.findCustomsEnvelopBillEmsNo(new Request(
										CommonVars.getCurrUser(), true),
										envelopNo);
						cbbEmsNo.removeAllItems();
						for (int i = 0; i < list.size(); i++) {
							cbbEmsNo.addItem(list.get(i).toString());
						}
						if (cbbEmsNo.getItemCount() > 0) {
							cbbEmsNo.setSelectedIndex(0);
						}
						initTable();
					}
				}
			});
		}
		return cbbEnvelopCode;
	}

	private void initUIComponents() {
		List list = transferFactoryManageAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser(), true),
				this.isImportGoods, true);
		cbbEnvelopCode.removeAllItems();
		if(list != null && list.size() > 0) {
			String[] names = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				CustomsEnvelopBill envelopBill = (CustomsEnvelopBill) list.get(i);
				names[i] = envelopBill.getCustomsEnvelopBillNo();
			}
			cbbEnvelopCode.setModel(new DefaultComboBoxModel(names));
			cbbEnvelopCode.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbEnvelopCode);
			cbbEnvelopCode.setSelectedIndex(-1);
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(290, 36, 56, 25));
			jLabel4.setText("结束时间");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(290, 9, 56, 25));
			jLabel3.setText("开始时间");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(78, 9, 68, 25));
			jLabel2.setText("客户");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEnvelopCode(), null);
			jPanel.add(getCbbCustomers(), null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jLabel22 = new JLabel();
			jLabel22.setText("手册/帐册号");
			jLabel22.setBounds(new Rectangle(78, 62, 69, 25));
			jPanel.add(jLabel22,null);
			jPanel.add(getCbbEmsNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(95);
		}
		return jSplitPane;
	}

	private JComboBox getCbbCustomers() {
		if (cbbCustomers == null) {
			cbbCustomers = new JComboBox();
			cbbCustomers.setBounds(new Rectangle(150, 9, 135, 25));
			cbbCustomers.setModel(new DefaultComboBoxModel(super.getManufacturer().toArray()));
			cbbCustomers.setRenderer(CustomBaseRender.getInstance().getRender(
					"code", "name", 100, 170));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbCustomers, "code", "name", 270);
			cbbCustomers.addItem(scmCoc != null?scmCoc.getName() : "");
		}
		return cbbCustomers;
	}
	
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(350, 9, 90, 25);
			cbbBeginDate.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {
					initTable();
				}
			});
		}
		return cbbBeginDate;
	}
	
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(350, 36, 90, 25);
			cbbEndDate.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {
					initTable();
				}
			});
		}
		return cbbEndDate;
	}
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setSize(new Dimension(135, 25));
			cbbEmsNo.setLocation(new Point(150, 63));
		}
		return cbbEmsNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
