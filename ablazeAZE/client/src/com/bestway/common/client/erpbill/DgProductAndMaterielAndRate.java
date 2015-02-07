package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderExg;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgProductAndMaterielAndRate extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JLabel jLabel = null;

	private JScrollPane jScrollPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel7 = null;

	private JPanel jPanel8 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbImg = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbExg = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbBom = null;

	private JList jList = null;

	private JButton btnExit = null;

	private JTableListModel tableModelImg = null; // 合同料件

	private JTableListModel tableModelExg = null; // 成品

	private JTableListModel tableModelBom = null; // 单耗

	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	private Integer customType = 0;  //  @jve:decl-index=0:

	private OrderCommonAction orderCommonAction = null; // @jve:decl-index=0:

	private CustomOrder customOrder = null; // @jve:decl-index=0:

	private JCheckBox jCheckBox = null;

	private JButton btnStat = null;

	private JButton btnPrint = null;

	private JButton btnFind = null;

	private JComboBox cbbPrint = null;

	private boolean isTransfer = false;

	private String reportTilte = null;

	private String orderCode = null;

	private int index = 0;

	private String billCode = null;

	private JButton jButton = null;

	private JPanel jPanel9 = null;

	private JPanel jPanel1 = null;

	/**
	 * @param owner
	 */
	public DgProductAndMaterielAndRate() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");

		initialize();
	}
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			fillList(customType, isTransfer);
		}
		super.setVisible(isFlag);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(844, 528);
		this.setTitle("料件,成品及单耗表");
		this.setContentPane(getJContentPane());
		cbbPrint.removeAllItems();
		cbbPrint.addItem(" 料件表");
		cbbPrint.addItem(" 成品表");
		cbbPrint.addItem(" 单耗表");
		initImgTable(new ArrayList());
		initExgTable(new ArrayList());
		initBomTable(new ArrayList());
		

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
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			jContentPane.add(getJPanel9(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jPanel.setPreferredSize(new Dimension(700, 40));
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(700, 40));
			// jToolBar.add(getRbBCSDZH());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnStat());
			jToolBar.add(getBtnFind());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
	}

	/**
	 * 设置数据源 list ,初始化表格
	 */
	private void showDataByJListSelectedCode(String billCode, int index) {
		customOrder = orderCommonAction.findCustomOrder(new Request(CommonVars
				.getCurrUser()), billCode);
		List listImg = orderCommonAction.findCutomOrderImg(new Request(
				CommonVars.getCurrUser()), customOrder);

		List listExg = orderCommonAction.findCustomOrderExg(new Request(
				CommonVars.getCurrUser()), customOrder);
		initImgTable(listImg);
		
		initExgTable(listExg);

	}

	private void fillList(int customType, boolean isTransfer) {
		Vector vector = new Vector();
		List orderNo = this.orderCommonAction.findCustomOrderByType(
				new Request(CommonVars.getCurrUser()), customType, isTransfer);
       
		for (int i = 0; i < orderNo.size(); i++) {
			customOrder = (CustomOrder) orderNo.get(i);
			vector.add(customOrder);
		}
		this.jList.setListData(vector);
		this.jList.setCellRenderer(new UserListCellRenderer());
		initImgTable(new ArrayList());
		initExgTable(new ArrayList());
		initBomTable(new ArrayList());
		if (this.jList.getModel().getSize() > 0) {
			DgProductAndMaterielAndRate.this.setCustomOrder((CustomOrder) jList
					.getSelectedValue());
			this.jList.setSelectedIndex(0);
			billCode = ((CustomOrder) jList.getSelectedValue()).getBillCode();
			showDataByJListSelectedCode(billCode, index);

		}

	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			customOrder = (CustomOrder) value;
			if (customOrder != null) {
				s = customOrder.getBillCode();

			}
			setText(s);
			return this;
		}
	}

	/**
	 * @return Returns the customOrder.
	 */
	public CustomOrder getCustomOrder() {
		return customOrder;
	}

	/**
	 * @param customOrder
	 *            The customOrder to set.
	 */
	public void setCustomOrder(CustomOrder customOrder) {
		this.customOrder = customOrder;
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
			jPanel2.setPreferredSize(new Dimension(120, 100));
			jPanel2.add(getJPanel4(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel3.setLayout(null);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("订单列表");
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setPreferredSize(new Dimension(60, 28));
			jPanel4.add(jLabel, gridBagConstraints);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("料件表", null, getJPanel6(), null);
			jTabbedPane.addTab("成品及单耗表", null, getJPanel5(), null);
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							if (jList.getSelectedValue() != null) {
								billCode = ((CustomOrder) jList
										.getSelectedValue()).getBillCode();
								if (tab.getSelectedIndex() == 1) {
									index = 1;
									showDataByJListSelectedCode(billCode, index);
								} else if (tab.getSelectedIndex() == 0) {
									index = 0;
									showDataByJListSelectedCode(billCode, index);
								}
							}

							// setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.setToolTipText("成品及单耗表");
			jPanel5.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.setToolTipText("料件表");
			jPanel6.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setTopComponent(getJPanel7());
			jSplitPane1.setBottomComponent(getJPanel8());
			jSplitPane1.setDividerLocation(150);
			jSplitPane1.setDividerSize(2);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbImg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
			tbImg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbImg
									.getModel();
							if (tableModel == null) {
								return;
							}

						}
					});
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbExg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
			tbExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExg == null)
								return;
							if (tableModelExg.getCurrentRow() == null)
								return;
							customOrder = orderCommonAction.findCustomOrder(
									new Request(CommonVars.getCurrUser()),
									billCode);
							CustomOrderExg customOrderExg = (CustomOrderExg) tableModelExg
									.getCurrentRow();
							List listBom = orderCommonAction
									.findCustomOrderBom(new Request(CommonVars
											.getCurrUser()), customOrder,
											customOrderExg);
							initBomTable(listBom);
						}
					});
		}
		return tbExg;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbBom());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes tbBom
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBom() {
		if (tbBom == null) {
			tbBom = new JTable();
		}
		return tbBom;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							if (jList.getSelectedValue() != null) {
								billCode = ((CustomOrder) jList
										.getSelectedValue()).getBillCode();
								showDataByJListSelectedCode(billCode, index);
								// fillList(setbgtype,isTransfer);

							}
						}
					});
		}
		return jList;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setName("jButton");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgProductAndMaterielAndRate.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化料件总表
	 * 
	 */
	private void initImgTable(List list) {
		tableModelImg = new JTableListModel(tbImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("未转合同数量", "notContractNum", 80));
						list.add(addColumn("已转合同数量", "contractNum", 80));
						if (jCheckBox.getSelectedObjects() != null) {
							list.add(addColumn("未转关封数量", "notTransNum", 100));
							list.add(addColumn("已转关封数量", "transNum", 100));
						}
						list.add(addColumn("总金额", "totalPrice", 100));

						return list;
					}
				});

	}

	/**
	 * 初始化成品表
	 * 
	 */
	private void initExgTable(List list) {
		tableModelExg = new JTableListModel(tbExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号",
								"customOrderDetail.materiel.ptNo", 80));
						list.add(addColumn("版本号",
								"customOrderDetail.version", 80));
						list.add(addColumn("送货日期",
								"customOrderDetail.salesdate", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("未转合同数量", "notContractNum", 80));
						list.add(addColumn("已转合同数量", "contractNum", 80));
						if (jCheckBox.getSelectedObjects() != null) {
							list.add(addColumn("未转关封数量", "notTransNum", 100));
							list.add(addColumn("已转关封数量", "transNum", 100));
						}
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("总额", "totalPrice", 100));

						return list;
					}
				});

	}

	/**
	 * 初始化单耗表
	 */
	private void initBomTable(List list) {

		tableModelBom = new JTableListModel(tbBom, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("成品料号", "customOrderExg.customOrderDetail.materiel.ptNo", 80));
						list.add(addColumn("料号", "ptNo", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("单耗", "unitWaste", 100));//edit by kcb   暂时性修改
						list.add(addColumn("损耗%", "waste", 100));
						list.add(addColumn("单项用量", "unitDosage", 100));//edit by kcb  
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("未转合同数量", "notContractNum", 100));
						list.add(addColumn("已转合同数量", "contractNum", 100));
						if (jCheckBox.getSelectedObjects() != null) {
							list.add(addColumn("未转关封数量", "notTransNum", 100));
							list.add(addColumn("已转关封数量", "transNum", 100));
						}
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("金额", "totalPrice", 100));

						return list;
					}
				});

	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("是否转厂");
			jCheckBox.setBounds(new Rectangle(275, 6, 82, 26));
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox.getSelectedObjects() != null) {
						isTransfer = true;

					} else {

						isTransfer = false;
					}
					fillList(customType, isTransfer);

				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes btnStat
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStat() {
		if (btnStat == null) {
			btnStat = new JButton();
			btnStat.setText("统计报表");
			btnStat.setName("jButton1");
			btnStat.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgOrderQuery dgOrderQuery = new DgOrderQuery(customType);
					dgOrderQuery.setVisible(true);
					// dgOrderQuery.setCustomType(setbgtype);

				}
			});
		}
		return btnStat;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(212, 5, 58, 28));
			btnPrint.setName("jButton2");
			btnPrint.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if (jList.getModel().getSize() == 0) {
						JOptionPane.showMessageDialog(
								DgProductAndMaterielAndRate.this, "没有你要打印的资料",
								"确认", 2);
						return;
					}
					int index = cbbPrint.getSelectedIndex();

					String className = null;
					billCode = ((CustomOrder) jList.getSelectedValue())
							.getBillCode();
					orderCode = billCode;
					List result = new ArrayList();
					String reportResouce = null;

					switch (index) {

					case 0:

						className = "CustomOrderImg";
						reportTilte = "　订　单　料　件　表　";
						reportResouce = "report/OrderExgReport.jasper";
						if (jCheckBox.getSelectedObjects() != null) {
							reportResouce = "report/OrderExgIsTransReport.jasper";
						}

						result = orderCommonAction.findReport(new Request(
								CommonVars.getCurrUser()), billCode, className);
						System.out.println("result==" + result.size());
						break;
					case 1:
						className = "CustomOrderExg";
						reportTilte = "　订　单　成　品　表　";
						reportResouce = "report/OrderExgReport.jasper";
						if (jCheckBox.getSelectedObjects() != null) {
							reportResouce = "report/OrderExgIsTransReport.jasper";
						}
						result = orderCommonAction.findReport(new Request(
								CommonVars.getCurrUser()), billCode, className);
						System.out.println("result==" + result.size());
						break;
					case 2:
						className = "CustomOrderBom";
						reportTilte = "　订　单　ＢＯＭ　表　";
						reportResouce = "report/OrderBomReport.jasper";
						if (jCheckBox.getSelectedObjects() != null) {
							reportResouce = "report/OrderBomIsTransReport.jasper";
						}
						result = orderCommonAction.findReport(new Request(
								CommonVars.getCurrUser()), billCode, className);
						System.out.println("result==" + result.size());
						break;
					}
					if (result != null && !result.isEmpty()) {

						CustomReportDataSource ds = new CustomReportDataSource(
								result);
						try {
							Map<String, Object> parameters = new HashMap<String, Object>();

							InputStream reportStream = DgProductAndMaterielAndRate.class
									.getResourceAsStream(reportResouce);

							parameters.put("reportTitle", reportTilte);
							parameters.put("billCode", orderCode);

							JasperPrint jasperPrint = JasperFillManager
									.fillReport(reportStream, parameters, ds);
							DgReportViewer dgReportViewer = new DgReportViewer(
									jasperPrint);
							dgReportViewer.setVisible(true);

						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
					btnPrint.setEnabled(true);

				}

			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton();
			btnFind.setText("查询");
			btnFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgOrderQueryCondition dg = new DgOrderQueryCondition();
					dg.setType(customType);
					dg.setVisible(true);
					dg.setIsTransfer(isTransfer);
					List list = dg.getLsResult();

					if (list != null) {
						Vector vector = new Vector();
						for (int i = 0; i < list.size(); i++) {
							customOrder = (CustomOrder) list.get(i);

							vector.add(customOrder);
						}
						jList.setListData(vector);
						jList.setCellRenderer(new UserListCellRenderer());
						initImgTable(new ArrayList());
						initExgTable(new ArrayList());
						initBomTable(new ArrayList());
						if (jList.getModel().getSize() > 0) {
							DgProductAndMaterielAndRate.this
									.setCustomOrder((CustomOrder) jList
											.getSelectedValue());
							jList.setSelectedIndex(0);
							billCode = ((CustomOrder) jList.getSelectedValue())
									.getBillCode();
							showDataByJListSelectedCode(billCode, index);

						}

					}

				}
			});
		}
		return btnFind;
	}

	/**
	 * This method initializes cbbPrint
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPrint() {
		if (cbbPrint == null) {
			cbbPrint = new JComboBox();
			cbbPrint.setBounds(new Rectangle(6, 5, 199, 26));
		}
		return cbbPrint;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("重新计算");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new InureDataThread().start();
				}
			});

		}
		return jButton;
	}

	private class InureDataThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在重新计算,将生效的订单资料折算成报关资料,请等待...");
			try {
				//wss:2010.05.09修改为只计算选中的那一个
				customOrder = (CustomOrder) jList.getSelectedValue();
				orderCommonAction.saveCustomOrderExg(new Request(CommonVars
							.getCurrUser()), customOrder);
				
				billCode = ((CustomOrder) jList.getSelectedValue())
						.getBillCode();
				showDataByJListSelectedCode(billCode, index);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();

			}

		}
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJPanel2(), BorderLayout.WEST);
			jPanel9.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getCbbPrint(), null);
			jPanel1.add(getBtnPrint(), null);
			jPanel1.add(getJCheckBox(), null);
		}
		return jPanel1;
	}

	public Integer getCustomType() {
		return customType;
	}

	public void setCustomType(Integer customType) {
		this.customType = customType;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
