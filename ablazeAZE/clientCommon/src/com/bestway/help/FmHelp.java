package com.bestway.help;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.bestway.client.windows.form.FmMain;

public class FmHelp extends JDialog {

	private static final long serialVersionUID = 1L;

	private static FmHelp fmHelp = null;

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JEditorPane jEditorPane = null;

	private Window owner = null;

	private String fileName = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel = null;

	private WikiToHtml wikiToHtml = new WikiToHtml();  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	private FmHelp(Window owner) {
		super(owner);
		initialize();
		this.owner = owner;
	}

	public static FmHelp getInstance(Window owner, String fileName) {
		if (owner instanceof FmHelp) {
			return fmHelp;
		}
		if (fmHelp != null) {
			fmHelp.dispose();
			fmHelp = null;
		}
		fmHelp = new FmHelp(owner);
		fmHelp.setModal(false);
		fmHelp.fileName = fileName;
		return fmHelp;
	}

	public void setVisible(boolean b) {
		if (b) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = owner.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			if (owner instanceof JDialog) {
				owner
						.setLocation(0,
								(screenSize.height - frameSize.height) / 2);
				this.setLocation(owner.getSize().width, 0);
				this.setSize(new Dimension(screenSize.width
						- owner.getSize().width, new Double(
								FmMain.getInstance().getSize().getHeight()).intValue()));
			} else {
				this.setSize(new Dimension(screenSize.width / 3, new Double(
						FmMain.getInstance().getSize().getHeight()).intValue()));//screenSize.height * 0.92
				this.setLocation(screenSize.width - this.getSize().width, 0);
			}
			String url = "/" + fileName.replace(".", "/") + ".txt";
			String title = "";
			if (owner instanceof JDialog) {
				title = ((JDialog) owner).getTitle();
			} else if (owner instanceof JFrame) {
				title = ((JFrame) owner).getTitle();
			}
			URL resource = this.getClass().getResource(url);
			if (resource != null) {
//				System.out.println(wikiToHtml.convert(resource));
				this.jEditorPane.setText(wikiToHtml.convert(resource));
			} else {
				url = "/" + fileName.replace(".", "/") + ".html";
				resource = this.getClass().getResource(url);
				if (resource != null) {
					try {
						this.jEditorPane.setPage(resource);
					} catch (IOException e) {
						this.jEditorPane.setText("不存在" + title + "的帮助文件");
					}
				}
				this.jEditorPane.setText("不存在" + title + "的帮助文件");
			}
			// InputStream fs=null;
			// try {
			// fs = resource.openStream();
			// } catch (Exception e) {
			// }
			// if (fs == null) {
			// this.jEditorPane.setText("不存在" + title + "的帮助文件");
			// } else {
			// StringBuffer buffer = new StringBuffer();
			// readToBuffer(buffer, fs);
			// try {
			// fs.close();
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// throw new RuntimeException(e1.getMessage());
			// }
			// this.jEditorPane.setText(buffer.toString());
			// }
		}
		super.setVisible(b);
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
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(507, 437);
		this.setContentPane(getJContentPane());
		this.setTitle("帮助");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (!(owner.getClass().getName().indexOf("FmMain") >= 0)) {
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					Dimension frameSize = owner.getSize();
					if (frameSize.height > screenSize.height) {
						frameSize.height = screenSize.height;
					}
					if (frameSize.width > screenSize.width) {
						frameSize.width = screenSize.width;
					}
					owner.setLocation((screenSize.width - frameSize.width) / 2,
							(screenSize.height - frameSize.height) / 2);
				}
			}
		});
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
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
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setContentType("text/html");
			jEditorPane.setEditable(false);
		}
		return jEditorPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("");
			jLabel.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/client/resource/images/help_large.gif")));
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}	
}
