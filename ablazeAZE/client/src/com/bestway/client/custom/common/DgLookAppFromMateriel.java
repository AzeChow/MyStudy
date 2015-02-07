/*
 * Created on 2005-10-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgLookAppFromMateriel extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField2 = null;
	private JButton jButton = null;
	
	private JTableListModel tableModel = null;
	private List list = null;
    //Bcus
	private EmsHeadH2kImg bcusimgbill = null;
	private EmsHeadH2kExg bcusexgbill = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField3 = null;
	private BaseCustomsFromMateriel fromMateriel = null;
	private JLabel jLabel4 = null;
	private JTextField jTextField4 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private  NumberFormatter numberFormatter=null;
	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse
	private JLabel jLabel5 = null;
	private JTextField jTextField5 = null;
	private JLabel jLabel8 = null;
	private JTextField jTextField6 = null;
	private JLabel jLabel9 = null;
	private JTextField jTextField7 = null;
	private Materiel m = null;
	private BaseCustomsDeclarationCommInfo info = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JButton jButton4 = null;
	private Double weightRate = Double.valueOf(0); //重量比例因子
	private EncAction baseEncAction = null;
	
	
	/**
	 * This is the default constructor
	 */
	public DgLookAppFromMateriel() {
		super();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean("encAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(744, 517);
		this.setContentPane(getJContentPane());
		this.setTitle("查看申请单转报关单明细");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				
				initdata();
				if (bcusimgbill!=null){
					fillBcusImgWindow();
				} else if (bcusexgbill!=null){
					fillBcusExgWindow();
				}
				if (list == null){
					list = new Vector();
				}
				initTable(list);
				showCy();
			}
		});		
	}
	
	private void initdata(){		
		int listsize = list == null ? 0 : list.size();
		jButton1.setEnabled(listsize > 1);
		jButton2.setEnabled(listsize > 1);
		jButton3.setEnabled(listsize > 1);
	}
	
	private String fomatD(Double d){
		double amount = 0.0;
		if (d != null){
			amount = d.doubleValue();
		}
		BigDecimal bd = new BigDecimal(amount);
		String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return totalPrice;
	}
    //联网监管
	private void fillBcusImgWindow(){
		jTextField.setText(String.valueOf(bcusimgbill.getSeqNum()));
		jTextField1.setText(bcusimgbill.getName());
		jTextField2.setText(bcusimgbill.getSpec());
		jTextField3.setText(bcusimgbill.getComplex().getCode());
		jTextField4.setText(bcusimgbill.getUnit()==null?null:bcusimgbill.getUnit().getName());
		jFormattedTextField.setText(doubleToStr(bcusimgbill.getUnitNetWeight()));
		jFormattedTextField1.setText(doubleToStr(bcusimgbill.getUnitGrossWeight()));
		jTextField5.setText(fromMateriel.getScmCoc() != null?fromMateriel.getScmCoc().getName():null);
		
	}
	private void fillBcusExgWindow(){
		EmsHeadH2kVersion verisonObj = baseEncAction.findEmsHeadH2kVersion(new Request(CommonVars.getCurrUser()),bcusexgbill.getSeqNum(),Integer.valueOf(info.getVersion()));
		jTextField.setText(String.valueOf(bcusexgbill.getSeqNum()));
		jTextField1.setText(bcusexgbill.getName());
		jTextField2.setText(bcusexgbill.getSpec());
		jTextField3.setText(bcusexgbill.getComplex().getCode());
		jTextField4.setText(bcusexgbill.getUnit()==null?null:bcusexgbill.getUnit().getName());
		jFormattedTextField.setText(doubleToStr(verisonObj.getUnitNetWeight()));
		jFormattedTextField1.setText(doubleToStr(verisonObj.getUnitGrossWeight()));
		jTextField5.setText(fromMateriel.getScmCoc() != null?fromMateriel.getScmCoc().getName():null);

	}
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("申请单号","impExpRequestBill.billNo", 100));
						list.add(addColumn("料号", "materiel.ptNo", 90));
						list.add(addColumn("名称", "materiel.factoryName", 120));
						list.add(addColumn("规格型号", "materiel.factorySpec",
										120));
						list.add(addColumn("单位", "materiel.calUnit.name", 60));
						list.add(addColumn("数量", "quantity", 60));
						list.add(addColumn("单价", "unitPrice", 60));
						list.add(addColumn("总金额", "amountPrice", 60));						
						list.add(addColumn("仓库毛重", "materiel.ckoutWeight",60));
		                list.add(addColumn("仓库净重", "materiel.cknetWeight", 60));
		                list.add(addColumn("重量单位", "materiel.calWeightUnit.name", 60));
		                list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("净重", "netWeight", 60));
		                //list.add(addColumn("毛重差异", "gossweightcy",60));
		                //list.add(addColumn("净重差异", "netweightcy", 60));
						list.add(addColumn("体积", "bulks", 60));
						list.add(addColumn("制单号", "makeBillNo", 60));
						list.add(addColumn("版本号", "materiel.ptVersion", 60));
						return list;
					}
				});
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(324,53,223,24));
			jLabel11.setForeground(java.awt.Color.red);
			jLabel11.setText("净重 - 仓库净重：");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(324,83,364,23));
			jLabel10.setForeground(java.awt.Color.red);
			jLabel10.setText("总净重-(仓库净重*申报数量)：");
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel5 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 18, 54, 19);
			jLabel.setText("商品序号");
			jLabel1.setBounds(158, 18, 51, 20);
			jLabel1.setText("商品名称");
			jLabel2.setBounds(328, 18, 50, 20);
			jLabel2.setText("商品规格");
			jLabel3.setBounds(8, 47, 52, 19);
			jLabel3.setText("商品编码");
			jLabel4.setBounds(158, 45, 52, 21);
			jLabel4.setText("申报单位");
			jLabel6.setBounds(8, 74, 52, 23);
			jLabel6.setText("帐册净重");
			jLabel7.setBounds(158, 74, 51, 22);
			jLabel7.setText("帐册毛重");
			jLabel5.setBounds(8, 104, 62, 23);
			jLabel5.setText("客户供应商");
			jLabel8.setBounds(550, 17, 63, 22);
			jLabel8.setForeground(java.awt.Color.red);
			jLabel8.setText("仓库净重");
			jLabel9.setBounds(550, 47, 64, 19);
			jLabel9.setForeground(java.awt.Color.red);
			jLabel9.setText("仓库毛重");
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJFormattedTextField(), null);
			jPanel.add(getJFormattedTextField1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField5(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField7(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getJButton4(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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
			jTextField.setBounds(61, 18, 88, 22);
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
			jTextField1.setBounds(211, 18, 103, 22);
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
			jTextField2.setBounds(381, 18, 123, 22);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(652, 117, 72,24);
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * @param list The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}
	/**
	 * @return Returns the bcusimgbill.
	 */
	public EmsHeadH2kImg getBcusimgbill() {
		return bcusimgbill;
	}
	/**
	 * @param bcusimgbill The bcusimgbill to set.
	 */
	public void setBcusimgbill(EmsHeadH2kImg bcusimgbill) {
		this.bcusimgbill = bcusimgbill;
	}
	/**
	 * @return Returns the bcusexgbill.
	 */
	public EmsHeadH2kExg getBcusexgbill() {
		return bcusexgbill;
	}
	/**
	 * @param bcusexgbill The bcusexgbill to set.
	 */
	public void setBcusexgbill(EmsHeadH2kExg bcusexgbill) {
		this.bcusexgbill = bcusexgbill;
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
			jTextField3.setBounds(61, 45, 88, 22);
		}
		return jTextField3;
	}
	/**
	 * @return Returns the fromMateriel.
	 */
	public BaseCustomsFromMateriel getFromMateriel() {
		return fromMateriel;
	}
	/**
	 * @param fromMateriel The fromMateriel to set.
	 */
	public void setFromMateriel(BaseCustomsFromMateriel fromMateriel) {
		this.fromMateriel = fromMateriel;
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
			jTextField4.setBounds(211, 45, 102, 23);
		}
		return jTextField4;
	}
	/**
	 * This method initializes jFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JFormattedTextField();
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.setEditable(false);
			jFormattedTextField.setBounds(61, 76, 89, 22);
		}
		return jFormattedTextField;
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
	/**
	 * This method initializes jFormattedTextField1	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new JFormattedTextField();
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField1.setEditable(false);
			jFormattedTextField1.setBounds(211, 76, 103, 22);
		}
		return jFormattedTextField1;
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
			jTextField5.setBounds(72, 105, 243, 22);
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
			jTextField6.setBounds(615, 17, 112, 22);
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
			jTextField7.setBounds(616, 45, 111, 23);
		}
		return jTextField7;
	}
	public void setM(Materiel m) {
		this.m = m;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(498,117,72,24));
			jButton1.setText("删除");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 new deletObj().start();
				}
			});
		}
		return jButton1;
	}
	
	
	class deletObj extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgLookAppFromMateriel.this);
				CommonProgress.setMessage("系统正在删除资料，请稍后...");
				ImpExpCommodityInfo obj = (ImpExpCommodityInfo) tableModel.getCurrentRow();
				baseEncAction.deleteImpExpCommodityInfo(new Request(CommonVars.getCurrUser()),info,obj,true);
				tableModel.deleteRow(obj);
				showCy();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgLookAppFromMateriel.this, "删除失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}	
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(346,117,72,24));
			jButton2.setText("上条");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tableModel.previousRow();
					showCy();
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
			jButton3.setBounds(new java.awt.Rectangle(422,117,72,24));
			jButton3.setText("下条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tableModel.nextRow();
					showCy();
				}
			});
		}
		return jButton3;
	}
	
	private void showCy(){
		if (tableModel.getCurrentRow() == null){
			return;
		}
		ImpExpCommodityInfo obj = (ImpExpCommodityInfo) tableModel.getCurrentRow();
		Materiel materiel = obj.getMateriel();
		
		if (materiel.getCalWeightUnit()!= null && materiel.getCalWeightUnit().getName() != null 
				&& materiel.getCalWeightUnit().getName().equals("克")){
			weightRate = 0.001;
		} else {
			weightRate = 1.0;
		}		
		
		double materielNetweight = materiel.getPtNetWeight()==null?0:(materiel.getPtNetWeight().doubleValue() * weightRate.doubleValue());//折算成千克
		double materielStorNetweight = materiel.getCknetWeight()==null?0:(materiel.getCknetWeight().doubleValue() * weightRate.doubleValue());//仓库净重
		double materielStorGrossweight = materiel.getCkoutWeight()==null?0:(materiel.getCkoutWeight().doubleValue() * weightRate.doubleValue());//仓库毛重
		jLabel11.setText("净重 - 仓库净重："+ String.valueOf(forNum(materielNetweight - materielStorNetweight))+" (千克)");		
		
		Double x1 = Double.valueOf(materielStorNetweight);
		Double x0 = x1 * forD(obj.getQuantity());
		Double x = forD(info.getNetWeight()) - x0;
		
		jLabel10.setText("总净重-(仓库净重*申报数量)："+String.valueOf(x)+" (千克)");
		
		jTextField6.setText(String.valueOf(forNum(materielStorNetweight))+" (千克)");//仓库净重
		jTextField7.setText(String.valueOf(forNum(materielStorGrossweight))+" (千克)");//仓库毛重
		
	}
	
	private Double forD(Double d){
		if (d != null){
			return d;
		}
		return Double.valueOf(0);
	}
	
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
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new java.awt.Rectangle(575,117,72,24));
			jButton4.setText("修改");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEditAppFromMateriel obj = new DgEditAppFromMateriel();
					obj.setCustomsInfo(info);
					obj.setTableModel(tableModel);
					obj.setVisible(true);
				}
			});
		}
		return jButton4;
	}
	public void setInfo(BaseCustomsDeclarationCommInfo info) {
		this.info = info;
	}

     }  //  @jve:decl-index=0:visual-constraint="15,11"
