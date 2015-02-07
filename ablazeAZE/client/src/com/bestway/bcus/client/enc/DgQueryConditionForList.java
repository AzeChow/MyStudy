/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgQueryConditionForList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel3 = null;
	private JComboBox cbbCustomer = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel4 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private EncAction encAction = null;
	private List lsResult=null;
	private boolean isImport;
	private JLabel jLabel = null;
	private int type = -1;
	private CommonBaseCodeAction    commonBaseCodeAction             = null;
	private JComboBox jComboBox = null;
	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}
	/**
	 * @param isImport The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}
	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgQueryConditionForList() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
                         "encAction");
		 commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
               .getApplicationContext().getBean("commonBaseCodeAction");
	}
	
    public void setVisible(boolean b) {
        if (b) {
        	initUIComponents();
        }
        super.setVisible(b);
    }
    /**
     * 初始化窗口上 控件的值 
     *
     */
    private void initUIComponents(){
    	this.cbbCustomer.removeAllItems();
		this.cbbCustomer.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.DRAFT).toString(), "草稿"));
		this.cbbCustomer.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.ALREADY_SEND).toString(), "审批未通过"));
		this.cbbCustomer.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.PASSED).toString(), "审批通过"));
//		this.cbbCustomer.addItem(new ItemProperty(Integer.valueOf(
//				ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION).toString(), "已转报关单"));
		this.cbbCustomer.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.Effect).toString(), "生效"));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer);
		this.cbbCustomer.setSelectedIndex(-1);
		
//    	初始化进出口类型
			this.jComboBox.removeAllItems();
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_IMPORT).toString(), "一般贸易进口"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));

			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_BACK_PORT).toString(), "边角料退港"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES).toString(),
					"边角料内销"));
			this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_EXPORT).toString(), "一般贸易出口"));
			this.jComboBox.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.jComboBox);
			this.jComboBox.setSelectedIndex(-1);
			
			
    }
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(375, 219);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(41, 25, 93, 24);
			jLabel1.setText("清单状态");
			jLabel3.setBounds(41, 87, 93, 24);
			jLabel3.setText("录入日期");
			jLabel4.setBounds(231, 89, 13, 18);
			jLabel4.setText("至");
			jLabel.setBounds(41, 56, 93, 20);
			jLabel.setText("进出口类型");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJComboBox(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(139, 25, 197, 22);
		}
		return cbbCustomer;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(139, 88, 89, 20);
		}
		return cbbBeginDate;
	}
	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(245, 88, 90, 19);
		}
		return cbbEndDate;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(82, 139, 81, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer state = null;
					if (cbbCustomer.getSelectedItem() != null){
					    state = Integer.valueOf(((ItemProperty) cbbCustomer.getSelectedItem()).getCode());
					}
					Integer inexptype = null;
					if (jComboBox.getSelectedItem() != null){
						inexptype = Integer.valueOf(((ItemProperty) jComboBox.getSelectedItem()).getCode());									
					}
					Date beginDate=cbbBeginDate.getDate();
					Date endDate=cbbEndDate.getDate();
					lsResult=encAction.findApplyBillList(new Request(
							CommonVars.getCurrUser()),state,inexptype,type,beginDate,endDate);
					dispose();
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(190, 139, 83, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton1;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(139, 55, 197, 23);
		}
		return jComboBox;
	}
          }  //  @jve:decl-index=0:visual-constraint="10,10"
