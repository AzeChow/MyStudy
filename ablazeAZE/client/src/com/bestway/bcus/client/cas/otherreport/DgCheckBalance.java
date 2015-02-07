package com.bestway.bcus.client.cas.otherreport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCheckBalance extends JDialogBase {

	private JSplitPane jSplitPane = null;
	
	private JToolBar jToolBar = null;
	
	private JPanel jPanel = null;
	
	private JButton jbSave = null;
	
	private JPanel jPanel1 = null;
	
	private JLabel jLabel = null;
	
	private JLabel jLabel1 = null;
	
	private JLabel jLabel2 = null;
	
	private JLabel jLabel3 = null;
	
	private JPanel jPanel2 = null;
	
	private JLabel jLabel4 = null;
	
	private JLabel jLabel11 = null;
	
	private JLabel jLabel21 = null;
	
	private JLabel jLabel31 = null;
	
	private JLabel jLabel5 = null;
	
	private JLabel jLabel6 = null;
	
	private JScrollPane jScrollPane = null;
	
	private JTextArea jtNote = null;
	
	private JTextField jtCheckDate = null;
	
	private JTextField jtPtNo = null;
	
	private JTextField jtWareSet = null;
	
	private JFormattedTextField jtCheckAmount = null;
	
	private JTextField jtHsName = null;
	
	private JTextField jtHsSpec = null;
	
	private JFormattedTextField jtHsAmount = null;
	
	private JTextField jtHsUnitName = null;
	
	private JFormattedTextField jtUnitConvert = null;
	
	private JButton jtSelect = null;
	
	private JTableListModel tableModel = null;
	
	private CasAction casAction = null;
	
	private CheckBalance checkBalance = null;  //  @jve:decl-index=0:

	private JLabel jLabel7 = null;

	private JTextField jtPtUnitName = null;
	
	FactoryAndFactualCustomsRalation tmp = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public DgCheckBalance() {
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
		this.setTitle("盘点平衡表修改");
		this.setContentPane(getJSplitPane());
        this.setSize(new Dimension(474, 382));
			
	}
	
	public void setVisible(Boolean b){
		if(b){
			if((checkBalance=(CheckBalance)tableModel.getCurrentRow())!=null){
				System.out.println("thisthisthis");
				fillAllData(checkBalance);
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(3);
			jSplitPane.setTopComponent(getJToolBar());
			jSplitPane.setBottomComponent(getJPanel());
			jSplitPane.setDividerLocation(30);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJbSave());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jbSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(checkBalance!=null){
						casAction.saveCheckBalance(
								new Request(CommonVars.getCurrUser()), saveData(checkBalance));
						tableModel.updateRow(checkBalance);
						DgCheckBalance.this.dispose();
					}
				}
				
			});
		}
		return jbSave;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(17, 70, 60, 21));
			jLabel7.setText("工厂单位:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(217, 45, 60, 21));
			jLabel3.setText("库存数量:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(217, 20, 60, 21));
			jLabel2.setText("仓库名称:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(17,45,60,21));
			jLabel1.setText("工厂料号:");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(17,20,60,21));
			jLabel.setText("盘点时间:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(6, 8, 450, 105));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "工厂物料盘点数据", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJtCheckDate(), null);
			jPanel1.add(getJtPtNo(), null);
			jPanel1.add(getJtWareSet(), null);
			jPanel1.add(getJtCheckAmount(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getJtPtUnitName(), null);
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
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(17, 97, 80, 21));
			jLabel6.setText("备注:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(17, 71, 80, 21));
			jLabel5.setText("折算报关数量:");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(247, 45, 80, 21));
			jLabel31.setText("折算报关系数:");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(247, 20, 80, 21));
			jLabel21.setText("报关商品单位:");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(17, 45, 80, 21));
			jLabel11.setText("报关商品规格:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(17, 20, 80, 21));
			jLabel4.setText("报关商品名称:");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(6, 119, 448, 176));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null, "折算为海关数量数据", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(jLabel31, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.add(getJtHsName(), null);
			jPanel2.add(getJtHsSpec(), null);
			jPanel2.add(getJtHsAmount(), null);
			jPanel2.add(getJtHsUnitName(), null);
			jPanel2.add(getJtUnitConvert(), null);
			jPanel2.add(getJtSelect(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(16, 123, 425, 48));
			jScrollPane.setViewportView(getJtNote());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jtNote	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJtNote() {
		if (jtNote == null) {
			jtNote = new JTextArea();
		}
		return jtNote;
	}

	/**
	 * This method initializes jtCheckDate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtCheckDate() {
		if (jtCheckDate == null) {
			jtCheckDate = new JTextField();
			jtCheckDate.setBounds(new Rectangle(80, 20, 121, 21));
			jtCheckDate.setEditable(false);
		}
		return jtCheckDate;
	}

	/**
	 * This method initializes jtPtNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtPtNo() {
		if (jtPtNo == null) {
			jtPtNo = new JTextField();
			jtPtNo.setBounds(new Rectangle(80, 45, 121, 21));
			jtPtNo.setEditable(false);
		}
		return jtPtNo;
	}

	/**
	 * This method initializes jtWareSet	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtWareSet() {
		if (jtWareSet == null) {
			jtWareSet = new JTextField();
			jtWareSet.setBounds(new Rectangle(281, 20, 121, 21));
			jtWareSet.setEditable(false);
		}
		return jtWareSet;
	}

	/**
	 * This method initializes jtCheckAmount	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getJtCheckAmount() {
		if (jtCheckAmount == null) {
			jtCheckAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jtCheckAmount.setBounds(new Rectangle(281, 45, 121, 21));
			jtCheckAmount.setEditable(false);
		}
		return jtCheckAmount;
	}

	/**
	 * This method initializes jtHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsName() {
		if (jtHsName == null) {
			jtHsName = new JTextField();
			jtHsName.setBounds(new Rectangle(100, 20, 115, 21));
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
			jtHsSpec.setBounds(new Rectangle(100, 45, 115, 21));
			jtHsSpec.setEditable(false);
		}
		return jtHsSpec;
	}

	/**
	 * This method initializes jtHsAmount	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getJtHsAmount() {
		if (jtHsAmount == null) {
			jtHsAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jtHsAmount.setBounds(new Rectangle(100, 71, 115, 21));
		}
		return jtHsAmount;
	}

	/**
	 * This method initializes jtHsUnitName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsUnitName() {
		if (jtHsUnitName == null) {
			jtHsUnitName = new JTextField();
			jtHsUnitName.setBounds(new Rectangle(331, 20, 100, 21));
			jtHsUnitName.setEditable(false);
		}
		return jtHsUnitName;
	}

	/**
	 * This method initializes jtUnitConvert	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getJtUnitConvert() {
		if (jtUnitConvert == null) {
			jtUnitConvert = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jtUnitConvert.setBounds(new Rectangle(331, 45, 100, 21));
			jtUnitConvert.setEditable(false);
		}
		return jtUnitConvert;
	}

	/**
	 * This method initializes jtSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJtSelect() {
		if (jtSelect == null) {
			jtSelect = new JButton();
			jtSelect.setBounds(new Rectangle(217, 20, 21, 21));
			jtSelect.setText("...");
			jtSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() != null) {
						List list = CasQuery.getInstance()
								.getStatCusNameRelationHsnForCheckBalance(false, 
										jtPtNo.getText());
						if (list != null && list.size() > 0) {
							tmp = (FactoryAndFactualCustomsRalation) list
									.get(0);
							fillHsData(tmp);
						}
					}
				}
			});		
		}
		return jtSelect;
	}
	
	/**
	 * 选择报关商品后填充窗体
	 * @param data
	 */
	private void fillHsData(FactoryAndFactualCustomsRalation data) {
		Double unitConvert = data.getUnitConvert() == null ? Double
				.valueOf(jtUnitConvert.getText()) : data.getUnitConvert();
		jtHsName.setText(data.getStatCusNameRelationHsn().getCusName());
		jtHsSpec.setText(data.getStatCusNameRelationHsn().getCusSpec());
		jtHsUnitName.setText(data.getStatCusNameRelationHsn().getCusUnit().getName());
		//jtUnitConvert.setText(unitConvert.toString());
		jtUnitConvert.setValue(Double.valueOf("".equals(unitConvert.toString())?"0":unitConvert.toString()));
		System.out.println("jtCheckAmount.getValue()="
				+ jtCheckAmount.getValue());
		jtHsAmount.setValue(Double.valueOf(jtCheckAmount.getValue().toString()
				.trim())
				* unitConvert);
	}
	
	/**
	 * 装载数据
	 * @param data
	 */
	public void fillAllData(CheckBalance data){
		checkBalance = data;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		jtCheckDate.setText(sdf.format(data.getCheckDate()));
		jtPtNo.setText(data.getPtNo());
		jtPtUnitName.setText(data.getPtUnitName());
		jtWareSet.setText(data.getWareSet().getName());
		jtCheckAmount.setValue(data.getCheckAmount());
		jtHsName.setText(data.getName());
		jtHsSpec.setText(data.getSpec());
		jtHsUnitName.setText(data.getHsUnit().getName());
		jtUnitConvert.setValue(isNullDouble(data.getUnitConvert()));
		jtHsAmount.setValue(isNullDouble(data.getHsAmount()));
//		jtHsAmount.setValue(isNullDouble(data.getCheckAmount())*isNullDouble(data.getUnitConvert()));
		jtNote.setText(data.getNote());
	}
	
	private Double isNullDouble(Double value){
		return value==null?0.00:value;
	}
	
	/**
	 * 保存数据
	 * @param data
	 * @return
	 */
	private CheckBalance saveData(CheckBalance data){
		data.setName(jtHsName.getText());
		data.setSpec(jtHsSpec.getText());
		if (tmp != null) {
				data.setHsUnit(tmp.getStatCusNameRelationHsn().getCusUnit());
			}
		data.setUnitConvert(Double.valueOf(jtUnitConvert.getValue().toString().trim()));
		data.setHsAmount(Double.valueOf(jtHsAmount.getValue().toString().trim()));
		data.setNote(jtNote.getText());
		return data;
	}

	/**
	 * @return the tableModel
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel the tableModel to set
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jtPtUnitName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtPtUnitName() {
		if (jtPtUnitName == null) {
			jtPtUnitName = new JTextField();
			jtPtUnitName.setBounds(new Rectangle(80, 70, 121, 21));
			jtPtUnitName.setEditable(false);
		}
		return jtPtUnitName;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
