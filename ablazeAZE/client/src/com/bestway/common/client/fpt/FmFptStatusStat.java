package com.bestway.common.client.fpt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class FmFptStatusStat extends JInternalFrameBase {

	private JSplitPane jSplitPane = null;
	private JPanel jPanel10 = null;
	private JButton btnStatusQuery = null;
	private JButton btnStatusPrint = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbStatus = null;
	private JButton jButton = null;
	private JTableListModel statusTableModel = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbListBeginDate = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbListEndDate = null;
	private JTextField tfListBillCode = null;
	private JButton btnListBillCode = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel31 = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel32 = null;
	private JComboBox jComboBox1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JTextField tfListMaterialCode = null;
	private JTextField tfListMaterialName = null;
	private JTextField tfListSeqNum = null;
	private JButton btnListMaterialCode = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JLabel jLabel3 = null;
	private JComboBox jComboBox3 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup bg = null; // @jve:decl-index=0:
	private FptManageAction fptManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptStatusStat() {
		super();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		tbStatus = new GroupableHeaderTable();
		initialize();
		initCompnent();
		getBg();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(834, 469));
		this.setContentPane(getJSplitPane());
		this.setTitle("转厂进出货状况表");

	}

	private void initCompnent() {
		// ----------------------------------
		jComboBox.addItem(new ItemProperty(DeclareState.APPLY_POR, DeclareState
				.getDeclareStateSpec(DeclareState.APPLY_POR)));
		jComboBox.addItem(new ItemProperty(DeclareState.WAIT_EAA, DeclareState
				.getDeclareStateSpec(DeclareState.WAIT_EAA)));
		jComboBox.addItem(new ItemProperty(DeclareState.PROCESS_EXE,
				DeclareState.getDeclareStateSpec(DeclareState.PROCESS_EXE)));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox.setSelectedIndex(2);
		// ----------------------------------
		jComboBox1.addItem(new ItemProperty(FptBusinessType.FPT_BILL,
				FptBusinessType
						.getFptBusinessTypeDesc(FptBusinessType.FPT_BILL)));
		jComboBox1
				.addItem(new ItemProperty(
						FptBusinessType.FPT_BILL_BACK,
						FptBusinessType
								.getFptBusinessTypeDesc(FptBusinessType.FPT_BILL_BACK)));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox1);
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox1.setSelectedIndex(-1);
		// ----------------------------------
		jComboBox3.addItem(new ItemProperty(String.valueOf(ProjectType.BCS),
				ProjectType.getNote(ProjectType.BCS)));
		jComboBox3.addItem(new ItemProperty(String.valueOf(ProjectType.BCUS),
				ProjectType.getNote(ProjectType.BCUS)));
		jComboBox3.addItem(new ItemProperty(String.valueOf(ProjectType.DZSC),
				ProjectType.getNote(ProjectType.DZSC)));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox3);
		this.jComboBox3.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox3.setSelectedIndex(0);
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
			jSplitPane.setDividerLocation(120);
			jSplitPane.setTopComponent(getJPanel10());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerSize(4);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(null);
			jPanel10.add(getBtnStatusQuery(), null);
			jPanel10.add(getBtnStatusPrint(), null);
			jPanel10.add(getJButton(), null);
			jPanel10.add(getJPanel(), null);
			jPanel10.add(getJPanel2(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes btnStatusQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatusQuery() {
		if (btnStatusQuery == null) {
			btnStatusQuery = new JButton();
			btnStatusQuery.setBounds(new Rectangle(672, 16, 60, 25));
			btnStatusQuery.setText("\u67e5\u8be2");
			btnStatusQuery
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Date beginDate = cbbListBeginDate.getDate();
							Date endDate = cbbListEndDate.getDate();
							if (beginDate != null && endDate != null
									&& endDate.before(beginDate)) {
								JOptionPane.showMessageDialog(
										FmFptStatusStat.this,
										"截止时间小于开始时间，无法查询！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (jComboBox3.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmFptStatusStat.this, "没有选择管理类型，请选择！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							new ExecuteThread().start();
						}
					});

		}
		return btnStatusQuery;
	}

	/**
	 * This method initializes btnStatusPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatusPrint() {
		if (btnStatusPrint == null) {
			btnStatusPrint = new JButton();
			btnStatusPrint.setBounds(new Rectangle(672, 47, 60, 25));
			btnStatusPrint.setText("\u6253\u5370");
			btnStatusPrint
					.addActionListener(new java.awt.event.ActionListener() {
						// 打印报表
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 检查表格中是否有数据
							if (statusTableModel == null
									|| statusTableModel.getList().size() < 1) {
								JOptionPane.showMessageDialog(
										FmFptStatusStat.this, "没有数据可打印", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							// 输出报表
							try {
								String isOut = "1";// 报表中判定是转入,还是转出报关单 1转入 2转出
								if (jRadioButton.isSelected()) {
									isOut = "1";
								} else {
									isOut = "2";
								}
								CustomReportDataSource ds = new CustomReportDataSource(
										statusTableModel.getList());
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("scmcoc", tfListBillCode.getText());
								parameters.put("isOut", isOut);
								Calendar c = Calendar.getInstance();
								String nowDate = c.get(c.YEAR) + "-"
										+ (c.get(c.MONTH) + 1) + "-"
										+ c.get(c.DATE);
								parameters.put("nowDate", nowDate);
								parameters.put("beginDate", cbbListBeginDate
										.getDate() == null ? ""
												: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbListBeginDate
														.getDate()));
								parameters.put("endDate", cbbListEndDate
										.getDate() == null ? ""
												: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbListEndDate
														.getDate()));
								InputStream statusReportStream = FmFptStatusStat.class
										.getResourceAsStream("report/TransferStatusListReport.jasper");
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(statusReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);
							} catch (Exception e1) {
								e1.printStackTrace();
							}

						}

					});
		}
		return btnStatusPrint;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbStatus());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbStatus
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStatus() {
		if (tbStatus == null) {
			tbStatus = new JTable();
		}
		return tbStatus;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(672, 77, 60, 25));
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}

	public void setVisible(boolean f) {
		if (f) {
			initEmptyTable();
		}
		super.setVisible(f);
	}

	private void initEmptyTable() {
		if (statusTableModel != null) {
			return;
		}// (GroupableHeaderTable)
		statusTableModel = new JTableListModel(tbStatus, new ArrayList(),
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("手册序号", "emsSeq", 60));
						list.add(addColumn("客户名称", "scmCoc", 120));
						list.add(addColumn("商品编码", "code", 80));
						list.add(addColumn("名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 150));
						list.add(addColumn("单位", "unit", 50));
						list.add(addColumn("申请单号", "billNo", 80));
						// ----------------------------
						list.add(addColumn("进出货累计", "beTransAmount", 100));
						list.add(addColumn("转厂累计", "beYesTrans", 100));
						list.add(addColumn("未转厂累计", "beNoTrans", 100));
						// ----------------------------
						list.add(addColumn("进出货累计", "thisTransAmount", 100));
						list.add(addColumn("转厂累计", "thYesTrans", 100));
						list.add(addColumn("未转厂累计", "thNoTrans", 100));
						// ----------------------------
						list.add(addColumn("未转厂累计", "noTransAmount", 100));
						return list;
					}
				});
		TableColumnModel cm = tbStatus.getColumnModel();
		ColumnGroup gPrevious = new ColumnGroup("前期");
		gPrevious.add(cm.getColumn(9));
		gPrevious.add(cm.getColumn(10));
		gPrevious.add(cm.getColumn(11));
		ColumnGroup gNow = new ColumnGroup("本期");
		gNow.add(cm.getColumn(12));
		gNow.add(cm.getColumn(13));
		gNow.add(cm.getColumn(14));
		ColumnGroup gTotal = new ColumnGroup("总计");
		gTotal.add(cm.getColumn(15));
		GroupableTableHeader header = (GroupableTableHeader) tbStatus
				.getTableHeader();
		header.addColumnGroup(gPrevious);
		header.addColumnGroup(gNow);
		header.addColumnGroup(gTotal);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(288, 78, 55, 22));
			jLabel3.setForeground(new Color(0, 0, 204));
			jLabel3.setText("管理类型");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(154, 78, 56, 22));
			jLabel32.setText("\u5355\u636e\u7c7b\u578b");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(9, 78, 53, 22));
			jLabel31.setText("\u7533\u62a5\u72b6\u6001");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(12, 48, 75, 22));
			jLabel2.setText("客户/供应商");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(163, 20, 16, 21));
			jLabel1.setText("\u81f3");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(13, 19, 66, 22));
			jLabel.setText("\u53d1\u8d27\u65f6\u95f4\u4ece");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(2, 2, 444, 115));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u5355\u636e\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.add(jLabel, null);
			jPanel.add(getCbbListBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbListEndDate(), null);
			jPanel.add(getTfListBillCode(), null);
			jPanel.add(getBtnListBillCode(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel31, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel32, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox3(), null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbListBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListBeginDate() {
		if (cbbListBeginDate == null) {
			cbbListBeginDate = new JCalendarComboBox();
			cbbListBeginDate.setBounds(new Rectangle(78, 20, 86, 22));
			cbbListBeginDate.setDate(null);
		}
		return cbbListBeginDate;
	}

	/**
	 * This method initializes cbbListEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListEndDate() {
		if (cbbListEndDate == null) {
			cbbListEndDate = new JCalendarComboBox();
			cbbListEndDate.setBounds(new Rectangle(177, 20, 85, 22));
			cbbListEndDate.setDate(null);
		}
		return cbbListEndDate;
	}

	/**
	 * This method initializes tfListBillCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListBillCode() {
		if (tfListBillCode == null) {
			tfListBillCode = new JTextField();
			tfListBillCode.setBounds(new Rectangle(88, 48, 321, 22));
			tfListBillCode.setEditable(false);
		}
		return tfListBillCode;
	}

	/**
	 * This method initializes btnListBillCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListBillCode() {
		if (btnListBillCode == null) {
			btnListBillCode = new JButton();
			btnListBillCode.setBounds(new Rectangle(408, 48, 22, 22));
			btnListBillCode.setText("...");
			btnListBillCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String fptInOutFlag = getJRadioButton()
									.isSelected() ? FptInOutFlag.IN
									: FptInOutFlag.OUT;
							Object temp = (Object) FptQuery
									.getInstance()
									.findDistinctComCocFptBillItem(fptInOutFlag);
							tfListBillCode.setText(temp == null ? "" : temp
									.toString());

						}
					});
		}
		return btnListBillCode;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(63, 78, 86, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(210, 78, 75, 22));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(12, 83, 66, 16));
			jLabel6.setText("\u5907\u6848\u5e8f\u53f7");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(12, 52, 66, 16));
			jLabel5.setText("\u5546\u54c1\u540d\u79f0");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(12, 21, 66, 16));
			jLabel4.setText("\u5546\u54c1\u7f16\u7801");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(450, 2, 208, 114));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u5546\u54c1\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getTfListMaterialCode(), null);
			jPanel2.add(getTfListMaterialName(), null);
			jPanel2.add(getTfListSeqNum(), null);
			jPanel2.add(getBtnListMaterialCode(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes tfListMaterialCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListMaterialCode() {
		if (tfListMaterialCode == null) {
			tfListMaterialCode = new JTextField();
			tfListMaterialCode.setBounds(new Rectangle(78, 20, 98, 20));
			tfListMaterialCode.setEditable(false);
		}
		return tfListMaterialCode;
	}

	/**
	 * This method initializes tfListMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListMaterialName() {
		if (tfListMaterialName == null) {
			tfListMaterialName = new JTextField();
			tfListMaterialName.setBounds(new Rectangle(78, 51, 98, 20));
			tfListMaterialName.setEditable(false);
		}
		return tfListMaterialName;
	}

	/**
	 * This method initializes tfListSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListSeqNum() {
		if (tfListSeqNum == null) {
			tfListSeqNum = new JTextField();
			tfListSeqNum.setBounds(new Rectangle(78, 80, 98, 20));
			tfListSeqNum.setEditable(false);
		}
		return tfListSeqNum;
	}

	/**
	 * This method initializes btnListMaterialCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListMaterialCode() {
		if (btnListMaterialCode == null) {
			btnListMaterialCode = new JButton();
			btnListMaterialCode.setBounds(new Rectangle(176, 20, 19, 19));
			btnListMaterialCode.setText("...");
			btnListMaterialCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object temp = (Object) FptQuery.getInstance()
									.findDistinctProperiesFromFptBillItem(
											"商品编码", "complex.code");
							tfListMaterialCode.setText(temp == null ? "" : temp
									.toString());
						}
					});
		}
		return btnListMaterialCode;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(176, 51, 19, 19));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery.getInstance()
							.findDistinctProperiesFromFptBillItem("商品名称",
									"commName");
					tfListMaterialName.setText(temp == null ? "" : temp
							.toString());
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(176, 80, 19, 19));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery.getInstance()
							.findDistinctProperiesFromFptBillItem("备案序号",
									"trGno");
					tfListSeqNum.setText(temp == null ? "" : temp.toString());
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(new Rectangle(346, 78, 85, 22));
		}
		return jComboBox3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(351, 19, 58, 22));
			jRadioButton.setText("转入");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					tfListBillCode.setText("");
				}
			});
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
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("转出");
			jRadioButton1.setBounds(new Rectangle(284, 19, 59, 22));
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					tfListBillCode.setText("");
				}
			});
		}
		return jRadioButton1;
	}

	public ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getJRadioButton());
			bg.add(getJRadioButton1());
		}
		return bg;
	}

	class ExecuteThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("正在执行任务，请耐心等待......");
			try {
				//发货时间的开始
				Date beginDate = cbbListBeginDate.getDate();
				//发货时间的结束
				Date endDate = cbbListEndDate.getDate();
				//转入或转出
				String fptInOutFlag = getJRadioButton().isSelected() ? FptInOutFlag.IN
						: FptInOutFlag.OUT;
				//客户/供应商
				String scmcocName = tfListBillCode.getText();
				//申报状态
				String declareState = ((ItemProperty) jComboBox.getSelectedItem()) == null ? null
						: ((ItemProperty) jComboBox.getSelectedItem()).getCode();
				//单据类型
				String fptBusinessType = ((ItemProperty) jComboBox1.getSelectedItem()) == null ? null
						: ((ItemProperty) jComboBox1.getSelectedItem()).getCode();
				//管理类型
				int projectType = Integer.valueOf(((ItemProperty) jComboBox3
						.getSelectedItem()).getCode());
				//商品编码
				String code = tfListMaterialCode.getText();
				//商品名称
				String name = tfListMaterialName.getText();
				//商品序号
				Integer seqNum = tfListSeqNum.getText() == null ? null: (tfListSeqNum.getText().trim()
						.equals("") ? null: Integer.valueOf(tfListSeqNum.getText()));
				List list = fptManageAction
						.findStatisticTotalTransferStatusQuantity(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								fptInOutFlag, declareState, fptBusinessType,
								code, name, seqNum, projectType, scmcocName);
				statusTableModel = new JTableListModel(tbStatus, list,
						new JTableListModelAdapter() {
							public List InitColumns() {
								List<JTableListColumn> list = new ArrayList<JTableListColumn>();
								list.add(addColumn("手册号", "emsNo", 100));
								list.add(addColumn("手册序号", "emsSeq", 60));
								list.add(addColumn("客户名称", "scmCoc", 120));
								list.add(addColumn("商品编码", "code", 80));
								list.add(addColumn("名称", "name", 150));
								list.add(addColumn("型号规格", "spec", 150));
								list.add(addColumn("单位", "unit", 50));
								list.add(addColumn("申请单号", "billNo", 80));
								// ----------------------------
								list.add(addColumn("进出货累计", "beTransAmount",
										100));
								list.add(addColumn("转厂累计", "beYesTrans", 100));
								list.add(addColumn("未转厂累计", "beNoTrans", 100));
								// ----------------------------
								list.add(addColumn("进出货累计", "thisTransAmount",
										100));
								list.add(addColumn("转厂累计", "thYesTrans", 100));
								list.add(addColumn("未转厂累计", "thNoTrans", 100));
								// ----------------------------
								list.add(addColumn("未转厂累计", "noTransAmount",
										100));
								return list;
							}
						});
				TableColumnModel cm = tbStatus.getColumnModel();
				ColumnGroup gPrevious = new ColumnGroup("前期");
				gPrevious.add(cm.getColumn(9));
				gPrevious.add(cm.getColumn(10));
				gPrevious.add(cm.getColumn(11));
				ColumnGroup gNow = new ColumnGroup("本期");
				gNow.add(cm.getColumn(12));
				gNow.add(cm.getColumn(13));
				gNow.add(cm.getColumn(14));
				ColumnGroup gTotal = new ColumnGroup("总计");
				gTotal.add(cm.getColumn(15));
				GroupableTableHeader header = (GroupableTableHeader) tbStatus
						.getTableHeader();
				header.addColumnGroup(gPrevious);
				header.addColumnGroup(gNow);
				header.addColumnGroup(gTotal);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				initEmptyTable();
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}

		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
