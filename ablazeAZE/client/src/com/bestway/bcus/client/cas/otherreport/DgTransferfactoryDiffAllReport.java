package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;

import org.apache.commons.lang.ObjectUtils.Null;

@SuppressWarnings("unchecked")
public class DgTransferfactoryDiffAllReport extends JDialogBase
{
	private static final long serialVersionUID = -8764955124734067792L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel lbScmCoc = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JComboBox cbbClientName = null;
	private JTextField tfWareName = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JTextField tfWareSpec = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private OtherOption otherOption = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:
	/**
	 * 查询action
	 */
	CasCheckAction casCheckAction = null;
	/**
	 * 料件管理操作接口
	 */
	MaterialManageAction materialManageAction = null;
	// 关联查询时传递 的参数
	private JPanel pnGroupCondition = null;
	private JCheckBox cbScmCoc = null;
	private JCheckBox cbWareName = null;
	private JCheckBox cbWareSpec = null;
	private JLabel lbSelectGroupCondition = null;
	private JLabel lbSelectGroupConditionShow = null;
	private DataModel model;
	private JRadioButton rbQuantity = null;
	private JRadioButton rbWeight = null;
	private JCheckBox cbUnit;

	public Boolean getIsM()
	{
		return model.isM;
	}

	public void setIsM(Boolean isM)
	{
		if (isM)
		{
			lbScmCoc.setText("供应商");
			cbScmCoc.setText("供应商");
		}
		model.isM = isM;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgTransferfactoryDiffAllReport()
	{
		super();
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
				"casCheckAction");
		materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext()
				.getBean("materialManageAction");
		otherOption = CasCommonVars.getOtherOption();
		model = new DataModel();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
	{
		this.setSize(new Dimension(1024, 800));
		this.setContentPane(getJContentPane());
		this.setTitle("***转厂差额总表");
		this.getButtonGroup();
	}

	public void setVisible(boolean b)
	{
		if (b)
		{
			initUi();
			initTable(new ArrayList()); // 初始化表格
		}
		super.setVisible(b);
	}

	/**
	 * 界面初始化
	 */
	private void initUi()
	{
		List<ScmCoc> list1 = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		List<ScmCoc> list = new ArrayList<ScmCoc>();
		for (ScmCoc scmCoc : list1)
		{
			if (getIsM())
			{
				if (scmCoc.getIsManufacturer())
				{
					list.add(scmCoc);
				}
			}
			else
			{
				if (scmCoc.getIsCustomer())
				{
					list.add(scmCoc);
				}
			}
		}
		this.cbbClientName.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbClientName.setRenderer(CustomBaseRender.getInstance().getRender("code", "name",
				50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbClientName, "code",
				"name");
		this.cbbClientName.setUI(new CustomBaseComboBoxUI(250));
		this.cbbClientName.setSelectedItem(null);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
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
	private JTable getJTable()
	{
		if (jTable == null)
		{
			jTable = new MultiSpanCellTable()
			{
				private static final long serialVersionUID = 1L;

				protected JTableHeader createDefaultTableHeader()
				{
					return new GroupableTableHeader(columnModel);
				}

			};

		}
		return jTable;
	}

	/**
	 * 初始化表格
	 */

	private JTableListModel initTable(List list)
	{
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter()
				{
					public List<JTableListColumn> InitColumns()
					{
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn(" 客户代码", "scmCoc.code", 100));// 1
						list.add(addColumn(" 客户名称", "customerName", 140));// 1
						list.add(addColumn("商品名称", "name", 100));// 2
						list.add(addColumn("商品规格", "spec", 100));// 3
						list.add(addColumn("单位", "unitName", 60));// 4
						if(rbQuantity.isSelected()){
							list.add(addColumn("期初数", "amountFirst", 80));// 5
						}else if(rbWeight.isSelected()){
							list.add(addColumn("期初重量(千克)", "amountFirst", 100));// 5
						}

						list.add(addColumn("业务类型", "type", 60));// 6
						list.add(addColumn("1月", "amountMonth1", 60));// 7
						list.add(addColumn("2月", "amountMonth2", 60));// 8
						list.add(addColumn("3月", "amountMonth3", 60));// 9
						list.add(addColumn("4月", "amountMonth4", 60));// 10
						list.add(addColumn("5月", "amountMonth5", 60));// 11
						list.add(addColumn("6月", "amountMonth6", 60));// 12
						list.add(addColumn("7月", "amountMonth7", 60));// 13
						list.add(addColumn("8月", "amountMonth8", 60));// 14
						list.add(addColumn("9月", "amountMonth9", 60));// 15
						list.add(addColumn("10月", "amountMonth10", 60));// 16
						list.add(addColumn("11月", "amountMonth11", 60));// 17
						list.add(addColumn("12月", "amountMonth12", 60));// 18
						list.add(addColumn("差额总值", "total", 60));// 19
						list.add(addColumn("年度合计", "yearTotal", 60));// 20
						list.add(addColumn("联系人", "scmCoc.linkMan", 60));// 22
						list.add(addColumn("联系电话", "scmCoc.linkTel", 100));// 23
						list.add(addColumn("联系传真", "scmCoc.fax", 100));// 24
						return list;
					}
				});
		tableModel.setSort(false);
		List<JTableListColumn> xs = tableModel.getColumns();
		// 显示小数位,默认2位
		int decimalLength = otherOption.getOtherReportMaximumFractionDigits() == null ? 2
				: otherOption.getOtherReportMaximumFractionDigits();
		xs.get(8).setFractionCount(decimalLength);
		xs.get(9).setFractionCount(decimalLength);
		xs.get(10).setFractionCount(decimalLength);
		xs.get(11).setFractionCount(decimalLength);
		xs.get(12).setFractionCount(decimalLength);
		xs.get(13).setFractionCount(decimalLength);
		xs.get(14).setFractionCount(decimalLength);
		xs.get(15).setFractionCount(decimalLength);
		xs.get(16).setFractionCount(decimalLength);
		xs.get(17).setFractionCount(decimalLength);
		xs.get(18).setFractionCount(decimalLength);
		xs.get(19).setFractionCount(decimalLength);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column)
			{
				if (value != null && "0.0".equals(value.toString()))
				{
					value = null;
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				return this;
			}
		};
		jTable.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(11).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(12).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(13).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(14).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(15).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(16).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(17).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(18).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(19).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(20).setCellRenderer(cellRenderer);

		return tableModel;
	}

	/**
	 * 
	 */
	public void combineTable()
	{
		((MultiSpanCellTable) jTable).splitRows2(new int[] { 1, 2, 3, 4, 5, 6, 20,22,23,24 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(new int[] { 1, 2, 3, 4, 5, 6, 20,22,23,24 },
				new int[] { 1, 2, 3, 4, 5, 6, 20,22,23,24 });
	}

	/**
	 * This method initializes cbbClientName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbClientName()
	{
		if (cbbClientName == null)
		{
			cbbClientName = new JComboBox();
			cbbClientName.setBounds(new Rectangle(79, 13, 158, 24));
		}
		return cbbClientName;
	}

	/**
	 * This method initializes tfWareName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWareName()
	{
		if (tfWareName == null)
		{
			tfWareName = new JTextField();
			tfWareName.setBounds(new Rectangle(79, 42, 136, 24));
		}
		return tfWareName;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate()
	{
		if (cbbBeginDate == null)
		{
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(324, 13, 158, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes tfWareSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWareSpec()
	{
		if (tfWareSpec == null)
		{
			tfWareSpec = new JTextField();
			tfWareSpec.setBounds(new Rectangle(324, 42, 136, 24));
		}
		return tfWareSpec;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery()
	{
		if (btnQuery == null)
		{
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(874, 13, 60, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					queryData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 查询
	 */
	public void queryData()
	{
		setModel();
		if (model.groupCondition == null || model.groupCondition.size() == 0)
		{
			throw new RuntimeException("至少要选择一个分组条件");
		}
		ConditionBalance conditionBalance = new ConditionBalance();
		conditionBalance.setM(model.isM);// 1.料件还是成品(进还是出)?
		ScmCoc sc = model.scmCoc;
		if (sc != null)
		{
			conditionBalance.setCustomerCode(sc.getCode());
			conditionBalance.setCustomerName(sc.getName());
		}
		else
		{
			conditionBalance.setCustomerCode("");
			conditionBalance.setCustomerName("");
		}
		conditionBalance.setDate(model.reportDate);// 3.查询日期
		conditionBalance.setName(model.wareName);// 4.报关名称
		conditionBalance.setSpec(model.wareSpec);// 5.报关规格
		conditionBalance.setIsQuantity(model.isQuantity);// 6.数量或重量
		conditionBalance.setGroupCondition(model.groupCondition);

		new Find(conditionBalance).execute();
	}

	class Find extends SwingWorker
	{

		private ConditionBalance conditionBalance = null;

		public Find(ConditionBalance conditionBalance)
		{
			this.conditionBalance = conditionBalance;
		}

		@Override
		protected Object doInBackground() throws Exception
		{// 后台计算

			// 查询
			CommonProgress.showProgressDialog(DgTransferfactoryDiffAllReport.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			// 查询返回结果
			if(conditionBalance.getDate()==null){
				JOptionPane.showMessageDialog(DgTransferfactoryDiffAllReport.this, 
						"请选择日期！", "提示", 2);
				return null;
			}
			return casCheckAction.getCasTransferfactoryDiffAllInfo(
					new Request(CommonVars.getCurrUser()), conditionBalance);
		}

		@Override
		protected void done()
		{// 更新视图
			List list = null;
			try
			{
				list = (List) this.get();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgTransferfactoryDiffAllReport.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			}
			if (list == null)
			{
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
			model.list = list;
			combineTable();
		}
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint()
	{
		if (btnPrint == null)
		{
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(874, 42, 60, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if(model!=null&&model.list!=null&&model.list.size()>0){
						DgTransferfactoryDiffAllReportGroupSet dg = new DgTransferfactoryDiffAllReportGroupSet(
								model);
						dg.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(DgTransferfactoryDiffAllReport.this, "当前没有数据！", "提示", 2);
					}
					
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
	private JButton getBtnClose()
	{
		if (btnClose == null)
		{
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(944, 13, 60, 24));
			btnClose.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane()
	{
		if (jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setDividerLocation(86);

		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop()
	{
		if (pnTop == null)
		{
			pnTop = new JPanel();
			pnTop.setLayout(null);
			jLabel5 = new JLabel();
			jLabel5.setText("商品规格");
			jLabel5.setBounds(new Rectangle(259, 42, 62, 24));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setBounds(new Rectangle(259, 13, 62, 24));
			jLabel2 = new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new Rectangle(16, 42, 62, 24));
			lbScmCoc = new JLabel();
			lbScmCoc.setText("客户名称");
			lbScmCoc.setBounds(new Rectangle(16, 13, 62, 24));
			pnTop.add(lbScmCoc, null);
			pnTop.add(getCbbClientName(), null);
			pnTop.add(jLabel2, null);
			pnTop.add(getTfWareName(), null);
			pnTop.add(jLabel3, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(jLabel5, null);
			pnTop.add(getTfWareSpec(), null);
			pnTop.add(getBtnClose(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(getJButton(), null);
			pnTop.add(getJButton1(), null);
			pnTop.add(getPnGroupCondition(), null);
			pnTop.add(getRbQuantity(), null);
			pnTop.add(getRbWeight(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton()
	{
		if (jButton == null)
		{
			jButton = new JButton();
			jButton.setBounds(new Rectangle(216, 42, 21, 24));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Object obj = CasQuery.getInstance().findStatCusNameRelationHsnNameAndSpec(
							model.isM, true);
					if (obj != null)
					{
						tfWareName.setText((String) ((TempObject) obj).getObject());
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1()
	{
		if (jButton1 == null)
		{
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(462, 42, 21, 24));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Object obj = CasQuery.getInstance().findStatCusNameRelationHsnNameAndSpec(
							model.isM, false);
					if (obj != null)
					{
						tfWareSpec.setText((String) ((TempObject) obj).getObject1());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * 关联查询时的参数注入
	 * 
	 * @param date
	 * @param hsName
	 * @param hsSpec
	 * @param customerName
	 * @author wss
	 */
	public void setQueryCondition(Date date, String hsName, String hsSpec, ScmCoc scmCoc)
	{
		if (date != null)
		{
			this.cbbBeginDate.setDate(date);
		}
		this.tfWareName.setText(hsName);
		this.tfWareSpec.setText(hsSpec);
		this.cbbClientName.setSelectedItem(scmCoc);
	}

	/**
	 * This method initializes pnGroupCondition
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnGroupCondition()
	{
		if (pnGroupCondition == null)
		{
			lbSelectGroupConditionShow = new JLabel();
			lbSelectGroupConditionShow.setBounds(new Rectangle(100, 42, 204, 20));
			lbSelectGroupConditionShow.setText("");
			lbSelectGroupCondition = new JLabel();
			lbSelectGroupCondition.setBounds(new Rectangle(8, 42, 88, 20));
			lbSelectGroupCondition.setText("已选分组条件：");
			pnGroupCondition = new JPanel();
			pnGroupCondition.setLayout(null);
			pnGroupCondition.setBounds(new Rectangle(490, 0, 327, 70));
			pnGroupCondition.setBorder(BorderFactory.createTitledBorder(null, "分组条件",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), Color.blue));
			pnGroupCondition.add(getCbScmCoc(), null);
			pnGroupCondition.add(getCbWareName(), null);
			pnGroupCondition.add(getCbWareSpec(), null);
			pnGroupCondition.add(getCbUnit(),null);
			pnGroupCondition.add(lbSelectGroupCondition, null);
			pnGroupCondition.add(lbSelectGroupConditionShow, null);
			
		}
		return pnGroupCondition;
	}

	/**
	 * This method initializes cbScmCoc
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbScmCoc()
	{
		if (cbScmCoc == null)
		{
			cbScmCoc = new JCheckBox();
			cbScmCoc.setBounds(new Rectangle(3, 15, 76, 20));
			cbScmCoc.setSelected(true);
			cbScmCoc.setText("客户名称");
			cbScmCoc.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					refreshGroupCondition();
				}
			});
		}
		return cbScmCoc;
	}

	/**
	 * This method initializes cbWareName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWareName()
	{
		if (cbWareName == null)
		{
			cbWareName = new JCheckBox();
			cbWareName.setBounds(new Rectangle(81, 15, 73, 20));
			cbWareName.setSelected(true);
			cbWareName.setText("商品名称");
			cbWareName.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					refreshGroupCondition();
				}
			});
		}
		return cbWareName;
	}

	/**
	 * This method initializes cbWareSpec
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWareSpec()
	{
		if (cbWareSpec == null)
		{
			cbWareSpec = new JCheckBox();
			cbWareSpec.setBounds(new Rectangle(156, 15, 73, 20));
			cbWareSpec.setSelected(true);
			cbWareSpec.setText("商品规格");
			cbWareSpec.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					refreshGroupCondition();
				}
			});
		}
		return cbWareSpec;
	}

	public void refreshGroupCondition()
	{
		String groupCondition = "";
		if (cbScmCoc.isSelected())
		{
			groupCondition += cbScmCoc.getText();
		}

		if (cbWareName.isSelected())
		{
			groupCondition += " /名称";
		}

		if (cbWareSpec.isSelected())
		{
			groupCondition += " /规格";
		}
		if (cbUnit.isSelected())
		{
			groupCondition += " /单位";
		}
		lbSelectGroupConditionShow.setText(groupCondition);
	}

	// 设置分组的 条件 2013.8.5 HerWar
	public void setModel()
	{
		model.groupCondition = new ArrayList<String>(3);

		if (cbScmCoc.isSelected())
		{
			model.groupCondition.add("b.billMaster.scmCoc.name");
			model.groupCondition.add("b.billMaster.scmCoc.code");
		}

		if (cbWareName.isSelected())
		{
			model.groupCondition.add("b.hsName");
		}

		if (cbWareSpec.isSelected())
		{
			model.groupCondition.add("b.hsSpec");
		}
		// 添加 2013.8.6 HerWar 选择单位
		if (cbUnit.isSelected())
		{
			model.groupCondition.add("b.hsUnit.name");
		}

		if (cbbClientName.getSelectedIndex() > -1)
		{
			model.scmCoc = (ScmCoc) cbbClientName.getSelectedItem();
		}else{
			model.scmCoc = null;
		}

		model.reportDate = cbbBeginDate.getDate();
		model.wareName = tfWareName.getText();
		model.wareSpec = tfWareSpec.getText();
		
		if (rbWeight.isSelected())
		{
			model.isQuantity = false;
		}
		else
		{
			model.isQuantity = true;
		}
		model.list = tableModel.getList();
	}

	public DataModel getModel()
	{
		return model;
	}

	class DataModel
	{
		/**
		 * 料件／成品
		 */
		Boolean isM = true; // @jve:decl-index=0:
		/**
		 * 客户/供应商名称
		 */
		ScmCoc scmCoc;
		/**
		 * 报表日期
		 */
		Date reportDate;
		/**
		 * 商品名称
		 */
		String wareName;
		/**
		 * 商品规格
		 */
		String wareSpec;
		/**
		 * 分组条件（分组字段名）
		 */
		List<String> groupCondition;
		/**
		 * 表格数据
		 */
		List list;
		/**
		 * 重量或数量
		 */
		Boolean isQuantity;
	}

	/**
	 * This method initializes rbQuantity
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbQuantity()
	{
		if (rbQuantity == null)
		{
			rbQuantity = new JRadioButton();
			rbQuantity.setBounds(new Rectangle(819, 13, 49, 24));
			rbQuantity.setSelected(true);
			rbQuantity.setText("数量");
		}
		return rbQuantity;
	}

	/**
	 * This method initializes rbWeight
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbWeight()
	{
		if (rbWeight == null)
		{
			rbWeight = new JRadioButton();
			rbWeight.setBounds(new Rectangle(819, 43, 49, 24));
			rbWeight.setText("重量");
		}
		return rbWeight;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup()
	{
		if (buttonGroup1 == null)
		{
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getRbQuantity());
			buttonGroup1.add(this.getRbWeight());
		}
		return buttonGroup1;
	}

	private JCheckBox getCbUnit()
	{
		if (cbUnit == null)
		{
			cbUnit = new JCheckBox("商品单位");
			cbUnit.setSelected(true);
			cbUnit.setBounds(231, 14, 73, 23);
		}
		cbUnit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				refreshGroupCondition();
			}
		});
		return cbUnit;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
