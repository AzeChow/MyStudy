/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.ThreadData;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class FmDBExport extends JInternalFrameBase {

	private javax.swing.JPanel	jContentPane		= null;
	private JPanel				jPanel1				= null;
	private JRadioButton		rbDataSource		= null;
	private JRadioButton		rbView				= null;
	private JRadioButton		rbRegion			= null;
	private JRadioButton		rbTaskPlan			= null;
	private JRadioButton		rbViewPara			= null;
	private JRadioButton		rbExecute			= null;
	private JPanel				jPanel2				= null;
	private ButtonGroup			group				= new ButtonGroup();
	private JPanel				jPanel				= null;
	private DataExportAction	dataExportAction	= null;
	private JCheckBox			cbIsStart			= null;
	private JRadioButton		rbSqlEvent			= null;

	/**
	 * This is the default constructor
	 */
	public FmDBExport() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		ThreadData threadData = dataExportAction.findThreadData(new Request(
				CommonVars.getCurrUser()), ThreadData.DB_TYPE);
		if (threadData != null) {
			this.cbIsStart.setSelected(threadData.getIsStart());
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAllRadioButtonProperty(this.jContentPane);
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
	private void setAllRadioButtonProperty(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			if (temp instanceof JRadioButton) {
				UUID uuid = UUID.randomUUID();
				final String flag = uuid.toString();
				JRadioButton radioButton = (JRadioButton) temp;
				radioButton.setOpaque(false);
				radioButton.setActionCommand(flag);
				radioButton.addActionListener(new RadioActionListner());
				group.add(radioButton);
			} else {
				setAllRadioButtonProperty(temp);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("DB导出管理");
		this.setSize(925, 507);
		this.setContentPane(getJContentPane());
		group.add(rbDataSource);
		group.add(rbView);
		group.add(rbRegion);
		group.add(rbTaskPlan);
		group.add(rbExecute);
		group.add(rbViewPara);
		group.add(rbSqlEvent);
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes rbDataSource
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbDataSource() {
		if (rbDataSource == null) {
			rbDataSource = new JRadioButton();
			rbDataSource.setText("数据源操作");
			rbDataSource.setBounds(221, 120, 123, 26);
			rbDataSource.setBackground(java.awt.Color.white);
			rbDataSource.addActionListener(new RadioActionListner());
		}
		return rbDataSource;
	}

	/**
	 * 
	 * This method initializes rbView
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbView() {
		if (rbView == null) {
			rbView = new JRadioButton();
			rbView.setText("视图操作");
			rbView.setBounds(289, 214, 113, 21);
			rbView.setBackground(java.awt.Color.white);
			rbView.addActionListener(new RadioActionListner());
		}
		return rbView;
	}

	/**
	 * 
	 * This method initializes rbRegion
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbRegion() {
		if (rbRegion == null) {
			rbRegion = new JRadioButton();
			rbRegion.setText("DB 域定义管理");
			rbRegion.setBounds(312, 245, 120, 21);
			rbRegion.setBackground(java.awt.Color.white);
			rbRegion.addActionListener(new RadioActionListner());
		}
		return rbRegion;
	}

	/**
	 * 
	 * This method initializes rbTaskPlan
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbTaskPlan() {
		if (rbTaskPlan == null) {
			rbTaskPlan = new JRadioButton();
			rbTaskPlan.setText("DB 任务计划设置");
			rbTaskPlan.setBounds(336, 279, 124, 21);
			rbTaskPlan.setBackground(java.awt.Color.white);
			rbTaskPlan.addActionListener(new RadioActionListner());
		}
		return rbTaskPlan;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					null, null));
			jPanel2.setBackground(java.awt.Color.white);
			jLabel.setBounds(-2, 3, 309, 97);
			jLabel.setText("");
			jLabel.setIcon(CommonVars.getIconSource().getIcon("txtexport.gif"));
			// jLabel
			// .setIcon(new ImageIcon(
			// getClass()
			// .getResource(
			// "/com/bestway/bcus/client/resources/images/txtexport.gif")));
			jLabel1.setBounds(7, 32, 518, 326);
			jLabel1.setText("");
			jLabel1
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/import_middle.gif")));
			jPanel2.add(getRbDataSource(), null);
			jPanel2.add(getRbView(), null);
			jPanel2.add(getRbRegion(), null);
			jPanel2.add(getRbTaskPlan(), null);
			jPanel2.add(getRbExecute(), null);
			jPanel2.add(getRbViewPara(), null);
			jPanel2.add(getRbSqlEvent(), null);

			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getCbIsStart(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes rbExecute
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbExecute() {
		if (rbExecute == null) {
			rbExecute = new JRadioButton();
			rbExecute.setText("执行DB导入导出");
			rbExecute.setBounds(353, 309, 131, 21);
			rbExecute.setBackground(java.awt.Color.white);
			rbExecute.addActionListener(new RadioActionListner());
		}
		return rbExecute;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (rbViewPara.isSelected()) { // SQL参数设置
				String flag = rbViewPara.getActionCommand();
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkSqlParameterExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCParameter dg = new DgJDBCParameter();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }                
			} else if (rbDataSource.isSelected()) { // 数据源操作	
				String flag = rbDataSource.getActionCommand();
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkDataSourceExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCDataSource dg = new DgJDBCDataSource();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }                    
                
			} else if (rbSqlEvent.isSelected()) { // 事件设置
				
				String flag = rbSqlEvent.getActionCommand();
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkEventExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCSqlEvent dg = new DgJDBCSqlEvent();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }
                
			} else if (rbView.isSelected()) { // 视图操作
				
				String flag = rbView.getActionCommand();
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkViewExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCView dg = new DgJDBCView();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }
                
			} else if (rbRegion.isSelected()) { // DB域定义管理
				
				String flag = rbRegion.getActionCommand();				
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkDBRegionExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCRegion dg = new DgJDBCRegion();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }
                
			} else if (rbTaskPlan.isSelected()) { // DB任务计划设置
				
				String flag = rbTaskPlan.getActionCommand();				
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkDBTaskExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCTask dg = new DgJDBCTask();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }
                
			} else if (rbExecute.isSelected()) { // 执行DB导入导出
				
				String flag = rbExecute.getActionCommand();				
                JDialogBase dialog = JDialogBaseHelper
                        .getJDialogBaseByFlag(flag);
                if (dialog == null) {
                	dataExportAction.checkDBTaskExeExportAuthority(new Request(CommonVars
            				.getCurrUser()));
                	DgJDBCTaskExe dg = new DgJDBCTaskExe();
                    JDialogBaseHelper.putJDialogBaseToFlag(flag,
                    		dg);
                    dg.setVisible(true);
                } else {
                    dialog.setVisibleNoChange(true);
                }
			}
		}
	}

	/**
	 * 
	 * This method initializes pn
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel2.setText("DB导入导出管理");
			jLabel2
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel2.setForeground(new java.awt.Color(255, 153, 0));
			jLabel3.setText("");
			jLabel3.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel4.setText("");
			jLabel4
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel2, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel3, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel4, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes cbIsStart
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbIsStart() {
		if (cbIsStart == null) {
			cbIsStart = new JCheckBox();
			cbIsStart.setBounds(451, 13, 188, 21);
			cbIsStart.setText("启动DB自动导入导出服务");
			cbIsStart.setBackground(java.awt.Color.white);
			cbIsStart.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					ThreadData threadData = dataExportAction.findThreadData(
							new Request(CommonVars.getCurrUser()),
							ThreadData.DB_TYPE);
					if (cbIsStart.isSelected()) {
						if (threadData == null) {
							threadData = new ThreadData();
						}
						threadData.setCompany(CommonVars.getCurrUser()
								.getCompany());
						threadData.setType(ThreadData.DB_TYPE);
						threadData.setIsStart(true);
						dataExportAction.saveThreadData(new Request(CommonVars
								.getCurrUser()), threadData);
						dataExportAction.startImportExportThread(new Request(
								CommonVars.getCurrUser()));
					} else {
						if (threadData != null) {
							threadData.setIsStart(false);
							dataExportAction.saveThreadData(new Request(
									CommonVars.getCurrUser()), threadData);
							dataExportAction
									.shutDownImportExportThread(new Request(
											CommonVars.getCurrUser()));
						}
					}
				}
			});

		}
		return cbIsStart;
	}

	/**
	 * This method initializes rbViewPara
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbViewPara() {
		if (rbViewPara == null) {
			rbViewPara = new JRadioButton();
			rbViewPara.setText("SQL参数设置");
			rbViewPara.setBackground(java.awt.Color.white);
			rbViewPara.setBounds(269, 185, 110, 21);
			rbViewPara.addActionListener(new RadioActionListner());
		}
		return rbViewPara;
	}

	/**
	 * This method initializes rbSqlEvent
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSqlEvent() {
		if (rbSqlEvent == null) {
			rbSqlEvent = new JRadioButton();
			rbSqlEvent.setBounds(249, 155, 91, 21);
			rbSqlEvent.setText("事件设置");
			rbSqlEvent.setBackground(java.awt.Color.white);
			rbSqlEvent.addActionListener(new RadioActionListner());
		}
		return rbSqlEvent;
	}
}
