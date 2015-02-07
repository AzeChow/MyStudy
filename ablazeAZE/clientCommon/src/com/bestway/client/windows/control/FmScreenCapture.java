package com.bestway.client.windows.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FmScreenCapture extends JFrame {

	private JPanel jPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	public JLabel jLabel = null;

	private BufferedImage pickedImage = null; // @jve:decl-index=0:

	public static FmScreenCapture fmScreenCapture = null; // @jve:decl-index=0:visual-constraint="521,10"

	public static FmScreenCapture getInstance() {
		if (fmScreenCapture == null) {
			fmScreenCapture = new FmScreenCapture();
		}
		return fmScreenCapture;
	}

	/**
	 * This method initializes
	 * 
	 */
	public FmScreenCapture() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			ScreenCapture cap = new ScreenCapture();
			pickedImage = cap.getPickedImage();
			if (cap.isOK) {
				ImageIcon icon = cap.getPickedIcon();
				this.jLabel.setIcon(icon);
				Dimension screenSize = Toolkit.getDefaultToolkit()
						.getScreenSize();
				Dimension frameSize = this.getSize();
				if (frameSize.height > screenSize.height) {
					frameSize.height = screenSize.height;
				}
				if (frameSize.width > screenSize.width) {
					frameSize.width = screenSize.width;
				}
				this.setLocation((screenSize.width - frameSize.width) / 2,
						(screenSize.height - frameSize.height) / 2);
				super.setVisible(b);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(701, 539));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("所抓取图像");
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.add(getJPanel2(), BorderLayout.CENTER);
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
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isSaveOK = false;
					JFileChooser fileChooser = new JFileChooser("./");
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser
							.addChoosableFileFilter(new FileNameExtensionFilter(
									"*.JPEG", "JPEG"));
					fileChooser
							.addChoosableFileFilter(new FileNameExtensionFilter(
									"*.png", "png"));
					String fileName = "";
					int state = fileChooser
							.showSaveDialog(FmScreenCapture.this);
					if (state == JFileChooser.APPROVE_OPTION) {
						File f = fileChooser.getSelectedFile();
						String description = fileChooser.getFileFilter()
								.getDescription();
						String suffix = description.substring(description
								.indexOf("."));
						if (f.getPath().indexOf(".") > 0) {
							fileName = f.getPath();
						} else {
							fileName = f.getPath() + suffix;
						}
					} else {
						return;
					}
					File saveFile = new File(fileName);
					if (saveFile.exists()) {
						if (JOptionPane.showConfirmDialog(FmScreenCapture.this,
								"文件已经存在,是否覆盖原文件?", "警告!",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
							return;
						} else {
							isSaveOK = saveFile(saveFile);
						}
					} else {
						isSaveOK = saveFile(saveFile);
					}

					if (isSaveOK) {
						FmScreenCapture.this.dispose();
						JOptionPane.showMessageDialog(FmScreenCapture.this,
								"文件保存成功!", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(FmScreenCapture.this,
								"文件保存失败!", "提示！", JOptionPane.ERROR);
					}

				}
			});
		}
		return jButton;
	}

	public boolean saveFile(File file) {
		boolean flag = true;
		try {
			ImageIO.write(pickedImage, "JPEG", file);
		} catch (IOException e1) {
			e1.printStackTrace();
			flag = false;
		} finally {
			return flag;
		}
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
					FmScreenCapture.this.dispose();

				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout());
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setText("");
			jLabel.setHorizontalAlignment(JLabel.CENTER);
			jLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel.setVerticalAlignment(JLabel.CENTER);
			jPanel2 = new JPanel();
			jPanel2.setBackground(Color.black);
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(jLabel, BorderLayout.CENTER);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel2;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
