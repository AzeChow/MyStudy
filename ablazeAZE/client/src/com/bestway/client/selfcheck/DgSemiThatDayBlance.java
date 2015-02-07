package com.bestway.client.selfcheck;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
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
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

public class DgSemiThatDayBlance extends JDialogBase {
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
	private JTextField tfFactoryName = null;
	private JCalendarComboBox cbDate = null;
	private JButton btnFactoryName = null;
	private JLabel lbFactorySpec = null;
	private JTextField tfFactorySpec = null;
	private JButton btnFactorySpec = null;
	private JLabel lbHsName = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JLabel lbHsSpec = null;
	private JTextField tfHsSpec = null;
	private JButton btnHsSpec = null;
	private JCheckBox cbSetName = null;
	private JCheckBox cbShowNegative = null;
	private JCheckBox cbFactoryByGroup = null;
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
	private CasAction casAction = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel21 = null;
	private JTextField tfPtPart = null;
	private JTextField tfPtPart1 = null;
	private JTextField tfCusPart = null;
	private JTextField tfCusPart1 = null;
	private JButton btnFactoryName1 = null;
	private JButton btnFactoryName2 = null;
	private JButton btnFactoryName3 = null;
	private JButton btnFactoryName4 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgSemiThatDayBlance() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
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
		this.setSize(791, 541);
        this.setContentPane(getJContentPane());
        List list = new ArrayList();
		jTable.setModel(initTable(list));
			
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
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(450, 84, 65, 22));
			jLabel21.setText("报关料号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(184, 84, 65, 22));
			jLabel2.setText("报关料号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(450, 30, 65, 22));
			jLabel1.setText("工厂料号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(184, 30, 65, 22));
			jLabel.setText("工厂料号");
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setBounds(new Rectangle(449, 111, 65, 22));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setBounds(new Rectangle(184, 111, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setBounds(new Rectangle(449, 57, 65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setBounds(new Rectangle(184, 138, 65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(184, 57, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(16, 16, 38, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(1, 200));
			jPanel1.add(lbFactory, null);
			jPanel1.add(lbFactoryName, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(lbDate, null);
			jPanel1.add(getTfFactoryName(), null);
			jPanel1.add(getBtnFactoryName(), null);
			jPanel1.add(lbFactorySpec, null);
			jPanel1.add(getTfFactorySpec(), null);
			jPanel1.add(getBtnFactorySpec(), null);
			jPanel1.add(lbHsName, null);
			jPanel1.add(getTfHsName(), null);
			jPanel1.add(getBtnHsName(), null);
			jPanel1.add(lbHsSpec, null);
			jPanel1.add(getTfHsSpec(), null);
			jPanel1.add(getBtnHsSpec(), null);
			jPanel1.add(getCbSetName(), null);
			jPanel1.add(getCbShowNegative(), null);
			jPanel1.add(getCbFactoryByGroup(), null);
			jPanel1.add(getBtnSelect(), null);
			jPanel1.add(getBtnMimeograph(), null);
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getTfPtPart(), null);
			jPanel1.add(getTfPtPart1(), null);
			jPanel1.add(getTfCusPart(), null);
			jPanel1.add(getTfCusPart1(), null);
			jPanel1.add(getBtnFactoryName1(), null);
			jPanel1.add(getBtnFactoryName2(), null);
			jPanel1.add(getBtnFactoryName3(), null);
			jPanel1.add(getBtnFactoryName4(), null);
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
			jScrollPane.setBounds(new Rectangle(15, 32, 157, 124));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setBounds(new Rectangle(249, 138, 135, 22));
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
	 * This method initializes tfFactoryName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfFactoryName() {
		if (tfFactoryName == null) {
			tfFactoryName = new JTextField();
			tfFactoryName.setBounds(new Rectangle(249, 57, 116, 22));
		}
		return tfFactoryName;
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
			btnFactoryName.setLocation(new Point(362, 57));
			btnFactoryName.setSize(new Dimension(24, 22));
			btnFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtNameFromStatCusNameRelationMt(materialType);
					if (object != null) {
						btnFactoryName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnFactoryName;
	}

	/**
	 * This method initializes tfFactorySpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfFactorySpec() {
		if (tfFactorySpec == null) {
			tfFactorySpec = new JTextField();
			tfFactorySpec.setBounds(new Rectangle(514, 57, 112, 22));
		}
		return tfFactorySpec;
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
			btnFactorySpec.setBounds(new Rectangle(625, 57, 24, 22));
			btnFactorySpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findPtSpecFromStatCusNameRelationMt(materialType,
									btnFactorySpec.getText());
					if (object != null) {
						btnFactorySpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnFactorySpec;
	}

	/**
	 * This method initializes tfHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(249, 111, 116, 22));
			
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnHsName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setText("...");
			btnHsName.setLocation(new Point(362, 111));
			btnHsName.setSize(new Dimension(24, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfHsName.setText((String) ((TempObject) object)
								.getObject());
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject1());
					}
				}
			});
		}
		return btnHsName;
	}

	/**
	 * This method initializes tfHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new Rectangle(514, 111, 112, 22));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes btnHsSpec	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHsSpec() {
		if (btnHsSpec == null) {
			btnHsSpec = new JButton();
			btnHsSpec.setText("...");
			btnHsSpec.setBounds(new Rectangle(625, 111, 24, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsSpecFromStatCusNameRelationHsn(materialType,
									tfHsName.getText());
					if (object != null) {
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsSpec;
	}

	/**
	 * This method initializes cbSetName	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbSetName() {
		if (cbSetName == null) {
			cbSetName = new JCheckBox();
			cbSetName.setText("报关名称汇总");
			cbSetName.setLocation(new Point(188, 165));
			cbSetName.setSize(new Dimension(107, 21));
		}
		return cbSetName;
	}

	/**
	 * This method initializes cbShowNegative	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbShowNegative() {
		if (cbShowNegative == null) {
			cbShowNegative = new JCheckBox();
			cbShowNegative.setText("显示负数结存");
			cbShowNegative.setLocation(new Point(351, 165));
			cbShowNegative.setSize(new Dimension(129, 21));
		}
		return cbShowNegative;
	}

	/**
	 * This method initializes cbFactoryByGroup	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbFactoryByGroup() {
		if (cbFactoryByGroup == null) {
			cbFactoryByGroup = new JCheckBox();
			cbFactoryByGroup.setText("按仓库分组");
			cbFactoryByGroup.setLocation(new Point(502, 164));
			cbFactoryByGroup.setSize(new Dimension(123, 21));
		}
		return cbFactoryByGroup;
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
			btnSelect.setSize(new Dimension(65, 23));
			btnSelect.setLocation(new Point(664, 27));
			btnSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {//查询
					//组织查询条件
					List<Condition> conditions = new ArrayList<Condition>();
					//生效的单据
					conditions.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));
					//生效日期(从当年开始至现在)
					if (cbDate.getDate() != null) { // 开始日期
						Date date = CommonVars.getEndDate(cbDate.getDate());
						
						Date startDate = CommonVars.getBeginDate(new Date(date.getYear(),1,1));
						conditions.add(new Condition("and", null,"billMaster.validDate", ">=",startDate, null));
						conditions.add(new Condition("and", null,"billMaster.validDate", "<=",cbDate.getDate(), null));
//						DateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//						System.out.println(fm.format(startDate));
//						System.out.println(fm.format(date));
					}
					//工厂名称
					if (!tfFactoryName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptName",
								"=", tfFactoryName.getText(), null));
					}
					//工厂规格
					if (!tfFactorySpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "ptSpec",
								"=", tfFactorySpec.getText(), null));
					}
					//报关名称
					if (!tfHsName.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsName",
								"=", tfHsName.getText(), null));
					}
					 //报关规格
					if (!tfHsSpec.getText().trim().equals("")) {
						conditions.add(new Condition("and", null, "hsSpec",
								"=", tfHsSpec.getText(), null));
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
						conditions.add(condition);
					}
					
//					String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
					//执行查询线程
					new Find(conditions,"",getMaterielType(),cbSetName.isSelected(),cbShowNegative.isSelected()).execute();
				}
			});
		}
		return btnSelect;
	}
	
	/**
	 * 在产品查询
	 * @author chensir
	 *
	 */
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditions = null;
        //排序
		String orderBy = null;
        //查询目标表
		String billDetail = null;
		//报关名称汇总
		Boolean ishsTaotal = false;
		//显示负数结存
		Boolean isShowLess = false;
		
		public Find(List<Condition> conditions, String orderBy,
				String billDetail,Boolean ishsTaotal,Boolean isShowLess) {
			this.conditions = conditions;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
//			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgSemiThatDayBlance.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
//			list = casAction.getCurrentBillDetailCurrDay(new Request(CommonVars.getCurrUser()), conditions,
//					orderBy,getMaterielType(),cbSetName.isSelected(),cbShowNegative.isSelected(),cbFactoryByGroup.isSelected());
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
			initTable(list);
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
			btnMimeograph.setLocation(new Point(665, 72));
			btnMimeograph.setSize(new Dimension(65, 23));
		
		}
		return btnMimeograph;
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
			btnClose.setLocation(new Point(665, 120));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.setSize(new Dimension(65, 23));
			
		}
		return btnClose;
	}
	private JTableListModel initTable(List list) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> columns = new Vector<JTableListColumn>();
							columns.add(addColumn("单据日期", "billMaster.validDate", 100));
							columns.add(addColumn("单据类型", "billMaster.billType.name", 100));
							columns.add(addColumn("产品名称", "", 100));
							columns.add(addColumn("产品规格", null, 100));
							columns.add(addColumn("工厂名称", "ptName", 100));
							columns.add(addColumn("工厂规格", "ptSpec", 100));
							columns.add(addColumn("工厂单位", "ptUnit.name", 100));
							columns.add(addColumn("报关名称", "hsName", 100));
							columns.add(addColumn("报关规格", "hsSpec", 100));
							columns.add(addColumn("报关单位", "hsUnit.name", 100));
							columns.add(addColumn("单耗", null, 100));
							columns.add(addColumn("损耗", null, 100));
							columns.add(addColumn("单项用量", null, 100));
							columns.add(addColumn("总用量", null, 100));
							columns.add(addColumn("折算系数", "hsAmount", 100));
							columns.add(addColumn("数量", "ptAmount", 100));
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
	 * This method initializes tfPtPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtPart() {
		if (tfPtPart == null) {
			tfPtPart = new JTextField();
			tfPtPart.setBounds(new Rectangle(249, 30, 116, 22));
			tfPtPart.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtPart.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfPtPart1.setEditable(true);
						btnFactoryName2.setEnabled(true);
					} else {
						tfPtPart1.setEditable(false);
						btnFactoryName2.setEnabled(false);
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
		return tfPtPart;
	}
	/**
	 * This method initializes tfPtPart1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtPart1() {
		if (tfPtPart1 == null) {
			tfPtPart1 = new JTextField();
			tfPtPart1.setBounds(new Rectangle(514, 30, 112, 22));
			tfPtPart1.setEditable(false);
		}
		return tfPtPart1;
	}
	/**
	 * This method initializes tfCusPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCusPart() {
		if (tfCusPart == null) {
			tfCusPart = new JTextField();
			tfCusPart.setBounds(new Rectangle(249, 84, 116, 22));
			tfCusPart.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfCusPart.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfCusPart1.setEditable(true);
						btnFactoryName4.setEnabled(true);
					} else {
						tfCusPart1.setEditable(false);
						btnFactoryName4.setEnabled(false);
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
		return tfCusPart;
	}
	/**
	 * This method initializes tfCusPart1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCusPart1() {
		if (tfCusPart1 == null) {
			tfCusPart1 = new JTextField();
			tfCusPart1.setBounds(new Rectangle(514, 84, 112, 22));
			tfCusPart1.setEditable(false);
		}
		return tfCusPart1;
	}
	/**
	 * This method initializes btnFactoryName1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName1() {
		if (btnFactoryName1 == null) {
			btnFactoryName1 = new JButton();
			btnFactoryName1.setBounds(new Rectangle(362, 30, 24, 22));
			btnFactoryName1.setText("...");
			btnFactoryName1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalation(false, getMaterielType(),
							new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfPtPart.setText(m == null ? "" : m.getPtNo());
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
			btnFactoryName2.setBounds(new Rectangle(625, 30, 24, 22));
			btnFactoryName2.setText("...");
			btnFactoryName2.setEnabled(false);
		}
		return btnFactoryName2;
	}
	/**
	 * This method initializes btnFactoryName3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName3() {
		if (btnFactoryName3 == null) {
			btnFactoryName3 = new JButton();
			btnFactoryName3.setBounds(new Rectangle(362, 84, 24, 22));
			btnFactoryName3.setText("...");
			btnFactoryName3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfCusPart.setText((String)((TempObject)object).getObject3());
					}
				}
			});
		}
		return btnFactoryName3;
	}
	/**
	 * This method initializes btnFactoryName4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFactoryName4() {
		if (btnFactoryName4 == null) {
			btnFactoryName4 = new JButton();
			btnFactoryName4.setBounds(new Rectangle(625, 84, 24, 22));
			btnFactoryName4.setText("...");
			btnFactoryName4.setEnabled(false);
			btnFactoryName4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(materialType);
					if (object != null) {
						tfCusPart1.setText((String)((TempObject)object).getObject3());
					}
				}
			});
		}
		return btnFactoryName4;
	}

}  //  @jve:decl-index=0:visual-constraint="-12,32"
