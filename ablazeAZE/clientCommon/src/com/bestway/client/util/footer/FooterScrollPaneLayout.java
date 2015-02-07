package com.bestway.client.util.footer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;
import javax.swing.ViewportLayout;
import javax.swing.plaf.UIResource;

public class FooterScrollPaneLayout extends ScrollPaneLayout {
	/**
	 * The column header child. Default is <code>null</code>.
	 * 
	 * @see JScrollPane#setColumnHeader
	 */
	protected JViewport colFooter;

	/**
	 * Identifies the area along the left side of the viewport between the upper
	 * left corner and the lower left corner.
	 */
	String COLUMN_FOOTER = "COLUMN_FOOTER";

	@Override
	public void addLayoutComponent(String s, Component c) {
		if (s.equals(COLUMN_FOOTER)) {
			colFooter = (JViewport) addSingletonComponent(colFooter, c);
		} else {
			super.addLayoutComponent(s, c);
		}
	}

	/**
	 * Removes the specified component from the layout.
	 * 
	 * @param c
	 *            the component to remove
	 */
	@Override
	public void removeLayoutComponent(Component c) {
		super.removeLayoutComponent(c);
		if (c == colFooter) {
			colFooter = null;
		}
	}

	/**
	 * This method is invoked after the ScrollPaneLayout is set as the
	 * LayoutManager of a <code>JScrollPane</code>. It initializes all of the
	 * internal fields that are ordinarily set by
	 * <code>addLayoutComponent</code>. For example:
	 * 
	 * <pre>
	 *                       ScrollPaneLayout mySPLayout = new ScrollPanelLayout() {
	 *                           public void layoutContainer(Container p) {
	 *                               super.layoutContainer(p);
	 *                               // do some extra work here ...
	 *                           }
	 *                       };
	 *                       scrollpane.setLayout(mySPLayout):
	 * </pre>
	 */
	@Override
	public void syncWithScrollPane(JScrollPane sp) {
		super.syncWithScrollPane(sp);
		colFooter = ((JFooterScrollPane) sp).getColumnFooter();
	}

	/**
	 * The preferred size of a <code>ScrollPane</code> is the size of the
	 * insets, plus the preferred size of the viewport, plus the preferred size
	 * of the visible headers, plus the preferred size of the scrollbars that
	 * will appear given the current view and the current scrollbar
	 * displayPolicies.
	 * <p>
	 * Note that the rowHeader is calculated as part of the preferred width and
	 * the colHeader is calculated as part of the preferred size.
	 * 
	 * @param parent
	 *            the <code>Container</code> that will be laid out
	 * @return a <code>Dimension</code> object specifying the preferred size
	 *         of the viewport and any scrollbars
	 * @see ViewportLayout
	 * @see LayoutManager
	 */
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = super.minimumLayoutSize(parent);
		int width = dim.width + colFooter.getPreferredSize().width;
		int height = dim.height + colFooter.getPreferredSize().height;
		return new Dimension(width, height);
	}

	/**
	 * Returns the <code>JViewport</code> object that is the column header.
	 * 
	 * @return the <code>JViewport</code> object that is the column header
	 * @see JScrollPane#getColumnHeader
	 */
	public JViewport getColumnFooter() {
		return colFooter;
	}

	/**
	 * The minimum size of a <code>ScrollPane</code> is the size of the insets
	 * plus minimum size of the viewport, plus the scrollpane's viewportBorder
	 * insets, plus the minimum size of the visible headers, plus the minimum
	 * size of the scrollbars whose displayPolicy isn't NEVER.
	 * 
	 * @param parent
	 *            the <code>Container</code> that will be laid out
	 * @return a <code>Dimension</code> object specifying the minimum size
	 */
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = super.minimumLayoutSize(parent);
		int width = dim.width + colFooter.getPreferredSize().width;
		int height = dim.height + colFooter.getPreferredSize().height;
		return new Dimension(width, height);
	}

	/**
	 * Lays out the scrollpane. The positioning of components depends on the
	 * following constraints:
	 * <ul>
	 * <li> The row header, if present and visible, gets its preferred width and
	 * the viewport's height.
	 * 
	 * <li> The column header, if present and visible, gets its preferred height
	 * and the viewport's width.
	 * 
	 * <li> If a vertical scrollbar is needed, i.e. if the viewport's extent
	 * height is smaller than its view height or if the
	 * <code>displayPolicy</code> is ALWAYS, it's treated like the row header
	 * with respect to its dimensions and is made visible.
	 * 
	 * <li> If a horizontal scrollbar is needed, it is treated like the column
	 * header (see the paragraph above regarding the vertical scrollbar).
	 * 
	 * <li> If the scrollpane has a non-<code>null</code>
	 * <code>viewportBorder</code>,
	 * then space is allocated for that.
	 * 
	 * <li> The viewport gets the space available after accounting for the
	 * previous constraints.
	 * 
	 * <li> The corner components, if provided, are aligned with the ends of the
	 * scrollbars and headers. If there is a vertical scrollbar, the right
	 * corners appear; if there is a horizontal scrollbar, the lower corners
	 * appear; a row header gets left corners, and a column header gets upper
	 * corners.
	 * </ul>
	 * 
	 * @param parent
	 *            the <code>Container</code> to lay out
	 */
	@Override
	public void layoutContainer(Container parent) {
		super.layoutContainer(parent);
		this.viewport.repaint();
		JTableFooter tableFooter = (JTableFooter) colFooter.getView();
		if (tableFooter.getFooterTypeInfos() != null
				&& !tableFooter.getFooterTypeInfos().isEmpty()) {
			colFooter.getView().setPreferredSize(
					new Dimension(colHead.getView().getPreferredSize().width,
							colFooter.getView().getPreferredSize().height));
			Rectangle viewportBounds = null;
			Rectangle colHeadBounds = null;
			if (viewport != null) {
				viewportBounds = viewport.getBounds();
			}
			if (super.colHead != null) {
				colHeadBounds = colHead.getBounds();
			}
			viewportBounds = new Rectangle(viewportBounds.x, viewportBounds.y,
					viewportBounds.width, viewportBounds.height
							- colFooter.getView().getPreferredSize().height);
			viewport.setBounds(viewportBounds);
			Rectangle colFootBounds = new Rectangle(
					colHeadBounds.x,
					colHeadBounds.y
							+ colHeadBounds.height
							+ (viewportBounds == null ? 0
									: viewportBounds.height),
					(colHeadBounds == null ? 0 : colHeadBounds.width),
					colFooter.getView().getPreferredSize().height);
			colFooter.setBounds(colFootBounds);
			Rectangle vsbBounds = new Rectangle(this.vsb.getBounds().x,
					this.vsb.getBounds().y, this.vsb.getBounds().width,
					this.vsb.getBounds().height - colFootBounds.height);
			this.vsb.setBounds(vsbBounds);
			if (this.lowerRight != null) {
				Rectangle lowerRightBounds = new Rectangle(this.lowerRight
						.getBounds().x, this.lowerRight.getBounds().y
						- colFootBounds.height,
						this.lowerRight.getBounds().width, this.lowerRight
								.getBounds().height
								+ colFootBounds.height);
				this.lowerRight.setBounds(lowerRightBounds);
			}
			((JTableFooter) colFooter.getView()).refreshFooterFiled();
		}
	}

	/**
	 * The UI resource version of <code>ScrollPaneLayout</code>.
	 */
	public static class FooterUIResource extends FooterScrollPaneLayout
			implements javax.swing.plaf.UIResource {
	}

}
