package com.bestway.dzsc.client.contractanalyse;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.checkcancel.entity.DzscContractBalance;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * 合同月结报表
 * @author 石小凯
 *
 */
public class DgDzscContractBalance extends JDialogBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbEndDate = null;
	private JButton btnQuery = null;
	private JButton btnClose = null;
	private JTableListModel tableModel = null;
	private DzscAnalyseAction contractAnalyseAction = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgDzscContractBalance() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
		.getApplicationContext().getBean("dzscAnalyseAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this
		.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(750, 510);
		this.setTitle("合同月结报表");
		this.setContentPane(getJContentPane());
			
	}
	public void setVisible(boolean b) {
		super.setVisible(b);
	}
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJSplitPane(), gridBagConstraints);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(50);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("截止日期");
			jLabel.setBounds(new Rectangle(234, 17, 56, 18));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbEndDate(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnClose(), null);
			
		}
		return jPanel;
	}
	private JCalendarComboBox getCbEndDate() {
		if (cbEndDate == null) {
			cbEndDate = new JCalendarComboBox();
			cbEndDate.setLocation(new Point(297, 16));
			cbEndDate.setSize(new Dimension(130, 20));
		}
		return cbEndDate;
	}
	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		DzscContractBalance dcb=(DzscContractBalance)list.get(0);
		final List<String> emsNolist=dcb.getContractEmsNo();
		System.out.println("emsNolist="+emsNolist);
		System.out.println("emsNolist.size="+emsNolist.size());
//		System.out.println("ContractTotle.size="+dcb.getContractTotle().size());
//		List<Double> l = dcb.getContractTotle();
//		for(Double d:l){
//		System.out.println("ContractTotle="+d);
//		}
		
		
		
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("料件名称/规格", "commNameSpec", 	150));
						list.add(addColumn("单位", "unitName", 100));
						list.add(addColumn("单价", "commUnitPrice", 100));
						for(int i=0;i<emsNolist.size();i++){
						String s=emsNolist.get(i);
						list.add(addColumn(s,"contract", 100));
						}
						list.add(addColumn("合同总余数", "total", 100));
						return list;
					}
				});
		for(int i=0;i<emsNolist.size();i++){
		 String s=emsNolist.get(i);
			jTable.getColumnModel().getColumn(5+i).setCellRenderer(
			new HashMapTableCellRenderer(s));
		}
	}
	
	//动态根据合同数生成列
	class HashMapTableCellRenderer extends DefaultTableCellRenderer{
		
	    private String emsNo="";
		
		public HashMapTableCellRenderer(String emsNo){
			this.emsNo=emsNo;
		}
		public Component getTableCellRendererComponent(
				JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			
			String newvalue=String.valueOf(value);
			
			String str = newvalue.substring(1,newvalue.length()-1);
			  String[] maps = str.split(",");
			  Map<String,Double> map = new HashMap<String,Double>();
			  for(String ss : maps){
			    String[] kv = ss.split("=");
			    map.put(kv[0].trim(),Double.parseDouble(kv[1]));
			  }
			  System.out.println("map="+map);
			  
//			
			Double totle =0.0;
			
			totle=map.get(emsNo.trim());
			
			if(totle==null){
				super.setText("");
			}
			  
			else{
				super.setText(totle.toString());
				
				System.out.println(emsNo+"合同的余量是："+" " +totle.toString());
				
			}
			
            return this;
		}
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
	 * This method initializes btnQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(499, 11, 96, 28));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date date=null;
					if (cbEndDate.getDate() != null) { // 截止日期
						date = cbEndDate.getDate();
					}
//					List list = contractAnalyseAction.findDzscContractBalance(new Request(
//							CommonVars.getCurrUser()),date);
//					initTable(list);
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(608, 11, 97, 28));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
