package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyes;
import com.bestway.common.fpt.entity.TempFptApplySurplus;
import com.bestway.customs.common.entity.BaseEmsExg;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.BaseEmsImg;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmFptApplyDifference extends FmCommon {

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JTextField tfComplexCode = null;

	private JTextField tfName = null;

	private JButton btnComplexCode = null;

	private JTableListModel tableModel = null;

	private JSplitPane jSplitPane = null;

	private JButton btnExit = null;

	private ButtonGroup bg = new ButtonGroup();
	private ButtonGroup bg1 = new ButtonGroup();
	private JRadioButton rbOut = null;

	private JRadioButton rbIn = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel51 = null;

	private JComboBox cbbProjectType = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbAll = null;

	protected ManualDeclareAction manualDeclareAction = null;
	private ContractAction contractAction = null;
	private DzscAction dzscAction = null;
	private JLabel label_3;
	private JRadioButton rbEmsYes;
	private JRadioButton rbEmsAll;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox cbbEmsHead;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptApplyDifference() {
		super();
		super.fptManageAction.checkCustomsEnvelopSpareAnalyse(new Request(
				CommonVars.getCurrUser()));
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		initUIComponents();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("深加工结转手册余量分析");
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
						// browse();
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
			jLabel51.setBounds(new Rectangle(214, 16, 54, 22));
			jLabel51.setText("\u9879\u76ee\u7c7b\u578b");
			jLabel51.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(551, 16, 17, 22));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(400, 16, 66, 22));
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setText("收退时间从");
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8fc7\u6ee4\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jLabel2.setBounds(409, 68, 56, 22);
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("商品名称");
			jLabel4.setBounds(214, 67, 54, 22);
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4.setText("商品编码");
			jPanel1.add(getTfComplexCode(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(getBtnComplexCode(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getBtnSearch(), null);
			// jPanel1.add(getBtnPrint(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel51, null);
			jPanel1.add(getCbbProjectType(), null);

			JLabel label = new JLabel();
			label.setText("手册/帐册号");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setBounds(new Rectangle(180, 90, 75, 22));
			label.setBounds(214, 43, 75, 22);
			jPanel1.add(label);

			cbbEmsHead = new JComboBox();
			cbbEmsHead.setBounds(new Rectangle(257, 90, 146, 22));
			cbbEmsHead.setBounds(292, 43, 364, 22);
			jPanel1.add(cbbEmsHead);
			jPanel1.add(getRbOut());
			jPanel1.add(getRbIn());

			JLabel label_1 = new JLabel();
			label_1.setBounds(10, 40, 73, 22);
			jPanel1.add(label_1);
			label_1.setText("申请表类型");
			label_1.setForeground(Color.BLUE);

			rbAll = new JRadioButton();
			rbAll.setBounds(151, 68, 55, 17);
			jPanel1.add(rbAll);
			rbAll.setText("全部");

			rbYes = new JRadioButton();
			rbYes.setBounds(74, 68, 80, 17);
			jPanel1.add(rbYes);
			rbYes.setText("正在执行");
			rbYes.setSelected(true);

			JLabel label_2 = new JLabel();
			label_2.setText("申请表状态");
			label_2.setForeground(Color.BLUE);
			label_2.setBounds(new Rectangle(203, 16, 54, 22));
			label_2.setBounds(10, 65, 62, 22);
			jPanel1.add(label_2);
			jPanel1.add(getLabel_3());
			jPanel1.add(getRbEmsYes());
			jPanel1.add(getRbEmsAll());
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
			btnSearch.setBounds(new Rectangle(662, 16, 62, 22));
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
			tfComplexCode.setBounds(292, 68, 86, 22);
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
			tfName.setBounds(465, 68, 190, 22);
		}
		return tfName;
	}

	/**
	 * This method initializes btnComplexCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplexCode() {
		if (btnComplexCode == null) {
			btnComplexCode = new JButton();
			btnComplexCode.setBounds(377, 68, 20, 22);
			btnComplexCode.setText("...");
			btnComplexCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Object obj =
							// CommonQuery.getInstance().getComplex();
							// if (obj != null) {
							// tfComplexCode
							// .setText(((Complex) obj).getCode());
							// }

							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(new JTableListColumn("编码", "complex.code",
									100));
							list.add(new JTableListColumn("序号", "seqNum", 150));
							list.add(new JTableListColumn("名称", "name", 100));
							list.add(new JTableListColumn("规格", "spec", 100));
							list.add(new JTableListColumn("单位", "unit.name",
									100));
							DgCommonQueryPage.setTableColumns(list);
							// DgCommonQueryPage.setLength(200);
							DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
								@Override
								public List getDataSource(int index,
										int length, String property,
										Object value, boolean isLike) {

									ItemProperty item = (ItemProperty) cbbProjectType
											.getSelectedItem();
									int projectType = Integer.parseInt(item
											.getCode());
									String emsNo = ((String) cbbEmsHead
											.getSelectedItem());

									return fptManageAction
											.findComplexByContract(new Request(
													CommonVars.getCurrUser(),
													true), index, length,
													projectType, emsNo, rbIn
															.isSelected(),
													property, value, isLike,
													rbEmsAll.isSelected());
								}
							};

							dgCommonQuery.setTitle("商品查询");
							DgCommonQueryPage.setSingleResult(true);
							dgCommonQuery.setVisible(true);
							if (dgCommonQuery.isOk()) {
								if (dgCommonQuery.getReturnValue() instanceof BaseEmsImg) {
									tfComplexCode
											.setText(((BaseEmsImg) dgCommonQuery
													.getReturnValue())
													.getComplex().getCode());
								} else {
									tfComplexCode
											.setText(((BaseEmsExg) dgCommonQuery
													.getReturnValue())
													.getComplex().getCode());
								}
							}
						}
					});

		}
		return btnComplexCode;
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

	private void initUICbb() {
		if (cbbProjectType.getSelectedItem() == null) {
			return;
		}
		// 电子帐册
		cbbEmsHead.removeAllItems();
		// cbbEmsHead.setForeground(Color.red);
		ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
		int projectType = Integer.parseInt(item.getCode());
		if (projectType == ProjectType.BCUS) {

			List contracts = manualDeclareAction
					.findEmsHeadH2kInExecuting(new Request(CommonVars
							.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
							.getEmsNo());
				}

			}
		} else if (projectType == ProjectType.DZSC) {

			List contracts = dzscAction.findExeEmsPorHead(new Request(
					CommonVars.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
							.getEmsNo());
				}

			}
		} else if (projectType == ProjectType.BCS) {

			List contracts = contractAction
					.findContractByProcessExe(new Request(CommonVars
							.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
							.getEmsNo());
				}

			}

		}
		cbbEmsHead.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (isSelected) {
					setForeground(Color.RED);
					setBackground(list.getSelectionBackground());
				} else {
					setForeground(list.getForeground());
					setBackground(list.getBackground());
				}
				String s = "";
				if (value != null) {
					this.setText(value.toString());
				}
				return this;
			}
		});
		this.cbbEmsHead.setSelectedIndex(-1);
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

		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			param.setProjectType(projectType);
		} else {
			param.setProjectType(null);
		}

		if (rbYes.isSelected()) {
			param.setDeclareState(DeclareState.PROCESS_EXE);
		} else {
			param.setDeclareState(null);
		}
		if (rbEmsYes.isSelected()) {
			param.setEmsDeclareState(DeclareState.PROCESS_EXE);
		} else {
			param.setEmsDeclareState(null);
		}
		param.setBeginDate(this.cbbBeginDate.getDate());
		param.setEndDate(this.cbbEndDate.getDate());

		if (!this.tfComplexCode.getText().trim().equals("")) {
			param.setComplex(this.tfComplexCode.getText());
		}

		if (!this.tfName.getText().trim().equals("")) {
			param.setName(this.tfName.getText());
		}

		if (this.cbbEmsHead.getSelectedItem() != null) {
			String emsNo = ((String) this.cbbEmsHead.getSelectedItem());
			param.setEmsNo(emsNo);
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

			parameters.put(
					"beginDate",
					cbbBeginDate.getDate() == null ? ""
							: (new SimpleDateFormat("yyyy-MM-dd"))
									.format(cbbBeginDate.getDate()));
			parameters.put("endDate",
					cbbEndDate.getDate() == null ? "" : (new SimpleDateFormat(
							"yyyy-MM-dd")).format(cbbEndDate.getDate()));

			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = FmFptApplyDifference.class
					.getResourceAsStream("report/TempFptAppSpareAnalyes.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @author HYQ 深加工结转手册余量分析
	 */
	class SearchThread extends Thread {
		public void run() {
			List<TempFptApplySurplus> list = new ArrayList<TempFptApplySurplus>();
			try {
				btnSearch.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				//
				// 获得查询参数对象
				//
				TempFptAppParam param = getTempFptAppParam();

				list = fptManageAction.findTempFptApplyDifference(new Request(
						CommonVars.getCurrUser()), param);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFptApplyDifference.this,
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
						list.add(addColumn("转入转出标记", "inOutFlag", 40));// 1
						list.add(addColumn("手册有效期", "contractEndDate", 70));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("手册序号", "trNo", 60));
						list.add(addColumn("记录号", "credenceNo", 60));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 200));
						list.add(addColumn("计量单位", "unitName", 50));
						list.add(addColumn("1.合同定量", "contractQty", 60));
						list.add(addColumn("2.申请表数量", "fptHeadQty", 100));
						list.add(addColumn("3.成品发货数量", "sendReceiveQty", 100));
						list.add(addColumn("4.成品退货数量", "returnQty", 100));
						list.add(addColumn("5.报关单转厂数量",
								"declarationTransferFactoryQty", 120));
						list.add(addColumn("6.(2-3+4)可发货数量", "remainQty", 150));
						list.add(addColumn("7.(3-4-5)未转厂量", "fptDifferenceQty",
								150));
						return list;
					}
				});

		jTable.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
						list.add(addColumn("转入转出标记", "inOutFlag", 40));// 1
						list.add(addColumn("手册有效期", "contractEndDate", 70));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("手册序号", "trNo", 60));
						list.add(addColumn("记录号", "credenceNo", 60));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 200));
						list.add(addColumn("计量单位", "unitName", 50));
						list.add(addColumn("1.合同定量", "contractQty", 60));
						list.add(addColumn("2.申请表数量", "fptHeadQty", 100));
						list.add(addColumn("3.料件发货数量", "sendReceiveQty", 100));
						list.add(addColumn("4.料件退货数量", "returnQty", 100));
						list.add(addColumn("5.报关单转厂数量",
								"declarationTransferFactoryQty", 120));
						list.add(addColumn("6.(2-3+4)可发货数量", "remainQty", 150));
						list.add(addColumn("7.(3-4-5)未转厂量", "fptDifferenceQty",
								150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
			jSplitPane.setDividerLocation(130);
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
			btnExit.setBounds(new Rectangle(662, 43, 62, 22));
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
	 * This method initializes rbOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOut() {
		if (rbOut == null) {
			rbOut = new JRadioButton();
			rbOut.setBounds(74, 43, 54, 17);
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
			rbIn.setBounds(152, 43, 54, 17);
			rbIn.setText("转入");

		}
		return rbIn;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(466, 16, 85, 22));
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
			cbbEndDate.setBounds(new Rectangle(570, 16, 85, 22));
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
			cbbProjectType.setBounds(new Rectangle(292, 16, 106, 22));
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						initUICbb();
					}
				}
			});
		}
		return cbbProjectType;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("手册状态");
			label_3.setForeground(Color.BLUE);
			label_3.setBounds(new Rectangle(203, 16, 54, 22));
			label_3.setBounds(10, 16, 54, 22);
		}
		return label_3;
	}

	private JRadioButton getRbEmsYes() {
		if (rbEmsYes == null) {
			rbEmsYes = new JRadioButton();
			buttonGroup.add(rbEmsYes);
			rbEmsYes.setText("正在执行");
			rbEmsYes.setSelected(true);
			rbEmsYes.setBounds(74, 21, 74, 17);
		}
		return rbEmsYes;
	}

	private JRadioButton getRbEmsAll() {
		if (rbEmsAll == null) {
			rbEmsAll = new JRadioButton();
			buttonGroup.add(rbEmsAll);
			rbEmsAll.setText("全部");
			rbEmsAll.setBounds(152, 21, 55, 17);
		}
		return rbEmsAll;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
