/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountImg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.DgImpCustomsRecord;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

import java.awt.Color;

/**
 * @author xc
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmECSCustomsCountImg extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JTextField tfBeginDate = null;
	private JLabel jLabel5 = null;
	private JTextField tfEndDate = null;
	private JButton btnCompute = null;
	private JButton btnExport = null;
	private JPanel jPanel7 = null;
	private JToolBar jToolBar = null;
	private JTable tbBgImg = null;
	private JScrollPane jScrollPane = null;
	private JButton btnClose = null;
	private JTextField tfSection = null;
	private JTableListModel tableModel = null;	
	private JButton btnQry;
	private JButton btnDel;
	private JLabel label;
	private JButton btnSel;
	private JLabel label_1;
	private JTextField tfCancelNum;
	private ECSCheckStockAction checkStockAction = null;
	/**
	 * 盘点核查批次
	 */
	private ECSSection section = null;
	private JLabel label_2;
	private JTextField tfSeqNum;	
	private static final String[] linkedColNames = new String[]{"进口数量(B)","转厂数量(C)","余料转入(D)","余料转出(E)","退料数量(F)"};
	private JLabel lblNewLabel;

	/**
	 * This is the default constructor
	 */
	public FmECSCustomsCountImg() {
		super();
		checkStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkCustomsCountImg(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(939, 290);
		this.setTitle("料件情况统计表");
		this.setContentPane(getJContentPane());
		getJContentPane().add(getJPanel7(), BorderLayout.CENTER);
		getJContentPane().add(getJPanel2(), BorderLayout.NORTH);
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
			jLabel3.setText("盘点核查批次:");
			jLabel4.setText("盘点开始日期:");
			jLabel5.setText("盘点结束日期:");
			jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			jPanel2.setPreferredSize(new Dimension(1,30));
			jPanel2.add(getLabel());
			jPanel2.add(getBtnSel());
			jPanel2.add(getLabel_1());
			jPanel2.add(getTfCancelNum());
			jPanel2.add(jLabel3);
			jPanel2.add(getTfSection());
			jPanel2.add(jLabel4);
			jPanel2.add(getTfBeginDate());
			jPanel2.add(jLabel5);
			jPanel2.add(getTfEndDate());
		}
		return jPanel2;
	}

	/**
	 * This method initializes JTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JTextField
	 */
	private JTextField getTfBeginDate() {
		if (tfBeginDate == null) {
			tfBeginDate = new JTextField();
			tfBeginDate.setPreferredSize(new Dimension(78, 21));
			tfBeginDate.setEnabled(false);
		}
		return tfBeginDate;
	}

	/**
	 * This method initializes JTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JTextField
	 */
	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setPreferredSize(new Dimension(78, 21));
			tfEndDate.setEnabled(false);
		}
		return tfEndDate;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel7.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			jToolBar.add(getLabel_2());
			jToolBar.add(getTfSeqNum());
			jToolBar.add(getBtnQry());
			jToolBar.add(getBtnCompute());
			jToolBar.add(getBtnExport());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnClose());
			jToolBar.add(getLblNewLabel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes tbBgImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBgImg() {
		if (tbBgImg == null) {
			tbBgImg = new JTable();			
			tbBgImg.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {					
					if(e.getClickCount() == 2){
						int colIndex = tbBgImg.columnAtPoint(e.getPoint());
						if(colIndex < 0)
							return;
						int index = CommonUtils.indexOf(linkedColNames, tbBgImg.getColumnName(colIndex));
						if(index < 0)
							return;
						ECSCustomsCountImg img = (ECSCustomsCountImg)tableModel.getCurrentRow();
						String impExpType = null;
						switch(index){
							case 0:
								impExpType = String.valueOf(ImpExpType.DIRECT_IMPORT);
								break;
							case 1:
								impExpType = String.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT);
								break;
							case 2:
								impExpType = String.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT);
								break;
							case 3:
								impExpType = String.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT);
								break;
							case 4:
								impExpType = String.valueOf(ImpExpType.BACK_MATERIEL_EXPORT);
								break;
						}
						showECSDeclarationCommInfoImg(img.getSeqNum(),impExpType,img.getSection(),true);						
					}
					super.mouseClicked(e);
				}
			});
			initTable(null);
		}
		return tbBgImg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBgImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSection() {
		if (tfSection == null) {
			tfSection = new JTextField();
			tfSection.setPreferredSize(new Dimension(44, 21));
			tfSection.setEditable(false);
		}
		return tfSection;
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
	
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton();
			btnExport.setText("导出Excel");
			btnExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tableModel.getMiSaveAllToExcel().doClick();
				}
			});
		}
		return btnExport;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmECSCustomsCountImg.this.dispose();
				}
			});
		}
		return btnClose;
	}
	/**
	 * 查看历史数据
	 * @return
	 */
	private JButton getBtnQry() {
		if (btnQry == null) {
			btnQry = new JButton("查看历史数据");
			btnQry.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请选择盘点核查批次！","提示",JOptionPane.INFORMATION_MESSAGE);						
						return;
					}
					new AsynSwingWorker<List<ECSCustomsCountImg>>() {
						Request request = new Request(CommonVars.getCurrUser());
						protected List<ECSCustomsCountImg> submit() {				
							long t = System.currentTimeMillis();
							try{
								setButtonState(false);
								CommonProgress.showProgressDialog(FmECSCustomsCountImg.this);
								CommonProgress.setMessage("正在加载数据，请稍候...");
								Integer seqNum = null;
								if(StringUtils.isNotBlank(tfSeqNum.getText().trim()))
									seqNum = NumberUtils.toInt(tfSeqNum.getText().trim());
								return checkStockAction.findECSCustomsImgBySection(request, section,seqNum);
							}finally{							
								System.out.println("耗时："+(System.currentTimeMillis()-t)+"毫秒");
								CommonProgress.closeProgressDialog();
								setButtonState(true);
							}							
						}						
						protected void success(List<ECSCustomsCountImg> list) {
							initTable(list);						
						};						
					}.doWork();
				}
			});
		}
		return btnQry;
	}
	/**
	 * 清空当前数据
	 * @return
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton("清空当前数据");
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请选择盘点核查批次！","提示",JOptionPane.INFORMATION_MESSAGE);						
						return;
					}
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(FmECSCustomsCountImg.this, "确定删除本批次数据","提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker<Object>() {
							Request request = new Request(CommonVars.getCurrUser());
							protected Object submit() {
								try{
									setButtonState(false);
									CommonProgress.showProgressDialog(FmECSCustomsCountImg.this);
									CommonProgress.setMessage("正在删除数据，请稍候...");
									checkStockAction.deleteECSCustomsImgBySection(request, section);
								}finally{
									CommonProgress.closeProgressDialog();
									setButtonState(true);
								}
								return null;
							}						
							protected void success(Object o) {
								tableModel.setList(Collections.EMPTY_LIST);
							};
						}.doWork();
					}
				}
			});
		}
		return btnDel;
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCompute() {
		if (btnCompute == null) {
			btnCompute = new JButton();
			btnCompute.setText("计算");
			btnCompute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final Request req = new Request(CommonVars.getCurrUser());
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请选择盘点核查批次！","提示",JOptionPane.INFORMATION_MESSAGE);						
						return;
					}															
					if(checkStockAction.isExistECSCustomsImgBySection(req, section)){
						CommonProgress.closeProgressDialog();
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(FmECSCustomsCountImg.this, "已存在本批次数据，确定删除本批次数据，重新计算?","提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
					}						
					new AsynSwingWorker<List<ECSCustomsCountImg>>(){
						long t = 0L;
						protected List<ECSCustomsCountImg> submit() {
							try{	
								t = System.currentTimeMillis();
								req.setTaskId(CommonStepProgress.getExeTaskId());
								CommonStepProgress.showStepProgressDialog(req.getTaskId());
								CommonStepProgress.setStepMessage("正在发送计算请求,请稍等...");
								setButtonState(false);
								return checkStockAction.computeECSCustomsImgBySection(req,section);
							}finally{
								setButtonState(true);
								CommonStepProgress.closeStepProgressDialog();
							}
						}
						protected void success(java.util.List<ECSCustomsCountImg> e) {
							JOptionPane.showMessageDialog(FmECSCustomsCountImg.this,"完成统计！耗时:"+(System.currentTimeMillis()-t)+"毫秒","提示", JOptionPane.INFORMATION_MESSAGE);
							initTable(e);
						};
					}.doWork();
				}
			});
		}
		return btnCompute;
	}
	/**
	 * 选择批次
	 * @return
	 */
	private JButton getBtnSel() {
		if (btnSel == null) {
			btnSel = new JButton("...");
			btnSel.setPreferredSize(new Dimension(29, 23));
			btnSel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dg = new DgECSSectionSel();
					dg.setVisible(true);
					if(dg.isOk()){						
						setSection(dg.getSection());
					}
				}
			});
		}
		return btnSel;
	}
	/**
	 * 设置批次
	 * @param section
	 */
	private void setSection(ECSSection section){
		this.section = section;
		if(section != null){
			//填充界面批次数据
			tfCancelNum.setText(section.getCancelOwnerHead().getCancelTimes());
			tfSection.setText(CommonUtils.getStringExceptNull(section.getVerificationNo()));
			tfBeginDate.setText(CommonUtils.getDate(section.getBeginDate(),"yyyy-MM-dd"));
			tfEndDate.setText(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd"));
			initTable(Collections.EMPTY_LIST);
		}
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("盘点核查批次选择：");
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("核销报核次数:");
		}
		return label_1;
	}
	private JTextField getTfCancelNum() {
		if (tfCancelNum == null) {
			tfCancelNum = new JTextField();
			tfCancelNum.setPreferredSize(new Dimension(44, 21));
			tfCancelNum.setEditable(false);
		}
		return tfCancelNum;
	}
	
//	public void showCustomsRecord(Integer seqNum, String impExpType,ECSSection sectoin,boolean impExpFlag) {
//		int iseffect = CustomsDeclarationState.EFFECTIVED;
//		DgImpCustomsRecord	dg = new DgImpCustomsRecord();
//		dg.showData(seqNum, null, impExpType, section.getBeginDate(), section.getEndDate(), Boolean.TRUE,iseffect, false, null,impExpFlag);
//		ShowFormControl.refreshInteralForm(dg);
//	}
	
	/**
	 * 穿透式查询
	 * @param seqNum
	 * @param impExpType
	 * @param sectoin
	 * @param impExpFlag
	 */
	public void showECSDeclarationCommInfoImg(Integer seqNum, String impExpType,ECSSection sectoin,boolean impExpFlag){
		FmECSDeclarationCommInfoImg fm = new FmECSDeclarationCommInfoImg();
		fm.showData(sectoin,seqNum);
		ShowFormControl.refreshInteralForm(fm);
	}
	
	
	/**
	 * 初始化表格模型
	 */
	private void initTable(List list) {
		if(list == null)
			list = Collections.EMPTY_LIST;
		if(tableModel == null){
			tableModel = new JTableListModel(tbBgImg, list,new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> list = new ArrayList<JTableListColumn>();
					list.add(addColumn("账册编号", "emsNo", 100));
					list.add(addColumn("备案序号", "seqNum", 50));
					list.add(addColumn("料件名称", "commName", 120));
					list.add(addColumn("型号规格", "commSpec", 140));
					list.add(addColumn("计量单位", "unit", 60));
					list.add(addColumn("期初数量(A)", "qcAmount", 90));
					list.add(addColumn(linkedColNames[0], "directImportAmount", 90));
					list.add(addColumn(linkedColNames[1], "transferExportAmount", 90));
					list.add(addColumn(linkedColNames[2], "remainForwardImportAmount", 90));
					list.add(addColumn(linkedColNames[3], "remainForwardExportAmount", 90));
					list.add(addColumn(linkedColNames[4], "backMaterExportAmount", 90));
					list.add(addColumn("总数量(A+B+C+D-E-F)", "totalImportAmount", 150));
					return list;
				}
			},ListSelectionModel.SINGLE_SELECTION);
			tbBgImg.setModel(tableModel);
		}else{
			tableModel.setList(list);			
		}
		for(int i = 7; i <= 11 ;i++){			 
			setColumnTooltip(tbBgImg, i, "双击‘"+tableModel.getColumnName(i)+"’可关联【料件明细表】");
		}
	}
	
	/**
	 * 为表格的某个列设置提示（不包含表头）
	 * @param table
	 * @param colIndex
	 * @param tipText
	 */
	private void setColumnTooltip(JTable table, int colIndex, String tipText) {
		TableColumn column = table.getColumnModel().getColumn(colIndex);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText(tipText);
		column.setCellRenderer(renderer);
	}
	
	private void setButtonState(boolean enable){
		btnCompute.setEnabled(enable);		
		btnDel.setEnabled(enable);
		btnExport.setEnabled(enable);
		btnQry.setEnabled(enable);
		btnSel.setEnabled(enable);
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("备案序号：");
		}
		return label_2;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
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
	 * 显示数据
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section,Integer seqNum){
		setSection(section);
		tfSeqNum.setText(seqNum == null ? "" : String.valueOf(seqNum));
		btnQry.doClick();
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("     （期初数量来源于当次自用核销周期内的期初数量）");
			lblNewLabel.setForeground(Color.BLUE);
		}
		return lblNewLabel;
	}
}
