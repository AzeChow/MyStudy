package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings("unchecked")
public class DgInputBGDFromQP extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isImportBGD;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	private boolean isOK = false;

	private JLabel jLabel2 = null;

	private JComboBox cbbDeclarationCustoms = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbTradeMode = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfEntryId = null;

	private JScrollPane jScrollPane = null;

	private JList lsEmsNo = null;

	private int projectType;

	private boolean isSpecialDeclaration = false;// 是否特殊报关单，否则进，出口报关单

	private JLabel jLabel7 = null;

	public void setImportBGD(boolean isImportBGD) {
		this.isImportBGD = isImportBGD;
	}

	public boolean isSpecialDeclaration() {
		return isSpecialDeclaration;
	}

	public void setSpecialDeclaration(boolean isSpecialDeclaration) {
		this.isSpecialDeclaration = isSpecialDeclaration;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public boolean isOK() {
		return isOK;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgInputBGDFromQP() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(564, 280));
		this.setContentPane(getJContentPane());
		this.setTitle("导入报关单");

	}

	public void setVisible(boolean b) {
		if (b) {
			initUI();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化一些组件
	 */
	private void initUI() {
		Date date = baseEncAction.getLastImportBGDDate(
				new Request(CommonVars.getCurrUser(), true), isImportBGD);
		if (date == null) {
			date = new Date();
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(GregorianCalendar.DATE, -7);
			date = calendar.getTime();
		}
		this.cbbBeginDate.setDate(date);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, 7);
		this.cbbEndDate.setDate(calendar.getTime());
		// 初始化贸易方式
		fillTrade(isSpecialDeclaration);

		// 初始化报送海关
		fillDeclarationCustoms();

		// 手册/账册号
		Vector<Object> vector = new Vector<Object>();
		List listEmsNo = baseEncAction.findExcuteEmsByProjectType(new Request(
				CommonVars.getCurrUser()), projectType);
		for (int i = 0; i < listEmsNo.size(); i++) {
			BaseEmsHead obj = (BaseEmsHead) listEmsNo.get(i);
			if (obj.getEmsNo() != null && !"".equals(obj.getEmsNo())) {
				CheckableItem item = new CheckableItem(obj.getEmsNo());
				vector.add(item);
			}
		}
		this.lsEmsNo.setListData(vector);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(345, 109, 159, 81));
			jLabel7.setForeground(Color.blue);
			jLabel7.setText("<html>说明：<br/>1:报关日期默认为7天；<br/>2:可选多本手册导入，不选则默认为全部正在执行的手册</html>");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(33, 6, 168, 18));
			jLabel6.setText("选择导入条件");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(38, 176, 49, 18));
			jLabel5.setText("报关单号");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(38, 123, 64, 18));
			jLabel4.setText("手册/账册号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(291, 75, 53, 18));
			jLabel3.setText("贸易方式");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(38, 74, 54, 18));
			jLabel2.setText("申报海关");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(304, 35, 25, 18));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(39, 36, 54, 18));
			jLabel.setText("报关日期");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbDeclarationCustoms(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbTradeMode(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfEntryId(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel7, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(114, 33, 170, 23));
			cbbBeginDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					Date date = cbbBeginDate.getDate();
					if (date == null) {
						cbbEndDate.setDate(null);
					}
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					calendar.add(GregorianCalendar.DATE, 7);
					cbbEndDate.setDate(calendar.getTime());
				}

			});
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
			cbbEndDate.setBounds(new Rectangle(350, 34, 170, 25));
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

			jButton.setBounds(new Rectangle(121, 205, 66, 25));

			jButton.setText("确定");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Date beginDate = cbbBeginDate.getDate();

					if (beginDate == null) {

						JOptionPane.showMessageDialog(DgInputBGDFromQP.this,
								"请输入或选择开始日期");

						return;

					}

					Date endDate = cbbEndDate.getDate();

					if (endDate == null) {

						JOptionPane.showMessageDialog(DgInputBGDFromQP.this,
								"请输入或选择结束日期");

						return;

					}

					// 结束日期与开始日期相差的天数 不能大于7天
					GregorianCalendar calendar = new GregorianCalendar();

					calendar.setTime(beginDate);

					calendar.add(GregorianCalendar.DATE, 7);

					// 判断 报关单号如果不为空 并且 长度不等于18 那么就不能继续执行
					if (StringUtils.isNotBlank(tfEntryId.getText())
							&& tfEntryId.getText().trim().length() != 18) {

						JOptionPane.showMessageDialog(DgInputBGDFromQP.this,
								"报关单号长度不等于18位，请检查");

						return;

					}

					isOK = true;

					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(381, 205, 59, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	public ImportBGDCondition getCondition() {
		ImportBGDCondition condition = new ImportBGDCondition();
		condition.setImportBGD(this.isImportBGD);
		condition.setBeginDate(this.cbbBeginDate.getDate());
		condition.setEndDate(this.cbbEndDate.getDate());
		// 申报海关
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		for (int j = 0; j < this.cbbDeclarationCustoms.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbDeclarationCustoms
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				checkableItemList.add(item);
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckBoxListItem item = checkableItemList.get(j);
			if (j == 0) {
				condition.setCustoms(item.getCode());
			} else {
				condition.setCustoms(condition.getCustoms() + ","
						+ item.getCode());
			}
		}

		// 贸易方式
		checkableItemList.clear();
		for (int j = 0; j < this.cbbTradeMode.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbTradeMode
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				checkableItemList.add(item);
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckBoxListItem item = checkableItemList.get(j);
			if (j == 0) {
				condition.setTrade(item.getCode());
			} else {
				condition.setTrade(condition.getTrade() + "," + item.getCode());
			}
		}

		if (!"".equals(this.tfEntryId.getText().trim())) {
			condition.setEntryId(this.tfEntryId.getText().trim());
		}
		// 手册/账册号
		List emsNoList = new ArrayList<String>();
		for (int j = 0; j < lsEmsNo.getModel().getSize(); j++) {
			Object value = lsEmsNo.getModel().getElementAt(j);
			if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				if (item.isSelected) {
					emsNoList.add(item.getName());
				}
			}
		}
		for (int i = 0; i < emsNoList.size(); i++) {
			if (i == 0) {
				condition.setEmsNo(emsNoList.get(0).toString());
			} else {
				condition.setEmsNo(condition.getEmsNo() + ","
						+ emsNoList.get(i).toString());
			}
		}

		return condition;
	}

	/**
	 * This method initializes cbbDeclarationCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclarationCustoms() {
		if (cbbDeclarationCustoms == null) {
			cbbDeclarationCustoms = new JComboBox();
			cbbDeclarationCustoms.setBounds(new Rectangle(114, 70, 170, 23));
			cbbDeclarationCustoms.addPropertyChangeListener("UI",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							JTextField tf = (JTextField) cbbDeclarationCustoms
									.getEditor().getEditorComponent();
							if (!"".equals(tf.getText().trim())) {
								String[] strs = tf.getText().split(",");
								Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
								int size = cbbDeclarationCustoms.getModel()
										.getSize();
								for (int i = 0; i < size; i++) {
									CheckBoxListItem item = (CheckBoxListItem) cbbDeclarationCustoms
											.getModel().getElementAt(i);
									checkBoxListItemMap.put(item.getName(),
											item);
								}
								//
								// 根据输入的字符串选中JList中的列表
								//
								String tempText = "";
								// System.out.println("strs.length =="+strs.length);
								for (String str : strs) {
									// System.out.println(str);
									CheckBoxListItem item = checkBoxListItemMap
											.get(str);
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
									CheckBoxListItem checkBoxListItem = iterator
											.next();
									checkBoxListItem.setIsSelected(false);
								}
								//
								// 重新设置其显示文本值
								//
								tf.setText(tempText);
							} else {
								String tempText = "";
								Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
								int size = cbbDeclarationCustoms.getModel()
										.getSize();
								for (int i = 0; i < size; i++) {
									CheckBoxListItem item = (CheckBoxListItem) cbbDeclarationCustoms
											.getModel().getElementAt(i);
									item.setIsSelected(true);
									checkBoxListItemMap.put(item.getName(),
											item);
									if ("".equals(tempText)) {
										tempText += item.getName();
									} else {
										tempText += "," + item.getName();
									}
								}
								tf.setText(tempText);
							}
						}
					});
		}
		return cbbDeclarationCustoms;
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new Rectangle(350, 72, 170, 23));
			cbbTradeMode.addPropertyChangeListener("UI",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							JTextField tf = (JTextField) cbbTradeMode
									.getEditor().getEditorComponent();
							if (!"".equals(tf.getText().trim())) {
								String[] strs = tf.getText().split(",");
								Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
								int size = cbbTradeMode.getModel().getSize();
								for (int i = 0; i < size; i++) {
									CheckBoxListItem item = (CheckBoxListItem) cbbTradeMode
											.getModel().getElementAt(i);
									checkBoxListItemMap.put(item.getName(),
											item);
								}
								//
								// 根据输入的字符串选中JList中的列表
								//
								String tempText = "";
								// System.out.println("strs.length =="+strs.length);
								for (String str : strs) {
									// System.out.println(str);
									CheckBoxListItem item = checkBoxListItemMap
											.get(str);
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
									CheckBoxListItem checkBoxListItem = iterator
											.next();
									checkBoxListItem.setIsSelected(false);
								}
								//
								// 重新设置其显示文本值
								//
								tf.setText(tempText);
							} else {
								String tempText = "";
								Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
								int size = cbbTradeMode.getModel().getSize();
								for (int i = 0; i < size; i++) {
									CheckBoxListItem item = (CheckBoxListItem) cbbTradeMode
											.getModel().getElementAt(i);
									item.setIsSelected(true);
									checkBoxListItemMap.put(item.getName(),
											item);
									if ("".equals(tempText)) {
										tempText += item.getName();
									} else {
										tempText += "," + item.getName();
									}
								}
								tf.setText(tempText);
							}
						}
					});
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes tfEntryId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEntryId() {
		if (tfEntryId == null) {
			tfEntryId = new JTextField();
			tfEntryId.setBounds(new Rectangle(114, 175, 170, 22));
		}
		return tfEntryId;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {

		if (jScrollPane == null) {

			jScrollPane = new JScrollPane();

			jScrollPane.setBounds(new Rectangle(115, 108, 170, 56));

			jScrollPane.setViewportView(getLsEmsNo());

		}

		return jScrollPane;

	}

	/**
	 * 手册/账册号
	 * 
	 * @return
	 */
	private JList getLsEmsNo() {
		if (lsEmsNo == null) {

			lsEmsNo = new JList();

			lsEmsNo.setCellRenderer(new InputCheckListRenderer());

			lsEmsNo.setFixedCellHeight(18);

			lsEmsNo.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {

					int index = lsEmsNo.locationToIndex(e.getPoint());

					Object obj = lsEmsNo.getModel().getElementAt(index);

					if (obj instanceof CheckableItem) {

						CheckableItem item = (CheckableItem) obj;

						item.setSelected(!item.isSelected());

						Rectangle rect = lsEmsNo.getCellBounds(index, index);

						lsEmsNo.repaint(rect);

					}
				}

			});
		}

		return lsEmsNo;
	}

	/**
	 * 列表框呈现类（此处用于手册/账册号）
	 * 
	 * @author Administrator
	 */
	class InputCheckListRenderer extends JCheckBox implements ListCellRenderer {

		public InputCheckListRenderer() {

			setBackground(UIManager.getColor("List.textBackground"));

			setForeground(UIManager.getColor("List.textForeground"));

		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {

			setEnabled(list.isEnabled());

			if (value instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) value;

				setSelected(item.isSelected());

				setSize(350, 17);

				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));
				setText("  " + item.getName());
			}
			return this;
		}
	}

	/**
	 * 用于列表框中项中的选择
	 */
	class CheckableItem {

		private boolean isSelected;

		private String name;

		public CheckableItem(String name) {
			this.name = name;
			isSelected = true;
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

	/**
	 * 贸易方式
	 * 
	 * @param declarationType
	 *            报关单类型：特殊，进，出口报关单
	 */
	private void fillTrade(boolean declarationType) {
		cbbTradeMode.removeAllItems();
		DefaultComboBoxModel dcbm = CustomBaseModel.getInstance()
				.getTradeModel();
		List tradeList = new ArrayList();
		for (int i = 0; i < dcbm.getSize(); i++) {
			tradeList.add(dcbm.getElementAt(i));
		}

		for (int i = 0; i < tradeList.size(); i++) {
			Trade trade = (Trade) tradeList.get(i);
			// lyh 2013.1.30需求不明确，暂时此功能不用。
			// 当进口类型选择0 或1时，进出口报关单，贸易方式选择 ！=【0110,0320,0446,0456,0466,0500,
			// 2025,2225,0844,0845,0864,0865】//的下载回来
			/*
			 * if(!isSpecialDeclaration){//进，出口报关单 if
			 * (trade.getCode().equalsIgnoreCase("0110") ||
			 * trade.getCode().equalsIgnoreCase("0320") ||
			 * trade.getCode().equalsIgnoreCase("0446") ||
			 * trade.getCode().equalsIgnoreCase("0456") ||
			 * trade.getCode().equalsIgnoreCase("0466") ||
			 * trade.getCode().equalsIgnoreCase("0500") ||
			 * trade.getCode().equalsIgnoreCase("2025") ||
			 * trade.getCode().equalsIgnoreCase("2225") ||
			 * trade.getCode().equalsIgnoreCase("0844") ||
			 * trade.getCode().equalsIgnoreCase("0845") ||
			 * trade.getCode().equalsIgnoreCase("0864") ||
			 * trade.getCode().equalsIgnoreCase("0865") ) { continue; }
			 * }else{//特殊报关单 if (!(trade.getCode().equalsIgnoreCase("0110") ||
			 * trade.getCode().equalsIgnoreCase("0320") ||
			 * trade.getCode().equalsIgnoreCase("0446") ||
			 * trade.getCode().equalsIgnoreCase("0456") ||
			 * trade.getCode().equalsIgnoreCase("0466") ||
			 * trade.getCode().equalsIgnoreCase("0500") ||
			 * trade.getCode().equalsIgnoreCase("2025") ||
			 * trade.getCode().equalsIgnoreCase("2225") ||
			 * trade.getCode().equalsIgnoreCase("0844") ||
			 * trade.getCode().equalsIgnoreCase("0845") ||
			 * trade.getCode().equalsIgnoreCase("0864") ||
			 * trade.getCode().equalsIgnoreCase("0865") )) { continue; } }
			 */
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(trade.getCode());
			item.setName(trade.getName());
			item.setIsSelected(Boolean.valueOf(true));
			cbbTradeMode.addItem(item);
		}
		this.cbbTradeMode.setSelectedItem(null);
		this.cbbTradeMode
				.setRenderer(new CheckBoxListCellRenderer(cbbTradeMode));
		this.cbbTradeMode.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbTypeEvent(cbbTradeMode);
	}

	/**
	 * 申报海关
	 */
	private void fillDeclarationCustoms() {
		cbbDeclarationCustoms.removeAllItems();
		DefaultComboBoxModel dcbm = CustomBaseModel.getInstance()
				.getCustomModel();
		List declarationCustomsList = new ArrayList();
		for (int i = 0; i < dcbm.getSize(); i++) {
			declarationCustomsList.add(dcbm.getElementAt(i));
		}

		for (int i = 0; i < declarationCustomsList.size(); i++) {
			Customs customs = (Customs) declarationCustomsList.get(i);
			CheckBoxListItem item = new CheckBoxListItem();
			item.setCode(customs.getCode());
			item.setName(customs.getName());
			item.setIsSelected(Boolean.valueOf(true));
			cbbDeclarationCustoms.addItem(item);

		}
		this.cbbDeclarationCustoms.setSelectedItem(null);
		this.cbbDeclarationCustoms.setRenderer(new CheckBoxListCellRenderer(
				cbbDeclarationCustoms));
		this.cbbDeclarationCustoms.setUI(new SteppedMetalComboBoxUI(225));
		this.setCmbTypeEvent(cbbDeclarationCustoms);
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
	private void setCmbTypeEvent(final JComboBox cbb) {
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
				if (!"".equals(tf.getText().trim())) {
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
				} else {
					String tempText = "";
					Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
					int size = listBox.getModel().getSize();
					for (int i = 0; i < size; i++) {
						CheckBoxListItem item = (CheckBoxListItem) listBox
								.getModel().getElementAt(i);
						item.setIsSelected(true);
						checkBoxListItemMap.put(item.getName(), item);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
					}
					tf.setText(tempText);
				}
			}
		});
	}
} // @jve:decl-index=0:visual-constraint="10,10"
