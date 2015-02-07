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

import com.bestway.bcs.client.verification.factoryanalyse.DgErrorMessage;
import com.bestway.bcs.client.verification.factoryanalyse.ErrorMessage;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
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
public class PnVFTransferDiffPtExg extends JPanel {

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
	private JButton btncacu;
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
	public PnVFTransferDiffPtExg(FmVFTransferDiffExg parent) {
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
			tbar.add(getBtncacu());
			tbar.add(getBtnexcel());
			tbar.add(getBtnClear());
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
			btnimp = new JButton("1.导入盘点数据");
			btnimp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再导入数据！","警告",JOptionPane.WARNING_MESSAGE);						
						return ;
					}
					Request request = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffExgs(request, section)){
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnVFTransferDiffPtExg.this, "本批次已存在数据，导入数据需时先删除本次已导入的数据，确认继续导入？",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return ;
						}
						vfAction.deleteVFTransferDiffExgs(new Request(CommonVars.getCurrUser()), section);
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
			btnunpick = new JButton("2.成品折算料件(报关常用工厂BOM)");
			btnunpick.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//成品拆料件
					unpickExgToImg();
				}
			});
		}
		return btnunpick;
	}	
	
	private JButton getBtncacu() {
		if (btncacu == null) {
			btncacu = new JButton("3.折算报关数量");
			btncacu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = parent.getSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					setButtonState(false);
					new AsynSwingWorker<List<VFTransferDiffExgConvert> >() {
						public List<VFTransferDiffExgConvert> submit() {			
							Request req = new Request(CommonVars.getCurrUser());
							req.setTaskId(CommonProgress.getExeTaskId());
							CommonStepProgress.showStepProgressDialog(req.getTaskId());
							CommonStepProgress.setStepMessage("系统正在发送折算请求，请稍后...");
							long beginTime = System.currentTimeMillis();	
							String logg =null;
							List<VFTransferDiffExgConvert> list = new ArrayList<VFTransferDiffExgConvert>();
							try {
								logg = vfAction
										.convertVFTransferDiffExgConvertToCustoms(
												req, section);
								if (logg != null && !logg.isEmpty()) {
									String[] ptNos = logg.toString().split(",");
									List<ErrorMessage> err = new ArrayList<ErrorMessage>();
									for (int i = 0; i < ptNos.length; i++) {
										err.add(new ErrorMessage(ptNos[i],
												"在【物料与报关对应表】中不存在,或找不到对应关系"));
									}
									DgErrorMessage.show(err);
								}
							}finally{
								System.out.println(" 折算报关数量完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
								setButtonState(true);
								CommonStepProgress.closeStepProgressDialog();
							}
							return list;
							
						}
						
						protected void success(java.util.List<VFTransferDiffExgConvert> e) {
							if( e != null && e.size() > 0 )
								initImgTbModel(e);
							pnTabCenter.setSelectedIndex(1);	
							loadTableData(btnViewHis);
						}
					}.doWork();
				}
			});
		}
		return btncacu;
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
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(PnVFTransferDiffPtExg.this, "确定删除本批次数据","提示",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker() {
							protected Object submit() {								
								try{
									CommonProgress.showProgressDialog(parent);
									CommonProgress.setMessage("正在删除数据，请稍等...");
									btnClear.setEnabled(false);
									vfAction.deleteVFTransferDiffExgs(new Request(CommonVars.getCurrUser()), section);
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
					cols.add(new JTableListColumn("成品料号","ptNo",150));
					cols.add(new JTableListColumn("工厂名称","ptName",200));
					cols.add(new JTableListColumn("工厂规格","ptSpec",150));					
					cols.add(new JTableListColumn("计量单位","ptUnit",80));
					cols.add(new JTableListColumn("已送货未转厂","unTransferNum",120));
					cols.add(new JTableListColumn("已转厂未送货","unSendferNum",120));
					cols.add(new JTableListColumn("BOM版本","bomVersion",60));
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
					cols.add(new JTableListColumn("工厂料号","exg.ptNo",100));
					cols.add(new JTableListColumn("BOM版本","exg.bomVersion",60));
										
					cols.add(new JTableListColumn("料件料号","ptNo",100));
					cols.add(new JTableListColumn("工厂名称","ptName",100));
					cols.add(new JTableListColumn("工厂规格","ptSpec",100));			
					cols.add(new JTableListColumn("工厂单位","ptUnit",60));
					cols.add(new JTableListColumn("单耗","unitWaste",50));
					cols.add(new JTableListColumn("损耗","waste",50));			
					cols.add(new JTableListColumn("单项用量","unitUsed",60));					
					cols.add(new JTableListColumn("已送货未转厂数","unTransferNum",100));
					cols.add(new JTableListColumn("已转厂未送货数","unSendferNum",100));
					
					cols.add(new JTableListColumn("归并序号","mergerNo",60));
					cols.add(new JTableListColumn("报关商品名称","hsName",100));					
					cols.add(new JTableListColumn("报关商品规格","hsSpec",100));
					cols.add(new JTableListColumn("报关商品单位","hsUnit",100));
					cols.add(new JTableListColumn("折算已送货未转厂数","convertUnTransHadSendNum",120));
					cols.add(new JTableListColumn("折算已转厂未送货数","convertUnSendHadTransNum",120));
					cols.add(new JTableListColumn("折算系数","unitConvert",100));
					return cols;
				}
			});
			TableColumnModel cm = tbimg.getColumnModel();			
			ColumnGroup g1 = new ColumnGroup("成品");
			g1.add(cm.getColumn(1));
			g1.add(cm.getColumn(2));			
			ColumnGroup g2 = new ColumnGroup("工厂料件");
			for(int i = 3 ;i < 12;i++){
				g2.add(cm.getColumn(i));
			}			
			ColumnGroup g3 = new ColumnGroup("报关料件");
			for(int i = 12 ;i < cm.getColumnCount() - 1;i++){
				g3.add(cm.getColumn(i));
			}
			GroupableTableHeader header = (GroupableTableHeader) tbimg.getTableHeader();
			header.addColumnGroup(g1);
			header.addColumnGroup(g2);
			header.addColumnGroup(g3);
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
						return vfAction.findVFTransferDiffExgs(request, section);
					}else if(pnTabCenter.getSelectedIndex() ==1){
						Integer seqNum = null;
						if(!tfSeqNum.getText().trim().isEmpty())
							seqNum = Integer.valueOf(tfSeqNum.getText().trim());
						return  vfAction.findVFTransferDiffExgConverts(request, section,seqNum);
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
		new AsynSwingWorker<List[]>() {
			public List[] submit() {
				setButtonState(false);
				Request req = new Request(CommonVars.getCurrUser());
				req.setTaskId(CommonProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(req.getTaskId());
				CommonStepProgress.setStepMessage("系统正在发送折算请求，请稍后...");
				long beginTime = System.currentTimeMillis();		
				try{
					final List<VFTransferDiffExgConvert> convls = vfAction.unpickExgToImg(req, section);
					final List<VFTransferDiffExg> exgLs = vfAction.findVFTransferDiffExgs(req, section);
					return new List[]{convls,exgLs};
				}finally{
					System.out.println(" 折算料件完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
					setButtonState(true);
					CommonStepProgress.closeStepProgressDialog();
				}
			}
			protected void success(List[] e) {
				pnTabCenter.setSelectedIndex(1);
				initImgTbModel(e[0]);
				initExgTbModel(e[1]);
			}
		}.doWork();
	}
	/**
	 * 设置按钮状态
	 * @param enable
	 */
	private void setButtonState(boolean enable){
		this.btnimp.setEnabled(enable);
		this.btncacu.setEnabled(enable);
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