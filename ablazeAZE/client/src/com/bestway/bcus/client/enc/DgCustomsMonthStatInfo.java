package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

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
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

@SuppressWarnings("unchecked")
public class DgCustomsMonthStatInfo extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private JButton btnQuery = null;

	private JButton btnReport = null;

	private JTextField tfExgSeqNum = null;

	private ManualDeclareAction manualDeclareAction = null;

	private EncAction encAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelMonth = null;

	private JTabbedPane jTabbedPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbEmsNo1 = null;

	private JLabel jLabel23 = null;

	private JButton btnQuery1 = null;

	private JComboBox jComboBox = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JComboBox jComboBox1 = null;

	private JButton btnQuery11 = null;

	private List listTerm = new ArrayList();

	private JPanel jPanel2 = null;

	private JLabel jLabel231 = null;

	private JLabel jLabel232 = null;

	private JComboBox cbbBeginTerm = null;

	private JComboBox cbbEndTerm = null;

	private JPanel jPanel3 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomsMonthStatInfo() {
		super();
		initialize();
		jTable1 = new MultiSpanCellTable();
		jScrollPane1.setViewportView(this.getJTable1());
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
		this.setContentPane(getJContentPane());
		this.setTitle("进口料件耗用月报表");

		this.setSize(new Dimension(811, 494));
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

		cbbEmsNo1.removeAllItems();
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmsNo1.addItem((EmsHeadH2k) contracts.get(i));
			}
			this.cbbEmsNo1.setRenderer(CustomBaseRender.getInstance()
					.getRender("preEmsNo", "emsNo", 0, 150));
		}
		if (this.cbbEmsNo1.getItemCount() > 0) {
			this.cbbEmsNo1.setSelectedIndex(0);
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
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
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(220, 44, 51, 25));
			jLabel21.setText("月结期间");
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
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnReport(), null);
			jPanel.add(getTfExgSeqNum(), null);
			jPanel.add(getJComboBox(), null);
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
			cbbEmsNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						jComboBox.removeAllItems();
						EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
								.getSelectedItem();
						if (emsHeadH2k == null) {
							return;
						}
						String initTerm = encAction.findInitCustomsMonthTerm(
								new Request(CommonVars.getCurrUser(), true),
								emsHeadH2k);
						if (initTerm == null || "".equals(initTerm.trim())) {
							return;
						}
						int year = Integer.parseInt(initTerm.substring(0, 4));
						int month = Integer.parseInt(initTerm.substring(5));
						int diff = 2020 - year;

						for (int i = 0; i <= diff; i++) {
							for (int j = month; j <= 12; j++) {
								String smonth = String.valueOf(j);
								if (smonth.length() == 1) {
									smonth = ("0" + smonth);
								}
								jComboBox.addItem(year + i + "/" + smonth);
							}
							month = 1;
						}
						listTerm = encAction.findCustomsMonthStatTerm(
								new Request(CommonVars.getCurrUser(), true),
								emsHeadH2k.getEmsNo());
						jComboBox.setRenderer(new DefaultListCellRenderer() {

							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
							public Component getListCellRendererComponent(
									JList list, Object value, int index,
									boolean isSelected, boolean cellHasFocus) {
								Component comp = super
										.getListCellRendererComponent(list,
												value, index, isSelected,
												cellHasFocus);
								if (listTerm.contains(value)) {
									jComboBox.setForeground(Color.RED);
									comp.setForeground(Color.RED);
								} else {
									comp.setForeground(Color.BLACK);
									jComboBox.setForeground(Color.BLACK);
								}
								return comp;
							}

						});
					}
				}
			});
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
			btnQuery.setBounds(new Rectangle(603, 10, 75, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
							.getSelectedItem();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择账册编号");
						return;
					}
					Integer impExpType = null;
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = Integer
								.parseInt(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode().trim());
					}
					if (jComboBox.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择月结期间");
						return;
					}
					String monthTerm = jComboBox.getSelectedItem().toString();
					// Date beginDate = cbbBeginDate.getDate();
					// Date endDate = cbbEndDate.getDate();
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
					List lsResult = encAction.findExgUsedImgMonthAmountInfo(
							new Request(CommonVars.getCurrUser(), true),
							emsHeadH2k, exgSeqNums, monthTerm, impExpType);
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
						list.add(addColumn("料件单价", "unitPrice", 100));
						list.add(addColumn("单耗用量", "unitUsed", 100));
						list.add(addColumn("损耗用量", "waste", 100));
						list.add(addColumn("耗用料件总量", "totalUsed", 100));
						list.add(addColumn("耗用金额", "totalMoney", 100));
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

		List<JTableListColumn> cm = tableModel.getColumns();
		cm.get(6).setFractionCount(decimalLength);
		cm.get(11).setFractionCount(decimalLength);
		cm.get(12).setFractionCount(decimalLength);
		cm.get(13).setFractionCount(decimalLength);
		cm.get(14).setFractionCount(decimalLength);
		cm.get(15).setFractionCount(decimalLength);

		CommonVars.castMultiplicationValue(jTable, 15, 14, 11,decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	private void initMonthTable(List list) {
		tableModelMonth = new AttributiveCellTableModel(
				(MultiSpanCellTable) this.getJTable1(), list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("帐册号", "emsNo", 100));
						list.add(addColumn("期间", "statTerm", 100));
						list
								.add(addColumn("料件序号", "seqNum", 100,
										Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("名称规格", "nameSpec", 100));
						list.add(addColumn("单位", "unit.name", 100));

						list.add(addColumn("余料数量", "preAmount", 100));
						list.add(addColumn("余料金额", "preMoney", 100));

						list.add(addColumn("进料数量", "currentImpAmount", 100));
						list.add(addColumn("进料金额", "currentImpMoney", 100));
						list.add(addColumn("料件耗用量", "currentUsedAmount", 100));
						list.add(addColumn("料件耗用金额", "currentUsedTotalMoney", 100));


						list.add(addColumn("结余数量", "endAmount", 100));
						list.add(addColumn("单价", "averageUnitPrice", 100));
						list.add(addColumn("结余金额", "endMoney", 100));
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

		//List<JTableListColumn> cms = tableModel.getColumns();
		List<JTableListColumn> cms = tableModelMonth.getColumns();
		cms.get(7).setFractionCount(decimalLength);
		cms.get(8).setFractionCount(decimalLength);
		cms.get(9).setFractionCount(decimalLength);
		cms.get(10).setFractionCount(decimalLength);
		cms.get(11).setFractionCount(decimalLength);
		cms.get(12).setFractionCount(decimalLength);
		cms.get(13).setFractionCount(decimalLength);
		cms.get(14).setFractionCount(decimalLength);
		cms.get(15).setFractionCount(decimalLength);
		
		/*CommonVars.castMultiplicationValue(jTable, 8, 13, 7,decimalLength);
		CommonVars.castMultiplicationValue(jTable, 11, 13, 9,decimalLength);
		CommonVars.castMultiplicationValue(jTable, 14, 13, 12,decimalLength);*/
		
		CommonVars.castMultiplicationValue(jTable1, 8, 14, 7,decimalLength);
		CommonVars.castMultiplicationValue(jTable1, 10, 14, 9,decimalLength);
		CommonVars.castMultiplicationValue(jTable1, 12, 14, 11,decimalLength);
		CommonVars.castMultiplicationValue(jTable1, 15, 14, 13,decimalLength);
		

		TableColumnModel cm = jTable1.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("上期结余");
		// gBefore.add(cm.getColumn(0));
		gBefore.add(cm.getColumn(7));
		gBefore.add(cm.getColumn(8));

		//		
		//
		ColumnGroup gAfter = new ColumnGroup("本期进料");
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));
		gAfter.add(cm.getColumn(12));

		//		
		//
		ColumnGroup gEnd = new ColumnGroup("本期结余");
		gEnd.add(cm.getColumn(13));
		gEnd.add(cm.getColumn(14));
		gEnd.add(cm.getColumn(15));

		GroupableTableHeader header = (GroupableTableHeader) jTable1
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		header.addColumnGroup(gEnd);
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReport() {
		if (btnReport == null) {
			btnReport = new JButton();
			btnReport.setBounds(new Rectangle(603, 44, 75, 25));
			btnReport.setText("打印");
			btnReport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List dlist = tableModel.getList();
					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					InputStream reportStream = DgCustomsMonthStatInfo.class
							.getResourceAsStream("report/ExgUsedImgMonthAmountInfo.jasper");
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("进口料件耗用月报表", null, getJSplitPane(), null);
			jTabbedPane.addTab("帐册料件月结", null, getJSplitPane1(), null);
		}
		return jTabbedPane;
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
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setTopComponent(getJPanel1());
			jSplitPane1.setBottomComponent(getJScrollPane1());
			jSplitPane1.setDividerLocation(80);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel23 = new JLabel();
			jLabel23.setText("月结期间");
			jLabel23.setBounds(new Rectangle(6, 4, 51, 25));
			jLabel3 = new JLabel();
			jLabel3.setText("\u8d26\u518c\u7f16\u53f7");
			jLabel3.setBounds(new Rectangle(7, 4, 50, 25));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getJPanel3(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbEmsNo1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo1() {
		if (cbbEmsNo1 == null) {
			cbbEmsNo1 = new JComboBox();
			cbbEmsNo1.setBounds(new Rectangle(64, 4, 136, 25));
			cbbEmsNo1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						jComboBox1.removeAllItems();
						cbbBeginTerm.removeAllItems();
						EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
								.getSelectedItem();
						if (emsHeadH2k == null) {
							return;
						}
						String initTerm = encAction.findInitCustomsMonthTerm(
								new Request(CommonVars.getCurrUser(), true),
								emsHeadH2k);
						if (initTerm == null || "".equals(initTerm.trim())) {
							return;
						}
						int year = Integer.parseInt(initTerm.substring(0, 4));
						int month = Integer.parseInt(initTerm.substring(5));
						int diff = 2020 - year;

						for (int i = 0; i <= diff; i++) {
							for (int j = month; j <= 12; j++) {
								String smonth = String.valueOf(j);
								if (smonth.length() == 1) {
									smonth = ("0" + smonth);
								}
								jComboBox1.addItem(year + i + "/" + smonth);
								cbbBeginTerm.addItem(year + i + "/" + smonth);
							}
							month = 1;
						}

						listTerm = encAction.findCustomsMonthStatTerm(
								new Request(CommonVars.getCurrUser(), true),
								emsHeadH2k.getEmsNo());

						jComboBox1.setRenderer(new DefaultListCellRenderer() {

							@Override
							public Component getListCellRendererComponent(
									JList list, Object value, int index,
									boolean isSelected, boolean cellHasFocus) {
								Component comp = super
										.getListCellRendererComponent(list,
												value, index, isSelected,
												cellHasFocus);
								if (listTerm.contains(value)) {
									jComboBox1.setForeground(Color.RED);
									comp.setForeground(Color.RED);
								} else {
									jComboBox1.setForeground(Color.BLACK);
									comp.setForeground(Color.BLACK);
								}
								return comp;
							}

						});

						cbbBeginTerm.setRenderer(new DefaultListCellRenderer() {

							@Override
							public Component getListCellRendererComponent(
									JList list, Object value, int index,
									boolean isSelected, boolean cellHasFocus) {
								Component comp = super
										.getListCellRendererComponent(list,
												value, index, isSelected,
												cellHasFocus);
								if (listTerm.contains(value)) {
									cbbBeginTerm.setForeground(Color.RED);
									comp.setForeground(Color.RED);
								} else {
									cbbBeginTerm.setForeground(Color.BLACK);
									comp.setForeground(Color.BLACK);
								}
								return comp;
							}

						});
					}
				}
			});
		}
		return cbbEmsNo1;
	}

	/**
	 * This method initializes btnQuery1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery1() {
		if (btnQuery1 == null) {
			btnQuery1 = new JButton();
			btnQuery1.setText("月结");
			btnQuery1.setBounds(new Rectangle(580, 4, 75, 25));
			btnQuery1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
							.getSelectedItem();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择账册编号");
						return;
					}
					if (cbbBeginTerm.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择开始月结期间");
						return;
					}
					if (cbbEndTerm.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择结束月结期间");
						return;
					}
					int index = cbbBeginTerm.getSelectedIndex();
					String preTerm = null;
					String beginTerm = cbbBeginTerm.getItemAt(index).toString();
					if (listTerm.contains(beginTerm)) {
						if (JOptionPane.showConfirmDialog(
								DgCustomsMonthStatInfo.this, "帐册"
										+ emsHeadH2k.getEmsNo() + "在期间"
										+ beginTerm + "已做月结，是否重新进行月结？", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					if (index > 0) {
						preTerm = cbbBeginTerm.getItemAt(index - 1).toString();
					}
					String endTerm = cbbEndTerm.getSelectedItem().toString();
					encAction.statCustomsInfoByMonth(new Request(CommonVars
							.getCurrUser(), true), emsHeadH2k, preTerm,
							beginTerm, endTerm);
					List list = encAction.findCustomsMonthStatInfoByTerm(
							new Request(CommonVars.getCurrUser(), true),
							emsHeadH2k.getEmsNo(), beginTerm);
					initMonthTable(list);

					listTerm = encAction.findCustomsMonthStatTerm(new Request(
							CommonVars.getCurrUser(), true), emsHeadH2k
							.getEmsNo());
					jComboBox1.setSelectedIndex(index);
					JOptionPane.showMessageDialog(DgCustomsMonthStatInfo.this,
							"月结完成！");
				}
			});
		}
		return btnQuery1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(280, 44, 302, 25));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(64, 4, 511, 26));
			jComboBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// Auto-generated
					// Event stub
					// itemStateChanged()
				}
			});
		}
		return jComboBox1;
	}

	/**
	 * This method initializes btnQuery11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery11() {
		if (btnQuery11 == null) {
			btnQuery11 = new JButton();
			btnQuery11.setText("查询");
			btnQuery11.setBounds(new Rectangle(581, 4, 75, 25));
			btnQuery11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) cbbEmsNo
							.getSelectedItem();
					if (emsHeadH2k == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择账册编号");
						return;
					}
					if (jComboBox1.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsMonthStatInfo.this, "请选择月结期间");
						return;
					}
					int index = jComboBox1.getSelectedIndex();
					String monthTerm = jComboBox1.getItemAt(index).toString();
					List list = encAction.findCustomsMonthStatInfoByTerm(
							new Request(CommonVars.getCurrUser(), true),
							emsHeadH2k.getEmsNo(), monthTerm);
					initMonthTable(list);
				}
			});
		}
		return btnQuery11;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel232 = new JLabel();
			jLabel232.setBounds(new Rectangle(406, 4, 48, 25));
			jLabel232.setText("结束期间");
			jLabel231 = new JLabel();
			jLabel231.setBounds(new Rectangle(206, 4, 48, 24));
			jLabel231.setText("开始期间");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(10, 5, 662, 33));
			jPanel2.setBorder(BorderFactory.createLineBorder(
					SystemColor.activeCaption, 1));
			jPanel2.add(jLabel3, null);
			jPanel2.add(getCbbEmsNo1(), null);
			jPanel2.add(getBtnQuery1(), null);
			jPanel2.add(jLabel231, null);
			jPanel2.add(jLabel232, null);
			jPanel2.add(getCbbBeginTerm(), null);
			jPanel2.add(getCbbEndTerm(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBeginTerm() {
		if (cbbBeginTerm == null) {
			cbbBeginTerm = new JComboBox();
			cbbBeginTerm.setBounds(new Rectangle(254, 3, 146, 27));
			cbbBeginTerm.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						cbbEndTerm.removeAllItems();
						String beginTerm = cbbBeginTerm.getSelectedItem()
								.toString();
						int year = Integer.parseInt(beginTerm.substring(0, 4));
						int month = Integer.parseInt(beginTerm.substring(5));
						int diff = 2020 - year;

						for (int i = 0; i <= diff; i++) {
							for (int j = month; j <= 12; j++) {
								String smonth = String.valueOf(j);
								if (smonth.length() == 1) {
									smonth = ("0" + smonth);
								}
								cbbEndTerm.addItem(year + i + "/" + smonth);
							}
							month = 1;
						}
						cbbEndTerm.setRenderer(new DefaultListCellRenderer() {

							@Override
							public Component getListCellRendererComponent(
									JList list, Object value, int index,
									boolean isSelected, boolean cellHasFocus) {
								Component comp = super
										.getListCellRendererComponent(list,
												value, index, isSelected,
												cellHasFocus);
								if (listTerm.contains(value)) {
									cbbEndTerm.setForeground(Color.RED);
									comp.setForeground(Color.RED);
								} else {
									cbbEndTerm.setForeground(Color.BLACK);
									comp.setForeground(Color.BLACK);
								}
								return comp;
							}

						});
					}
				}
			});
		}
		return cbbBeginTerm;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEndTerm() {
		if (cbbEndTerm == null) {
			cbbEndTerm = new JComboBox();
			cbbEndTerm.setBounds(new Rectangle(455, 4, 120, 26));
		}
		return cbbEndTerm;
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
			jPanel3.setBounds(new Rectangle(10, 42, 662, 32));
			jPanel3.setBorder(BorderFactory.createLineBorder(
					SystemColor.activeCaption, 1));
			jPanel3.add(jLabel23, null);
			jPanel3.add(getJComboBox1(), null);
			jPanel3.add(getBtnQuery11(), null);
		}
		return jPanel3;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
