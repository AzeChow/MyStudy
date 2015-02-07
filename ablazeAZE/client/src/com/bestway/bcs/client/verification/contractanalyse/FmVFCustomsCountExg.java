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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFCustomsCountExg;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFCustomsCountExg extends JInternalFrameBase {


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
	private JLabel lblNewLabel;
	private JTextField tfMergerNo;
	private static final String[] linkedColNames = new String[]{"本期总出口量"};
	/**
	 * Create the frame.
	 */
	public FmVFCustomsCountExg() {
		super();
		vfVerificationAction=(VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkCustomsCountExg(new Request(CommonVars.getCurrUser()));
		initialize();	
		initUIComboBox();
		initTable(null);
	}
	
	/**
	 * 初始化控件
	 */
	private void initialize(){
		
		this.setContentPane(getJContentPane());
		this.setTitle("成品数据统计表");
		
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
			toolbar.add(getLblNewLabel());
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
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "请选择需要查询的批次！");
						return;
					}
					VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
					Integer mergerNo = null;
					if (!"".equals(tfMergerNo.getText().trim())) {
						mergerNo = CommonUtils.getInteger(tfMergerNo.getText()
								.trim());
					}
					List dataSource=vfVerificationAction
							.findVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG,mergerNo);
					initTable(dataSource);
				}
			});
			
		}
		return btnHistory;
	}
	
	
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton("统计成品明细数据");
			btnFind.setPreferredSize(new Dimension(120, 30));
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "请选择需要查询的批次！");
						return;
					}
					int type=JOptionPane.showOptionDialog(null, "确定要先删除本次批次的数据再重新获取吗？","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
					if(type==JOptionPane.OK_OPTION){
						VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
						boolean isTrue=vfVerificationAction.isExistVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
						if(isTrue){	
							JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "该批次被成品折料所引用，请先删除【成品折算统计表】中该批次的数据！");
							return;
						}else{
							new GetQueryData().execute();
						}	
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
						JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "请选择需要查询的批次！");
						return;
					}
					if(tableModel.getRowCount()<=0){
						return;
					}
					try {
						int type=JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
						if(type==JOptionPane.OK_OPTION){
							VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
							boolean isTrue=vfVerificationAction.isExistVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
							if(isTrue){	
								JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "该批次被成品折料所引用，不能删除！");
								return;
							}else{
								vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG);
								initTable(null);
							}
							
						}else{
							return;
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "未能成功删除！","警告",JOptionPane.WARNING_MESSAGE);
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
				/**
				 * 双击穿透报表
				 */
				public void mouseClicked(MouseEvent e) {					
					if(e.getClickCount() ==2){
						int colIdx = tbMaterials.columnAtPoint(e.getPoint());
						if(colIdx < 0)	
							return;
						int idx = CommonUtils.indexOf(linkedColNames,tbMaterials.getColumnName(colIdx));
						if(idx < 0 )	
							return;
						VFCustomsCountExg  aly = (VFCustomsCountExg)tableModel.getCurrentRow();
						VFSection section = aly.getSection();
						section.setEndDate(Date.valueOf(CommonUtils.getDate(section.getEndDate(), "yyyy-MM-dd")));
						
						FmVFDeclarationCommInfoExg fm = new FmVFDeclarationCommInfoExg();						
						fm.showData(aly.getSection(), aly.getEmsNo(),aly.getSeqNum());
						ShowFormControl.refreshInteralForm(fm);
					}
				}
			});
		}
		return tbMaterials;
	}
	
	/**
	 * 初始化下拉框
	 */
	private void initUIComboBox(){
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
	
	void initTable(List list){
		tableModel=new JTableListModel(tbMaterials, list, new JTableListModelAdapter() {

			public List InitColumns() {
				List<JTableListColumn> list=new Vector<JTableListColumn>();
				list.add(addColumn("备案序号", "seqNum", 100));
				list.add(addColumn("手册号", "emsNo", 100));
				list.add(addColumn("企业名称", "companyName", 100));
				list.add(addColumn("申报日期", "declarationDate", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("商品规格", "commSpec", 100));
				list.add(addColumn("单位", "unit", 100));	
				list.add(addColumn(linkedColNames[0], "expTotalAmont", 100));
				list.add(addColumn("本期总出口金额", "expTotalMoney", 100));
				return list;
			}
		});
		
		tbMaterials.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() { return "双击‘"+linkedColNames[0]+"’可关联【成品报关明细表】";}
		});
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
				CommonProgress.showProgressDialog(FmVFCustomsCountExg.this);
				CommonProgress.setMessage("系统正在删除数据，请稍后...");
				setButtonState(false);
				//先删除
				VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
				vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG);
				//后增加，再查询
				CommonProgress.setMessage("系统正在保存数据，请稍后...");
				vfVerificationAction.gainVFCustomsCountExg(new Request(CommonVars.getCurrUser()), vf);
				CommonProgress.setMessage("系统正在读取数据，请稍后...");
				Integer mergerNo = null;
				if (!"".equals(tfMergerNo.getText().trim())) {
					mergerNo = CommonUtils.getInteger(tfMergerNo.getText()
							.trim());
				}
				dataSource=vfVerificationAction
						.findVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG,mergerNo);
				return dataSource;
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmVFCustomsCountExg.this, "获取并保存数据失败！","警告",JOptionPane.WARNING_MESSAGE);
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
	
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("归并序号：");
		}
		return lblNewLabel;
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
	
	/**
	 * 显示数据
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
