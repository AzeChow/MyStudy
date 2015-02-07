package com.bestway.common.client.fpt;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JPanelBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class PnMakeFptBillList extends JPanelBase {

	private FptManageAction fptManageAction = null;

	private JTableListModel tableModelDetail = null;
	
//	private String sysType = ""; // @jve:decl-index=0:
//	
//	private String backSysType="";

	private String inOutFlag = ""; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JScrollPane jScrollPane = null;

	private JTable tfTransFactBill = null;

	private JButton btnNotSelectAll = null;

	private JButton btnSelectAll = null;

	private JPanel jPanel = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JLabel jLabel9 = null;

	private JTextField jTextField1 = null;

	private JButton btnChoose = null;

	public BaseCustomsDeclaration cm = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton11 = null;

	private JLabel jLabel = null;

	private JComboBox cbbFptApp = null;
	
	private JCalendarComboBox cbbBeginDate=null;
	
	private JCalendarComboBox cbbEndDate = null;

	private JPanel jPanel2 = null;
	private ButtonGroup bg = new ButtonGroup(); // @jve:decl-index=0:
	private ButtonGroup bg1 = new ButtonGroup(); // @jve:decl-index=0:
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBillList() {
		super();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		initialize();
		initUIComponents();
	}

	@SuppressWarnings("rawtypes")
	private void initUIComponents() {
		bg.add(jRadioButton2);
		bg.add(jRadioButton11);
		bg1.add(jRadioButton);
		bg1.add(jRadioButton1);
		if (inOutFlag == null || "".equals(inOutFlag)) {
			inOutFlag = FptInOutFlag.OUT;
		}
		List list = fptManageAction.findFptAppHeadByScmCoc(new Request(CommonVars.getCurrUser(), true), inOutFlag,DeclareState.PROCESS_EXE, null,false);
		cbbFptApp.removeAllItems();
		this.cbbFptApp.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbFptApp,	"appNo", "appNo");
		cbbFptApp.setRenderer(CustomBaseRender.getInstance().getRender("appNo","appNo", 200, 0));
		cbbFptApp.setSelectedIndex(-1);
		getBtnChoose().setEnabled(false);
		initTable();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.insets = new Insets(0, 8, 0, 9);
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.ipadx = 636;
		gridBagConstraints3.ipady = 30;
		gridBagConstraints3.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(1, 9, 7, 7);
		gridBagConstraints2.gridy = 2;
		gridBagConstraints2.ipadx = 185;
		gridBagConstraints2.ipady = -204;
		gridBagConstraints2.gridx = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.insets = new Insets(5, 8, 0, 8);
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.ipadx = 637;
		gridBagConstraints1.ipady = 73;
		gridBagConstraints1.gridx = 0;
		this.setLayout(new GridBagLayout());
		this.setSize(654, 335);
		this.add(getJPanel(), gridBagConstraints1);
		this.add(getJPanel1(), gridBagConstraints2);
		this.add(getJPanel2(), gridBagConstraints3);
	}

	/**
	 * 获得右边jList的数据源
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListData() {
		if (this.tableModel == null) {
			return new ArrayList();
		} else {
			List lsResult = new ArrayList();
			List list = this.tableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				FptBillHead bill = (FptBillHead) list.get(i);
				if (bill.getIsSelected() != null && bill.getIsSelected()) {
					lsResult.add(bill);
				}
			}
			return lsResult;
		}
	}

	/**
	 * 填充数据到JList
	 */
	@SuppressWarnings("serial")
	private void initTable() {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		String appNo = null;
		if (this.cbbFptApp.getSelectedItem() != null) {
			appNo = ((FptAppHead) this.cbbFptApp.getSelectedItem()).getAppNo();
		}
		if (appNo != null && !"".equals(appNo)) {			
			System.out.println("----------------------------"+inOutFlag+"--------------------"+appNo);
			list = fptManageAction.findFptBillMakeCustomsDeclaration(
					new Request(CommonVars.getCurrUser()), inOutFlag, 
					appNo,cbbBeginDate.getDate(),cbbEndDate.getDate());
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));//1.
				list.add(addColumn("转出标志", "billSort", 80));//9
				list.add(addColumn("单据类型", "sysType", 80)); //2.单据类型标志 收发货单 2 退货单 3
				if (FptInOutFlag.OUT.equals(inOutFlag)) {
					list.add(addColumn("申报日期", "issueIsDeclaDate", 80));//4.
					list.add(addColumn("转出企业内部编号", "issueCopBillNo", 150));//3.
					list.add(addColumn("转出手册/帐册号", "outEmsNo", 100));//5.
				} else {
					list.add(addColumn("申报日期", "receiveIsDeclaDate", 80));//4.
					list.add(addColumn("转入企业内部编号", "receiveCopBillNo", 150));//3.
				}
				list.add(addColumn("申报状态", "appState", 80));//5.6.
				list.add(addColumn("申请表编号", "appNo", 80));//6.7.
				if (FptInOutFlag.OUT.equals(inOutFlag)) {
					list.add(addColumn("客户/供应商", "customer.name", 150));//7.8.
				} else {
					list.add(addColumn("客户/供应商", "customer.name", 150));//7.8
				}
				return list;
			}
		};
		tableModel = new JTableListModel(tfTransFactBill, list,	jTableListModelAdapter);
//		jTableListModelAdapter.setEditableColumn(1);
		int count = 0;
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			count = 7;
		} else {
			count = 6;
		}
		tfTransFactBill.getTableHeader().setReorderingAllowed(false);
		JTableListModel.dataBind(tfTransFactBill, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModelDetail = (JTableListModel) tfTransFactBill.getModel();
		tableModelDetail.setAllowSetValue(true);
		jTableListModelAdapter.setEditableColumn(1);
		tfTransFactBill.getColumnModel().getColumn(1).setHeaderRenderer(
				new CheckBoxHeader(false));
		tfTransFactBill.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		
		tfTransFactBill.getColumnModel().getColumn(count).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(	JTable table, Object value, boolean isSelected,	boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value.toString());
					}
					
				});
		tfTransFactBill.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "2".equals(value) ? "收发货单":"退货单";//单据类型标志 收发货单 2 退货单 3
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		tfTransFactBill.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
	}
	
	/**
	 * 表格渲染器
	 * 
	 * @author Administrator
	 * 
	 */
	@SuppressWarnings("serial")
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null||"false".equals(value)) {
				checkBox.setSelected(false);
				checkBox.setHorizontalAlignment(SwingConstants.CENTER);
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
				checkBox.setHorizontalAlignment(SwingConstants.CENTER);
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

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
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
			if (obj instanceof FptBillHead) {
				FptBillHead temp = (FptBillHead) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTfTransFactBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTfTransFactBill() {
		if (tfTransFactBill == null) {
			tfTransFactBill = new JTable();
		}
		return tfTransFactBill;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setBounds(new Rectangle(75, 3, 60, 28));
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setBounds(new Rectangle(0, 3, 60, 28));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
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
			if (list.get(i) instanceof FptBillHead) {
				FptBillHead t = (FptBillHead) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
//		tableModel.updateRows(list);
		tableModelDetail.updateRows(list);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(254, 21, 68, 18));
			jLabel.setText("申请单编号");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(255, 44, 139, 24));
			jLabel9.setText("手册/账册/报关单流水号:");
			jLabel9.setForeground(Color.BLUE);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u751f\u6210\u62a5\u5173\u5355\u65b9\u5f0f\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getBtnChoose(), null);
			jPanel.add(getJRadioButton2(), null);
			jPanel.add(getJRadioButton11(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbFptApp(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(121, 17, 117, 24));
			jRadioButton.setText("生成新的报关单");
			jRadioButton.setSelected(true);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getBtnChoose().setEnabled(false);
					jTextField1.setText(null);
				}
			});
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
			jRadioButton1.setBounds(new Rectangle(121, 43, 129, 24));
			jRadioButton1.setText("追加到现有报关单");
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getBtnChoose().setEnabled(true);
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(396, 45, 120, 24));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes btnChoose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton();
			btnChoose.setBounds(new Rectangle(517, 45, 23, 24));
			btnChoose.setText("...");
			btnChoose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbFptApp.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "请选择申请单编号！", "警告！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Integer project = 0;
					if (cbbFptApp.getSelectedItem() != null) {
						project = ((FptAppHead) cbbFptApp.getSelectedItem())
								.getProjectType();
					}
					Object obj = findCustomsDeclaration(
							"0".equals(inOutFlag) ? ImpExpFlag.EXPORT
									: ImpExpFlag.IMPORT, project);
					if (obj != null) {
						cm = (BaseCustomsDeclaration) obj;
						jTextField1.setText(cm.getEmsHeadH2k()
								+ "/"
								+ (cm.getSerialNumber() == null ? " " : cm
										.getSerialNumber().toString()));
					}

				}
			});
		}
		return btnChoose;
	}

	public boolean vaildateData() {
		if (this.inOutFlag.equals(FptInOutFlag.OUT)) {
			if (jTextField1.getText() != null
					&& !"".equals(jTextField1.getText())) {
				return fptManageAction.checkmakeFptToBgdEmsH2k(jTextField1
						.getText(), tableModel.getList());
			}
		}
		return true;
	}

	/**
	 * 抓取四码Distinct资料
	 * 
	 * @param projectType
	 * @return
	 */
	@SuppressWarnings("serial")
	public Object findCustomsDeclaration(final Integer isImport,
			final Integer project) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册/账册号", "emsHeadH2k", 150));
		list.add(new JTableListColumn("流水号", "serialNumber", 50));
		list.add(new JTableListColumn("进出口类型", "impExpType", 150));
		list.add(new JTableListColumn("是否生效", "effective", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {

			@SuppressWarnings("rawtypes")
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				EncAction encAction = (EncAction) CommonVars
						.getApplicationContext().getBean("encAction");
				return fptManageAction.findCustomsDeclarationByImpExpFlag(
						new Request(CommonVars.getCurrUser()), isImport,
						project);
			}

			@Override
			public void doSomethingBeforeVisable(JTable table) {
				table.getColumnModel().getColumn(3).setCellRenderer(
						new DefaultTableCellRenderer() {
							@Override
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									switch (Integer.parseInt(value.toString())) {
									case ImpExpType.TRANSFER_FACTORY_IMPORT:
										str = "料件转厂";
										break;
									case ImpExpType.TRANSFER_FACTORY_EXPORT:
										str = "转厂出口";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
				table.getColumnModel().getColumn(4).setCellRenderer(
						new TableCheckBoxRender());
			}
		};
		dgCommonQuery.setTitle("请选择报关单！");
		DgCommonQueryPage.setSingleResult(true);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(8, 16, 109, 26));
			jRadioButton2.setSelected(true);
			jRadioButton2.setText("发货和退货单");
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jRadioButton2.isSelected()) {
//								sysType = FptBusinessType.FPT_BILL;
//								backSysType=FptBusinessType.FPT_BILL_BACK;
								inOutFlag = FptInOutFlag.OUT;
								initUIComponents();
							}
						}
					});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setBounds(new Rectangle(8, 42, 99, 26));
			jRadioButton11.setText("收货和退货单");
			jRadioButton11
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jRadioButton11.isSelected()) {
//								sysType = FptBusinessType.FPT_BILL;
//								backSysType=FptBusinessType.FPT_BILL_BACK;
								inOutFlag = FptInOutFlag.IN;
								initUIComponents();
							}
						}
					});
		}
		return jRadioButton11;
	}

	/**
	 * 返回选中的结转申请单
	 * 
	 * @return
	 */
	public FptAppHead getSelectedFptAppHead() {
		return (FptAppHead) this.cbbFptApp.getSelectedItem();
	}

	/**
	 * This method initializes cbbFptApp
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFptApp() {
		if (cbbFptApp == null) {
			cbbFptApp = new JComboBox();
			cbbFptApp.setBounds(new Rectangle(327, 17, 210, 21));
			cbbFptApp.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable();
						cbbBeginDate.setEnabled(true);
						cbbEndDate.setEnabled(true);
					}
				}
			});
		}
		return cbbFptApp;
	}

	public String getIsImport() {
		return inOutFlag;
	}

	public void setIsImport(String isImport) {
		this.inOutFlag = isImport;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getBtnSelectAll(), null);
			jPanel2.add(getBtnNotSelectAll(), null);
			jPanel2.add(getLblNewLabel());
			jPanel2.add(getCbbBeginDate());
			jPanel2.add(getLblNewLabel_1());
			jPanel2.add(getCbbEndDate());
		}
		return jPanel2;
	}

	
//	public String getBackSysType() {
//		return backSysType;
//	}
//
//	public void setBackSysType(String backSysType) {
//		this.backSysType = backSysType;
//	}
//
//	public String getSysType() {
//		return sysType;
//	}
//
//	public void setSysType(String sysType) {
//		this.sysType = sysType;
//	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("转报关单开始日期 自：");
			lblNewLabel.setBounds(249, 10, 126, 15);
		}
		return lblNewLabel;
	}
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			
			cbbBeginDate.setDate(CommonUtils.firstDayOfMonth(new Date()));
			cbbBeginDate.setBounds(new Rectangle(385, 3, 97, 23));
			cbbBeginDate.setEnabled(false);
			cbbBeginDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					initTable();
				}
			});
			
		}
		return cbbBeginDate;
	}
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setDate(CommonUtils.lastDayOfMonth(new Date()));
			cbbEndDate.setBounds(new Rectangle(525, 3, 97, 23));
			cbbEndDate.setEnabled(false);
			cbbEndDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					initTable();
				}
			});
		}
		return cbbEndDate;
	}
	
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("至");
			lblNewLabel_1.setBounds(492, 10, 28, 15);
		}
		return lblNewLabel_1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
