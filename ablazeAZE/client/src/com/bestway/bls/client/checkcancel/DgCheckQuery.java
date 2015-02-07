package com.bestway.bls.client.checkcancel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.client.storagebill.FmBillCorresponding;
import com.bestway.bls.entity.FormType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.editor.MessageInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

public class DgCheckQuery extends JDialogBase{

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JTable tbTtorageBill = null;
	private JTableListModel tableModel;
	private BlsCheckCancelAction blsCheckCancelAction = null;
	private String ioFlag;
	private String formType;
	private String collateFormType;

	/**
	 * This method initializes 
	 * 
	 */
//	public DgCheckQuery() {
//		super();
//		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
//		       .getApplicationContext().getBean("blsCheckCancelAction");
//		initialize();
//	}
//	
	public DgCheckQuery(String ioFlag,String formType,String collateFormType) {
		super();
		this.ioFlag = ioFlag;
		this.formType = formType;
		this.collateFormType = collateFormType;
		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
		       .getApplicationContext().getBean("blsCheckCancelAction");
		initialize();
	}
	
	
	
	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		initTable(getNoCheckStoregeBill());
		super.setVisible(b);
	}



	private JTableListModel initTable(final List list) {
		if (FormType.IM_MFT.equals(formType)
				&& FormType.IM_ENT.equals(collateFormType)) {// 入仓单--进口报关单
			tableModel = new JTableListModel(tbTtorageBill, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("入仓单编号", "storageBill.billNo",
									100));
							list.add(addColumn("入仓单商品序号", "seqNo", 100));
							list.add(addColumn("入仓单商品编码", "codeTS.code", 100));
							list.add(addColumn("入仓单商品名称", "name", 100));
							list.add(addColumn("入仓单商品规格", "model", 100));
							list.add(addColumn("入仓单申报单位", "unit.name", 100));
							list.add(addColumn("入仓单申报数量", "qty", 100));
							list.add(addColumn("进口报关单编号", "entryID", 100));
							list.add(addColumn("进口报关单商品序号", "entryGNo", 100));
							return list;
						}
					});
		}else if(FormType.EX_MFT.equals(formType)
				&& FormType.EX_ENT.equals(collateFormType)){//出仓单--出口报关单
			tableModel = new JTableListModel(tbTtorageBill, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("出仓单编号", "storageBill.billNo",
									100));
							list.add(addColumn("出仓单商品序号", "seqNo", 100));
							list.add(addColumn("出仓单商品编码", "codeTS.code", 100));
							list.add(addColumn("出仓单商品名称", "name", 100));
							list.add(addColumn("出仓单商品规格", "model", 100));
							list.add(addColumn("出仓单申报单位", "unit.name", 100));
							list.add(addColumn("出仓单申报数量", "qty", 100));
							list.add(addColumn("出口报关单编号", "entryID", 100));
							list.add(addColumn("出口报关单商品序号", "entryGNo", 100));
							return list;
						}
					});
		}else if(FormType.EX_MFT.equals(formType)
				&& FormType.IM_MFT.equals(collateFormType)){//出仓单--入仓单
			tableModel = new JTableListModel(tbTtorageBill, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("出仓单编号", "storageBill.billNo",
									100));
							list.add(addColumn("出仓单商品序号", "seqNo", 100));
							list.add(addColumn("入仓单编号", "corrBillNo", 100));
							list.add(addColumn("入仓单商品序号", "corrBillGNo", 100));
							list.add(addColumn("出仓单商品编码", "codeTS.code", 100));
							list.add(addColumn("出仓单商品名称", "name", 100));
							list.add(addColumn("出仓单商品规格", "model", 100));
							list.add(addColumn("出仓单申报单位", "unit.name", 100));
							list.add(addColumn("出仓单申报数量", "qty", 100));
							
							return list;
						}
					});
		}
		return tableModel;
	}
	
	private List getNoCheckStoregeBill(){
		System.out.println("初始查询...");
		System.out.println("client:"+formType+"    "+collateFormType);
		return blsCheckCancelAction.findNoCheckBill(new Request(
				CommonVars.getCurrUser()), ioFlag,formType,collateFormType);
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(627, 412));
        this.setContentPane(getJPanel());
        this.setTitle("核销导入");
			
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
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel2.setLayout(new FlowLayout());
			jPanel2.add(getJButton());
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("核销导入");
			jButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new BillCheck().execute();
				}
				
			});
		}
		return jButton;
	}
	
	class BillCheck extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			List list = tableModel.getCurrentRows();
			if(list==null || list.size()<=0 ){
				JOptionPane.showMessageDialog(
						DgCheckQuery.this, 
						"请选择要核销的仓单", 
						"自动核销", 
						JOptionPane.OK_OPTION);
				return null;
			}
			CommonProgress.showProgressDialog(DgCheckQuery.this);
			CommonProgress.setMessage("系统正在处理数据，请稍后..."); 
			blsCheckCancelAction.checkBill(new Request(
							CommonVars.getCurrUser()), list,
							formType, 
							collateFormType);
			CommonProgress.closeProgressDialog();			
			DgCheckQuery.this.dispose();
			return null;
		}
		
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbTtorageBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbTtorageBill	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbTtorageBill() {
		if (tbTtorageBill == null) {
			tbTtorageBill = new JTable();
		}
		return tbTtorageBill;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
