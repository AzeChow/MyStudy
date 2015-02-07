/*
 * Created on 2004-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgThirdMerge extends JDialogBase {

    private javax.swing.JPanel   jContentPane          = null;
    private JLabel               jLabel2               = null;
    private JToolBar             jToolBar2             = null;
    private JButton              btnAdd                = null;
    private JButton              btnEdit               = null;
    private JButton              btnSave               = null;
    private JButton              btnCancel             = null;
    private JTextField           tfHsFourNo            = null;
    private JTextField           tfHsFourMaterielName  = null;
    private JTextField           tfHsFourCode          = null;
    private JLabel               jLabel3               = null;
    private JButton              btnComplex            = null;
    private JButton              jButton1              = null;
    private JComboBox            cbbMaterielType       = null;

    private int                  dataState             = DataState.BROWSE; ;
    private JTableListModel      tableModel            = null;
    private String               materielType          = null;
    private ReverseMergeFourData reverseMergeFourData  = null;  //  @jve:decl-index=0:
    private CommonBaseCodeAction commonBaseCodeAction  = null;
    private CustomBaseAction		customBaseAction		= null;
    private boolean              hasTenCodeChildRecord = false;
    private boolean              isPutOnRecord         = false;

    //private
    /**
     * This is the default constructor
     */
    public DgThirdMerge(JDialog owner) {
        super(owner,true);
        commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                .getApplicationContext().getBean("commonBaseCodeAction");
        customBaseAction = (CustomBaseAction) CommonVars
		.getApplicationContext().getBean("customBaseAction");
        initialize();
        initUIComponents();

    }

    public void setVisible(boolean isFlag) {
        if (isFlag) {
            showData();
            if (dataState == DataState.EDIT) {
                int count = commonBaseCodeAction
                        .findReverseMergeTenDataCountByFour(new Request(
                                CommonVars.getCurrUser()),
                                this.reverseMergeFourData);
                if (count > 0) {
                    hasTenCodeChildRecord = true;
                }
            }
            setState();
        }
        super.setVisible(isFlag);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("四码反归并管理");
        this.setSize(401, 255);
        this.setContentPane(getJContentPane());

    }

    /**
     * @return Returns the reverseMergeFourData.
     */
    public ReverseMergeFourData getReverseMergeFourData() {
        return reverseMergeFourData;
    }

    /**
     * @return Returns the isPutOnRecord.
     */
    public boolean isPutOnRecord() {
        return isPutOnRecord;
    }

    /**
     * @param isPutOnRecord
     *            The isPutOnRecord to set.
     */
    public void setPutOnRecord(boolean isPutOnRecord) {
        this.isPutOnRecord = isPutOnRecord;
    }

    /**
     * @param reverseMergeFourData
     *            The reverseMergeFourData to set.
     */
    public void setReverseMergeFourData(
            ReverseMergeFourData reverseMergeFourData) {
        this.reverseMergeFourData = reverseMergeFourData;
    }

    /*
     * jContentPane.add(getJTextField(), null); jConten
     * jContentPane.add(jLabel3, null); jLabel3.setText("JLabel");
     * tPane.add(getJTextField1(), null); jContentPane.add(getJTextField2(),
     * null); jContentPane.add(getJTextField3(), null); return
     * javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {

            JLabel jLabel1 = new JLabel();
            JLabel jLabel = new JLabel();
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(null);

            jLabel.setBounds(25, 56, 48, 20);
            jLabel.setText("类别");
            jLabel1.setBounds(25, 95, 48, 20);
            jLabel1.setText("归并序号");
            jContentPane.add(getJToolBar2(), null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel1, null);
            jLabel3 = new JLabel();
            jLabel3.setBounds(208, 56, 48, 20);
            jContentPane.add(jLabel3, null);
            jLabel2 = new JLabel();
            jLabel2.setBounds(208, 94, 48, 20);
            jLabel2.setText("商品名称");
            jLabel3.setText("商品编码");
            jContentPane.add(jLabel2, null);
            jContentPane.add(getBtnComplex(), null);
            jContentPane.add(getTfHsFourNo(), null);
            jContentPane.add(getTfHsFourMaterielName(), null);
            jContentPane.add(getTfHsFourCode(), null);
            jContentPane.add(getCbbMaterielType(), null);
        }
        return jContentPane;
    }

    /**
     * @return Returns the dataState.
     */
    public int getDataState() {
        return dataState;
    }

    /**
     * @return Returns the materielType.
     */
    public String getMaterielType() {
        return materielType;
    }

    /**
     * @param materielType
     *            The materielType to set.
     */
    public void setMaterielType(String materielType) {
        this.materielType = materielType;
    }

    /**
     * @param dataState
     *            The dataState to set.
     */
    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    /**
     * This method initializes jToolBar2
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar2() {
        if (jToolBar2 == null) {
            jToolBar2 = new JToolBar();
            jToolBar2.setBounds(0, 0, 393, 34);
            jToolBar2.add(getBtnAdd());
            jToolBar2.add(getBtnEdit());
            jToolBar2.add(getBtnSave());
            //jToolBar2.add(getBtnCancel());
            jToolBar2.add(getJButton1());
        }
        return jToolBar2;
    }

    /**
     * This method initializes btnAdd
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnAdd() {
        if (btnAdd == null) {
            btnAdd = new JButton();
            btnAdd.setText("新增");
            btnAdd.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dataState = DataState.ADD;
                    showData();
                    setState();
                }
            });
        }
        return btnAdd;
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
                    dataState = DataState.EDIT;
                    showData();
                    setState();
                }
            });
        }
        return btnEdit;
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
                    if (validateIsNull() == true) {
                        return;
                    }
                    if (!validateIsExist()) {
                        JOptionPane.showMessageDialog(DgThirdMerge.this,
                                "四位商品编码不存在", "警告",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    if(JOptionPane.showConfirmDialog(DgThirdMerge.this, "是否要保存以上的内容?","提示", 0)==0)
                    	save();
                }
            });
        }
        return btnSave;
    }

    /**
     * This method initializes btnCancel
     * 
     * @return javax.swing.JButton
     */
//    private JButton getBtnCancel() {
//        if (btnCancel == null) {
//            btnCancel = new JButton();
//            btnCancel.setText("取消");
//            btnCancel.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    if (reverseMergeFourData != null) {
//                        dataState = DataState.EDIT;
//                        showData();
//                    }
//                    dataState = DataState.BROWSE;
//                    setState();
//                }
//            });
//        }
//        return btnCancel;
//    }

    /**
     * This method initializes tfHsFourNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfHsFourNo() {
        if (tfHsFourNo == null) {
            tfHsFourNo = new JTextField();
            tfHsFourNo.setBounds(75, 95, 99, 20);
        }
        return tfHsFourNo;
    }

    /**
     * This method initializes tfHsFourMaterielName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfHsFourMaterielName() {
        if (tfHsFourMaterielName == null) {
            tfHsFourMaterielName = new JTextField();
            tfHsFourMaterielName.setBounds(260, 95, 99, 20);
        }
        return tfHsFourMaterielName;
    }

    /**
     * This method initializes tfHsFourCode
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfHsFourCode() {
        if (tfHsFourCode == null) {
            tfHsFourCode = new JTextField();
            tfHsFourCode.setBounds(260, 56, 99, 20);
            tfHsFourCode.setDocument(new PlainDocument() {
                public void insertString(int offs, String str, AttributeSet a)
                        throws BadLocationException {
                    if (str == null) {
                        return;
                    }
                    if (super.getLength() >= 4
                            || super.getLength() + str.length() > 4) {
                        return;
                    }
                    super.insertString(offs, str, a);
                }
            });
        }
        return tfHsFourCode;
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
     * This method initializes btnComplex
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnComplex() {
        if (btnComplex == null) {
            btnComplex = new JButton();
            btnComplex.setBounds(364, 56, 18, 19);
            btnComplex.setText("...");
            btnComplex.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Complex complex = (Complex) CommonQuery.getInstance()
                            .getComplex();
                    if (complex != null) {
                        tfHsFourCode.setText(complex.getCode().trim()
                                .substring(0, 4));
                    }
                }
            });
        }
        return btnComplex;
    }

    /**
     * This method initializes cbbMaterielType
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbMaterielType() {
        if (cbbMaterielType == null) {
            cbbMaterielType = new JComboBox();
            cbbMaterielType.setEnabled(false);
            cbbMaterielType.setBounds(75, 56, 99, 20);

        }
        return cbbMaterielType;

    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setText("关闭");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgThirdMerge.this.dispose();
                }
            });
        }
        return jButton1;
    }

    /**
     * 初始化组件
     */
    private void initUIComponents() {
        cbbMaterielType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
                "成品"));
        cbbMaterielType.addItem(new ItemProperty(
                MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
        cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
        cbbMaterielType.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
        cbbMaterielType.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
                "边角料"));
        cbbMaterielType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
                "残次品"));
    }

    /**
     * 显示数据
     *  
     */
    private void showData() {
        if (this.dataState == DataState.ADD) {
            this.cbbMaterielType.setSelectedIndex(ItemProperty.getIndexByCode(
                    this.materielType, this.cbbMaterielType));
            //
            // 设置最大的四位商品编码
            //
            int maxFourNo = this.commonBaseCodeAction
                    .findMaxFourReverseMergeNo(new Request(CommonVars
                            .getCurrUser()), this.materielType);
            this.tfHsFourNo.setText(String.valueOf(maxFourNo + 1));
            this.tfHsFourCode.setText("");
            this.tfHsFourMaterielName.setText("");
        } else if (this.dataState == DataState.EDIT) {
            if (this.reverseMergeFourData == null) {
                return;
            }
            if (this.reverseMergeFourData.getImrType() != null) {
                this.materielType = this.reverseMergeFourData.getImrType();
            }
            this.cbbMaterielType.setSelectedIndex(ItemProperty.getIndexByCode(
                    this.materielType, this.cbbMaterielType));
            if (this.reverseMergeFourData.getHsFourNo() != null) {
                this.tfHsFourNo.setText(this.reverseMergeFourData.getHsFourNo()
                        .toString());
            } else {
                this.tfHsFourNo.setText("");
            }
            if (this.reverseMergeFourData.getHsFourCode() != null) {
                this.tfHsFourCode.setText(this.reverseMergeFourData
                        .getHsFourCode());
            } else {
                this.tfHsFourCode.setText("");
            }
            if (this.reverseMergeFourData.getHsFourMaterielName() != null) {
                this.tfHsFourMaterielName.setText(this.reverseMergeFourData
                        .getHsFourMaterielName());
            } else {
                this.tfHsFourMaterielName.setText("");
            }
        }
    }

    /**
     * 填充对象
     */
    private void fillData() {
        this.reverseMergeFourData.setCompany(CommonVars.getCurrUser()
                .getCompany());
        this.reverseMergeFourData
                .setImrType(((ItemProperty) this.cbbMaterielType
                        .getSelectedItem()).getCode());
        this.reverseMergeFourData.setHsFourCode(this.tfHsFourCode.getText());
        this.reverseMergeFourData
                .setHsFourMaterielName(this.tfHsFourMaterielName.getText());

    }

    /**
     * 设置状态
     *  
     */
    private void setState() {
        this.btnAdd.setEnabled(dataState == DataState.BROWSE && !isPutOnRecord);
        this.btnEdit
                .setEnabled(dataState == DataState.BROWSE && !isPutOnRecord);
        this.btnSave
                .setEnabled(dataState != DataState.BROWSE && !isPutOnRecord);
//        this.btnCancel.setEnabled(dataState != DataState.BROWSE
//                && this.reverseMergeFourData != null && !isPutOnRecord);
        this.cbbMaterielType.setEnabled(false);
        this.tfHsFourNo.setEditable(false);
        this.tfHsFourCode.setEnabled(dataState != DataState.BROWSE
                && hasTenCodeChildRecord == false && !isPutOnRecord);
        this.btnComplex.setEnabled(dataState != DataState.BROWSE
                && hasTenCodeChildRecord == false && !isPutOnRecord);
        this.tfHsFourMaterielName.setEnabled(dataState != DataState.BROWSE
                && !isPutOnRecord);
    }

    /**
     * 验证数据
     */
    private boolean validateIsNull() {
        if (this.cbbMaterielType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "类别不可为空", "警告",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (this.tfHsFourCode.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "商品编码不可为空", "警告",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (this.tfHsFourNo.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "归并序号不可为空", "警告",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (this.tfHsFourMaterielName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "商品名称不可为空", "警告",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * 验证商品编码是否存在
     * 
     * @return
     */
    private boolean validateIsExist() {
    	String fourCode = this.tfHsFourCode.getText();
    	long count = customBaseAction.findComplexLikeFourCodeCount(fourCode);
    	if(count > 0){
    		return true;
    	}
    	return false;
    }

    /**
     * 保存
     *  
     */
    private void save() {
        if (dataState == DataState.ADD) {
            this.reverseMergeFourData = new ReverseMergeFourData();
            this.fillData();
            //
            // 设置最大的四位商品编码
            //
            int maxFourNo = this.commonBaseCodeAction
                    .findMaxFourReverseMergeNo(new Request(CommonVars
                            .getCurrUser()), this.materielType);
            this.reverseMergeFourData.setHsFourNo(new Integer(maxFourNo + 1));
            reverseMergeFourData = commonBaseCodeAction
                    .saveReverseMergeFourData(new Request(CommonVars
                            .getCurrUser()), reverseMergeFourData);
            this.tableModel.addRow(reverseMergeFourData);
        } else if (dataState == DataState.EDIT) {
            this.fillData();
            reverseMergeFourData = commonBaseCodeAction
                    .saveReverseMergeFourData(new Request(CommonVars
                            .getCurrUser()), reverseMergeFourData);
            this.tableModel.updateRow(reverseMergeFourData);
        }
        setDataState(DataState.BROWSE);
        setState();
    }

} //  @jve:decl-index=0:visual-constraint="10,10"
