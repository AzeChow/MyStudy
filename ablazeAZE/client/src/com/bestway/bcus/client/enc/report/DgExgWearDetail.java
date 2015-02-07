/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockExg;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillTemp1;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.manualdeclare.DgRequestTOApplyTOCustomsReport;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgExgWearDetail extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="10,556"

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null; // @jve:decl-index=0:
	
	private JComboBox cbbImpExpType = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JButton jButton2 = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbProjectDept = null;

	private JLabel jLabel5 = null;

	private JButton jButton3 = null;
	private JCheckBox cbIsAllUnitWear;

	/**
	 * This is the default constructor
	 */
	public DgExgWearDetail() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	private void initUIComponents() {
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(850, 454);
		this.setTitle("料件耗用明细表");
		List<ProjectDept> projectList = materialManageAction
				.findProjectDept(new Request(CommonVars.getCurrUser()));
		this.cbbProjectDept.setModel(new DefaultComboBoxModel(projectList
				.toArray()));
		this.cbbProjectDept.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbProjectDept, "code", "name");
		cbbProjectDept.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			
	
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			initUIComponents();
			list = new Vector();
			initTable(list);
		}
		super.setVisible(f);
	}
	/**
	 * 
	 * @param impExpType 进出口类型
	 * @param begin      开始时间
	 * @param end		 结束时间
	 * @param seqNum	 料件序号
	 */
	public void showFromDgImgBalanceTotal(Integer impExpType, Date begin, Date end, Integer seqNum) {
		this.jCalendarComboBox.setDate(begin);
		this.jCalendarComboBox1.setDate(end);
		if(impExpType != null) {
			this.cbbImpExpType.setSelectedItem(new ItemProperty(
					impExpType + "", ImpExpType.getImpExpTypeDesc(impExpType)));
		}
		this.jTextField.setText(seqNum == null ? null : seqNum.toString());
		
		ShowFormControl.refreshInteralForm(this);
//		new find().start();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FindImgConsumption().execute();
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setSize(new Dimension(814, 427));
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "bill5", 60));
						list.add(addColumn("料件名称", "bill4", 100));
						list.add(addColumn("成品序号", "bill8", 60));
						list.add(addColumn("成品名称", "bill7", 100));
						list.add(addColumn("成品规格", "bill11", 100));
						list.add(addColumn("版本号", "bill6", 50));
						list.add(addColumn("单耗", "billSum1", 80));
						list.add(addColumn("损耗", "billSum2", 80));
						list.add(addColumn("出口耗用量", "billSum3", 100));
						list.add(addColumn("出口量", "billSum4", 100));
						list.add(addColumn("报关单流水号", "bill1", 100));
						list.add(addColumn("报关单号", "bill10", 100));
						list.add(addColumn("进出口类型", "bill3", 100));
						list.add(addColumn("进出口日期", "bill9", 100));
						list.add(addColumn("贸易方式", "bill2", 100));
						return list;
					}
				});
		List<JTableListColumn> cms = tableModel.getColumns();
		cms.get(7).setFractionCount(3);
		cms.get(8).setFractionCount(3);
		cms.get(9).setFractionCount(3);
		jTable.getColumnModel().getColumn(13).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpType.DIRECT_IMPORT:
								str = "料件进口";
								break;
							case ImpExpType.TRANSFER_FACTORY_IMPORT:
								str = "料件转厂";
								break;
							case ImpExpType.BACK_FACTORY_REWORK:
								str = "退厂返工";
								break;
							case ImpExpType.GENERAL_TRADE_IMPORT:
								str = "一般贸易进口";
								break;
							case ImpExpType.DIRECT_EXPORT:
								str = "成品出口";
								break;
							case ImpExpType.TRANSFER_FACTORY_EXPORT:
								str = "转厂出口";
								break;
							case ImpExpType.BACK_MATERIEL_EXPORT:
								str = "退料出口";
								break;
							case ImpExpType.REWORK_EXPORT:
								str = "返工复出";
								break;
							case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
								str = "边角料退港";
								break;
							case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
								str = "边角料内销";
								break;
							case ImpExpType.GENERAL_TRADE_EXPORT:
								str = "一般贸易出口";
								break;
							case ImpExpType.REMAIN_FORWARD_EXPORT:
								str = "余料结转";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null){
			decimalLength = Integer.valueOf(reportDecimalLength);
		}
		List<JTableListColumn> cm = tableModel.getColumns();
		cm.get(7).setFractionCount(decimalLength);
		cm.get(8).setFractionCount(decimalLength);
		cm.get(9).setFractionCount(decimalLength);
		cm.get(10).setFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable1(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "bill5", 60));
						list.add(addColumn("料件名称", "bill4", 100));
						list.add(addColumn("成品序号", "bill8", 60));
						list.add(addColumn("成品名称", "bill7", 100));
						list.add(addColumn("成品规格", "bill11", 100));
						list.add(addColumn("版本号", "bill6", 50));
						list.add(addColumn("单耗", "billSum1", 80));
						list.add(addColumn("损耗", "billSum2", 80));
						list.add(addColumn("总出口量", "billSum4", 100));
						list.add(addColumn("总耗用量", "billSum19", 100));

						return list;
					}
				});
		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null){
			decimalLength = Integer.valueOf(reportDecimalLength);
		}
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(442, 71, 96, 22));
			jLabel5.setText("出口类型");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(442, 41, 96, 22));
			jLabel11.setText("事业部");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(9, 44, 139, 31));
			jLabel4
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel4.setForeground(new java.awt.Color(255, 102, 51));
			jLabel4.setText("帐册级");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(442, 9, 96, 22));
			jLabel3.setText("进出口结束日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(197, 9, 93, 22));
			jLabel2.setText("进出口开始日期");
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setText("料件耗用明细表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 182, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(197, 41, 93, 22);
			jLabel1.setText("料件序号");

			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getCbbProjectDept(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getCbIsAllUnitWear());
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getClickCount() == 2) {
						showDgRequestTOApplyTOCustomsReport();
					}
				}
			});
		}
		return jTable;
	}
	
	public void showDgRequestTOApplyTOCustomsReport() {
		DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
		BillTemp bill = (BillTemp) tableModel.getCurrentRow();
		if(bill == null) {
			throw new RuntimeException("必须选择一条报表记录才能进行关联！");
		}
		
		dg.showFromDgImpCustomsRecord(false, bill.getBill10(), null);
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
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(700, 9, 120, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgExgWearDetail.this.jTextField.getText().equals("")) {
						JOptionPane.showMessageDialog(DgExgWearDetail.this,
								"请先选择一个物料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(cbIsAllUnitWear.isSelected()){
						new find().start();
					}else{
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								new FindImgConsumption().execute();
							}
						});
					}
					
				}
			});
		}
		return jButton;
	}

	/**
	 * 开始执行查询
	 * 
	 * @author Administrator
	 * 
	 */
	class find extends Thread {

		public void run() {
			
			final boolean isTotal = jCheckBox.isSelected();
			try {
				CommonStepProgress.showStepProgressDialog();
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				ProjectDept projectDept = (ProjectDept) cbbProjectDept
						.getSelectedItem();

				Integer[] imgSeqNums = CommonVars.getIntegerArrayBySplit(
						jTextField.getText().trim(), ",");
				// HashMap<String, BillTemp1> mapBillTemp = new HashMap<String,
				// BillTemp1>();

				final List lsResult = new ArrayList();
		
				List returnList = encAction.findBomBySeqNum(new Request(
						CommonVars.getCurrUser()), imgSeqNums);
				CommonStepProgress.setStepProgressMaximum(returnList.size());
				for (int i = 0; i < returnList.size(); i++) {
					long	t = System.currentTimeMillis();
					BillTemp1 bill = new BillTemp1();
					Object[] obj = (Object[]) returnList.get(i);
					Integer CpseqNum = (Integer) obj[0];
					String name = (String) obj[1];
					String spec = (String) obj[2];
					Double unitwear = (Double) obj[3];
					Double wear = (Double) obj[4];
					Integer version = (Integer) obj[5];
					String ljName = (String) obj[6];
					bill.setBill1(String.valueOf(CpseqNum));// 成品序号
					bill.setBill2(name);// 名称
					bill.setBill3(spec);// 规格
					bill.setBill4(ljName);// 料件名称
					bill.setBill5(String.valueOf(obj[7])); // 料件序号
					bill.setBill6(String.valueOf(version));// 版本
					bill.setBillSum1(unitwear);
					bill.setBillSum2(wear);
					CommonStepProgress.setStepProgressValue(i);
					
					if (isTotal) {// 汇总
						lsResult.addAll(encAction
								.findExgUnitWearBySeqNumIsTotal(new Request(
										CommonVars.getCurrUser()), bill,
										beginDate, endDate,
										projectDept == null ? null
												: projectDept.getId()));
//					    initTable1(lsResult);
					} else {
						List lst = encAction.findExgUnitWearBySeqNumNoTotal(
								new Request(CommonVars.getCurrUser()), bill,
								beginDate, endDate, projectDept == null ? null
										: projectDept.getId(), cbbImpExpType
										.getSelectedIndex() > -1 ? Integer
										.parseInt(((ItemProperty) cbbImpExpType
												.getSelectedItem()).getCode())
										: null);
						lsResult.addAll(lst);
//						initTable(lsResult);
					}
					CommonStepProgress.setStepMessage("系统已查"
							+ returnList.size() + "条成品耗用，正在查成品出口量" +lsResult.size()
							+ "笔,共查出成品出口量为["+lsResult.size()+"]，请稍后...");
					SwingUtilities.invokeLater(new Runnable() {

						public void run() {
							if (tableModel == null) {
								if (isTotal) {
									initTable1(lsResult);
								} else {
									initTable(lsResult);
								}
							} else {
								tableModel.setList(lsResult);
							}
						}
					});
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + (System.currentTimeMillis()-t)/1000d+"秒");
				}
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				e.printStackTrace();
//				JOptionPane.showMessageDialog(DgExgWearDetail.this, "获取数据失败：！"
//						+ e.printStackTrace(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}
	
	class FindImgConsumption extends SwingWorker<Object, Object>{

		@Override
		protected Object doInBackground() throws Exception {
			CommonProgress.showProgressDialog(DgExgWearDetail.this);
			CommonProgress.setMessage("系统正在查询,请稍等...");
			boolean isTotal = jCheckBox.isSelected();
			Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
					.getDate());
			Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
					.getDate());
			ProjectDept projectDept = (ProjectDept) cbbProjectDept
					.getSelectedItem();
			Integer[] imgSeqNums = CommonVars.getIntegerArrayBySplit(
					jTextField.getText().trim(), ",");
			
			Integer impExpType = cbbImpExpType.getSelectedIndex() > -1 ? 
					Integer.parseInt(((ItemProperty) cbbImpExpType.getSelectedItem()).getCode()): null;
			
			List returnList = encAction.findImgConsumption(new Request(CommonVars.getCurrUser()),
					beginDate,endDate,projectDept,imgSeqNums,impExpType,isTotal);
			
			return returnList;
		}
		
		@Override
		protected void done() {
			try {
				List list = (List)get()==null?new ArrayList<Object>():(List)get();
				boolean isTotal = jCheckBox.isSelected();
				if (isTotal) {
					initTable1(list);
				} else {
					initTable(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				CommonProgress.closeProgressDialog();
			}
		}
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(700, 71, 120, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(290, 41, 94, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(290, 9, 114, 22));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(new Rectangle(540, 9, 98, 22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(384, 41, 20, 22));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = CommonQuery.getInstance()
							.getTempCustomsDeclarationCommInfo(true);
					if (list == null) {
						return;
					}
					String s = "";
					for (int i = 0; i < list.size(); i++) {
						BillTemp obj = (BillTemp) list.get(i);
						if (i == 0) {
							s = obj.getBill1();
						} else {
							s = s + "," + obj.getBill1();
						}
					}
					jTextField.setText(s);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(194, 71, 120, 22));
			jCheckBox.setText("是否汇总查询");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tableModel = null;
				}
			});
		}
		return jCheckBox;
	}
	
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(540, 71, 98, 22));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes cbbProjectDept
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectDept() {
		if (cbbProjectDept == null) {
			cbbProjectDept = new JComboBox();
			cbbProjectDept.setBounds(new Rectangle(540, 41, 98, 22));
		}
		return cbbProjectDept;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(700, 41, 120, 23));
			jButton3.setText("关联大小清单");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showDgRequestTOApplyTOCustomsReport();
				}
			});
		}
		return jButton3;
	}
	private JCheckBox getCbIsAllUnitWear() {
		if (cbIsAllUnitWear == null) {
			cbIsAllUnitWear = new JCheckBox();
			cbIsAllUnitWear.setText("显示全部单耗");
			cbIsAllUnitWear.setBounds(new Rectangle(194, 71, 124, 22));
			cbIsAllUnitWear.setBounds(312, 71, 124, 22);
		}
		return cbIsAllUnitWear;
	}
}
