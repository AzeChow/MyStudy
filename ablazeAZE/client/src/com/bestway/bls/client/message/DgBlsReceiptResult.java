package com.bestway.bls.client.message;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.entity.BlsCollateBindResultType;
import com.bestway.bls.entity.BlsDeliveryResultType;
import com.bestway.bls.entity.BlsEntranceMessageResultType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBlsReceiptResult extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField tfMessageType = null;
	private JTextField tfKeyCode = null;
	private JTextField tfMessageCode = null;
	private JTextField tfServiceStatus = null;
	private JTextField tfServiceHandle = null;
	private JScrollPane jScrollPane = null;
	private JTextArea tfDescription = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	/**
	 * 信息类型
	 */
	private String messageType; // @jve:decl-index=0:
	/**
	 * 单证号码
	 */
	private String relateID; // @jve:decl-index=0:
	/**
	 * 回执信息
	 */
	private BlsReceiptResult receiptResult = null; // @jve:decl-index=0:

	/**
	 * 是否确认
	 */
	private boolean isOK = false;

	private BlsMessageAction blsMessageAction = null;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRelateID() {
		return relateID;
	}

	public void setRelateID(String keyCode) {
		this.relateID = keyCode;
	}

	public boolean isOK() {
		return isOK;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBlsReceiptResult() {
		super();
		initialize();
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.receiptResult = blsMessageAction.findBlsReceiptResultFromHG(
					new Request(CommonVars.getCurrUser()), messageType,
					relateID);
			this.showData();
			this.setState(receiptResult);
		}
		super.setVisible(b);
	}

	/**
	 * 显示回执信息
	 */
	private void showData() {
		if (receiptResult == null) {
			this.tfDescription.setText("回执查询的返回结果为空(没有回执)！");
		} else {
			this.tfMessageType.setText(BlsServiceType
					.getServiceDescByType(messageType));//receiptResult.getMessageType()));
			this.tfMessageCode.setText(receiptResult.getMessageCode());
			this.tfKeyCode.setText(receiptResult.getKeyCode());
			String serviceType = messageType;//receiptResult.getMessageType();
//			System.out.println(" serviceType:"+serviceType);
			int status = Integer.parseInt(receiptResult.getServiceStatus());
			System.out.println("------------------"+status+"\\\\\\\\\\\\\\\\\\"+receiptResult.getServiceStatus());
			String serviceStatus = "";
			if (BlsServiceType.MANIFEST_DECLARE.equals(serviceType)) {
				serviceStatus = BlsDeliveryResultType
						.getReceiptResultSpecByType(status);
			} else if (BlsServiceType.COLLATEBIND_DECLARE.equals(serviceType)) {
				serviceStatus = BlsCollateBindResultType
						.getReceiptResultSpecByType(status);
			} else if (BlsServiceType.FREIGHTAGE_DECLARE.equals(serviceType)) {
				serviceStatus = BlsEntranceMessageResultType
						.getReceiptResultSpecByType(status);
			}
			this.tfServiceStatus.setText(serviceStatus);
			this.tfServiceHandle.setText(receiptResult.getServiceHandle());
			this.tfDescription.setText(receiptResult.getDescription());
		}
	}

	private void setState(BlsReceiptResult receiptResult) {
		if (receiptResult != null) {
			String serviceType = receiptResult.getMessageType();
			boolean isWaitfor = false;
			if (BlsServiceType.MANIFEST_DECLARE.equals(serviceType)) {
				isWaitfor = BlsDeliveryResultType
						.checkReceiptResultIsWaitfor(receiptResult);
			} else if (BlsServiceType.COLLATEBIND_DECLARE.equals(serviceType)) {
				isWaitfor = BlsCollateBindResultType
						.checkReceiptResultIsWaitfor(receiptResult);
			} else if (BlsServiceType.FREIGHTAGE_DECLARE.equals(serviceType)) {
				isWaitfor = BlsEntranceMessageResultType
						.checkReceiptResultIsWaitfor(receiptResult);
			}
			this.btnOK.setEnabled(!isWaitfor);
		} else {
			this.btnOK.setEnabled(false);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(455, 391));
		this.setTitle("回执信息");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(27, 155, 61, 17));
			jLabel5.setText("信息描述");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(27, 128, 61, 17));
			jLabel4.setText("服务句柄");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(27, 99, 61, 17));
			jLabel3.setText("服务状态");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(27, 72, 61, 17));
			jLabel2.setText("信息代码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(27, 43, 61, 17));
			jLabel1.setText("单证号码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(27, 19, 61, 17));
			jLabel.setText("数据类型");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfMessageType(), null);
			jContentPane.add(getTfKeyCode(), null);
			jContentPane.add(getTfMessageCode(), null);
			jContentPane.add(getTfServiceStatus(), null);
			jContentPane.add(getTfServiceHandle(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMessageType() {
		if (tfMessageType == null) {
			tfMessageType = new JTextField();
			tfMessageType.setBounds(new Rectangle(91, 18, 322, 22));
			tfMessageType.setEditable(false);
		}
		return tfMessageType;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfKeyCode() {
		if (tfKeyCode == null) {
			tfKeyCode = new JTextField();
			tfKeyCode.setBounds(new Rectangle(92, 44, 322, 22));
			tfKeyCode.setEditable(false);
		}
		return tfKeyCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMessageCode() {
		if (tfMessageCode == null) {
			tfMessageCode = new JTextField();
			tfMessageCode.setBounds(new Rectangle(92, 72, 322, 22));
			tfMessageCode.setEditable(false);
		}
		return tfMessageCode;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfServiceStatus() {
		if (tfServiceStatus == null) {
			tfServiceStatus = new JTextField();
			tfServiceStatus.setBounds(new Rectangle(92, 99, 322, 22));
			tfServiceStatus.setEditable(false);
		}
		return tfServiceStatus;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfServiceHandle() {
		if (tfServiceHandle == null) {
			tfServiceHandle = new JTextField();
			tfServiceHandle.setBounds(new Rectangle(91, 127, 325, 22));
			tfServiceHandle.setEditable(false);
		}
		return tfServiceHandle;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(92, 155, 325, 150));
			jScrollPane.setViewportView(getTfDescription());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTfDescription() {
		if (tfDescription == null) {
			tfDescription = new JTextArea();
			tfDescription.setEditable(false);
			tfDescription.setEnabled(true);
		}
		return tfDescription;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(73, 315, 80, 25));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOK = true;
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(300, 315, 80, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOK = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	public BlsReceiptResult getBlsReceiptResult() {
		return receiptResult;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
