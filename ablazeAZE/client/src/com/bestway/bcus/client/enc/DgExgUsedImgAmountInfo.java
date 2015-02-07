package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
@SuppressWarnings("unchecked")
public class DgExgUsedImgAmountInfo extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbImpExpType = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JButton btnQuery = null;

	private JButton btnReport = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JTextField tfExgSeqNum = null;

	private ManualDeclareAction manualDeclareAction = null;

	private EncAction encAction = null;

	private JTableListModel tableModel = null;

	private JCheckBox cbIsPrintZeroTotalUsed = null;

	private JRadioButton rbEffective = null;

	private JRadioButton rbInvalid = null;

	private JRadioButton rbAll = null;
	
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgExgUsedImgAmountInfo() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initUIComponents();
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(869, 488));
		this.setContentPane(getJContentPane());
		this.setTitle("出口成品耗用料件统计");
		getButtonGroup();
	}

	private void initUIComponents() {
		// 电子帐册
		cbbEmsNo.removeAllItems();
		List contracts = manualDeclareAction
				.findEmsHeadH2kInExecuting(new Request(
						CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmsNo.addItem((EmsHeadH2k) contracts.get(i));
			}
			this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
					"preEmsNo", "emsNo", 0, 150));
		}
		if (this.cbbEmsNo.getItemCount() > 0) {
			this.cbbEmsNo.setSelectedIndex(0);
		}

		// 初始化出口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbImpExpType.setSelectedIndex(-1);
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
			jLabel22.setBounds(new Rectangle(406, 44, 52, 25));
			jLabel22.setText("结束日期");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(220, 44, 51, 25));
			jLabel21.setText("开始日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(220, 10, 51, 25));
			jLabel2.setText("选择成品");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 44, 57, 25));
			jLabel1.setText("出口类型");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(17, 10, 57, 25));
			jLabel.setText("账册编号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnReport(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getTfExgSeqNum(), null);
			jPanel.add(getCbIsPrintZeroTotalUsed(), null);
			jPanel.add(getRbEffective(), null);
			jPanel.add(getRbInvalid(), null);
			jPanel.add(getRbAll(), null);
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
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(75, 10, 136, 25));
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(75, 44, 136, 25));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(765, 10, 62, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
							.getSelectedItem();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								DgExgUsedImgAmountInfo.this, "请选择账册编号");
					}
					Integer impExpType = null;
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = Integer
								.parseInt(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode().trim());
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					// String[] strTemp =
					// tfExgSeqNum.getText().trim().split(",");
					// List<Integer> lsTemp = new ArrayList<Integer>();
					// for (int i = 0; i < strTemp.length; i++) {
					// if (strTemp[i] != null && !"".equals(strTemp[i].trim()))
					// {
					// lsTemp.add(Integer.parseInt(strTemp[i]));
					// }
					// }
					// Integer[] exgSeqNums = new Integer[lsTemp.size()];
					// for(int i=0;i<lsTemp.size();i++){
					// exgSeqNums[i]=lsTemp.get(i);
					// }
					Integer[] exgSeqNums = CommonVars.getIntegerArrayBySplit(
							tfExgSeqNum.getText().trim(), ",");
					boolean  isPrintZeroTotalUsed = cbIsPrintZeroTotalUsed.isSelected();
					
					Boolean isEffective = true;
					if(rbEffective.isSelected()){
						isEffective = true;
					}else if (rbInvalid.isSelected()){
						isEffective = false;
					}else if (rbAll.isSelected()){
						isEffective = null;
					}else {
						isEffective = true;
					}
					
					
					//System.out.println(encAction.getClass().getName());
					
					List lsResult = encAction.findExgUsedImgAmountInfo(
							new Request(CommonVars.getCurrUser(), true),
							emsHeadH2k, exgSeqNums, beginDate, endDate,
							impExpType, isPrintZeroTotalUsed,isEffective);
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
						list.add(addColumn("成品备案序号", "exgSeqNum", 100,
								Integer.class));
						list.add(addColumn("成品商品编码", "exgComplex", 100));
						list.add(addColumn("成品名称规格", "exgNameSpec", 100));
						list.add(addColumn("单位", "exgUnit", 100));
						list.add(addColumn("对应版本号", "version", 100));
						list.add(addColumn("成品出口数量", "exgAmount", 100));
						list.add(addColumn("料件备案序号", "imgSeqNum", 100,
								Integer.class));
						list.add(addColumn("料件商品编码", "imgComplex", 100));
						list.add(addColumn("料件名称规格", "imgNameSpec", 100));
						list.add(addColumn("单位", "imgUnit", 100));
						
						list.add(addColumn("单耗", "unitWearNew", 100));
						list.add(addColumn("损耗", "wasteNew", 100));
						
						list.add(addColumn("单耗用量", "unitUsed", 100));
						list.add(addColumn("损耗用量", "waste", 100));
						list.add(addColumn("耗用料件总量", "totalUsed", 100));
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
			btnReport.setBounds(new Rectangle(765, 44, 62, 25));
			btnReport.setText("打印");
			btnReport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgExgUsedImgAmountInfo.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List dlist = tableModel.getList();
					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					InputStream reportStream = DgExgUsedImgAmountInfo.class
							.getResourceAsStream("report/ExgUsedImgAmountInfo.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					JasperPrint jasperPrint;
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
			cbbBeginDate.setBounds(new Rectangle(277, 44, 119, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(458, 44, 123, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgSeqNum() {
		if (tfExgSeqNum == null) {
			tfExgSeqNum = new JTextField();
			tfExgSeqNum.setBounds(new Rectangle(277, 10, 305, 25));
		}
		return tfExgSeqNum;
	}

	/**
	 * This method initializes cbIsPrintZeroTotalUsed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsPrintZeroTotalUsed() {
		if (cbIsPrintZeroTotalUsed == null) {
			cbIsPrintZeroTotalUsed = new JCheckBox();
			cbIsPrintZeroTotalUsed.setBounds(new Rectangle(581, 11, 150, 21));
			cbIsPrintZeroTotalUsed.setText("不显示耗用为0的料件");
		}
		return cbIsPrintZeroTotalUsed;
	}

	/**
	 * This method initializes rbEffective	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbEffective() {
		if (rbEffective == null) {
			rbEffective = new JRadioButton();
			rbEffective.setBounds(new Rectangle(585, 46, 53, 21));
			rbEffective.setSelected(true);
			rbEffective.setText("生效");
		}
		return rbEffective;
	}

	/**
	 * This method initializes rbInvalid	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbInvalid() {
		if (rbInvalid == null) {
			rbInvalid = new JRadioButton();
			rbInvalid.setBounds(new Rectangle(640, 46, 67, 21));
			rbInvalid.setText("未生效");
		}
		return rbInvalid;
	}

	/**
	 * This method initializes rbAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(710, 46, 53, 21));
			rbAll.setText("全部");
		}
		return rbAll;
	}
	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbEffective());
			buttonGroup.add(getRbInvalid());
			buttonGroup.add(getRbAll());
		}
		return buttonGroup;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
