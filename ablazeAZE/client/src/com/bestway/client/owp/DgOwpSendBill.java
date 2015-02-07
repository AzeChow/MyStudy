package com.bestway.client.owp;

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
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DgOwpSendBill extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jJToolBarBar = null;
	private JTabbedPane jTabbedPane = null;
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
	protected int dataState = DataState.BROWSE;
	protected int dataItemState = DataState.BROWSE;
	private OwpBillSendHead owpBillSendHead = null;  //  @jve:decl-index=0:
	private OwpAppHead owpAppHead = null;  //  @jve:decl-index=0:
	/**
	 * 外发加工发货登记表表头tableModel
	 */
	private JTableListModel tableModelHead = null;
//	/**
//	 * 外发加工远程服务接口
//	 */
//	private OwpMessageAction owpMessageAction = null;
	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton btnAdd = null;
	private JLabel lbListNo = null;
	private JTextField tfListNo = null;
	private JButton btnListNo = null;
	private JLabel lbAppGNo = null;
	private JTextField tfAppGNo = null;
	private JLabel lbComplex = null;
	private JTextField tfComplex = null;
	private JLabel lbHsName = null;
	private JTextField tfHsName = null;
	private JLabel lbHsSpec = null;
	private JTextField tfHsSpec = null;
	private JLabel lbHsUnit = null;
	private JTextField tfHsUnit = null;
	private JLabel lbQty = null;
	private JTextField tfQty = null;
	private JLabel lbNote1 = null;
	private JTextField tfNote1 = null;
	private OwpBillSendItem owpSendBillItem = null;
	private OwpBillAction owpBillAction = null;
	private JTableListModel tableModel = null;
	private JButton btnDelete = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgOwpSendBill() {
		super();
		/**
		 * 初始化外发加工远程服务接口
		 */
//		owpMessageAction = (OwpMessageAction) CommonVars.getApplicationContext()
//																.getBean("owpMessageAction");
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
				}
				showHeadDate();
			}
			else{
				showPrimalData();
			}
			setHeadState();
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(705, 541));
        this.setContentPane(getJContentPane());
        this.setTitle("外发加工出货登记表");
	}
	/**
	 * 当新增或删除时，显示最初数据
	 */
	protected void showPrimalData() {
		tfAppNo.setText(owpAppHead.getAppNo());
		tfSeqNo.setText(owpAppHead.getSeqNo());
		tfEmsNo.setText(owpAppHead.getEmsNo());
		tfOutTradeCode.setText(owpAppHead.getTradeCode());
		tfOutTradeName.setText(owpAppHead.getTradeName());
		tfInTradeCode.setText(owpAppHead.getInTradeCode());
		tfInTradeName.setText(owpAppHead.getInTradeName());
		tfIsDeclaEm.setText(owpAppHead.getDecl());
	}
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
		tfIsDeclaEm.setText(owpBillSendHead.getIsDeclaEm());
		tfNote.setText(owpBillSendHead.getNote());
		
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
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnSave());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnCancel());
			jJToolBarBar.add(getBtnPrint());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本信息", null, getPnBillHead(), null);
			jTabbedPane.addTab("商品明细", null, getPnBillItem(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					int index = jTabbedPane.getSelectedIndex();
					if (index == 0) {
						btnAdd.setVisible(false);
						btnDelete.setVisible(false);
					} else if (index == 1) {
						btnAdd.setVisible(true);
						btnDelete.setVisible(true);
						setItemState();
						setItemDate();
						List list = new ArrayList();
						if(null != owpBillSendHead){
//							list=owpMessageAction.findOwpSendBillItemByHead(new Request(
//								CommonVars.getCurrUser()),owpSendBillHead);
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
				}
			});
		}
		return jTabbedPane;
	}

	private void showCurrentDataItem(OwpBillSendItem owpSendBillItem){
		if(null != owpSendBillItem){
			tfAppGNo.setText(String.valueOf(owpSendBillItem.getAppGNo()));
			tfListNo.setText(String.valueOf(owpSendBillItem.getListNo()));
			if(null!=owpSendBillItem.getComplex()){
				tfComplex.setText(owpSendBillItem.getComplex().getCode());
			}
			tfHsName.setText(owpSendBillItem.getHsName());
			tfHsSpec.setText(owpSendBillItem.getHsSpec());
			if(null!=owpSendBillItem.getHsUnit()){
				tfHsUnit.setText(owpSendBillItem.getHsUnit().getName());
			}
			tfQty.setText(String.valueOf(owpSendBillItem.getQty()));
			tfNote1.setText(owpSendBillItem.getNote());
		}
		
	}
	private void setItemDate(){
	}
	protected JCalendarComboBox getCbbCollectDate() {
		if (cbbCollectDate == null) {
			cbbCollectDate = new JCalendarComboBox();
//			cbbImpExpDate.addChangeListener(new ChangeListener() {
//				public void stateChanged(ChangeEvent arg0) {
//					cbbDeclarationDate.setDate(cbbImpExpDate.getDate());
//				}
//			});
			cbbCollectDate.setDate(new Date());
			cbbCollectDate.setBounds(521, 180, 131, 20);
//			if (this.customsDeclaration.getImpExpDate() != null) {
//				this.cbbImpExpDate.setDate(this.customsDeclaration
//						.getImpExpDate());
//			} else {
//				this.cbbImpExpDate.setDate(null);
//			}
		}
		return cbbCollectDate;
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
					DgOwpSendBill.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState= DataState.EDIT;
						setHeadState();
					}
					else if (jTabbedPane.getSelectedIndex() == 1) {
						dataItemState= DataState.EDIT;
						setItemState();
					}
				}
			});
		}
		return btnEdit;
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
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
					fillHeadData();
//					owpBillSendHead = owpMessageAction.saveOwpSendBillHead(
//							new Request(CommonVars.getCurrUser()), owpBillSendHead);
					if (dataState == DataState.ADD) {
						tableModelHead.addRow(owpBillSendHead);
					}else{
						tableModelHead.updateRow(owpBillSendHead);
					}
					dataState = DataState.BROWSE;
					setHeadState();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						if(null==owpSendBillItem){
							owpSendBillItem = new OwpBillSendItem();
						}
						fillSendBillItem(owpSendBillItem);
						System.out.println("owpSendBillItem"+owpSendBillItem.getHsName());
//						owpSendBillItem = owpMessageAction.saveOwpSendBillItem(
//								new Request(CommonVars.getCurrUser()), owpSendBillItem);
						JOptionPane.showMessageDialog(null, "保存成功！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						if (dataItemState == DataState.ADD) {
							System.out.println("新增");
							tableModel.addRow(owpSendBillItem);
							
						}else{
							System.out.println("修改");
							tableModel.updateRow(owpSendBillItem);
						}
						dataItemState = DataState.BROWSE;
						setItemState();
					}
					
				}
			});
		}
		return btnSave;
	}
	public void fillSendBillItem(OwpBillSendItem owpSendBillItem){
//		if(dataItemState == DataState.ADD){
//			owpSendBillItem = new OwpSendBillItem();
//		}
		System.out.println("tfHsName.getText()"+tfHsName.getText());
		owpSendBillItem.setHead(owpBillSendHead);
		owpSendBillItem.setAppGNo(Integer.valueOf(tfAppGNo.getText()));
		owpSendBillItem.setListNo(Integer.valueOf(tfListNo.getText()));
		owpSendBillItem.setComplex(customBaseAction.findComplexByCode(tfComplex.getText()));
		owpSendBillItem.setHsName(tfHsName.getText());
		owpSendBillItem.setHsSpec(tfHsSpec.getText());
		System.out.println("owpSendBillItem.setHsName"+owpSendBillItem.getHsName());
//		owpSendBillItem.setHsUnit(tfHsUnit.getText());
		owpSendBillItem.setQty(Double.valueOf(tfQty.getText()));
		owpSendBillItem.setNote(tfNote1.getText());
	}
	private void fillHeadData() {
		if (owpBillSendHead == null) {
			owpBillSendHead = new OwpBillSendHead();
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
		owpBillSendHead.setCollectDate(cbbCollectDate.getDate());
		owpBillSendHead.setIsDeclaEm(tfIsDeclaEm.getText());
		owpBillSendHead.setNote(tfNote.getText());
		owpBillSendHead.setCompany(CommonUtils.getCompany());
	}
	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					if(dataState!=DataState.ADD){
					if (tableModelHead != null) {
						owpBillSendHead = (OwpBillSendHead) tableModelHead
								.getCurrentRow();
					}
					showHeadDate();
				}
				else{
					showPrimalData();
				}
				setHeadState();
					setHeadState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes pnBillHead	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnBillHead() {
		if (pnBillHead == null) {
			lbNote = new JLabel();
			lbNote.setBounds(new Rectangle(383, 224, 96, 18));
			lbNote.setText("备注");
			lbIsDeclaEm = new JLabel();
			lbIsDeclaEm.setBounds(new Rectangle(17, 221, 97, 18));
			lbIsDeclaEm.setText("发货申报人");
			lbCollectDate = new JLabel();
			lbCollectDate.setBounds(new Rectangle(382, 181, 67, 18));
			lbCollectDate.setText("发货日期");
			lbInTradeName = new JLabel();
			lbInTradeName.setBounds(new Rectangle(381, 142, 125, 18));
			lbInTradeName.setText("承接方企业名称");
			lbInTradeCode = new JLabel();
			lbInTradeCode.setBounds(new Rectangle(18, 141, 100, 18));
			lbInTradeCode.setText("承接方企业编码");
			lbOutTradeName = new JLabel();
			lbOutTradeName.setBounds(new Rectangle(380, 106, 129, 18));
			lbOutTradeName.setText("委托方企业名称");
			lbOutTradeCode = new JLabel();
			lbOutTradeCode.setBounds(new Rectangle(19, 105, 102, 18));
			lbOutTradeCode.setText("委托方企业编码");
			lbCopBillNo = new JLabel();
			lbCopBillNo.setBounds(new Rectangle(19, 180, 94, 18));
			lbCopBillNo.setText("企业内部编号");
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new Rectangle(380, 68, 133, 20));
			lbEmsNo.setText("委托方企业手册/帐册号");
			lbBillNo = new JLabel();
			lbBillNo.setBounds(new Rectangle(21, 66, 91, 18));
			lbBillNo.setText("发货单编号");
			lbSeqNo = new JLabel();
			lbSeqNo.setBounds(new Rectangle(379, 32, 112, 18));
			lbSeqNo.setText("电子口岸统一编号");
			lbAppNo = new JLabel();
			lbAppNo.setBounds(new Rectangle(23, 32, 72, 18));
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
		}
		return pnBillHead;
	}
	private void initTableModel(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品序号", "listNo", 100));
						list.add(addColumn("申请表序号", "appGNo", 100));
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
	 * This method initializes pnBillItem	
	 * 	
	 * @return javax.swing.JPanel	
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
		btnEdit.setEnabled(dataState != DataState.EDIT );
		btnSave.setEnabled(dataState != DataState.BROWSE );
		tfAppNo.setEditable(dataState != DataState.BROWSE );
		tfSeqNo.setEditable(dataState != DataState.BROWSE );
		tfBillNo.setEditable(dataState != DataState.BROWSE );
		tfEmsNo.setEditable(dataState != DataState.BROWSE );
		tfOutTradeCode.setEditable(dataState != DataState.BROWSE );
		tfOutTradeName.setEditable(dataState != DataState.BROWSE );
		tfInTradeCode.setEditable(dataState != DataState.BROWSE );
		tfInTradeName.setEditable(dataState != DataState.BROWSE );
		tfCopBillNo.setEditable(dataState != DataState.BROWSE );
		tfIsDeclaEm.setEditable(dataState != DataState.BROWSE );
		tfNote.setEditable(dataState != DataState.BROWSE );
		this.cbbCollectDate.setEnabled(dataState != DataState.BROWSE);
	}
	/**
	 * 表体窗体控制
	 */
	protected void setItemState() {
		btnEdit.setEnabled(true);
		tfAppGNo.setEditable(false );
		btnListNo.setEnabled(dataItemState != DataState.BROWSE);
		tfListNo.setEditable(dataItemState != DataState.BROWSE );
		tfComplex.setEditable(dataItemState != DataState.BROWSE );
		tfHsName.setEditable(dataItemState != DataState.BROWSE );
		tfHsSpec.setEditable(dataItemState != DataState.BROWSE );
		tfHsUnit.setEditable(dataItemState != DataState.BROWSE );
		tfQty.setEditable(dataItemState != DataState.BROWSE );
		tfNote1.setEditable(dataItemState != DataState.BROWSE );
	}
	/**
	 * This method initializes tfAppNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(139, 30, 146, 22));
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
			tfSeqNo.setBounds(new Rectangle(519, 30, 135, 22));
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
			tfBillNo.setBounds(new Rectangle(138, 66, 147, 22));
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
			tfEmsNo.setBounds(new Rectangle(520, 65, 135, 22));
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
			tfCopBillNo.setBounds(new Rectangle(138, 180, 145, 22));
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
			tfOutTradeCode.setBounds(new Rectangle(139, 104, 144, 22));
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
			tfOutTradeName.setBounds(new Rectangle(521, 103, 132, 22));
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
			tfInTradeCode.setBounds(new Rectangle(138, 140, 141, 22));
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
			tfInTradeName.setBounds(new Rectangle(521, 141, 129, 22));
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
			tfIsDeclaEm.setBounds(new Rectangle(139, 223, 143, 22));
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
			tfNote.setBounds(new Rectangle(522, 221, 141, 22));
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
		if (jPanel == null) {
			lbNote1 = new JLabel();
			lbNote1.setBounds(new Rectangle(254, 69, 37, 22));
			lbNote1.setText("备注");
			lbQty = new JLabel();
			lbQty.setBounds(new Rectangle(26, 67, 63, 22));
			lbQty.setText("申报数量");
			lbHsUnit = new JLabel();
			lbHsUnit.setBounds(new Rectangle(464, 38, 60, 22));
			lbHsUnit.setText("计量单位");
			lbHsSpec = new JLabel();
			lbHsSpec.setBounds(new Rectangle(253, 38, 70, 22));
			lbHsSpec.setText("商品规格");
			lbHsName = new JLabel();
			lbHsName.setBounds(new Rectangle(26, 37, 62, 22));
			lbHsName.setText("商品名称");
			lbComplex = new JLabel();
			lbComplex.setBounds(new Rectangle(466, 6, 59, 22));
			lbComplex.setText("商品编码");
			lbAppGNo = new JLabel();
			lbAppGNo.setBounds(new Rectangle(27, 6, 70, 22));
			lbAppGNo.setText("申请表序号");
			lbListNo = new JLabel();
			lbListNo.setBounds(new Rectangle(253, 6, 62, 22));
			lbListNo.setText("商品序号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(lbListNo, null);
			jPanel.add(getTfListNo(), null);
			jPanel.add(getBtnListNo(), null);
			jPanel.add(lbAppGNo, null);
			jPanel.add(getTfAppGNo(), null);
			jPanel.add(lbComplex, null);
			jPanel.add(getTfComplex(), null);
			jPanel.add(lbHsName, null);
			jPanel.add(getTfHsName(), null);
			jPanel.add(lbHsSpec, null);
			jPanel.add(getTfHsSpec(), null);
			jPanel.add(lbHsUnit, null);
			jPanel.add(getTfHsUnit(), null);
			jPanel.add(lbQty, null);
			jPanel.add(getTfQty(), null);
			jPanel.add(lbNote1, null);
			jPanel.add(getTfNote1(), null);
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
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBounds(new Rectangle(0, 0, 690, 0));
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
			jScrollPane.setPreferredSize(new Dimension(200, 100));
			jScrollPane.setBounds(new Rectangle(5, 106, 684, 333));
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
			jTable = new JTable();
			jTable.getSelectionModel().addListSelectionListener(
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
		return jTable;
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
	
	private void SetItemData(){
		dataItemState = DataState.ADD;
		setItemState();
		tfListNo.setText("");
		tfAppGNo.setText(owpAppHead.getAppNo());
		tfComplex.setText("");
		tfHsName.setText("");
		tfHsSpec.setText("");
		tfHsUnit.setText("");
		tfQty.setText("");
		tfNote1.setText("");
		
	}
	/**
	 * This method initializes tfListNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfListNo() {
		if (tfListNo == null) {
			tfListNo = new JTextField();
			tfListNo.setBounds(new Rectangle(319, 6, 110, 22));
		}
		return tfListNo;
	}
	/**
	 * This method initializes btnListNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnListNo() {
		if (btnListNo == null) {
			btnListNo = new JButton();
			btnListNo.setBounds(new Rectangle(429, 6, 26, 21));
			btnListNo.setText("...");
//			btnListNo.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					if(tfAppGNo.getText()!=null){
//						List  list = OwpQuery.getInstance().findAllAppSendItem(tfAppGNo.getText());
//						if (list != null && list.size()>0) {
//							System.out.println("sxk list.size = " + list.size());
//							OwpAppSendItem b = (OwpAppSendItem)list.get(0);
//							if(b != null){
//								if(null!=b.getListNo()){
//									tfListNo.setText(String.valueOf(b.getListNo()));
//								}
//								if(null!=b.getComplex()){
//									tfComplex.setText(b.getComplex().getCode());
//								}
//								tfHsName.setText(b.getHsName());
//								tfHsSpec.setText(b.getHsSpec());
//								tfHsUnit.setText(b.getHsUnit().getName());
//						
//							}
//						}
//					}
//				}
//			});
		}
		return btnListNo;
	}
	/**
	 * This method initializes tfAppGNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppGNo() {
		if (tfAppGNo == null) {
			tfAppGNo = new JTextField();
			tfAppGNo.setBounds(new Rectangle(91, 6, 109, 22));
		}
		return tfAppGNo;
	}
	/**
	 * This method initializes tfComplex	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(527, 6, 107, 22));
		}
		return tfComplex;
	}
	/**
	 * This method initializes tfHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(90, 37, 109, 22));
		}
		return tfHsName;
	}
	/**
	 * This method initializes tfHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new Rectangle(319, 39, 109, 22));
		}
		return tfHsSpec;
	}
	/**
	 * This method initializes tfHsUnit	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsUnit() {
		if (tfHsUnit == null) {
			tfHsUnit = new JTextField();
			tfHsUnit.setBounds(new Rectangle(527, 39, 107, 22));
		}
		return tfHsUnit;
	}
	/**
	 * This method initializes tfQty	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfQty() {
		if (tfQty == null) {
			tfQty = new JTextField();
			tfQty.setBounds(new Rectangle(91, 68, 108, 22));
		}
		return tfQty;
	}
	/**
	 * This method initializes tfNote1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote1() {
		if (tfNote1 == null) {
			tfNote1 = new JTextField();
			tfNote1.setBounds(new Rectangle(318, 70, 109, 22));
		}
		return tfNote1;
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
	private void deleteBillItem(){
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgOwpSendBill.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(DgOwpSendBill.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		OwpBillSendItem owpSendBillItem = (OwpBillSendItem) tableModel
			.getCurrentRow();
//		owpMessageAction.deleteOwpSendBillItem(new Request(
//				CommonVars.getCurrUser()), owpSendBillItem);
		tableModel.deleteRow(owpSendBillItem);
		
	}



}  //  @jve:decl-index=0:visual-constraint="10,10"
