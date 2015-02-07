package com.bestway.client.util.footer;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

public class JFooterScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;

	protected JViewport columnFooter;

	protected String COLUMN_FOOTER = "COLUMN_FOOTER";

	public JFooterScrollPane() {
		super();
	}

	/**
	 * Removes the old viewport (if there is one); forces the viewPosition of
	 * the new viewport to be in the +x,+y quadrant; syncs up the row and column
	 * headers (if there are any) with the new viewport; and finally syncs the
	 * scrollbars and headers with the new viewport.
	 * <p>
	 * Most applications will find it more convenient to use
	 * <code>setViewportView</code> to add a viewport and a view to the
	 * scrollpane.
	 * 
	 * @param viewport
	 *            the new viewport to be used; if viewport is <code>null</code>,
	 *            the old viewport is still removed and the new viewport is set
	 *            to <code>null</code>
	 * @see #createViewport
	 * @see #getViewport
	 * @see #setViewportView
	 * 
	 * @beaninfo expert: true bound: true attribute: visualUpdate true
	 *           description: The viewport child for this scrollpane
	 * 
	 */
	@Override
	public void setViewport(JViewport viewport) {
		viewport = new JLineViewport();
		super.setViewport(viewport);
	}

	/**
	 * Creates a <code>JScrollPane</code> that displays the view component in a
	 * viewport whose view position can be controlled with a pair of scrollbars.
	 * The scrollbar policies specify when the scrollbars are displayed, For
	 * example, if <code>vsbPolicy</code> is
	 * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code> then the vertical scrollbar
	 * only appears if the view doesn't fit vertically. The available policy
	 * settings are listed at {@link #setVerticalScrollBarPolicy} and
	 * {@link #setHorizontalScrollBarPolicy}.
	 * 
	 * @see #setViewportView
	 * 
	 * @param view
	 *            the component to display in the scrollpanes viewport
	 * @param vsbPolicy
	 *            an integer that specifies the vertical scrollbar policy
	 * @param hsbPolicy
	 *            an integer that specifies the horizontal scrollbar policy
	 */
	public void setLayout(LayoutManager layout) {
		super.setLayout(new FooterScrollPaneLayout.FooterUIResource());
	}

	/**
	 * Returns the column header.
	 * 
	 * @return the <code>columnHeader</code> property
	 * @see #setColumnHeader
	 */
	public JViewport getColumnFooter() {
		return columnFooter;
	}

	/**
	 * Removes the old columnHeader, if it exists; if the new columnHeader isn't
	 * <code>null</code>, syncs the x coordinate of its viewPosition with the
	 * viewport (if there is one) and then adds it to the scroll pane.
	 * <p>
	 * Most applications will find it more convenient to use
	 * <code>setColumnHeaderView</code> to add a column header component and its
	 * viewport to the scroll pane.
	 * 
	 * @see #getColumnHeader
	 * @see #setColumnHeaderView
	 * 
	 * @beaninfo bound: true description: The column header child for this
	 *           scrollpane attribute: visualUpdate true
	 */
	public void setColumnFooter(JViewport columnFooter) {
		JViewport old = getColumnFooter();
		this.columnFooter = columnFooter;
		if (columnFooter != null) {
			add(columnFooter, COLUMN_FOOTER);
		} else if (old != null) {
			remove(old);
		}
		firePropertyChange("columnFooter", old, columnFooter);

		revalidate();
		repaint();
	}

	/**
	 * Creates a column-header viewport if necessary, sets its view, and then
	 * adds the column-header viewport to the scrollpane. For example:
	 * 
	 * <pre>
	 * JScrollPane scrollpane = new JScrollPane();
	 * scrollpane.setViewportView(myBigComponentToScroll);
	 * scrollpane.setColumnHeaderView(myBigComponentsColumnHeader);
	 * </pre>
	 * 
	 * @see #setColumnHeader
	 * @see JViewport#setView
	 * 
	 * @param view
	 *            the component to display as the column header
	 */
	public void setColumnFooterView(Component view) {
		if (getColumnFooter() == null) {
			setColumnFooter(createViewport());
		}
		getColumnFooter().setView(view);
	}

	@Override
	public JScrollBar createVerticalScrollBar() {
		JScrollBar vScrollBar = super.createVerticalScrollBar();
		vScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (columnFooter != null) {
					Point vpHeader = columnHeader.getViewPosition();
					Point vp = columnFooter.getViewPosition();
					vp.x = vpHeader.x;
					columnFooter.setViewPosition(vp);
					columnFooter.repaint();
					viewport.repaint();
					JScrollBar jsb = ((JScrollBar) e.getSource());					
					if (e.getValue() == 0) {
						jsb.setValue(jsb.getMinimum());
					} else if ((jsb.getMaximum() - (e.getValue() + jsb.getVisibleAmount())) == columnFooter.getPreferredSize().height) {
						jsb.setValue(jsb.getMaximum());
					}
				}
			}
		});
		return vScrollBar;
	}

	@Override
	public JScrollBar createHorizontalScrollBar() {
		//
		JScrollBar horizontalScrollBar = super.createHorizontalScrollBar();
		horizontalScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (columnFooter != null) {
					Point vpHeader = columnHeader.getViewPosition();
					Point vp = columnFooter.getViewPosition();
					vp.x = vpHeader.x;
					columnFooter.setViewPosition(vp);
					columnFooter.repaint();
					viewport.repaint();
				}
			}
		});
		return horizontalScrollBar;
	}

}
