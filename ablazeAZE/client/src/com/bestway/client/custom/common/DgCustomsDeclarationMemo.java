/*
 * Created on 2004-8-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.RegexUtil;
import com.bsw.core.client.eport.QueryBaseList;

/**
 * @author bsway
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("serial")
public class DgCustomsDeclarationMemo extends JDialogBase {

	private String codes;

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	private boolean ok = false;
	private String memoStr = null;
	private String certificateCode = null;

	private JEditorPane jEditorPane = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel1 = null;
	//
	// 分割符8个空格
	//
	@SuppressWarnings("unused")
	private static String SPLIT = "        ";
	private int charQuantity = 255;
	public String containerNum = null;
	private Integer impExpType = null;
	private JScrollPane jScrollPane1 = null;
	private JEditorPane jEditorPane1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;

	public DgCustomsDeclarationMemo() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("备注格式填写");
		this.setContentPane(getJContentPane());
		this.setSize(393, 357);

	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initComponent();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the memoStr.
	 */
	public String getMemoStr() {
		return memoStr;
	}

	/**
	 * @return Returns the certificateCode.
	 */
	public String getCertificateCode() {
		return certificateCode;
	}

	/**
	 * @param certificateCode
	 *            The certificateCode to set.
	 */
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	/**
	 * @param memoStr
	 *            The memoStr to set.
	 */
	public void setMemoStr(String memoStr) {
		this.memoStr = memoStr;
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
			jContentPane.add(getJPanel1(), null);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(12, 0, 322, 18));
			jLabel1.setText("标记麦码、手册转厂说明或其它说明");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(18, 154, 347, 128);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(jLabel1, null);
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
			btnOk.setBounds(215, 290, 64, 25);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 给予提示：
					if (impExpType != null
							&& (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT || impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT)) {

					} else {

						if (containerNum != null
								&& containerNum.equals("0")
								&& !(CommonVars.substring(
										jEditorPane.getText(), 0, 2))
										.equals("#1")) {

							if (JOptionPane
									.showConfirmDialog(
											DgCustomsDeclarationMemo.this,
											"系统提示：集装箱数为'0'\n是否在说明字符串前加 '#1' ?",
											"提示", 0) == 0) {

								jEditorPane.setText("#1 "
										+ jEditorPane.getText());
								return;
							}
						}
					}

					if (checkEnterSymbol(jEditorPane.getText())) {
						JOptionPane.showMessageDialog(
								DgCustomsDeclarationMemo.this,
								"说明字符串中存在回车符,不能生成报关单!!!!", "警告!!!",
								JOptionPane.WARNING_MESSAGE);
					} else {
						setOk(true);
						DgCustomsDeclarationMemo.this.dispose();
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
			btnCancel.setBounds(289, 290, 64, 25);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					DgCustomsDeclarationMemo.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 检查文本中是否有回车符
	 * 
	 * @param sourceText
	 */
	private boolean checkEnterSymbol(String sourceText) {
		boolean isExist = false;
		if (!"".equals(sourceText)) {
			byte[] bytes = sourceText.getBytes();
			for (int i = 0; i < bytes.length; i++) {
				// 回车符是13
				if (bytes[i] == 13) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	/**
	 * 返回备注值
	 * 
	 * @return
	 */
	public String returnMemoValue() {
		return this.jEditorPane.getText();
	}

	/**
	 * 返回证件编码的值
	 * 
	 * @return
	 */
	public String returnCertificateCodeValue() {

		StringBuffer returnValue = new StringBuffer();

		String s = this.jEditorPane1.getText();

		codes = "";

		if (!"".equals(s)) {

			String[] yy = s.split("\n");

			for (int i = 0; i < yy.length; i++) {

				String everyStrArr = yy[i].trim();

				// 一个随附单证 的 代码对应 号码 e.g. A:123456
				String[] oneCodeValue = everyStrArr.split(":");

				codes += oneCodeValue[0];

				if (StringUtils.isNotBlank(everyStrArr)) {

					if (i != (yy.length - 1)) {

						returnValue.append(everyStrArr);

						returnValue.append(",");

					} else {

						returnValue.append(everyStrArr);

					}

				}

			}
		}
		return returnValue.toString();
	}

	public String returnAttachedBillCode() {

		return codes;

	}

	public String repeatStr(String str, int count) {
		String returnStr = "";
		for (int i = 0; i < count; i++) {
			returnStr += str;
		}
		return returnStr;
	}

	private void initComponent() {
		if (StringUtils.isNotBlank(memoStr)) {
			this.jEditorPane.setText(memoStr);
		}
		String s = "";

		if (StringUtils.isNotBlank(certificateCode)) {

			// 用于检查是否包含 中文字 如果没有中文字就 跳过循环不处理替换字符串
			int count = RegexUtil.match("[\u4e00-\u9fa5]", certificateCode);

			if (certificateCode.contains(";")) {

				certificateCode = certificateCode.replaceAll(";", ",");

			}

			// 证件代码
			String[] certificates = certificateCode.split(",");

			// 所有随附单证类型
			QueryBaseList licenseDocus = QueryBaseList.LICENSE_DOCU;

			for (int i = 0; i < certificates.length; i++) {

				String str = certificates[i];

				// 如果包含 中文 字符 , 那么就开始处理掉中文
				if (count != 0) {

					String codition = str.substring(0, str.indexOf(":"));

					// 取得左段替换成 随附单据代码 而不是使用 中文:12346这种形式
					String code = licenseDocus.findMatchName(codition,
							LicenseDocu.class);

					if (StringUtils.isNotBlank(code)) {

						str = str.replaceFirst(codition, code);

					}

				}

				if (i == 0) {

					s = str;

				} else {
					s = s + "\n" + str;
				}

			}

			this.jEditorPane1.setText(s);
		}
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (jEditorPane.getText().getBytes().length >= charQuantity
							|| str.getBytes().length > charQuantity
							|| jEditorPane.getText().getBytes().length
									+ str.getBytes().length > charQuantity) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
			jEditorPane.setBounds(new Rectangle(0, 0, 317, 94));
		}
		return jEditorPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(11, 20, 324, 97);
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(13, 0, 319, 35));
			jLabel.setText("<html>证件代码=代码+分隔符+号码,如 A:12345678或A*123<br/>如果多个代码用回车或者半角逗号隔开。如A:666666,B:888<html/>");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(18, 7, 347, 135);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.add(jLabel, null);
		}
		return jPanel1;
	}

	/**
	 * 填充数据到JList
	 */
	/*
	 * private void fillListData(List list, JList jList) { if (list.size() > 0)
	 * { Vector vector = new Vector(); vector.addAll(list);
	 * jList.setListData(vector); } }
	 */

	/**
	 * 加入一个select JList 对象到另一个 JList
	 */
	/*
	 * private void addListItem(Object obj, int index, JList toJList) { if (obj
	 * == null) { return; } Vector toSource = getListData(toJList);
	 * toSource.insertElementAt(obj, index); toJList.setListData(toSource); }
	 */

	/*
	 * private void removeSelectedListItem(JList toJList) { if
	 * (toJList.getSelectedValue() == null) { return; } Vector vector =
	 * this.getListData(toJList); vector.remove(toJList.getSelectedValue());
	 * toJList.setListData(vector); }
	 */

	/**
	 * 获得 jList所有对象数据
	 */
	/*
	 * private Vector getListData(JList jList) { int size =
	 * jList.getModel().getSize(); Vector vector = new Vector(); for (int i = 0;
	 * i < size; i++) { vector.add(jList.getModel().getElementAt(i)); } return
	 * vector; }
	 */

	/**
	 * @return Returns the containerNum.
	 */
	public String getContainerNum() {
		return containerNum;
	}

	/**
	 * @param containerNum
	 *            The containerNum to set.
	 */
	public void setContainerNum(String containerNum) {
		this.containerNum = containerNum;
	}

	public Integer getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(13, 38, 320, 80));
			jScrollPane1.setViewportView(getJEditorPane1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jEditorPane1
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane1() {
		if (jEditorPane1 == null) {
			jEditorPane1 = new JEditorPane();

		}
		return jEditorPane1;
	}

}