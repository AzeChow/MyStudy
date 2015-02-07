package com.bestway.bcus.client.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SteppedMetalComboBoxUI extends MetalComboBoxUI {// MetalComboBoxUI {BasicComboBoxUI
	private int	popupWidth	= -1;

	public SteppedMetalComboBoxUI(int popupWidth) {
		super();
		this.popupWidth = popupWidth;
	}
	
	
	public SteppedMetalComboBoxUI() {
		super();
	}

	protected ComboPopup createPopup() {
		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			public void show() {
				Dimension popupSize = ((JComboBox) comboBox).getSize();
				if (popupWidth != -1 && popupWidth > 0) {
					if (popupWidth < popupSize.width) {
						popupWidth = popupSize.width-2;
					}
					popupSize.setSize(popupWidth,
							getPopupHeightForRowCount(comboBox
									.getMaximumRowCount()));
				} else if (popupSize.width > 150) {
					popupSize.setSize(popupSize.width,
							getPopupHeightForRowCount(comboBox
									.getMaximumRowCount()));
				} else {
					popupSize.setSize(150, getPopupHeightForRowCount(comboBox
							.getMaximumRowCount()));
				}
				Rectangle popupBounds = computePopupBounds(0, comboBox
						.getBounds().height, popupSize.width, popupSize.height);// popupSize.width,
				// popupSize.height);
				scroller.setMaximumSize(popupBounds.getSize());
				scroller.setPreferredSize(popupBounds.getSize());
				scroller.setMinimumSize(popupBounds.getSize());
				list.invalidate();
				int selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex == -1) {
					list.clearSelection();
				} else {
					list.setSelectedIndex(selectedIndex);
				}
				list.ensureIndexIsVisible(list.getSelectedIndex());
				setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());

				show(comboBox, popupBounds.x, popupBounds.y);
			}
		};
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}
}
