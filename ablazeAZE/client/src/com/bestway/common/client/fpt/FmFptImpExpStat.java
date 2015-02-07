package com.bestway.common.client.fpt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmFptImpExpStat extends JInternalFrameBase {

	private JSplitPane jSplitPane = null;
	private JPanel jPanel11 = null;
	private JPanel jPanel6 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JCalendarComboBox cbbPortBeginDate = null;
	private JCalendarComboBox cbbPortEndDate = null;
	private JPanel jPanel8 = null;
	private JLabel jLabel19 = null;
	private JLabel jLabel20 = null;
	private JLabel jLabel21 = null;
	private JTextField tfPortSeqNum = null;
	private JTextField tfPortMaterialCode = null;
	private JTextField tfPortMaterialName = null;
	private JButton btnCommSerialNo = null;
	private JButton btnPortQuery = null;
	private JButton btnPortPrint = null;
	private JScrollPane jScrollPane2 = null;
	private JTable tbPort = null;
	private JButton jButton = null;
	private MaterialManageAction materialManageAction = null;
	private FptManageAction fptManageAction = null;
	private JTableListModel portTableModel = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel3 = null;
	private JComboBox jComboBox = null;
	private JComboBox jComboBox1 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JPanel jPanel = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private ButtonGroup bg = null; // @jve:decl-index=0:
	private int projectType = ProjectType.BCUS;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptImpExpStat() {
		super();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		fptManageAction.checkTransferStatisticAnalyse(new Request(CommonVars
				.getCurrUser()));
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		getBg();
		initCompnent();
	}

	private void initCompnent() {
		// ----------------------------------
		jComboBox.addItem(new ItemProperty("0", "生效"));
		jComboBox.addItem(new ItemProperty("1", "未生效"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox.setSelectedIndex(0);
		// ----------------------------------
		jComboBox1.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), ImpExpType
				.getImpExpTypeDesc(ImpExpType.TRANSFER_FACTORY_IMPORT)));
		jComboBox1.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), ImpExpType
				.getImpExpTypeDesc(ImpExpType.TRANSFER_FACTORY_EXPORT)));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox1);

		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox1.setSelectedIndex(0);
		// ----------------------------------
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(834, 469));
		this.setContentPane(getJSplitPane());
		this.setTitle("结转报关进出货明细表");

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
			jSplitPane.setTopComponent(getJPanel11());
			jSplitPane.setBottomComponent(getJScrollPane2());
			jSplitPane.setDividerSize(4);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.add(getJPanel6(), null);
			jPanel11.add(getJPanel8(), null);
			jPanel11.add(getBtnPortQuery(), null);
			jPanel11.add(getBtnPortPrint(), null);
			jPanel11.add(getJButton(), null);
			jPanel11.add(getJPanel(), null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(208, 77, 67, 23));
			jLabel3.setText("生效状态");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(8, 76, 67, 23));
			jLabel2.setText("客户供应商");
			jLabel1 = new JLabel();
			jLabel1.setForeground(Color.BLUE);
			jLabel1.setBounds(new Rectangle(208, 49, 67, 23));
			jLabel1.setText("报关单类型");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(32, 49, 42, 23));
			jLabel.setText("手册号");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(209, 18, 17, 23));
			jLabel16.setText("到");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(18, 18, 58, 23));
			jLabel15.setText("申报日期");
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.setBounds(new Rectangle(9, 6, 374, 111));
			jPanel6.setBorder(BorderFactory.createTitledBorder(null,
					"\u62a5\u5173\u5355\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel6.add(jLabel15, null);
			jPanel6.add(jLabel16, null);
			jPanel6.add(getCbbPortBeginDate(), null);
			jPanel6.add(getCbbPortEndDate(), null);
			jPanel6.add(jLabel, null);
			jPanel6.add(jLabel1, null);
			jPanel6.add(jLabel2, null);
			jPanel6.add(getJTextField(), null);
			jPanel6.add(getJTextField1(), null);
			jPanel6.add(jLabel3, null);
			jPanel6.add(getJComboBox(), null);
			jPanel6.add(getJComboBox1(), null);
			jPanel6.add(getJButton3(), null);
			jPanel6.add(getJButton4(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes cbbPortBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbPortBeginDate() {
		if (cbbPortBeginDate == null) {
			cbbPortBeginDate = new JCalendarComboBox();
			cbbPortBeginDate.setBounds(new Rectangle(77, 18, 122, 23));
			cbbPortBeginDate.setDate(null);
		}
		return cbbPortBeginDate;
	}

	/**
	 * This method initializes cbbPortEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbPortEndDate() {
		if (cbbPortEndDate == null) {
			cbbPortEndDate = new JCalendarComboBox();
			cbbPortEndDate.setBounds(new Rectangle(227, 18, 142, 23));
			cbbPortEndDate.setDate(null);
		}
		return cbbPortEndDate;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new java.awt.Rectangle(12, 79, 86, 18));
			jLabel21.setText("\u5546\u54c1\u540d\u79f0");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new java.awt.Rectangle(12, 49, 86, 18));
			jLabel20.setText("\u5546\u54c1\u7f16\u7801");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new java.awt.Rectangle(12, 19, 86, 17));
			jLabel19.setText("\u5907\u6848\u5e8f\u53f7");
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new Rectangle(479, 6, 215, 107));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null,
					"\u5546\u54c1\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel8.add(jLabel19, null);
			jPanel8.add(jLabel20, null);
			jPanel8.add(jLabel21, null);
			jPanel8.add(getTfPortSeqNum(), null);
			jPanel8.add(getTfPortMaterialCode(), null);
			jPanel8.add(getTfPortMaterialName(), null);
			jPanel8.add(getBtnPortEmsNo(), null);
			jPanel8.add(getJButton1(), null);
			jPanel8.add(getJButton2(), null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes tfPortSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortSeqNum() {
		if (tfPortSeqNum == null) {
			tfPortSeqNum = new JTextField();
			tfPortSeqNum.setBounds(new Rectangle(100, 18, 86, 21));
			tfPortSeqNum.setEditable(false);
		}
		return tfPortSeqNum;
	}

	/**
	 * This method initializes tfPortMaterialCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortMaterialCode() {
		if (tfPortMaterialCode == null) {
			tfPortMaterialCode = new JTextField();
			tfPortMaterialCode.setBounds(new Rectangle(100, 47, 86, 21));
			tfPortMaterialCode.setEditable(false);
		}
		return tfPortMaterialCode;
	}

	/**
	 * This method initializes tfPortMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortMaterialName() {
		if (tfPortMaterialName == null) {
			tfPortMaterialName = new JTextField();
			tfPortMaterialName.setBounds(new Rectangle(100, 77, 86, 21));
			tfPortMaterialName.setEditable(false);
		}
		return tfPortMaterialName;
	}

	/**
	 * This method initializes btnPortEmsNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortEmsNo() {
		if (btnCommSerialNo == null) {
			btnCommSerialNo = new JButton();
			btnCommSerialNo.setBounds(new Rectangle(184, 18, 21, 20));
			btnCommSerialNo.setText("...");
			btnCommSerialNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object temp = (Object) FptQuery.getInstance()
									.findDistinctTranceCustomsDeclaration("序号",
											projectType, "commSerialNo");
							tfPortSeqNum.setText(temp == null ? "" : temp
									.toString());
						}
					});

		}
		return btnCommSerialNo;
	}

	/**
	 * This method initializes btnPortQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortQuery() {
		if (btnPortQuery == null) {
			btnPortQuery = new JButton();
			btnPortQuery.setBounds(new Rectangle(696, 18, 60, 21));
			btnPortQuery.setText("查询");
			btnPortQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbPortBeginDate.getDate();
					Date endDate = cbbPortEndDate.getDate();
					if (beginDate != null && endDate != null
							&& endDate.before(beginDate)) {
						JOptionPane.showMessageDialog(FmFptImpExpStat.this,
								"截止时间小于开始时间，无法查询", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (jComboBox1.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmFptImpExpStat.this,
								"请选择报关单类型！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					new ExecuteThread().start();
				}
			});
		}
		return btnPortQuery;
	}

	/**
	 * This method initializes btnPortPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortPrint() {
		if (btnPortPrint == null) {
			btnPortPrint = new JButton();
			btnPortPrint.setBounds(new Rectangle(696, 50, 60, 21));
			btnPortPrint.setText("\u6253\u5370");
			btnPortPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List list = new ArrayList();
					if (portTableModel != null) {
						list = portTableModel.getList();
					} else
						System.out.println("portTableModel is null@!!");
					try {
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("scmcoc", jTextField1.getText());
						parameters.put("beginDate",
								cbbPortBeginDate.getDate()== null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbPortBeginDate
												.getDate()));
						parameters.put("endDate",
								cbbPortEndDate.getDate()== null ? ""
										: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbPortEndDate
												.getDate()));

						InputStream masterReportStream = FmFptImpExpStat.class
								.getResourceAsStream("report/FmFptImpExpStatReport.jasper");
						if (masterReportStream == null) {
							System.out.println("masterReportStream is null!!");
						}

						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					// System.out.println("actionPerformed()"); // TODO
					// Auto-generated Event stub actionPerformed()
				}
			});
		}
		return btnPortPrint;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbPort());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbPort
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbPort() {
		if (tbPort == null) {
			tbPort = new JTable();
		}
		return tbPort;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(696, 82, 60, 21));
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
		if (portTableModel != null) {
			return;
		}
		portTableModel = new JTableListModel(getTbPort(), new ArrayList(),
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("申报日期",
								"baseCustomsDeclaration.declarationDate", 100));
						list.add(addColumn("报关单类型",
								"baseCustomsDeclaration.impExpType", 100));
						list.add(addColumn("手册序号", "commSerialNo", 100));
						list.add(addColumn("客户/供应商",
								"baseCustomsDeclaration.customer.brief.name",
								150));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "commName", 150));
						list.add(addColumn("商品规格", "commSpec", 150));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("数量", "commAmount", 100));
						return list;
					}
				});
		tbPort.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (isSelected) {
							setForeground(table.getSelectionForeground());
							setBackground(table.getSelectionBackground());
						} else {
							setForeground(table.getForeground());
							setBackground(table.getBackground());
						}
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						if (value == null) {
							return "";
						}
						return ImpExpType.getImpExpTypeDesc(Integer
								.valueOf(value.toString()));
					}
				});
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(184, 48, 21, 19));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery.getInstance()
							.findDistinctTranceCustomsDeclaration("商品编码",
									projectType, "complex.code");
					tfPortMaterialCode.setText(temp == null ? "" : temp
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
			jButton2.setBounds(new Rectangle(184, 78, 21, 18));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery.getInstance()
							.findDistinctTranceCustomsDeclaration("商品名称",
									projectType, "commName");
					tfPortMaterialName.setText(temp == null ? "" : temp
							.toString());
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(77, 49, 102, 23));
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(77, 77, 102, 23));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(276, 77, 95, 23));
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
			jComboBox1.setBounds(new Rectangle(276, 48, 95, 23));

		}
		return jComboBox1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(178, 49, 22, 23));
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery.getInstance()
							.findDistinctTranceCustomsDeclaration("手册号",
									projectType,
									"baseCustomsDeclaration.emsHeadH2k");
					jTextField.setText(temp == null ? "" : temp.toString());
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(178, 77, 22, 21));
			jButton4.setText("...");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object temp = (Object) FptQuery
							.getInstance()
							.findDistinctTranceCustomsDeclaration("客户供应商",
									projectType,
									"baseCustomsDeclaration.customer.brief.name");
					jTextField1.setText(temp == null ? "" : temp.toString());
				}
			});
		}
		return jButton4;
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
			jPanel.setBounds(new Rectangle(384, 5, 92, 108));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u7ba1\u7406\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.add(getJRadioButton2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(3, 27, 80, 22));
			jRadioButton.setSelected(true);
			jRadioButton.setText("电子帐册");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					projectType = ProjectType.BCUS;
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
			jRadioButton1.setBounds(new Rectangle(3, 50, 79, 22));
			jRadioButton1.setText("电子手册");
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					projectType = ProjectType.DZSC;
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(3, 77, 96, 22));
			jRadioButton2.setText("电子化手册");
			jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					projectType = ProjectType.BCS;
				}
			});
		}
		return jRadioButton2;
	}

	private ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getJRadioButton());
			bg.add(getJRadioButton1());
			bg.add(getJRadioButton2());
		}
		return bg;
	}

	class ExecuteThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("正在执行任务，请耐心等待......");
			try {

				Date beginDate = cbbPortBeginDate.getDate();
				Date endDate = cbbPortEndDate.getDate();
				Integer impexpType = Integer.valueOf(((ItemProperty) jComboBox1
						.getSelectedItem()).getCode());
				String emsNo = jTextField.getText();
				String scmcoc = jTextField1.getText();
				String name = tfPortMaterialName.getText();
				Integer seqNum = (getTfPortSeqNum().getText() == null || getTfPortSeqNum()
						.getText().trim().equals("")) ? null : (Integer
						.valueOf(getTfPortSeqNum().getText().trim()));
				String code = tfPortMaterialCode.getText();
				Boolean effec = null;
				ItemProperty item = (ItemProperty) getJComboBox()
						.getSelectedItem();
				if (item != null) {
					if (item.getCode().equals("0")) {
						effec = true;
					} else if (item.getCode().equals("1")) {
						effec = false;
					}
				}

				List list = fptManageAction.findTransferImpExpCustomsList(
						new Request(CommonVars.getCurrUser()), beginDate,
						endDate, impexpType, emsNo, scmcoc, projectType, effec,
						code, name, seqNum);

				portTableModel = new JTableListModel(tbPort, list,
						new JTableListModelAdapter() {
							public List InitColumns() {
								List list = new Vector();
								list
										.add(addColumn(
												"申报日期",
												"baseCustomsDeclaration.declarationDate",
												100));
								list.add(addColumn("报关单类型",
										"baseCustomsDeclaration.impExpType",
										100));
								list
										.add(addColumn("手册序号", "commSerialNo",
												100));
								list
										.add(addColumn(
												"客户/供应商",
												"baseCustomsDeclaration.customer.brief.name",
												150));
								list
										.add(addColumn("商品编码", "complex.code",
												100));
								list.add(addColumn("商品名称", "commName", 150));
								list.add(addColumn("商品规格", "commSpec", 150));
								list.add(addColumn("单位", "unit.name", 100));
								list.add(addColumn("数量", "commAmount", 100));
								return list;
							}
						});
				tbPort.getColumnModel().getColumn(2).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								if (isSelected) {
									setForeground(table
											.getSelectionForeground());
									setBackground(table
											.getSelectionBackground());
								} else {
									setForeground(table.getForeground());
									setBackground(table.getBackground());
								}
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								if (value == null) {
									return "";
								}
								return ImpExpType.getImpExpTypeDesc(Integer
										.valueOf(value.toString()));
							}
						});

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
