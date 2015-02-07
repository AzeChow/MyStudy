package com.bestway.ui.winuicontrol;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bestway.client.windows.control.UIStyleControl;

public class JFrameBase extends JFrame {

	// 判断是否要加入添加键监听器
	private boolean isAddkeyListenered = false;

	private Object flag = null;

	private void setUIStyle() {

		UIStyleControl.setCurrentUIStyle(this);

	}

	public void setVisible(boolean b) {
		if (b) {
			if (!isAddkeyListenered) {
				KeyAdapterControl.AddListener(this.getContentPane());
				isAddkeyListenered = true;
			}
			setUIStyle();
			this.setIconImage((new ImageIcon(getClass().getResource(
					"/com/bestway/client/resource/images/bestway.gif")))
					.getImage());
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);
		}
		super.setVisible(b);
	}

	public void setVisibleNoChange(boolean b) {
		super.setVisible(b);
	}

	public Object getFlag() {
		return flag;
	}

	public void setFlag(Object flag) {
		this.flag = flag;
	}

}
