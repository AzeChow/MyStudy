/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.parameter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.authority.CasParameterAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillInit;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.bcus.client.CheckAutoUpgradeState;
import com.bestway.bcus.client.cas.DgImgOrgUseInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.owp.DgOwpAppHead;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.DgBillCheckResult;
import com.bestway.common.client.erpbillcenter.FmBill;
import com.bestway.common.tools.action.ToolsAction;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PnWriteAccountYear extends PnCasParameterCommon {

	private JPanel jPanel = null;
	private JCheckBox cbInitBillMake = null;
	private JPanel jPanel1 = null;
	private JButton btnEdit = null;
	private JButton btnAvailability = null;
	private JButton btnExit = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JComboBox cbbYear = null;
	private JLabel jLabel2 = null;
	private static PnWriteAccountYear pnWriteAccountYear = null;
	private WriteAccountYear writeAccountYear = null; // @jve:decl-index=0:
	private CasParameterAction casParameterAction = null;

	 private boolean autoRestart = false;

	/**
	 * This method initializes
	 * 
	 */
	public PnWriteAccountYear() {
		super();
		casParameterAction = (CasParameterAction) CommonVars
				.getApplicationContext().getBean("casParameterAction");
		this.writeAccountYear = CasCommonVars.getWriteAccountYear();
		initialize();
		showData(false);
	}

	public static PnWriteAccountYear getInstance() {
		if (pnWriteAccountYear == null) {
			pnWriteAccountYear = new PnWriteAccountYear();
		}
		return pnWriteAccountYear;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
//		jLabel5 = new JLabel();
//		jLabel5.setBounds(new Rectangle(3, 375, 611, 18));
//		jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));
//		jLabel5.setText("为了防止因单据资料有误而导致生成的期初单不正确，建议先检查单据。（建议直接到单据管理中心进行检查）");
		jLabel4 = new JLabel();
		jLabel4.setBounds(new Rectangle(35, 82, 32, 18));
		jLabel4.setText("１：");
		jLabel3 = new JLabel();
		jLabel3.setBounds(22, 406, 140, 26);
		jLabel3.setText("");
		// (*)需重启才能生效!!!
		jLabel2 = new JLabel();
		jLabel1 = new JLabel();
		jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(631, 446);
		jLabel.setBounds(3, 31, 586, 21);
		jLabel.setText("记帐年度");
		jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		jLabel1.setBounds(70, 82, 86, 21);
		jLabel1.setText("当前记帐年度");
		jLabel2.setBounds(264, 82, 32, 21);
		jLabel2.setText("年");
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
		this.add(getCbInitBillMake(), null);
		this.add(getBtnEdit(), null);
		this.add(getBtnAvailability(), null);
		this.add(getBtnExit(), null);
		this.add(jLabel, null);

		this.add(jLabel1, null);
		this.add(getCbbYear(), null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(getJCheckBox11(), null);
		this.add(getBtnSave(), null);
		this.add(jLabel4, null);
//		this.add(jLabel5, null);
		this.add(getBtnBillCheck(), null);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(3, 55, 625, 3);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel;
	}

	/**
	 * This method initializes cbInitBillMake
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbInitBillMake() {
		if (cbInitBillMake == null) {
			cbInitBillMake = new JCheckBox();
			cbInitBillMake.setText("年度期初单保存至BillInit，【保存】后需点击【生成期初】按钮");
			cbInitBillMake.setSelected(true);
			cbInitBillMake.setBounds(86, 110, 382, 18);
			cbInitBillMake.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			cbInitBillMake
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if (cbInitBillMake.isSelected()) {
								jCheckBox11.setSelected(false);
							}
							// if(!jCheckBox1.isSelected()){
							// jCheckBox11.setSelected(true);
							// }
						}

					});
		}
		return cbInitBillMake;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBounds(3, 394, 625, 3);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(300, 405, 68, 26);
			btnEdit.setText("修改");
			btnEdit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setBounds(373, 405, 68, 26);
			btnAvailability.setText("保存");
			btnAvailability.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (vali() == false) {
								return;
							}
							dataSate = DataState.BROWSE;
							// setState();
							showData(true);
							// test();
							new AvailabilityThread().start();
						}
					});
		}
		return btnAvailability;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(552, 405, 68, 26);
			btnExit.setText("关闭");
			btnExit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		jPanel.setBounds(3, this.getHeight() - 50, this.getWidth() - 6, 3);
		jPanel1.setBounds(3, 55, this.getWidth() - 6, 3);
		
//		jLabel5.setBounds(3, btnBillCheck.getY() - 30, this.getWidth(), jLabel5.getHeight());
		

		btnExit.setBounds(this.getWidth() - 100, this.getHeight() - 40, btnExit
				.getWidth(), btnExit.getHeight());
		btnSave.setBounds(this.getWidth() - 92 - btnExit.getWidth() - 50, this
				.getHeight() - 40, btnSave.getWidth(), btnSave.getHeight());
		btnAvailability.setBounds(this.getWidth() - 92 - btnExit.getWidth()
				- btnSave.getWidth() - 20, this.getHeight() - 40,
				btnAvailability.getWidth(), btnAvailability.getHeight());
		
		
		btnBillCheck.setBounds(this.getWidth() - 150 - btnEdit.getWidth() - btnExit.getWidth()
				- btnSave.getWidth() - btnAvailability.getWidth() - 50, this
				.getHeight() - 40, btnBillCheck.getWidth(), btnBillCheck.getHeight());
		
		
		
		btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
				- btnSave.getWidth() - btnAvailability.getWidth() - 30, this
				.getHeight() - 40, btnEdit.getWidth(), btnEdit.getHeight());

		jLabel3.setBounds(jLabel3.getX(), btnEdit.getY(), jLabel3.getWidth(),
				jLabel3.getHeight());
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbYear() {
		if (cbbYear == null) {
			cbbYear = new JComboBox();
			for (int i = 0; i < 50; i++) {
				cbbYear.addItem(new Integer(2000 + i));
			}
			cbbYear.setBounds(164, 82, 86, 21);
		}
		return cbbYear;
	}

	/**
	 * 修改
	 */
	private void edit() {
		casParameterAction.checkCasParameterByUpdate(new Request(CommonVars
				.getCurrUser()));
		super.setContainerEnabled(this, true);
		this.dataSate = DataState.EDIT;
		setState();
	}

	/**
	 * 生效
	 */
	// private void availability() {
	// if (vali() == false) {
	// return;
	// }
	// CasAction casAction = (CasAction) CommonVars.getApplicationContext()
	// .getBean("casAction");
	// // =========没有重启前抓取期初单数据
	// Map<String, Map<String, Double>> map = null;
	// Map<String, Double> productMap = null;
	// Map<String, Map<String, Map<String, Double>>> maps = null;
	// Map<String, Map<String, Double>> f1Map = null;
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在生成期初数据，请稍候...");
	// String taskId = CommonProgress.getExeTaskId();
	// if (jCheckBox1.isSelected()) {
	// map = casAction.getMaterielF24F25ForBill(new Request(CommonVars
	// .getCurrUser()), taskId, null, null);
	// productMap = casAction.getProductF1ForBill(new Request(
	// CommonVars.getCurrUser()), taskId, null, null);
	// } else if (jCheckBox11.isSelected()) {
	// maps = casAction.getMaterielF24F25ForBillByCustom(new Request(
	// CommonVars.getCurrUser()), "", null, null);
	// f1Map = casAction.getProductF1ForBillByCustom(new Request(
	// CommonVars.getCurrUser()), "", null, null);
	// }
	//
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// e.printStackTrace();
	// JOptionPane.showMessageDialog(PnWriteAccountYear.this, "生成数据失败：！"
	// + e.getMessage(), "提示", 2);
	// return;
	// }
	// CommonProgress.setMessage("抓取数据完毕，正保存记账年度，准备重启服务器...");
	// // =========重启服务器
	// Integer item = (Integer) this.cbbYear.getSelectedItem();
	// if (item.intValue() != CasCommonVars.getYearInt()) {
	// fillData();
	// writeAccountYear = casAction.saveWriteAccountYear(new Request(
	// CommonVars.getCurrUser()), this.writeAccountYear);
	// CasCommonVars.setWriteAccountYear(writeAccountYear);
	// try {
	// SystemAction systemAction = (SystemAction) CommonVars
	// .getApplicationContext().getBean("systemAction");
	// systemAction.restart(new Request(CommonVars.getCurrUser()));
	// new CheckAutoUpgradeState(" 重启服务器 ").start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// CommonProgress.setMessage("保存记账年度完毕，等待服务器重启5分钟...");
	// // ==========生成期初单
	// try {
	// Thread.currentThread().sleep(1000 * 60 * 5);// 等待5分钟，让系统重启后再运行
	// CommonProgress.setMessage("等待完毕，开始保存期初单...");
	// // 根据Map生成单据
	// if (jCheckBox1.isSelected()) {
	// if (map != null) {
	// Map<String, Double> f24 = map.get("f24");
	// casAction.updateBillByMap(new Request(CommonVars
	// .getCurrUser()), (HashMap) f24, "1001");// 料件期初单
	// CommonProgress.setMessage("正在生成料件部分期初单据...");
	// System.out.println("=====正在生成料件部分期初单据");
	// Map<String, Double> f25 = map.get("f25");
	// casAction.updateBillByMap(new Request(CommonVars
	// .getCurrUser()), (HashMap) f25, "1002");// 在产品期初单
	// }
	// // 成品期初单
	// if (productMap != null) {
	// CommonProgress.setMessage("正在生成成品部分期初单据...");
	// System.out.println("=====正在生成成品部分期初单据");
	// casAction.updateBillByMap(new Request(CommonVars
	// .getCurrUser()), (HashMap) productMap, "2001");// 成品期初单
	// }
	// }
	// // ******************************************分客户
	// if (jCheckBox11.isSelected()) {
	// if (maps != null) {
	// CommonProgress.setMessage("正在生成料件部分期初单据...");
	// System.out.println("=====正在生成料件部分期初单据");
	// casAction.updateBillByMapForCustom(new Request(CommonVars
	// .getCurrUser()), (HashMap) map.get("f24"), "1001");
	// casAction.updateBillByMapForCustom(new Request(CommonVars
	// .getCurrUser()), (HashMap) map.get("f25"), "1002");
	// }
	// if (f1Map != null) {
	// CommonProgress.setMessage("正在生成成品部分期初单据...");
	// System.out.println("=====正在生成成品部分期初单据");
	// casAction.updateBillByMapForCustom(new Request(CommonVars
	// .getCurrUser()), (HashMap) f1Map, "2001");
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// super.setContainerEnabled(this, false);
	// this.dataSate = DataState.BROWSE;
	// setState();
	// }
	class AvailabilityThread extends Thread {
		public void run() {

			CasAction casAction = (CasAction) CommonVars
					.getApplicationContext().getBean("casAction");
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			// =========没有重启前抓取期初单数据
			Integer item = (Integer) cbbYear.getSelectedItem();
			int lastYearInt = CasCommonVars.getYearInt();
			boolean isMakeCacheData = item.intValue() > lastYearInt;// 是否是后一年
			CommonProgress.showProgressDialog();
			// 1.如果是后一年，则需生成下年度期初
			if (isMakeCacheData) {
				try {
//					CommonProgress.showProgressDialog();
//					CommonProgress.setMessage("系统正在生成期初数据，请稍候...");
					//String taskId = CommonProgress.getExeTaskId();
					ProgressTask progressTask = new ProgressTask() {
						protected void setClientTipMessage() {
							ToolsAction toolsAction = (ToolsAction) CommonVars
									.getApplicationContext().getBean(
											"toolsAction");
							ProgressInfo progressInfo = toolsAction
									.getProgressInfo(flag);
							if (progressInfo != null
									&& progressInfo.getHintMessage() != null) {
								CommonProgress.setMessage(flag, progressInfo
										.getHintMessage());
							}
						}
					};
//					CommonProgress.showProgressDialog(flag,
//							PnWriteAccountYear.this, false, progressTask, 5000);
					
					CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
					if (cbInitBillMake.isSelected()) {
						casAction.saveBillInit(flag,new Request(CommonVars.getCurrUser()));//生成下年度的期初   （即本年度的年度结存）
					}

				} catch (Exception e) {
					CommonProgress.closeProgressDialog();
					e.printStackTrace();
					JOptionPane.showMessageDialog(PnWriteAccountYear.this,
							"生成数据失败：！" + e.getMessage(), "提示", 2);
					return;
				}
				CommonProgress.setMessage("抓取数据完毕，正保存记账年度.....");
			}
			
			
			CommonProgress.setMessage("正保存记账年度，准备重新加载服务器...");
			
			
			// 2.改变记帐年度
			fillData();
			PnWriteAccountYear.this.writeAccountYear = casAction
					.saveWriteAccountYear(
							new Request(CommonVars.getCurrUser()),
							PnWriteAccountYear.this.writeAccountYear);
			CasCommonVars
					.setWriteAccountYear(PnWriteAccountYear.this.writeAccountYear);
			CommonProgress.setMessage("正在重新加载服务器,请耐心等待一分钟...");
			
			// 重新加载服务器
			try {
				casAction.reloadSessionFactory(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(PnWriteAccountYear.this,
						"重新加载服务器成功！");
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(PnWriteAccountYear.this,
						"重新加载服务器失败！" + e.getMessage());
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * wss test测试重启功能
	 * 用于重启
	 * @author wss
	 */
//	private void test(){
//		CasAction casAction = (CasAction) CommonVars
//				.getApplicationContext().getBean("casAction");
//
//		// =========没有重启前抓取期初单数据
//		Integer item = (Integer) cbbYear.getSelectedItem();
//		int lastYearInt = CasCommonVars.getYearInt();
//		boolean isMakeCacheData = item.intValue() > lastYearInt;//是否是后一年
//		
//		//1.如果是后一年，则需生成下年度期初
//		if (isMakeCacheData) {
//			try {
//				CommonProgress.showProgressDialog();
//				CommonProgress.setMessage("系统正在生成期初数据，请稍候...");
//				//String taskId = CommonProgress.getExeTaskId();
//				if (cbInitBillMake.isSelected()) {
//					casAction.saveBillInit(new Request(CommonVars.getCurrUser()));//生成下年度的期初   （即本年度的年度结存）
//				}
//
//			} catch (Exception e) {
//				CommonProgress.closeProgressDialog();
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(PnWriteAccountYear.this,
//						"生成数据失败：！" + e.getMessage(), "提示", 2);
//				return;
//			}
//			CommonProgress.setMessage("抓取数据完毕，正保存记账年度.....");
//		}
//		
//		//2.改变记帐年度
//		fillData();
//		PnWriteAccountYear.this.writeAccountYear = casAction
//				.saveWriteAccountYear(new Request(CommonVars
//						.getCurrUser()),
//						PnWriteAccountYear.this.writeAccountYear);
//		CasCommonVars
//				.setWriteAccountYear(PnWriteAccountYear.this.writeAccountYear);
//		CommonProgress.setMessage("保存记帐完毕，" + (autoRestart == true ? "正在进行TOMCAT重启....":"您需要重启TOMCAT...."));
//		CommonProgress.closeProgressDialog();
//				
////		//3.重启服务器
////		if(autoRestart == true){
////			try {
////				Thread t = new RestartTomcat();
////				t.start();
////				t.join(3000);
////				//Thread.sleep(1000);
////				new CheckAutoUpgradeState(" 重启服务器 ").start();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
//		
//	}
	
	//wss:重启Tomcat线程
	class RestartTomcat extends Thread {

		public void run() {
			SystemAction systemAction = (SystemAction) CommonVars
						.getApplicationContext().getBean("systemAction");
			systemAction.restart(new Request(CommonVars.getCurrUser()));
		}
	}
	 
	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		this.writeAccountYear.setYear((Integer) this.cbbYear.getSelectedItem());
		this.writeAccountYear.setIsCleftCustomerStat(this.cbInitBillMake
				.isSelected());
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData(boolean afterSave) {
		if (!afterSave) {
			this.cbbYear.setSelectedItem(this.writeAccountYear.getYear());
			this.cbInitBillMake.setSelected(true);
		}
		setState();
		super.setContainerEnabled(this, false);
	}

	private int dataSate = DataState.BROWSE;

	private JLabel jLabel3 = null;
	private JCheckBox jCheckBox11 = null;
	private JButton btnSave = null;
	private JLabel jLabel4 = null;
	private JLabel lbMessage = null; // @jve:decl-index=0:visual-constraint="490,476"
//	private JLabel jLabel5 = null;
	private JButton btnBillCheck = null;
	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		btnEdit.setEnabled(dataSate == DataState.BROWSE);
		btnAvailability.setEnabled(dataSate != DataState.BROWSE);
		if (cbInitBillMake.isSelected()) {
			btnSave.setEnabled(true);
		}

	}
	
	
	
	/**
	 * 验证数据
	 */
//	private boolean vali() {
//		Integer item = (Integer) this.cbbYear.getSelectedItem();
//		String info = "";
//		if (item == null) {
//			info = "当前记帐年度不可为空!!!";
//			getLbMessage().setText(info);
//			JOptionPane.showMessageDialog(this,lbMessage , "提示",
//					JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//		int lastYearInt = CasCommonVars.getYearInt() + 1;
//		if (item.intValue() > lastYearInt - 1 && item.intValue() != lastYearInt) {
//			info = "记帐年度只能更改到当前记帐年度的后一年   ==  ["
//							+ lastYearInt + "]";
//			getLbMessage().setText(info);
//			JOptionPane.showMessageDialog(this, lbMessage, "提示", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//		if (item.intValue() == lastYearInt - 1) {
//			info = "记帐年度   == 当前记帐年度";
//			getLbMessage().setText(info);
//			JOptionPane.showMessageDialog(this, lbMessage, "提示",
//					JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//		if (item.intValue() != CasCommonVars.getYearInt()) {
//			info = "\n确定要更改当前的记帐年度吗? \n系统需重启TOMCAT使设置生效:";
//			if (item.intValue() > lastYearInt - 1) {
//				info += "\n\n(当重启服务器后,请务必手动生成期初.!!)";
//				;
//			} else {
//				info += "\n回退的变更,不需要生成期初.!!";
//				;
//			}
//
//			getLbMessage().setText(info);
//			Object[] options = {"自动重启","手动重启","取消"};
//			int option = JOptionPane.showOptionDialog(FmMain.getInstance(), 
//											lbMessage, 
//											"重要！", 
//											JOptionPane.YES_NO_CANCEL_OPTION, 
//											JOptionPane.WARNING_MESSAGE, 
//											null, 
//											options, 
//											options[2]);
//
//			if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
//				return false;
//			}else{
//				autoRestart = (option == JOptionPane.OK_OPTION ? true : false);
//				System.out.println(autoRestart);
//				return true;
//			}
//			
//		} else {
//			return false;
//		}
//	}

	/**
	 * 验证数据
	 */
	private boolean vali() {
		Integer item = (Integer) this.cbbYear.getSelectedItem();
		String info = "";
		if (item == null) {
			info = "当前记帐年度不可为空!!!";
			getLbMessage().setText(info);
			JOptionPane.showMessageDialog(this, lbMessage, "提示",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		int lastYearInt = CasCommonVars.getYearInt() + 1;
		if (item.intValue() > lastYearInt - 1 && item.intValue() != lastYearInt) {
			info = "记帐年度只能更改到当前记帐年度的后一年   ==  [" + lastYearInt + "]";
			getLbMessage().setText(info);
			JOptionPane.showMessageDialog(this, lbMessage, "提示",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (item.intValue() == lastYearInt - 1) {
			info = "记帐年度   == 当前记帐年度";
			getLbMessage().setText(info);
			JOptionPane.showMessageDialog(this, lbMessage, "提示",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (item.intValue() != CasCommonVars.getYearInt()) {
			info = "确定要更改当前的记帐年度吗?系统需重新加载使设置生效,\n";
			if (item.intValue() > lastYearInt - 1) {
				info += "(当重新加载后,请务必手动生成期初.)\n";
			} else {
				info += "回退的变更,不需要生成期初.\n";
			}
			info += "由于系统需重新加载使设置，所以请其他用户暂时退出系统，防止用户正编辑的数据丢失。";
			// if (JOptionPane.showConfirmDialog(FmMain.getInstance(), info,
			// "重要!!!", JOptionPane.YES_NO_OPTION,
			// JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
			// return false;
			// }
			// return true;
			// } else {
			// return false;
			// }
			// }
			getLbMessage().setText(info);
			// Object[] options = { "自动重启", "手动重启", "取消" };
			// int option = JOptionPane.showOptionDialog(FmMain.getInstance(),
			// lbMessage, "重要！", JOptionPane.YES_NO_CANCEL_OPTION,
			// JOptionPane.WARNING_MESSAGE, null, options, options[2]);
			//
			// if (option == JOptionPane.CANCEL_OPTION
			// || option == JOptionPane.CLOSED_OPTION) {
			// return false;
			// } else {
			// autoRestart = (option == JOptionPane.OK_OPTION ? true : false);
			// System.out.println(autoRestart);
			// return true;
			// }
			if (JOptionPane.showConfirmDialog(FmMain.getInstance(), info, "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	/**
	 * This method initializes jCheckBox11
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox11() {
		if (jCheckBox11 == null) {
			jCheckBox11 = new JCheckBox();
			jCheckBox11.setBounds(new Rectangle(86, 133, 147, 27));
			jCheckBox11.setText("年度期初单分客户生成");
			jCheckBox11.setVisible(false);
			jCheckBox11.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBox11.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (jCheckBox11.isSelected()) {
						cbInitBillMake.setSelected(false);
					}
					// if(!jCheckBox11.isSelected()){
					// jCheckBox1.setSelected(true);
					// }
				}

			});
		}
		return jCheckBox11;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(446, 405, 100, 26));
			btnSave.setText("生成期初");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// ==========生成期初单
					new MakeInitBillThread().start();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 生成期初线程
	 * @author Administrator
	 *
	 */
	class MakeInitBillThread extends Thread {
		public void run() {

			CasAction casAction = (CasAction) CommonVars
					.getApplicationContext().getBean("casAction");
			try {
				if (cbInitBillMake.isSelected()) {
					CommonProgress.showProgressDialog();
					CommonProgress.setMessage("开始保存期初单...");

					CommonProgress.setMessage("正在生成期初单据...");
					// 生成当年度期初单
					casAction
							.makeBillInit(new Request(CommonVars.getCurrUser()));

					CommonProgress.closeProgressDialog();
					
					JOptionPane.showMessageDialog(PnWriteAccountYear.this, 
							"生成期初完毕！", 
							"提示", 
							2);
					
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				throw new RuntimeException("保存期初数据失败", e1);
			} finally {
				CommonProgress.closeProgressDialog();
				PnWriteAccountYear.this.setContainerEnabled(
						PnWriteAccountYear.this, false);
				PnWriteAccountYear.this.dataSate = DataState.BROWSE;
				setState();
			}
			// CommonProgress.setMessage("保存记账年度完毕，等待服务器重启4分钟...");
		}
	}

	/**
	 * This method initializes lbMessage
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getLbMessage() {
		if (lbMessage == null) {
			lbMessage = new JLabel();
			lbMessage.setText("JLabel");
			lbMessage.setForeground(Color.red);
			lbMessage.setFont(new Font("Dialog", Font.BOLD, 14));
			lbMessage.setSize(new Dimension(264, 79));
		}
		return lbMessage;
	}

	/**
	 * This method initializes btnBillCheck	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBillCheck() {
		if (btnBillCheck == null) {
			btnBillCheck = new JButton();
			btnBillCheck.setBounds(new Rectangle(171, 405, 86, 26));
			btnBillCheck.setText("检查单据");
			String tips = "为了防止因单据资料有误而导致生成的期初单不正确，\n"
						+ "建议先检查单据。（建议直接到单据管理中心进行检查）";
			btnBillCheck.setToolTipText(tips);
			btnBillCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new CheckBillThread().start();
				}
			});
		}
		return btnBillCheck;
	}
	
	/**
	 * 检查部分线程
	 * @author wss
	 */
	class CheckBillThread extends Thread {
	
		public void run() {

			try {
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();
				
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在进行检查，请稍后...");
				
				List<BillDetail> listError = casAction.checkAllBillDetail(new Request(CommonVars.getCurrUser()));
				
				if (listError != null && listError.size() != 0) {
					CommonProgress.closeProgressDialog();
					DgBillCheckResult dg = new DgBillCheckResult();
					dg.setDataSource(listError);
					dg.setVisible(true);
				}else{
					CommonProgress.closeProgressDialog();

					info += " 检查完毕,没有错误的单据体！,耗时:" + (System.currentTimeMillis() - beginTime)
							+ " 毫秒 ";
					JOptionPane
							.showMessageDialog(PnWriteAccountYear.this, info, "提示", 2);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(PnWriteAccountYear.this, "检查失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();

			}
		}
	}
	
	

}
