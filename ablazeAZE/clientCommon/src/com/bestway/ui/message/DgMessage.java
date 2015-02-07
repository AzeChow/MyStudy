package com.bestway.ui.message;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

public class DgMessage extends JDialogBase{

	private Panel panel = null;
	private Panel panel1 = null;
	private Panel panel2 = null;
	private JButton btnOk = null;
	private JScrollPane jScrollPane = null;
	private JTextPaneBase jTextPane = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgMessage() {
		super();
		initialize();
		this.setVisible(true);
	}
	
	public DgMessage(String title,String str) {
		super();
		initialize();
		this.setTitle(title);
		this.jTextPane.setText(str);
		this.setVisible(true);
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(514, 344));
        this.setContentPane(getPanel());
			
	}

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel() {
		if (panel == null) {
			panel = new Panel();
			panel.setLayout(new BorderLayout());
			panel.add(getPanel1(), BorderLayout.CENTER);
			panel.add(getPanel2(), BorderLayout.SOUTH);
		}
		return panel;
	}

	class JTextPaneBase extends JTextPane implements MouseListener {
		private static final long serialVersionUID = -2308615404205560110L;

		private JPopupMenu pop = null; // 弹出菜单

		private JMenuItem copy = null, paste = null, cut = null; // 三个功能菜单

		public JTextPaneBase() {
			super();
			init();
		}

		private void init() {
			this.addMouseListener(this);
			pop = new JPopupMenu();
			pop.add(copy = new JMenuItem("复制"));
			pop.add(paste = new JMenuItem("粘贴"));
			pop.add(cut = new JMenuItem("剪切"));
			copy.setAccelerator(KeyStroke.getKeyStroke('C',
					InputEvent.CTRL_MASK));
			paste.setAccelerator(KeyStroke.getKeyStroke('V',
					InputEvent.CTRL_MASK));
			cut.setAccelerator(KeyStroke
					.getKeyStroke('X', InputEvent.CTRL_MASK));
			copy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action(e);
				}
			});
			paste.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action(e);
				}
			});
			cut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					action(e);
				}
			});
			this.add(pop);
		}

		/**
		 * 菜单动作
		 * 
		 * @param e
		 */
		public void action(ActionEvent e) {
			String str = e.getActionCommand();
			if (str.equals(copy.getText())) { // 复制
				this.copy();
			} else if (str.equals(paste.getText())) { // 粘贴
				this.paste();
			} else if (str.equals(cut.getText())) { // 剪切
				this.cut();
			}
		}

		public JPopupMenu getPop() {
			return pop;
		}

		public void setPop(JPopupMenu pop) {
			this.pop = pop;
		}

		/**
		 * 剪切板中是否有文本数据可供粘贴
		 * 
		 * @return true为有文本数据
		 */
		public boolean isClipboardString() {
			boolean b = false;
			Clipboard clipboard = this.getToolkit().getSystemClipboard();
			Transferable content = clipboard.getContents(this);
			try {
				if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
					b = true;
				}
			} catch (Exception e) {
			}
			return b;
		}

		/**
		 * 文本组件中是否具备复制的条件
		 * 
		 * @return true为具备
		 */
		public boolean isCanCopy() {
			boolean b = false;
			int start = this.getSelectionStart();
			int end = this.getSelectionEnd();
			if (start != end)
				b = true;
			return b;
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				copy.setEnabled(isCanCopy());
				paste.setEnabled(isClipboardString());
				cut.setEnabled(isCanCopy());
				pop.show(this, e.getX(), e.getY());
			}
		}

		public void mouseReleased(MouseEvent e) {
		}
	}
	
	/**
	 * This method initializes panel1	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel1() {
		if (panel1 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			panel1 = new Panel();
			panel1.setLayout(gridLayout);
			panel1.add(getJScrollPane(), null);
		}
		return panel1;
	}

	/**
	 * This method initializes panel2	
	 * 	
	 * @return java.awt.Panel	
	 */
	private Panel getPanel2() {
		if (panel2 == null) {
			panel2 = new Panel();
			panel2.setLayout(new FlowLayout());
			panel2.setPreferredSize(new Dimension(10, 50));
			panel2.add(getBtnOk(), null);
		}
		return panel2;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});			
		}
		return btnOk;
	}
	
	public void showMessage(String message){
		jTextPane.setText("");	
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPaneBase getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPaneBase();
		}
		return jTextPane;
	}

}  //  @jve:decl-index=0:visual-constraint="157,70"
