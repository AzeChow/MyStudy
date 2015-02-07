package com.bestway.client.owp;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.DgFptAppHead;
import com.bestway.common.client.fpt.DgFptAppItem;
import com.bestway.common.client.fpt.FptQuery;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.action.OwpMessageAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Color;

/**
 * 外发加工出货登记表
 * @author sxk
 *
 */
public class DgOwpBillSend extends JDialogBase {

	private JPanel pnContentPane = null;
	private JToolBar tbToolBarBar = null;
	private JTabbedPane tpnMian = null;
	private JButton btnClose = null;
	private JButton btnEdit = null;
	private JButton btnPrint = null;
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JPanel pnBillHead = null;
	private JPanel pnBillItem = null;
	private JTextField tfAppNo = null;
	private JLabel lbAppNo = null;
	private JLabel lbSeqNo = null;
	private JTextField tfSeqNo = null;
	private JLabel lbBillNo = null;
	private JTextField tfBillNo = null;
	private JLabel lbEmsNo = null;
	private JTextField tfEmsNo = null;
	private JLabel lbCopBillNo = null;
	private JTextField tfCopBillNo = null;
	private JTextField tfOutTradeCode = null;
	private JLabel lbOutTradeCode = null;
	private JLabel lbOutTradeName = null;
	private JTextField tfOutTradeName = null;
	private JLabel lbInTradeCode = null;
	private JTextField tfInTradeCode = null;
	private JLabel lbInTradeName = null;
	private JTextField tfInTradeName = null;
	private JLabel lbCollectDate = null;
	protected JCalendarComboBox cbbCollectDate = null;
	private JLabel lbIsDeclaEm = null;
	private JTextField tfIsDeclaEm = null;
	private JLabel lbNote = null;
	private JTextField tfNote = null;
	//基本信息状态标示
	protected int dataState = DataState.BROWSE;
	//商品明细状态标示
	protected int dataItemState = DataState.BROWSE;
	/**
	 * 外发加工出货表头
	 */
	private OwpBillSendHead owpBillSendHead = null;  //  @jve:decl-index=0:
	/**
	 * 外发加工申请表表头
	 */
	private OwpAppHead owpAppHead = null;  //  @jve:decl-index=0:
	/**
	 * 外发加工发货登记表表头tableModelHead
	 */
	private JTableListModel tableModelHead = null;
	
	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;
	
	private JPanel pnHead = null;
	private JPanel pnLabel = null;
	private JScrollPane jpnItem = null;
	private JTable tbItem = null;
	private JButton btnAdd = null;
	/**
	 * 外发加工发货表体
	 */
	private OwpBillSendItem owpSendBillItem = null;
	/**
	 * 外发加工登记表远程服务接口
	 */
	private OwpBillAction owpBillAction = null;
	/**
	 * 商品明细表模型
	 */
	private JTableListModel tableModel = null;
	private JButton btnDelete = null;
	private boolean isInsertByApp = false;//是否通过申请表导入
	/**
	 * 当前外发加工登记表的审批状态
	 */
	private String declareState = DeclareState.APPLY_POR;
	private JLabel lbCreaterDate = null;
	private JCalendarComboBox cbbCreateDate = null;
	/**
	 * 窗体构造函数
	 */
	public DgOwpBillSend() {
		super();
		/** 
		 * 海关基础资料服务器端接口
		 */
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
																.getBean("customBaseAction");
		
		/**
		 * 初始化外发加工登记表远程服务接口
		 */
		owpBillAction = (OwpBillAction) CommonVars
			.getApplicationContext().getBean("owpBillAction");
		initialize();
	}
	/**
	 * 设置显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			if(dataState!=DataState.ADD){
				if (tableModelHead != null) {
					owpBillSendHead = (OwpBillSendHead) tableModelHead
							.getCurrentRow();
					declareState = owpBillSendHead.getDeclareState();
				}
				showHeadDate();
			}
			else{
				if(checkapp()){
					return;
				}
				showPrimalData();
			}
			setHeadState();
		}
		super.setVisible(b);
	}
	/**
	 * 校验
	 * @return
	 */
	private boolean checkapp(){
		if(null == owpAppHead){
			JOptionPane.showMessageDialog(
					DgOwpBillSend.this, "请选择申请表！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}
	/**
	 * 初始化容器
	 */
	private void initialize() {
        this.setSize(new Dimension(705, 541));
        this.setContentPane(getJContentPane());
        this.setTitle("外发加工发货登记表");
	}
	/**
	 * 当新增或删除时，显示最初数据
	 */
	protected void showPrimalData() {
			tfAppNo.setText(owpAppHead.getAppNo());
//			tfSeqNo.setText(owpAppHead.getSeqNo());
			tfEmsNo.setText(owpAppHead.getEmsNo());
			Random rd1 = new Random();
			tfCopBillNo.setText("2010"+rd1.nextInt(9999)+""+rd1.nextInt(999999999));
			tfOutTradeCode.setText(owpAppHead.getTradeCode());
			tfOutTradeName.setText(owpAppHead.getTradeName());
			tfInTradeCode.setText(owpAppHead.getInTradeCode());
			tfInTradeName.setText(owpAppHead.getInTradeName());
//			tfIsDeclaEm.setText(owpAppHead.getDecl());
	}
	/**
	 * 当修改时，显示所选表头的数据
	 */
	private void showHeadDate(){
		tfAppNo.setText(owpBillSendHead.getAppNo());
		tfSeqNo.setText(owpBillSendHead.getSeqNo());
		tfBillNo.setText(owpBillSendHead.getBillNo());
		tfEmsNo.setText(owpBillSendHead.getEmsNo());
		tfOutTradeCode.setText(owpBillSendHead.getOutTradeCode());
		tfOutTradeName.setText(owpBillSendHead.getOutTradeName());
		tfInTradeCode.setText(owpBillSendHead.getInTradeCode());
		tfInTradeName.setText(owpBillSendHead.getInTradeName());
		tfCopBillNo.setText(owpBillSendHead.getCopBillNo());
		cbbCollectDate.setDate(owpBillSendHead.getCollectDate());
		cbbCreateDate.setDate(owpBillSendHead.getCreaterDate());
		tfIsDeclaEm.setText(owpBillSendHead.getIsDeclaEm());
		tfNote.setText(owpBillSendHead.getNote());
		
	}

	/**
	 * 面板容器设置
	 */
	private JPanel getJContentPane() {
		if (pnContentPane == null) {
			pnContentPane = new JPanel();
			pnContentPane.setLayout(new BorderLayout());
			pnContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			pnContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pnContentPane;
	}

	/**
	 * 工具栏
	 */
	private JToolBar getJJToolBarBar() {
		if (tbToolBarBar == null) {
			tbToolBarBar = new JToolBar();
			tbToolBarBar.add(getBtnAdd());
			tbToolBarBar.add(getBtnEdit());
			tbToolBarBar.add(getBtnSave());
			tbToolBarBar.add(getBtnDelete());
			tbToolBarBar.add(getBtnCancel());
			tbToolBarBar.add(getBtnPrint());
			tbToolBarBar.add(getBtnClose());
		}
		return tbToolBarBar;
	}

	/**
	 * 多页重叠面板设置、事件
	 */
	private JTabbedPane getJTabbedPane() {
		if (tpnMian == null) {
			tpnMian = new JTabbedPane();
			tpnMian.setTabPlacement(JTabbedPane.BOTTOM);
			tpnMian.addTab("基本信息", null, getPnBillHead(), null);
			tpnMian.addTab("商品明细", null, getPnBillItem(), null);
			tpnMian.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					int index = tpnMian.getSelectedIndex();
					if (index == 0) {
						setHeadState();
					} else if (index == 1) {
						if (vaildatorDataIsNull()) {
							tpnMian.setSelectedIndex(0);
							return;
						}
						fillHeadData();
						owpBillSendHead = owpBillAction.saveOwpSendBillHead(
								new Request(CommonVars.getCurrUser()), owpBillSendHead);
						if (dataState == DataState.ADD) {
							tableModelHead.addRow(owpBillSendHead);
						}else{
							tableModelHead.updateRow(owpBillSendHead);
						}
						dataState = DataState.BROWSE;
						setHeadState();
						setItemState();
						setItemDate();
					}
				}
			});
		}
		return tpnMian;
	}

	/**
	 * 显示控件数据
	 * @param owpSendBillItem
	 */
	private void showCurrentDataItem(OwpBillSendItem owpSendBillItem){
		if(null != owpSendBillItem){
		}
		
	}
	
	/**
	 * 表体窗口数据显示
	 */
	private void setItemDate(){
		List list = new ArrayList();
		if(null != owpBillSendHead){
			list=owpBillAction.findOwpSendBillItemByHead(new Request(
					CommonVars.getCurrUser()),owpBillSendHead);
		}
		if(list.size()>0){
			initTableModel(list);
		}
		else{
			initTableModel(new Vector());
		}
		showCurrentDataItem((OwpBillSendItem)tableModel.getCurrentRow());
	}
	/**
	 * 表头日期下拉框
	 */
	protected JCalendarComboBox getCbbCollectDate() {
		if (cbbCollectDate == null) {
			cbbCollectDate = new JCalendarComboBox();
			cbbCollectDate.setDate(null);
			cbbCollectDate.setBounds(484, 190, 190, 24);
		}
		return cbbCollectDate;
	}
	/**
	 * 关闭按钮
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillSend.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 修改
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tpnMian.getSelectedIndex() == 0) {
						dataState= DataState.EDIT;
						setHeadState();
					}
					else if (tpnMian.getSelectedIndex() == 1) {
//						dataItemState= DataState.EDIT;
//						setItemState();
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgOwpBillSend.this, "请选择你要修改的资料", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}	
						
						editData();
							
					
					}
				}
			});
		}
		return btnEdit;
	}
	/**
	 * 编缉数据
	 */
	private void editData() {
		DgOwpBillEdit dg = new DgOwpBillEdit();
		dg.setTableModel(tableModel);
		dg.setOutFlag(true);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	}

	/**
	 * 打印按钮
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
		}
		return btnPrint;
	}

	/**
	 * 保存按钮	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (tpnMian.getSelectedIndex() == 0) {
						if (vaildatorDataIsNull()) {
							return;
						}
						fillHeadData();
						owpBillSendHead = owpBillAction.saveOwpSendBillHead(
								new Request(CommonVars.getCurrUser()), owpBillSendHead);
						if (dataState == DataState.ADD) {
							tableModelHead.addRow(owpBillSendHead);
						}else{
							tableModelHead.updateRow(owpBillSendHead);
						}
						dataState = DataState.BROWSE;
						declareState = DeclareState.APPLY_POR;
						setHeadState();
					} 
					
				}
			});
		}
		return btnSave;
	}
	/**
	 * 判断保存表单内容
	 * @return
	 */
	private boolean vaildatorDataIsNull() {
//		if(null==tfBillNo.getText()||"".equals(tfBillNo.getText())){
//			JOptionPane.showMessageDialog(null, "发货单编号不可为空", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		if(null==tfCopBillNo.getText()||"".equals(tfCopBillNo.getText())){
			JOptionPane.showMessageDialog(null, "企业内部编号不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if(null==tfIsDeclaEm.getText()||"".equals(tfIsDeclaEm.getText())){
			JOptionPane.showMessageDialog(null, "发货申报人不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}
	/**
	 * 填充登记表体
	 */
	public void fillSendBillItem(OwpBillSendItem owpSendBillItem){
//		owpSendBillItem.setHead(owpBillSendHead);
//		owpSendBillItem.setAppGNo(Integer.valueOf(tfAppGNo.getText()));
//		owpSendBillItem.setListNo(Integer.valueOf(tfListNo.getText()));
//		owpSendBillItem.setComplex(customBaseAction.findComplexByCode(tfComplex.getText()));
//		owpSendBillItem.setHsName(tfHsName.getText());
//		owpSendBillItem.setHsSpec(tfHsSpec.getText());
//		owpSendBillItem.setQty(Double.valueOf(tfQty.getText()));
//		owpSendBillItem.setNote(tfNote1.getText());
	}
	/**
	 *  填充表头信息
	 */
	private void fillHeadData() {
		if (owpBillSendHead == null) {
			owpBillSendHead = new OwpBillSendHead();
		}
		if (dataState == DataState.ADD) {
			int seqNum =this.owpBillAction.findBillSendHeadMaxNo(
					new Request(CommonVars.getCurrUser()), tfAppNo.getText());
			
			owpBillSendHead.setSeqNum(seqNum+1);
		}
		owpBillSendHead.setAppNo(tfAppNo.getText());
		owpBillSendHead.setSeqNo(tfSeqNo.getText());
		owpBillSendHead.setBillNo(tfBillNo.getText());
		owpBillSendHead.setEmsNo(tfEmsNo.getText());
		owpBillSendHead.setOutTradeCode(tfOutTradeCode.getText());
		owpBillSendHead.setOutTradeName(tfOutTradeName.getText());
		owpBillSendHead.setInTradeCode(tfInTradeCode.getText());
		owpBillSendHead.setInTradeName(tfInTradeName.getText());
		owpBillSendHead.setCopBillNo(tfCopBillNo.getText());
//		owpBillSendHead.setCollectDate(cbbCollectDate.getDate());
		owpBillSendHead.setCreaterDate(cbbCreateDate.getDate());
		owpBillSendHead.setIsDeclaEm(tfIsDeclaEm.getText());
		owpBillSendHead.setNote(tfNote.getText());
		owpBillSendHead.setCompany(CommonUtils.getCompany());
		owpBillSendHead.setCancelMark(0);
		if (dataState == DataState.ADD) {
			owpBillSendHead.setDeclareState(DeclareState.APPLY_POR);
			owpBillSendHead.setIsInsertByApp(isInsertByApp);
		}
	}
	/**
	 * 取消按钮
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					dataState = DataState.BROWSE;
//					if (dataState != DataState.ADD) {
//						if (tableModelHead != null) {
//							owpBillSendHead = (OwpBillSendHead) tableModelHead
//									.getCurrentRow();
//						}
//						showHeadDate();
//					} else {
//						showPrimalData();
//					}
//					setHeadState();
//					setHeadState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 表头面板控件设置
	 */
	private JPanel getPnBillHead() {
		if (pnBillHead == null) {
			lbCreaterDate = new JLabel();
			lbCreaterDate.setBounds(new Rectangle(434, 234, 48, 18));
			lbCreaterDate.setText("录入日期");
			lbCreaterDate.setForeground(Color.blue);
			lbNote = new JLabel();
			lbNote.setBounds(new Rectangle(110, 266, 26, 24));
			lbNote.setText("备注");
			lbIsDeclaEm = new JLabel();
			lbIsDeclaEm.setBounds(new Rectangle(73, 230, 65, 24));
			lbIsDeclaEm.setForeground(Color.blue);
			lbIsDeclaEm.setText("发货申报人");
			lbCollectDate = new JLabel();
			lbCollectDate.setBounds(new Rectangle(433, 190, 52, 24));
			lbCollectDate.setForeground(Color.darkGray);
			lbCollectDate.setText("发货日期");
			lbInTradeName = new JLabel();
			lbInTradeName.setBounds(new Rectangle(397, 149, 87, 24));
			lbInTradeName.setForeground(Color.darkGray);
			lbInTradeName.setText("承接方企业名称");
			lbInTradeCode = new JLabel();
			lbInTradeCode.setBounds(new Rectangle(47, 150, 89, 24));
			lbInTradeCode.setForeground(Color.darkGray);
			lbInTradeCode.setText("承接方企业编码");
			lbOutTradeName = new JLabel();
			lbOutTradeName.setBounds(new Rectangle(395, 109, 89, 24));
			lbOutTradeName.setForeground(Color.darkGray);
			lbOutTradeName.setText("委托方企业名称");
			lbOutTradeCode = new JLabel();
			lbOutTradeCode.setBounds(new Rectangle(50, 110, 87, 24));
			lbOutTradeCode.setForeground(Color.darkGray);
			lbOutTradeCode.setText("委托方企业编码");
			lbCopBillNo = new JLabel();
			lbCopBillNo.setBounds(new Rectangle(60, 190, 75, 24));
			lbCopBillNo.setForeground(Color.blue);
			lbCopBillNo.setText("企业内部编号");
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new Rectangle(360, 69, 123, 24));
			lbEmsNo.setForeground(Color.darkGray);
			lbEmsNo.setText("委托方企业手册/帐册号");
			lbBillNo = new JLabel();
			lbBillNo.setBounds(new Rectangle(71, 70, 65, 24));
			lbBillNo.setText("发货单编号");
			lbSeqNo = new JLabel();
			lbSeqNo.setBounds(new Rectangle(385, 29, 97, 24));
			lbSeqNo.setForeground(Color.darkGray);
			lbSeqNo.setText("电子口岸统一编号");
			lbAppNo = new JLabel();
			lbAppNo.setBounds(new Rectangle(71, 29, 65, 24));
			lbAppNo.setFont(new Font("Dialog", Font.PLAIN, 12));
			lbAppNo.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbAppNo.setForeground(Color.darkGray);
			lbAppNo.setText("申请表编号");
			pnBillHead = new JPanel();
			pnBillHead.setLayout(null);
			pnBillHead.add(getCbbCollectDate(), null);
			pnBillHead.setName("基本信息");
			pnBillHead.add(getTfAppNo(), null);
			pnBillHead.add(lbAppNo, null);
			pnBillHead.add(lbSeqNo, null);
			pnBillHead.add(getTfSeqNo(), null);
			pnBillHead.add(lbBillNo, null);
			pnBillHead.add(getTfBillNo(), null);
			pnBillHead.add(lbEmsNo, null);
			pnBillHead.add(getTfEmsNo(), null);
			pnBillHead.add(lbCopBillNo, null);
			pnBillHead.add(getTfCopBillNo(), null);
			pnBillHead.add(getTfOutTradeCode(), null);
			pnBillHead.add(lbOutTradeCode, null);
			pnBillHead.add(lbOutTradeName, null);
			pnBillHead.add(getTfOutTradeName(), null);
			pnBillHead.add(lbInTradeCode, null);
			pnBillHead.add(getTfInTradeCode(), null);
			pnBillHead.add(lbInTradeName, null);
			pnBillHead.add(getTfInTradeName(), null);
			pnBillHead.add(lbCollectDate, null);
			pnBillHead.add(lbIsDeclaEm, null);
			pnBillHead.add(getTfIsDeclaEm(), null);
			pnBillHead.add(lbNote, null);
			pnBillHead.add(getTfNote(), null);
			pnBillHead.add(lbCreaterDate, null);
			pnBillHead.add(getCbbCreateDate(), null);
		}
		return pnBillHead;
	}
	/**
	 * 登记表体初始化
	 * @param list
	 */
	private void initTableModel(List list) {
		tableModel = new JTableListModel(tbItem, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品序号", "listNo", 100));
						list.add(addColumn("申请表序号", "appGNo", 100));
						list.add(addColumn("手册序号", "trNo", 50,Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "hsName", 100));
						list.add(addColumn("商品规格", "hsSpec", 100));
						list.add(addColumn("计量单位", "hsUnit.name", 100));
						list.add(addColumn("申报数量", "qty", 100));
						list.add(addColumn("备注", "note", 100));
						
						return list;
					}
				});
}
	/**
	 * 表体面板容器设置
	 */
	private JPanel getPnBillItem() {
		if (pnBillItem == null) {
			pnBillItem = new JPanel();
			pnBillItem.setLayout(new BorderLayout());
			pnBillItem.setName("商品明细");
			pnBillItem.add(getJPanel(), BorderLayout.CENTER);
		}
		return pnBillItem;
	}
	/**
	 * 表头窗体控制
	 */
	protected void setHeadState() {
		btnAdd.setVisible(false);
		btnDelete.setVisible(false);
		btnEdit.setEnabled(dataState != DataState.EDIT
				&& (DeclareState.APPLY_POR.equals(declareState)||
						DeclareState.CHANGING_EXE.equals(declareState)));
		btnCancel.setEnabled(dataState != DataState.EDIT
				&& (DeclareState.APPLY_POR.equals(declareState)||
						DeclareState.CHANGING_EXE.equals(declareState)));
		btnSave.setVisible(true);
		btnSave.setEnabled(dataState != DataState.BROWSE );
		tfAppNo.setEditable(false);
		tfSeqNo.setEditable(false);
		tfBillNo.setEditable(false);
		tfEmsNo.setEditable(false );
		tfOutTradeCode.setEditable(false );
		tfOutTradeName.setEditable(false );
		tfInTradeCode.setEditable(false );
		tfInTradeName.setEditable(false );
		cbbCollectDate.setEnabled(false);
		cbbCreateDate.setEnabled(dataState != DataState.BROWSE);
		tfCopBillNo.setEditable(dataState != DataState.BROWSE );
		tfIsDeclaEm.setEditable(dataState != DataState.BROWSE );
		tfNote.setEditable(dataState != DataState.BROWSE );
	}
	/**
	 * 表体窗体控制
	 */
	protected void setItemState() {
		btnEdit.setEnabled(dataItemState != DataState.EDIT && (DeclareState.APPLY_POR.equals(declareState)||
				DeclareState.CHANGING_EXE.equals(declareState)));
		btnCancel.setEnabled(dataItemState != DataState.EDIT && (DeclareState.APPLY_POR.equals(declareState)||
				DeclareState.CHANGING_EXE.equals(declareState)));
		btnAdd.setVisible(true);
		btnAdd.setEnabled(DeclareState.APPLY_POR.equals(declareState)||
				DeclareState.CHANGING_EXE.equals(declareState));
		btnDelete.setVisible(true);
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(declareState)||
				DeclareState.CHANGING_EXE.equals(declareState));
		btnSave.setVisible(false);
	}
	/**
	 * This method initializes tfAppNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(140, 30, 191, 24));
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
			tfSeqNo.setBounds(new Rectangle(484, 30, 190, 24));
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes tfBillNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(new Rectangle(140, 70, 191, 24));
		}
		return tfBillNo;
	}

	/**
	 * This method initializes tfEmsNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(484, 70, 190, 24));
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfCopBillNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCopBillNo() {
		if (tfCopBillNo == null) {
			tfCopBillNo = new JTextField();
			tfCopBillNo.setBounds(new Rectangle(140, 190, 191, 24));
		}
		return tfCopBillNo;
	}

	/**
	 * This method initializes tfOutTradeCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfOutTradeCode() {
		if (tfOutTradeCode == null) {
			tfOutTradeCode = new JTextField();
			tfOutTradeCode.setBounds(new Rectangle(140, 110, 191, 24));
		}
		return tfOutTradeCode;
	}

	/**
	 * This method initializes tfOutTradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfOutTradeName() {
		if (tfOutTradeName == null) {
			tfOutTradeName = new JTextField();
			tfOutTradeName.setBounds(new Rectangle(484, 110, 190, 24));
		}
		return tfOutTradeName;
	}

	/**
	 * This method initializes tfInTradeCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInTradeCode() {
		if (tfInTradeCode == null) {
			tfInTradeCode = new JTextField();
			tfInTradeCode.setBounds(new Rectangle(140, 150, 191, 24));
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
			tfInTradeName.setBounds(new Rectangle(484, 150, 190, 24));
		}
		return tfInTradeName;
	}

	/**
	 * This method initializes tfIsDeclaEm	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfIsDeclaEm() {
		if (tfIsDeclaEm == null) {
			tfIsDeclaEm = new JTextField();
			tfIsDeclaEm.setBounds(new Rectangle(140, 230, 191, 24));
		}
		return tfIsDeclaEm;
	}

	/**
	 * This method initializes tfNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(139, 267, 190, 24));
		}
		return tfNote;
	}

	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	public OwpBillSendHead getOwpSendBillHead() {
		return owpBillSendHead;
	}

	public void setOwpSendBillHead(OwpBillSendHead owpSendBillHead) {
		this.owpBillSendHead = owpSendBillHead;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}
	public OwpAppHead getOwpAppHead() {
		return owpAppHead;
	}
	public void setOwpAppHead(OwpAppHead owpAppHead) {
		this.owpAppHead = owpAppHead;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (pnHead == null) {
			pnHead = new JPanel();
			pnHead.setLayout(new BorderLayout());
			pnHead.add(getJPanel1(), BorderLayout.NORTH);
			pnHead.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pnHead;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (pnLabel == null) {
			pnLabel = new JPanel();
			pnLabel.setLayout(new GridBagLayout());
		}
		return pnLabel;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jpnItem == null) {
			jpnItem = new JScrollPane();
			jpnItem.setPreferredSize(new Dimension(200, 100));
			jpnItem.setViewportView(getJTable());
		}
		return jpnItem;
	}
	/**
	 * 登记表表体事件
	 */
	private JTable getJTable() {
		if (tbItem == null) {
			tbItem = new JTable();
			tbItem.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null) {
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								return;
							}
							owpSendBillItem = (OwpBillSendItem)tableModel.getCurrentRow();
							showCurrentDataItem(owpSendBillItem);

						}
					});
		}
		return tbItem;
	}
	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetItemData();
				}
			});
		}
		return btnAdd;
	}
	
	/**
	 *  新增表体
	 */
	private void SetItemData(){
//		List<ContractExg> contractExgs = FptQuery.getInstance()
//		.findContractExgByProcessExe(parentId, emsNo);
		List<OwpBillSendItem> billList = new ArrayList<OwpBillSendItem>();
		
		if(owpBillSendHead.getIsInsertByApp()){
			//查找该申请表编号的申请表进出货物
			List<OwpAppSendItem> applist =  OwpBillQuery.getInstance().findAllAppSendItem(owpBillSendHead.getAppNo(),owpBillSendHead.getId(),true);
			
			//将申请表货物转换为登记表并填充表格
			if(applist != null && applist.size() > 0){
				billList = owpBillAction.addOwpBillSendItem(new Request(CommonVars
					.getCurrUser()),owpBillSendHead,applist);
			}
		}else{
			//查找单据中心的 2.外发加工出库、3.发货退回 中的料件半成品
			List<BillDetail> billDetailList = OwpBillQuery.getInstance().findBillDetailSendItem(owpBillSendHead.getAppNo(),owpBillSendHead.getId(),true);
			
			//将单据中心货物转换为登记表并填充表格
			if(null != billDetailList && billDetailList.size()>0){
				billList = owpBillAction.addBillDetailSendItem(new Request(CommonVars
						.getCurrUser()),owpBillSendHead,billDetailList);
			}
		}
		if(null!=billList && billList.size()>0){	
			tableModel.addRows(billList);
			editData(billList.get(0));
		}
		
	}
	/**
	 * 编缉数据
	 */
	private void editData(OwpBillSendItem f) {
		DgOwpBillEdit dg = new DgOwpBillEdit();
		dg.setTableModel(tableModel);
		dg.setOutFlag(true);
		dg.setDataState(DataState.EDIT);
//		dg.setFptAppHead(owpBillSendHea);
		dg.setVisible(true);
	}
	/**
	 * This method initializes btnDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 deleteBillItem();
				}
			});
		}
		return btnDelete;
	}
	/**
	 * 删除表体信息
	 */
	private void deleteBillItem(){
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgOwpBillSend.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(DgOwpBillSend.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		OwpBillSendItem owpSendBillItem = (OwpBillSendItem) tableModel
			.getCurrentRow();
		owpBillAction.deleteOwpSendBillItem(new Request(CommonVars.getCurrUser()),
				owpSendBillItem);
		tableModel.deleteRow(owpSendBillItem);
		
	}
	public boolean isInsertByApp() {
		return isInsertByApp;
	}
	public void setInsertByApp(boolean isInsertByApp) {
		this.isInsertByApp = isInsertByApp;
	}
	/**
	 * This method initializes cbbCreateDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbCreateDate() {
		if (cbbCreateDate == null) {
			cbbCreateDate = new JCalendarComboBox();
			cbbCreateDate.setBounds(new Rectangle(485, 231, 189, 22));
			cbbCreateDate.setDate(new Date());
		}
		return cbbCreateDate;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
