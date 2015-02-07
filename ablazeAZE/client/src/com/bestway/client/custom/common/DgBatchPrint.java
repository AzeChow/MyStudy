package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ConvertNumberToUpperCase;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.client.custom.common.report.DgImportInvoiceMergerReportSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.editor.CheckBoxEditor;
import com.bestway.ui.render.CheckBoxRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class DgBatchPrint extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JFooterScrollPane srcPn = null;

	private JTable tblShow = null;

	private JTableListModel showTableModel = null; // @jve:decl-index=0:

	private List data = null; // @jve:decl-index=0:

	private JPanel pnTop = null;

	private JPanel pnCondition = null;

	private JPanel pnQuery = null;

	private JTextField tfQueryValue = null;

	private JLabel lbDeclarationDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel lbTo = null;

	private JLabel lbPrintType = null;

	private JComboBox cbbPrintType = null;

	private JCheckBox jCheckBox = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private Integer impExpFlag = ImpExpFlag.IMPORT;
	/**
	 * 报关单的基础类接口
	 */
	protected EncAction encAction = null;
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private Integer projectType;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel impExpTypeLabel;
	private JComboBox cbbPrintStyle;
	private JComboBox cbbType;
	private DzscMessageAction dzscMessageAction = null;
	private String emsNo;
	/**
	 * 电子帐册操作接口
	 */
	private ManualDeclareAction manualDecleareAction = null;

	private ContractCavAction contractCavAction = null;

	/**
	 * This is the default constructor
	 */
	public DgBatchPrint() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		initialize();
	}

	public DgBatchPrint(Integer impExpFlag, Integer projectType, String emsNo) {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		this.impExpFlag = impExpFlag;
		this.projectType = projectType;
		this.emsNo = emsNo;
		initialize();
	}

	public DgBatchPrint(Integer impExpFlag, Integer projectType) {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		this.impExpFlag = impExpFlag;
		this.projectType = projectType;
		initialize();
		// this(impExpFlag,projectType,null);
	}

	private void initUI() {

		// 初始化打印类型
		cbbPrintType.removeAllItems();
		cbbPrintType.addItem(new ItemProperty("1", "套打"));
		cbbPrintType.addItem(new ItemProperty("0", "非套打"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbPrintType);
		cbbPrintType.setRenderer(CustomBaseRender.getInstance().getRender());
		cbbPrintType.setSelectedIndex(0);

		// 初始化打印类别
		cbbPrintStyle.removeAllItems();
		cbbPrintStyle.addItem(new ItemProperty("0", "报关单"));
		cbbPrintStyle.addItem(new ItemProperty("1", "司机纸"));
		if (impExpFlag != ImpExpFlag.IMPORT && impExpFlag == ImpExpFlag.EXPORT) {
			cbbPrintStyle.addItem(new ItemProperty("2", "发票"));
		}
		if (impExpFlag != ImpExpFlag.IMPORT && impExpFlag != ImpExpFlag.EXPORT) {
			cbbPrintStyle.addItem(new ItemProperty("2", "进口发票"));
			cbbPrintStyle.addItem(new ItemProperty("3", "出口加工发票"));
		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbPrintStyle);
		cbbPrintStyle.setRenderer(CustomBaseRender.getInstance().getRender());
		cbbPrintStyle.setSelectedIndex(0);
		if (impExpFlag.equals(ImpExpFlag.SPECIAL)) {
			// 初始化进出口类型
			cbbType.removeAllItems();
			cbbType.addItem(new ItemProperty("0", "进口报关单"));
			cbbType.addItem(new ItemProperty("1", "出口报关单"));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbType);
			this.cbbType
					.setRenderer(CustomBaseRender.getInstance().getRender());
			cbbType.setSelectedIndex(0);
		}
	}

	public void setVisible(boolean b) {
		if (b) {
			initUI();
			initTable(null);
			setData(getDataSource());
		}
		super.setVisible(b);
	}

	private void initialize() {
		this.setTitle("批量打印");
		this.setSize(800, 537);
		this.setMinimumSize(new Dimension(800, 537));
		this.setContentPane(getJContentPane());
	}

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getSrcPn(), BorderLayout.CENTER);
			jContentPane.add(getPnTop(), BorderLayout.NORTH);
		}

		return jContentPane;
	}

	private JFooterScrollPane getSrcPn() {
		if (srcPn == null) {
			srcPn = new JFooterScrollPane();
			srcPn.setViewportView(getTblShow());
		}
		return srcPn;
	}

	private JTable getTblShow() {
		if (tblShow == null) {
			tblShow = new GroupableHeaderTable();
		}
		return tblShow;
	}

	public void reflash() {
		this.showTableModel.setList(data);
	}

	/**
	 * 初始化报关单商品信息
	 */
	private void initTable(List<BaseCustomsDeclaration> list) {
		if (showTableModel == null) {
			JTableListModelAdapter adapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("流水号", "serialNumber", 50));
					list.add(addColumn("报关单号", "customsDeclarationCode", 150));
					list.add(addColumn("选项", "isSelected", 80));
					list.add(addColumn("是否检查", "isCheck", 60));
					list.add(addColumn("是否生效", "effective", 60));
					list.add(addColumn("运输工具", "conveyance", 80));
					return list;
				}
			};
			adapter.setEditableColumn(3);
			showTableModel = new JTableListModel(tblShow, list, adapter);

			tblShow.getColumnModel().getColumn(3)
					.setCellRenderer(new CheckBoxRender());
			tblShow.getColumnModel().getColumn(3)
					.setCellEditor(new CheckBoxEditor(new JCheckBox()) {
						private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent e) {
							JTableListModel tableModel = (JTableListModel) this.table
									.getModel();
							System.out.println("-----");
							Object obj = tableModel.getCurrentRow();
							if (obj instanceof BaseCustomsDeclaration) {
								BaseCustomsDeclaration temp = (BaseCustomsDeclaration) obj;
								temp.setIsSelected(new Boolean(cb.isSelected()));
								tableModel.updateRow(obj);
							}
							fireEditingStopped();
						}
					});
			TableCellRenderer boolTcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
					if (value == null || "false".equals(value.toString())) {
						this.setText("否");
					} else {
						this.setText("是");
					}
					return this;
				}
			};

			tblShow.getColumnModel().getColumn(4).setCellRenderer(boolTcr);
			tblShow.getColumnModel().getColumn(5).setCellRenderer(boolTcr);

		} else {
			showTableModel.setList(list);
		}
	}

	public void setData(List data) {
		this.data = data;
		this.reflash();
	}

	private List<BaseCustomsDeclaration> getDataSource() {
		List<BaseCustomsDeclaration> list = null;
		if (impExpFlag.equals(ImpExpFlag.SPECIAL)) {
			String customsDecCode = tfQueryValue.getText();
			List impExpTypeList = isImportOrExport(((ItemProperty) (cbbType
					.getSelectedItem())).getCode());
			list = encAction.findCustomsDeclaration(
					new Request(CommonVars.getCurrUser()), impExpFlag,
					cbbBeginDate.getDate(), cbbEndDate.getDate(),
					customsDecCode, projectType, emsNo, impExpTypeList);
		} else {
			String customsDecCode = tfQueryValue.getText();
			list = encAction.findCustomsDeclaration(
					new Request(CommonVars.getCurrUser()), impExpFlag,
					cbbBeginDate.getDate(), cbbEndDate.getDate(),
					customsDecCode, projectType, emsNo);
		}
		List<BaseCustomsDeclaration> returnList = new ArrayList<BaseCustomsDeclaration>();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration customDec = list.get(i);
//			if (customDec.getConveyance() == null
//					|| "".equals(customDec.getConveyance().trim())
//					|| customDec.getConveyance().indexOf("@") != -1) {
				returnList.add(customDec);
//			}
		}
		return returnList;
	}

	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setPreferredSize(new Dimension(100, 80));
			pnTop.setLayout(new BorderLayout());
			pnTop.add(getPnCondition(), BorderLayout.CENTER);
			pnTop.add(getPnQuery(), BorderLayout.EAST);
		}
		return pnTop;
	}

	private JPanel getPnCondition() {
		if (pnCondition == null) {
			lbPrintType = new JLabel();
			lbPrintType.setBounds(new Rectangle(8, 42, 60, 27));
			lbPrintType.setText("打印类型");
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(366, 42, 12, 27));
			lbTo.setText("到");
			lbDeclarationDate = new JLabel();
			lbDeclarationDate.setBounds(new Rectangle(199, 42, 67, 27));
			lbDeclarationDate.setText("申报日期 从");
			pnCondition = new JPanel();
			pnCondition.setLayout(null);
			pnCondition.add(getTfQueryValue(), null);
			pnCondition.add(lbDeclarationDate, null);
			pnCondition.add(getCbbBeginDate(), null);
			pnCondition.add(getCbbEndDate(), null);
			pnCondition.add(lbTo, null);
			pnCondition.add(lbPrintType, null);
			pnCondition.add(getCbbPrintType(), null);
			pnCondition.add(getJCheckBox(), null);
			pnCondition.add(getLblNewLabel());
			pnCondition.add(getLabel());
			pnCondition.add(getCbbPrintStyle());
			// DgBatchPrint(impExpFlag,projectType);
			if (impExpFlag.equals(ImpExpFlag.SPECIAL)) {
				pnCondition.add(getImpExpTypeLabel());
				pnCondition.add(getCbbType());
			}
		}
		return pnCondition;
	}

	private JPanel getPnQuery() {
		if (pnQuery == null) {
			pnQuery = new JPanel();
			pnQuery.setPreferredSize(new Dimension(120, 20));
			pnQuery.setLayout(null);
			pnQuery.add(getBtnQuery(), null);
			pnQuery.add(getBtnPrint(), null);
		}
		return pnQuery;
	}

	private JTextField getTfQueryValue() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setBounds(new Rectangle(266, 8, 212, 27));
		}
		return tfQueryValue;
	}

	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setDate(CommonVars.getBeginDate());
			cbbBeginDate.setBounds(266, 42, 90, 27);
		}
		return cbbBeginDate;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(388, 42, 90, 27);
		}
		return cbbEndDate;
	}

	private JComboBox getCbbPrintType() {
		if (cbbPrintType == null) {
			cbbPrintType = new JComboBox();
			cbbPrintType.setBounds(new Rectangle(61, 42, 116, 27));
		}
		return cbbPrintType;
	}

	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(529, 42, 135, 27));
			jCheckBox.setText("全选报关单");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (Boolean.TRUE.equals(jCheckBox.isSelected())) {
						jCheckBox.setText("取消全选报关单");
					} else {
						jCheckBox.setText("全选报关单");
					}

					List<BaseCustomsDeclaration> list = showTableModel
							.getList();
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setIsSelected(jCheckBox.isSelected());
					}
					showTableModel.updateRows(list);
				}
			});
		}
		return jCheckBox;
	}

	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(12, 8, 80, 27));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setData(getDataSource());
				}
			});
		}
		return btnQuery;
	}

	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(12, 42, 80, 27));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doPrint();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * 获取集装箱号（打印使用）
	 * 
	 * @return
	 */
	protected String getContia(BaseCustomsDeclaration customsDeclaration) {
		String contain = "";
		List list = encAction.findContainerByCustomsDeclaration(new Request(
				CommonVars.getCurrUser()), customsDeclaration);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String xs = ((BaseCustomsDeclarationContainer) list.get(i))
						.getContainerCode();
				if (null != customsDeclaration.getContainerNum()) {
					if (customsDeclaration.getContainerNum().length() > 10
							&& !xs.equals(customsDeclaration.getContainerNum()
									.substring(0, 11))) {
						contain = contain + xs + " ";
					}
				}
			}
		}
		if (!contain.equals("")) {
			contain = "集装箱:" + contain;
		}
		return contain;
	}

	private String cutString(String str) {
		for (int i = str.length(); i > 0; i--) {
			if (str.substring(i - 1, i).equals("0")) {
				str = str.substring(0, i - 1);
			} else if (str.substring(i - 1, i).equals(".")) {
				str = str.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return str;
	}

	// 得到要打印的报关单
	private List getCusDecs() {
		List res = new ArrayList();
		List<BaseCustomsDeclaration> list = showTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).getIsSelected()) {
				res.add(list.get(i));
			}
		}
		return res;

	}

	public void doPrint() {

		// 准备要导出的报关单数据
		List<BaseCustomsDeclaration> list = getCusDecs();
		if (list.size() <= 0) {
			JOptionPane.showMessageDialog(DgBatchPrint.this, "请先选择要打印的报关单!");
			return;
		}

		String type = ((ItemProperty) (cbbPrintStyle.getSelectedItem()))
				.getCode();

		batchPrintDriversPaper(list, getPrintType());
	}

	/**
	 * 获取打印类型 非套打为true
	 * 
	 * @return
	 */
	private boolean getPrintType() {
		String type = ((ItemProperty) (cbbPrintType.getSelectedItem()))
				.getCode();
		if ("0".equals(type)) {
			return true;
		}
		return false;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * 进口报关单
	 */
	public JasperPrint printImpCustoms(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		// 非套打报关单
		CustomReportDataSource ds = new CustomReportDataSource(listBase);
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
				.get(0);
		String attachedBillName = customsDeclaration.getAttachedBill();

		DzscParameterSet dzscParameterSet = dzscMessageAction
				.findDzscMessageDirSet1(new Request(CommonVars.getCurrUser(),
						true));
		if (noTaoda) {
			try {
				List contianList = encAction.findContainerByCustomsDeclaration(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
				if (contianList.size() != 0) {
					if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
							"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						CustomsDeclarationSubReportDataSource
								.setContainerData(contianList);
					} else {
						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());
					}
				} else {
					CustomsDeclarationSubReportDataSource
							.setContainerData(new ArrayList());
				}

				CustomsDeclarationSubReportDataSource
						.setSubReportData(listInfo);
				InputStream masterReportStream = null;
				if (customsDeclaration.getEmsHeadH2k() != null
						&& !"".equals(customsDeclaration.getEmsHeadH2k())
						&& customsDeclaration.getEmsHeadH2k().substring(0, 1)
								.equals("H")) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCustomsHEmsDeclarationReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");
				}
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");
				JasperReport containerReport = (JasperReport) JRLoader
						.loadObject(containerReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contia", getContia(customsDeclaration));
				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("containerReport", containerReport);
				parameters.put("overprint", new Boolean(false));
				Company company = (Company) customsDeclaration.getCompany();
				parameters.put("linkMan", company.getLinkman());
				parameters.put("linkTel", company.getTel());
				parameters.put("tradeFlat", company.getCounFlag());
				parameters.put("coFlat", company.getOwnercounFlag());
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印航次
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");
				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);
				if (isPrintCustomWithVersion == null)
					isPrintCustomWithVersion = "0";
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位
				parameters.put("attachedBillName", attachedBillName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {// 套打报关单
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(listInfo);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contia", getContia(customsDeclaration));
				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("overprint", new Boolean(true));
				Company company = (Company) customsDeclaration.getCompany();
				parameters.put("linkMan", company.getLinkman());
				parameters.put("linkTel", company.getTel());
				parameters.put("tradeFlat", company.getCounFlag());
				parameters.put("coFlat", company.getOwnercounFlag());
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印航次
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");
				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);
				if (isPrintCustomWithVersion == null)
					isPrintCustomWithVersion = "0";
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位
				parameters.put("attachedBillName", attachedBillName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 出口报关单
	 */
	public JasperPrint printExpCustoms(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		CustomReportDataSource ds = new CustomReportDataSource(listBase);
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
				.get(0);
		CustomsDeclarationSubReportDataSource.setSubReportData(listInfo);
		String attachedBillName = customsDeclaration.getAttachedBill();
		DzscParameterSet dzscParameterSet = dzscMessageAction
				.findDzscMessageDirSet1(new Request(CommonVars.getCurrUser(),
						true));
		if (noTaoda) {
			try {
				List contianList = encAction.findContainerByCustomsDeclaration(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
				if (contianList.size() != 0) {
					if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
							"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						CustomsDeclarationSubReportDataSource
								.setContainerData(contianList);
					} else {
						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());
					}
				} else {
					CustomsDeclarationSubReportDataSource
							.setContainerData(new ArrayList());
				}
				CustomsDeclarationSubReportDataSource
						.setSubReportData(listInfo);
				InputStream masterReportStream = null;
				if (customsDeclaration.getEmsHeadH2k() != null
						&& !"".equals(customsDeclaration.getEmsHeadH2k())
						&& customsDeclaration.getEmsHeadH2k().substring(0, 1)
								.equals("H")) {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsHEmsDeclarationReport.jasper");
				} else {
					masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");
				}
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");
				JasperReport containerReport = (JasperReport) JRLoader
						.loadObject(containerReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contia", getContia(customsDeclaration));
				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("containerReport", containerReport);
				parameters.put("overprint", new Boolean(false));
				Company company = (Company) customsDeclaration.getCompany();
				parameters.put("linkMan", company.getLinkman());
				parameters.put("linkTel", company.getTel());
				parameters.put("tradeFlat", company.getCounFlag());
				parameters.put("coFlat", company.getOwnercounFlag());
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印航次
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");

				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");
				String isPrintCustomWithVersion = manualDecleareAction
						.getBpara(new Request(CommonVars.getCurrUser()),
								BcusParameter.PrintCustomWithVersion);
				if (isPrintCustomWithVersion == null)
					isPrintCustomWithVersion = "0";
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位
				parameters.put("attachedBillName", attachedBillName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				CustomsDeclarationSubReportDataSource
						.setSubReportData(listInfo);
				InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");
				InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
						.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");
				JasperReport commInfoReport = (JasperReport) JRLoader
						.loadObject(commInfoReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("contia", getContia(customsDeclaration));
				parameters.put("commInfoReport", commInfoReport);
				parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
				parameters.put("overprint", new Boolean(true));
				Company company = (Company) customsDeclaration.getCompany();
				parameters.put("linkMan", company.getLinkman());
				parameters.put("linkTel", company.getTel());
				parameters.put("tradeFlat", company.getCounFlag());
				parameters.put("coFlat", company.getOwnercounFlag());
				parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
						.toString());// 是否打印航次
				parameters.put("isPrintToolCode", dzscParameterSet
						.getIsPrintToolCode().toString());// 是否打印航次
				// 设置毛净重
				DecimalFormat f = new DecimalFormat();
				f.setMaximumFractionDigits(3);
				f.setGroupingSize(0);
				parameters
						.put("grossWeight",
								customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
										.getGrossWeight().toString()) : "0");
				parameters
						.put("netWeight",
								customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
										.getNetWeight().toString()) : "0");
				String isPrintCustomWithVersion = "0";
				parameters.put("isPrintCustomWithVersion",
						isPrintCustomWithVersion);// 用来控制是否显示版本栏位
				parameters.put("attachedBillName", attachedBillName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 特殊报关单
	 */
	public JasperPrint printSpecCustoms(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		String type = ((ItemProperty) (cbbType.getSelectedItem())).getCode();
		if ("0".equals(type)) {
			CustomReportDataSource ds = new CustomReportDataSource(listBase);
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);
			String attachedBillName = customsDeclaration.getAttachedBill();

			DzscParameterSet dzscParameterSet = dzscMessageAction
					.findDzscMessageDirSet1(new Request(CommonVars
							.getCurrUser(), true));
			if (noTaoda) {
				try {
					List contianList = encAction
							.findContainerByCustomsDeclaration(new Request(
									CommonVars.getCurrUser()),
									customsDeclaration);
					if (contianList.size() != 0) {
						if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
								"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							CustomsDeclarationSubReportDataSource
									.setContainerData(contianList);
						} else {
							CustomsDeclarationSubReportDataSource
									.setContainerData(new ArrayList());
						}
					} else {
						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());
					}

					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream masterReportStream = null;
					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {
						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsHEmsDeclarationReport.jasper");
					} else {
						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");
					}
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");
					JasperReport containerReport = (JasperReport) JRLoader
							.loadObject(containerReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contia", getContia(customsDeclaration));
					parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
					parameters.put("commInfoReport", commInfoReport);
					parameters.put("containerReport", containerReport);
					parameters.put("overprint", new Boolean(false));
					Company company = (Company) customsDeclaration.getCompany();
					parameters.put("linkMan", company.getLinkman());
					parameters.put("linkTel", company.getTel());
					parameters.put("tradeFlat", company.getCounFlag());
					parameters.put("coFlat", company.getOwnercounFlag());
					parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
							.toString());// 是否打印航次
					parameters.put("isPrintToolCode", dzscParameterSet
							.getIsPrintToolCode().toString());// 是否打印航次
					parameters
							.put("grossWeight",
									customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
											.getGrossWeight().toString()) : "0");
					parameters
							.put("netWeight",
									customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
											.getNetWeight().toString()) : "0");
					String isPrintCustomWithVersion = manualDecleareAction
							.getBpara(new Request(CommonVars.getCurrUser()),
									BcusParameter.PrintCustomWithVersion);
					if (isPrintCustomWithVersion == null)
						isPrintCustomWithVersion = "0";
					parameters.put("isPrintCustomWithVersion",
							isPrintCustomWithVersion);// 用来控制是否显示版本栏位
					parameters.put("attachedBillName", attachedBillName);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReportStream, parameters, ds);
					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {// 套打报关单
				try {
					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCustomsDeclarationReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportCustomsDeclarationCommInfoReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contia", getContia(customsDeclaration));
					parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
					parameters.put("commInfoReport", commInfoReport);
					parameters.put("overprint", new Boolean(true));
					Company company = (Company) customsDeclaration.getCompany();
					parameters.put("linkMan", company.getLinkman());
					parameters.put("linkTel", company.getTel());
					parameters.put("tradeFlat", company.getCounFlag());
					parameters.put("coFlat", company.getOwnercounFlag());
					parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
							.toString());// 是否打印航次
					parameters.put("isPrintToolCode", dzscParameterSet
							.getIsPrintToolCode().toString());// 是否打印航次
					parameters
							.put("grossWeight",
									customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
											.getGrossWeight().toString()) : "0");
					parameters
							.put("netWeight",
									customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
											.getNetWeight().toString()) : "0");
					String isPrintCustomWithVersion = manualDecleareAction
							.getBpara(new Request(CommonVars.getCurrUser()),
									BcusParameter.PrintCustomWithVersion);
					if (isPrintCustomWithVersion == null)
						isPrintCustomWithVersion = "0";
					parameters.put("isPrintCustomWithVersion",
							isPrintCustomWithVersion);// 用来控制是否显示版本栏位
					parameters.put("attachedBillName", attachedBillName);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReportStream, parameters, ds);
					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		} else if ("1".equals(type)) {
			CustomReportDataSource ds = new CustomReportDataSource(listBase);
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);
			CustomsDeclarationSubReportDataSource.setSubReportData(listInfo);
			String attachedBillName = customsDeclaration.getAttachedBill();
			DzscParameterSet dzscParameterSet = dzscMessageAction
					.findDzscMessageDirSet1(new Request(CommonVars
							.getCurrUser(), true));
			if (noTaoda) {
				try {
					List contianList = encAction
							.findContainerByCustomsDeclaration(new Request(
									CommonVars.getCurrUser()),
									customsDeclaration);
					if (contianList.size() != 0) {
						if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
								"是否打印集装箱及托架?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							CustomsDeclarationSubReportDataSource
									.setContainerData(contianList);
						} else {
							CustomsDeclarationSubReportDataSource
									.setContainerData(new ArrayList());
						}
					} else {
						CustomsDeclarationSubReportDataSource
								.setContainerData(new ArrayList());
					}
					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream masterReportStream = null;
					if (customsDeclaration.getEmsHeadH2k() != null
							&& !"".equals(customsDeclaration.getEmsHeadH2k())
							&& customsDeclaration.getEmsHeadH2k()
									.substring(0, 1).equals("H")) {
						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsHEmsDeclarationReport.jasper");
					} else {
						masterReportStream = DgBaseImportCustomsDeclaration.class
								.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");
					}
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					InputStream containerReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/CustomsDeclarationContainerReport.jasper");
					JasperReport containerReport = (JasperReport) JRLoader
							.loadObject(containerReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contia", getContia(customsDeclaration));
					parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
					parameters.put("commInfoReport", commInfoReport);
					parameters.put("containerReport", containerReport);
					parameters.put("overprint", new Boolean(false));
					Company company = (Company) customsDeclaration.getCompany();
					parameters.put("linkMan", company.getLinkman());
					parameters.put("linkTel", company.getTel());
					parameters.put("tradeFlat", company.getCounFlag());
					parameters.put("coFlat", company.getOwnercounFlag());
					parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
							.toString());// 是否打印航次
					parameters.put("isPrintToolCode", dzscParameterSet
							.getIsPrintToolCode().toString());// 是否打印航次
					parameters
							.put("grossWeight",
									customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
											.getGrossWeight().toString()) : "0");

					parameters
							.put("netWeight",
									customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
											.getNetWeight().toString()) : "0");
					String isPrintCustomWithVersion = manualDecleareAction
							.getBpara(new Request(CommonVars.getCurrUser()),
									BcusParameter.PrintCustomWithVersion);
					if (isPrintCustomWithVersion == null)
						isPrintCustomWithVersion = "0";
					parameters.put("isPrintCustomWithVersion",
							isPrintCustomWithVersion);// 用来控制是否显示版本栏位
					parameters.put("attachedBillName", attachedBillName);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReportStream, parameters, ds);
					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream masterReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsDeclarationReport.jasper");
					InputStream commInfoReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ExportCustomsDeclarationCommInfoReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("contia", getContia(customsDeclaration));
					parameters.put("commInfoReport", commInfoReport);
					parameters.put("projectType", String.valueOf(projectType));// 只有是BCUS有需要才打印版本栏位
					parameters.put("overprint", new Boolean(true));
					Company company = (Company) customsDeclaration.getCompany();
					parameters.put("linkMan", company.getLinkman());
					parameters.put("linkTel", company.getTel());
					parameters.put("tradeFlat", company.getCounFlag());
					parameters.put("coFlat", company.getOwnercounFlag());
					parameters.put("isPrintNo", dzscParameterSet.getIsPrintNo()
							.toString());// 是否打印航次
					parameters.put("isPrintToolCode", dzscParameterSet
							.getIsPrintToolCode().toString());// 是否打印航次
					// 设置毛净重
					DecimalFormat f = new DecimalFormat();
					f.setMaximumFractionDigits(3);
					f.setGroupingSize(0);
					parameters
							.put("grossWeight",
									customsDeclaration.getGrossWeight() != null ? cutString(customsDeclaration
											.getGrossWeight().toString()) : "0");
					parameters
							.put("netWeight",
									customsDeclaration.getNetWeight() != null ? cutString(customsDeclaration
											.getNetWeight().toString()) : "0");
					String isPrintCustomWithVersion = "0";
					parameters.put("isPrintCustomWithVersion",
							isPrintCustomWithVersion);// 用来控制是否显示版本栏位
					parameters.put("attachedBillName", attachedBillName);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							masterReportStream, parameters, ds);
					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 出口发票
	 */
	public void printExpInvoice(List listBase, boolean noTaoda,
			List<BaseCustomsDeclarationCommInfo> listInfo) {
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
				.get(0);
		CustomsDeclarationSubReportDataSource.setSubReportData(listInfo);
		// 计算加工费
		Double process = 0d;// 加工费
		for (BaseCustomsDeclarationCommInfo info : listInfo) {
			DzscEmsExgBill exg = contractCavAction
					.getDzscEmsExgBillExgByContract((info
							.getBaseCustomsDeclaration().getEmsHeadH2k()), info
							.getCommSerialNo(), info
							.getBaseCustomsDeclaration().getAuthorizeFile());
			if (exg != null) {
				System.out.println(exg.getMachinPrice());
			}
			process = process
					+ CommonUtils.getDoubleExceptNull(info.getCommAmount())
					* CommonUtils.getDoubleExceptNull(((exg == null) ? 0
							: CommonUtils.getDoubleExceptNull(exg
									.getMachinPrice())));
		}
		System.out.println("加工费：" + process);
		//
		if (noTaoda) {
			if (customsDeclaration != null) {
				DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
				dgExportInvoiceItemReportSub
						.setCustomsDeclaration(customsDeclaration);
				dgExportInvoiceItemReportSub
						.setDeclarationCommInfoList(listInfo);
				dgExportInvoiceItemReportSub.setIsOverPrint(true);
				dgExportInvoiceItemReportSub.setProjectType(projectType);
				dgExportInvoiceItemReportSub.setProcess(process);
				dgExportInvoiceItemReportSub.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"当前出口报关单记录不存在,请先保存!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			if (customsDeclaration != null) {
				DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
				dgExportInvoiceItemReportSub
						.setCustomsDeclaration(customsDeclaration);
				dgExportInvoiceItemReportSub
						.setDeclarationCommInfoList(listInfo);
				dgExportInvoiceItemReportSub.setIsOverPrint(false);
				dgExportInvoiceItemReportSub.setProjectType(projectType);
				dgExportInvoiceItemReportSub.setProcess(process);
				dgExportInvoiceItemReportSub.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"当前出口报关单记录不存在,请先保存!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * 特殊进口发票
	 */
	public void printSpecImpInvoice(List listBase, boolean noTaoda,
			List<BaseCustomsDeclarationCommInfo> listInfo) {
		String type = ((ItemProperty) (cbbType.getSelectedItem())).getCode();
		if ("0".equals(type)) {
			CustomReportDataSource ds = new CustomReportDataSource(listBase);
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);
			// CustomsDeclarationSubReportDataSource.setSubReportData(listInfo);
			DgImportInvoiceMergerReportSet dg = new DgImportInvoiceMergerReportSet(
					true);
			dg.setBaseCustomsDeclaration(customsDeclaration);
			dg.setisImport(false);
			dg.setExp(false);
			dg.setVisible(true);
			Boolean isOk = dg.getIsOk();

			if (null == isOk || !isOk) {
				return;
			}
			if (noTaoda) {
				try {
					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream subReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportInvoiceCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isCopyPrint", Boolean.valueOf(true));
					parameters.put("subReport", subReport);
					parameters.put("isOverPrint", Boolean.valueOf(false));
					parameters.put("area", dg.getArea());
					parameters.put("dealWay", dg.getDealWay());
					InputStream reportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportInvoiceReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);
					DgReportViewer dgReportViewer = new DgReportViewer(
							jasperPrint);
					dgReportViewer.setVisible(true);
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {

				try {
					CustomsDeclarationSubReportDataSource
							.setSubReportData(listInfo);
					InputStream subReportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportInvoiceCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isCopyPrint", Boolean.valueOf(false));
					parameters.put("subReport", subReport);
					parameters.put("isOverPrint", Boolean.valueOf(true));
					InputStream reportStream = DgBaseImportCustomsDeclaration.class
							.getResourceAsStream("report/ImportInvoiceReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);
					DgReportViewer dgReportViewer = new DgReportViewer(
							jasperPrint);
					dgReportViewer.setVisible(true);
				} catch (JRException e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	/**
	 * 特殊出口发票
	 */
	public void printSpecExpInvoice(List listBase, boolean noTaoda,
			List<BaseCustomsDeclarationCommInfo> listInfo) {
		String type = ((ItemProperty) (cbbType.getSelectedItem())).getCode();
		if ("1".equals(type)) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);
			CustomsDeclarationSubReportDataSource.setSubReportData(listInfo);
			// 计算加工费
			Double process = 0d;// 加工费
			for (BaseCustomsDeclarationCommInfo info : listInfo) {
				if (info.getBaseCustomsDeclaration().getEmsHeadH2k() != null
						&& info.getCommSerialNo() != null
						&& info.getBaseCustomsDeclaration().getAuthorizeFile() != null) {
					DzscEmsExgBill exg = contractCavAction
							.getDzscEmsExgBillExgByContract((info
									.getBaseCustomsDeclaration()
									.getEmsHeadH2k()), info.getCommSerialNo(),
									info.getBaseCustomsDeclaration()
											.getAuthorizeFile());
					if (exg != null) {
						System.out.println(exg.getMachinPrice());
					}
					process = process
							+ CommonUtils.getDoubleExceptNull(info
									.getCommAmount())
							* CommonUtils
									.getDoubleExceptNull(((exg == null) ? 0
											: CommonUtils
													.getDoubleExceptNull(exg
															.getMachinPrice())));
				}
			}
			System.out.println("加工费：" + process);
			//
			if (noTaoda) {
				if (customsDeclaration != null) {
					DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
					dgExportInvoiceItemReportSub
							.setCustomsDeclaration(customsDeclaration);
					dgExportInvoiceItemReportSub
							.setDeclarationCommInfoList(listInfo);
					dgExportInvoiceItemReportSub.setIsOverPrint(true);
					dgExportInvoiceItemReportSub.setProjectType(projectType);
					dgExportInvoiceItemReportSub.setProcess(process);
					dgExportInvoiceItemReportSub.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"当前出口报关单记录不存在,请先保存!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				if (customsDeclaration != null) {
					DgExportInvoiceItemReportSub dgExportInvoiceItemReportSub = new DgExportInvoiceItemReportSub();
					dgExportInvoiceItemReportSub
							.setCustomsDeclaration(customsDeclaration);
					dgExportInvoiceItemReportSub
							.setDeclarationCommInfoList(listInfo);
					dgExportInvoiceItemReportSub.setIsOverPrint(false);
					dgExportInvoiceItemReportSub.setProjectType(projectType);
					dgExportInvoiceItemReportSub.setProcess(process);
					dgExportInvoiceItemReportSub.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"当前出口报关单记录不存在,请先保存!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	// 出口商品发票打印
	private JasperPrint printExportReport(List commInfoList,
			boolean isOverPrint, BaseCustomsDeclaration customsDeclaration,
			Boolean rb7, Boolean rb8, Boolean rb9, Double process,
			Boolean rbtnPrintNo, Boolean cbIsPrintCavOrder, String memos,
			String bank, String account, PortLin portOfTransport,
			String conveyance, String portOfAim, String invoicePeople,
			Boolean isInvoicePeople, String portOrTransport,
			Boolean isPrintCustomer, Boolean isPrintStyle, Integer leftPage,
			Integer rightPage, Integer topPage, Integer buttomPage) {
		List list = new ArrayList();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(this, "当前无数据可列印！", "提示",
					JOptionPane.YES_NO_OPTION);
			return null;
		}
		CustomReportDataSource ds = new CustomReportDataSource(list);
		try {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoList);
			InputStream subReportStream = null;
			if (rb7) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			} else if (rb8) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport8.jasper");
			} else if (rb9) {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport9.jasper");
			} else {
				subReportStream = DgExportInvoiceItemReportSub.class
						.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			}

			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", Boolean.valueOf(isOverPrint));
			parameters.put("subReport", subReport);
			if (customsDeclaration.getTradeMode() != null
					&& "来料加工".equals(customsDeclaration.getTradeMode()
							.getName())) {
				String currName = customsDeclaration.getCurrency() == null ? ""
						: customsDeclaration.getCurrency().getCurrSymb();
				String str = String.format("%.2f", process);
				String str1 = str == null ? "" : str;
				parameters.put("process", "加工费(" + currName + "):" + str1);
			} else {
				parameters.put("process", "");
			}
			if (this.getProjectType() == ProjectType.BCUS) {
				parameters
						.put("emsHeadH2k", customsDeclaration.getEmsHeadH2k());
			} else {
				parameters.put("emsHeadH2k", customsDeclaration.getContract());
			}
			// 是否打印备案序号
			parameters.put("isPrintNo", (rbtnPrintNo ? "true" : "false"));
			System.out.println(rbtnPrintNo ? "true" : "false");
			// CompanyOther other = CommonVars.getOther();
			if (cbIsPrintCavOrder
					&& !"".equals(customsDeclaration
							.getCustomsDeclarationCode())) {
				String customsDeclarationCode = customsDeclaration
						.getCustomsDeclarationCode() == null ? ""
						: customsDeclaration.getCustomsDeclarationCode();
				parameters.put("memo", "报关单号：" + customsDeclarationCode + " "
						+ memos);// 备注

			} else {
				parameters.put("memo", memos);// 备注
			}
			Company comp = (Company) CommonVars.getCurrUser().getCompany();
			if (comp != null) {
				parameters.put("companyName", comp.getBuName());// 经营单位名称
				parameters.put("companyAdd", comp.getBuaddress());// 经营单位地址
				parameters.put("companyTel", comp.getButel());// 经营单位电话

				System.out.println("outFax" + comp.getOutFax());
				parameters.put("outFax", comp.getOutFax());// 外商公司传真

				parameters.put("openAnAccountBank", bank);// 开户银行
				parameters.put("bankAccounts", account);// 帐户
				parameters.put("portOfTransport", portOfTransport.getName());// 转运港
				parameters.put("transferModeName", conveyance);// 运输工具
				parameters.put("portOfLoadingOrUnloadingName", portOfAim);// 目的港
				parameters.put("invoicePeople", invoicePeople);// 开票人
				parameters.put("invoicePeopleName", isInvoicePeople ? "开票人"
						: "");// 开票人
				parameters.put("portOrTransport", portOrTransport);// 发运港
				if (isPrintCustomer) {
					ScmCoc customer = customsDeclaration.getCustomer();
					if (customer != null) {
						parameters.put("customerName", customer.getName());// 客户公司
						parameters.put("customerTel", customer.getLinkTel());// 客户公司电话
						parameters.put("customerAdd", customer.getAddre());// 客户公司地址
					}
				}
			}
			parameters.put("transac", isPrintStyle ? "成交方式："
					+ customsDeclaration.getTransac().getName() : "");// 成交方式
			InputStream itemRepot = DgExportInvoiceItemReportSub.class
					.getResourceAsStream("report/ExportInvoiceItemReport.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(itemRepot);
			jasperDesign.setLeftMargin(leftPage);
			jasperDesign.setRightMargin(rightPage);
			jasperDesign.setTopMargin(topPage);
			jasperDesign.setBottomMargin(buttomPage);

			JasperReport js = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(js,
					parameters, ds);
			return jasperPrint;

		} catch (JRException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "边距设置超界", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		} finally {
			System.gc();
		}
		return null;
	}

	/**
	 * 进口司机纸
	 */
	public JasperPrint printImpDriversPaper(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		CustomReportDataSource ds = new CustomReportDataSource(listBase);
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
				.get(0);
		List<BaseCustomsDeclaration> list = new ArrayList<BaseCustomsDeclaration>();
		list.add(customsDeclaration);
		if (noTaoda) {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				System.out.println("标志" + ImpExpFlag);
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.encAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag, projectType);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < listInfo.size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
								.get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i < 6) {
						tempList.add(listInfo.get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(encAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));

				InputStream subReportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (listInfo.size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					//计算载货清单的页数 第一页显示6条，第二页以后均18条每页显示
					pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count -6)/18.0);
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());//
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());//
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				parameters.put("isOverPrint", Boolean.valueOf(true));
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i > 5) {
						t.add(listInfo.get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - listInfo.size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();

					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {

				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.encAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < listInfo.size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
								.get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
				}

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i < 6) {
						tempList.add(listInfo.get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(encAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (listInfo.size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					//计算载货清单的页数 第一页显示6条，第二页以后均18条每页显示
					pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count -6)/18.0);
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("sendCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("sendCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("acceptCompany", company.getName());
				parameters.put("acceptCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", company.getOutTradeCo());
				// ll
				// parameters.put("acceptCompany", company.getName());// 外商公司
				// parameters.put("sendCompany", company.getOutTradeCo());//
				// 加工公司
				parameters.put("isOverPrint", Boolean.valueOf(false));
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				if (projectType == ProjectType.BCS) {
					parameters.put("emsNo", customsDeclaration.getContract());
				} else {
					parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				}
				InputStream reportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i > 5) {
						t.add(listInfo.get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - listInfo.size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());// 载货清单共有的页数
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * 出口司机纸
	 */
	public JasperPrint printExpDriversPaper(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		CustomReportDataSource ds = new CustomReportDataSource(listBase);
		BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
				.get(0);

		List list = new ArrayList();
		list.add(customsDeclaration);
		if (noTaoda) {
			try {
				// 提运单号
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();// 运输工具
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.encAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < listInfo.size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
								.get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}

					// 获取不同的合同号(BCS与DZSC)
					if (projectType == ProjectType.BCS) {
						customMap.put(customsDeclaration.getContract(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getContract(), tempB);
						}
					} else {
						customMap.put(customsDeclaration.getEmsHeadH2k(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getEmsHeadH2k(), tempB);
						}
					}
				}

				// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				Object[] array = customMap.keySet().toArray();
				if (array.length >= 4)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString() + " "
							+ array[3].toString();
				else if (array.length == 3)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString();
				else {
					for (int i = 0; i < array.length; i++) {
						emsNo += array[i].toString() + "\n";
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i < 6) {
						tempList.add(listInfo.get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(encAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (listInfo.size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
					pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("isOverPrint", Boolean.valueOf(true));
				// parameters.put("acceptCompany", scmcoc.getName());
				// parameters.put("acceptCompanyAdd", scmcoc.getAddre());
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名

				// BCS 时把手册编号换成合同号
				// if (projectType == ProjectType.BCS) {
				// parameters.put("emsNo", customsDeclaration.getContract());
				// } else {
				// parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				// }
				InputStream reportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i > 5) {
						t.add(listInfo.get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - listInfo.size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				String homeCard = customsDeclaration.getBillOfLading();
				MotorCode motorCode = null;
				String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				if (homeCard != null && !homeCard.trim().equals("")) {
					motorCode = materialManageAction.findMotorCodeByCode(
							new Request(CommonVars.getCurrUser()), homeCard);
				}
				//
				// 相同运输工具的所有商品明细
				//
				List<Object> otherCommList = new ArrayList<Object>();
				String conveyance = customsDeclaration.getConveyance();
				Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
				Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
				//
				// 如果运输工具不为空的时候才进行检索
				//
				List allList = null;
				if (conveyance != null && !conveyance.trim().equals("")) {
					allList = this.encAction
							.findCustomsDeclarationCommInfoByConveyance(
									new Request(CommonVars.getCurrUser()),
									conveyance, ImpExpFlag);
					Map<String, String> tempMap = new HashMap<String, String>();
					for (int i = 0; i < listInfo.size(); i++) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
								.get(i);
						tempMap.put(b.getId(), b.getId());
					}
					for (Object obj : allList) {
						BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
						BaseCustomsDeclaration tempB = b
								.getBaseCustomsDeclaration();
						if (!customsDeclaration.getId().equals(tempB.getId())) {
							set.add(tempB);
						}
						if (tempMap.containsKey(b.getId())) {
							continue;
						}
						otherCommList.add(obj);
					}
					// 获取不同的合同号(BCS与DZSC)
					if (projectType == ProjectType.BCS) {
						customMap.put(customsDeclaration.getContract(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getContract(), tempB);
						}
					} else {
						customMap.put(customsDeclaration.getEmsHeadH2k(),
								customsDeclaration);
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							customMap.put(tempB.getEmsHeadH2k(), tempB);
						}
					}

				}

				// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
				Object[] array = customMap.keySet().toArray();
				if (array.length >= 4)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString() + " "
							+ array[3].toString();
				else if (array.length == 3)
					emsNo = array[0].toString() + " " + array[1].toString()
							+ "\n" + array[2].toString();
				else {
					for (int i = 0; i < array.length; i++) {
						emsNo += array[i].toString() + "\n";
					}
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);

				//
				// 获得前6条
				//
				List<Object> tempList = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i < 6) {
						tempList.add(listInfo.get(i));
					}
					if (i == 5) {
						break;
					}
				}
				//
				// 用 otherCommList 为 tempList 加到 6 个
				//
				if (tempList.size() < 6) {
					int count = tempList.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i < 6 - count) {
							tempList.add(otherCommList.get(i));
						} else {
							break;
						}
					}
				}

				String commodityNum = "";
				String grossWeight = "";
				int tempCommodityNum = 0;
				double tempGrossWeight = 0.0;
				Iterator<BaseCustomsDeclaration> iterator = set.iterator();
				while (iterator.hasNext()) {
					BaseCustomsDeclaration b = iterator.next();
					tempCommodityNum += b.getCommodityNum() == null ? 0 : b
							.getCommodityNum();
					tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
							.getGrossWeight();
				}
				tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
						: customsDeclaration.getCommodityNum();
				tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
						: customsDeclaration.getGrossWeight();
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				commodityNum = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempCommodityNum));
				commodityNum += "(" + tempCommodityNum + ")件";
				grossWeight = ConvertNumberToUpperCase.convertInteger(String
						.valueOf(tempGrossWeight));
				grossWeight += "(" + tempGrossWeight + ")公斤";

				if (allList == null)
					CustomsDeclarationSubReportDataSource
							.setSubReportData(tempList);
				else
					CustomsDeclarationSubReportDataSource
							.setSubReportData(allList);
				CustomsDeclarationSubReportDataSource
						.setContainerData(encAction
								.findAllContainerByConveyance(new Request(
										CommonVars.getCurrUser()),
										customsDeclaration));
				InputStream subReportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);

				// Company company = (Company) customsDeclaration.getCompany();
				Company company = (Company) CommonVars.getCurrUser()
						.getCompany();
				CustomReportDataSource subReportDataList = new CustomReportDataSource(
						tempList);
				Integer pageCount = 1;// 记录载货清单的页数
				// if (listInfo.size() > 6)
				// pageCount = 2;
				if (allList != null) {
					Integer count = allList.size();
					//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
					pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
				}

				parameters.put("pageCount", pageCount.toString());
				ScmCoc scmcoc = customsDeclaration.getCustomer();
				parameters.put("acceptCompany",
						scmcoc == null ? "" : scmcoc.getName());
				parameters.put("acceptCompanyAdd",
						scmcoc == null ? "" : scmcoc.getAddre());
				parameters.put("sendCompany", company.getName());
				parameters.put("sendCompanyAdd", company.getAddress());
				parameters.put("outTradeCo", customsDeclaration.getMachName());
				parameters.put("isOverPrint", Boolean.valueOf(false));
				// parameters.put("acceptCompany", company.getOutTradeCo());//
				// 外商公司
				// parameters.put("sendCompany", company.getName());// 加工公司
				parameters.put("subReport", subReport);
				parameters.put("list", list);
				parameters.put("subReportDataList", subReportDataList);
				parameters.put("commodityNum", commodityNum);
				parameters.put("grossWeight", grossWeight);
				// 香港车牌
				parameters.put("hongkongCard",
						motorCode == null
								|| motorCode.getHongkongCard() == null ? ""
								: motorCode.getHongkongCard());
				// 运输公司名称
				parameters
						.put("trafficUnit",
								motorCode == null
										|| motorCode.getTrafficUnit() == null ? ""
										: motorCode.getTrafficUnit());
				parameters.put("trafficUnitAdd",
						motorCode == null
								|| motorCode.getTrafficUnitAdd() == null ? ""
								: motorCode.getTrafficUnitAdd());
				parameters.put("trafficUnitTel",
						motorCode == null
								|| motorCode.getTrafficUnitTel() == null ? ""
								: motorCode.getTrafficUnitTel());//
				parameters.put("name",
						motorCode == null || motorCode.getName() == null ? ""
								: motorCode.getName());// 司机姓名
				// BCS 时把手册编号换成合同号
				// if (projectType == ProjectType.BCS) {
				// parameters.put("emsNo", customsDeclaration.getContract());
				// } else {
				// parameters.put("emsNo", customsDeclaration.getEmsHeadH2k());
				// }
				InputStream reportStream = DgBatchPrint.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						reportStream, parameters, ds);

				//
				// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
				//
				List<Object> t = new ArrayList<Object>();
				for (int i = 0; i < listInfo.size(); i++) {
					if (i > 5) {
						t.add(listInfo.get(i));
					}
				}
				//
				// 用 otherCommList 加到 t 中
				//
				int outCount = 6 - listInfo.size();
				for (int i = 0; i < otherCommList.size(); i++) {
					if (i >= outCount) {
						t.add(otherCommList.get(i));
					}
				}

				if (isTD && t.size() > 0) {
					CustomsDeclarationSubReportDataSource.setSubReportData(t);
					InputStream masterReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
					InputStream commInfoReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
					JasperReport commInfoReport = (JasperReport) JRLoader
							.loadObject(commInfoReportStream);
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("pageCount", pageCount.toString());
					p.put("commInfoReport", commInfoReport);
					JasperPrint jasperPrint2 = JasperFillManager.fillReport(
							masterReportStream, p, new CustomReportDataSource(
									list));
					for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
						jasperPrint.addPage((JRPrintPage) jasperPrint2
								.getPages().get(i));
					}
				}

				return jasperPrint;
			} catch (JRException e1) {
				e1.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 特殊司机纸
	 */
	public JasperPrint printSpecDriversPaper(boolean isTD, int ImpExpFlag,
			List listBase, boolean noTaoda, List listInfo) {
		String type = ((ItemProperty) (cbbType.getSelectedItem())).getCode();
		if ("0".equals(type)) {
			CustomReportDataSource ds = new CustomReportDataSource(listBase);
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);
			List<BaseCustomsDeclaration> list = new ArrayList<BaseCustomsDeclaration>();
			list.add(customsDeclaration);
			if (noTaoda) {
				try {
					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}
					//
					// 相同运输工具的所有商品明细
					//
					List<Object> otherCommList = new ArrayList<Object>();
					String conveyance = customsDeclaration.getConveyance();
					Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
					//
					// 如果运输工具不为空的时候才进行检索
					//
					System.out.println("标志" + ImpExpFlag);
					List allList = null;
					if (conveyance != null && !conveyance.trim().equals("")) {
						allList = this.encAction
								.findCustomsDeclarationCommInfoByConveyance(
										new Request(CommonVars.getCurrUser()),
										conveyance, ImpExpFlag, projectType);
						Map<String, String> tempMap = new HashMap<String, String>();
						for (int i = 0; i < listInfo.size(); i++) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
									.get(i);
							tempMap.put(b.getId(), b.getId());
						}
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							if (!customsDeclaration.getId().equals(
									tempB.getId())) {
								set.add(tempB);
							}
							if (tempMap.containsKey(b.getId())) {
								continue;
							}
							otherCommList.add(obj);
						}
					}

					//
					// 获得前6条
					//
					List<Object> tempList = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i < 6) {
							tempList.add(listInfo.get(i));
						}
						if (i == 5) {
							break;
						}
					}
					//
					// 用 otherCommList 为 tempList 加到 6 个
					//
					if (tempList.size() < 6) {
						int count = tempList.size();
						for (int i = 0; i < otherCommList.size(); i++) {
							if (i < 6 - count) {
								tempList.add(otherCommList.get(i));
							} else {
								break;
							}
						}
					}

					String commodityNum = "";
					String grossWeight = "";
					int tempCommodityNum = 0;
					double tempGrossWeight = 0.0;
					Iterator<BaseCustomsDeclaration> iterator = set.iterator();
					while (iterator.hasNext()) {
						BaseCustomsDeclaration b = iterator.next();
						tempCommodityNum += b.getCommodityNum() == null ? 0 : b
								.getCommodityNum();
						tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
								.getGrossWeight();
					}
					tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
							: customsDeclaration.getCommodityNum();
					tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
							: customsDeclaration.getGrossWeight();
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					grossWeight += "(" + tempGrossWeight + ")公斤";

					if (allList == null)
						CustomsDeclarationSubReportDataSource
								.setSubReportData(tempList);
					else
						CustomsDeclarationSubReportDataSource
								.setSubReportData(allList);
					CustomsDeclarationSubReportDataSource
							.setContainerData(encAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));

					InputStream subReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					// Company company = (Company)
					// customsDeclaration.getCompany();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							tempList);
					Integer pageCount = 1;// 记录载货清单的页数
					// if (listInfo.size() > 6)
					// pageCount = 2;
					if (allList != null) {
						Integer count = allList.size();
						//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
						pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
					}

					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("sendCompany",
							scmcoc == null ? "" : scmcoc.getName());//
					parameters.put("sendCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("acceptCompany", company.getName());//
					parameters.put("acceptCompanyAdd", company.getAddress());
					parameters.put("outTradeCo", company.getOutTradeCo());
					parameters.put("isOverPrint", Boolean.valueOf(true));
					// parameters.put("acceptCompany", company.getName());//
					// 外商公司
					// parameters.put("sendCompany", company.getOutTradeCo());//
					// 加工公司
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名
					// BCS 时把手册编号换成合同号
					if (projectType == ProjectType.BCS) {
						parameters.put("emsNo",
								customsDeclaration.getContract());
					} else {
						parameters.put("emsNo",
								customsDeclaration.getEmsHeadH2k());
					}
					InputStream reportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);

					//
					// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
					//
					List<Object> t = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i > 5) {
							t.add(listInfo.get(i));
						}
					}
					//
					// 用 otherCommList 加到 t 中
					//
					int outCount = 6 - listInfo.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i >= outCount) {
							t.add(otherCommList.get(i));
						}
					}

					if (isTD && t.size() > 0) {
						CustomsDeclarationSubReportDataSource
								.setSubReportData(t);
						InputStream masterReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
						InputStream commInfoReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);
						Map<String, Object> p = new HashMap<String, Object>();

						p.put("pageCount", pageCount.toString());// 载货清单共有的页数
						p.put("commInfoReport", commInfoReport);
						JasperPrint jasperPrint2 = JasperFillManager
								.fillReport(masterReportStream, p,
										new CustomReportDataSource(list));
						for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
							jasperPrint.addPage((JRPrintPage) jasperPrint2
									.getPages().get(i));
						}
					}

					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {
				try {

					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}
					//
					// 相同运输工具的所有商品明细
					//
					List<Object> otherCommList = new ArrayList<Object>();
					String conveyance = customsDeclaration.getConveyance();
					Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
					//
					// 如果运输工具不为空的时候才进行检索
					//
					List allList = null;
					if (conveyance != null && !conveyance.trim().equals("")) {
						allList = this.encAction
								.findCustomsDeclarationCommInfoByConveyance(
										new Request(CommonVars.getCurrUser()),
										conveyance, ImpExpFlag);
						Map<String, String> tempMap = new HashMap<String, String>();
						for (int i = 0; i < listInfo.size(); i++) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
									.get(i);
							tempMap.put(b.getId(), b.getId());
						}
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							if (!customsDeclaration.getId().equals(
									tempB.getId())) {
								set.add(tempB);
							}
							if (tempMap.containsKey(b.getId())) {
								continue;
							}
							otherCommList.add(obj);
						}
					}

					//
					// 获得前6条
					//
					List<Object> tempList = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i < 6) {
							tempList.add(listInfo.get(i));
						}
						if (i == 5) {
							break;
						}
					}
					//
					// 用 otherCommList 为 tempList 加到 6 个
					//
					if (tempList.size() < 6) {
						int count = tempList.size();
						for (int i = 0; i < otherCommList.size(); i++) {
							if (i < 6 - count) {
								tempList.add(otherCommList.get(i));
							} else {
								break;
							}
						}
					}

					String commodityNum = "";
					String grossWeight = "";
					int tempCommodityNum = 0;
					double tempGrossWeight = 0.0;
					Iterator<BaseCustomsDeclaration> iterator = set.iterator();
					while (iterator.hasNext()) {
						BaseCustomsDeclaration b = iterator.next();
						tempCommodityNum += b.getCommodityNum() == null ? 0 : b
								.getCommodityNum();
						tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
								.getGrossWeight();
					}
					tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
							: customsDeclaration.getCommodityNum();
					tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
							: customsDeclaration.getGrossWeight();
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					grossWeight += "(" + tempGrossWeight + ")公斤";

					if (allList == null)
						CustomsDeclarationSubReportDataSource
								.setSubReportData(tempList);
					else
						CustomsDeclarationSubReportDataSource
								.setSubReportData(allList);
					CustomsDeclarationSubReportDataSource
							.setContainerData(encAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));
					InputStream subReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					Map<String, Object> parameters = new HashMap<String, Object>();
					// Company company = (Company)
					// customsDeclaration.getCompany();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							tempList);
					Integer pageCount = 1;// 记录载货清单的页数
					// if (listInfo.size() > 6)
					// pageCount = 2;
					if (allList != null) {
						Integer count = allList.size();
						//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
						pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
					}

					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("sendCompany",
							scmcoc == null ? "" : scmcoc.getName());
					parameters.put("sendCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("acceptCompany", company.getName());
					parameters.put("acceptCompanyAdd", company.getAddress());
					parameters.put("outTradeCo", company.getOutTradeCo());
					// ll
					// parameters.put("acceptCompany", company.getName());//
					// 外商公司
					// parameters.put("sendCompany", company.getOutTradeCo());//
					// 加工公司
					parameters.put("isOverPrint", Boolean.valueOf(false));
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名
					// BCS 时把手册编号换成合同号
					if (projectType == ProjectType.BCS) {
						parameters.put("emsNo",
								customsDeclaration.getContract());
					} else {
						parameters.put("emsNo",
								customsDeclaration.getEmsHeadH2k());
					}
					InputStream reportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);

					//
					// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
					//
					List<Object> t = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i > 5) {
							t.add(listInfo.get(i));
						}
					}
					//
					// 用 otherCommList 加到 t 中
					//
					int outCount = 6 - listInfo.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i >= outCount) {
							t.add(otherCommList.get(i));
						}
					}

					if (isTD && t.size() > 0) {
						CustomsDeclarationSubReportDataSource
								.setSubReportData(t);
						InputStream masterReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
						InputStream commInfoReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("pageCount", pageCount.toString());// 载货清单共有的页数
						p.put("commInfoReport", commInfoReport);
						JasperPrint jasperPrint2 = JasperFillManager
								.fillReport(masterReportStream, p,
										new CustomReportDataSource(list));
						for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
							jasperPrint.addPage((JRPrintPage) jasperPrint2
									.getPages().get(i));
						}
					}

					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		} else if ("1".equals(type)) {
			CustomReportDataSource ds = new CustomReportDataSource(listBase);
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) listBase
					.get(0);

			List list = new ArrayList();
			list.add(customsDeclaration);
			if (noTaoda) {
				try {
					// 提运单号
					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}
					//
					// 相同运输工具的所有商品明细
					//
					List<Object> otherCommList = new ArrayList<Object>();
					String conveyance = customsDeclaration.getConveyance();// 运输工具
					Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
					Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
					//
					// 如果运输工具不为空的时候才进行检索
					//
					List allList = null;
					if (conveyance != null && !conveyance.trim().equals("")) {
						allList = this.encAction
								.findCustomsDeclarationCommInfoByConveyance(
										new Request(CommonVars.getCurrUser()),
										conveyance, ImpExpFlag);
						Map<String, String> tempMap = new HashMap<String, String>();
						for (int i = 0; i < listInfo.size(); i++) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
									.get(i);
							tempMap.put(b.getId(), b.getId());
						}
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							if (!customsDeclaration.getId().equals(
									tempB.getId())) {
								set.add(tempB);
							}
							if (tempMap.containsKey(b.getId())) {
								continue;
							}
							otherCommList.add(obj);
						}

						// 获取不同的合同号(BCS与DZSC)
						if (projectType == ProjectType.BCS) {
							customMap.put(customsDeclaration.getContract(),
									customsDeclaration);
							for (Object obj : allList) {
								BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
								BaseCustomsDeclaration tempB = b
										.getBaseCustomsDeclaration();
								customMap.put(tempB.getContract(), tempB);
							}
						} else {
							customMap.put(customsDeclaration.getEmsHeadH2k(),
									customsDeclaration);
							for (Object obj : allList) {
								BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
								BaseCustomsDeclaration tempB = b
										.getBaseCustomsDeclaration();
								customMap.put(tempB.getEmsHeadH2k(), tempB);
							}
						}
					}

					// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
					Object[] array = customMap.keySet().toArray();
					if (array.length >= 4)
						emsNo = array[0].toString() + " " + array[1].toString()
								+ "\n" + array[2].toString() + " "
								+ array[3].toString();
					else if (array.length == 3)
						emsNo = array[0].toString() + " " + array[1].toString()
								+ "\n" + array[2].toString();
					else {
						for (int i = 0; i < array.length; i++) {
							emsNo += array[i].toString() + "\n";
						}
					}
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("emsNo", emsNo);

					//
					// 获得前6条
					//
					List<Object> tempList = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i < 6) {
							tempList.add(listInfo.get(i));
						}
						if (i == 5) {
							break;
						}
					}
					//
					// 用 otherCommList 为 tempList 加到 6 个
					//
					if (tempList.size() < 6) {
						int count = tempList.size();
						for (int i = 0; i < otherCommList.size(); i++) {
							if (i < 6 - count) {
								tempList.add(otherCommList.get(i));
							} else {
								break;
							}
						}
					}

					String commodityNum = "";
					String grossWeight = "";
					int tempCommodityNum = 0;
					double tempGrossWeight = 0.0;
					Iterator<BaseCustomsDeclaration> iterator = set.iterator();
					while (iterator.hasNext()) {
						BaseCustomsDeclaration b = iterator.next();
						tempCommodityNum += b.getCommodityNum() == null ? 0 : b
								.getCommodityNum();
						tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
								.getGrossWeight();
					}
					tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
							: customsDeclaration.getCommodityNum();
					tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
							: customsDeclaration.getGrossWeight();
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					grossWeight += "(" + tempGrossWeight + ")公斤";

					if (allList == null)
						CustomsDeclarationSubReportDataSource
								.setSubReportData(tempList);
					else
						CustomsDeclarationSubReportDataSource
								.setSubReportData(allList);
					CustomsDeclarationSubReportDataSource
							.setContainerData(encAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));
					InputStream subReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);
					// Company company = (Company)
					// customsDeclaration.getCompany();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							tempList);
					Integer pageCount = 1;// 记录载货清单的页数
					// if (listInfo.size() > 6)
					// pageCount = 2;
					if (allList != null) {
						Integer count = allList.size();
						//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
						pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
					}

					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("isOverPrint", Boolean.valueOf(true));
					// parameters.put("acceptCompany", scmcoc.getName());
					// parameters.put("acceptCompanyAdd", scmcoc.getAddre());
					parameters.put("acceptCompany", scmcoc == null ? ""
							: scmcoc.getName());
					parameters.put("acceptCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("sendCompany", company.getName());
					parameters.put("sendCompanyAdd", company.getAddress());
					parameters.put("outTradeCo",
							customsDeclaration.getMachName());
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名

					// BCS 时把手册编号换成合同号
					// if (projectType == ProjectType.BCS) {
					// parameters.put("emsNo",
					// customsDeclaration.getContract());
					// } else {
					// parameters.put("emsNo",
					// customsDeclaration.getEmsHeadH2k());
					// }
					InputStream reportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);

					//
					// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
					//
					List<Object> t = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i > 5) {
							t.add(listInfo.get(i));
						}
					}
					//
					// 用 otherCommList 加到 t 中
					//
					int outCount = 6 - listInfo.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i >= outCount) {
							t.add(otherCommList.get(i));
						}
					}

					if (isTD && t.size() > 0) {
						CustomsDeclarationSubReportDataSource
								.setSubReportData(t);
						InputStream masterReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
						InputStream commInfoReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("pageCount", pageCount.toString());
						p.put("commInfoReport", commInfoReport);
						JasperPrint jasperPrint2 = JasperFillManager
								.fillReport(masterReportStream, p,
										new CustomReportDataSource(list));
						for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
							jasperPrint.addPage((JRPrintPage) jasperPrint2
									.getPages().get(i));
						}
					}

					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					String homeCard = customsDeclaration.getBillOfLading();
					MotorCode motorCode = null;
					String emsNo = "";// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
					if (homeCard != null && !homeCard.trim().equals("")) {
						motorCode = materialManageAction
								.findMotorCodeByCode(
										new Request(CommonVars.getCurrUser()),
										homeCard);
					}
					//
					// 相同运输工具的所有商品明细
					//
					List<Object> otherCommList = new ArrayList<Object>();
					String conveyance = customsDeclaration.getConveyance();
					Set<BaseCustomsDeclaration> set = new HashSet<BaseCustomsDeclaration>();
					Map<String, BaseCustomsDeclaration> customMap = new HashMap<String, BaseCustomsDeclaration>();
					//
					// 如果运输工具不为空的时候才进行检索
					//
					List allList = null;
					if (conveyance != null && !conveyance.trim().equals("")) {
						allList = this.encAction
								.findCustomsDeclarationCommInfoByConveyance(
										new Request(CommonVars.getCurrUser()),
										conveyance, ImpExpFlag);
						Map<String, String> tempMap = new HashMap<String, String>();
						for (int i = 0; i < listInfo.size(); i++) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) listInfo
									.get(i);
							tempMap.put(b.getId(), b.getId());
						}
						for (Object obj : allList) {
							BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
							BaseCustomsDeclaration tempB = b
									.getBaseCustomsDeclaration();
							if (!customsDeclaration.getId().equals(
									tempB.getId())) {
								set.add(tempB);
							}
							if (tempMap.containsKey(b.getId())) {
								continue;
							}
							otherCommList.add(obj);
						}
						// 获取不同的合同号(BCS与DZSC)
						if (projectType == ProjectType.BCS) {
							customMap.put(customsDeclaration.getContract(),
									customsDeclaration);
							for (Object obj : allList) {
								BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
								BaseCustomsDeclaration tempB = b
										.getBaseCustomsDeclaration();
								customMap.put(tempB.getContract(), tempB);
							}
						} else {
							customMap.put(customsDeclaration.getEmsHeadH2k(),
									customsDeclaration);
							for (Object obj : allList) {
								BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) obj;
								BaseCustomsDeclaration tempB = b
										.getBaseCustomsDeclaration();
								customMap.put(tempB.getEmsHeadH2k(), tempB);
							}
						}

					}

					// 用来记录多份合同号(BCS与DZSC时),DZZC时只取一个emsHeadH2k
					Object[] array = customMap.keySet().toArray();
					if (array.length >= 4)
						emsNo = array[0].toString() + " " + array[1].toString()
								+ "\n" + array[2].toString() + " "
								+ array[3].toString();
					else if (array.length == 3)
						emsNo = array[0].toString() + " " + array[1].toString()
								+ "\n" + array[2].toString();
					else {
						for (int i = 0; i < array.length; i++) {
							emsNo += array[i].toString() + "\n";
						}
					}
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("emsNo", emsNo);

					//
					// 获得前6条
					//
					List<Object> tempList = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i < 6) {
							tempList.add(listInfo.get(i));
						}
						if (i == 5) {
							break;
						}
					}
					//
					// 用 otherCommList 为 tempList 加到 6 个
					//
					if (tempList.size() < 6) {
						int count = tempList.size();
						for (int i = 0; i < otherCommList.size(); i++) {
							if (i < 6 - count) {
								tempList.add(otherCommList.get(i));
							} else {
								break;
							}
						}
					}

					String commodityNum = "";
					String grossWeight = "";
					int tempCommodityNum = 0;
					double tempGrossWeight = 0.0;
					Iterator<BaseCustomsDeclaration> iterator = set.iterator();
					while (iterator.hasNext()) {
						BaseCustomsDeclaration b = iterator.next();
						tempCommodityNum += b.getCommodityNum() == null ? 0 : b
								.getCommodityNum();
						tempGrossWeight += b.getGrossWeight() == null ? 0.0 : b
								.getGrossWeight();
					}
					tempCommodityNum += customsDeclaration.getCommodityNum() == null ? 0
							: customsDeclaration.getCommodityNum();
					tempGrossWeight += customsDeclaration.getGrossWeight() == null ? 0.0
							: customsDeclaration.getGrossWeight();
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					commodityNum = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempCommodityNum));
					commodityNum += "(" + tempCommodityNum + ")件";
					grossWeight = ConvertNumberToUpperCase
							.convertInteger(String.valueOf(tempGrossWeight));
					grossWeight += "(" + tempGrossWeight + ")公斤";

					if (allList == null)
						CustomsDeclarationSubReportDataSource
								.setSubReportData(tempList);
					else
						CustomsDeclarationSubReportDataSource
								.setSubReportData(allList);
					CustomsDeclarationSubReportDataSource
							.setContainerData(encAction
									.findAllContainerByConveyance(new Request(
											CommonVars.getCurrUser()),
											customsDeclaration));
					InputStream subReportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReport.jasper");
					JasperReport subReport = (JasperReport) JRLoader
							.loadObject(subReportStream);

					// Company company = (Company)
					// customsDeclaration.getCompany();
					Company company = (Company) CommonVars.getCurrUser()
							.getCompany();
					CustomReportDataSource subReportDataList = new CustomReportDataSource(
							tempList);
					Integer pageCount = 1;// 记录载货清单的页数
					// if (listInfo.size() > 6)
					// pageCount = 2;
					if (allList != null) {
						Integer count = allList.size();
						//计算载货清单的页数 第一页显示6条，第二页及以上页面均每页显示18条
						pageCount = count <= 6 ? 1 : 1 + (int)Math.ceil((count - 6) / 18.0d);
					}

					parameters.put("pageCount", pageCount.toString());
					ScmCoc scmcoc = customsDeclaration.getCustomer();
					parameters.put("acceptCompany", scmcoc == null ? ""
							: scmcoc.getName());
					parameters.put("acceptCompanyAdd", scmcoc == null ? ""
							: scmcoc.getAddre());
					parameters.put("sendCompany", company.getName());
					parameters.put("sendCompanyAdd", company.getAddress());
					parameters.put("outTradeCo",
							customsDeclaration.getMachName());
					parameters.put("isOverPrint", Boolean.valueOf(false));
					// parameters.put("acceptCompany",
					// company.getOutTradeCo());//
					// 外商公司
					// parameters.put("sendCompany", company.getName());// 加工公司
					parameters.put("subReport", subReport);
					parameters.put("list", list);
					parameters.put("subReportDataList", subReportDataList);
					parameters.put("commodityNum", commodityNum);
					parameters.put("grossWeight", grossWeight);
					// 香港车牌
					parameters.put("hongkongCard", motorCode == null
							|| motorCode.getHongkongCard() == null ? ""
							: motorCode.getHongkongCard());
					// 运输公司名称
					parameters.put("trafficUnit", motorCode == null
							|| motorCode.getTrafficUnit() == null ? ""
							: motorCode.getTrafficUnit());
					parameters.put("trafficUnitAdd", motorCode == null
							|| motorCode.getTrafficUnitAdd() == null ? ""
							: motorCode.getTrafficUnitAdd());
					parameters.put("trafficUnitTel", motorCode == null
							|| motorCode.getTrafficUnitTel() == null ? ""
							: motorCode.getTrafficUnitTel());//
					parameters
							.put("name",
									motorCode == null
											|| motorCode.getName() == null ? ""
											: motorCode.getName());// 司机姓名
					// BCS 时把手册编号换成合同号
					// if (projectType == ProjectType.BCS) {
					// parameters.put("emsNo",
					// customsDeclaration.getContract());
					// } else {
					// parameters.put("emsNo",
					// customsDeclaration.getEmsHeadH2k());
					// }
					InputStream reportStream = DgBatchPrint.class
							.getResourceAsStream("report/ExportCustomesAutocarMainfestReport.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportStream, parameters, ds);

					//
					// 内地海关及香港海关陆路出/进境载货清单(附表) 获得前6条后面的所有记录
					//
					List<Object> t = new ArrayList<Object>();
					for (int i = 0; i < listInfo.size(); i++) {
						if (i > 5) {
							t.add(listInfo.get(i));
						}
					}
					//
					// 用 otherCommList 加到 t 中
					//
					int outCount = 6 - listInfo.size();
					for (int i = 0; i < otherCommList.size(); i++) {
						if (i >= outCount) {
							t.add(otherCommList.get(i));
						}
					}

					if (isTD && t.size() > 0) {
						CustomsDeclarationSubReportDataSource
								.setSubReportData(t);
						InputStream masterReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessoryReport.jasper");
						InputStream commInfoReportStream = DgBatchPrint.class
								.getResourceAsStream("report/ExportCustomsMainfestAccessorySubReport.jasper");
						JasperReport commInfoReport = (JasperReport) JRLoader
								.loadObject(commInfoReportStream);
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("pageCount", pageCount.toString());
						p.put("commInfoReport", commInfoReport);
						JasperPrint jasperPrint2 = JasperFillManager
								.fillReport(masterReportStream, p,
										new CustomReportDataSource(list));
						for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
							jasperPrint.addPage((JRPrintPage) jasperPrint2
									.getPages().get(i));
						}
					}

					return jasperPrint;
				} catch (JRException e1) {
					e1.printStackTrace();
				}

			}
		}
		return null;
	}

	/**
	 * 批量打印司机纸
	 */
	public void batchPrintDriversPaper(List<BaseCustomsDeclaration> list,
			Boolean noTaoda) {
		Map<String, List> map = (Map<String, List>) encAction
				.findCustomsDeclarationInfos(
						new Request(CommonVars.getCurrUser()), getBaseId(list),
						projectType);
		BaseCustomsDeclaration customsDeclaration = null;
		JasperPrint jasperPrint = null;
		try {
			List<BaseCustomsDeclaration> listBase = new ArrayList<BaseCustomsDeclaration>();
			listBase.add(list.get(0));
			List<BaseCustomsDeclarationCommInfo> listInfo = map.get(list.get(0)
					.getId());
			if (listInfo == null || listInfo.size() < 1) {
				listInfo = new ArrayList<BaseCustomsDeclarationCommInfo>();
			}
			jasperPrint = getJasperPrint(listBase, noTaoda, listInfo);

			for (int n = 1; n < list.size(); n++) {
				List<JRPrintPage> pages = null;
				customsDeclaration = list.get(n);
				listBase.clear();
				listBase.add(customsDeclaration);
				listInfo.clear();
				if (map.get(list.get(n).getId()) != null) {
					listInfo.addAll(map.get(list.get(n).getId()));
				}
				// if(impExpFlag==ImpExpFlag.IMPORT){
				// JasperPrint jp = printImpDriversPaper(false,
				// ImpExpFlag.IMPORT, listBase, noTaoda,listInfo);
				// if(jp!=null){
				// pages = jp.getPages();
				// }
				// }else{
				// JasperPrint jp = printExpDriversPaper(false,
				// ImpExpFlag.EXPORT, listBase, noTaoda,listInfo);
				// if(jp!=null){
				// pages = jp.getPages();
				// }
				// }
				JasperPrint jp = getJasperPrint(listBase, noTaoda, listInfo);
				if (jp != null) {
					pages = jp.getPages();
				}
				if (pages != null && jasperPrint != null) {
					for (JRPrintPage printPage : pages) {
						jasperPrint.addPage(printPage);
					}
				}
			}

			if (jasperPrint == null || jasperPrint.getPages().size() == 0) {
				return;
			}

			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);

		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private JasperPrint getJasperPrint(List listBase, Boolean noTaoda,
			List listInfo) {

		String type = ((ItemProperty) (cbbPrintStyle.getSelectedItem()))
				.getCode();

		JasperPrint jasperPrint = null;
		if (impExpFlag == ImpExpFlag.IMPORT) {
			if ("0".equals(type)) {
				jasperPrint = printImpCustoms(false, ImpExpFlag.IMPORT,
						listBase, noTaoda, listInfo);
			} else if ("1".equals(type)) {
				jasperPrint = printImpDriversPaper(false, ImpExpFlag.IMPORT,
						listBase, noTaoda, listInfo);
			}
		} else if (impExpFlag == ImpExpFlag.EXPORT) {
			if ("0".equals(type)) {
				jasperPrint = printExpCustoms(false, ImpExpFlag.EXPORT,
						listBase, noTaoda, listInfo);
			} else if ("1".equals(type)) {
				jasperPrint = printExpDriversPaper(false, ImpExpFlag.EXPORT,
						listBase, noTaoda, listInfo);
			} else {
				printExpInvoice(listBase, noTaoda, listInfo);
			}
		} else if (impExpFlag == ImpExpFlag.SPECIAL) {
			if ("0".equals(type)) {
				jasperPrint = printSpecCustoms(false, ImpExpFlag.SPECIAL,
						listBase, noTaoda, listInfo);
			} else if ("1".equals(type)) {
				jasperPrint = printSpecDriversPaper(false, ImpExpFlag.SPECIAL,
						listBase, noTaoda, listInfo);
			} else if ("2".equals(type)) {
				printSpecImpInvoice(listBase, noTaoda, listInfo);
			} else {
				printSpecExpInvoice(listBase, noTaoda, listInfo);
			}
		}
		return jasperPrint;
	}

	/**
	 * 获取报关单表头id
	 * 
	 * @return
	 */
	private List<String> getBaseId(List<BaseCustomsDeclaration> listBase) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listBase.size(); i++) {
			list.add(listBase.get(i).getId());
		}
		return list;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("打印类别");
			lblNewLabel.setBounds(8, 14, 54, 15);
		}
		return lblNewLabel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("报关单号");
			label.setBounds(199, 14, 54, 15);
		}
		return label;
	}

	private JLabel getImpExpTypeLabel() {
		if (impExpTypeLabel == null) {
			impExpTypeLabel = new JLabel("进出口类型");
			impExpTypeLabel.setBounds(482, 14, 67, 15);
		}
		return impExpTypeLabel;
	}

	private JComboBox getCbbPrintStyle() {
		if (cbbPrintStyle == null) {
			cbbPrintStyle = new JComboBox();
			cbbPrintStyle.setBounds(new Rectangle(61, 42, 90, 27));
			cbbPrintStyle.setBounds(61, 8, 116, 27);
		}
		return cbbPrintStyle;
	}

	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setBounds(new Rectangle(61, 42, 90, 27));
			cbbType.setBounds(548, 8, 106, 27);
		}
		return cbbType;
	}

	public List isImportOrExport(String type) {
		List<Object> list = new ArrayList<Object>();
		if ("0".equals(type)) {
			list.add(ImpExpType.DIRECT_IMPORT);
			list.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			list.add(ImpExpType.BACK_FACTORY_REWORK);
			list.add(ImpExpType.GENERAL_TRADE_IMPORT);
			list.add(ImpExpType.EQUIPMENT_IMPORT);
			list.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
			list.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
			list.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			list.add(ImpExpType.IMPORT_STORAGE);
		} else if ("1".equals(type)) {
			list.add(ImpExpType.DIRECT_EXPORT);
			list.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			list.add(ImpExpType.BACK_MATERIEL_EXPORT);
			list.add(ImpExpType.REWORK_EXPORT);
			list.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
			list.add(ImpExpType.GENERAL_TRADE_EXPORT);
			list.add(ImpExpType.EQUIPMENT_BACK_PORT);
			list.add(ImpExpType.BACK_PORT_REPAIR);
			list.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		}
		return list;
	}
	
}
