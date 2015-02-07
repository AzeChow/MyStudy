/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalComboBoxEditor;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.FptEmailParamver;
import com.bestway.common.fpt.entity.TempFptEmail;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptCreateEmail extends DgCommon {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTextField tfsubject = null;

	private JLabel jLabel194 = null;

	private JTextField tffieldAress = null;

	private JButton btnSendMail = null;

	private JButton btnExit = null;

	private FptEmail email = null; // @jve:decl-index=0:

	private JButton btnInTradeCode2 = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JSplitPane jSplitPane = null;

	private File excelFile = null;

	private JComboBox tftoName = null;

	private JLabel jLabel2 = null;

	private JComboBox tfSysType = null;

	private JLabel jLabel3 = null;

	private JTextField tfCopBillNo = null;

	private JButton btnCopBillNo = null;

	private JPanel jPanel1 = null;

	private JTextArea tfmailbody = null;

	private List list = null; // @jve:decl-index=0:
	private String smptServer = null;
	private int dataState = -1;// DataState.BROWSE;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel4 = null;
	private String fromEmail = null;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public DgFptCreateEmail() {
		super();
		initialize();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("创建邮件信息表");
		this.setSize(625, 474);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initComponents();
			if (dataState == DataState.BROWSE) {
				showData(email);
				setState();
			}

		}
		super.setVisible(isFlag);
	}

	private void showData(FptEmail email) {
		if (email == null) {
			return;
		}
		tftoName.setSelectedItem(email.getToEmailAdress().toString());
		this.tfSysType.setSelectedIndex(ItemProperty.getIndexByCode(email
				.getSysType().toString(), this.tfSysType));
		tfsubject.setText(email.getMailSubject());
		tfmailbody.setText(email.getMailbody());
		tffieldAress.setText(email.getMailFileAffix());

	}

	public void setState() {

		this.btnInTradeCode2.setEnabled(dataState != DataState.BROWSE);
		this.tffieldAress.setEditable(dataState != DataState.BROWSE);

		this.btnCopBillNo.setEnabled(dataState != DataState.BROWSE);
		this.btnSendMail.setEnabled(dataState != DataState.BROWSE);
		this.tfCopBillNo.setEditable(dataState != DataState.BROWSE);
		this.tfmailbody.setEditable(dataState != DataState.BROWSE);
		this.tfsubject.setEditable(dataState != DataState.BROWSE);
		this.tfSysType.setEnabled(dataState != DataState.BROWSE);
		this.tftoName.setEnabled(dataState != DataState.BROWSE);

	}

	private void initComponents() {
		final KJComboBoxEditor kjce = new KJComboBoxEditor(tftoName); // @jve:decl-index=0:
		initCombox();
		tftoName.setEditor(kjce);
		tftoName.setSelectedIndex(-1);

		tfSysType.removeAllItems();
		tfSysType.addItem(new ItemProperty("1", "结转申请表"));
		tfSysType.addItem(new ItemProperty("2", "发货单"));
		tfSysType.addItem(new ItemProperty("3", "收退货单"));
		tfSysType.addItem(new ItemProperty("6", "其它"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.tfSysType);
		this.tfSysType.setRenderer(CustomBaseRender.getInstance().getRender());
		tfSysType.setSelectedIndex(-1);

	}

	private void initCombox() {
		list = fptManageAction.FindFptEmailToAress(new Request(CommonVars
				.getCurrUser()));
//		final Vector<String> v = new Vector<String>(list.size());
//		String[] str = new String[list.size()];
//		for (int i = 0; i < list.size(); i++) {
//			FptEmail fp = (FptEmail) list.get(i);
//			str[i] = fp.getToEmailAdress();
//			v.add(str[i]);
//		}
		this.tftoName.setModel(new DefaultComboBoxModel(list.toArray()));
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel194 = new JLabel();
			jLabel194.setText("收件人");
			jLabel194.setBounds(new Rectangle(26, 46, 90, 18));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel4 = new JLabel();
			jLabel4.setForeground(Color.blue);
			jLabel4
					.setText("\u5907\u6ce8\uff1a\u5f53\u6536/\u53d1\u7c7b\u578b\u4e0d\u4e3a\u7a7a\uff0c\u6807\u9898\u5e94\u5e26\u9009\u62e9\u7c7b\u578b\u91cc\u9762\u7684\u5b57\u6837\uff01\u5982:\u7ed3\u8f6c\u7533\u8bf7\u8868\uff0c\u5219\u6807\u9898\u5e94\u4e3a\u7ed3\u8f6c\u7b49!");
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(5, 5, 435, 34));
			jToolBar.add(getBtnSendMail());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel4);
		}
		return jToolBar;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfsubject() {
		if (tfsubject == null) {
			tfsubject = new JTextField();
			tfsubject.setBounds(new Rectangle(135, 72, 284, 22));
		}
		return tfsubject;
	}

	/**
	 * This method initializes tffieldAress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTffieldAress() {
		if (tffieldAress == null) {
			tffieldAress = new JTextField();
			tffieldAress.setBounds(new Rectangle(135, 99, 261, 22));
		}
		return tffieldAress;
	}

	/**
	 * This method initializes btnSendMail
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendMail() {
		if (btnSendMail == null) {
			btnSendMail = new JButton();
			btnSendMail.setText("发送");
			btnSendMail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateDataIsNull()) {
						return;
					}
					new SendEmailThread().start();
				}

			});
		}
		return btnSendMail;
	}

	/**
	 * 发送邮件
	 * 
	 * @return
	 */

	class SendEmailThread extends Thread {
		public void run() {
			try {

				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					String toEmail = tftoName.getSelectedItem().toString();
					String setSubject = tfsubject.getText().trim();
					String fileAffix = tffieldAress.getText().trim();
					String mailbody = tfmailbody.getText().trim();
					FptEmailParamver paramver = null;
					CommonStepProgress.setStepMessage("系统正在获取参数，请稍后...");
					paramver = fptManageAction
							.findFptEmailParamver(new Request(CommonVars
									.getCurrUser()));
					String smtp = paramver.getSmptServer();
					fromEmail = paramver.getMyEmailAdress();
					String uerName = paramver.getUserName();
					String passWord = paramver.getPassword();
					String port = paramver.getSmtpport();
					setSmptServer(smtp);
					boolean isAuthenticator = paramver.getIsAuthenticator();
					CommonStepProgress.setStepMessage("系统正在发送邮件，请稍后...");
					boolean ls = (boolean) SendMail.getInstance()
							.sendEmail(smtp, setSubject, mailbody, toEmail,
									fromEmail, fileAffix, uerName, passWord,
									port, isAuthenticator);
					if (ls) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(null, "发送邮件成功！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						upSavadata();
					} else {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(null, "发送邮件失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "发送邮件失败 "
							+ ex.getMessage(), "确认", 1);
				}
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * 保存一些发送资料
	 */
	private void upSavadata() {
		FptEmail email = new FptEmail();
		if (this.tfSysType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) tfSysType.getSelectedItem();
			String projectType = item.getCode();
			email.setSysType(projectType);
		} else {
			email.setSysType(null);
		}
		email.setToEmailAdress(tftoName.getSelectedItem().toString().trim());
		email.setMyEmailAdress(fromEmail.trim());
		email.setMailSubject(tfsubject.getText());
		email.setMailFileAffix(tffieldAress.getText().trim());
		email.setMailbody(tfmailbody.getText().trim());
		email.setMailIRType(email.issueEmail);
		email.setSmtpServer(smptServer);
		this.fptManageAction.saveFptEmail(
				new Request(CommonVars.getCurrUser()), email);
	}

	private boolean validateDataIsNull() {
		String str = "";
		if (tfSysType.getSelectedItem() != null) {
			if (tfCopBillNo.getText() == null
					|| "".equals(tfCopBillNo.getText())) {
				str = "企业单据编号不能为空!";
			}
		}
		if (tfSysType.getSelectedItem() != null) {
			if ((tfSysType.getSelectedItem().toString().indexOf("其它") < 0)
					&& tfsubject.getText() != null) {
				if (tfsubject.getText().indexOf(
						tfSysType.getSelectedItem().toString()) < 0) {
					str = "如果发送类型不为空，则标题应为["
							+ tfSysType.getSelectedItem().toString()
							+ "]等字样为标题!";
				}
			}
		}

		if (tftoName.getSelectedItem() == null) {
			str = "收件人不能为空!";
		}
		if (tfsubject.getText() == null || "".equals(tfsubject.getText())) {
			str = "标题不能为空!";
		}

		if (!"".equals(str)) {
			JOptionPane.showMessageDialog(this, str, "提示", 0);
			return false;
		}
		return true;
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
					dispose();
				}
			});
		}
		return btnExit;
	}

	public FptEmail getEmail() {
		return email;
	}

	public void setEmail(FptEmail email) {
		this.email = email;
	}

	/**
	 * This method initializes btnInTradeCode2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInTradeCode2() {
		if (btnInTradeCode2 == null) {
			btnInTradeCode2 = new JButton();
			btnInTradeCode2.setText("...");
			btnInTradeCode2.setBounds(new Rectangle(395, 98, 27, 22));
			btnInTradeCode2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir();
						}
					});
		}
		return btnInTradeCode2;
	}

	private void setDir() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		// fileChooser.setFileFilter(new CommonFileFilter(
		// new String[] {}, "选择文档"));
		int state = fileChooser.showOpenDialog(DgFptCreateEmail.this);
		if (state != JFileChooser.APPROVE_OPTION) {
			return;
		}
		excelFile = fileChooser.getSelectedFile();
		tffieldAress.setText(excelFile.getPath());

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(284, 14, 84, 18));
			jLabel3.setText("选择企业单据");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(26, 13, 90, 18));
			jLabel2.setText("收/发送类型");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(26, 99, 90, 18));
			jLabel1.setText("附件");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(26, 72, 90, 18));
			jLabel.setText("标题");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel194, null);
			jPanel.add(getTfsubject(), null);
			jPanel.add(getTffieldAress(), null);
			jPanel.add(getBtnInTradeCode2(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTftoName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfSysType(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfCopBillNo(), null);
			jPanel.add(getBtnCopBillNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(6, 46, 436, 324));
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(130);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tftoName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTftoName() {
		if (tftoName == null) {
			tftoName = new JComboBox();
			tftoName.setBounds(new Rectangle(135, 43, 284, 24));
			tftoName.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {

					}
				}
			});
		}
		return tftoName;
	}

	/**
	 * This method initializes tfSysType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTfSysType() {
		if (tfSysType == null) {
			tfSysType = new JComboBox();
			tfSysType.setBounds(new Rectangle(135, 8, 140, 29));// 184, 67, 219,
			// 29
			tfSysType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {

					}
					if (tfSysType.getSelectedItem() != null) {
						tfCopBillNo.setEditable(true);
						btnCopBillNo.setEnabled(true);
						tfmailbody.setEditable(false);
					} else {
						tfCopBillNo.setEditable(false);
						btnCopBillNo.setEnabled(false);
						tfmailbody.setEditable(true);
					}
				}
			});
		}
		return tfSysType;
	}

	/**
	 * This method initializes tfCopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopBillNo() {
		if (tfCopBillNo == null) {
			tfCopBillNo = new JTextField();
			tfCopBillNo.setBounds(new Rectangle(373, 11, 148, 23));
			tfCopBillNo.setEditable(false);
		}
		return tfCopBillNo;
	}

	/**
	 * This method initializes btnCopBillNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopBillNo() {
		if (btnCopBillNo == null) {
			btnCopBillNo = new JButton();
			btnCopBillNo.setBounds(new Rectangle(520, 10, 26, 23));
			btnCopBillNo.setText("...");
			btnCopBillNo.setEnabled(false);
			btnCopBillNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfSysType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "收/发送类型为空!！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List lists = FptQuery.getInstance().findAppBillToFptEmail(
							((ItemProperty) tfSysType.getSelectedItem())
									.getCode(), DeclareState.PROCESS_EXE,
							FptInOutFlag.OUT, "1");
					if (lists == null) {
						return;
					}
					String overSysType = "";
					for (int i = 0; i < lists.size(); i++) {
						TempFptEmail head = (TempFptEmail) lists.get(i);
						tfCopBillNo.setText(head.getOutCopAppNo());
						String outCopAppNo = head.getOutCopAppNo() == null ? ""
								: head.getOutCopAppNo();
						String seqNo = head.getSeqNo() == null ? "" : head
								.getSeqNo();
						String appno = head.getAppNo() == null ? "" : head
								.getAppNo();
						String outCode = head.getOutCode() == null ? "" : head
								.getOutCode();
						String outName = head.getOutName() == null ? "" : head
								.getOutName();
						String billNo = head.getBillNo() == null ? "" : head
								.getBillNo();
						String sysType = head.getSysType() == null ? "" : head
								.getSysType();
						if (FptBusinessType.FPT_APP.equals(sysType)) {
							overSysType = "结转申请表";
						} else if (FptBusinessType.FPT_BILL.equals(sysType)) {
							overSysType = "发货单据";
						} else if (FptBusinessType.FPT_BILL_BACK
								.equals(sysType)) {
							overSysType = "发退货单据";
						}

						String str = "收发类型 " + "[" + overSysType + "]" + "\n"
								+ "企业内部编号 " + "[" + outCopAppNo + "]" + "\n"
								+ "统一编号 " + "[" + seqNo + "]" + "\n" + "申请单编号 "
								+ "[" + appno + "]" + "\n" + "企业编号 " + "["
								+ outCode + "]" + "\n" + "企业名称 " + "["
								+ outName + "]" + "\n" + "发退货编号 " + "["
								+ billNo + "]";
						tfmailbody.setText(str);
					}

				}
			});
		}
		return btnCopBillNo;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane(), gridBagConstraints1);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfmailbody
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTfmailbody() {
		if (tfmailbody == null) {
			tfmailbody = new JTextArea();
			tfmailbody.setLineWrap(true);
		}
		return tfmailbody;
	}

	public String getSmptServer() {
		return smptServer;
	}

	public void setSmptServer(String smptServer) {
		this.smptServer = smptServer;
	}

	/**
	 * ComboBoxEdito
	 * 
	 * @author ower
	 * 
	 */
	class KJComboBoxEditor extends MetalComboBoxEditor {

		Object oldObject = null;
		JComboBox comboBox = null;
		String codeField = "";
		String nameField = "";
		ComboBoxModel model = null;
		int oldSelectedIndex = -1;

		public KJComboBoxEditor(JComboBox comboBox) {
			super();
			this.comboBox = comboBox;
			this.comboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					oldSelectedIndex = getComBoBoxSelectedIndex();
				}
			});
			editor.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					copyModel();
					((JTextField) e.getSource()).selectAll();
				}

				public void focusLost(FocusEvent e) {
					String keyAndValue = getSelectKeyAndValue();
					// resetModel();
					// setSelectedIndex(vaule);
					//System.out.println("keyAndValue="+keyAndValue);
				    setSelectedIndexByKeyAndValue(keyAndValue);
					//System.out.println("ffff");
//					int index = getComBoBoxSelectedIndex();
//					if (index != oldSelectedIndex) {
//						fireSelectedIndexChange(oldSelectedIndex, index);
//					}
				}
			});

			editor.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {

					SwingUtilities.invokeLater(notifyTableRunnable);
				}
			});
			this.comboBox.setEditable(true);
		}

		private void fireSelectedIndexChange(int oldValue, int newValue) {
			// System.out.println("-----------------selectedIndex:oldValue"
			// + oldValue + ",newValue:" + newValue);
			ItemListener[] itemListeners = this.comboBox.getItemListeners();
			int eventType = ItemEvent.ITEM_STATE_CHANGED;
			Object item = null;
			if (oldValue >= 0) {
				item = this.comboBox.getItemAt(oldValue);
				if (itemListeners != null && itemListeners.length > 0) {
					for (ItemListener itemListener : itemListeners) {
						if (itemListener != null) {
							itemListener.itemStateChanged(new ItemEvent(
									this.comboBox, eventType, item,
									ItemEvent.DESELECTED));
						}
					}
				}
			}
			if (newValue >= 0) {
				item = this.comboBox.getItemAt(newValue);
				if (itemListeners != null && itemListeners.length > 0) {
					for (ItemListener itemListener : itemListeners) {
						if (itemListener != null) {
							itemListener.itemStateChanged(new ItemEvent(
									this.comboBox, eventType, item,
									ItemEvent.SELECTED));
						}
					}
				}
			}
		}

		private int getComBoBoxSelectedIndex() {
			if (this.comboBox == null) {
				return -1;
			}
			return this.comboBox.getSelectedIndex();
		}

		private void resetModel() {
			comboBox.setModel(model);
			comboBox.hidePopup();
		}

		private void copyModel() {
//			final Vector<String> v = new Vector<String>(list.size());
//			String[] str = new String[list.size()];
//			for (int i = 0; i < list.size(); i++) {
//				FptEmail fp = (FptEmail) list.get(i);
//				str[i] = fp.getToEmailAdress();
//				v.add(str[i]);
//			}
			comboBox.setModel(new DefaultComboBoxModel(list.toArray()));
			comboBox.setSelectedIndex(-1);
		}

		protected Runnable notifyTableRunnable = new Runnable() {
			public void run() {
				String key = editor.getText().trim();
				filterKey(key);
			}
		};

		private void filterKey(String key) {
			if (key == null || "".equals(key.trim())) {
				copyModel();
			} else {
				List list = getFiltedList(key.trim());
				ComboBoxModel tempModel = new DefaultComboBoxModel(list
						.toArray());
				tempModel.setSelectedItem(null);
				comboBox.setModel(tempModel);
				comboBox.showPopup();
			}

			editor.setText(key);
		}

		public void setSelectedIndexByKeyAndValue(String keyAndValue) {
			int index = -1;
			String codeName = "";
			String itemName = "";
			String itemCode = "";
//			if (keyAndValue.equals("")) {
//				this.comboBox.setSelectedIndex(index);
//				return;
//			}
//			if (this.comboBox.getItemCount() < 1) {
//				editor.setText("");
//				return;
//			}
			if (this.comboBox.getSelectedItem() != null) {
				Object obj = this.comboBox.getSelectedItem();
				if (obj instanceof CustomBaseEntity) {
					itemName = ((CustomBaseEntity) obj).getName();
					itemCode = ((CustomBaseEntity) obj).getCode();
				} else if (obj instanceof ItemProperty) {
					itemName = ((ItemProperty) obj).getName();
					itemCode = ((ItemProperty) obj).getCode();
				} else {
					if ((codeField != null) && (!codeField.equals(""))
							|| (nameField != null) && (!nameField.equals(""))) {
						if ((codeField != null) && (!codeField.equals(""))) {
							try {
								itemCode = BeanUtils
										.getProperty(obj, codeField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
						if ((nameField != null) && (!nameField.equals(""))) {
							try {
								itemName = BeanUtils
										.getProperty(obj, nameField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
					} else {
						itemName = obj.toString();
						itemCode = obj.toString();
					}
				}
				if (nameField != null && !nameField.equals("")) {
					editor.setText(itemName);
				} else if (codeField != null && !codeField.equals("")) {
					editor.setText(itemCode);
				} else {
					editor.setText(itemName);
				}
			}
			if (this.comboBox != null) {
				if (this.comboBox.getItemCount() < 1) {
					return;
				}

				Object obj = this.comboBox.getItemAt(0);

				if ((codeField != null) && (!codeField.equals(""))
						|| (nameField != null) && (!nameField.equals(""))) {
					try {
						ComboBoxModel model = this.comboBox.getModel();
						int size = model.getSize();
						for (int i = 0; i < size; i++) {
							if ((codeField != null) && (!codeField.equals(""))) {
								itemCode = BeanUtils.getProperty(model
										.getElementAt(i), codeField);
								codeName = (itemCode == null ? "" : itemCode);
								if (itemCode != null
										&& itemCode.equals(keyAndValue)) {
									index = i;
									break;
								}
							}
							if ((nameField != null) && (!nameField.equals(""))) {
								itemName = BeanUtils.getProperty(model
										.getElementAt(i), nameField);
								codeName += (itemName == null ? "" : itemName);
								// if (itemName != null
								// && itemName.equals(keyAndValue)) {
								// index = i;
								// break;
								// }
							}
							if (codeName.equals(keyAndValue)) {
								index = i;
								break;
							}
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				} else {
					ComboBoxModel model = this.comboBox.getModel();
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						itemCode = model.getElementAt(i).toString();
						codeName = (itemCode == null ? "" : itemCode);
						if (codeName.equals(keyAndValue)) {
							index = i;
							break;
						}
					}
				}
			}
			if (this.comboBox.getSelectedIndex() < 0 && index < 0) {
				editor.setText("");
			}
			this.comboBox.setSelectedIndex(index);
		}

		private String getSelectKeyAndValue() {
			String str = "";
			if (comboBox.getSelectedItem() == null
					&& (comboBox.getItemCount() != 1 || "".equals(editor
							.getText().trim())
							&& model.getSize() <= 1)) {
				System.out.println("---return 111" + editor.getText());
				final Vector<String> v = new Vector<String>(list.size() + 1);
				v.add(editor.getText());
				comboBox.setModel(new DefaultComboBoxModel(v));
				return editor.getText();
			}
			Object obj = null;
			if (comboBox.getSelectedItem() != null) {
				System.out.println("---return 333");
				obj = comboBox.getSelectedItem();
			}
			if (obj == null) {
				System.out.println("---return 222");
				return str;
			}
			if ((codeField != null) && (!codeField.equals(""))
					|| (nameField != null) && (!nameField.equals(""))) {
				try {
					if ((codeField != null) && (!codeField.equals(""))) {
						String itemCode = BeanUtils.getProperty(obj, codeField);
						str = (itemCode == null ? "" : itemCode);
					}
					if ((nameField != null) && (!nameField.equals(""))) {
						String itemName = BeanUtils.getProperty(obj, nameField);
						str += (itemName == null ? "" : itemName);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else {
				str = obj.toString();
				System.out.println("---return 222="+str);
			}
			return str;
		}

		private List getFiltedList(String key) {
			List lsResult = new ArrayList();
			String itemName = "";
			String itemCode = "";
			model = comboBox.getModel();
			if (model.getSize() < 1) {
				return lsResult;
			}
			if (((codeField != null) && (!codeField.equals("")))
					|| ((nameField != null) && (!nameField.equals("")))) {
				try {
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						if ((codeField != null) && (!codeField.equals(""))) {
							itemCode = BeanUtils.getProperty(model
									.getElementAt(i), codeField);
							if (itemCode != null && itemCode.indexOf(key) >= 0) {
								lsResult.add(model.getElementAt(i));
								continue;
							}
						}
						if ((nameField != null) && (!nameField.equals(""))) {
							itemName = BeanUtils.getProperty(model
									.getElementAt(i), nameField);
							if (itemName != null && itemName.indexOf(key) >= 0) {
								lsResult.add(model.getElementAt(i));
								continue;
							}
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else {
				int size = model.getSize();
				for (int i = 0; i < size; i++) {
					itemCode = model.getElementAt(i).toString();
					if (itemCode != null && itemCode.indexOf(key) >= 0) {
						lsResult.add(model.getElementAt(i));
					}
				}
			}
			// }
			return lsResult;
		}

		public void addActionListener(ActionListener l) {
		}

		public Object getItem() {
			return oldObject;
		}

		public void removeActionListener(ActionListener l) {
		}

		public void setItem(Object anObject) {
			oldObject = anObject;
			if (anObject == null) {
				editor.setText("");
				return;
			}
			this.editor.setText(anObject.toString());
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
			jScrollPane.setViewportView(getTfmailbody());
		}
		return jScrollPane;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
