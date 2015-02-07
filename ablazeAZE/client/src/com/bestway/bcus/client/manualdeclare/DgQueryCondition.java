/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempComplex;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgQueryCondition extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbCommInfo = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private Contract contract = null;

	private EncAction encAction = null;

	private List lsResult = null;

	private boolean isImport;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup = null;

	private JLabel jLabel5 = null;

	private JComboBox jComboBox = null;

	private MaterialManageAction materialManageAction = null;

	private JCheckBox jCheckBox = null;

	private JRadioButton Radiotrue = null;

	private JRadioButton Radiofalse = null;

	private JRadioButton Radioall = null;

	private ButtonGroup buttonGroup1 = null;

	private boolean isSpecial = false;

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgQueryCondition() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 商品
		this.cbbCommInfo.setModel(new DefaultComboBoxModel(encAction
				.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()), isImport)
				.toArray()));
		this.cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 320));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCommInfo, "code", "name", 400);
		this.cbbCommInfo.setSelectedIndex(-1);
		// 客户
		this.cbbCustomer.setModel(new DefaultComboBoxModel(encAction
				.findCustomsDeclarationCustomer(
						new Request(CommonVars.getCurrUser()), isImport)
				.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "fname", 80, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "fname", 280);
		this.cbbCustomer.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if(this.isSpecial){
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT), "余料结转进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.MATERIAL_DOMESTIC_SALES), "海关批准内销"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			//hwy2012-11-07
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES), "边角料内销"));
		}else{
			if (isImport) {
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT), "余料结转进口"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转出口"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.MATERIAL_DOMESTIC_SALES), "海关批准内销"));
			} else {
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
				this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
						ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
				this.cbbImpExpType.addItem(new ItemProperty(String
						.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			}
		}
	
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);
		
		// 事业部
		fillProject();
//		List projectList = materialManageAction.findProjectDept(new Request(
//				CommonVars.getCurrUser(), true));
//		this.jComboBox
//				.setModel(new DefaultComboBoxModel(projectList.toArray()));
//		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
//				"code", "name"));
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
//				this.jComboBox, "code", "name");
//		jComboBox.setSelectedIndex(-1);
	}
	/**
	 * 填充事业部
	 */
	private void fillProject() {
		jComboBox.removeAllItems();
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < projectList.size(); i++) {
			ProjectDept billType = (ProjectDept) projectList.get(i);
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(billType.getCode());
			item.setName(billType.getName());
			jComboBox.addItem(item);
		}
		this.jComboBox.setSelectedItem(null);
		this.jComboBox.setRenderer(new CheckBoxListCellRenderer(jComboBox));
		this.jComboBox.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbBillTypeEvent(jComboBox);
	}
	
	

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
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
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(379, 298);
		this.setContentPane(getJContentPane());
		getButtonGroup();
		getButtonGroup1();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(167, 102, 47, 18));
			jLabel5.setForeground(java.awt.Color.red);
			jLabel5.setText("事业部");
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(41, 16, 93, 24);
			jLabel.setText("检索商品");
			jLabel1.setBounds(41, 44, 93, 24);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(41, 73, 93, 24);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(42, 130, 93, 24);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(233, 132, 13, 18);
			jLabel4.setText("至");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCommInfo(), null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbImpExpType(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getRadiotrue(), null);
			jContentPane.add(getRadiofalse(), null);
			jContentPane.add(getRadioall(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommInfo() {
		if (cbbCommInfo == null) {
			cbbCommInfo = new JComboBox();
			cbbCommInfo.setBounds(140, 16, 197, 22);
		}
		return cbbCommInfo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(140, 44, 197, 22);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(139, 73, 197, 22);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(141, 131, 89, 20);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(247, 131, 90, 19);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(43, 225, 105, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<String> deptCode=null;
					boolean isdept = true;
					if (jCheckBox.isSelected()) { // 分事业部
						// 事业部
						List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
						for (int j = 0; j < jComboBox.getModel().getSize(); j++) {
							CheckBoxListItem item = (CheckBoxListItem)jComboBox
									.getModel().getElementAt(j);
							if (item.getIsSelected()) {
								checkableItemList.add(item);
							}
						}
						if(checkableItemList.size()==0){
							JOptionPane.showMessageDialog(
									DgQueryCondition.this, "请选择事业部!", "提示", 2);
							return;
						}
						deptCode=new ArrayList<String>();
						for(int i=0;i<checkableItemList.size();i++){
							deptCode.add((checkableItemList.get(i)).getCode());
						}
						isdept = true;						
						new find(isdept, deptCode).start();
					} else {// 不分事业部
						isdept = false;
						new find(isdept, null).start();
					}
				}
			});
		}
		return jButton;
	}

	class find extends Thread {
		private List deptCode = null;

		private boolean isdept = false;

		public find(boolean isdept, List deptCode) {
			this.isdept = isdept;
			this.deptCode = deptCode;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgQueryCondition.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Integer seqNum = null;
				String customer = null;
				String impExpType = null;
				if (cbbCommInfo.getSelectedItem() != null) {// 序号
					seqNum = Integer.valueOf(((TempComplex) cbbCommInfo
							.getSelectedItem()).getCode());
				}
				if (cbbCustomer.getSelectedItem() != null) {// 客户
					customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
				}
				if (cbbImpExpType.getSelectedItem() != null) {// 类型
					impExpType = ((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode();
				}

				Date beginDate = cbbBeginDate
						.getDate();
				Date endDate = cbbEndDate.getDate();

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}				
				if (isSpecial) {
					if (jRadioButton.isSelected()) {// 按申报日期
						lsResult = encAction.findSpecialImpExpCommInfoList(new Request(
								CommonVars.getCurrUser()), isImport, seqNum,
								customer, impExpType, beginDate, endDate, true,
								isdept, deptCode, iseffect);
					} else if (jRadioButton1.isSelected()) {// 按结关日期
						lsResult = encAction.findSpecialImpExpCommInfoList(new Request(
								CommonVars.getCurrUser()), isImport, seqNum,
								customer, impExpType, beginDate, endDate, false,
								isdept, deptCode, iseffect);
					}
				}else{
					if (jRadioButton.isSelected()) {// 按申报日期
						lsResult = encAction.findImpExpCommInfoList(new Request(
								CommonVars.getCurrUser()), isImport, seqNum,
								customer, impExpType, beginDate, endDate,null, true,
								isdept, deptCode, iseffect);
					} else if (jRadioButton1.isSelected()) {// 按结关日期
						lsResult = encAction.findImpExpCommInfoList(new Request(
								CommonVars.getCurrUser()), isImport, seqNum,
								customer, impExpType, beginDate, endDate, null,false,
								isdept, deptCode, iseffect);
					}
				}
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgQueryCondition.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(230, 225, 111, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(46, 163, 113, 23));
			jRadioButton.setText("按申报日期查询");
			jRadioButton.setSelected(true);
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
			jRadioButton1.setBounds(new Rectangle(248, 161, 117, 23));
			jRadioButton1.setText("按结关日期查询");

		}
		return jRadioButton1;
	}

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
		}
		return buttonGroup;
	}

	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRadiofalse());
			buttonGroup1.add(getRadiotrue());
			buttonGroup1.add(getRadioall());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new java.awt.Rectangle(214, 101, 121, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(41, 101, 126, 21));
			jCheckBox.setForeground(java.awt.Color.red);
			jCheckBox.setText("是否分事业部统计");
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox.isSelected()) { // 分事业部
						jComboBox.setEnabled(true);
					} else { // 不分事业部
						jComboBox.setEnabled(false);
					}
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes Radiotrue
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadiotrue() {
		if (Radiotrue == null) {
			Radiotrue = new JRadioButton();
			Radiotrue.setBounds(new Rectangle(46, 190, 84, 21));
			Radiotrue.setText("已生效");
			Radiotrue.setSelected(true);
		}
		return Radiotrue;
	}

	/**
	 * This method initializes Radiofalse
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadiofalse() {
		if (Radiofalse == null) {
			Radiofalse = new JRadioButton();
			Radiofalse.setBounds(new Rectangle(151, 190, 75, 21));
			Radiofalse.setText("未生效");
		}
		return Radiofalse;
	}

	/**
	 * This method initializes Radioall
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadioall() {
		if (Radioall == null) {
			Radioall = new JRadioButton();
			Radioall.setBounds(new Rectangle(248, 191, 54, 21));
			Radioall.setText("全部");
		}
		return Radioall;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
