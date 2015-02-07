package com.bestway.common.client.fpt.btpls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.bcsinnermerge.entity.BcsProductScaleInfo;
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
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadAction;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadParaAction;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationHeadTemp;
import com.bestway.common.fpt.btplsinput.logic.BtplsDownloadLogic;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings( { "unchecked", "serial" })
public class FmBtpltsCustomsDeclarationDownData extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfSerialNumber = null;
	private JLabel jLabel1 = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:
	/**
	 * 开始日期
	 */
	private JCalendarComboBox cbbBeginDate = null;

	/**
	 * 结束日期
	 */
	private JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel2 = null;
	private JButton btnQuiry = null;
	private JScrollPane jScrollPane = null;
	private JButton btnDownload = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModelSuccess = null;
	private BtplsDownloadAction btplsDownloadAction = null;
	private BtplsDownloadParaAction btplsDownloadParaAction = null;
	private JRadioButton rbIn = null;
	private JRadioButton rbOut = null;
	private boolean isOut = false;
	private JTabbedPane jTabbedPane = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbSuccess = null;
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
	/**
	 * This method initializes
	 * 
	 */
	public FmBtpltsCustomsDeclarationDownData() {
		super();
		btplsDownloadAction = (BtplsDownloadAction) CommonVars
				.getApplicationContext().getBean("btplsDownloadAction");
		btplsDownloadParaAction = (BtplsDownloadParaAction) CommonVars
				.getApplicationContext().getBean("btplsDownloadParaAction");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
		.getBean("contractAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		parameterSet = fptManageAction
		 .findTransParameterSet(new Request(CommonVars
		 .getCurrUser(), true));
		initialize();
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("进出口报关单下载");
		this.setSize(new Dimension(900, 600));
		initUIComponents();
	}

	private void initData() {
		
		// 初始化出口口岸
		DefaultComboBoxModel customModel = CustomBaseModel.getInstance().getCustomModel();
		// 初始化运输方式
		DefaultComboBoxModel transfModel = CustomBaseModel.getInstance().getTransfModel();
		// 初始化贸易方式
		DefaultComboBoxModel tradeModel = CustomBaseModel.getInstance().getTradeModel();
		
		for (int i = 0; i < customModel.getSize(); i++) {
			Customs obj = (Customs)customModel.getElementAt(i);
			customList.add(obj);
		}
		for (int i = 0; i < transfModel.getSize(); i++) {
			Transf obj = (Transf)transfModel.getElementAt(i);
			transfList.add(obj);
		}
		for (int i = 0; i < tradeModel.getSize(); i++) {
			Trade obj = (Trade)tradeModel.getElementAt(i);
			tradeList.add(obj);
		}
		
		showEmptyData();
		List list = new ArrayList();
		if (list != null && list.size() > 0) {
			initTable(list);
		} else {
			initTable(new Vector());
			initTbSuccess(new Vector());
		}
	}

	private void initUIComponents() {
		List list = new ArrayList();
		list = btplsDownloadParaAction.findScmManuFactoryFromBtplsDownloadPara();
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		List contracts = new ArrayList();
		Integer projectType = parameterSet.getProjectType();
		if (projectType.equals(ProjectType.BCS)) {
			// 电子化手册
			cbbEmsNo.removeAllItems();
			contracts = contractAction.findContract(new Request(CommonVars.getCurrUser()));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmsNo.addItem((Contract) contracts.get(i));
				}
				this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance()
						.getRender("emsNo", "expContractNo", 100, 150)
						.setForegroundColor(java.awt.Color.red));
			} else if (projectType.equals(ProjectType.BCUS)) {
				// 电子账册
				contracts = manualDeclareAction
						.findEmsHeadH2kAll(new Request(CommonVars.getCurrUser()));
				if (contracts != null && contracts.size() > 0) {
					for (int i = 0; i < contracts.size(); i++) {
						this.cbbEmsNo.addItem((EmsHeadH2k) contracts.get(i));
					}
					this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance()
							.getRender("preEmsNo", "emsNo", 0, 150));
				}
				if (this.cbbEmsNo.getItemCount() > 0) {
					this.cbbEmsNo.setSelectedIndex(0);
				}
			}
		}
		initData();
		getButtonGroup();
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
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("流水号：");
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
			jPanel.add(jLabel, null);
			jPanel.add(getTfSerialNumber(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuiry(), null);
			jPanel.add(getBtnDownload(), null);
			//jPanel.add(getRbIn(), null);
			//jPanel.add(getRbOut(), null);
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
			btnQuiry.setText("下载");
			btnQuiry.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {					
					HashMap<String, Object> condition = null;
					BtplsDownloadPara para = null;
					if(!checkData()){
						return;
					}
					ScmCoc sc = (ScmCoc) cbbScmCoc.getSelectedItem();
					para = (BtplsDownloadPara) btplsDownloadParaAction
							.findBtplsDownloadParaByScm(sc).get(0);
					
					Integer projectType = parameterSet.getProjectType();
					condition = fillQueryCondition();
					if (para == null || para.getIpAddre() == null
							|| para.getPort() == null) {
						return;
					}
					new Find(condition, para,projectType).execute();
				}
			});
		}
		return btnQuiry;
	}

	class Find extends SwingWorker {
		private HashMap condition;
		private BtplsDownloadPara para;
		private HashMap<String, String> parameter = new HashMap<String, String>();
		private Integer projectType;

		
		public Find(HashMap map, BtplsDownloadPara para,Integer projectType) {
			this.condition = map;
			this.para = para;
			this.projectType = projectType;
			parameter.put("ipAddre", para.getIpAddre());
			parameter.put("port", para.getPort());
			parameter.put("companyCode", para.getScmCoc().getCode());
			parameter.put("projectType", projectType.toString());
		}

		@Override
		protected Object doInBackground() throws Exception { // 后台计算
			// 查询返回结果
			return BtplsDownloadLogic.findCustomsDeclarationHead(parameter, condition);
		}

		@Override
		protected void done() {// 更新视图
			List list = null;
			try {
				CommonProgress
						.showProgressDialog(FmBtpltsCustomsDeclarationDownData.this);
				CommonProgress.setMessage("正下载报关单资料...");
				list = (List) this.get();
				if (list == null) {
					list = new ArrayList();
				}
				initTable(list);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						FmBtpltsCustomsDeclarationDownData.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			}finally{
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 检查数据
	 */
	private boolean checkData() {
		ScmCoc  sc = (ScmCoc) this.cbbScmCoc.getSelectedItem();
		if (sc== null) {
			JOptionPane.showMessageDialog(
					FmBtpltsCustomsDeclarationDownData.this,
					"没有选择供应商或者客户", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if ("".equals(this.tfSerialNumber.getText().trim())
				&& this.cbbBeginDate.getDate() == null
				&& this.cbbEndDate.getDate() == null) {
			JOptionPane.showMessageDialog(FmBtpltsCustomsDeclarationDownData.this,
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
		ScmCoc sc = (ScmCoc) this.cbbScmCoc.getSelectedItem();
		if (sc == null) {
			JOptionPane.showMessageDialog(
					FmBtpltsCustomsDeclarationDownData.this, "没有选择供应商或者客户",
					"提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
		   condition.put("a.companyCode = ?",sc.getCode());
		}
		condition.put("serialNumber = ?", tfSerialNumber.getText().trim());
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
					List liste = tableModel	.getSelectedDataByBoolProperty("isSelected");
					
					
					HashMap<String, Object> condition = new HashMap<String, Object>();
					ScmCoc sc = (ScmCoc) cbbScmCoc.getSelectedItem();
					BtplsDownloadPara para = (BtplsDownloadPara) btplsDownloadParaAction
							.findBtplsDownloadParaByScm(sc).get(0);
					
					// 取得要下载的报关单id
					List idList = getSelectId();

					// 报关模式
					String projectType = "1";

					condition.put("port", para.getPort());
					condition.put("ipAddre", para.getIpAddre());
					condition.put("idList", idList);
					condition.put("projectType", projectType);
					
					// 下载报关单
					List list = btplsDownloadAction.downloadCustomsDeclaration(
							new Request(CommonVars.getCurrUser()), condition);
					
					
					
					// 把下载的报关单显示到下载成功面板
					initTbSuccess(list);
					
					JOptionPane.showMessageDialog(FmBtpltsCustomsDeclarationDownData.this,
							"同步成功！一共同步" + list.size() + "条报关单", "提示", 0);
				}
			});
		}
		return btnDownload;
	}
	
	private List<String> getSelectId() {
		List<String> ids = new ArrayList<String>();
		List<CustomsDeclarationHeadTemp> list = tableModel.getCurrentRows();
		CustomsDeclarationHeadTemp h = null;
		for (int i = 0; i < list.size(); i++) {
			h = list.get(i);
			ids.add(h.getId());
		}
		
		return ids;
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

	private void initTable(List list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
			JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("选择", "isSelected", 60));// 选择1
					list.add(addColumn("手册号", "emsNo", 100));// 2
					list.add(addColumn("报关单号", "rollCovorNO", 100));// 3
					list.add(addColumn("企业海关编码", "customsCode", 100));// 4
					list.add(addColumn("企业海关名称", "companyName", 100));// 5
					list.add(addColumn("申报日期", "sendDate", 100));// 6
					list.add(addColumn("进出口岸", "portLin", 100));// 7
					list.add(addColumn("贸易方式", "trade", 100));// 8
					list.add(addColumn("报送海关", "customs", 100));// 9
					list.add(addColumn("毛重", "grossWeight", 100));// 10
					list.add(addColumn("净重", "netWeight", 100));// 11
					list.add(addColumn("件数", "pieces", 100));// 12
					list.add(addColumn("是否已下载", "isJBCUSDown", 60));// 是否已下载13
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
			jTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){//进出口岸
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					 super.getTableCellRendererComponent(table,
								value, isSelected, hasFocus, row, column);
					for (int i = 0; i < customList.size(); i++) {
							Customs customs =	customList.get(i);
							if(customs.getCode().equals(value)){
								super.setText(customs.getName());
								break;
							}
					}
					return this;
				}
			});
			jTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){//贸易方式
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					 super.getTableCellRendererComponent(table,
								value,isSelected, hasFocus, row, column);
							for (int i = 0; i < tradeList.size(); i++) {
								Trade trans = tradeList.get(i);
								if (trans.getCode().equals(value)) {
									super.setText(trans.getName());
									break;
								}
							}
					return this;
				}
			});
			jTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer(){//进出口岸==报送海关
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					 super.getTableCellRendererComponent(table,
								value, isSelected, hasFocus, row, column);
					for (int i = 0; i < customList.size(); i++) {
							Customs customs =	customList.get(i);
							if(customs.getCode().equals(value)){
								super.setText(customs.getName());
								break;
							}
					}
					return this;
				}
			});
			
			jTable.getColumnModel().getColumn(13).setCellRenderer(
					new TableCheckBoxRender());
		} else {
			tableModel.setList(list);
		}
	}

	/**
	 * 初始化下载报关单成功的表格
	 * 
	 * @param list
	 */
	private void initTbSuccess(List list) {
		if (list == null && list.size() < 0) {
			list = new Vector();
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("流水号", "serialNumber", 60));// 选择1
				list.add(addColumn("手册号", "emsHeadH2k", 100));// 2
				list.add(addColumn("报关单号", "rollCovorNO", 100));// 3
				list.add(addColumn("企业海关编码", "tradeCode", 100));// 4
				list.add(addColumn("企业海关名称", "tradeName", 100));// 5
				list.add(addColumn("申报日期", "declarationDate", 100));// 6
				list.add(addColumn("进出口岸", "portLin", 100));// 7
				list.add(addColumn("贸易方式", "tradeMode.name", 100));// 8
				list.add(addColumn("报送海关", "customs", 100));// 9
				list.add(addColumn("毛重", "grossWeight", 100));// 10
				list.add(addColumn("净重", "netWeight", 100));// 11
				list.add(addColumn("件数", "pieces", 100));// 12
				return list;
			}
		};
		JTableListModel.dataBind(tbSuccess, list, tableModelAdapter,
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tbSuccess.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){//进出口岸
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				 super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
				for (int i = 0; i < customList.size(); i++) {
						Customs customs =	customList.get(i);
						if(customs.getCode().equals(value)){
							super.setText(customs.getName());
							break;
						}
				}
				return this;
			}
		});
		jTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){//贸易方式
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				 super.getTableCellRendererComponent(table,
							value,isSelected, hasFocus, row, column);
						for (int i = 0; i < tradeList.size(); i++) {
							Trade trans = tradeList.get(i);
							if (trans.getCode().equals(value)) {
								super.setText(trans.getName());
								break;
							}
						}
				return this;
			}
		});
		jTable.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer(){//进出口岸==报送海关
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				 super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
				for (int i = 0; i < customList.size(); i++) {
						Customs customs =	customList.get(i);
						if(customs.getCode().equals(value)){
							super.setText(customs.getName());
							break;
						}
				}
				return this;
			}
		});
	}

	/**
	 * This method initializes rbIn
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIn() {
		if (rbIn == null) {
			rbIn = new JRadioButton();
			rbIn.setText("进口");
			rbIn.setSelected(true);
			rbIn.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					isOut = false;
				}
			});
		}
		return rbIn;
	}

	/**
	 * This method initializes rbOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOut() {
		if (rbOut == null) {
			rbOut = new JRadioButton();
			rbOut.setText("出口");
			rbOut.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					isOut = true;
				}
			});
		}
		return rbOut;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbIn());
			buttonGroup.add(this.getRbOut());
		}
		return buttonGroup;
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
			jTabbedPane.addTab("报关单", null, getJScrollPane(), null);
			jTabbedPane.addTab("下载成功报关单", null, getJScrollPane1(), null);
		}
		return jTabbedPane;
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
