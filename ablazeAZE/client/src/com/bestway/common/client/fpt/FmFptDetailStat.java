package com.bestway.common.client.fpt;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
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
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmFptDetailStat extends JInternalFrameBase {

	private JSplitPane jSplitPane = null;
	private JPanel jPanel9 = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbListBeginDate = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbListEndDate = null;
	private JButton btnListBillCode = null;
	private JTextField tfListBillCode = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JTextField tfListMaterialCode = null;
	private JTextField tfListMaterialName = null;
	private JTextField tfListSeqNum = null;
	private JButton btnListMaterialCode = null;
	private JButton btnListQuery = null;
	private JButton btnListPrint = null;
	private JScrollPane jScrollPane = null;
	private JTable tbList = null;
	private FptManageAction fptManageAction = null;
	private JTableListModel listTableModel = null;
	private JButton jButton = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel31 = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel32 = null;
	private JComboBox jComboBox1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup bg = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public FmFptDetailStat() {
		super();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		fptManageAction.checkTransferStatisticAnalyse(new Request(
				CommonVars.getCurrUser()));
		initialize();
		initCompnent();
	}

	private void initCompnent() {
		// ----------------------------------
		getBg();
		jComboBox.addItem(new ItemProperty(DeclareState.APPLY_POR, DeclareState.getDeclareStateSpec(DeclareState.APPLY_POR)));
		jComboBox.addItem(new ItemProperty(DeclareState.WAIT_EAA, DeclareState.getDeclareStateSpec(DeclareState.WAIT_EAA)));
		jComboBox.addItem(new ItemProperty(DeclareState.PROCESS_EXE,DeclareState.getDeclareStateSpec(DeclareState.PROCESS_EXE)));
		jComboBox.addItem(new ItemProperty(DeclareState.IS_CANCELED,DeclareState.getDeclareStateSpec(DeclareState.IS_CANCELED)));
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
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(835, 513));
		this.setContentPane(getJSplitPane());
		this.setTitle("深加工结转进出货明细表");

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
			jSplitPane.setTopComponent(getJPanel9());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerSize(4);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(null);
			jPanel9.add(getJPanel(), null);
			jPanel9.add(getJPanel2(), null);
			jPanel9.add(getBtnListQuery(), null);
			jPanel9.add(getBtnListPrint(), null);
			jPanel9.add(getJButton(), null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(236, 75, 56, 23));
			jLabel32.setText("单据类型");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(11, 77, 60, 20));
			jLabel31.setText("申报状态");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(11, 48, 80, 21));
			jLabel2.setText("客户/供应商");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(168, 20, 16, 21));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(3, 23, 90, 17));
			jLabel.setText("收/发货时间从");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(11, 5, 425, 115));
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
			cbbListBeginDate.setBounds(new Rectangle(83, 20, 86, 22));
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
			cbbListEndDate.setBounds(new Rectangle(182, 20, 85, 22));
			cbbListEndDate.setDate(null);
		}
		return cbbListEndDate;
	}

	/**
	 * This method initializes btnListBillCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListBillCode() {
		if (btnListBillCode == null) {
			btnListBillCode = new JButton();
			btnListBillCode.setText("...");
			btnListBillCode.setBounds(new Rectangle(318, 49, 24, 21));
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
							
//							String scmcoc = "";
//							if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
//								scmcoc = "fptBillHead.receiveTradeName";
//							} else if (FptInOutFlag.IN.equals(fptInOutFlag)) {
//								scmcoc = "fptBillHead.issueTradeName";
//							}
//							String str = "";
//							billNoList.clear();
//							List intemp = TransferFactoryQuery.getInstance()
//									.findFptBillItemBillNo("企业内部编号", scmcoc);
//							if (intemp != null) {
//								for (int i = 0; i < intemp.size(); i++) {
//									Object obj = intemp.get(i);
//									if (obj != null
//											&& !obj.toString().trim()
//													.equals("")) {
//										billNoList.add(obj.toString().trim());
//										str += (obj.toString().trim() + ";");
//									}
//
//								}
//							}
//							tfListBillCode.setText(str);
						}
					});
		}
		return btnListBillCode;
	}

	/**
	 * This method initializes tfListBillCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListBillCode() {
		if (tfListBillCode == null) {
			tfListBillCode = new JTextField();
			tfListBillCode.setBounds(new Rectangle(93, 49, 224, 21));
			tfListBillCode.setEditable(false);
		}
		return tfListBillCode;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(12, 80, 66, 16));
			jLabel6.setText("\u5907\u6848\u5e8f\u53f7");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(12, 52, 66, 16));
			jLabel5.setText("\u5546\u54c1\u540d\u79f0");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(12, 21, 66, 16));
			jLabel4.setText("\u5546\u54c1\u7f16\u7801");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(440, 5, 231, 115));
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
							Object temp = (Object) FptQuery
									.getInstance()
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
	 * This method initializes btnListQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListQuery() {
		if (btnListQuery == null) {
			btnListQuery = new JButton();
			btnListQuery.setBounds(new Rectangle(684, 11, 60, 25));
			btnListQuery.setText("\u67e5\u8be2");
			btnListQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbListBeginDate.getDate();
					Date endDate = cbbListEndDate.getDate();
					if (beginDate != null && endDate != null
							&& endDate.before(beginDate)) {
						JOptionPane.showMessageDialog(FmFptDetailStat.this,
								"截止时间小于开始时间，无法查询！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					new ExecuteThread().start();
				}
			});
		}
		return btnListQuery;
	}

	/**
	 * This method initializes btnListPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListPrint() {
		if (btnListPrint == null) {
			btnListPrint = new JButton();
			btnListPrint.setBounds(new Rectangle(684, 45, 60, 25));
			btnListPrint.setText("\u6253\u5370");
			btnListPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (listTableModel == null
							|| listTableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								FmFptDetailStat.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					try {
//						Date Begindate = cbbListBeginDate.getDate();
//						Date Enddate = cbbListEndDate.getDate();
//						String TextBegindate = "起始日期";
//						String TextEnddate = "截止日期";
//						String Begindate1 = CommonVars.dateToString(Begindate);
//						String Enddate1 = CommonVars.dateToString(Enddate);
//						if (Begindate == null) {
//							Begindate1 = "";
//							TextBegindate = "";
//						}
//						if (Enddate == null) {
//							Enddate1 = "";
//							TextEnddate = "";
//						}
//						System.out.println("起始日期>>"+Begindate.toLocaleString());
//						System.out.println("截止日期>>"+Enddate.toLocaleString());
						String isOut="1";//报表中判定是转入,还是转出报关单 1转入  2转出
						if(jRadioButton.isSelected()){
							isOut="1";
						}else{
							isOut="2";
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								listTableModel.getList());
						Map<String, Object> parameters = new HashMap<String, Object>();
//						parameters.put("Begindate", Begindate1);
//						parameters.put("Enddate", Enddate1);
//						parameters.put("TextBegindate", TextBegindate);
//						parameters.put("TextEnddate", TextEnddate);
						Calendar c = Calendar.getInstance();
						String nowDate=c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+c.get(c.DATE);
						parameters.put("nowDate",nowDate);						
						parameters.put("isOut", isOut);
						parameters.put("scmCoc", tfListBillCode.getText());
						
						parameters.put("beginDate", cbbListBeginDate.getDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbListBeginDate
										.getDate()));
						parameters.put("endDate", cbbListEndDate.getDate() == null ? ""
								: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbListEndDate
										.getDate()));
						
						InputStream masterReportStream = FmFptDetailStat.class
								.getResourceAsStream("report/TransferImpExpGoodsListReport1.jasper");						
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnListPrint;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbList
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbList() {
		if (tbList == null) {
			tbList = new JTable();
		}
		return tbList;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(684, 79, 60, 25));
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
		if (listTableModel != null) {
			return;
		}
		listTableModel = new JTableListModel(tbList, new ArrayList(),
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("收发货日期","fptBillHead.issueDate", 80));
						list.add(addColumn("单据类型", "fptBillHead.sysType", 50));
						list.add(addColumn("单据编码", "fptBillHead.billNo", 130));
						list.add(addColumn("供应商","fptBillHead.receiveAgentName", 150));
						list.add(addColumn("申请单编号", "fptBillHead.appNo", 120));
						list.add(addColumn("手册序号", "fptBillHead.outEmsNo",100));
						list.add(addColumn("商品名称", "commName", 150));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("型号规格", "commSpec", 150));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("数量", "qty", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(75, 76, 146, 23));
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
			jComboBox1.setBounds(new Rectangle(291, 75, 121, 24));
		}
		return jComboBox1;
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
			jButton2.setBounds(new Rectangle(175, 80, 20, 19));
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
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(357, 20, 55, 23));
			jRadioButton.setText("转入");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getTfListBillCode().setText("");
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
			jRadioButton1.setBounds(new Rectangle(302, 19, 53, 25));
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("转出");
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getTfListBillCode().setText("");
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

				Date beginDate = cbbListBeginDate.getDate();
				Date endDate = cbbListEndDate.getDate();
				String fptInOutFlag = getJRadioButton().isSelected() ? FptInOutFlag.IN: FptInOutFlag.OUT;
				String declareState = ((ItemProperty) jComboBox.getSelectedItem()) == null ? null: ((ItemProperty) jComboBox.getSelectedItem()).getCode();
				String fptBusinessType = ((ItemProperty) jComboBox1.getSelectedItem()) == null ? null: ((ItemProperty) jComboBox1.getSelectedItem()).getCode();
				String code = tfListMaterialCode.getText();
				String name = tfListMaterialName.getText();
				Integer seqNum = tfListSeqNum.getText() == null ? null: (tfListSeqNum.getText().trim().equals("") ? null: Integer.valueOf(tfListSeqNum.getText()));
				String scmsoc=tfListBillCode.getText();
				List list = fptManageAction.findTransferFactoryImpExpGoodsList
						(new Request(CommonVars.getCurrUser()), beginDate, endDate,fptInOutFlag, declareState, fptBusinessType,code, name, seqNum, scmsoc);
				final String flag = fptInOutFlag;
				listTableModel = new JTableListModel(tbList, list,
						new JTableListModelAdapter() {
							public List InitColumns() {
								List list = new Vector();
								if (flag.equals(FptInOutFlag.IN)) {
									list.add(addColumn("收货日期","fptBillHead.receiveDate", 80));
								} else {
									list.add(addColumn("发货日期","fptBillHead.issueDate", 80));
								}
								list.add(addColumn("单据类型","fptBillHead.sysType", 60));
								if (flag.equals(FptInOutFlag.IN)) {
									list.add(addColumn("手册号","inEmsNo", 100));
								} else {
									list.add(addColumn("手册号","fptBillHead.outEmsNo", 100));
								}
								list.add(addColumn("申请表编号","fptBillHead.appNo", 100));
								list.add(addColumn("单据编码","fptBillHead.billNo", 130));
								if (flag.equals(FptInOutFlag.IN)) {
									list.add(addColumn("供应商","fptBillHead.issueTradeName",150));
								} else {
									list.add(addColumn("客户","fptBillHead.receiveTradeName", 150));
								}
								list.add(addColumn("申请表序号", "listNo",60));
								list.add(addColumn("手册序号", "trGno",60));

								list.add(addColumn("商品编码", "complex.code",80));
								list.add(addColumn("商品名称", "commName", 150));
								
								list.add(addColumn("型号规格", "commSpec", 150));
								list.add(addColumn("单位", "unit.name", 80));
								list.add(addColumn("数量", "qty", 80));
								return list;
							}
						});
				tbList.getColumnModel().getColumn(2).setCellRenderer(
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
								return FptBusinessType
										.getFptBusinessTypeDesc(value
												.toString());
							}
						});
				
				FptBillItem f =new FptBillItem();
//				listTableModel.addRow("");
				Double sum = 0.0;
			    for(int i = 0; i < listTableModel.getRowCount(); i++){
			    	sum += Double.parseDouble((String)listTableModel.getValueAt(i, 13));
			    }
//			    listTableModel.setAllowSetValue(true);
//				listTableModel.setValueAt(sum, listTableModel.getRowCount() - 1, 13);
			    f.setQty(sum);
			    listTableModel.addRow(f);
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
