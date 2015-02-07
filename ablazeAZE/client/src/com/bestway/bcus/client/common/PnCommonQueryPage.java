/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.common.BswProgres;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.SerialColumn;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 *         查询操作页面
 */
public abstract class PnCommonQueryPage extends JPanelBase {

	public JComboBox cbbQueryField = null;

	public JTableListModel tableModel = null;
	public List dataSource = new ArrayList();

	private JTextField tfQueryValue = null;
	private JButton btnUpPage = null;
	private JButton btnDownPage = null;
	private JButton btnQuery = null;
	private int index = -100;
	private int length = 100;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private String editBeforeQueryValue = "";
	private boolean eidtBeforeBtnUpPageState = false;
	private boolean eidtBeforeBtnDownPageState = false;
	private JRadioButton rbPrecision = null;
	private JRadioButton rbDark = null;
	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:
	private boolean isFirst = true;
	private boolean isFirstHasDataInit = true;
	private JCheckBox cbIsTrue = null;
	private DocumentListener documentListener = null; // @jve:decl-index=0:
	private Long count = 0L;
	private Integer pages = null;
	private JButton btnFirstPage = null;
	private JButton btnLastPage = null;
	private JLabel lbPageInfo = null;
	private boolean isChange = true;
	private boolean isProgres = false;
	private SystemAction systemAction = null;
	protected CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public PnCommonQueryPage() {
		super();
		initialize();
		group.add(this.rbDark);
		group.add(this.rbPrecision);
		cbIsTrue.setVisible(false);
		// Item item = (Item) this.cbbQueryField.getSelectedItem();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		List list = systemAction.findCompanyOther(new Request(CommonVars
				.getCurrUser(), true));
		if (list.size() > 0 && list.get(0) != null) {
			CompanyOther companyOther = (CompanyOther) list.get(0);
			if (companyOther.getPageSize() != null
					&& companyOther.getPageSize() > 0) {
				length = companyOther.getPageSize();
			}
		}
		// setState();
		this.rbPrecision.setSelected(!this.getIsLikeByInit());

		// 加此句将执行两次
		/*
		 * dataSource = this.getDataSource(0, length, item == null ? null : item
		 * .getProperty(), getValue(), getIsLike());
		 */
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isFirst) {
			setVisible(true);
			removeAndAddKeyListener();
			isFirst = false;
		}
	}

	private void removeAndAddKeyListener() {
		//
		// 加入回车事件
		//
		KeyListener[] keyListeners = btnQuery.getKeyListeners();
		boolean isExist = false;
		for (KeyListener kl : keyListeners) {
			// if(kl instanceof KeyAdapterExtend){
			btnQuery.removeKeyListener(kl);
			// }
		}

		tfQueryValue.addFocusListener(new FocusListener() {

			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

			public void focusLost(FocusEvent e) {
				// setTextToZero();
			}
		});

		// if(!isExist){
		btnQuery.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {// Enter

					e.getComponent().dispatchEvent(
							new KeyEvent((Component) e.getSource(),
									KeyEvent.KEY_PRESSED, 0, 0,
									KeyEvent.VK_SPACE, e.getKeyChar()));
					e.getComponent().dispatchEvent(
							new KeyEvent((Component) e.getSource(),
									KeyEvent.KEY_RELEASED, 0, 0,
									KeyEvent.VK_SPACE, e.getKeyChar()));
					// e.getComponent().dispatchEvent(new KeyEvent((Component) e
					// .getSource(), KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, e.getKeyChar()));
					tfQueryValue.requestFocus();
				}
			}
		});
		// }
	}

	// /**
	// * 显示
	// */
	// pubprivateid setVisible(boolean b) {
	// if (b) {
	// setInitState();
	// if(dataSource.size() <= 0){
	// initCbbQueryField();
	// }
	// }
	// super.setVisible(b);
	// }
	/**
	 * 初始化查询字段
	 * 
	 */
	public void initCbbQueryField() {
		// if (getCbbQueryField().getItemCount() > 0) {
		// cbbQueryField.setSelectedItem(null);
		// return;
		// }
		this.tfQueryValue.setText("");
		Map<String, Integer> indexmap = new HashMap<String, Integer>();
		getCbbQueryField().removeAllItems();
		if (tableModel != null) {
			for (int i = 0; i < tableModel.getColumnCount(); i++) {
				JTableListColumn c = tableModel.getColumns().get(i);
				if (c instanceof SerialColumn) {
					continue;
				}
				if (c.isShowSearch() == false) {
					continue;
				}
				String property = c.getProperty().toLowerCase().trim();
				if (property.endsWith("isselected")) {
					continue;
				}
				int dataType = getDataTypeByColumns(c);

				getCbbQueryField().addItem(
						new Item(c.getCaption(),
								c.getCustomProperty() == null ? c.getProperty()
										: c.getCustomProperty(), c
										.getDataType() != DataType.NULL ? c
										.getDataType() : dataType));
			}
			cbbQueryField.setSelectedIndex(0);
			selectItem();

		}
	}

	/**
	 * 设置初始状态
	 * 
	 */
	public void setInitState() {
		// System.out.println("DDDDDDDDDDD");
		index = -length;
		this.btnQuery.setVisible(true);
		isChange = true;
		searchPage(true);

	}

	/**
	 * 设置初始状态
	 * 
	 */
	public void reset() {
		if (this.cbbQueryField.getItemCount() > 0) {
			this.cbbQueryField.setSelectedIndex(0);
			selectItem();
		}
		this.tfQueryValue.setText("");
		setInitState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(5);
		flowLayout.setHgap(2);
		lbPageInfo = new JLabel();
		// lbPageInfo
		// .setText("<html><boby>共<font color='red'>5</font>页</body></html>");
		lbPageInfo.setText("");
		// gridBagConstraints4.ripadx = 188;
		lbPageInfo.setPreferredSize(new Dimension(270, 30));
		this.setLayout(flowLayout);
		// gridBagConstraints4.weightx = 1.0;
		this.setSize(new Dimension(852, 30));
		jLabel1 = new JLabel();
		jLabel1.setText("类型:");
		jLabel = new JLabel();
		jLabel.setForeground(new java.awt.Color(255, 153, 0));
		jLabel.setPreferredSize(new Dimension(50, 18));
		jLabel.setText("无");
		this.add(getCbbQueryField(), null);
		this.add(jLabel1, null);
		this.add(jLabel, null);
		this.add(getTfQueryValue(), null);
		this.add(getCbIsTrue(), null);
		this.add(getBtnQuery(), null);
		this.add(getBtnFirstPage(), null);
		this.add(getBtnUpPage(), null);
		this.add(getBtnDownPage(), null);
		this.add(getBtnLastPage(), null);
		this.add(getRbPrecision(), null);
		this.add(getRbDark(), null);
		this.add(lbPageInfo, null);
	}

	/**
	 * 查询的字段
	 * 
	 * @return
	 */
	public JComboBox getCbbQueryField() {
		if (cbbQueryField == null) {
			cbbQueryField = new JComboBox();
			cbbQueryField.setUI(new SteppedMetalComboBoxUI(150));
			cbbQueryField.setPreferredSize(new Dimension(100, 25));
			cbbQueryField.setSelectedItem(null);
			cbbQueryField.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED
							&& cbbQueryField.isFocusOwner()) {
						selectItem();
						setState();
						if (!tfQueryValue.getText().equals("")) {
							index = -length;
						}
						setQueryState(false);
					}
				}
			});
		}
		return cbbQueryField;
	}

	/**
	 * 
	 * This method initializes tfQueryValue
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	public JTextField getTfQueryValue() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setPreferredSize(new Dimension(120, 25));
			tfQueryValue.addFocusListener(new FocusListener() {

				public void focusGained(FocusEvent e) {
					editBeforeQueryValue = tfQueryValue.getText();
					eidtBeforeBtnUpPageState = btnUpPage.isEnabled();
					eidtBeforeBtnDownPageState = btnUpPage.isEnabled();
				}

				public void focusLost(FocusEvent e) {
					String editAfterQueryValue = tfQueryValue.getText();
					if (!editAfterQueryValue
							.equalsIgnoreCase(editBeforeQueryValue)) {
						index = -length;
					}
				}
			});

			// 文本监听器
			documentListener = new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					String editAfterQueryValue = tfQueryValue.getText();
					if (!editAfterQueryValue
							.equalsIgnoreCase(editBeforeQueryValue)) {
						setQueryState(false);
					} else {
						btnDownPage.setEnabled(eidtBeforeBtnDownPageState);
						btnUpPage.setEnabled(eidtBeforeBtnUpPageState);
					}
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					String editAfterQueryValue = tfQueryValue.getText();
					if (!editAfterQueryValue
							.equalsIgnoreCase(editBeforeQueryValue)) {
						setQueryState(false);
					} else {
						btnDownPage.setEnabled(eidtBeforeBtnDownPageState);
						btnUpPage.setEnabled(eidtBeforeBtnUpPageState);
					}
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					String editAfterQueryValue = tfQueryValue.getText();
					if (!editAfterQueryValue
							.equalsIgnoreCase(editBeforeQueryValue)) {
						setQueryState(false);
					} else {
						btnDownPage.setEnabled(eidtBeforeBtnDownPageState);
						btnUpPage.setEnabled(eidtBeforeBtnUpPageState);
					}
					setState();
				}
			};

			// 加入文本监听器
			tfQueryValue.getDocument().addDocumentListener(documentListener);
		}
		return tfQueryValue;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getRbPrecision() {
		if (rbPrecision == null) {
			rbPrecision = new JRadioButton();
			rbPrecision.setText("精确");
			rbPrecision.setSelected(true);
			rbPrecision.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						index = -length;
						setQueryState(false);
					}
				}
			});
		}
		return rbPrecision;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getRbDark() {
		if (rbDark == null) {
			rbDark = new JRadioButton();
			rbDark.setText("模糊");
			rbDark.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						index = -length;
						setQueryState(false);
					}
				}
			});
		}
		return rbDark;
	}

	/**
	 * This method initializes btnUp
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getBtnUpPage() {
		if (btnUpPage == null) {
			btnUpPage = new JButton();
			btnUpPage.setText("上页");
			btnUpPage.setPreferredSize(new Dimension(60, 25));
			btnUpPage.setToolTipText("查询前 " + length + " 条记录!!");
			// btnUpPage.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnUpPage.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					12));
			btnUpPage.setEnabled(false);
			btnUpPage.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					searchPage(false);
				}
			});
		}
		return btnUpPage;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getBtnDownPage() {
		if (btnDownPage == null) {
			btnDownPage = new JButton();
			btnDownPage.setText("下页");
			btnDownPage.setPreferredSize(new Dimension(60, 25));
			btnDownPage.setToolTipText("查询后 " + length + " 条记录!!");
			btnDownPage.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			// btnDownPage.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnDownPage.setEnabled(false);
			btnDownPage.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					searchPage(true);
				}
			});
		}
		return btnDownPage;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setPreferredSize(new Dimension(60, 25));
			btnQuery.setToolTipText("查询后 " + length + " 条记录!!");
			btnQuery.setVisible(true);
			btnQuery.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					12));
			// btnDownPage.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Object value = getValue();
					// if (value == null || value.equals("")) {
					// if (JOptionPane.showConfirmDialog(null,
					// "提醒：查询条件为空，将显示所有数据，是否继续?", "提示",
					// JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					// return;
					// }
					// }
					System.out.println("feng ye but index:" + index);
					index = (-length);
					searchPage(true);
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 验证数据
	 */
	private boolean validateData() {
		Item item = (Item) this.cbbQueryField.getSelectedItem();
		if (item == null) {
			return true;
		}
		//
		// 如果属性值为 ""
		//
		if (item.getProperty() == null || "".equals(item.getProperty().trim())) {
			JOptionPane.showMessageDialog(this, " 字段不允许查找 !!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		int dataType = item.getDataType();
		String value = this.tfQueryValue.getText();
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		if (dataType == DataType.BOOLEAN) {
		} else if (dataType == DataType.INTEGER) {
			try {
				Integer.valueOf(value);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "查询的值请使用 整数!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} else if (dataType == DataType.DOUBLE) {
			try {
				Double.valueOf(value);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "查询的值请使用 数值!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} else if (dataType == DataType.NUMBER) {
			try {
				Double.valueOf(value);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "查询的值请使用 数值!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} else if (dataType == DataType.STRING) {
		} else if (dataType == DataType.DATE) {
			try {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				bartDateFormat.parse(value);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
						"查询的值请使用日期格式例如:1988-08-12 !!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} /*
		 * else if (dataType == DataType.NULL) {
		 * JOptionPane.showMessageDialog(this, "查询的字段不是有效的基本数据类型!!", "提示",
		 * JOptionPane.INFORMATION_MESSAGE); return false; }
		 */
		return true;
	}

	/**
	 * 选择项目
	 * 
	 */
	private void selectItem() {
		Item item = (Item) this.cbbQueryField.getSelectedItem();
		if (item == null) {
			jLabel.setText("无");
			return;
		}
		int dataType = item.getDataType();
		if (dataType == DataType.BOOLEAN) {
			jLabel.setText("布尔型");
			tfQueryValue.setVisible(false);
			cbIsTrue.setText(((Item) cbbQueryField.getSelectedItem()).getName());
			cbIsTrue.setVisible(true);
		} else {
			tfQueryValue.setVisible(true);
			cbIsTrue.setVisible(false);
		}
		if (dataType == DataType.INTEGER) {
			jLabel.setText("整型");
		} else if (dataType == DataType.DOUBLE) {
			jLabel.setText("数值型");
		} else if (dataType == DataType.NUMBER) {
			jLabel.setText("数值型");
		} else if (dataType == DataType.STRING) {
			jLabel.setText("字符型");
		} else if (dataType == DataType.DATE) {
			jLabel.setText("日期型");
		} else if (dataType == DataType.NULL) {
			jLabel.setText("无");
		}
	}

	/**
	 * 设置模糊查询的状态
	 * 
	 */
	private void setState() {
		//
		// 当数据类型是字符型时才有模糊查询
		//
		Item item = (Item) this.cbbQueryField.getSelectedItem();
		if (item == null) {
			this.rbDark.setEnabled(false);
			this.rbPrecision.setEnabled(false);
			this.rbPrecision.setSelected(true);
			return;
		}
		int dataType = item.getDataType();
		if (dataType == DataType.STRING) {
			this.rbDark.setEnabled(true);
			// this.rbPrecision.setEnabled(true);
			this.rbDark.setSelected(true);
		} else {
			// this.rbDark.setEnabled(false);
			this.rbPrecision.setEnabled(true);
			this.rbPrecision.setSelected(true);
		}
	}

	public void setQueryState(boolean b) {
		// btnFirstPage.setVisible(b);
		// btnUpPage.setVisible(b);
		// btnDownPage.setVisible(b);
		// btnLastPage.setVisible(b);
		// lbPageInfo.setVisible(b);
		// btnQuery.setVisible(!b);
		btnFirstPage.setEnabled(b);
		btnUpPage.setEnabled(b);
		btnDownPage.setEnabled(b);
		btnLastPage.setEnabled(b);
		lbPageInfo.setEnabled(b);
		isChange = !b;
		// btnQuery.setEnabled(!b)
	}

	/**
	 * 获得是否是模糊查询
	 * 
	 * @return
	 */
	private boolean getIsLike() {
		if (this.rbDark.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得值
	 * 
	 */
	private Object getValue() {
		Item item = (Item) this.cbbQueryField.getSelectedItem();
		if (item == null) {
			return null;
		}
		int dataType = item.getDataType();
		if (dataType == DataType.BOOLEAN) {
			return cbIsTrue.isSelected();
		}
		String value = this.tfQueryValue.getText();
		if (value == null || "".equals(value.trim())) {
			return null;
		}
		if (dataType == DataType.INTEGER) {
			try {
				return Integer.valueOf(value);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "\"" + value
						+ "\"不是一个有效的整型数字");
				this.tfQueryValue.setText("");
				return null;
			}
		} else if (dataType == DataType.DOUBLE) {
			try {
				return Double.valueOf(value);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "\"" + value
						+ "\"不是一个有效的数字");
				this.tfQueryValue.setText("");
				return null;
			}
		} else if (dataType == DataType.NUMBER) {
			try {
				return Double.valueOf(value);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "\"" + value
						+ "\"不是一个有效的数字");
				this.tfQueryValue.setText("");
				return null;
			}
		} else if (dataType == DataType.STRING) {
			return value;
		} else if (dataType == DataType.DATE) {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return bartDateFormat.parse(value);
			} catch (Exception ex) {
			}
		} else if (dataType == DataType.NULL) {
			return value;
		}
		return null;
	}

	/**
	 * 获得数据类型来自列
	 * 
	 * @param col
	 * @return
	 */
	public int getDataTypeByColumns(JTableListColumn c) {
		List list = tableModel.getList();
		// if (list == null || list.size() <= 0) {
		// return DataType.STRING;
		// }
		int dataType = DataType.STRING;
		try {

			String sProp = c.getProperty();
			Class cls = c.getClassType();

			if (cls == null) {
				if (list == null || list.size() <= 0 || list.get(0) == null) {
					return dataType;
				}
				cls = CommonVariables.getTypeByField(list.get(0).getClass(),
						sProp);
			}
			if (cls.equals(Integer.class) || cls.equals(Long.class)
					|| cls.equals(Short.class)) {
				dataType = DataType.INTEGER;
			} else if (cls.equals(Double.class) || cls.equals(Float.class)) {
				dataType = DataType.DOUBLE;
			} else if (cls.equals(String.class)) {
				dataType = DataType.STRING;
			} else if (cls.equals(Boolean.class)) {
				dataType = DataType.BOOLEAN;
			} else if (cls.equals(Date.class) || cls.equals(Calendar.class)) {
				dataType = DataType.DATE;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataType;
	}

	/**
	 * 查询上下页
	 * 
	 * @param materielType
	 * @param isDownPage
	 */
	private void searchPage(Boolean isDownPage) {
		System.out.println("~~~~~~~~~~~~~" + isChange + "  isProgres:"
				+ isProgres + " !validateData()" + !validateData());
		if (isProgres) {
			searchPage(isDownPage, isProgres);
		} else {
			if (!validateData()) {
				return;
			}

			if (isDownPage != null && isDownPage == false) {
				index -= length;
			} else if (isDownPage != null && isDownPage == true) {
				index += length;
			}

			Item item = (Item) this.cbbQueryField.getSelectedItem();
			dataSource = this.getDataSource(index, length, item == null ? null
					: item.getProperty(), getValue(), getIsLike());

			if (isChange) {
				count = this.getDataSourceCount(index, length,
						item == null ? null : item.getProperty(), getValue(),
						getIsLike());
			}

			if (count != 0) {
				pages = (count.intValue() / length)
						+ (count % length == 0 ? 0 : 1);
				int currentPage = (index + length) / length;
				// setPageInfo(currentPage, count);
				// this.lbPageInfo.setText("共"+pages+"页");
				setPageInfo(currentPage, count, length, pages);
				if (pages == currentPage) {
					this.btnLastPage.setEnabled(false);
					this.btnDownPage.setEnabled(false);
				} else {
					this.btnLastPage.setEnabled(true);
					this.btnDownPage.setEnabled(true);
				}

			} else {
				setPageInfoIsNull();
			}

			this.tableModel = initTable(dataSource);
			//
			// 第一次有数据的初始化 cbbQueryField 才是有效的
			//

			if (isFirstHasDataInit) {
				// if (dataSource.size() > 0 && isFirstHasDataInit) {
				initCbbQueryField();
				isFirstHasDataInit = false;
			}

			if (tableModel != null && count == 0) {

				if (length > this.tableModel.getRowCount()) {
					this.btnLastPage.setEnabled(false);
					this.btnDownPage.setEnabled(false);
				} else {
					this.btnLastPage.setEnabled(true);
					this.btnDownPage.setEnabled(true);
				}
			}

			System.out.println("tableModel: " + tableModel);

			if (tableModel != null) {
				if (index == 0) {
					this.btnUpPage.setEnabled(false);
					this.btnFirstPage.setEnabled(false);
				} else {
					this.btnUpPage.setEnabled(true);
					this.btnFirstPage.setEnabled(true);
				}
			}

			// ------------------------------------以下是新加的BY:kcb
			Integer col = null;
			Object obj = getCbbQueryField().getSelectedItem();
			if (obj == null || !(obj instanceof Item)) {
				return;
			}
			Item itm = (Item) getCbbQueryField().getSelectedItem();
			if (itm == null || itm.getName() == null) {
				return;
			}
			String pro = itm.getName();
			List<JTableListColumn> list = ((JTableListModel) tableModel)
					.getColumns();
			for (int i = 0; i < list.size(); i++) {
				JTableListColumn data = list.get(i);
				if (data.getCaption() == null) {
					continue;
				}
				if (data.getCaption().equals(pro)) {
					col = i;
				}
			}
			boolean isEqual = true;
			if (getRbPrecision().isSelected()) {
				isEqual = true;
			} else {
				isEqual = false;
			}
			String value = getTfQueryValue().getText();
			if (value == null || value.equals("")) {
				return;
			}
			isChange = false;
		}

	}

	/**
	 * 查询上下页
	 * 
	 * @param materielType
	 * @param isDownPage
	 */
	private void searchPage(Boolean isDownPage, boolean isProgres) {
		if (!validateData()) {
			return;
		}
		if (isDownPage != null && isDownPage == false) {
			index -= length;
		} else if (isDownPage != null && isDownPage == true) {
			index += length;
		}
		BswProgres.showDialog();
		SwingWorker sk = new SwingWorker() {

			@Override
			protected void done() {
				BswProgres.closeDialog();
				if (count != 0) {
					pages = (count.intValue() / length)
							+ (count % length == 0 ? 0 : 1);
					int currentPage = (index + length) / length;
					// setPageInfo(currentPage, count);
					setPageInfo(currentPage, count, length, pages);
					if (pages == currentPage) {
						btnLastPage.setEnabled(false);
						btnDownPage.setEnabled(false);
					} else {
						btnLastPage.setEnabled(true);
						btnDownPage.setEnabled(true);
					}

				} else {
					setPageInfoIsNull();
				}
				tableModel = initTable(dataSource);
				//
				// 第一次有数据的初始化 cbbQueryField 才是有效的
				//
				if (isFirstHasDataInit) {
					// if (dataSource.size() > 0 && isFirstHasDataInit) {
					initCbbQueryField();
					isFirstHasDataInit = false;
				}
				if (tableModel != null && count == 0) {
					if (length > tableModel.getRowCount()) {
						btnLastPage.setEnabled(false);
						btnDownPage.setEnabled(false);
					} else {
						btnLastPage.setEnabled(true);
						btnDownPage.setEnabled(true);
					}
				}
				if (tableModel != null) {
					if (index == 0) {
						btnUpPage.setEnabled(false);
						btnFirstPage.setEnabled(false);
					} else {
						btnUpPage.setEnabled(true);
						btnFirstPage.setEnabled(true);
					}
				}
				// ------------------------------------以下是新加的BY:kcb
				Integer col = null;
				Object obj = getCbbQueryField().getSelectedItem();
				if (obj == null || !(obj instanceof Item)) {
					return;
				}
				Item itm = (Item) getCbbQueryField().getSelectedItem();
				if (itm == null || itm.getName() == null) {
					return;
				}
				String pro = itm.getName();
				List<JTableListColumn> list = ((JTableListModel) tableModel)
						.getColumns();
				for (int i = 0; i < list.size(); i++) {
					JTableListColumn data = list.get(i);
					if (data.getCaption() == null) {
						continue;
					}
					if (data.getCaption().equals(pro)) {
						col = i;
					}
				}
				boolean isEqual = true;
				if (getRbPrecision().isSelected()) {
					isEqual = true;
				} else {
					isEqual = false;
				}
				String value = getTfQueryValue().getText();
				if (value == null || value.equals("")) {
					return;
				}
				isChange = false;
			}

			@Override
			protected List doInBackground() throws Exception {

				Item item = (Item) cbbQueryField.getSelectedItem();
				dataSource = getDataSource(index, length, item == null ? null
						: item.getProperty(), getValue(), getIsLike());
				System.out.println("isChange --->" + isChange);
				if (isChange) {
					count = getDataSourceCount(index, length,
							item == null ? null : item.getProperty(),
							getValue(), getIsLike());
					System.out.println("count --->" + count);
				}
				return dataSource;
			}
		};
		sk.execute();

	}

	/**
	 * 刷新当前记录数据
	 * 
	 */
	public void refreshData() {
		searchPage(null);
	}

	/**
	 * 初始化表
	 * 
	 * @param dataSource
	 */
	public abstract JTableListModel initTable(List dataSource);

	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public abstract List getDataSource(int index, int length, String property,
			Object value, boolean isLike);

	protected Long getDataSourceCount(int index, int length, String property,
			Object value, boolean isLike) {
		return 0L;
	}

	protected Boolean getIsLikeByInit() {
		return true;
	}

	/**
	 * This method initializes cbIsTrue
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTrue() {
		if (cbIsTrue == null) {
			cbIsTrue = new JCheckBox();
			cbIsTrue.setPreferredSize(new Dimension(20, 27));
			cbIsTrue.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					index = -length;
					setQueryState(false);
				}
			});
		}
		return cbIsTrue;
	}

	public boolean isFirstHasDataInit() {
		return isFirstHasDataInit;
	}

	public void setFirstHasDataInit(boolean isFirstHasDataInit) {
		this.isFirstHasDataInit = isFirstHasDataInit;
	}

	public DocumentListener getDocumentListener() {
		return documentListener;
	}

	public void setDocumentListener(DocumentListener documentListener) {
		this.documentListener = documentListener;
	}

	/**
	 * This method initializes btnFirstPage
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFirstPage() {
		if (btnFirstPage == null) {
			btnFirstPage = new JButton();
			btnFirstPage.setText("首页");
			btnFirstPage.setPreferredSize(new Dimension(60, 25));
			btnFirstPage.setToolTipText("查询最前 " + length + " 条记录!!");
			// btnUpPage.setMnemonic(java.awt.event.KeyEvent.VK_UP);
			btnFirstPage.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			btnFirstPage.setEnabled(false);
			btnFirstPage.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					index = -length;
					searchPage(true);
				}
			});
		}
		return btnFirstPage;
	}

	/**
	 * This method initializes btnLastPage
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLastPage() {
		if (btnLastPage == null) {
			btnLastPage = new JButton();
			btnLastPage.setText("末页");
			btnLastPage.setPreferredSize(new Dimension(60, 25));
			btnLastPage.setToolTipText("查询最后 " + length + " 条记录!!");
			btnLastPage.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			btnLastPage.setEnabled(false);
			btnLastPage.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (pages != null) {
						index = (length * (pages - 2));
						searchPage(true);
					}
				}
			});
		}
		return btnLastPage;
	}

	public boolean isIsProgres() {
		return isProgres;
	}

	public void setIsProgres(boolean isProgres) {
		this.isProgres = isProgres;
	}

	private void setPageInfo(int currentPage, long count) {
		// lbPageInfo.setText("<html><boby>第<font color='black'>" + currentPage
		// + "</font>页,共<font color='black'>" + pages + "</font>页,"
		// + "每页<font color='black'>" + length + "</font>" + "条记录,"
		// + " 共<font color='red'>" + count + "</font>"
		// + "条记录</body></html>");

		lbPageInfo.setText("<html><boby>共<font color='red'>" + count
				+ "</font>" + "条记录</body></html>");
	}

	private void setPageInfo(int currentPage, long count, long length,
			long pages) {

		lbPageInfo.setText("<html><boby>共<font color='red'>" + count
				+ "</font>" + "条记录," + "每页<font color='red'>" + length
				+ "</font>" + "条记录," + "共<font color='red'>" + pages
				+ "</font>页</body></html>");
	}

	private void setPageInfoIsNull() {
		// lbPageInfo.setText("<html><boby>记录为<font color='black'>0"
		// + "</font>条</body></html>");
		lbPageInfo.setText("");
	}

}
