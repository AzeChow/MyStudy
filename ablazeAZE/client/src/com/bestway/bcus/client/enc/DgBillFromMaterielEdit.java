/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsFromMateriel;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
/**
 * @author xxm(公共模块)
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates//
 */

public class DgBillFromMaterielEdit extends JDialogBase {
    private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	
	private JFormattedTextField jFormattedTextField2 = null;//总箱数
	private JFormattedTextField jFormattedTextField4 = null;//每箱件数
	
	private JFormattedTextField jFormattedTextField3 = null;//申报数量
	
	private JFormattedTextField jFormattedTextField1 = null;//实际净重
	private JFormattedTextField jFormattedTextField5 = null;//实际毛重	
	
	private JFormattedTextField jFormattedTextField = null; //总净重
	private JFormattedTextField jFormattedTextField6 = null;//总毛重
	
	private JFormattedTextField jFormattedTextField7 = null;//合同净重
	private JFormattedTextField jFormattedTextField8 = null;//合同毛重
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JTableListModel tableModel = null;
	
	private JCheckBox jCheckBox1 = null;
	private  NumberFormatter numberFormatter=null;
	private Materiel materiel = null; //物料
	//Bcs
	private ContractImg bcsimgbill = null;
	private ContractExg bcsexgbill = null;
	//Bcus
	private EmsHeadH2kImg bcusimgbill = null;
	private EmsHeadH2kExg bcusexgbill = null;
	
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
	private JLabel jLabel16 = null;
	private JLabel jLabel17 = null;
	private JLabel jLabel18 = null;
	private Double weightpara = Double.valueOf(0);	
	private BaseCustomsDeclaration customsDeclaration = null;	
	private BaseCustomsDeclarationCommInfo info = null;
	private boolean	 ok	= false;
	private ContractExeAction bcsEncAction = null;
	private EncAction bcusEncAction = null;
	private JPanel jPanel = null;
	private JLabel jLabel19 = null;
	private JLabel jLabel20 = null;
	private JTextField jTextField9 = null;
	private JTextField jTextField10 = null;
	private int projectType = ProjectType.BCUS;
	private BaseCustomsFromMateriel fromMateriel = null;
	private boolean isBrowse = false;
	private Double weightRate = Double.valueOf(0); //重量比例因子
	private JLabel jLabel21 = null;
	private JLabel jLabel22 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton5 = null;//删除
	private List list = null;
	private int pagenum = 0;
	private ApplyToCustomsBillList applyToCustomsBillList = null;
	private List allList = null;
	/**
	 * This is the default constructor
	 */
	public DgBillFromMaterielEdit() {
		super();
		bcsEncAction = (ContractExeAction) CommonVars.getApplicationContext()
                 .getBean("contractExeAction");
		bcusEncAction = (EncAction) CommonVars.getApplicationContext()
		         .getBean("encAction");
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
		this.setSize(545, 428);
		this.setContentPane(getJContentPane());
		this.setTitle("编辑申报信息");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {	
				pagenum = 0;
				initdata(pagenum);
			}
		});
	}
	
	private void initdata(int pagenum){		
		/*if (isBrowse){ //查看状态
			CustomsFromMateriel customsFrom = null;
			if (list != null && list.size()>0){
				customsFrom = (CustomsFromMateriel) list.get(pagenum);
			}
			if (customsFrom == null){
				return;
			}
			setMateriel(customsFrom.getMateriel());
			EmsHeadH2kImg img = bcusEncAction.getEmsHeadImg(new Request(CommonVars.getCurrUser()),customsFrom.getBcusimgNo()==null?0:customsFrom.getBcusimgNo());
			EmsHeadH2kExg exg = bcusEncAction.getEmsHeadExg(new Request(CommonVars.getCurrUser()),customsFrom.getBcusexgNo()==null?0:customsFrom.getBcusexgNo());
			setBcusexgbill(exg);
			setBcusimgbill(img);
			setFromMateriel(customsFrom);
		}*/
		
		
		CompanyOther other = CommonVars.getOther();
		if (other != null){			
			weightpara = other.getWeightPara();
		}
		if (materiel.getCalWeightUnit()!= null && materiel.getCalWeightUnit().getName() != null 
				&& materiel.getCalWeightUnit().getName().equals("克")){
			weightRate = 0.001;
		} else {
			weightRate = 1.0;
		}
		//初始化物料主档
		fillMateriel();
		switch (projectType){
		case ProjectType.BCUS:
			if (bcusimgbill != null){
				fillBcusImgWindow();
			} else {
				fillBcusExgWindow();
			}
			break;
		case ProjectType.BCS:
			if (bcsimgbill != null){
				fillBcsImgWindow();
			} else {
				fillBcsExgWindow();
			}
			break;
		}
       //fillOther();
       setState();
	}
	/*private void fillOther(){
		if (fromMateriel != null && isBrowse){
			jFormattedTextField3.setValue(fromMateriel.getPtNum());
			jFormattedTextField2.setValue(fromMateriel.getSumBoxNum());
			jFormattedTextField4.setValue(fromMateriel.getBoxNum());
			jFormattedTextField1.setValue(fromMateriel.getSjNetWeight());
			jFormattedTextField5.setValue(fromMateriel.getSjGrossWeight());
			jFormattedTextField.setValue(fromMateriel.getSumNetWeight());
			jFormattedTextField6.setValue(fromMateriel.getSumGrossWeight());
			double materielStorNetweight = materiel.getCknetWeight()==null?0:(materiel.getCknetWeight().doubleValue()*weightRate.doubleValue());
			jLabel22.setText("总净重-(仓库净重*申报数量)："+String.valueOf(fromMateriel.getSumNetWeight() - (materielStorNetweight * fromMateriel.getPtNum())));
		}
	}*/
	private void fillMateriel(){
		jTextField.setText(materiel.getPtNo());
		jTextField1.setText(materiel.getFactoryName());
		jTextField2.setText(materiel.getFactorySpec());
		if (materiel.getCalUnit()!=null){
		   jTextField3.setText(materiel.getCalUnit().getName());
		   jTextField8.setText(materiel.getCalUnit().getName());
		}
		Double cknetweight = Double.valueOf(0);
		if (materiel.getCknetWeight() != null){
			cknetweight = materiel.getCknetWeight()*weightRate; //仓库净重
		}
		Double ckgosweight = Double.valueOf(0);
		if (materiel.getCkoutWeight() != null){ 
			ckgosweight = materiel.getCkoutWeight()*weightRate; //仓库毛重
		}
		jLabel17.setText("仓库净重："+String.valueOf(cknetweight));
		jLabel18.setText("仓库毛重："+String.valueOf(ckgosweight));	
		jTextField9.setText((materiel.getPtNetWeight()==null)?"0.0":String.valueOf(materiel.getPtNetWeight() * weightRate));//净重
		jTextField10.setText((materiel.getPtOutWeight()==null)?"0.0":String.valueOf(materiel.getPtOutWeight() * weightRate));//毛重
		
		double materielNetweight = materiel.getPtNetWeight()==null?0:(materiel.getPtNetWeight().doubleValue() * weightRate.doubleValue());//折算成千克
		double materielStorNetweight = materiel.getCknetWeight()==null?0:(materiel.getCknetWeight().doubleValue() * weightRate.doubleValue());
		
		jLabel21.setText("净重 - 仓库净重："+ String.valueOf(forNum(materielNetweight - materielStorNetweight)));
	}
	//联网监管
	private void fillBcusImgWindow(){		
		if (bcusimgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(bcusimgbill.getSeqNum()));
		}
		jTextField5.setText(bcusimgbill.getName());
		jTextField6.setText(bcusimgbill.getSpec());
		if (bcusimgbill.getUnit()!=null){
		   jTextField7.setText(bcusimgbill.getUnit().getName());
		}		
		jFormattedTextField7.setText(doubleToStr(bcusimgbill.getUnitNetWeight()));
		jFormattedTextField8.setText(doubleToStr(bcusimgbill.getUnitGrossWeight()));
		
	}
	private void fillBcusExgWindow(){	
		Integer versionI = null;
		if (info != null){
			versionI = Integer.valueOf(info.getVersion());
		} else {
			versionI = bcusexgbill.getVersion();
		}
		EmsHeadH2kVersion verisonObj = bcusEncAction.findEmsHeadH2kVersion(new Request(CommonVars.getCurrUser()),bcusexgbill.getSeqNum(),versionI);
		if (bcusexgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(bcusexgbill.getSeqNum()));
		}
		
		jTextField5.setText(bcusexgbill.getName());
		jTextField6.setText(bcusexgbill.getSpec());
		if (bcusexgbill.getUnit()!=null){
		   jTextField7.setText(bcusexgbill.getUnit().getName());
		}		
		
		jFormattedTextField7.setText(doubleToStr(verisonObj.getUnitNetWeight()));
		jFormattedTextField8.setText(doubleToStr(verisonObj.getUnitGrossWeight()));	
		
		jFormattedTextField1.setValue(getExgNetWeight(bcusexgbill.getUnit(),verisonObj.getUnitNetWeight()));
		jFormattedTextField5.setValue(getExgGotWeight(bcusexgbill.getUnit(),verisonObj.getUnitGrossWeight(),verisonObj.getUnitNetWeight()));
    }
	//Bcs
	private void fillBcsImgWindow(){		
		if (bcsimgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(bcsimgbill.getSeqNum()));
		}
		jTextField5.setText(bcsimgbill.getName());
		jTextField6.setText(bcsimgbill.getSpec());
		if (bcsimgbill.getUnit()!=null){
		   jTextField7.setText(bcsimgbill.getUnit().getName());
		}		
	}
	private void fillBcsExgWindow(){
		if (bcsexgbill.getSeqNum()!= null){
		    jTextField4.setText(String.valueOf(bcsexgbill.getSeqNum()));
		}
		
		jTextField5.setText(bcsexgbill.getName());
		jTextField6.setText(bcsexgbill.getSpec());
		if (bcsexgbill.getUnit()!=null){
		   jTextField7.setText(bcsexgbill.getUnit().getName());
		}
		
		jFormattedTextField7.setText(doubleToStr(bcsexgbill.getUnitNetWeight()));
		jFormattedTextField8.setText(doubleToStr(bcsexgbill.getUnitGrossWeight()));
		
		jFormattedTextField1.setValue(getExgNetWeight(bcsexgbill.getUnit(),bcsexgbill.getUnitNetWeight()));
		jFormattedTextField5.setValue(getExgGotWeight(bcsexgbill.getUnit(),bcsexgbill.getUnitGrossWeight(),bcsexgbill.getUnitNetWeight()));
	}
	
	//成品得到实际净重
	private Double getExgNetWeight(Unit unit,Double unitNetWeight){
        //成品：实际净重 = 物料净重*参数(单位为千克)
		//成品：实际净重=合同净重（单位为个)
		double ptNetWeight = 0;
		double netWeight = 0;
		if (materiel.getPtNetWeight()!= null){
			ptNetWeight = materiel.getPtNetWeight().doubleValue() * weightRate.doubleValue();//折算成千克
		}
		if (unit!= null && (unit.getName().trim().equals("公斤") || 
				unit.getName().trim().equals("千克"))){
		    double sj = ptNetWeight * weightpara.doubleValue();
		    return forNum(sj);
		} else if (unit != null && unit.getName().trim().equals("个")){
			if (unitNetWeight!= null){
				netWeight = unitNetWeight.doubleValue();
			}
			return forNum(netWeight);
		}
		return forNum(0);
	}
    //成品得到实际毛重
	private Double getExgGotWeight(Unit unit,Double unitGrossWeight,Double unitNetWeight){
        //成品：实际毛重 = 物料净重*参数*合同毛重/合同净重（单位为千克）
		//成品：实际毛重 = 合同毛重(单位为个)
		double netWeight = 0;
		double gossWeight = 0;
		double ptNetWeight = 0;
		if (unitNetWeight!= null){
			netWeight = unitNetWeight.doubleValue();
		}
		if (unitGrossWeight!= null){
			gossWeight = unitGrossWeight.doubleValue();
		} 
		if (materiel.getPtNetWeight()!= null){
			ptNetWeight = materiel.getPtNetWeight().doubleValue() * weightRate;//折算成千克
		}
		if (unit!= null && (unit.getName().trim().equals("公斤") || 
				unit.getName().trim().equals("千克"))){
			double sj = ptNetWeight * weightpara;
			if (Double.valueOf(netWeight)== 0.0){
				return forNum(0);
			}
			double weightScale = gossWeight/netWeight;
			return forNum(sj*weightScale);
		} else if (unit!= null && unit.getName().trim().equals("个")){
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
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
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
			jLabel17.setBounds(29, 285, 239, 17);
			jLabel17.setText("仓库净重");
			jLabel17.setForeground(java.awt.Color.red);
			jLabel18.setBounds(29, 306, 239, 18);
			jLabel18.setText("仓库毛重");
			jLabel18.setForeground(java.awt.Color.red);
			jLabel19.setBounds(29, 70, 63, 20);
			jLabel19.setText("净重");
			jLabel19.setForeground(new java.awt.Color(153,51,0));
			jLabel20.setBounds(277, 70, 58, 23);
			jLabel20.setText("毛重");
			jLabel20.setForeground(new java.awt.Color(153,51,0));
			jLabel21.setBounds(278, 285, 231, 18);
			jLabel21.setText("净重-仓库净重：");
			jLabel21.setForeground(java.awt.Color.red);
			jLabel22.setBounds(278, 306, 231, 18);
			jLabel22.setText("总净重-(仓库净重*申报数量)：");
			jLabel22.setForeground(java.awt.Color.red);
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
			jContentPane.add(jLabel21, null);
			jContentPane.add(jLabel22, null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton5(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField() { //总净重
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.setBounds(363, 229, 129, 22);
			jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					//总净重-(仓库净重*申报数量)
					getZj_CK();						        
				}
			}
		  );
		}
		return jFormattedTextField;
	}
	
	private void getZj_CK(){

		double totalNetweight = (jFormattedTextField.getText() == null || jFormattedTextField
				.getText().equals("")) ? 0 : Double
				.parseDouble(jFormattedTextField.getText());
		double declareNum = (jFormattedTextField3 == null || jFormattedTextField3
				.getText().equals("")) ? 0 : Double
				.parseDouble(jFormattedTextField3.getText());
		double materielStorNetght = materiel.getPtNetWeight() == null ? 0
				: (materiel.getPtNetWeight().doubleValue());		
		
		jLabel22.setText("总净重-(净重*申报数量)："
				+ String.valueOf(totalNetweight
						- (materielStorNetght * declareNum)));
		
		
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
	
    //得到每箱件数 
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
	private JFormattedTextField getJFormattedTextField3() {//申报数量
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField3.setBounds(97, 195, 82, 22);
			jFormattedTextField3.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {    
			        	jFormattedTextField2.setText(jFormattedTextField3.getText());
			        	jFormattedTextField4.setText("1");
			        	if (bcusexgbill != null){
			        		getExgTotalNetGrossWeight(bcusexgbill);
			        	} else if (bcusimgbill != null){
			        		getImgTotalNetGrossWeight(bcusimgbill);
			        	}
			        	//得到总净重-(仓库净重*申报数量)
			        	getZj_CK();
				}
			});
			
		}
		return jFormattedTextField3;
	}
	
	
    //得到料件总净毛重
	private void getImgTotalNetGrossWeight(EmsHeadH2kImg bcusimgbill){
		//1.不論申報單位為個或KG或臺時,凈重=內部申報數量*ERP凈重
		                        //毛重 = 内部申报数量* ERP净重
		double declareNum =  Double.parseDouble(jFormattedTextField3.getText().toString());//申报数量
		double materielStorNetweight = materiel.getPtNetWeight()==null?0:(materiel.getPtNetWeight().doubleValue()*weightRate.doubleValue()); 
		double materielStorGossweight = materiel.getPtOutWeight()==null?0:(materiel.getPtOutWeight().doubleValue()*weightRate.doubleValue()); 
		jFormattedTextField.setValue(materielStorNetweight * declareNum);  //总净重
		jFormattedTextField6.setValue(materielStorGossweight * declareNum);	//总毛重
	}	
	
	
    //得到成品总净毛重
	private void getExgTotalNetGrossWeight(EmsHeadH2kExg bcusexgbill){
		Integer versionI = null;
		if (info != null){
			versionI = Integer.valueOf(info.getVersion());
		} else {
			versionI = bcusexgbill.getVersion();
		}
		EmsHeadH2kVersion verisonObj = bcusEncAction.findEmsHeadH2kVersion(new Request(CommonVars.getCurrUser()),bcusexgbill.getSeqNum(),versionI);
		double netWeight = 0;
        double grossWeight = 0;		
        double bcusnetWeight = verisonObj.getUnitNetWeight()==null?0.0:verisonObj.getUnitNetWeight().doubleValue();
		double bcusgossWeight = verisonObj.getUnitGrossWeight()==null?0.0:verisonObj.getUnitGrossWeight().doubleValue();
		double declareNum =  Double.parseDouble(jFormattedTextField3.getText().toString());//申报数量
		if (bcusexgbill.getUnit() != null
				&& (bcusexgbill.getUnit().getName().trim().equals("公斤") || bcusexgbill.getUnit().getName().trim().equals("千克"))) {         
            //申报单位净重 = 申报数量 * ERP净重 * 重量比例
			//申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)            
            double materielStorNetweight = materiel.getPtNetWeight()==null?0:(materiel.getPtNetWeight().doubleValue()*weightRate.doubleValue());            
            double weightScale = bcusnetWeight == 0?0:bcusgossWeight/bcusnetWeight;
			netWeight = declareNum * materielStorNetweight * weightpara;			
			grossWeight = netWeight * weightScale;
		} else {// 个
			grossWeight = declareNum * bcusgossWeight;
			netWeight = declareNum * bcusnetWeight;
		}
		jFormattedTextField6.setValue(grossWeight);//总毛重
		jFormattedTextField.setValue(netWeight);  //总净重
	}
	
	private Double forD(Double d){
		if (d != null){
			return d;
		}
		return Double.valueOf(0);
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(331, 331, 73, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					double materielNetweight = materiel.getPtNetWeight()==null?0:(materiel.getPtNetWeight().doubleValue() * weightRate.doubleValue());//折算成千克
					double materielStorNetweight = materiel.getCknetWeight()==null?0:(materiel.getCknetWeight().doubleValue() * weightRate.doubleValue());
					if ((materielNetweight - materielStorNetweight)>0.5){
						if (JOptionPane.showConfirmDialog(DgBillFromMaterielEdit.this,"净重比仓库净重大于0.5,是否继续?","提示",0)!=0){
							return;
						}
					}
					double totalNetweight = (jFormattedTextField.getText()==null || jFormattedTextField.getText().equals(""))?0:Double.parseDouble(jFormattedTextField.getText());
					double declareNum = (jFormattedTextField3==null|| jFormattedTextField3.getText().equals(""))?0:Double.parseDouble(jFormattedTextField3.getText());
					if (totalNetweight-(materielStorNetweight*declareNum)>300){
						if (JOptionPane.showConfirmDialog(DgBillFromMaterielEdit.this,"总净重-(仓库净重*申报数量)大于300,是否继续?","提示",0)!=0){
							return;
						}
					}
					
					
					double gossWeight = 0;
					double netWeight = 0;
					Integer piece = 0;
					AtcMergeBeforeComInfo before = null;
					Unit unit = null;
					Unit firstUnit = null;
					Unit seconedUnit = null;					
                    //总毛重					
					if (jFormattedTextField6.getText()!= null && !jFormattedTextField6.getText().equals("")){
						gossWeight =  Double.parseDouble(jFormattedTextField6.getText().toString());
					}
                    //总净重
					if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
						netWeight =  Double.parseDouble(jFormattedTextField.getText().toString());
					}
					//件数
					try{
						piece = Integer.valueOf(jFormattedTextField2.getText());
					} catch  (Exception e1){
						piece = 0;
					}
					switch (projectType){
					case ProjectType.BCUS:
						before = new AtcMergeBeforeComInfo();
						if (bcusimgbill != null){
						    unit = bcusimgbill.getUnit();
						    firstUnit = bcusimgbill.getComplex().getFirstUnit();
						    seconedUnit = bcusimgbill.getComplex().getSecondUnit();
						} else if (bcusexgbill != null){
							unit = bcusexgbill.getUnit();
							firstUnit = bcusexgbill.getComplex().getFirstUnit();
						    seconedUnit = bcusexgbill.getComplex().getSecondUnit();
						}
						break;
					case ProjectType.BCS:
						before = new AtcMergeBeforeComInfo();
						if (bcsimgbill != null ){
						   unit = bcsimgbill.getUnit();
						} else if (bcsexgbill != null){
						   unit = bcsexgbill.getUnit();
						}
						break;
					}
				    //浏览修改
					/*if (isBrowse){								
						double commAmount = 0;
						double firstAmount = 0;
						double secondAmount = 0;							
	                    //进口
						if (bcusimgbill != null  || bcsimgbill != null){ 
						    if (unit != null && (unit.getName().trim().equals("公斤") || 
									unit.getName().trim().equals("千克"))){
								if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
									commAmount =  Double.parseDouble(jFormattedTextField.getText().toString()); //报关数量=总净重
								}
							} else {//个
								if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
									commAmount =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量
								}	
							}						
							double yshuliang =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量					
							if (firstUnit != null && firstUnit.getName().trim().equals("米")) {
								firstAmount = yshuliang * 0.9144;
				            } else if (firstUnit!= null && (firstUnit.getName().trim().equals(
								      "千克") || firstUnit.getName().trim().equals("公斤"))) {
				            	firstAmount = commAmount;			            
				            }
							if (seconedUnit != null && seconedUnit.getName().trim().equals("米")) {
								secondAmount = yshuliang * 0.9144;				            
				            } else if (seconedUnit!= null && (seconedUnit.getName().trim().equals(
								      "千克") || seconedUnit.getName().trim().equals("公斤"))) {
				            	secondAmount = commAmount;				           
				            }
							
							
                            //当申报单位等于第一法定单位或第二法定单位
							if (unit!= null && unit.equals(firstUnit)){
								firstAmount = commAmount;
					            commInfo.setFirstAmount(commAmount);
							}
							if (unit!= null && unit.equals(seconedUnit)){
								secondAmount = commAmount;
					            commInfo.setSecondAmount(commAmount);	
							}
				        
						} else { //出口						
							if (unit != null && (unit.getName().trim().equals("公斤") || 
									unit.getName().trim().equals("千克"))){
								if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
									commAmount =  Double.parseDouble(jFormattedTextField.getText().toString());//报关数量=总净重									 									
								}
                                //第一法定单位
								firstAmount = commAmount;									
								if (firstUnit!= null && (firstUnit.getName().trim().equals( "个") || firstUnit.getName().trim().equals( "辆"))) {
			            	        firstAmount = Double.parseDouble(jFormattedTextField3.getText().toString());
								} else if (firstUnit!= null && firstUnit.getName().trim().equals( "件")){
									firstAmount = Double.valueOf(piece); //等于件数
								}
								//第二法定单位
								if (seconedUnit != null && (seconedUnit.getName().trim().equals("个") || seconedUnit.getName().trim().equals("辆"))) {
									secondAmount = Double.parseDouble(jFormattedTextField3.getText().toString());//第二法定数量=申报数量			            
					            } else if (seconedUnit!= null && (seconedUnit.getName().trim().equals("千克") || seconedUnit.getName().trim().equals("公斤"))) {
					            	secondAmount = commAmount;
					            }
							} else { //个
								if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
									commAmount =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量									
								}
								//第一法定单位
								firstAmount = commAmount;
								//第二法定单位
								if (seconedUnit!= null && (seconedUnit.getName().trim().equals("千克") || seconedUnit.getName().trim().equals("公斤"))) {
									secondAmount = Double.parseDouble(jFormattedTextField.getText().toString());//第二法定数量=总净重	            
					            }								
							}						
						}						
						commInfo.setCommAmount(forD(fromMateriel.getBgCommAmount())-commAmount);
						commInfo.setFirstAmount(forD(fromMateriel.getBgFirstAmount()) - firstAmount);
						commInfo.setSecondAmount(forD(fromMateriel.getBgSecondAmount()) - secondAmount);
						commInfo.setNetWeight(forD(fromMateriel.getBgNetWeight()) - netWeight);
						commInfo.setGrossWeight(forD(fromMateriel.getBgGrossWeight()) - gossWeight);
						commInfo.setPieces((fromMateriel.getBgPieces() == null ? 0 : fromMateriel.getBgPieces()) - piece);	
						commInfo.setVersion(info.getVersion());
						bcusEncAction.saveCustomsinfoFromBill2(new Request(CommonVars.getCurrUser()),commInfo,customsDeclaration,bcusexgbill,bcusimgbill);	

						//报关单数据------------------------------------------
						fromMateriel.setBgCommAmount(commAmount);
						fromMateriel.setBgFirstAmount(firstAmount);
						fromMateriel.setBgSecondAmount(secondAmount);
						fromMateriel.setBgGrossWeight(gossWeight);
						fromMateriel.setBgNetWeight(netWeight);
						fromMateriel.setBgPieces(piece);
						//--------------------------------------------------
						fromMateriel.setPtNum(CommonVars.formatStrDouble(jFormattedTextField3.getText()));
						fromMateriel.setSumBoxNum(CommonVars.formatStrDouble(jFormattedTextField2.getText()));
						fromMateriel.setBoxNum(CommonVars.formatStrDouble(jFormattedTextField4.getText()));
						fromMateriel.setSumNetWeight(CommonVars.formatStrDouble(jFormattedTextField.getText()));
						fromMateriel.setSumGrossWeight(CommonVars.formatStrDouble(jFormattedTextField6.getText()));
						fromMateriel.setSjNetWeight(CommonVars.formatStrDouble(jFormattedTextField1.getText()));
						fromMateriel.setSjGrossWeight(CommonVars.formatStrDouble(jFormattedTextField5.getText()));		
						bcusEncAction.SaveCustomsFromMateriel(new Request(CommonVars.getCurrUser()),(CustomsFromMateriel)fromMateriel);						
					} else {*/
							double commAmount = 0;
							double firstAmount = 0;
							double secondAmount = 0;					
		                    //进口
							if (bcusimgbill != null){ 
							    if (unit != null && (unit.getName().trim().equals("公斤") || 
										unit.getName().trim().equals("千克"))){
									if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
										commAmount =  Double.parseDouble(jFormattedTextField.getText().toString()); //报关数量=总净重
									}
								} else {//个
									if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
										commAmount =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量
									}	
								}						
								double yshuliang =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量
								before.setDeclaredAmount(commAmount);//数量						
								if (firstUnit != null && firstUnit.getName().trim().equals("米")) {
									firstAmount = yshuliang * 0.9144;
									before.setLegalAmount(firstAmount);
					            } else if (firstUnit!= null && (firstUnit.getName().trim().equals(
									      "千克") || firstUnit.getName().trim().equals("公斤"))) {
					            	firstAmount = commAmount;
					            	before.setLegalAmount(commAmount);					            
					            }
								if (seconedUnit != null && seconedUnit.getName().trim().equals("米")) {
									secondAmount = yshuliang * 0.9144;
						            before.setSecondLegalAmount(secondAmount);
						            
					            } else if (seconedUnit!= null && (seconedUnit.getName().trim().equals(
									      "千克") || seconedUnit.getName().trim().equals("公斤"))) {
					            	secondAmount = commAmount;
					            	before.setSecondLegalAmount(commAmount);					           
					            }
								//当申报单位等于第一法定单位或第二法定单位
								if (unit!= null && unit.equals(firstUnit)){
									firstAmount = commAmount;
						            before.setLegalAmount(commAmount);
								}
								if (unit!= null && unit.equals(seconedUnit)){
									secondAmount = commAmount;
						            before.setSecondLegalAmount(commAmount);	
								}
								
							} else { //出口						
								if (unit != null && (unit.getName().trim().equals("公斤") || 
										unit.getName().trim().equals("千克"))){
									if (jFormattedTextField.getText()!= null && !jFormattedTextField.getText().equals("")){
										commAmount =  Double.parseDouble(jFormattedTextField.getText().toString());//报关数量=总净重
									}
                                    //第一法定单位
									firstAmount = commAmount;									
									if (firstUnit!= null && (firstUnit.getName().trim().equals( "个") || seconedUnit.getName().trim().equals("辆"))) {
				            	        firstAmount = Double.parseDouble(jFormattedTextField3.getText().toString());
									}  else if (firstUnit!= null && firstUnit.getName().trim().equals( "件")){
										firstAmount = Double.valueOf(piece); //等于件数
									}
									//第二法定单位
									if (seconedUnit != null && (seconedUnit.getName().trim().equals("个") || seconedUnit.getName().trim().equals("辆"))) {
										secondAmount = Double.parseDouble(jFormattedTextField3.getText().toString());//第二法定数量=申报数量			            
						            }  else if (seconedUnit!= null && (seconedUnit.getName().trim().equals("千克") || seconedUnit.getName().trim().equals("公斤"))) {
						            	secondAmount = commAmount;		           
						            }
									
								} else { //个
									if (jFormattedTextField3.getText()!= null && !jFormattedTextField3.getText().equals("")){
										commAmount =  Double.parseDouble(jFormattedTextField3.getText().toString());//报关数量=申报数量
									}	
									//第二法定单位
									if (seconedUnit!= null && (seconedUnit.getName().trim().equals("千克") || seconedUnit.getName().trim().equals("公斤"))) {
										secondAmount = Double.parseDouble(jFormattedTextField.getText().toString());//第二法定数量=总净重	            
						            }
									//第一法定单位
									firstAmount = commAmount; 
								}									
								before.setLegalAmount(forInterNum(firstAmount));   //第一法定数量
								before.setSecondLegalAmount(forInterNum(secondAmount));//第二法定数量
								before.setDeclaredAmount(forInterNum(commAmount));    //数量		
								before.setVersion(bcusexgbill.getVersion() == null?null:bcusexgbill.getVersion());//版本号
							}					
							before.setGrossWeight(forInterNum(gossWeight));//毛重
							before.setNetWeight(forInterNum(netWeight));//净重
							before.setMateriel(materiel);//物料
							switch (projectType){
							case ProjectType.BCUS:
								allList = bcusEncAction.newBillBeforeFromMateriel(new Request(CommonVars.getCurrUser()),before,bcusimgbill,bcusexgbill,applyToCustomsBillList);
								/*CustomsFromMateriel obj = new CustomsFromMateriel();
								obj.setInfoid(info.getId());
								obj.setMateriel(materiel);
								obj.setBcusexgbill(bcusexgbill);
								obj.setBcusimgbill(bcusimgbill);
								obj.setBcusexgNo(bcusexgbill==null?null:bcusexgbill.getSeqNum());
								obj.setBcusimgNo(bcusimgbill==null?null:bcusimgbill.getSeqNum());
								//报关单数据------------------------------------------
								obj.setBgCommAmount(commAmount);
								obj.setBgFirstAmount(firstAmount);
								obj.setBgSecondAmount(secondAmount);
								obj.setBgGrossWeight(gossWeight);
								obj.setBgNetWeight(netWeight);
								obj.setBgPieces(piece);
								//--------------------------------------------------
								obj.setPtNum(CommonVars.formatStrDouble(jFormattedTextField3.getText()));
								obj.setSumBoxNum(CommonVars.formatStrDouble(jFormattedTextField2.getText()));
								obj.setBoxNum(CommonVars.formatStrDouble(jFormattedTextField4.getText()));
								obj.setSumNetWeight(CommonVars.formatStrDouble(jFormattedTextField.getText()));
								obj.setSumGrossWeight(CommonVars.formatStrDouble(jFormattedTextField6.getText()));
								obj.setSjNetWeight(CommonVars.formatStrDouble(jFormattedTextField1.getText()));
								obj.setSjGrossWeight(CommonVars.formatStrDouble(jFormattedTextField5.getText()));
								obj.setCompany(CommonVars.getCurrUser().getCompany());						
								bcusEncAction.SaveCustomsFromMateriel(new Request(CommonVars.getCurrUser()),obj);*/
								break;
							case ProjectType.BCS:
								/*BaseCustomsDeclarationCommInfo bcsinfo = bcsEncAction.saveCustomsinfoFromBill(new Request(CommonVars.getCurrUser()),commInfo,customsDeclaration,bcsexgbill,bcsimgbill);
								BcsCustomsFromMateriel bcsObj = new BcsCustomsFromMateriel();
								bcsObj.setInfoid(bcsinfo.getId());
								bcsObj.setMateriel(materiel);
								bcsObj.setBcsexgbill(bcsexgbill);
								bcsObj.setBcsimgbill(bcsimgbill);
								bcsObj.setPtNum(CommonVars.formatStrDouble(jFormattedTextField3.getText()));
								bcsObj.setSumBoxNum(CommonVars.formatStrDouble(jFormattedTextField2.getText()));
								bcsObj.setBoxNum(CommonVars.formatStrDouble(jFormattedTextField4.getText()));
								bcsObj.setSumNetWeight(CommonVars.formatStrDouble(jFormattedTextField.getText()));
								bcsObj.setSumGrossWeight(CommonVars.formatStrDouble(jFormattedTextField6.getText()));
								bcsObj.setSjNetWeight(CommonVars.formatStrDouble(jFormattedTextField1.getText()));
								bcsObj.setSjGrossWeight(CommonVars.formatStrDouble(jFormattedTextField5.getText()));
								bcsObj.setCompany(CommonVars.getCurrUser().getCompany());						
								bcsEncAction.SaveCustomsFromMateriel(new Request(CommonVars.getCurrUser()),bcsObj);*/
								break;					
							}	
					//}
					setOk(true);
					dispose();
				}
			});
		}
		return jButton;
	}
	
	private Double forInterNum(Double shuliang) {
		if (shuliang == null) {
			return shuliang;
		}
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}
	
	/*private Double forNum(double shuliang){
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalshuliang);
	}*/
	
	public Double forNum(double unroundedValue ){
		 int digits = 6;
		 int x = 1;
		 for (int i=0;i<digits;i++){
			x = x * 10;
		 }
	     double intPortion = Math.floor(unroundedValue );
	     double unroundedDecimalPortion = (unroundedValue - intPortion );		
	     double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x );	
	     roundedDecimalPortion = roundedDecimalPortion / x;	
	     double total = intPortion + roundedDecimalPortion;		
	     return new Double( total );
   }
	 
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(421, 331, 74, 25);
			jButton1.setText("放弃");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					setOk(false);
					DgBillFromMaterielEdit.this.dispose();
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
	private JFormattedTextField getJFormattedTextField6() { //总毛重
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
	/**
	 * @param bcsexgbill The bcsexgbill to set.
	 */
	public void setBcsexgbill(ContractExg bcsexgbill) {
		this.bcsexgbill = bcsexgbill;
	}
	/**
	 * @param bcsimgbill The bcsimgbill to set.
	 */
	public void setBcsimgbill(ContractImg bcsimgbill) {
		this.bcsimgbill = bcsimgbill;
	}
	/**
	 * @param bcusexgbill The bcusexgbill to set.
	 */
	public void setBcusexgbill(EmsHeadH2kExg bcusexgbill) {
		this.bcusexgbill = bcusexgbill;
	}
	/**
	 * @param bcusimgbill The bcusimgbill to set.
	 */
	public void setBcusimgbill(EmsHeadH2kImg bcusimgbill) {
		this.bcusimgbill = bcusimgbill;
	}

	/**
	 * @param projectType The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	/**
	 * @param info The info to set.
	 */
	public void setInfo(BaseCustomsDeclarationCommInfo info) {
		this.info = info;
	}
	/**
	 * @param fromMateriel The fromMateriel to set.
	 */
	public void setFromMateriel(BaseCustomsFromMateriel fromMateriel) {
		this.fromMateriel = fromMateriel;
	}
	/**
	 * @param isBrowse The isBrowse to set.
	 */
	public void setBrowse(boolean isBrowse) {
		this.isBrowse = isBrowse;
	}
	private void setState(){				
		int listsize = list == null ? 0 : list.size();
		jButton2.setEnabled(listsize > 1 && isBrowse);
		jButton3.setEnabled(listsize > 1 && isBrowse);
		jButton5.setEnabled(listsize > 1 && isBrowse); //删除按扭状态
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(93, 331, 71, 25);
			jButton2.setText("上页");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (pagenum>0){
						pagenum = pagenum -1;
						initdata(pagenum);
					}
				}
			});
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(169, 331, 69, 25);
			jButton3.setText("下页");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					if (pagenum<list.size()-1){
						pagenum = pagenum+1;
						initdata(pagenum);
					}
				}
			});
		}
		return jButton3;
	}
	
	
	
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(245, 331, 69, 25);
			jButton5.setText("删除");
			jButton5.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					if (JOptionPane.showConfirmDialog(DgBillFromMaterielEdit.this,
							"确定要删除吗？", "确认", 0) == 0) {						
						bcusEncAction.saveCustomsinfoFromBill3(new Request(CommonVars.getCurrUser()),fromMateriel,customsDeclaration,bcusexgbill,bcusimgbill);	
						setOk(true);
						dispose();
					}
				}
			});
		}
		return jButton5;
	}
	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}
	/**
	 * @param list The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	public ApplyToCustomsBillList getApplyToCustomsBillList() {
		return applyToCustomsBillList;
	}

	public void setApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.applyToCustomsBillList = applyToCustomsBillList;
	}

	public List getAllList() {
		return allList;
	}
                             }  //  @jve:decl-index=0:visual-constraint="10,10"
