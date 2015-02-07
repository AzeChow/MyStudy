package com.bestway.wjsz.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.waijing.action.WjszAction;

public class DgSBaseDataWjCode extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnUndo = null;
	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnClose = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfSeqNum = null;
	private JLabel jLabel1 = null;
	private JTextField tfWjCode = null;
	private int dataState = DataState.BROWSE;
	private JTableListModel tableModel = null;
	private boolean isLj = true;
    private BcsDictPorImg img = null;
    private BcsDictPorExg exg = null;
    
    private DzscEmsImgWj dzscImg = null;
    private DzscEmsExgWj dzscExg = null;  //  @jve:decl-index=0:
    private WjszAction wjszAction = null;
    private int selectInt = 0;
	/**
	 * This is the default constructor
	 */
	public DgSBaseDataWjCode() {
		super();
		wjszAction = (WjszAction) CommonVars.getApplicationContext().getBean("wjszAction");
		initialize();
	}

	
	public void setVisible(boolean b) {
        if (b) {
            if (dataState != DataState.ADD){//修改
                showData();                
            }            
            setState();
        }
        initkey();
        super.setVisible(b);        
    }
    
	 private void setState() {
         this.btnEdit.setEnabled(dataState == DataState.BROWSE);//编辑
         this.btnSave.setEnabled(dataState != DataState.BROWSE);//保存
         this.btnUndo.setEnabled(dataState != DataState.BROWSE);//取消
         this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
         this.btnNext.setEnabled(tableModel.hasNextRow());
       
         this.tfWjCode.setEditable(dataState != DataState.BROWSE);
     }
	
	//显示输入
    private void showData() {
    	if (selectInt == 1){//电子手册
    		if (isLj){//料件
    			dzscImg = (DzscEmsImgWj)tableModel.getCurrentRow();
    			this.tfSeqNum.setText(dzscImg.getSeqNum()==null?"":String.valueOf(dzscImg.getSeqNum()));
    			this.tfWjCode.setText(dzscImg.getWjCode());
    		} else {
    			dzscExg = (DzscEmsExgWj)tableModel.getCurrentRow();
    			this.tfSeqNum.setText(dzscExg.getSeqNum()==null?"":String.valueOf(dzscExg.getSeqNum()));
    			this.tfWjCode.setText(dzscExg.getWjCode());
    		}
    	} else if (selectInt == 2){ //电子化手册
    		if (isLj){//料件
    			img = (BcsDictPorImg)tableModel.getCurrentRow();
    			this.tfSeqNum.setText(img.getSeqNum()==null?"":String.valueOf(img.getSeqNum()));
    			this.tfWjCode.setText(img.getWjCode());
    		} else {
    			exg = (BcsDictPorExg)tableModel.getCurrentRow();
    			this.tfSeqNum.setText(exg.getSeqNum()==null?"":String.valueOf(exg.getSeqNum()));
    			this.tfWjCode.setText(exg.getWjCode());
    		}
    	}
		
    }
    
    
    private void initkey(){
        List<Object> components = new ArrayList<Object>();
        components.add(this.tfWjCode);
        components.add(this.btnSave);
        components.add(new Component[] { this.btnNext,this.btnClose });
        this.setComponentFocusList(components);
    }
    
    
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(344, 225);
		this.setContentPane(getJContentPane());
		this.setTitle("修改编码");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;//修改 
			        setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (selectInt == 1){//电子手册
						if (isLj){
							dzscImg.setWjCode(tfWjCode.getText().trim());
							dzscImg = (DzscEmsImgWj) wjszAction.saveDzscEmsImgWj(new Request(CommonVars.getCurrUser()),dzscImg);
							tableModel.updateRow(dzscImg);
							
						} else {
							dzscExg.setWjCode(tfWjCode.getText().trim());
							dzscExg = (DzscEmsExgWj) wjszAction.saveDzscEmsExgWj(new Request(CommonVars.getCurrUser()),dzscExg);
							tableModel.updateRow(dzscExg);
							
						}
					} else if (selectInt == 2){//电子化手册
						if (isLj){
							img.setWjCode(tfWjCode.getText().trim());
							img = (BcsDictPorImg) wjszAction.saveBcsDictPorImg(new Request(CommonVars.getCurrUser()),img);
							tableModel.updateRow(img);
							
						} else {
							exg.setWjCode(tfWjCode.getText().trim());
							exg = (BcsDictPorExg) wjszAction.saveBcsDictPorExg(new Request(CommonVars.getCurrUser()),exg);
							tableModel.updateRow(exg);
							
						}
					}					
					dataState = DataState.BROWSE;
			        setState();
					
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();//取消
			        dataState = DataState.BROWSE;
			        setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
			            btnPrevious.setEnabled(false);
			            btnNext.setEnabled(true);
			        } else {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(true);
			        }
			        showData();//上笔
			        dataState = DataState.EDIT;
			        setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(false);
			        } else {
			            btnPrevious.setEnabled(true);
			            btnNext.setEnabled(true);
			        }
			        showData();//下笔
			        dataState = DataState.EDIT;
			        setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();//关闭
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(53,77,72,26));
			jLabel1.setText("外经凭证序号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(52,35,71,27));
			jLabel.setText("备案序号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSeqNum(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfWjCode(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfSeqNum	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setEditable(false);
			tfSeqNum.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			tfSeqNum.setForeground(java.awt.Color.red);
			tfSeqNum.setBounds(new java.awt.Rectangle(124,35,158,26));
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes tfWjCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfWjCode() {
		if (tfWjCode == null) {
			tfWjCode = new JTextField();
			tfWjCode.setBounds(new java.awt.Rectangle(124,77,158,26));
		}
		return tfWjCode;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}


	public boolean isLj() {
		return isLj;
	}


	public void setLj(boolean isLj) {
		this.isLj = isLj;
	}


	public DzscEmsExgWj getDzscExg() {
		return dzscExg;
	}


	public void setDzscExg(DzscEmsExgWj dzscExg) {
		this.dzscExg = dzscExg;
	}


	public DzscEmsImgWj getDzscImg() {
		return dzscImg;
	}


	public void setDzscImg(DzscEmsImgWj dzscImg) {
		this.dzscImg = dzscImg;
	}


	public int getSelectInt() {
		return selectInt;
	}


	public void setSelectInt(int selectInt) {
		this.selectInt = selectInt;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
