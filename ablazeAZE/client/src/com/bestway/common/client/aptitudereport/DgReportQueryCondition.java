package com.bestway.common.client.aptitudereport;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.Rectangle;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.action.AptitudeReportAction;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JTristateCheckBox;
import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;



public class DgReportQueryCondition extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JComboBox cbbAimObject = null;

	private JLabel jLabel1 = null;

	private JTextField tfReportName = null;

	private JButton btnClose = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JList listFieldShow = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnAnd = null;

	private JButton btnOr = null;

	private JButton btnDel1 = null;

	private JComboBox cbbFieldName = null;

	private JComboBox cbbOperator = null;

	private JTextField tfValue = null;


	private int dataState = DataState.BROWSE;

	private AptitudeReportAction aptitudeReportAction = null;

	private SelectCondition selectCondition = null; // 表头 // @jve:decl-index=0:

	private Vector<ReportField> vectorSource = new Vector<ReportField>(); // @jve:decl-index=0:

	private Vector<ReportField> vectorDest = new Vector<ReportField>(); // @jve:decl-index=0:

	private Vector<FiltCondition> vectorCondition = new Vector<FiltCondition>(); // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private JTableListModel tableModelDetail = null;

	private ToolsAction toolsAction = null;

	private GroupValue groupValue = null; // @jve:decl-index=0:

	private boolean isMasterTable = true;

	private int dataStateReportQuery = DataState.BROWSE;

	private List<ReportField> reportFieldSourceList = new ArrayList<ReportField>(); // @jve:decl-index=0:

	private List<ReportField> reportFieldDestList = new ArrayList<ReportField>(); // @jve:decl-index=0:

	private List<ReportField> reportFieldDestListMod = new ArrayList<ReportField>();  //  @jve:decl-index=0:
	
	private List<ReportField> conditionTempList = new ArrayList<ReportField>(); //临时条件字段存储  //  @jve:decl-index=0:
	
	private List reportFieldList =  new ArrayList();  //  @jve:decl-index=0:

	private List<FiltCondition> addFiltConditionList = new ArrayList<FiltCondition>(); // @jve:decl-index=0:

	private List<FiltCondition> removeFiltConditionList = new ArrayList<FiltCondition>(); // @jve:decl-index=0:

	private List<StatTypeValue> statTypeValueList = new ArrayList<StatTypeValue>(); // @jve:decl-index=0:

	private JList listFiltCondition = null;

	private String logic = "and"; // @jve:decl-index=0:

	private String dataType = null;

	private JTree jTree = null;
	
	private JTree jTree1 = null;
	
	private String fieldEn = null;
	
	private JTree jTreeCombox = null;
	
	private CheckNode treeNode = new CheckNode();

	private int sign = 0;
	
	private Map<String, String> delReportFeild = new HashMap<String, String>();
	/**
	 * This is the default constructor
	 */
	public DgReportQueryCondition() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		aptitudeReportAction = (AptitudeReportAction) CommonVars
				.getApplicationContext().getBean("aptitudeReportAction");
		initialize();
		String className = null;
		int state = 1;
		setTreeModel(className,state);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(730, 450);
		this.setTitle("报表查询条件");
		this.setPreferredSize(new Dimension(730, 550));
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new Dimension(730, 550));
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel3(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(730, 36));
			jToolBar.add(getJPanel());
		}
		return jToolBar;
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
			jPanel.add(getJPanel1(), BorderLayout.WEST);
			jPanel.add(getJPanel2(), BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(234, 3, 57, 25));
			jLabel1.setText("报表名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 3, 53, 23));
			jLabel.setText("数据来源");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(500, 36));
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbAimObject(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfReportName(), null);
		}
		return jPanel1;
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
			jPanel2.setPreferredSize(new Dimension(200, 34));
			jPanel2.add(getBtnSave(), null);
			jPanel2.add(getBtnEdit(), null);
			jPanel2.add(getBtnClose(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbAimObject
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAimObject() {
		if (cbbAimObject == null) {
			cbbAimObject = new JComboBox();
			cbbAimObject.setBounds(new Rectangle(72, 3, 144, 26));
			//
			// model
			//

			cbbAimObject.addItem(new ItemProperty("  ", "  "));

			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailMateriel",
					"料件单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailProduct",
					"成品单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailFixture",
					"设备单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailHalfProduct",
					"半成品单据"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.cas.bill.entity.BillDetailRemainProduct",
					"残次品单据"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.bcus.cas.bill.entity.BillDetailLeftoverMateriel",
							"边角料单据"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo",
							"电子手册报关单"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo",
							"纸质手册报关单"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo",
					"电子帐册报关单"));
			cbbAimObject.addItem(new ItemProperty(
					"com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo",
					"电子帐册报关清单"));
			cbbAimObject
					.addItem(new ItemProperty(
							"com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo",
							"电子手册报关清单"));

			cbbAimObject.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object value = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(value);
					cbbFieldName.removeAllItems();
					try {
						Object obj = Class.forName(trageClassName)
								.newInstance();
						int index = cbbAimObject.getSelectedIndex();
						statTypeValueList = new ArrayList<StatTypeValue>();
						cbbFieldName.addItem(new ItemProperty("", ""));

						 
						reportFieldDestList = new ArrayList<ReportField>();
						reportFieldSourceList = new ArrayList<ReportField>();
						addFiltConditionList = new ArrayList<FiltCondition>();
						removeFiltConditionList = new ArrayList<FiltCondition>();
						vectorSource.removeAllElements();

						if (getDataState() == 1) {
							vectorDest.clear();
							int state = 1;
							setTreeModel(trageClassName,state);

						} else if (getDataState() == 2) {

							int state = 2;
							
							reportFieldDestListMod = getEditReportFieldDestList();
							setTreeModel(trageClassName,state);
							setReportList(reportFieldDestListMod);
							
							for (int j = 0; j < reportFieldDestListMod.size(); j++) {
						
								vectorDest.addElement(reportFieldDestListMod
										.get(j));
							}
						}

						List<ReportField> fieldNameList = getConditionTempList();
						for(int i=0;i<fieldNameList.size();i++){
			
							cbbFieldName.addItem(new ItemProperty(fieldNameList.get(i).getEnName(),
									fieldNameList.get(i).getCnName()));
						}
						

						setReportFieldDestList(reportFieldDestList);
						listFieldShow.setListData(vectorDest);
						listFieldShow
								.setCellRenderer(new FieldShowListCellRenderer());


					} catch (Exception ex) {
						ex.printStackTrace();

					}

					cbbAimObject.validate();
				}

			});

		}
		return cbbAimObject;
	}

	/**
	 * This method initializes tfReportName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReportName() {
		if (tfReportName == null) {
			tfReportName = new JTextField();
			tfReportName.setBounds(new Rectangle(297, 4, 182, 25));
		}
		return tfReportName;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(139, 5, 60, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgReportQueryCondition.this.dispose();
				}
			});
		}
		return btnClose;
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
			btnEdit.setBounds(new Rectangle(74, 5, 60, 25));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new Rectangle(9, 5, 60, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}

					List<ReportField> reportFieldDestList = getReportFieldDestList();

					List<FiltCondition> addFiltConditionList = getAddFiltConditionList();
					List<GroupValue> groupValueList = null;

					if (dataState == DataState.ADD) {
						SelectCondition selectCondition = new SelectCondition();
						GroupValue groupValue = new GroupValue();
						fillData(selectCondition);
						selectCondition = aptitudeReportAction
								.saveSelectCondition(new Request(CommonVars
										.getCurrUser()), selectCondition,
										reportFieldDestList,
										addFiltConditionList);
						DgReportQueryCondition.this.setSelect(selectCondition,
								groupValue);
						if (isMasterTable == true) {
							tableModel.addRow(selectCondition);
						}

					} else if (dataState == DataState.EDIT) {
						fillData(selectCondition);
						List<ReportField> edlitList = getEditReportFieldDestList();
						
						List<FiltCondition> filtConditionList = getFiltCondition();
						
						for (int i = 0; i < edlitList.size(); i++) {
							String enName = edlitList.get(i).getEnName();
							if(delReportFeild.get(enName)!=null){
								aptitudeReportAction
								.deleteReportField(new Request(CommonVars
										.getCurrUser()), edlitList.get(i));
							}

						}
						for (int i = 0; i < filtConditionList.size(); i++) {

							aptitudeReportAction.deleteFiltCondition(
									new Request(CommonVars.getCurrUser()),
									filtConditionList.get(i));

						}
						selectCondition = aptitudeReportAction
								.saveSelectCondition(new Request(CommonVars
										.getCurrUser()), selectCondition,
										reportFieldDestList,
										addFiltConditionList);

					}

					dataState = DataState.BROWSE;
					setState();
				}

			});

		}
		return btnSave;
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
			jPanel3.setPreferredSize(new Dimension(730, 514));
			jPanel3.add(getJPanel4(), BorderLayout.NORTH);
			jPanel3.add(getJPanel5(), BorderLayout.WEST);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(440, 8, 231, 18));
			jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel3.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel3.setText("显示的字段");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(89, 9, 224, 18));
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel2.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel2.setText("待选择的字段");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setPreferredSize(new Dimension(730, 214));
			jPanel4.setBorder(BorderFactory.createTitledBorder(null,
					"\u5b57\u6bb5\u663e\u793a\u9009\u62e9",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null,
					SystemColor.activeCaption));
			jPanel4.add(jLabel2, null);
			jPanel4.add(jLabel3, null);
			jPanel4.add(getJScrollPane(), null);
			jPanel4.add(getJScrollPane1(), null);
		}
		return jPanel4;
	}

	
	void showReportField(List list,boolean isClass){
		reportFieldDestList.clear();
		if(getDataState() == 2){
			List<ReportField> tempList = getReportList();
			for(int i=0;i<tempList.size();i++){
				vectorDest.addElement(tempList.get(i));
			}
		}
		for(int i =0;i<list.size();i++){
			CheckNode node =(CheckNode) list.get(i);
			Object object = ((DefaultMutableTreeNode) node).getUserObject();
			boolean isOrClass = node.getIsClass();
			fieldEn = getFieldEn(node,isOrClass);
			if (object instanceof TempNodeItem) {
				TempNodeItem temp = (TempNodeItem) object;
				ReportField reportField =new ReportField();
				reportField.setCnName(temp.getRemark());
				reportField.setClassname(temp.getClassName());
				reportField.setEnName(fieldEn);
				reportField.setFieldType(1);
				reportFieldDestList.add(reportField);				
				vectorDest.addElement(reportField);
			}
		}
		setReportFieldDestList(reportFieldDestList);



		this.listFieldShow.setListData(vectorDest);
		this.listFieldShow.setCellRenderer(new FieldListCellRenderer());

	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(89, 29, 224, 182));
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(440, 27, 233, 183));
			jScrollPane1.setViewportView(getListFieldShow());
		}
		return jScrollPane1;
	}

	class FieldListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((ReportField) value).getCnName();
			}
			setText(s);
			return this;
		}
	}

	class FieldShowListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((ReportField) value).getCnName();
			}
			setText(s);
			return this;
		}
	}

	
	/**
	 * This method initializes listFieldShow
	 * 
	 * @return javax.swing.JList
	 */
	private JList getListFieldShow() {
		if (listFieldShow == null) {
			listFieldShow = new JList();
		}
		return listFieldShow;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setPreferredSize(new Dimension(720, 170));
			jPanel5.setBorder(BorderFactory.createTitledBorder(null,
					"\u7b5b\u9009\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null,
					SystemColor.activeCaption));
			jPanel5.add(getJScrollPane2(), null);
			jPanel5.add(getBtnAnd(), null);
			jPanel5.add(getBtnOr(), null);
			jPanel5.add(getBtnDel1(), null);
			jPanel5.add(getCbbFieldName(), null);
			jPanel5.add(getCbbOperator(), null);
			jPanel5.add(getTfValue(), null);
			// jPanel5.add(getJButton11(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new Rectangle(444, 13, 229, 148));
			jScrollPane2.setViewportView(getListFiltCondition());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes btnAnd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAnd() {
		if (btnAnd == null) {
			btnAnd = new JButton();
			btnAnd.setBounds(new Rectangle(345, 21, 60, 29));
			btnAnd.setHorizontalAlignment(SwingConstants.LEADING);
			btnAnd.setText("and");
			btnAnd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (e.getSource() == btnAnd) {
						setLogic(btnAnd.getText());
					}

					if (checkNull()) {
						return;
					}

					String logic = getLogic();
					String fieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getName();
					String enFieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getCode();
					List<ReportField> list1 = getConditionTempList();
					List<ReportField> dateList = new ArrayList<ReportField>();
					for(int i=0;i<list1.size();i++){
						if(enFieldName.equals(list1.get(i).getEnName())){
							String dataType = list1.get(i).getClassname();
//							System.out.println(i+"=="+dataType+"     "+list1.get(i).getEnName());
							setDataType(dataType);
						}

//						if(list1.get(i).getClassname().equals("java.util.Date")){
//							 String dataType =	"java.util.Date";				
//							 setDataType(dataType);
//						java.util.Date     billMaster.validDate
//						}
					}

					String operator = ((ItemProperty) cbbOperator
							.getSelectedItem()).getCode();
					String cnOpertor = ((ItemProperty) cbbOperator
							.getSelectedItem()).getName();
					String value = tfValue.getText();
					String sqlSentence = logic + " " + fieldName + " "
							+ cnOpertor + " " + value;
					String enSqlSentence = logic + " (" + enFieldName + " "
							+ operator + " " + value + ")";

					Object classname = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(classname);
					try {

						PropertyDescriptor[] props;
						props = PropertyUtils.getPropertyDescriptors(Class
								.forName(trageClassName));
						for (int i = 0; i < props.length; i++) {
							Class clazz = props[i].getPropertyType();
							String tempField = props[i].getName();
							Object cls = props[i].getClass();
							if (tempField.equalsIgnoreCase(enFieldName)) {
								String dataType = clazz.getName();
								setDataType(dataType);
							}

						}

					} catch (ClassNotFoundException f) {
						f.printStackTrace();
					}

					dataType = getDataType();
					if("java.lang.Boolean".equals(dataType)){
						if("是".equals(value)){
							value = "true";
						}else if("否".equals(value)){
							value = "false";
						}
					}
					addFiltCondition(logic, fieldName, operator, value,
							sqlSentence, enSqlSentence, enFieldName, dataType);
					if("java.util.Date".equals(dataType)){
						   try{
							      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							      java.util.Date outDate = dateFormat.parse(value);
							      java.sql.Date simpleDate=new java.sql.Date(outDate.getTime());
							      value=simpleDate.toString();
							    }catch(Exception f){
							    	getValiDate();
							    	int i = getAddFiltConditionList().size()-1;
							    	List<FiltCondition> list = getAddFiltConditionList();
							    	FiltCondition condition = (FiltCondition) list.get(i);
							    	remove(condition);
							    } 
					}
					
					clearData();

				}
			});

		}
		return btnAnd;
	}

	/**
	 * This method initializes btnOr
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOr() {
		if (btnOr == null) {
			btnOr = new JButton();
			btnOr.setBounds(new Rectangle(345, 71, 60, 29));
			btnOr.setText("or");
			btnOr.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (e.getSource() == btnOr) {
						setLogic(btnOr.getText());
					}

					if (checkNull()) {
						return;
					}

					String logic = getLogic();
					String fieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getName();
					String enFieldName = ((ItemProperty) cbbFieldName
							.getSelectedItem()).getCode();
					String operator = ((ItemProperty) cbbOperator
							.getSelectedItem()).getCode();
					String cnOpertor = ((ItemProperty) cbbOperator
							.getSelectedItem()).getName();
					List<ReportField> list1 = getConditionTempList();
					List<ReportField> dateList = new ArrayList<ReportField>();
					for(int i=0;i<list1.size();i++){

						if(enFieldName.equals(list1.get(i).getEnName())){
							String dataType = list1.get(i).getClassname();
//							System.out.println(i+"=="+dataType+"     "+list1.get(i).getEnName());
							setDataType(dataType);
						}

//						if(list1.get(i).getClassname().equals("java.util.Date")){
//							 String dataType =	"java.util.Date";				
//							 setDataType(dataType);
//						java.util.Date     billMaster.validDate
//						}
					
//						if(list1.get(i).getClassname().equals("java.util.Date")){
//							 String dataType =	"java.util.Date";				
//							 setDataType(dataType);
//						}
					}
					String value = tfValue.getText(); 

					
					String sqlSentence = logic + " " + fieldName + " "
							+ cnOpertor + " " + value;
					String enSqlSentence = logic + " (" + enFieldName + " "
							+ operator + " " + value + ")";

					Object classname = ((ItemProperty) cbbAimObject
							.getSelectedItem()).getCode();
					String trageClassName = String.valueOf(classname);
					try {

						PropertyDescriptor[] props;
						props = PropertyUtils.getPropertyDescriptors(Class
								.forName(trageClassName));
						for (int i = 0; i < props.length; i++) {
							Class clazz = props[i].getPropertyType();
							String tempField = props[i].getName();
							Object cls = props[i].getClass();
							if (tempField.equalsIgnoreCase(enFieldName)) {
								String dataType = clazz.getName();
								setDataType(dataType);
							}

						}

					} catch (ClassNotFoundException f) {
						f.printStackTrace();
					}
					dataType = getDataType();
					if("java.lang.Boolean".equals(dataType)){
						if("是".equals(value)){
							value = "true";
						}else if("否".equals(value)){
							value = "false";
						}
					}
					
					addFiltCondition(logic, fieldName, operator, value,
							sqlSentence, enSqlSentence, enFieldName, dataType);
					if("java.util.Date".equals(dataType)){
						   try{
							      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							      java.util.Date outDate = dateFormat.parse(value);
							      java.sql.Date simpleDate=new java.sql.Date(outDate.getTime());
							      value=simpleDate.toString();
							    }catch(Exception f){
							    	getValiDate();
							    	int i = getAddFiltConditionList().size()-1;
							    	List<FiltCondition> list = getAddFiltConditionList();
							    	FiltCondition condition = (FiltCondition) list.get(i);
							    	remove(condition);
							    } 
					}
					clearData();

				}
			});
		}
		return btnOr;
	}


	/**
	 * This method initializes btnDel1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel1() {
		if (btnDel1 == null) {
			btnDel1 = new JButton();
			btnDel1.setBounds(new Rectangle(345, 118, 60, 29));
			btnDel1.setHorizontalTextPosition(SwingConstants.CENTER);
			btnDel1.setText("删除");
			btnDel1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					removeFiltCondition();
				}
			});
		}
		return btnDel1;
	}

	void addFiltCondition(String logic, String fieldName, String operator,
			String value, String sqlSentence, String enSqlSentence,
			String enFieldName, String dataType) {

		FiltCondition condition = new FiltCondition();
		condition.setBeginBracket("(");
		condition.setConditionType("0");
		condition.setDataType(dataType);
		condition.setEndBracket(")");
		condition.setFieldName(fieldName);
		condition.setLogic(logic);
		condition.setValue(value);
		condition.setOperator(operator);
		condition.setSqlSentence(sqlSentence);
		condition.setEnFieldName(enFieldName);
		condition.setEnSqlSentence(enSqlSentence);
		addFiltConditionList.add(condition);
		vectorCondition.addElement(condition);

		setAddFiltConditionList(addFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());
	}

	void removeFiltCondition() {
		if (this.listFiltCondition.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除的筛选条件!", "提示", 1);
			return;
		}

		Object[] removedField = this.listFiltCondition.getSelectedValues();
		for (int i = 0; i < removedField.length; i++) {
			FiltCondition condition = (FiltCondition) removedField[i];
			vectorCondition.removeElement(condition);
			removeFiltConditionList.add(condition);
			addFiltConditionList.remove(condition);

		}
		setAddFiltConditionList(addFiltConditionList);
		setRemoveFiltConditionList(removeFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());

	}

	class ConditionListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((FiltCondition) value).getSqlSentence();
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes cbbFieldName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFieldName() {
		if (cbbFieldName == null) {
			cbbFieldName = new JComboBox();
			cbbFieldName.setBounds(new Rectangle(90, 23, 218, 29));
			cbbFieldName.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});

		}

		return cbbFieldName;
	}

	/**
	 * This method initializes cbbOperator
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOperator() {
		if (cbbOperator == null) {
			cbbOperator = new JComboBox();
			cbbOperator.setBounds(new Rectangle(90, 73, 102, 29));
			cbbOperator.addItem(new ItemProperty("  ", "  "));
			cbbOperator.addItem(new ItemProperty("=", "等于"));
			cbbOperator.addItem(new ItemProperty(">", "大于"));
			cbbOperator.addItem(new ItemProperty(">=", "大于等于"));
			cbbOperator.addItem(new ItemProperty("<", "小于"));
			cbbOperator.addItem(new ItemProperty("<=", "小于等于"));
			cbbOperator.addItem(new ItemProperty("<>", "不等于"));
			cbbOperator.addItem(new ItemProperty("like", "模糊"));
			cbbOperator.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});

		}
		return cbbOperator;
	}

	/**
	 * This method initializes tfValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfValue() {
		if (tfValue == null) {
			tfValue = new JTextField();
			tfValue.setBounds(new Rectangle(90, 118, 195, 29));
			tfValue.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setState();
				}
			});
		}
		return tfValue;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */

	private boolean setEditFlag() {
		boolean editFlag = false;
		if (dataState == DataState.EDIT) {
			editFlag = true;
		}
		return editFlag;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	private void fillData(SelectCondition selectCondition) {

		selectCondition.setAimObjectValue(((ItemProperty) this.cbbAimObject
				.getSelectedItem()).getName());

		selectCondition.setAimObject(((ItemProperty) this.cbbAimObject
				.getSelectedItem()).getCode());

		selectCondition.setReportName(this.tfReportName.getText());
		selectCondition.setReportType(1);

	}

	public void setSelect(SelectCondition selectCondition, GroupValue groupValue) {
		this.selectCondition = selectCondition;
		this.groupValue = groupValue;

	}

	// private void setState() {
	//
	// }

	private void setReportFieldSourceList(
			List<ReportField> reportFieldSourceList) {

		this.reportFieldSourceList = reportFieldSourceList;
	}

	private void setAddFiltConditionList(
			List<FiltCondition> addFiltConditionList) {

		this.addFiltConditionList = addFiltConditionList;
	}

	private List<FiltCondition> getAddFiltConditionList() {
		return addFiltConditionList;
	}

	private void setRemoveFiltConditionList(
			List<FiltCondition> removeFiltConditionList) {

		this.removeFiltConditionList = removeFiltConditionList;
	}

	private List<FiltCondition> getRemoveFiltConditionList() {
		return removeFiltConditionList;
	}

	private void setReportFieldDestList(List<ReportField> reportFieldDestList) {

		this.reportFieldDestList = reportFieldDestList;
	}

	private List<ReportField> getReportFieldDestList() {
		return reportFieldDestList;
	}

	private boolean validateData() {

		if (tfReportName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "报表名称不能为空！", "提示！", 0);
			return true;
		}
		if (cbbAimObject.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "数据来源不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	/**
	 * This method initializes listFiltCondition
	 * 
	 * @return javax.swing.JList
	 */
	private JList getListFiltCondition() {
		if (listFiltCondition == null) {
			listFiltCondition = new JList();
		}
		return listFiltCondition;
	}

	private void setLogic(String logic) {
		this.logic = logic;
	}

	private String getLogic() {
		return logic;
	}

	private boolean checkNull() { // 检查是否为空
		if (cbbFieldName.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "字段名称不能为空！", "提示！", 0);
			return true;
		}
		if (tfValue.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "判断值不能为空！", "提示！", 0);
			return true;
		}
		if (cbbOperator.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "判断符号不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	private boolean checknull1() {
		if (this.cbbAimObject.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "数据来源不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	private void clearData() {
		tfValue.setText("");
		cbbFieldName.setSelectedItem("");
		cbbOperator.setSelectedItem(null);

	}


	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (dataState == DataState.EDIT) { // 编辑
				dataStateReportQuery = DataState.EDIT;
				Object temp = tableModel.getCurrentRow();
				if (temp != null) {
					if (temp instanceof SelectCondition) {
						selectCondition = (SelectCondition) temp;
						isMasterTable = true;
					} else if (temp instanceof FiltCondition) {
						selectCondition = ((FiltCondition) temp)
								.getSelectCondition();
						isMasterTable = false;
					}

				}
				setState();
				initUI(); // 初始化控件
				initUIData();// 初始化数据
			} else if (dataState == DataState.ADD) { // 新增
				dataStateReportQuery = DataState.ADD;

				
				initUI();
				setState();
			} else if (dataState == DataState.BROWSE) {
				Object temp = tableModel.getCurrentRow();
				if (temp != null) {
					if (temp instanceof SelectCondition) {
						selectCondition = (SelectCondition) temp;
						isMasterTable = true;
					} else if (temp instanceof FiltCondition) {
						selectCondition = ((FiltCondition) temp)
								.getSelectCondition();

						isMasterTable = false;
					}
				}
				initUI(); // 初始化控件
				initUIData();// 初始化数据
				setContainerEnabled(this, false);

			}

			initUIJList();

		}
		super.setVisible(isFlag);
	}

	private void initUI() {

		this.cbbAimObject.setUI(new CustomBaseComboBoxUI(250));

	}

	private void initUIData() {
		if (selectCondition.getAimObject() != null) {

			cbbAimObject.setSelectedIndex(ItemProperty.getIndexByCode(
					selectCondition.getAimObject(), cbbAimObject));
//			String className = selectCondition.getAimObject();
			
//			setTreeModel(className);
		} else {
			cbbAimObject.setSelectedItem(null);
		}

		tfReportName.setText(selectCondition.getReportName());

	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if ((component instanceof JButton)) {
				component.setEnabled(isFlag);
			} else if (component instanceof Container) {
				setContainerEnabled((Container) component, isFlag);
			}
		}
	}

	private void initUIJList() {
		if (selectCondition != null) {
			List<FiltCondition> filtConditionList = getFiltCondition();
			for (int i = 0; i < filtConditionList.size(); i++) {
				vectorCondition.addElement(filtConditionList.get(i));
			}
			setAddFiltConditionList(filtConditionList);
			this.listFiltCondition.setListData(vectorCondition);
			this.listFiltCondition
					.setCellRenderer(new ConditionListCellRenderer());

		}
	}



	private List<ReportField> getEditReportFieldDestList() {

		List<ReportField> reportFieldDestList = aptitudeReportAction
				.findReportField(new Request(CommonVars.getCurrUser()),
						selectCondition.getId());
		return reportFieldDestList;
	}

	private List<FiltCondition> getFiltCondition() {
		List<FiltCondition> filtConditionList = aptitudeReportAction
				.findFiltCondition(new Request(CommonVars.getCurrUser()),
						selectCondition.getId(), "1");
		return filtConditionList;
	}

	private void setDataType(String dataType) {
		this.dataType = dataType;
	}

	private String getDataType() {
		return dataType;
	}

	private void setState() {

		this.cbbAimObject.setEnabled(dataState == DataState.ADD);
		this.tfReportName.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbFieldName.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.cbbOperator.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.tfValue.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);
		this.btnSave.setEnabled(dataState == DataState.ADD
				|| dataState == DataState.EDIT);

	}
	
	private void getValiDate(){

				JOptionPane.showMessageDialog(this, "日期格式不正确！正确格式为"+
						"2006-01-01"+"!"+"请重新输入!!!",
						"提示！", 0);

	}
	
	private void remove(FiltCondition condition){
		vectorCondition.removeElement(condition);
		removeFiltConditionList.add(condition);
		addFiltConditionList.remove(condition);
		setAddFiltConditionList(addFiltConditionList);
		setRemoveFiltConditionList(removeFiltConditionList);
		this.listFiltCondition.setListData(vectorCondition);
		this.listFiltCondition.setCellRenderer(new ConditionListCellRenderer());

		
	}

	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();

		}
		return jTree;
	}


	
	
	class CheckNode extends DefaultMutableTreeNode {

		public final static int SINGLE_SELECTION = 0;

		public final static int DIG_IN_SELECTION = 4;

		protected int selectionMode;

		protected boolean isSelected;

		private JTristateCheckBox.State state = JTristateCheckBox.NOT_SELECTED;

		private Object aclPermission = null;

		private String id;

		private boolean isClass;
		
		public CheckNode() {
			this(null);
		}

		public CheckNode(Object userObject) {
			this(userObject, true, false);
		}

		public CheckNode(Object userObject, boolean allowsChildren,
				boolean isSelected) {
			super(userObject, allowsChildren);
			this.isSelected = isSelected;
			setSelectionMode(DIG_IN_SELECTION);
			id = UUID.randomUUID().toString();
		}

		public void setSelectionMode(int mode) {
			selectionMode = mode;
		}

		public int getSelectionMode() {
			return selectionMode;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;

			if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
				Enumeration enums = children.elements();
				while (enums.hasMoreElements()) {
					CheckNode node = (CheckNode) enums.nextElement();
					node.setSelected(isSelected);
					
				}
			}
		}

		public boolean isSelected() {
			return isSelected;
		}



		/**
		 * @return Returns the aclPermission.
		 */
		public Object getAclPermission() {
			return aclPermission;
		}

		/**
		 * @param aclPermission
		 *            The aclPermission to set.
		 */
		public void setAclPermission(Object aclPermission) {
			this.aclPermission = aclPermission;
		}

		/**
		 * @return Returns the state.
		 */
		public JTristateCheckBox.State getState() {
			return state;
		}

		/**
		 * @param state
		 *            The state to set.
		 */
		public void setState(JTristateCheckBox.State state) {
			this.state = state;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean equals(Object other) {
			if (other == null)
				return false;
			if (!(other.getClass().equals(this.getClass())))
				return false;
			CheckNode castOther = (CheckNode) other;
			if (this.getId() == null && castOther.getId() == null) {
				return super.equals(other);
			} else {
				return new EqualsBuilder().append(this.getId(),
						castOther.getId()).isEquals();
			}
		}

		public boolean getIsClass() {
			return isClass;
		}

		public void setIsClass(boolean isClass) {
			this.isClass = isClass;
		}
	}
	
	/**
	 * 
	 */
	private void setTreeModel(String className,int state) {
		conditionTempList.clear();
		Map<String, String> mapTree = new HashMap<String, String>();
		CheckNode root = new CheckNode("待选择的字段");
		List<TempNodeItem> list = this.toolsAction.getTempNodeItems();
		if(className!=null){
			
		for (int i=0;i<list.size();i++) {
			TempNodeItem temp = list.get(i);
			if(className.equalsIgnoreCase(temp.getClassName())){
				if(!isBasicType(className)){
					CheckNode node = new CheckNode(temp);
					TreeNode[] ss = node.getPath();
					
					iterate(node,state);
					 Enumeration treeNode = node.breadthFirstEnumeration();
					 int dex =1;
					  while(treeNode.hasMoreElements()){
						  DefaultMutableTreeNode defaultTree = (DefaultMutableTreeNode)treeNode.nextElement();
						  Object object = ((DefaultMutableTreeNode) defaultTree).getUserObject();
						  TreeNode[] fieldEnTemp = defaultTree.getPath();
						  
							StringBuffer fieldEnBuffer = new StringBuffer();
							String index = ".";
								if (object instanceof TempNodeItem){				
									TempNodeItem temp1 = (TempNodeItem) object;
									
									if(isBasicType(temp1.getClassName())&&
											!("id".equals(temp1.getEnName()))&&
											temp1.getRemark()!=null&&
											!("".equals(temp1.getRemark()))
											){
										
											for(int j =0;j<fieldEnTemp.length;j++){
												if(j>0){
													if(j==fieldEnTemp.length-1){
														index = "";
													}
													String field = "";
													field = fieldEnTemp[j]+index;
													fieldEnBuffer.append(field);
												}
				
											}
	
										fieldEn = fieldEnBuffer.toString();
										
										//com.bestway.bcus.cas.bill.entity.BillDetailMateriel
										ReportField condition =new ReportField();
										condition.setCnName(temp1.getRemark());
										condition.setEnName(fieldEn);
										condition.setClassname(temp1.getClassName());
										conditionTempList.add(condition);
										
									}

									if(!isBasicType(temp1.getClassName())&&
											temp1.getEnName()!=null&&
											temp1.getName().equals(temp1.getEnName())){
										
										for(int j =0;j<fieldEnTemp.length;j++){
											if(j>0){
												if(j==fieldEnTemp.length-1){
													index = "";
												}
												String field = "";
												field = fieldEnTemp[j]+index;
												fieldEnBuffer.append(field);
											}
			
										}

									fieldEn = fieldEnBuffer.toString()+"."+"name";
									
									//com.bestway.bcus.cas.bill.entity.BillDetailMateriel
									ReportField condition =new ReportField();
									condition.setCnName(temp1.getRemark());
									condition.setEnName(fieldEn);
									condition.setClassname(temp1.getClassName());
									conditionTempList.add(condition);
									}
								
									}
							
						   dex++;
						  }
					  setConditionTempList(conditionTempList);
					  
					root.add(node);
					
				}

			}
		}

		}

		DefaultTreeModel model = new DefaultTreeModel(root);
		jTree.setModel(model);
		jTree.expandRow(0);
		jTree.setRootVisible(false);
		ToolTipManager.sharedInstance().registerComponent(jTree);
		jTree.setCellRenderer(new CheckRenderer());	

		if (!checkMouseListener()) {
			jTree.addMouseListener(new NodeSelectionListener(jTree));
		}
//		setJTreeCombox(jTree);
	}
	private void setJTreeCombox(JTree jTreeCombox){
		this.jTreeCombox = jTreeCombox;
	}
	private JTree getJTreeCombox(){
		return this.jTreeCombox;
	}
	
	private void iterate(CheckNode parentNode,int state) {
		
		Object value = parentNode.getUserObject();
		if(value==null) return;
		String className = ((TempNodeItem) value).getClassName();
		
		
		try {
			if (isBasicType(className)) {
				return;
			}
			PropertyDescriptor[] props = PropertyUtils
					.getPropertyDescriptors(Class.forName(className));
			List<TempNodeItem> tempNodeItemList = new ArrayList<TempNodeItem>();


			
			for (int i = 0; i < props.length; i++) {

				String tempField = props[i].getName();
				if (tempField.equals("class")) {
					continue;
				}
			
				Class clazz = props[i].getPropertyType();
				
				
				TempNodeItem temp = new TempNodeItem();
				temp.setClassName(clazz.getName());
				temp.setName(tempField);
				temp.setCnName("");
				tempNodeItemList.add(temp);
//				tempNodeItemList1.add(temp);
			}
			tempNodeItemList = this.toolsAction.getAnnotate(tempNodeItemList,
					className);
			List<TempNodeItem> listTempNodeItem = new ArrayList<TempNodeItem>();
			Map<String, TempNodeItem> tniMap = new HashMap<String, TempNodeItem>();
			Map<String, String> tni2Map = new HashMap<String, String>();
			Map<String, String> tni3Map = new HashMap<String, String>();
			for(TempNodeItem tni :tempNodeItemList){
				tniMap.put(tni.getEnName(), tni);
				tni2Map.put(tni.getEnName(), tni.getRemark());
				tni3Map.put(tni.getEnName(), tni.getName());
				if(!"".equals(tni2Map.get(tni.getEnName()))
						&&tni2Map.get(tni.getEnName())!=null
						&&!"id".equals(tni3Map.get(tni.getEnName()))){
					listTempNodeItem.add(tni);
				}
				tniMap.clear();
				tni2Map.clear();
			}

			List<ReportField> list = getConditionTempList();
			for (TempNodeItem temp : listTempNodeItem) {
				
				CheckNode node = new CheckNode(temp);
				
				if(state ==2){
					reportFieldDestList = getEditReportFieldDestList();
				
					if(reportFieldDestList.size()!=0){
						for(int i=0;i<reportFieldDestList.size();i++){
						
							if(reportFieldDestList.get(i).getCnName().equals(temp.getRemark())){
								node.setState(JTristateCheckBox.SELECTED);
							}
						}
					}
				}
				sign++;


//com.bestway.bcus.cas.bill.entity.BillMasterMateriel
					if(!isBasicType(temp.getClassName())&&
							("com.bestway.bcus.cas.bill.entity.BillMasterMateriel"
									.equals(temp.getClassName())||
									"com.bestway.bcus.cas.bill.entity.BillMasterProduct"
									.equals(temp.getClassName())||
									"com.bestway.bcus.cas.bill.entity.BillMasterHalfProduct"
									.equals(temp.getClassName())||
									"com.bestway.bcus.cas.bill.entity.BillMasterRemainProduct"
									.equals(temp.getClassName())||
									"com.bestway.bcus.cas.bill.entity.BillMasterLeftoverMateriel"
									.equals(temp.getClassName())||
									"com.bestway.bcus.cas.bill.entity.BillMasterFixture"
									.equals(temp.getClassName())||
									"com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration"
									.equals(temp.getClassName())||
									"com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration"
									.equals(temp.getClassName())||
									"com.bestway.bcus.enc.entity.CustomsDeclaration"
									.equals(temp.getClassName())||
									"com.bestway.bcus.enc.entity.AtcMergeAfterComInfo"
									.equals(temp.getClassName())||
									"com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo"
									.equals(temp.getClassName())
							)){
						//com.bestway.bcus.cas.bill.entity.BillMaster
						
						iterate(node,state);
					}
				if(!isBasicType(temp.getClassName())){
					node.setIsClass(true);
				}
				parentNode.add(node);

				
//				tree.expandRow(0);
//				tree.setRootVisible(false);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	    }


	private boolean checkMouseListener() {
		MouseListener[] listeners = jTree.getMouseListeners();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof NodeSelectionListener) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 树Cell呈现
	 */
	
	class CheckRenderer
	  extends JPanel
	  implements TreeCellRenderer {
		protected JTristateCheckBox check;
		protected TreeLabel label;
		
		/**
		 * @return Returns the check.
		 */
		public JCheckBox getCheck() {
			return check;
		}

		/**
		 * @return Returns the label.
		 */
		public TreeLabel getLabel() {
			return label;
		}

	public CheckRenderer() {
	  setLayout(null);
	  add(check = new JTristateCheckBox());
	  add(label = new TreeLabel());
	  check.setBackground(UIManager.getColor("Tree.textBackground"));
	  label.setForeground(UIManager.getColor("Tree.textForeground"));
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
	                                boolean isSelected,
	                                boolean expanded, boolean leaf,
	                                int row,
	                                boolean hasFocus) {

			Object object = ((DefaultMutableTreeNode) value).getUserObject();
			CheckNode node = (CheckNode) value;

			check.setState(node.getState());
			

			label.setFont(tree.getFont()); 
			if (object instanceof TempNodeItem) {
				TempNodeItem temp = (TempNodeItem) object;
				label.setText(temp.getRemark());
				String className = temp.getClassName();
				
				
				if (!isBasicType(className)) {
					label.setForeground(Color.BLUE);
				}else{
					label.setForeground(Color.BLACK);
				}
				
			} else {
				if (object != null) {
					label.setText(object.toString());
				}
			}
			label.setSelected(isSelected);

			label.setFocus(hasFocus);
			if (leaf) {
				label.setIcon(UIManager.getIcon("Tree.leafIcon"));
			} else if (expanded) {
				label.setIcon(UIManager.getIcon("Tree.openIcon"));
			} else {
				label.setIcon(UIManager.getIcon("Tree.closedIcon"));
			}
			return this;
}
	

	public Dimension getPreferredSize() {
	  Dimension d_check = check.getPreferredSize();
	  Dimension d_label = label.getPreferredSize();
	  return new Dimension(d_check.width + d_label.width,
	                (d_check.height < d_label.height ? d_label.height
	                : d_check.height));
	}

	public void doLayout() {
	  Dimension d_check = check.getPreferredSize();
	  Dimension d_label = label.getPreferredSize();
	  int y_check = 0;
	  int y_label = 0;
	  if (d_check.height < d_label.height) {
	    y_check = (d_label.height - d_check.height) / 2;
	  }
	  else {
	    y_label = (d_check.height - d_label.height) / 2;
	  }
	  check.setLocation(0, y_check);
	  check.setBounds(0, y_check, d_check.width, d_check.height);
	  label.setLocation(d_check.width, y_label);
	  label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
	}

	public void setBackground(Color color) {
	  if (color instanceof ColorUIResource) {
	    color = null;
	  }
	  super.setBackground(color);
	}

	public class TreeLabel
	    extends JLabel {
	  boolean isSelected;

	  boolean hasFocus;

	  public TreeLabel() {
	  }

	  public void setBackground(Color color) {
	    if (color instanceof ColorUIResource) {
	    color = null;
	    }
	    super.setBackground(color);
	  }

	  public void paint(Graphics g) {
	    String str;
	    if ( (str = getText()) != null) {
	    if (0 < str.length()) {
	      if (isSelected) {
	        g.setColor(UIManager
	              .getColor("Tree.selectionBackground"));
	      }
	      else {
	        g.setColor(UIManager.getColor("Tree.textBackground"));
	      }
	      Dimension d = getPreferredSize();
	      int imageOffset = 0;
	      Icon currentI = getIcon();
	      if (currentI != null) {
	        imageOffset = currentI.getIconWidth()
	          + Math.max(0, getIconTextGap() - 1);
	      }
	      g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
	              d.height);
	      if (hasFocus) {
	        g.setColor(UIManager
	              .getColor("Tree.selectionBorderColor"));
	        g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
	              d.height - 1);
	      }
	    }
	    }
	    super.paint(g);
	  }

	  public Dimension getPreferredSize() {
	    Dimension retDimension = super.getPreferredSize();
	    if (retDimension != null) {
	    retDimension = new Dimension(retDimension.width + 3,
	                        retDimension.height);
	    }
	    return retDimension;
	  }

	  public void setSelected(boolean isSelected) {
	    this.isSelected = isSelected;
	  }

	  public void setFocus(boolean hasFocus) {
	    this.hasFocus = hasFocus;
	  }
	}
	}

	
	
	
	
	
	
	/**
	 * 是否是基本类型
	 * 
	 * @param cls
	 * @return
	 */
	private boolean isBasicType(String cls) {
		boolean isTrue = false;
		if (cls.equals(int.class.getName()) || cls.equals(long.class.getName())
				|| cls.equals(short.class.getName())
				|| cls.equals(double.class.getName())
				|| cls.equals(float.class.getName())
				|| cls.equals(boolean.class.getName())
				|| cls.equals(Integer.class.getName())
				|| cls.equals(Long.class.getName())
				|| cls.equals(Short.class.getName())
				|| cls.equals(Double.class.getName())
				|| cls.equals(Float.class.getName())
				|| cls.equals(String.class.getName())
				|| cls.equals(Boolean.class.getName())
				|| cls.equals(Date.class.getName())
				|| cls.equals(Calendar.class.getName())
				|| cls.equals(Set.class.getName())
				|| cls.equals(List.class.getName())
				|| cls.equals(Map.class.getName())
				|| cls.equals(HashMap.class.getName())
				|| cls.equals(HashSet.class.getName())) {
			isTrue = true;
		}
		return isTrue;
	}	
	
	

	
	
	class NodeSelectionListener extends MouseAdapter {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		public void mouseClicked(MouseEvent e) {
			if (dataState == DataState.BROWSE) {
				return;
			}
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);

			TreePath path = tree.getPathForRow(row);
			Rectangle pathBounds = tree.getPathBounds(path);
			if (path != null) {
				CheckNode node = (CheckNode) path.getLastPathComponent();
//				reportFieldDestList = getEditReportFieldDestList();
				
				Enumeration enume =  node.depthFirstEnumeration();
				List<CheckNode> nodeList = new ArrayList<CheckNode>();
				
				while(enume.hasMoreElements()){
					
					CheckNode node1 = (CheckNode) enume.nextElement();
					boolean isClass = node1.getIsClass();
				
					fieldEn = getFieldEn(node1,isClass);
					System.out.println(fieldEn);
				if(reportFieldDestList.size()!=0){
					for(int i=0;i<reportFieldDestList.size();i++){
						if(reportFieldDestList.get(i).getEnName().equals(fieldEn)){
							node1.setState(JTristateCheckBox.SELECTED);
						}
						
					}
				}
			
				}

				boolean isClass = node.getIsClass();
				CheckRenderer renderer = (CheckRenderer) tree.getCellRenderer()
						.getTreeCellRendererComponent(tree, node, true, true,
								true, 0, true);
				Object object = ((DefaultMutableTreeNode) node).getUserObject();
				if (object instanceof TempNodeItem) {
					TempNodeItem temp = (TempNodeItem) object;
				
				}
				Point point = renderer.getCheck().getParent().getLocation();
				int checkLen = renderer.getCheck().getWidth();
				if (x > pathBounds.x + checkLen) {
					return;
				}
				boolean isSelected = false;

				if (node.getState() == JTristateCheckBox.SELECTED
						|| node.getState() == JTristateCheckBox.DONT_CARE) {
					isSelected = false;
				} else {
					isSelected = true;
				}

				Object obj = node.getUserObject();

//					reportFieldDestList = getEditReportFieldDestList();
//					if(reportFieldDestList.size()!=0){
//					for(int i=0;i<reportFieldDestList.size();i++){
//						if(object.equals(reportFieldDestList.get(i).getEnName())){
//
//						}
//					}
//					}
//					node.setState(JTristateCheckBox.SELECTED);
//				System.out.println(obj+"    "+isSelected);
//				for(int j=0;j<nodeList.size();j++){
//					isSelected = true;
//					changeChildAclPermission(nodeList.get(j), isSelected);
//					}
				if (node.equals(jTree.getModel().getRoot())) {
					for (int i = 0; i < node.getChildCount(); i++) {
						
						CheckNode moduleNode = (CheckNode) node.getChildAt(i);
						CheckNode permissionNode = null;
						for (int j = 0; j < moduleNode.getChildCount(); j++) {

							permissionNode = (CheckNode) moduleNode
									.getChildAt(j);
							changeChildAclPermission(permissionNode, isSelected,isClass);
							setNodeState(permissionNode, isSelected);
							((DefaultTreeModel) tree.getModel())
									.nodeChanged(permissionNode);
						}
						setParentNodeState(permissionNode);
					}
				} else if (obj instanceof TempNodeItem) {
					
					if(node.getChildCount()==0){
						changeChildAclPermission(node, isSelected,isClass);
					}else{
						showNodeList(node,isSelected,isClass);
					}

				} 
				vectorDest.removeAllElements();

				showReportField(getNodeList(),isClass);
				setNodeState(node, isSelected);
				setParentNodeState(node);
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
				if (row == 0) {
					tree.revalidate();
					tree.repaint();
				}
			}

		}
		
		private void showNodeList(CheckNode node ,boolean isSelected,boolean isClass){
			for(int i=0;i<node.getChildCount();i++){
				CheckNode permissionNode = (CheckNode) node
				.getChildAt(i);
				if(permissionNode.getChildCount()==0){
					changeChildAclPermission(permissionNode, isSelected, isClass);
				}else{
					for(int j=0;j<permissionNode.getChildCount();j++){
						permissionNode = (CheckNode)permissionNode.getChildAt(j);
						showNodeList(permissionNode,isSelected,isClass);
					}
				}
				setNodeState(permissionNode, isSelected);
				((DefaultTreeModel) tree.getModel())
						.nodeChanged(permissionNode);
			}

		}

		/**
		 * @param node
		 * @param isSelected
		 */
		private void setNodeState(CheckNode node, boolean isSelected) {
			if (isSelected) {
				node.setState(JTristateCheckBox.SELECTED);
			} else {
				node.setState(JTristateCheckBox.NOT_SELECTED);
			}
		}

		/**
		 * @param node
		 * @param isSelected
		 */
		private void changeChildAclPermission(CheckNode node, boolean isSelected,boolean isClass) {
			if (isSelected) {
					reportFieldList.add(node);


			} else {
			
				fieldEn = getFieldEn(node,isClass);
				List<ReportField> list = reportFieldDestListMod;
				
				for(int i=0;i<list.size();i++){
					if(fieldEn.equals(list.get(i).getEnName())){
						list.remove(i);
					}
				}
				delReportFeild.put(fieldEn, fieldEn);
				setReportList(list);
				reportFieldList.remove(node);

			}
			
			setNodeList(reportFieldList);
		}
		
	}
	
	private String getFieldEn(CheckNode node,boolean isClass){
		String fieldEn = null;
		TreeNode[] fieldEnTemp = node.getPath();
		StringBuffer fieldEnBuffer = new StringBuffer();
		String index = ".";
		
		for(int i =0;i<fieldEnTemp.length;i++){
			if(i>1){
				if(i==fieldEnTemp.length-1&&!isClass){
					index = "";
				}
				String field = "";
				field = fieldEnTemp[i]+index;
				if(isClass&&i==fieldEnTemp.length-1){
					String field1 = "";
//					if(!("billMaster".equals(fieldEnTemp[fieldEnTemp.length-1].toString())||
//							"baseCustomsDeclaration".equals(fieldEnTemp[fieldEnTemp.length-1].toString())||
//							"afterComInfo".equals(fieldEnTemp[fieldEnTemp.length-1].toString()))){
						field1 = field+"name";
						fieldEnBuffer.append(field1);
					}else{
						fieldEnBuffer.append(field);
					}
				

				
			}
		}
		fieldEn = fieldEnBuffer.toString();
		return fieldEn;
	}
	
	private void setReportList(List<ReportField> list){
		this.reportFieldDestListMod = list;
	}
	private List<ReportField> getReportList(){
		return this.reportFieldDestListMod;
	}
	
	private void setNodeList(List list){
		this.reportFieldList = list;
	}
	private List getNodeList(){
		return this.reportFieldList;
	}

	private void setParentNodeState(CheckNode node) {
		if (node == null) {
			return;
		}
		if (node.equals(jTree.getModel().getRoot())) {
			return;
		}
		CheckNode parentNode = (CheckNode) node.getParent();
		int iSelectCount = 0;
		int iDontCareCount = 0;
		for (int i = 0; i < parentNode.getChildCount(); i++) {
			if (((CheckNode) parentNode.getChildAt(i)).getState() == JTristateCheckBox.SELECTED) {
				iSelectCount++;
			} else if (((CheckNode) parentNode.getChildAt(i)).getState() == JTristateCheckBox.DONT_CARE) {
				iDontCareCount++;
			}
		}
		if (iSelectCount > 0 && iSelectCount == parentNode.getChildCount()) {
			parentNode.setSelected(true);
			parentNode.setState(JTristateCheckBox.SELECTED);
		} else if (iSelectCount > 0
				&& iSelectCount < parentNode.getChildCount()
				|| iDontCareCount > 0) {
			parentNode.setSelected(true);
			parentNode.setState(JTristateCheckBox.DONT_CARE);
		} else if (iSelectCount == 0 && iDontCareCount == 0) {
			parentNode.setState(JTristateCheckBox.NOT_SELECTED);
		}
		((DefaultTreeModel) jTree.getModel()).nodeChanged(parentNode);
		setParentNodeState(parentNode);
	}
	
	public class JTreeComboBox extends JComboBox{
	    /**
	     * 显示用的树
	     */
	    private JTree fileTree;

	    public JTreeComboBox(){
	        this(new JTree());
	    }

	    public JTreeComboBox(JTree fileTree){
	        this.setTree(fileTree);
	    }

	    /**
	     * 设置树
	     * @param tree JTree
	     */
	    public void setTree(JTree fileTree){
	        this.fileTree = fileTree;
	        if(fileTree != null){
	            this.setSelectedItem(fileTree.getSelectionPath());
	            this.setRenderer(new JTreeComboBoxRenderer());
	        }
	        this.updateUI();
	    }

	    /**
	     * 取得树
	     * @return JTree
	     */
	    public JTree getTree(){
	        return fileTree;
	    }

	    /**
	     * 设置当前选择的树路径
	     * @param o Object
	     */
	    public void setSelectedItem(Object o){
	    	fileTree.setSelectionPath((TreePath)o);
	        getModel().setSelectedItem(o);
	    }

	    public void updateUI(){
	        ComboBoxUI cui = (ComboBoxUI)UIManager.getUI(this);
	        if(cui instanceof MetalComboBoxUI){
	            cui = new MetalJTreeComboBoxUI();
	        } else if(cui instanceof MotifComboBoxUI){
	            cui = new MotifJTreeComboBoxUI();
	        } else {
	            cui = new WindowsJTreeComboBoxUI();
	        }
	        setUI(cui);
	    }

	    // UI Inner classes -- one for each supported Look and Feel
	    class MetalJTreeComboBoxUI extends MetalComboBoxUI{
	        protected ComboPopup createPopup(){
	            return new TreePopup(comboBox);
	        }
	    }

	    class WindowsJTreeComboBoxUI extends WindowsComboBoxUI{
	        protected ComboPopup createPopup(){
	            return new TreePopup(comboBox);
	        }
	    }

	    class MotifJTreeComboBoxUI extends MotifComboBoxUI{
	        protected ComboPopup createPopup(){
	            return new TreePopup(comboBox);
	        }
	    }
	    /**
	     * <p>Title: OpenSwing</p>
	     * <p>Description: 树形结构而来的DefaultListCellRenderer </p>
	     * <p>Copyright: Copyright (c) 2004</p>
	     * <p>Company: </p>
	     * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	     * @version 1.0
	     */
	    class JTreeComboBoxRenderer extends DefaultListCellRenderer{
	        public Component getListCellRendererComponent(JList list, Object value,
	            int index, boolean isSelected, boolean cellHasFocus){
	            if(value != null){
	                TreePath path = (TreePath)value;
	                TreeNode node = (TreeNode)path.getLastPathComponent();
	                value = node;
	                TreeCellRenderer r = fileTree.getCellRenderer();
	                JLabel lb = (JLabel)r.getTreeCellRendererComponent(
	                		fileTree, value, isSelected, false, node.isLeaf(), index,
	                    cellHasFocus);
	                return lb;
	            }
	            return super.getListCellRendererComponent(list, value, index,
	                isSelected, cellHasFocus);
	        }
	    }
	}
	
	class TreePopup extends JPopupMenu implements ComboPopup{
	    protected JTreeComboBox comboBox;
	    protected JScrollPane scrollPane;

	    protected MouseMotionListener mouseMotionListener;
	    protected MouseListener mouseListener;
	    private MouseListener treeSelectListener = new MouseAdapter(){
	        public void mouseReleased(MouseEvent e){
	            JTree tree = (JTree)e.getSource();
	            TreePath tp = tree.getPathForLocation(e.getPoint().x,
	                                                  e.getPoint().y);
	            if(tp == null){
	                return;
	            }
	            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
	            TreeNode[] treeNode = node.getPath();
	            Object object = ((DefaultMutableTreeNode) node).getUserObject();
				if (object instanceof TempNodeItem) {
					TempNodeItem temp = (TempNodeItem) object;
					
				}
				
				StringBuffer fieldEnBuffer = new StringBuffer();
				String index = ".";
				for(int i =0;i<treeNode.length;i++){
					if(i>1){
						if(i==treeNode.length-1){
							index = "";
						}
						String field = "";
						field = treeNode[i]+index;
						fieldEnBuffer.append(field);
					}
				}
				fieldEn = fieldEnBuffer.toString();
//				comboBox.addItem(new ItemProperty("hsAmount",
//				"折算海关数量"));
	            comboBox.setSelectedItem(tp);
	            togglePopup();
	            MenuSelectionManager.defaultManager().clearSelectedPath();
	        }
	    };

	    public TreePopup(JComboBox comboBox){
	        this.comboBox = (JTreeComboBox)comboBox;
	        setBorder(BorderFactory.createLineBorder(Color.black));
	        setLayout(new BorderLayout());
	        setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
	        JTree tree = this.comboBox.getTree();
	        if(tree != null){
	            scrollPane = new JScrollPane(tree);
	            scrollPane.setBorder(null);
	            add(scrollPane, BorderLayout.CENTER);
	            tree.addMouseListener(treeSelectListener);
	        }
	    }

	    public void show(){
	        updatePopup();
	        show(comboBox, 0, comboBox.getHeight());
	        comboBox.getTree().requestFocus();
	    }

	    public void hide(){
	        setVisible(false);
	        comboBox.firePropertyChange("popupVisible", true, false);
	    }

	    protected JList list = new JList();
	    public JList getList(){
	        return list;
	    }

	    public MouseMotionListener getMouseMotionListener(){
	        if(mouseMotionListener == null){
	            mouseMotionListener = new MouseMotionAdapter(){};
	        }
	        return mouseMotionListener;
	    }

	    public KeyListener getKeyListener(){
	        return null;
	    }

	    public void uninstallingUI(){}

	    /**
	     * Implementation of ComboPopup.getMouseListener().
	     *
	     * @return a <code>MouseListener</code> or null
	     * @see ComboPopup#getMouseListener
	     */
	    public MouseListener getMouseListener(){
	        if(mouseListener == null){
	            mouseListener = new InvocationMouseHandler();
	        }
	        return mouseListener;
	    }

	    protected void togglePopup(){
	        if(isVisible()){
	            hide();
	        } else{
	            show();
	        }
	    }

	    protected void updatePopup(){
	        setPreferredSize(new Dimension(comboBox.getSize().width, 200));
	        Object selectedObj = comboBox.getSelectedItem();
	        if(selectedObj != null){
	            TreePath tp = (TreePath)selectedObj;
	            ((JTreeComboBox)comboBox).getTree().setSelectionPath(tp);
	        }
	    }

	    protected class InvocationMouseHandler extends MouseAdapter{
	        public void mousePressed(MouseEvent e){
	            if(!SwingUtilities.isLeftMouseButton(e) || !comboBox.isEnabled()){
	                return;
	            }
	            if(comboBox.isEditable()){
	                Component comp = comboBox.getEditor().getEditorComponent();
	                if((!(comp instanceof JComponent)) ||
	                   ((JComponent)comp).isRequestFocusEnabled()){
	                    comp.requestFocus();
	                }
	            } else if(comboBox.isRequestFocusEnabled()){
	                comboBox.requestFocus();
	            }
	            togglePopup();
	        }
	    }
	}
	
private void setConditionTempList(List<ReportField> conditionTempList){
	this.conditionTempList = conditionTempList;
}

private List<ReportField> getConditionTempList(){
	return conditionTempList;
}
	
}

