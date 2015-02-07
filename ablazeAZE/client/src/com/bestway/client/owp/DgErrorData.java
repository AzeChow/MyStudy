package com.bestway.client.owp;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 外发加工申请表，外发货物、收回货物 错误提示窗口
 * @author Administrator
 */
public class DgErrorData extends JDialogBase {

	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private List dataSource = null;
	private Boolean isSend = true;

	/**
	 * This method initializes 
	 * 
	 */
	public DgErrorData() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(715, 317));
        this.setContentPane(getJScrollPane());
        this.setTitle("数据不可保存,有以下错误");
			
	}
	
	/**
	 * 可视化
	 * */
	public void setVisible(boolean isFlag){
		if(isFlag){
			if (dataSource != null) {
				if(isSend){
					initTableSend(dataSource);
				}else{
					initTableRecv(dataSource);
				}
				
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}
	
	
	/**
	 * 初始化表 外发货物
	 * @param dataSource
	 */
	private void initTableSend(List dataSource) {
		
		tableModel = new JTableListModel(jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("错误行号","row", 75));
						list.add(addColumn("手册序号","owpAppSendItem.trNo",75));
						list.add(addColumn("编码","owpAppSendItem.complex.code",75));
						list.add(addColumn("名称","owpAppSendItem.hsName",75));
						list.add(addColumn("规格","owpAppSendItem.hsSpec",75));
						list.add(addColumn("单位","owpAppSendItem.hsName",75));
						list.add(addColumn("错误原因","errinfo", 350));
						return list;
					}
				});;	
	}
	
	/**
	 * 初始化表 收回货物
	 * @param dataSource
	 */
	private void initTableRecv(List dataSource) {
		
		tableModel = new JTableListModel(jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("错误行号","row", 75));
						list.add(addColumn("手册序号","owpAppRecvItem.trNo",75));
						list.add(addColumn("编码","owpAppRecvItem.complex.code",75));
						list.add(addColumn("名称","owpAppRecvItem.hsName",75));
						list.add(addColumn("规格","owpAppRecvItem.hsSpec",75));
						list.add(addColumn("单位","owpAppRecvItem.hsName",75));
						list.add(addColumn("错误原因","errinfo", 350));
						return list;
					}
				});;	
	}

	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"
