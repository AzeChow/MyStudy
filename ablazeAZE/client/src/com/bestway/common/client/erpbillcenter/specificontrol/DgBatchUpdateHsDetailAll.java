package com.bestway.common.client.erpbillcenter.specificontrol;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.ErpBillCenterQuery;
import com.bestway.ui.winuicontrol.JDialogBase;
@SuppressWarnings("unchecked")
public class DgBatchUpdateHsDetailAll extends JDialogBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel5 = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JTextField jtComplex = null;
	private JButton jtSelect = null;
	private JTextField jtHsName = null;
	private JTextField jtHsSpec = null;
	private JTextField jtHsUnit = null;
	private JFormattedTextField jtUnitConvert = null;
	private JButton jtOk = null;
	private JButton jbCancel = null;
	private Complex complex = null;  //  @jve:decl-index=0:
	private Unit unit = null;
	private JLabel jLabel12 = null;
	private JFormattedTextField jtHsVersion = null;
	private JTableListModel tableModel = null;
	private CasAction casAction = null;
	private String materielType = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgBatchUpdateHsDetailAll() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("批量-修改工厂料号对应的报关商品");
		this.setContentPane(getJPanel());
        this.setSize(new Dimension(459, 230));
	}
	
	public void setVisible(boolean b){
		if(b)
			initData();
		super.setVisible(b);
	}
	
	private void initData() {
		if (tableModel != null && tableModel.getCurrentRow() != null) {
			BillDetail detail = (BillDetail) tableModel.getCurrentRow();
			this.jtComplex.setText(detail.getComplex() == null ? "" : detail
					.getComplex().getCode());
			this.jtHsName.setText(detail.getHsName());
			this.jtHsSpec.setText(detail.getHsSpec());
			this.jtHsUnit.setText(detail.getHsUnit() == null ? "" : detail
					.getHsUnit().getName());
			this.jtUnitConvert.setText(detail.getUnitConvert() == null ? ""
					: detail.getUnitConvert().toString());
			this.jtHsVersion.setText(detail.getHsVersion() == null ? ""
					: detail.getHsVersion().toString());
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(224, 102, 94, 28));
			jLabel12.setText("报关商品版本号:");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(23, 102, 78, 26));
			jLabel11.setText("报关折算系数");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(224, 72, 78, 26));
			jLabel10.setText("报关商品单位:");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(224, 42, 78, 26));
			jLabel9.setText("报关商品规格:");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(23, 72, 78, 26));
			jLabel8.setText("报告商品名称:");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(23, 42, 78, 26));
			jLabel7.setText("报关商品编码:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(19, 10, 72, 26));
			jLabel5.setText("报关商品资料");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getJtComplex(), null);
			jPanel.add(getJtSelect(), null);
			jPanel.add(getJtHsName(), null);
			jPanel.add(getJtHsSpec(), null);
			jPanel.add(getJtHsUnit(), null);
			jPanel.add(getJtUnitConvert(), null);
			jPanel.add(getJtOk(), null);
			jPanel.add(getJbCancel(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(getJtHsVersion(), null);
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
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(88, 24, 311, 2));
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jtComplex	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtComplex() {
		if (jtComplex == null) {
			jtComplex = new JTextField();
			jtComplex.setBounds(new Rectangle(102, 42, 95, 25));
			jtComplex.setEditable(false);
		}
		return jtComplex;
	}

	/**
	 * This method initializes jtSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJtSelect() {
		if (jtSelect == null) {
			jtSelect = new JButton();
			jtSelect.setBounds(new Rectangle(197, 42, 23, 23));
			jtSelect.setText("...");
			jtSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String ptNos = "";
					List<BillDetail> selectList = tableModel.getCurrentRows();
					BillDetail bd = null;
					for (int i = 0; i < selectList.size(); i++) {
						bd = selectList.get(i);
						if(i != 0) {
							ptNos += ",";
						}
						ptNos += bd.getPtPart();
					}
					
					
					List list = ErpBillCenterQuery.getInstance().getHsByPtNo(
							false, ptNos);
					if (list != null && list.size() > 0) {
						FactoryAndFactualCustomsRalation tmp = (FactoryAndFactualCustomsRalation) list
								.get(0);
						fillHsInfo(tmp);
					}
				}
			});
		}
		return jtSelect;
	}

	/**
	 * This method initializes jtHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtHsName() {
		if (jtHsName == null) {
			jtHsName = new JTextField();
			jtHsName.setBounds(new Rectangle(102, 72, 119, 25));
			jtHsName.setEditable(false);
		}
		return jtHsName;
	}

	/**
	 * This method initializes jtHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsSpec() {
		if (jtHsSpec == null) {
			jtHsSpec = new JTextField();
			jtHsSpec.setBounds(new Rectangle(307, 42, 119, 25));
			jtHsSpec.setEditable(false);
		}
		return jtHsSpec;
	}

	/**
	 * This method initializes jtHsUnit	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsUnit() {
		if (jtHsUnit == null) {
			jtHsUnit = new JTextField();
			jtHsUnit.setBounds(new Rectangle(307, 74, 119, 25));
			jtHsUnit.setEditable(false);
		}
		return jtHsUnit;
	}

	/**
	 * This method initializes jtUnitConvert	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtUnitConvert() {
		if (jtUnitConvert == null) {
			jtUnitConvert = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jtUnitConvert.setBounds(new Rectangle(102, 102, 119, 25));
			jtUnitConvert.setEditable(false);
		}
		return jtUnitConvert;
	}

	/**
	 * This method initializes jtOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJtOk() {
		if (jtOk == null) {
			jtOk = new JButton();
			jtOk.setBounds(new Rectangle(115, 152, 84, 28));
			jtOk.setText("确定");
			jtOk.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(DgBatchUpdateHsDetailAll.this.getComplex()==null){
						JOptionPane.showMessageDialog(DgBatchUpdateHsDetailAll.this, 
								"您还没有选择对应的报关商品!","提示",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(JOptionPane.showConfirmDialog(DgBatchUpdateHsDetailAll.this,
							"您确定要批量修改吗？","提示",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION){
						return;	
					}else{
						new UpdateDataRunnable().start();
					}
				}
				
			});
		}
		return jtOk;
	}
	
	class UpdateDataRunnable extends Thread {
		public void run() {
			try {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在分析数据，请稍后...");
				Map map = new HashMap<String,Object>();
				map.put("complex", DgBatchUpdateHsDetailAll.this.getComplex());
				map.put("hsName", jtHsName.getText());
				map.put("hsSpec", jtHsSpec.getText());
				map.put("hsUnit", DgBatchUpdateHsDetailAll.this.getUnit());
				map.put("unitConvert", jtUnitConvert.getValue());
				map.put("hsVersion", jtHsVersion.getValue());
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List<BillDetail> list = tableModel.getCurrentRows();
				list = casAction.BatchUpdateHs(request,list,taskId, map,materielType);
				CommonStepProgress.closeStepProgressDialog();
				tableModel.updateRows(list);
				JOptionPane.showMessageDialog(DgBatchUpdateHsDetailAll.this, 
						"批量修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				DgBatchUpdateHsDetailAll.this.dispose();

			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}	

	/**
	 * This method initializes jbCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbCancel() {
		if (jbCancel == null) {
			jbCancel = new JButton();
			jbCancel.setBounds(new Rectangle(229, 152, 84, 28));
			jbCancel.setText("取消");
			jbCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});				
		}
		return jbCancel;
	}
	
	private void fillHsInfo(FactoryAndFactualCustomsRalation value){
		if(value!=null){
		StatCusNameRelationHsn hsn = value.getStatCusNameRelationHsn();
		Double unitConvert = value.getUnitConvert();
		this.setComplex(hsn.getComplex());
		this.setUnit(hsn.getCusUnit());
		this.jtComplex.setText(hsn.getComplex().getCode());
		this.jtHsName.setText(hsn.getCusName());
		this.jtHsSpec.setText(hsn.getCusSpec());
		this.jtHsUnit.setText(hsn.getCusUnit().getName());
		this.jtUnitConvert.setValue(unitConvert);
		}
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * This method initializes jtHsVersion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsVersion() {
		if (jtHsVersion == null) {
			jtHsVersion = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jtHsVersion.setBounds(new Rectangle(321, 102, 105, 25));
			jtHsVersion.setEditable(false);
		}
		return jtHsVersion;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

}  //  @jve:decl-index=0:visual-constraint="10,0"
