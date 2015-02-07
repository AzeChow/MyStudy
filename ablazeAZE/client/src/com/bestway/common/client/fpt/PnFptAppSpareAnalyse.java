/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
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
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyesDetail;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.JPanelBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnFptAppSpareAnalyse extends JPanelBase {

	private FptManageAction fptManageAction = null; // 合同
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JTextField tfComplexCode = null;
	private JTextField tfName = null;
	private JTextField tfEName = null;
	private JButton btnComplexCode = null;
	private JButton btnOppositeEnterpriseName = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JButton btnSearch = null;
	private JButton btnPrint = null;
	private JButton btnExit = null;
	private JPanel jPanel = null;
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
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private ButtonGroup bg = new ButtonGroup(); // @jve:decl-index=0:
	private ButtonGroup bg1 = new ButtonGroup(); // @jve:decl-index=0:
	private JTableListModel tableModel = null;
	private JComboBox cbbEmsHead = null;
	private JTextField tfAppNo = null;
	private JButton btnAppNo = null;
	private JLabel jLabel21 = null;
	private JLabel jLabel31 = null;
	protected ManualDeclareAction manualDeclareAction = null;
	private ContractAction contractAction = null;
	private DzscAction dzscAction = null;

	/**
	 * This is the default constructor
	 */
	public PnFptAppSpareAnalyse() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initUIComponents();
	}

	public void setVisible(boolean isFlag) {
		// if (isFlag == true) {
		// if (fptAppHead != null) {
		// String parentId = fptAppHead.getId();
		// List list = transferFactoryManageAction.findTempFptAppItemByParentId(
		// new Request(CommonVars.getCurrUser()), fptAppHead);
		// }
		// }
		super.setVisible(isFlag);
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
						list.add(addColumn("申请表编号", "appNo", 80));
						list.add(addColumn("手册/帐册号", "emsNo", 100));

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
						list.add(addColumn("申请表编号", "appNo", 80));
						list.add(addColumn("手册/帐册号", "emsNo", 100));

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
	 * showData
	 * 
	 * @return
	 */
	public void showData(TempFptAppParam param) {
		if (FptInOutFlag.OUT.equals(param.getInOutFlag())) {
			rbOut.setSelected(true);
		} else {
			rbIn.setSelected(true);
		}
		if (DeclareState.PROCESS_EXE.equals(param.getDeclareState())) {
			rbYes.setSelected(true);
		} else {
			rbAll.setSelected(true);
		}
		if (param.getProjectType() != null) {
			cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(param
					.getProjectType().toString(), cbbProjectType));
		} else {
			cbbProjectType.setSelectedItem(null);
		}
		if (param.getTradeName() != null
				&& !"".equals(param.getTradeName().trim())) {
			this.tfEName.setText(param.getTradeName().trim());
		}

		this.cbbBeginDate.setDate(param.getBeginDate());
		this.cbbEndDate.setDate(param.getEndDate());

		if (param.getComplex() != null && !"".equals(param.getComplex().trim())) {
			this.tfComplexCode.setText(param.getComplex().trim());
		}

		if (param.getName() != null && !"".equals(param.getName().trim())) {
			this.tfName.setText(param.getName().trim());
		}

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
		if (!this.tfAppNo.getText().trim().equals("")) {
			param.setAppNo(this.tfAppNo.getText());
		}

		if (this.cbbEmsHead.getSelectedItem() != null) {
			String emsNo = ((String) this.cbbEmsHead.getSelectedItem());
			param.setEmsNo(emsNo);
		}
		return param;
	}

	public void search() {
		new SearchThread().start();
	}

	class SearchThread extends Thread {
		public void run() {
			List<TempFptAppSpareAnalyesDetail> list = new ArrayList<TempFptAppSpareAnalyesDetail>();
			try {
				btnSearch.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				//
				// 获得查询参数对象
				//
				TempFptAppParam param = getTempFptAppParam();

				list = fptManageAction.findTempFptAppSpareAnalyesDetail(
						new Request(CommonVars.getCurrUser()), param);
				// if (list.isEmpty()) {
				// JOptionPane.showMessageDialog(FmFptAppSpareAnalyse.this,
				// "查到0笔数据", "提示", JOptionPane.INFORMATION_MESSAGE);
				// }
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						getComponent(PnFptAppSpareAnalyse.this), "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				btnSearch.setEnabled(true);
				initTable(list);
			}
		}
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
			/*String declareState="1";//报表中判定是正在执行,还是全部 1正在执行  0全部
			if(rbYes.isSelected()){
				declareState="1";
			}else{
				declareState="0";
			}*/
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("beginDate", cbbBeginDate.getDate() == null ? ""
					: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbBeginDate
							.getDate()));
			parameters.put("endDate", cbbEndDate.getDate() == null ? ""
					: (new SimpleDateFormat("yyyy-MM-dd")).format(cbbEndDate
							.getDate()));

			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = FmFptAppSpareAnalyse.class
					.getResourceAsStream("report/TempFptAppSpareAnalyesDetail.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(745, 386);
		this.add(getJSplitPane(), BorderLayout.CENTER);
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
			jSplitPane.setDividerSize(3);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(180, 90, 75, 22));
			jLabel31.setText("手册/帐册号");
			jLabel31.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(409, 90, 55, 22));
			jLabel21.setText("申请表号");
			jLabel21.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(203, 16, 54, 22));
			jLabel51.setText("\u9879\u76ee\u7c7b\u578b");
			jLabel51.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(516, 16, 17, 22));
			jLabel1.setText("\u5230");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(365, 16, 66, 22));
			jLabel.setText("\u7533\u62a5\u65f6\u95f4\u4ece");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(203, 41, 54, 22));
			jLabel4.setText("\u5546\u54c1\u7f16\u7801");
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(179, 66, 78, 22));
			jLabel3.setText("客户/供应商");
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(375, 41, 56, 22));
			jLabel2.setText("\u5546\u54c1\u540d\u79f0");
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8fc7\u6ee4\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
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
			jPanel1.add(getCbbEmsHead(), null);
			jPanel1.add(getTfAppNo(), null);
			jPanel1.add(getBtnAppNo(), null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(jLabel31, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(new java.awt.Rectangle(258, 41, 86, 22));
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
			tfName.setBounds(new java.awt.Rectangle(431, 41, 190, 22));
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
			tfEName.setBounds(new java.awt.Rectangle(258, 66, 341, 22));
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
			btnComplexCode.setBounds(new java.awt.Rectangle(343, 41, 20, 22));
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
			btnOppositeEnterpriseName.setBounds(new java.awt.Rectangle(598, 66,
					22, 22));
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
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(637, 16, 62, 22));
			btnSearch.setText("\u67e5\u8be2");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
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
			btnPrint.setBounds(new Rectangle(637, 41, 62, 22));
			btnPrint.setText("\u6253\u5370");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
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
					close();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 关闭窗体
	 * 
	 */
	protected void close() {
		Component component = getComponent(this);
		if (component instanceof JInternalFrame) {
			JInternalFrame frame = (JInternalFrame) component;
			if (frame != null) {
				frame.doDefaultCloseAction();
			}
		} else if (component instanceof JDialog) {
			JDialog frame = (JDialog) component;
			if (frame != null) {
				frame.dispose();
			}
		}
	}

	private Component getComponent(Component component) {
		if (component instanceof JInternalFrame || component instanceof JDialog) {
			return component;
		}
		if (component == null) {
			return null;
		}
		return getComponent(component.getParent());
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
			jPanel.setBounds(new Rectangle(18, 13, 151, 47));
			jPanel.setOpaque(false);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u7533\u8bf7\u8868\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel.add(getRbOut(), null);
			jPanel.add(getRbIn(), null);
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
			rbOut.setBounds(new Rectangle(12, 20, 54, 17));
			rbOut.setText("\u8f6c\u51fa");
			rbOut.setSelected(true);
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
			rbIn.setBounds(new Rectangle(73, 19, 54, 17));
			rbIn.setText("\u8f6c\u5165");
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
			jPanel2.setBounds(new Rectangle(18, 65, 151, 46));
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
			rbYes.setBounds(new Rectangle(8, 21, 80, 17));
			rbYes.setText("\u6b63\u5728\u6267\u884c");
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
			rbAll.setBounds(new Rectangle(90, 20, 55, 17));
			rbAll.setText("\u5168\u90e8");
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
	 * This method initializes cbbEmsHead
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsHead() {
		if (cbbEmsHead == null) {
			cbbEmsHead = new JComboBox();
			cbbEmsHead.setBounds(new Rectangle(257, 90, 146, 22));
		}
		return cbbEmsHead;
	}

	/**
	 * This method initializes tfAppNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(463, 90, 137, 22));
		}
		return tfAppNo;
	}

	/**
	 * This method initializes btnAppNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAppNo() {
		if (btnAppNo == null) {
			btnAppNo = new JButton();
			btnAppNo.setBounds(new Rectangle(598, 90, 22, 22));
			btnAppNo.setText("...");
			btnAppNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String declareState = null;
					if (rbYes.isSelected()) {
						declareState = DeclareState.PROCESS_EXE;
					}
					String inOutFlag = FptInOutFlag.IN;
					if (rbOut.isSelected()) {
						inOutFlag = FptInOutFlag.OUT;
					}
					FptAppHead newHead = FptQuery.getInstance()
							.findFptAppHeadByInOutFlag(inOutFlag, declareState,null);
					if (newHead != null) {
						tfAppNo.setText(newHead.getAppNo());
					}
				}
			});
		}
		return btnAppNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
