package com.bestway.bcus.client.checkcancel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.FactoryStoryProduct;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgInputFactoryStoryMateriel extends JDialogBase {

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JList jlistBefore = null;

	private JList jlistAfter = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JPanel jPanel1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private List beforeDataList = null; // @jve:decl-index=0:

	private List afterDataList = null; // @jve:decl-index=0:

	private List defualtDataList = new ArrayList(); // @jve:decl-index=0:

	private JFileChooser jFileChooser = null; // @jve:decl-index=0:visual-constraint="568,16"

	private CheckCancelAction checkCancelAction = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgInputFactoryStoryMateriel() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(432, 317));
		this.setResizable(false);
		this.setContentPane(getJPanel1());
		this.setTitle("导入对应字段设置");

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(279, 31, 50, 19));
			jLabel1.setText("新顺序");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(84, 31, 50, 19));
			jLabel.setText("默认顺序");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(2, 2, 417, 202));
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"对应设置",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(59, 55, 107, 140));
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(256, 55, 105, 140));
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jlistBefore == null) {
			jlistBefore = new JList();
			// jlistBefore.setCellRenderer(new MyCellRenderer());
			jlistBefore.setListData(getListDate().toArray());
		}
		return jlistBefore;
	}

	private void changeDataList(List delList, List addList, int index) {
		addList.add(delList.get(index));
		delList.remove(index);
		jlistBefore.setListData(beforeDataList.toArray());
		jlistAfter.setListData(afterDataList.toArray());
		this.repaint();

	}

	private List getListDate() {
		beforeDataList = new ArrayList();
		afterDataList = new ArrayList();
		defualtDataList.add("料号(非空)");
		defualtDataList.add("名称(不限)");
		defualtDataList.add("规格(不限)");
		defualtDataList.add("数量(数字)");
		defualtDataList.add("单价(数字)");
		defualtDataList.add("总价(数字)");
		defualtDataList.add("毛重(数字)");
		defualtDataList.add("净重(数字)");
		defualtDataList.add("单位(不限)");
		// beforDataList.add("编码");

		beforeDataList.addAll(defualtDataList);
		return beforeDataList;
	}

	private Map getDifferentOder() {
		Map ordermap = new HashMap();
		for (int k = 0; k < defualtDataList.size(); k++) {
			String befstr = (String) defualtDataList.get(k);
			ordermap.put(new Integer(k), null);
			for (int m = 0; m < afterDataList.size(); m++) {
				String aftstr = (String) afterDataList.get(m);
				if (befstr.equals(aftstr)) {
					ordermap.put(new Integer(k), new Integer(m));
				}
			}
		}
		return ordermap;
	}

	// private List inputData() {
	// List resultList = new ArrayList();
	//		
	// try {
	// FileReader fr = new FileReader(f);
	// BufferedReader br = new BufferedReader(fr);
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// if (line.trim().equals("")) {
	// continue;
	// }
	// String[] strs = line.split(String
	// .valueOf((char) KeyEvent.VK_TAB));
	// FactoryStoryProduct fs = praseStr(strs);
	// if (fs == null) {
	// return new ArrayList();
	// }
	// resultList.add(fs);
	//
	// }
	// } catch (IOException ex) {
	// ex.printStackTrace();
	// return new ArrayList();
	// }
	// return resultList;
	// }

	private FactoryStoryProduct praseStr(String[] strs) {
		FactoryStoryProduct data = new FactoryStoryProduct();
		Map ordermap = getDifferentOder();
		try {
			if (afterDataList.size() != 0) {
				// if (afterDataList.size() != strs.length) {
				// JOptionPane.showMessageDialog(
				// DgInputFactoryStoryMateriel.this,
				// "对应字段个数与文本字段个数不同！", "错误！",
				// JOptionPane.ERROR_MESSAGE);
				// return null;
				// }
				for (int s = 0; s <= ordermap.size(); s++) {
					if (ordermap.get(s) != null) {
						int index = ((Integer) ordermap.get(s)).intValue();

						if (s == 0) {
							if (strs[index] == null
									|| strs[index].trim().equals("")) {
								CommonProgress.closeProgressDialog();
								JOptionPane.showMessageDialog(
										DgInputFactoryStoryMateriel.this,
										"对应字段料号不能为空！", "错误！",
										JOptionPane.ERROR_MESSAGE);
								return null;
							}
							data.setPtNo(strs[index].trim());
						} else if (s == 1 && index < strs.length) {
							data.setPtName(strs[index].trim());
						} else if (s == 2 && index < strs.length) {
							data.setPtSpec(strs[index].trim());
						} else if (s == 3 && index < strs.length) {
							data.setQuantity(Double.parseDouble(strs[index]
									.trim()));
						} else if (s == 4 && index < strs.length) {
							data.setUnitPrice(Double.parseDouble(strs[index]
									.trim()));
						} else if (s == 5 && index < strs.length) {
							data.setAmountPrice(Double.parseDouble(strs[index]
									.trim()));
						} else if (s == 6 && index < strs.length) {
							data.setGrossWeight(Double.parseDouble(strs[index]
									.trim()));
						} else if (s == 7 && index < strs.length) {
							data.setNetWeight(Double.parseDouble(strs[index]
									.trim()));
						} else if (s == 8 && index < strs.length) {
							data.setUnits(strs[index].trim());
						} else if (s == 9 && index < strs.length) {

						}
					}
				}
			} else {
				// if (beforeDataList.size() != strs.length) {
				// JOptionPane.showMessageDialog(
				// DgInputFactoryStoryMateriel.this,
				// "对应字段个数与文本字段个数不同！", "错误！",
				// JOptionPane.ERROR_MESSAGE);
				// return null;
				// }
				for (int s = 0; s <= defualtDataList.size(); s++) {
					if (s == 0) {
						if (strs[0] == null || strs[0].trim().equals("")) {
							CommonProgress.closeProgressDialog();
							JOptionPane.showMessageDialog(
									DgInputFactoryStoryMateriel.this,
									"对应字段料号不能为空！", "错误！",
									JOptionPane.ERROR_MESSAGE);
							return null;
						}
						data.setPtNo(strs[0]);
					} else if (s == 1 && s < strs.length) {
						data.setPtName(strs[s].trim());
					} else if (s == 2 && s < strs.length) {
						data.setPtSpec(strs[s].trim());
					} else if (s == 3 && s < strs.length) {
						data.setQuantity(Double.parseDouble(strs[s].trim()));
					} else if (s == 4 && s < strs.length) {
						data.setUnitPrice(Double.parseDouble(strs[s].trim()));
					} else if (s == 5 && s < strs.length) {
						data.setAmountPrice(Double.parseDouble(strs[s].trim()));
					} else if (s == 6 && s < strs.length) {
						data.setGrossWeight(Double.parseDouble(strs[s].trim()));
					} else if (s == 7 && s < strs.length) {
						data.setNetWeight(Double.parseDouble(strs[s].trim()));
					} else if (s == 8 && s < strs.length) {
						data.setUnits(strs[s].trim());
					} else if (s == 9 && s < strs.length) {

					}
				}
			}
		} catch (Exception ex) {
			CommonProgress.closeProgressDialog();
			ex.printStackTrace();
			JOptionPane.showMessageDialog(DgInputFactoryStoryMateriel.this,
					"其中有字段为字数，但文本所对应的不为数字！", "失败！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return data;
	}

	/**
	 * This method initializes jList1
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList1() {
		if (jlistAfter == null) {
			jlistAfter = new JList();
			// jlistAfter.setCellRenderer(new MyCellRenderer());
		}
		return jlistAfter;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(183, 69, 53, 20));
			jButton.setText("->");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jlistBefore.getSelectedValue() == null) {
						return;
					}
					changeDataList(beforeDataList, afterDataList, jlistBefore
							.getSelectedIndex());
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(183, 148, 53, 20));
			jButton1.setText("<-");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jButton1
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									if (jlistAfter.getSelectedValue() == null) {
										return;
									}
									changeDataList(afterDataList,
											beforeDataList, jlistAfter
													.getSelectedIndex());
								}
							});
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(18, 223, 351, 18));
			jLabel3.setText("如果料号在该表中存在,则数据也不会导进!");
			jLabel3.setForeground(Color.BLUE);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 206, 351, 18));
			jLabel2.setForeground(Color.BLUE);
			jLabel2.setText("如果料号在归并关系归并前成品中不存在,则数据不会导进!");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton3(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(263, 249, 63, 22));
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(103, 249, 63, 22));
			jButton3.setText("导入");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					int returnVal = getJFileChooser().showOpenDialog(
							DgInputFactoryStoryMateriel.this);
					if (returnVal != JFileChooser.APPROVE_OPTION) {
						return;
					}
					File file = getJFileChooser().getSelectedFile();
					InputDataThread inputDataThread = new InputDataThread();
					inputDataThread.setFile(file);
					inputDataThread.start();

				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jFileChooser
	 * 
	 * @return javax.swing.JFileChooser
	 */
	private JFileChooser getJFileChooser() {
		if (jFileChooser == null) {
			jFileChooser = new JFileChooser();
			jFileChooser.setSize(new Dimension(60, 34));
			jFileChooser
					.removeChoosableFileFilter(jFileChooser.getFileFilter());
			jFileChooser.setFileFilter(new TextFileFilter());
		}
		return jFileChooser;
	}

	class TextFileFilter extends FileFilter {
		public boolean accept(File f) {
			if (f.getName().endsWith(".txt")) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "请选择文本文件";
		}
	}

	class InputDataThread extends Thread {
		File file = null;

		List rlist = new ArrayList();

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正导入数据，请稍后...");
				FileReader fr = new FileReader(getFile());
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.trim().equals("")) {
						continue;
					}
					String[] strs = line.split(String
							.valueOf((char) KeyEvent.VK_TAB));
					FactoryStoryProduct fs = praseStr(strs);
					if (fs == null) {
						rlist = new ArrayList();
						return;
					}
					rlist.add(fs);
				}
			} catch (IOException ex) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgInputFactoryStoryMateriel.this,
						"导入数据失败：！" + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			if (rlist.size() != 0 && rlist.get(0) != null) {
				checkCancelAction.saveOrUpdateFactoryStoryProduct(new Request(
						CommonVars.getCurrUser()), rlist);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgInputFactoryStoryMateriel.this,
						"导入数据成功！", "提示！", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		public File getFile() {
			return file;
		}

		public void setFile(File f) {
			this.file = f;
		}
	}
} // @jve:decl-index=0:visual-constraint="114,30"
