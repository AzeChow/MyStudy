package com.bestway.bcs.client.verification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyse;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;
/**
 * 大类短溢分析
 * @author xc
 * 
 */
public class FmVFCategoryAnalyse extends JInternalFrameBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VFVerificationAction vfVerificationAction;

	private AttributiveCellTableModel showTableModel;

	private JTableListModelAdapter adapter;

	public Request request;
	
	private JTable table;

	private JLabel lbBatchSelect;

	/**
	 * 批次时间选择
	 */
	private JComboBox cbbSelectVFSection;

	/**
	 * 折算报关数量
	 */
	private JButton btnQuery;

	/**
	 * 查看历史
	 */
	private JButton btnViewHistory;

	/**
	 * 导出Excel
	 */
	private JButton btnExportExcel;

	/**
	 * 清空当前数据
	 */
	private JButton btnemptyCurrentData;

	private JToolBar tbMenu;
	
	private JScrollPane scrollPane;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private JLabel lblNewLabel;
	private JTextField tfseqNum;
	private JLabel lbMsg;

	public FmVFCategoryAnalyse() {
		this.setTitle("大类短溢分析");

		request = new Request(CommonVars.getCurrUser());
		
		vfVerificationAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkAnalyse(request);
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	public void initialize() {

		getContentPane().add(getTbMenu(), BorderLayout.NORTH);
		getContentPane().add(getScrollpane(), BorderLayout.CENTER);

		initJtable(new ArrayList<Object>());
		initCbbSelectTime();
	}
	
	private JScrollPane getScrollpane(){
		if(scrollPane==null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private JTable getTable(){
		if(table == null){
			table = new MultiSpanCellTable();
		}
		return table;
	}
	
	/**
	 * 菜单控件
	 */
	private JToolBar getTbMenu(){
		if(tbMenu==null){
			tbMenu = new JToolBar();
			// 设置tbMenu布局
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			tbMenu.setLayout(f);
			tbMenu.add(getLbBatchSelect());
			tbMenu.add(getCbbSelectVFSection());
			tbMenu.add(getLblNewLabel());
			tbMenu.add(getTfSection());
			tbMenu.add(getBtnViewHistory());
			tbMenu.add(getBtnQuery());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(btnemptyCurrentData());
			tbMenu.add(getLbMsg());
		}
		return tbMenu;
	}
	
	private JLabel getLbBatchSelect(){
		if(lbBatchSelect==null){
			lbBatchSelect = new JLabel("批次选择:");
		}
		return lbBatchSelect;
	}
	
	private JComboBox getCbbSelectVFSection(){
		if(cbbSelectVFSection==null){
			cbbSelectVFSection = new JComboBox();
			cbbSelectVFSection.setPreferredSize(new Dimension(120,27));
			cbbSelectVFSection.setEditable(true);
		}
		return cbbSelectVFSection;
	}

	private JButton getBtnViewHistory(){
		if(btnViewHistory == null){
			btnViewHistory = new JButton("查询批次数据");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {						
						JOptionPane.showMessageDialog(FmVFCategoryAnalyse.this, "请选择需要查询的批次!");
						return;
					}
					new AsynSwingWorker<List<?>>() {
						protected java.util.List<?> submit() {
							String seqNum = tfseqNum.getText().trim();
							return vfVerificationAction.findVFCategoryAnalyses(request, getVFSection(),"".equals(seqNum) ? null:Integer.parseInt(seqNum));
						}
						protected void success(java.util.List<?> result) {
							initJtable(result);
						}
					}.doWork();
					
				}
			});
		}
		return btnViewHistory;
	}
	
	private JButton getBtnQuery(){
		if(btnQuery==null){
			btnQuery = new JButton("短溢分析");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFCategoryAnalyse.this, "请选择需要查询的批次!");
						return;
					}					
					new AsynSwingWorker<List<?>>() {
						protected java.util.List<?> submit() {
							CommonProgress.showProgressDialog(FmVFCategoryAnalyse.this);
							CommonProgress.setMessage("系统正在分析，请稍后...");
							try{
								disableControls();
								return vfVerificationAction.categoryAnalyses(request, getVFSection());
							}finally{
								CommonProgress.closeProgressDialog();
								enableControls();
							}
						}
						protected void success(java.util.List<?> result) {
							initJtable(result);
						}
					}.doWork();

				}
			});
		}
		return btnQuery;
	}	
	
	private JButton getBtnExportExcel(){
		if(btnExportExcel == null){
			btnExportExcel = new JButton("导出Excel");
			btnExportExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFCategoryAnalyse.this, "请选择需要查询的批次!");
						return;
					}
					showTableModel.getMiSaveAllToExcel().doClick();
				}
			});
		}
		return btnExportExcel;
	}
	
	private JButton btnemptyCurrentData(){
		if(btnemptyCurrentData==null){
			btnemptyCurrentData = new JButton("清空当前数据");
			btnemptyCurrentData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFCategoryAnalyse.this, "请选择需要查询的批次!");
						return;
					}
					if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
							FmVFCategoryAnalyse.this, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
							null, new Object[] { "是", "否" }, "否")) {
						vfVerificationAction.deleteVFCategoryAnalyse(request, getVFSection());
						initJtable(Collections.EMPTY_LIST);
					} else {
						return;
					}
				}
			});
		}
		return btnemptyCurrentData;
	}
	
	/**
	 * 初始化Jtable
	 * 
	 * @param list
	 */
	public void initJtable(List list) {
		adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("序号  ", "seqNum", 80));
				list.add(addColumn("名称", "hsName", 100));
				list.add(addColumn("规格", "hsSpec", 100));
				list.add(addColumn("单位", "hsUnit", 40));
				list.add(addColumn("进口数量(A)", "impAmount", 100));
				list.add(addColumn("出口总耗用(B)", "expAmount", 100));
				list.add(addColumn("合同应剩余(C=A-B)", "contractLeaveNum",120));
				list.add(addColumn("料件库存(D)", "stockAmountImg", 80));
				list.add(addColumn("在产品库存(E)", "stockAmountProcessImg", 80));
				list.add(addColumn("成品库存(F)", "stockAmountExg", 80));
				list.add(addColumn("外发库存(G)", "stockAmountOutSend", 80));
				list.add(addColumn("厂外存放库存(H)", "stockAmountOutFactory", 120));
				list.add(addColumn("内购库存(I)", "stockAmountBuy", 80));
				list.add(addColumn("在途库存(J)", "stockAmountTraveling", 80));
				list.add(addColumn("库存数量(K=D+E+F+G+H+I+J)", "stockTotalAmount", 180));
				list.add(addColumn("已转厂未收货(6)", "unReceiveHadTransNum", 110));
				list.add(addColumn("已送货未转厂(7)", "unTransHadSendNum", 110));
				list.add(addColumn("已转厂未送货(8)", "unSendHadTransNum", 110));
				list.add(addColumn("已收货未转厂(9)", "unTransHadReceiveNum", 110));
				list.add(addColumn("结转差额(10=6+7-8-9)", "transferDiffNum", 120));
				list.add(addColumn("短溢数量(K-C+10)", "overOrshortNum", 120));
				return list;
			}
		};
//		showTableModel = new JTableListModel(table, list, adapter);
		showTableModel = new AttributiveCellTableModel((MultiSpanCellTable) table, list, adapter);
		
//		【合同数据分析】：进口数量、出口总耗用、合同应剩余 
//		【盘点数据分析】：料件库存、在产品库存、成品库存、外发库存、厂外存放库存、内购库存、在途库存、库存数量 
//		【结转数据分析】：已转厂未收货、已送货未转厂、已转厂未送货、结转差额
		
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup htGroup = new ColumnGroup("【合同数据分析】");
		htGroup.add(cm.getColumn(5));
		htGroup.add(cm.getColumn(6));
		htGroup.add(cm.getColumn(7));
		ColumnGroup pdGroup = new ColumnGroup("【盘点数据分析】");
		pdGroup.add(cm.getColumn(8));
		pdGroup.add(cm.getColumn(9));
		pdGroup.add(cm.getColumn(10));
		pdGroup.add(cm.getColumn(11));
		pdGroup.add(cm.getColumn(12));
		pdGroup.add(cm.getColumn(13));
		pdGroup.add(cm.getColumn(14));
		pdGroup.add(cm.getColumn(15));
		ColumnGroup jzGroup = new ColumnGroup("【结转数据分析】");
		jzGroup.add(cm.getColumn(16));
		jzGroup.add(cm.getColumn(17));
		jzGroup.add(cm.getColumn(18));
		jzGroup.add(cm.getColumn(19));
		jzGroup.add(cm.getColumn(20));
		jzGroup.add(cm.getColumn(21));
		GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
		header.addColumnGroup(htGroup);
		header.addColumnGroup(pdGroup);
		header.addColumnGroup(jzGroup);
	}
	
	/**
	 * 初始化批次选择的下拉列表
	 */
	public void initCbbSelectTime() {

		// 格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VFSection> dataSource = vfVerificationAction.findVFSectionList(new Request(
				CommonVars.getCurrUser()));
		List<VFSection> manulist = new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection = (VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection
					.getEndDate())));
			manulist.add(vfSection);
		}
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel(
				manulist.toArray());
		this.cbbSelectVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbSelectVFSection, "verificationNo", "endDate", 150);
		this.cbbSelectVFSection.setRenderer(CustomBaseRender.getInstance()
				.getRender("verificationNo", "endDate", 40, 100));
		// this.cbbSelectVFSection.setSelectedItem(null);
	}

	/**
	 * 获得批次号,当批次号为空时返回null,否则返回批次号
	 * 
	 * @return
	 */
	public VFSection getVFSection() {
		VFSection section = (VFSection) cbbSelectVFSection.getSelectedItem();
		return section;
	}
	
	private void disableControls(){
		cbbSelectVFSection.setEnabled(false);
		btnViewHistory.setEnabled(false);
		btnExportExcel.setEnabled(false);
		btnemptyCurrentData.setEnabled(false);
	}
	
	private void enableControls(){
		cbbSelectVFSection.setEnabled(true);
		btnViewHistory.setEnabled(true);
		btnExportExcel.setEnabled(true);
		btnemptyCurrentData.setEnabled(true);
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("序号:");
		}
		return lblNewLabel;
	}
	private JTextField getTfSection() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setPreferredSize(new Dimension(6, 27));
			tfseqNum.setColumns(10);
			tfseqNum.setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					char c = str.charAt(str.length()-1);
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}
				}
			});
		}
		return tfseqNum;
	}
	private JLabel getLbMsg() {
		if (lbMsg == null) {
			lbMsg = new JLabel("（序号前带*为已做大类归并的商品）");
			lbMsg.setForeground(Color.BLUE);
		}
		return lbMsg;
	}
}
