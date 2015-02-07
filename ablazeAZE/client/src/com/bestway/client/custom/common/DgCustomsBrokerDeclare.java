/*
 * Created on 2004-8-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Rectangle;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCustomsBrokerDeclare extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	private boolean ok = false;
	private String attachedBill = "";
	private JLabel lbCustomsBroker = null;
	private JComboBox cbbCustomsBroker = null;

	public Integer projectType;
	public BaseEncAction baseEncAction;
	public JTableListModel tableModel = null;
	private String customDeclaretionId; // @jve:decl-index=0:

	private CustomBaseAction customBaseAction;
	private MessageAction messageAction;

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomsBrokerDeclare() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		messageAction = (MessageAction) CommonVars
		.getApplicationContext().getBean("messageAction");
		initialize();
		initUI();
	}

	
	@Override
	public void setVisible(boolean b) {

		BaseCustomsDeclaration d = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();

		// 未选择报关单
		if (d == null) {
			JOptionPane.showMessageDialog(DgCustomsBrokerDeclare.this,
					"请选择报关单", "确认", 2);
			return;
		}

		// 检查状态
		if (!d.getIsCheck()) {
			// 未检查
			JOptionPane.showMessageDialog(DgCustomsBrokerDeclare.this,
					"报关单【未检查】", "确认", 2);
			return;
		}
		
//		// 已经发送
//		if (d.getIsSend() != null && d.getIsSend()) {
//			JOptionPane.showMessageDialog(DgCustomsBrokerDeclare.this,
//					"报关单【已发送】", "确认", 2);
//			return;
//		}

		customDeclaretionId = d.getId();


		super.setVisible(b);
	}

	private void initUI() {
		List<CustomsBroker> customsBrokers = customBaseAction
				.findCustomsBroker("", "");
		CustomsBroker cb = null;
		for (int i = 0; i <customsBrokers.size(); i++) {
			cb = customsBrokers.get(i);
			if(Boolean.TRUE.equals(cb.getIsDefault())) {
				
				customsBrokers.remove(cb);
				customsBrokers.add(0, cb);
				break;
			}
		}
		
		cbbCustomsBroker.setModel(new DefaultComboBoxModel(customsBrokers.toArray()));
		
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbCustomsBroker);
		this.cbbCustomsBroker.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 100, 100));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("选择申报报关行");
		this.setContentPane(getJContentPane());
		this.setSize(355, 163);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbCustomsBroker = new JLabel();
			lbCustomsBroker.setBounds(new Rectangle(50, 30, 48, 18));
			lbCustomsBroker.setText("报关行：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(lbCustomsBroker, null);
			jContentPane.add(getCbbCustomsBroker(), null);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @param attachedBill
	 *            The attachedBill to set.
	 */
	public void setAttachedBill(String attachedBill) {
		this.attachedBill = attachedBill;
	}

	public String getAttachedBill() {
		return this.attachedBill;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(90, 80, 60, 26);
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsBroker cbk = (CustomsBroker) cbbCustomsBroker.getSelectedItem();
					if(cbk == null) {
						JOptionPane.showMessageDialog(DgCustomsBrokerDeclare.this,
									"必须选择报关行", "确认", 2);
						return ;
					}
					
					CommonProgress
							.showProgressDialog(DgCustomsBrokerDeclare.this);
					CommonProgress.setMessage("系统正在申报可能需要10几秒，请稍后...");
					
					try {
						messageAction.customsBrokerDeclaretion(new Request(CommonVars.getCurrUser()), customDeclaretionId,
								null, projectType, cbk);
					} catch (RuntimeException e1) {
						throw e1;
					} finally {
						CommonProgress.closeProgressDialog();
					}
					
					setOk(true);
					DgCustomsBrokerDeclare.this.dispose();
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
			btnCancel.setText("取消");
			btnCancel.setBounds(200, 80, 60, 26);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgCustomsBrokerDeclare.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	class CheckableItem {
		private String code;
		private String name;
		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	/**
	 * This method initializes cbbCustomsBroker
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomsBroker() {
		if (cbbCustomsBroker == null) {
			cbbCustomsBroker = new JComboBox();
			cbbCustomsBroker.setBounds(new Rectangle(100, 28, 200, 23));
		}
		return cbbCustomsBroker;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
