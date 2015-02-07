package com.bestway.bcs.client.verification.factoryanalyse;

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
import java.util.Vector;

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.bestway.bcs.client.verification.contractanalyse.FmVFDeclarationCommInfoImg;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFBadStockAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
import com.bestway.bcs.verification.entity.VFFinishingStockAnalyse;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFStockAnalyse;
import com.bestway.bcs.verification.entity.VFStockOutSendAnalyse;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;
import com.borland.primetime.vfs.VFS;

public class FmVFBadStockAnalyse extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTableListModel tableModel;
	
	/**
	 * 存放工厂库存汇总表的JTable
	 */
	private JTable table;
	
	private JLabel lbBatchSelect;
	
	/**
	 * 批次时间选择
	 */
	private JComboBox cbbSelectVFSection;
	
	/**
	 * 计算
	 */
	private JButton btncalculate;
	
	/**
	 * 查看历史
	 */
	private JButton btnViewHistory;
	
	/**
	 * 导出Excel
	 */
	private JButton btnExportExcel;
	
	
	private JToolBar tbMenu; 
	
	private JScrollPane scrollPane;
	
	private static final String[] linkedColNames = new String[]{"原材料(A)","半成品(B)","成品(C)"};
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	/**
	 * 清空当前数据
	 */
	private JButton btnClear;
	private VFVerificationAction vfAction = null;
	private JLabel label;
	private JTextField tfSeqNum;
	public FmVFBadStockAnalyse(){
		this.setTitle("残次品库存汇总");
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
//		authority.checkBadStockAnalyse(new Request(CommonVars.getCurrUser()));
		initialize();
		initCompontents();
	}
	
	/**
	 * 初始化窗体
	 */
	public void initialize(){
		getContentPane().add(getTbMenu(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);		
	}
	
	public JScrollPane getScrollPane(){
		if(scrollPane==null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	public JTable getTable(){
		if(table==null){
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() ==2){
						int colIdx = table.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols,table.getColumnModel().getColumn(colIdx));
						if(idx < 0 )	return;
						VFBadStockAnalyse s = (VFBadStockAnalyse)tableModel.getCurrentRow();
						VFSection section = s.getSection();
						section.setEndDate(Date.valueOf(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd")));
						switch(idx){
							case 0 : 
								FmVFBadImg fm0 = new FmVFBadImg();
								fm0.showData(s.getSection(),s.getMergerNo());
								ShowFormControl.refreshInteralForm(fm0);
								break;
							case 1 : 
								FmVFSemiBadExg fm1 = new FmVFSemiBadExg();
								fm1.showData(s.getSection(),s.getMergerNo());
								ShowFormControl.refreshInteralForm(fm1);
								break;
							case 2:
								FmVFBadExg fm2 = new FmVFBadExg();
								fm2.showData(s.getSection(),s.getMergerNo());
								ShowFormControl.refreshInteralForm(fm2);
								break;
						}									
					}
				}
			});
		}
		
		return table;
	}
	
	public JToolBar getTbMenu(){
		if(tbMenu == null){
			tbMenu = new JToolBar();
			//设置tbMenu布局
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			tbMenu.setLayout(f);
			tbMenu.add(getLbBatchSelect());
			tbMenu.add(getCbbSelectVFSection());
			tbMenu.add(getLabel());
			tbMenu.add(getTfSeqNum());
			tbMenu.add(getBtnViewHistory());
			tbMenu.add(getBtncalculate());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(getBtnClear());
		}
		return tbMenu;
	}
	public JButton getBtnClear(){
		if(btnClear==null){
			btnClear = new JButton("清空当前数据");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = (VFSection)cbbSelectVFSection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(FmVFBadStockAnalyse.this, "确定删除本批次数据","提示",
							JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker<Object>(){
							protected Object submit() {
								try{
									btnClear.setEnabled(false);	
									vfAction.deleteVFBadStockAnalyse(new Request(CommonVars.getCurrUser()), section);
									return null;
								}finally{
									btnClear.setEnabled(true);
								}
							}
							public void success(Object o){
								initTable(Collections.EMPTY_LIST);
							}
						}.doWork();						
					}
				}
			});
		}
		return btnClear;
	}
	
	public JButton getBtnExportExcel(){
		if(btnExportExcel==null){
			btnExportExcel = new JButton("导出Excel");
			btnExportExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tableModel.getMiSaveAllToExcel().doClick();
				}
			});
		}
		return btnExportExcel;
	}
	
	public JButton getBtncalculate(){
		if(btncalculate==null){
			btncalculate = new JButton("计算");
			btncalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = (VFSection)cbbSelectVFSection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					/**
					 * 先判断是否存在，并提示是否删除历史数据
					 */
					final Request req = new Request(CommonVars.getCurrUser());
					if(vfAction.isExistsBySection(req, section, VFStockOutSendAnalyse.class)){
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(FmVFBadStockAnalyse.this, "计算前将先删除本批次数据，然后再进行计算。是否继续执行计算？","提示",
								JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
					}
					/**计算***/
					new AsynSwingWorker<List<?>>(){
						protected List<?> submit() {
							try{
								btncalculate.setEnabled(false);									
								return vfAction.badVFStockAnalyse(req, section);
							}finally{
								btncalculate.setEnabled(true);
							}
						}
						public void success(List<?> ls){
							initTable(ls == null ? Collections.EMPTY_LIST : ls);
						}
					}.doWork();			
				}
			});
		}
		return btncalculate;
	}
	
	public JButton getBtnViewHistory(){
		if(btnViewHistory==null){
			btnViewHistory = new JButton("查询批次数据");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final VFSection section = (VFSection)cbbSelectVFSection.getSelectedItem();
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}			
//					/**
//					 * 先判断是否存在，并提示是否删除历史数据
//					 */
//					final Request req = new Request(CommonVars.getCurrUser());
//					if(vfAction.isExistsBySection(req, section, VFBadStockAnalyse.class)){
//						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(FmVFBadStockAnalyse.this, "计算前将先删除本批次数据，然后再进行计算。是否继续执行计算？","提示",
//								JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
//							return;
//						}
//					}
					new AsynSwingWorker<List>(){
						protected List submit() {
							try{
								btnViewHistory.setEnabled(false);
								Integer seqNum = null;
								if(StringUtils.isNotBlank(tfSeqNum.getText()))
									seqNum = NumberUtils.toInt(tfSeqNum.getText().trim());
								return vfAction.findVFBadAnalyse(new Request(CommonVars.getCurrUser()), section,seqNum);
							}finally{
								btnViewHistory.setEnabled(true);
							}
						}
						public void success(List ls){
							initTable(ls == null ? Collections.EMPTY_LIST : ls);
						}
					}.doWork();					
				}
			});
		}
		return btnViewHistory;
	}
	
	public JComboBox getCbbSelectVFSection(){
		if(cbbSelectVFSection==null){
			cbbSelectVFSection = new JComboBox();//批次时间选择		
			cbbSelectVFSection.setPreferredSize(new Dimension(120,27));
			cbbSelectVFSection.setEditable(true);
		}
		return cbbSelectVFSection;
	}
	
	public JLabel getLbBatchSelect(){
		if(lbBatchSelect==null){
			lbBatchSelect= new JLabel("批次选择:");
		}
		return lbBatchSelect;
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
		cbbSelectVFSection.setModel(new DefaultComboBoxModel(sections.toArray()));		
		cbbSelectVFSection.setRenderer(CustomBaseRender.getInstance().getRender("verificationNo", "endDate", 40, 100));		
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbSelectVFSection,"verificationNo", "endDate");
		
		initTable(Collections.EMPTY_LIST);
	}
	
	/**
	 * 初始化Jtable
	 * @param list
	 */
	public void initTable(List list){
		if(tableModel == null){
			tableModel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();						
						list.add(addColumn("归并序号", "mergerNo", 50));
						list.add(addColumn("报关名称", "hsName", 150));
						list.add(addColumn("报关规格", "hsSpec", 150));
						list.add(addColumn("计量单位", "hsUnit", 50));
						list.add(addColumn(linkedColNames[0], "imgHsAmount", 80));
						list.add(addColumn(linkedColNames[1], "semiExgHsAmount", 80));
						list.add(addColumn(linkedColNames[2], "exgHsAmount", 80));
						list.add(addColumn("库存汇总(A+B+C)", "hsAmount", 120));
						return list;
					}		
				});
		}else{
			tableModel.setList(list);
		}
		setColumnTooltip();
	}
	
	/**
	 * 设置表列的Tooltip
	 */
	private void setColumnTooltip(){
		this.linkedCols.clear();
		TableColumnModel tcm = table.getColumnModel();
		for(int i = 0 ;i < linkedColNames.length ;i++){
			final int col = i;
			linkedCols.add(tcm.getColumn(i+5));
			tcm.getColumn(i+5).setCellRenderer(new DefaultTableCellRenderer(){				
				public String getToolTipText() {
					return "双击‘"+linkedColNames[col]+"’列 可关联【"+linkedColNames[col]+"】";
				}
			});
		}
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
			tfSeqNum.setDocument(new PlainDocument (){
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
//		this.cbbSelectVFSection.setSelectedItem(section);
		int count = cbbSelectVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbSelectVFSection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbbSelectVFSection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfSeqNum.setText(String.valueOf(mergerNo));
		btnViewHistory.doClick();
	}
} 
