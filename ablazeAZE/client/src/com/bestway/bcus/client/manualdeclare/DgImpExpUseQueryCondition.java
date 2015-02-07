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
import javax.swing.JPanel;
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
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgImpExpUseQueryCondition extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	JComboBox cbbCommInfo = null;
	JComboBox cbbCustomer = null;
	JComboBox cbbImpExpType = null;
	JCalendarComboBox cbbBeginDate = null;
	JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel4 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private Contract contract = null;
	private EncAction encAction = null;
	private List lsResult = null;
	private boolean isImport;
	private boolean isImg;
	private ButtonGroup buttonGroup1 = null;  //  @jve:decl-index=0:
	private ButtonGroup buttonGroup2 = null;  //  @jve:decl-index=0:
	JRadioButton jRadioButton = null;
	JRadioButton jRadioButton1 = null;
	private JLabel jLabel5 = null;
	private JComboBox jComboBox = null;
	private MaterialManageAction materialManageAction = null;
	private JCheckBox jCheckBox = null;
	private JPanel jPanel = null;
	JRadioButton Radiotrue = null;
	JRadioButton Radiofalse = null;
	JRadioButton Radioall = null;
	private JLabel jLabel6 = null;
	private JCheckBox jCheckBox1 = null;
	
	private List<TempComplex> commInfos;  //  @jve:decl-index=0:

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
	public DgImpExpUseQueryCondition() {
		super();
		initialize();
		getButtonGroup1();
	    getButtonGroup2();
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
		commInfos = encAction
		.findCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()), isImport);
		this.cbbCommInfo.setModel(new DefaultComboBoxModel(
				commInfos.toArray()));
		
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
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转"));

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
		this.setSize(379, 350);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(42, 243, 107, 18));
			jLabel6.setText("是否统计仓库数量");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(174, 110, 43, 18));
			jLabel5.setForeground(java.awt.Color.red);
			jLabel5.setText("事业部");
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(42, 20, 93, 24);
			jLabel.setText("检索商品");
			jLabel1.setBounds(42, 46, 93, 24);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(42, 73, 93, 24);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(42, 138, 93, 24);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(231, 141, 13, 18);
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
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJCheckBox1(), null);
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
			cbbCommInfo.setBounds(140, 20, 197, 22);
		}
		return cbbCommInfo;
	}
	
	private void ifChangeState() {
		if(Radioall.isSelected()) {
			cbbCommInfo.setModel(new DefaultComboBoxModel(
					commInfos.toArray()));
		} else if (Radiotrue.isSelected()) {
			List<TempComplex> list = new ArrayList<TempComplex>();
			for (int i = 0; i < commInfos.size(); i++) {
				if(commInfos.get(i).isEffective()) {
					list.add(commInfos.get(i));
				}
			}
			cbbCommInfo.setModel(new DefaultComboBoxModel(
					list.toArray()));
		} else if (Radiofalse.isSelected()) {
			List<TempComplex> list = new ArrayList<TempComplex>();
			for (int i = 0; i < commInfos.size(); i++) {
				if(!commInfos.get(i).isEffective()) {
					list.add(commInfos.get(i));
				}
			}
			cbbCommInfo.setModel(new DefaultComboBoxModel(
					list.toArray()));
		}
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(140, 46, 197, 22);
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
			cbbImpExpType.setBounds(140, 73, 197, 22);
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
			cbbBeginDate.setBounds(139, 139, 89, 20);
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
			cbbEndDate.setBounds(245, 139, 90, 19);
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
			jButton.setBounds(65, 276, 105, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox.isSelected()) { // 分事业部
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
									DgImpExpUseQueryCondition.this, "请选择事业部!", "提示", 2);
							return;
						}
						new findfordept().start();
					} else {// 不分事业部
						new find().start();
					}
				}
			});
		}
		return jButton;
	}

	class find extends Thread {// 不分事业部

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgImpExpUseQueryCondition.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Integer seqNum = null;// 序号
				String customer = null; // 客户名称
				String impExpType = null;// 进出口类型
				
				

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}

				if (cbbCommInfo.getSelectedItem() != null) {// 序号
					seqNum = Integer.valueOf(((TempComplex) cbbCommInfo
							.getSelectedItem()).getCode());
				}
				if (cbbCustomer.getSelectedItem() != null) { // 客户
					customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
				}
				if (cbbImpExpType.getSelectedItem() != null) {// 类型
					impExpType = ((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode();
				}
				Date beginDate = CommonVars.dateToStandDate(cbbBeginDate
						.getDate());
				Date endDate = CommonVars.dateToStandDate(cbbEndDate.getDate());
				if (isImg) {// 料件
					if (jRadioButton.isSelected()) { // 申报日期 (isDeclaration =
														// true)
						lsResult = encAction.findImpExpCommInfoUseNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, true,  iseffect, 
								jCheckBox1.isSelected()// 是否统计仓库里的数量
							);
					} else if (jRadioButton1.isSelected()) { // 结关日期
						lsResult = encAction.findImpExpCommInfoUseNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, false, iseffect, 
								jCheckBox1.isSelected()// 是否统计仓库里的数量
							);
					}
				} else {
					if (jRadioButton.isSelected()) {// 申报日期
						lsResult = encAction.findImpExpCommInfoUseForExgNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, true, iseffect);
					} else if (jRadioButton1.isSelected()) {// 结关日
						lsResult = encAction.findImpExpCommInfoUseForExgNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, false, iseffect);
					}
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImpExpUseQueryCondition.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	class findfordept extends Thread {

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgImpExpUseQueryCondition.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Integer seqNum = null;
				String customer = ""; // 客户名称
				String impExpType = "";// 进出口类型
				if (cbbCommInfo.getSelectedItem() != null) {
					seqNum = Integer.valueOf(((TempComplex) cbbCommInfo
							.getSelectedItem()).getCode());
				}
				if (cbbCustomer.getSelectedItem() != null) {
					customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
				}
				// 事业部
				List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
				for (int j = 0; j < jComboBox.getModel().getSize(); j++) {
					CheckBoxListItem item = (CheckBoxListItem)jComboBox
							.getModel().getElementAt(j);
					if (item.getIsSelected()) {
						checkableItemList.add(item);
					}
				}
				List<String>  deptCode=new ArrayList<String>();
				for(int i=0;i<checkableItemList.size();i++){
					deptCode.add((checkableItemList.get(i)).getCode());
				}
				boolean isdept = true;
				if (cbbImpExpType.getSelectedItem() != null) {
					impExpType = ((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode();
				}
				

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}

				Date beginDate = CommonVars.dateToStandDate(cbbBeginDate
						.getDate());
				Date endDate = CommonVars.dateToStandDate(cbbEndDate.getDate());
				if (isImg) {// 料件
					if (jRadioButton.isSelected()) {
						lsResult = encAction.findImpExpCommInfoUseForDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, true, deptCode, iseffect);
					} else if (jRadioButton1.isSelected()) {
						lsResult = encAction.findImpExpCommInfoUseForDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, false, deptCode, iseffect);
					}
				} else {
					if (jRadioButton.isSelected()) {
						lsResult = encAction
								.findImpExpCommInfoUseForExgForDept(
										new Request(CommonVars.getCurrUser()),
										isImport, seqNum, customer, impExpType,
										beginDate, endDate, true, isdept,
										deptCode, iseffect);
					} else if (jRadioButton1.isSelected()) {
						lsResult = encAction
								.findImpExpCommInfoUseForExgForDept(
										new Request(CommonVars.getCurrUser()),
										isImport, seqNum, customer, impExpType,
										beginDate, endDate, false, isdept,
										deptCode, iseffect);
					}
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImpExpUseQueryCondition.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(204, 276, 111, 25);
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
	 * @return Returns the isImg.
	 */
	public boolean isImg() {
		return isImg;
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(67, 172, 117, 19));
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
			jRadioButton1.setBounds(new java.awt.Rectangle(200, 171, 112, 21));
			jRadioButton1.setText("按结关日期查询");
		}
		return jRadioButton1;
	}

	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton());
			buttonGroup1.add(getJRadioButton1());
		}
		return buttonGroup1;
	}

	public ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getRadiotrue());
			buttonGroup2.add(getRadiofalse());
			buttonGroup2.add(getRadioall());
		}
		return buttonGroup2;
	}

	/**
	 * This method initializes jComboBox
	 * 
	jCheckBox = new JCheckBox();
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new java.awt.Rectangle(222, 109, 113, 21));
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
			jCheckBox.setBounds(new java.awt.Rectangle(41, 109, 123, 21));
			jCheckBox.setForeground(java.awt.Color.red);
			jCheckBox.setText("是否分事业部统计");
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox.isSelected()) {
						jComboBox.setEnabled(true);
					} else {
						jComboBox.setEnabled(false);
					}
				}
			});
		}
		return jCheckBox;
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
			jPanel.setBounds(new Rectangle(41, 197, 292, 31));
			jPanel.add(getRadiotrue(), null);
			jPanel.add(getRadiofalse(), null);
			jPanel.add(getRadioall(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes Radiotrue
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadiotrue() {
		if (Radiotrue == null) {
			Radiotrue = new JRadioButton();
			Radiotrue.setText("已生效");
			Radiotrue.setBounds(new Rectangle(60, 2, 61, 26));
			Radiotrue.setSelected(true);
			Radiotrue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ifChangeState();
				}
			});
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
			Radiofalse.setText("未生效");
			Radiofalse.setBounds(new Rectangle(121, 2, 61, 26));
			Radiofalse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ifChangeState();
				}
			});
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
			Radioall.setText("全部");
			Radioall.setBounds(new Rectangle(182, 2, 49, 26));
			Radioall.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ifChangeState();
				}
			});
		}
		return Radioall;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(220, 240, 21, 21));
		}
		return jCheckBox1;
	}
}  //  @jve:decl-index=0:visual-constraint="38,40"
