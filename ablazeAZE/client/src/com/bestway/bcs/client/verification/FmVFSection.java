package com.bestway.bcs.client.verification;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.DgAbout;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;


public class FmVFSection extends JInternalFrameBase {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	/**
	 * 新增
	 */
	private JButton btnInsert;
	
	/**
	 * 删除
	 */
	private JButton btnDelete;
	/**
	 *数据MODE
	 */
	private JTableListModel tableMode;

	private DgVFSection dgVFSection;

	private Request request;
	private VFVerificationAction vfAction;
	protected JTableListModel tableModel;
	private JButton btnModify;

	/**
	 * Create the frame.
	 */
	public FmVFSection() {
		request = new Request(CommonVars.getCurrUser());
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkSection(request);
		
		initialize();
	}

	/**
	 * 初始化
	 * 
	 */
	
	private void initialize() {
		this.setTitle("核查批次设定");
		getContentPane().setLayout(new BorderLayout(0, 0));

		table = new JTable();

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		toolBar.add(getBtnInsert());
		toolBar.add(getBtnModify());
		toolBar.add(getBtnDelete());
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
		initTable();
	}

	/**
	 * 增加
	 * @return
	 */
	public JButton getBtnInsert(){
		if(btnInsert == null){
			btnInsert = new JButton("    \u65B0 \u589E     ");
			btnInsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// new insert
					DgVFSection dg = new DgVFSection(DataState.ADD);
					dg.setTableModel(tableMode);					
					dg.setVisible(true);
					initTable();
				}
			});
		}
		return btnInsert;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JButton getBtnDelete(){
		if(btnDelete == null){
			btnDelete = new JButton("    \u5220 \u9664    ");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(FmVFSection.this, "确定需要删除本次批次的所有数据吗?\r\n包括合同数据，盘点数据和结转数据?","提示", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否"))
					{
					if (tableMode != null && tableMode.getCurrentRow() != null) {
						List<VFSection> list = (List<VFSection>) tableMode.getCurrentRows();
						VFSection section = (VFSection) list.get(0);
						if (list != null && list.size() == 1) {
							// 单行删除
							tableMode.deleteRow(section);
							vfAction.deleteAttachmentAll(request, section);
							vfAction.deleteVFSection(request, section);
						} else {
							JOptionPane.showMessageDialog(FmVFSection.this, "不能删除多行数据！", "提示!",
									JOptionPane.INFORMATION_MESSAGE);
							// 多行删除
							//tableMode.deleteRows(list);
							//vfAction.deleteVFSectionByList(request, list);
						}
					//还要删除次模块下的所有实体数据
					}
					}
				}
			});
		}
		return btnDelete;
	}
	
	
	
	/**
	 * This method initializes Table
	 * 批次设定
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void initTable() {
		List dataSource = vfAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
		if (dataSource == null || dataSource.size() <= 0) {
			dataSource = new Vector();
		}
		
		tableMode = new JTableListModel(table, dataSource, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("核查批次", "verificationNo", 60));
				list.add(addColumn("截止时间", "endDate", 100));
				list.add(addColumn("结转差额分析文本导入方式", "isImportHs", 200));
				list.add(addColumn("内购库存是否参与统计","buyIsCount",160));
				list.add(addColumn("创建时间", "createDate", 100));
				list.add(addColumn("创建人", "createPeople", 100));
				list.add(addColumn("备注", "memo", 300));
				return list;
			}
		});
		
		table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				String formatValue = "料号级";
				if("true".equals(value)) {
					formatValue = "编码级";
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,
						row, column);
			}
		});
		table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				String formatValue = "否";
				if("true".equals(value)) {
					formatValue = "是";
				}
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,
						row, column);
			}
		});
	}


	private JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton("    修 改    ");
			btnModify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableMode.getCurrentRow()==null){
						JOptionPane.showMessageDialog(FmVFSection.this, "请选择你要修改的资料！");
						return;
					}
					DgVFSection dgModify =new DgVFSection(DataState.EDIT);
					dgModify.setTableModel(tableMode);
					dgModify.setVisible(true);
					tableMode.fireTableDataChanged();
//					initTable();
				}
			});
		}
		return btnModify;
	}
}
