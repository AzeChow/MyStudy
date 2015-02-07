package com.bestway.common.client.fpt;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class PnMakeCustomsOrderToFptAppHead3 extends JPanel {

	private JScrollPane jScrollPane = null;
	private JTextPane jTextPane = null;

	/**
	 * This method initializes
	 * 
	 */
	public PnMakeCustomsOrderToFptAppHead3() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(568, 352));
		this.add(getJScrollPane(), BorderLayout.CENTER);

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
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
		}
		return jTextPane;
	}

	public void showMessage(String message) {
		jTextPane.setText(message);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
