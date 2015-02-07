package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CarryBalance;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgSupplierBalanceDetailInfo extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JTextField tfWareName = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JTextField tfWareSpec = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	/**
	 * 查询action
	 */
	private CasCheckAction casCheckAction = null;
	
	/**
	 * 料件／成品
	 */
	private Boolean isM = true;
	
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton btnRelation = null;
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="837,215"
	private JMenuItem miEnvelopDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miRecvSendDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miTransferDetailInfo = null;  //  @jve:decl-index=0:visual-constraint="812,321"
	private JMenuItem miBalanceAllInfo = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miExeContractDetail = null;  //  @jve:decl-index=0:visual-constraint="813,382"
	
	
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sCustomerName;
	private ScmCoc sScmCoc;
	

	public Boolean getIsM() {
		return isM;
	}

	public void setIsM(Boolean isM) {
		this.isM = isM;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public DgSupplierBalanceDetailInfo() {
		super();
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean("casCheckAction");
		materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(779, 567));
        this.setContentPane(getJContentPane());
        this.setTitle("转厂差额分表");
        initTable(new Vector());	//初始化表格
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
			//jTable = new JTable();
			jTable = new MultiSpanCellTable(){
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			
			};
		}
		return jTable;
	}
	
	
	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		//tableModel = new JTableListModel(jTable, list,
		tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable)jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("商品规格", "spec", 100));
						list.add(addColumn("单位", "unitName", 60));
						list.add(addColumn(" 客户名称", "customerName", 140));
						list.add(addColumn("期初", "amountFirst",60));
						list.add(addColumn("1月", "amountMonth1",60));
						list.add(addColumn("2月", "amountMonth2", 60));
						list.add(addColumn("3月", "amountMonth3", 60));
						list.add(addColumn("4月", "amountMonth4", 60));
						list.add(addColumn("5月", "amountMonth5", 60));
						list.add(addColumn("6月", "amountMonth6", 60));
						list.add(addColumn("7月", "amountMonth7", 60));
						list.add(addColumn("8月", "amountMonth8", 60));
						list.add(addColumn("9月", "amountMonth9", 60));
						list.add(addColumn("10月", "amountMonth10", 60));
						list.add(addColumn("11月", "amountMonth11", 60));
						list.add(addColumn("12月", "amountMonth12", 60));
						return list;
					}
				});
		return tableModel;
	}
	
	/**
	 * 合并行
	 */
	public void combineTable(){
		((MultiSpanCellTable) jTable).splitRows2(new int[] { 1, 2, 3 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(
										new int[] {1, 2, 3 }, 
										new int[] { 1, 2, 3 });
	}

	/**
	 * This method initializes tfWareName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareName() {
		if (tfWareName == null) {
			tfWareName = new JTextField();
			tfWareName.setBounds(new Rectangle(138, 15, 138, 20));
		}
		return tfWareName;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			// cbbBeginDate.setDate(findBeginData2());
			cbbBeginDate.setBounds(new Rectangle(138, 35, 158, 20));
		}
		return cbbBeginDate;
	}
	
	
	/**
	 * This method initializes tfWareSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWareSpec() {
		if (tfWareSpec == null) {
			tfWareSpec = new JTextField();
			tfWareSpec.setBounds(new Rectangle(433, 15, 138, 20));
		}
		return tfWareSpec;
	}

	/**
	 * This method initializes btnQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(621, 10, 74, 22));
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuery;
	}
	
	/**
	 * 查询数据
	 */
	public void queryData(){
		 ConditionBalance conditionBalance = new  ConditionBalance();
		 conditionBalance.setM(isM);
		conditionBalance.setCustomerCode("");
		 conditionBalance.setCustomerName("");
		 conditionBalance.setDate(cbbBeginDate.getDate()==null?new Date():cbbBeginDate.getDate());
		 conditionBalance.setName(tfWareName.getText().trim());
		 conditionBalance.setSpec(tfWareSpec.getText().trim());
		new Find(conditionBalance).execute();
	
	}
	
	/**
	 * 查询线程
	 * @author Administrator
	 */
	class Find extends SwingWorker {
		
		private ConditionBalance conditionBalance = null;
		
		public Find(ConditionBalance conditionBalance){
			this.conditionBalance = conditionBalance;
		}
		@Override
		protected Object doInBackground() throws Exception {//后台计算
			
			//查询
			CommonProgress.showProgressDialog(DgSupplierBalanceDetailInfo.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			//查询返回结果
			return casCheckAction.getCurrentBalanceInfoTotal(new Request(CommonVars.getCurrUser()), conditionBalance);
		}

		@Override
		protected void done() {//更新视图
			List list=null;
			try {
				list = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgSupplierBalanceDetailInfo.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
			combineTable();
		}
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
			btnPrint.setBounds(new Rectangle(621, 34, 74, 22));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List list = tableModel.getList();
						CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
								list);
				
						InputStream masterReportStream = DgSupplierBalanceAllInfo.class
								.getResourceAsStream("report/supplierBalance.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", getTitle());
						JasperPrint jasperPrint = JasperFillManager
								.fillReport(masterReportStream,
										parameters, ds);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
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
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(621, 58, 74, 22));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
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
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBorder(
					BorderFactory.createTitledBorder(null, 
													"选项", 
													TitledBorder.DEFAULT_JUSTIFICATION, 
													TitledBorder.DEFAULT_POSITION, 
													new Font("Dialog", Font.BOLD, 12), 
													Color.blue));
			jLabel5 = new JLabel();
			jLabel5.setText("商品规格");
			jLabel5.setBounds(new Rectangle(368, 15, 62, 18));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setBounds(new Rectangle(75, 35, 62, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new Rectangle(75, 15, 62, 18));
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
			pnTop.add(getBtnRelation(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(276, 15, 20, 20));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = CasQuery.getInstance()
					.findHsNameFromStatCusNameRelationHsn(isM == true ? MaterielType.MATERIEL:
														MaterielType.FINISHED_PRODUCT);
					if (obj != null) {
						tfWareName.setText((String)((TempObject)obj).getObject());
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(572, 15, 20, 20));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = CasQuery.getInstance()
					.findHsNameFromStatCusNameRelationHsn(isM == true ? MaterielType.MATERIEL:
														MaterielType.FINISHED_PRODUCT);
					if (obj != null) {
						tfWareSpec.setText((String)((TempObject)obj).getObject1());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(489, 50, 103, 28));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgSupplierBalanceDetailInfo.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					CarryBalance c = (CarryBalance)tableModel.getCurrentRow();
					sDate = cbbBeginDate.getDate();
					sHsCode = c.getCode();
					sHsName = c.getName();
					sHsSpec = c.getSpec();
					sCustomerName = c.getCustomerName();
					sScmCoc = c.getScmCoc();
					
					Component comp = (Component) e.getSource();
					getPmRelation().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnRelation;
	}
	
	/**
	 * This method initializes pmRelation	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();
			
			//关封明细
			pmRelation.add(getMiEnvelopDetailInfo());
			
			//收送货明细
			pmRelation.add(getMiRecvSendDetailInfo());
			
			//转厂明细
			pmRelation.add(getMiTransferDetailInfo());
			
			//结转差额总表
			pmRelation.add(getMiBalanceAllInfo());
			
			//合同执行情况
			pmRelation.add(getMiExeContractDetail());			
		}
		return pmRelation;
	}
	
	/**
	 * This method initializes miEnvelopDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiEnvelopDetailInfo() {
		if (miEnvelopDetailInfo == null) {
			miEnvelopDetailInfo = new JMenuItem();
			miEnvelopDetailInfo.setText("关封明细");
			miEnvelopDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					   
					if(isM){
						DgSupplierEnvelopDetailInfo dg = new DgSupplierEnvelopDetailInfo();
						dg.setImport(true);
						dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sScmCoc);
						
						dg.queryData();
						
						dg.setVisible(true);
						
					}else{
						DgClientEnvelopDetailInfo dg = new DgClientEnvelopDetailInfo();
						dg.setImport(false);
						dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sScmCoc);
						
						dg.queryData();
						dg.setVisible(true);
						
					}
				
				}
			});
		}
		return miEnvelopDetailInfo;
	}
	/**
	 * This method initializes miRecvSendDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiRecvSendDetailInfo() {
		if (miRecvSendDetailInfo == null) {
			miRecvSendDetailInfo = new JMenuItem();
			miRecvSendDetailInfo.setText("收/送货明细");
			miRecvSendDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = MaterielType.MATERIEL;
					String title = "收货明细表";
					if(!isM){
						type = MaterielType.FINISHED_PRODUCT;
						title = "送货明细表";
					}
					DgRecvSendDetailInfo dg = new DgRecvSendDetailInfo();
					dg.setMaterielType(type);
					dg.setTitle(title);
					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sScmCoc);
					
					dg.queryData();
					
					dg.setVisible(true);
				}
			});
		}
		return miRecvSendDetailInfo;
	}
	/**
	 * This method initializes miTransferDetailInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiTransferDetailInfo() {
		if (miTransferDetailInfo == null) {
			miTransferDetailInfo = new JMenuItem();
			miTransferDetailInfo.setText("转厂明细");
			miTransferDetailInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isImp = true;
					if(!isM){
						isImp = false;
					}
					
					DgTransferDetailInfo dg = new DgTransferDetailInfo();
					dg.setIsImport(isImp);
					dg.setTitle("结转明细表");
					
					dg.setVisible(true);				
					
				}
			});
		}
		return miTransferDetailInfo;
	}
	/**
	 * This method initializes miBalanceAllInfo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiBalanceAllInfo() {
		if (miBalanceAllInfo == null) {
			miBalanceAllInfo = new JMenuItem();
			miBalanceAllInfo.setText("结转差额总表");
			miBalanceAllInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceAllInfo dg = new DgSupplierBalanceAllInfo();
					dg.setTitle("结转差额总表");
					dg.setIsM(isM);
					
					dg.setQueryCondition(sDate, sHsName, sHsSpec,sScmCoc);
					dg.queryData();
					
					dg.setVisible(true);
				}
			});
		}
		return miBalanceAllInfo;
	}
	/**
	 * This method initializes miExeContractDetail	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiExeContractDetail() {
		if (miExeContractDetail == null) {
			miExeContractDetail = new JMenuItem();
			miExeContractDetail.setText("合同执行情况");
			miExeContractDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmContractExecStat dg = new FmContractExecStat();
//					ShowFormControl.showForm(dg);
					dg.setVisible(true);				}
			});
		}
		return miExeContractDetail;
	}

	/**
	 * 关联查询时的参数注入
	 * @param date
	 * @param hsName
	 * @param hsSpec
	 * @param customerName
	 * @author wss
	 */
	public void setQueryCondition(Date date,String hsName,String hsSpec){
		if(date != null){
			this.cbbBeginDate.setDate(date);
		}
		this.tfWareName.setText(hsName);
		this.tfWareSpec.setText(hsSpec);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
