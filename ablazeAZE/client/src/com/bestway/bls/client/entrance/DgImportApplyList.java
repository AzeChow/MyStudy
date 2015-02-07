package com.bestway.bls.client.entrance;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bls.action.MaterielImportApplyAction;
import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.GoodsList;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgImportApplyList extends JDialogBase{

	private JPanel jPanel = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JToolBar jToolBar = null;
	/**
	 * 保存按钮
	 */
	private JButton btnSave = null;
	/**
	 * 表头编辑按钮
	 */
	private JButton btnHeadEdit = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JToolBar jToolBar1 = null;
	/**
	 * 新增物料
	 */
	private JButton btnAddImport = null;
	/**
	 * 删除物料
	 */
	private JButton btnDeleteImport = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	/**
	 * 仓库编码
	 */
	private JComboBox cbbDepotNo = null;
	/**
	 * 申报人
	 */
	private JTextField tfApplyReason = null;
	/**
	 * 备注
	 */
	private JTextField tfNote = null;
	private JPanel jPanel5 = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 物料表格
	 */
	private JTable tableImportList = null;
	
	/**
	 * 保税物料进口申请action
	 */
	private MaterielImportApplyAction materielImportApplyAction = null;  //  @jve:decl-index=0:
	
	/**
	 *  料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	/**
	 * 表头表格模型
	 */
	private JTableListModel tableHeadModel = null;
	
	/**
	 * 商品列表表格模型
	 */
	private JTableListModel tableModelApplyList = null;
	
	/**
	 * 表头
	 */
	private MaterielImportApply materielImportApply=null;
	
	/**
	 * 页面编辑状态
	 */
	private int dataState = DataState.BROWSE;
	private JLabel jLabel13 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel7 = null;
	/**
	 * 录入时间
	 */
	private JCalendarComboBox cbbWriteDate = null;
	/**
	 * 申报时间 
	 */
	private JCalendarComboBox cbbApplyDate = null;
	/**
	 * 录入人
	 */
	private JTextField tfWritePerson = null;
	/**
	 * 申报人
	 */
	private JTextField tfApplyPerson = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgImportApplyList() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(528, 464));
        this.setTitle("保税物料进口申请");
        this.setContentPane(getJPanel());
			
      //初始化materielImportApplyAction
		materielImportApplyAction = (MaterielImportApplyAction)CommonVars
			.getApplicationContext().getBean("materielImportApplyAction");	
		materialManageAction = (MaterialManageAction)CommonVars
		.getApplicationContext().getBean("materialManageAction");
		List wareSetList = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(),true));
		this.cbbDepotNo
				.setModel(new DefaultComboBoxModel(wareSetList.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbDepotNo,
				"code", "name", 250);
		this.cbbDepotNo.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		
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
			jTabbedPane.addTab("保税物料进口申请表头", null, getJPanel1(), null);
			jTabbedPane.addTab("保税物料进口申请商品", null, getJPanel2(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
//					if(dataState!=DataState.BROWSE){
//						JOptionPane.showMessageDialog(DgImportApplyList.this, "请先保存申请表头!");
//						jTabbedPane.setSelectedIndex(0);
//					}
					MaterielImportApply materielImportApply = (MaterielImportApply)tableHeadModel.getCurrentRow();
					if(materielImportApply==null)
						return;
					List list = materielImportApplyAction.findMaterielImportApplyListByHead(new Request(CommonVars.getCurrUser()),materielImportApply);
					initTable(list);
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
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel4(), BorderLayout.CENTER);
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
			jPanel2.add(getJPanel3(), BorderLayout.NORTH);
			jPanel2.add(getJPanel5(), BorderLayout.CENTER);
		}
		return jPanel2;
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
			jToolBar.add(getBtnHeadEdit());
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
					if(cbbDepotNo.getSelectedItem()==null){
						JOptionPane.showMessageDialog(DgImportApplyList.this, "请填写仓库编码!");
						return;
					}
					if(dataState==DataState.ADD){
				       materielImportApply = new MaterielImportApply();
					}
					if(dataState==DataState.EDIT){
					   materielImportApply = (MaterielImportApply)tableHeadModel.getCurrentRow();
					}
					if(materielImportApply==null){
						return;
					}
					materielImportApply.setWarehouseCode((WareSet) cbbDepotNo.getSelectedItem());
					materielImportApply.setApplyReason(tfApplyReason.getText().trim());
					materielImportApply.setNote(tfNote.getText().trim());
					materielImportApply.setCompany(CommonUtils.getCompany());
					materielImportApply.setApplyDate(cbbApplyDate.getDate());
					materielImportApply.setDeclareState(DeclareState.APPLY_POR);
					materielImportApply.setWriteDate(cbbWriteDate.getDate());
					materielImportApply.setApplyPerson(tfApplyPerson.getText());
					materielImportApply.setWritePerson(tfWritePerson.getText());
					materielImportApply=materielImportApplyAction.saveMaterielImportApply(new Request(CommonVars.getCurrUser()),materielImportApply);					
					if(dataState==DataState.EDIT){
					   tableHeadModel.updateRow(materielImportApply);
					}else{
					   tableHeadModel.addRow(materielImportApply);
					}
					setDataState(DataState.BROWSE);					
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnHeadEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHeadEdit() {
		if (btnHeadEdit == null) {
			btnHeadEdit = new JButton();
			btnHeadEdit.setText("修改");
			btnHeadEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDataState(DataState.EDIT);
				}
			});
		}
		return btnHeadEdit;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJToolBar1(), BorderLayout.NORTH);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(40, 163, 36, 28));
			jLabel7.setText("\u7533\u62a5\u4eba");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(40, 102, 36, 28));
			jLabel5.setText("\u5f55\u5165\u4eba");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(293, 108, 48, 28));
			jLabel6.setText("\u7533\u62a5\u65f6\u95f4");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(294, 45, 48, 28));
			jLabel13.setText("\u5f55\u5165\u65f6\u95f4");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(39, 284, 37, 28));
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("备注");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(22, 220, 54, 28));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("申请原因");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(23, 45, 53, 28));
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setText("仓库编码");
			jLabel.setForeground(Color.BLUE);
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.add(jLabel, null);
			jPanel4.add(jLabel1, null);
			jPanel4.add(jLabel2, null);
			jPanel4.add(getCbbDepotNo(), null);
			jPanel4.add(getTfApplyReason(), null);
			jPanel4.add(getTfNote(), null);
			jPanel4.add(jLabel13, null);
			jPanel4.add(jLabel6, null);
			jPanel4.add(jLabel5, null);
			jPanel4.add(jLabel7, null);
			jPanel4.add(getCbbWriteDate(), null);
			jPanel4.add(getCbbApplyDate(), null);
			jPanel4.add(getTfWritePerson(), null);
			jPanel4.add(getTfApplyPerson(), null);
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
			jToolBar1.add(getBtnAddImport());
			jToolBar1.add(getBtnDeleteImport());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAddImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddImport() {
		if (btnAddImport == null) {
			btnAddImport = new JButton();
			btnAddImport.setText("新增");
			btnAddImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<GoodsList> goodsListS = findTradeDepot();
					if(goodsListS==null){
						return;
					}
					materielImportApplyAction.saveMaterielImportApplyList(new Request(CommonVars.getCurrUser()),goodsListS);
					tableModelApplyList.addRows(goodsListS);
				}
			});
		}
		return btnAddImport;
	}

	/**
	 * This method initializes btnDeleteImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDeleteImport() {
		if (btnDeleteImport == null) {
			btnDeleteImport = new JButton();
			btnDeleteImport.setText("删除");
			btnDeleteImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelApplyList.getCurrentRows();
					if(list==null){
						return;
					}
					materielImportApplyAction.deleteMaterielImportApplyList(new Request(CommonVars.getCurrUser()),list);
					tableModelApplyList.deleteRows(list);
				}
			});
		}
		return btnDeleteImport;
	}

	/**
	 * This method initializes tfDepotNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JComboBox getCbbDepotNo() {
		if (cbbDepotNo == null) {
			cbbDepotNo = new JComboBox();
			cbbDepotNo.setBounds(new Rectangle(99, 45, 160, 28));
			cbbDepotNo.setSelectedItem(null);
		}
		return cbbDepotNo;
	}

	/**
	 * This method initializes tfApplyCause	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfApplyReason() {
		if (tfApplyReason == null) {
			tfApplyReason = new JTextField();			
			tfApplyReason.setBounds(new Rectangle(99, 216, 384, 28));
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
			tfNote.setBounds(new Rectangle(99, 282, 385, 28));
		}
		return tfNote;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTableImportList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tableImportList	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTableImportList() {
		if (tableImportList == null) {
			tableImportList = new JTable();
		}
		return tableImportList;
	}

	public JTableListModel getTableHeadModel() {
		return tableHeadModel;
	}

	public void setTableHeadModel(JTableListModel tableHeadModel) {
		this.tableHeadModel = tableHeadModel;
	}
	
	/**
	 * 设置界面显示状态
	 */
	private void setState(){
		btnSave.setEnabled(dataState==DataState.BROWSE?false:true);		
		btnHeadEdit.setEnabled(dataState==DataState.BROWSE?true:false);
		tfApplyReason.setEditable(dataState==DataState.BROWSE?false:true);
		cbbDepotNo.setEnabled(dataState==DataState.BROWSE?false:true);
		cbbApplyDate.setEnabled(dataState==DataState.BROWSE?false:true);
		cbbWriteDate.setEnabled(dataState==DataState.BROWSE?false:true);
		tfApplyPerson.setEditable(dataState==DataState.BROWSE?false:true);
		tfWritePerson.setEditable(dataState==DataState.BROWSE?false:true);
		tfNote.setEditable(dataState==DataState.BROWSE?false:true);
		
		jTabbedPane.setEnabled(dataState != DataState.ADD);
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
		setState();
	}
	
	private void initTable(List list){
		tableModelApplyList = new JTableListModel(this.tableImportList, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("海关商品编码", "codeTS.code", 100));
						list.add(addColumn("海关商品名称", "codeTS.name", 100));
						return list;
					}
				});
	}
	
	/**
	 * 查询商品库商品
	 */
	private List<GoodsList> findTradeDepot(){
		final MaterielImportApply materielImportApply = (MaterielImportApply)tableHeadModel.getCurrentRow();
		if(materielImportApply==null)
			return null;
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("海关商品编码", "code", 100));
		list.add(new JTableListColumn("海关商品名称", "name", 200));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {				
			    return materielImportApplyAction.findComplex(new Request(CommonVars.getCurrUser()),property, (String)value,materielImportApply);
			    }
		};
		dgCommonQuery.setTitle("商品库商品查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if(dgCommonQuery.isOk()){
			List<GoodsList> goodsLists = new ArrayList<GoodsList>();			
			List blsTenInnerMergeList = dgCommonQuery.getReturnList();
			for(int i=0,size=blsTenInnerMergeList.size();i<size;i++){
				GoodsList goodsList = new GoodsList();
				Complex complex = (Complex)blsTenInnerMergeList.get(i);
				goodsList.setCodeTS(complex);
				goodsList.setMaterielImportApply(materielImportApply);
				goodsList.setCompany(CommonUtils.getCompany());
				goodsLists.add(goodsList);				
			}			
			return goodsLists;
		}
		return null;
	}
	/**
	 * 显示数据
	 */
	public void showData(){
		MaterielImportApply materielImportApply = (MaterielImportApply)tableHeadModel.getCurrentRow();
		if(materielImportApply==null)
			return;
		
		cbbDepotNo.setSelectedItem(materielImportApply.getWarehouseCode());
		tfApplyReason.setText(materielImportApply.getApplyReason());
		tfNote.setText(materielImportApply.getNote());
		
		cbbApplyDate.setDate(materielImportApply.getApplyDate());
		cbbWriteDate.setDate(materielImportApply.getWriteDate());
		tfApplyPerson.setText(materielImportApply.getApplyPerson());
		tfWritePerson.setText(materielImportApply.getWritePerson());
	}

	/**
	 * This method initializes cbbWriteDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbWriteDate() {
		if (cbbWriteDate == null) {
			cbbWriteDate = new JCalendarComboBox();
			cbbWriteDate.setBounds(new Rectangle(368, 45, 113, 28));
		}
		return cbbWriteDate;
	}

	/**
	 * This method initializes cbbApplyDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbApplyDate() {
		if (cbbApplyDate == null) {
			cbbApplyDate = new JCalendarComboBox();
			cbbApplyDate.setBounds(new Rectangle(367, 112, 113, 28));
		}
		return cbbApplyDate;
	}

	/**
	 * This method initializes tfWritePerson	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWritePerson() {
		if (tfWritePerson == null) {
			tfWritePerson = new JTextField();
			tfWritePerson.setBounds(new Rectangle(99, 108, 160, 28));
		}
		return tfWritePerson;
	}

	/**
	 * This method initializes tfApplyPerson	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfApplyPerson() {
		if (tfApplyPerson == null) {
			tfApplyPerson = new JTextField();
			tfApplyPerson.setBounds(new Rectangle(99, 165, 160, 28));
		}
		return tfApplyPerson;
	}
	

}  //  @jve:decl-index=0:visual-constraint="27,7"
