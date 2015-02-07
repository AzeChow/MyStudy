package com.bestway.common.client.aptitudereport;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CheckBoxListCellRendererNotCode;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.action.AptitudeReportAction;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

import javax.swing.ComboBoxEditor;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.jfree.util.ShapeUtilities;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.awt.ComponentOrientation;


public class FmReportStatResultShow extends JInternalFrameBase{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private JToolBar jToolBar1 = null;

	private JButton btnCakyChart = null;

	private JButton btnLinetype = null;

	private JButton btnHistogram = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JComboBox cbbGroupShow = null;

	private JScrollPane jScrollPane = null;
	
	private SelectCondition selectCondition = null;
	
	private List reportList = null; // @jve:decl-index=0:

	private List<GroupValue> groupByList = null;  //  @jve:decl-index=0:
	
	private List<GroupValue> groupValueList = null;  //  @jve:decl-index=0:
	
	private List<GroupValue> statTypeList = null;  //  @jve:decl-index=0:

	private JTableListModel tableModelReport = null;
	
	private String reportPath = null;
	
	private Boolean isAdd = true;  //  @jve:decl-index=0:
	
	private String reportTilte = null;  //  @jve:decl-index=0:
	
	private int state = 1;
	
	private String groupingColumnValue = "";  //  @jve:decl-index=0:

	private JLabel jLabel1 = null;

	private JComboBox cbbStatType = null;
	
	private AptitudeReportAction aptitudeReportAction = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable = null;

	/**
	 * This is the default constructor
	 */
	public FmReportStatResultShow(SelectCondition selectCondition, List reportList,
			List<GroupValue> groupByList,List<GroupValue> groupValueList,
			List<GroupValue> statTypeList) {
		super();
		aptitudeReportAction = (AptitudeReportAction) CommonVars
			.getApplicationContext().getBean("aptitudeReportAction");
		this.selectCondition = selectCondition;
		this.reportList = reportList;
		this.groupByList = groupByList;
		this.groupValueList = groupValueList;
		this.statTypeList = statTypeList;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setPreferredSize(new Dimension(734, 550));
		this.setContentPane(getJContentPane());
		this.setTitle("报表显示");
		String reportName = selectCondition.getReportName().toString() + "报表分析";
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
			jContentPane.setPreferredSize(new Dimension(734, 520));
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
			jSplitPane.setDividerLocation(160);
			jSplitPane.setResizeWeight(0.0D);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setPreferredSize(new Dimension(680, 510));
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setPreferredSize(new Dimension(734,160));
			jPanel.setComponentOrientation(ComponentOrientation.UNKNOWN);
//			jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setPreferredSize(new Dimension(465, 350));
//			jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel1.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}
	
	private void initTable(final List list) {
		tableModelReport = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						setSelectCondition(selectCondition);
						String statColumn = selectCondition.getStatColumn();
						String statColumnValue = selectCondition
								.getStatColumnValue();
						String aimObject = selectCondition.getAimObject();

						List<JTableListColumn> list = new Vector<JTableListColumn>();
						if (groupByList.size() > 0) {
							for (int i = 0; i < groupByList.size(); i++) {
								list.add(addColumn(
										groupByList.get(i).getName(),
										groupByList.get(i).getCode(), 100));
							}


						}
			
						return list;
					}
				});
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(208, 34));
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
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
					btnPrint.setEnabled(false);

					List<TempReport> tempReportList = getReportLst(reportList);

					if (tempReportList != null && !tempReportList.isEmpty()) {
						CommonDataSource reportDS = new CommonDataSource(
								tempReportList);
						String aimObject = selectCondition.getAimObjectValue();
						String groupingColumnValue = selectCondition
								.getGroupingColumnValue();

						String statColumnValue = getCnStatTypeValue();
						int btnSelect = getState();
						if (btnSelect == 1) {
							reportPath = "report/AptitudeReportCakyChart.jasper";
						}
						if (btnSelect == 3) {
							reportPath = "report/AptitudeReportLinetype.jasper";
						}
						if (btnSelect == 2) {
							reportPath = "report/AptitudeReportHistogram.jasper";
						}
						InputStream masterReportStream =FmReportStatResultShow.class
								.getResourceAsStream(reportPath);
						try {
							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("aimObject", aimObject);
							parameters.put("groupingColumnValue",
									groupingColumnValue);
							parameters.put("statColumnValue", statColumnValue);
							parameters.put("reportTilte", FmReportStatResultShow.this
									.getReportTilte());
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, reportDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
					reportPath = null;
					btnPrint.setEnabled(true);
				}
			});

		}
		return btnPrint;
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
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmReportStatResultShow.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setPreferredSize(new Dimension(208, 34));
			jToolBar1.add(getBtnCakyChart());
			jToolBar1.add(getBtnLinetype());
			jToolBar1.add(getBtnHistogram());
			jToolBar1.add(getJPanel2());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnCakyChart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCakyChart() {
		if (btnCakyChart == null) {
			btnCakyChart = new JButton();
			btnCakyChart.setText("饼状图");
			btnCakyChart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (validateData() == true) {
						return;
					}
					if (validateData1() == true) {
						return;
					}
					int state = 1;
					setState(state);
					getSelectButton(state);

				}
			});

		}
		return btnCakyChart;
	}

	/**
	 * This method initializes btnLinetype	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnLinetype() {
		if (btnLinetype == null) {
			btnLinetype = new JButton();
			btnLinetype.setText("曲线图");
			btnLinetype.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}
					int state = 3;
					setState(state);
					getSelectButton(state);

				}
			});
		}
		return btnLinetype;
	}

	/**
	 * This method initializes btnHistogram	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHistogram() {
		if (btnHistogram == null) {
			btnHistogram = new JButton();
			btnHistogram.setText("柱状图");
			btnHistogram.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}
					int state = 2;
					setState(state);
					getSelectButton(state);

				}
			});
		}
		return btnHistogram;
	}
	
	
	private void getSelectButton(int state) {
		int x =0;
		int y =0;
		switch (state) {

		case 1:
			JFreeChart chart = createChart(createDataset());
			ChartPanel component = new ChartPanel(chart);
			if(reportList.size()>=20&&reportList.size()<=40){
				component.setPreferredSize(new Dimension(800, 800));
			}
			if(reportList.size()>40&&reportList.size()<80){
				component.setPreferredSize(new Dimension(800, 1200));
			}
			jScrollPane.setViewportView(component);

			break;
		case 2:
			if(reportList.size()<20){
				x = 465;
				y = 400;
			}else if(reportList.size()<40){
				x = 800;
				y = 800;
			}else if(reportList.size()<80){
				x = 1024;
				y = 1024;
			}else {
				x = 1200;
				y = 1200;
			}
			JFreeChart chart1 = createChartHistogram(createDatasetHistogram());
			ChartPanel component1 = new ChartPanel(chart1);
			component1.setPreferredSize(new Dimension(x, y));
			jScrollPane.setViewportView(component1);
			break;
		case 3:
			if(reportList.size()<20){
				x = 465;
				y =400;
			}else if(reportList.size()<40){
				x = 665;
				y =400;
			}else if(reportList.size()<80){
				x = 865;
				y =400;
			}else {
				x = 1024;
				y =400;
			}
			JFreeChart chart2 = createChartLine(createDatasetLine());
			ChartPanel component2 = new ChartPanel(chart2);
			component2.setPreferredSize(new Dimension(x, y));
			jScrollPane.setViewportView(component2);
			break;

		}

	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(291, 2, 56, 26));
			jLabel1.setText("统计方式");
			jLabel = new JLabel();
			jLabel.setText("选择显示分组");
			jLabel.setBounds(new Rectangle(12, 2, 85, 25));
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(jLabel, null);
			jPanel2.add(getCbbGroupShow(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getCbbStatType(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbGroupShow	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbGroupShow() {
		if (cbbGroupShow == null) {
			cbbGroupShow = new JComboBox();
			cbbGroupShow.setBounds(new Rectangle(95, 0, 185, 28));
		}

		for (int i = 0; i < groupValueList.size(); i++) {
			cbbGroupShow
					.addItem(new CheckBoxListItem(groupValueList.get(i).getCode(),
							groupValueList.get(i).getName()));
		}
		cbbGroupShow.setSelectedItem(null);
		cbbGroupShow.setRenderer(new CheckBoxListCellRendererNotCode(cbbGroupShow));
		cbbGroupShow.setUI(new SteppedMetalComboBoxUI(220));
		setCmbBillTypeEvent(cbbGroupShow);
		cbbGroupShow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {

			}
		});
		return cbbGroupShow;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(465, 350));
		}

		return jScrollPane;
	}

	public void setSelectCondition(SelectCondition selectCondition) {
		// 	this.selectCondition = selectCondition;

	}

	public void setReportList(List reportList) {
		// 
		this.reportList = reportList;

	}

	public SelectCondition getSelectCondition(SelectCondition selectCondition) {
		return selectCondition;
	}

	public List getReportList() {
		return reportList;
	}

	public void setAdd(boolean isAdd) {
		// 
		this.isAdd = isAdd;

	}

	private JFreeChart createChart(PieDataset piedataset) {
		String reportName = selectCondition.getReportName();
		String str = reportName.toString() + "报表分析图";
		List list = getCheckableItemList();
		String sign = "/";
		String groupingColumn = "";
		for (int i = 0; i < list.size(); i++) {
			String index = list.get(i).toString();
			if (i == list.size() - 1)
				sign = "";
			int n = Integer.decode(index);
			groupingColumn += groupValueList.get(n).getName() + sign;
		}
		String StatColumn = selectCondition.getStatColumnValue();
		JFreeChart jfreechart = ChartFactory.createPieChart3D(str, piedataset,
				true, false, false);
		PiePlot3D pieplot3d = (PiePlot3D) jfreechart.getPlot();
		pieplot3d.setStartAngle(270D);
		pieplot3d.setDirection(Rotation.ANTICLOCKWISE);
		pieplot3d.setForegroundAlpha(0.6F);
		pieplot3d.setInteriorGap(0.33000000000000002D);
		return jfreechart;
	}

	private PieDataset createDataset() {
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		int time = 0;
		int indexToStat = 0;
		indexToStat = getIndexToStat();
		List<String> groupNameList = new ArrayList<String>();
		for (int i = 0; i < reportList.size(); i++) {
			List<Integer> list = getCheckableItemList();

			String sign = "/";
			String groupingColumn = "";
			for (int j = 0; j < list.size(); j++) {
				int index = list.get(j);
				if (j == list.size() - 1)
					sign = "";
				groupingColumn += tableModelReport.getValueAt(i, index + 1)
						+ sign;
			}
			groupNameList.add(groupingColumn);
			String groupingColumnNew = "";
			for (int k = 0; k < groupNameList.size(); k++) {
				groupingColumnNew = groupNameList.get(k);
				if (groupingColumnNew == groupingColumn) {
					time++;
					groupingColumn += "(" + time + ")";

				}
			}

			Double statColumn = (Double) tableModelReport.getValueAt(i,
					indexToStat + 1);
			defaultpiedataset.setValue(groupingColumn, new Double(statColumn));

		}

		return defaultpiedataset;
	}

	/**
	 * 曲线图
	 */
	private CategoryDataset createDatasetLine() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		int time = 0;
		int indexToStat = 0;
		indexToStat = getIndexToStat();
		List<String> groupNameList = new ArrayList<String>();
		for (int i = 0; i < reportList.size(); i++) {
			List<Integer> list = getCheckableItemList();
			String sign = "/";
			String groupingColumn = "";
			String groupingColumnValue = "";
			for (int j = 0; j < list.size(); j++) {
				int index = list.get(j);
				if (j == list.size() - 1)
					sign = "";
				groupingColumn += tableModelReport.getValueAt(i, index + 1)
						+ sign;
				groupingColumnValue += groupValueList.get(index).getName() + sign;
				setGroupingColumnValue(groupingColumnValue);
			}
			groupNameList.add(groupingColumn);
			String groupingColumnNew = "";
			for (int k = 0; k < groupNameList.size(); k++) {
				groupingColumnNew = groupNameList.get(k);
				if (groupingColumnNew == groupingColumn) {
					time++;
					groupingColumn += "(" + time + ")";

				}
			}

			Double statColumn = (Double) tableModelReport.getValueAt(i,
					indexToStat + 1);
			defaultcategorydataset.addValue(statColumn, groupingColumnValue,
					groupingColumn);

		}
		return defaultcategorydataset;
	}

	private JFreeChart createChartLine(CategoryDataset categorydataset) {
		String reportName = selectCondition.getReportName();
		String str = reportName.toString() + "报表分析图";
		List list = getCheckableItemList();
		String sign = "/";
		String groupingColumn = "";
		for (int i = 0; i < list.size(); i++) {
			String index = list.get(i).toString();
			if (i == list.size() - 1)
				sign = "";
			int n = Integer.decode(index);
			groupingColumn += groupValueList.get(n).getName() + sign;
		}
		String StatColumn = getCnStatTypeValue();
		JFreeChart jfreechartLine = ChartFactory.createLineChart(str,
				groupingColumn, StatColumn, categorydataset,
				PlotOrientation.VERTICAL, true, true, false);
		jfreechartLine.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechartLine.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setSeriesShapesVisible(0, true);
		lineandshaperenderer.setSeriesShapesVisible(1, false);
		lineandshaperenderer.setSeriesShapesVisible(2, true);
		lineandshaperenderer.setSeriesLinesVisible(2, false);
		lineandshaperenderer
				.setSeriesShape(2, ShapeUtilities.createDiamond(4F));
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setFillPaint(Color.white);
		return jfreechartLine;
	}

	/**
	 * 
	 * 柱状图
	 */

	private CategoryDataset createDatasetHistogram() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		int time = 0;
		int indexToStat = 0;
		indexToStat = getIndexToStat();
		List<String> groupNameList = new ArrayList<String>();
		for (int i = 0; i < reportList.size(); i++) {
			List<Integer> list = getCheckableItemList();
			String sign = "/";
			String groupingColumn = "";
			String groupingColumnValue = "";
			for (int j = 0; j < list.size(); j++) {
				int index = list.get(j);
				if (j == list.size() - 1)
					sign = "";
				groupingColumn += tableModelReport.getValueAt(i, index + 1)
						+ sign;
				groupingColumnValue += groupValueList.get(index).getName() + sign;
			}
			groupNameList.add(groupingColumn);
			String groupingColumnNew = "";
			for (int k = 0; k < groupNameList.size(); k++) {
				groupingColumnNew = groupNameList.get(k);
				if (groupingColumnNew == groupingColumn) {
					time++;
					groupingColumn += "(" + time + ")";
				}
			}

			Double statColumn = (Double) tableModelReport.getValueAt(i,
					indexToStat + 1);
			defaultcategorydataset.addValue(statColumn, groupingColumn,
					groupingColumnValue);

		}

		return defaultcategorydataset;
	}

	private JFreeChart createChartHistogram(CategoryDataset categorydataset) {
		String reportName = selectCondition.getReportName();
		String str = reportName.toString() + "报表分析图";
		List list = getCheckableItemList();
		String sign = "/";
		String groupingColumn = "";
		for (int i = 0; i < list.size(); i++) {
			String index = list.get(i).toString();
			if (i == list.size() - 1)
				sign = "";
			int n = Integer.decode(index);
			groupingColumn += groupValueList.get(n).getName() + sign;
		}
		String StatColumn =  getCnStatTypeValue();
		JFreeChart jfreechart = ChartFactory.createBarChart3D(str,
				groupingColumn, StatColumn, categorydataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(0.39269908169872414D));
		CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
		categoryitemrenderer.setItemLabelsVisible(true);
		BarRenderer barrenderer = (BarRenderer) categoryitemrenderer;
		barrenderer.setMaximumBarWidth(0.050000000000000003D);
		return jfreechart;
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

		int time = 0;
		int indexToStat = 0;
		indexToStat = getIndexToStat();
		List<String> groupNameList = new ArrayList<String>();
		for (int i = 0; i < reportList.size(); i++) {
			TempReport tempReport = new TempReport();

			List<Integer> list = getCheckableItemList();
			String sign = "/";
			String groupingColumn = "";
			String groupingColumnValue = "";
			for (int j = 0; j < list.size(); j++) {
				int index = list.get(j);
				if (j == list.size() - 1)
					sign = "";
				groupingColumn += tableModelReport.getValueAt(i, index + 1)
						+ sign;
				groupingColumnValue += groupValueList.get(index).getName() + sign;
			}
			groupNameList.add(groupingColumn);
			String groupingColumnNew = "";
			for (int k = 0; k < groupNameList.size(); k++) {
				groupingColumnNew = groupNameList.get(k);
				if (groupingColumnNew == groupingColumn) {
					time++;
					groupingColumn += "(" + time + ")";
				}
			}

			Double statColumn = (Double) tableModelReport.getValueAt(i,
					indexToStat + 1);
			tempReport.setGroupingColumn(groupingColumn);
			tempReport.setStatColumn(statColumn);
			newList.add(tempReport);
		}
		return newList;
	}

	public void setGroupValueList(List<GroupValue> groupByList) {

		this.groupByList = groupByList;

	}

	public List<GroupValue> getGroupValueList() {
		return groupByList;
	}
	
	public void setStatTypeList(List<GroupValue> statTypeList) {

		this.statTypeList = statTypeList;

	}

	public List<GroupValue> getStatTypeList() {
		return statTypeList;
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
	private void setCmbBillTypeEvent(final JComboBox cbb) {
		final JList listBox;
		try {
			Field field = JComponent.class.getDeclaredField("ui");
			field.setAccessible(true);
			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
			field = BasicComboBoxUI.class.getDeclaredField("listBox");
			field.setAccessible(true);
			listBox = (JList) field.get(ui);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		if (listBox == null) {
			return;
		}

		listBox.setBackground(Color.white);
		listBox.setFixedCellHeight(20);

		MouseListener[] mouseListener = listBox.getMouseListeners();
		for (int i = 0; i < mouseListener.length; i++) {
			listBox.removeMouseListener(mouseListener[i]);
		}

		listBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listBox.locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());

					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}

				}

				if (validateData1() == true) {
					return;
				}
				state = getState();
				if (state == 1) {
					getSelectButton(state);
				} else if (state == 2) {
					getSelectButton(state);
				} else if (state == 3) {
					getSelectButton(state);
				}

			}
		});

		//
		// 当焦点丢失的时候
		//
		ComboBoxEditor com = cbb.getEditor();
		final JTextField tf = (JTextField) com.getEditorComponent();
		tf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				String[] strs = tf.getText().split(",");
				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();

				int size = listBox.getModel().getSize();
				for (int i = 0; i < size; i++) {
					CheckBoxListItem item = (CheckBoxListItem) listBox
							.getModel().getElementAt(i);
					checkBoxListItemMap.put(item.getName(), item);
				}
				//
				// 根据输入的字符串选中JList中的列表
				//
				String tempText = "";
				for (String str : strs) {
					CheckBoxListItem item = checkBoxListItemMap.get(str);
					if (item != null) {
						item.setIsSelected(true);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
						checkBoxListItemMap.remove(str);
					}
				}
				//
				// 清除未选中的记录
				//
				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItem checkBoxListItem = iterator.next();
					checkBoxListItem.setIsSelected(false);
				}
				//
				// 重新设置其显示文本值
				//
				tf.setText(tempText);
			}
		});

	}

	private List getIndexOfGroupingColumnValue() {
		List indexList = new ArrayList();
		int index = 0;
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		List<GroupValue> groupList = this.groupByList;
		for (int i = 0; i < cbbGroupShow.getModel().getSize(); i++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbGroupShow
					.getModel().getElementAt(i);
			checkableItemList.add(item);
			for (int j = 0; j < groupList.size(); j++) {
				boolean flag = groupList.get(j).getName().equalsIgnoreCase(
						checkableItemList.get(i).getName());
				if (flag) {
					index = i;
					indexList.add(index);
				}

			}
		}
		return indexList;

	}

	private List getCheckableItemList() {
		List<Integer> list = new ArrayList<Integer>();
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		for (int j = 0; j < this.cbbGroupShow.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbGroupShow
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {

				list.add(j);
				checkableItemList.add(item);

			}

		}
		return list;
	}

	private void setState(int state) {
		this.state = state;

	}

	private int getState() {
		return state;
	}

	private boolean validateData() {
		List list = getCheckableItemList();
		if (list.size() < 1) {
			JOptionPane.showMessageDialog(this, "分组显示选择不能为空！", "提示！", 0);
			return true;
		}

		return false;
	}

	private boolean validateData1() {
		if(reportList.size() >80){
			JOptionPane.showMessageDialog(this, "统计数据记录超过80条,不适合使用饼状图显示!", "提示！", 0);
			return true;
		}

		return false;
	}
	
	private void setGroupingColumnValue(String groupingColumnValue) {
		this.groupingColumnValue = groupingColumnValue;
	}

	private String getGroupingColumnValue() {
		return groupingColumnValue;
	}

	/**
	 * This method initializes cbbStatType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbStatType() {
		if (cbbStatType == null) {
			cbbStatType = new JComboBox();
			cbbStatType.setBounds(new Rectangle(348, 1, 106, 27));
			for(int i =0;i<statTypeList.size();i++){
				cbbStatType.addItem(new ItemProperty(statTypeList.get(i).getCode(),
						statTypeList.get(i).getName()));
			}
		}
		cbbStatType.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				state = getState();
				if (state == 1) {
					getSelectButton(state);
				} else if (state == 2) {
					getSelectButton(state);
				} else if (state == 3) {
					getSelectButton(state);
					
				}
			}
		});
		return cbbStatType;
	}
	
	private String getEnStatTypeValue(){
		String enStatTypeValue = "";
		
		enStatTypeValue = ((ItemProperty) cbbStatType.getSelectedItem()).getCode();
		return enStatTypeValue;
	}
	
	private String getCnStatTypeValue(){
		String cnStatTypeValue = "";
		cnStatTypeValue = ((ItemProperty) cbbStatType.getSelectedItem()).getName();
		return cnStatTypeValue;
	}
	
	private Integer getIndexToStat(){
		Integer index = 0;
		String code = getEnStatTypeValue();
		List<GroupValue> groupByList = aptitudeReportAction
				.findGroupValue(new Request(CommonVars
				.getCurrUser()), selectCondition.getId());
		for(int i=0;i<groupByList.size();i++){
			
			if(groupByList.get(i).getCode().equalsIgnoreCase(code)){
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
			jScrollPane1.setViewportView(getJTable());
			initTable(reportList);
		}
		return jScrollPane1;
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
	
	
}
