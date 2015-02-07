/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.windows.form;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;

import com.bestway.client.common.CommonVariables;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.MouseEvent;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgAbout extends JDialogBase {

    private JPanel      jContentPane = null;
    private JEditorPane jEditorPane  = null;

    private JButton     jButton      = null;
    private JPanel      jPanel1      = null;
    private JEditorPane epnVesion = null;
    private JEditorPane epnAbout = null;

    private JScrollPane jScrollPane  = null;
    private JLabel jLabel = null;
	private JLabel jLabel1 = null;
    /**
     * This method initializes
     *  
     */
    public DgAbout() {
        super();
        initialize();
        this.setResizable(false);
        this.epnAbout.setText(CommonVariables.readFileByAbout().toString());
        this.epnVesion.setText(CommonVariables.readFileByVesion().toString());
        this.epnAbout.setCaretPosition(0);
    }
   

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("关于--"+CommonVariables.mainTitle);
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setSize(432, 359);

    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(7, 303, 409, 20));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel1.setForeground(SystemColor.controlDkShadow);
            jLabel1.setFont(new Font("Dialog", Font.BOLD, 12));
            jLabel1.setText(" 了解海关最新信息请关注我们的关务论坛   http://www.bsw.com.cn");
            jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {   
            	public void mouseExited(java.awt.event.MouseEvent e) {    
            		((JLabel)e.getSource()).setForeground(Color.BLACK);
            	}
            	public void mouseClicked(java.awt.event.MouseEvent e) {
            		String operatingSystem = System.getProperty("os.name");

					try {
						if (operatingSystem.toLowerCase().indexOf("windows") > -1) {
							Runtime
									.getRuntime()
									.exec(
											"rundll32 url.dll,FileProtocolHandler http://www.ibestsoft.com/");
						} else {
							// Try with htmlview...
							Runtime.getRuntime().exec(
									"htmlview http://www.ibestsoft.com/");
						}
					} catch (Exception ex) {
						javax.swing.JOptionPane.showMessageDialog(DgAbout.this,
								"Unable to open http://www.ibestsoft.com/",
								"", javax.swing.JOptionPane.ERROR_MESSAGE);
					}
            	}
            	/**
                 * Invoked when a mouse button has been released on a component.
                 */
                public void mouseReleased(MouseEvent e) {
                	((JLabel)e.getSource()).setForeground(Color.BLACK);
                }

                /**
                 * Invoked when the mouse enters a component.
                 */
                public void mouseEntered(MouseEvent e) {
                	((JLabel)e.getSource()).setForeground(Color.RED);
                }
            });
            jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(6, 4, 89, 229));
            jLabel.setIcon(new ImageIcon(getClass().getResource("/com/bestway/client/resource/images/about.gif")));
            jLabel.setText("");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setBackground(java.awt.SystemColor.controlLtHighlight);
            jContentPane.add(getJEditorPane(), null);
            jContentPane.add(getJButton(), null);
            jContentPane.add(getJPanel1(), null);
            jContentPane.add(getJEditorPane1(), null);
            jContentPane.add(getJScrollPane(), null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel1, null);
        }
        return jContentPane;
    }

    class JPanelImage extends JPanel {
        private Image image = null;

        public JPanelImage() {
            try {
                this.image = CommonVariables.getImageSource().getImage(
                        "about.image");
            } catch (Exception o) {

            }
        }

        public void paintComponent(Graphics g) {
            try {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(),
                        this);
            } catch (Exception e) {

            }
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
            jEditorPane.setBounds(6, 241, 343, 60);
            jEditorPane
                    .setText("【警告】本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。");
            jEditorPane.setEditable(false);
        }
        return jEditorPane;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(354, 246, 59, 22);
            jButton.setText("OK");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgAbout.this.dispose();
                }
            });
        }
        return jButton;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setBounds(3, 237, 418, 3);
            jPanel1
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jPanel1;
    }

    /**
     * This method initializes jEditorPane1
     * 
     * @return javax.swing.JEditorPane
     */
    private JEditorPane getJEditorPane1() {
        if (epnVesion == null) {
            epnVesion = new JEditorPane();
            epnVesion.setBounds(99, 7, 319, 83);
//            epnVesion
//                    .setText("BESTWAYSOFT?     JBCUS 2005 \n版权所有(C) 百思维软件公司 2003-2005.  保留所有权利");
            epnVesion.setEditable(false);
        }
        return epnVesion;
    }

    /**
     * This method initializes jEditorPane2
     * 
     * @return javax.swing.JEditorPane
     */
    private JEditorPane getJEditorPane2() {
        if (epnAbout == null) {
            epnAbout = new JEditorPane();
            epnAbout.setEditable(false);

        }
        return epnAbout;
    }

    

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setBounds(98, 97, 322, 137);
            jScrollPane.setViewportView(getJEditorPane2());
        }
        return jScrollPane;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10" 
