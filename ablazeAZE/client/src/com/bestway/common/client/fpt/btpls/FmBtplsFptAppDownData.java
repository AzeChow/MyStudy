package com.bestway.common.client.fpt.btpls;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import bsh.This;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
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
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmBtplsFptAppDownData extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JPanel pan1 = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfSerialNumber = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel2 = null;
	private JButton btnQuiry = null;
	private JScrollPane jScrollPane = null;
	private JButton btnDownload = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	
	private BtplsDownloadAction btplsDownloadAction =null;
	private BtplsDownloadParaAction btplsDownloadParaAction =null;
	private JTabbedPane jTabbedPane = null;
	private JPanel pan2 = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbSuccess = null;
	private FptParameterSet parameterSet = null;
	private FptManageAction fptManageAction = null;
	private List btpList = new ArrayList();
	private HashMap<String, Object> parameter = new HashMap<String, Object>();
	/**
	 * This method initializes 
	 * 
	 */
	public FmBtplsFptAppDownData() {
		super();
		btplsDownloadAction = (BtplsDownloadAction) CommonVars.getApplicationContext().getBean("btplsDownloadAction");
		btplsDownloadParaAction = (BtplsDownloadParaAction) CommonVars.getApplicationContext().getBean("btplsDownloadParaAction");
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext().getBean("fptManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(918, 447));
        this.setContentPane(getJContentPane());
        this.setTitle("结转申请表下载");
        initUIComponents();
        initData();
	}
	private void initData(){
		showEmptyData();
		List list= new ArrayList();
		if (list != null && list.size() > 0) {
			initTable(list);
			
		} else {
			initTable(new Vector());
		}
		initTbSuccess(list);
	}
	private void initUIComponents(){
		List list = new ArrayList();
		list = btplsDownloadParaAction.findScmManuFactoryFromBtplsDownloadPara();
		List contracts = new ArrayList();
	};
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
	 * This method initializes top	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan1() {
		if (pan1 == null) {
			pan1 = new JPanel();
			pan1.setLayout(new BorderLayout());
			pan1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pan1;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("申请单号：");
			jLabel.setBounds(new Rectangle(5, 5, 37, 18));
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
			jLabel2 = new JLabel();
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setText("申请日期");
			jPanel = new JPanel();
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(5);
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSerialNumber(), null);
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
	 * This method initializes tfSerialNumber	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSerialNumber() {
		if (tfSerialNumber == null) {
			tfSerialNumber = new JTextField();
			tfSerialNumber.setPreferredSize(new java.awt.Dimension(100, 24));
		}
		return tfSerialNumber;
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
//			cbbEndDate.setDate(new Date());
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnQuiry	
	 * 根据当前登录用户以及日期限制来查询数据
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuiry() {
		if (btnQuiry == null) {
			btnQuiry = new JButton();
			btnQuiry.setText("查询");
			btnQuiry.addActionListener(new java.awt.event.ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					HashMap<String, Object> condition = new HashMap<String, Object>();
					checkData();
					String currComCode= ((Company)CommonVars.getCurrUser().getCompany()).getCode();
					System.out.println("currComCode:"+currComCode);
					condition = fillQueryCondition();
					//获取参数设置连接数据
					parameterSet = fptManageAction .findTransParameterSet(new Request(CommonVars .getCurrUser(), true));
					Integer projectType = parameterSet.getProjectType();
					btnQuiry.setEnabled(false);
					new Find(condition,currComCode,projectType).execute();
				}
			});
		}
		return btnQuiry;
	}
	
	class Find extends SwingWorker {
		private HashMap<String, Object> condition;
		private BtplsDownloadPara para=null;
        public Find(HashMap<String, Object> map,String currComCode,Integer projectType) {
        	this.condition = map;
        	btpList= btplsDownloadParaAction.findScmManuFactoryFromBtplsDownloadPara();
        	if(btpList.size()==0){
        		 JOptionPane.showMessageDialog(FmBtplsFptAppDownData.this,
                         "获取当 前用户深加工结转远程连接失败！" , "提示" , 2);
        		 return;
        	}
        	CommonProgress.showProgressDialog(FmBtplsFptAppDownData.this);
			CommonProgress.setMessage("正在查询,请耐心等待...");
        	para = (BtplsDownloadPara)btpList.get(0); 			
			parameter.put("ipAddre", para.getIpAddre());
			parameter.put("port",para.getPort());
			parameter.put("companyCode", para.getScmCoc().getCode());
			parameter.put("projectType", String.valueOf(ProjectType.BCS));
        }
        @Override
        protected Object doInBackground() throws Exception { // 后台计算
			return BtplsDownloadLogic.findMessageCustomsCoverHead(parameter, condition);
       }
        
        @Override
        protected void done() {// 更新视图
             List list = null;
              try {
				list = (List) this.get();
				if (list == null) {
					list = new ArrayList();
				}
				initTable(list);
				tableModel.setList(list);
             } catch (Exception e) {
                   e.printStackTrace();
                   CommonProgress.closeProgressDialog();
                   JOptionPane.showMessageDialog(FmBtplsFptAppDownData.this,
                                "远程获取数据失败：！", "提示" , 2);
             }finally {
            	 btnQuiry.setEnabled(true);
 				CommonProgress.closeProgressDialog();
 			}
       }
 }
	/**
	 * 检查数据
	 */
	private boolean checkData() {
		if ("".equals(tfSerialNumber.getText().trim())
				&& cbbBeginDate.getDate() == null
				&& cbbEndDate.getDate() == null) {
			JOptionPane.showMessageDialog(FmBtplsFptAppDownData.this,
					"为了下载速度且同步准确，请至少选择一个查询条件!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
	/**
	 * This method initializes btnQuiry	
	 * 	申请单条件查询条件
	 * @return javax.swing.JButton	
	 */
	public HashMap<String, Object> fillQueryCondition() {
		HashMap<String, Object> condition = new HashMap<String, Object>();
		condition.put(" a.appNo  = ?",tfSerialNumber.getText().trim());
		condition.put(" a.startDate >= ? ", cbbBeginDate.getDate());
		condition.put(" a.expiryDate <= ? ", cbbEndDate.getDate());
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
					List<MessageCustomsCoverHeadTemp> seList = getCommodityList();
					List<String> selIdList = new ArrayList<String>();
					if (seList.size()==0) {
						JOptionPane.showMessageDialog(null, "请选择申请单号!", "提示!",
								JOptionPane.OK_OPTION);
						return;
					}
					for (int i = 0; i < seList.size(); i++) {
						selIdList.add(seList.get(i).getId());
					}
					String currCompany =((Company)CommonVars.getCurrUser().getCompany()).getCode();
					btnDownload.setEnabled(false);
					CommonProgress.setMessage("正在同步,请耐心等待...");
					parameter.put("idList", selIdList);	
					parameter.put("condition", fillQueryCondition());	
					parameter.put("currCompany", currCompany);	
					
					List list = btplsDownloadAction.downloadBtplsFptAppBody(new Request(CommonVars.getCurrUser()), parameter);
					
					initTbSuccess(list);
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null,	"同步成功！一共同步" + list.size() + "条申请单", "提示", 0);	
					btnDownload.setEnabled(false);
				}
			});			
		}
		return btnDownload;
	}


	
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					List<MessageCustomsCoverHeadTemp> list = tableModel.getCurrentRows();
					for (MessageCustomsCoverHeadTemp item : list) {
						item.setIsSelected(!item.getIsSelected());
					}
				}
			});
		}
		
		return jTable;
	}
	/**
	 * 获得选中申请单信息
	 */
	public List getCommodityList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof MessageCustomsCoverHeadTemp) {
				MessageCustomsCoverHeadTemp temp = (MessageCustomsCoverHeadTemp) list
						.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}
	
	
	private void initTable(List list){
		if (list == null && list.size() < 0) {
			list = new Vector(); 
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 60));//选择1
				list.add(addColumn("关封号", "coverNO", 100)); // 关封号//2
				//list.add(addColumn("申报状态", "declareState", 100)); // 申报状态3
				list.add(addColumn("手册号", "emsNoIn", 100));//3
				list.add(addColumn("转出企业代码", "outTradeCode", 100));//4
				list.add(addColumn("转出企业名称", "outTradeName", 100));//5
				list.add(addColumn("转出海关", "outCustoms", 100));//6
				list.add(addColumn("转入企业代码", "inTradeCode", 100));//7
				list.add(addColumn("转入企业名称", "inTradeName", 100));//8
				list.add(addColumn("转入海关", "inCustoms", 100));//9
				list.add(addColumn("关封有效开始日期", "startDate", 100));//10
				list.add(addColumn("关封有效截止日期", "expiryDate", 100));//11
				list.add(addColumn("是否已下载", "isJBCUSDown", 60));//是否已下载12
				return list;
			}
		};
		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jTable.getTableHeader().setReorderingAllowed(false);
		tableModelAdapter.setEditableColumn(1);
		tableModel = (JTableListModel) jTable.getModel();
		tableModel.setAllowSetValue(true);
		jTable.getColumnModel().getColumn(1).setHeaderRenderer(
				new CheckBoxHeader(false));
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new TableCheckBoxRender());
	}
	/**
	 * 初始化下载报关单成功的表格
	 * @param list
	 */
	private void initTbSuccess(List list){
		if (list == null && list.size() < 0) {
			list = new Vector(); 
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("关封号", "coverNO", 100)); //2
				list.add(addColumn("转出企业代码", "outTradeCode", 100));//3
				list.add(addColumn("转出企业名称", "outTradeName", 100));//4
				list.add(addColumn("转出海关", "outCustoms", 100));//5
				list.add(addColumn("转入企业代码", "inTradeCode", 100));//6
				list.add(addColumn("转入企业名称", "inTradeName", 100));//7
				list.add(addColumn("转入海关", "inCustoms", 100));//8
				list.add(addColumn("关封有效开始日期", "startDate", 100));//9
				list.add(addColumn("关封有效截止日期", "expiryDate", 100));//10
				list.add(addColumn("是否已下载", "isJBCUSDown", 60));//是否已下载11
				return list;
			}
		};
		JTableListModel.dataBind(tbSuccess, list, tableModelAdapter,
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tbSuccess.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
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
			jTabbedPane.addTab("转厂申请表", null, getPan1(), null);
			jTabbedPane.addTab("成功下载转厂申请表", null, getPan2(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pan2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan2() {
		if (pan2 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			pan2 = new JPanel();
			pan2.setLayout(new GridBagLayout());
			pan2.add(getJScrollPane1(), gridBagConstraints);
		}
		return pan2;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getSuccessTab());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes successTab	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getSuccessTab() {
		if (tbSuccess == null) {
			tbSuccess = new JTable();
		}
		return tbSuccess;
	}
	
	/**
	 * 显示空数据
	 * 
	 */
	protected void showEmptyData() {
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
