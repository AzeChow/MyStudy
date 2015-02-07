package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;

public class PnVFTransferDiffPtImg extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar tbar;
	private JScrollPane scrollPane;
	private JTable tbimg;	
	private JTableListModel tbmImg;
	private JLabel lbsection;
	private JButton btnimp;
	private JButton btnconvert;
	private JButton btnviewhis;
	private JButton btnclear;
	private VFVerificationAction vfAction = null;
	private JButton btnexcel;
	private JLabel label;
	private JTextField tfSeqNum;
	private FmVFTransferDiffImg parent;
	
	/**
	 * Create the panel.
	 */
	public PnVFTransferDiffPtImg(FmVFTransferDiffImg parent) {
		this.parent = parent;
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout(0, 0));
		this.add(getTbar(), BorderLayout.NORTH);
		this.add(getScrollPane(), BorderLayout.CENTER);
		initCompontents();
	}
	/**
	 * 初始化控件数据
	 */
	private void initCompontents(){
		initTbmimg(Collections.EMPTY_LIST);
	}

	private JToolBar getTbar() {
		if (tbar == null) {
			tbar = new JToolBar();
			FlowLayout fl_tbar = new FlowLayout();
			fl_tbar.setAlignment(FlowLayout.LEFT);
			fl_tbar.setVgap(0);
			fl_tbar.setHgap(0);
			tbar.setLayout(fl_tbar);
			tbar.add(getLbsection());
			tbar.add(parent.getCbsection());
			tbar.add(getLabel());
			tbar.add(getTfSeqNum());
			tbar.add(getBtnviewhis());
			tbar.add(getBtnimp());
			tbar.add(getBtnconvert());
			tbar.add(getBtnexcel());
			tbar.add(getBtnclear());
		}
		return tbar;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbimg());
		}
		return scrollPane;
	}
	private JTable getTbimg() {
		if (tbimg == null) {
			tbimg = new GroupableHeaderTable();
		}
		return tbimg;
	}
	/**
	 * 初始化表格模型
	 * @param list
	 * @return
	 */
	private JTableListModel initTbmimg(List list){
		if(tbmImg == null){
			tbmImg = new JTableListModel(tbimg, list, new JTableListModelAdapter() {				
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
					cols.add(new JTableListColumn("工厂料号","ptNo",100));
					cols.add(new JTableListColumn("工厂名称","ptName",100));
					cols.add(new JTableListColumn("工厂规格","ptSpec",100));			
					cols.add(new JTableListColumn("工厂单位","ptUnit",60));
					cols.add(new JTableListColumn("已收货未转厂","unTransferNum",100));
					cols.add(new JTableListColumn("已转厂未收货","unReceiveNum",100));
					cols.add(new JTableListColumn("归并序号","mergerNo",60));
					cols.add(new JTableListColumn("报关商品名称","hsName",100));
					cols.add(new JTableListColumn("报关商品规格","hsSpec",100));
					cols.add(new JTableListColumn("报关商品单位","hsUnit",100));
					cols.add(new JTableListColumn("折算已收货未转厂数","convertUnTransHadReceiveNum",120));
					cols.add(new JTableListColumn("折算已转厂未收货数","convertUnReceiveHadTransNum",120));
					cols.add(new JTableListColumn("折算系数","unitConvert",100));
					return cols;
				}
			});
			TableColumnModel cm = tbimg.getColumnModel();
			ColumnGroup g1 = new ColumnGroup("工厂资料");
			for(int i = 1 ;i < 7;i++){
				g1.add(cm.getColumn(i));
			}
			ColumnGroup g2 = new ColumnGroup("报关资料");
			for(int i = 7 ;i < cm.getColumnCount() - 1; i++){
				g2.add(cm.getColumn(i));
			}
			GroupableTableHeader header = (GroupableTableHeader) tbimg.getTableHeader();
			header.addColumnGroup(g1);
			header.addColumnGroup(g2);
		}else{
			tbmImg.setList(list);
		}
		return tbmImg;
	}
	private JLabel getLbsection() {
		if (lbsection == null) {
			lbsection = new JLabel("批次选择：");
		}
		return lbsection;
	}
	
	private JButton getBtnimp() {
		if (btnimp == null) {
			btnimp = new JButton("1.导入盘点数据");
			btnimp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(PnVFTransferDiffPtImg.this,"请先选择批次后，再导入数据！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					Request request = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffImgs(request, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffPtImg.this,"本批次已存在数据，导入数据需时先删除本次已导入的数据，确认继续导入？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
						vfAction.deleteVFTransferDiffImgs(new Request(CommonVars.getCurrUser()), section);
						initTbmimg(Collections.EMPTY_LIST);
					}

					DgImpDiffExgImg dg = new DgImpDiffExgImg(DgImpDiffExgImg.IMP_DIFFIMG, "导入料件结转差额表窗口",section);
					//设置导入数据数据变化回调函数
					dg.setDataChangeCallback(new Runnable() {						
						public void run() {
							loadTableData(null);							
						}
					});
					dg.setVisible(true);
				}
			});
		}
		return btnimp;
	}
	private JButton getBtnconvert() {
		if (btnconvert == null) {
			btnconvert = new JButton("2.折算海关数量");
			btnconvert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(PnVFTransferDiffPtImg.this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					setButtonState(false);
					new AsynSwingWorker<List<VFTransferDiffImg>>(){
						public List<VFTransferDiffImg> submit() {				
							Request req = new Request(CommonVars.getCurrUser());
							req.setTaskId(CommonProgress.getExeTaskId());
							CommonStepProgress.showStepProgressDialog(req.getTaskId());
							CommonStepProgress.setStepMessage("系统正在发送折算请求，请稍后...");
							long beginTime = System.currentTimeMillis();		
							try{
								return vfAction.convertVFTransferDiffImgToCustoms(req,section);
							}finally{
								System.out.println(" 折算报关数量完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
								setButtonState(true);
								CommonStepProgress.closeStepProgressDialog();
							}
						}
						protected void success(java.util.List<VFTransferDiffImg> e) {
							JOptionPane.showMessageDialog(PnVFTransferDiffPtImg.this,"折算完成!","提示",JOptionPane.INFORMATION_MESSAGE);
							initTbmimg(e);
						}
					}.doWork();
				}
			});
		}
		return btnconvert;
	}
	private JButton getBtnviewhis() {
		if (btnviewhis == null) {
			btnviewhis = new JButton("查询批次数据");
			btnviewhis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadTableData(btnviewhis);
				}
			});
		}
		return btnviewhis;
	}
	private JButton getBtnclear() {
		if (btnclear == null) {
			btnclear = new JButton("清空当前数据");
			btnclear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(PnVFTransferDiffPtImg.this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(PnVFTransferDiffPtImg.this, "确定删除本批次数据","提示",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker() {
							protected Object submit() {
								try{
									CommonProgress.showProgressDialog(parent);
									CommonProgress.setMessage("正在删除数据,请稍等...");
									btnclear.setEnabled(false);
									vfAction.deleteVFTransferDiffImgs(new Request(CommonVars.getCurrUser()), section);
								}finally{
									btnclear.setEnabled(true);
									CommonProgress.closeProgressDialog();
								}
								return null;
							};
							protected void success(Object e) {loadTableData(btnclear);};
						}.doWork();						
					}
				}
			});
		}
		return btnclear;
	}
	
	private void setButtonState(boolean enable){
		this.btnimp.setEnabled(enable);
		this.btnconvert.setEnabled(enable);
		this.btnclear.setEnabled(enable);
		this.btnviewhis.setEnabled(enable);
		this.btnexcel.setEnabled(enable);
	}
	/**
	 * 加载表格数据
	 * @param evtSource
	 */
	private void loadTableData(final JButton evtSource){
		final VFSection section = parent.getSection();
		if(section == null){
			JOptionPane.showMessageDialog(this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		final Request request = new Request(CommonVars.getCurrUser());
		new AsynSwingWorker<List>() {
			protected List submit() {
				try{
					CommonProgress.showProgressDialog(parent);
					CommonProgress.setMessage("正在查询数据，请稍等...");
					if(evtSource != null ) evtSource.setEnabled(false);
					Integer seqNum = null;
					if(!tfSeqNum.getText().trim().isEmpty())
						seqNum = Integer.valueOf(tfSeqNum.getText().trim());
					return vfAction.findVFTransferDiffImgs(request, section,seqNum);
				}finally{
					CommonProgress.closeProgressDialog();
					if(evtSource != null ) evtSource.setEnabled(true);
				}
			}
			protected void success(List ls) {
				initTbmimg(ls);
			}
		}.doWork();		
	}
	private JButton getBtnexcel() {
		if (btnexcel == null) {
			btnexcel = new JButton("导出Excel");
			btnexcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tbmImg.getMiSaveAllToExcel().doClick();
				}
			});
		}
		return btnexcel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("归并序号：");
		}
		return label;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80, 27));
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
	 * 显示查询结果
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
		parent.setSection(section);
		if(mergerNo != null)
			tfSeqNum.setText(String.valueOf(mergerNo));
		btnviewhis.doClick();
	}
		
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean aFlag) {
		this.tbar.add(parent.getCbsection(), 1);
		super.setVisible(aFlag);
	}	
}
