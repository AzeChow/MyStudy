package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.BaseEntity;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmCheckupComplex extends JInternalFrameBase {

	private JPanel jPanel = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JToolBar jJToolBarBar = null;
	private JButton btnAdd = null;
	private JButton btnDel = null;
	private JButton btnCacel = null;
	private JLabel jLabel = null;
	private JComboBox cbbType = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JToolBar jJToolBarBar1 = null;
	private JButton btnAddC = null;
	private JButton btnDelC = null;
	private JButton btnCacelC = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane2 = null;
	private JTable jTable2 = null;
	private JPanel jPanel4 = null;
	private JRadioButton rbBcs = null;
	private JRadioButton rbBcus = null;
	private JRadioButton rbDzsc = null;
	private JLabel jLabel1 = null;
	private JComboBox cbbEmsNo = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JComboBox cbbCounty = null;
	private JButton btnQuery = null;
	private JButton btnCacelF = null;
	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction"); // @jve:decl-index=0:
	private JTableListModel countryModel = null;
	private JTableListModel checkerModel = null;
	private JTableListModel bcsImgModel = null;
	private JTableListModel bcsExgModel = null;
	private JTableListModel dzscExgModel = null;
	private JTableListModel dzscImgModel = null;
	private JTableListModel bcusExgModel = null;
	private JTableListModel bcusImgModel = null;
	private JLabel jLabel3 = null;
	private ButtonGroup gp = null; // @jve:decl-index=0:
	private JRadioButton rbImp = null;
	private JRadioButton rbExp = null;
	private ButtonGroup gp1 = null; // @jve:decl-index=0:
	private EncAction encAction = null;
	private JButton btnEdit = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmCheckupComplex() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		customBaseAction.checkCheckupComplexAuthority(new Request(CommonVars
		.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(783, 471));
		this.setContentPane(getJPanel());
		this.setTitle("商检商品及疫区");
		getGp();
		getGp1();
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			initFirst();
			initCbbCounty();
			setCbbRender();
			initBcsImg(new ArrayList());
			super.setVisible(f);
		}
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
			jPanel.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("商检商品", null, getJPanel1(), null);
			jTabbedPane.addTab("疫区", null, getJPanel2(), null);
			jTabbedPane.addTab("手册合同查询", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (getJTabbedPane().getSelectedIndex() == 0) {
								initFirst();
							} else if (getJTabbedPane().getSelectedIndex() == 1) {
								List list = CustombaseQuery.getInstance()
										.findCountrysCheckuped();
								initCountryTable(list);
							} else if (getJTabbedPane().getSelectedIndex() == 2) {
								initCbbCounty();
							}
						}
					});
		}
		return jTabbedPane;
	}

	private void initFirst() {
		ItemProperty temp = (ItemProperty) getCbbType().getSelectedItem();
		Integer flag = Integer.parseInt(temp.getCode());
		List data = customBaseAction.findComplexByFlag(new Request(CommonVars
				.getCurrUser()), flag);
		initCheckerTable(data);
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJJToolBarBar1(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel2;
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
			jPanel3.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("进出口类型");
			jJToolBarBar = new JToolBar();
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setHgap(1);
			fl.setVgap(1);
			fl.setAlignOnBaseline(true);
			jJToolBarBar.setLayout(fl);
			jJToolBarBar.add(jLabel);
			jJToolBarBar.add(getCbbType());
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDel());
			jJToolBarBar.add(getBtnCacel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(65, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty temp = (ItemProperty) getCbbType()
							.getSelectedItem();
					Integer flag = Integer.parseInt(temp.getCode());
					List data = CustombaseQuery.getInstance()
							.findComplexForCheckupNotIn(flag);
					List rlist = customBaseAction.addCheckupComplex(
							new Request(CommonVars.getCurrUser()), data, flag);
					checkerModel.addRows(rlist);

				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除");
			btnDel.setPreferredSize(new Dimension(65, 30));
			btnDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = checkerModel.getCurrentRows();
					if (list == null || list.size() == 0) {
						JOptionPane.showMessageDialog(FmCheckupComplex.this,
								"请选择数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					customBaseAction.deleteObjects(new Request(CommonVars
							.getCurrUser()), list);
					checkerModel.deleteRows(list);
				}
			});
		}
		return btnDel;
	}

	/**
	 * This method initializes btnCacel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCacel() {
		if (btnCacel == null) {
			btnCacel = new JButton();
			btnCacel.setText("退出");
			btnCacel.setPreferredSize(new Dimension(65, 30));
			btnCacel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCacel;
	}

	/**
	 * This method initializes cbbType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setPreferredSize(new Dimension(118, 28));
			ItemProperty itimp = new ItemProperty(
					new Integer(ImpExpFlag.IMPORT).toString(), "进口");
			ItemProperty itexp = new ItemProperty(
					new Integer(ImpExpFlag.EXPORT).toString(), "出口");
			cbbType.addItem(itimp);
			cbbType.addItem(itexp);
			cbbType.setRenderer(CustomBaseRender.getInstance().getRender());
			// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbType);
			cbbType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					initFirst();
				}
			});
		}
		return cbbType;
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
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jJToolBarBar1 = new JToolBar();
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setHgap(1);
			fl.setVgap(1);
			jJToolBarBar1.setLayout(fl);
			jJToolBarBar1.add(getBtnAddC());
			jJToolBarBar1.add(getBtnDelC());
			jJToolBarBar1.add(getBtnCacelC());
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes btnAddC
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddC() {
		if (btnAddC == null) {
			btnAddC = new JButton();
			btnAddC.setText("新增");
			btnAddC.setPreferredSize(new Dimension(65, 30));
			btnAddC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = CustombaseQuery.getInstance()
							.findCountrysNotCheckup();
					List rlist = customBaseAction.saveCoutryAsChecked(
							new Request(CommonVars.getCurrUser()), list, true);
					countryModel.addRows(rlist);
				}
			});
		}
		return btnAddC;
	}

	/**
	 * This method initializes btnDelC
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelC() {
		if (btnDelC == null) {
			btnDelC = new JButton();
			btnDelC.setText("删除");
			btnDelC.setPreferredSize(new Dimension(65, 30));
			btnDelC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = countryModel.getCurrentRows();
					if (list == null || list.size() == 0) {
						JOptionPane.showMessageDialog(FmCheckupComplex.this,
								"请选择数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List rlist = customBaseAction.saveCoutryAsChecked(
							new Request(CommonVars.getCurrUser()), list, false);
					countryModel.deleteRows(rlist);
				}
			});
		}
		return btnDelC;
	}

	/**
	 * This method initializes btnCacelC
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCacelC() {
		if (btnCacelC == null) {
			btnCacelC = new JButton();
			btnCacelC.setText("退出");
			btnCacelC.setPreferredSize(new Dimension(65, 30));
			btnCacelC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCacelC;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
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
			jSplitPane.setDividerSize(2);
			jSplitPane.setBottomComponent(getJScrollPane2());
			jSplitPane.setTopComponent(getJPanel4());
			jSplitPane.setDividerLocation(79);
			jSplitPane.setEnabled(false);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(267, 10, 37, 23));
			jLabel3.setText("疫区:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(267, 38, 38, 23));
			jLabel2.setText("商编:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 38, 56, 23));
			jLabel1.setText("手册号：");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.add(getRbBcs(), null);
			jPanel4.add(getRbBcus(), null);
			jPanel4.add(getRbDzsc(), null);
			jPanel4.add(jLabel1, null);
			jPanel4.add(getCbbEmsNo(), null);
			jPanel4.add(jLabel2, null);
			jPanel4.add(getJTextField(), null);
			jPanel4.add(getJButton(), null);
			jPanel4.add(getCbbCounty(), null);
			jPanel4.add(getBtnQuery(), null);
			jPanel4.add(getBtnCacelF(), null);
			jPanel4.add(jLabel3, null);
			jPanel4.add(getRbImp(), null);
			jPanel4.add(getRbExp(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes rbBcs
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcs() {
		if (rbBcs == null) {
			rbBcs = new JRadioButton();
			rbBcs.setBounds(new Rectangle(11, 10, 94, 23));
			rbBcs.setText("电子化手册");
			rbBcs.setSelected(true);
			rbBcs.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setCbbRender();
				}
			});
		}
		return rbBcs;
	}

	private int getType() {
		if (getRbBcs().isSelected()) {
			return ProjectType.BCS;
		} else if (getRbDzsc().isSelected()) {
			return ProjectType.DZSC;
		} else if (getRbBcus().isSelected()) {
			return ProjectType.BCUS;
		} else {
			return ProjectType.BCS;
		}
	}

	/**
	 * This method initializes rbBcus
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcus() {
		if (rbBcus == null) {
			rbBcus = new JRadioButton();
			rbBcus.setBounds(new Rectangle(182, 10, 84, 23));
			rbBcus.setText("电子帐册");
			rbBcus.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setCbbRender();
				}
			});
		}
		return rbBcus;
	}

	/**
	 * This method initializes rbDzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDzsc() {
		if (rbDzsc == null) {
			rbDzsc = new JRadioButton();
			rbDzsc.setBounds(new Rectangle(103, 10, 80, 23));
			rbDzsc.setText("电子手册");
			rbDzsc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setCbbRender();
				}
			});
		}
		return rbDzsc;
	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(72, 38, 183, 23));
//			DefaultListCellRenderer dc = new DefaultListCellRenderer() {
//				@Override
//				public Component getListCellRendererComponent(JList list,
//						Object value, int index, boolean isSelected,
//						boolean cellHasFocus) {
//					setComponentOrientation(list.getComponentOrientation());
//
//					Color bg = null;
//					Color fg = null;
//
//					JList.DropLocation dropLocation = list.getDropLocation();
//					if (dropLocation != null && !dropLocation.isInsert()
//							&& dropLocation.getIndex() == index) {
//
//						bg = UIManager.getColor("List.dropCellBackground");
//						fg = UIManager.getColor("List.dropCellForeground");
//
//						isSelected = true;
//					}
//
//					if (isSelected) {
//						setBackground(bg == null ? list.getSelectionBackground()
//								: bg);
//						setForeground(fg == null ? list.getSelectionForeground()
//								: fg);
//					} else {
//						setBackground(list.getBackground());
//						setForeground(list.getForeground());
//					}
//
//					if (value instanceof Icon) {
//						setIcon((Icon) value);
//						setText("");
//					} else {
//						setIcon(null);
//						String str = "";
//						if (value instanceof Contract) {
//							System.out.println("11111111111111");
//							Contract c = (Contract) value;
//							setText(c.getEmsNo()==null?"":c.getEmsNo());
//						} else if (value instanceof DzscEmsPorHead) {
//							System.out.println("22222222222222");
//							DzscEmsPorHead c = (DzscEmsPorHead) value;
//							System.out.println(c.getEmsNo());
//							setText(c.getEmsNo()==null?"":c.getEmsNo());
//						} else if (value instanceof EmsHeadH2k) {
//							System.out.println("333333333333333");
//							EmsHeadH2k c = (EmsHeadH2k) value;
//							setText(c.getEmsNo()==null?"":c.getEmsNo());
//						} else {
//						}
//					}
//
//					setEnabled(list.isEnabled());
//					setFont(list.getFont());
//
//					Border border = null;
//					if (cellHasFocus) {
//						if (isSelected) {
//							border = UIManager
//									.getBorder("List.focusSelectedCellHighlightBorder");
//						}
//						if (border == null) {
//							border = UIManager
//									.getBorder("List.focusCellHighlightBorder");
//						}
//					} else {
//						border = new EmptyBorder(1, 1, 1, 1);
//					}
//					setBorder(border);
//
//					return this;
//				}
//			};
//			cbbEmsNo.setRenderer(dc);
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsNo,
					"emsNo", "emsNo", 250);
			this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
					"emsNo", "emsNo", 0, 150));
		}
		return cbbEmsNo;
	}

	private void setCbbRender() {
		getCbbEmsNo();
		cbbEmsNo.removeAllItems();
		cbbEmsNo.setSelectedIndex(-1);
		int type = getType();
		List ems = encAction.findEmsByProjectType(new Request(CommonVars
				.getCurrUser()), type);
		
		this.cbbEmsNo.setModel(new DefaultComboBoxModel(ems
				.toArray()));
		
//		for (int i = 0; i < ems.size(); i++) {
//			cbbEmsNo.addItem(ems.get(i));
//		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(307, 38, 107, 23));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(415, 38, 25, 23));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer flag = null;
					if (getRbImp().isSelected()) {
						flag = ImpExpFlag.IMPORT;
					} else {
						flag = ImpExpFlag.EXPORT;
					}
					List list = CustombaseQuery.getInstance()
							.findCheckupComplex(flag);
					if (list != null && list.size() > 0) {
						CheckupComplex cp = (CheckupComplex) list.get(0);
						getJTextField().setText(cp.getComplex().getCode());
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes cbbCounty
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCounty() {
		if (cbbCounty == null) {
			cbbCounty = new JComboBox();
			cbbCounty.setBounds(new Rectangle(306, 10, 133, 23));
		}
		return cbbCounty;
	}

	private void initCbbCounty() {
		List coutrys = customBaseAction.findCountry("", "");
		List tempList = new ArrayList();
		for (int i = 0; i < coutrys.size(); i++) {
			Country cou = (Country) coutrys.get(i);
			if (cou == null) {
				continue;
			}
			if (cou.getIsChcekup() != null && cou.getIsChcekup()) {
				tempList.add(cou);
			}
		}
		cbbCounty.setModel(new DefaultComboBoxModel(tempList.toArray()));
		cbbCounty.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCounty);
		cbbCounty.setSelectedItem(null);
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(508, 10, 69, 23));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validData()) {
						return;
					}
					initTbs();
				}
			});
		}
		return btnQuery;
	}

	private void initTbs() {
		int type = getType();
		int flag = getRbImp().isSelected() ? ImpExpFlag.IMPORT
				: ImpExpFlag.EXPORT;
		BaseEntity be = (BaseEntity) getCbbEmsNo().getSelectedItem();
		Country cou = (Country) getCbbCounty().getSelectedItem();
		String code = getJTextField().getText();
		new MySwingWorker(be.getId(), type, flag, cou == null ? "" : cou
				.getCode(), code).execute();
	}

	private boolean validData() {
		boolean f1 = true;
		if (getCbbEmsNo().getSelectedItem() == null) {
			JOptionPane.showMessageDialog(FmCheckupComplex.this, "请选择手册号！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return true;

		}
		if ((getJTextField().getText() == null || getJTextField().getText()
				.trim().equals(""))
				&& (getCbbCounty().getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(FmCheckupComplex.this,
					"疫区和商编，至少要选一个作查询！", "提示！", JOptionPane.WARNING_MESSAGE);
			return true;
		}

		return false;

	}

	/**
	 * This method initializes btnCacelF
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCacelF() {
		if (btnCacelF == null) {
			btnCacelF = new JButton();
			btnCacelF.setBounds(new Rectangle(508, 38, 69, 23));
			btnCacelF.setText("退出");
			btnCacelF.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCacelF;
	}

	private void initCountryTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("代码", "code", 100));
				list.add(new JTableListColumn("名称", "name", 155));
				list.add(new JTableListColumn("英文名称", "countryEnname", 100));
				return list;
			}
		};
		this.countryModel = new JTableListModel(jTable1, list,
				jTableListModelAdapter);
	}

	private void initCheckerTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("商品编码", "complex.code", 100));
				list.add(new JTableListColumn("商品名称", "complex.name", 220));
				list.add(new JTableListColumn("第一法定单位",
						"complex.firstUnit.name", 80));
				list.add(new JTableListColumn("第二法定单位",
						"complex.secondUnit.name", 80));
				list.add(new JTableListColumn("许可证代码", "complex.ccontrol", 150));
				list.add(new JTableListColumn("备注", "complex.note", 260));
				return list;
			}
		};
		this.checkerModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
	}

	private void initBcsImg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "contract.emsNo", 110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.bcsImgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	private void initBcsExg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "contract.emsNo", 110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.bcsExgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	private void initDzscExg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "dzscEmsPorHead.emsNo",
						110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.dzscExgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	private void initDzscImg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "dzscEmsPorHead.emsNo",
						110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.dzscImgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	private void initBcusImg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "emsHeadH2k.emsNo", 110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.bcusImgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	private void initBcusExg(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("手册号", "emsHeadH2k.emsNo", 110));
				list.add(new JTableListColumn("料件序号", "seqNum", 60,Integer.class));
				list.add(new JTableListColumn("商品编码", "complex.code", 80));
				list.add(new JTableListColumn("商品名称", "name", 180));
				list.add(new JTableListColumn("商品规格", "spec", 120));
				list.add(new JTableListColumn("单位", "unit.name", 80));
				return list;
			}
		};
		this.bcusExgModel = new JTableListModel(jTable2, list,
				jTableListModelAdapter);
	}

	public ButtonGroup getGp() {
		if (gp == null) {
			gp = new ButtonGroup();
			gp.add(getRbBcs());
			gp.add(getRbBcus());
			gp.add(getRbDzsc());
		}
		return gp;
	}

	public ButtonGroup getGp1() {
		if (gp1 == null) {
			gp1 = new ButtonGroup();
			gp1.add(getRbImp());
			gp1.add(getRbExp());
		}
		return gp1;
	}

	/**
	 * This method initializes rbImp
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImp() {
		if (rbImp == null) {
			rbImp = new JRadioButton();
			rbImp.setBounds(new Rectangle(445, 10, 56, 23));
			rbImp.setText("进口");
			rbImp.setSelected(true);
		}
		return rbImp;
	}

	/**
	 * This method initializes rbExp
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExp() {
		if (rbExp == null) {
			rbExp = new JRadioButton();
			rbExp.setBounds(new Rectangle(445, 38, 56, 23));
			rbExp.setText("出口");
		}
		return rbExp;
	}

	class MySwingWorker extends SwingWorker {
		int type = ProjectType.BCS;
		int flag = ImpExpFlag.IMPORT;
		String headid = "";
		List data = new ArrayList();
		String couId = "";
		String code = "";

		public MySwingWorker(String headid, int type, int flag, String couId,
				String code) {
			super();
			this.type = type;
			this.flag = flag;
			this.headid = headid;
			this.couId = couId;
			this.code = code;
		}

		@Override
		protected Object doInBackground() throws Exception {

			try {
				CommonProgress.showProgressDialog(); // @jve:decl-index=0:
				CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
				data = encAction.findImgOrExg(new Request(CommonVars
						.getCurrUser()), headid, type, flag, couId, code);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(FmCheckupComplex.this, "加载失败："
						+ e.getMessage(), "提示!", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			if (type == ProjectType.BCS) {
				if (ImpExpFlag.IMPORT == flag) {
					initBcsImg(data);
				} else {
					initBcsExg(data);
				}
			} else if (type == ProjectType.DZSC) {
				if (ImpExpFlag.IMPORT == flag) {
					initDzscImg(data);
				} else {
					initDzscExg(data);
				}
			} else if (type == ProjectType.BCUS) {
				if (ImpExpFlag.IMPORT == flag) {
					initBcusImg(data);
				} else {
					initBcusExg(data);
				}
			}
		}
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
			btnEdit.setPreferredSize(new Dimension(65, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkerModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCheckupComplex.this,
								"请选择你要修改的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DgCheckupComplex dg = new DgCheckupComplex();
					dg.setCheckComplex((CheckupComplex) checkerModel.getCurrentRow());
					dg.setVisible(true);
					if (dg.isOk()) {
						CheckupComplex checkComplex = dg.getCheckComplex();
						checkerModel.updateRow(checkComplex);
					}
				}
			});
		}
		return btnEdit;
	};
} // @jve:decl-index=0:visual-constraint="10,10"
