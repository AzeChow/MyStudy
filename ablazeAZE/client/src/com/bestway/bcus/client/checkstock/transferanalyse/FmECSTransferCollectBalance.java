package com.bestway.bcus.client.checkstock.transferanalyse;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStockAnalyse;
import com.bestway.bcus.checkstock.entity.ECSTransferAnalyse;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockBuyImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutFactoryImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockOutSendExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockProcessImg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockTravelingExg;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockTravelingImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;
import com.bestway.util.RegexUtil;
/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class FmECSTransferCollectBalance extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNump;
	private JTextField tfNumb;
	private JButton btnChoose;
	private ECSSection ecsSection;
	private Request request;
	private JLabel label;
	private JTextField tfSeqNum;
	/***关联列***/
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	public ECSSection getEcsSection() {
		return ecsSection;
	}

	public void setEcsSection(ECSSection ecsSection) {
		this.ecsSection = ecsSection;
	}
	/**
	 * This is the default constructor
	 */
	public FmECSTransferCollectBalance() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkTransferCollectBalance(request);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(834, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("结转汇总情况表");
		initTable(Collections.EMPTY_LIST);
		initBtnStatus(0);
	}

	public void initTable(List list){
		list = (list == null ? new ArrayList() : list);
		initTable(tableModel,table,list);
	}
	// 填充盘点--料件--下
	public JTableListModel initTable(JTableListModel tableModel,
			JTable jTable, final List list) {		
		this.tableModel = new JTableListModel(jTable, list,
			new JTableListModelAdapter() {
				public List InitColumns() {
					List list = new Vector();						
					list.add(addColumn("备案序号", "seqNum", 110));
					list.add(addColumn("报关商品名称", "hsName", 100));
					list.add(addColumn("报关商品规格", "hsSpec", 100));
					list.add(addColumn("计量单位", "hsUnit", 50));
					
					list.add(addColumn("已送货未转厂数(A)", "unTransHadSendNum", 110));
					list.add(addColumn("已转厂未送货数(B)", "unSendHadTransNum", 110));
					
					list.add(addColumn("已收货未转厂数(C)", "unTransHadReceiveNum", 110));
					list.add(addColumn("已转厂未收货数(D)", "unReceiveHadTransNum", 110));
					
					list.add(addColumn("结转差额(A+D-B-C)", "diffAmount", 120));
					return list;
				}
		});
		TableColumnModel tcm = jTable.getColumnModel();
		tcm.getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘已送货未转厂数(A)’列可关联【成品结转情况表】";}
		});
		tcm.getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘已转厂未送货数(B)’列可关联【成品结转情况表】";}
		});		
		tcm.getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘已收货未转厂数(C)’列可关联【料件结转情况表】";}
		});
		tcm.getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘已转厂未收货数(D)’列可关联【料件结转情况表】";}
		});
		this.linkedCols.clear();
		linkedCols.add(tcm.getColumn(5));		
		linkedCols.add(tcm.getColumn(6));
		linkedCols.add(tcm.getColumn(7));
		linkedCols.add(tcm.getColumn(8));
		return tableModel;
	}




	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(ecsSection.getVerificationNo().toString());
		tfNumb.setText(ecsSection.getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(ecsSection.getBeginDate()));
		tfEnd.setText(dFormat.format(ecsSection.getEndDate()));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jLabel3.setText("\u76D8\u70B9\u6838\u67E5\u6279\u6B21\u9009\u62E9");
			jLabel3.setBounds(5, 5, 99, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(413, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(545, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			
			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(283, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfEnd());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfNumb());
			jPanel2.add(getBtnChoose());
		}
		return jPanel2;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}


	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	
	
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnCalculate());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClean());
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getBtnHistroy() {
		if ( btnHistroy == null) {
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(ecsSection == null){
						JOptionPane.showMessageDialog(FmECSTransferCollectBalance.this, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					FmECSTransferCollectBalance.this.doFindStars();
				}
			});
		}
		return btnHistroy;
	}	


	/**
	 * 
	 */
	private void doFindStars(){
		new AsynSwingWorker<List<?>>() {
			protected java.util.List<?> submit() {
				CommonProgress.showProgressDialog(FmECSTransferCollectBalance.this);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				btnCalculate.setEnabled(false);
				String seqNum = tfSeqNum.getText().trim();
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.findECSTransferAnalyses(request, ecsSection,(seqNum.isEmpty() ? null:Integer.parseInt(seqNum)));
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(java.util.List<?> result) {
				initTable(result);
			}
		}.doWork();
	}
	/**
	 * 
	 * @return
	 */
	private JButton getBtnCalculate() {
		if ( btnCalculate == null) {
			btnCalculate = new JButton("  \u8BA1\u7B97  ");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FmECSTransferCollectBalance.this.doConvertStars();
				}
			});
		}
		return btnCalculate;
	}
	
	/**
	 * 
	 */
	private void doConvertStars(){
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				CommonProgress.showProgressDialog(FmECSTransferCollectBalance.this);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.transferAnalyse(request, ecsSection);
				}finally{					
					CommonProgress.closeProgressDialog();
					initBtnStatus(2);
				}
			}
			
			@Override
			protected void done() {
				try {
					initTable(this.get());
				} catch (Exception e2) {
					DgErrorMessage dgErrorMessage = new DgErrorMessage();
					String[] strs = e2.getMessage().split(",");
					List<String> list = new ArrayList<String>();
						for(String str:strs){
							list.add(str);
						}
					dgErrorMessage.initTable(list);
					CommonProgress.closeProgressDialog();
					dgErrorMessage.setVisible(true);
				}
			};
			
		}.execute();
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getBtnExport() {
		if ( btnExport == null) {
			btnExport = new JButton("  \u5BFC\u51FAExcel  ");
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tableModel = (JTableListModel) table.getModel();
					if ( tableModel != null && tableModel.getRowCount() > 0 ) {
						tableModel.getMiSaveTableListToExcel().doClick();
					}
				}
			});
		}
		return btnExport;
	}
	
	/**
	 * 
	 * @return
	 */
	private JButton getBtnClean() {
		if ( btnClean == null) {
			btnClean = new JButton("  \u6E05\u7A7A\u5F53\u524D\u6570\u636E  ");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(table.getModel().getRowCount()<=0){
						return;
					}
					if(ecsSection == null){
						JOptionPane.showMessageDialog(FmECSTransferCollectBalance.this, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
					return;
					}
					if(JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)")){
						try{
							//....
							ecsCheckStockAction.deleteECSTransferAnalyses(request, ecsSection);
							initTable(Collections.EMPTY_LIST);
						}catch ( Exception ex ) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(FmECSTransferCollectBalance.this, "未能成功删除！","警告",JOptionPane.WARNING_MESSAGE);
						}
						
					}else{
						return;
					}
				}
			});
		}
		return btnClean;
	}
	
	private JButton getBtnClose() {
		if ( btnClose == null) {
			btnClose = new JButton("  \u5173\u95ED  ");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FmECSTransferCollectBalance.this.dispose();
				}
			});
		}
		return btnClose;
	}
	
	
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	
	
	
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				/**
				 * 双击穿透报表
				 */
				public void mouseClicked(MouseEvent e) {					
					if(e.getClickCount() ==2){
						int colIdx = table.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols, table.getColumnModel().getColumn(colIdx));						 
						if(idx < 0 )	return;
						ECSTransferAnalyse aly = (ECSTransferAnalyse)tableModel.getCurrentRow();
						switch(idx){
							case 0:	//成品结转表																
							case 1: //成品结转表
								FmECSTransferExgBalance fm1 = new FmECSTransferExgBalance();
								fm1.showData(aly.getSection(),aly.getSeqNum());
								ShowFormControl.refreshInteralForm(fm1);
								break;
							case 2:	//料件结转表								
							case 3:	//料件结转表
								FmECSTransferImgBalance fm2 = new FmECSTransferImgBalance();
								fm2.showData(aly.getSection(),aly.getSeqNum());
								ShowFormControl.refreshInteralForm(fm2);
								break;
						}
					}
				}
			});
		}
		return table;
	}
	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(595, 4, 78, 21);
			tfEnd.setColumns(10);
		}
		return tfEnd;
	}
	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setBounds(465, 4, 78, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}
	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(359, 4, 52, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}
	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(225, 4, 52, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}
	
	
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("New button");
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						ecsSection = dgECSSection.getSection();
						fillValue();
						initBtnStatus(1);
					}
				}
			});
			btnChoose.setBounds(104, 3, 35, 23);
		}
		return btnChoose;
	}
	
	
	/**
	 * 
	 * @param i
	 */
	private void initBtnStatus(int i) {
		switch (i) {
		case 0:
			btnCalculate.setEnabled(false);
			btnClean.setEnabled(false);
			btnExport.setEnabled(false);
			btnHistroy.setEnabled(false);
			break;
		case 1:
			btnHistroy.setEnabled(true);
			btnCalculate.setEnabled(true);
			break;
		case 2:
			btnCalculate.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnHistroy.setEnabled(true);
			break;
		default:
			break;
		}
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备案序号：");
		}
		return label;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setColumns(10);
			tfSeqNum.setPreferredSize(new Dimension(80,25));
			tfSeqNum.setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					char c = str.charAt(str.length()-1);
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}
				}
			});
		}
		return tfSeqNum;
	}
	
	/**
	 * 按照条件查询显示数据
	 * @param section2
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {		
		this.ecsSection = section;
		fillValue();
		if(seqNum != null)
			getTfSeqNum().setText(String.valueOf(seqNum));
		doFindStars();
	}
}
