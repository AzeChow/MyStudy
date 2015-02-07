package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.time.DateFormatUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Font;

public class FmUseFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel usedTableModel = null;

	private FecavAction fecavAction = null;

	private JScrollPane spInner = null;

	private JTable tbUsed = null;

	private JButton btnEdit = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JLabel lbCount = null;

	private Date editBeforeDate;

	private JButton btnSave = null;

	private JButton btnUpdateCustomsDeclarationCode = null;

	private JButton btnPrint1 = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="662,128"

	private JMenuItem jmOverPrint = null; // @jve:decl-index=0:visual-constraint="753,172"

	private JMenuItem jmNotOverPrint = null; // @jve:decl-index=0:visual-constraint="782,156"

	private JButton btnAdd = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmUseFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initDate();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(553, 365));
		this.setContentPane(getJContentPane());
		this.setTitle("核销单使用");
		// this
		// .addInternalFrameListener(new
		// javax.swing.event.InternalFrameAdapter() {
		// public void internalFrameOpened(
		// javax.swing.event.InternalFrameEvent e) {
		// List list = fecavAction.findFecavBillNotUsed(
		// new Request(CommonVars.getCurrUser()), "",
		// new ArrayList());
		// initUsedTable(list);
		// showCount(list);
		// setState();
		// }
		// });

	}

	private void initDate() {
		List list = fecavAction.findFecavBillNotUsed(new Request(CommonVars
				.getCurrUser()), "", new ArrayList());
		initUsedTable(list);
		showCount(list);
		setState();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbCount = new JLabel();
			lbCount.setText("JLabel");
			lbCount.setForeground(Color.blue);
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getSpInner(), java.awt.BorderLayout.CENTER);
			jContentPane.add(lbCount, java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private void showCount(List list) {
		Double total = 0.0;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				total += (fecavBill.getTotalPrice() == null ? 0.0 : fecavBill
						.getTotalPrice());
			}
		}
		this.lbCount.setText(" 总份数: " + list.size() + "           统计金额："
				+ CommonVars.formatDoubleToString(total, 999, 6));

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
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnPrint1());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnUpdateCustomsDeclarationCode());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refresh();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmUseFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initUsedTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("出口日期", "exportDate", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("核销单号 ", "code", 200));
				list.add(addColumn("领单日期", "innerObtainDate", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("手册号码", "emsNo", 100));
				list.add(addColumn("币别", "curr.name", 100));
				list.add(addColumn("总金额", "totalPrice", 100));
				list.add(addColumn("标志", "billState", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		usedTableModel = new JTableListModel(tbUsed, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		usedTableModel.setAllowSetValue(true);
		tbUsed.getColumnModel().getColumn(1).setCellEditor(
				new JComboBoxEditor(new JComboBox()));
		tbUsed.getColumnModel().getColumn(1).setCellRenderer(
				new ForcedEditTableCellRenderer());
		setRowColorDeclareDateOver30Day();
		TableColumn column = this.tbUsed.getColumnModel().getColumn(9);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				//TableColumn column5 = tbUsed.getColumnModel().getColumn(5);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				this.setText(CommonVars.getFecavState(state));
				return this;
			}
		});
		tbUsed.setRowHeight(20);
	}

	/**
	 * 编辑列
	 */
	class JComboBoxEditor extends DefaultCellEditor implements // ChangeListener,
			KeyListener, FocusListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JCalendarComboBox cbbDate = new JCalendarComboBox();

		private JTable table = null;

		private int keyCode = -1;

		public JComboBoxEditor(JComboBox tf) {
			super(tf);
			this.setClickCountToStart(1);
			this.cbbDate.getJTextField().addFocusListener(this);
			this.cbbDate.getJTextField().addKeyListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				try {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date date = bartDateFormat.parse(value.toString());
					cbbDate.setDate(date);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				cbbDate.setDate(null);
			}
			this.table = table;
			return cbbDate;
		}

		public Object getCellEditorValue() {
			return cbbDate.getDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		public void keyTyped(KeyEvent e) {
			// 

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent e) {
			keyCode = e.getKeyCode();
			if (e.getKeyCode() == 10) {
				tbUsed.requestFocus();
			}
			// if (e.getKeyCode() == 40) {
			// if (tableModel != null && tableModel.hasNextRow()) {
			// tableModel.nextRow();
			// }
			// tbUsed.requestFocus();
			// }
			// if (e.getKeyCode() == 38) {
			// if (tableModel != null && tableModel.hasPreviousRow()) {
			// tableModel.previousRow();
			// }
			// tbUsed.requestFocus();
			// }
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent e) {
		}

		public void focusGained(FocusEvent e) {
			editBeforeDate = this.cbbDate.getDate();
			keyCode = -1;
		}

		public void focusLost(FocusEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			FecavBill fecavBill = (FecavBill) tableModel.getCurrentRow();
			if (fecavBill == null) {
				return;
			}
			if (cbbDate.getDate() != null && fecavBill.getDeclareDate() != null) {
				if (DateFormatUtils.format(cbbDate.getDate(), "yyyy-MM-dd")
						.compareTo(
								DateFormatUtils.format(fecavBill
										.getDeclareDate(), "yyyy-MM-dd")) < 0) {
					JOptionPane.showMessageDialog(FmUseFecavBill.this, "核销单"
							+ fecavBill.getCode() + "出口日期必须大于等于申报日期!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					cbbDate.setDate(null);
					cbbDate.getJTextField().requestFocus();
					return;
				}
			}
			if (cbbDate.getDate() != null || editBeforeDate != null) {
				if ((cbbDate.getDate() != null && editBeforeDate == null)
						|| (cbbDate.getDate() == null && editBeforeDate != null)
						|| DateFormatUtils.format(cbbDate.getDate(),
								"yyyy-MM-dd").compareTo(
								DateFormatUtils.format(editBeforeDate,
										"yyyy-MM-dd")) != 0) {
					if (keyCode == 10 && tableModel != null
							&& tableModel.hasNextRow()) {
						tableModel.nextRow();
						keyCode = -1;
					}
				}
			}
		}
	}

	/**
	 * 设置数据着色
	 */
	private void setRowColorDeclareDateOver30Day() {
		if (this.usedTableModel == null) {
			return;
		}
		for (int i = 2; i < this.usedTableModel.getColumnCount() - 1; i++) {
			tbUsed.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
		tbUsed.repaint();
		tbUsed.validate();
	}

	/**
	 * 验证报关单Id对应的这条报关单是否已经生效
	 * 
	 * @param customsDeclarationId ==
	 *            报关单id
	 * @return
	 */
	private boolean validateData(FecavBill fecavBill) {
		String customsDeclarationId = fecavBill.getCustomsDeclarationId();
		if (fecavBill.getExportDate() == null) {
			return true;
		}
		if (customsDeclarationId == null
				|| "".equals(customsDeclarationId.trim())) {
			JOptionPane.showMessageDialog(FmUseFecavBill.this, "核销单"
					+ fecavBill.getCode() + "没有被报关单使用!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String customsDeclarationCode = fecavBill.getCustomsDeclarationCode();
		if (customsDeclarationCode == null
				|| "".equals(customsDeclarationCode.trim())) {
			JOptionPane.showMessageDialog(FmUseFecavBill.this, "核销单"
					+ fecavBill.getCode() + "的报关单号不存在!请按更新按钮更新核销单", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		BaseEncAction baseEncAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
		BaseCustomsDeclaration baseCustomsDeclaration = baseEncAction
				.findAllCustomsDeclarationById(new Request(CommonVars
						.getCurrUser(), true), customsDeclarationId);
		if (baseCustomsDeclaration == null) {
			JOptionPane.showMessageDialog(FmUseFecavBill.this, "使用该核销单"
					+ fecavBill.getCode() + "的报关单" + "不存在!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (baseCustomsDeclaration.getDeclarationDate() != null
				&& fecavBill.getExportDate() != null) {
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			if (dateFormat.format(baseCustomsDeclaration.getDeclarationDate()).compareTo(
					dateFormat.format(fecavBill.getExportDate())) > 0) {
				JOptionPane.showMessageDialog(FmUseFecavBill.this, "核销单"
						+ fecavBill.getCode() + "出口日期必须大于等于申报日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		// if (baseCustomsDeclaration.getEffective() == null
		// || baseCustomsDeclaration.getEffective() == false) {
		// JOptionPane.showMessageDialog(FmUseFecavBill.this,
		// "使用该核销单的报关单没有生效!!\n请生效该报关单后再填入 [出口日期] !!", "提示",
		// JOptionPane.OK_OPTION);
		// return false;
		// }
		return true;
	}

	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public ForcedEditTableCellRenderer() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// Obtain the default component.
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (hasFocus && isSelected) {
				TableModel tblModel = table.getModel();
				if (tblModel.isCellEditable(row, column)) {
					// Cell is editable
					table.editCellAt(row, column);
					// table.getEditorComponent().requestFocus();
					JFormattedTextField tf = (JFormattedTextField) ((JCalendarComboBox) table
							.getEditorComponent()).getJTextField();
					tf.requestFocus();
				}
			}
			return comp;
		}

	}

	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (checkValue(table, row, column)) {
				c.setForeground(Color.BLUE);
			} else {
				if (isSelected) {
					c.setForeground(table.getSelectionForeground());
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setForeground(table.getForeground());
					c.setBackground(table.getBackground());
				}
			}
			return c;
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		FecavBill data = (FecavBill) usedTableModel.getDataByRow(row);
		if (data.getExportDate() != null || data.getDeclareDate() == null) {
			return false;
		}
		Calendar tempDate = Calendar.getInstance();
		Calendar declareDate = Calendar.getInstance();
		tempDate.setTime(new Date());
		declareDate.setTime(data.getDeclareDate());
		declareDate.add(Calendar.DAY_OF_YEAR, 30);
		//
		// 时间超过30天
		//
		if (tempDate.after(declareDate)) {
			return true;
		}
		return false;
	}

	private void refresh() {
		// if (jTabbedPane.getSelectedIndex() == 0) {
		// List list = fecavAction.findFecavBillByState(new Request(CommonVars
		// .getCurrUser()), FecavState.INNER_OBTAIN);
		// initUnusedTable(list);
		// } else if (jTabbedPane.getSelectedIndex() == 1) {
		// List list = fecavAction.findFecavBillByState(new Request(CommonVars
		// .getCurrUser()), FecavState.USED);
		List list = fecavAction.findFecavBillNotUsed(new Request(CommonVars
				.getCurrUser()), "", new ArrayList());
		initUsedTable(list);
		showCount(list);
		// }
		setState();
	}

	/**
	 * This method initializes spInner
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpInner() {
		if (spInner == null) {
			spInner = new JScrollPane();
			spInner.setViewportView(getTbUsed());
		}
		return spInner;
	}

	/**
	 * This method initializes tbInner
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUsed() {
		if (tbUsed == null) {
			tbUsed = new JTable();
		}
		return tbUsed;
	}

	private void setState() {
		// this.btnEdit.setVisible(jTabbedPane.getSelectedIndex() == 1);
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
					if (usedTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmUseFecavBill.this,
								"请选择你要修改的核销单", "提示", JOptionPane.OK_OPTION);
						return;
					}
					FecavBill fecavBill = (FecavBill) usedTableModel
							.getCurrentRow();
					DgEditUnusedFecavBill dg = new DgEditUnusedFecavBill();
					dg.setFecavBill(fecavBill);
					dg.setVisible(true);
					if (dg.isOk()) {
						// fecavBill = dg.getFecavBill();
						// usedTableModel.updateRow(fecavBill);
						refresh();
					}
					showCount(usedTableModel.getList());
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFecavBillQuery dg = new DgFecavBillQuery();
					dg.setFecavState(FecavState.USED);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						initUsedTable(list);
						showCount(list);
						setState();
					}
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (usedTableModel == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							usedTableModel.getList());
					InputStream reportStream = FmUseFecavBill.class
							.getResourceAsStream("report/FecavBillReport.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "外汇核销单使用");
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tbUsed.getCellEditor() != null) {
						tbUsed.getCellEditor().stopCellEditing();
					}
					List list = usedTableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						FecavBill fecavBill = (FecavBill) list.get(i);
						if (validateData(fecavBill) == false) {
							return;
						}
					}
					for (int i = 0; i < list.size(); i++) {
						FecavBill fecavBill = (FecavBill) list.get(i);
						fecavAction.saveFecavBillAndUpdateCustomsDeclaration(
								new Request(CommonVars.getCurrUser()),
								fecavBill);
					}
					refresh();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdateCustomsDeclarationCode() {
		if (btnUpdateCustomsDeclarationCode == null) {
			btnUpdateCustomsDeclarationCode = new JButton();
			btnUpdateCustomsDeclarationCode.setText("更新");
			btnUpdateCustomsDeclarationCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							fecavAction
									.updateFecavBillCustomsDeclarationCode(new Request(
											CommonVars.getCurrUser()));
							refresh();
						}
					});
		}
		return btnUpdateCustomsDeclarationCode;
	}

	/**
	 * This method initializes btnPrint1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint1() {
		if (btnPrint1 == null) {
			btnPrint1 = new JButton();
			btnPrint1.setText("出口收汇核销单");
			btnPrint1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (usedTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmUseFecavBill.this,
								"请选择你要打印的核销单", "提示", JOptionPane.OK_OPTION);
						return;
					}
					getJPopupMenu().show(btnPrint1, 0, btnPrint1.getHeight());

				}
			});
		}
		return btnPrint1;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPopupMenu.add(getJmOverPrint());
			jPopupMenu.add(getJmNotOverPrint());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jmOverPrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmOverPrint() {
		if (jmOverPrint == null) {
			jmOverPrint = new JMenuItem();
			jmOverPrint.setText("套打");
			jmOverPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport(true);
				}
			});
		}
		return jmOverPrint;
	}

	/**
	 * This method initializes jmNotOverPrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmNotOverPrint() {
		if (jmNotOverPrint == null) {
			jmNotOverPrint = new JMenuItem();
			jmNotOverPrint.setText("非套打");
			jmNotOverPrint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport(false);
						}
					});
		}
		return jmNotOverPrint;
	}

	/**
	 * 弹出打印对话框
	 * 
	 * @param isOverPrint
	 *            是否要套打
	 */
	private void printReport(boolean isOverPrint) {
		DgExportCancelBillSub dgExportCancelBillSub = new DgExportCancelBillSub();
		dgExportCancelBillSub.setIsOverPrint(isOverPrint);
		dgExportCancelBillSub.setMasterModel(usedTableModel);
		dgExportCancelBillSub.setVisible(true);
	}

	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("从内部领单新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}
	private void addData() {
		List<FecavBill> list = FecavQuery
				.getInstance().findInnerObtainFecavBill();
		if (list != null && list.size() > 0) {
			List<FecavBill> dataSource = fecavAction
					.addFecavBill(new Request(CommonVars
							.getCurrUser()), list);
			this.usedTableModel.addRows(dataSource);
		}
		showCount(usedTableModel.getList());
	}
}
