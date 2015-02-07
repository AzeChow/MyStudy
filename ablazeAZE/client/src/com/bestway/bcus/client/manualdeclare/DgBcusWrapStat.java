package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgBcusWrapStat extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JComboBox cbbWrapType = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbImpExpFlag = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JButton btnQuery = null;

	private JButton btnReport = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private EncAction encAction = null;

	private CustomBaseAction customBaseAction = null;

	private JTableListModel tableModel = null;

	private JComboBox cbbEffectiveState = null;

	private ManualDeclareAction manualDeclareAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBcusWrapStat() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(685, 487));
		this.setContentPane(getJContentPane());
		this.setTitle("进出包装统计");

	}

	private void initUIComponents() {
		// 包装种类
		List listwarp = customBaseAction.findWrap();
		this.getCbbWrapType().setModel(
				new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedIndex(-1);

		// 进出口标志
		this.cbbImpExpFlag.removeAllItems();
		this.cbbImpExpFlag.addItem(new ItemProperty(Integer.valueOf(
				ImpExpFlag.IMPORT).toString(), "进口报关单"));
		this.cbbImpExpFlag.addItem(new ItemProperty(Integer.valueOf(
				ImpExpFlag.EXPORT).toString(), "出口报关单"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpFlag);
		this.cbbImpExpFlag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbImpExpFlag.setSelectedIndex(-1);

		// 生效状态
		this.cbbEffectiveState.removeAllItems();
		this.cbbEffectiveState.addItem(new ItemProperty(Integer.valueOf(1)
				.toString(), "已生效"));
		this.cbbEffectiveState.addItem(new ItemProperty(Integer.valueOf(0)
				.toString(), "未生效"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbEffectiveState);
		this.cbbEffectiveState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbEffectiveState.setSelectedIndex(-1);

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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(80);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(402, 10, 20, 25));
			jLabel22.setText("至");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(220, 10, 51, 25));
			jLabel21.setText("报关日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(219, 44, 51, 25));
			jLabel2.setText("生效状态");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 44, 57, 25));
			jLabel1.setText("进出类型");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(17, 10, 57, 25));
			jLabel.setText("包装种类");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbWrapType(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbImpExpFlag(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnReport(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbEffectiveState(), null);
		}
		return jPanel;
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(75, 10, 136, 25));
		}
		return cbbWrapType;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpFlag() {
		if (cbbImpExpFlag == null) {
			cbbImpExpFlag = new JComboBox();
			cbbImpExpFlag.setBounds(new Rectangle(75, 44, 136, 25));
		}
		return cbbImpExpFlag;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(573, 9, 75, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Wrap wrap = null;// 包装种类
					Date beginDate = null, endDate = null;// 申报日期
					Integer impExpFlag = null;// 进出口标志
					Boolean effective = null;// 是否生效
					if (cbbWrapType.getSelectedItem() != null) {
						wrap = (Wrap) cbbWrapType.getSelectedItem();
					}
					beginDate = cbbBeginDate.getDate();
					endDate = cbbEndDate.getDate();
					if (cbbImpExpFlag.getSelectedItem() != null) {
						String code = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();
						impExpFlag = Integer.valueOf(code);
					}
					if (cbbEffectiveState.getSelectedItem() != null) {
						String code = ((ItemProperty) cbbEffectiveState
								.getSelectedItem()).getCode();
						effective = Boolean.valueOf(code);
					}

					List lsResult = encAction.findWrapStat(new Request(
							CommonVars.getCurrUser(), true), wrap, impExpFlag,
							effective, beginDate, endDate);
					initTable(lsResult);
				}
			});
		}
		return btnQuery;
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(this.getJTable(), list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("包装种类", "wrapName", 150));
						list.add(addColumn("包装重量", "wrapWeight", 150));
						list.add(addColumn("包装单位", "wrapUnit", 150));
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDeclareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReport() {
		if (btnReport == null) {
			btnReport = new JButton();
			btnReport.setBounds(new Rectangle(573, 43, 75, 25));
			btnReport.setText("打印");
			btnReport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(DgBcusWrapStat.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List dlist = tableModel.getList();
					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					InputStream reportStream = DgBcusWrapStat.class
							.getResourceAsStream("report/WrapStat.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("beginDate",
							(cbbBeginDate.getDate() == null ? "" : dateFormat
									.format(cbbBeginDate.getDate())));
					parameters.put("endDate",
							(cbbEndDate.getDate() == null ? "" : dateFormat
									.format(cbbEndDate.getDate())));
					String wrapType = "所有包装种类";
					if (cbbWrapType.getSelectedItem() != null) {
						Wrap wrap = (Wrap) cbbWrapType.getSelectedItem();
						wrapType = wrap.getName();
					}
					parameters.put("wrapType", wrapType);

					String title = "进/出口包装 统计表";
					if (cbbImpExpFlag.getSelectedItem() != null) {
						String code = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();
						if (ImpExpFlag.IMPORT == Integer.valueOf(code))
							title = "进口包装 统计表";
						else
							title = "出口包装 统计表";
					}
					parameters.put("title", title);

					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnReport;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(277, 10, 119, 25));
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
			cbbEndDate.setBounds(new Rectangle(428, 10, 123, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbbEffectiveState
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEffectiveState() {
		if (cbbEffectiveState == null) {
			cbbEffectiveState = new JComboBox();
			cbbEffectiveState.setBounds(new Rectangle(277, 44, 119, 25));
		}
		return cbbEffectiveState;
	}

}
