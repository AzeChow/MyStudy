package com.bestway.bcs.client.verification.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.JTextFieldTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFContractAnalyse extends JInternalFrameBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane=null;
	private JToolBar toolbar=null;
	private JLabel lbBatch=null;
	/**
	 * 批次设定下拉框
	 */
	private JComboBox cbbVFSection=null;
	/**
	 * 查看历史记录按钮
	 */
	private JButton btnHistory=null;
	/**
	 * 获取数据按钮
	 */
	private JButton btnFind=null;
	/**
	 * 导出数据按钮
	 */
	private JButton btnExport=null;
	/**
	 * 清除当前数据按钮
	 */
	private JButton btnClear=null;
	/**
	 * 关闭窗口按钮
	 */
	private JButton btnClose=null;
	/**
	 * 表格
	 */
	private JScrollPane scrollPane=null;
	private JTable tbMaterials=null;
	private JTableListModel tableModel = null;

	private VFVerificationAction vfVerificationAction=null;
	private JTextField tfMergerNo;
	private JLabel label;
	private static final String[] linkedColNames =new String[]{"进口数量(A)","出口数量(B)"};
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	/**
	 * Create the frame.
	 */
	public FmVFContractAnalyse() {
		super();
		vfVerificationAction=(VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkContractAnalyse(new Request(CommonVars.getCurrUser()));
		
		initialize();
		initUI();
		initTable(null);
	}
	
	/**
	 * 初始化控件
	 */
	private void initialize(){
		
		this.setContentPane(getJContentPane());
		this.setTitle("合同分析");
		
	}
	
	private JPanel getJContentPane() {
		if(jContentPane==null){
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getScrollPane(), BorderLayout.CENTER);
		}
		
		return jContentPane;
	}
	
	private JToolBar getJToolBar() {
		if(toolbar==null){
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			toolbar=new JToolBar();
			toolbar.setLayout(f);
			toolbar.add(getlbBatch());
			toolbar.add(getCbbDate());
			toolbar.add(getLabel());
			toolbar.add(getTfMergerNo());
			toolbar.add(getBtnHistory());
			toolbar.add(getBtnFind());
			toolbar.add(getBtnExport());
			toolbar.add(getBtnClear());
			toolbar.add(getBtnClose());
		}
		
		return toolbar;
	}
	
	private JLabel getlbBatch(){
		if(lbBatch==null){
			lbBatch=new JLabel();
			lbBatch.setText("批次设定：");
		}
		return lbBatch;
	}
	private JComboBox getCbbDate() {
		if (cbbVFSection == null) {
			cbbVFSection = new JComboBox();
			cbbVFSection.setPreferredSize(new Dimension(120, 27));
		}
		return cbbVFSection;
	}
	
	private JButton getBtnHistory(){
		if(btnHistory==null){
			btnHistory=new JButton("查询批次数据");
			btnHistory.setPreferredSize(new Dimension(90, 30));
			btnHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFContractAnalyse.this, "请选择需要查询的批次！");
						return;
					}
					VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
					String seqNum = tfMergerNo.getText().trim();					
					List dataSource=vfVerificationAction.findVFContractAnalyseVFBySection(new Request(CommonVars.getCurrUser()), 
							vf,"".equals(seqNum) ? null : Integer.valueOf(seqNum));
					initTable(dataSource);
				}
			});
			
		}
		return btnHistory;
	}
	
	
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton("获取合同分析数据");
			btnFind.setPreferredSize(new Dimension(120, 30));
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFContractAnalyse.this, "请选择需要查询的批次！");
						return;
					}
					int type=JOptionPane.showOptionDialog(null, "确定要先删除本次批次的数据再重新获取吗？","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
					if(type==JOptionPane.OK_OPTION){
						new GetQueryData().execute();
					}else{
						return;
					}
				}
			});
			
		}
		return btnFind;
	}
	
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton("导出excel");
			btnExport.setPreferredSize(new Dimension(90, 30));
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableModel.getRowCount()<=0){
						return;
					}else{
						tableModel.getMiSaveAllToExcel().doClick();
					}
				}
			});
			
		}
		return btnExport;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("清空当前数据");
			btnClear.setPreferredSize(new Dimension(90, 30));
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFContractAnalyse.this, "请选择需要查询的批次！");
						return;
					}
					if(tableModel.getRowCount()<=0){
						return;
					}
					try {
						int type=JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
						if(type==JOptionPane.OK_OPTION){
							VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
							vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCONTRACT_ANALYSE);
							initTable(null);
						}else{
							return;
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(FmVFContractAnalyse.this, "未能成功删除！","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			});
			
		}
		return btnClear;
	}
	
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.setPreferredSize(new Dimension(65, 30));
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbMaterials());
		}
		return scrollPane;
	}
	private JTable getTbMaterials() {
		if (tbMaterials == null) {
			tbMaterials = new JTable();
			tbMaterials.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() ==2){
						int colIdx = tbMaterials.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols,tbMaterials.getColumnModel().getColumn(colIdx));
						if(idx < 0 )	return;
						VFContractAnalyse c = (VFContractAnalyse)tableModel.getCurrentRow();
						VFSection section = c.getSection();
						section.setEndDate(Date.valueOf(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd")));
						if(idx == 0){	//料件
							FmVFCustomsCountImg fm = new FmVFCustomsCountImg();
							fm.showData(section,c.getMergerNo());
							ShowFormControl.refreshInteralForm(fm);
						}else if(idx ==1){	//成品
							FmVFCustomsCountExgConvert fm = new FmVFCustomsCountExgConvert();
							fm.showData(section,c.getMergerNo());
							ShowFormControl.refreshInteralForm(fm);
						}
					}
				}
			});
		}
		return tbMaterials;
	}
	
	/**
	 * 初始化下拉框
	 */
	private void initUI(){
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel();
		//格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List dataSource=vfVerificationAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
		List<VFSection> manulist=new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection=(VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
			manulist.add(vfSection);
		}
		DfComboBox = new DefaultComboBoxModel(manulist.toArray());
		this.cbbVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbVFSection,"verificationNo","endDate",150);
		this.cbbVFSection.setRenderer(CustomBaseRender.getInstance().getRender(
				"verificationNo", "endDate", 50, 100));
		this.cbbVFSection.setSelectedItem(null);
		
	}
	
	private void initTable(List list){
		JTableListModelAdapter adapter = new JTableListModelAdapter() {			
			public List InitColumns() {
				List<JTableListColumn> list=new Vector<JTableListColumn>();
				list.add(addColumn("归并序号", "mergerNo", 60));
				list.add(addColumn("海关料件名称", "hsName", 100));
				list.add(addColumn("海关料件规格", "hsSpec", 140));
				list.add(addColumn("海关计量单位", "hsUnit", 100));
				list.add(addColumn("料件进口平均单价", "unitPrice", 100));
				list.add(addColumn(linkedColNames[0], "impAmount", 100));
				list.add(addColumn(linkedColNames[1], "expAmount", 100));
				list.add(addColumn("合同应余数量", "contractLeaveNum", 100));
				list.add(addColumn("库存数量", "stockAmount", 100));
				list.add(addColumn("溢多数量(A-B)", "overflowAmount", 100));
				list.add(addColumn("短少数量(A-B)", "deficitAmount", 100));
				list.add(addColumn("溢多金额", "overflowPrice", 100));
				list.add(addColumn("短少金额", "deficitPrice", 100));
				return list;
			}
		};
						
		tableModel=new JTableListModel(tbMaterials, list, adapter);		
		TableColumnModel tcm = tbMaterials.getColumnModel();
		linkedCols.clear();
		linkedCols.add(tcm.getColumn(6));
		linkedCols.add(tcm.getColumn(7));
		tcm.getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[0]+"’列可关联【料件数据统计表】";}
		});
		tcm.getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[1]+"’列可关联【成品折算统计表】";}
		});
		//设置可编辑
		adapter.setEditableColumn(5);
		tableModel.setAllowSetValue(true);
		JNumberTableCellEditor editor = new JNumberTableCellEditor(9);
		editor.addPersistListener(new TableCellEditorListener() {			
			@Override
			public void run(TableCellEditorParameter parm) {
				VFContractAnalyse analyse = (VFContractAnalyse)parm.getEditingData();				
				analyse = vfVerificationAction.update(analyse);
				if(analyse.getOverflowAmount() > 0.0)
					analyse.setOverflowPrice(analyse.getUnitPrice() * analyse.getOverflowAmount());
//				if(analyse.getDeficitAmount() > 0.0)
					analyse.setDeficitPrice(analyse.getUnitPrice() * analyse.getDeficitAmount());
				tableModel.updateRow(analyse);
			}
		});			
		tbMaterials.getColumnModel().getColumn(5).setCellEditor(editor); //设置第5列编辑控件	
		//f(tcm.getColumn(10).getCellEditor() > 0)
		
		
//		if(this.tableModel.getCurrentRows().size()>0){
//			VFContractAnalyse vfa = (VFContractAnalyse)tableModel.getCurrentRow();
//			vfa.setDeficitAmount(100.0+200.0);
//			tableModel.updateRow(vfa);
//		}
		
	}
	
	/**
	 * 获取数据线程
	 * @author Administrator
	 *
	 */
	class GetQueryData extends SwingWorker{
  
		@Override
		protected Object doInBackground() throws Exception {
			List dataSource=null;
			try {
				CommonProgress.showProgressDialog(FmVFContractAnalyse.this);
				CommonProgress.setMessage("系统正在删除数据，请稍后...");
				setButtonState(false);
				VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
				//先删除
				vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCONTRACT_ANALYSE);
				CommonProgress.setMessage("系统正在保存数据，请稍后...");
				//后增加，再读取
				vfVerificationAction.gainContractAnalyse(new Request(CommonVars.getCurrUser()), vf);
				CommonProgress.setMessage("系统正在读取数据，请稍后...");
				dataSource = vfVerificationAction.findVFContractAnalyseVFBySection(new Request(CommonVars.getCurrUser()),vf, null);
				return dataSource;
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmVFContractAnalyse.this, "获取并保存数据失败！","警告",JOptionPane.WARNING_MESSAGE);
			}finally{
				CommonProgress.closeProgressDialog();
			}
			return null;
		}
		
		protected void done(){
			List dataSource=null;
			try {	
				dataSource=(List) this.get();		
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			setButtonState(true);
			CommonStepProgress.closeStepProgressDialog();
			initTable(dataSource);
			
		}
	}
	
	public void setButtonState(boolean isEnabled){
		cbbVFSection.setEnabled(isEnabled);
		btnHistory.setEnabled(isEnabled);
		btnFind.setEnabled(isEnabled);
		btnExport.setEnabled(isEnabled);
		btnClear.setEnabled(isEnabled);
		btnClose.setEnabled(isEnabled);
	}
	
	
	private JTextField getTfMergerNo() {
		if (tfMergerNo == null) {
			tfMergerNo = new JTextField();
			tfMergerNo.setPreferredSize(new Dimension(80, 27));
			tfMergerNo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					for (int i = 0; i < str.length(); i++) {
						if (str.charAt(0) < '1' || str.charAt(i) > '9') {
							return;
						}
						if (str.charAt(i) < '0' || str.charAt(i) > '9') {
							return;
						}
					}
					super.insertString(offs, str, a);
				}
			});

		}
		return tfMergerNo;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("归并序号：");
		}
		return label;
	}
	/**
	 * 查询数据并显示与界面
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
		int count = cbbVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbVFSection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbbVFSection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfMergerNo.setText(String.valueOf(mergerNo));
		btnHistory.doClick();
	}
}
