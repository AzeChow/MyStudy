/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBlsTenResetNo extends JDialogBase {

	private JPanel		jContentPane		= null;
	private JTextField	tfRowNumber			= null;
	private JButton		btnOK				= null;
	private JButton		btnCancel			= null;
	private JPanel		jPanel				= null;
	private List		currentRows			= null;
	private boolean		isOk				= false;
	BlsInnerMergeAction	blsInnerMergeAction	= (BlsInnerMergeAction) CommonVars
													.getApplicationContext()
													.getBean(
															"blsInnerMergeAction");

	/**
	 * This method initializes
	 * 
	 */
	public DgBlsTenResetNo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("重排行号到");
		this.setSize(351, 151);
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
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
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(255, 88, 59, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					DgBlsTenResetNo.this.dispose();
				}
			});
		}
		return btnCancel;
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
			jPanel.setBounds(17, 11, 304, 64);
			jLabel.setText("重排行号到");
			jLabel.setBounds(28, 24, 60, 18);
			jPanel.add(jLabel, null);
			jPanel.add(getTfRowNumber(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
		}
		return jPanel;
	}

	/**
	 * @return Returns the currentRows.
	 */
	public List getCurrentRows() {
		return currentRows;
	}

	/**
	 * @param currentRows
	 *            The currentRows to set.
	 */
	public void setCurrentRows(List currentRows) {
		this.currentRows = currentRows;
	}

	/**
	 * This method initializes tfRowNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRowNumber() {
		if (tfRowNumber == null) {
			tfRowNumber = new JTextField();
			tfRowNumber.setBounds(102, 22, 163, 22);
			tfRowNumber.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == 10) {
						isOk = true;
						new CustomInnerMergeDataThread().start();
					}
				}
			});
		}
		return tfRowNumber;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(190, 88, 59, 23);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					new CustomInnerMergeDataThread().start();
					isOk = true;
				}
			});
		}
		return btnOK;
	}

	class CustomInnerMergeDataThread extends Thread {
		public void run() {
			try {
				resetInnerMergeData();
			} catch (Exception e) {
				e.printStackTrace() ;
				JOptionPane.showMessageDialog(null, "数据重排条件可能有错!!", "信息!!", 0);
			}
		}
	}

	/**
	 * 重排数据
	 */
	private void resetInnerMergeData() {
		if (!checkToRowNumber()) {
			return;
		}
		int toRowNumber = Integer.parseInt(tfRowNumber.getText());
		if (!this.checkTenInnerMergeSort(toRowNumber)) {
			return;
		}
//		List list = blsInnerMergeAction.findBlsInnerMergeInContract(
//				new Request(CommonVars.getCurrUser()), currentRows);
//		if (list !=null && list.size() >0) {			
//			JOptionPane.showMessageDialog(this, "选中的行中有数据在合同被引用,不能重排!!",
//					"警告!!", JOptionPane.WARNING_MESSAGE);
//			return;
//		}
		tenResetData(toRowNumber);
	}

	/**
	 * 十位重排
	 */
	private void tenResetData(int toRowNumber) {
		try {
			this.btnOK.setEnabled(false);
			CommonProgress.showProgressDialog(DgBlsTenResetNo.this);
			CommonProgress.setMessage("正在进行十位数据重排,请稍候...");
			this.blsInnerMergeAction.reSortMergeTenNo(new Request(CommonVars
					.getCurrUser()), currentRows, toRowNumber);
			this.dispose();
		} catch (Exception e) {
			e.printStackTrace() ;
			JOptionPane.showMessageDialog(null, "数据重排条件可能有错!!", "信息!!", 0);
		} finally {
			CommonProgress.closeProgressDialog();
			this.btnOK.setEnabled(true);
		}
	}

	/**
	 * 检查行号
	 */
	private boolean checkToRowNumber() {
		if (checkTextFieldIsNull(tfRowNumber)) {
			JOptionPane.showMessageDialog(null, "行号不能为空!!", "警告", 0);
			return false;
		}
		if (!checkTextFieldIsInteger(tfRowNumber)) {
			JOptionPane.showMessageDialog(null, "请输入正确的数值!!", "警告", 0);
			return false;
		}
		return true;
	}

	private boolean checkTextFieldIsNull(JTextField tf) {
		boolean isNull = true;
		if (!tf.getText().equals("")) {
			isNull = false;
		}
		return isNull;
	}

	private boolean checkTextFieldIsInteger(JTextField tf) {
		boolean isInteger = true;
		try {
			Integer.parseInt(tf.getText());
		} catch (Exception ex) {
			isInteger = false;
		}
		return isInteger;
	}

	/**
	 * 检查十位重排数据
	 */
	private boolean checkTenInnerMergeSort(int toRowNumber) {
		boolean isTrue = false;
		int flag = this.checkDataForTenInnerMergeSort(currentRows, toRowNumber);
		switch (flag) {
		case 0:
			isTrue = true;
			break;
		case -1:
			JOptionPane.showMessageDialog(null, "当前没有选择的项!!", "警告", 0);
			break;
		case -2:
			JOptionPane.showMessageDialog(null, "重排行号超出范围!!", "警告", 0);
			break;
		case -3:
			JOptionPane.showMessageDialog(null, "重排数据中有空行!!", "警告", 0);
			break;
		case -4:
			JOptionPane
					.showMessageDialog(null, "选择的行号在选择的行中,不能进行重排!!", "警告", 0);
			break;
		}
		return isTrue;
	}

	/**
	 * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4:
	 * 选择的行号在选择的行中,不能进行重排
	 */
	public int checkDataForTenInnerMergeSort(List selectedRows, int toRowNumber) {
		int max = 0;
		int min = 0;
		int maxValue = 0;
		String type = "";
		if (selectedRows.size() <= 0) {
			return -1;
		}
//		type = ((BlsInnerMerge) selectedRows.get(0)).getMaterielType();
		maxValue = this.blsInnerMergeAction.getMaxBlsInnerMergeNo(new Request(
				CommonVars.getCurrUser()));//, type
		for (int i = 0; i < selectedRows.size(); i++) {
			BlsInnerMerge data = (BlsInnerMerge) selectedRows.get(i);
			if (data.getBlsTenInnerMerge() == null) {
				return -3;
			}
			int currentValue = data.getBlsTenInnerMerge().getSeqNum().intValue();
			if (i == 0) {
				max = currentValue;
				min = max;
				continue;
			}
			if (max < currentValue) {
				max = currentValue;
			}
			if (min > currentValue) {
				min = currentValue;
			}
		}
		if (toRowNumber > maxValue || toRowNumber <= 0) {
			return -2;
		}
		if (min <= toRowNumber && max >= toRowNumber) {
			return -4;
		}
		return 0;
	}

}
