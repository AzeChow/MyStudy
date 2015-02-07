/*
 * Created on 2004-7-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author bsway
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFourInnerMerge extends JDialogBase
{

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JTextField tfFourCommodityCode = null;
	private CommonBaseCodeAction commonBaseCodeAction=null;
	private MultiSpanCellTable jTable;
	private List selectedRows;
	private boolean isNew = false;
	/**
	 * This method initializes 
	 * 
	 */
	public DgFourInnerMerge() {
		super();
		initialize();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
		.getApplicationContext().getBean("commonBaseCodeAction");
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJContentPane());
        this.setTitle("4位商品归并");
        this.setSize(338, 164);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    	this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (!isNew()) {//添加10位归并
					if (selectedRows != null) {
						for (int i = 0; i < selectedRows.size(); i++) {
							InnerMergeData data = (InnerMergeData) selectedRows
									.get(i);							
							if (data.getHsFourMaterielName() != null
									&& !data.getHsFourMaterielName()
											.equals("")) {
								tfFourCommodityCode.setText(data
										.getHsFourMaterielName());
								break;
							}							
						}
					}					
				} 
				else {//新增10位归并
					if (selectedRows != null) {
						for (int i = 0; i < selectedRows.size(); i++) {
							InnerMergeData data = (InnerMergeData) selectedRows
									.get(i);
							if (data.getHsAfterMaterielNameSpec() != null
									&& !data.getHsAfterMaterielNameSpec()
											.equals("")) {
								tfFourCommodityCode.setText(data
										.getHsAfterMaterielNameSpec());
								break;
							}
						}
					}
				}
			}
		});
			
	}
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(24, 13, 290, 69);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jLabel.setText("4位商品名称");
			jLabel.setBounds(18, 28, 82, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getTfFourCommodityCode(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(152, 97, 69, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if(!tfFourCommodityCode.getText().equals(""))
					{
						List selectedRows = getSelectedRows();
						if(selectedRows == null || selectedRows.size()<=0 ){
							return;
						}
						List ls = commonBaseCodeAction.fourInnerMerge(new Request(CommonVars.getCurrUser()),
						selectedRows,tfFourCommodityCode.getText(), isNew());
						refreshTable(ls);
						DgFourInnerMerge.this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(DgFourInnerMerge.this,
								"4位商品不能为空，请输入", "警告！", 0);
						return;
					}
				}
			});
		}
		return btnOk;
	}
	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(235, 97, 69, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DgFourInnerMerge.this.dispose();
				}
			});
		}
		return btnCancel;
	}
	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
				.getModel();
		tableModel.updateRows(ls);
		((MultiSpanCellTable) jTable).combineRows(new int[] { 11, 6 },
				new int[] { 11,12,13 });
		((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6,7,8,9,10});
	}
	/**
	 * This method initializes tfFourCommodityCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTfFourCommodityCode() {
		if (tfFourCommodityCode == null) {
			tfFourCommodityCode = new JTextField();
			tfFourCommodityCode.setBounds(110, 26, 163, 22);
		}
		return tfFourCommodityCode;
	}
	
	/**
	 * @return Returns the isNew.
	 */
	public boolean isNew()
	{
		return isNew;
	}
	/**
	 * @param isNew The isNew to set.
	 */
	public void setNew(boolean isNew)
	{
		this.isNew = isNew;
	}
	/**
	 * @return Returns the jTable.
	 */
	public MultiSpanCellTable getJTable()
	{
		return jTable;
	}
	/**
	 * @param table The jTable to set.
	 */
	public void setJTable(MultiSpanCellTable table)
	{
		jTable = table;
	}
	/**
	 * @return Returns the selectedRows.
	 */
	public List getSelectedRows()
	{
		return selectedRows;
	}
	/**
	 * @param selectedRows The selectedRows to set.
	 */
	public void setSelectedRows(List selectedRows)
	{
		this.selectedRows = selectedRows;
	}
     }  //  @jve:decl-index=0:visual-constraint="10,10"
