package com.bestway.common.client.fpt.btpls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadAction;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadParaAction;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageCustomsCoverHeadTemp;
import com.bestway.common.fpt.btplsinput.entity.MessageIndentureHeadTemp;
import com.bestway.common.fpt.btplsinput.logic.BtplsDownloadLogic;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings( { "unchecked", "serial" })
public class FmTransferFactoryBillDownData extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel2 = null;
	private JButton btnQuiry = null;
	private JScrollPane jScrollPane = null;
	private JButton btnDownload = null;
	private JTable jTable = null;
	private JTable returnjTable = null;
	private JTable secessjTable = null;
	private JTableListModel tableModel = null;
	private JTableListModel returntableModel = null;	
	private JTableListModel secesstableModel = null;	
	private BtplsDownloadAction btplsDowloadAction = null;
	private BtplsDownloadParaAction btplsDowloadParaAction = null;
	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JLabel jLabel3 = null;
	private JComboBox cbbScmCoc = null;
	private FptParameterSet parameterSet = null;
	private FptManageAction fptManageAction = null;
	// 初始化出口口岸
	private List<Customs> customList = new ArrayList<Customs>();  //  @jve:decl-index=0:
	// 初始化运输方式
	private List<Transf> transfList = new ArrayList<Transf>();  //  @jve:decl-index=0:
	// 初始化贸易方式
	private List<Trade> tradeList = new ArrayList<Trade>();  //  @jve:decl-index=0:
	private JLabel jLabel4 = null;
	private JComboBox cbbEmsNo = null;
	private ContractAction contractAction = null;
	protected ManualDeclareAction manualDeclareAction = null;
	private HashMap<String, Object> parameter = new HashMap<String, Object>();
	/**
	 * This method initializes
	 * 
	 */
	public FmTransferFactoryBillDownData() {
		super();
		btplsDowloadAction = (BtplsDownloadAction) CommonVars.getApplicationContext().getBean("btplsDownloadAction");
		btplsDowloadParaAction = (BtplsDownloadParaAction) CommonVars.getApplicationContext().getBean("btplsDownloadParaAction");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext().getBean("fptManageAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext().getBean("contractAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars.getApplicationContext().getBean("manualdeclearAction");
		parameterSet = fptManageAction .findTransParameterSet(new Request(CommonVars .getCurrUser(), true));
		initialize();		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("收发货单据表");
		this.setSize(new Dimension(900, 600));
		initUIComponents();
	}

	

	private void initUIComponents() {
		List list = new ArrayList();
		list = btplsDowloadParaAction.findScmManuFactoryFromBtplsDownloadPara();
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,	"scmCoc.code", "scmCoc.name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender("scmCoc.code", "scmCoc.name", 80, 170));

		initData();
	};
	
	@SuppressWarnings("rawtypes")
	private void initData() {
		List list = new ArrayList();
		if (list != null && list.size() > 0) {
			initTable(list);
			initReturnTable(list);
			initSecessTable(list);
		} else {
			initTable(new Vector());	
			initReturnTable(new Vector());
			initSecessTable(new Vector());
		}
		
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
			jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJPanel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("手册编号：");
			jLabel3 = new JLabel();
			jLabel3.setText("客户/供应商:");
			jLabel2 = new JLabel();
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setText("申报日期");
			jPanel = new JPanel();
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(5);
			flowLayout.setVgap(5);
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuiry(), null);
			jPanel.add(getBtnDownload(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(100, 24));
			cbbBeginDate.setDate(null);
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
			cbbEndDate.setPreferredSize(new java.awt.Dimension(100, 24));
			// cbbEndDate.setDate(new Date());
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnQuiry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuiry() {
		if (btnQuiry == null) {
			btnQuiry = new JButton();
			btnQuiry.setText("查询");
			btnQuiry.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {					
					HashMap<String, Object> condition = null;
					BtplsDownloadPara para = null;
					if(!checkData()){
						return;
					}
					initData();
					BtplsDownloadPara sc = (BtplsDownloadPara) cbbScmCoc.getSelectedItem();					
					para = (BtplsDownloadPara) btplsDowloadParaAction.findBtplsDownloadParaByScm(sc.getScmCoc()).get(0);					
					Integer projectType = ProjectType.BCUS;					
					condition = fillQueryCondition();
					if (para == null || para.getIpAddre() == null|| para.getPort() == null) {
						return;
					}
					btnQuiry.setEnabled(false);
					btnDownload.setEnabled(true);
					new Find(condition, para,projectType).execute();
				}
			});
		}
		return btnQuiry;
	}

	class Find extends SwingWorker {
		private HashMap condition;
		private BtplsDownloadPara para;
		private Integer projectType;

		public Find(HashMap map, BtplsDownloadPara para,Integer projectType) {
			CommonProgress.showProgressDialog(FmTransferFactoryBillDownData.this);
			CommonProgress.setMessage("正在远程查询收/发货资料,请耐心等待...");
			this.condition = map;
			this.para = para;
			this.projectType = projectType;
			
			parameter.put("ipAddre", para.getIpAddre());
			parameter.put("port", para.getPort());
			parameter.put("companyCode", para.getScmCoc().getCode());
			parameter.put("projectType", projectType.toString());
			parameter.put("selectIndex",jTabbedPane.getSelectedIndex() );
		}

		@Override
		protected Object doInBackground() throws Exception {
			return BtplsDownloadLogic.findMessageIndentureHead(parameter, condition);
		}

		@Override
		protected void done() {// 更新视图
			List list = null;
			try {
				list = (List) this.get();
				if (list == null) {
					list = new ArrayList();
				}
				if(jTabbedPane.getSelectedIndex()==0){
					initTable(list);
				}else if(jTabbedPane.getSelectedIndex()==1){
					initReturnTable(list);
				}else {
					initSecessTable(list);
				}
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						FmTransferFactoryBillDownData.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			}finally{
				btnQuiry.setEnabled(true);
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 检查数据
	 */
	private boolean checkData() {
		BtplsDownloadPara  sc = (BtplsDownloadPara) this.cbbScmCoc.getSelectedItem();
		if (sc== null) {
			JOptionPane.showMessageDialog(
					FmTransferFactoryBillDownData.this,
					"没有选择供应商或者客户", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.cbbBeginDate.getDate() == null
				&& this.cbbEndDate.getDate() == null) {
			JOptionPane.showMessageDialog(FmTransferFactoryBillDownData.this,
					"为了下载速度且同步准确，请至少选择一个查询条件!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
	/**
	 * This method initializes btnQuiry
	 * 
	 * @return javax.swing.JButton
	 */
	public HashMap fillQueryCondition() {
		HashMap<String, Object> condition = new HashMap<String, Object>();
		BtplsDownloadPara sc = (BtplsDownloadPara) this.cbbScmCoc.getSelectedItem();
		if (sc.getScmCoc() == null) {
			JOptionPane.showMessageDialog(
					FmTransferFactoryBillDownData.this, "没有选择供应商或者客户",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
		   condition.put("a.companyCode = ?",sc.getScmCoc().getCode());
		}
		condition.put(" a.sendDate >= ? ", cbbBeginDate.getDate());
		condition.put(" a.sendDate <= ? ", cbbEndDate.getDate());
		return condition;
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
	 * This method initializes jScrollPane
	 * 收发退货单据
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getReturnJTable());
		}
		return jScrollPane2;
	}
	
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getseccessJTable());
		}
		return jScrollPane1;
	}	

	
	/**
	 * This method initializes btnDownload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton();
			btnDownload.setText("同步");
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<MessageIndentureHeadTemp> seList = getCommodityList();					
					List<String> selIdList = new ArrayList<String>();
					if (seList.size()==0) {
						JOptionPane.showMessageDialog(null, "请选择收发货数据项!", "提示!",
								JOptionPane.OK_OPTION);
						return;
					}
					for (int i = 0; i < seList.size(); i++) {
						selIdList.add(seList.get(i).getId());
					}
					btnDownload.setEnabled(false);
					parameter.put("idList", selIdList);
					CommonProgress.showProgressDialog(FmTransferFactoryBillDownData.this);
					CommonProgress.setMessage("正在同步远程，下载数据,请耐心等待...");
					// 下载收发货单
					List list = btplsDowloadAction.downloadMessageIndentureBody(new Request(CommonVars.getCurrUser()), parameter);					
					StringBuffer sb = new StringBuffer();
					if(list.size()==0){	
						if(jTabbedPane.getSelectedIndex()==0){
							sb.append("没有收发货单表体数据可同步");
						}else if(jTabbedPane.getSelectedIndex()==1){
							sb.append("没有收退货表体数据可同步！");
						}else {
							sb.append("其它未知项！");
						}
					}else{
						if(jTabbedPane.getSelectedIndex()==0){
							sb.append("同步成功！一共同步").append(list.size()).append("条收发货单数据");
				
						}else if(jTabbedPane.getSelectedIndex()==1){
							sb.append("同步成功！一共同步").append(list.size()).append("条收退货单数据");
						}else {
							sb.append("其它未知项！");	
						}
						initSecessTable(list);
					}
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(FmTransferFactoryBillDownData.this,sb, "提示", 0);
					btnDownload.setEnabled(true);
				}
			});
		}
		return btnDownload;
	}
	

	/**
	 * This method initializes jTable
	 * 收发货
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					List<MessageIndentureHeadTemp> list = tableModel.getCurrentRows();
					for (MessageIndentureHeadTemp item : list) {
						item.setIsSelected(!item.getIsSelected());
					}
				}
			});
		}
		return jTable;
	}
	/**
	 * This method initializes jTable
	 * 收退货
	 * @return javax.swing.JTable
	 */
	private JTable getReturnJTable() {
		if (returnjTable == null) {
			returnjTable = new JTable();
			returnjTable.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					List<MessageIndentureHeadTemp> list = returntableModel.getCurrentRows();
					for (MessageIndentureHeadTemp item : list) {
						item.setIsSelected(!item.getIsSelected());
					}
				}
			});
		}
		return returnjTable;
	}
	/**
	 * This method initializes jTable
	 * 成功下载
	 * @return javax.swing.JTable
	 */
	private JTable getseccessJTable() {
		if (secessjTable == null) {
			secessjTable = new JTable();
			secessjTable.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					List<MessageIndentureHeadTemp> list = secesstableModel.getCurrentRows();
					for (MessageIndentureHeadTemp item : list) {
						item.setIsSelected(!item.getIsSelected());
					}
				}
			});
		}
		return secessjTable;
	}

	/**
	 * 获得选中收发货、退货信息
	 */
	public List getCommodityList() {
		List list= null;
		if(jTabbedPane.getSelectedIndex()==0){
			if (this.tableModel == null) {
				return null;
			}
			list = tableModel.getList();
		}else if(jTabbedPane.getSelectedIndex()==1){
			if (this.returntableModel == null) {
				return null;
			}
			list = returntableModel.getList();
		}else {
			if (this.secesstableModel == null) {
				return null;
			}
			list = secesstableModel.getList();
		}
		
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof MessageIndentureHeadTemp) {
				MessageIndentureHeadTemp temp = (MessageIndentureHeadTemp) list	.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}
	
	
	/**
	 * 初始化收发货单据表
	 * FptInOutFlag.OUT:0
	 * FptInOutFlag.IN :1
	 * FptBusinessType.FPT_BILL:2
	 * @param list
	 */
	private void initTable(List list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
			JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("", "isSelected", 60));// 选择1
					if(jTabbedPane.getSelectedIndex()==1){
						list.add(addColumn("收退货编号", "billNo", 100));// 2
					}else{
						list.add(addColumn("收发货编号", "billNo", 100));// 2
					}
					list.add(addColumn("申请单号", "appNo", 100));// 3
					list.add(addColumn("合同号", "contractNo", 100));// 4
					list.add(addColumn("发货企业编码", "issueTradeCod", 100));// 5
					list.add(addColumn("发货企业名称", "issueTradeName", 100));// 6
					list.add(addColumn("申报日期", "issueIsDeclaDate", 100));// 7
					list.add(addColumn("收货企业编码", "receiveTradeCod", 100));// 8
					list.add(addColumn("收货企业名称", "receiveTradeName", 100));// 9
					list.add(addColumn("收货日期", "receiveDate", 100));// 10
					list.add(addColumn("收货申报人", "receiveIsDeclaEm", 100));// 11
					list.add(addColumn("收货申报日期", "receiveIsDeclaDate", 100));// 12
					list.add(addColumn("单据类型", "messageIndentureType", 100));// 13
					list.add(addColumn("收货备注", "receiveNote", 100));// 14
					list.add(addColumn("是否已下载", "isJBCUSDown", 60));// 是否已下载15
					return list;
				}
			};
			
			JTableListModel.dataBind(jTable, list, tableModelAdapter,ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jTable.getTableHeader().setReorderingAllowed(false);
			tableModelAdapter.setEditableColumn(1);
			tableModel = (JTableListModel) jTable.getModel();
			tableModel.setAllowSetValue(true);
			jTable.getColumnModel().getColumn(1).setHeaderRenderer(
					new CheckBoxHeader(false));
			jTable.getColumnModel().getColumn(1).setCellRenderer(
					new TableCheckBoxRender());
			
			jTable.getColumnModel().getColumn(13).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "" : FptInOutFlag
									.getInOutFlagSpec(value.toString()));
							return this;
						}
					});
			
			jTable.getColumnModel().getColumn(15).setCellRenderer(
					new TableCheckBoxRender());
		} else {
			tableModel.setList(list);
		}
	}
	/**
	 * 初始化收发退货单据表
	 * FptInOutFlag.OUT:0
	 * FptInOutFlag.IN :1
	 * FptBusinessType.FPT_BILL:2
	 * @param list
	 */
	private void initReturnTable(List list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
			JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("", "isSelected", 60));// 选择1
					list.add(addColumn("收退货编号", "billNo", 100));// 2					
					list.add(addColumn("申请单号", "appNo", 100));// 3
					list.add(addColumn("合同号", "contractNo", 100));// 4
					list.add(addColumn("发货企业编码", "issueTradeCod", 100));// 5
					list.add(addColumn("发货企业名称", "issueTradeName", 100));// 6
					list.add(addColumn("申报日期", "issueIsDeclaDate", 100));// 7
					list.add(addColumn("收货企业编码", "receiveTradeCod", 100));// 8
					list.add(addColumn("收货企业名称", "receiveTradeName", 100));// 9
					list.add(addColumn("收货日期", "receiveDate", 100));// 10
					list.add(addColumn("收货申报人", "receiveIsDeclaEm", 100));// 11
					list.add(addColumn("收货申报日期", "receiveIsDeclaDate", 100));// 12
					list.add(addColumn("单据类型", "messageIndentureType", 100));// 13
					list.add(addColumn("收货备注", "receiveNote", 100));// 14
					list.add(addColumn("是否已下载", "isJBCUSDown", 60));// 是否已下载15
					return list;
				}
			};
			
			JTableListModel.dataBind(returnjTable, list, tableModelAdapter,ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			returnjTable.getTableHeader().setReorderingAllowed(false);
			tableModelAdapter.setEditableColumn(1);
			returntableModel = (JTableListModel) returnjTable.getModel();
			returntableModel.setAllowSetValue(true);
			returnjTable.getColumnModel().getColumn(1).setHeaderRenderer(
					new CheckBoxHeader(false));
			returnjTable.getColumnModel().getColumn(1).setCellRenderer(
					new TableCheckBoxRender());
			
			returnjTable.getColumnModel().getColumn(13).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "" : FptInOutFlag
									.getInOutFlagSpec(value.toString()));
							return this;
						}
					});
			
			returnjTable.getColumnModel().getColumn(15).setCellRenderer(
					new TableCheckBoxRender());
		} else {
			returntableModel.setList(list);
		}
	}

	/**
	 * 初始化收发退货单据表
	 * FptInOutFlag.OUT:0
	 * FptInOutFlag.IN :1
	 * FptBusinessType.FPT_BILL:2
	 * @param list
	 */
	private void initSecessTable(List list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
			JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("", "isSelected", 60));// 选择1
					list.add(addColumn("收发退货编号", "billNo", 100));// 2					
					list.add(addColumn("申请单号", "appNo", 100));// 3
					list.add(addColumn("合同号", "contractNo", 100));// 4
					list.add(addColumn("发货企业编码", "issueTradeCod", 100));// 5
					list.add(addColumn("发货企业名称", "issueTradeName", 100));// 6
					list.add(addColumn("申报日期", "issueIsDeclaDate", 100));// 7
					list.add(addColumn("收货企业编码", "receiveTradeCod", 100));// 8
					list.add(addColumn("收货企业名称", "receiveTradeName", 100));// 9
					list.add(addColumn("收货日期", "receiveDate", 100));// 10
					list.add(addColumn("收货申报人", "receiveIsDeclaEm", 100));// 11
					list.add(addColumn("收货申报日期", "receiveIsDeclaDate", 100));// 12
					list.add(addColumn("单据类型", "messageIndentureType", 100));// 13
					list.add(addColumn("收货备注", "receiveNote", 100));// 14
					list.add(addColumn("是否已下载", "isJBCUSDown", 60));// 是否已下载15
					return list;
				}
			};
			
			JTableListModel.dataBind(secessjTable, list, tableModelAdapter,ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			secessjTable.getTableHeader().setReorderingAllowed(false);
			tableModelAdapter.setEditableColumn(1);
			secesstableModel = (JTableListModel) secessjTable.getModel();
			secesstableModel.setAllowSetValue(true);
			secessjTable.getColumnModel().getColumn(1).setHeaderRenderer(
					new CheckBoxHeader(false));
			secessjTable.getColumnModel().getColumn(1).setCellRenderer(
					new TableCheckBoxRender());
			
			secessjTable.getColumnModel().getColumn(13).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "" : FptInOutFlag
									.getInOutFlagSpec(value.toString()));
							return this;
						}
					});
			
			secessjTable.getColumnModel().getColumn(15).setCellRenderer(
					new TableCheckBoxRender());
		} else {
			secesstableModel.setList(list);
		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();			
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("收发货单据表", null, getJScrollPane(), null);
			jTabbedPane.addTab("收发退货单据表", null, getJScrollPane2(), null);
			jTabbedPane.addTab("下载成功收发货单据表", null, getJScrollPane1(), null);
//			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
//				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					initData();						
//				}
//			});

		}
		return jTabbedPane;
	}
	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setPreferredSize(new java.awt.Dimension(120, 24));
		}
		return cbbScmCoc;
	}

	/**
	 * 显示空数据
	 * 
	 */
	protected void showEmptyData() {
		this.cbbScmCoc.setSelectedItem(null);
	}

	/**
	 * This method initializes cbbEmsNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setPreferredSize(new Dimension(120, 27));
		}
		return cbbEmsNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
