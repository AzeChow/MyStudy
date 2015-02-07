/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExg;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExgResolve;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.manualdeclare.DgExpCustomsRecord;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.jptds.common.AppException;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmECSDeclarationCommInfoExg extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JButton btnCount = null;
	private JButton btnExport = null;
	private JLabel jLabel6 = null;
	private JPanel jPanel5 = null;
	private JTable tbBgExgToImg = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbBgExg = null;
	private JScrollPane jScrollPane2 = null;
	private JButton btnClose = null;
	private JTableListModel tableModelImg = null; // 料件
	private JTableListModel tableModelExg = null; // 成品
	private EmsAnalyHead head = null;
	private CheckCancelAction checkCancelAction = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	protected JTable jTable3;
	private JTabbedPane tabbedPane;
	private JButton btnHistroy = null;
	private JButton btnConvert = null;
	private JButton btnClear = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private ECSSection section = null; // @jve:decl-index=0:
	private JToolBar toolBar;
	private int index;
	private String version = null;
	private JPanel panel;
	private JLabel label;
	private JButton btnChoose;
	private JLabel label_1;
	private JTextField tfCanTime;
	private JLabel label_2;
	private JTextField tfSection;
	private JLabel label_3;
	private JTextField tfBeginDate;
	private JLabel label_4;
	private JTextField tfEndDate;
	private JLabel lbSeqNum;
	private JTextField tfSeqNum;
	private static final String[] linkedColNames = new String[] { "出口数量(A)",
			"转厂数量(B)", "退厂返工(C)", "返工复出(D)" };
	/**
	 * 分页菜单公共组件
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;
	private JPanel panel_1;

	public ECSSection getSection() {
		return section;
	}

	public void setSection(ECSSection section) {
		this.section = section;
		if (section != null) {
			tfCanTime.setText(section.getCancelOwnerHead().getCancelTimes());
			tfSection.setText(section.getVerificationNo() + "");
			tfBeginDate.setText(CommonUtils.getDate(section.getBeginDate(),
					"yyyy-MM-dd"));
			tfEndDate.setText(CommonUtils.getDate(section.getEndDate(),
					"yyyy-MM-dd"));
		}
	}

	/**
	 * This is the default constructor
	 */
	public FmECSDeclarationCommInfoExg() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkDeclarationCommInfoExg(new Request(CommonVars
				.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(924, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("成品统计/成品折料");
		List list = new ArrayList();
		// initTableExg(list);
		// initTableToImg(list);
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				pnCommonQueryPage.setInitState();
			}
		});
	}

	// 初始化成品统计表
	private JTableListModel initTableExg(List<?> list) {
		if (tableModelExg == null) {
			tableModelExg = new JTableListModel(tbBgExg, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> list = new ArrayList<JTableListColumn>();
							list.add(addColumn("账册号", "emsNo", 100));
							list.add(addColumn("备案序号", "seqNum", 50,Integer.class));
							list.add(addColumn("成品名称", "commName", 140));
							list.add(addColumn("型号规格", "commSpec", 140));
							list.add(addColumn("版本", "version", 40));
							list.add(addColumn("计量单位", "unit", 60));
							list.add(addColumn(linkedColNames[0],
									"directExportAmount", 90));
							list.add(addColumn(linkedColNames[1],
									"transferExportAmount", 90));
							list.add(addColumn(linkedColNames[2],
									"backFactoryReworkAmount", 90));
							list.add(addColumn(linkedColNames[3],
									"reworkExportAmount", 90));
							list.add(addColumn("总出口(A+B-C+D)",
									"totalExportAmount", 120));
							return list;
						}
					}, ListSelectionModel.SINGLE_SELECTION);
			tbBgExg.setModel(tableModelExg);
		} else {
			tableModelExg.setList(list);
		}
		for (int i = 7; i < 11; i++) {
			setColumnTooltip(tbBgExg, i, "双击‘" + tableModelExg.getColumnName(i)
					+ "’可关联【成品明细表】");
		}
		version = (String)tableModelExg.getValueAt(tableModelExg.getCurrentRows(), 5);
		return tableModelExg;
	}

	// 初始化成品折料表
	private JTableListModel initTableToImg(final List list) {
		tableModelImg = new JTableListModel(tbBgExgToImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// 账册编号 备案序号 版本号 成品名称 成品规格， 料件序号 料件名称 料件规格 计量单位 单耗 损耗
						// 总耗用 总出口
						list.add(addColumn("账册编号", "emsNo", 100));
						list.add(addColumn("成品序号", "ecsCustomsCountExg.seqNum", 50,Integer.class));
						list.add(addColumn(" 版本号", "ecsCustomsCountExg.version", 50));
						list.add(addColumn("成品名称", "ecsCustomsCountExg.commName", 140));
						list.add(addColumn("成品规格", "ecsCustomsCountExg.commSpec", 140));
						list.add(addColumn("料件序号", "seqNum", 50,Integer.class));
						list.add(addColumn("料件名称", "commName", 140));
						list.add(addColumn("型号规格", "commSpec", 140));
						list.add(addColumn("计量单位", "unit", 60));
						list.add(addColumn("单耗", "unitWaste", 60));
						list.add(addColumn("损耗", "waste", 90));
						list.add(addColumn("总耗用", "wasteAmount", 90));
						list.add(addColumn("总出口",
								"ecsCustomsCountExg.totalExportAmount", 90));
						return list;
					}
				});
		return tableModelImg;
	}

	private void setState(boolean enabled) {
		// this.btnHistroy.setEnabled(enabled);
		this.btnCount.setEnabled(enabled);
		this.btnConvert.setEnabled(enabled);
		this.btnExport.setEnabled(enabled);
		this.btnClear.setEnabled(enabled);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getPanel(), BorderLayout.NORTH);
			jContentPane.add(getJPanel5(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnCount
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCount() {
		if (btnCount == null) {
			btnCount = new JButton();
			btnCount.setText("1.计算");
			btnCount.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (section == null) {
						JOptionPane.showMessageDialog(
								FmECSDeclarationCommInfoExg.this,
								"请选择一个盘点核销批次！", "提示", 2);
						return;
					}
					Request request = new Request(CommonVars.getCurrUser());
					boolean isTrueExg = ecsCheckStockAction
							.isExistECSCountExgBySection(request, section);
					if (isTrueExg
							&& JOptionPane.showConfirmDialog(
									FmECSDeclarationCommInfoExg.this,
									"已经存在该批次的成品数据，确定删除重新统计吗？", "确认", 2) == 2) {
						return;
					}
					setState(false);
					new Jisuan().start();
				}
			});
		}
		return btnCount;
	}

	/**
	 * 成品折料
	 */
	private void resolve() {
		if (section == null) {
			JOptionPane.showMessageDialog(FmECSDeclarationCommInfoExg.this,
					"请选择一个盘点核销批次！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (ecsCheckStockAction.isExistECSCountExgResolveBySection(new Request(
				CommonVars.getCurrUser()), section)) {
			if (JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(
					FmECSDeclarationCommInfoExg.this,
					"该批次成品折料已经存在是否删除该批次重新折算数据？", "提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, new Object[] { "是", "否" }, "否")) {
				return;
			} else {
				ecsCheckStockAction.delectECSCustomsCountExgResolveBySection(
						new Request(CommonVars.getCurrUser()), section);
			}
		}
		initTableToImg(Collections.EMPTY_LIST);
		new AsynSwingWorker<List<ECSCustomsCountExgResolve>>() {
			long t = 0;

			protected List<ECSCustomsCountExgResolve> submit() {
				t = System.currentTimeMillis();
				setState(false);
				try {
					Request request = new Request(CommonVars.getCurrUser());
					request.setTaskId(CommonStepProgress.getExeTaskId());
					CommonStepProgress.showStepProgressDialog(request
							.getTaskId());
					CommonStepProgress.setStepMessage("系统正在发送折算请求，请稍后...");
					ecsCheckStockAction.convertCustomsCountExgToImg(request,
							section);
					// List customsconvertImgs =
					// if (customsconvertImgs.size()== 0) {
					// CommonProgress.closeProgressDialog();
					// JOptionPane.showMessageDialog(FmECSDeclarationCommInfoExg.this,"统计数据为空！",
					// "提示", 2);
					// }
					// return customsconvertImgs;
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(
							FmECSDeclarationCommInfoExg.this, "统计数据失败:"+ex.getMessage(),
							"提示", 2);
				} finally {

					setState(true);
					CommonStepProgress.closeStepProgressDialog();
				}
				return new ArrayList();
			}

			protected void success(List<ECSCustomsCountExgResolve> list) {
				// JOptionPane.showMessageDialog(FmECSDeclarationCommInfoExg.this,
				// "耗时："+(System.currentTimeMillis()-t)+"毫秒");
				// initTableToImg(list);
				pnCommonQueryPage.setInitState();
				tabbedPane.setSelectedIndex(1);
			}

			protected void errorHandler(RuntimeException e) {
				throw new AppException("统计失败：！" + e.getMessage(), e);
			};
		}.doWork();
	}

	class Jisuan extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(FmECSDeclarationCommInfoExg.this);
				CommonProgress.setMessage("系统正在统计资料，请稍后...");
				if (FmECSDeclarationCommInfoExg.this.tabbedPane
						.getSelectedIndex() == 0) { // 成品统计
					// 删除成品折料
					ecsCheckStockAction
							.delectECSCustomsCountExgResolveBySection(
									new Request(CommonVars.getCurrUser()),
									section);
					// 删除成品统计
					ecsCheckStockAction.delectECSCustomsCountExgBySection(
							new Request(CommonVars.getCurrUser()), section);
					ecsCheckStockAction.gainECSCustomsCountExg(new Request(
							CommonVars.getCurrUser()), section);
					List customsCountExgs = ecsCheckStockAction
							.findECSCustomsCountExgBySection(new Request(
									CommonVars.getCurrUser()), section, null);
					initTableExg(customsCountExgs);
					CommonProgress.closeProgressDialog();
					if (customsCountExgs.size() == 0) {
						JOptionPane.showMessageDialog(
								FmECSDeclarationCommInfoExg.this, "统计数据为空！",
								"提示", 2);
					}
				} else if (FmECSDeclarationCommInfoExg.this.tabbedPane
						.getSelectedIndex() == 1) { // 成品折料
					// 删除成品折料
					ecsCheckStockAction
							.delectECSCustomsCountExgResolveBySection(
									new Request(CommonVars.getCurrUser()),
									section);
					// 删除成品统计
					ecsCheckStockAction.delectECSCustomsCountExgBySection(
							new Request(CommonVars.getCurrUser()), section);
					ecsCheckStockAction.gainECSCustomsCountExg(new Request(
							CommonVars.getCurrUser()), section);
					// List customsCountExgs =
					// ecsCheckStockAction.findECSCustomsCountExgBySection(new
					// Request(CommonVars.getCurrUser()), section,null);
					// initTableExg(customsCountExgs);
					// initTableToImg(new ArrayList());
					pnCommonQueryPage.setInitState();
					CommonProgress.closeProgressDialog();
					// if (customsCountExgs.size()== 0) {
					// JOptionPane.showMessageDialog(FmECSDeclarationCommInfoExg.this,
					// "统计数据为空！", "提示", 2);
					// }
				}
				setState(true);
				tabbedPane.setSelectedIndex(0);
			} catch (Exception e) {
				setState(true);
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmECSDeclarationCommInfoExg.this,
						"统计失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	private List getConditions(int i) {
		List<Object> conditions = new Vector<Object>();
		if (i == 0) { // 料件
			conditions.add(new Condition("and", "(",
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.DIRECT_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.TRANSFER_FACTORY_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.BACK_MATERIEL_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.REMAIN_FORWARD_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.REMAIN_FORWARD_EXPORT, ")"));
		} else {
			conditions.add(new Condition("and", "(",
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.DIRECT_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.TRANSFER_FACTORY_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.BACK_FACTORY_REWORK, ")"));
		}
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", ">=", CommonVars
						.dateToStandDate(section.getBeginDate()), null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", "<=", CommonVars
						.dateToStandDate(section.getEndDate()), null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.company.id", "=", CommonVars
						.getCurrUser().getCompany().getId(), null));
		return conditions;
	}

	/**
	 * This method initializes btnExport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton();
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (index == 0) {
						tableModelExg.getMiSaveAllToExcel().doClick();
						;
					} else if (index == 1) {
						tableModelImg.getMiSaveAllToExcel().doClick();
						;
					}
				}
			});
			btnExport.setText("导出EXCEL");
		}
		return btnExport;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout(0, 0));
			jPanel5.add(getPanel_1(), BorderLayout.NORTH);
			jPanel5.add(getTabbedPane());
		}
		return jPanel5;
	}

	/**
	 * This method initializes tbBgExgToImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBgExgToImg() {
		if (tbBgExgToImg == null) {
			tbBgExgToImg = new JTable();
		}
		return tbBgExgToImg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbBgExgToImg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbBgExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBgExg() {
		if (tbBgExg == null) {
			tbBgExg = new JTable();
			tbBgExg.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int colIndex = tbBgExg.columnAtPoint(e.getPoint());
						if (colIndex < 0)
							return;
						int index = CommonUtils.indexOf(linkedColNames,
								tbBgExg.getColumnName(colIndex));
						if (index < 0)
							return;
						boolean impExpFlag = false;
						String impExpType = null;
						switch (index) {
						case 0:
							impExpType = String
									.valueOf(ImpExpType.DIRECT_EXPORT);
							break;
						case 1:
							impExpType = String
									.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT);
							break;
						case 2:
							impExpType = String
									.valueOf(ImpExpType.BACK_FACTORY_REWORK);
							// impExpFlag = true;
							break;
						case 3:
							impExpType = String
									.valueOf(ImpExpType.REWORK_EXPORT);
							break;
						}
						ECSCustomsCountExg exg = (ECSCustomsCountExg) tableModelExg
								.getCurrentRow();
						showECSDeclarationCommInfoExgx(exg.getSeqNum(),
								String.valueOf(exg.getVersion()), impExpType,
								exg.getSection(), impExpFlag);
					}
				}
			});
		}
		return tbBgExg;
	}

//	public void showCustomsRecord(Integer seqNum, String version,
//			String impExpType, ECSSection section, boolean impExpFlag) {
//		DgExpCustomsRecord dg = new DgExpCustomsRecord();
//		int iseffect = CustomsDeclarationState.EFFECTIVED;
//		dg.showData(seqNum, null, impExpType, section.getBeginDate(),
//				section.getEndDate(), version, Boolean.TRUE, iseffect, false,
//				null, impExpFlag);
//		ShowFormControl.refreshInteralForm(dg);
//	}
	
	public void showECSDeclarationCommInfoExgx(Integer seqNum, String version,
			String impExpType, ECSSection section, boolean impExpFlag){
		FmECSDeclarationCommInfoExgx fm = new FmECSDeclarationCommInfoExgx();
		fm.showData(section, seqNum,version);
		ShowFormControl.refreshInteralForm(fm);
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbBgExg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmECSDeclarationCommInfoExg.this.dispose();
				}
			});
		}
		return btnClose;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("成品统计", null, getJScrollPane2(), null);
			tabbedPane.addTab("成品折料", null, getJScrollPane1(), null);
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JTabbedPane tab = (JTabbedPane) e.getSource();
					pnCommonQueryPage.setFirstHasDataInit(true);
					pnCommonQueryPage.setInitState();
					if (tab.getSelectedIndex() == 0) {
						index = 0;
						// setState();
					} else if (tab.getSelectedIndex() == 1) {
						index = 1;
						// setState();
					}
				}
			});
		}
		return tabbedPane;
	}

	/**
	 * This method initializes btnHistroy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton();
			btnHistroy.setText("查看历史记录");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (section == null) {
						JOptionPane.showMessageDialog(
								FmECSDeclarationCommInfoExg.this,
								"请选择一个盘点核销批次！", "提示", 2);
						return;
					}
					Request request = new Request(CommonVars.getCurrUser());
					new AsynSwingWorker<List>() {
						Request request = new Request(CommonVars.getCurrUser());

						protected List submit() {
							CommonProgress
									.showProgressDialog(FmECSDeclarationCommInfoExg.this);
							CommonProgress.setMessage("系统正在查询数据，请稍后...");
							setState(false);
							try {
								Integer seqNum = null;
								if (!tfSeqNum.getText().trim().isEmpty()) {
									seqNum = NumberUtils.toInt(tfSeqNum
											.getText().trim());
								}
								if (index == 0)
									return ecsCheckStockAction
											.findECSCustomsCountExgBySection(
													request, section, seqNum);
								else if (index == 1)
									return ecsCheckStockAction
											.findECSCustomsCountExgResolveBySection(
													request, section, seqNum);
							} finally {
								CommonProgress.closeProgressDialog();
								setState(true);
							}
							return null;
						}

						protected void success(List list) {
							if (index == 0)
								initTableExg(list);
							else if (index == 1)
								tableModelImg.setList(list);
						};
					}.doWork();

				}
			});

		}
		return btnHistroy;
	}

	/**
	 * This method initializes btnConvert
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConvert() {
		if (btnConvert == null) {
			btnConvert = new JButton();
			btnConvert.setText("2.成品折算料件");
			btnConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resolve();
				}
			});
		}
		return btnConvert;
	}

	/**
	 * This method initializes btnClear
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton();
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (section == null) {
						JOptionPane.showMessageDialog(getContentPane(),
								"请选择盘点核查批次！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int index = tabbedPane.getSelectedIndex();
					if (index == 0) {
						if (JOptionPane.YES_OPTION == JOptionPane
								.showOptionDialog(
										FmECSDeclarationCommInfoExg.this,
										"确定删除本批次成品统计及相关的成品折料数据？", "提示",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										new Object[] { "是", "否" }, "否")) {
							new AsynSwingWorker<Object>() {
								Request request = new Request(CommonVars
										.getCurrUser());

								protected Object submit() {
									ecsCheckStockAction
											.delectECSCustomsCountExgResolveBySection(
													request, section);
									ecsCheckStockAction
											.delectECSCustomsCountExgBySection(
													request, section);
									return null;
								}

								protected void success(Object o) {
									tableModelImg
											.setList(Collections.EMPTY_LIST);
									tableModelExg
											.setList(Collections.EMPTY_LIST);
								};
							}.doWork();
						}
					} else if (index == 1) {
						if (JOptionPane.YES_OPTION == JOptionPane
								.showOptionDialog(
										FmECSDeclarationCommInfoExg.this,
										"确定删除本批次的成品折料数据？", "提示",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										new Object[] { "是", "否" }, "否")) {
							new AsynSwingWorker<Object>() {
								Request request = new Request(CommonVars
										.getCurrUser());

								protected Object submit() {
									ecsCheckStockAction
											.delectECSCustomsCountExgResolveBySection(
													request, section);
									return null;
								}

								protected void success(Object o) {
									tableModelImg
											.setList(Collections.EMPTY_LIST);
								};
							}.doWork();
						}
					}

				}
			});
			btnClear.setText("清空当前数据");
		}
		return btnClear;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			// toolBar.add(getLbSeqNum());
			// toolBar.add(getTfSeqNum());
			// toolBar.add(getBtnHistroy());
			toolBar.add(getBtnCount());
			toolBar.add(getBtnConvert());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClear());
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}

	/**
	 * 为表格的某个列设置提示（不包含表头）
	 * 
	 * @param table
	 * @param colIndex
	 * @param tipText
	 */
	private void setColumnTooltip(JTable table, int colIndex, String tipText) {
		TableColumn column = table.getColumnModel().getColumn(colIndex);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText(tipText);
		column.setCellRenderer(renderer);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(1, 30));
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			panel.add(getLabel());
			panel.add(getBtnChoose());
			panel.add(getLabel_1());
			panel.add(getTfCanTime());
			panel.add(getLabel_2());
			panel.add(getTfSection());
			panel.add(getLabel_3());
			panel.add(getTfBeginDate());
			panel.add(getLabel_4());
			panel.add(getTfEndDate());
		}
		return panel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("盘点核查批次选择：");
		}
		return label;
	}

	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("...");
			btnChoose.setPreferredSize(new Dimension(29, 23));
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dg = new DgECSSectionSel();
					dg.setVisible(true);
					if (dg.isOk()) { // 选中批次后确定
						setSection(dg.getSection());// 拿到批次
					}
				}
			});
		}
		return btnChoose;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("核销报核次数:");
		}
		return label_1;
	}

	private JTextField getTfCanTime() {
		if (tfCanTime == null) {
			tfCanTime = new JTextField();
			tfCanTime.setPreferredSize(new Dimension(44, 21));
			tfCanTime.setEditable(false);
		}
		return tfCanTime;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("盘点核查批次:");
		}
		return label_2;
	}

	private JTextField getTfSection() {
		if (tfSection == null) {
			tfSection = new JTextField();
			tfSection.setPreferredSize(new Dimension(44, 21));
			tfSection.setEditable(false);
		}
		return tfSection;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("盘点开始日期:");
		}
		return label_3;
	}

	private JTextField getTfBeginDate() {
		if (tfBeginDate == null) {
			tfBeginDate = new JTextField();
			tfBeginDate.setPreferredSize(new Dimension(78, 21));
			tfBeginDate.setEnabled(false);
		}
		return tfBeginDate;
	}

	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel();
			label_4.setText("盘点结束日期:");
		}
		return label_4;
	}

	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setPreferredSize(new Dimension(78, 21));
			tfEndDate.setEnabled(false);
		}
		return tfEndDate;
	}

	private JLabel getLbSeqNum() {
		if (lbSeqNum == null) {
			lbSeqNum = new JLabel("备案序号：");
		}
		return lbSeqNum;
	}

	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80, 25));
			tfSeqNum.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					char c = str.charAt(str.length() - 1);
					if (c >= '0' && c <= '9') {
						super.insertString(offs, str, a);
					}
				}
			});
		}
		return tfSeqNum;
	}

	/**
	 * 根据查询条件显示数据
	 * 
	 * @param section
	 * @param mergerNo
	 */
	public void showData(ECSSection section, Integer mergerNo) {
		setSection(section);
		tabbedPane.setSelectedIndex(1);
		if (mergerNo != null) {
			pnCommonQueryPage.getCbbQueryField().setSelectedIndex(5);
			pnCommonQueryPage.getTfQueryValue().setText(
					String.valueOf(mergerNo));
			pnCommonQueryPage.getRbPrecision().setSelected(true);
		}
		pnCommonQueryPage.getBtnQuery().doClick();
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {
				private static final long serialVersionUID = 1L;

				@Override
				public JTableListModel initTable(List dataSource) {
					JTableListModel tableModel = null;
					if (tabbedPane.getSelectedIndex() == 0) {
						tableModel = FmECSDeclarationCommInfoExg.this
								.initTableExg(dataSource);
					} else if (tabbedPane.getSelectedIndex() == 1) {
						tableModel = FmECSDeclarationCommInfoExg.this
								.initTableToImg(dataSource);
					}
					return tableModel;
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					if (section != null) {
						if (tabbedPane.getSelectedIndex() == 0) {
							return ecsCheckStockAction
									.findPageECSCountExgBySection(new Request(
											CommonVars.getCurrUser()), section,
											index, length, property, value,
											isLike);
						} else if (tabbedPane.getSelectedIndex() == 1) {
							System.out.println("------------isLike:" + isLike);
							return ecsCheckStockAction
									.findPageECSCountExgResolveBySection(
											new Request(CommonVars
													.getCurrUser()), section,
											index, length, property, value,
											isLike);
						}
					}
					return new Vector();
				}

				@Override
				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike) {
					if (section != null) {
						if (tabbedPane.getSelectedIndex() == 0) {
							return (long) ecsCheckStockAction
									.countECSCustomsCountExgByECSSection(
											new Request(CommonVars
													.getCurrUser()), section,
											property, value, isLike);
						} else if (tabbedPane.getSelectedIndex() == 1) {
							return (long) ecsCheckStockAction
									.countECSCustomsCountExgResolveByECSSection(
											new Request(CommonVars
													.getCurrUser()), section,
											property, value, isLike);
						}
					}
					return new Long(0);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setSize(500, 100);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getToolBar(), BorderLayout.NORTH);
			panel_1.add(getPnCommonQueryPage());
		}
		return panel_1;
	}
}
