package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffCount;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

public class FmVFTransferDiffCount extends JInternalFrameBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable tbCount;
	private JLabel lbsection;
	private JComboBox cbsection;
	private JButton btnCacu;
	private JButton btnViewHis;
	private JButton btnClear;
	private JTableListModel tbmCount; 
	private VFVerificationAction vfAction = null;
	private JButton btnexcel;
	private JLabel label;
	private JTextField tfSeqNum;
	private static final String[] linkedColNames = new String[]{"已送货未转厂数(A)","已转厂未送货数(B)","已收货未转厂数(C)","已转厂未收货数(D)"};
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	/**
	 * Create the frame.
	 */
	public FmVFTransferDiffCount() {
		initialize();
	}
	/**
	 * 初始化
	 */
	private void initialize() {
		this.setTitle("结转差额汇总表");
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkTransferDiffCount(new Request(CommonVars.getCurrUser()));
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		initCompontents();
	}
	/**
	 * 初始化控件数据
	 */
	private void initCompontents(){
		//初始化批次下拉框
		List<VFSection> sections = vfAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
		for(VFSection s : sections){
			if(s.getEndDate() != null){
				s.setEndDate(java.sql.Date.valueOf(CommonUtils.getDate(s.getEndDate(),"yyyy-MM-dd")));
			}
		}
		cbsection.setModel(new DefaultComboBoxModel(sections.toArray()));
		cbsection.setRenderer(CustomBaseRender.getInstance().getRender("verificationNoView", "endDate", 150, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbsection,"verificationNoView", "endDate",250);
		initTbmCount(Collections.EMPTY_LIST);
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			toolBar.add(getLbsection());
			toolBar.add(getCbsection());
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnViewHis());
			toolBar.add(getBtnCacu());			
			toolBar.add(getBtnexcel());
			toolBar.add(getBtnClear());
		}
		return toolBar;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbCount());
		}
		return scrollPane;
	}
	private JTable getTbCount() {
		if (tbCount == null) {
			tbCount = new JTable();
			tbCount.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() ==2){
						int colIdx = tbCount.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols,tbCount.getColumnModel().getColumn(colIdx));
						if(idx < 0 )	return;
						VFTransferDiffCount c = (VFTransferDiffCount)tbmCount.getCurrentRow();
						VFSection section = c.getSection();
						section.setEndDate(Date.valueOf(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd")));
						switch(idx){
							case 0:
							case 1:
								FmVFTransferDiffExg fm0 = new FmVFTransferDiffExg();
								fm0.showData(section,c.getMergerNo());
								ShowFormControl.refreshInteralForm(fm0);
								break;
							case 2:
							case 3:
								FmVFTransferDiffImg fm1 = new FmVFTransferDiffImg();
								fm1.showData(section,c.getMergerNo());
								ShowFormControl.refreshInteralForm(fm1);
								break;
						}
					}
				}
			});
		}
		return tbCount;
	}
	private JLabel getLbsection() {
		if (lbsection == null) {
			lbsection = new JLabel("批次：");
		}
		return lbsection;
	}
	private JComboBox getCbsection() {
		if (cbsection == null) {
			cbsection = new JComboBox();
			cbsection.setPreferredSize(new Dimension(120,27));
			cbsection.setEditable(true);
		}
		return cbsection;
	}
	private JButton getBtnCacu() {
		if (btnCacu == null) {
			btnCacu = new JButton("计算");
			btnCacu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = (VFSection)cbsection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					final Request request = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistVFTransferDiffCounts(request, section)){
						if(JOptionPane.showOptionDialog(getContentPane(), "已存在本批次的汇总数据，是否删除后重新计算？","提示",
								JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否") != JOptionPane.YES_OPTION){
							return;
						}
					}
					new AsynSwingWorker<List>() {
						protected List submit() {
							btnCacu.setEnabled(false);
							try{								
								CommonProgress.showProgressDialog(FmVFTransferDiffCount.this);
								CommonProgress.setMessage("正在计算，请稍等...");
								return  vfAction.cacuVFTransferDiffCount(request, section);
							}finally{
								CommonProgress.closeProgressDialog();
								btnCacu.setEnabled(true);
							}
						}
						protected void success(List ls) {
							initTbmCount(ls == null ? Collections.EMPTY_LIST : ls);
						}
					}.doWork();
				}
			});
		}
		return btnCacu;
	}
	private JButton getBtnViewHis() {
		if (btnViewHis == null) {
			btnViewHis = new JButton("查询批次数据");
			btnViewHis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = (VFSection)cbsection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					 
					new AsynSwingWorker<List>() {
						protected List submit() {
							btnViewHis.setEnabled(false);
							CommonProgress.showProgressDialog(FmVFTransferDiffCount.this);
							CommonProgress.setMessage("正在查询数据,请稍等...");
							try{
								Integer seqNum = null;
								if(!tfSeqNum.getText().isEmpty())
									seqNum = Integer.valueOf(tfSeqNum.getText().trim());
								return vfAction.findVFTransferDiffCount(new Request(CommonVars.getCurrUser()), section,seqNum);
							}finally{
								btnViewHis.setEnabled(true);
								CommonProgress.closeProgressDialog();
							}
						}						
						protected void success(List ls) {
							initTbmCount(ls == null ? Collections.EMPTY_LIST : ls);
						}
					}.doWork();
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
					final VFSection section = (VFSection)cbsection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
			
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(FmVFTransferDiffCount.this, "确定删除本批次数据","提示",JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){											
						new AsynSwingWorker<List>() {
							protected List submit() {
								btnClear.setEnabled(false);
								CommonProgress.showProgressDialog(FmVFTransferDiffCount.this);
								CommonProgress.setMessage("正在删除数据,请稍等...");
								try{
									vfAction.deleteVFTransferDiffCount(new Request(CommonVars.getCurrUser()), section);
								}finally{
									btnClear.setEnabled(true);
									CommonProgress.closeProgressDialog();
								}
								return null;
							}						
							protected void success(List ls) {
								initTbmCount(Collections.EMPTY_LIST);
							}
						}.doWork();				
					}
				}
			});
		}
		return btnClear;
	}
	
	/**
	 * 初始化表格模型
	 * @param list
	 * @return
	 */
	private JTableListModel initTbmCount(List list){
		if(tbmCount == null){
			tbmCount = new JTableListModel(tbCount,list,new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
					cols.add(new JTableListColumn("归并序号", "mergerNo",60));
					cols.add(new JTableListColumn("报关商品名称", "hsName",100));
					cols.add(new JTableListColumn("报关商品规格", "hsSpec",100));
					cols.add(new JTableListColumn("报关商品单位", "hsUnit",80));
					cols.add(new JTableListColumn(linkedColNames[0], "convertUnTransHadSendNum",120));
					cols.add(new JTableListColumn(linkedColNames[1], "convertUnSendHadTransNum",120));
					cols.add(new JTableListColumn(linkedColNames[2], "convertUnTransHadReceiveNum",120));
					cols.add(new JTableListColumn(linkedColNames[3], "convertUnReceiveHadTransNum",120));
					cols.add(new JTableListColumn("差额(A+D-B-C)", "diffAmount",120));
					return cols;
				}
				
			});
		}else{
			tbmCount.setList(list);
		}
		setColumnsTooltip();
		TableColumnModel tcm = tbCount.getColumnModel();
		this.linkedCols.clear();
		this.linkedCols.add(tcm.getColumn(5));
		this.linkedCols.add(tcm.getColumn(6));
		this.linkedCols.add(tcm.getColumn(7));
		this.linkedCols.add(tcm.getColumn(8));
		return tbmCount;
	}
	
	@SuppressWarnings("serial")
	private void setColumnsTooltip(){
		TableColumnModel tcm = tbCount.getColumnModel();
		tcm.getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[0]+"’可关联到【成品结转差额表】";}
		});
		tcm.getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[1]+"’可关联到【成品结转差额表】";}
		});		
		tcm.getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[2]+"’可关联到【料件结转差额表】";}
		});
		tcm.getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[3]+"’可关联到【料件结转差额表】";}
		});
	}
	
	private JButton getBtnexcel() {
		if (btnexcel == null) {
			btnexcel = new JButton("导出Excel");
			btnexcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tbmCount.getMiSaveAllToExcel().doClick();
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
	 * 查询数据并显示与界面
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		this.cbsection.setSelectedItem(section);
		int count = cbsection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbsection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbsection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfSeqNum.setText(String.valueOf(mergerNo));
		btnViewHis.doClick();
	}
	
}
