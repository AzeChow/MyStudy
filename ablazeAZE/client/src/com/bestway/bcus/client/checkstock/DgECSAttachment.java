/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.DgFptBillImport;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgECSAttachment extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton btnOk = null;

	private JButton jButton1 = null;

	private ECSAttachmentManagement attachment = null;//附件
	
	private ECSCheckStockAction ecsCheckStockAction = null;
	
	private File attachmentFile;
	
	/**
	 * 编辑状态
	 */
	private int dataState = DataState.ADD;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JButton btnSelect;
	private JTextField tfRemark;
	private JLabel label;
	
	/**
	 * This is the default constructor
	 */
	public DgECSAttachment() {
		super();
		this.dataState = dataState;
		ecsCheckStockAction = (ECSCheckStockAction)CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(305, 210);
		this.setTitle("上传附件");
		getJPanel2().add(getLabel_2());
		getJPanel2().add(getTextField());
		getJPanel2().add(getBtnSelect());
	}
	
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getBtnOk(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfRemark());
			jPanel2.add(getLabel());
		}
		return jPanel2;
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
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(53, 124, 71, 23);
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if(textField.getText()!=null&&!textField.getText().trim().isEmpty()){
						//上传文件
						upLoadFileDataRunnable load = new upLoadFileDataRunnable();
						new Thread(load).start();
					}else{
						JOptionPane.showMessageDialog(DgECSAttachment.this, "请先选择要上传文件！");
					}
				}
			});

		}
		return btnOk;
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
			jButton1.setText("取消");
			jButton1.setBounds(154, 124, 70, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgECSAttachment.this.dispose();
				}
			});

		}
		return jButton1;
	}
	private JLabel getLabel_2() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("选择文件");
			lblNewLabel.setBounds(30, 78, 54, 15);
		}
		return lblNewLabel;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(82, 75, 142, 21);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton("选择");
			btnSelect.setBounds(234, 74, 23, 23);
			btnSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
//					fileChooser.setFileFilter(new CommonFileFilter(
//							new String[] { "*" }, "选择文件"));
					int state = fileChooser
							.showOpenDialog(DgECSAttachment.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					attachmentFile = fileChooser.getSelectedFile();
					if (attachmentFile == null || !attachmentFile.exists()) {
						return;
					}
					textField.setText(attachmentFile.getName());
				}
			});
		}
		return btnSelect;	}
	
	//上传文件
	class upLoadFileDataRunnable implements Runnable {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgECSAttachment.this);
				CommonProgress.setMessage("系统正在上传文件资料，请稍后...");
				
				ZipUtil zip = new ZipUtil();
				if(attachmentFile==null){
					JOptionPane.showMessageDialog(DgECSAttachment.this, "清先选择文件");
				}
				String fileName = attachmentFile.getName();
				if (fileName.indexOf(".") >= 0) {
					fileName = fileName.substring(0, fileName.lastIndexOf("."));
				}
				String newFile = attachmentFile.getParentFile()+"\\"+fileName + ".zip";
				zip.zip(newFile, attachmentFile);
				
				byte[] fileEntity = zip.fileToBytes(newFile);//将文件转成byte[]
				ECSAttachmentManagement attachment = getAttachment();
				attachment.setAttachmentName(attachmentFile.getName());//文件名称
				attachment.setUpdateDate(new Date());
				ecsCheckStockAction.updateAttachment(new Request(CommonVars.getCurrUser()), attachment);
				ecsCheckStockAction.upLoadAttachment(new Request(CommonVars.getCurrUser()),fileEntity,fileName+attachment.getId()+ ".zip");
				new File(newFile).delete();
				DgECSAttachment.this.dispose();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}
	
	private JTextField getTfRemark() {
		if (tfRemark == null) {
			tfRemark = new JTextField();
			tfRemark.setColumns(10);
			tfRemark.setBounds(82, 35, 142, 21);
		}
		return tfRemark;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备注：");
			label.setBounds(30, 38, 54, 15);
		}
		return label;
	}

	/**
	 * 附件
	 * @return
	 */
	public ECSAttachmentManagement getAttachment() {
		return attachment;
	}

	/**
	 * 附件
	 * @param attachment
	 */
	public void setAttachment(ECSAttachmentManagement attachment) {
		this.attachment = attachment;
	}
	
 }
