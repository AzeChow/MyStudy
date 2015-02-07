package com.bestway.ui.winuicontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JIPTextField extends JPanel {

	private IPTextField seg1 = null;
	private IPTextField seg2 = null;
	private IPTextField seg3 = null;
	private IPTextField seg4 = null;

	private JLabel lbSeg1 = null;
	private JLabel lbSeg2 = null;
	private JLabel lbSeg3 = null;

	/** */
	/**
	 * init a IpTextPanel instance
	 * 
	 * @param frame
	 *            parent JFrame
	 */
	public JIPTextField() {
		super();
		seg1 = new IPTextField(3, 1, 255);
		seg2 = new IPTextField(3, 0, 255);
		seg3 = new IPTextField(3, 0, 255);
		seg4 = new IPTextField(3, 1, 255);

		seg1.setHorizontalAlignment(JTextField.CENTER);
		seg2.setHorizontalAlignment(JTextField.CENTER);
		seg3.setHorizontalAlignment(JTextField.CENTER);
		seg4.setHorizontalAlignment(JTextField.CENTER);
		seg1.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				seg1.selectAll();
			}
		});
		seg2.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				seg2.selectAll();
			}
		});
		seg3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				seg3.selectAll();
			}
		});
		seg4.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				seg4.selectAll();
			}
		});
		seg1.addKeyListener(new java.awt.event.KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(seg1.getText().length()>=3){
					seg2.requestFocus();
				}
			}
		});
		seg2.addKeyListener(new java.awt.event.KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(seg2.getText().length()>=3){
					seg3.requestFocus();
				}
			}
		});
		seg3.addKeyListener(new java.awt.event.KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(seg3.getText().length()>=3){
					seg4.requestFocus();
				}
			}
		});
		lbSeg1 = new JLabel(".");
		lbSeg2 = new JLabel(".");
		lbSeg3 = new JLabel(".");

		lbSeg1.setOpaque(true);
		lbSeg2.setOpaque(true);
		lbSeg3.setOpaque(true);

		lbSeg1.setHorizontalAlignment(SwingConstants.CENTER);
		lbSeg1.setFont(new Font("Dialog", Font.BOLD, 15));

		lbSeg2.setHorizontalAlignment(SwingConstants.CENTER);
		lbSeg2.setFont(new Font("Dialog", Font.BOLD, 15));

		lbSeg3.setHorizontalAlignment(SwingConstants.CENTER);
		lbSeg3.setFont(new Font("Dialog", Font.BOLD, 15));

		add(seg1);
		add(lbSeg1);
		add(seg2);
		add(lbSeg2);
		add(seg3);
		add(lbSeg3);
		add(seg4);
		setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		setBackground(seg1.getBackground());
	}

	/** */
	/**
	 * set an ip address,it won''t check the ip is valid or not
	 * 
	 * @param ip
	 *            address type
	 */
	public void setText(String ip) {
		if (ip == null || "".equals(ip.trim())) {
			seg1.setText("");
			seg2.setText("");
			seg3.setText("");
			seg4.setText("");
			return;
		}
		StringTokenizer st = new StringTokenizer(ip, ".");
		seg1.setText(st.nextToken());
		seg2.setText(st.nextToken());
		seg3.setText(st.nextToken());
		seg4.setText(st.nextToken());
		// String[] ipTokens=ip.split(".");
		// for(int i=0;i<ipTokens.length;i++){
		// System.out.println(i+":"+ipTokens[i].trim());
		// }
		// seg1.setText(ipTokens.length>1?ipTokens[0].trim():"");
		// seg2.setText(ipTokens.length>2?ipTokens[1].trim():"");
		// seg3.setText(ipTokens.length>3?ipTokens[2].trim():"");
		// seg4.setText(ipTokens.length>=4?ipTokens[3].trim():"");
	}

	/** */
	/**
	 * clear the ip address
	 */
	public void clear() {
		seg1.setText("");
		seg2.setText("");
		seg3.setText("");
		seg4.setText("");
	}

	/** */
	/**
	 * get the ip address content
	 * 
	 * @return ipaddress String value
	 */
	public String getText() {
		if (seg1.getText().equals("") || seg2.getText().equals("")
				|| seg3.getText().equals("") || seg4.getText().equals("")) {
			// JOptionPane.showMessageDialog(null, "请输入完整", "WARNING",
			// JOptionPane.WARNING_MESSAGE);
			return null;
		} else {
			return Integer.parseInt(seg1.getText().trim()) + "."
					+ Integer.parseInt(seg2.getText().trim()) + "."
					+ Integer.parseInt(seg3.getText().trim()) + "."
					+ Integer.parseInt(seg4.getText().trim());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		// SoftBevelBorder的线宽是1。
		Double borderWidth = 1.0;
		Double perDotWidth = 2.0;
		Double nextX = borderWidth;
		Double width = this.getSize().getWidth();// this.getPreferredSize().getWidth();
		Double height = this.getSize().getHeight();// this.getPreferredSize().getHeight();
		Double perEditHeight = height - borderWidth * 2.0;
		Double perEditWidth = (width - perDotWidth * 3.0 - borderWidth * 2.0) / 4.0;
		seg1.setBounds(new Rectangle(nextX.intValue(), borderWidth.intValue(),
				perEditWidth.intValue(), perEditHeight.intValue()));
		nextX += perEditWidth;
		lbSeg1.setBounds(new Rectangle(nextX.intValue(),
				borderWidth.intValue(), perDotWidth.intValue(), perEditHeight
						.intValue()));
		nextX += perDotWidth;
		seg2.setBounds(new Rectangle(nextX.intValue(), borderWidth.intValue(),
				perEditWidth.intValue(), perEditHeight.intValue()));
		nextX += perEditWidth;
		lbSeg2.setBounds(new Rectangle(nextX.intValue(),
				borderWidth.intValue(), perDotWidth.intValue(), perEditHeight
						.intValue()));
		nextX += perDotWidth;
		seg3.setBounds(new Rectangle(nextX.intValue(), borderWidth.intValue(),
				perEditWidth.intValue(), perEditHeight.intValue()));
		nextX += perEditWidth;
		lbSeg3.setBounds(new Rectangle(nextX.intValue(),
				borderWidth.intValue(), perDotWidth.intValue(), perEditHeight
						.intValue()));
		nextX += perDotWidth;
		seg4.setBounds(new Rectangle(nextX.intValue(), borderWidth.intValue(),
				perEditWidth.intValue(), perEditHeight.intValue()));
	}

	public void setEnabled(boolean enabled) {
		this.seg1.setEnabled(enabled);
		this.seg2.setEnabled(enabled);
		this.seg3.setEnabled(enabled);
		this.seg4.setEnabled(enabled);
		lbSeg1.setBackground(seg1.getBackground());
		lbSeg2.setBackground(seg1.getBackground());
		lbSeg3.setBackground(seg1.getBackground());
		if (enabled) {
			setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		} else {
			this.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
		}
	}

	public void setEditable(boolean enabled) {
		this.seg1.setEditable(enabled);
		this.seg2.setEditable(enabled);
		this.seg3.setEditable(enabled);
		this.seg4.setEditable(enabled);
		lbSeg1.setBackground(seg1.getBackground());
		lbSeg2.setBackground(seg1.getBackground());
		lbSeg3.setBackground(seg1.getBackground());
		if (enabled) {
			setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		} else {
			this.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
		}
	}

	class IPTextField extends JTextField {

		private int min = 0;// min value

		private int max = 255;// max value

		private IpSegDocument document = null;

		public IPTextField(int columns, int min, int max) {
			super(columns);
			this.min = min;
			this.max = max;
			setDocument(document = new IpSegDocument(columns, min, max));
			setBorder(null);
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
			if (document != null) {
				document.setMax(max);
			}
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
			if (document != null) {
				document.setMin(min);
			}
		}
	}

	class IpSegDocument extends PlainDocument {

		private int limit;

		private int min;

		private int max;

		// Component parentComponent;

		public IpSegDocument(int limit, int min, int max) {
			super();
			// this.parentComponent = parentComponent;
			this.limit = limit;
			this.min = min;
			this.max = max;
		}

		/** */
		/**
		 * offset 输入位置 str 输入字符串.有多个输入的情况,如粘贴剪贴板中的内容 attr 总是null
		 */
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (((getText(0, getLength()).trim()).length() + str.length()) <= limit) {

				char[] upper = str.toCharArray();
				int length = 0;
				for (int i = 0; i < upper.length; i++) {
					if (upper[i] >= '0' && upper[i] <= '9') {
						upper[length++] = upper[i];
					}
				}
				String valueStr = getText(0, offset)
						+ new String(upper, 0, length)
						+ getText(offset, getLength() - offset).trim();
				if (!valueStr.equals("")) {
					int value = Integer.parseInt(valueStr.trim());
					if (value >= min && value <= max) {
						super.remove(0, getLength());
						super.insertString(0, String.valueOf(value), attr);
					} else {
						// if (parentComponent == null) {
						// parentComponent = JOptionPane.getRootFrame();
						// }
						// JOptionPane.showMessageDialog(null, "非法输入",
						// "WARNING", JOptionPane.WARNING_MESSAGE);
						super.remove(0, getLength());
					}
				}

			}
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}
	}
}
