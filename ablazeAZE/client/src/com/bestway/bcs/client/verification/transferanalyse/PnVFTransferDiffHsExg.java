package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.BorderLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;

/**
 * 成品结转差额表
 * @author xc
 * 2013-09-02
 */
public class PnVFTransferDiffHsExg extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar tbar;
	private JTabbedPane pnTabCenter;
	private JScrollPane spnExg;
	private JScrollPane spnImg;
	private JLabel lbsection;
	private JButton btnimp;
	private JButton btnunpick;
	private JButton btnImportFromFpt;
	private JButton btnViewHis;
	private JButton btnClear;
	private JTable tbexg;
	private MultiSpanCellTable tbimg;
	private JTableListModel tbmExg;
	private JTableListModel tbmImg;
	
	private VFVerificationAction vfAction = null;
	private JButton btnexcel;
	private JLabel lbSeqNum;
	private JTextField tfSeqNum;
	private FmVFTransferDiffExg parent;
	/**
	 * Create the frame.
	 */
	public PnVFTransferDiffHsExg(FmVFTransferDiffExg parent) {
		this.parent = parent;
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkTransferDiffExg(new Request(CommonVars.getCurrUser()));
		initialize();
	}
	/**
	 * 初始化
	 */
	private void initialize() {
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		setLayout(new BorderLayout(0, 0));
		this.add(getTbar(), BorderLayout.NORTH);
		this.add(getPnTabCenter(), BorderLayout.CENTER);
		initCompontents();
	}
	/**
	 * 初始化控件数据
	 */
	private void initCompontents(){
		initExgTbModel(Collections.EMPTY_LIST);
		initImgTbModel(Collections.EMPTY_LIST);
		
		boolean show =  pnTabCenter.getSelectedIndex() == 1 ;
		lbSeqNum.setVisible(show);
		tfSeqNum.setVisible(show);
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
			tbar.add(getLabel_1());
			tbar.add(getTfSeqNum());
			tbar.add(getBtnViewHis());
			tbar.add(getBtnimp());
			tbar.add(getBtnunpick());
			tbar.add(getBtnexcel());
			tbar.add(getBtnClear());
			tbar.add(getBtnImportFromFpt());
		}
		return tbar;
	}
	private JTabbedPane getPnTabCenter() {
		if (pnTabCenter == null) {
			pnTabCenter = new JTabbedPane(JTabbedPane.TOP);
			pnTabCenter.addTab("导入原态", null, getSpnExg(), null);
			pnTabCenter.addTab("折算料件", null, getSpnImg(), null);			
			pnTabCenter.addChangeListener(new ChangeListener() {				
				public void stateChanged(ChangeEvent e) {
					boolean show =  pnTabCenter.getSelectedIndex() == 1 ;
					lbSeqNum.setVisible(show);
					tfSeqNum.setVisible(show);
				}
			});
		}
		return pnTabCenter;
	}
	private JScrollPane getSpnExg() {
		if (spnExg == null) {
			spnExg = new JScrollPane();
			spnExg.setViewportView(getTbexg());
		}
		return spnExg;
	}
	private JScrollPane getSpnImg() {
		if (spnImg == null) {
			spnImg = new JScrollPane();
			spnImg.setViewportView(getTbimg());
		}
		return spnImg;
	}
	private JLabel getLbsection() {
		if (lbsection == null) {
			lbsection = new JLabel("批次选择：");
		}
		return lbsection;
	}
	private JButton getBtnimp() {
		if (btnimp == null) {
			btnimp = new JButton("1.导入盘点数据(编码级)");
			btnimp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再导入数据！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					Request request = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffHsExgs(request, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffHsExg.this, "本批次已存在数据，导入数据需时先删除本次已导入的数据，确认继续导入？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
						vfAction.deleteVFTransferDiffHsExgs(new Request(CommonVars.getCurrUser()), section);
						initExgTbModel(Collections.EMPTY_LIST);
						initImgTbModel(Collections.EMPTY_LIST);
					}
					DgImpDiffExgImg dg = new DgImpDiffExgImg(DgImpDiffExgImg.IMP_DIFFEXG, "导入成品结转差额表窗口",section);
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
	private JButton getBtnunpick() {
		if (btnunpick == null) {		
			btnunpick = new JButton("2.成品折算料件");
			btnunpick.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//成品拆料件
					unpickExgToImg();
				}
			});
		}
		return btnunpick;
	}	
	
	private JButton getBtnImportFromFpt() {
		if (btnImportFromFpt == null) {
			btnImportFromFpt = new JButton("\u4ECE\u6DF1\u52A0\u5DE5\u7ED3\u8F6C\u83B7\u53D6");
			btnImportFromFpt.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
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
					if(vfAction.isExistVFTransferDiffHsExgs(req, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffHsExg.this, "本批次已存在数据，确定先删除本批次数据，并从深加工模块获取数据吗？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
					}
					new AsynSwingWorker<List<VFTransferDiffHsExg> >() {
						public List<VFTransferDiffHsExg> submit() {			
							req.setTaskId(CommonProgress.getExeTaskId());
							CommonStepProgress.showStepProgressDialog(req.getTaskId());
							CommonStepProgress.setStepMessage("系统正在发送获取请求，请稍后...");
							long beginTime = System.currentTimeMillis();									
							try{
								setButtonState(false);
								return vfAction.generateTransferDiffHsExgsFormFpt(req, section);												
							}finally{
								System.out.println(" 获取数量完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
								setButtonState(true);
								CommonStepProgress.closeStepProgressDialog();
							}
						}
						protected void success(List<VFTransferDiffHsExg> e) {
							pnTabCenter.setSelectedIndex(0);	
							initExgTbModel(e);							
						}
					}.doWork();
				}
			});
		}
		return btnImportFromFpt;
	}
	private JButton getBtnViewHis() {
		if (btnViewHis == null) {
			btnViewHis = new JButton("查询批次数据");			
			btnViewHis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loadTableData(btnViewHis);
				}
			});
		}
		return btnViewHis;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("清空当前数据");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}					
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(PnVFTransferDiffHsExg.this, "确定删除本批次数据","提示",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker() {
							protected Object submit() {								
								try{
									CommonProgress.showProgressDialog(parent);
									CommonProgress.setMessage("正在删除数据，请稍等...");
									btnClear.setEnabled(false);
									vfAction.deleteVFTransferDiffHsExgs(new Request(CommonVars.getCurrUser()), section);
								}finally{
									CommonProgress.closeProgressDialog();
									btnClear.setEnabled(true);;
								}
								return null;
							}
							protected void success(Object e) {
								loadTableData(btnClear);
							}
						}.doWork();											
					}
				}
			});
		}
		return btnClear;
	}
	private JTable getTbexg() {
		if (tbexg == null) {
			tbexg = new JTable();
		}
		return tbexg;
	}	
	private MultiSpanCellTable getTbimg() {
		if (tbimg == null) {
//			tbimg = new GroupableHeaderTable();
			tbimg = new MultiSpanCellTable();
		}
		return tbimg;
	}
	/**
	 * 初始化成品表格模型
	 * @param data
	 * @return
	 */
	private JTableListModel initExgTbModel(List data){
		if(tbmExg == null){
			tbmExg = new JTableListModel(tbexg, data, new JTableListModelAdapter() {				
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
					cols.add(new JTableListColumn("成品备案序号","seqNum", 100));
					cols.add(new JTableListColumn("手册号", "emsNo", 100));
					cols.add(new JTableListColumn("报关品名", "hsName", 120));
					cols.add(new JTableListColumn("报关规格", "hsSpec", 120));
					cols.add(new JTableListColumn("计量单位", "hsUnit", 110));
					cols.add(new JTableListColumn("已送货未转厂", "unTransHadSendNum", 110));
					cols.add(new JTableListColumn("已转厂未送货", "unSendHadTransNum", 110));
					cols.add(new JTableListColumn("报关单数量", "customsBillNum", 110));
					cols.add(new JTableListColumn("发货单数量", "sendBillNum", 110));
					return cols;
				}
			});
		}else{
			tbmExg.setList(data);
		}
		return tbmExg;
	}
	/**
	 * 初始化料件表格模型
	 * @param data
	 * @return
	 */
	private JTableListModel initImgTbModel(List data){
		if(tbmImg == null){
			tbmImg = new AttributiveCellTableModel(tbimg, data, new JTableListModelAdapter() {				
				public List<JTableListColumn> InitColumns() {
					
					List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
					cols.add(new JTableListColumn("手册号","hsExg.emsNo",100));
					cols.add(new JTableListColumn("成品备案序号","hsExg.seqNum",100));
					cols.add(new JTableListColumn("成品名称","hsExg.hsName",100));					
					cols.add(new JTableListColumn("成品规格","hsExg.hsSpec",100));
					cols.add(new JTableListColumn("计量单位","hsExg.hsUnit",100));
					cols.add(new JTableListColumn("已送货未结转","hsExg.unTransHadSendNum",120));
					cols.add(new JTableListColumn("已结转未送货","hsExg.unSendHadTransNum",120));
					
					cols.add(new JTableListColumn("归并序号","mergerNo",60));
					cols.add(new JTableListColumn("料件名称","hsName",100));
					cols.add(new JTableListColumn("料件规格","hsSpec",100));			
					cols.add(new JTableListColumn("计量单位","hsUnit",60));
					cols.add(new JTableListColumn("已送货未结转","unTransferNum",100));
					cols.add(new JTableListColumn("已结转未送货","unSendferNum",100));
					return cols;
				}
			});
			TableColumnModel cm = tbimg.getColumnModel();			
			ColumnGroup g1 = new ColumnGroup("备案成品");
			for(int i = 1 ;i < 8;i++){
				g1.add(cm.getColumn(i));
			}				
			ColumnGroup g2 = new ColumnGroup("备案料件");
			for(int i = 8 ;i < cm.getColumnCount();i++){
				g2.add(cm.getColumn(i));
			}			
			GroupableTableHeader header = (GroupableTableHeader) tbimg.getTableHeader();
			header.addColumnGroup(g1);
			header.addColumnGroup(g2);
//			tbimg.combineRows(new int[]{1,2});
		}else{
			tbmImg.setList(data);
//			tbimg.combineRows(new int[]{1,2});
		}
		return tbmImg;
	}
	
	/**
	 * 加载表格数据
	 */
	private void loadTableData(final JButton eventSouce){	
		final VFSection section = parent.getSection();
		if(section == null){
			JOptionPane.showMessageDialog(this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		setButtonState(false);
		final Request request = new Request(CommonVars.getCurrUser());
		new AsynSwingWorker<List>() {
			protected List submit() {
				try{
					CommonProgress.showProgressDialog(parent);
					CommonProgress.setMessage("正在查询数据,请稍等...");
					if(pnTabCenter.getSelectedIndex() == 0){
						return vfAction.findVFTransferDiffHsExgs(request, section);
					}else if(pnTabCenter.getSelectedIndex() ==1){
						Integer seqNum = null;
						if(!tfSeqNum.getText().trim().isEmpty())
							seqNum = Integer.valueOf(tfSeqNum.getText().trim());
						return  vfAction.findVFTransferDiffHsExgResolves(request, section,seqNum);
					}
					return null;
				}finally{
					setButtonState(true);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(List e) {
				if(pnTabCenter.getSelectedIndex() == 0){
					initExgTbModel(e);
				}else if(pnTabCenter.getSelectedIndex() ==1){
					initImgTbModel(e);
				}				
			}
		}.doWork();		
	}
	/**
	 * 成品拆分成料件
	 */
	private void unpickExgToImg(){
		final VFSection section = parent.getSection();
		if(section == null){
			JOptionPane.showMessageDialog(this,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);			
			return ;
		}
		//禁用事件触发按钮
		new AsynSwingWorker<List>() {
			public List submit() {
				setButtonState(false);
				Request req = new Request(CommonVars.getCurrUser());
				req.setTaskId(CommonProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(req.getTaskId());
				CommonStepProgress.setStepMessage("系统正在发送折算请求，请稍后...");
				long beginTime = System.currentTimeMillis();		
				try{
					return vfAction.resolveTransferDiffHsExgs(req, section);
				}finally{
					System.out.println(" 折算料件完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
					setButtonState(true);
					CommonStepProgress.closeStepProgressDialog();
				}
			}
			protected void success(List e) {
				pnTabCenter.setSelectedIndex(1);
				initImgTbModel(e);
			}
		}.doWork();
	}
	/**
	 * 设置按钮状态
	 * @param enable
	 */
	private void setButtonState(boolean enable){
		this.btnimp.setEnabled(enable);
		this.btnImportFromFpt.setEnabled(enable);
		this.btnunpick.setEnabled(enable);
		this.btnClear.setEnabled(enable);
		this.btnViewHis.setEnabled(enable);		
		this.btnexcel.setEnabled(enable);
	}
	private JButton getBtnexcel() {
		if (btnexcel == null) {
			btnexcel = new JButton("导出Excel");
			btnexcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(pnTabCenter.getSelectedIndex() == 0){
						tbmExg.getMiSaveAllToExcel().doClick();
					}else if(pnTabCenter.getSelectedIndex() == 1){
						tbmImg.getMiSaveAllToExcel().doClick();
					}
				}
			});
		}
		return btnexcel;
	}
	private JLabel getLabel_1() {
		if (lbSeqNum == null) {
			lbSeqNum = new JLabel("归并序号：");
		}
		return lbSeqNum;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80, 27));
			tfSeqNum.setDocument(new PlainDocument(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
		pnTabCenter.setSelectedIndex(1);
		btnViewHis.doClick();
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