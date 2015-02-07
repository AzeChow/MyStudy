package com.bestway.common.client.aptitudereport;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import java.awt.Dimension;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.aptitudereport.action.AptitudeReportAction;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmReportQueryResultShow extends JInternalFrameBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnClose = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private SelectCondition selectCondition = null; // @jve:decl-index=0:

	private List<GroupValue> groupByList = null; // @jve:decl-index=0:

	private List<BillDetail> reportBillDetailList = null; // @jve:decl-index=0:

	private List<BaseCustomsDeclarationCommInfo> reportCustomsList = null; // @jve:decl-index=0:

	private List<AtcMergeAfterComInfo> reportAtcMergeList = null; // @jve:decl-index=0:

	private List<DzscBillListBeforeCommInfo> reportDzscBillList = null;

	private List<ReportField> reportFieldList = null;

	private Boolean isAdd = true;

	private JTableListModel tableModelReport = null;

	private String reportTilte = null;

	private String reportPath = null;

	private String className = null;

	private AptitudeReportAction aptitudeReportAction = null;

	private JButton btnPrint = null;

	/**
	 * This is the default constructor
	 */
	public FmReportQueryResultShow(SelectCondition selectCondition,
			List reportBillDetailList, List reportCustomsList,
			List reportAtcMergeList, List reportDzscBillList,
			List reportFieldList) {
		super();
		this.selectCondition = selectCondition;
		this.reportBillDetailList = reportBillDetailList;
		this.reportCustomsList = reportCustomsList;
		this.reportAtcMergeList = reportAtcMergeList;
		this.reportDzscBillList = reportDzscBillList;
		this.reportFieldList = reportFieldList;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setPreferredSize(new Dimension(734, 552));
		this.setContentPane(getJContentPane());
		this.setTitle("报表查询结果");
		String reportName = selectCondition.getReportName().toString()
				+ "报表查询结果";
		this.setReportTilte(reportName);
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
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(306, 34));
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
		}
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				FmReportQueryResultShow.this.dispose();
			}
		});
		return btnClose;
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

			if (reportBillDetailList != null && reportBillDetailList.size() > 0) {

				initTable(reportBillDetailList);
			}
			if (reportCustomsList != null && reportCustomsList.size() > 0) {

				initTable(reportCustomsList);
			}
			if (reportAtcMergeList != null && reportAtcMergeList.size() > 0) {

				initTable(reportAtcMergeList);
			}
			if (reportDzscBillList != null && reportDzscBillList.size() > 0) {

				initTable(reportDzscBillList);
			}
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

	public void setSelectCondition(SelectCondition selectCondition) {

		this.selectCondition = selectCondition;

	}

	public SelectCondition getSelectCondition(SelectCondition selectCondition) {
		return selectCondition;
	}

	public void setGroupValueList(List<GroupValue> groupByList) {

		this.groupByList = groupByList;

	}

	public List<GroupValue> getGroupValueList() {
		return groupByList;
	}

	public void setReportBillDetailList(List reportBillDetailList) {
		this.reportBillDetailList = reportBillDetailList;
	}

	public List getReportBillDetailList() {
		return reportCustomsList;
	}

	public void setReportCustomsList(List reportCustomsList) {
		this.reportCustomsList = reportCustomsList;
	}

	public List getReportCustomsList() {
		return reportCustomsList;
	}

	public void setReportAtcMergeList(List reportAtcMergeList) {
		this.reportAtcMergeList = reportAtcMergeList;
	}

	public List getReportAtcMergeList() {
		return reportAtcMergeList;
	}

	public void setReportFieldList(List<ReportField> reportFieldList) {
		this.reportFieldList = reportFieldList;
	}

	public List<ReportField> getReportFieldList() {
		return reportFieldList;
	}

	public void setReportDzscBillList(List reportDzscBillList) {
		this.reportDzscBillList = reportDzscBillList;
	}

	public List getReportDzscBillList() {
		return reportDzscBillList;
	}

	public void setAdd(boolean isAdd) {
		// 
		this.isAdd = isAdd;

	}

	private void initTable(final List list) {
		tableModelReport = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						for (int i = 0; i < reportFieldList.size(); i++) {
							list.add(addColumn(reportFieldList.get(i)
									.getCnName(), reportFieldList.get(i)
									.getEnName(), 80));
						}
						return list;

					}
				});
	}

	public String getReportTilte() {
		return reportTilte;
	}

	/**
	 * @param reportTilte
	 *            The reportTilte to set.
	 */
	public void setReportTilte(String reportTilte) {
		this.reportTilte = reportTilte;
	}

	public List<TempReport> getReportLst(List reportList) {
		List<TempReport> newList = new ArrayList<TempReport>();
		for (int i = 0; i < reportList.size(); i++) {
			TempReport tempReport = new TempReport();
			tempReport.setGroupingColumn((String) tableModelReport.getValueAt(
					i, 1));
			tempReport
					.setStatColumn((Double) tableModelReport.getValueAt(i, 2));
			newList.add(tempReport);
		}
		return newList;
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
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}
					List resultList = tableModelReport.getList();
					CommonDataSource reportDS = new CommonDataSource(resultList);
					String cnFieldNameAll = getStaticText(reportFieldList);
					String fieldDetailAll = getReportDetail(reportFieldList);
					String lineToFieldAll = getDrawLineToField(reportFieldList);
					String lineToDetailAll = getDrawLineToDetail(reportFieldList);
					String reportDetailFieldAll = getInitReportDetailField(reportFieldList);
					String pageSize = getPageSize(reportFieldList);
					String initParameter = getInitParameter(reportFieldList);
					String filename = "com/bestway/common/client/aptitudereport/report.jrxmltemp";
					BufferedReader br = null;
					try {
						ClassLoader ccl = Thread.currentThread().getContextClassLoader();
						br = new BufferedReader(new InputStreamReader(ccl
								.getResourceAsStream(filename), "UTF-8"));
						StringBuffer stringBuffer = new StringBuffer(1000);
						String s = new String();
						while ((s = br.readLine()) != null) {
							if (s.trim().endsWith("[FLAG_INITPARAMETER]")) {
								stringBuffer.append(initParameter + "\n");

							} else if (s.trim().endsWith("[FLAG_PAGESIZE]")) {
								stringBuffer.append(pageSize + "\n");
							} else if (s.trim().endsWith(
									"[FLAG_REPORTDETAILFIELD]")) {
								stringBuffer
										.append(reportDetailFieldAll + "\n");

							} else if (s.trim().endsWith("[FLAG_FIELDNAME]")) {
								stringBuffer.append(cnFieldNameAll + "\n");

							} else if (s.trim().endsWith(
									"[FLAG_DRAWLINEFILELD]")) {
								stringBuffer.append(lineToFieldAll + "\n");

							} else if (s.trim().endsWith("[FLAG_FIELDDETAIL]")) {
								stringBuffer.append(fieldDetailAll + "\n");

							} else if (s.trim().endsWith(
									"[FLAG_DRAWLINEDETAIL]")) {
								stringBuffer.append(lineToDetailAll + "\n");

							} else {
								stringBuffer.append(s + "\n");
							}

						}
						br.close();

						ByteArrayInputStream byteArrayInpterStream = new ByteArrayInputStream(
								stringBuffer.toString().getBytes("UTF-8"));
						try {
							JasperReport jasperReport = JasperCompileManager
									.compileReport(byteArrayInpterStream);

							Map<String, Object> parameters = new HashMap<String, Object>();

							for (int i = 0; i < reportFieldList.size(); i++) {
								String cnName = "cnName";
								cnName = cnName + i;
								parameters.put(cnName, reportFieldList.get(i)
										.getCnName());
							}
							parameters.put("reportTitle", selectCondition
									.getReportName());
							parameters.put("aimObject", selectCondition
									.getAimObjectValue());

							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									jasperReport, parameters, reportDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}

					} catch (IOException e1) {

						e1.printStackTrace();
						try {
							if (br != null) {
								br.close();
							}
						} catch (Exception ex) {

						}
					}

				}
			});

		}
		return btnPrint;
	}

	private boolean validateData() {
		int count = this.reportFieldList.size();
		if(count>20){
			JOptionPane.showMessageDialog(this, "报表列数超过20列,A4的纸张无法完整打印！"+"\n"+"请将报表列数控制在20列以内再打印!!", "提示！", 0);
			return true;
			
		}

		return false;
	}
	
	
	// 报表打印功能部分

	private String getPageSize(List<ReportField> reportFieldList) {
		String pageSize = "";
		int pageWidth = 0;
		int pageHeight = 0;
		int columnWidth = 0;
		char comma = '"';
		if (reportFieldList.size() > 20) {
			pageWidth = 1190;
			pageHeight = 595;
			columnWidth = 1130;

		} else if (reportFieldList.size() > 10) {
			pageWidth = 842;
			pageHeight = 595;
			columnWidth = 782;
		} else {
			pageWidth = 595;
			pageHeight = 842;
			columnWidth = 535;
		}
		pageSize = "         pageWidth=" + comma + pageWidth + comma + "\n"
				+ "          pageHeight=" + comma + pageHeight + comma + "\n"
				+ "          columnWidth=" + comma + columnWidth + comma + "\n";
		return pageSize;

	}

	private String getInitParameter(List<ReportField> reportFieldList) {
		char comma = '"';
		String parametercnName = "";
		String fieldName = "";
		String fieldNameClass = "";
		for (int i = 0; i < reportFieldList.size(); i++) {
			fieldName = "cnName" + i;
			fieldNameClass = reportFieldList.get(i).getClassname();
			parametercnName = parametercnName + "<parameter name=" + comma
					+ fieldName + comma + " isForPrompting=" + comma + "true"
					+ comma + " class=" + comma + "java.lang.String" + comma
					+ "/>" + "\n";

		}
		return parametercnName;

	}

	// 参数初始化
	private String getInitReportDetailField(List<ReportField> reportFieldList) {
		String reportDetailField = "";
		String fieldName = "";
		String fieldNameClass = "";
		char comma = '"';
		for (int i = 0; i < reportFieldList.size(); i++) {
			fieldName = reportFieldList.get(i).getEnName();
			fieldNameClass = reportFieldList.get(i).getClassname();
			reportDetailField = reportDetailField + "<field name=" + comma
					+ fieldName + comma + " class=" + comma
					+ "java.lang.String" + comma + ">" + "\n"
					+ "     <fieldDescription><![CDATA[" + fieldName
					+ "]]></fieldDescription>\n" + "</field>\n";

		}
		return reportDetailField;

	}

	// 得到报表中文列名（jrxml语句）
	private String getStaticText(List<ReportField> reportFieldList) {
		String cnFieldName = "";
		String fieldName = "";
		char comma = '"';
		int coordinateByX = 0;
		int coordinateByY = 0;
		int width = 0;
		int height = 22;
		int key = 1;

		if (reportFieldList.size() > 20) {
			width = 1130 / reportFieldList.size();
		} else if (reportFieldList.size() > 10) {
			width = 782 / reportFieldList.size();
		} else {
			width = 65;
		}
		for (int i = 0; i < reportFieldList.size(); i++) {
			key++;

			if (reportFieldList.size() > 20) {
				coordinateByX = i * 1130 / reportFieldList.size();
			} else if (reportFieldList.size() > 10) {
				coordinateByX = i * 782 / reportFieldList.size();
			} else {
				coordinateByX = 65 * i;
			}

			coordinateByY = 78;
			// fieldName=reportFieldList.get(i).getCnName().trim();
			fieldName = "cnName" + i;

			cnFieldName = cnFieldName + "<textField isStretchWithOverflow="
					+ comma + "true" + comma + " pattern=" + comma + "" + comma
					+ " isBlankWhenNull=" + comma + "false" + comma
					+ " evaluationTime=" + comma + "Now" + comma
					+ " hyperlinkType=" + comma + "None" + comma
					+ "  hyperlinkTarget=" + comma + "Self" + comma + " >"
					+ "\n" + "<reportElement" + "\n" + "mode=" + comma
					+ "Opaque" + comma + "\n" + "x=" + comma + coordinateByX
					+ comma + "\n" + "y=" + comma + coordinateByY + comma
					+ "\n" + "width=" + comma + width + comma + "\n"
					+ "height=" + comma + height / 2 + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "textField-" + key + comma + "\n"
					+ "stretchType=" + comma + "NoStretch" + comma + "\n"
					+ "positionType=" + comma + "FixRelativeToTop" + comma
					+ "\n" + "isPrintRepeatedValues=" + comma + "true" + comma
					+ "\n" + "isRemoveLineWhenBlank=" + comma + "false" + comma
					+ "\n" + "isPrintInFirstWholeBand=" + comma + "false"
					+ comma + "\n" + "isPrintWhenDetailOverflows=" + comma
					+ "false" + comma + "/>" + "\n" + "<box topBorder=" + comma
					+ "None" + comma + " topBorderColor=" + comma + "#000000"
					+ comma + " leftBorder=" + comma + "None" + comma
					+ " leftBorderColor=" + comma + "#000000" + comma
					+ " rightBorder=" + comma + "None" + comma
					+ " rightBorderColor=" + comma + "#000000" + comma
					+ " bottomBorder=" + comma + "None" + comma
					+ " bottomBorderColor=" + comma + "#000000" + comma + "/>"
					+ "\n" + "<textElement textAlignment=" + comma + "Center"
					+ comma + " verticalAlignment=" + comma + "Top" + comma
					+ " rotation=" + comma + "None" + comma + " lineSpacing="
					+ comma + "Single" + comma + ">" + "\n" + "<font fontName="
					+ comma + "宋体" + comma + " pdfFontName=" + comma
					+ "Helvetica" + comma + " size=" + comma + "10" + comma
					+ " isBold=" + comma + "false" + comma + " isItalic="
					+ comma + "false" + comma + " isUnderline=" + comma
					+ "false" + comma + " isPdfEmbedded =" + comma + "false"
					+ comma + " pdfEncoding =" + comma + "Cp1252" + comma
					+ " isStrikeThrough=" + comma + "false" + comma + " />"
					+ "\n" + "</textElement>" + "\n"
					+ "<textFieldExpression   class=" + comma
					+ "java.lang.String" + comma + ">" + "<![CDATA[$P{"
					+ fieldName + "}]]></textFieldExpression>" + "\n"
					+ "</textField>" + "\n";

		}

		return cnFieldName;
	}

	// 得到报表明细（jrxml语句）
	private String getReportDetail(List<ReportField> reportFieldList) {

		String fieldDetail = "";
		String fieldName = "";
		String fieldNameClass = "";
		char comma = '"';
		int coordinateByX = 0;
		int coordinateByY = 0;
		int width = 0;
		int height = 22;
		int key = 3;
		if (reportFieldList.size() > 20) {
			width = 1130 / reportFieldList.size();
		} else if (reportFieldList.size() > 10) {
			width = 782 / reportFieldList.size();
		} else {
			width = 65;
		}
		for (int i = 0; i < reportFieldList.size(); i++) {
			key++;
			if (reportFieldList.size() > 20) {
				coordinateByX = i * 1130 / reportFieldList.size();
			} else if (reportFieldList.size() > 10) {
				coordinateByX = i * 782 / reportFieldList.size();
			} else {
				coordinateByX = 65 * i;
			}
			fieldName = reportFieldList.get(i).getEnName();
			fieldNameClass = reportFieldList.get(i).getClassname();
			// String fieldNameDescription = fieldName.replace(".", "");
			fieldDetail = fieldDetail + "<textField isStretchWithOverflow="
					+ comma + "true" + comma + " pattern=" + comma + "" + comma
					+ " isBlankWhenNull=" + comma + "false" + comma
					+ " evaluationTime=" + comma + "Now" + comma
					+ " hyperlinkType=" + comma + "None" + comma
					+ "  hyperlinkTarget=" + comma + "Self" + comma + " >"
					+ "\n" + "<reportElement" + "\n" + "mode=" + comma
					+ "Opaque" + comma + "\n" + "x=" + comma + coordinateByX
					+ comma + "\n" + "y=" + comma + coordinateByY + comma
					+ "\n" + "width=" + comma + width + comma + "\n"
					+ "height=" + comma + height / 2 + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "textField-" + key + comma + "\n"
					+ "stretchType=" + comma + "NoStretch" + comma + "\n"
					+ "positionType=" + comma + "FixRelativeToTop" + comma
					+ "\n" + "isPrintRepeatedValues=" + comma + "true" + comma
					+ "\n" + "isRemoveLineWhenBlank=" + comma + "false" + comma
					+ "\n" + "isPrintInFirstWholeBand=" + comma + "false"
					+ comma + "\n" + "isPrintWhenDetailOverflows=" + comma
					+ "false" + comma + "/>" + "\n" + "<box topBorder=" + comma
					+ "None" + comma + " topBorderColor=" + comma + "#000000"
					+ comma + " leftBorder=" + comma + "None" + comma
					+ " leftBorderColor=" + comma + "#000000" + comma
					+ " rightBorder=" + comma + "None" + comma
					+ " rightBorderColor=" + comma + "#000000" + comma
					+ " bottomBorder=" + comma + "None" + comma
					+ " bottomBorderColor=" + comma + "#000000" + comma + "/>"
					+ "\n" + "<textElement textAlignment=" + comma + "Left"
					+ comma + " verticalAlignment=" + comma + "Top" + comma
					+ " rotation=" + comma + "None" + comma + " lineSpacing="
					+ comma + "Single" + comma + ">" + "\n" + "<font fontName="
					+ comma + "宋体" + comma + " pdfFontName=" + comma
					+ "Helvetica" + comma + " size=" + comma + "8" + comma
					+ " isBold=" + comma + "false" + comma + " isItalic="
					+ comma + "false" + comma + " isUnderline=" + comma
					+ "false" + comma + " isPdfEmbedded =" + comma + "false"
					+ comma + " pdfEncoding =" + comma + "Cp1252" + comma
					+ " isStrikeThrough=" + comma + "false" + comma + " />"
					+ "\n" + "</textElement>" + "\n"
					+ "<textFieldExpression   class=" + comma
					+ "java.lang.String" + comma + ">" + "<![CDATA[$F{"
					+ fieldName + "}]]></textFieldExpression>" + "\n"
					+ "</textField>" + "\n";

		}

		return fieldDetail;

	}

	// 得到报表列名行列边框（jrxml语句）
	private String getDrawLineToField(List<ReportField> reportFieldList) {

		String lineToField = "";
		char comma = '"';
		int coordinateByX = 0;
		int coordinateByY = 0;
		int width = 0;
		int height = 0;
		int index = 0;
		String stretchType = "";
		String positionType = "";
		if (reportFieldList.size() > 20) {
			width = 1130;
		} else if (reportFieldList.size() > 10) {
			width = 782;
		} else {
			width = 65 * reportFieldList.size();
		}
		for (int i = 0; i < 3; i++) {
			index++;
			if (i == 0) {
				stretchType = "NoStretch";
				positionType = "FixRelativeToTop";
				coordinateByX = 1;
				coordinateByY = 78;

			} else if (i == 1) {
				stretchType = "NoStretch";
				positionType = "FixRelativeToBottom";
				coordinateByX = 1;
				coordinateByY = 99;
			} else if (i == 2) {
				stretchType = "RelativeToBandHeight";
				positionType = "FixRelativeToTop";
				coordinateByY = 78;
				height = 22;
				width = 0;
			}
			lineToField = lineToField + "<line direction=" + comma + "TopDown"
					+ comma + ">" + "\n" + "<reportElement" + "\n" + "mode="
					+ comma + "Opaque" + comma + "\n" + "x=" + comma
					+ coordinateByX + comma + "\n" + "y=" + comma
					+ coordinateByY + comma + "\n" + "width=" + comma + width
					+ comma + "\n" + "height=" + comma + height + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "line-" + index + comma + "\n" + "stretchType="
					+ comma + stretchType + comma + "\n" + "positionType="
					+ comma + positionType + comma + "\n"
					+ "isPrintRepeatedValues=" + comma + "true" + comma + "\n"
					+ "isRemoveLineWhenBlank=" + comma + "false" + comma + "\n"
					+ "isPrintInFirstWholeBand=" + comma + "false" + comma
					+ "\n" + "isPrintWhenDetailOverflows=" + comma + "false"
					+ comma + "/>" + "\n" + "<graphicElement stretchType="
					+ comma + stretchType + comma + " pen=" + comma + "Thin"
					+ comma + " fill=" + comma + "Solid" + comma + " />" + "\n"
					+ "</line>" + "\n";
		}
		index = 3;
		for (int i = 0; i < reportFieldList.size(); i++) {
			stretchType = "RelativeToBandHeight";
			positionType = "FixRelativeToTop";
			index++;
			if (reportFieldList.size() > 20) {
				coordinateByX = (i + 1) * 1130 / reportFieldList.size();
			} else if (reportFieldList.size() > 10) {
				coordinateByX = (i + 1) * 782 / reportFieldList.size();
			} else {
				coordinateByX = 65 * (i + 1);
			}

			coordinateByY = 78;
			height = 22;
			width = 0;
			lineToField = lineToField + "<line direction=" + comma + "TopDown"
					+ comma + ">" + "\n" + "<reportElement" + "\n" + "mode="
					+ comma + "Opaque" + comma + "\n" + "x=" + comma
					+ coordinateByX + comma + "\n" + "y=" + comma
					+ coordinateByY + comma + "\n" + "width=" + comma + width
					+ comma + "\n" + "height=" + comma + height + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "line-" + index + comma + "\n" + "stretchType="
					+ comma + stretchType + comma + "\n" + "positionType="
					+ comma + positionType + comma + "\n"
					+ "isPrintRepeatedValues=" + comma + "true" + comma + "\n"
					+ "isRemoveLineWhenBlank=" + comma + "false" + comma + "\n"
					+ "isPrintInFirstWholeBand=" + comma + "false" + comma
					+ "\n" + "isPrintWhenDetailOverflows=" + comma + "false"
					+ comma + "/>" + "\n" + "<graphicElement stretchType="
					+ comma + stretchType + comma + " pen=" + comma + "Thin"
					+ comma + " fill=" + comma + "Solid" + comma + " />" + "\n"
					+ "</line>" + "\n";
		}

		return lineToField;

	}

	// 得到报表明细行列边框（jrxml语句）

	private String getDrawLineToDetail(List<ReportField> reportFieldList) {
		String lineToDetail = "";
		char comma = '"';
		int coordinateByX = 0;
		int coordinateByY = 0;
		int width = 0;
		int height = 0;
		int index = reportFieldList.size() + 3;
		String stretchType = "";
		String positionType = "";
		if (reportFieldList.size() > 20) {
			width = 1130;
		} else if (reportFieldList.size() > 10) {
			width = 782;
		} else {
			width = 65 * reportFieldList.size();
		}
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				stretchType = "NoStretch";
				positionType = "FixRelativeToBottom";
				coordinateByX = 1;
				coordinateByY = 22;
			} else if (i == 1) {
				stretchType = "RelativeToBandHeight";
				positionType = "FixRelativeToTop";
				coordinateByX = 1;
				coordinateByY = -1;
				height = 23;
				width = 0;
			}
			index++;
			lineToDetail = lineToDetail + "<line direction=" + comma
					+ "TopDown" + comma + ">" + "\n" + "<reportElement" + "\n"
					+ "mode=" + comma + "Opaque" + comma + "\n" + "x=" + comma
					+ coordinateByX + comma + "\n" + "y=" + comma
					+ coordinateByY + comma + "\n" + "width=" + comma + width
					+ comma + "\n" + "height=" + comma + height + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "line-" + i + comma + "\n" + "stretchType="
					+ comma + stretchType + comma + "\n" + "positionType="
					+ comma + positionType + comma + "\n"
					+ "isPrintRepeatedValues=" + comma + "true" + comma + "\n"
					+ "isRemoveLineWhenBlank=" + comma + "false" + comma + "\n"
					+ "isPrintInFirstWholeBand=" + comma + "false" + comma
					+ "\n" + "isPrintWhenDetailOverflows=" + comma + "false"
					+ comma + "/>" + "\n" + "<graphicElement stretchType="
					+ comma + stretchType + comma + " pen=" + comma + "Thin"
					+ comma + " fill=" + comma + "Solid" + comma + " />" + "\n"
					+ "</line>" + "\n";
		}
		index = index + 2;
		for (int i = 0; i < reportFieldList.size(); i++) {
			stretchType = "RelativeToBandHeight";
			positionType = "FixRelativeToTop";
			index++;
			if (reportFieldList.size() > 20) {
				coordinateByX = (i + 1) * 1130 / reportFieldList.size();
			} else if (reportFieldList.size() > 10) {
				coordinateByX = (i + 1) * 782 / reportFieldList.size();
			} else {
				coordinateByX = 65 * (i + 1);
			}

			height = 23;
			width = 0;
			coordinateByY = -1;
			lineToDetail = lineToDetail + "<line direction=" + comma
					+ "TopDown" + comma + ">" + "\n" + "<reportElement" + "\n"
					+ "mode=" + comma + "Opaque" + comma + "\n" + "x=" + comma
					+ coordinateByX + comma + "\n" + "y=" + comma
					+ coordinateByY + comma + "\n" + "width=" + comma + width
					+ comma + "\n" + "height=" + comma + height + comma + "\n"
					+ "forecolor=" + comma + "#000000" + comma + "\n"
					+ "backcolor=" + comma + "#FFFFFF" + comma + "\n" + "key="
					+ comma + "line-" + index + comma + "\n" + "stretchType="
					+ comma + stretchType + comma + "\n" + "positionType="
					+ comma + positionType + comma + "\n"
					+ "isPrintRepeatedValues=" + comma + "true" + comma + "\n"
					+ "isRemoveLineWhenBlank=" + comma + "false" + comma + "\n"
					+ "isPrintInFirstWholeBand=" + comma + "false" + comma
					+ "\n" + "isPrintWhenDetailOverflows=" + comma + "false"
					+ comma + "/>" + "\n" + "<graphicElement stretchType="
					+ comma + stretchType + comma + " pen=" + comma + "Thin"
					+ comma + " fill=" + comma + "Solid" + comma + " />" + "\n"
					+ "</line>" + "\n";
		}

		return lineToDetail;

	}
	

}
