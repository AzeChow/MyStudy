package com.bestway.client.selfcheck;

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
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
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
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgWorkOrderBOM extends JDialogBase {
	//
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JTextField tfFactoryMaterialName = null;
	private JTextField tfCustomMaterialName = null;
	private JTextField tfCustomMaterialSpec = null;
	private JTextField tfFactoryMaterialSpec = null;
	private JTable jTable = null;
	private JCheckBox cbFactoryName = null;
	private JCheckBox cbCustomName = null;
	private JButton btQuerry = null;
	private JButton btprint = null;
	private JButton btClose = null;
	private JTableListModel tableModel = null;
	private ArrayList dataSource = null;
	private JSplitPane jSplitPane = null;
	private JCalendarComboBox cbStartDate = null;
	private JCalendarComboBox cbEndDate = null;
	private JButton btFacotryMaterialName = null;
	private JButton btCustomMaterialName = null;
	private JButton btFactoryMaterialSpec = null;
	private JButton btCustomMaterialSpec = null;
	private String materielType = null; // @jve:decl-index=0:
	private String reportTilte = null; // @jve:decl-index=0:

	/**
	 * 查询action
	 */
	private CasCheckAction casAction = null;
	private CasAction casAction1 = null;
	private MaterialManageAction materialManageAction = null;
	private JLabel jLabel71 = null;
	private JTextField tfFactoryNumber1 = null;
	private JButton btFactoryNumber1 = null;
	private JLabel jLabel711 = null;
	private JLabel jLabel712 = null;
	private JLabel jLabel713 = null;
	private JLabel jLabel714 = null;
	private JTextField tfCustomNumber1 = null;
	private JTextField tfFactoryNumber2 = null;
	private JTextField tfCustomNumber2 = null;
	private JTextField tfWorkOder = null;
	private JButton btCustomNumber1 = null;
	private JButton btFactoryNumber2 = null;
	private JButton btCustomNumber2 = null;
	private JButton btWorkOrder = null;
	private List list = null;
	/**
	 * 小数位控制
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:
	private JLabel jLabel111 = null;
	private JLabel jLabel112 = null;
	private JPopupMenu jPopupMenuPrint = null;
	private JMenuItem printDeclareNameGroups = null;
	private JMenuItem printPartNumberGroups = null;
	private JButton btnRelation = null;
	
	
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="807,216"
	private JMenuItem miPandianDifferent = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	
	
	
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;

	/**
	 * This method initializes
	 * 
	 */
	public DgWorkOrderBOM() {
		super();
		casAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		casAction1 = (CasAction) CommonVars.getApplicationContext().getBean(
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
		this.setSize(new Dimension(882, 541));
		this.setContentPane(getJSplitPane());
		this.setTitle("制单耗用情况表");
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		this.cbStartDate.setDate(beginClarendar.getTime());
		this.cbStartDate.setCalendar(beginClarendar);
		
		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		endClarendar.set(Calendar.HOUR_OF_DAY, 0);
		endClarendar.set(Calendar.MINUTE, 0);
		endClarendar.set(Calendar.SECOND, 0);
		this.cbEndDate.setDate(endClarendar.getTime());
		this.cbEndDate.setCalendar(endClarendar);
		
		

	}

	private void initUIComponents() {
		cbFactoryName.setSelected(true);
		initTable(new ArrayList());
		cbFactoryName.setVisible(false);
		cbCustomName.setVisible(false);

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
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel112 = new JLabel();
			jLabel112.setText("\u81f3");
			jLabel112.setSize(new Dimension(12, 22));
			jLabel112.setLocation(new Point(177, 22));
			jLabel112
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel111 = new JLabel();
			jLabel111.setText("\u81f3");
			jLabel111.setSize(new Dimension(12, 22));
			jLabel111.setLocation(new Point(177, 92));
			jLabel111
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel714 = new JLabel();
			jLabel714.setText("报关编码");
			jLabel714.setSize(new Dimension(54, 22));
			jLabel714.setLocation(new Point(192, 58));
			jLabel714
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel713 = new JLabel();
			jLabel713.setText("制单号");
			jLabel713.setSize(new Dimension(54, 22));
			jLabel713.setLocation(new Point(375, 92));
			jLabel713
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel712 = new JLabel();
			jLabel712.setText("报关编码");
			jLabel712.setSize(new Dimension(54, 22));
			jLabel712.setLocation(new Point(4, 58));
			jLabel712
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel711 = new JLabel();
			jLabel711.setText("工厂料号");
			jLabel711.setSize(new Dimension(54, 22));
			jLabel711.setLocation(new Point(192, 23));
			jLabel711
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel71 = new JLabel();
			jLabel71.setText("工厂料号");
			jLabel71.setSize(new Dimension(54, 22));
			jLabel71.setLocation(new Point(3, 23));
			jLabel71
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel11 = new JLabel();
			jLabel11.setText("至");
			jLabel11.setSize(new Dimension(12, 22));
			jLabel11
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel11.setLocation(new Point(177, 58));
			jLabel10 = new JLabel();
			jLabel10.setText("单据日期");
			jLabel10.setSize(new Dimension(54, 22));
			jLabel10
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel10.setLocation(new Point(192, 92));
			jLabel9 = new JLabel();
			jLabel9.setText("报关规格");
			jLabel9.setSize(new Dimension(54, 22));
			jLabel9.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel9.setLocation(new Point(563, 58));
			jLabel8 = new JLabel();
			jLabel8.setText("工厂规格");
			jLabel8.setSize(new Dimension(54, 22));
			jLabel8.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel8.setLocation(new Point(563, 23));
			jLabel5 = new JLabel();
			jLabel5.setText("单据日期");
			jLabel5.setSize(new Dimension(54, 22));
			jLabel5.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel5.setLocation(new Point(2, 92));
			jLabel4 = new JLabel();
			jLabel4.setText("报关名称");
			jLabel4.setSize(new Dimension(54, 22));
			jLabel4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel4.setLocation(new Point(375, 58));
			jLabel3 = new JLabel();
			jLabel3.setText("工厂名称");
			jLabel3.setSize(new Dimension(54, 22));
			jLabel3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setLocation(new Point(375, 23));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(10, 10, 849, 159));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u5236\u5355\u53f7\u67e5\u8be2\u9009\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 14), Color.blue));
			jPanel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getTfFactoryMaterialName(), null);
			jPanel1.add(getTfCustomMaterialName(), null);
			jPanel1.add(getTfCustomMaterialSpec(), null);
			jPanel1.add(getTfFactoryMaterialSpec(), null);
			jPanel1.add(getCbFactoryName(), null);
			jPanel1.add(getCbCustomName(), null);
			jPanel1.add(getBtQuerry(), null);
			jPanel1.add(getBtprint(), null);
			jPanel1.add(getBtClose(), null);
			jPanel1.add(getCbStartDate(), null);
			jPanel1.add(getCbEndDate(), null);
			jPanel1.add(getBtFacotryMaterialName(), null);
			jPanel1.add(getBtCustomMaterialName(), null);
			jPanel1.add(getBtFactoryMaterialSpec(), null);
			jPanel1.add(getBtCustomMaterialSpec(), null);
			jPanel1.add(jLabel71, null);
			jPanel1.add(getTfFactoryNumber1(), null);
			jPanel1.add(getBtFactoryNumber1(), null);
			jPanel1.add(jLabel711, null);
			jPanel1.add(jLabel712, null);
			jPanel1.add(jLabel713, null);
			jPanel1.add(jLabel714, null);
			jPanel1.add(getTfCustomNumber1(), null);
			jPanel1.add(getTfFactoryNumber2(), null);
			jPanel1.add(getTfCustomNumber2(), null);
			jPanel1.add(getTfWorkOder(), null);
			jPanel1.add(getBtCustomNumber1(), null);
			jPanel1.add(getBtFactoryNumber2(), null);
			jPanel1.add(getBtCustomNumber2(), null);
			jPanel1.add(getBtWorkOrder(), null);
			jPanel1.add(jLabel111, null);
			jPanel1.add(jLabel112, null);
			jPanel1.add(getBtnRelation(), null);
		}
		return jPanel1;
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
	 * This method initializes tfFactoryMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryMaterialName() {
		if (tfFactoryMaterialName == null) {
			tfFactoryMaterialName = new JTextField();
			tfFactoryMaterialName.setLocation(new Point(431, 23));
			tfFactoryMaterialName.setSize(new Dimension(100, 22));
		}
		return tfFactoryMaterialName;
	}

	/**
	 * This method initializes tfCustomMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomMaterialName() {
		if (tfCustomMaterialName == null) {
			tfCustomMaterialName = new JTextField();
			tfCustomMaterialName.setLocation(new Point(431, 58));
			tfCustomMaterialName.setSize(new Dimension(100, 22));
		}
		return tfCustomMaterialName;
	}

	/**
	 * This method initializes tfCustomMaterialSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomMaterialSpec() {
		if (tfCustomMaterialSpec == null) {
			tfCustomMaterialSpec = new JTextField();
			tfCustomMaterialSpec.setLocation(new Point(618, 58));
			tfCustomMaterialSpec.setSize(new Dimension(100, 22));
		}
		return tfCustomMaterialSpec;
	}

	/**
	 * This method initializes tfFactoryMaterialSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryMaterialSpec() {
		if (tfFactoryMaterialSpec == null) {
			tfFactoryMaterialSpec = new JTextField();
			tfFactoryMaterialSpec.setLocation(new Point(618, 23));
			tfFactoryMaterialSpec.setSize(new Dimension(100, 22));
		}
		return tfFactoryMaterialSpec;
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
		if (jTable == null) {
			jTable = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						// editData();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes cbFactoryName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFactoryName() {
		if (cbFactoryName == null) {
			cbFactoryName = new JCheckBox();
			cbFactoryName.setText("按工厂名称汇总（库存）");
			cbFactoryName.setLocation(new Point(65, 125));
			cbFactoryName.setSize(new Dimension(162, 21));
			cbFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbCustomName.setSelected(false);
				}
			});
		}
		return cbFactoryName;
	}

	/**
	 * This method initializes cbCustomName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomName() {
		if (cbCustomName == null) {
			cbCustomName = new JCheckBox();
			cbCustomName.setText("按报关名称汇总（库存）");
			cbCustomName.setLocation(new Point(280, 122));
			cbCustomName.setSize(new Dimension(157, 21));
			cbCustomName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbFactoryName.setSelected(false);
				}
			});
		}
		return cbCustomName;
	}

	/**
	 * This method initializes btQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtQuerry() {
		if (btQuerry == null) {
			btQuerry = new JButton();
			btQuerry.setText("查询");
			btQuerry.setSize(new Dimension(84, 25));
			btQuerry.setMnemonic(KeyEvent.VK_UNDEFINED);
			btQuerry.setLocation(new Point(755, 23));
			btQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btQuerry;
	}

	public void queryData(){
		// 查询
		// 组织查询条件
		List<Condition> conditionsMaterial = new ArrayList<Condition>();
		List<Condition> conditionsBill = new ArrayList<Condition>();
		conditionsMaterial = getMaterialCondtions();
		conditionsBill = getBillCondtions();

		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		// 执行查询线程
		new Find(conditionsBill, conditionsMaterial, "",
				billDetail, cbFactoryName.isSelected(),
				cbCustomName.isSelected()).execute();

	
	}
	/**
	 * 返回单据条件
	 * 
	 * @return
	 */
	protected List<Condition> getBillCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 生效的单据
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));
		// 生效日期
		if (cbStartDate.getDate() != null) { // 开始日期
			Date date = cbStartDate.getDate();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", CommonVars.getBeginDate(date), null));
			System.out.println("date="+CommonVars.getBeginDate(date));
		}
		if (cbStartDate.getDate() != null) { // 结束日期
			Date date = cbEndDate.getDate();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", CommonVars.getEndDate(date), null));
		}
		// 制单号
		if (!tfWorkOder.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "productNo", "=",
					tfWorkOder.getText(), null));
		}
		return conditions;
	}

	/**
	 * 返回所查询折料后的料件条件
	 * 
	 * @return
	 */
	protected List<Condition> getMaterialCondtions() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 工厂名称
		if (!tfFactoryMaterialName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "materiel.factoryName", "=",
					tfFactoryMaterialName.getText(), null));
		}
		// 工厂规格
		if (!tfFactoryMaterialSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "materiel.factorySpec", "=",
					tfFactoryMaterialSpec.getText(), null));
		}
		// 报关名称
		if (!tfCustomMaterialName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "statCusNameRelationHsn.cusName", "=",
					tfCustomMaterialName.getText(), null));
		}
		// 报关规格
		if (!tfCustomMaterialSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "statCusNameRelationHsn.cusSpec", "=",
					tfCustomMaterialSpec.getText(), null));
		}
		// 范围工厂开始料号不等于空,结束料号为空时
		if (!tfFactoryNumber1.getText().trim().equals("")
				&& tfFactoryNumber2.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "materiel.ptNo", "=",
					tfFactoryNumber1.getText(), null));
		} else if (!tfFactoryNumber1.getText().trim().equals("")
				&& !tfFactoryNumber2.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "materiel.ptNo", ">=",
					tfFactoryNumber1.getText(), null));
			conditions.add(new Condition("and", null, "materiel.ptNo", "<=",
					tfFactoryNumber2.getText(), ")"));
		}

		// 范围报关编码开始不等于空,结束料号为空时
		if (!tfCustomNumber1.getText().trim().equals("")
				&& tfCustomNumber2.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "statCusNameRelationHsn.complex.code", "=",
					tfCustomNumber1.getText(), null));
		} else // 范围报关编码开始不等于空,结束料号不为空时
		if (!tfCustomNumber1.getText().trim().equals("")
				&& !tfCustomNumber2.getText().trim().equals("")) {
			conditions.add(new Condition("or", "(", "statCusNameRelationHsn.complex.code", ">=",
					tfCustomNumber1.getText(), null));
			conditions.add(new Condition("and", null, "statCusNameRelationHsn.complex.code", "<=",
					tfCustomNumber2.getText(), ")"));
		}
		return conditions;
	}

	class Find extends SwingWorker {
		// 查询条件
		List<Condition> conditionsMaterial = null;
		List<Condition> conditionsBill = null;
		// 排序
		String orderBy = null;
		// 查询目标表
		String billDetail = null;
		// 报关名称汇总
		Boolean isFactoryName = false;
		// 显示负数结存
		Boolean isCustomName = false;

		public Find(List<Condition> conditionsBill,
				List<Condition> conditionsMaterial, String orderBy,
				String billDetail, Boolean isFactoryName, Boolean isCustomName) {
			this.conditionsBill = conditionsBill;
			this.conditionsMaterial = conditionsMaterial;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			this.isFactoryName = isFactoryName;
			this.isCustomName = isCustomName;
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress.showProgressDialog(DgWorkOrderBOM.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			list = casAction.getWorkOrderBillDetail(new Request(CommonVars
					.getCurrUser()), conditionsBill, conditionsMaterial,
					billDetail, cbFactoryName.isSelected(), cbCustomName
							.isSelected());

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
		if (btprint == null) {
			btprint = new JButton();
			btprint.setText("打印");
			btprint.setSize(new Dimension(84, 25));
			btprint.setLocation(new Point(755, 58));
			btprint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenuPrint().show(btprint, 0, btprint.getHeight());

				}
			});

		}
		return btprint;
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
				JOptionPane.showMessageDialog(DgWorkOrderBOM.this, "没有列印的记录！",
						"提示！", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
					list);
			InputStream masterReportStream = DgMaterialThatDayBalance.class
					.getResourceAsStream("report/WorkOrderQueryReport.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			parameters.put("type", getMaterielType());
			parameters.put("starttime", cbStartDate.getDate());
			parameters.put("endtime",cbEndDate.getDate());
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
		try {
			List list = tableModel.getList();
			if (list == null || list.isEmpty()) {
				JOptionPane.showMessageDialog(DgWorkOrderBOM.this, "没有列印的记录！",
						"提示！", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(list);
			InputStream masterReportStream = DgMaterialThatDayBalance.class
					.getResourceAsStream("report/WorkOrderQueryReport1.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			parameters.put("type", getMaterielType());
			parameters.put("starttime", cbStartDate.getDate());
			parameters.put("endtime",cbEndDate.getDate());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes btClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtClose() {
		if (btClose == null) {
			btClose = new JButton();
			btClose.setText("关闭");
			btClose.setSize(new Dimension(84, 25));
			btClose.setLocation(new Point(755, 92));
			btClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btClose;
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
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						//成品
						list.add(addColumn("物料类别", "materielType", 100));
						list.add(addColumn("成品料号", "productPtPart", 100));
						list.add(addColumn("成品名称", "productPtName", 100));
						list.add(addColumn("成品规格", "productPtSpec", 100));
						list.add(addColumn("成品单位", "productPtUnit.name", 100));
						list.add(addColumn("成品数量", "productPtAmount", 100));
						list.add(addColumn("单位比例", "productUnitConvert.name", 100));
						//折料后的料件
						list.add(addColumn("单据日期", "date", 70));
						list.add(addColumn("单据类型", "billType", 100));
						list.add(addColumn("工厂料号", "ptPart", 100));
						list.add(addColumn("工厂名称", "ptName", 100));
						list.add(addColumn("工厂规格", "ptSpec", 100));
						list.add(addColumn("工厂单位", "ptUnit.name", 80));
						list.add(addColumn("报关名称", "hsName", 100));
						list.add(addColumn("报关规格", "hsSpec", 100));
						list.add(addColumn("报关单位", "hsUnit.name", 80));

						list.add(addColumn("单据号", "inBillNo", 100));
						list.add(addColumn("工厂入库数量", "ptIn", 80));
						list.add(addColumn("报关入库数量", "hsIn", 80));

						list.add(addColumn("单据号", "outBillNo", 100));
						list.add(addColumn("工厂出库数量", "ptOut", 80));
						list.add(addColumn("报关出库数量", "hsOut", 80));

						list.add(addColumn("工厂结存数量", "ptAmount", 80));
						list.add(addColumn("报关结存数量", "hsAmount", 80));

						list.add(addColumn("制单号", "bill.productNo", 80));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("进");
		g_in.add(cm.getColumn(17));
		g_in.add(cm.getColumn(18));
		g_in.add(cm.getColumn(19));

		ColumnGroup g_out = new ColumnGroup("出");
		g_out.add(cm.getColumn(20));
		g_out.add(cm.getColumn(21));
		g_out.add(cm.getColumn(22));

		ColumnGroup g_rs = new ColumnGroup("结存");
		g_rs.add(cm.getColumn(23));
		g_rs.add(cm.getColumn(24));
		
//		ColumnGroup g_product = new ColumnGroup("成品");
//		g_product.add(cm.getColumn(1));
//		
//		ColumnGroup g_material = new ColumnGroup("料件");
//		g_material.add(cm.getColumn(15));
//		g_material.add(g_in);
//		g_material.add(g_out);
//		g_material.add(g_rs);
		
		
		
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_rs);
		jTable.repaint();
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
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(1);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes cbStartDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbStartDate() {
		if (cbStartDate == null) {
			cbStartDate = new JCalendarComboBox();
			cbStartDate.setLocation(new Point(57, 92));
			cbStartDate.setSize(new Dimension(120, 22));
		}
		return cbStartDate;
	}

	/**
	 * This method initializes cbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbEndDate() {
		if (cbEndDate == null) {
			cbEndDate = new JCalendarComboBox();
			cbEndDate.setLocation(new Point(245, 92));
			cbEndDate.setSize(new Dimension(120, 22));
		}
		return cbEndDate;
	}

	/**
	 * This method initializes btFacotryMaterialName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtFacotryMaterialName() {
		if (btFacotryMaterialName == null) {
			btFacotryMaterialName = new JButton();
			btFacotryMaterialName.setText("...");
			btFacotryMaterialName.setSize(new Dimension(18, 22));
			btFacotryMaterialName.setLocation(new Point(531, 23));
			btFacotryMaterialName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object object = CasQuery.getInstance()
							.findPtNameFromBillDetail(MaterielType.MATERIEL);
							if (object != null) {
								tfFactoryMaterialName
										.setText((String) ((TempObject) object)
												.getObject());

							}
						}
					});
		}
		return btFacotryMaterialName;
	}

	/**
	 * This method initializes btCustomMaterialName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtCustomMaterialName() {
		if (btCustomMaterialName == null) {
			btCustomMaterialName = new JButton();
			btCustomMaterialName.setText("...");
			btCustomMaterialName.setSize(new Dimension(18, 22));
			btCustomMaterialName.setLocation(new Point(531, 58));
			btCustomMaterialName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							Object object = CasQuery.getInstance()
									.findHsNameFromStatCusNameRelationHsn(
											MaterielType.MATERIEL);
							if (object != null) {
								tfCustomMaterialName
										.setText((String) ((TempObject) object)
												.getObject());
								tfCustomMaterialSpec
										.setText((String) ((TempObject) object)
												.getObject1());
							}
						}
					});
		}
		return btCustomMaterialName;
	}

	/**
	 * This method initializes btFactoryMaterialSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtFactoryMaterialSpec() {
		if (btFactoryMaterialSpec == null) {
			btFactoryMaterialSpec = new JButton();
			btFactoryMaterialSpec.setText("...");
			btFactoryMaterialSpec.setSize(new Dimension(18, 22));
			btFactoryMaterialSpec.setLocation(new Point(719, 23));
			btFactoryMaterialSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object object = CasQuery.getInstance()
									.findPtSpecFromStatCusNameRelationMt(MaterielType.MATERIEL,tfFactoryMaterialName.getText());
							if (object != null) {
								tfFactoryMaterialSpec
										.setText((String) ((TempObject) object)
												.getObject());
							}
						}
					});
		}
		return btFactoryMaterialSpec;
	}

	/**
	 * This method initializes btCustomMaterialSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtCustomMaterialSpec() {
		if (btCustomMaterialSpec == null) {
			btCustomMaterialSpec = new JButton();
			btCustomMaterialSpec.setText("...");
			btCustomMaterialSpec.setSize(new Dimension(18, 22));
			btCustomMaterialSpec.setLocation(new Point(719, 58));
			btCustomMaterialSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							Object object = CasQuery.getInstance()
									.findHsSpecFromStatCusNameRelationHsn(
											MaterielType.MATERIEL,
											tfCustomMaterialName.getText());
							if (object != null) {
								tfCustomMaterialSpec
										.setText((String) ((TempObject) object)
												.getObject());
							}
						}
					});
		}
		return btCustomMaterialSpec;
	}

	/**
	 * This method initializes tfFactoryNumber1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryNumber1() {
		if (tfFactoryNumber1 == null) {
			tfFactoryNumber1 = new JTextField();
			tfFactoryNumber1.setSize(new Dimension(100, 22));
			tfFactoryNumber1.setLocation(new Point(57, 23));
			tfFactoryNumber1.getDocument().addDocumentListener(
					new DocumentListener() {
						private void setState() {
							String editAfterQueryValue = tfFactoryNumber1
									.getText().trim();
							if (!"".equalsIgnoreCase(editAfterQueryValue)) {
								tfFactoryNumber2.setEditable(true);
								btFactoryNumber2.setEnabled(true);
							} else {
								tfFactoryNumber2.setText(null);
								tfFactoryNumber2.setEditable(false);
								btFactoryNumber2.setEnabled(false);
							}
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
		return tfFactoryNumber1;
	}

	/**
	 * This method initializes btFactoryNumber1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtFactoryNumber1() {
		if (btFactoryNumber1 == null) {
			btFactoryNumber1 = new JButton();
			btFactoryNumber1.setText("...");
			btFactoryNumber1.setLocation(new Point(158, 23));
			btFactoryNumber1.setSize(new Dimension(18, 22));
			btFactoryNumber1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalation(false, MaterielType.MATERIEL,
									new ArrayList());
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFactoryNumber1.setText(m == null ? "" : m
										.getPtNo());
							}
						}
					});
		}
		return btFactoryNumber1;
	}

	/**
	 * This method initializes tfCustomNumber1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomNumber1() {
		if (tfCustomNumber1 == null) {
			tfCustomNumber1 = new JTextField();
			tfCustomNumber1.setSize(new Dimension(100, 22));
			tfCustomNumber1.setLocation(new Point(57, 58));
			tfCustomNumber1.getDocument().addDocumentListener(
					new DocumentListener() {
						private void setState() {
							String editAfterQueryValue = tfCustomNumber1
									.getText().trim();
							if (!"".equalsIgnoreCase(editAfterQueryValue)) {
								tfCustomNumber2.setEditable(true);
								btCustomNumber2.setEnabled(true);
							} else {
								tfCustomNumber2.setEditable(false);
								btCustomNumber2.setEnabled(false);
							}
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
		return tfCustomNumber1;
	}

	/**
	 * This method initializes tfFactoryNumber2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryNumber2() {
		if (tfFactoryNumber2 == null) {
			tfFactoryNumber2 = new JTextField();
			tfFactoryNumber2.setSize(new Dimension(100, 22));
			tfFactoryNumber2.setLocation(new Point(245, 23));
			tfFactoryNumber2.setEditable(false);
		}
		return tfFactoryNumber2;
	}

	/**
	 * This method initializes tfCustomNumber2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomNumber2() {
		if (tfCustomNumber2 == null) {
			tfCustomNumber2 = new JTextField();
			tfCustomNumber2.setSize(new Dimension(100, 22));
			tfCustomNumber2.setLocation(new Point(245, 58));
			tfCustomNumber2.setEditable(false);
		}
		return tfCustomNumber2;
	}

	/**
	 * This method initializes tfWorkOder
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWorkOder() {
		if (tfWorkOder == null) {
			tfWorkOder = new JTextField();
			tfWorkOder.setSize(new Dimension(100, 22));
			tfWorkOder.setLocation(new Point(431, 92));
		}
		return tfWorkOder;
	}

	/**
	 * This method initializes btCustomNumber1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtCustomNumber1() {
		if (btCustomNumber1 == null) {
			btCustomNumber1 = new JButton();
			btCustomNumber1.setText("...");
			btCustomNumber1.setLocation(new Point(158, 58));
			btCustomNumber1.setSize(new Dimension(18, 22));
			btCustomNumber1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object object = CasQuery.getInstance()
									.findHsComplexFromStatCusNameRelationHsn(
											MaterielType.MATERIEL);
							if (object != null) {
								tfCustomNumber1
										.setText((String) ((TempObject) object)
												.getObject3());

							}
						}
					});
		}
		return btCustomNumber1;
	}

	/**
	 * This method initializes btFactoryNumber2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtFactoryNumber2() {
		if (btFactoryNumber2 == null) {
			btFactoryNumber2 = new JButton();
			btFactoryNumber2.setText("...");
			btFactoryNumber2.setLocation(new Point(347, 23));
			btFactoryNumber2.setSize(new Dimension(18, 22));
			btFactoryNumber2.setEnabled(false);
			btFactoryNumber2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List<TempObject> list =  CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalation(false, MaterielType.MATERIEL,
									new ArrayList());
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFactoryNumber2.setText(m == null ? "" : m
										.getPtNo());
							}
						}
					});
		}
		return btFactoryNumber2;
	}

	/**
	 * This method initializes btCustomNumber2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtCustomNumber2() {
		if (btCustomNumber2 == null) {
			btCustomNumber2 = new JButton();
			btCustomNumber2.setText("...");
			btCustomNumber2.setLocation(new Point(347, 58));
			btCustomNumber2.setSize(new Dimension(18, 22));
			btCustomNumber2.setEnabled(false);
			btCustomNumber2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object object = CasQuery.getInstance()
									.findHsComplexFromStatCusNameRelationHsn(
											MaterielType.MATERIEL);
							if (object != null) {
								tfCustomNumber2
										.setText((String) ((TempObject) object)
												.getObject3());

							}
						}
					});
		}
		return btCustomNumber2;
	}

	/**
	 * This method initializes btWorkOrder
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtWorkOrder() {
		if (btWorkOrder == null) {
			btWorkOrder = new JButton();
			btWorkOrder.setMnemonic(KeyEvent.VK_UNDEFINED);
			btWorkOrder.setSize(new Dimension(18, 22));
			btWorkOrder.setLocation(new Point(531, 92));
			btWorkOrder.setText("...");
			btWorkOrder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance().findAllWorkOrder();
					if (object != null) {
						tfWorkOder.setText((String) ((TempObject) object)
								.getObject());

					}
				}
			});
		}
		return btWorkOrder;
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
		if(endDate != null){
			this.cbEndDate.setDate(endDate);
			if(startDate != null){
				this.cbStartDate.setDate(startDate);
			}else{
				this.cbStartDate.setDate(CommonVars.getEndDate(new Date(endDate.getYear(),0,1)));
			}
		}
		this.tfCustomNumber1.setText(hsCode);
		this.tfCustomNumber2.setText("");
		
		this.tfCustomMaterialName.setText(hsName);
		this.tfCustomMaterialSpec.setText(hsSpec);
		
		
		this.tfFactoryNumber1.setText(ptNo);
		this.tfFactoryNumber2.setText("");
		
		this.tfFactoryMaterialName.setText(ptName);
		this.tfFactoryMaterialSpec.setText(ptSpec);
	}

	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(755, 124, 84, 25));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgWorkOrderBOM.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					WorkOrderList b = (WorkOrderList)tableModel.getCurrentRow();
					sDate = cbEndDate.getDate();
//					sHsCode = b.getComplex().getCode();
					sHsName = b.getHsName();
					sHsSpec = b.getHsSpec();
					sPtNo = b.getPtPart();
					sPtName = b.getPtName();
					sPtSpec = b.getPtSpec();
					
					Component comp = (Component) e.getSource();
					getPmRelation().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnRelation;
	}
	
	
	/**
	 * This method initializes pmRelation	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();
			
			pmRelation.add(getMiPandianDifferent());//结存与盘点差额ok2010.06.28
			
		}
		return pmRelation;
	}
	
	/**
	 * This method initializes miPandianDifferent	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiPandianDifferent() {
		if (miPandianDifferent == null) {
			miPandianDifferent = new JMenuItem();
			miPandianDifferent.setText("在成品结存与盘点差额");
			miPandianDifferent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentDifferent dg=new DgCurrentDifferent();
					dg.setTitle("在成品结存与盘点差额表");
					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					dg.queryData();
					dg.setVisible(true);
					
				}
			});
		}
		return miPandianDifferent;
	}

}  //  @jve:decl-index=0:visual-constraint="10,257"
