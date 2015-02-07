package com.bestway.client.fixtureonorder;

import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * @author fhz
 *
 */
public class FmFixtureLocation extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private PnFixtureLocation pnFixassetLocation = null;

	/**
	 * This method initializes 
	 * 
	 */
	public FmFixtureLocation() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(421, 251));
        this.setContentPane(getJContentPane());
        this.setTitle("设备位置存放表");
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPnFixassetLocation(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnFixassetLocation	
	 * 	
	 * @return com.bestway.client.fixasset.PnFixassetLocation	
	 */
	private PnFixtureLocation getPnFixassetLocation() {
		if (pnFixassetLocation == null) {
			pnFixassetLocation = new PnFixtureLocation();
		}
		return pnFixassetLocation;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
