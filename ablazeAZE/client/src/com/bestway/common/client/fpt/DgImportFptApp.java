package com.bestway.common.client.fpt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgImportFptApp extends JDialogBase {

	
	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField jTextField = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

    public FptManageAction fptManageAction = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="440,82"

	private boolean isOk = false;

	private FptAppHead fptAppHead = null;  //  @jve:decl-index=0:

	private File excelFile = null;

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	private FmCommon cfm=null;
	private FmFptExgAppHead ofm=null;
	private FmFptImgAppHead ifm=null;
	private String fptInOutFlag;
	private JCalendarComboBox cbbEndDate;
	
	private boolean isQp;

	public boolean isQp() {
		return isQp;
	}

	public void setQp(boolean isQp) {
		this.isQp = isQp;
	}

	public String getFptInOutFlag() {
		return fptInOutFlag;
	}

	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}


	public FptAppHead getFptAppHead() {
		return fptAppHead;
	}

	public void setFptAppHead(FptAppHead fptAppHead) {
		this.fptAppHead = fptAppHead;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportFptApp(FmCommon fm) {
		super();
		this.cfm=fm;
		initialize();
        this.fptManageAction = (FptManageAction) CommonVars
        .getApplicationContext().getBean(
                "fptManageAction");
	}

	public void setVisible(boolean b) {
		if (b) {
		}
		 if(FptInOutFlag.OUT.equals(fptInOutFlag)){
			 this.ofm=(FmFptExgAppHead)cfm; 
		 }else{
			 this.ifm=(FmFptImgAppHead)cfm; 
		 }
		super.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(384, 169));
		this.setTitle("导入Excel");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(28, 32, 55, 20));
			jLabel.setText("Excel文件");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			
			JLabel label = new JLabel();
			label.setText("有效日期");
			label.setBounds(new Rectangle(28, 32, 55, 20));
			label.setBounds(28, 66, 55, 20);
			jContentPane.add(label);
			jContentPane.add(getCbbEndDate());
		}
		return jContentPane;
	}
	
	public JCalendarComboBox getCbbEndDate(){
		if(cbbEndDate==null){
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(100, 20));
			cbbEndDate.setBounds(87, 64, 126, 20);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(87, 32, 224, 22));
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(311, 32, 25, 22));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportFptApp.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					excelFile = fileChooser.getSelectedFile();
					jTextField.setText(excelFile.getPath());
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(101, 94, 70, 26));
			jButton1.setText("确定");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButton1.setEnabled(false);
					if(isQp){
						new ImportFileDataRunnable().start();
					}else{
						isOk=true;
						dispose();
						return;
					}
				}
			});
		}
		return jButton1;
	}

	class ImportFileDataRunnable extends Thread {
		

		public void run() {
			try {			
				if (excelFile == null || !excelFile.exists()) {
					JOptionPane.showMessageDialog(
							DgImportFptApp.this, "请选择需要导入的文件！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				Request request = new Request(CommonVars.getCurrUser());
				
				byte[] excelFileContent = null;
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(excelFile);
					excelFileContent = new byte[(int) excelFile.length()];
					fileInputStream.read(excelFileContent);
				} catch (Exception ex) {
					throw new RuntimeException(ex.getMessage());
				} finally {
					try {
						fileInputStream.close();
					} catch (IOException ex1) {
						throw new RuntimeException(ex1.getMessage());
					}
				}
				List<FptAppHead> isExists = fptManageAction.importFptAppisExists(new Request(CommonVars.getCurrUser()),
						excelFileContent,fptInOutFlag);
				Date endDate = cbbEndDate.getDate();
				if(!isExists.isEmpty() && isExists.size() >= 1){
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(null, "已经存在相应的数据"+isExists.size()+"条，是否覆盖?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)")){
						
						// CommonStepProgress.showStepProgressDialog(taskId);
						String taskId = CommonProgress.getExeTaskId();
						CommonStepProgress.showStepProgressDialog(taskId);
						request.setTaskId(taskId);
						CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
						CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");
						
						List<Object[]> errList = fptManageAction.importFptApp(
								new Request(CommonVars.getCurrUser()),
								excelFileContent, taskId,fptInOutFlag,isExists,endDate);
						
						if(!errList.isEmpty()){
							DgErrorMessage dgErrorMessage = new DgErrorMessage();
							dgErrorMessage.initTable(errList);
							dgErrorMessage.setVisible(true);
							return;
						}
						
						
						DgImportFptApp.this.isOk = true;
						DgImportFptApp.this.dispose();
					}else {
						return;
					}
				}else {
					// CommonStepProgress.showStepProgressDialog(taskId);
					String taskId = CommonProgress.getExeTaskId();
					CommonStepProgress.showStepProgressDialog(taskId);
					request.setTaskId(taskId);
					CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
					CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");
					
					List<Object[]> errList = fptManageAction.importFptApp(
							new Request(CommonVars.getCurrUser()),
							excelFileContent, taskId,fptInOutFlag,isExists,endDate);
					
					if(!errList.isEmpty()){
						DgErrorMessage dgErrorMessage = new DgErrorMessage();
						dgErrorMessage.initTable(errList);
						dgErrorMessage.setVisible(true);
						return;
					}
					
					
					DgImportFptApp.this.isOk = true;
					DgImportFptApp.this.dispose();
				}

					
				CommonStepProgress.closeStepProgressDialog();
				
				
				if(FptInOutFlag.OUT.equals(fptInOutFlag)){
					ofm.refresh();
				 }else{
					ifm.refresh();
				 }
				JOptionPane.showMessageDialog(DgImportFptApp.this,
						"导入数据成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgImportFptApp.this,
						"导入数据失败！\n" + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				jButton1.setEnabled(true);
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(187, 94, 70, 26));
			jButton2.setText("取消");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
		}
		return buttonGroup;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
