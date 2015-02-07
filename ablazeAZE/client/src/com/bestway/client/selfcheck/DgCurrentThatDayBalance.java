package com.bestway.client.selfcheck;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.TempCheckBalance;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.CheckableItem;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.io.InputStream;

public class DgCurrentThatDayBalance extends JDialogBase {
	private MaterialManageAction materialManageAction = null;
	private static String SELECT_ALL = "全选";  //  @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选";  //  @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JPanel top = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JLabel lbFactory = null;
	private JList lsFactory = null;
	private JLabel lbFactoryName = null;
	private JLabel lbDate = null;
	private JTextField tfPtName = null;
	private JCalendarComboBox cbDate = null;
	private JButton btnFactoryName = null;
	private JLabel lbFactorySpec = null;
	private JTextField tfPtSpec = null;
	private JButton btnFactorySpec = null;
	private JCheckBox cbByGroup = null;
	private JButton btnSelect = null;
	private JButton btnMimeograph = null;
	private JButton btnClose = null;
	private JTableListModel tableModel = null;
	
	/**
	 * 物料类型
	 */
	private String materielType = null;  //  @jve:decl-index=0:
	
	/**
	 * 查询action
	 */

	private CasCheckAction casCheckAction = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField tfPtNo1 = null;
	private JTextField tfPtNo2 = null;
	private JButton btnFactoryName1 = null;
	private JButton btnFactoryName2 = null;
	private JPanel pnSeach = null;
	private JButton btnRelation = null;
	
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="807,216"

	private JMenuItem miCurrentConvert = null;  //  @jve:decl-index=0:visual-constraint="811,257"
	
	
	private ButtonGroup group = null;  //  @jve:decl-index=0:
	
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;
	private JRadioButton rbDetail = null;
	private JRadioButton rbCheck = null;
	private JCheckBox cbHsTaotal = null;
	private JCheckBox cbShowLess = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public DgCurrentThatDayBalance() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
																		"casCheckAction");
		initialize();
		initUI();
	}
	private void initUI() {
		// 初始化仓库
		Vector<Object> vector = new Vector<Object>();
		List listWareSet = materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < listWareSet.size(); i++) {
			if (i == 0) {
				vector.add(new CheckableItemExtra(SELECT_ALL));
			}
			WareSet obj = (WareSet) listWareSet.get(i);
			CheckableItem item = new CheckableItem(obj.getCode(), obj.getName());
			vector.add(item);
		}
		this.lsFactory.setListData(vector);

	
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("在产品当日结存表");
		this.setSize(817, 541);
        this.setContentPane(getJContentPane());
        group = new ButtonGroup();
        group.add(getRbDetail());
		group.add(getRbCheck());
        List list = new ArrayList();
		jTable.setModel(initTableCheck(list));
			
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
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("工厂料号");
			jLabel1.setBounds(new Rectangle(264, 18, 65, 22));
			jLabel = new JLabel();
			jLabel.setText("工厂料号");
			jLabel.setBounds(new Rectangle(19, 18, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setBounds(new Rectangle(264, 42, 65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setBounds(new Rectangle(223, 109, 65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(19, 42, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(13, 20, 28, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(1, 150));
			jPanel1.add(lbFactory, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(lbDate, null);
			jPanel1.add(getCbByGroup(), null);
			jPanel1.add(getBtnSelect(), null);
			jPanel1.add(getBtnMimeograph(), null);
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(getPnSeach(), null);
			jPanel1.add(getBtnRelation(), null);
			jPanel1.add(getRbDetail(), null);
			jPanel1.add(getRbCheck(), null);
			jPanel1.add(getCbHsTaotal(), null);
			jPanel1.add(getCbShowLess(), null);
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
			jScrollPane.setBounds(new Rectangle(42, 24, 157, 113));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setBounds(new Rectangle(292, 109, 144, 22));
		}
		return cbDate;
	}
	class CheckableItem {
		private String code;

		private String name;

		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name + " (" + code + ")";
		}
	}
	/**
	 * This method initializes lsFactory	
	 * 	
	 * @return javax.swing.JList	
	 */
	class CheckableItemExtra {

		private boolean isSelected;

		private String name;
		public CheckableItemExtra(String name) {
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

	

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
	private JList getLsFactory() {
		if (lsFactory == null) {
			lsFactory = new JList();
			lsFactory.setCellRenderer(new CheckListRenderer());
			lsFactory.setFixedCellHeight(18);
			lsFactory.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = lsFactory.locationToIndex(e.getPoint());
					Object obj = lsFactory.getModel().getElementAt(index);
					if (obj instanceof CheckableItemExtra) {
						CheckableItemExtra item = (CheckableItemExtra) obj;
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							item.setName(SELECT_NOT_ALL);
						} else {
							item.setName(SELECT_ALL);
						}
						ListModel model = lsFactory.getModel();
						for (int i = 0; i < model.getSize(); i++) {
							Object o = model.getElementAt(i);
							if (o instanceof CheckableItem) {
								CheckableItem c = (CheckableItem) o;
								c.setSelected(item.isSelected());
							}
						}
						lsFactory.repaint();
					} else if (obj instanceof CheckableItem) {
						CheckableItem item = (CheckableItem) obj;
						item.setSelected(!item.isSelected());
						Rectangle rect = lsFactory.getCellBounds(index, index);
						lsFactory.repaint(rect);
					}
				}
			});
		}
		return lsFactory;
	}
	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());
			if (value instanceof CheckableItemExtra) {
				CheckableItemExtra item = (CheckableItemExtra) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(),Font.BOLD, getFont().getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(),Font.PLAIN, getFont().getSize()));
				setText("  " + item.getCode() + " (" + item.getName() + ")");
			}
			return this;
		}
	}
	/**
	 * This method initializes tfPtName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new Rectangle(90, 42, 116, 22));
		}
		return tfPtName;
	}

	/**
	 * This method initializes btnFactoryName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName() {
		if (btnFactoryName == null) {
			btnFactoryName = new JButton();
			btnFactoryName.setText("...");
			btnFactoryName.setBounds(new Rectangle(208, 42, 24, 22));
			btnFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
						.getFactoryAndFactualCustomsRalationForBalance(false, "currentTotal",
															new ArrayList(),"ptName");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfPtName.setText(m == null ? "" : m.getFactoryName());
					}
				
				}
			});
		}
		return btnFactoryName;
	}

	/**
	 * This method initializes tfPtSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtSpec() {
		if (tfPtSpec == null) {
			tfPtSpec = new JTextField();
			tfPtSpec.setBounds(new Rectangle(333, 42, 112, 22));
		}
		return tfPtSpec;
	}

	/**
	 * This method initializes btnFactorySpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactorySpec() {
		if (btnFactorySpec == null) {
			btnFactorySpec = new JButton();
			btnFactorySpec.setText("...");
			btnFactorySpec.setBounds(new Rectangle(448, 42, 24, 22));
			btnFactorySpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, "currentTotal",
														new ArrayList(),"ptSpec");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfPtSpec.setText(m == null ? "" : m.getFactorySpec());
					}
				
				}
			});
		}
		return btnFactorySpec;
	}

	/**
	 * This method initializes cbByGroup	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbByGroup() {
		if (cbByGroup == null) {
			cbByGroup = new JCheckBox();
			cbByGroup.setText("按仓库分组");
			cbByGroup.setLocation(new Point(539, 109));
			cbByGroup.setSize(new Dimension(123, 22));
			cbByGroup.setSelected(true);
		}
		return cbByGroup;
	}

	/**
	 * This method initializes btnSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSelect() {//在产品
		if (btnSelect == null) {
			btnSelect = new JButton();
			btnSelect.setText("查询");
			btnSelect.setSize(new Dimension(82, 25));
			btnSelect.setLocation(new Point(704, 25));
			btnSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnSelect;
	}
	
	
	
	public void queryData(){
		//查询
		//组织查询条件
		List<Condition> conditionsCheck = new ArrayList<Condition>();
		List<Condition> conditionsBill = new ArrayList<Condition>();
		conditionsBill.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));

		
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtNo1.getText().trim().equals("")
				&& tfPtNo2.getText().trim().equals("")) {
			conditionsCheck.add(new Condition("and", null, "ptNo",
					"=", tfPtNo1.getText(), null));
			
			conditionsBill.add(new Condition("and", null, "ptPart",
					"=", tfPtNo1.getText(), null));
		} 
		
		// 工厂料号不等于空,结束料号不为空时
		else if (!tfPtNo1.getText().trim().equals("")
				&& !tfPtNo2.getText().trim().equals("")) {
			conditionsCheck.add(new Condition("and", "(", "ptNo",
					">=", tfPtNo1.getText(), null));
			conditionsCheck.add(new Condition("and", null, "ptNo",
					"<=", tfPtNo2.getText(), ")"));
			
			conditionsBill.add(new Condition("and", "(", "ptPart",
					">=", tfPtNo1.getText(), null));
			conditionsBill.add(new Condition("and", null, "ptPart",
					"<=", tfPtNo2.getText(), ")"));
		}
		
		//生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date date = CommonVars.getEndDate(cbDate.getDate());
			Date beginDate = CommonUtils.getBeginDate(date);
			Date endDate = CommonUtils.getEndDate(date);
			
			Date startDate = CommonVars.getBeginDate(new Date(endDate.getYear(),0,1));
			
//			Date startDate = CommonVars.getBeginDate(new Date(date.getYear(),1,1));
			conditionsCheck.add(new Condition("and", null,"checkDate", ">=",beginDate, null));
			conditionsCheck.add(new Condition("and", null,"checkDate", "<=",endDate, null));

			conditionsBill.add(new Condition("and", null,"billMaster.validDate", ">=",startDate, null));
			conditionsBill.add(new Condition("and", null,"billMaster.validDate", "<=",endDate, null));

		}
		//工厂名称
		if (!tfPtName.getText().trim().equals("")) {
			conditionsCheck.add(new Condition("and", null, "ptName",
					"=", tfPtName.getText(), null));
			
			conditionsBill.add(new Condition("and", null, "ptName",
					"=", tfPtName.getText(), null));
		}
		//工厂规格
		if (!tfPtSpec.getText().trim().equals("")) {
			conditionsCheck.add(new Condition("and", null, "ptSpec",
					"=", tfPtSpec.getText(), null));
			
			conditionsBill.add(new Condition("and", null, "ptSpec",
					"=", tfPtSpec.getText(), null));
		}
		
		// 仓库
		List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
		for (int j = 0; j < lsFactory.getModel().getSize(); j++) {
			Object value = lsFactory.getModel().getElementAt(j);
			if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				if (item.isSelected) {
					checkableItemList.add(item);
				}
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckableItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(",
						"wareSet.code", "=", item.getCode().trim(),
						null);
			} else {
				condition = new Condition("or", null,
						"wareSet.code", "=", item.getCode().trim(),
						null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			conditionsCheck.add(condition);
			conditionsBill.add(condition);

		}
		
		//执行查询线程
		new Find(conditionsCheck,conditionsBill,"",
				cbByGroup.isSelected(),true,
				cbHsTaotal.isSelected(),cbShowLess.isSelected()).execute();
	
	}
	/**
	 * 在产品查询
	 * @author wss
	 *
	 */
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditionsCheck = null;
		List<Condition> conditionsBill = null;

        //排序
		String orderBy = null;
        
		boolean isGroup = true;
		
		boolean isFromCheck = true;
		
		boolean isHsTaotal = false;
		
		boolean isShowLess = false;
		
		public Find(List<Condition> conditionsCheck,List<Condition> conditionsBill, 
				String orderBy,
				boolean isGroup,boolean isFromCheck,boolean isHsTaotal,boolean isShowLess) {
			this.conditionsCheck = conditionsCheck;
			this.conditionsBill = conditionsBill;
			this.orderBy = orderBy;
			this.isGroup = isGroup;
			this.isFromCheck = isFromCheck;
			
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgCurrentThatDayBalance.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			list = casCheckAction.getCurrentCheckBalance(
					new Request(CommonVars.getCurrUser()), conditionsCheck,conditionsBill,
										orderBy,isGroup,isFromCheck,isHsTaotal,isShowLess);
			System.out.println("结果："+list.size());
			return list;
		}

		@Override
		protected void done() {//更新视图
			List list=null;
			try {
				list = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			if(rbDetail.isSelected()){
				initTableBill(list);
			}else{
				initTableCheck(list);
			}
			
		}
	}

	/**
	 * This method initializes btnMimeograph	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMimeograph() {
		if (btnMimeograph == null) {
			btnMimeograph = new JButton();
			btnMimeograph.setText("打印");
			btnMimeograph.setLocation(new Point(704, 55));
			btnMimeograph.setSize(new Dimension(82, 25));
			btnMimeograph.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print();
				}
			});
		
		}
		return btnMimeograph;
	}
	
	private void print(){

		try {
			List list = tableModel.getList();
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
					list);
	
			InputStream masterReportStream = DgCurrentDifferent.class
					.getResourceAsStream("report/dayBalanceCurrent.jasper");
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			parameters.put("date",cbDate.getDate());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(masterReportStream,
							parameters, ds);
			DgReportViewer viewer = new DgReportViewer(
					jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
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
			btnClose.setLocation(new Point(704, 85));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.setSize(new Dimension(82, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentThatDayBalance.this.dispose();
				}
			});
			
		}
		return btnClose;
	}
	private JTableListModel initTableCheck(List list) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> columns = new Vector<JTableListColumn>();
							columns.add(addColumn("工厂料号", "ptNo", 100));
							columns.add(addColumn("工厂名称", "ptName", 100));
							columns.add(addColumn("工厂型号规格", "ptSpec", 100));
							columns.add(addColumn("数量", "checkAmount", 100));
							columns.add(addColumn("工厂单位", "unitName", 100));
							if(cbByGroup.isSelected()){
								columns.add(addColumn("仓库", "wareSet.name", 100));
							}
							
							columns.add(addColumn("备注", "note", 200));
							return columns;
						}
					});
		return tableModel;
	}
	
	
	
	private JTableListModel initTableBill(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> columns = new Vector<JTableListColumn>();
//						if(!cbSetName.isSelected()){
							columns.add(addColumn("工厂料号", "ptPart", 100));
							columns.add(addColumn("工厂名称", "ptName", 100));
							columns.add(addColumn("工厂规格", "ptSpec", 100));
							columns.add(addColumn("工厂单位", "ptUnit.name", 100));
							columns.add(addColumn("工厂数量", "ptAmount", 100));
//						}
						columns.add(addColumn("报关编码", "complex.code", 100));
						columns.add(addColumn("报关名称", "hsName", 100));
						columns.add(addColumn("报关规格", "hsSpec", 100));
						columns.add(addColumn("报关单位", "hsUnit.name", 100));
						columns.add(addColumn("报关数量", "hsAmount", 100));
						//columns.add(addColumn("物料类别", "materialType", 100));
						if(cbByGroup.isSelected()){
							columns.add(addColumn("物料仓库", "wareSet.name", 100));
						}
						return columns;
					}
				});
	return tableModel;
}

	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
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
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
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
	 * This method initializes tfPtNo1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo1() {
		if (tfPtNo1 == null) {
			tfPtNo1 = new JTextField();
			tfPtNo1.setBounds(new Rectangle(90, 18, 116, 22));
//			tfPtNo1.getDocument().addDocumentListener(new DocumentListener() {
//				private void setState() {
//					String editAfterQueryValue = tfPtNo1.getText().trim();
//					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
//						tfPtNo2.setEditable(true);
//						btnFactoryName2.setEnabled(true);
//					} else {
//						tfPtNo2.setEditable(false);
//						btnFactoryName2.setEnabled(false);
//					}
//				}
//
//				public void insertUpdate(DocumentEvent e) {
//					setState();
//				}
//
//				public void removeUpdate(DocumentEvent e) {
//					setState();
//				}
//
//				public void changedUpdate(DocumentEvent e) {
//					setState();
//				}
//
//			});
		}
		return tfPtNo1;
	}
	/**
	 * This method initializes tfPtNo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo2() {
		if (tfPtNo2 == null) {
			tfPtNo2 = new JTextField();
			//tfPtPart1.setEditable(false);
			tfPtNo2.setBounds(new Rectangle(333, 18, 112, 22));
		}
		return tfPtNo2;
	}

	/**
	 * This method initializes btnFactoryName1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName1() {
		if (btnFactoryName1 == null) {
			btnFactoryName1 = new JButton();
			btnFactoryName1.setText("...");
			btnFactoryName1.setBounds(new Rectangle(208, 18, 24, 22));
			btnFactoryName1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, "currentTotal",
																new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfPtNo1.setText(m == null ? "" : m.getPtNo());
					}
				
				}
			});
		}
		return btnFactoryName1;
	}
	/**
	 * This method initializes btnFactoryName2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName2() {
		if (btnFactoryName2 == null) {
			btnFactoryName2 = new JButton();
			btnFactoryName2.setText("...");
			btnFactoryName2.setBounds(new Rectangle(448, 18, 24, 22));
			//btnFactoryName2.setEnabled(false);
			btnFactoryName2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, "currentTotal",
																new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfPtNo2.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnFactoryName2;
	}
	/**
	 * This method initializes pnSeach	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnSeach() {
		if (pnSeach == null) {
			pnSeach = new JPanel();
			pnSeach.setLayout(null);
			pnSeach.setBorder(BorderFactory.createTitledBorder(null,
					"在产品查询选项",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnSeach.setBounds(new Rectangle(204, 29, 489, 69));
			pnSeach.add(getTfPtNo1(), null);
			pnSeach.add(getBtnFactoryName1(), null);
			pnSeach.add(jLabel, null);
			pnSeach.add(jLabel1, null);
			pnSeach.add(getTfPtNo2(), null);
			pnSeach.add(getBtnFactoryName2(), null);
			pnSeach.add(getBtnFactorySpec(), null);
			pnSeach.add(getTfPtSpec(), null);
			pnSeach.add(lbFactorySpec, null);
			pnSeach.add(getBtnFactoryName(), null);
			pnSeach.add(getTfPtName(), null);
			pnSeach.add(lbFactoryName, null);
			
		}
		return pnSeach;
	}
	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(704, 115, 82, 25));
			btnRelation.setText("报关关联");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgCurrentThatDayBalance.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					TempCheckBalance b = (TempCheckBalance)tableModel.getCurrentRow();
					sDate = cbDate.getDate();
//					sHsCode = b.getComplex().getCode();
//					sHsName = b.getHsName();
//					sHsSpec = b.getHsSpec();
					sPtNo = b.getPtNo();
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
			
			pmRelation.add(getMiCurrentConvert());//在产品结存折料情况表
		}
		return pmRelation;
	}
	/**
	 * This method initializes miCurrentConvert	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiCurrentConvert() {
		if (miCurrentConvert == null) {
			miCurrentConvert = new JMenuItem();
			miCurrentConvert.setText("结存折料情况表");
			miCurrentConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentThatDayBalanceToBom dg = new DgCurrentThatDayBalanceToBom();
					
					dg.setState();
					dg.setQueryConditionF(sDate, sPtNo, sPtName, sPtSpec);
					
					dg.queryData();
					
					dg.setVisible(true);
					
				}
			});
		}
		return miCurrentConvert;
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
			this.cbDate.setDate(endDate);
		}
//		this.tfCusPart.setText(hsCode);
//		this.tfCusPart1.setText("");
//		this.tfHsName.setText(hsName);
//		this.tfHsSpec.setText(hsSpec);
//		
		
		this.tfPtNo1.setText(ptNo);
		this.tfPtNo2.setText("");
		this.tfPtName.setText(ptName);
		this.tfPtSpec.setText(ptSpec);
	}
	/**
	 * This method initializes rbDetail	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbDetail() {
		if (rbDetail == null) {
			rbDetail = new JRadioButton();
			rbDetail.setBounds(new Rectangle(341, 20, 140, 21));
			rbDetail.setText("数据来源于单据中心");
			rbDetail.setVisible(false);
			rbDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbHsTaotal.setEnabled(true);
					cbShowLess.setEnabled(true);
				}
			});
		}
		return rbDetail;
	}
	/**
	 * This method initializes rbCheck	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbCheck() {
		if (rbCheck == null) {
			rbCheck = new JRadioButton();
			rbCheck.setText("数据来源于盘点");
			rbCheck.setBounds(new Rectangle(194, 20, 144, 21));
			rbCheck.setSelected(true);
			rbCheck.setVisible(false);
			rbCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbHsTaotal.setEnabled(false);
					cbShowLess.setEnabled(false);
				}
			});
		}
		return rbCheck;
	}
	/**
	 * This method initializes cbHsTaotal	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbHsTaotal() {
		if (cbHsTaotal == null) {
			cbHsTaotal = new JCheckBox();
			cbHsTaotal.setBounds(new Rectangle(460, 131, 45, 15));
			cbHsTaotal.setText("报关名称汇总");
			cbHsTaotal.setEnabled(false);
			cbHsTaotal.setVisible(false);
		}
		return cbHsTaotal;
	}
	/**
	 * This method initializes cbShowLess	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbShowLess() {
		if (cbShowLess == null) {
			cbShowLess = new JCheckBox();
			cbShowLess.setBounds(new Rectangle(621, 131, 59, 16));
			cbShowLess.setText("显示负数结存");
			cbShowLess.setEnabled(false);
			cbShowLess.setVisible(false);
		}
		return cbShowLess;
	}

}  //  @jve:decl-index=0:visual-constraint="-12,32"
