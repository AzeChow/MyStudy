package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.ImpExpGoodsBill;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgImpExpGoodsBill extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JTableListModel tableModelImg = null;
	private JTableListModel tableModelExg = null;
	private File txtFile = null;
	private JButton jButton1 = null;
	private ContractExeAction            encAction                 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JPanel jPanel3 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private ButtonGroup					group				= new ButtonGroup();
	private Boolean isTcustoms = null;
	private JLabel jLabel = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JLabel jLabel1 = null;
	/**
	 * This is the default constructor
	 */
	public DgImpExpGoodsBill() {
		super();
		this.encAction = (ContractExeAction) CommonVars.getApplicationContext()
		       .getBean("contractExeAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(720, 479);
		this.setContentPane(getJPanel());
		this.setTitle("进出货单");
		jRadioButton.setSelected(true);
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton2);
		//显示未生效数据
		Date beginDate = CommonVars.dateToStandDate(new Date());
		Date endDate = CommonVars.dateToStandDate(new Date());
		isTcustoms = new Boolean(false);
		inittable(isTcustoms,beginDate,endDate);
	}
	
	private void inittable(Boolean isTcustoms,Date beginDate,Date endDate){
		List list = encAction.findImpExpGoodsBillAll(new Request(CommonVars.getCurrUser()),new Boolean(true),isTcustoms,beginDate,endDate);
		initTableImg(list);
		list = encAction.findImpExpGoodsBillAll(new Request(CommonVars.getCurrUser()),new Boolean(false),isTcustoms,beginDate,endDate);
		initTableExg(list);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton2());			
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton3());
			jToolBar.add(getJPanel3());
			
			
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("导入文件(E)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_E);
			jButton.setForeground(java.awt.Color.blue);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] {"xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImpExpGoodsBill.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					if (jTabbedPane.getSelectedIndex() == 0){//进口
					    new inputDataImport(fileChooser).start();
					} else {//出口
						new inputDataExport(fileChooser).start();
					}
					
				}
			});
		}
		return jButton;
	}
	
	/**
	 * 进口资料导入
	 * @author abcefg
	 *
	 */
	class inputDataImport extends Thread{
		private JFileChooser fileChooser;
		public inputDataImport(JFileChooser fileChooser){
			this.fileChooser = fileChooser;
		}
		public void run(){
			 try {
	            CommonProgress.showProgressDialog();
	            CommonProgress.setMessage("系统正在导入数据，请稍后...");
	            txtFile = fileChooser.getSelectedFile();
				if (txtFile == null) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(
							DgImpExpGoodsBill.this,
							"请选择要导入的文件", "提示", 0);					
					return;
				}
				if (!txtFile.exists()) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(
							DgImpExpGoodsBill.this,
							"你选择的文件不存在", "提示", 0);
					return;
				}
				ArrayList<ImpExpGoodsBill> list = new ArrayList<ImpExpGoodsBill>();
				try {					
					if (txtFile.isDirectory() == true) {
						CommonProgress.closeProgressDialog();
						return;
					}
					List krLs = encAction.findDistinctImpExpGoodsBill(new Request(CommonVars.getCurrUser()));
					InputStream is = null;
					String[] strs = null;							
					try {
						is = new FileInputStream(txtFile);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						int rowCount = rs.getRows();
						for (int i = 0; i < rowCount; i++) {
							if (i == 0){ //第一行为标题
								continue;
							}
							Cell[] cell = rs.getRow(i);
							if (cell[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cell.length];
							for (int j = 0; j < cell.length; j++) {
								strs[j] =CommonClientBig5GBConverter.getInstance().big5ConvertToGB(cell[j].getContents().trim());
							}
							String krNo = String.valueOf(strs[5]);
							if (krLs.contains(krNo)){
								CommonProgress.closeProgressDialog();
								JOptionPane.showMessageDialog(DgImpExpGoodsBill.this,
										"对不起，该文件重复导入！\nKR#:"+krNo, "提示", 0);
								return;
							}
							convertToFileDataImport(list, strs);									
						}
						
					} catch (Exception ex) {
						CommonProgress.closeProgressDialog();
						ex.printStackTrace();
					}
					try {
						is.close();
					} catch (IOException e2) {
						CommonProgress.closeProgressDialog();
						e2.printStackTrace();
					}
				} catch (Exception e3) {
					CommonProgress.closeProgressDialog();
					e3.printStackTrace();
				}
				encAction.saveImpExpGoodsBillList(new Request(CommonVars.getCurrUser()),list);
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
				inittable(isTcustoms,beginDate,endDate);
				CommonProgress.closeProgressDialog();
	            
	            
			 } catch (Exception e) {
		          CommonProgress.closeProgressDialog();
		          JOptionPane.showMessageDialog(DgImpExpGoodsBill.this,
		                  "导入数据失败：！" + e.getMessage(), "提示", 2);    
			 } finally {	
			 }
	   }
	}

	private void convertToFileDataImport(ArrayList<ImpExpGoodsBill> list, String[] strs) {
		ImpExpGoodsBill obj = new ImpExpGoodsBill();		
		obj.setCountry(String.valueOf(strs[4]));//原产国
		obj.setContractNo(String.valueOf(strs[1]));//手册号
		obj.setSeqNum(Integer.valueOf(strs[2]));//序号
		obj.setNum(Double.valueOf(strs[3]==null? "0.0" : strs[3]));//海关数量
		obj.setKrNo(String.valueOf(strs[5]));//KR#
		obj.setCatNo(String.valueOf(strs[0])); //车牌号码
		obj.setCustomsNo(String.valueOf(strs[6]));//报关单号	
		
		obj.setImportDate(CommonVars.dateToStandDate(new Date()));
		obj.setIsTcustoms(new Boolean(false));
		obj.setIsLj(new Boolean(true));
		list.add(obj);
	}

	
	
	
	private void convertToFileDataExport(ArrayList<ImpExpGoodsBill> list, String[] strs) {
		ImpExpGoodsBill obj = new ImpExpGoodsBill();		
		obj.setCatNo(String.valueOf(strs[0])); //车牌号码
		obj.setContractNo(String.valueOf(strs[1]));//合同号
		obj.setSeqNum(Integer.valueOf(strs[2]));//序号
		obj.setNum(Double.valueOf(strs[3]==null? "0.0" : strs[3]));//海关数量
		obj.setCommodityNum(Integer.valueOf(strs[4]));//件数
		obj.setWrapType(String.valueOf(strs[5]));//包装种类
		obj.setGrossWeight(Double.valueOf(strs[6]==null? "0.0" : strs[6]));//毛重
		obj.setNetWeight(Double.valueOf(strs[7]==null? "0.0" : strs[7]));//净重		
		obj.setCountryOfLoadingOrUnloading(String.valueOf(strs[8]));//运抵国/出口国
		obj.setTradeMode(String.valueOf(strs[9]));//贸易方式
		obj.setMemo1(String.valueOf(strs[10]));//备注1
		obj.setMemo2(String.valueOf(strs[11]));//备注2
        obj.setCustoms(String.valueOf(strs[12]));//出口口岸
		obj.setPortLoad(String.valueOf(strs[13]));//指运港
		obj.setAuthorizeFile(String.valueOf(strs[14]));//批准文号
		obj.setContainerNum(String.valueOf(strs[15]));//集装箱号
		obj.setConveyance(String.valueOf(strs[16]));//运输工具名称
		obj.setTransferMode(String.valueOf(strs[17]));//运输方式
		obj.setDomeConveyance(String.valueOf(strs[18]));//境内运输工具编号		
		
		obj.setCountry(String.valueOf(strs[19]));//目的国
		obj.setDeclarationCustoms(String.valueOf(strs[20]));//报送海关
		
		obj.setImportDate(CommonVars.dateToStandDate(new Date()));
		obj.setIsTcustoms(new Boolean(false));

		obj.setIsLj(new Boolean(false));
		list.add(obj);
	}
	
	
	
	
	/**
	 * 出口资料导入
	 * @author abcefg
	 *
	 */
	class inputDataExport extends Thread{
		private JFileChooser fileChooser;
		public inputDataExport(JFileChooser fileChooser){
			this.fileChooser = fileChooser;
		}
		public void run(){
			 try {
	            CommonProgress.showProgressDialog();
	            CommonProgress.setMessage("系统正在导入数据，请稍后...");
	            txtFile = fileChooser.getSelectedFile();
				if (txtFile == null) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(
							DgImpExpGoodsBill.this,
							"请选择要导入的文件", "提示", 0);					
					return;
				}
				if (!txtFile.exists()) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(
							DgImpExpGoodsBill.this,
							"你选择的文件不存在", "提示", 0);
					return;
				}
				ArrayList<ImpExpGoodsBill> list = new ArrayList<ImpExpGoodsBill>();
				try {					
					if (txtFile.isDirectory() == true) {
						CommonProgress.closeProgressDialog();
						return;
					}
					//List krLs = encAction.findDistinctImpExpGoodsBill(new Request(CommonVars.getCurrUser()));
					InputStream is = null;
					String[] strs = null;							
					try {
						is = new FileInputStream(txtFile);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						//获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						int rowCount = rs.getRows();
						for (int i = 0; i < rowCount; i++) {
							if (i == 0){ //第一行为标题
								continue;
							}
							Cell[] cell = rs.getRow(i);
							if (cell[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cell.length];
							for (int j = 0; j < cell.length; j++) {
								strs[j] =CommonClientBig5GBConverter.getInstance().big5ConvertToGB(cell[j].getContents().trim());
							}
							//String krNo = String.valueOf(strs[5]);
							/*if (krLs.contains(krNo)){
								CommonProgress.closeProgressDialog();
								JOptionPane.showMessageDialog(DgImpExpGoodsBill.this,
										"对不起，该文件重复导入！\nKR#:"+krNo, "提示", 0);
								return;
							}*/
							convertToFileDataExport(list, strs);									
						}
						
					} catch (Exception ex) {
						CommonProgress.closeProgressDialog();
						ex.printStackTrace();
					}
					try {
						is.close();
					} catch (IOException e2) {
						CommonProgress.closeProgressDialog();
						e2.printStackTrace();
					}
				} catch (Exception e3) {
					CommonProgress.closeProgressDialog();
					e3.printStackTrace();
				}
				encAction.saveImpExpGoodsBillList(new Request(CommonVars.getCurrUser()),list);
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
				inittable(isTcustoms,beginDate,endDate);
				CommonProgress.closeProgressDialog();
	            
	            
			 } catch (Exception e) {
		          CommonProgress.closeProgressDialog();
		          JOptionPane.showMessageDialog(DgImpExpGoodsBill.this,
		                  "导入数据失败：！" + e.getMessage(), "提示", 2);    
			 } finally {	
			 }
	   }
	}

	
	
	
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("进口", null, getJPanel1(), null);
			jTabbedPane.addTab("出口", null, getJPanel2(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}
	
	
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("手册号", "contractNo", 100));
						list.add(addColumn("序号", "seqNum", 60));
						list.add(addColumn("原产国", "country", 80));
						list.add(addColumn("数量", "num", 80));
						list.add(addColumn("KR#", "krNo", 80));
						list.add(addColumn("车牌号码", "catNo", 80));
						list.add(addColumn("报关单号", "customsNo", 100));
						list.add(addColumn("导入时间", "importDate", 100));
						list.add(addColumn("是否转报关单", "isTcustoms", 80));
						list.add(addColumn("已转报关单流水号", "serialNumber", 80));
						return list;
					}
				});
	}

	
	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("提运单号/大陆车牌", "catNo", 100));
						list.add(addColumn("合同号", "contractNo", 60));
						list.add(addColumn("项号/海关序号", "seqNum", 80));
						list.add(addColumn("海关数量", "num", 80));
						list.add(addColumn("件数/箱数", "commodityNum", 80));
						list.add(addColumn("包装种类", "wrapType", 80));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("运抵国/出口国", "countryOfLoadingOrUnloading", 100));
						list.add(addColumn("贸易方式", "tradeMode", 100));
						list.add(addColumn("备注1", "memo1", 100));
						list.add(addColumn("备注2", "memo2", 100));
						list.add(addColumn("出口口岸", "customs", 100));
						list.add(addColumn("指运港", "portLoad", 100));
						list.add(addColumn("批准文号", "authorizeFile", 100));
						list.add(addColumn("集装箱号", "containerNum", 100));
						list.add(addColumn("运输工具名称", "conveyance", 100));
						list.add(addColumn("运输方式", "transferMode", 100));
						list.add(addColumn("境内运输工具编号", "domeConveyance", 100));				
						
						list.add(addColumn("最终目的国", "country", 100));
						list.add(addColumn("报送海关", "declarationCustoms", 100));
						
						list.add(addColumn("导入时间", "importDate", 100));
						list.add(addColumn("是否转报关单", "isTcustoms", 80));
						list.add(addColumn("已转报关单流水号", "serialNumber", 80));
						return list;
					}
				});
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
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("转报关单(T)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_T);
			jButton1.setForeground(java.awt.Color.blue);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeCustomsFromImpExpGoods dg = new DgMakeCustomsFromImpExpGoods();
					if (jTabbedPane.getSelectedIndex() == 0){
					    dg.setLj(true);
					} else {
						dg.setLj(false);
					}
					dg.setVisible(true);
					Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
					Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
					inittable(isTcustoms,beginDate,endDate);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除(D)");
			jButton2.setMnemonic(java.awt.event.KeyEvent.VK_D);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgImpExpGoodsBill.this,
							"确定要删除您选中的数据吗?", "确认", 0) != 0) {
						return;
					}
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0){
					    list = tableModelImg.getCurrentRows();
					    encAction.DeleteImpExpGoodsBillList(new Request(CommonVars.getCurrUser()),list);
					    tableModelImg.deleteRows(list);
					} else {
						list = tableModelExg.getCurrentRows();
						encAction.DeleteImpExpGoodsBillList(new Request(CommonVars.getCurrUser()),list);
						tableModelExg.deleteRows(list);
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
			jButton3.setText("退出(Q)");
			jButton3.setMnemonic(java.awt.event.KeyEvent.VK_Q);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImpExpGoodsBill.this.dispose();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(331,5,17,19));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(182,6,56,20));
			jLabel.setText("导入日期");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getJRadioButton(), null);
			jPanel3.add(getJRadioButton1(), null);
			jPanel3.add(getJRadioButton2(), null);
			jPanel3.add(jLabel, null);
			jPanel3.add(getJCalendarComboBox(), null);
			jPanel3.add(getJCalendarComboBox1(), null);
			jPanel3.add(jLabel1, null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(9,5,59,20));
			jRadioButton.setText("未转");
			jRadioButton.addActionListener(new RadioActionListner());
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new java.awt.Rectangle(68,4,61,22));
			jRadioButton1.setText("已转");
			jRadioButton1.addActionListener(new RadioActionListner());
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new java.awt.Rectangle(126,5,56,21));
			jRadioButton2.setText("全部");
			jRadioButton2.addActionListener(new RadioActionListner());
		}
		return jRadioButton2;
	}
	
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {			
			if (jRadioButton.isSelected()) {
				isTcustoms = new Boolean(false);
			} else if (jRadioButton1.isSelected()) {
				isTcustoms = new Boolean(true);
			} else if (jRadioButton2.isSelected()) {
				isTcustoms = null;
			}
			Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
			Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
			inittable(isTcustoms,beginDate,endDate);
		}
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new java.awt.Rectangle(237,5,90,21));
			jCalendarComboBox.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
					Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
					inittable(isTcustoms,beginDate,endDate);
				}
			});
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(new java.awt.Rectangle(345,5,86,21));
			jCalendarComboBox1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox.getDate());
					Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
					inittable(isTcustoms,beginDate,endDate);
				}
			});
		}
		return jCalendarComboBox1;
	}
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
