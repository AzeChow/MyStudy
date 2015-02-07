/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableColorRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JToolBar;
import javax.swing.JTextField;
/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBillReverseUnit extends JDialogBase {

    private JPanel                jContentPane           = null;
    private ButtonGroup           buttonGroup            = null;
    private JTableListModel       tableModelTbFirst1     = null;
    private JTableListModel       tableModelTbFirst2     = null;
    private JTableListModel       tableModelTbFour3      = null;
    private JTableListModel       tableModelTbFour2      = null;
    private JTableListModel       tableModelTbFour1      = null;
    private ContractAnalyseAction contractAnalyseAction  = null;
    private ContractAction        contractAction         = null;

    private static final int      ALL_SELECT_ITEM        = 123;
    private static int            SEARCH_TYPE            = SearchType.NAME_SPEC_CODE_UNIT;

	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton4 = null;
	private JComboBox jComboBox = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
    /**
     * This method initializes
     * 
     */
    public DgBillReverseUnit() {
        super();
        initialize();
        contractAnalyseAction = (ContractAnalyseAction) CommonVars
                .getApplicationContext().getBean("contractAnalyseAction");
        contractAction = (ContractAction) CommonVars.getApplicationContext()
                .getBean("contractAction");
        initUIComponents();

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("由单据反算基本资料里的单位折算");
        this.setContentPane(getJContentPane());
        this.setSize(755, 539);        
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
            jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
            jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
        }
        return jContentPane;
    }

    /**
     * 初始化数据
     * 
     */
    private void initUIComponents() {
       

    }

    /**
     * 打印
     */
    private void printReport() {
        JOptionPane.showMessageDialog(this, "没有数据可以打印!!", "提示",
                JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    /**
     * 查询
     */
    private void search() {
       
    }

   

    


	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new java.awt.Dimension(397,35));
			jToolBar.add(getJPanel());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("开始计算");
			jButton.setPreferredSize(new java.awt.Dimension(60,55));
		}
		return jButton;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("关闭");
		}
		return jButton4;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(69, 4, 103, 24);
		}
		return jComboBox;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(9, 4, 58, 24);
			jLabel.setText("商品类型:");
			jLabel1.setBounds(179, 4, 449, 24);
			jLabel1.setText("注意：1.计算依据为本年度生效单据2.当工厂商品单位与报关商品单位一致时,不反算");
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
		}
		return jPanel;
	}
           } // @jve:decl-index=0:visual-constraint="12,46"
