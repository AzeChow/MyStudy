package com.bestway.bcs.client.verification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFCategory;
import com.bestway.bcs.verification.entity.VFCategoryBcsTenInnerMerge;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

/**
 * 大类名称对应关系表
 * @author xc
 * 
 */
public class FmVFCategoryBcsTenInnerMerge extends JInternalFrameBase {
	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private JPanel panel;
	private JToolBar toolBar;
	private JPanel northPanel;
	private JScrollPane scrollPane;
	private MultiSpanCellTable table;
	private JButton button;
	private VFVerificationAction action;
	private AttributiveCellTableModel tableModel;
	private Request request = null;
	private JPopupMenu tbContxtMenu;
	
	/************右键菜单项***************/	
	private JMenuItem miAddCategory;	//新增大类
	private JMenuItem miModifyCategory;	//修改大类
	private JMenuItem miSelCategory;	//补充对应关系（大类选择）	
	private JMenuItem miCancel;			//取消大类名次对应关系
	private JMenuItem miDelete;			//删除
	private JPanel centerPanel;
	private JToolBar tbarPaging;
	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;
	
	public PnCommonQueryPage getPnCommonQueryPage() {
		if(pnCommonQueryPage == null){
			pnCommonQueryPage = new PnCommonQueryPage() {
				
				public JTableListModel initTable(List dataSource) {
					FmVFCategoryBcsTenInnerMerge.this.initTable(dataSource);
					return FmVFCategoryBcsTenInnerMerge.this.tableModel;
				}
				
				public List getDataSource(int index, int length, String property,Object value, boolean isLike) {
					return action.findVFCategoryBcsTenInnerMerge(request,index,length,property,value,isLike);
				}
			};
		}
		return pnCommonQueryPage;
	}
	
	public FmVFCategoryBcsTenInnerMerge() {
		initialize();
		addInternalFrameListener(new InternalFrameAdapter() {			
			public void internalFrameOpened(InternalFrameEvent e) {
				loadData();					
			}
		});
	}
	
	private void initialize() {
		request = new Request(CommonVars.getCurrUser());
		action = (VFVerificationAction)CommonVars.getApplicationContext().getBean("vfVerificationAction");
		setTitle("大类名称对应表");
		setContentPane(getPanel());			
	}
	/**
	 * 加载表格数据
	 */
	private void loadData(){
//		new AsynSwingWorker<List<VFCategoryBcsTenInnerMerge>>() {
//			protected List<VFCategoryBcsTenInnerMerge> submit() {						
//				return action.findVFCategoryBcsTenInnerMerge(request);
//			}
//			protected void success(List<VFCategoryBcsTenInnerMerge> result) {
//				initTable(result);
//			}
//		}.doWork();
		pnCommonQueryPage.setInitState();
	}
	
	/**
	 * 初始化表格
	 * @param list
	 */
	private void initTable(List list){
		list = (list == null ? Collections.EMPTY_LIST : list);
		if(tableModel == null){
			tableModel = new AttributiveCellTableModel(table, list, new JTableListModelAdapter() {				
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> columns = new ArrayList<JTableListColumn>();					
					columns.add(new JTableListColumn("归并序号","bcsTenInnerMerge.seqNum",60));
					columns.add(new JTableListColumn("报关名称","bcsTenInnerMerge.name",120));
					columns.add(new JTableListColumn("报关规格","bcsTenInnerMerge.spec",200));
					columns.add(new JTableListColumn("计量单位","bcsTenInnerMerge.comUnit.name",60));
					
					columns.add(new JTableListColumn("大类序号","category.seqNum",60));
					columns.add(new JTableListColumn("商品编码","category.complexCode",120));
					columns.add(new JTableListColumn("商品名称","category.complexName",120));
					columns.add(new JTableListColumn("商品规格","category.spec",200));
					columns.add(new JTableListColumn("计量单位","category.unit",60));
					return columns;
				}
			});
			GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
			ColumnGroup g = new ColumnGroup("报关商品");
			for(int i = 1; i <= 4;i++)
				g.add(table.getColumnModel().getColumn(i));
			header.addColumnGroup(g);			
			g = new ColumnGroup("大类别");
			for(int i = 5; i < table.getColumnCount(); i++)
				g.add(table.getColumnModel().getColumn(i));
			header.addColumnGroup(g);
			tableModel.addTableModelListener(new TableModelListener() {				
				public void tableChanged(TableModelEvent e) {
					setColor(table);
				}
			});				
		}else{			
			tableModel.setList(list);
		}
		deleteTableContextPopupListener();		
	}
	
	private void deleteTableContextPopupListener(){
		MouseListener[] mouseListeners = table.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof CommonTableContextPopupEvent) {
				table.removeMouseListener(mouseListeners[i]);
			}
		}
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getNorthPanel(), BorderLayout.NORTH);
			panel.add(getCenterPanel(), BorderLayout.CENTER);
		}
		return panel;
	}
	
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getButton());
		}
		return toolBar;
	}
	private JPanel getNorthPanel() {
		if (northPanel == null) {
			northPanel = new JPanel();
			northPanel.setLayout(new BorderLayout(0, 0));
			northPanel.add(getToolBar(), BorderLayout.NORTH);
		}
		return northPanel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private MultiSpanCellTable getTable() {
		if (table == null) {
			table = new MultiSpanCellTable();
			//设置列选择模式，分为报关商品区域列选择以及大类别区域列选择
			table.getColumnModel().addColumnModelListener(new TableColumnModelListener() {				
				public void columnSelectionChanged(ListSelectionEvent e) {
					int col = table.getSelectedColumn();
					if(col >= 0 && col <= 4){
						table.setColumnSelectionInterval(1, 4);
					}else if(col >= 5 && col < table.getColumnCount()){
						table.setColumnSelectionInterval(5, table.getColumnCount()-1);
					}
				}				
				public void columnRemoved(TableColumnModelEvent e) {}
				public void columnMoved(TableColumnModelEvent e) {}
				public void columnMarginChanged(ChangeEvent e) {}
				public void columnAdded(TableColumnModelEvent e) {}
			});
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//不是右键点击的时候不做任何处理
					if(e.getButton() != MouseEvent.BUTTON3){
						return;
					}
					List<VFCategoryBcsTenInnerMerge> sels = tableModel.getCurrentRows();
					getMiAddCategory().setEnabled(true);
					getMiModifyCategory().setEnabled(true);
					getMiSelCategory().setEnabled(true);
					getMiCancel().setEnabled(true);
					getMiDelete().setEnabled(true);									
					getTableContextMenu().show(table, e.getX(), e.getY());
					boolean flag = false;
					for(VFCategoryBcsTenInnerMerge sel : sels){
						if(sel.getCategory() == null) flag = true;						
					}
					
					if(sels.size() > 1 || flag)
						getMiModifyCategory().setEnabled(false);
					
					if(flag){												
						getMiCancel().setEnabled(false);
					}else{						
						getMiAddCategory().setEnabled(false);
						getMiSelCategory().setEnabled(false);
					}
				}
			});
			
			initTable(null);			
		}
		return table;
	}
	
	private JButton getButton() {
		if (button == null) {
			button = new JButton("新增报关商品");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final List<BcsTenInnerMerge> list = showBcsTenInnerMergeDialog();
					if(list != null){
						new AsynSwingWorker<List>() {
							protected List submit() {
								return action.addBcsTenInnerMergeToCategory(request,list);
							}
							protected void success(List result) {
								tableModel.getList().addAll(result);
								initTable(tableModel.getList());								
							}
						}.doWork();						
					}
				}
			});
		}
		return button;
	}
	
	/**
	 * 获取表格右键菜单
	 * @return
	 */
	public JPopupMenu getTableContextMenu(){
		if(tbContxtMenu == null){
			tbContxtMenu = new JPopupMenu();
			tbContxtMenu.add(getMiAddCategory());
			tbContxtMenu.add(getMiModifyCategory());
			tbContxtMenu.add(getMiSelCategory());
			tbContxtMenu.addSeparator();
			tbContxtMenu.add(getMiCancel());
			tbContxtMenu.add(getMiDelete());
			if(tableModel != null){
				tbContxtMenu.addSeparator();
				tbContxtMenu.add(tableModel.getMiCopy());
				tbContxtMenu.add(tableModel.getMiCopyValue());
				tbContxtMenu.add(tableModel.getMiSearch());
				tbContxtMenu.add(tableModel.getMiSaveTableListToExcel());
			}
		}
		return tbContxtMenu;
	}
	
	/***************右键项*************/
	public JMenuItem getMiAddCategory() {
		if(miAddCategory == null){
			miAddCategory = new JMenuItem("新增大类");
			miAddCategory.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					final List<VFCategoryBcsTenInnerMerge> categoryInnerMerges = tableModel.getCurrentRows();
					BcsTenInnerMerge im = categoryInnerMerges.get(0).getBcsTenInnerMerge();
					DgVFCategory dg = new DgVFCategory();
					dg.getTfCode().setText(im.getComplex().getCode());
					dg.getTfName().setText(im.getName());
					dg.getTfSpec().setText(im.getSpec());
					dg.setUnit(im.getComUnit().getName());
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
					if(dg.isOk()){
						final VFCategory category = dg.getCategory();
						new AsynSwingWorker<List>() {							
							protected java.util.List submit() {
								return action.addCategoryToBcsTenInnerMergeCategory(request, categoryInnerMerges, category, true);		
							}
							protected void success(List result) {
								tableModel.updateRows(result);
							}							
						}.doWork();
					}
				}
			});
		}
		return miAddCategory;
	}

	public JMenuItem getMiModifyCategory() {
		if(miModifyCategory == null){
			miModifyCategory = new JMenuItem("修改大类");
			miModifyCategory.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					VFCategoryBcsTenInnerMerge cbim = (VFCategoryBcsTenInnerMerge)tableModel.getCurrentRow();
					if(cbim==null){
						JOptionPane.showMessageDialog(FmVFCategoryBcsTenInnerMerge.this, 
								"请选择一笔数据!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgVFCategory dg = new DgVFCategory();					
					dg.setCategory(cbim.getCategory());
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					if(dg.isOk()){						
						VFCategory category = dg.getCategory();
						category = action.saveVFCategory(request, category);
						loadData();
					}
				}
			});
		}
		return miModifyCategory;
	}


	public JMenuItem getMiSelCategory() {
		if(miSelCategory == null){
			miSelCategory = new JMenuItem("补充报关与大类对应");
			miSelCategory.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					final VFCategory category = showCategoryDialog();
					if(category != null){
						new AsynSwingWorker<List<?>>(){
							List<VFCategoryBcsTenInnerMerge> sels = tableModel.getCurrentRows();
							protected List<?> submit() {
								return action.addCategoryToBcsTenInnerMergeCategory(request, sels, category, false);
							}
							protected void success(List<?> result) {
								tableModel.updateRows(result);
							}
						}.doWork();
					}
				}
			});
		}
		return miSelCategory;
	}

	public JMenuItem getMiCancel() {
		if(miCancel == null){
			miCancel = new JMenuItem("撤销报关与大类对应");
			miCancel.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getContentPane(), "确定要撤销报关与大类对应？","提示", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否"))
						return;
					new AsynSwingWorker<List<?>>(){
						List<VFCategoryBcsTenInnerMerge> sels = tableModel.getCurrentRows();
						protected List<?> submit() {
							return action.undoVFCategoryBcsTenInnerMerge(request,sels);
						}
						protected void success(List<?> result) {
							tableModel.updateRows(result);
						}
					}.doWork();
				}
			});
		}
		return miCancel;
	}
	//删除对应关系
	public JMenuItem getMiDelete() {
		if(miDelete == null){
			miDelete = new JMenuItem("删除");
			miDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getContentPane(), "确定要删除报关与大类对应？","提示", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否"))
						return;
					new AsynSwingWorker(){
						List<VFCategoryBcsTenInnerMerge> sels = tableModel.getCurrentRows();
						protected Object submit() {
							action.deleteVFCategoryBcsTenInnerMerge(request,sels);
							return null;
						}
						protected void success(Object result) {
							tableModel.deleteRows(sels);
						}
					}.doWork();
				}
			});
		}
		return miDelete;
	}

	/***************右键项结束*************/
	
	/**
	 * 显示大类选择框
	 * @return
	 */
	private VFCategory showCategoryDialog(){
		DgCommonQueryPage dg = new DgCommonQueryPage(){
			public List getDataSource(int index, int length, String property,Object value, boolean isLike) {				 
				return action.findVFCategory(request,index,length,property,value,isLike);
			}			
		};
		List<JTableListColumn> columns = new ArrayList<JTableListColumn>();
		columns.add(new JTableListColumn("大类序号", "seqNum", 60));
		columns.add(new JTableListColumn("商品编码", "complexCode", 80));
		columns.add(new JTableListColumn("商品名称", "complexName", 100));
		columns.add(new JTableListColumn("商品规格", "spec", 100));
		columns.add(new JTableListColumn("计量单位", "unit", 80));
		DgCommonQueryPage.setTableColumns(columns);
		dg.setTitle("大类名称");
		DgCommonQueryPage.setSingleResult(true);
		dg.setVisible(true);
		if(dg.isOk()){			
			return (VFCategory)dg.getReturnValue();
		}
		return null;
	}
	/**
	 * 显示十位商品编码选择框
	 * @return
	 */
	private List<BcsTenInnerMerge> showBcsTenInnerMergeDialog(){
		DgCommonQueryPage dg = new DgCommonQueryPage(){
			public List getDataSource(int index, int length, String property,Object value, boolean isLike) {				 
				return action.findBcsTenInnerNotInCategory(request,index,length,property,value,isLike);
			}			
		};
		List<JTableListColumn> columns = new ArrayList<JTableListColumn>();
		columns.add(new JTableListColumn("归并序号", "seqNum", 60));
		columns.add(new JTableListColumn("编码", "complex.code", 80));
		columns.add(new JTableListColumn("商品名称", "name", 100));
		columns.add(new JTableListColumn("商品规格", "spec", 100));
		columns.add(new JTableListColumn("常用单位", "comUnit.name", 80));
		DgCommonQueryPage.setTableColumns(columns);
		dg.setTitle("报关商品资料");
		DgCommonQueryPage.setSingleResult(false);
		dg.setVisible(true);
		if(dg.isOk()){			
			return dg.getReturnList();
		}
		return null;
	}

	
	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public ColorTableCellRenderer() {}
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			boolean isChange = false;
			if (checkValue(table, row, column)) {
				c.setForeground(new Color(0, 0, 204));
				c.setBackground(table.getBackground());
				isChange = true;
			}
			if (isSelected) {
				c.setForeground(table.getSelectionForeground());
				c.setBackground(table.getSelectionBackground());
			}
			if (isChange == false && !isSelected) {
				c.setForeground(table.getForeground());
				c.setBackground(table.getBackground());
			}
			return c;
		}
	}

	private void setColor(JTable jTable) {
		JTableListModel tableModel = (JTableListModel) jTable.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		JTableListModel tableModel = (JTableListModel) table.getModel();
		VFCategoryBcsTenInnerMerge data = (VFCategoryBcsTenInnerMerge) tableModel.getDataByRow(row);
		if (data == null) {
			return false;
		}
		Date currentDate = new Date();
		if (data.getCreateDate() != null) {
			if (bartDateFormat.format(data.getCreateDate()).equals(bartDateFormat.format(currentDate))) {
				return true;
			}
		}
		return false;
	}
	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout(0, 0));
			centerPanel.add(getScrollPane());
			centerPanel.add(getTbarPaging(), BorderLayout.NORTH);
		}
		return centerPanel;
	}
	private JToolBar getTbarPaging() {
		if (tbarPaging == null) {
			tbarPaging = new JToolBar();
			tbarPaging.setFloatable(false);
			tbarPaging.add(getPnCommonQueryPage());
		}
		return tbarPaging;
	}
}
