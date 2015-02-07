package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyes;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmFptAppSpareAnalyse extends FmCommon {

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JTextField tfComplexCode = null;

	private JTextField tfName = null;

	private JTextField tfEName = null;

	private JButton btnComplexCode = null;

	private JButton btnOppositeEnterpriseName = null;

	private JTableListModel tableModel = null;

	private JSplitPane jSplitPane = null;

	private JButton btnExit = null;

	private JPanel jPanel = null;

	private ButtonGroup bg = new ButtonGroup(); 
	private ButtonGroup bg1 = new ButtonGroup(); 
	private JRadioButton rbOut = null;

	private JRadioButton rbIn = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbAll = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel51 = null;

	private JComboBox cbbProjectType = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptAppSpareAnalyse() {
		super();
		super.fptManageAction.checkCustomsEnvelopSpareAnalyse(new Request(
				CommonVars.getCurrUser()));
		initialize();
		initUIComponents();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("申请表余量分析");
		this.setContentPane(getJContentPane());
		this.setSize(752, 447);

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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();

			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						browse();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 浏览
	 * 
	 */
	private void browse() {
		TempFptAppSpareAnalyes head = (TempFptAppSpareAnalyes) tableModel
				.getCurrentRow();
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要显示的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFptAppSpareAnalyseDetail dg = new DgFptAppSpareAnalyseDetail();
		//
		// 获得查询参数对象
		//
		TempFptAppParam param = getTempFptAppParam();
		param.setComplex(head.getComplex());
		param.setTradeName(head.getTradeName());
		param.setInOutFlag(head.getInOutFlag());
		param.setName(head.getName());
		dg.setParam(param);
		dg.setVisible(true);
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
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(203, 16, 54, 22));
			jLabel51.setText("\u9879\u76ee\u7c7b\u578b");
			jLabel51.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(516, 16, 17, 22));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(365, 16, 66, 22));
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setText("申报时间从");
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8fc7\u6ee4\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jLabel2.setBounds(375, 41, 56, 22);
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("商品名称");
			jLabel3.setBounds(179, 66, 78, 22);
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3.setText("客户/供应商");
			jLabel4.setBounds(203, 41, 54, 22);
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4.setText("商品编码");
			jPanel1.add(getTfComplexCode(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(getTfEName(), null);
			jPanel1.add(getBtnComplexCode(), null);
			jPanel1.add(getBtnOppositeEnterpriseName(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getBtnSearch(), null);
			jPanel1.add(getBtnPrint(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getJPanel(), null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel51, null);
			jPanel1.add(getCbbProjectType(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setBounds(new Rectangle(637, 16, 62, 22));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SearchThread().start();
				}
			});
		}
		return btnSearch;
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
			btnPrint.setBounds(new Rectangle(637, 41, 62, 22));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(258, 41, 86, 22);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(431, 41, 190, 22);
		}
		return tfName;
	}

	/**
	 * This method initializes tfEName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEName() {
		if (tfEName == null) {
			tfEName = new JTextField();
			tfEName.setBounds(258, 66, 341, 22);
		}
		return tfEName;
	}

	/**
	 * This method initializes btnComplexCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplexCode() {
		if (btnComplexCode == null) {
			btnComplexCode = new JButton();
			btnComplexCode.setBounds(343, 41, 20, 22);
			btnComplexCode.setText("...");
			btnComplexCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = CommonQuery.getInstance().getComplex();
							if (obj != null) {
								tfComplexCode
										.setText(((Complex) obj).getCode());
							}
						}
					});

		}
		return btnComplexCode;
	}

	/**
	 * This method initializes btnOppositeEnterpriseName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOppositeEnterpriseName() {
		if (btnOppositeEnterpriseName == null) {
			btnOppositeEnterpriseName = new JButton();
			btnOppositeEnterpriseName.setBounds(598, 66, 22, 22);
			btnOppositeEnterpriseName.setText("...");
			btnOppositeEnterpriseName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							ScmCoc b = null;
							if(rbOut.isSelected()){
								 b = (ScmCoc) FptQuery
										.getInstance().getFptAppScmCon(FptInOutFlag.IN);//客户
							}else{
								 b = (ScmCoc) FptQuery
											.getInstance().getFptAppScmCon(FptInOutFlag.OUT);//供应商
							}
							if (b != null) {
								tfEName.setText(b.getName());
							}
						}
					});
		}
		return btnOppositeEnterpriseName;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		bg.add(rbOut);
		bg.add(rbIn);

		bg1.add(rbYes);
		bg1.add(rbAll);

		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);

		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "电子化手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(0);

		initTable(new ArrayList());
	}

	/**
	 * 获得查询参数对象
	 * 
	 * @return
	 */
	private TempFptAppParam getTempFptAppParam() {
		TempFptAppParam param = new TempFptAppParam();
		if (rbOut.isSelected()) {
			param.setInOutFlag(FptInOutFlag.OUT);
		} else {
			param.setInOutFlag(FptInOutFlag.IN);
		}

		if (rbYes.isSelected()) {
			param.setDeclareState(DeclareState.PROCESS_EXE);
		} else {
//			param.setDeclareState(DeclareState.PROCESS_ALL);
			param.setDeclareState(null);
		}

		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			param.setProjectType(projectType);
		} else {
			param.setProjectType(null);
		}

		if (!this.tfEName.getText().trim().equals("")) {
			param.setTradeName(this.tfEName.getText());
		}
		param.setBeginDate(this.cbbBeginDate.getDate());
		param.setEndDate(this.cbbEndDate.getDate());
		if (!this.tfComplexCode.getText().trim().equals("")) {
			param.setComplex(this.tfComplexCode.getText());
		}
		if (!this.tfName.getText().trim().equals("")) {
			param.setName(this.tfName.getText());
		}
		return param;
	}

	/**
	 * 报表打印
	 */
	private void printReport() {
		try {
			List list = this.tableModel.getList();
			if (list == null || list.size() <= 0) {
				JOptionPane.showMessageDialog(null, "请选择打印数据!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("beginDate", cbbBeginDate.getDate() == null ? ""
					: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbBeginDate
							.getDate()));
			parameters.put("endDate", cbbEndDate.getDate() == null ? ""
					: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbEndDate
							.getDate()));

			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = FmFptAppSpareAnalyse.class
					.getResourceAsStream("report/TempFptAppSpareAnalyes.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	// /**
	// * 查询
	// */
	// private void search() {
	// btnSearch.setEnabled(false);
	// initTableByOut(list);
	// }

	class SearchThread extends Thread {
		public void run() {
			List<TempFptAppSpareAnalyes> list = new ArrayList<TempFptAppSpareAnalyes>();
			try {
				btnSearch.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				//
				// 获得查询参数对象
				//
				TempFptAppParam param = getTempFptAppParam();

				list = fptManageAction.findTempFptAppSpareAnalyes(new Request(
						CommonVars.getCurrUser()), param);
				// if (list.isEmpty()) {
				// JOptionPane.showMessageDialog(FmFptAppSpareAnalyse.this,
				// "查到0笔数据", "提示", JOptionPane.INFORMATION_MESSAGE);
				// }
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFptAppSpareAnalyse.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				btnSearch.setEnabled(true);
				initTable(list);
			}
		}
	}

	/**
	 * 初始化表
	 * 
	 * @param list
	 */
	private void initTable(List list) {
		if (rbOut.isSelected()) {
			initTableByOut(list);
		} else {
			initTableByIn(list);
		}
	}

	/**
	 * 初始化数据Table
	 */
	private void initTableByOut(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("转入转出标记", "inOutFlag", 100));
						list.add(addColumn("客户名称", "tradeName", 100));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 80));
						list.add(addColumn("规格型号", "spec", 80));
						list.add(addColumn("计量单位", "unitName", 100));

						list.add(addColumn("1.合同定量", "f1", 80));
						list.add(addColumn("2.可出口量", "f2", 80));

						list.add(addColumn("3.申请表数量", "f3", 80));
						list.add(addColumn("4.累计申请表数量", "f4", 100));
						list.add(addColumn("5.可申请数量", "f5", 80));
						// 6.已收货数量
						list.add(addColumn("6.已送货数量(7+8)", "f6", 80));
						// 7.已收发数量
						list.add(addColumn("7.已发送数量", "f7", 80));
						// 8.未收发数量
						list.add(addColumn("8.未发送数量", "f8", 80));
						// 9.可收发数量(5-7)
						list.add(addColumn("9.申请表余量(3-7)", "f9", 80));
						// 10.已转厂数量(报关)
						list.add(addColumn("10.已转厂数量", "f10", 80));
						// 11.未转厂数量(6-10)(报关)
						list.add(addColumn("11.未转厂数量(6-10)", "f11", 80));
						// 12.可收货数量
						list.add(addColumn("12.可送货数量(3-6)", "f12", 80));
						// 13.累计发货数量
						list.add(addColumn("13.累计收货数量", "f13", 100));
						list.add(addColumn("14.累计转厂数", "f14", 80));
						list.add(addColumn("15.总未转厂数（期初单+6）", "f15", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return FptInOutFlag.getNote(value.toString());
					}
				});
	}

	/**
	 * 初始化数据Table
	 */
	private void initTableByIn(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("转入转出标记", "inOutFlag", 100));
						list.add(addColumn("供应商名称", "tradeName", 100));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 80));
						list.add(addColumn("规格型号", "spec", 80));
						list.add(addColumn("计量单位", "unitName", 100));

						list.add(addColumn("1.合同定量", "f1", 80));
						list.add(addColumn("2.可进口量", "f2", 80));

						list.add(addColumn("3.申请表数量", "f3", 80));
						list.add(addColumn("4.累计申请表数量", "f4", 100));
						list.add(addColumn("5.可申请数量", "f5", 80));
						// 6.已收货数量
						list.add(addColumn("6.已收货数量(7+8)", "f6", 80));
						// 7.已收发数量
						list.add(addColumn("7.已收发数量", "f7", 80));
						// 8.未收发数量
						list.add(addColumn("8.未收发数量", "f8", 80));
						// 9.可收发数量(5-7)
						list.add(addColumn("9.申请表余量(3-7)", "f9", 80));
						// 10.已转厂数量(报关)
						list.add(addColumn("10.已转厂数量", "f10", 80));
						// 11.未转厂数量(6-10)(报关)
						list.add(addColumn("11.未转厂数量(6-10)", "f11", 80));
						// 12.可收货数量
						list.add(addColumn("12.可收货数量(3-6)", "f12", 80));
						// 13.累计发货数量
						list.add(addColumn("13.累计发货数量", "f13", 100));
						list.add(addColumn("14.累计转厂数", "f14", 80));
						list.add(addColumn("15.总未转厂数（期初单+6）", "f15", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return FptInOutFlag.getNote(value.toString());
					}
				});
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
			jSplitPane.setDividerLocation(100);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(638, 66, 62, 22));
			btnExit.setText("\u5173\u95ed");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
			jPanel.setBounds(new Rectangle(18, 13, 151, 39));
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u7533\u8bf7\u8868\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel.add(getRbOut(), null);
			jPanel.add(getRbIn(), null);
			jPanel.setOpaque(false);
		}
		return jPanel;
	}

	/**
	 * This method initializes rbOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOut() {
		if (rbOut == null) {
			rbOut = new JRadioButton();
			rbOut.setBounds(new Rectangle(14, 17, 54, 17));
			rbOut.setSelected(true);
			rbOut.setText("转出");
			
		}
		return rbOut;
	}

	/**
	 * This method initializes rbIn
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIn() {
		if (rbIn == null) {
			rbIn = new JRadioButton();
			rbIn.setBounds(new Rectangle(75, 16, 54, 17));
			rbIn.setText("转入");
			
		}
		return rbIn;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(18, 53, 151, 39));
			jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u7533\u8bf7\u8868\u7533\u62a5\u72b6\u6001",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel2.add(getRbYes(), null);
			jPanel2.add(getRbAll(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbYes
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(8, 17, 80, 17));
			rbYes.setText("正在执行");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes rbAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(90, 16, 55, 17));
			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(431, 16, 85, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(535, 16, 85, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(258, 16, 105, 22));
		}
		return cbbProjectType;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
