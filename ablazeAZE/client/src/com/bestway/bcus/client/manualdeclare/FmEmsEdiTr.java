/*
 * Created on 2004-7-5
 *
 * //s
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmEmsEdiTr extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton btnPrint = null;
	
	private JButton btnBrowse =null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private EmsEdiTrHead emsHead = null;

	private ManualDeclareAction manualDecleareAction = null;

	private SystemAction systemAction = null;
	
	private MessageAction messageAction = null;

	private JTableListModel tableModel = null;

	private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	private JButton jButton6 = null;
	private JButton jButton8 = null;

	private JButton jButton81 = null;
	/**
	 * This is the default constructor
	 */
	public FmEmsEdiTr() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		messageAction = (MessageAction)CommonVars.getApplicationContext()
			.getBean("messageAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(691, 350);
		this.setTitle("经营范围-表头");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = manualDecleareAction.findEmsEdiTrHead(new Request(
				CommonVars.getCurrUser()));
		initTable(dataSource);
		setState();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60, Integer.class));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "ownerCode", 100));
						list.add(addColumn("加工单位名称", "ownerName", 120));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "productRatio", 80));
						list.add(addColumn("外经贸部门", "declareDep.name", 100));
						list.add(addColumn("主管海关", "masterCustoms.code", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("帐册类型", "emsType", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmEmsEdiTr.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmEmsEdiTr.this.setChange(true);
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmEmsEdiTr.this.isChange)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 *  
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getbtnProcess());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getJButton6());
			jToolBar.add(getJButton8());
			jToolBar.add(getJButton81());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}
	private boolean checkNull() {
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getBuCode()==null) {
			JOptionPane.showMessageDialog(this, "经营单位代码不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getCode()==null) {
			JOptionPane.showMessageDialog(this, "加工单位代码不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		if (((Company) CommonVars.getCurrUser().getCompany())
				.getAmountProduct()==null) {
			JOptionPane.showMessageDialog(this, "年加工能力不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getMasterCustoms()==null) {
			JOptionPane.showMessageDialog(this, "主管海关不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getInverst()==null) {
			JOptionPane.showMessageDialog(this, "投资金额不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getBuOwnerType()==null) {
			JOptionPane.showMessageDialog(this, "经营单位企业性质不能为空,请检查公司资料表!", "提示!", 2);
			return true;
		}
		return false;
	}
	private void fillEmsEdiTrHead(EmsEdiTrHead emsEdiTrHead) {
		
		emsEdiTrHead.setCopEmsNo(systemAction
				.generateAutoNo(EmsEdiTrHead.class));         //企业内部编号
		emsEdiTrHead.setDeclareType(DeclareState.APPLY_POR);  //申报类型
		emsEdiTrHead.setDeclareState(DeclareState.APPLY_POR); //审批状态 
		emsEdiTrHead.setHistoryState(new Boolean(false)); //历史状态
		emsEdiTrHead.setModifyTimes(Integer.valueOf(0));  //变更次数
		emsEdiTrHead.setModifyMark(ModifyMarkState.ADDED);//修改标志
		emsEdiTrHead.setTradeCode(((Company) CommonVars.getCurrUser()
				.getCompany()).getBuCode().trim()); //经营单位代码
		emsEdiTrHead.setTradeName(((Company) CommonVars.getCurrUser()
				.getCompany()).getBuName().trim()); //经营单位名称
		emsEdiTrHead.setOwnerCode(((Company) CommonVars.getCurrUser()
				.getCompany()).getCode().trim()); //加工单位代码
		emsEdiTrHead.setOwnerName(((Company) CommonVars.getCurrUser()
				.getCompany()).getName().trim()); //加工单位名称
		emsEdiTrHead.setProductRatio(new Double(
				((Company) CommonVars.getCurrUser().getCompany())
						.getAmountProduct().doubleValue() / 10000));//年加工能力
		emsEdiTrHead.setMasterCustoms(((Company) CommonVars.getCurrUser()
				.getCompany()).getMasterCustoms()); //主管海关
		emsEdiTrHead
				.setInvestAmount(new Double(((Company) CommonVars.getCurrUser()
						.getCompany()).getInverst().doubleValue() / 10000));//投资金额
		emsEdiTrHead.setOwnerType(((Company) CommonVars.getCurrUser()
				.getCompany()).getBuOwnerType());//经营单位企业性质
		if (((Company) CommonVars.getCurrUser().getCompany())
				.getTel()!=null)
		emsEdiTrHead.setPhone(((Company) CommonVars.getCurrUser().getCompany())
				.getTel().trim()); //联系电话
		else
			emsEdiTrHead.setPhone("");
		if (((Company) CommonVars.getCurrUser()
				.getCompany()).getAddress()!=null)
		emsEdiTrHead.setAddress(((Company) CommonVars.getCurrUser()
				.getCompany()).getAddress().trim());//联系地址
		else
			emsEdiTrHead.setAddress("");
		String coCode = ((Company) CommonVars.getCurrUser().getCompany())
				.getBuCode().trim().substring(0, 5); //取经营单位代码前五位
		String soCode = ((Company) CommonVars.getCurrUser().getCompany())
				.getBuCode().trim().substring(0, 4); //取经营单位代码前四位
		District district = findDistrictByCode(coCode);
		emsEdiTrHead.setArea(district); //得到地区代码(取经营单位代码前五位)
		RedDep redDep = findRedDepByCode(soCode);
		emsEdiTrHead.setDeclareDep(redDep); //得到外经委代码(取经营单位代码前四位)

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		emsEdiTrHead.setInputDate(java.sql.Date.valueOf(defaultDate)); //录入日期
		emsEdiTrHead.setBeginDate(java.sql.Date.valueOf(defaultDate)); //开始有效期
		emsEdiTrHead.setEmsType("0"); //帐册类型:经营范围帐册
		if (CommonVars.getCurrUser()!=null)
		   emsEdiTrHead.setInputEr(CommonVars.getCurrUser().getLoginName()); //录入员代号
		else
		   emsEdiTrHead.setInputEr("");
		emsEdiTrHead.setCompany(CommonVars.getCurrUser().getCompany()); //公司id
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private District findDistrictByCode(String code) { //得到地区代码
		for (int i = 0; i < CustomBaseList.getInstance().getDistrict().size(); i++) {
			if (((District) CustomBaseList.getInstance().getDistrict().get(i))
					.getCode().equals(code))
				return (District) CustomBaseList.getInstance().getDistrict()
						.get(i);
		}
		return null;
	}

	private RedDep findRedDepByCode(String code) { //得到外经委代码
		for (int i = 0; i < CustomBaseList.getInstance().getRedDeps().size(); i++) {
			if (((RedDep) CustomBaseList.getInstance().getRedDeps().get(i))
					.getCode().equals(code))
				return (RedDep) CustomBaseList.getInstance().getRedDeps()
						.get(i);
		}
		return null;
	}

	private JButton getBtnAdd() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");

			jButton.setPreferredSize(new Dimension(60, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {  //检查公司表是否为空
						return;
					}
					EmsEdiTrHead emsEdiTrHead = new EmsEdiTrHead();
					fillEmsEdiTrHead(emsEdiTrHead);
					emsEdiTrHead = manualDecleareAction
							.saveEmsEdiTrHead(new Request(CommonVars
									.getCurrUser()), emsEdiTrHead);
					tableModel.addRow(emsEdiTrHead);
					setState();
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnEdit() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改");
			jButton1.setPreferredSize(new Dimension(60, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return jButton1;
	}
	
	/**
	 * 
	 * This method initializes btnView（浏览按钮）
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.setPreferredSize(new Dimension(60, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiTr.this, "请选择你将要浏览的记录",
								"提示！", 0);
						return;
					}
					DgEmsEdiTr dgEmsEdiTr = new DgEmsEdiTr();

					dgEmsEdiTr.setTableModel(tableModel);
					dgEmsEdiTr.setVisible(true);
				}
			});

		}
		return btnBrowse;
	}
	
	
	
	
	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsEdiTr.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgEmsEdiTr dgEmsEdiTr = new DgEmsEdiTr();
		dgEmsEdiTr.setChange(((EmsEdiTrHead) tableModel.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsEdiTr.setTableModel(tableModel);
		dgEmsEdiTr.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnDelete() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsEdiTr.this,
							"是否要删除该帐册记录？\n请注意：其下的成品料件将一并被删除！", "确认", 0)== 0) {
						    EmsEdiTrHead emsEdi = (EmsEdiTrHead)tableModel.getCurrentRow(); 
						    manualDecleareAction.deleteEmsEdiTrImgExg(new Request(
									CommonVars.getCurrUser()), emsEdi);
							manualDecleareAction.deleteEmsEdiTrHead(new Request(
									CommonVars.getCurrUser()), emsEdi);
							tableModel.deleteRow(emsEdi);
						}						
						setState();
					}
			});
		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnChange() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("变更");
			jButton3.setPreferredSize(new Dimension(60, 30));
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsEdiTrHead emsHeadChange = null;
					emsHeadChange = manualDecleareAction
							.emsEdiChange(new Request(CommonVars.getCurrUser()));
					tableModel.addRow(emsHeadChange);
					setState();
				}
			});

		}
		return jButton3;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnDeclare() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("海关申报");
			jButton4.setPreferredSize(new Dimension(60, 30));
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					emsEdiTrHead = (EmsEdiTrHead) tableModel.getCurrentRow();
					if (emsEdiTrHead.getInvestAmount() == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,
								"投资金额不能为空", "提示！", 0);
						return;
					}
					if (emsEdiTrHead.getProductRatio() == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,
								"年加工生产能力不能为空", "提示！", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsEdiTr.this,
							"是否确定要将【经营范围】向海关申报吗？", "确认", 0)== 0) {
					    chelonian();
					}
					setState();
				}
			});

		}
		return jButton4;
	}

	private void chelonian() {
		String messageName = "";		
		
		Date now = new Date();
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(now);
		emsEdiTrHead = (EmsEdiTrHead) tableModel.getCurrentRow();
		emsEdiTrHead.setDeclareDate(java.sql.Date.valueOf(defaultDate));//申报日期
		emsEdiTrHead.setDeclareTime(CommonVars.dateToTime(now));//申报时间
		emsEdiTrHead = manualDecleareAction.saveEmsEdiTrHead(new Request(CommonVars.getCurrUser()), emsEdiTrHead);
	    tableModel.updateRow(emsEdiTrHead);
	       
		
		emsEdiTrHead = (EmsEdiTrHead) tableModel.getCurrentRow();
		List list = CustomBaseList.getInstance().getGbtobigs();
		
		if(emsEdiTrHead.getDeclareType().equals(DelcareType.APPLICATION)){ //申请
			messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),emsEdiTrHead,1,list)[0];
			messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_TR,
					DelcareType.APPLICATION,messageName,emsEdiTrHead.getCopEmsNo(),"EMS211",0);
		}
		else if(emsEdiTrHead.getDeclareType().equals(DelcareType.MODIFY)){ //变更
			messageName=messageAction.exportMessage(new Request(CommonVars.getCurrUser()),emsEdiTrHead,2,list)[0];
			messageAction.saveMessageQuery(new Request(CommonVars.getCurrUser()),MessageQuery.SENDTYPE,EdiType.EMS_EDI_TR,DelcareType.MODIFY,messageName,emsEdiTrHead.getCopEmsNo(),"EMS221",0);
		   
		}
		emsEdiTrHead.setDeclareState(DeclareState.WAIT_EAA); //审批状态		
		emsEdiTrHead.setCheckMark("1");                      //执行标志
		emsEdiTrHead = manualDecleareAction.saveEmsEdiTrHead(new Request(CommonVars.getCurrUser()), emsEdiTrHead);
		tableModel.updateRow(emsEdiTrHead);
		JOptionPane.showMessageDialog(this,"报文已经生成，位置在中间服务器的："+messageName,"报文已生成",1);		       
	}

	private void turning(EmsEdiTrHead emsEdiTrHead) {
	    if (emsEdiTrHead.getDeclareType().equals(DelcareType.MODIFY)){
	    	List list = manualDecleareAction.findEmsEdiTrHead(new Request(
					CommonVars.getCurrUser()));
	    	for (int i=0;i<list.size();i++){
	    		if (((EmsEdiTrHead) list.get(i)).getDeclareType().equals(DelcareType.APPLICATION)){
	    			EmsEdiTrHead emsHead = (EmsEdiTrHead) list.get(i);
	    			emsHead.setHistoryState(new Boolean(true));  
	    			emsHead = manualDecleareAction
					.saveEmsEdiTrHead(new Request(CommonVars
							.getCurrUser()), emsHead);
	    			tableModel.deleteRow(emsHead);
	    		}
	    	}
	    	emsEdiTrHead.setDeclareType(DelcareType.APPLICATION);
	    }
		emsEdiTrHead.setDeclareState(DeclareState.PROCESS_EXE); //审批状态 
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getbtnProcess() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("回执处理");
			jButton5.setPreferredSize(new Dimension(60, 30));
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
					dgProcessMessage.setType("EmsEdiTrHead");
					dgProcessMessage.setVisible(true);
					
					List dataSource = manualDecleareAction.findEmsEdiTrHead(new Request(
							CommonVars.getCurrUser()));
					initTable(dataSource);
					setState();
				}
			});

		}
		return jButton5;
	}

	private void setState() {
		jButton.setEnabled(tableModel.getSize() == 0);//新增
		jButton1.setEnabled(isDeclareState());        //修改
		jButton2.setEnabled(isDeclareState());        //删除
		jButton3.setEnabled(isChangeState() && isPROCESS_EXE());//变更
		//jButton4.setEnabled(isDeclareState());        //海关申报
		jButton4.setEnabled(!isPROCESS_EXE());
		jButton5.setEnabled((tableModel.getCurrentRow() != null));//回执处理
		jButton81.setEnabled(!isPROCESS_EXE());
	}

	private boolean isChangeState() {                 //只有一条记录
		List list = null;
		list = manualDecleareAction.findEmsEdiTrHead(new Request(CommonVars
				.getCurrUser()));
		if (list.size() == 1) {
			return true;
		}
		return false;
	}

	private boolean isDeclareState() {               //审批状态为：1
		if (tableModel.getCurrentRow() != null
				&& !((EmsEdiTrHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)
						&& !((EmsEdiTrHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.WAIT_EAA)) {
			return true;
		}
		return false;
	}
	
	private boolean isPROCESS_EXE() {               //审批状态为：3
		if (tableModel.getCurrentRow() != null
				&& ((EmsEdiTrHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * This method initializes btnPrint
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setVisible(false);
			btnPrint.setText("打印申请表");
			btnPrint.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					EmsEdiTrHead head = (EmsEdiTrHead)tableModel.getCurrentRow();
					if(head == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,"必须先选择一个表头，才能打印");
						return;
					}
					List imgExgs = manualDecleareAction.findEmsEdiImgExgs(new Request(CommonVars.getCurrUser()),head);
					CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
					List company = new Vector(); //只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);
						
					InputStream masterReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclare.jasper");			
					InputStream detailReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclareDetail.jasper");		

					try {
						
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);
							
						Map parameters = new HashMap();
						parameters.put("company", CommonVars.getCurrUser().getCompany());
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						JasperPrint jasperPrint;
					
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);				
					} catch (JRException e1) {
						e1.printStackTrace();
					}

				}
			});
		}
		return btnPrint;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnExit() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("关闭");
			jButton7.setPreferredSize(new Dimension(60, 30));
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmEmsEdiTr.this.dispose();

				}
			});

		}
		return jButton7;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 *  
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
			                if (tableModel == null)
			                	return;
			                if (tableModel.getCurrentRow() == null)
			                	return;
			                 setState();
						}
					});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return Returns the emsHeadPar.
	 */
	public EmsEdiTrHead getEmsHeadPar() {
		return emsHead;
	}

	/**
	 * @param emsHeadPar
	 *            The emsHeadPar to set.
	 */
	public void setEmsHeadPar(EmsEdiTrHead emsHeadPar) {
		this.emsHead = emsHeadPar;
	}
	/**

	 * This method initializes jButton6	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setVisible(false);
			jButton6.setText("打印批准证");
			jButton6.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					EmsEdiTrHead head = (EmsEdiTrHead)tableModel.getCurrentRow();
					if(head == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,"必须先选择一个表头，才能打印");
						return;
					}
					String emsApprNo = "";
					List imgExgs = manualDecleareAction.findEmsEdiImgExgs(new Request(CommonVars.getCurrUser()),head);
					CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
					List company = new Vector(); 
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);
					emsApprNo = head.getEmsApprNo();
					if (emsApprNo == null){
						emsApprNo = "";
					}
					InputStream masterReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessSanction.jasper");			
					InputStream detailReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessSanctionSub.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);		
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("emsApprNo",emsApprNo);
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}

				}
			});

		}
		return jButton6;
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("批准证申请表");
			jButton8.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					EmsEdiTrHead head = (EmsEdiTrHead)tableModel.getCurrentRow();
					if(head == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,"必须先选择一个表头，才能打印");
						return;
					}
					List imgExgs = manualDecleareAction.findEmsEdiImgExgs(new Request(CommonVars.getCurrUser()),head);
					CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
					List company = new Vector(); 
					company.add(CommonVars.getCurrUser().getCompany());
					Company comp = (Company) company.get(0);
					String DamountOut ="0";
					if (comp.getAmountOut()!=null){
						DamountOut = comp.getAmountOut().toString();
					}
					String DamountProduct ="0";
					if (comp.getAmountProduct()!=null){
						DamountProduct = comp.getAmountProduct().toString();
					}
					CommonDataSource companyDS = new CommonDataSource(company);
					InputStream masterReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclareSanction.jasper");			
					InputStream detailReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclareSanctionSub.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);		
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("sDamountOut",String.valueOf(Double.parseDouble(DamountOut)/10000));
						parameters.put("sDamountProduct",String.valueOf(Double.parseDouble(DamountProduct)/10000));
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton81	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton81() {
		if (jButton81 == null) {
			jButton81 = new JButton();
			jButton81.setText("批准证变更表");
			jButton81.setEnabled(false);
			jButton81.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					EmsEdiTrHead head = (EmsEdiTrHead)tableModel.getCurrentRow();
					if(head == null){
						JOptionPane.showMessageDialog(FmEmsEdiTr.this,"必须先选择一个表头，才能打印");
						return;
					}
					List imgExgs = manualDecleareAction.findEditedEmsEdiImgExgs(new Request(CommonVars.getCurrUser()),head);
					CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
					List company = new Vector(); 
					company.add(CommonVars.getCurrUser().getCompany());
					Company comp = (Company) company.get(0);
					String DamountOut ="0";
					if (comp.getAmountOut()!=null){
						DamountOut = comp.getAmountOut().toString();
					}
					String DamountProduct ="0";
					if (comp.getAmountProduct()!=null){
						DamountProduct = comp.getAmountProduct().toString();
					}
					CommonDataSource companyDS = new CommonDataSource(company);
					InputStream masterReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclareSanction.jasper");			
					InputStream detailReportStream = FmEmsEdiTr.class
						.getResourceAsStream("report/BusinessDeclareSanctionSub.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);		
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("sDamountOut",String.valueOf(Double.parseDouble(DamountOut)/10000));
						parameters.put("sDamountProduct",String.valueOf(Double.parseDouble(DamountProduct)/10000));
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton81;
	}
  } //  @jve:decl-index=0:visual-constraint="10,10"
