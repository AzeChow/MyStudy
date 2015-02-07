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
public class DgChooseInquireDate extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;
	
	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;
	
	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	private boolean isOK = false;

	private Date beginDate;
	
	private Date endDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private JLabel jLabel6 = null;
	
	private GregorianCalendar calendar = new GregorianCalendar();

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}
	
	public GregorianCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(GregorianCalendar calendar) {
		this.calendar = calendar;
	}

	public boolean isOK() {
		return isOK;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgChooseInquireDate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(314, 206));
		this.setContentPane(getJContentPane());
		this.setTitle("选择查询日期");

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
		Date date = new Date();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DATE, 0);
		date = calendar.getTime();
		this.cbbBeginDate.setDate(date);
		GregorianCalendar calendar0 = new GregorianCalendar();
		calendar0.setTime(date);
		calendar0.add(GregorianCalendar.DATE, 7);
		this.cbbEndDate.setDate(calendar0.getTime());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(20, 5, 168, 18));
			jLabel6.setText("选择查询日期：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(33, 38, 71, 18));
			jLabel.setText("查询日期：");
			jLabel1 = new JLabel();
		    jLabel1.setBounds(new Rectangle(189, 56, 18, 18));
		    jLabel1.setText("至");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
		    jContentPane.add(jLabel1,null);
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(114, 74, 170, 25));
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
			jButton.setBounds(new Rectangle(72, 122, 66, 25));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbBeginDate.getDate();
					if (beginDate == null) {
						JOptionPane.showMessageDialog(DgChooseInquireDate.this,
								"请输入或选择查询日期");
						return;
					}
					Date endDate = cbbEndDate.getDate();
					if (endDate == null) {
						JOptionPane.showMessageDialog(DgChooseInquireDate.this,
								"请输入或选择结束日期");
						return;
					}
					calendar.setTime(beginDate);
					calendar.add(GregorianCalendar.DATE, 7);
					calendar.setTime(endDate);
					calendar.add(GregorianCalendar.DATE, 7);
					isOK= true;
					setBeginDate(beginDate);
					setEndDate(endDate);
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
			jButton1.setBounds(new Rectangle(176, 122, 66, 24));
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
