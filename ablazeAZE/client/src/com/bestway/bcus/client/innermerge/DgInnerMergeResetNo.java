/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class DgInnerMergeResetNo extends JDialogBase {

    private JPanel               jContentPane         = null;
    private JTextField           tfRowNumber          = null;
    private JButton              btnOK                = null;
    private JButton              btnCancel            = null;
    private JPanel               jPanel               = null;
    private List                 currentRows          = null;
    private MultiSpanCellTable   jTable               = null;
    private CommonBaseCodeAction commonBaseCodeAction = null;
    private boolean              tenInnerMergeSort    = false;
    private boolean              isOk                 = false;

    /**
     * This method initializes
     *  
     */
    public DgInnerMergeResetNo() {
        super();
        commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                .getApplicationContext().getBean("commonBaseCodeAction");
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
            btnOK.setBounds(133, 88, 71, 25);
            btnOK.setText("确定");
            btnOK.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    isOk = true;
                    new CustomInnerMergeDataThread().start();
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
        if (isTenInnerMergeSort() == true) {
            int toRowNumber = Integer.parseInt(tfRowNumber.getText());
            AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
                    .getModel();
            InnerMergeData innerMergeData = (InnerMergeData) tableModel
                    .getCurrentRow();
            if (!this.checkTenInnerMergeSort(toRowNumber)) {
                return;
            }
            if (innerMergeData.getImrType().trim().equals(MaterielType.FINISHED_PRODUCT)
                    || innerMergeData.getImrType().trim().equals(MaterielType.MATERIEL)) {
                List list = this.commonBaseCodeAction.findInnerMergeByFourNo(
                        new Request(CommonVars.getCurrUser()), innerMergeData
                                .getImrType(), innerMergeData.getHsFourNo()
                                .intValue());
                List tempList = commonBaseCodeAction
                        .findInnerMergeInEmsEdiMergeAfter(new Request(
                                CommonVars.getCurrUser()), list);
                if (tempList.size() > 0) {
                    JOptionPane.showMessageDialog(this,
                            "选中的行中有数据在归并归系中备案,不能进行重排!!", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }            
            tenResetData(toRowNumber);
        } else {
            int toRowNumber = Integer.parseInt(tfRowNumber.getText());
            AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
                    .getModel();
            InnerMergeData innerMergeData = (InnerMergeData) tableModel
                    .getCurrentRow();
            if (!this.checkFourInnerMergeSort(toRowNumber)) {
                return;
            }
            if (innerMergeData.getImrType().trim().equals(MaterielType.FINISHED_PRODUCT)
                    ) {
                boolean b = this.commonBaseCodeAction.findHasEmsEdiTrImg(
                        new Request(CommonVars.getCurrUser()));               
                if (b) {
                    JOptionPane.showMessageDialog(this,
                            "成品在经营范围中备案,不能进行重排!!", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }else if(innerMergeData.getImrType().trim().equals(MaterielType.MATERIEL)){
                boolean b = this.commonBaseCodeAction.findHasEmsEdiTrExg(
                        new Request(CommonVars.getCurrUser()));               
                if (b) {
                    JOptionPane.showMessageDialog(this,
                            "料件在经营范围中备案,不能进行重排!!", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }            
            fourResetData(toRowNumber);
        }
    }

    /**
     * 十位重排
     */
    private void tenResetData(int toRowNumber) {
        try {
            this.btnOK.setEnabled(false);
            AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
                    .getModel();
            CommonProgress.showProgressDialog(DgInnerMergeResetNo.this);
            CommonProgress.setMessage("正在进行十位数据重排,请稍候...");
            commonBaseCodeAction.reSortMergeTenNo(new Request(CommonVars
                    .getCurrUser()), currentRows, toRowNumber);
            //            tableModel.updateRows(ls);
            //            refreshTable();
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据重排条件可能有错!!", "信息!!", 0);
        } finally {
            CommonProgress.closeProgressDialog();
            this.btnOK.setEnabled(true);
        }
    }

    /**
     * 四位重排
     */
    private void fourResetData(int toRowNumber) {
        try {
            this.btnOK.setEnabled(false);
            AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
                    .getModel();
            CommonProgress.showProgressDialog(DgInnerMergeResetNo.this);
            CommonProgress.setMessage("正在进行四位数据重排,请稍候...");
            commonBaseCodeAction.reSortMergeFourNo(new Request(CommonVars
                    .getCurrUser()), currentRows, toRowNumber);
            //            tableModel.updateRows(ls);
            //            refreshTable();
            this.dispose();
        } catch (Exception e) {
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
     * 检查四位重排数据
     */
    private boolean checkFourInnerMergeSort(int toRowNumber) {
        boolean isTrue = false;
        Request requet = new Request(CommonVars.getCurrUser());
        switch (commonBaseCodeAction.checkDataForFourInnerMergeSort(requet,
                currentRows, toRowNumber)) {
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
     * 检查十位重排数据
     */
    private boolean checkTenInnerMergeSort(int toRowNumber) {
        boolean isTrue = false;
        if (!checkTneInner(toRowNumber)) {
            return isTrue;
        }
        Request requet = new Request(CommonVars.getCurrUser());
        List ls = null;
        switch (commonBaseCodeAction.checkDataForTenInnerMergeSort(requet,
                currentRows, toRowNumber)) {
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

    private boolean checkTneInner(int toRowNumber) {
        boolean isOk = false;
        AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
                .getModel();
        switch (commonBaseCodeAction
                .checkTenInnerMergeDataInFourMerge(new Request(CommonVars
                        .getCurrUser()), currentRows, toRowNumber)) {
        case 0:
            isOk = true;
            break;
        case -1:
            JOptionPane.showMessageDialog(null, "没有选中的行!!", "警告", 0);
            break;
        case -2:
            JOptionPane.showMessageDialog(null, "选中的有空行,不能重排!!", "警告", 0);
            break;
        case -3:
            JOptionPane.showMessageDialog(null, "在多个四位归并类别中不能重排!!", "警告", 0);
            break;
        case -4:
            JOptionPane.showMessageDialog(null, "重排行数超出范围!!", "警告", 0);
            break;
        }
        return isOk;
    }

    /**
     * This method initializes btnCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setBounds(237, 86, 68, 25);
            btnCancel.setText("取消");
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgInnerMergeResetNo.this.dispose();
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
     * @return Returns the jTable.
     */
    public MultiSpanCellTable getJTable() {
        return jTable;
    }

    /**
     * @param table
     *            The jTable to set.
     */
    public void setJTable(MultiSpanCellTable table) {
        jTable = table;
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
     * @return Returns the tenInnerMerge.
     */
    public boolean isTenInnerMergeSort() {
        return tenInnerMergeSort;
    }

    /**
     * @param tenInnerMerge
     *            The tenInnerMerge to set.
     */
    public void setTenInnerMergeSort(boolean tenInnerMerge) {
        this.tenInnerMergeSort = tenInnerMerge;
    }
} //  @jve:decl-index=0:visual-constraint="10,10"
