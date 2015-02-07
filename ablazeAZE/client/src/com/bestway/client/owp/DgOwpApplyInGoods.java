package com.bestway.client.owp;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.WorkOrderList;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.owp.DgOwpApplyOutGoods.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.owp.action.OwpAnalyAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgOwpApplyInGoods extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1639337249383987601L;
	private JPanel pnTop = null;
	private JPanel PnTop1 = null;
	private JScrollPane jScrollPane = null;
	private JTable tbTable = null;
	private JButton btnQuerry = null;
	private JButton btnprint = null;
	private JButton btnClose = null;
	private JTableListModel tableModel = null;
	private ArrayList dataSource = null;
	private JSplitPane jSplitPane = null;
	private String materielType = null; // @jve:decl-index=0:
	private String reportTilte = null; // @jve:decl-index=0:

	/**
	 * 查询action
	 */
	private CasCheckAction casAction = null;
	private CasAction casAction1 = null;
	private OwpAnalyAction owpAnalyAction = null;
	private JLabel lbOwpNo = null;
	private JTextField tfOwpNo = null;
	private List list = null;
	/**
	 * 小数位控制
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:
	private JPopupMenu jPopupMenuPrint = null;
	private JMenuItem printDeclareNameGroups = null;
	private JMenuItem printPartNumberGroups = null;
	private JLabel lbInTradeName = null;
	private JTextField tfIntradeName = null;
	private JButton btnOwpNo = null;
	private JButton btnIntradeName = null;
	private JLabel lbTextState = null;
	private JCheckBox cbState2 = null;
	private JCheckBox cbState5 = null;
	private JCheckBox cbState1 = null;
	private JCheckBox cbState3 = null;
	private JCheckBox cbState4 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgOwpApplyInGoods() {
		super();
		owpAnalyAction = (OwpAnalyAction) CommonVars
				.getApplicationContext().getBean("owpAnalyAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(882, 541));
		this.setContentPane(getJSplitPane());
		this.setTitle("申请外发加工收货明细表");
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		
		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		endClarendar.set(Calendar.HOUR_OF_DAY, 0);
		endClarendar.set(Calendar.MINUTE, 0);
		endClarendar.set(Calendar.SECOND, 0);
		
		

	}

	private void initUIComponents() {
		initTable(new ArrayList());

	}

	private void setCmbBillTypeEvent(final JComboBox cbb) {
		final JList listBox;
		try {
			Field field = JComponent.class.getDeclaredField("ui");
			field.setAccessible(true);
			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
			field = BasicComboBoxUI.class.getDeclaredField("listBox");
			field.setAccessible(true);
			listBox = (JList) field.get(ui);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		if (listBox == null) {
			return;
		}

		listBox.setBackground(Color.white);
		listBox.setFixedCellHeight(20);

		MouseListener[] mouseListener = listBox.getMouseListeners();
		for (int i = 0; i < mouseListener.length; i++) {
			listBox.removeMouseListener(mouseListener[i]);
		}

		listBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listBox.locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());
					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}
				}
			}
		});

		//
		// 当焦点丢失的时候
		//
		ComboBoxEditor com = cbb.getEditor();
		final JTextField tf = (JTextField) com.getEditorComponent();
		tf.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				String[] strs = tf.getText().split(",");
				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();

				int size = listBox.getModel().getSize();
				for (int i = 0; i < size; i++) {
					CheckBoxListItem item = (CheckBoxListItem) listBox
							.getModel().getElementAt(i);
					checkBoxListItemMap.put(item.getName(), item);
				}
				//
				// 根据输入的字符串选中JList中的列表
				//
				String tempText = "";
				// System.out.println("strs.length =="+strs.length);
				for (String str : strs) {
					// System.out.println(str);
					CheckBoxListItem item = checkBoxListItemMap.get(str);
					if (item != null) {
						item.setIsSelected(true);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
						checkBoxListItemMap.remove(str);
					}
				}
				//
				// 清除未选中的记录
				//
				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItem checkBoxListItem = iterator.next();
					checkBoxListItem.setIsSelected(false);
				}
				//
				// 重新设置其显示文本值
				//
				tf.setText(tempText);
			}
		});

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.add(getPnTop1(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop1() {
		if (PnTop1 == null) {
			lbTextState = new JLabel();
			lbTextState.setBounds(new Rectangle(325, 22, 85, 19));
			lbTextState.setFont(new Font("Dialog", Font.PLAIN, 12));
			lbTextState.setForeground(Color.red);
			lbTextState.setText("申报状态条件");
			lbInTradeName = new JLabel();
			lbInTradeName.setBounds(new Rectangle(3, 51, 92, 22));
			lbInTradeName.setText("承揽方企业名称");
			lbOwpNo = new JLabel();
			lbOwpNo.setText("申请表编号");
			lbOwpNo.setSize(new Dimension(92, 22));
			lbOwpNo.setLocation(new Point(3, 23));
			lbOwpNo
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			PnTop1 = new JPanel();
			PnTop1.setLayout(null);
			PnTop1.setBounds(new Rectangle(10, 10, 849, 93));
			PnTop1.setBorder(BorderFactory.createTitledBorder(null, "\u67e5\u8be2\u6761\u4ef6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 14), Color.blue));
			PnTop1.setFont(new Font("Dialog", Font.PLAIN, 12));
			PnTop1.add(getBtQuerry(), null);
			PnTop1.add(getBtprint(), null);
			PnTop1.add(getBtClose(), null);
			PnTop1.add(lbOwpNo, null);
			PnTop1.add(getTfOwpNo(), null);
			PnTop1.add(lbInTradeName, null);
			PnTop1.add(getTfIntradeName(), null);
			PnTop1.add(getBtnOwpNo(), null);
			PnTop1.add(getBtnIntradeName(), null);
			PnTop1.add(lbTextState, null);
			PnTop1.add(getCbState2(), null);
			PnTop1.add(getCbState5(), null);
			PnTop1.add(getCbState1(), null);
			PnTop1.add(getCbState3(), null);
			PnTop1.add(getCbState4(), null);
		}
		return PnTop1;
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

	public String getMaterielType() {
		materielType = MaterielType.MATERIEL;
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbTable == null) {
			tbTable = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
			tbTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						// editData();
					}
				}
			});
		}
		return tbTable;
	}

	/**
	 * This method initializes btQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtQuerry() {
		if (btnQuerry == null) {
			btnQuerry = new JButton();
			btnQuerry.setSize(new Dimension(84, 22));
			btnQuerry.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnQuerry.setText("查询");
			btnQuerry.setLocation(new Point(655, 22));
			btnQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuerry;
	}

	public void queryData(){
		if((tfOwpNo.getText()==null||tfOwpNo.getText().trim().equals(""))
				&&(tfIntradeName.getText()==null||tfIntradeName.getText().trim().equals(""))){
			JOptionPane.showMessageDialog(DgOwpApplyInGoods.this, "申请表编号或承揽方企业名称至少填写一个", "提示", 0);
			return;
		}
		if(!cbState1.isSelected()&&!cbState2.isSelected()&&!cbState3.isSelected()
				&&!cbState4.isSelected()){
			JOptionPane.showMessageDialog(DgOwpApplyInGoods.this, "请至少选择一种要查询的申报状态", "提示", 0);
			return;
		}
		// 查询
		// 组织查询条件
		List<Condition> billConditions = new ArrayList<Condition>();
		billConditions = getBillCondtions();
		List<Condition> appConditions = new ArrayList<Condition>();
		appConditions = getAppCondtions();

		// 执行查询线程
		new Find(appConditions,billConditions).execute();

	
	}

	/**
	 * 发货登记表查询条件
	 * 
	 * @return
	 */
	protected List<Condition> getBillCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		if(tfOwpNo.getText()!=null&&!tfOwpNo.getText().equals("")){
		conditions.add(new Condition("and", null, "head.appNo",
				"=",tfOwpNo.getText(), null));
		}
		if(tfIntradeName.getText()!=null&&!tfIntradeName.getText().equals("")){
			conditions.add(new Condition("and", null, "head.inTradeName",
					"=",tfIntradeName.getText(), null));
			}
		List<String> stateList=new ArrayList<String>();
		if(cbState1.isSelected()){
			stateList.add("1");
		}
		if(cbState2.isSelected()){
			stateList.add("2");
		}
		if(cbState3.isSelected()){
			stateList.add("3");
		}
		if(cbState4.isSelected()){
			stateList.add("4");
		}
		System.out.println("stateList="+stateList.size());
		if(stateList.size()>0){
			String[] toBeStored = new String[]{};    
			String[] str = stateList.toArray(toBeStored);   
			System.out.println("str.length="+str.length);
		conditions.add(new Condition("and", null, "head.declareState",
					" in (",str, ")"));
			
		}

			
		return conditions;
	}
	/**
	 * 发货申请表查询条件
	 * 
	 * @return
	 */
	protected List<Condition> getAppCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		if(tfOwpNo.getText()!=null&&!tfOwpNo.getText().equals("")){
		conditions.add(new Condition("and", null, "head.appNo",
				"=",tfOwpNo.getText(), null));
		}
		if(tfIntradeName.getText()!=null&&!tfIntradeName.getText().equals("")){
			conditions.add(new Condition("and", null, "head.inTradeName",
					"=",tfIntradeName.getText(), null));
			}
		return conditions;
	}

	class Find extends SwingWorker {
		// 申请表查询条件
		List<Condition> appConditions = null;
		// 登记表查询条件
		List<Condition> billConditions = null;
		// 排序

		public Find(List<Condition> appConditions,List<Condition> billConditions) {
			this.appConditions=appConditions;
			this.billConditions=billConditions;
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress.showProgressDialog(DgOwpApplyInGoods.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			list=owpAnalyAction.findOwpApplyInGoods(appConditions,billConditions);
			return list;
		}

		@Override
		protected void done() {// 更新视图
			list = null;
			try {
				list = (List) this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}

	/**
	 * This method initializes btprint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtprint() {
		if (btnprint == null) {
			btnprint = new JButton();
			btnprint.setText("打印");
			btnprint.setSize(new Dimension(84, 22));
			btnprint.setLocation(new Point(756, 22));
			btnprint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenuPrint().show(btnprint, 0, btnprint.getHeight());

				}
			});

		}
		return btnprint;
	}

	private JPopupMenu getJPopupMenuPrint() {
		if (jPopupMenuPrint == null) {
			jPopupMenuPrint = new JPopupMenu();
			jPopupMenuPrint.setSize(new Dimension(110, 36));
			jPopupMenuPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPopupMenuPrint.add(getPrintPartNumberGroups());
			jPopupMenuPrint.add(getPrintDeclareNameGroups());

		}
		return jPopupMenuPrint;
	}

	private JMenuItem getPrintPartNumberGroups() {
		if (printPartNumberGroups == null) {
			printPartNumberGroups = new JMenuItem();
			printPartNumberGroups.setText("打印(工厂料号)");
			printPartNumberGroups.setBounds(620, 36, 65, 23);
			printPartNumberGroups
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}

					});
		}
		return printPartNumberGroups;
	}

	private void printReport() {
		
		try {
			List list = tableModel.getList();
			if (list == null || list.isEmpty()) {
				JOptionPane.showMessageDialog(DgOwpApplyInGoods.this, "没有列印的记录！",
						"提示！", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
					list);
			InputStream masterReportStream =null;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			parameters.put("type", getMaterielType());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	private JMenuItem getPrintDeclareNameGroups() {
		if (printDeclareNameGroups == null) {
			printDeclareNameGroups = new JMenuItem();
			printDeclareNameGroups.setText("打印(报关名称)");
			printDeclareNameGroups.setBounds(620, 60, 65, 23);
			printDeclareNameGroups
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport2();
						}
					});
		}
		return printDeclareNameGroups;
	}

	/**
	 * 报表打印
	 * 
	 */
	private void printReport2() {
	}

	/**
	 * This method initializes btClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setSize(new Dimension(84, 22));
			btnClose.setLocation(new Point(756, 65));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes dataSource
	 * 
	 * @return java.util.ArrayList
	 */
	private ArrayList getDataSource() {
		if (dataSource == null) {
			dataSource = new ArrayList();
		}
		return dataSource;
	}

	/**
	 * This method initializes tableModel
	 * 
	 * @return com.bestway.client.util.mutispan.AttributiveCellTableModel
	 */

	private void initTable(final List list) {
		tableModel = new JTableListModel(tbTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("申请表编号", "appNo", 100));
						list.add(addColumn("货物序号", "listNo", 100));
						list.add(addColumn("手册序号", "trNo", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "hsName", 100));
						list.add(addColumn("型号规格", "hsSpec", 100));
						list.add(addColumn("申报单位", "hsUnit.name", 100));
						list.add(addColumn("申请数量", "qty", 70));
						list.add(addColumn("累计收货数量", "countQty", 100));
						list.add(addColumn("可收货数量", "canQty", 100));

						return list;
					}
				});
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(120);
			jSplitPane.setDividerSize(1);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tfOwpNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOwpNo() {
		if (tfOwpNo == null) {
			tfOwpNo = new JTextField();
			tfOwpNo.setSize(new Dimension(151, 22));
			tfOwpNo.setLocation(new Point(100, 22));
			tfOwpNo.getDocument().addDocumentListener(
					new DocumentListener() {
						private void setState() {
							String editAfterQueryValue = tfOwpNo
									.getText().trim();
						}

						public void insertUpdate(DocumentEvent e) {
							setState();
						}

						public void removeUpdate(DocumentEvent e) {
							setState();
						}

						public void changedUpdate(DocumentEvent e) {
							setState();
						}
					});
		}
		return tfOwpNo;
	}

	public String getReportTilte() {
		return reportTilte;
	}

	public void setReportTilte(String reportTilte) {
		this.reportTilte = reportTilte;
	}
	
	
	/**
	 * 关联查询时的参数注入
	 * @param startDate
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryCondition(Date startDate,Date endDate,String hsCode,
			String hsName,String hsSpec,String ptNo,String ptName,String ptSpec){
		//如果结束日期不为空
	}

	/**
	 * This method initializes tfIntradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfIntradeName() {
		if (tfIntradeName == null) {
			tfIntradeName = new JTextField();
			tfIntradeName.setBounds(new Rectangle(100, 53, 151, 22));
		}
		return tfIntradeName;
	}

	/**
	 * This method initializes btnOwpNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOwpNo() {
		if (btnOwpNo == null) {
			btnOwpNo = new JButton();
			btnOwpNo.setBounds(new Rectangle(257, 22, 18, 22));
			btnOwpNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = OwpHclQuery
					.getInstance()
					.findAllApplyNo();
								if(object!=null){
					 String No=(((OwpAppHead)object).getAppNo()==null?"":((OwpAppHead)object).getAppNo());
					 String Name=((OwpAppHead)object).getInTradeName()==null?"":((OwpAppHead)object).getInTradeName();
					 tfOwpNo.setText(No);
					 tfIntradeName.setText(Name);
					}
				}
			});
		}
		return btnOwpNo;
	}

	/**
	 * This method initializes btnIntradeName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnIntradeName() {
		if (btnIntradeName == null) {
			btnIntradeName = new JButton();
			btnIntradeName.setBounds(new Rectangle(257, 54, 18, 22));
			btnIntradeName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = OwpHclQuery
					.getInstance()
					.findAllInTradeName();
								if(object!=null){
					 String Name=((OwpAppHead)object).getInTradeName()==null?"":((OwpAppHead)object).getInTradeName();
					 tfIntradeName.setText(Name);
					}
				}
			});
		}
		return btnIntradeName;
	}

	/**
	 * This method initializes cbState2	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbState2() {
		if (cbState2 == null) {
			cbState2 = new JCheckBox();
			cbState2.setBounds(new Rectangle(323, 43, 105, 20));
			cbState2.setText("等待审批");
			cbState2.setSelected(true);
			cbState2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbState1.isSelected()&&cbState2.isSelected()&&cbState3.isSelected()
							&&cbState4.isSelected()){
						cbState5.setSelected(true);
					}else{
						cbState5.setSelected(false);
					}
					
				}
			});
			
		}
		return cbState2;
	}

	/**
	 * This method initializes cbState5	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbState5() {
		if (cbState5 == null) {
			cbState5 = new JCheckBox();
			cbState5.setBounds(new Rectangle(437, 22, 80, 19));
			cbState5.setText("全部");
			cbState5.setSelected(true);
			cbState5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				boolean state=cbState5.isSelected();
				cbState1.setSelected(state);
				cbState2.setSelected(state);
				cbState3.setSelected(state);
				cbState4.setSelected(state);
				
				}
			});
		}
		return cbState5;
	}

	/**
	 * This method initializes cbState1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbState1() {
		if (cbState1 == null) {
			cbState1 = new JCheckBox();
			cbState1.setBounds(new Rectangle(323, 65, 105, 19));
			cbState1.setText("初始状态");
			cbState1.setSelected(true);
			cbState1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbState1.isSelected()&&cbState2.isSelected()&&cbState3.isSelected()
							&&cbState4.isSelected()){
						cbState5.setSelected(true);
					}else{
						cbState5.setSelected(false);
					}
					
				}
			});
		}
		return cbState1;
	}

	/**
	 * This method initializes cbState3	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbState3() {
		if (cbState3 == null) {
			cbState3 = new JCheckBox();
			cbState3.setBounds(new Rectangle(437, 43, 105, 17));
			cbState3.setText("正在执行");
			cbState3.setSelected(true);
			cbState3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbState1.isSelected()&&cbState2.isSelected()&&cbState3.isSelected()
							&&cbState4.isSelected()){
						cbState5.setSelected(true);
					}else{
						cbState5.setSelected(false);
					}
					
				}
			});
		}
		return cbState3;
	}

	/**
	 * This method initializes cbState4	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbState4() {
		if (cbState4 == null) {
			cbState4 = new JCheckBox();
			cbState4.setBounds(new Rectangle(437, 65, 105, 20));
			cbState4.setText("等待撤销审批");
			cbState4.setSelected(true);
			cbState4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbState1.isSelected()&&cbState2.isSelected()&&cbState3.isSelected()
							&&cbState4.isSelected()){
						cbState5.setSelected(true);
					}else{
						cbState5.setSelected(false);
					}
					
				}
			});
		}
		return cbState4;
	}




}  //  @jve:decl-index=0:visual-constraint="10,257"
