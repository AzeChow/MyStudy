package com.bestway.ui.winuicontrol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.form.FmMain;

public class JNaviBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<JNaviButton> list = new ArrayList<JNaviButton>(); // @jve:decl-index=0:

	private List<JNaviMenuItem> lsOther = new ArrayList<JNaviMenuItem>(); // @jve:decl-index=0:

	// private int allowWidth = 0;

	private boolean isOverFlow = false;

	private JPopupMenu jPopupMenu = null;

	/**
	 * This is the default constructor
	 */
	public JNaviBar() {
		super();
		initialize();
		this.setBorder(null);
		// this.setBackground(Color.WHITE);
		// this.setBackground((Color)
		// UIManager.getLookAndFeel().getDefaults().get(
		// "TextPane.selectionBackground"));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		FlowLayout flowLayout = new NaviFlowLayout();

		flowLayout.setHgap(0);

		flowLayout.setAlignment(FlowLayout.LEFT);

		flowLayout.setVgap(3);

		setLayout(flowLayout);

		setSize(342, 93);
		// this.setBackground(new Color(218, 234, 241));
		// this.setBackground((Color) UIManager.getLookAndFeel().getDefaults()
		// .get("TextPane.selectionBackground"));
	}

	class NaviFlowLayout extends FlowLayout {

		private static final long serialVersionUID = 1L;

		@Override
		public void layoutContainer(Container target) {

			synchronized (target.getTreeLock()) {
				Insets insets = target.getInsets();
				int maxwidth = target.getWidth()
						- (insets.left + insets.right + this.getHgap() * 2);
				int nmembers = target.getComponentCount();
				int x = 0, y = insets.top;// + vgap
				int rowh = 0, start = 0;

				boolean ltr = target.getComponentOrientation().isLeftToRight();

				boolean useBaseline = getAlignOnBaseline();
				int[] ascent = null;
				int[] descent = null;

				if (useBaseline) {
					ascent = new int[nmembers];
					descent = new int[nmembers];
				}

				for (int i = 0; i < nmembers; i++) {
					Component m = target.getComponent(i);
					if (m.isVisible()) {
						Dimension d = m.getPreferredSize();
						m.setSize(d.width, d.height);

						if (useBaseline) {
							int baseline = m.getBaseline(d.width, d.height);
							if (baseline >= 0) {
								ascent[i] = baseline;
								descent[i] = d.height - baseline;
							} else {
								ascent[i] = -1;
							}
						}
						if ((x == 0) || ((x + d.width) <= maxwidth)) {
							if (x > 0) {
								x += this.getHgap();
							}
							x += d.width;
							rowh = Math.max(rowh, d.height);
						} else {
							rowh = this
									.moveComponents(target,
											insets.left + this.getHgap(), y,
											maxwidth - x, rowh, start, i, ltr,
											useBaseline, ascent, descent);
							x = d.width;
							y += rowh;// vgap +
							rowh = d.height;
							start = i;
						}
					}
				}
				moveComponents(target, insets.left + this.getHgap(), y,
						maxwidth - x, rowh, start, nmembers, ltr, useBaseline,
						ascent, descent);
			}
		}

		private int moveComponents(Container target, int x, int y, int width,
				int height, int rowStart, int rowEnd, boolean ltr,
				boolean useBaseline, int[] ascent, int[] descent) {
			switch (this.getAlignment()) {
			case LEFT:
				x += ltr ? 0 : width;
				break;
			case CENTER:
				x += width / 2;
				break;
			case RIGHT:
				x += ltr ? width : 0;
				break;
			case LEADING:
				break;
			case TRAILING:
				x += width;
				break;
			}
			int maxAscent = 0;
			int nonbaselineHeight = 0;
			int baselineOffset = 0;
			if (useBaseline) {
				int maxDescent = 0;
				for (int i = rowStart; i < rowEnd; i++) {
					Component m = target.getComponent(i);
					if (m.isVisible()) {
						if (ascent[i] >= 0) {
							maxAscent = Math.max(maxAscent, ascent[i]);
							maxDescent = Math.max(maxDescent, descent[i]);
						} else {
							nonbaselineHeight = Math.max(m.getHeight(),
									nonbaselineHeight);
						}
					}
				}
				height = Math.max(maxAscent + maxDescent, nonbaselineHeight);
				baselineOffset = (height - maxAscent - maxDescent) / 2;
			}
			for (int i = rowStart; i < rowEnd; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					int cy;
					if (useBaseline && ascent[i] >= 0) {
						cy = y + baselineOffset + maxAscent - ascent[i];
					} else {
						cy = y + (height - m.getHeight()) / 2;
					}
					if (m instanceof JNaviButton) {
						if (!((JNaviButton) m).isSelect()) {
							cy = cy + 2;
						}
					}
					if (ltr) {
						m.setLocation(x, cy);
					} else {
						m.setLocation(target.getWidth() - x - m.getWidth(), cy);
					}
					x += m.getWidth() + this.getHgap();
				}
			}
			return height;
		}

	}

	public void addInternalFrame(JInternalFrame jf) {
		JNaviButton button = new JNaviButton(jf);
		int diffTotalWidth = getActiveFormButtonWidth()// this.getPreferredSize().width
				+ button.getPreferredSize().width - this.getAllowWidth();
		int count = getHideButtonCount(diffTotalWidth);
		if (count > 0) {
			if (!isOverFlow) {
				isOverFlow = true;
			}
			int delCount = 0, delIndex = 0;
			for (int i = 0; i < list.size(); i++) {
				if (delCount == count) {
					break;
				}
				JNaviButton delButton = list.get(delIndex);
				if (delButton.isSelect()) {
					delIndex++;
					continue;
				}
				delCount++;
				list.remove(delIndex);
				this.remove(delButton);
				final JNaviMenuItem menuItem = new JNaviMenuItem(
						delButton.getJf());
				menuItem.setIcon(delButton.getJf().getFrameIcon());
				menuItem.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						FmMain.getInstance().reopenForm(menuItem.getJf());
						FmMain.getInstance().refreshNaviBar();
					}
				});
				this.getJPopupMenu().add(menuItem);
				lsOther.add(menuItem);
			}
		}
		list.add(button);
		this.add(button);
		button.setSelect(true);
		this.validate();
		this.repaint();
		// System.out.println("------------size:"
		// + this.getPreferredSize().getWidth() + "----allowWidth:"
		// + this.getAllowWidth());
	}

	/**
	 * 激活内部的框
	 * 
	 * @param jf
	 */
	public void activeInternalFrame(JInternalFrame jf) {

		for (int i = 0; i < list.size(); i++) {

			// 导航按钮
			JNaviButton button = list.get(i);

			if (((JInternalFrameBase) button.getJf()).getFlowPaneClassName() != null ? ((JInternalFrameBase) button
					.getJf()).getFlowPaneClassName().equals(
					((JInternalFrameBase) jf).getFlowPaneClassName()) : button
					.getJf().getClass().getName()
					.equals(jf.getClass().getName())) {

				button.setSelect(true);

			} else {

				button.setSelect(false);

			}
		}
		for (int i = lsOther.size() - 1; i >= 0; i--) {
			JNaviMenuItem menuItem = lsOther.get(i);
			if (((JInternalFrameBase) menuItem.getJf()).getFlowPaneClassName() != null ? ((JInternalFrameBase) menuItem
					.getJf()).getFlowPaneClassName().equals(
					((JInternalFrameBase) jf).getFlowPaneClassName())
					: menuItem.getJf().getClass().getName()
							.equals(jf.getClass().getName())) {
				lsOther.remove(i);
				this.getJPopupMenu().remove(menuItem);
				addInternalFrame(menuItem.getJf());
				break;
			}
		}
		this.validate();
		this.repaint();
	}

	private int getHideButtonCount(int diffSize) {
		int totalWidth = 0;
		if (diffSize <= 0) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			JNaviButton button = list.get(i);
			if (button.isSelect()) {
				continue;
			}
			totalWidth += button.getPreferredSize().width;
			count++;
			if (totalWidth > diffSize) {
				// if (i + 1 == list.size()) {
				// return i;
				// } else {
				// return i + 1;
				// }
				return count;
			}
		}
		return 0;
	}

	/**
	 * 关闭内部框
	 * 
	 * @param jf
	 */
	public void removeInternalFrame(JInternalFrame jf) {
		for (int i = list.size() - 1; i >= 0; i--) {
			JNaviButton button = list.get(i);
			if (((JInternalFrameBase) button.getJf()).getFlowPaneClassName() != null ? ((JInternalFrameBase) button
					.getJf()).getFlowPaneClassName().equals(
					((JInternalFrameBase) jf).getFlowPaneClassName()) : button
					.getJf().getClass().getName()
					.equals(jf.getClass().getName())) {

				list.remove(i);

				remove(button);

				int diffTotalWidth = getAllowWidth()
						- getActiveFormButtonWidth();// this.getPreferredSize().width;

				if (diffTotalWidth > 0) {
					for (int j = lsOther.size() - 1; j >= 0; j--) {
						JNaviMenuItem menuItem = (JNaviMenuItem) lsOther.get(j);
						JNaviButton newButton = new JNaviButton(
								menuItem.getJf());
						if (diffTotalWidth > newButton.getPreferredSize().width) {
							list.add(newButton);
							this.add(newButton);
							button.setSelect(true);
							lsOther.remove(j);
							this.getJPopupMenu().remove(menuItem);
							diffTotalWidth = diffTotalWidth
									- newButton.getPreferredSize().width;
						} else {
							break;
						}
					}
				}
				break;
			}
		}
		for (int i = lsOther.size() - 1; i >= 0; i--) {
			JNaviMenuItem menuItem = lsOther.get(i);
			if (((JInternalFrameBase) menuItem.getJf()).getFlowPaneClassName() != null ? ((JInternalFrameBase) menuItem
					.getJf()).getFlowPaneClassName().equals(
					((JInternalFrameBase) jf).getFlowPaneClassName())
					: menuItem.getJf().getClass().getName()
							.equals(jf.getClass().getName())) {
				lsOther.remove(i);
				this.getJPopupMenu().remove(menuItem);
				break;
			}
		}
		// if(this.getSelectedInternalFrame()==null&&list.size()>0){
		// JNaviButton button = list.get(list.size()-1);
		// button.setSelect(true);
		// }
		if (lsOther.size() <= 0) {
			isOverFlow = false;
			// System.out.println("remove BtnOther BtnOther");
		}
		if (list.size() <= 0) {
			FmMain.getInstance().setTitle(CommonVariables.mainTitle);
		}
		this.validate();
		this.repaint();
		// System.out.println("333333333333333333333333");
	}

	public int getAllowWidth() {
		return FmMain.getInstance().getNaviBarAllowWidth();
	}

	public void adjustNaviBar() {

		int diffTotalWidth = getActiveFormButtonWidth() - getAllowWidth();

		if (diffTotalWidth > 0) {
			int count = getHideButtonCount(diffTotalWidth);
			if (count > 0) {
				if (!isOverFlow) {
					isOverFlow = true;
				}
				int delCount = 0, delIndex = 0;
				for (int i = 0; i < list.size(); i++) {
					if (delCount == count) {
						break;
					}
					JNaviButton delButton = list.get(delIndex);
					if (delButton.isSelect()) {
						delIndex++;
						continue;
					}
					delCount++;
					list.remove(delIndex);
					this.remove(delButton);
					final JNaviMenuItem menuItem = new JNaviMenuItem(
							delButton.getJf());
					menuItem.setIcon(delButton.getJf().getFrameIcon());
					menuItem.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmMain.getInstance().reopenForm(menuItem.getJf());
							FmMain.getInstance().refreshNaviBar();
						}
					});
					this.getJPopupMenu().add(menuItem);
					lsOther.add(menuItem);
				}
			}
		} else {
			diffTotalWidth = (-diffTotalWidth);
			for (int j = lsOther.size() - 1; j >= 0; j--) {
				JNaviMenuItem menuItem = (JNaviMenuItem) lsOther.get(j);
				JNaviButton newButton = new JNaviButton(menuItem.getJf());
				if (diffTotalWidth > newButton.getPreferredSize().width) {
					list.add(newButton);
					this.add(newButton);
					// button.setSelect(true);
					lsOther.remove(j);
					this.getJPopupMenu().remove(menuItem);
					diffTotalWidth = diffTotalWidth
							- newButton.getPreferredSize().width;
				} else {
					break;
				}
			}
		}
	}

	private int getActiveFormButtonWidth() {
		int totalWidth = 0;
		for (int i = 0; i < list.size(); i++) {
			JNaviButton button = list.get(i);
			totalWidth += button.getPreferredSize().width;
		}
		return totalWidth;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	public JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(116, 32));
		}
		return jPopupMenu;
	}

	public int getActiveFormCount() {
		return list.size();
	}

	public int getInactiveFormCount() {
		return this.lsOther.size();
	}

	public JInternalFrame getSelectedInternalFrame() {
		for (int i = 0; i < list.size(); i++) {
			JNaviButton button = list.get(i);
			if (button.isSelect()) {
				// this.activeInternalFrame((JInternalFrameBase)
				// button.getJf());
				return button.getJf();
			}
			// if (((JInternalFrameBase) button.getJf()).getFlowPaneClassName()
			// != null ? ((JInternalFrameBase) button
			// .getJf()).getFlowPaneClassName().equals(
			// ((JInternalFrameBase) jf).getFlowPaneClassName())
			// : button.getJf().getClass().getName().equals(
			// jf.getClass().getName())) {
			// button.setSelect(true);
			// } else {
			// button.setSelect(false);
			// }
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			int x = 0;
			int y = 23;
			int rectwidth = this.getWidth();
			int rectheight = 6;
			g2.setColor((Color) UIManager.getLookAndFeel().getDefaults()
					.get("TextPane.selectionBackground"));

			// g2.setColor(new Color(6, 193, 35));
			g2.draw(new Rectangle2D.Double(x, y, rectwidth, rectheight));
			g2.fill(new Rectangle2D.Double(x, y, rectwidth, rectheight));
			g2.setStroke(new BasicStroke(0.5f));
			g2.setColor(Color.LIGHT_GRAY);
			// g2.setColor((Color) UIManager.getLookAndFeel().getDefaults().get(
			// "TextPane.selectionBackground"));
			JNaviButton selectedButton = null;
			for (int i = 0; i < list.size(); i++) {
				JNaviButton button = list.get(i);
				if (button.isSelect()) {
					selectedButton = button;
					break;
				}
			}
			if (selectedButton != null) {
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawLine(x, y, selectedButton.getX(), y);
				g2.setColor((Color) UIManager.getLookAndFeel().getDefaults()
						.get("TextPane.selectionBackground"));
				g2.drawLine(selectedButton.getX(), y - 1, selectedButton.getX()
						+ selectedButton.getWidth(), y - 1);
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawLine(selectedButton.getX() + selectedButton.getWidth(),
						y, this.getWidth(), y);
			}
		} catch (Exception ex) {

		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
