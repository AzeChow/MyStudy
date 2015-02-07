/*
 * Created on 2004-9-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseComboBoxUI extends MetalComboBoxUI {// BasicComboBoxUI
    // MetalComboBoxUI
    private double popupWidth = 0;

    public CustomBaseComboBoxUI(double popupWidth) {
        this.popupWidth = popupWidth;
    }

    public CustomBaseComboBoxUI() {
    }

    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        bounds.x += 2;
        bounds.y += 2;
        bounds.width -= 3;
        bounds.height -= 4;
        super.paintCurrentValue(g, bounds, hasFocus);
    }

    public void paintCurrentValueBackground(Graphics g, Rectangle bounds,
            boolean hasFocus) {
        // g.setColor(MetalLookAndFeel.getControlDarkShadow());
        g.setColor(new java.awt.Color(106, 135, 171));
        g.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
        // g.setColor(MetalLookAndFeel.getControlShadow());
        // g.setColor(new java.awt.Color(106, 135, 171));
        // g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 2,
        // bounds.height - 3);
    }

    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {

            public void show() {
                Dimension popupSize = ((JComboBox) comboBox).getSize();
                if (popupWidth > 0) {                	
                    if (popupWidth < popupSize.width) {
                        popupWidth = popupSize.width - 2;
                    }
                    popupSize.setSize(popupWidth,
                            getPopupHeightForRowCount(comboBox
                                    .getMaximumRowCount()));
                } else if (popupSize.width > 150) {
                    popupSize.setSize(popupSize.width - 2,
                            getPopupHeightForRowCount(comboBox
                                    .getMaximumRowCount()));
                } else {
                    popupSize.setSize(150, getPopupHeightForRowCount(comboBox
                            .getMaximumRowCount()));
                }

                Rectangle popupBounds = computePopupBounds(0, comboBox
                        .getBounds().height, popupSize.width, popupSize.height);// popupSize.width,
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
//        popup.getList().setBackground(Color.WHITE);
        return popup;
    }
}