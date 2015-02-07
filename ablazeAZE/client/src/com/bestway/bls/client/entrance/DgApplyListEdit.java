package com.bestway.bls.client.entrance;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.BillSpecialApplyAction;
import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import javax.swing.JTextField;


/**
 * 商品编辑,修改
 * @author chen
 *
 */
public class DgApplyListEdit extends JDialogBase {

	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	/**
	 * 保存按钮
	 */
	private JButton btnSave = null;
	private JPanel jPanel1 = null;
	/**
	 * 编辑按钮
	 */
	private JButton btnEdit = null;
	/**
	 * 上一个商品
	 */
	private JButton btnPrevious = null;
	/**
	 * 下一个商品
	 */
	private JButton btnNext = null;
	/**
	 * 退出按钮
	 */
	private JButton btnExit = null;
	private JLabel jLabel2 = null;
	
	/**
	 * 窗口状态
	 */
	private int dataState = DataState.EDIT;	
	
	/**
	 * 当前商品列号
	 */
	private int rowNumber;
	/**
	 * 商品列表表格表格模型
	 */
	private JTableListModel applyTableModel = null;

	/**
	 * 仓单特殊申请Action
	 */
	BillSpecialApplyAction billSpecialApplyAction = null;  //  @jve:decl-index=0:
	/**
	 * 商品项号
	 */
	private JTextField tfSeqNo = null;
	private JLabel jLabel1 = null;
	/**
	 * 商品数量
	 */
	private JCustomFormattedTextField tfNumber = null;
	private JLabel jLabel = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	/**
	 * 商品编码
	 */
	private JTextField tfCode = null;
    /**
     * 商品名称
     */
	private JTextField tfName = null;
	/**
	 * 商品规格
	 */
	private JTextField tfSpec = null;
	/**
	 * 商品单位
	 */
	private JTextField tfUnit = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgApplyListEdit() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(494, 297));
        this.setContentPane(getJPanel());        
        // 初始化属性
		billSpecialApplyAction = (BillSpecialApplyAction) CommonVars
				.getApplicationContext().getBean("billSpecialApplyAction");		
	}
	
	public void setState(){
		btnSave.setEnabled(dataState==DataState.BROWSE?false:true);
		btnEdit.setEnabled(dataState==DataState.BROWSE?true:false);
//		btnNext.setEnabled(dataState==DataState.BROWSE?true:false);
//		btnPrevious.setEnabled(dataState==DataState.BROWSE?true:false);	
		tfNumber.setEditable(dataState==DataState.BROWSE?false:true);
		//当前选中行数
		int allRow = applyTableModel.getList().size();
		//先中的第一行行号
//		int startRow = applyTableModel.getCurrRowCount();
		if(rowNumber<=0){
			btnPrevious.setEnabled(false);
		}
		if(rowNumber>=allRow-1){
			btnNext.setEnabled(false);
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int allSize = applyTableModel.getList().size();
					if(rowNumber<0 || rowNumber>=allSize){
						return;
					}
					BillSpecialApplyApplyList applyList = (BillSpecialApplyApplyList)applyTableModel.getList().get(rowNumber);
					applyList.setQtTy(Double.valueOf(tfNumber.getValue().toString()));
					System.out.println("数量："+applyList.getQtTy());
					billSpecialApplyAction.saveApplyList(new Request(CommonVars.getCurrUser()), applyList);
					applyTableModel.updateRow(applyList);
					setDataState(DataState.BROWSE);
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(258, 164, 53, 22));
			jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5.setText("商品单位");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(253, 103, 58, 22));
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4.setText("商品规格");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(41, 103, 60, 22));
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3.setText("商品名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(254, 45, 57, 22));
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setText("商品编码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(64, 164, 37, 22));
			jLabel1.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("数量");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(38, 45, 63, 22));
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("商品项号");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfSeqNo(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfNumber(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getTfCode(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(getTfSpec(), null);
			jPanel1.add(getTfUnit(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDataState(DataState.EDIT);
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnPrevious	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rowNumber--;
					showDate();
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rowNumber++;
					showDate();
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgApplyListEdit.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfGtNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setBounds(new Rectangle(110, 45, 128, 22));
			tfSeqNo.setEditable(false);
		}
		return tfSeqNo;
	}

	/**
	 * 显示商信息
	 */
	public void showDate(){		
		BillSpecialApplyApplyList applyList = (BillSpecialApplyApplyList)applyTableModel.getList().get(rowNumber);
        Integer seqNo = applyList.getSeqNo();
        if(seqNo!=null){
		   tfSeqNo.setText(seqNo.toString());
        }
        tfNumber.setValue(applyList.getQtTy());
        tfCode.setText(applyList.getQtCode());
        tfName.setText(applyList.getQtName());
        tfSpec.setText(applyList.getQtModel());
        tfUnit.setText(applyList.getQtUnit());
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getApplyTableModel() {
		return applyTableModel;
	}

	public void setApplyTableModel(JTableListModel applyTableModel) {
		this.applyTableModel = applyTableModel;
		rowNumber = this.applyTableModel.getCurrRowCount();
	}

	/**
	 * This method initializes tfName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JCustomFormattedTextField getTfNumber() {
		if (tfNumber == null) {
			tfNumber = new JCustomFormattedTextField();
			tfNumber.setBounds(new Rectangle(110, 164, 128, 22));
		}
		return tfNumber;
	}

	/**
	 * This method initializes tfCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(326, 45, 128, 22));
			tfCode.setEditable(false);
		}
		return tfCode;
	}

	/**
	 * This method initializes tfName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(110, 103, 128, 22));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(326, 103, 128, 22));
			tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfUnit	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(new Rectangle(326, 164, 128, 22));
			tfUnit.setEditable(false);
		}
		return tfUnit;
	}

}  //  @jve:decl-index=0:visual-constraint="134,70"
