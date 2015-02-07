package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import com.bestway.bcs.verification.entity.VFTransferDiffHsImg;
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

public class PnVFTransferDiffHsImg extends JPanel {

	
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
	public PnVFTransferDiffHsImg(FmVFTransferDiffImg parent) {
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
			tbar.add(getBtnexcel());
			tbar.add(getBtnclear());
			tbar.add(getBtnImportFromFpt());
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
	private JTableListModel initTbmimg(List<?> list){
		if(tbmImg == null){
			tbmImg = new JTableListModel(tbimg, list, new JTableListModelAdapter() {				
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
					cols.add(new JTableListColumn("料件备案序号", "seqNum", 100));
					cols.add(new JTableListColumn("手册号", "emsNo", 100));
					cols.add(new JTableListColumn("报关品名", "hsName", 120));
					cols.add(new JTableListColumn("报关规格", "hsSpec", 120));
					cols.add(new JTableListColumn("计量单位", "hsUnit", 110));
					cols.add(new JTableListColumn("已收货未转厂", "unTransHadReceiveNum", 100));
					cols.add(new JTableListColumn("已转厂未收货", "unReceiveHadTransNum", 100));
					cols.add(new JTableListColumn("报关单数量", "customsBillNum", 110));
					cols.add(new JTableListColumn("收货单数量", "receiveBillNum", 110));
					cols.add(new JTableListColumn("归并序号", "mergerNo", 100));
					return cols;
				}
			});
			TableColumnModel cm = tbimg.getColumnModel();
			ColumnGroup g2 = new ColumnGroup("报关资料");
			for(int i = 1 ;i < cm.getColumnCount(); i++){
				g2.add(cm.getColumn(i));
			}
			GroupableTableHeader header = (GroupableTableHeader) tbimg.getTableHeader();
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
			btnimp = new JButton("1.导入盘点数据（编码级）");
			btnimp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(PnVFTransferDiffHsImg.this,"请先选择批次后，再导入数据！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					Request request = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffHsImgs(request, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffHsImg.this,"本批次已存在数据，导入数据需时先删除本次已导入的数据，确认继续导入？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
						vfAction.deleteVFTransferDiffHsImgs(new Request(CommonVars.getCurrUser()), section);
						initTbmimg(Collections.EMPTY_LIST);
					}

					DgImpDiffExgImg dg = new DgImpDiffExgImg(DgImpDiffExgImg.IMP_DIFFIMG, "导入料件结转差额表窗口(编码级)",section);
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
						JOptionPane.showMessageDialog(PnVFTransferDiffHsImg.this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(PnVFTransferDiffHsImg.this, "确定删除本批次数据","提示",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker() {
							protected Object submit() {
								try{
									CommonProgress.showProgressDialog(PnVFTransferDiffHsImg.this.getParentDialog());
									CommonProgress.setMessage("正在删除数据,请稍等...");
									btnclear.setEnabled(false);
									vfAction.deleteVFTransferDiffHsImgs(new Request(CommonVars.getCurrUser()), section);
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
	
	private FmVFTransferDiffImg getParentDialog(){
		Component p = this;
		while(true) {
			if(p instanceof FmVFTransferDiffImg) {
				break;
			}
			p = p.getParent();
			if(p == null) {
				break;
			}
		}
		return (FmVFTransferDiffImg) p;
	}
	

	
	private void setButtonState(boolean enable){
		this.btnimp.setEnabled(enable);
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
					CommonProgress.showProgressDialog(PnVFTransferDiffHsImg.this.getParentDialog());
					CommonProgress.setMessage("正在查询数据，请稍等...");
					if(evtSource != null ) evtSource.setEnabled(false);
					Integer seqNum = null;
					if(!tfSeqNum.getText().trim().isEmpty())
						seqNum = Integer.valueOf(tfSeqNum.getText().trim());
					return vfAction.findVFTransferDiffHsImgs(request, section,seqNum);
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

	
	
	private JButton btnImportFromFpt;
	

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean aFlag) {
		this.tbar.add(parent.getCbsection(), 1);
		super.setVisible(aFlag);
	}	
	
	
	private JButton getBtnImportFromFpt() {
		if (btnImportFromFpt == null) {
			btnImportFromFpt = new JButton("\u4ECE\u6DF1\u52A0\u5DE5\u7ED3\u8F6C\u83B7\u53D6");
			btnImportFromFpt.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					btnImportFromFpt.setToolTipText("从深加工结转模块获取数据");
				}
			});
			btnImportFromFpt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					final Request req = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffHsImgs(req, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffHsImg.this, "本批次已存在数据，确定先删除本批次数据，并从深加工模块获取数据吗？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
					}
					new AsynSwingWorker<List<VFTransferDiffHsImg> >() {
						public List<VFTransferDiffHsImg> submit() {			

							req.setTaskId(CommonProgress.getExeTaskId());
							CommonStepProgress.showStepProgressDialog(req.getTaskId());
							CommonStepProgress.setStepMessage("系统正在发送获取请求，请稍后...");
							long beginTime = System.currentTimeMillis();									
							setButtonState(false);
							try{
								return vfAction.generateTransferDiffHsImgHsFormFpt(req, section);												
							}finally{
								System.out.println(" 获取数量完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
								setButtonState(true);
								CommonStepProgress.closeStepProgressDialog();
							}
						}
						protected void success(List<VFTransferDiffHsImg> e) {
							initTbmimg(e);
						}
					}.doWork();
				}
			});
		}
		return btnImportFromFpt;
	}
}
