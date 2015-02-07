/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;
/**
 * 盘点核查批次列表窗口
 */
@SuppressWarnings("serial")
public class FmECSSection extends JInternalFrameBase {

	private JSplitPane jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private GroupableHeaderTable jTable = null;

	private JScrollPane jScrollPane = null;

	private CheckCancelAction checkCancelAction = null;  //  @jve:decl-index=0:
	
	private ECSCheckStockAction ecsCheckStockAction = null; 
	/**
	 * 自用核销表格模型
	 */
	private JTableListModel tableModel = null;
	private JPanel pnTop;
	private JPanel pnBottom;
	private JScrollPane scrollPane;
	private GroupableHeaderTable table;
	/**
	 * 盘点批次表格模型
	 */
	private JTableListModel sectionTableModel; 
	
	/**
	 * This is the default constructor
	 */
	public FmECSSection() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars.getApplicationContext().getBean("checkCancelAction");
		ecsCheckStockAction = (ECSCheckStockAction)CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkSection(new Request(CommonVars.getCurrUser()));
		
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(793, 565);
		this.setTitle("盘点核销期设置");
		this.setContentPane(getJContentPane());			
		initTable();
		initSectionTable();
		setState();
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameOpened(InternalFrameEvent e) {				
				loadTable();
			}
		});
	}
	/**
	 * 加载自用核销表头数据
	 */
	private void loadTable(){
		new Thread(new Runnable() {			
			public void run() {
				final List<?> ls = checkCancelAction.findCancelHead(new Request(CommonVars.getCurrUser()),true);
				SwingUtilities.invokeLater(new Runnable() {					
					public void run() {
						tableModel.setList(ls);	
					}
				});
			}
		}).start();
	}
	
	/**
	 * 初始化自用核销表头
	 * @param list
	 */
	private void initTable() {
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = new JTableListModel(table, Collections.EMPTY_LIST,new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("账册编号", "emsNo", 120));
				list.add(addColumn("报核次数", "cancelTimes", 100));
				list.add(addColumn("报核开始日期", "beginDate", 100));
				list.add(addColumn("报核结束日期", "endDate", 100));
				list.add(addColumn("申报日期", "declareDate", 100));				
				list.add(addColumn("创建日期", "createDate", 100));
				list.add(addColumn("创建人", "createPeople", 100));
				list.add(addColumn("备注", "note", 200));
				return list;
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				changeCancelHead();
			}
		});
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g1 = new ColumnGroup("来源于自用核销");
		for(int i = 1 ;i < cm.getColumnCount();i++){
			g1.add(cm.getColumn(i));
		}		
		GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
		header.addColumnGroup(g1);
	}
	/**
	 * 加载盘点核查批次表格数据
	 * @param list
	 */
	private void initSectionTable(){
		sectionTableModel = new JTableListModel(jTable, Collections.EMPTY_LIST, new JTableListModelAdapter() {			
			public List<JTableListColumn> InitColumns(){
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("报核次数", "cancelOwnerHead.cancelTimes", 100));
				list.add(addColumn("批次号", "verificationNo", 100));
				list.add(addColumn("盘点核查开始日期", "beginDate", 120));
				list.add(addColumn("盘点核查结束日期", "endDate", 120));
				list.add(addColumn("盘点数据方式", "stockImportIsHs", 120));
				list.add(addColumn("结转统计方式", "transImportIsHs", 100));
				list.add(addColumn("内购参与统计", "buyIsCount", 120));
				list.add(addColumn("价来源方式", "priceFromType", 240));
				list.add(addColumn("创建日期", "createDate", 100));
				list.add(addColumn("创建人", "createPeople", 100));
				list.add(addColumn("备注", "memo", 200));
				return list;
			}
		});
		sectionTableModel.addTableModelListener(new TableModelListener() {			
			public void tableChanged(TableModelEvent e) {
				setState();
			}
		});
		jTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				String formatValue = "料号级";
				if(value !=null && "true".equals(value)){										
					formatValue = "编码级";					
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		
		jTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				String formatValue = "编码级";
				if(value !=null && "false".equals(value)){										
					formatValue = "料号级";					
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		jTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "否";
				if("true".equals(value)){										
					formatValue = "是";					
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		jTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "短溢分析单价来源平均单价";				
				if(value !=null){ 
					if("1".equals(value.toString())){										
						formatValue = "短溢分析单价来源本报核周期平均单价";
					}else if("2".equals(value.toString())){
						formatValue = "短溢分析单价来源上一期报核周期平均单价";
					}else if("3".equals(value.toString())){
						formatValue = "短溢分析单价来源报关单单价";
					}
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g1 = new ColumnGroup("盘点核查批次");
		for(int i = 1 ;i < cm.getColumnCount();i++){
			g1.add(cm.getColumn(i));
		}		
		GroupableTableHeader header = (GroupableTableHeader) jTable.getTableHeader();
		header.addColumnGroup(g1);
	}
	
	/**
	 * 自用核销表头 选择行后改变处理方法
	 */
	private void changeCancelHead(){
		final CancelOwnerHead obj = (CancelOwnerHead)tableModel.getCurrentRow();
		if(obj == null){
			sectionTableModel.setList(Collections.EMPTY_LIST);
			setState();
		}else{
			new Thread(new Runnable() {
				public void run() {
					final List<ECSSection> ls = ecsCheckStockAction.findEcsSectionByCancelOwnerHead(new Request(CommonVars.getCurrUser()), obj);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							sectionTableModel.setList(ls == null ? Collections.EMPTY_LIST : ls);
							setState();							
						}
					});
				}
			}).start();
		}		
	}
	
	

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JSplitPane getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JSplitPane();
			jContentPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jContentPane.setDividerSize(3);
			jContentPane.setOneTouchExpandable(true);
			jContentPane.setDoubleBuffered(true);
			jContentPane.setDividerLocation(250);
			jContentPane.setTopComponent(getPnTop());
			jContentPane.setBottomComponent(getPnBottom());			
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 *  
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);			
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增核查批次");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					DgECSSection dg = new DgECSSection(DataState.ADD);
					dg.setTableModel(sectionTableModel);
					dg.setHead((CancelOwnerHead)tableModel.getCurrentRow());
					dg.setVisible(true);
				}
			});

		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return jButton1;
	}
	private void edit(){		
		if (sectionTableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmECSSection.this, "请选择你将要修改的记录","提示！", 0);
			return;
		}
		DgECSSection dg = new DgECSSection(DataState.EDIT);
		dg.setHead((CancelOwnerHead)tableModel.getCurrentRow());
		dg.setTableModel(sectionTableModel);
		dg.setVisible(true);
	}
	/**
	 * 设置界面按钮使用状态
	 */
	private void setState() {
		CancelOwnerHead o = (CancelOwnerHead)tableModel.getCurrentRow();
		boolean canAdd = false;
		if(o != null){
			if(o.getBeginDate() !=null && o.getEndDate() != null){
				canAdd = true;
			}
		}
		jButton.setEnabled(canAdd);	//新增
		jButton1.setEnabled(sectionTableModel.getRowCount() > 0); //修改
		jButton2.setEnabled(sectionTableModel.getRowCount() > 0); //删除
	}
	
	/**
	 * 屏蔽按钮
	 */
	private void diableButtons() {		
		jButton.setEnabled(false);	//新增
		jButton1.setEnabled(false); //修改
		jButton2.setEnabled(false); //删除
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					final ECSSection section = (ECSSection) sectionTableModel.getCurrentRow();
					final Request request = new Request(CommonVars.getCurrUser());
					if (section == null) {
						JOptionPane.showMessageDialog(FmECSSection.this,"请选择你要删除的资料", "确认", 1);
						return;
					}
					if(ecsCheckStockAction.isExistEcsCheckDataBySection(request, section)){
						if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(FmECSSection.this,  
								"本批次已存在统计数据，确定删除本批次以及相关的全部统计数据(包括核查文档)吗？","提示",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							new AsynSwingWorker<ECSSection>() {
								@Override
								protected ECSSection submit() {
									request.setTaskId(CommonStepProgress.getExeTaskId());
									CommonStepProgress.showStepProgressDialog(request.getTaskId());
									CommonStepProgress.setStepMessage("正在发送删除请求,请稍等...");
									try{
										ecsCheckStockAction.deleteECSSection(request,section);										
										return section;
									}finally{
										setState();
										CommonStepProgress.closeStepProgressDialog();
									}									
								}
								protected void success(ECSSection section) {
									sectionTableModel.deleteRow(section);
								};
							}.doWork();
						}
						return;
					}
					if (JOptionPane.showConfirmDialog(FmECSSection.this,"确定要删除该盘点批次吗?", "确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						ecsCheckStockAction.deleteECSSection(request,section);
						sectionTableModel.deleteRow(section);
						setState();
					}
				}
			});
		}
		return jButton2;
	}
	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 *  
	 */
	private GroupableHeaderTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});

		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}


	/**
	 * @return Returns the checkCancelAction.
	 */
	public CheckCancelAction getCheckCancelAction() {
		return checkCancelAction;
	}

	/**
	 * @param checkCancelAction
	 *            The checkCancelAction to set.
	 */
	public void setCheckCancelAction(CheckCancelAction checkCancelAction) {
		this.checkCancelAction = checkCancelAction;
	}
	
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(new BorderLayout(0, 0));
			pnTop.add(getScrollPane());
		}
		return pnTop;
	}
	private JPanel getPnBottom() {
		if (pnBottom == null) {
			pnBottom = new JPanel();			
			pnBottom.setLayout(new BorderLayout(0, 0));
			pnBottom.add(getJToolBar(),BorderLayout.NORTH);
			pnBottom.add(getJScrollPane(),BorderLayout.CENTER);
		}
		return pnBottom;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private GroupableHeaderTable getTable() {
		if (table == null) {
			table = new GroupableHeaderTable();
		}
		return table;
	}
   }  //  @jve:decl-index=0:visual-constraint="23,14"
