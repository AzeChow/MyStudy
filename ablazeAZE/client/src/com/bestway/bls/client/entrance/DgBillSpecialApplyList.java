package com.bestway.bls.client.entrance;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.action.BillSpecialApplyAction;
import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BillSpecialApplyType;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 仓单特殊申请表头编辑
 * @author chen
 *
 */
public class DgBillSpecialApplyList extends JDialogBase{

	private JPanel jPanel = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel = null;
	/**
	 * 企业编码
	 */
	private JTextField tfTradeCo = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox cbbWriteDate = null;
	private JCalendarComboBox cbbApplyDate = null;
	/**
	 * 特殊仓单申请类型
	 */
	private JComboBox cbbApplyType = null;
	private JLabel jLabel3 = null;
	/**
	 * 申请原因
	 */
	private JTextField tfApplyReason = null;
	private JLabel jLabel31 = null;
	/**
	 * 备注
	 */
	private JTextField tfNote = null;
	private JToolBar jToolBar = null;
	/**
	 * 保存
	 */
	private JButton btnSave = null;
	private JPanel jPanel4 = null;
	private JToolBar jToolBar1 = null;
	/**
	 * 编辑成品
	 */
	private JButton btnEditProducter = null;
	/**
	 * 删除成品
	 */
	private JButton btnDeleteProduct = null;
	/**
	 * 添加成品
	 */
	private JButton btnAddProduct = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 商品列表表格
	 */
	private JTable applyListTable = null;
	
	/**
	 * 商品列表表格表格模型
	 */
	private JTableListModel applyTableModel = null;
	/**
	 * 表头窗口状态
	 */
	private int dataState = DataState.BROWSE;
	
	/**
	 * 仓单特殊申请表头实体
	 */
	private BillSpecialApplyHead billSpecialApplyHead;  //  @jve:decl-index=0:
	/**
	 * 仓单特殊申请表表格模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 仓单特殊申请Action
	 */
	BillSpecialApplyAction billSpecialApplyAction = null;  //  @jve:decl-index=0:
	private JTextField tfBillNo = null;
	private JButton btnEdit = null;
	private JButton btnSerch = null;
	private JLabel jLabel4 = null;
	/**
	 * 企业名称
	 */
	private JTextField tfTradeName = null;
	private JLabel jLabel13 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	/**
	 * 申报人
	 */
	private JTextField tfApplyPerson = null;
	
	/**
	 * 写入人
	 */
	private JTextField tfWritePerson = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgBillSpecialApplyList() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(574, 445));
        this.setTitle("仓单特殊申请");
        this.setContentPane(getJPanel());
        
        // 初始化属性
		billSpecialApplyAction = (BillSpecialApplyAction) CommonVars
				.getApplicationContext().getBean("billSpecialApplyAction");
        
        //初始化特殊申请类型
        cbbApplyType.removeAllItems();
        cbbApplyType.addItem(new ItemProperty(BillSpecialApplyType.PRC,"增值性加工"));
        cbbApplyType.addItem(new ItemProperty(BillSpecialApplyType.ABD,"弃货"));
        cbbApplyType.addItem(new ItemProperty(BillSpecialApplyType.RPL,"换货"));
        cbbApplyType.addItem(new ItemProperty(BillSpecialApplyType.CAN,"仓单作废"));
        cbbApplyType.addItem(new ItemProperty(BillSpecialApplyType.DLA,"货物延期存放"));
        cbbApplyType.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbApplyType);
		cbbApplyType.setSelectedItem(null);
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
			jPanel.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("仓单特殊申请表头", null, getJPanel1(), null);
			jTabbedPane.addTab("仓单特殊申请商品", null, getJPanel2(), "");
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {					
					if(jTabbedPane.getSelectedIndex()==1){	
//						if(dataState!=DataState.BROWSE){
//							JOptionPane.showMessageDialog(DgBillSpecialApplyList.this, "请先保存表头！");
//							jTabbedPane.setSelectedIndex(0);
//							return;
//						}
						BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead)tableModel.getCurrentRow();						
						if(billSpecialApplyHead==null || "".equals(billSpecialApplyHead.getBillNo())){
							return;
						}
//						String billNo = billSpecialApplyHead.getBillNo();
						List<BillSpecialApplyApplyList> applyLists =
							billSpecialApplyAction.findApplyListByBillNo(new Request(CommonVars.getCurrUser()),billSpecialApplyHead);											
						initTable(applyLists);
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
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel3(), BorderLayout.CENTER);
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jPanel1;
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
			jPanel2.add(getJPanel4(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(320, 183, 37, 22));
			jLabel7.setText("申报人");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(34, 183, 69, 22));
			jLabel6.setText("申报时间");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(320, 125, 37, 22));
			jLabel5.setText("录入人");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(34, 125, 69, 22));
			jLabel13.setText("录入时间");
			jLabel4 = new JLabel();			
			jLabel4.setBounds(new Rectangle(308, 33, 49, 22));
			jLabel4.setForeground(Color.BLUE);
			jLabel4.setText("企业名称");			
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(55, 294, 48, 22));
			jLabel31.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel31.setText("备注");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(44, 239, 59, 22));
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3.setText("申请原因");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(26, 78, 78, 22));
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("特殊申请类型");
			jLabel2.setForeground(Color.BLUE);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(310, 78, 48, 22));
			jLabel1.setText("仓单号");
			jLabel1.setForeground(Color.BLUE);
			jLabel = new JLabel();		
			jLabel.setText("企业编码");
			jLabel.setForeground(Color.BLUE);
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setBounds(new Rectangle(52, 32, 51, 22));			
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(jLabel, null);
			jPanel3.add(getTfTradeCo(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getCbbApplyType(), null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(getTfApplyReason(), null);
			jPanel3.add(jLabel31, null);
			jPanel3.add(getTfNote(), null);
			jPanel3.add(getCbbBillNo(), null);
			jPanel3.add(getBtnSerch(), null);
			jPanel3.add(jLabel4, null);
			jPanel3.add(getTfTradeName(), null);
			jPanel3.add(jLabel13, null);
			jPanel3.add(getCbbWriteDate(), null);
			jPanel3.add(jLabel5, null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(getCbbApplyDate(), null);
			jPanel3.add(jLabel7, null);
			jPanel3.add(getTfApplyPerson(), null);
			jPanel3.add(getTfWritePerson(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes tfTradeCo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTradeCo() {
		if (tfTradeCo == null) {
			tfTradeCo = new JTextField();
			tfTradeCo.setPreferredSize(new Dimension(4, 22));
			tfTradeCo.setSize(new Dimension(145, 22));
			tfTradeCo.setLocation(new Point(125, 33));				
		}
		return tfTradeCo;
	}

	/**
	 * This method initializes cbbApplyType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbApplyType() {
		if (cbbApplyType == null) {
			cbbApplyType = new JComboBox();
			cbbApplyType.setBounds(new Rectangle(126, 78, 145, 22));
		}
		return cbbApplyType;
	}

	/**
	 * This method initializes tfApplyReason	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfApplyReason() {
		if (tfApplyReason == null) {
			tfApplyReason = new JTextField();
			tfApplyReason.setBounds(new Rectangle(125, 239, 402, 22));
			tfApplyReason.setPreferredSize(new Dimension(4, 22));
		}
		return tfApplyReason;
	}

	/**
	 * This method initializes tfNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(125, 295, 403, 22));
			tfNote.setPreferredSize(new Dimension(4, 22));
		}
		return tfNote;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
		}
		return jToolBar;
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
					String billNo = tfBillNo.getText().trim();
					if(billNo==null || "".equals(billNo)){
						JOptionPane.showMessageDialog(DgBillSpecialApplyList.this, "请选择申请仓单");
						return;
					}
					ItemProperty itemProperty = ((ItemProperty)cbbApplyType.getSelectedItem());					
					if(itemProperty==null || itemProperty.getCode().equals("")){
						JOptionPane.showMessageDialog(DgBillSpecialApplyList.this, "请选择申请类型");
						return;
					}					
					if(dataState==DataState.ADD){
					   billSpecialApplyHead = new BillSpecialApplyHead();
					}
					billSpecialApplyHead.setTradeCo(tfTradeCo.getText());
					billSpecialApplyHead.setTradeName(tfTradeName.getText());
					billSpecialApplyHead.setBillNo(billNo);
					billSpecialApplyHead.setApplyType(itemProperty.getCode());
					billSpecialApplyHead.setApplyReason(tfApplyReason.getText());
					billSpecialApplyHead.setNote(tfNote.getText());
					billSpecialApplyHead.setDeclareState(DeclareState.APPLY_POR);
					billSpecialApplyHead.setCompany(CommonUtils.getCompany());
					billSpecialApplyHead.setApplyDate(cbbApplyDate.getDate());
					billSpecialApplyHead.setWriteDate(cbbWriteDate.getDate());
					billSpecialApplyHead.setApplyPerson(tfApplyPerson.getText());
					billSpecialApplyHead.setWritePerson(tfWritePerson.getText());
					billSpecialApplyHead=billSpecialApplyAction.saveBillSpecialApplyHead(new Request(CommonVars.getCurrUser()),billSpecialApplyHead);	
					if(dataState==DataState.ADD){
					   tableModel.addRow(billSpecialApplyHead);
					}else if(dataState==DataState.EDIT){
						tableModel.updateRow(billSpecialApplyHead);
					}
					setDataState(DataState.BROWSE);
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 设置表头初始化数据
	 * @param billSpecialApplyHead
	 */
	public void setInitData(BillSpecialApplyHead billSpecialApplyHead){	
		this.billSpecialApplyHead = billSpecialApplyHead;
		if(billSpecialApplyHead==null || billSpecialApplyHead.getTradeCo()==null || billSpecialApplyHead.getTradeCo().equals("")){
			Company company = (Company)CommonVars.getCurrUser().getCompany();
			tfTradeCo.setText(company.getCode());
			tfTradeName.setText(company.getName());
		}else{
		    tfTradeCo.setText(billSpecialApplyHead.getTradeCo());
		    tfTradeName.setText(billSpecialApplyHead.getTradeName());
		}
		if(billSpecialApplyHead==null){
			return;
		}
		tfBillNo.setText(billSpecialApplyHead.getBillNo());
		cbbApplyType.setSelectedIndex(ItemProperty.getIndexByCode(billSpecialApplyHead.getApplyType(), cbbApplyType));
		tfApplyReason.setText(billSpecialApplyHead.getApplyReason());
		tfNote.setText(billSpecialApplyHead.getNote());
		cbbApplyDate.setDate(billSpecialApplyHead.getApplyDate());
		cbbWriteDate.setDate(billSpecialApplyHead.getWriteDate());
		tfApplyPerson.setText(billSpecialApplyHead.getApplyPerson());
		tfWritePerson.setText(billSpecialApplyHead.getWritePerson());
	}
	
	/**
	 * 初始化表格
	 * @param list
	 */
	private void initTable(List list){		
		applyTableModel = new JTableListModel(this.applyListTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品项号", "seqNo", 100));
						list.add(addColumn("商品编码", "qtCode", 100));
						list.add(addColumn("商品名称", "qtName", 100));
						list.add(addColumn("商品规格", "qtModel", 100));
						list.add(addColumn("商品单位", "qtUnit", 100));
						list.add(addColumn("数量", "qtTy", 100));		
						return list;
					}
				});
	}
	
	/**
	 * 查询商品库商品
	 */
	private List<BillSpecialApplyApplyList> findTradeDepot(final String tfBillNo,final boolean isFilt ){
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品项号", "seqNo", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "model", 100));
		list.add(new JTableListColumn("商品单位", "unit.name", 100));
		list.add(new JTableListColumn("商品编码", "codeTS.code", 100));
		list.add(new JTableListColumn("申报数量", "qty", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {				
			    return billSpecialApplyAction.findBillDepot(new Request(CommonVars.getCurrUser()),tfBillNo, isFilt);
			}
		};
		dgCommonQuery.setTitle("仓单商品查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if(dgCommonQuery.isOk()){
			List depotList = dgCommonQuery.getReturnList();			
			List<BillSpecialApplyApplyList> applyList = new ArrayList<BillSpecialApplyApplyList>();
			for(int i=0,size=depotList.size();i<size;i++){
				BillSpecialApplyApplyList apply = new BillSpecialApplyApplyList();
				StorageBillAfter storageBillAfter = ((StorageBillAfter)depotList.get(i));
				apply.setBillSpecialApplyHead((BillSpecialApplyHead)tableModel.getCurrentRow());
				apply.setSeqNo(storageBillAfter.getSeqNo());
				apply.setQtTy(0d);
				apply.setCompany(CommonUtils.getCompany());	
				apply.setQtCode(storageBillAfter.getCodeTS().getCode());
				apply.setQtName(storageBillAfter.getName());
				apply.setQtModel(storageBillAfter.getModel());
				if(storageBillAfter.getUnit()!=null){
				   apply.setQtUnit(storageBillAfter.getUnit().getName());
				}
				applyList.add(apply);
			}
			return applyList;
		}
		return null;
	}
	
	/**
	 * 查询所有仓单号
	 */
	private String findBillNo(){
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("仓单号", "billNo", 100));
//		list.add(new JTableListColumn("商品名称", "name", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {				
			    return billSpecialApplyAction.findStorageBill(new Request(CommonVars.getCurrUser()));
			}
		};
		dgCommonQuery.setTitle("仓单查询");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if(dgCommonQuery.isOk()){
			StorageBill bill = (StorageBill)dgCommonQuery.getReturnValue();		
			if(bill==null){
				return "";
			}
			return bill.getBillNo();
		}
		return "";
	}
	
	/**
	 * 设置状态
	 */
	public void setState(){	
		btnSerch.setEnabled(this.dataState==DataState.BROWSE?false:true);
		tfTradeCo.setEditable(this.dataState==DataState.BROWSE?false:true);
		tfTradeName.setEditable(this.dataState==DataState.BROWSE?false:true);
		tfApplyReason.setEditable(this.dataState==DataState.BROWSE?false:true);
		tfWritePerson.setEditable(this.dataState==DataState.BROWSE?false:true);
		tfApplyPerson.setEditable(this.dataState==DataState.BROWSE?false:true);
		tfNote.setEditable(this.dataState==DataState.BROWSE?false:true);
//		cbbApplyType.setEditable(this.dataState==DataState.BROWSE?false:true);
		cbbApplyType.setEnabled(this.dataState==DataState.BROWSE?false:true);
		tfBillNo.setEditable(this.dataState==DataState.BROWSE?false:true);
		btnSave.setEnabled(this.dataState==DataState.BROWSE?false:true);
		btnEdit.setEnabled(this.dataState==DataState.BROWSE?true:false);
		cbbWriteDate.setEnabled(this.dataState==DataState.BROWSE?false:true);
		cbbApplyDate.setEnabled(this.dataState==DataState.BROWSE?false:true);		
		jTabbedPane.setEnabled(dataState != DataState.ADD);
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel4.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddProduct());
			jToolBar1.add(getBtnEditProducter());
			jToolBar1.add(getBtnDeleteProduct());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnEditProducter	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEditProducter() {
		if (btnEditProducter == null) {
			btnEditProducter = new JButton();
			btnEditProducter.setText("修改");
			btnEditProducter.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					if(applyListTable.getSelectedRowCount()>0){
						DgApplyListEdit dgApplyListEdit = new DgApplyListEdit();
						dgApplyListEdit.setApplyTableModel(applyTableModel);
						dgApplyListEdit.showDate();
						dgApplyListEdit.setState();						
						dgApplyListEdit.setVisible(true);
					}
				}
			
			});
		}
		return btnEditProducter;
	}

	/**
	 * This method initializes btnDeleteProduct	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDeleteProduct() {
		if (btnDeleteProduct == null) {
			btnDeleteProduct = new JButton();
			btnDeleteProduct.setText("删除");
			btnDeleteProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List<BillSpecialApplyApplyList> selectList = applyTableModel.getCurrentRows();
							billSpecialApplyAction.deleteApplyList(new Request(CommonVars.getCurrUser()), selectList);
							applyTableModel.deleteRows(selectList);
						}
					});
		}
		return btnDeleteProduct;
	}

	/**
	 * This method initializes btnAddProduct	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddProduct() {
		if (btnAddProduct == null) {
			btnAddProduct = new JButton();
			btnAddProduct.setText("新增");
			btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<BillSpecialApplyApplyList> list = findTradeDepot(tfBillNo.getText().trim(),false);
					if(list==null || list.size()==0){
						return;
					}
					billSpecialApplyAction.saveApplyList(new Request(CommonVars.getCurrUser()),list);
					applyTableModel.addRows(list);					
				}
			});
		}
		return btnAddProduct;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getApplyListTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes applyListTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getApplyListTable() {
		if (applyListTable == null) {
			applyListTable = new JTable();
			applyListTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						btnEdit.doClick();
					}
				}
			});
		}
		return applyListTable;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes cbbBillNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JTextField getCbbBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(new Rectangle(381, 78, 121, 22));
			tfBillNo.setEditable(true);
		}
		return tfBillNo;
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
					setDataState(DataState.EDIT);
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSerch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSerch() {
		if (btnSerch == null) {
			btnSerch = new JButton();
			btnSerch.setBounds(new Rectangle(501, 78, 26, 22));
			btnSerch.setText("...");
			btnSerch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String billNo = findBillNo();
					tfBillNo.setText(billNo);
				}
			});
		}
		return btnSerch;
	}

	/**
	 * This method initializes tfTradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(380, 32, 147, 22));
		}
		return tfTradeName;
	}

	/**
	 * This method initializes tfApplyPerson	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfApplyPerson() {
		if (tfApplyPerson == null) {
			tfApplyPerson = new JTextField();
			tfApplyPerson.setBounds(new Rectangle(380, 183, 147, 22));
		}
		return tfApplyPerson;
	}

	/**
	 * This method initializes tfWritePerson	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWritePerson() {
		if (tfWritePerson == null) {
			tfWritePerson = new JTextField();
			tfWritePerson.setBounds(new Rectangle(380, 125, 147, 22));
		}
		return tfWritePerson;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbWriteDate() {
		if (cbbWriteDate == null) {
			cbbWriteDate = new JCalendarComboBox();
			cbbWriteDate.setBounds(new Rectangle(125, 125, 145, 22));
		}
		return cbbWriteDate;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbApplyDate() {
		if (cbbApplyDate == null) {
			cbbApplyDate = new JCalendarComboBox();
			cbbApplyDate.setBounds(new Rectangle(125, 183, 145, 22));
		}
		return cbbApplyDate;
	}
	

}  //  @jve:decl-index=0:visual-constraint="97,19"
