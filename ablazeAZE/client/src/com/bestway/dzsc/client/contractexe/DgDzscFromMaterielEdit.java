/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JPanel;
/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDzscFromMaterielEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField2 = null;
	private JFormattedTextField jFormattedTextField3 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private DzscAction dzscAction = null;
	private JTableListModel tableModel = null;
	private JFormattedTextField jFormattedTextField4 = null;
	private JCheckBox jCheckBox1 = null;
	private  NumberFormatter numberFormatter=null;
	private Materiel materiel = null; //物料
	private DzscEmsImgBill imgbill = null; //料件清单
	private DzscEmsExgBill exgbill = null; //成品清单
	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField4 = null;
	private JLabel jLabel6 = null;
	private JTextField jTextField5 = null;
	private JLabel jLabel7 = null;
	private JTextField jTextField6 = null;
	private JLabel jLabel8 = null;
	private JTextField jTextField7 = null;
	private JTextField jTextField8 = null;
	private JLabel jLabel13 = null;
	private JFormattedTextField jFormattedTextField5 = null;
	private JLabel jLabel14 = null;
	private JFormattedTextField jFormattedTextField6 = null;
	private JLabel jLabel15 = null;
	private JFormattedTextField jFormattedTextField7 = null;
	private JFormattedTextField jFormattedTextField8 = null;
	private JLabel jLabel16 = null;
	private JLabel jLabel17 = null;
	private JLabel jLabel18 = null;
	private Double weightpara = Double.valueOf(0);
	private DzscCustomsDeclarationCommInfo commInfo = null;
	private BaseCustomsDeclaration customsDeclaration = null;
	private DzscContractExeAction baseEncAction = null;
	private boolean	 ok	= false;
	
	private JPanel jPanel = null;
	private JLabel jLabel19 = null;
	private JLabel jLabel20 = null;
	private JTextField jTextField9 = null;
	private JTextField jTextField10 = null;
	/**
	 * This is the default constructor
	 */
	public DgDzscFromMaterielEdit() {
		super();
		dzscAction = (DzscAction) CommonVars
	         .getApplicationContext().getBean("dzscAction");  
		baseEncAction = (DzscContractExeAction) CommonVars.getApplicationContext()
	         .getBean("dzscContractExeAction");
		initialize();
	}
	
	public  NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat();  //  @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}
	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public  Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				//	return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			//return new Double("0");
			return null;
		}
	}

	public  String doubleToStr(Double value) { //转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	} 

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(545, 384);
		this.setContentPane(getJContentPane());
		this.setTitle("编辑申报信息");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				weightpara = baseEncAction.findweightpara(new Request(CommonVars.getCurrUser()));
				if (imgbill != null){//料件进口报关单
					fillImgWindow();
				} else {
					fillExgWindow();
				}
			}
		});
	}
	private void fillImgWindow(){
		jTextField.setText(materiel.getPtNo());
		jTextField1.setText(materiel.getFactoryName());
		jTextField2.setText(materiel.getFactorySpec());
		if (materiel.getCalUnit()!=null){
		   jTextField3.setText(materiel.getCalUnit().getName());
		   jTextField8.setText(materiel.getCalUnit().getName());
		}
		Double cknetweight = Double.valueOf(0);
		if (materiel.getCknetWeight() != null){
			cknetweight = materiel.getCknetWeight();
		}
		Double ckgosweight = Double.valueOf(0);
		if (materiel.getCkoutWeight() != null){
			ckgosweight = materiel.getCkoutWeight();
		}
		jLabel17.setText("仓库净重："+String.valueOf(cknetweight));
		jLabel18.setText("仓库毛重："+String.valueOf(ckgosweight));	
		
		jTextField9.setText((materiel.getPtNetWeight()==null)?"0.0":String.valueOf(materiel.getPtNetWeight()));//净重
		jTextField10.setText((materiel.getPtOutWeight()==null)?"0.0":String.valueOf(materiel.getPtOutWeight()));//毛重
		
		if (imgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(imgbill.getSeqNum()));
		}
		jTextField5.setText(imgbill.getName());
		jTextField6.setText(imgbill.getSpec());
		if (imgbill.getUnit()!=null){
		   jTextField7.setText(imgbill.getUnit().getName());
		}
		
	}
	private void fillExgWindow(){
		jTextField.setText(materiel.getPtNo());
		jTextField1.setText(materiel.getFactoryName());
		jTextField2.setText(materiel.getFactorySpec());
		if (materiel.getCalUnit()!=null){
		   jTextField3.setText(materiel.getCalUnit().getName());
		   jTextField8.setText(materiel.getCalUnit().getName());
		}
		Double cknetweight = Double.valueOf(0);
		if (materiel.getCknetWeight() != null){
			cknetweight = materiel.getCknetWeight();
		}
		Double ckgosweight = Double.valueOf(0);
		if (materiel.getCkoutWeight() != null){
			ckgosweight = materiel.getCkoutWeight();
		}
		jLabel17.setText("仓库净重："+String.valueOf(cknetweight));
		jLabel18.setText("仓库毛重："+String.valueOf(ckgosweight));	
		if (exgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(exgbill.getSeqNum()));
		}
		
		jTextField9.setText((materiel.getPtNetWeight()==null)?"0.0":String.valueOf(materiel.getPtNetWeight()));//净重
		jTextField10.setText((materiel.getPtOutWeight()==null)?"0.0":String.valueOf(materiel.getPtOutWeight()));//毛重
		
		jTextField5.setText(exgbill.getName());
		jTextField6.setText(exgbill.getSpec());
		if (exgbill.getUnit()!=null){
		   jTextField7.setText(exgbill.getUnit().getName());
		}
		
		jFormattedTextField7.setText(doubleToStr(exgbill.getUnitNetWeight()));
		jFormattedTextField8.setText(doubleToStr(exgbill.getUnitGrossWeight()));
		
		jFormattedTextField1.setValue(getExgNetWeight());
		jFormattedTextField5.setValue(getExgGotWeight());
	}
	//成品得到实际净重
	private Double getExgNetWeight(){
        //成品：实际净重 = 物料净重*参数(单位为千克)
		//成品：实际净重=合同净重（单位为个）
		double ptNetWeight = 0;
		double netWeight = 0;
		if (materiel.getPtNetWeight()!= null){
			ptNetWeight = materiel.getPtNetWeight().doubleValue();
		}
		if (exgbill.getUnit()!= null && (exgbill.getUnit().getName().trim().equals("公斤") || 
				exgbill.getUnit().getName().trim().equals("千克"))){
		    double sj = ptNetWeight * weightpara.doubleValue();
		    return forNum(sj);
		} else if (exgbill.getUnit()!= null && exgbill.getUnit().getName().trim().equals("个")){
			if (exgbill.getUnitNetWeight()!= null){
				netWeight = exgbill.getUnitNetWeight().doubleValue();
			}
			return forNum(netWeight);
		}
		return forNum(0);
	}
    //成品得到实际毛重
	private Double getExgGotWeight(){
        //成品：实际毛重 = 物料净重*参数*合同毛重/合同净重（单位为千克）
		//成品：实际毛重 = 合同毛重(单位为个)
		double netWeight = 0;
		double gossWeight = 0;
		double ptNetWeight = 0;
		if (exgbill.getUnitNetWeight()!= null){
			netWeight = exgbill.getUnitNetWeight().doubleValue();
		}
		if (exgbill.getUnitGrossWeight()!= null){
			gossWeight = exgbill.getUnitGrossWeight().doubleValue();
		} 
		if (materiel.getPtNetWeight()!= null){
			ptNetWeight = materiel.getPtNetWeight().doubleValue();
		}
		if (exgbill.getUnit()!= null && (exgbill.getUnit().getName().trim().equals("公斤") || 
				exgbill.getUnit().getName().trim().equals("千克"))){
			double sj = ptNetWeight * weightpara;
			if (Double.valueOf(netWeight)== 0.0){
				return forNum(0);
			}
			double bili = gossWeight/netWeight;
			return forNum(sj*bili);
		} else if (exgbill.getUnit()!= null && exgbill.getUnit().getName().trim().equals("个")){
			return forNum(gossWeight);
		}
		return forNum(0);
		
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel3 = new JLabel();
			jLabel = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(29, 11, 48, 20);
			jLabel1.setText("料号");
			jLabel1.setForeground(new java.awt.Color(153,51,0));
			jLabel2.setBounds(29, 41, 63, 21);
			jLabel2.setText("型号规格");
			jLabel2.setForeground(new java.awt.Color(153,51,0));
			jLabel4.setBounds(247, 195, 39, 20);
			jLabel4.setText("总箱数");
			jLabel5.setBounds(299, 229, 58, 19);
			jLabel5.setText("总净重");
			jLabel9.setBounds(29, 195, 57, 22);
			jLabel9.setText("申报数量");
			jLabel10.setBounds(29, 230, 59, 24);
			jLabel10.setText("实际净重");
			jLabel11.setBounds(277, 42, 80, 23);
			jLabel11.setText("料件单位");
			jLabel11.setForeground(new java.awt.Color(153,51,0));
			jLabel12.setBounds(29, 262, 55, 21);
			jLabel12.setText("实际毛重");
			jLabel.setBounds(277, 12, 73, 23);
			jLabel.setText("料件名称");
			jLabel.setForeground(new java.awt.Color(153,51,0));
			jLabel3.setBounds(28, 107, 55, 22);
			jLabel3.setText("清单序号");
			jLabel6.setBounds(276, 108, 73, 21);
			jLabel6.setText("商品名称");
			jLabel7.setBounds(28, 136, 59, 19);
			jLabel7.setText("型号规格");
			jLabel8.setBounds(276, 137, 82, 20);
			jLabel8.setText("申报单位");
			jLabel13.setBounds(362, 195, 56, 20);
			jLabel13.setText("每箱件数");
			jLabel14.setBounds(299, 261, 52, 22);
			jLabel14.setText("总毛重");
			jLabel15.setBounds(28, 162, 60, 21);
			jLabel15.setText("合同净重");
			jLabel16.setBounds(276, 165, 78, 23);
			jLabel16.setText("合同毛重");
			jLabel17.setBounds(29, 291, 239, 23);
			jLabel17.setText("仓库净重");
			jLabel17.setForeground(java.awt.Color.red);
			jLabel18.setBounds(29, 313, 239, 22);
			jLabel18.setText("仓库毛重");
			jLabel18.setForeground(java.awt.Color.red);
			jLabel19.setBounds(29, 70, 63, 20);
			jLabel19.setText("净重");
			jLabel19.setForeground(new java.awt.Color(153,51,0));
			jLabel20.setBounds(277, 70, 58, 23);
			jLabel20.setText("毛重");
			jLabel20.setForeground(new java.awt.Color(153,51,0));
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(getJCheckBox1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(getJTextField8(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getJFormattedTextField5(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJFormattedTextField6(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJFormattedTextField7(), null);
			jContentPane.add(getJFormattedTextField8(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(jLabel20, null);
			jContentPane.add(getJTextField9(), null);
			jContentPane.add(getJTextField10(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.setBounds(363, 229, 129, 22);
		}
		return jFormattedTextField;
	}
	/**
	 * This method initializes jFormattedTextField1	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField1.setBounds(97, 230, 129, 22);
			jFormattedTextField1.setEditable(false);
		}
		return jFormattedTextField1;
	}
	
//	得到每箱件数 
	private Double getjianshu() {
		double amount = 0;
		double xiangshu = 0;
		if (this.jFormattedTextField3.getText() != null && !this.jFormattedTextField3.getText().equals("")) {
			amount = Double.parseDouble(this.jFormattedTextField3.getText()
					.toString());
		}
		if (this.jFormattedTextField2.getText() != null && !this.jFormattedTextField2.getText().equals("")) {
			xiangshu = Double.parseDouble(this.jFormattedTextField2.getText()
					.toString());
		}
		if (Double.valueOf(xiangshu) == 0.0){
			return Double.valueOf(0);
		}
		BigDecimal bd = new BigDecimal(amount / xiangshu);
		String totalPrice = bd.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}
	/**
	 * This method initializes jFormattedTextField2	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField2.setBounds(287, 195, 65, 22);
			jFormattedTextField2.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {    
			        	jFormattedTextField4.setValue(getjianshu());
			        
				}
			});
		}
		return jFormattedTextField2;
	}
	

	
	/**
	 * This method initializes jFormattedTextField3	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField3.setBounds(97, 195, 82, 22);
			jFormattedTextField3.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {    
			        	jFormattedTextField2.setText(jFormattedTextField3.getText());
			        	jFormattedTextField4.setText("1");
			        	if (exgbill != null){
				        	   getweight();
			        	} else if (imgbill != null){
			        		getImgWeight();
			        	}
			        
				}
			});
			
		}
		return jFormattedTextField3;
	}
//	得到料件总净毛重
	private void getImgWeight(){
		double imgjingzhong = 0;
		double imgmaozhong = 0;
		double amount = 0;
		if (this.jFormattedTextField3.getText() != null && !this.jFormattedTextField3.getText().equals("")) {
			amount = Double.parseDouble(this.jFormattedTextField3.getText()
					.toString());
		}
		if (materiel.getPtNetWeight()!=null){
			imgjingzhong = materiel.getPtNetWeight().doubleValue();
		}
		if (materiel.getPtOutWeight()!=null){
			imgmaozhong = materiel.getPtOutWeight().doubleValue();
		}
		jFormattedTextField.setValue(forInterNum(amount*imgjingzhong));
		jFormattedTextField6.setValue(forInterNum(amount*imgmaozhong));
	}
//	得到成品总净毛重
	private void getweight() {
		double amount = 0;
		double sjjinzhong = 0;
		double sjmaozhong = 0;
		if (this.jFormattedTextField3.getText() != null && !this.jFormattedTextField3.getText().equals("")) {
			amount = Double.parseDouble(this.jFormattedTextField3.getText()
					.toString());
		}
		if (this.jFormattedTextField1.getText() != null && !this.jFormattedTextField1.getText().equals("")) {
			sjjinzhong = Double.parseDouble(this.jFormattedTextField1.getText()
					.toString());
		} 
		if (this.jFormattedTextField5.getText() != null && !this.jFormattedTextField5.getText().equals("")) {
			sjmaozhong = Double.parseDouble(this.jFormattedTextField5.getText()
					.toString());
		}
		jFormattedTextField.setValue(forInterNum(amount * sjjinzhong));
		jFormattedTextField6.setValue(forInterNum(amount * sjmaozhong));
	}
	
	private Double forInterNum(double shuliang){
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalshuliang);
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(319, 310, 73, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					commInfo = new DzscCustomsDeclarationCommInfo();
					double shuliang = 0;
					double gossWeight = 0;
					double netWeight = 0;
					double hgossWeight = 0;
					double hnetWeight = 0;
					if (imgbill != null){ //进口
					    if (imgbill.getUnit()!= null && (imgbill.getUnit().getName().trim().equals("公斤") || 
								imgbill.getUnit().getName().trim().equals("千克"))){
							if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
								shuliang =  Double.parseDouble(jFormattedTextField.getText()
										.toString()); //报关数量=总净重
							}
						} else {//个
								if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
									shuliang =  Double.parseDouble(jFormattedTextField3.getText()
											.toString());//报关数量=申报数量
								}	
						}
						if (jFormattedTextField6.getText()!= null && !jFormattedTextField6.getText().equals("")){
							gossWeight =  Double.parseDouble(jFormattedTextField6.getText()//总毛重
									.toString());
						}
						if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
							netWeight =  Double.parseDouble(jFormattedTextField.getText()//总净重
									.toString());
						}
					} else { //出口		
						if (exgbill.getUnit()!= null && (exgbill.getUnit().getName().trim().equals("公斤") || 
								exgbill.getUnit().getName().trim().equals("千克"))){
							if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
								shuliang =  Double.parseDouble(jFormattedTextField.getText()
										.toString());//报关数量=总净重
							}
							if (jFormattedTextField6.getText()!= null && !jFormattedTextField6.getText().equals("")){
								gossWeight =  Double.parseDouble(jFormattedTextField6.getText()//总毛重
										.toString());
							}
							if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
								netWeight =  Double.parseDouble(jFormattedTextField.getText()//总净重
										.toString());
							}
						} else { //个
							if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
								shuliang =  Double.parseDouble(jFormattedTextField3.getText()
										.toString());
							}							
							if (exgbill.getUnitGrossWeight()!= null){
								hgossWeight = exgbill.getUnitGrossWeight();//合同毛重
							}
							if (exgbill.getUnitNetWeight()!= null){
								hnetWeight = exgbill.getUnitNetWeight(); //合同净重
							}
							gossWeight = hgossWeight * shuliang; //合同毛重*申报数量
							netWeight = hnetWeight * shuliang;   //合同净重*申报数量
						}	
					}
					commInfo.setCommAmount(forNum(shuliang));//数量
					commInfo.setGrossWeight(forInterNum(gossWeight));//毛重
					commInfo.setNetWeight(forInterNum(netWeight));//净重	
					try{
					    commInfo.setPieces(Integer.valueOf(jFormattedTextField2.getText()));
					} catch  (Exception e1){
						commInfo.setPieces(0);
					}
					baseEncAction.saveCustomsinfoFromBill(new Request(CommonVars.getCurrUser()),commInfo,customsDeclaration,exgbill,imgbill);
					setOk(true);
					dispose();
				}
			});
		}
		return jButton;
	}
	private Double forNum(double shuliang){
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalshuliang);
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(418, 310, 74, 25);
			jButton1.setText("放弃");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					setOk(false);
					DgDzscFromMaterielEdit.this.dispose();
				}
			});
		}
		return jButton1;
	}
	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}
	/**
	 * @param tableModel The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}
	/**
	 * This method initializes jFormattedTextField4	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField4.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField4.setBounds(420, 195, 73, 22);
		}
		return jFormattedTextField4;
	}
	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setVisible(false);
			jCheckBox1.setBounds(146, 244, 126, 21);
			jCheckBox1.setText("自动计算料件总额");
		}
		return jCheckBox1;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(97, 11, 150, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(368, 12, 141, 22);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setBounds(97, 41, 150, 22);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(368, 42, 141, 22);
		}
		return jTextField3;
	}
	/**
	 * This method initializes jTextField4	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setEditable(false);
			jTextField4.setBounds(96, 107, 150, 22);
		}
		return jTextField4;
	}
	/**
	 * This method initializes jTextField5	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setEditable(false);
			jTextField5.setBounds(367, 108, 141, 22);
		}
		return jTextField5;
	}
	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setEditable(false);
			jTextField6.setBounds(96, 137, 150, 22);
		}
		return jTextField6;
	}
	/**
	 * This method initializes jTextField7	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setEditable(false);
			jTextField7.setBounds(367, 137, 141, 22);
		}
		return jTextField7;
	}
	/**
	 * @return Returns the exgbill.
	 */
	public DzscEmsExgBill getExgbill() {
		return exgbill;
	}
	/**
	 * @param exgbill The exgbill to set.
	 */
	public void setExgbill(DzscEmsExgBill exgbill) {
		this.exgbill = exgbill;
	}
	/**
	 * @return Returns the imgbill.
	 */
	public DzscEmsImgBill getImgbill() {
		return imgbill;
	}
	/**
	 * @param imgbill The imgbill to set.
	 */
	public void setImgbill(DzscEmsImgBill imgbill) {
		this.imgbill = imgbill;
	}
	/**
	 * This method initializes jTextField8	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setEditable(false);
			jTextField8.setBounds(178, 195, 51, 22);
		}
		return jTextField8;
	}
	/**
	 * This method initializes jFormattedTextField5	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new JFormattedTextField();
			jFormattedTextField5.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField5.setBounds(97, 262, 129, 22);
			jFormattedTextField5.setEditable(false);
		}
		return jFormattedTextField5;
	}
	/**
	 * This method initializes jFormattedTextField6	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField6() {
		if (jFormattedTextField6 == null) {
			jFormattedTextField6 = new JFormattedTextField();
			jFormattedTextField6.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField6.setBounds(363, 261, 129, 22);
		}
		return jFormattedTextField6;
	}
	/**
	 * This method initializes jFormattedTextField7	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField7() {
		if (jFormattedTextField7 == null) {
			jFormattedTextField7 = new JFormattedTextField();
			jFormattedTextField7.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField7.setEditable(false);
			jFormattedTextField7.setBounds(96, 165, 150, 22);
		}
		return jFormattedTextField7;
	}
	/**
	 * This method initializes jFormattedTextField8	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField8() {
		if (jFormattedTextField8 == null) {
			jFormattedTextField8 = new JFormattedTextField();
			jFormattedTextField8.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField8.setEditable(false);
			jFormattedTextField8.setBounds(367, 165, 141, 22);
		}
		return jFormattedTextField8;
	}
	
	/**
	 * @return Returns the commInfo.
	 */
	public DzscCustomsDeclarationCommInfo getCommInfo() {
		return commInfo;
	}
	/**
	 * @param commInfo The commInfo to set.
	 */
	public void setCommInfo(DzscCustomsDeclarationCommInfo commInfo) {
		this.commInfo = commInfo;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}
	/**
	 * @param ok The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	/**
	 * @return Returns the materiel.
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	/**
	 * @param materiel The materiel to set.
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	/**
	 * @return Returns the customsDeclaration.
	 */
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}
	/**
	 * @param customsDeclaration The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBounds(23, 99, 495, 4);
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.control,5));
		}
		return jPanel;
	}
	/**
	 * This method initializes jTextField9	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setEditable(false);
			jTextField9.setBounds(97, 70, 152, 21);
		}
		return jTextField9;
	}
	/**
	 * This method initializes jTextField10	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setEditable(false);
			jTextField10.setBounds(367, 71, 142, 21);
		}
		return jTextField10;
	}
                           }  //  @jve:decl-index=0:visual-constraint="10,10"
