package com.bestway.docs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader; //import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane; //import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgExplain extends JDialogBase {
	private static final long serialVersionUID = 1L;
	String[] s = { "作者", "程序日志", "版权", "更新信息", "功能介绍", "计划实现", "版本信息" };
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	private JScrollPane jScrollPane1 = null;
	private JTextPaneBase jTextPane = null;

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

	public DgExplain() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(918, 514));
		this.setTitle("程序日志");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(100);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane1());
			jSplitPane.setDividerSize(5);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	public void readToBuffer(StringBuffer buffer, InputStream is) {
		String line = ""; // 用来保存每行读取的内容
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "GBK"));// UTF-8
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} // 读取第一行
		while (line != null) {
			// 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} // 读取下一行
		}
	}

	// hwy 2012-10-16
	public void readToBuffer1(StringBuffer buffer, InputStream is) {
		String line = ""; // 用来保存每行读取的内容
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));// UTF-8
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} // 读取第一行
		while (line != null) {
			// 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} // 读取下一行
		}
	}

	private void inserIcon(String str)// 在TextPane里插入图片的方法
	{
		URL url = getClass().getResource(str); // 当前编译后class文件所在目录查找
		ImageIcon icon = new ImageIcon(url);
		getJTextPane().insertIcon(icon);
	}

	// hwy 2012-10-16
	private void readFile(String filename) {
		InputStream fs = getClass().getResourceAsStream(filename);
		StringBuffer buffer = new StringBuffer();
		readToBuffer1(buffer, fs);
		System.out.println(buffer); // 将读到 buffer 中的内容写出来
		try {
			fs.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		getJTextPane().setText(buffer.toString());
	}

	private LogInfoAdapter logInfoAdapter = null;

	public void setLogInfoAdapter(LogInfoAdapter logInfoAdapter) {
		this.logInfoAdapter = logInfoAdapter;
	}

	private String getLogInfo() {
		if (this.logInfoAdapter != null) {
			return this.logInfoAdapter.getLogInfo();
		} else {
			InputStream fs = getClass().getResourceAsStream("ChangLog.txt");
			StringBuffer buffer = new StringBuffer();
			readToBuffer1(buffer, fs);
			return buffer.toString();
		}
	}

	public void showLog() {
		getJTextPane().setText(getLogInfo());
	}

	private JList getJList() {
		if (jList == null) {
			jList = new JList(s);
			jList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					int index = jList.getSelectedIndex();
					/*
					 * getSelectedIndex():
					 * 返回最小的选择单元索引；只选择了列表中单个项时，返回该选择。选择了多项时，则只返回最小的选择索引。
					 * 如果什么也没有选择，则返回 -1。 此方法是委托给 getMinSelectionIndex 的覆盖方法。
					 */
					switch (index) {
					case 0:
						readFile("AUTHORS.txt");
						break;
					case 1:
						showLog();
						break;
					case 2:
						readFile("COPYRIGHT.txt");
						break;
					case 3:
						readFile("NEWS.txt");
						break;
					case 4:
						readFile("README.txt");
						break;
					case 5:
						readFile("TODO.txt");
						break;
					case 6:
						readFile("VERSION.txt");
						break;
					default:
						getJTextPane().setText("");
					}
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextPane());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextPaneBase getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPaneBase();
			jTextPane.setEditable(false);
			// jTextPane.setLineWrap(true);
			// jTextPane.setWrapStyleWord(true);
		}
		return jTextPane;
	}

	/*
	 * public void valueChanged(ListSelectionEvent e) { // TODO Auto-generated
	 * method stub int tmp=0; String stmp="您目前选取"; int[] index =
	 * jList.getSelectedIndices
	 * ();//利用JList类所提供的getSelectedIndices()方法可得到用户所选取的所有 for(int
	 * i=0;i<index.length;i++)//index值，这些index值由一个int array返回. { tmp = index[i];
	 * stmp = stmp+s[tmp]+" "; } getJTextArea().setText(stmp); }
	 */

} // @jve:decl-index=0:visual-constraint="50,18"
// @jve:decl-index=0:visual-constraint="16,8"
