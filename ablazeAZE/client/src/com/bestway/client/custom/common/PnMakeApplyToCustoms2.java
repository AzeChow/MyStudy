/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class PnMakeApplyToCustoms2 extends JPanelBase {

	private int impExpType = -1;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private EncAction encAction = null;

	private boolean isexe = false;

	private JComboBox jComboBox = null;

	private List list = null;

	private JButton jButton = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginAvailabilityDate = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndAvailabilityDate = null;

	private JButton btnSearch = null;

	private JLabel jLabel3 = null;

	private JTextField tfBillNo = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeApplyToCustoms2() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			cbbBeginAvailabilityDate.setDate(new Date());
			Calendar cd = Calendar.getInstance();
			cd.setTime(cbbBeginAvailabilityDate.getDate());
			cd.add(Calendar.DATE, 7);
			cbbEndAvailabilityDate.setDate(cd.getTime());
			list = this.encAction.findTempImpExpRequestBillByImpExpTypeToATC(
					new Request(CommonVars.getCurrUser()),
					cbbBeginAvailabilityDate.getDate(), cbbEndAvailabilityDate
							.getDate(),null, this.impExpType);
			initTable(list);
			isexe = false;
			initCombox(list);
		}
		super.setVisible(isFlag);
		isexe = true;
	}

	// 初始化Combox
	public void initCombox(List list) {
		this.jComboBox.removeAllItems();
		for (int i = 0; i < list.size(); i++) {
			TempImpExpRequestBill temp = (TempImpExpRequestBill) list.get(i);
			if (temp.getImpExpRequestBill() == null) {
				continue;
			}
			this.jComboBox.addItem(temp.getImpExpRequestBill().getBillNo());
		}
		this.jComboBox.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(750, 248);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
	}

	/**
	 * @return Returns the impExpType.
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * @param impExpType
	 *            The impExpType to set.
	 */
	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					List<TempImpExpRequestBill> list = tableModel.getCurrentRows();
					Point p =   jTable.getMousePosition();
					int column = jTable.columnAtPoint(p);
					for (TempImpExpRequestBill item : list) {
						if(column!=1){//lyh 如果鼠标选择的第一列复选框将不执行表格拖拉事件
							item.setIsSelected(!item.getIsSelected());
						}
					}
				}
			});
		}
		return jTable;
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
			btnSelectAll.setPreferredSize(new Dimension(60, 25));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PnMakeApplyToCustoms2.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					PnMakeApplyToCustoms2.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));

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
			btnNotSelectAll.setPreferredSize(new Dimension(60, 25));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							PnMakeApplyToCustoms2.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							selectAll(false);
							PnMakeApplyToCustoms2.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return btnNotSelectAll;
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
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 60));
				list.add(addColumn("车次编号", "impExpRequestBill.catNo", 100));
				list.add(addColumn("单据号", "impExpRequestBill.billNo", 100));
				list.add(addColumn("生效日期",
						"impExpRequestBill.beginAvailability", 80));
				list
						.add(addColumn("有效",
								"impExpRequestBill.isAvailability", 50));
				list.add(addColumn("已转报关清单", "impExpRequestBill.isCustomsBill",
						80));
				list.add(addColumn("项目个数", "impExpRequestBill.itemCount", 60));
				list.add(addColumn("仓库名称", "impExpRequestBill.wareSet.name",
						100));
				list.add(addColumn("客户/供应商名称", "impExpRequestBill.scmCoc.name",
						150));
				list.add(addColumn("运输工具", "impExpRequestBill.conveyance", 60));
				list.add(addColumn("录入日期", "impExpRequestBill.imputDate", 80));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new MultiRenderer());
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
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
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

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
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill temp = (TempImpExpRequestBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 获得选中关封申请单中的信息
	 */
	public List getParentList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				ImpExpRequestBill impExpRequestBill = t.getImpExpRequestBill();
				System.out.println("billNo=" + impExpRequestBill.getBillNo());
				if (impExpRequestBill != null) {
					String memos = impExpRequestBill.getMemos();
					if (memos != null) {
						System.out.println("memos=" + memos);
						impExpRequestBill.setMemos(getLimitLengthString(memos,
								210));
						System.out.println("impExpRequestBill.getMemos();="
								+ impExpRequestBill.getMemos());
						
					}
				}
				if (t.getIsSelected().booleanValue() == true) {
					newList.add(t);
				}
			}
		}
		return newList;
	}

	/**
	 * 按长度截取字符，考虑汉字不被截断
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public String getLimitLengthString(String str, int len) {
		try {
			int counterOfDoubleByte = 0;
			System.out.println("in Memos=" + str);
			byte[] b = str.getBytes("GBK");
			System.out.println("in Memos-----------");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0)
					counterOfDoubleByte++;
			}
			if (counterOfDoubleByte % 2 == 0)
				return new String(b, 0, len, "GBK");
			else
				return new String(b, 0, len - 1, "GBK");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEditable(true);
			jComboBox.setVisible(false);
			jComboBox.setPreferredSize(new Dimension(110, 27));
			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (jComboBox.getSelectedItem() == null
							|| jComboBox.getSelectedItem().equals("")
							|| isexe == false) {
						return;
					}
					String billNo = (String) jComboBox.getSelectedItem();
					list = encAction
							.findTempImpExpRequestBillByImpExpTypeToATC(
									new Request(CommonVars.getCurrUser()),
									impExpType);
					for (int i = list.size() - 1; i >= 0; i--) {
						TempImpExpRequestBill temp = (TempImpExpRequestBill) list
								.get(i);
						if (temp.getImpExpRequestBill() != null
								&& !temp.getImpExpRequestBill().getBillNo()
										.equals(billNo)) {
							temp.setIsSelected(new Boolean(false));
							list.remove(i);
						} else if (temp.getImpExpRequestBill() != null
								&& temp.getImpExpRequestBill().getBillNo()
										.equals(billNo)) {
							temp.setIsSelected(new Boolean(true));
						}
					}
					initTable(list);
				}

			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("显示全部");
			jButton.setPreferredSize(new Dimension(88, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jComboBox.setSelectedIndex(-1);
					list = encAction
							.findTempImpExpRequestBillByImpExpTypeToATC(
									new Request(CommonVars.getCurrUser()),
									impExpType);

					initTable(list);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setText(" 单据号 ");
			jLabel2 = new JLabel();
			jLabel2.setText("至");
			jLabel = new JLabel();
			jLabel.setText("生效日期 ");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(1);
			jLabel1 = new JLabel();
			jLabel1.setVisible(false);
			jLabel1.setText("\u53ef\u4f9b\u9009\u62e9\u7684\u5355\u636e");
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginAvailabilityDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbEndAvailabilityDate(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfBillNo(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbBeginAvailabilityDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginAvailabilityDate() {
		if (cbbBeginAvailabilityDate == null) {
			cbbBeginAvailabilityDate = new JCalendarComboBox();
			cbbBeginAvailabilityDate.setPreferredSize(new Dimension(90, 22));
		}
		return cbbBeginAvailabilityDate;
	}

	/**
	 * This method initializes cbbEndAvailabilityDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndAvailabilityDate() {
		if (cbbEndAvailabilityDate == null) {
			cbbEndAvailabilityDate = new JCalendarComboBox();
			cbbEndAvailabilityDate.setPreferredSize(new Dimension(90, 22));
		}
		return cbbEndAvailabilityDate;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setPreferredSize(new Dimension(60, 25));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					list = encAction.findTempImpExpRequestBillByImpExpTypeToATC(
							new Request(CommonVars.getCurrUser()),
							cbbBeginAvailabilityDate.getDate(), cbbEndAvailabilityDate
									.getDate(),tfBillNo.getText().trim(), impExpType);
					initTable(list);
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes tfBillNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setPreferredSize(new Dimension(110, 22));
		}
		return tfBillNo;
	}
}
