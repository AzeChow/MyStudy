/*
 * Created on 2005-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeListExgList;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgMakeListPrintSetup extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private EncAction         encAction               = null;
	private List <BillTemp>listTemp = new Vector<BillTemp>();
	private CustomsDeclaration      customsDeclaration               = null; 
	private boolean isImport = true;
	private JButton jButton2 = null;
	public boolean isOverPrint = true;
	/**
	 * This is the default constructor
	 */
	public DgMakeListPrintSetup() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
               .getBean("encAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(433, 197);
		this.setContentPane(getJContentPane());
		this.setTitle("内地海关及香港海关陆路进/出境货清单设置");
		initTable(new Vector());
	}
	
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() { 
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("货物名称及规格", "bill1", 230));
						return list;
					}
				});
		jTable.getColumnModel().removeColumn(jTable.getColumnModel().getColumn(0));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
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
			jScrollPane.setBounds(6, 6, 236, 156);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(249, 98, 76, 22);
			jButton.setText("添加");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					
					List listMakeList = encAction.findMakeListExgList(new Request(CommonVars.getCurrUser()));
					List list = CommonQuery.getInstance().getMakeListExgList(listMakeList);
					for (int i=0;i<list.size();i++){
						MakeListExgList exg = (MakeListExgList) list.get(i);
						String name = exg.getName();
						BillTemp temp = new BillTemp();
						temp.setBill1(name);//货物名称及规格,临时做处理
					    if (listTemp == null || listTemp.size()==0){
					    	temp = addOther(temp);
					    }
						listTemp.add(temp);
						tableModel.addRow(temp);						
					}
				}
			});
		}
		return jButton;
	}
	private BillTemp addOther(BillTemp temp){
		temp.setBill2(String.valueOf(customsDeclaration.getCommodityNum()));//总件数
		temp.setBillSum2(customsDeclaration.getGrossWeight());//总重量（总毛重）
		List listDetail = encAction.findCustomsDeclarationCommInfo(new Request(CommonVars.getCurrUser()),customsDeclaration);
		temp.setBillSum3(getTotalPrices(listDetail));//总价格
		if (isImport(customsDeclaration.getImpExpType())){ //进口
		    temp.setBill3(customsDeclaration.getCustomer().getName());//付货人/地址
		    temp.setBill4(customsDeclaration.getMachName());//收货人/地址
		} else {
			temp.setBill3(customsDeclaration.getMachName());//付货人/地址
			temp.setBill4(customsDeclaration.getCustomer().getName());//收货人/地址
		}
		temp.setBill5("1");
		return temp;
	}
	//判断进出口
	private boolean isImport(int billType) {
        switch (billType) {
        case ImpExpType.DIRECT_IMPORT:
        case ImpExpType.TRANSFER_FACTORY_IMPORT:
        case ImpExpType.BACK_FACTORY_REWORK:
        case ImpExpType.GENERAL_TRADE_IMPORT:
        case ImpExpType.EQUIPMENT_IMPORT:
            isImport = true;
            break;
        case ImpExpType.DIRECT_EXPORT:
        case ImpExpType.TRANSFER_FACTORY_EXPORT:
        case ImpExpType.BACK_MATERIEL_EXPORT:
        case ImpExpType.REWORK_EXPORT:
        case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
        case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
        case ImpExpType.GENERAL_TRADE_EXPORT:
        case ImpExpType.EQUIPMENT_BACK_PORT:
        case ImpExpType.BACK_PORT_REPAIR:
            isImport = false;
            break;
        }
        return isImport;
    }
	
	/**
	 * 取得进出口报关单商品明细的金额总和
	 * 
	 * @return
	 */
	public Double getTotalPrices(List list) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (list == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < list.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
				if (commInfo.getCommTotalPrice() != null) {
					prices += commInfo.getCommTotalPrice().doubleValue();
				}
			}
			return new Double(prices);
		}
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(337, 98, 76, 22);
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					
					List imgExgs = listTemp;
					CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
					List headList = new ArrayList(); 
					headList.add(customsDeclaration);
					String barcode = "";//CustomsDeclarationSubReportDataSource.getBarCode(customsDeclaration.getConveyance());
					CommonDataSource companyDS = new CommonDataSource(headList);
					InputStream masterReportStream = DgMakeListPrintSetup.class
						.getResourceAsStream("report/ExportCustomesAutocarMainfestReportForStril.jasper");			
					InputStream detailReportStream = DgMakeListPrintSetup.class
						.getResourceAsStream("report/ExportCustomsAutocarMainfestCommodityReportForStril.jasper");
					try {
						JasperReport detailReport = (JasperReport)JRLoader.loadObject(detailReportStream);		
						Map parameters = new HashMap();
						parameters.put("imgExgDS",imgExgDS);
						parameters.put("detailReport",detailReport);
						parameters.put("isOverPrint", Boolean.valueOf(isOverPrint));
						parameters.put("barcode",barcode);
						parameters.put("list",headList);
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}
	/**
	 * @return Returns the customsDeclaration.
	 */
	public CustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}
	/**
	 * @param customsDeclaration The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(CustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(337, 126, 76, 22);
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeListPrintSetup.this.dispose();
				}
			});
		}
		return jButton2;
	}
	/**
	 * @return Returns the isOverPrint.
	 */
	public boolean isOverPrint() {
		return isOverPrint;
	}
	/**
	 * @param isOverPrint The isOverPrint to set.
	 */
	public void setOverPrint(boolean isOverPrint) {
		this.isOverPrint = isOverPrint;
	}
     }  //  @jve:decl-index=0:visual-constraint="10,10"
