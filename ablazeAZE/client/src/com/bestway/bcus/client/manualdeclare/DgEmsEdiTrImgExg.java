/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsEdiTrImgExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField tfSeqNum = null;

	private JTextField jTextField2 = null;

	private JButton jButton = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;
	private ManualDeclareAction manualdeclearAction = null;
	private Complex complex = null;
	private EmsEdiTrImg emsEdiTrImg = null; // 经营范围料件
	private EmsEdiTrExg emsEdiTrExg = null; // 经营范围成品
	private EmsEdiTrHead emsEdiTrHead = null; // 经营范围表头 // @jve:decl-index=0:
	private boolean isChange = false;
	private boolean isImg = true;
	private boolean isModifySeqNUm = false; // 可否修改备案序号
	private boolean isAdd = false; // 是否是新增

	public boolean isModifySeqNUm() {
		return isModifySeqNUm;
	}

	public void setModifySeqNUm(boolean isModifySeqNUm) {
		this.isModifySeqNUm = isModifySeqNUm;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiTrImgExg() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(377, 229);
		this.setTitle("料件维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (!isAdd) {
					if (DgEmsEdiTrImgExg.this.isImg) {
						if (tableModel.getCurrentRow() != null) {
							emsEdiTrImg = (EmsEdiTrImg) tableModel
									.getCurrentRow();
							emsEdiTrHead = emsEdiTrImg.getEmsEdiTrHead();
						}
					} else {
						if (tableModel.getCurrentRow() != null) {
							emsEdiTrExg = (EmsEdiTrExg) tableModel
									.getCurrentRow();
							emsEdiTrHead = emsEdiTrExg.getEmsEdiTrHead();
						}
					}
				} else {
					List listImgExg = null;
					if (DgEmsEdiTrImgExg.this.isImg) {
						listImgExg = manualdeclearAction.findEmsEdiTrImg(
								new Request(CommonVars.getCurrUser()),
								emsEdiTrHead);
						emsEdiTrImg = new EmsEdiTrImg();
						if (listImgExg != null && listImgExg.size() > 0) {
							EmsEdiTrImg Img = (EmsEdiTrImg) listImgExg
									.get(listImgExg.size() - 1);
							Integer ptNo = Img.getPtNo() + 1;
							emsEdiTrImg.setPtNo(ptNo);
						}else{
							emsEdiTrImg.setPtNo(1);
						}
						emsEdiTrImg.setEmsEdiTrHead(emsEdiTrHead);
						emsEdiTrImg.setModifyTimes(emsEdiTrHead
								.getModifyTimes()); // 变更次数
						emsEdiTrImg.setModifyMark(ModifyMarkState.ADDED);
						emsEdiTrImg.setCompany(CommonVars.getCurrUser()
								.getCompany());
						emsEdiTrImg.setChangeDate(new Date()); // 修改时间

					} else {
						listImgExg = manualdeclearAction.findEmsEdiTrExg(
								new Request(CommonVars.getCurrUser()),
								emsEdiTrHead);
						emsEdiTrExg = new EmsEdiTrExg();
						if (listImgExg != null && listImgExg.size() > 0) {
							EmsEdiTrExg exg = (EmsEdiTrExg) listImgExg
									.get(listImgExg.size() - 1);
							Integer ptNo = exg.getPtNo() + 1;
							emsEdiTrExg.setPtNo(ptNo);
						}else{
							emsEdiTrExg.setPtNo(1);
						}

						emsEdiTrExg.setEmsEdiTrHead(emsEdiTrHead);
						emsEdiTrExg.setModifyTimes(emsEdiTrHead
								.getModifyTimes());
						emsEdiTrExg.setModifyMark(ModifyMarkState.ADDED);
						emsEdiTrExg.setChangeDate(new Date());// 修改时间
						emsEdiTrExg.setCompany(CommonVars.getCurrUser()
								.getCompany());
					}

				}

				fillWindow();
			}
		});
	}

	private void fillWindow() {
		if (DgEmsEdiTrImgExg.this.isImg) {
			if (emsEdiTrImg != null) {
				// 初始基本信息
				if (emsEdiTrHead != null)
					jTextField.setText(emsEdiTrHead.getCopEmsNo()); // 企业内部编号
				else
					jTextField.setText("");
				this.setTitle("经营范围料件维护");
				if (emsEdiTrImg.getComplex() != null) {
					jTextField2.setText(emsEdiTrImg.getComplex()); // 商品编码
				} else {
					jTextField2.setText(null);
				}
				jTextField3.setText(emsEdiTrImg.getName());// 商品名称
				jTextField4.setText(emsEdiTrImg.getNote());// 备注
				tfSeqNum.setText(emsEdiTrImg.getPtNo().toString());// 料件序号

			}
		} else {
			if (emsEdiTrExg != null) {
				// 初始基本信息
				if (emsEdiTrHead != null)
					jTextField.setText(emsEdiTrHead.getCopEmsNo()); // 企业内部编号
				else
					jTextField.setText("");
				this.setTitle("经营范围成品维护");
				if (emsEdiTrExg.getComplex() != null) {
					jTextField2.setText(emsEdiTrExg.getComplex()); // 商品编码
				} else {
					jTextField2.setText(null);
				}
				jTextField3.setText(emsEdiTrExg.getName());// 商品名称
				jTextField4.setText(emsEdiTrExg.getNote());// 备注
				tfSeqNum.setText(emsEdiTrExg.getPtNo().toString());// 成品序号
			}
		}
		if (this.isModifySeqNUm) {
			tfSeqNum.setEditable(true);
		} else {
			tfSeqNum.setEditable(false);
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(21, 31, 81, 21);
			jLabel.setText("企业内部编号");
			jLabel1.setBounds(195, 31, 62, 21);
			jLabel1.setText("料件序号");
			jLabel2.setBounds(22, 61, 80, 21);
			jLabel2.setText("商品编码(4位)");
			jLabel3.setBounds(21, 94, 79, 21);
			jLabel3.setText("商品名称/规格");
			jLabel4.setBounds(21, 125, 77, 23);
			jLabel4.setText("备注");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(99, 31, 83, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes tfSeqNum
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(261, 31, 87, 22);

		}
		return tfSeqNum;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(100, 61, 132, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(230, 61, 27, 21);
			jButton.setText("...");
			// jButton.setEnabled(false);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Complex c = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (c != null) {
						DgEmsEdiTrImgExg.this.complex = c;
						getJTextField2().setText(
								complex.getCode().substring(0, 4));
						jTextField3.setText(complex.getName());
					}

				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(99, 94, 247, 22);
			// jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(99, 125, 247, 22);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(83, 161, 78, 25);
			jButton1.setText("确定");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) { // 只有保存
					boolean ischange = false;
					if (JOptionPane.showConfirmDialog(DgEmsEdiTrImgExg.this,
							"是否同时更新到【内部归并】中?", "提示", 0) == 0) {
						ischange = true;
					}
					if (DgEmsEdiTrImgExg.this.isImg) {
						if (ischange) {
							manualdeclearAction.changeCustomsFourNo(
									new Request(CommonVars.getCurrUser()),
									MaterielType.MATERIEL, emsEdiTrImg
											.getPtNo(), jTextField2.getText(),
									jTextField3.getText());
						}
						fillDataImg(emsEdiTrImg);
						emsEdiTrImg = manualdeclearAction.saveEmsEdiTrImg(
								new Request(CommonVars.getCurrUser()),
								emsEdiTrImg);
						if (!isAdd) {
							tableModel.updateRow(emsEdiTrImg);
						} else {
							tableModel.addRow(emsEdiTrImg);
						}
						DgEmsEdiTrImgExg.this.dispose();
					} else {
						if (ischange) {
							manualdeclearAction.changeCustomsFourNo(
									new Request(CommonVars.getCurrUser()),
									MaterielType.FINISHED_PRODUCT, emsEdiTrExg
											.getPtNo(), jTextField2.getText(),
									jTextField3.getText());
						}
						fillDataExg(emsEdiTrExg);
						emsEdiTrExg = manualdeclearAction.saveEmsEdiTrExg(
								new Request(CommonVars.getCurrUser()),
								emsEdiTrExg);
						if (!isAdd) {
							tableModel.updateRow(emsEdiTrExg);
						} else {
							tableModel.addRow(emsEdiTrExg);
						}
						DgEmsEdiTrImgExg.this.dispose();
					}

				}
			});

		}
		return jButton1;
	}

	public void fillDataImg(EmsEdiTrImg emsEdiTrImg) { // 料件保存
		EmsEdiTrImg emsEdiTrImgOld = new EmsEdiTrImg();
		emsEdiTrImgOld = (EmsEdiTrImg) emsEdiTrImg.clone();
		emsEdiTrImg.setName(jTextField3.getText().trim());
		emsEdiTrImg.setNote(jTextField4.getText().trim());
		emsEdiTrImg.setComplex(jTextField2.getText().trim());
		Integer oldPtNo = emsEdiTrImg.getPtNo();
		Integer newPtNo = Integer.valueOf(tfSeqNum.getText());
		if (!oldPtNo.equals(newPtNo)) { // 说明已有修改备案序号
			// 更新内部归并的序号
			manualdeclearAction.modityInnergerFourSeqNum(new Request(CommonVars
					.getCurrUser()), "2", oldPtNo, newPtNo);
			emsEdiTrImg.setPtNo(Integer.valueOf(tfSeqNum.getText()));
		}
		if (!emsEdiTrImg.fullEquals(emsEdiTrImgOld)
				&& !emsEdiTrImg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsEdiTrImg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsEdiTrImg.getEmsEdiTrHead().getDeclareType().equals(
						DelcareType.MODIFY)) {
			emsEdiTrImg.setModifyMark(ModifyMarkState.MODIFIED);
			emsEdiTrImg.setChangeDate(new Date());// 变更次数
			if (emsEdiTrHead.getDeclareType().equals(DelcareType.MODIFY)) {
				emsEdiTrImg.setModifyTimes(emsEdiTrHead.getModifyTimes());
			}
		}

	}

	public void fillDataExg(EmsEdiTrExg emsEdiTrExg) { // 成品保存
		EmsEdiTrExg emsEdiTrExgOld = new EmsEdiTrExg();
		emsEdiTrExgOld = (EmsEdiTrExg) emsEdiTrExg.clone();
		emsEdiTrExg.setName(jTextField3.getText().trim());
		emsEdiTrExg.setNote(jTextField4.getText().trim());
		emsEdiTrExg.setComplex(jTextField2.getText().trim());
		Integer oldPtNo = emsEdiTrExg.getPtNo();
		Integer newPtNo = Integer.valueOf(tfSeqNum.getText());
		if (!oldPtNo.equals(newPtNo)) { // 说明已有修改备案序号
			// 更新内部归并的序号
			manualdeclearAction.modityInnergerFourSeqNum(new Request(CommonVars
					.getCurrUser()), "0", oldPtNo, newPtNo);
			emsEdiTrExg.setPtNo(Integer.valueOf(tfSeqNum.getText()));
		}
		if (!emsEdiTrExg.fullEquals(emsEdiTrExgOld)
				&& !emsEdiTrExg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsEdiTrExg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsEdiTrExg.getEmsEdiTrHead().getDeclareType().equals(
						DelcareType.MODIFY)) {
			emsEdiTrExg.setModifyMark(ModifyMarkState.MODIFIED);
			emsEdiTrExg.setChangeDate(new Date());// 变更次数
			if (emsEdiTrHead.getDeclareType().equals(DelcareType.MODIFY)) {
				emsEdiTrExg.setModifyTimes(emsEdiTrHead.getModifyTimes());
			}
		}

	}

	/**
	 * 
	 * This method initializes
	 * jButton2emseditr.getDeclareType().equals(DelcareType.APPLICATION)
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(195, 161, 80, 24);
			jButton2.setText("取消");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiTrImgExg.this.dispose();

				}
			});

		}
		return jButton2;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the isImg.
	 */
	public boolean isImg() {
		return isImg;
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return Returns the emsEdiTrHead.
	 */
	public EmsEdiTrHead getEmsEdiTrHead() {
		return emsEdiTrHead;
	}

	/**
	 * @param emsEdiTrHead
	 *            The emsEdiTrHead to set.
	 */
	public void setEmsEdiTrHead(EmsEdiTrHead emsEdiTrHead) {
		this.emsEdiTrHead = emsEdiTrHead;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
