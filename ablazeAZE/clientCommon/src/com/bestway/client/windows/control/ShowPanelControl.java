package com.bestway.client.windows.control;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ShowPanelControl {

  private ShowPanelControl() {
  }

  public static JPanel showDesktopPanel(String form,
                                        Container desktop) {
    Class cls = null;
    JPanel panel = null;
    try {
      cls = Class.forName(form);
      panel = (JPanel) cls.newInstance();
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    showDesktopPanel(panel, desktop);
    return panel;
  }

  public static JPanel showDesktopPanel(JPanel panel,
                                        Container desktop) {
    Dimension containerSize = desktop.getSize();
    panel.setLocation(0, 0);
    panel.setSize(containerSize.width, containerSize.height);
    desktop.add(panel, BorderLayout.CENTER);
    desktop.validate();
    return panel;
  }
}
