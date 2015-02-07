package com.bestway.bcus.client.common;

import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.swing.JScrollBar;
import java.awt.Rectangle;
import javax.swing.JScrollPane;

public class Formulasearch extends JDialogBase 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextPaneBases jTextPane = null;
	private JScrollPane jScrollPane = null;
	/**
	 * This method initializes 
	 * 
	 */
	public Formulasearch() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(505, 503));
        this.setContentPane(getJContentPane());
        this.setTitle("公式查询");
			
	}

	class JTextPaneBases extends JTextPane implements MouseListener
	{
			private static final long serialVersionUID = -2308615404205560110L;

			private JPopupMenu pop = null; // 弹出菜单

			private JMenuItem copy=null, paste=null, cut=null; // 三个功能菜单

			public JTextPaneBases()
			{
					super();
					init();
			}

			private void init() 
			{
					this.addMouseListener(this);
					pop = new JPopupMenu();
					pop.add(copy = new JMenuItem("复制"));
					pop.add(paste = new JMenuItem("粘贴"));
					pop.add(cut = new JMenuItem("剪切"));
					copy.setAccelerator(KeyStroke.getKeyStroke('C',InputEvent.CTRL_MASK));
					paste.setAccelerator(KeyStroke.getKeyStroke('V',InputEvent.CTRL_MASK));
					cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
					copy.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							action(e);
						}
					});
					paste.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e)
						{
							action(e);
						}
					});
					cut.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e)
						{
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
			
			public void readToBuffer(StringBuffer buffer, InputStream is)
			{
					String line = ""; // 用来保存每行读取的内容
					BufferedReader reader = null;
					try 
					{
						reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					} 
					catch(UnsupportedEncodingException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new RuntimeException(e1.getMessage());
					}
					try
					{
						line=reader.readLine();
					} 
					catch(IOException e)
					{
							e.printStackTrace();
							throw new RuntimeException(e.getMessage());
					} 		// 读取第一行
					while(line !=null)
					{ 
							// 如果 line 为空说明读完了
							buffer.append(line); // 将读到的内容添加到 buffer 中
							buffer.append("\n"); // 添加换行符
							try 
							{
								line = reader.readLine();
							} 
							catch(IOException e) 
							{
								e.printStackTrace();
								throw new RuntimeException(e.getMessage());
							} // 读取下一行
					}
			}
			
			private void readFile(String filename)
			{
					InputStream fs = getClass().getResourceAsStream(filename);
					StringBuffer buffer = new StringBuffer();
					readToBuffer(buffer, fs);
					System.out.println(buffer); // 将读到 buffer 中的内容写出来
					try 
					{
						fs.close();
					} 
					catch(IOException e1) 
					{
						e1.printStackTrace();
						throw new RuntimeException(e1.getMessage());
					}
					getJTextPane().setText(buffer.toString());
			}	
		public void action(ActionEvent e) 
		{
				String str = e.getActionCommand();
				if(str.equals(copy.getText()))
				{ // 复制
					this.copy();
				} 
				else if(str.equals(paste.getText())) 
				{ // 粘贴
					this.paste();
				} 
				else if (str.equals(cut.getText())) 
				{ // 剪切
					this.cut();
				}
		}

		public JPopupMenu getPop() 
		{
				return pop;
		}

		public void setPop(JPopupMenu pop)
		{
				this.pop = pop;
		}

		/**
		 * 剪切板中是否有文本数据可供粘贴
		 * 
		 * @return true为有文本数据
		 */
		public boolean isClipboardString() 
		{
				boolean b = false;
				Clipboard clipboard = this.getToolkit().getSystemClipboard();
				Transferable content = clipboard.getContents(this);
				try 
				{
					if(content.getTransferData(DataFlavor.stringFlavor) instanceof String) 
					{
						b = true;
					}
				}
				catch(Exception e) 
				{
				}
				return b;
		}

		/**
		 * 文本组件中是否具备复制的条件
		 * 
		 * @return true为具备
		 */
		public boolean isCanCopy()
		{
				boolean b = false;
				int start = this.getSelectionStart();
				int end = this.getSelectionEnd();
				if(start != end)	
				b = true;
				return b;
		}

		public void mouseClicked(MouseEvent e)
		{
		}

		public void mouseEntered(MouseEvent e) 
		{
		}

		public void mouseExited(MouseEvent e) 
		{
		}

		public void mousePressed(MouseEvent e) 
		{
				if(e.getButton()==MouseEvent.BUTTON3) 
				{
					copy.setEnabled(isCanCopy());
					paste.setEnabled(isClipboardString());
					cut.setEnabled(isCanCopy());
					pop.show(this, e.getX(), e.getY());
				}
		}
		public void mouseReleased(MouseEvent e) 
		{
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	
	private JScrollPane getJScrollPane()
	{
			if(jScrollPane==null)
			{
				jScrollPane=new JScrollPane();
				jScrollPane.setViewportView(getJTextPane());
			}
			return jScrollPane;
	}
	
	private JTextPaneBases getJTextPane() 
	{
		if(jTextPane==null) 
		{
			jTextPane=new JTextPaneBases();
			jTextPane.setEditable(false);
			jTextPane.readFile("Formulas.txt");
			//jTextPane.setLineWrap(true);
			//jTextPane.setWrapStyleWord(true);
		}
		return jTextPane;
	}
	
	private JPanel getJContentPane() 
	{
			if(jContentPane==null) 
			{
				jContentPane = new JPanel();
				jContentPane.setLayout(new BorderLayout());
				jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
				//jContentPane.add(getJPanel(), BorderLayout.SOUTH);
				//jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			}
			return jContentPane;
	}
}  //  @jve:decl-index=0:visual-constraint="7,-39"
