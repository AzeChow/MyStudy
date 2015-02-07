package com.bestway.ui.winuicontrol;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import com.bestway.client.windows.form.FmMain;

public class JNaviButton extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lbPre = null;

	private JLabel lbClose = null;

	private JLabel lbCaption = null;

	private boolean isSelect = false;

	private JInternalFrame jf = null;

	final static BasicStroke stroke = new BasicStroke(1.0f); // @jve:decl-index=0:

	final static BasicStroke wideStroke = new BasicStroke(2.0f);

	final static float dash1[] = { 10.0f };

	final static BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	private JPopupMenu pmCloseForm = null; // @jve:decl-index=0:visual-constraint="269,94"

	private JMenuItem miCloseCurrentForm = null;

	private JMenuItem miCloseOtherForm = null;

	private JMenuItem miCloseAllForm = null;

	private Icon enabledIcon = null;

	private Icon disabledIcon = null;

	/**
	 * This is the default constructor
	 */
	public JNaviButton(JInternalFrame jf) {
		super();
		this.jf = jf;
		initialize();
		this.lbCaption.setText(jf.getTitle() + "  ");// " " +
		this.enabledIcon = jf.getFrameIcon();
		this.lbCaption.setIcon(this.enabledIcon);
		this.lbClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setPreferredSize(new Dimension(this.lbPre.getPreferredSize().width
				+ this.lbCaption.getPreferredSize().width
				+ this.lbClose.getPreferredSize().width, 22));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		lbPre = new JLabel();
		lbPre.setText("  ");
		lbCaption = new JLabel();
		lbCaption.setText("JLabel");
		lbCaption.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				FmMain.getInstance().reopenForm(jf);
				if (e.getModifiers() == java.awt.event.MouseEvent.META_MASK) {// 表示鼠标右键
					getPmCloseForm().show(JNaviButton.this, e.getX(), e.getY());
				} else {
					if (e.getClickCount() == 2) {
						FmMain.getInstance().reshowTreeNavi();
					}
				}
			}
		});
		lbClose = new JLabel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
			}
		};
		lbClose.setText("  ");
		lbClose.setIcon(FmMain.getInstance().getIconByName(
				"/com/bestway/client/resource/images/close2.gif"));
		lbClose.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		lbClose.setFont(new Font("Dialog", Font.BOLD, 12));
		lbClose.setToolTipText("关闭当前窗口\n: " + this.jf.getTitle());
		lbClose.setForeground(new Color(80, 80, 80));
		lbClose.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				lbClose.setForeground(new Color(80, 80, 80));
				lbClose.repaint();
			}

			public void mouseEntered(java.awt.event.MouseEvent e) {
				lbClose.setForeground(Color.RED);
				lbClose.repaint();
			}

			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (jf != null) {
					jf.doDefaultCloseAction();
				}
			}
		});
		this.setLayout(new BorderLayout());
		this.add(lbPre, BorderLayout.WEST);
		this.add(lbCaption, BorderLayout.CENTER);
		this.add(lbClose, BorderLayout.EAST);
		this.setSelect(false);
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				FmMain.getInstance().reopenForm(jf);
				if (e.getClickCount() == 2) {
					FmMain.getInstance().reshowTreeNavi();
				}
			}
		});
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
		this.repaint();
	}

	public JInternalFrame getJf() {
		return jf;
	}

	private GeneralPath getFramePolygon() {
		GeneralPath framePolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 6);
		framePolygon.moveTo(0.0, this.getPreferredSize().height);
		framePolygon.lineTo(0.0, this.getPreferredSize().height / 2.0);
		framePolygon.quadTo(this.getPreferredSize().height / 4.0 - 6.0, this
				.getPreferredSize().height / 4.0 - 6.0,
				this.getPreferredSize().height / 2.0, 0);
		framePolygon.lineTo(this.getPreferredSize().width
				- this.getPreferredSize().height / 2.0, 0.0);
		framePolygon.quadTo(this.getPreferredSize().width
				- this.getPreferredSize().height / 4.0 + 6.0, this
				.getPreferredSize().height / 4.0 - 6.0,
				this.getPreferredSize().width - 1.0,
				this.getPreferredSize().height / 2.0);
		framePolygon.lineTo(this.getPreferredSize().width - 1.0, this
				.getPreferredSize().height);
		framePolygon.lineTo(0.0, this.getPreferredSize().height);
		return framePolygon;
	}

	private GeneralPath getFilledPolygon() {
		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				6);
		filledPolygon.moveTo(1.0, this.getPreferredSize().height);
		filledPolygon.lineTo(1.0, this.getPreferredSize().height / 2.0);
		filledPolygon.quadTo(this.getPreferredSize().height / 4.0 - 5.0, this
				.getPreferredSize().height / 4.0 - 5.0,
				this.getPreferredSize().height / 2.0, 1.0);
		filledPolygon.lineTo(this.getPreferredSize().width
				- this.getPreferredSize().height / 2.0 - 1.0, 1.0);
		filledPolygon.quadTo(this.getPreferredSize().width
				- this.getPreferredSize().height / 4.0 + 5.0, this
				.getPreferredSize().height / 4.0 - 5.0,
				this.getPreferredSize().width - 1.0,
				this.getPreferredSize().height / 2.0);
		filledPolygon.lineTo(this.getPreferredSize().width - 1.0, this
				.getPreferredSize().height);
		filledPolygon.lineTo(0.0, this.getPreferredSize().height);
		return filledPolygon;
	}

	// This method returns a buffered image with the contents of an image
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image
					.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image
					.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}

	// This method returns an Image object from a buffered image
	public static Image toImage(BufferedImage bufferedImage) {
		return Toolkit.getDefaultToolkit().createImage(
				bufferedImage.getSource());
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			if (isSelect) {
				this.lbClose.setIcon(FmMain.getInstance().getIconByName(
						"/com/bestway/client/resource/images/close2.gif"));
				if (this.enabledIcon == null) {
					this.enabledIcon = this.jf.getFrameIcon();
				}
				this.lbCaption.setIcon(this.enabledIcon);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				// GradientPaint redtowhite = new GradientPaint(0, 0,
				// (Color) UIManager.getLookAndFeel().getDefaults().get(
				// "TextPane.selectionBackground"), this
				// .getPreferredSize().width, 0, (Color)
				// UIManager.getLookAndFeel().getDefaults().get(
				// "TextPane.selectionBackground"));
				GradientPaint redtowhite = new GradientPaint(0, this
						.getPreferredSize().height, (Color) UIManager
						.getLookAndFeel().getDefaults().get(
								"TextPane.selectionBackground"), 0, 0,
						Color.WHITE);
				// GradientPaint redtowhite = new GradientPaint(0, 0,
				// Color.WHITE, this
				// .getPreferredSize().width, 0,
				// Color.WHITE);//Color.WHITEColor.WHITE
				g2.setStroke(stroke);
				g2.setPaint((Color) UIManager.getLookAndFeel().getDefaults()
						.get("TextPane.selectionBackground"));
				// g2.setPaint(new Color(204, 255, 255));

				g2.draw(getFramePolygon());
				g2.setPaint(redtowhite);
				g2.fill(getFilledPolygon());

			} else {
				this.lbClose
						.setIcon(FmMain
								.getInstance()
								.getIconByName(
										"/com/bestway/client/resource/images/closeDisable.gif"));
				if (this.disabledIcon == null) {
					BufferedImage bimg = toBufferedImage(((ImageIcon) this.jf
							.getFrameIcon()).getImage());

					// 开始灰度图处理

					ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);

					ColorConvertOp op = new ColorConvertOp(cs, null);

					bimg = op.filter(bimg, null);
					this.disabledIcon = new ImageIcon(toImage(bimg));
				}
				//
				this.lbCaption.setIcon(this.disabledIcon);
				this.setBackground((Color) UIManager.getLookAndFeel()
						.getDefaults().get("Panel.background"));
				// this.setBackground(Color.WHITE);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(stroke);
				// g2.setPaint((Color) UIManager.getLookAndFeel().getDefaults()
				// .get("TextPane.selectionBackground"));
				g2.setPaint(Color.GRAY);
				g2.draw(getFramePolygon());
				if (this.lbCaption.getText().indexOf("向导") >= 0) {
					// this.lbCaption.setForeground(Color.BLUE);// new Color(51,
					// 102, 0)
				} else {
					this.lbCaption.setForeground((Color) UIManager
							.getLookAndFeel().getDefaults().get(
									"TextPane.foreground"));
				}
			}
		} catch (Exception e) {

		}
	}

	public boolean isSelect() {
		return isSelect;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmCloseForm() {
		if (pmCloseForm == null) {
			pmCloseForm = new JPopupMenu();
			pmCloseForm.add(getMiCloseCurrentForm());
			pmCloseForm.add(getMiCloseOtherForm());
			pmCloseForm.add(getMiCloseAllForm());
		}
		return pmCloseForm;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCloseCurrentForm() {
		if (miCloseCurrentForm == null) {
			miCloseCurrentForm = new JMenuItem();
			miCloseCurrentForm.setText("关闭当前窗口");
			miCloseCurrentForm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jf != null) {
								jf.doDefaultCloseAction();
							}
						}
					});
		}
		return miCloseCurrentForm;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCloseOtherForm() {
		if (miCloseOtherForm == null) {
			miCloseOtherForm = new JMenuItem();
			miCloseOtherForm.setText("关闭其他窗口");
			miCloseOtherForm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmMain.getInstance()
									.closeAllChildFormExceptThis(jf);
						}
					});
		}
		return miCloseOtherForm;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCloseAllForm() {
		if (miCloseAllForm == null) {
			miCloseAllForm = new JMenuItem();
			miCloseAllForm.setText("关闭所有窗口");
			miCloseAllForm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmMain.getInstance().closeAllChildForm();
						}
					});
		}
		return miCloseAllForm;
	}
}
