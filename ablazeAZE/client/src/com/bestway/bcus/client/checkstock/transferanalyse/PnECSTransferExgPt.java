package com.bestway.bcus.client.checkstock.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

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

import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExg;
import com.bestway.bcus.client.common.CommonProgress;
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
import com.bestway.util.FileUtils;

@SuppressWarnings("serial")
public class PnECSTransferExgPt extends JPanel{
	
	private JToolBar toolBar;
	private JScrollPane scrollPaneP;
	private MultiSpanCellTable tablePrim;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private Request request;
	private JButton btnSwitch;
	private JScrollPane scrollPaneH;
	private JTable tableHand;
	private JTabbedPane tabbedPane;
	private JPanel panelP;
	private JPanel panelH;
	private JTableListModelAdapter adapterConvert;
	private JTableListModel tableModelP;
	private JTableListModelAdapter adapterRawdata;
	private JTableListModel tableModelH;
	private JTableListModel tableModel = null;
	private JLabel label;
	private JTextField tfSeqNum;
	private FmECSTransferExgBalance parent;
	
	
	public PnECSTransferExgPt(FmECSTransferExgBalance parent) {
		this.parent = parent;
		request = new Request(CommonVars.getCurrUser());
		initialize();
	}
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		add(getToolBar(),BorderLayout.NORTH);
		add(getTabbedPane(),BorderLayout.CENTER);
		initJtableP(Collections.EMPTY_LIST);
		initJtableH(Collections.EMPTY_LIST);		
	}
	

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnImport());
			
			toolBar.add(getBtnCalculate());
			toolBar.add(getBtnSwitch());
			
			toolBar.add(getBtnExport());
			
			toolBar.add(getBtnClean());
			
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}
	
	
	void initJtableH(List<?> list){
		if(adapterRawdata==null){
			adapterRawdata = new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("成品料号", "baseExg.ptNo", 110));
					list.add(addColumn("成品序号", "baseExg.seqNum", 120));
					list.add(addColumn("成品版本", "baseExg.version", 110));
					list.add(addColumn("成品已送货未转厂", "baseExg.ptUnTransferNum", 110));
					list.add(addColumn("成品已转厂未送货", "baseExg.ptUnSendferNum", 110));
					list.add(addColumn("料件序号", "seqNum", 120));
					list.add(addColumn("料件名称", "hsName", 120));
					list.add(addColumn("料件规格", "hsSpec", 120));
					list.add(addColumn("计量单位", "hsUnit", 50));
					list.add(addColumn("单耗", "unitWaste", 100));
					list.add(addColumn("损耗", "waste", 100));
					list.add(addColumn("折算已收货未转厂", "hsUnTransferNum", 100));
					list.add(addColumn("折算已转厂未送货", "hsUnSendferNum", 100));					
					return list;
				}
			};
		}
		tableModelH = new JTableListModel(tableHand, list, adapterRawdata);
	}
	
	JTableListModel initJtableP(List<?> list){
		if(adapterConvert==null){
			adapterConvert = new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("料号", "ptNo", 110));
					list.add(addColumn("工厂名称", "ptName", 120));
					list.add(addColumn("工厂规格", "ptSpec", 120));
					list.add(addColumn("计量单位", "ptUnit", 50));
					list.add(addColumn("版本号", "version", 110));
					list.add(addColumn("已送货未转厂", "ptUnTransferNum", 110));
					list.add(addColumn("已转厂未送货", "ptUnSendferNum", 110));
					
					list.add(addColumn("备案序号", "seqNum", 110));
					list.add(addColumn("报关商品名称", "hsName", 120));
					list.add(addColumn("报关商品规格", "hsSpec", 120));
					list.add(addColumn("计量单位", "hsUnit", 50));
					list.add(addColumn("版本号", "version", 110));
					list.add(addColumn("折算已收货未转厂", "hsUnTransferNum", 100));
					list.add(addColumn("折算已转厂未送货", "hsUnSendferNum", 100));
					list.add(addColumn("折算系数", "unitConvert", 100));
					return list;
				}
			};
		}
//		tableModelP = new JTableListModel(tablePrim, list, adapterConvert);
		tableModelP = new AttributiveCellTableModel((MultiSpanCellTable) tablePrim, list, adapterConvert);
		TableColumnModel cm = tablePrim.getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂资料】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		exgGroup.add(cm.getColumn(4));
		exgGroup.add(cm.getColumn(5));
		exgGroup.add(cm.getColumn(6));
		exgGroup.add(cm.getColumn(7));
		ColumnGroup hsImpGroup = new ColumnGroup("【报关资料】");
		hsImpGroup.add(cm.getColumn(8));
		hsImpGroup.add(cm.getColumn(9));
		hsImpGroup.add(cm.getColumn(10));
		hsImpGroup.add(cm.getColumn(11));
		hsImpGroup.add(cm.getColumn(12));
		hsImpGroup.add(cm.getColumn(13));
		hsImpGroup.add(cm.getColumn(14));
		hsImpGroup.add(cm.getColumn(15));

		GroupableTableHeader header = (GroupableTableHeader) tablePrim.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(hsImpGroup);
		return tableModelP;
	}
	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane==null){
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("导入原态", null, getPnRawdata(), null);
			tabbedPane.addTab("成品折料", null, getPnconvert(), null);			
		}
		return tabbedPane;
	}
	
	private JPanel getPnRawdata(){
		if(panelP == null){
			panelP = new JPanel();
			panelP.setLayout(new BorderLayout(0, 0));
			panelP.add(getScrollPaneP(), BorderLayout.CENTER);
		}
		return panelP;
	}
	
	private JPanel getPnconvert(){
		if(panelH == null){
			panelH = new JPanel();
			panelH.setLayout(new BorderLayout(0, 0));
			panelH.add(getScrollPaneH(), BorderLayout.CENTER);
		}
		return panelH;
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
	
	
	
	private JButton getBtnHistroy() {
		if ( btnHistroy == null) {
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(parent.getEcsSection() == null){
						JOptionPane.showMessageDialog(parent, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
					return;
					}
					doFindStars();
				}
			});
		}
		return btnHistroy;
	}	

	private void doFindStars() {
		final String seqNum = tfSeqNum.getText().trim();
		
		new AsynSwingWorker<List<?>>() {		
			int idx = tabbedPane.getSelectedIndex() ;
			protected List<?> submit(){
				try{
					initBtnStatus(0);
					CommonProgress.showProgressDialog(parent);
					CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
					if(idx==1){
						return parent.ecsCheckStockAction.findECSTransferDiffExgResolves(request, parent.getEcsSection(),(seqNum.isEmpty() ?null:Integer.parseInt(seqNum)));
					}
					return parent.ecsCheckStockAction.findECSTransferDiffExgs(request, parent.getEcsSection(),(seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}

			protected void success(List<?> result) {
				if(idx ==1){
					initJtableH(result);	
				}else {
					initJtableP(result);									
				}
			}

		}.doWork();
	}
	/**
	 * 折算海关
	 * @return
	 */
	private JButton getBtnCalculate() {
		if ( btnCalculate == null) {
			btnCalculate = new JButton("2.\u6298\u7B97\u62A5\u5173\u6570\u91CF");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(parent.getEcsSection() == null){
						JOptionPane.showMessageDialog(parent, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
					return;
					}
					tabbedPane.setSelectedIndex(0);					
					doConvertHs();
				}
			});
		}
		return btnCalculate;
	}
	
	private void doConvertHs(){
		new AsynSwingWorker<List<?>>() {
			protected List<?> submit() {
				CommonProgress.showProgressDialog(parent);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");				
				try{
					initBtnStatus(0);
					return parent.ecsCheckStockAction.convertECSTransferDiffExgs(request, parent.getEcsSection());
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			
			protected void success(List<?> result) {
				initJtableP(result);
			};
			
		}.doWork();
	}
	
	private JButton getBtnExport() {
		if ( btnExport == null) {
			btnExport = new JButton("  \u5BFC\u51FAExcel  ");
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = tabbedPane.getSelectedIndex();
					if(index == 1){
						tableModelH = (JTableListModel) tableHand.getModel();
						if ( tableModelH != null && tableModelH.getRowCount() > 0 ) {
							tableModelH.getMiSaveTableListToExcel().doClick();
						}
					}else if(index == 0){
						tableModelP = (JTableListModel) tablePrim.getModel();
						if ( tableModelP != null && tableModelP.getRowCount() > 0 ) {
							tableModelP.getMiSaveTableListToExcel().doClick();
						}
					}					
				}
			});
		}
		return btnExport;
	}
	
	private JButton getBtnClean() {
		if ( btnClean == null) {
			btnClean = new JButton("  \u6E05\u7A7A\u5F53\u524D\u6570\u636E  ");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(parent.getEcsSection() == null){
						JOptionPane.showMessageDialog(parent, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)")){
						try{
							initBtnStatus(0);
							parent.ecsCheckStockAction.deleteECSTransferDiffExgs(request, parent.getEcsSection());
							initJtableP(Collections.EMPTY_LIST);
							initJtableH(Collections.EMPTY_LIST);
						}finally{
							initBtnStatus(2);
						}
						
					}
				}
			});
		}
		return btnClean;
	}
	
	private JButton getBtnClose() {
		if ( btnClose == null) {
			btnClose = new JButton("  \u5173\u95ED  ");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.dispose();
				}
			});
		}
		return btnClose;
	}
	
	
	

	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("1.\u5BFC\u5165\u76D8\u70B9\u6570\u91CF");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(parent.getEcsSection() == null){
						JOptionPane.showMessageDialog(parent, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					try{
						initBtnStatus(0);
						if(parent.ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getEcsSection(), ECSTransferDiffExg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							parent.ecsCheckStockAction.deleteECSTransferDiffExgs(request, parent.getEcsSection());
							initJtableH(Collections.EMPTY_LIST);
							initJtableP(Collections.EMPTY_LIST);
						}
						tabbedPane.setSelectedIndex(0);
						DgECSImport dgECSImport = new DgECSImport(false,false);
						dgECSImport.setFmObj(parent);
						dgECSImport.setVisible(true);
					}finally{
						initBtnStatus(2);
					}					
				}
			});
		}
		return btnImport;
	}
	
	
	
	/**
	 * 成品折算料件
	 * @return
	 */
	private JButton getBtnSwitch() {
		if (btnSwitch == null) {
			btnSwitch = new JButton("3.\u6210\u54C1\u6298\u7B97\u6599\u4EF6\uFF08\u8D26\u518C\u5355\u8017\uFF09");
			btnSwitch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(parent.getEcsSection() == null){
						JOptionPane.showMessageDialog(parent, "批次号不能为空！","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					tabbedPane.setSelectedIndex(1);					
					doConvertHsTo();
				}
			});
		}
		return btnSwitch;
	}

	private void doConvertHsTo(){
		new AsynSwingWorker<List<?>>() {
			protected List<?> submit(){
				CommonProgress.showProgressDialog(parent);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					initBtnStatus(0);
					return parent.ecsCheckStockAction.resolveECSTransferDiffExgResolves(request, parent.getEcsSection());
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(List<?> result) {				
					initJtableH(result);				
			}
			
		}.doWork();
	}
	private JScrollPane getScrollPaneH() {
		if (scrollPaneH == null) {
			scrollPaneH = new JScrollPane();
			scrollPaneH.setViewportView(getTableHand());
		}
		return scrollPaneH;
	}
	private JTable getTableHand() {
		if (tableHand == null) {
			tableHand = new JTable();
		}
		return tableHand;
	}
	
	private JScrollPane getScrollPaneP() {
		if (scrollPaneP == null) {
			scrollPaneP = new JScrollPane();
			scrollPaneP.setViewportView(getTablePrim());
		}
		return scrollPaneP;
	}
	
	private JTable getTablePrim() {
		if (tablePrim == null) {
			tablePrim = new MultiSpanCellTable();
		}
		return tablePrim;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备案序号：");
		}
		return label;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setColumns(10);
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
	 * 
	 * @param i
	 */
	private void initBtnStatus(int i) {
		switch (i) {
		case 0:
			btnCalculate.setEnabled(false);
			btnClean.setEnabled(false);
			btnExport.setEnabled(false);
			btnHistroy.setEnabled(false);
			btnImport.setEnabled(false);
			btnSwitch.setEnabled(false);
			break;
		case 1:
			btnHistroy.setEnabled(true);
			btnImport.setEnabled(true);
			break;
		case 2:
			btnHistroy.setEnabled(true);
			btnImport.setEnabled(true);
			btnCalculate.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnSwitch.setEnabled(true);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 显示数据
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		this.tfSeqNum.setText(seqNum == null ? null : String.valueOf(seqNum));
		this.tabbedPane.setSelectedIndex(1);
		doFindStars();
	}
}
