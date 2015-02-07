package com.bestway.ui.winuicontrol;

import java.awt.Graphics;

import javax.swing.JPanel;

public class JPanelBase extends JPanel {

    private boolean isAddkeyListenered = true;
    private Object  tag                = null;

    protected void paintComponent(Graphics g) {
        if (isAddkeyListenered) {
            KeyAdapterControl.AddListener(this);
            isAddkeyListenered = false;
        }
        super.paintComponent(g);
    }
    

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
