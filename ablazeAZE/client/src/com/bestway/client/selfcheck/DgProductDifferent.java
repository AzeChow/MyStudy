package com.bestway.client.selfcheck;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.selfcheck.DgMaterialDifferent.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.CheckableItem;
import com.bestway.common.materialbase.action.MaterialManageAction;
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

public class DgProductDifferent extends JDialogBase {
	private MaterialManageAction materialManageAction = null;
	private static String SELECT_ALL = "全选";  //  @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选";  //  @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel top = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private JLabel lbFactory = null;
	private JList lsFactory = null;
	private JLabel lbType = null;
	private JLabel lbFactoryName = null;
	private JLabel lbDate = null;
	private JComboBox cbTpye = null;
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
	/**
	 * This method initializes 
	 * 
	 */
	public DgProductDifferent() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		
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
		this.setTitle("产成品差额表");
		this.setSize(791, 541);
        this.setContentPane(getJContentPane());
        Vector list=new Vector();
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		list.add(null);
		jTable.setModel(initTableModel(list));
			
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(165);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerSize(8);
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
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setLocation(new Point(450, 90));
			lbHsSpec.setSize(new Dimension(60, 18));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setLocation(new Point(186, 90));
			lbHsName.setSize(new Dimension(58, 18));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setLocation(new Point(450, 60));
			lbFactorySpec.setSize(new Dimension(61, 19));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setLocation(new Point(450, 30));
			lbDate.setSize(new Dimension(63, 18));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setLocation(new Point(185, 60));
			lbFactoryName.setSize(new Dimension(58, 17));
			lbType = new JLabel();
			lbType.setText("物料类别");
			lbType.setLocation(new Point(185, 30));
			lbType.setSize(new Dimension(57, 18));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(16, 16, 38, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.add(lbFactory, null);
			jPanel1.add(lbType, null);
			jPanel1.add(lbFactoryName, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(lbDate, null);
			jPanel1.add(getCbTpye(), null);
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
			cbDate.setSize(new Dimension(135, 20));
			cbDate.setLocation(new Point(510, 30));
		}
		return cbDate;
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
	 * This method initializes cbTpye	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbTpye() {
		if (cbTpye == null) {
			cbTpye = new JComboBox();
			cbTpye.setSize(new Dimension(135, 20));
			cbTpye.setPreferredSize(new Dimension(29, 30));
			cbTpye.setLocation(new Point(244, 30));
		}
		return cbTpye;
	}

	/**
	 * This method initializes tfFactoryName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfFactoryName() {
		if (tfFactoryName == null) {
			tfFactoryName = new JTextField();
			tfFactoryName.setSize(new Dimension(115, 20));
			tfFactoryName.setLocation(new Point(245, 60));
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
			btnFactoryName.setLocation(new Point(360, 60));
			btnFactoryName.setSize(new Dimension(18, 20));
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
			tfFactorySpec.setSize(new Dimension(115, 20));
			tfFactorySpec.setLocation(new Point(510, 60));
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
			btnFactorySpec.setLocation(new Point(628, 60));
			btnFactorySpec.setSize(new Dimension(18, 20));
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
			tfHsName.setSize(new Dimension(115, 20));
			tfHsName.setLocation(new Point(245, 89));
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
			btnHsName.setLocation(new Point(359, 89));
			btnHsName.setSize(new Dimension(18, 20));
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
			tfHsSpec.setSize(new Dimension(115, 20));
			tfHsSpec.setLocation(new Point(510, 90));
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
			btnHsSpec.setLocation(new Point(628, 90));
			btnHsSpec.setSize(new Dimension(18, 20));
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
			cbSetName.setLocation(new Point(190, 122));
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
			cbShowNegative.setLocation(new Point(353, 122));
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
			cbFactoryByGroup.setLocation(new Point(504, 121));
			cbFactoryByGroup.setSize(new Dimension(123, 21));
		}
		return cbFactoryByGroup;
	}

	/**
	 * This method initializes btnSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSelect() {
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
					//生效日期
					if (cbDate.getDate() != null) { // 开始日期
						Date date = cbDate.getDate();
						date.setHours(0);
						date.setMinutes(0);
						date.setSeconds(0);
						conditions.add(new Condition("and", null,"billMaster.validDate", ">=",CommonVars.getBeginDate(cbDate.getDate()), null));
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
					
					String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
					//执行查询线程
					new Find(conditions,"",billDetail,cbSetName.isSelected(),cbShowNegative.isSelected()).execute();
				}
			});
		}
		return btnSelect;
	}
	
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
			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgProductDifferent.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
//			list = casAction.getCurrentBillDetail(new Request(CommonVars.getCurrUser()), conditions,
//					orderBy,getMaterielType(),cbSetName.isSelected(),cbShowNegative.isSelected(),cbFactoryByGroup.isSelected());
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
			initTableModel(list);
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
	private JTableListModel initTableModel(List list) {
		if (tableModel == null) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("工厂商品名称", null, 100));
							list.add(addColumn("工厂商品规格", null, 100));
							list.add(addColumn("工厂单位", null, 100));
							list.add(addColumn("工厂账存数量", null, 100));
							list.add(addColumn("工厂盘点数量", null, 100));
							list.add(addColumn("工厂数量差异", null, 100));
							list.add(addColumn("报关商品名称", null, 100));
							list.add(addColumn("报关商品规格", null, 100));
							list.add(addColumn("报关单位", null, 100));
							list.add(addColumn("报关账存数量", null, 100));
							list.add(addColumn("报关盘点数量", null, 100));
							list.add(addColumn("报关数量差异", null, 100));
							list.add(addColumn("物料类别", null, 100));
							list.add(addColumn("物料仓位", null, 100));
							list.add(addColumn("制单号", null, 100));
							return list;
						}
					});
		}
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
}  //  @jve:decl-index=0:visual-constraint="-12,32"
