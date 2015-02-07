package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBcusImpexpRequestbillSplit extends JDialogBase {

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;

	private JLabel lbHeadText = null;

	private JLabel jLabel19 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel jTableModel = null;

	private String barbarismBillNo = "";

	private EncAction encAction = null;

	private ImpExpRequestBill impExpRequestBill = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JList jList = null;

	private Set set = new HashSet();

	private List billNolist = new ArrayList();

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="915,265"

	private List dataSource = null;

	private boolean isSaved = false;

	public boolean isOk = false;

	/**
	 * This method initializes
	 * 
	 */
	public DgBcusImpexpRequestbillSplit() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(900, 569));
		this.setContentPane(getJPanel());
		this.setContentPane(getJPanel());
		getButtonGroup();
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
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.add(getJPanel11(), BorderLayout.NORTH);
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
			jSplitPane.setDividerLocation(740);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerSize(2);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.RIGHT);
			fl.setHgap(10);
			fl.setVgap(3);
			jPanel1.setLayout(fl);
			jPanel1.add(getJRadioButton1(), null);
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("保存");
			jButton.setPreferredSize(new Dimension(58, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getJList().getModel().getSize() == 1
							&& getJList().getModel().getElementAt(0).toString()
									.equals(impExpRequestBill.getBillNo())) {
						JOptionPane.showMessageDialog(
								DgBcusImpexpRequestbillSplit.this,
								"数据没有做任何的拆分！", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					isSaved = true;
					if (!saveSplitedBills()) {
						JOptionPane.showMessageDialog(
								DgBcusImpexpRequestbillSplit.this,
								"数据保存出错，拆分数据将不会生效！", "提示!",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(
								DgBcusImpexpRequestbillSplit.this, "数据保存成功！",
								"提示!", JOptionPane.INFORMATION_MESSAGE);
					}
					jButton.setEnabled(false);
					isOk = true;
				}
			});
		}
		return jButton;
	}

	private boolean saveSplitedBills() {
		return encAction.saveSplitedImpExpRequestBill(new Request(CommonVars
				.getCurrUser()), impExpRequestBill, set, jTableModel.getList());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("退出");
			jButton1.setPreferredSize(new Dimension(58, 25));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!isSaved) {
						if (JOptionPane.showConfirmDialog(
								DgBcusImpexpRequestbillSplit.this,
								"申请单拆分没有保存，将不会生效，你确定要退出吗？", "提示！",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							dispose();
						} else {
							return;
						}
					}
					dispose();
				}
			});
		}
		return jButton1;
	}

	public void setVisible(boolean f) {
		if (f) {
			barbarismBillNo = impExpRequestBill.getBillNo();
			lbHeadText.setText("被拆分的申请单号：" + barbarismBillNo);
			dataSource = this.encAction.findImpExpCommodityInfo(new Request(
					CommonVars.getCurrUser()), impExpRequestBill.getId());
			billNolist = this.encAction
					.findBillNoOfImpExpRequestBill(new Request(CommonVars
							.getCurrUser()));
			initTable(changeToTemp(dataSource));
			getJList().setListData(new Object[] { barbarismBillNo });
			super.setVisible(f);
		}
	}

	public JComboBox getStrCb() {
		JComboBox cb = new JComboBox();
		cb.addItem(barbarismBillNo);
		int i = 0;
		int m = 0;
		while (i < 10) {
			m++;
			String item = barbarismBillNo + "-" + m;
			if (billNolist.contains(item)) {
				continue;
			}
			cb.addItem(item);
			i++;
		}
		cb.setUI(new CustomBaseComboBoxUI(200));
		return cb;
	}

	private List changeToTemp(List list) {
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo impExpRequestBill = (ImpExpCommodityInfo) list
					.get(i);
			impExpRequestBill.setId(null);
			TempImpExpCommodityInfo temp = new TempImpExpCommodityInfo();
			temp.setImpExpCommodityInfo(impExpRequestBill);
			temp.setInImpExpRequestBill(impExpRequestBill
					.getImpExpRequestBill().getBillNo());
			impExpRequestBill.setImpExpRequestBill(null);
			String key = (impExpRequestBill.getMateriel().getPtNo())
					+ "/"
					+ (impExpRequestBill.getCountry() == null ? ""
							: impExpRequestBill.getCountry().getCode())
					+ "/"
					+ (temp.getInImpExpRequestBill() == null ? "" : temp
							.getInImpExpRequestBill());
			if (map.get(key) != null) {
				TempImpExpCommodityInfo qtemp = (TempImpExpCommodityInfo) map
						.get(key);
				qtemp
						.getImpExpCommodityInfo()
						.setQuantity(
								(impExpRequestBill.getQuantity() == null ? 0.0
										: impExpRequestBill.getQuantity())
										+ (qtemp.getImpExpCommodityInfo()
												.getQuantity() == null ? 0.0
												: qtemp
														.getImpExpCommodityInfo()
														.getQuantity()));
				qtemp.getImpExpCommodityInfo().setAmountPrice(
						qtemp.getImpExpCommodityInfo().getQuantity()
								* (qtemp.getImpExpCommodityInfo()
										.getUnitPrice() == null ? 0.0 : qtemp
										.getImpExpCommodityInfo()
										.getUnitPrice()));

			} else {
				map.put(key, temp);
			}

		}
		if (map.keySet().size() != list.size()) {
			JOptionPane.showMessageDialog(DgBcusImpexpRequestbillSplit.this,
					"该申请单中有重复表体(即料号，原产国相同),已做了数量合并！", "提示！", 1);
		}
		List relist = new ArrayList(map.values());
		Collections.sort(relist);
		return relist;
	}

	private void uniteBills() {
		Map map = new HashMap();
		List datalist = jTableModel.getList();
		TempImpExpCommodityInfo obj = (TempImpExpCommodityInfo) jTableModel
				.getCurrentRow();
		for (int i = 0; i < datalist.size(); i++) {
			TempImpExpCommodityInfo tinfo = (TempImpExpCommodityInfo) datalist
					.get(i);

			ImpExpCommodityInfo impExpRequestBill = tinfo
					.getImpExpCommodityInfo();
			double qua = impExpRequestBill.getQuantity() == null ? 0.0
					: impExpRequestBill.getQuantity();
			String key = (impExpRequestBill.getMateriel().getPtNo())
					+ "/"
					+ (impExpRequestBill.getCountry() == null ? ""
							: impExpRequestBill.getCountry().getCode())
					+ "/"
					+ (tinfo.getInImpExpRequestBill() == null ? "" : tinfo
							.getInImpExpRequestBill());

			if (map.get(key) == null) {
				map.put(key, tinfo);
			} else {
				TempImpExpCommodityInfo info = (TempImpExpCommodityInfo) map
						.get(key);
				ImpExpCommodityInfo iof = info.getImpExpCommodityInfo();
				double qu = iof.getQuantity() == null ? 0.0 : iof.getQuantity();
				double price = iof.getUnitPrice();
				iof.setQuantity(qu + qua);
				iof.setAmountPrice((qu + qua) * price);
			}
		}
		jTableModel.deleteRows(datalist);
		List list = new ArrayList(map.values());
		Collections.sort(list);
		jTableModel.addRows(list);

		ImpExpCommodityInfo ib = obj.getImpExpCommodityInfo();
		String key = (ib.getMateriel().getPtNo())
				+ "/"
				+ (ib.getCountry() == null ? "" : ib.getCountry().getCode())
				+ "/"
				+ (obj.getInImpExpRequestBill() == null ? "" : obj
						.getInImpExpRequestBill());

		obj = (TempImpExpCommodityInfo) map.get(key);
		if (obj != null) {
			jTableModel.setTableSelectedRow(obj);
		} else {
			jTableModel.setTableFirstSelectRow();
		}
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			lbHeadText = new JLabel();
			lbHeadText.setFont(new Font("Dialog", Font.BOLD, 20));
			lbHeadText.setText("");
			lbHeadText.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBackground(Color.white);
			jPanel11.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel11.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel11.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel11.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel11;
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
			jTable.setRowHeight(22);
		}
		return jTable;
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = null;
		jTableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter = new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("所在申请单", "inImpExpRequestBill",
										100));
						list.add(addColumn("料号",
								"impExpCommodityInfo.materiel.ptNo", 100));
						list
								.add(addColumn(
										"名称",
										"impExpCommodityInfo.materiel.factoryName",
										120));
						list
								.add(addColumn(
										"规格型号",
										"impExpCommodityInfo.materiel.factorySpec",
										120));
						list.add(addColumn("原产国",
								"impExpCommodityInfo.country.name", 60));

						list
								.add(addColumn(
										"工厂单位",
										"impExpCommodityInfo.materiel.calUnit.name",
										50));

						list.add(addColumn("单价",
								"impExpCommodityInfo.unitPrice", 60));
						list.add(addColumn("数量",
								"impExpCommodityInfo.quantity", 60));
						list
								.add(addColumn(
										"已转报关单",
										"impExpCommodityInfo.isTransferCustomsBill",
										60));

						return list;
					}
				});
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new MultiRenderer());
		jTableListModelAdapter.setEditableColumn(1);
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new JComboBoxTableCellEditor(getStrCb(), jTable) {
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new CheckBoxTableCellRenderer());
		jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTableModel.setAllowSetValue(true);
		if (list.size() > 0) {
			jTableModel.setTableFirstSelectRow();
		}
	}

	private void splistValue(String str, TempImpExpCommodityInfo temp,
			String newitem, String olditem) {
		if (!getJRadioButton().isSelected()) {
			uniteBills();
			return;
		}
		Double dous = Double.parseDouble(str);
		ImpExpCommodityInfo info = new ImpExpCommodityInfo();
		ImpExpCommodityInfo oldinfo = temp.getImpExpCommodityInfo();
		TempImpExpCommodityInfo newtemp = new TempImpExpCommodityInfo();
		double quantity = oldinfo.getQuantity() == null ? 0.0 : oldinfo
				.getQuantity();
		double price = oldinfo.getUnitPrice() == null ? 0.0 : oldinfo
				.getUnitPrice();
		if (dous == quantity) {
			temp.setInImpExpRequestBill(newitem);
		} else {
			try {
				// -------------------------------------------
				PropertyUtils.copyProperties(info, oldinfo);
				info.setQuantity(dous);
				info.setAmountPrice(dous * price);
				oldinfo.setQuantity(quantity - dous);
				oldinfo.setAmountPrice(price * (quantity - dous));
				// -------------------------------------------
				newtemp.setImpExpCommodityInfo(info);
				temp.setInImpExpRequestBill(olditem);
				newtemp.setInImpExpRequestBill(newitem);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			jTableModel.addRow(newtemp);
		}
		jTableModel.updateRow(temp);
		uniteBills();
	}

	private String checValue(TempImpExpCommodityInfo temp) {
		if (!getJRadioButton().isSelected()) {
			return "";
		}
		String strs = null;
		Double dou = temp.getImpExpCommodityInfo().getQuantity();
		strs = JOptionPane.showInputDialog(DgBcusImpexpRequestbillSplit.this,
				"请你输入要拆分的数量!", "提示！", JOptionPane.INFORMATION_MESSAGE);
		if (strs == null) {
			return null;
		}
		if (!checkInputDate(strs, dou)) {
			return null;
		}
		return strs;
	}

	private void reFreshTheNewBills(JTable table) {
		set.clear();
		List dlist = ((JTableListModel) table.getModel()).getList();
		for (int i = 0; i < dlist.size(); i++) {
			TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) dlist
					.get(i);
			if (temp.getInImpExpRequestBill() != null) {
				set.add(temp.getInImpExpRequestBill());
			}
		}
		getJList().setListData(new Vector(set));
		getJList().repaint();
	}

	private boolean checkInputDate(String strs, Double old) {
		double amount = 0;
		if (old == null) {
			JOptionPane.showMessageDialog(DgBcusImpexpRequestbillSplit.this,
					"被拆分数量为空!", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		try {
			amount = Double.parseDouble(strs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(DgBcusImpexpRequestbillSplit.this,
					"输入数量不合法!", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (amount < 0 || amount > old) {
			JOptionPane.showMessageDialog(DgBcusImpexpRequestbillSplit.this,
					"输入数量大于被拆分数量或小于零!", "提示！", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	private void removeListners(JComboBox comboBox) {
		ActionListener[] acs = comboBox.getActionListeners();
		for (int i = 0; i < acs.length; i++) {
			ActionListener ac = acs[i];
			comboBox.removeActionListener(ac);
		}
		ItemListener[] tems = comboBox.getItemListeners();
		for (int i = 0; i < tems.length; i++) {
			ItemListener ac = tems[i];
			comboBox.removeItemListener(ac);
		}
	}

	/**
	 * 编辑列
	 */
	class JComboBoxTableCellEditor extends DefaultCellEditor {
		private JComboBox comboBox = null;

		private JTable table = null;

		private boolean isChangbeValue = false;

		private boolean isEditer = false;

		private boolean isRevertValue = false;

		private Object oldValue = null;

		// private Point pot = null;

		public JComboBoxTableCellEditor(JComboBox cb, JTable table) {
			super(cb);
			this.comboBox = cb;
			this.table = table;
			removeListners(comboBox);
			delegate = new EditorDelegate() {
				@Override
				public void setValue(Object value) {
					comboBox.setSelectedItem(value);
				}

				@Override
				public Object getCellEditorValue() {
					return comboBox.getSelectedItem();
				}

				@Override
				public boolean shouldSelectCell(EventObject anEvent) {
					if (anEvent instanceof MouseEvent) {
						MouseEvent e = (MouseEvent) anEvent;
						return e.getID() != MouseEvent.MOUSE_DRAGGED;
					}
					return true;
				}

				@Override
				public boolean stopCellEditing() {

					if (!isEditer) {
						return true;
					}
					if (comboBox.isEditable()) {
						comboBox.actionPerformed(new ActionEvent(
								JComboBoxTableCellEditor.this, 0, ""));
					}
					return super.stopCellEditing();
				}

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() != ItemEvent.SELECTED) {
						return;
					}
					if (isRevertValue) {
						isRevertValue = false;
						return;
					}
					TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) ((JTableListModel) JComboBoxTableCellEditor.this.table
							.getModel()).getCurrentRow();
					if (isChangbeValue) {
						String str = checValue(temp);
						isEditer = (str != null);
						if (isEditer) {
							JComboBoxTableCellEditor.this.stopCellEditing();
							splistValue(str, temp, e.getItem().toString(),
									oldValue.toString());
							oldValue = e.getItem();
							reFreshTheNewBills(jTable);
						} else {
							isRevertValue = true;
							comboBox.setSelectedItem(oldValue);
						}
						isEditer = false;
					}
					if (!isChangbeValue) {
						isChangbeValue = true;
					}
				}
			};
			comboBox.addItemListener(delegate);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			isChangbeValue = false;
			if (oldValue == null) {
				oldValue = value;
			}
			if (oldValue == value) {
				ItemListener[] itms = comboBox.getItemListeners();
				for (int i = 0; i < itms.length; i++) {
					if (itms[i] instanceof EditorDelegate) {
						itms[i].itemStateChanged(new ItemEvent(comboBox,
								ItemEvent.ITEM_STATE_CHANGED, value,
								ItemEvent.SELECTED));
						break;
					}
				}
			} else {
				oldValue = value;
			}
			delegate.setValue(value);
			TempImpExpCommodityInfo info = (TempImpExpCommodityInfo) ((JTableListModel) table
					.getModel()).getDataByRow(row);
			if ((info.getImpExpCommodityInfo().getIsTransferCustomsBill() != null)
					&& info.getImpExpCommodityInfo().getIsTransferCustomsBill()) {
				editorComponent.setEnabled(false);
			} else {
				editorComponent.setEnabled(true);
			}
			return editorComponent;
		}
	}

	private class CheckBoxTableCellRenderer extends DefaultTableCellRenderer {
		private JComboBox cb = new JComboBox();;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			Color fg = null;
			Color bg = null;
			JTable.DropLocation dropLocation = table.getDropLocation();
			if (dropLocation != null && !dropLocation.isInsertRow()
					&& !dropLocation.isInsertColumn()
					&& dropLocation.getRow() == row
					&& dropLocation.getColumn() == column) {

				fg = UIManager.getColor("Table.dropCellForeground");
				bg = UIManager.getColor("Table.dropCellBackground");

				isSelected = true;
			}
			if (isSelected) {
				cb.setForeground(fg == null ? table.getSelectionForeground()
						: fg);
				cb.setBackground(bg == null ? table.getSelectionBackground()
						: bg);

			} else {
				cb.setForeground(table.getForeground());
				cb.setBackground(table.getBackground());
			}
			cb.setFont(table.getFont());

			if (hasFocus) {
				Border border = null;
				if (isSelected) {
					border = UIManager
							.getBorder("Table.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager
							.getBorder("Table.focusCellHighlightBorder");
				}
				cb.setBorder(border);

				if (!isSelected && table.isCellEditable(row, column)) {
					Color col;
					col = UIManager.getColor("Table.focusCellForeground");
					if (col != null) {
						cb.setForeground(col);
					}
					col = UIManager.getColor("Table.focusCellBackground");
					if (col != null) {
						cb.setBackground(col);
					}
				}
			} else {
				cb.setBorder(new EmptyBorder(1, 1, 1, 1));
			}
			if (value instanceof String) {
				cb.removeAllItems();
				cb.addItem(value == null ? "" : value);
				cb.setSelectedItem(value);
			}
			return cb;
		}

	}

	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("拆分数量");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("拆分单体");
			jRadioButton1.setSelected(true);
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setText("所有申请单号:");
			jLabel.setForeground(Color.BLUE);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(jLabel, BorderLayout.NORTH);
			jPanel2.add(getJList(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
		}
		return jList;
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
		}
		return buttonGroup;
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
} // @jve:decl-index=0:visual-constraint="123,11"
