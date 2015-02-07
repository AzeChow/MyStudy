/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bestway.ui.winuicontrol.JFrameBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBalanceInfoTip extends JFrameBase {  
    private JPanel jContentPane = null;
    private JScrollPane jScrollPane = null;
    private JEditorPane epnInfo = null;

    /**
     * This method initializes
     * 
     */
    public FmBalanceInfoTip(String tipInfo,String title) {
        super();       
        initialize();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.epnInfo.setText(tipInfo);
        this.epnInfo.setCaretPosition(0);
        this.setTitle(title);
    }
    
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(733, 541);
        this.setContentPane(getJContentPane());        
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
        }
        return jContentPane;
    }

    /**
     * This method initializes jScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setViewportView(getEpnInfo());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jepInfo	
     * 	
     * @return javax.swing.JEditorPane	
     */
    private JEditorPane getEpnInfo() {
        if (epnInfo == null) {
            epnInfo = new JEditorPane();
        }
        return epnInfo;
    }

   
}
