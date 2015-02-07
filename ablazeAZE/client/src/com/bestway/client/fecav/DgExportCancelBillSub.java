package com.bestway.client.fecav;

import java.awt.Rectangle;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class DgExportCancelBillSub  extends JDialogBase{
	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel71 = null;

	private JLabel jLabel72 = null;

	private Boolean isOverPrint = false; // @jve:decl-index=0:

	private JComboBox cbbCurrency = null;

	private JComboBox cbbBalanceModel = null;

	private JTextArea jtMemo = null;
	
	private JCalendarComboBox cbbDeclarationDate = null;
	
	private JLabel jLabel81 = null;

	private JTextField jtHsCode = null;

	private JLabel jLabel56 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;
	
	private JTableListModel tableModel = null;
	
	private JTableListModel masterModel = null;

	private JTextField jtPayDate = null;
	
	private BaseEncAction baseEncAction = null;

	private JLabel jLabel = null;

	private JLabel jLabel11 = null;

	private JTextField jTextField = null;
	
	private JScrollPane	jScrollPane1	= null;	

	/**
	 * This is the default constructor
	 */
	public DgExportCancelBillSub() {
		super();
		initialize();
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext().getBean(
		"encAction");		
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
	private void initUIComponents() {
		FecavBill fbill = (FecavBill)masterModel.getCurrentRow();
		this.jtHsCode.setText(fbill.getCustomsDeclarationCode());
		if(fbill.getTotalPrice()!=null){
			this.jTextField.setText(fbill.getTotalPrice().toString());
		}else{
			this.jTextField.setText("0.0");
		}
		BaseCustomsDeclaration bcd = baseEncAction.findAllCustomsDeclarationById(new Request(CommonVars
				.getCurrUser()), fbill.getCustomsDeclarationId());
		List list = null;
		if(bcd!=null){
			list = baseEncAction.findCustomsDeclarationInfo(new Request(CommonVars
				.getCurrUser()), bcd);
		}
		initTable(list);
		// 初始化币制
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCurrency);	
		// 备注
		this.jtMemo.setText("");
		// 初始化结汇方式
		this.cbbBalanceModel.setModel(CustomBaseModel.getInstance()
				.getBalanceModeModel());
		this.cbbBalanceModel.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbBalanceModel.setMaximumRowCount(CustomBaseModel.getInstance()
				.getBalanceModeModel().getSize());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBalanceModel);
		if(bcd!=null){
			this.cbbCurrency.setSelectedItem(bcd.getCurrency());
			this.cbbBalanceModel.setSelectedItem(bcd.getBalanceMode());
			this.cbbDeclarationDate.setDate(bcd.getDeclarationDate());
		}		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口收汇核销单项目");
		this.setSize(508, 388);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(272, 10, 75, 23));
			jLabel11.setText("出口总价");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(451, 40, 18, 23));
			jLabel.setText("天");
			jLabel56 = new JLabel();
			jLabel56.setBounds(new Rectangle(25, 185, 60, 22));
			jLabel56.setText("货物资料");
			jLabel81 = new JLabel();
			jLabel81.setBounds(new Rectangle(25, 72, 60, 22));
			jLabel81.setText("报关单编号");
			jLabel72 = new JLabel();
			jLabel72.setBounds(new Rectangle(25, 10, 60, 22));
			jLabel72.setText("出口币种");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(25, 40, 60, 22));
			jLabel71.setText("收汇方式");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(25, 104, 60, 22));
			jLabel21.setText("备注");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(272, 40, 75, 22));
			jLabel7.setText("预计收款日期");
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(272, 70, 60, 22);
			jLabel1.setText("报关日期");
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel21, null);
			jContentPane.add(jLabel71, null);
			jContentPane.add(jLabel72, null);
			jContentPane.add(getCbbCurrency(), null);
			jContentPane.add(getCbbBalanceModel(), null);
			jContentPane.add(getJtMemo(), null);
			jContentPane.add(getCbbDeclarationDate(), null);
			jContentPane.add(jLabel81, null);
			jContentPane.add(getJtHsCode(), null);
			jContentPane.add(jLabel56, null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJtPayDate(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJScrollPane1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(133, 318, 90, 21);
			jButton.setText("打印");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<BaseCustomsDeclarationCommInfo> list = tableModel.getList();
					if(list.size()>4){//如果打印行数大于4,然后统计总数量,统计总价格并弹出对话框填写货物名称
						String inputName = JOptionPane.showInputDialog(DgExportCancelBillSub.this, "请输入货物名称:");
						if(inputName==null||inputName.equals("")){
							JOptionPane.showMessageDialog(null,"请输入您要打印的货物名称","提示信息",JOptionPane.ERROR_MESSAGE);
							return;
						}
						double totalPiece = 0.0;
						double totalPrice = 0.0;
						for(BaseCustomsDeclarationCommInfo item : list){
							if(item.getCommAmount()!=null)
								totalPiece = doubleAdd(totalPiece,item.getCommAmount().doubleValue());
							
							if(item.getCommTotalPrice()!=null)
								totalPrice = doubleAdd(totalPrice,item.getCommTotalPrice().doubleValue());
						}
						List<BaseCustomsDeclarationCommInfo> listTmp = new ArrayList();
						BaseCustomsDeclarationCommInfo cdc = new BaseCustomsDeclarationCommInfo();
						cdc.setCommName(inputName);
						cdc.setCommTotalPrice(new Double(totalPrice));
						cdc.setCommAmount(new Double(totalPiece));
						listTmp.add(cdc);
						printExportReport(listTmp, isOverPrint);
					}else{
						printExportReport(list, isOverPrint);
					}

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
			jButton1.setBounds(269, 318, 90, 21);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	//出口收汇核销单
	private void printExportReport(List commInfoList, boolean isOverPrint) {
		if(commInfoList==null||commInfoList.size()==0){
			JOptionPane.showMessageDialog(this, "当前无数据可列印！", "提示",
					JOptionPane.YES_NO_OPTION);
			return;			
		}
		CustomReportDataSource ds = new CustomReportDataSource(commInfoList);
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("overprint", Boolean.valueOf(isOverPrint));
			parameters.put("totalMoney", jTextField.getText());//出口货币总价
			if(cbbBalanceModel.getSelectedItem()!=null){
				BalanceMode bm = (BalanceMode)cbbBalanceModel.getSelectedItem();
				parameters.put("balanceModelName", bm.getName());//收汇方式
			}
			if(cbbCurrency.getSelectedItem()!=null){
				Curr curr = (Curr)cbbCurrency.getSelectedItem();
				parameters.put("currencyName", curr.getName());//出口货币币种
			}
			
			parameters.put("payDate", jtPayDate.getText());//预计收款日期
			parameters.put("declarationDate", cbbDeclarationDate.getDate());//报关日期
			parameters.put("memo", jtMemo.getText());//备注
			parameters.put("hsCode", jtHsCode.getText());//报关单号
			InputStream reportStream = DgExportCancelBillSub.class
					.getResourceAsStream("report/ExportCancelBill.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param isOverPrint
	 *            the isOverPrint to set
	 */
	public void setIsOverPrint(Boolean isOverPrint) {
		this.isOverPrint = isOverPrint;
	}

	/**
	 * This method initializes cbbCurrency	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(new Rectangle(96, 10, 144, 22));
		}
		return cbbCurrency;
	}

	/**
	 * This method initializes cbbBanlanceModel	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbBalanceModel() {
		if (cbbBalanceModel == null) {
			cbbBalanceModel = new JComboBox();
			cbbBalanceModel.setBounds(new Rectangle(96, 40, 143, 22));
		}
		return cbbBalanceModel;
	}

	/**
	 * This method initializes jtMemo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextArea getJtMemo() {
		if (jtMemo == null) {
			jtMemo = new JTextArea();
			jtMemo.setBounds(new Rectangle(96, 104, 373, 61));
		}
		return jtMemo;
	}
	
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(96, 104, 373, 61));
			jScrollPane1.setViewportView(getJtMemo());
		}
		return jScrollPane1;
	}	
	
	private JCalendarComboBox getCbbDeclarationDate() {
		if (cbbDeclarationDate == null) {
			cbbDeclarationDate = new JCalendarComboBox();
			cbbDeclarationDate.setBounds(new Rectangle(361, 70, 107, 21));
		}
		return cbbDeclarationDate;
	}
	
	/**
	 * This method initializes jtHsCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtHsCode() {
		if (jtHsCode == null) {
			jtHsCode = new JTextField();
			jtHsCode.setBounds(new Rectangle(96, 72, 143, 22));
		}
		return jtHsCode;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(26, 216, 442, 87));
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
		}
		return jTable;
	}
	
	/**
	 * 初始化报关单商品信息
	 */
	private void initTable(List<BaseCustomsDeclarationCommInfo> list) {
		if(list==null){
			list = new ArrayList();
		}
//		List<CustomsDeclarationCommInfo> list = new ArrayList();
		//List list = getDataSource();
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("数量", "commAmount", 60));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("总价", "commTotalPrice", 60));
						
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * This method initializes jtPayDate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtPayDate() {
		if (jtPayDate == null) {
			jtPayDate = new JTextField();
			jtPayDate.setBounds(new Rectangle(361, 40, 88, 22));
			jtPayDate.setHorizontalAlignment(JTextField.RIGHT);
			jtPayDate.setText("90");
		}
		return jtPayDate;
	}

	public JTableListModel getMasterModel() {
		return masterModel;
	}

	public void setMasterModel(JTableListModel masterModel) {
		this.masterModel = masterModel;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(361, 10, 107, 22));
		}
		return jTextField;
	}	

	/**
	 * 二进制相加
	 * @param first
	 * @param second
	 * @return
	 */
	private double doubleAdd(double first,double second){
		BigDecimal biga = new BigDecimal(first*Math.pow(10.0,16));
		BigDecimal bigb = new BigDecimal(second*Math.pow(10.0,16));
		return bigb.add(biga).movePointLeft(16).doubleValue();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
