package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.owp.action.OwpAppAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpSignInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 外发加工申请表窗体
 * @author wss
 */
public class DgOwpAppHead extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSaveHead = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddSend = null;

	private JPanel jPanel = null;

	private JTable tbSend = null;

	private JScrollPane jScrollPane = null;
	
	/**
	 * 当前的外发加工申请表表头
	 */
	private OwpAppHead owpAppHead = null;  //  @jve:decl-index=0:
	
	/**
	 * 当前的外发加工 外发货物
	 */
	private OwpAppSendItem owpAppSendItem = null;
	
	/**
	 * 当前的外发加工 收回货物
	 */
	private OwpAppRecvItem owpAppRecvItem = null;
	
	/**
	 * 外发加工申请表表头tableModel
	 */
	private JTableListModel tableModelHead = null;
	
	/**
	 * 外发货物商品tableModel
	 */
	private JTableListModel tableModelSend = null;

	/**
	 * 收回货物商品tableModel
	 */
	private JTableListModel tableModelRecv = null;
	
	/**
	 * 外发加工远程服务接口
	 */
	private OwpAppAction owpAppAction = null;
	
	/**
	 * 当前外发加工申请表的审批状态
	 */
	private String declareState = null;


	/**
	 * 基本信息 当前状态
	 */
	private int dataStateHead = DataState.BROWSE;
	
	/**
	 * 外发货物信息 当前状态
	 */
	private int dataStateSend = DataState.BROWSE;
	
	/**
	 * 收回货物信息 当前状态
	 */
	private int dataStateRecv = DataState.BROWSE;
	
	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;


	private JButton btnEditHead = null;


	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JTextField tfAppNo = null;

	private JTextField tfSeqNo = null;

	private JTextField tfCopAppNo = null;

	private JComboBox cbbAppClass = null;

	private JTextField tfTradeCode = null;

	private JTextField tfTradeName = null;

	private JTextField tfAgentCode = null;

	private JTextField tfAgentName = null;

	private JTextField tfEmsNo = null;

	private JComboBox cbbMastCust = null;

	private JTextField tfContrNo = null;

	private JFormattedTextField ftfConveyDay = null;

	private JTextField tfCorp = null;

	private JTextField tfDecl = null;

	private JComboBox cbbInMastCust = null;

	private JTextField tfInTradeCode = null;

	private JTextField tfInTradeName = null;

	private JTextField tfInCorp = null;
	

	private JTextField tfNote = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel24 = null;

	private JLabel jLabel25 = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel27 = null;

	private JLabel jLabel28 = null;

	private JTextField tfSendListNo = null;

	private JTextField tfSendHsSpec = null;

	private JTextField tfSendHsCode = null;

	private JTextField tfSendHsName = null;

	private JComboBox cbbSendHsUnit = null;

	private JFormattedTextField ftfSendQty = null;

	private JComboBox cbbSendModifyMark = null;

	private JTextField tfSendNote = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbRecv = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel211 = null;

	private JLabel jLabel221 = null;

	private JLabel jLabel231 = null;

	private JLabel jLabel241 = null;

	private JLabel jLabel251 = null;

	private JLabel jLabel261 = null;

	private JLabel jLabel271 = null;

	private JLabel jLabel281 = null;

	private JTextField tfRecvListNo = null;

	private JTextField tfRecvHsSpec = null;

	private JTextField tfRecvHsCode = null;

	private JTextField tfRecvHsName = null;

	private JComboBox cbbRecvHsUnit = null;

	private JFormattedTextField ftfRecvQty = null;

	private JComboBox cbbRecvModifyMark = null;

	private JTextField tfRecvNote = null;

	private JToolBar jToolBar11 = null;

	private JButton btnAddRecv = null;
	
	private JCalendarComboBox cbbAppDate = null;

	private JComboBox cbbInDistrict = null;

	private JButton btnEditSend = null;

	private JButton btnDelSend = null;

	private JButton btnSaveSend = null;

	private JButton btnEditRecv = null;

	private JButton btnDelRecv = null;

	private JButton btnSaveRecv = null;

	private JButton btnCancelHead = null;

	private JButton btnPrintHead = null;

	private JPanel pnHead = null;

	private JButton btnCancelSend = null;

	private JButton btnCancelRecv = null;

	private JButton btnEmsNo = null;

	private JButton btnInTradeCode = null;

	private JButton btnSendHsCode = null;

	private JButton btnRecvHsCode = null;

	private JLabel jLabel29 = null;

	private JComboBox cbbProjectType = null;

	private JButton btnImport = null;

	private JPanel jPanel4 = null;

	private JTextField tfInputMan = null;

	private JLabel jLabel33 = null;

	private JButton btnSendPrev = null;

	private JButton btnSendNext = null;

	private JButton jButton = null;

	private JButton btnRecvPrev = null;

	private JButton btnRecvNext = null;

	private JButton jButton2 = null;

	private JLabel jLabel30 = null;

	private JCalendarComboBox cbbInputDate = null;

	private JButton btnRefresh = null;

	private JButton btnImportSend = null;

	private JButton btnImportRecv = null;

	private JButton btnExitSend = null;

	private JButton btnExitRecv = null;

	private JPopupMenu pmSendAdd = null;  //  @jve:decl-index=0:visual-constraint="820,124"

	private JPopupMenu pmRecvAdd = null;  //  @jve:decl-index=0:visual-constraint="906,129"

	private JMenuItem miAddFromContractSend = null;  //  @jve:decl-index=0:visual-constraint="818,184"

	private JMenuItem miHandImputSend = null;  //  @jve:decl-index=0:visual-constraint="818,217"

	private JMenuItem miAddFromContractRecv = null;  //  @jve:decl-index=0:visual-constraint="940,184"

	private JMenuItem miHandInputRecv = null;  //  @jve:decl-index=0:visual-constraint="930,222"

	private JLabel jLabel31 = null;

	private JTextField tfSendContractNo = null;

	private JLabel jLabel32 = null;

	private JTextField tfRecvContractNo = null;
	
	/** 
	 * 缺省的格式工厂 
	 */
	private DefaultFormatterFactory defaultFormatterFactory = null;
	
	/** 
	 * 数字格式
	 */
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private JButton btnSortSend = null;

	private JButton btnSortRecv = null;

	/**
	 * 构造方法
	 * @throws java.awt.HeadlessException
	 */
	public DgOwpAppHead() {
		super();
		/**
		 * 初始化外发加工远程服务接口
		 */
		owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																.getBean("owpAppAction");
		/**
		 * 海关基础资料服务器端接口
		 */
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
																.getBean("customBaseAction");
		initialize();
		
		initUIComponents();
	}
	
	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		
		//录入人
		
		//录入日期
		
		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "电子化手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(2), "电子手册"));
		this.initComboBoxRenderer(cbbProjectType);

		
		//加工期限
		CustomFormattedTextFieldUtils.setFormatterFactory(this.ftfConveyDay,0);
		
		//委托方海关
		this.cbbMastCust.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.initComboBoxRenderer(cbbMastCust);
		
		//承揽方海关
		this.cbbInMastCust.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.initComboBoxRenderer(cbbInMastCust);
		
		//目的地
		this.cbbInDistrict.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		this.initComboBoxRenderer(cbbInDistrict);
		
		//(外发货物)计量单位
		this.cbbSendHsUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.initComboBoxRenderer(cbbSendHsUnit);
		
		//(外发货物)申报数量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.ftfSendQty,0);
		
		//(外发货物)修改标志
		this.cbbSendModifyMark.removeAllItems();
		this.cbbSendModifyMark.addItem(new ItemProperty(ModifyMarkState.UNCHANGE,"未修改"));
		this.cbbSendModifyMark.addItem(new ItemProperty(ModifyMarkState.MODIFIED, "修改"));
		this.cbbSendModifyMark.addItem(new ItemProperty(ModifyMarkState.DELETED, "删除"));
		this.cbbSendModifyMark.addItem(new ItemProperty(ModifyMarkState.ADDED, "增加"));
		
		this.initComboBoxRenderer(cbbSendModifyMark);
		
		
		//(收回货物)计量单位
		this.cbbRecvHsUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.initComboBoxRenderer(cbbRecvHsUnit);

		//(收回货物)申报数量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.ftfRecvQty,0);
		
		//(收回货物)修改标志
		this.cbbRecvModifyMark.removeAllItems();
		this.cbbRecvModifyMark.addItem(new ItemProperty(ModifyMarkState.UNCHANGE,"未修改"));
		this.cbbRecvModifyMark.addItem(new ItemProperty(ModifyMarkState.MODIFIED, "修改"));
		this.cbbRecvModifyMark.addItem(new ItemProperty(ModifyMarkState.DELETED, "删除"));
		this.cbbRecvModifyMark.addItem(new ItemProperty(ModifyMarkState.ADDED, "增加"));
		
		this.initComboBoxRenderer(cbbRecvModifyMark);

		
	}
	
	/**
	 * 初始化Cbb呈现
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(770, 507);
		this.setContentPane(getJContentPane());
		this.setTitle("外发加工申请表");
	}

	/**
	 * 设置显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (tableModelHead != null) {
				owpAppHead = (OwpAppHead) tableModelHead.getCurrentRow();
				declareState = owpAppHead.getDeclareState();
			}
			
			setStateHead();
//			setStateSend();
//			setStateRecv();
			
			showHeadData(owpAppHead);

//			initTableRecv();
//			initTableSend();
//			showCurrentDataSend((OwpSendItem)tableModelSend.getCurrentRow());
//			showCurrentDataRecv((OwpRecvItem)tableModelRecv.getCurrentRow());
		}
		super.setVisible(b);
	}

	/**
	 * 窗体状态控制(基本信息.表头)
	 */
	private void setStateHead() {
		//修改按钮
		this.btnEditHead.setEnabled(dataStateHead == DataState.BROWSE
						&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
								.equals(declareState)));
		//保存按钮
		this.btnSaveHead.setEnabled(dataStateHead != DataState.BROWSE);
		
		//取消按钮
		this.btnCancelHead.setEnabled(dataStateHead != DataState.BROWSE);
		
		//导入按钮
		this.btnImport.setEnabled(dataStateHead != DataState.BROWSE);
		
		this.cbbProjectType.setEnabled(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		
		this.tfAgentCode.setEditable(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.tfAgentName.setEditable(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));

		this.btnEmsNo.setEnabled(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.cbbMastCust.setEnabled(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.ftfConveyDay.setEditable(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.tfCorp.setEditable(dataStateHead != DataState.BROWSE);//
		
		this.tfDecl.setEditable(dataStateHead != DataState.BROWSE);//
		
		this.cbbInMastCust.setEnabled(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.btnInTradeCode.setEnabled(dataStateHead != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.tfInCorp.setEditable(dataStateHead != DataState.BROWSE);//
		
		this.cbbInDistrict.setEnabled(dataStateHead != DataState.BROWSE 
						&& DeclareState.APPLY_POR.equals(declareState));
		
		this.tfNote.setEditable(dataStateHead != DataState.BROWSE 
						&& DeclareState.APPLY_POR.equals(declareState) );
	}

	/**
	 * 窗体状态控制(外发货物)
	 */
	private void setStateSend() {
		
		//新增
		this.btnAddSend.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//修改
		this.btnEditSend.setEnabled(dataStateSend != DataState.EDIT 
										&&(DeclareState.APPLY_POR.equals(declareState) 
												|| DeclareState.CHANGING_EXE.equals(declareState))); 
		
		//删除
		this.btnDelSend.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//保存
		this.btnSaveSend.setEnabled((dataStateSend != DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState)));
		
		//新增
		this.btnImportSend.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//取消
		this.btnCancelSend.setEnabled((dataStateSend != DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState)));
		

		
		
//		this.tfSendListNo.setEditable(dataStateSend != DataState.BROWSE
//									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
		this.tfSendHsSpec.setEditable(dataStateSend != DataState.BROWSE
									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
//		this.tfSendHsCode.setEditable(dataStateSend != DataState.BROWSE
//									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
		this.btnSendHsCode.setEnabled(dataStateSend != DataState.BROWSE
									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
		this.tfSendHsName.setEditable(dataStateSend != DataState.BROWSE
									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
		this.cbbSendHsUnit.setEnabled(dataStateSend != DataState.BROWSE
									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
		this.ftfSendQty.setEditable(dataStateSend != DataState.BROWSE);//
//		this.cbbSendModifyMark.setEnabled(!(dataStateSend == DataState.BROWSE));
		this.tfSendNote.setEditable(dataStateSend != DataState.BROWSE
									&& ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark()));
	}
	
	/**
	 * 显示当前 外发货物商品 信息
	 */
	private void showCurrentDataSend(OwpAppSendItem item){
		if(item != null){
			tfSendListNo.setText(""+item.getSeqNum());
			tfSendHsSpec.setText(item.getHsSpec());
			tfSendHsCode.setText(item.getComplex() == null ? "":item.getComplex().getCode());
			tfSendHsName.setText(item.getHsName());
			cbbSendHsUnit.setSelectedItem(item.getHsUnit());
			ftfSendQty.setValue(item.getQty());
			tfSendContractNo.setText(item.getTrNo() == null ? "":"" + item.getTrNo());
			
			cbbSendModifyMark.setSelectedIndex(ItemProperty.getIndexByCode(item.getModifyMark(),
					cbbSendModifyMark));
		}
	}
	
	/**
	 * 窗体状态控制(收回货物)
	 */
	private void setStateRecv(){
		
		//新增
		this.btnAddRecv.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//修改
		this.btnEditRecv.setEnabled(dataStateRecv != DataState.EDIT && 
									  (DeclareState.APPLY_POR.equals(declareState) 
											  || DeclareState.CHANGING_EXE.equals(declareState)));
		
		//删除
		this.btnDelRecv.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//保存
		this.btnSaveRecv.setEnabled((dataStateRecv != DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState)));
		
		//新增
		this.btnImportRecv.setEnabled(DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState));
		
		//取消
		this.btnCancelRecv.setEnabled((dataStateRecv != DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
						.equals(declareState)));
		
		
		this.tfRecvListNo.setEditable(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		this.tfRecvHsSpec.setEditable(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		this.tfRecvHsCode.setEditable(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		this.btnRecvHsCode.setEnabled(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		this.tfRecvHsName.setEditable(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		this.cbbRecvHsUnit.setEnabled(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
		
		this.ftfRecvQty.setEditable(!(dataStateRecv == DataState.BROWSE));//
		
//		this.cbbRecvModifyMark.setEnabled(!(dataStateRecv == DataState.BROWSE));
		
		this.tfRecvNote.setEditable(!(dataStateRecv == DataState.BROWSE)
				&& ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark()));
	}
	
	/**
	 * 显示当前所选的 收回货物商品 信息
	 */
	private void showCurrentDataRecv(OwpAppRecvItem item){
		if(item != null){
			tfRecvListNo.setText(""+item.getSeqNum());
			tfRecvHsSpec.setText(item.getHsSpec());
			tfRecvHsCode.setText(item.getComplex() == null ? "":item.getComplex().getCode());
			tfRecvHsName.setText(item.getHsName());
			cbbRecvHsUnit.setSelectedItem(item.getHsUnit());
			ftfRecvQty.setValue(item.getQty());
			tfRecvContractNo.setText(item.getTrNo() == null ? "":"" + item.getTrNo());
			
			cbbRecvModifyMark.setSelectedIndex(ItemProperty.getIndexByCode(item.getModifyMark(),
					cbbRecvModifyMark));		}
	}
	
	/**
	 * 显示申请表基本信息
	 * @param head
	 */
	private void showHeadData(OwpAppHead head) {
		if (head == null) {
			return;
		}
		
		tfInputMan.setText(head.getInputMan());//录入人
		cbbInputDate.setDate(head.getInputDate());//录入日期
		
		this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(String
				.valueOf(head.getEmsType()),
				cbbProjectType));
		
		this.tfAppNo.setText(head.getAppNo());
		this.tfSeqNo.setText(head.getSeqNo());
		this.tfCopAppNo.setText(head.getCopAppNo());
		this.tfTradeCode.setText(head.getTradeCode());
		this.tfTradeName.setText(head.getTradeName());
		this.tfAgentCode.setText(head.getAgentCode());
		this.tfAgentName.setText(head.getAgentName());
		this.tfEmsNo.setText(head.getEmsNo());
		this.cbbMastCust.setSelectedItem(head.getMastCust());
		this.tfContrNo.setText(head.getContrNo());
		this.ftfConveyDay.setValue(head.getConveyDay());
		this.tfCorp.setText(head.getCorp());
		this.cbbAppDate.setDate(head.getAppDate());
		this.tfDecl.setText(head.getDecl());
		this.cbbInMastCust.setSelectedItem(head.getInMastCust());
		this.tfInTradeCode.setText(head.getInTradeCode());
		this.tfInTradeName.setText(head.getInTradeName());
		this.tfInCorp.setText(head.getInCorp());
		this.cbbInDistrict.setSelectedItem(head.getInDistrict());
		this.tfNote.setText(head.getNote());
		
	}

	/**
	 * 初始化数据 外发货物表
	 * (OwpSendItem)
	 */
	private void initTableSend() {
		OwpAppHead head = (OwpAppHead) tableModelHead.getCurrentRow();
		if (head == null) {
			return;
		}
		List<OwpAppSendItem> list = owpAppAction.findOwpAppSendItemByHeadId(new Request(
				CommonVars.getCurrUser()), head.getId());
		
		System.out.println("wss list.size = " + list.size());
		
		tableModelSend = new JTableListModel(tbSend, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品序号", "seqNum", 50,Integer.class));//1
						list.add(addColumn("手册序号", "trNo", 50,Integer.class));//2
						list.add(addColumn("商品编码", "complex.code", 100,Integer.class));//3
						list.add(addColumn("商品名称", "hsName", 100));//4
						list.add(addColumn("规格型号", "hsSpec", 120,Integer.class));//5
						list.add(addColumn("计量单位", "hsUnit.name", 80));//6
						list.add(addColumn("申报数量", "qty", 60));//7
						list.add(addColumn("修改标志", "modifyMark", 80));//8
						list.add(addColumn("备注", "note", 110));//9
						return list;
					}
				});
		
		//修改标志  渲染
		tbSend.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String text = "";
						if("0".equals(value)){
							text = "未修改";
						}else if("1".equals(value)){
							text = "修改";
						}else if("2".equals(value)){
							text = "删除";
						}else if("3".equals(value)){
							text = "增加";
						}
						super.setText(text);
						
						return this;
					}
				});
	}
	
	
	/**
	 * 初始化数据 收回货物表
	 * (OwpRecvItem)
	 */
	private void initTableRecv() {
		OwpAppHead head = (OwpAppHead) tableModelHead.getCurrentRow();
		if (head == null) {
			return;
		}
		List<OwpAppRecvItem> list = owpAppAction.findOwpAppRecvItemByHeadId(new Request(
				CommonVars.getCurrUser()), head.getId());
		System.out.println("wss list.size = " + list.size());
		
		tableModelRecv = new JTableListModel(tbRecv, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品序号", "seqNum", 50,Integer.class));//1
						list.add(addColumn("手册序号", "trNo", 50,Integer.class));//2
						list.add(addColumn("商品编码", "complex.code", 100,Integer.class));//3
						list.add(addColumn("商品名称", "hsName", 100));//4
						list.add(addColumn("规格型号", "hsSpec", 120,Integer.class));//5
						list.add(addColumn("计量单位", "hsUnit.name", 80));//6
						list.add(addColumn("申报数量", "qty", 60));//7
						list.add(addColumn("修改标志", "modifyMark", 80));//8
						list.add(addColumn("备注", "note", 110));//9
						return list;
					}
				});
		
		//修改标志  渲染
		tbRecv.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String text = "";
						if("0".equals(value)){
							text = "未修改";
						}else if("1".equals(value)){
							text = "修改";
						}else if("2".equals(value)){
							text = "删除";
						}else if("3".equals(value)){
							text = "增加";
						}
						super.setText(text);
						
						return this;
					}
				});
		
	}

	
	/**
	 * 外发加工申请表表头tableModel
	 */
	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	/**
	 * 外发加工申请表表头tableModel
	 * @param tableModelHead
	 */
	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataStateHead = dataState;
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEditHead());
			jToolBar.add(getBtnSaveHead());
			jToolBar.add(getBtnCancelHead());
			jToolBar.add(getBtnPrintHead());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJPanel4());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAddUnitWaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveHead() {
		if (btnSaveHead == null) {
			btnSaveHead = new JButton();
			btnSaveHead.setText("保存");
			btnSaveHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!checkData()){
						return ;
					}
					fillHeadData(owpAppHead);
					owpAppHead = owpAppAction.saveOwpAppHead(
							new Request(CommonVars.getCurrUser()),
							owpAppHead);
					tableModelHead.updateRow(owpAppHead);
					dataStateHead = DataState.BROWSE;
					setStateHead();
				}
			});
		}
		return btnSaveHead;
	}
	
	/**
	 * 检查数据是否合法 主要检查输入的格式
	 * @return
	 */
	private boolean checkData(){
		if(tfCorp.getText() != null && !"".equals(tfCorp.getText().trim())){
			if(!tfCorp.getText().contains("/")){
				JOptionPane.showMessageDialog(DgOwpAppHead.this, 
												"委托方企业法人/联系电话 格式不合法！", 
												"注意！", 
												JOptionPane.YES_OPTION);
				return false;
			}
			
		}
		if(tfDecl.getText() != null && !"".equals(tfDecl.getText().trim())){
			if(!tfDecl.getText().contains("/")){
				JOptionPane.showMessageDialog(DgOwpAppHead.this, "委托方企业申报人/联系电话 格式不合法！", 
												"注意！", 
												JOptionPane.YES_OPTION);	
				return false;	
			}
			
		}
		if(tfInCorp.getText() != null && !"".equals(tfInCorp.getText().trim())){
			if(!tfInCorp.getText().contains("/")){
				JOptionPane.showMessageDialog(DgOwpAppHead.this, "承揽方企业法人/联系电话  格式不合法！", 
												"注意！", 
												JOptionPane.YES_OPTION);
				return false;		
			}
			
		}
		return true;
	}

	/**
	 * 填充基本信息
	 * @param head
	 */
	private void fillHeadData(OwpAppHead head) {
		if (head == null) {
			return;
		}
		
		head.setInputMan(tfInputMan.getText().trim());
		head.setInputDate(cbbInputDate.getDate());
		
		if(cbbProjectType.getSelectedItem()!= null){
			head.setEmsType(Integer.parseInt(((ItemProperty)cbbProjectType.getSelectedItem()).getCode()));//项目类型
		}
		head.setAgentCode(this.tfAgentCode.getText().trim());//委托方申报企业代码
		head.setAgentName(this.tfAgentName.getText().trim());//委托方申报企业名称
		head.setEmsNo(this.tfEmsNo.getText().trim());//委托方手册/帐册编号
		head.setContrNo(this.tfContrNo.getText().trim());
		
		head.setMastCust((Customs) this.cbbMastCust.getSelectedItem());//委托方海关
		
		head.setConveyDay(this.ftfConveyDay.getValue() == null ? 0.0
				: Double.valueOf(this.ftfConveyDay.getValue().toString()));//加工期限
		
		head.setCorp(this.tfCorp.getText().trim());//委托方企业法人/联系电话
		
		head.setDecl(this.tfDecl.getText().trim()); //委托方企业申报人/联系电话
		
		head.setInMastCust((Customs) this.cbbInMastCust.getSelectedItem());//承揽方海关

		head.setInTradeCode(this.tfInTradeCode.getText().trim());//承揽方企业代码
		
		head.setInTradeName(this.tfInTradeName.getText().trim());//承揽方企业名称
		
		head.setInCorp(this.tfInCorp.getText().trim());//承揽方企业法人/联系电话
		
		head.setInDistrict((District) this.cbbInDistrict.getSelectedItem());//目的地
		
		head.setNote(this.tfNote.getText().trim());//备注
	}
	/**
	 * 关闭按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpAppHead.this.dispose();
				}
			});
		}
		return btnExit;
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
			jTabbedPane.addTab("基本信息", null, getPn1(), null);
			jTabbedPane.addTab("外发货物", null, getJPanel(), null);
			jTabbedPane.addTab("收回货物", null, getJPanel2(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					int index = jTabbedPane.getSelectedIndex();
					if (index == 0) {
						setStateHead();
						showHeadData(owpAppHead);
					} else{
						//必须选择手册类型
						if (owpAppHead.getEmsType() == null) {
							JOptionPane.showMessageDialog(DgOwpAppHead.this,
									"请选择项目类型", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							jTabbedPane.setSelectedIndex(0);
							return;
						}
						
						//必须选择手册帐册编号
						if (owpAppHead.getEmsType() != ProjectType.BCUS 
											&& owpAppHead.getEmsNo() == null) {
							JOptionPane.showMessageDialog(DgOwpAppHead.this,
									"请选择手册/帐册编号", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							jTabbedPane.setSelectedIndex(0);
							return;
						}
						if (index == 1) {
							setStateSend();
							initTableSend();
							owpAppSendItem = (OwpAppSendItem)tableModelSend.getCurrentRow();
							showCurrentDataSend(owpAppSendItem);
						}else if(index == 2){
							setStateRecv();
							initTableRecv();
							owpAppRecvItem = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
							showCurrentDataRecv(owpAppRecvItem);
						}
					}
						
				}
			});
		}
		return jTabbedPane;
	}


	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("目的地");
			jLabel20.setForeground(Color.blue);
			jLabel20.setBounds(new Rectangle(495, 310, 40, 24));
			jLabel19 = new JLabel();
			jLabel19.setText("承揽方企业名称");
			jLabel19.setBounds(new Rectangle(446, 280, 89, 24));
			jLabel18 = new JLabel();
			jLabel18.setText("承揽方海关");
			jLabel18.setForeground(Color.blue);
			jLabel18.setBounds(new Rectangle(472, 250, 63, 24));
			jLabel17 = new JLabel();
			jLabel17.setText("申报日期");
			jLabel17.setBounds(new Rectangle(481, 220, 54, 24));
			jLabel16 = new JLabel();
			jLabel16.setText("加工期限");
			jLabel16.setForeground(Color.blue);
			jLabel16.setBounds(new Rectangle(483, 190, 52, 24));
			jLabel15 = new JLabel();
			jLabel15.setText("委托方海关");
			jLabel15.setForeground(Color.blue);
			jLabel15.setBounds(new Rectangle(471, 160, 64, 24));
			jLabel14 = new JLabel();
			jLabel14.setText("委托方申报企业组织机构名称");
			jLabel14.setForeground(Color.blue);
			jLabel14.setBounds(new Rectangle(378, 130, 158, 24));
			jLabel13 = new JLabel();
			jLabel13.setText("委托方企业名称");
			jLabel13.setBounds(new Rectangle(446, 100, 89, 24));
			jLabel12 = new JLabel();
			jLabel12.setText("企业内部编号");
			jLabel12.setForeground(Color.blue);
			jLabel12.setBounds(new Rectangle(459, 70, 76, 24));
			jLabel11 = new JLabel();
			jLabel11.setText("电子口岸统一编号");
			jLabel11.setBounds(new Rectangle(430, 40, 103, 24));
			jLabel10 = new JLabel();
			jLabel10.setText("备注");
			jLabel10.setForeground(Color.blue);
			jLabel10.setBounds(new Rectangle(128, 340, 28, 24));
			jLabel9 = new JLabel();
			jLabel9.setText("承揽方企业法人/联系电话");
			jLabel9.setForeground(Color.blue);
			jLabel9.setBounds(new Rectangle(15, 310, 141, 24));
			jLabel8 = new JLabel();
			jLabel8.setText("承揽方企业代码");
			jLabel8.setForeground(Color.blue);
			jLabel8.setBounds(new Rectangle(68, 280, 88, 24));
			jLabel7 = new JLabel();
			jLabel7.setText("委托方企业申报人/联系电话");
			jLabel7.setBounds(new Rectangle(3, 250, 153, 24));
			jLabel6 = new JLabel();
			jLabel6.setText("委托方企业法人/联系电话");
			jLabel6.setForeground(Color.blue);
			jLabel6.setForeground(Color.blue);
			jLabel6.setBounds(new Rectangle(18, 220, 138, 24));
			jLabel5 = new JLabel();
			jLabel5.setText("委托方加工合同号");
			jLabel5.setBounds(new Rectangle(58, 190, 98, 24));
			jLabel4 = new JLabel();
			jLabel4.setText("委托方手册/帐册编号");
			jLabel4.setForeground(Color.blue);
			jLabel4.setBounds(new Rectangle(42, 160, 114, 24));
			jLabel3 = new JLabel();
			jLabel3.setText("委托方企业9位组织机构代码");
			jLabel3.setForeground(Color.blue);
			jLabel3.setBounds(new Rectangle(5, 130, 151, 24));
			jLabel2 = new JLabel();
			jLabel2.setText("委托方企业代码");
			jLabel2.setBounds(new Rectangle(65, 100, 91, 24));
			jLabel1 = new JLabel();
			jLabel1.setText("申请表类型");
			jLabel1.setBounds(new Rectangle(91, 70, 65, 24));
			jLabel = new JLabel();
			jLabel.setText("申请表编号");
			jLabel.setBounds(new Rectangle(91, 40, 65, 24));
			pn1 = new JPanel();
			pn1.setLayout(new BorderLayout());
			pn1.add(getPnHead(), BorderLayout.CENTER);
			pn1.add(getJToolBar(), BorderLayout.NORTH);
		}
		return pn1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddSend());
			jToolBar1.add(getBtnEditSend());
			jToolBar1.add(getBtnDelSend());
			jToolBar1.add(getJButton());
			jToolBar1.add(getBtnSendPrev());
			jToolBar1.add(getBtnSendNext());
			jToolBar1.add(getBtnSaveSend());
			jToolBar1.add(getBtnImportSend());
			jToolBar1.add(getBtnCancelSend());
			jToolBar1.add(getBtnSortSend());
			jToolBar1.add(getBtnExitSend());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAddFinishProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddSend() {
		if (btnAddSend == null) {
			btnAddSend = new JButton();
			btnAddSend.setText("新增");
			btnAddSend.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component comp = (Component)e.getSource();

					getPmSendAdd().show(comp, 0, comp.getHeight());
				}
			
			});
		}
		return btnAddSend;
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
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
			jPanel.add(getJToolBar1(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * 外发货物 jTable
	 * @return javax.swing.JTable
	 */
	private JTable getTbSend() {
		if (tbSend == null) {
			tbSend = new JTable();
			tbSend.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelSend == null) {
								return;
							}
							if (tableModelSend.getCurrentRow() == null) {
								return;
							}
							owpAppSendItem = (OwpAppSendItem)tableModelSend.getCurrentRow();
							showCurrentDataSend(owpAppSendItem);
							setStateSend();

						}
					});
		}
		return tbSend;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
//			jScrollPane.setPreferredSize(new Dimension(200, 100));
			jScrollPane.setBounds(new Rectangle(5, 110, 748, 300));
			jScrollPane.setViewportView(getTbSend());
		}
		return jScrollPane;
	}

	/**
	 * 修改按钮(基本信息)
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditHead() {
		if (btnEditHead == null) {
			btnEditHead = new JButton();
			btnEditHead.setText("修改");
			btnEditHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataStateHead = DataState.EDIT;
					setStateHead();
				}
			});
		}
		return btnEditHead;
	}

	/**
	 * This method initializes tfAppNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setEditable(false);
			tfAppNo.setBounds(new Rectangle(160, 40, 191, 24));
		}
		return tfAppNo;
	}

	/**
	 * This method initializes tfSeqNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setEditable(false);
			tfSeqNo.setBounds(new Rectangle(538, 40, 202, 24));
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes tfCopAppNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCopAppNo() {
		if (tfCopAppNo == null) {
			tfCopAppNo = new JTextField();
			tfCopAppNo.setEditable(false);
			tfCopAppNo.setBounds(new Rectangle(538, 70, 202, 24));
		}
		return tfCopAppNo;
	}

	/**
	 * This method initializes cbbAppClass	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbAppClass() {
		if (cbbAppClass == null) {
			cbbAppClass = new JComboBox();
			cbbAppClass.addItem("外发加工");
//			cbbAppClass.setEditable(false);
			cbbAppClass.setEnabled(false);
			cbbAppClass.setBounds(new Rectangle(160, 70, 191, 24));
		}
		return cbbAppClass;
	}

	/**
	 * This method initializes tfTradeCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setEditable(false);
			tfTradeCode.setBounds(new Rectangle(160, 100, 191, 24));
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes tfTradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setEditable(false);
			tfTradeName.setBounds(new Rectangle(538, 100, 202, 24));
		}
		return tfTradeName;
	}

	/**
	 * This method initializes tfAgentCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAgentCode() {
		if (tfAgentCode == null) {
			tfAgentCode = new JTextField();
//			tfAgentCode.setEditable(false);
			tfAgentCode.setBounds(new Rectangle(160, 130, 191, 24));
		}
		return tfAgentCode;
	}

	/**
	 * This method initializes tfAgentName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAgentName() {
		if (tfAgentName == null) {
			tfAgentName = new JTextField();
//			tfAgentName.setEditable(false);
			tfAgentName.setBounds(new Rectangle(538, 130, 202, 24));
		}
		return tfAgentName;
	}

	/**
	 * This method initializes tfEmsNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setEditable(false);
			tfEmsNo.setBounds(new Rectangle(160, 160, 171, 24));
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes cbbMastCust	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbMastCust() {
		if (cbbMastCust == null) {
			cbbMastCust = new JComboBox();
			cbbMastCust.setBounds(new Rectangle(538, 160, 202, 24));
		}
		return cbbMastCust;
	}

	/**
	 * This method initializes tfContrNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContrNo() {
		if (tfContrNo == null) {
			tfContrNo = new JTextField();
			tfContrNo.setEditable(false);
			tfContrNo.setBounds(new Rectangle(160, 190, 191, 24));
		}
		return tfContrNo;
	}

	/**
	 * This method initializes tfConveyDay	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getFtfConveyDay() {
		if (ftfConveyDay == null) {
			ftfConveyDay = new JFormattedTextField();
			ftfConveyDay.setBounds(new Rectangle(538, 190, 202, 24));
		}
		return ftfConveyDay;
	}

	/**
	 * This method initializes tfCorp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCorp() {
		if (tfCorp == null) {
			tfCorp = new JTextField();
			tfCorp.setBounds(new Rectangle(160, 220, 191, 24));
			tfCorp.setToolTipText("注意企业法人与联系电话用/隔开");
		}
		return tfCorp;
	}

	/**
	 * This method initializes tfDecl	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfDecl() {
		if (tfDecl == null) {
			tfDecl = new JTextField();
			tfDecl.setBounds(new Rectangle(160, 250, 191, 24));
			tfDecl.setToolTipText("注意申报人与联系电话用/隔开");
		}
		return tfDecl;
	}

	/**
	 * This method initializes cbbInMastCust	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbInMastCust() {
		if (cbbInMastCust == null) {
			cbbInMastCust = new JComboBox();
			cbbInMastCust.setBounds(new Rectangle(538, 250, 202, 24));
		}
		return cbbInMastCust;
	}

	/**
	 * This method initializes tfInTradeCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInTradeCode() {
		if (tfInTradeCode == null) {
			tfInTradeCode = new JTextField();
			tfInTradeCode.setEditable(false);
			tfInTradeCode.setBounds(new Rectangle(160, 280, 171, 24));
		}
		return tfInTradeCode;
	}

	/**
	 * This method initializes tfInTradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInTradeName() {
		if (tfInTradeName == null) {
			tfInTradeName = new JTextField();
			tfInTradeName.setEditable(false);
			tfInTradeName.setBounds(new Rectangle(538, 280, 202, 24));
		}
		return tfInTradeName;
	}

	/**
	 * This method initializes tfInCorp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInCorp() {
		if (tfInCorp == null) {
			tfInCorp = new JTextField();
			tfInCorp.setBounds(new Rectangle(160, 310, 191, 24));
			tfInCorp.setToolTipText("注意企业法人与联系电话用/隔开");
		}
		return tfInCorp;
	}

	/**
	 * This method initializes tfNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(160, 340, 581, 24));
		}
		return tfNote;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(59, 73, 48, 24));
			jLabel31.setText("手册序号");
			jLabel28 = new JLabel();
			jLabel28.setBounds(new Rectangle(496, 73, 48, 24));
			jLabel28.setText("备注");
			jLabel27 = new JLabel();
			jLabel27.setBounds(new Rectangle(59, 45, 48, 24));
			jLabel27.setText("修改标志");
			jLabel26 = new JLabel();
			jLabel26.setBounds(new Rectangle(263, 73, 48, 24));
			jLabel26.setText("申报数量");
			jLabel25 = new JLabel();
			jLabel25.setBounds(new Rectangle(263, 45, 48, 24));
			jLabel25.setText("计量单位");
			jLabel23 = new JLabel();
			jLabel23.setBounds(new Rectangle(496, 17, 48, 24));
			jLabel23.setText("商品名称");
			jLabel24 = new JLabel();
			jLabel24.setBounds(new Rectangle(263, 17, 48, 24));
			jLabel24.setText("商品编码");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(496, 45, 48, 24));
			jLabel22.setText("规格型号");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(59, 17, 48, 24));
			jLabel21.setText("商品序号");
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"外发货物商品",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(375, 50));
			jPanel1.add(jLabel21, null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(jLabel23, null);
			jPanel1.add(jLabel24, null);
			jPanel1.add(jLabel25, null);
			jPanel1.add(jLabel26, null);
			jPanel1.add(jLabel27, null);
			jPanel1.add(jLabel28, null);
			jPanel1.add(getTfSendListNo(), null);
			jPanel1.add(getTfSendHsSpec(), null);
			jPanel1.add(getTfSendHsCode(), null);
			jPanel1.add(getTfSendHsName(), null);
			jPanel1.add(getCbbSendHsUnit(), null);
			jPanel1.add(getFtfSendQty(), null);
			jPanel1.add(getCbbSendModifyMark(), null);
			jPanel1.add(getTfSendNote(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(getBtnSendHsCode(), null);
			jPanel1.add(jLabel31, null);
			jPanel1.add(getTfSendContractNo(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfSendListNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendListNo() {
		if (tfSendListNo == null) {
			tfSendListNo = new JTextField();
			tfSendListNo.setBounds(new Rectangle(109, 17, 124, 24));
			tfSendListNo.setEditable(false);
		}
		return tfSendListNo;
	}

	/**
	 * This method initializes tfSendHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendHsSpec() {
		if (tfSendHsSpec == null) {
			tfSendHsSpec = new JTextField();
			tfSendHsSpec.setBounds(new Rectangle(547, 45, 189, 24));
		}
		return tfSendHsSpec;
	}

	/**
	 * This method initializes tfSendHsCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendHsCode() {
		if (tfSendHsCode == null) {
			tfSendHsCode = new JTextField();
			tfSendHsCode.setEditable(false);
			tfSendHsCode.setBounds(new Rectangle(313, 17, 123, 24));
		}
		return tfSendHsCode;
	}

	/**
	 * This method initializes tfSendHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendHsName() {
		if (tfSendHsName == null) {
			tfSendHsName = new JTextField();
			tfSendHsName.setBounds(new Rectangle(547, 17, 189, 24));
		}
		return tfSendHsName;
	}

	/**
	 * This method initializes cbbSendHsUnit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbSendHsUnit() {
		if (cbbSendHsUnit == null) {
			cbbSendHsUnit = new JComboBox();
			cbbSendHsUnit.setBounds(new Rectangle(314, 45, 142, 24));
		}
		return cbbSendHsUnit;
	}

	/**
	 * This method initializes tfSendQty	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getFtfSendQty() {
		if (ftfSendQty == null) {
			ftfSendQty = new JFormattedTextField();
			ftfSendQty.setFormatterFactory(getDefaultFormatterFactory());
			ftfSendQty.setBounds(new Rectangle(314, 73, 142, 24));
		}
		return ftfSendQty;
	}

	/**
	 * This method initializes cbbSendModifyMark	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbSendModifyMark() {
		if (cbbSendModifyMark == null) {
			cbbSendModifyMark = new JComboBox();
			cbbSendModifyMark.setBounds(new Rectangle(109, 45, 124, 24));
			cbbSendModifyMark.setEnabled(false);

		}
		return cbbSendModifyMark;
	}

	/**
	 * This method initializes tfSendNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendNote() {
		if (tfSendNote == null) {
			tfSendNote = new JTextField();
			tfSendNote.setBounds(new Rectangle(547, 73, 189, 24));
		}
		return tfSendNote;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel11(), java.awt.BorderLayout.CENTER);
			jPanel2.add(getJToolBar11(), java.awt.BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
//			jScrollPane1.setPreferredSize(new Dimension(200, 100));
			jScrollPane1.setBounds(new Rectangle(5, 110, 748, 300));
			jScrollPane1.setViewportView(getTbRecv());
		}
		return jScrollPane1;
	}

	/**
	 * 	收回货物 jTable
	 * @return javax.swing.JTable	
	 */
	private JTable getTbRecv() {
		if (tbRecv == null) {
			tbRecv = new JTable();
			tbRecv.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelRecv == null) {
								return;
							}
							if (tableModelRecv.getCurrentRow() == null) {
								return;
							}
							owpAppRecvItem = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
							showCurrentDataRecv(owpAppRecvItem);
							setStateRecv();
						}
					});
		}
		return tbRecv;
	}

	/**
	 * This method initializes jPanel11	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(58, 77, 48, 24));
			jLabel32.setText("手册序号");
			jLabel281 = new JLabel();
			jLabel281.setBounds(new Rectangle(496, 77, 48, 24));
			jLabel281.setText("\u5907\u6ce8");
			jLabel271 = new JLabel();
			jLabel271.setBounds(new Rectangle(58, 47, 48, 24));
			jLabel271.setText("\u4fee\u6539\u6807\u5fd7");
			jLabel261 = new JLabel();
			jLabel261.setBounds(new Rectangle(262, 77, 48, 24));
			jLabel261.setText("\u7533\u62a5\u6570\u91cf");
			jLabel251 = new JLabel();
			jLabel251.setBounds(new Rectangle(262, 47, 48, 24));
			jLabel251.setText("\u8ba1\u91cf\u5355\u4f4d");
			jLabel241 = new JLabel();
			jLabel241.setBounds(new Rectangle(496, 17, 48, 24));
			jLabel241.setText("\u5546\u54c1\u540d\u79f0");
			jLabel231 = new JLabel();
			jLabel231.setBounds(new Rectangle(262, 17, 48, 24));
			jLabel231.setText("\u5546\u54c1\u7f16\u7801");
			jLabel221 = new JLabel();
			jLabel221.setBounds(new Rectangle(496, 47, 48, 24));
			jLabel221.setText("\u89c4\u683c\u578b\u53f7");
			jLabel211 = new JLabel();
			jLabel211.setBounds(new Rectangle(58, 17, 48, 24));
			jLabel211.setText("\u5546\u54c1\u5e8f\u53f7");
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setBorder(BorderFactory.createTitledBorder(null, 
					"收回货物商品", 
					TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.DEFAULT_POSITION, 
					new Font("Dialog", Font.BOLD, 12), Color.blue));
			jPanel11.add(jLabel211, null);
			jPanel11.add(jLabel221, null);
			jPanel11.add(jLabel231, null);
			jPanel11.add(jLabel241, null);
			jPanel11.add(jLabel251, null);
			jPanel11.add(jLabel261, null);
			jPanel11.add(jLabel271, null);
			jPanel11.add(jLabel281, null);
			jPanel11.add(getTfRecvListNo(), null);
			jPanel11.add(getTfRecvHsSpec(), null);
			jPanel11.add(getTfRecvHsCode(), null);
			jPanel11.add(getTfRecvHsName(), null);
			jPanel11.add(getCbbRecvHsUnit(), null);
			jPanel11.add(getFtfRecvQty(), null);
			jPanel11.add(getCbbRecvModifyMark(), null);
			jPanel11.add(getTfRecvNote(), null);
			jPanel11.add(getJScrollPane1(), null);
			jPanel11.add(getBtnRecvHsCode(), null);
			jPanel11.add(jLabel32, null);
			jPanel11.add(getTfRecvContractNo(), null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes tfRecvListNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvListNo() {
		if (tfRecvListNo == null) {
			tfRecvListNo = new JTextField();
			tfRecvListNo.setBounds(new Rectangle(109, 17, 124, 24));
			tfRecvListNo.setEditable(false);
		}
		return tfRecvListNo;
	}

	/**
	 * This method initializes tfRecvHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvHsSpec() {
		if (tfRecvHsSpec == null) {
			tfRecvHsSpec = new JTextField();
			tfRecvHsSpec.setBounds(new Rectangle(547, 47, 189, 24));
		}
		return tfRecvHsSpec;
	}

	/**
	 * This method initializes tfRecvHsCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvHsCode() {
		if (tfRecvHsCode == null) {
			tfRecvHsCode = new JTextField();
			tfRecvHsCode.setEnabled(false);
			tfRecvHsCode.setBounds(new Rectangle(313, 17, 121, 24));
		}
		return tfRecvHsCode;
	}

	/**
	 * This method initializes tfRecvHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvHsName() {
		if (tfRecvHsName == null) {
			tfRecvHsName = new JTextField();
			tfRecvHsName.setBounds(new Rectangle(547, 17, 189, 24));
		}
		return tfRecvHsName;
	}

	/**
	 * This method initializes cbbRecvHsUnit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbRecvHsUnit() {
		if (cbbRecvHsUnit == null) {
			cbbRecvHsUnit = new JComboBox();
			cbbRecvHsUnit.setBounds(new Rectangle(313, 47, 142, 24));
		}
		return cbbRecvHsUnit;
	}

	/**
	 * This method initializes tfRecvQty	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getFtfRecvQty() {
		if (ftfRecvQty == null) {
			ftfRecvQty = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfRecvQty.setBounds(new Rectangle(313, 77, 142, 24));
			ftfRecvQty.setFormatterFactory(getDefaultFormatterFactory());
			
			ftfRecvQty.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					Double qty = 0.0;
					try {
						qty = Double.parseDouble(ftfRecvQty.getText());
						System.out.println(ftfRecvQty.getText());
						System.out.println(qty);
						ftfRecvQty.setValue(qty);
					} catch (Exception ex) {
						ftfRecvQty.setText(ftfRecvQty.getValue().toString());
						return;
					}
				}
			});
		}
		return ftfRecvQty;
	}

	/**
	 * This method initializes cbbRecvModifyMark	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbRecvModifyMark() {
		if (cbbRecvModifyMark == null) {
			cbbRecvModifyMark = new JComboBox();
			cbbRecvModifyMark.setBounds(new Rectangle(109, 47, 124, 24));
			cbbRecvModifyMark.setEnabled(false);
		}
		return cbbRecvModifyMark;
	}

	/**
	 * This method initializes tfRecvNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvNote() {
		if (tfRecvNote == null) {
			tfRecvNote = new JTextField();
			tfRecvNote.setBounds(new Rectangle(547, 77, 189, 24));
		}
		return tfRecvNote;
	}

	/**
	 * This method initializes jToolBar11	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar11() {
		if (jToolBar11 == null) {
			jToolBar11 = new JToolBar();
			jToolBar11.add(getBtnAddRecv());
			jToolBar11.add(getBtnEditRecv());
			jToolBar11.add(getBtnDelRecv());
			jToolBar11.add(getJButton2());
			jToolBar11.add(getBtnRecvPrev());
			jToolBar11.add(getBtnRecvNext());
			jToolBar11.add(getBtnSaveRecv());
			jToolBar11.add(getBtnImportRecv());
			jToolBar11.add(getBtnCancelRecv());
			jToolBar11.add(getBtnSortRecv());
			jToolBar11.add(getBtnExitRecv());
		}
		return jToolBar11;
	}

	/**
	 * This method initializes btnAddRecv	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddRecv() {
		if (btnAddRecv == null) {
			btnAddRecv = new JButton();
			btnAddRecv.setText("\u65b0\u589e");
			btnAddRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component comp = (Component)e.getSource();
					getPmRecvAdd().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnAddRecv;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbAppDate() {
		if (cbbAppDate == null) {
			cbbAppDate = new JCalendarComboBox();
			cbbAppDate.setDate(null);
			cbbAppDate.setBounds(new Rectangle(538, 220, 202, 24));
			cbbAppDate.setEnabled(false);
		}
		return cbbAppDate;
	}

	/**
	 * This method initializes cbbInDistrict	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbInDistrict() {
		if (cbbInDistrict == null) {
			cbbInDistrict = new JComboBox();
			cbbInDistrict.setBounds(new Rectangle(538, 310, 202, 24));
		}
		return cbbInDistrict;
	}

	/**
	 * This method initializes btnEditSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEditSend() {
		if (btnEditSend == null) {
			btnEditSend = new JButton();
			btnEditSend.setText("修改");
			btnEditSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSend.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择要修改的申请表资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
//					owpAppSendItem  = (OwpAppSendItem)tableModelSend.getCurrentRow();
//					owpAppSendItem.setModifyMark(ModifyMarkState.MODIFIED);
//					showCurrentDataSend(owpAppSendItem);
					
					dataStateSend = DataState.EDIT;
					setStateSend();
					
				}
			});
		}
		return btnEditSend;
	}

	/**
	 * This method initializes btnDelSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelSend() {
		if (btnDelSend == null) {
			btnDelSend = new JButton();
			btnDelSend.setText("删除");
			btnDelSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSend.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择要删除的外发货物商品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if (JOptionPane.showConfirmDialog(DgOwpAppHead.this,
						"您确定要删除此外发货物商品信息", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List<OwpAppSendItem> ls = (List<OwpAppSendItem>) tableModelSend
							.getCurrentRows();
					
//					if(DeclareState.CHANGING_EXE.equals(owpAppHead.getDeclareState())){
//						for(int i=0;i<ls.size();i++){
//							owpAppSendItem = ls.get(i);
//							owpAppSendItem.setModifyMark(ModifyMarkState.DELETED);
//							tableModelSend.updateRow(owpAppSendItem);
//						}
//					}else{
//						ls = owpAppAction.deleteOwpAppSendItems(new Request(
//								CommonVars.getCurrUser()), ls);
//						tableModelSend.deleteRows(ls);
//					}
					
					for(int i=0;i<ls.size();i++){
						owpAppSendItem = ls.get(i);
						
						//如果状态不为新增的则修改状态
						if(!ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark())){
							owpAppSendItem.setModifyMark(ModifyMarkState.DELETED);
							tableModelSend.updateRow(owpAppSendItem);
							ls.remove(i);
							System.out.println("ls.size" + ls.size());
							i--;
						}
						
					}
					
					System.out.println("ls.size" + ls.size());
					ls = owpAppAction.deleteOwpAppSendItems(new Request(
							CommonVars.getCurrUser()), ls);
					tableModelSend.deleteRows(ls);
//					
				}
			});
		}
		return btnDelSend;
	}

	/**
	 * This method initializes btnSaveSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSaveSend() {
		if (btnSaveSend == null) {
			btnSaveSend = new JButton();
			btnSaveSend.setText("保存");
			btnSaveSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!validateDataSend()){
						return;
					}

					//填充数据
					if(ModifyMarkState.ADDED.equals(owpAppSendItem.getModifyMark())){
						owpAppSendItem.setHsSpec(tfSendHsSpec.getText());
						owpAppSendItem.setComplex(customBaseAction.findComplexByCode(tfSendHsCode.getText()));
						owpAppSendItem.setHsName(tfSendHsName.getText());
						owpAppSendItem.setHsUnit((Unit)cbbSendHsUnit.getSelectedItem());
						owpAppSendItem.setNote(tfSendNote.getText());	
					}
					
					owpAppSendItem.setQty(Double.parseDouble(ftfSendQty.getText()));
					
					//未修改变成已修改
					if(ModifyMarkState.UNCHANGE.equals(owpAppSendItem.getModifyMark())){
						owpAppSendItem.setModifyMark(ModifyMarkState.MODIFIED);
					}
					owpAppSendItem = owpAppAction.saveOwpAppSendItem(new Request(CommonVars
							.getCurrUser()),owpAppSendItem);
					tableModelSend.updateRow(owpAppSendItem);

				}
			});
		}
		return btnSaveSend;
	}

	/**
	 * 验证数据(外发货物)
	 */
	private boolean validateDataSend(){
		String complexCode = tfSendHsCode.getText();
		if ("".equals(complexCode)) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		Complex complex = customBaseAction.findComplexByCode(complexCode);
		if (complex == null) {
			JOptionPane.showMessageDialog(this, "商品编码不存在!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String hsName = tfSendHsName.getText();
		if ("".equals(hsName)) {
			JOptionPane.showMessageDialog(this, "商品名称不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (cbbSendHsUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "商品单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (ftfSendQty.getValue() == null) {
			JOptionPane.showMessageDialog(this, "申报数量不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		

		return true;
	}
	

	
	/**
	 * 修改按钮(收回货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEditRecv() {
		if (btnEditRecv == null) {
			btnEditRecv = new JButton();
			btnEditRecv.setText("修改");
			btnEditRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelRecv.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择要修改的申请表资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
//					owpAppRecvItem  = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
//					owpAppRecvItem.setModifyMark(ModifyMarkState.MODIFIED);
//					showCurrentDataRecv(owpAppRecvItem);
					
					dataStateRecv = DataState.EDIT;
					setStateRecv();
					
				}
			});
		}
		return btnEditRecv;
	}

	/**
	 * 删除按钮(收回货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelRecv() {
		if (btnDelRecv == null) {
			btnDelRecv = new JButton();
			btnDelRecv.setText("删除");
			btnDelRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelRecv.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择要删除的收回货物商品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if (JOptionPane.showConfirmDialog(DgOwpAppHead.this,
						"您确定要删除此收回货物商品信息", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List<OwpAppRecvItem> ls = (List<OwpAppRecvItem>) tableModelRecv
							.getCurrentRows();
					
//					if(DeclareState.CHANGING_EXE.equals(owpAppHead.getDeclareState())){
//						for(int i=0;i<ls.size();i++){
//							owpAppRecvItem = ls.get(i);
//							owpAppRecvItem.setModifyMark(ModifyMarkState.DELETED);
//							tableModelRecv.updateRow(owpAppRecvItem);
//						}
//					}else{
//						ls = owpAppAction.deleteOwpAppRecvItems(new Request(
//								CommonVars.getCurrUser()), ls);
//						tableModelRecv.deleteRows(ls);
//					}
					
					for(int i=0;i<ls.size();i++){
						owpAppRecvItem = ls.get(i);
						
						//如果状态不为新增的则修改状态
						if(!ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark())){
							owpAppRecvItem.setModifyMark(ModifyMarkState.DELETED);
							tableModelRecv.updateRow(owpAppRecvItem);
							ls.remove(i);
							System.out.println("ls.size" + ls.size());
							i--;
						}
						
					}
					
					System.out.println("ls.size" + ls.size());
					ls = owpAppAction.deleteOwpAppRecvItems(new Request(
							CommonVars.getCurrUser()), ls);
					tableModelRecv.deleteRows(ls);
					
					
				}
			});
		}
		return btnDelRecv;
	}

	/**
	 * 保存按钮(收回货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSaveRecv() {
		if (btnSaveRecv == null) {
			btnSaveRecv = new JButton();
			btnSaveRecv.setText("保存");
			btnSaveRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!validateDataRecv()){
						return;
					}

					//填充数据
					if(ModifyMarkState.ADDED.equals(owpAppRecvItem.getModifyMark())){
						owpAppRecvItem.setHsSpec(tfRecvHsSpec.getText());
						owpAppRecvItem.setComplex(customBaseAction.findComplexByCode(tfRecvHsCode.getText()));
						owpAppRecvItem.setHsName(tfRecvHsName.getText());
						owpAppRecvItem.setHsUnit((Unit)cbbRecvHsUnit.getSelectedItem());
						owpAppRecvItem.setNote(tfRecvNote.getText());
					}
					System.out.println("888888888888" + ftfRecvQty.getValue());
					
					if (ftfRecvQty.getText() != null && !ftfRecvQty.getText().equals("")) {
						owpAppRecvItem.setQty(strToDouble(ftfRecvQty.getValue().toString()));
					} else {
						owpAppRecvItem.setQty(Double.valueOf(0));
					}
					
//					owpAppRecvItem.setQty(Double.parseDouble(ftfRecvQty.getText()));
//					owpAppRecvItem.setQty((Long)ftfRecvQty.getValue());

					System.out.println("999999999998" + owpAppRecvItem.getQty());


					
					//未修改变成已修改
					if(ModifyMarkState.UNCHANGE.equals(owpAppRecvItem.getModifyMark())){
						owpAppRecvItem.setModifyMark(ModifyMarkState.MODIFIED);
					}
					
					owpAppRecvItem = owpAppAction.saveOwpAppRecvItem(new Request(CommonVars
							.getCurrUser()),owpAppRecvItem);
					
					tableModelRecv.updateRow(owpAppRecvItem);
						
				}
			});
		}
		return btnSaveRecv;
	}

	/**
	 * 验证数据(收回货物)
	 */
	private boolean validateDataRecv(){
		String complexCode = tfRecvHsCode.getText();
		if ("".equals(complexCode)) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		Complex complex = customBaseAction.findComplexByCode(complexCode);
		if (complex == null) {
			JOptionPane.showMessageDialog(this, "商品编码不存在!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String hsName = tfRecvHsName.getText();
		if ("".equals(hsName)) {
			JOptionPane.showMessageDialog(this, "商品名称不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (cbbRecvHsUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "商品单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (ftfRecvQty.getValue() == null) {
			JOptionPane.showMessageDialog(this, "申报数量不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}
	/**
	 * 取消按钮(收回货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancelHead() {
		if (btnCancelHead == null) {
			btnCancelHead = new JButton();
			btnCancelHead.setText("取消");
			btnCancelHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataStateHead = DataState.BROWSE;
					showHeadData(owpAppHead);
					setStateHead();
				}
			});
		}
		return btnCancelHead;
	}

	/**
	 * 	打印按钮
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrintHead() {
		if (btnPrintHead == null) {
			btnPrintHead = new JButton();
			btnPrintHead.setText("打印");
		}
		return btnPrintHead;
	}

	/**
	 * This method initializes pnHead	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnHead() {
		if (pnHead == null) {
			jLabel29 = new JLabel();
			jLabel29.setText("项目类型");
			jLabel29.setBounds(new Rectangle(545, 8, 56, 24));
			jLabel29.setForeground(Color.blue);
			pnHead = new JPanel();
			pnHead.setLayout(null);
			pnHead.add(jLabel, null);
			pnHead.add(getTfAppNo(), null);
			pnHead.add(jLabel11, null);
			pnHead.add(getTfSeqNo(), null);
			pnHead.add(getTfCopAppNo(), null);
			pnHead.add(jLabel12, null);
			pnHead.add(getCbbAppClass(), null);
			pnHead.add(jLabel1, null);
			pnHead.add(jLabel2, null);
			pnHead.add(getTfTradeCode(), null);
			pnHead.add(jLabel13, null);
			pnHead.add(getTfTradeName(), null);
			pnHead.add(getTfAgentName(), null);
			pnHead.add(jLabel14, null);
			pnHead.add(getTfAgentCode(), null);
			pnHead.add(jLabel3, null);
			pnHead.add(jLabel4, null);
			pnHead.add(getTfEmsNo(), null);
			pnHead.add(jLabel15, null);
			pnHead.add(getCbbMastCust(), null);
			pnHead.add(getFtfConveyDay(), null);
			pnHead.add(jLabel16, null);
			pnHead.add(getTfContrNo(), null);
			pnHead.add(jLabel5, null);
			pnHead.add(jLabel6, null);
			pnHead.add(jLabel7, null);
			pnHead.add(jLabel8, null);
			pnHead.add(jLabel9, null);
			pnHead.add(jLabel10, null);
			pnHead.add(getTfNote(), null);
			pnHead.add(getTfInCorp(), null);
			pnHead.add(getTfInTradeCode(), null);
			pnHead.add(getTfDecl(), null);
			pnHead.add(getTfCorp(), null);
			pnHead.add(jLabel17, null);
			pnHead.add(getCbbAppDate(), null);
			pnHead.add(getCbbInMastCust(), null);
			pnHead.add(jLabel18, null);
			pnHead.add(jLabel19, null);
			pnHead.add(getTfInTradeName(), null);
			pnHead.add(jLabel20, null);
			pnHead.add(getCbbInDistrict(), null);
			pnHead.setBorder(BorderFactory.createTitledBorder(null,
					"",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnHead.add(getBtnEmsNo(), null);
			pnHead.add(getBtnInTradeCode(), null);
			pnHead.add(jLabel29, null);
			pnHead.add(getCbbProjectType(), null);
		}
		return pnHead;
	}

	/**
	 * 取消按钮(发出货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancelSend() {
		if (btnCancelSend == null) {
			btnCancelSend = new JButton();
			btnCancelSend.setText("取消");
			btnCancelSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataStateSend = DataState.BROWSE;
					setStateSend();
					
				}
			});
		}
		return btnCancelSend;
	}

	/**
	 * 取消按钮(收回货物)
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancelRecv() {
		if (btnCancelRecv == null) {
			btnCancelRecv = new JButton();
			btnCancelRecv.setText("取消");
			btnCancelRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataStateRecv = DataState.BROWSE;
					setStateRecv();
					
				}
			});
		}
		return btnCancelRecv;
	}

	/**
	 * This method initializes btnEmsNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEmsNo() {
		if (btnEmsNo == null) {
			btnEmsNo = new JButton();
			btnEmsNo.setBounds(new Rectangle(333, 160, 18, 24));
			btnEmsNo.setText("...");
			btnEmsNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbProjectType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择项目类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					Integer type = Integer.parseInt(((ItemProperty)cbbProjectType.getSelectedItem()).getCode());
					List  list = OwpQuery.getInstance().findEmsNo(type);
					if (list != null && list.size()>0) {
						System.out.println("wss list.size = " + list.size());
						String emsNo = "";
						String contrNo = "";
						if(ProjectType.BCS == type){
							Contract c = (Contract)list.get(0);
							if(c != null){
								emsNo = c.getEmsNo();
								contrNo = c.getImpContractNo();
							}
						}
						else if(ProjectType.BCUS == type){
							EmsHeadH2k c = (EmsHeadH2k)list.get(0);
							if(c != null){
								emsNo = c.getEmsNo();
							}
						}else if(2 == type){
							DzscEmsPorHead c = (DzscEmsPorHead)list.get(0);
							if(c != null){
								emsNo = c.getEmsNo();
								contrNo = c.getIeContractNo();
							}
						}
						tfEmsNo.setText(emsNo);
						tfContrNo.setText(contrNo);
					}		
				}
			});
		}
		return btnEmsNo;
	}

	/**
	 * This method initializes btnInTradeCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnInTradeCode() {
		if (btnInTradeCode == null) {
			btnInTradeCode = new JButton();
			btnInTradeCode.setBounds(new Rectangle(333, 280, 18, 24));
			btnInTradeCode.setText("...");
			btnInTradeCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List  list = OwpQuery.getInstance().findBrief();
					if (list != null && list.size()>0) {
						System.out.println("wss list.size = " + list.size());
						Brief b = (Brief)list.get(0);
						if(b != null){
							tfInTradeCode.setText(b.getCode());
							tfInTradeName.setText(b.getName());
						}
					}
				}
			});
		}
		return btnInTradeCode;
	}

	/**
	 * This method initializes btnSendHsCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSendHsCode() {
		if (btnSendHsCode == null) {
			btnSendHsCode = new JButton();
			btnSendHsCode.setBounds(new Rectangle(437, 17, 19, 24));
			btnSendHsCode.setText("...");
			btnSendHsCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
//					if (owpAppHead.getEmsType() == null) {
//						JOptionPane.showMessageDialog(DgOwpAppHead.this,
//								"请选择项目类型", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//					
//					if (owpAppHead.getEmsType() != ProjectType.BCUS 
//										&& owpAppHead.getEmsNo() == null) {
//						JOptionPane.showMessageDialog(DgOwpAppHead.this,
//								"请选择手册/帐册编号", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//					
//					List  list = OwpQuery.getInstance().findContractImgExg(owpAppHead.getEmsType(),owpAppHead.getId(),owpAppHead.getEmsNo(),true);
//					
//					if(list != null && list.size() > 0){
//						switch(owpAppHead.getEmsType()){
//						case ProjectType.BCS:
//							ContractImg bcsImg = (ContractImg)list.get(0);
//							owpAppSendItem.setTrNo(bcsImg.getSeqNum());
//							tfSendHsCode.setText(bcsImg.getComplex().getCode());
//							tfSendHsSpec.setText(bcsImg.getSpec());
//							tfSendHsName.setText(bcsImg.getName());
//							cbbSendHsUnit.setSelectedItem(bcsImg.getUnit());
//							break;
//						case ProjectType.BCUS:
//							EmsHeadH2kImg bcusImg = (EmsHeadH2kImg)list.get(0);
//							owpAppSendItem.setTrNo(bcusImg.getSeqNum());
//							tfSendHsCode.setText(bcusImg.getComplex().getCode());
//							tfSendHsSpec.setText(bcusImg.getSpec());
//							tfSendHsName.setText(bcusImg.getName());
//							cbbSendHsUnit.setSelectedItem(bcusImg.getUnit());
//							break;
//						case 2:
//							DzscEmsImgBill dzscImg = (DzscEmsImgBill)list.get(0);
//							owpAppSendItem.setTrNo(dzscImg.getSeqNum());
//							tfSendHsCode.setText(dzscImg.getComplex().getCode());
//							tfSendHsSpec.setText(dzscImg.getSpec());
//							tfSendHsName.setText(dzscImg.getName());
//							cbbSendHsUnit.setSelectedItem(dzscImg.getUnit());
////							DzscEmsImgBill
////							DzscEmsExgBill
//							break;
//						}
//						
//					}
					
					List  list = OwpQuery.getInstance().findComplex();
					if(list != null && list.size()>0){
						Complex c = (Complex)list.get(0);
						if(c != null){
							tfSendHsCode.setText(c.getCode());
						}
					}
				}
			});
		}
		return btnSendHsCode;
	}

	/**
	 * This method initializes btnRecvHsCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRecvHsCode() {
		if (btnRecvHsCode == null) {
			btnRecvHsCode = new JButton();
			btnRecvHsCode.setBounds(new Rectangle(436, 17, 19, 24));
			btnRecvHsCode.setText("...");
			btnRecvHsCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
//					if (owpAppHead.getEmsType() == null) {
//						JOptionPane.showMessageDialog(DgOwpAppHead.this,
//								"请选择项目类型", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//					
//					if (owpAppHead.getEmsType() != ProjectType.BCUS 
//										&& owpAppHead.getEmsNo() == null) {
//						JOptionPane.showMessageDialog(DgOwpAppHead.this,
//								"请选择手册/帐册编号", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//					
//					List  list = OwpQuery.getInstance().findContractImgExg(owpAppHead.getEmsType(),
//																			owpAppHead.getId(),
//																			owpAppHead.getEmsNo(),
//																			false);
//					
//					if(list != null && list.size() > 0){
//						
//
//						switch(owpAppHead.getEmsType()){
//						case ProjectType.BCS:
//							ContractExg bcsExg = (ContractExg)list.get(0);
//							owpAppRecvItem.setTrNo(bcsExg.getSeqNum());
//							tfRecvHsCode.setText(bcsExg.getComplex().getCode());
//							tfRecvHsSpec.setText(bcsExg.getSpec());
//							tfRecvHsName.setText(bcsExg.getName());
//							cbbRecvHsUnit.setSelectedItem(bcsExg.getUnit());
//							break;
//						case ProjectType.BCUS:
//							EmsHeadH2kExg bcusExg = (EmsHeadH2kExg)list.get(0);
//							owpAppRecvItem.setTrNo(bcusExg.getSeqNum());
//							tfRecvHsCode.setText(bcusExg.getComplex().getCode());
//							tfRecvHsSpec.setText(bcusExg.getSpec());
//							tfRecvHsName.setText(bcusExg.getName());
//							cbbRecvHsUnit.setSelectedItem(bcusExg.getUnit());
//							break;
//						case 2:
//							DzscEmsExgBill dzscExg = (DzscEmsExgBill)list.get(0);
//							owpAppRecvItem.setTrNo(dzscExg.getSeqNum());
//							tfRecvHsCode.setText(dzscExg.getComplex().getCode());
//							tfRecvHsSpec.setText(dzscExg.getSpec());
//							tfRecvHsName.setText(dzscExg.getName());
//							cbbRecvHsUnit.setSelectedItem(dzscExg.getUnit());
////							DzscEmsImgBill
////							DzscEmsExgBill
//							break;
//						}
//						
//					}
					
					//新增选择结果
					List  list = OwpQuery.getInstance().findComplex();
					if(list != null && list.size()>0){
						Complex c = (Complex)list.get(0);
						if(c != null){
							tfRecvHsCode.setText(c.getCode());
						}
					}
					
				}
			});
		}
		return btnRecvHsCode;
	}

	/**
	 * This method initializes cbbProjectType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(623, 9, 118, 24));
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					
				}
			});
		}
		return cbbProjectType;
	}

	/**
	 * This method initializes btnImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpSendRecvItemInput dg = new DgOwpSendRecvItemInput();
					dg.setHead(owpAppHead);
					dg.setVisible(true);
					reloadData();
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel30 = new JLabel();
			jLabel30.setBounds(new Rectangle(328, 3, 55, 25));
			jLabel30.setText("录入日期");
			jLabel33 = new JLabel();
			jLabel33.setBounds(new Rectangle(167, 3, 42, 25));
			jLabel33.setText("录入人");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setPreferredSize(new Dimension(250, 30));
			jPanel4.add(getTfInputMan(), null);
			jPanel4.add(jLabel33, null);
			jPanel4.setBorder(BorderFactory.createTitledBorder(null,
					"",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel4.add(jLabel30, null);
			jPanel4.add(getCbbInputDate(), null);
		}
		return jPanel4;
	}

	/**
	 * 操作员
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInputMan() {
		if (tfInputMan == null) {
			tfInputMan = new JTextField();
			tfInputMan.setBounds(new Rectangle(211, 3, 98, 25));
			tfInputMan.setEditable(false);
		}
		return tfInputMan;
	}

	/**
	 * This method initializes btnSendPrev	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSendPrev() {
		if (btnSendPrev == null) {
			btnSendPrev = new JButton();
			btnSendPrev.setText("上笔");
			btnSendPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelSend.previousRow()) {
						btnSendPrev.setEnabled(false);
						btnSendNext.setEnabled(true);
					} else {
						owpAppSendItem = (OwpAppSendItem)tableModelSend.getCurrentRow();
						btnSendPrev.setEnabled(true);
						btnSendNext.setEnabled(true);
					}
					
					showCurrentDataSend(owpAppSendItem);
//					dataStateSend = DataState.EDIT;
					setStateSend();
				}
			});
		}
		return btnSendPrev;
	}

	/**
	 * This method initializes btnSendNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSendNext() {
		if (btnSendNext == null) {
			btnSendNext = new JButton();
			btnSendNext.setText("下笔");
			btnSendNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelSend.nextRow()) {
						btnSendPrev.setEnabled(true);
						btnSendNext.setEnabled(false);
					} else {
						owpAppSendItem = (OwpAppSendItem)tableModelSend.getCurrentRow();
						btnSendPrev.setEnabled(true);
						btnSendNext.setEnabled(true);
					}
					showCurrentDataSend(owpAppSendItem);
					setStateSend();
//					dataStateSend = DataState.EDIT;
//					setState();
				}
			});
		}
		return btnSendNext;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("  ");
			jButton.setEnabled(false);
		}
		return jButton;
	}

	/**
	 * This method initializes btnRecvPrev	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRecvPrev() {
		if (btnRecvPrev == null) {
			btnRecvPrev = new JButton();
			btnRecvPrev.setText("上笔");
			btnRecvPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelRecv.previousRow()) {
						btnRecvPrev.setEnabled(false);
						btnRecvNext.setEnabled(true);
					} else {
						owpAppRecvItem = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
						btnRecvPrev.setEnabled(true);
						btnRecvNext.setEnabled(true);
					}
					
					showCurrentDataRecv(owpAppRecvItem);
//					dataStateRecv = DataState.EDIT;
					setStateRecv();
				}
			});
		}
		return btnRecvPrev;
	}

	/**
	 * This method initializes btnRecvNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRecvNext() {
		if (btnRecvNext == null) {
			btnRecvNext = new JButton();
			btnRecvNext.setText("下笔");
			btnRecvNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelRecv.nextRow()) {
						btnRecvPrev.setEnabled(true);
						btnRecvNext.setEnabled(false);
					} else {
						owpAppRecvItem = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
						btnRecvPrev.setEnabled(true);
						btnRecvNext.setEnabled(true);
					}
					showCurrentDataRecv(owpAppRecvItem);
					setStateRecv();
//					dataStateRecv = DataState.EDIT;
//					setState();
				}
			});
		}
		return btnRecvNext;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("  ");
			jButton2.setEnabled(false);
		}
		return jButton2;
	}

	/**
	 * This method initializes cbbInputDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbInputDate() {
		if (cbbInputDate == null) {
			cbbInputDate = new JCalendarComboBox();
			cbbInputDate.setBounds(new Rectangle(385, 3, 95, 25));
			cbbInputDate.setPreferredSize(new Dimension(95, 25));
		}
		return cbbInputDate;
	}

	/**
	 * This method initializes btnRefresh	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					reloadData();
				}
			});
		}
		return btnRefresh;
	}
	
	/**
	 * 重新加载数据
	 */
	public void reloadData(){

		int index = jTabbedPane.getSelectedIndex();
		if (index == 0) {
			setStateHead();
			owpAppHead = owpAppAction.findOwpAppHeadById(
										new Request(CommonVars.getCurrUser(),true), 
										owpAppHead.getId());
			showHeadData(owpAppHead);
		} else if (index == 1) {
			setStateSend();
			initTableSend();
			owpAppSendItem = (OwpAppSendItem)tableModelSend.getCurrentRow();
			showCurrentDataSend(owpAppSendItem);
		}else if(index == 2){
			setStateRecv();
			initTableRecv();
			owpAppRecvItem = (OwpAppRecvItem)tableModelRecv.getCurrentRow();
			showCurrentDataRecv(owpAppRecvItem);
		}
	}

	/**
	 * This method initializes btnImportSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImportSend() {
		if (btnImportSend == null) {
			btnImportSend = new JButton();
			btnImportSend.setText("导入");
			btnImportSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpSendRecvItemInput dg = new DgOwpSendRecvItemInput();
					dg.setHead(owpAppHead);
					dg.setVisible(true);
					reloadData();				
				}
			});
		}
		return btnImportSend;
	}

	/**
	 * This method initializes btnImportRecv	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImportRecv() {
		if (btnImportRecv == null) {
			btnImportRecv = new JButton();
			btnImportRecv.setText("导入");
			btnImportRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpSendRecvItemInput dg = new DgOwpSendRecvItemInput();
					dg.setHead(owpAppHead);
					dg.setVisible(true);
					reloadData();	
				}
			});
		}
		return btnImportRecv;
	}

	/**
	 * This method initializes btnExitSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExitSend() {
		if (btnExitSend == null) {
			btnExitSend = new JButton();
			btnExitSend.setText("关闭");
			btnExitSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpAppHead.this.dispose();
				}
			});
		}
		return btnExitSend;
	}

	/**
	 * This method initializes btnExitRecv	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExitRecv() {
		if (btnExitRecv == null) {
			btnExitRecv = new JButton();
			btnExitRecv.setText("关闭");
			btnExitRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpAppHead.this.dispose();
				}
			});
		}
		return btnExitRecv;
	}

	/**
	 * This method initializes pmSendAdd	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getPmSendAdd() {
		if (pmSendAdd == null) {
			pmSendAdd = new JPopupMenu();
			pmSendAdd.setSize(new Dimension(98, 37));
			pmSendAdd.add(getMiAddFromContractSend());
			pmSendAdd.add(getMiHandImputSend());
		}
		return pmSendAdd;
	}

	/**
	 * This method initializes pmRecvAdd	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getPmRecvAdd() {
		if (pmRecvAdd == null) {
			pmRecvAdd = new JPopupMenu();
			pmRecvAdd.add(getMiAddFromContractRecv());
			pmRecvAdd.add(getMiHandInputRecv());
		}
		return pmRecvAdd;
	}

	/**
	 * This method initializes miAddFromContractSend	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiAddFromContractSend() {
		if (miAddFromContractSend == null) {
			miAddFromContractSend = new JMenuItem();
			miAddFromContractSend.setText("新增来自手册");
			miAddFromContractSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					//必须选择手册类型
					if (owpAppHead.getEmsType() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择项目类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					//必须选择手册帐册编号
					if (owpAppHead.getEmsType() != ProjectType.BCUS 
										&& owpAppHead.getEmsNo() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择手册/帐册编号", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					//新增选择结果
					List  list = OwpQuery.getInstance().findContractImgExg(
															owpAppHead.getEmsType(),
															owpAppHead.getId(),
															owpAppHead.getEmsNo(),
															true);
					
					List<OwpAppSendItem> lsAdd = new ArrayList<OwpAppSendItem>();
					
					if(list != null && list.size() > 0){
						lsAdd = owpAppAction.addOwpAppSendItemFromContract(new Request(CommonVars
							.getCurrUser()),owpAppHead,list);
					}
					
					if(lsAdd != null && lsAdd.size() > 0){
						tableModelSend.addRows(lsAdd);
						owpAppSendItem = lsAdd.get(0);
						dataStateSend = DataState.EDIT;
						setStateSend();
					}
					
					showCurrentDataSend(owpAppSendItem);
					
				}
			});
		}
		return miAddFromContractSend;
	}

	/**
	 * This method initializes miHandImputSend	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiHandImputSend() {
		if (miHandImputSend == null) {
			miHandImputSend = new JMenuItem();
			miHandImputSend.setText("手工录入");
			miHandImputSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//新增选择结果
					List  list = OwpQuery.getInstance().findComplex();
					if(list != null){
						System.out.println(" wss 啦啦啦list.size = "  + list.size());
					}
					
					
					List<OwpAppSendItem> lsAdd = new ArrayList<OwpAppSendItem>();
					
					//转换为外发货物
					if(list != null && list.size() > 0){
						lsAdd = owpAppAction.addOwpAppSendItemFromComplex(new Request(CommonVars
							.getCurrUser()),owpAppHead,list);
						System.out.println(" wss lsAdd.size = " + lsAdd.size());
					}
					
					if(lsAdd != null && lsAdd.size() > 0){
						tableModelSend.addRows(lsAdd);
						owpAppSendItem = lsAdd.get(0);
						dataStateSend = DataState.EDIT;
						setStateSend();
					}
					//显示
					showCurrentDataSend(owpAppSendItem);
				}
			});
		}
		return miHandImputSend;
	}

	/**
	 * This method initializes miAddFromContractRecv	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiAddFromContractRecv() {
		if (miAddFromContractRecv == null) {
			miAddFromContractRecv = new JMenuItem();
			miAddFromContractRecv.setText("新增来自手册");
			miAddFromContractRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					//必须选择手册类型
					if (owpAppHead.getEmsType() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择项目类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					//必须选择手册/帐册编号
					if (owpAppHead.getEmsType() != ProjectType.BCUS 
										&& owpAppHead.getEmsNo() == null) {
						JOptionPane.showMessageDialog(DgOwpAppHead.this,
								"请选择手册/帐册编号", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					//新增选择结果
					List  list = OwpQuery.getInstance().findContractImgExg(
															owpAppHead.getEmsType(),
															owpAppHead.getId(),
															owpAppHead.getEmsNo(),
															false);
					
					List<OwpAppRecvItem> lsAdd = new ArrayList<OwpAppRecvItem>();
					
					if(list != null && list.size() > 0){
						lsAdd = owpAppAction.addOwpAppRecvItemFromContract(new Request(CommonVars
							.getCurrUser()),owpAppHead,list);
					}
					
					if(lsAdd != null && lsAdd.size() > 0){
						tableModelRecv.addRows(lsAdd);
						owpAppRecvItem = lsAdd.get(0);
						showCurrentDataRecv(owpAppRecvItem);
						dataStateRecv = DataState.EDIT;
					}
					
					setStateRecv();
				}
			});
		}
		return miAddFromContractRecv;
	}

	/**
	 * This method initializes miHandInputRecv	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiHandInputRecv() {
		if (miHandInputRecv == null) {
			miHandInputRecv = new JMenuItem();
			miHandInputRecv.setText("手工录入");
			miHandInputRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//新增选择结果
					List  list = OwpQuery.getInstance().findComplex();
					
					List<OwpAppRecvItem> lsAdd = new ArrayList<OwpAppRecvItem>();
					
					//转化为收回货物
					if(list != null && list.size() > 0){
						lsAdd = owpAppAction.addOwpAppRecvItemFromComplex(new Request(CommonVars
							.getCurrUser()),owpAppHead,list);
						System.out.println(" wss lsAdd.size = " + lsAdd.size());

					}
					
					if(lsAdd != null && lsAdd.size() > 0){
						tableModelRecv.addRows(lsAdd);
						owpAppRecvItem = lsAdd.get(0);
						dataStateRecv = DataState.EDIT;
						setStateRecv();
					}
					
					showCurrentDataRecv(owpAppRecvItem);
				}
			});
		}
		return miHandInputRecv;
	}

	/**
	 * This method initializes tfSendContractNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSendContractNo() {
		if (tfSendContractNo == null) {
			tfSendContractNo = new JTextField();
			tfSendContractNo.setBounds(new Rectangle(109, 73, 124, 24));
			tfSendContractNo.setEditable(false);
		}
		return tfSendContractNo;
	}

	/**
	 * This method initializes tfRecvContractNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRecvContractNo() {
		if (tfRecvContractNo == null) {
			tfRecvContractNo = new JTextField();
			tfRecvContractNo.setBounds(new Rectangle(109, 77, 124, 24));
			tfRecvContractNo.setEditable(false);
		}
		return tfRecvContractNo;
	}
	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}
	/**
	 * 数字匹配器
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(5);
			numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat);
			
		}
		return numberFormatter;
	}
	
	/** String 转换成 Double */
	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method initializes btnSortSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSortSend() {
		if (btnSortSend == null) {
			btnSortSend = new JButton();
			btnSortSend.setText("序号重排");
			btnSortSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(owpAppHead != null){
						owpAppAction.sortSeqNumOfOwpAppSendItem(
								new Request(CommonVars.getCurrUser()),owpAppHead.getId());
						initTableSend();
					}
					
				}
			});
		}
		return btnSortSend;
	}

	/**
	 * This method initializes btnSortRecv	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSortRecv() {
		if (btnSortRecv == null) {
			btnSortRecv = new JButton();
			btnSortRecv.setText("序号重排");
			btnSortRecv.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(owpAppHead != null){
						owpAppAction.sortSeqNumOfOwpAppRecvItem(
								new Request(CommonVars.getCurrUser()),owpAppHead.getId());
						initTableRecv();
					}
					
				}
			});
		}
		return btnSortRecv;
	}
	
} // @jve:decl-index=0:visual-constraint="16,46"
