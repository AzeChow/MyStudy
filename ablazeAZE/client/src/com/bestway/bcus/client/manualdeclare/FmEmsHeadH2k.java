/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.manualdeclare.report.ManualdeclareReportVars;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 电子帐册管理---电子帐册表头
 * 
 * 刘民添加部分注释 修改时间: 2009-09-07
 * 
 * @author Administrator // change the template for this generated type comment
 * 
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmEmsHeadH2k extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnAmend = null;

	private JButton btnDelete = null;

	private JButton btnChange = null;

	private JButton btnCustomDeclare = null;

	private JButton btnReceipt = null;

	private JButton btnChecked = null;

	private JButton btnExit = null;

	private JTable tbEmsHeadH2k = null;

	private JScrollPane jScrollPane = null;

	private JButton btnReportPrint = null;

	private ManualDeclareAction manualDeclearAction = null;

	private MessageAction messageAction = null;

	// private SystemAction systemAction = null;

	private JTableListModel tableModel = null;
	/** 电子帐册表头 */
	private EmsHeadH2k emsHeadH2k = null;
	/** 是否变更 */
	private boolean isChange = false;

	private JButton btnBeforehandChange = null;

	private JLabel lbProgress = null;

	private JButton btnPrint = null;

	private JButton btnBrowse = null;

	private JButton btnUpdate = null;

	private JButton btnMove = null;

	private JButton btnUpdating = null;

	private JButton btnInitialize = null;

	private JPopupMenu miPopupMenuPrint = null;

	private JMenuItem miAllEms = null;

	private JMenuItem miImgChangeForCount = null;

	private JMenuItem miImgChangeForEmsNo = null;

	private JMenuItem miExgChangeListForCount = null;

	private JMenuItem miExgChangeListForEmsNo = null;

	private JMenuItem miBomChangeForCount = null;

	private JMenuItem miBomChangeForEmsNo = null;

	private JMenuItem miBomChangeForTime = null;

	private JMenuItem miEms = null;

	private JButton btnUpdateInnerMerge = null;
	private JButton btnCompareMessage;

	/**
	 * This is the default constructor
	 */
	public FmEmsHeadH2k() {
		super();

		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		initialize();
		// initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(894, 408);
		this.setTitle("电子帐册-表头");
		this.setContentPane(getJContentPane());
		List dataSource = null;
		dataSource = manualDeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser()));
		if (dataSource != null && !dataSource.isEmpty()) {
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}
		setState();
		if (CommonVars.getEmsFrom().equals("1")) {
			lbProgress.setText("   注：物料来源于归并关系(正在执行)");
		} else if (CommonVars.getEmsFrom().equals("2")) {
			lbProgress.setText("   注：物料来源于归并关系(申请备案)");
		} else if (CommonVars.getEmsFrom().equals("3")) {
			lbProgress.setText("   注：物料来源于企业物料归并关系");
		}
	}

	/**
	 * This method initializes miImgChangeForEmsNo[按备案序号] 料件变更清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImgChangeForEmsNo() {
		if (miImgChangeForEmsNo == null) {
			miImgChangeForEmsNo = new JMenuItem();
			miImgChangeForEmsNo.setText("[按备案序号] 料件变更清单");
			miImgChangeForEmsNo.setSize(new Dimension(100, 30));
			miImgChangeForEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintNo dg = new DgEmsPrintNo();
								dg.setShow(false);
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								String selectPrintModel = dg.getSelectPrintString();
								String paramterField = "备案序号";
								String paramterValue = "";
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								List dataSource = new ArrayList();
								if ("1".equals(selectPrintModel)) {
									int beginNo = dg.getBeginNo();
									int endNo = dg.getEndNo();
									if (beginNo <= -1 && endNo <= -1) {
										return;
									}
									if (beginNo > 0 && endNo > 0) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kImgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														endNo, null);
										paramterValue = "从 " + beginNo + " 到 "
												+ endNo;
									} else if (beginNo <= 0 && endNo > 0) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kImgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, null,
														endNo, null);
										paramterValue = "从 0 到 " + endNo;
									} else if (beginNo > 0 && endNo <= -1) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kImgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														null, null);
										paramterValue = "从 " + beginNo
												+ " 到最后 ";
									}
								} else if ("2".equals(selectPrintModel)){
									Integer[] seqNumArray = dg.getSeqNumArray();
									dataSource = manualDeclearAction
											.findEmsHeadH2kImgChange(
													new Request(CommonVars
															.getCurrUser()),
													emsHeadH2k, null, null,
													seqNumArray);
									paramterValue = dg.getSeqNum();
								}
								CustomReportDataSource ds = new CustomReportDataSource(
										dataSource);

								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeMateriel.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("tradeName", emsHeadH2k
										.getTradeName() == null ? ""
										: emsHeadH2k.getTradeName());
								parameters.put("tradeCode", emsHeadH2k
										.getTradeCode() == null ? ""
										: emsHeadH2k.getTradeCode());
								parameters.put("machCode", emsHeadH2k
										.getMachCode() == null ? ""
										: emsHeadH2k.getMachCode());
								parameters.put("machName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("copEmsNo", emsHeadH2k
										.getCopEmsNo() == null ? ""
										: emsHeadH2k.getCopEmsNo());
								parameters.put("sancEmsNo", emsHeadH2k
										.getSancEmsNo() == null ? ""
										: emsHeadH2k.getSancEmsNo());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miImgChangeForEmsNo;
	}

	/**
	 * This method initializes miAllEms 完整电子帐册
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAllEms() {
		if (miAllEms == null) {
			miAllEms = new JMenuItem();
			miAllEms.setText("完整电子帐册");
			miAllEms.setSize(new Dimension(100, 30));
			miAllEms.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
							.getCurrentRow();
					if (emsHeadH2k != null) {
						list.add(emsHeadH2k);
					} else {
						JOptionPane
								.showMessageDialog(FmEmsHeadH2k.this,
										"请选择电子帐册表头记录！", "提示",
										JOptionPane.YES_NO_OPTION);
						return;
					}
					printAllEms(emsHeadH2k);
				}
			});
		}
		return miAllEms;
	}

	/**
	 * This method initializes miEms 加工贸易电子帐册
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEms() {
		if (miEms == null) {
			miEms = new JMenuItem();
			miEms.setText("加工贸易电子帐册");
			miEms.setSize(new Dimension(100, 30));
			miEms.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
							.getCurrentRow();
					if (emsHeadH2k != null) {
						list.add(emsHeadH2k);
					} else {
						JOptionPane
								.showMessageDialog(FmEmsHeadH2k.this,
										"请选择电子帐册表头记录！", "提示",
										JOptionPane.YES_NO_OPTION);
						return;
					}
					try {
						CustomReportDataSource ds = new CustomReportDataSource(
								list);
						InputStream masterReportStream = FmEmsHeadH2k.class
								.getResourceAsStream("report/EmsCover.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("declareType", emsHeadH2k
								.getDeclareType());
						parameters
								.put("runState", emsHeadH2k.getDeclareState());
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return miEms;
	}

	/**
	 * This method initializes miBomChangeForTime[按变更时间] 成品单耗清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBomChangeForTime() {
		if (miBomChangeForTime == null) {
			miBomChangeForTime = new JMenuItem();
			miBomChangeForTime.setText("[按变更时间] 成品单耗清单");
			miBomChangeForTime.setSize(new Dimension(100, 30));
			miBomChangeForTime
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintDate dg = new DgEmsPrintDate();
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								Date beginDate = dg.getBeginDate();
								Date endDate = dg.getEndDate();
								boolean isDeclared = dg.isDeclared();
								String paramterField = "变更时间";
								//
								// 查询语句
								//
								List ls = new ArrayList();
								List emsHeadH2kVersionList = new ArrayList();
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"yyyy-MM-dd");
								String begin = (beginDate == null ? "空"
										: dateFormat.format(beginDate));
								String end = (endDate == null ? "空"
										: dateFormat.format(endDate));
								String paramterValue = "从 " + begin + " 到 "
										+ end;
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								// if (beginDate != null && endDate != null) {
								// begin = dateFormat.format(beginDate);
								// end = dateFormat.format(endDate);
								// paramterValue = "从 " + begin + " 到 " + end;
								//
								// } else if (beginDate == null && endDate !=
								// null) {
								// begin = dateFormat.format(beginDate);
								// end = dateFormat.format(endDate);
								// paramterValue = "从 空 " + " 到 " + end;
								//
								// } else if (beginDate != null && endDate ==
								// null) {
								// begin = dateFormat.format(beginDate);
								// end = dateFormat.format(endDate);
								// paramterValue = "从 " + begin + " 到 空";
								//
								// } else if (beginDate == null && endDate ==
								// null) {
								// begin = dateFormat.format(beginDate);
								// end = dateFormat.format(endDate);
								// paramterValue = "从 空" + " 到 空";
								//
								// }
								List<List> dataSource = manualDeclearAction
										.findEmsHeadH2kBomByDate(new Request(
												CommonVars.getCurrUser()),
												emsHeadH2k, beginDate, endDate,
												isDeclared);
								emsHeadH2kVersionList = (List) dataSource
										.get(0);
								ls = (List) dataSource.get(1);
								ManualdeclareReportVars
										.setEmsHeadH2kVersionList(emsHeadH2kVersionList);
								CustomReportDataSource ds = new CustomReportDataSource(
										ls);
								ds.setMaximumFractionDigits(9);
								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeBomByDate.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("companyName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("count", ls.size());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miBomChangeForTime;
	}

	/**
	 * This method initializes miBomChangeForEmsNo[按备案序号] 成品单耗清单"
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBomChangeForEmsNo() {
		if (miBomChangeForEmsNo == null) {
			miBomChangeForEmsNo = new JMenuItem();
			miBomChangeForEmsNo.setText("[按备案序号] 成品单耗清单");
			miBomChangeForEmsNo.setSize(new Dimension(100, 30));
			miBomChangeForEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintNo dg = new DgEmsPrintNo();
								dg.setShow(true);
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								String selectPrintModel = dg.getSelectPrintString();
								List ls = new ArrayList();
								List emsHeadH2kVersionList = new ArrayList();
								String paramterField = "备案序号";
								String paramterValue = "";
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								if ("1".equals(selectPrintModel)) {
									//连续打印
									int beginNo = dg.getBeginNo();
									int endNo = dg.getEndNo();
									if (beginNo <= -1 && endNo <= -1) {
										return;
									}

									if (beginNo > 0 && endNo > 0) {
										paramterValue = "从 " + beginNo + " 到 "
												+ endNo;
										List<List> dataSource = manualDeclearAction
												.findEmsHeadH2kBomChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														endNo, null);
										emsHeadH2kVersionList = (List) dataSource
												.get(0);
										ls = (List) dataSource.get(1);
									} else if (beginNo <= 0 && endNo > 0) {
										paramterValue = "从 0 到 " + endNo;
										List<List> dataSource = manualDeclearAction
												.findEmsHeadH2kBomChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, null,
														endNo, null);
										emsHeadH2kVersionList = (List) dataSource
												.get(0);
										ls = (List) dataSource.get(1);
									} else if (beginNo > 0 && endNo == -1) {
										paramterValue = "从 " + beginNo
												+ " 到最后 ";
										List<List> dataSource = manualDeclearAction
												.findEmsHeadH2kBomChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														null, null);
										emsHeadH2kVersionList = (List) dataSource
												.get(0);
										ls = (List) dataSource.get(1);
									}
								} else if ("2".equals(selectPrintModel))  {
									//间隔打印全部版本
									paramterValue = dg.getSeqNum();
									Integer[] seqNumArray = dg.getSeqNumArray();
									List<List> dataSource = manualDeclearAction
											.findEmsHeadH2kBomChange(
													new Request(CommonVars
															.getCurrUser()),
													emsHeadH2k, null, null,
													seqNumArray);
									emsHeadH2kVersionList = (List) dataSource
											.get(0);
									ls = (List) dataSource.get(1);
								}else if ("3".equals(selectPrintModel)) {
									//间隔打印部分版本
									paramterValue = dg.getAllSeqNum();
									Map<Integer, List<Integer>> mapAllSeqNum = dg.getAllSeqNumArray();
									List<List> dataSource = manualDeclearAction
											.findEmsHeadH2kBomSingleVersionChange(
													new Request(CommonVars
															.getCurrUser()),
													emsHeadH2k,mapAllSeqNum);
									emsHeadH2kVersionList = (List) dataSource
											.get(0);
									ls = (List) dataSource.get(1);
								}
								
								ManualdeclareReportVars
										.setEmsHeadH2kVersionList(emsHeadH2kVersionList);
								CustomReportDataSource ds = new CustomReportDataSource(
										ls);
								ds.setMaximumFractionDigits(9);
								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeBom.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("companyName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("count", ls.size());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miBomChangeForEmsNo;
	}

	/**
	 * This method initializes miBomChangeForCount[按变更次数] 成品单耗清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBomChangeForCount() {
		if (miBomChangeForCount == null) {
			miBomChangeForCount = new JMenuItem();
			miBomChangeForCount.setText("[按变更次数] 成品单耗清单");
			miBomChangeForCount.setSize(new Dimension(100, 30));
			miBomChangeForCount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintTime dg = new DgEmsPrintTime();
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								int time = dg.getTime();
								if (time <= -1) {
									return;
								}
								String paramterField = "变更次数";
								String paramterValue = String.valueOf(time);
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								//
								// 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合）
								// list(1) 单耗记录（集合）
								//
								List<List> dataSource = manualDeclearAction
										.findEmsHeadH2kBomChange(new Request(
												CommonVars.getCurrUser()),
												emsHeadH2k, time);

								ManualdeclareReportVars
										.setEmsHeadH2kVersionList(dataSource
												.get(0));
								CustomReportDataSource ds = new CustomReportDataSource(
										dataSource.get(1));
								ds.setMaximumFractionDigits(9);
								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeBom.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("companyName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("count", dataSource.size());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miBomChangeForCount;
	}

	/**
	 * This method initializes miExgChangeListForEmsNo[按备案序号] 成品变更清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExgChangeListForEmsNo() {
		if (miExgChangeListForEmsNo == null) {
			miExgChangeListForEmsNo = new JMenuItem();
			miExgChangeListForEmsNo.setText("[按备案序号] 成品变更清单");
			miExgChangeListForEmsNo.setSize(new Dimension(100, 30));
			miExgChangeListForEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintNo dg = new DgEmsPrintNo();
								dg.setShow(false);
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								String selectPrintModel = dg.getSelectPrintString();
								String paramterField = "备案序号";
								String paramterValue = "";
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								List dataSource = new ArrayList();
								if ("1".equals(selectPrintModel)) {
									int beginNo = dg.getBeginNo();
									int endNo = dg.getEndNo();
									if (beginNo <= -1 && endNo <= -1) {
										return;
									}
									if (beginNo > 0 && endNo > 0) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kExgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														endNo, null);
										paramterValue = "从 " + beginNo + " 到 "
												+ endNo;
									} else if (beginNo <= 0 && endNo > 0) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kExgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, null,
														endNo, null);
										paramterValue = "从 0 到 " + endNo;
									} else if (beginNo > 0 && endNo <= -1) {
										dataSource = manualDeclearAction
												.findEmsHeadH2kExgChange(
														new Request(CommonVars
																.getCurrUser()),
														emsHeadH2k, beginNo,
														null, null);
										paramterValue = "从 " + beginNo
												+ " 到最后 ";
									}
								} else if ("2".equals(selectPrintModel)) {
									Integer[] seqNumArray = dg.getSeqNumArray();
									dataSource = manualDeclearAction
											.findEmsHeadH2kExgChange(
													new Request(CommonVars
															.getCurrUser()),
													emsHeadH2k, null, null,
													seqNumArray);
									paramterValue = dg.getSeqNum();
								}
								CustomReportDataSource ds = new CustomReportDataSource(
										dataSource);

								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeProduct.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("tradeName", emsHeadH2k
										.getTradeName() == null ? ""
										: emsHeadH2k.getTradeName());
								parameters.put("tradeCode", emsHeadH2k
										.getTradeCode() == null ? ""
										: emsHeadH2k.getTradeCode());
								parameters.put("machCode", emsHeadH2k
										.getMachCode() == null ? ""
										: emsHeadH2k.getMachCode());
								parameters.put("machName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("copEmsNo", emsHeadH2k
										.getCopEmsNo() == null ? ""
										: emsHeadH2k.getCopEmsNo());
								parameters.put("sancEmsNo", emsHeadH2k
										.getSancEmsNo() == null ? ""
										: emsHeadH2k.getSancEmsNo());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miExgChangeListForEmsNo;
	}

	/**
	 * This method initializes miExgChangeListForCount[按变更次数] 成品变更清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExgChangeList() {
		if (miExgChangeListForCount == null) {
			miExgChangeListForCount = new JMenuItem();
			miExgChangeListForCount.setText("[按变更次数] 成品变更清单");
			miExgChangeListForCount.setSize(new Dimension(100, 30));
			miExgChangeListForCount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintTime dg = new DgEmsPrintTime();
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								int time = dg.getTime();
								if (time <= -1) {
									return;
								}
								String paramterField = "变更次数";
								String paramterValue = String.valueOf(time);
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								//
								// 查询语句
								//
								List dataSource = manualDeclearAction
										.findEmsHeadH2kExgChange(new Request(
												CommonVars.getCurrUser()),
												emsHeadH2k, time);
								CustomReportDataSource ds = new CustomReportDataSource(
										dataSource);

								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeProduct.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("tradeName", emsHeadH2k
										.getTradeName() == null ? ""
										: emsHeadH2k.getTradeName());
								parameters.put("tradeCode", emsHeadH2k
										.getTradeCode() == null ? ""
										: emsHeadH2k.getTradeCode());
								parameters.put("machCode", emsHeadH2k
										.getMachCode() == null ? ""
										: emsHeadH2k.getMachCode());
								parameters.put("machName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("copEmsNo", emsHeadH2k
										.getCopEmsNo() == null ? ""
										: emsHeadH2k.getCopEmsNo());
								parameters.put("sancEmsNo", emsHeadH2k
										.getSancEmsNo() == null ? ""
										: emsHeadH2k.getSancEmsNo());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miExgChangeListForCount;
	}

	/**
	 * This method initializes miImgChangeForCount[按变更次数] 料件变更清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImgChangeForCount() {
		if (miImgChangeForCount == null) {
			miImgChangeForCount = new JMenuItem();
			miImgChangeForCount.setText("[按变更次数] 料件变更清单");
			miImgChangeForCount.setSize(new Dimension(100, 30));
			miImgChangeForCount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgEmsPrintTime dg = new DgEmsPrintTime();
								dg.setVisible(true);
								if (!dg.isOk()) {
									return;
								}
								int time = dg.getTime();
								if (time <= -1) {
									return;
								}
								String paramterField = "变更次数";
								String paramterValue = String.valueOf(time);
								List list = new ArrayList();
								EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
										.getCurrentRow();
								if (emsHeadH2k != null) {
									list.add(emsHeadH2k);
								} else {
									JOptionPane.showMessageDialog(
											FmEmsHeadH2k.this, "请选择电子帐册表头记录！",
											"提示", JOptionPane.YES_NO_OPTION);
									return;
								}
								//
								// 查询语句
								//
								List dataSource = manualDeclearAction
										.findEmsHeadH2kImgChange(new Request(
												CommonVars.getCurrUser()),
												emsHeadH2k, time);

								CustomReportDataSource ds = new CustomReportDataSource(
										dataSource);

								InputStream masterReportStream = FmEmsHeadH2k.class
										.getResourceAsStream("report/EmsChangeMateriel.jasper");
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("paramterField", paramterField);
								parameters.put("paramterValue", paramterValue);
								parameters.put("emsNo",
										emsHeadH2k.getEmsNo() == null ? ""
												: emsHeadH2k.getEmsNo());
								parameters.put("tradeName", emsHeadH2k
										.getTradeName() == null ? ""
										: emsHeadH2k.getTradeName());
								parameters.put("tradeCode", emsHeadH2k
										.getTradeCode() == null ? ""
										: emsHeadH2k.getTradeCode());
								parameters.put("machCode", emsHeadH2k
										.getMachCode() == null ? ""
										: emsHeadH2k.getMachCode());
								parameters.put("machName", emsHeadH2k
										.getMachName() == null ? ""
										: emsHeadH2k.getMachName());
								parameters.put("copEmsNo", emsHeadH2k
										.getCopEmsNo() == null ? ""
										: emsHeadH2k.getCopEmsNo());
								parameters.put("sancEmsNo", emsHeadH2k
										.getSancEmsNo() == null ? ""
										: emsHeadH2k.getSancEmsNo());
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return miImgChangeForCount;
	}

	/**
	 * This method initializes miPopupMenuPrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JPopupMenu getJPopupMenuPrint() {
		if (miPopupMenuPrint == null) {
			miPopupMenuPrint = new JPopupMenu();
			miPopupMenuPrint.setSize(new Dimension(110, 36));
			miPopupMenuPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			miPopupMenuPrint.add(getMiAllEms());
			miPopupMenuPrint.add(getMiImgChangeForCount());
			miPopupMenuPrint.add(getMiImgChangeForEmsNo());
			miPopupMenuPrint.add(getMiExgChangeList());
			miPopupMenuPrint.add(getMiExgChangeListForEmsNo());
			miPopupMenuPrint.add(getMiBomChangeForCount());
			miPopupMenuPrint.add(getMiBomChangeForEmsNo());
			miPopupMenuPrint.add(getMiBomChangeForTime());
			miPopupMenuPrint.add(getMiEms());
		}
		return miPopupMenuPrint;
	}

	/**
	 * This method initializes tableModel
	 * 
	 * @param list
	 *            初始化电子帐册管理表头
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(tbEmsHeadH2k, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60,
								Integer.class));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 100));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		tbEmsHeadH2k.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmEmsHeadH2k.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmEmsHeadH2k.this.setChange(true);
						} else if (value.equals("3")) {
							returnValue = "预变更";
							FmEmsHeadH2k.this.setChange(true);
						}
						return returnValue;
					}
				});
		tbEmsHeadH2k.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmEmsHeadH2k.this.isChange)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3")) {
							returnValue = "正在执行";
						} else if (value.equals("4")) {
							returnValue = "正在变更";
						}
						return returnValue;
					}
				});
		tbEmsHeadH2k.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
						else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	// private JPanel getJPanel() {
	// if (jPanel == null) {
	// jPanel = new JPanel();
	// jPanel.setLayout(null);
	// // jPanel.add(getCbbPrintParam(), null);
	// jPanel.add(getBtnPrint(), null);
	// jPanel.add(getJButton9(), null);
	// jPanel.add(getJLabel(), null);
	// }
	// return jPanel;
	// }

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new java.awt.Rectangle(136, 3, 65, 24));
			btnPrint.setPreferredSize(new Dimension(60, 30));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenuPrint()
							.show(btnPrint, 0, btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnBeforehandChange());
			jToolBar.add(getBtnCustomDeclare());
			jToolBar.add(getBtnReceipt());
			jToolBar.add(getBtnCompareMessage());
			jToolBar.add(getBtnReportPrint());
			jToolBar.add(getBtnMove());
			jToolBar.add(getBtnUpdating());
			jToolBar.add(getBtnInitialize());
			jToolBar.add(getBtnChecked());
			jToolBar.add(getBtnUpdateInnerMerge());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
			jToolBar.add(getBtnUpdate());
			jToolBar.add(getJLabel());
			// jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes lbProgress物料来源归并关系（正在执行）
	 * 
	 * @return avax.swing.JLabel
	 */

	private JLabel getJLabel() {
		if (lbProgress == null) {
			lbProgress = new JLabel();
			lbProgress.setText("物料来源归并关系（正在执行）");
			lbProgress.setForeground(new java.awt.Color(0, 102, 51));
			lbProgress.setBounds(new Rectangle(207, -3, 260, 32));
			lbProgress.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					12));
		}
		return lbProgress;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					String emsFrom = CommonVars.getEmsFrom();
					if (emsFrom.equals("1")) {
						list = manualDeclearAction
								.findEmsEdiMergerHead(new Request(CommonVars
										.getCurrUser()));
						if ((list.size() == 1 && ((EmsEdiMergerHead) list
								.get(0)).getDeclareState().equals(
								DeclareState.PROCESS_EXE))
								|| list.size() == 2) {
							EmsHeadH2k emsHeadH2k = manualDeclearAction
									.emsHeadH2kAdd(new Request(CommonVars
											.getCurrUser()));
							tableModel.addRow(emsHeadH2k);
							setState();
						} else {
							JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
									"归并关系不在《正在执行》状态，不能备案电子帐册", "提示！", 0);
							return;
						}
					} else {
						EmsHeadH2k emsHeadH2k = manualDeclearAction
								.emsHeadH2kAdd(new Request(CommonVars
										.getCurrUser()));
						tableModel.addRow(emsHeadH2k);
						setState();
					}
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes btnAmend
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnAmend == null) {
			btnAmend = new JButton();
			btnAmend.setText("修改");
			btnAmend.setPreferredSize(new Dimension(60, 30));
			btnAmend.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return btnAmend;
	}

	/**
	 * 
	 * This method initializes btnBrowse（浏览按钮）
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.setPreferredSize(new Dimension(60, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
								"请选择你将要浏览的记录", "提示！", 0);
						return;
					}
					DgEmsHeadH2k dgEmsHeadH2k = new DgEmsHeadH2k();
					dgEmsHeadH2k.setTableModel(tableModel);
					dgEmsHeadH2k.setVisible(true);
				}
			});

		}
		return btnBrowse;
	}

	/**
	 * 修改记录
	 */
	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgEmsHeadH2k dgEmsHeadH2k = new DgEmsHeadH2k();
		dgEmsHeadH2k.setChange(((EmsHeadH2k) tableModel.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsHeadH2k.setTableModel(tableModel);
		dgEmsHeadH2k.setVisible(true);
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsHeadH2k.this,
							"是否要删除该帐册记录？\n请注意：其下的成品料件以及单耗将一并被删除！", "确认", 0) == 0) {
						EmsHeadH2k emsEdi = (EmsHeadH2k) tableModel
								.getCurrentRow();
						manualDeclearAction.deleteEmsHeadH2kImgExg(new Request(
								CommonVars.getCurrUser()), emsEdi);
						manualDeclearAction.deleteEmsHeadH2k(new Request(
								CommonVars.getCurrUser()), emsEdi);
						tableModel.deleteRow(emsEdi);
					}
					setState();
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnChange
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.setPreferredSize(new Dimension(60, 30));
			btnChange.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (exitPrepareChange()) {
						if (JOptionPane.showConfirmDialog(FmEmsHeadH2k.this,
								"已经存在预变更，是否将预变更更换正式变更", "确认", 0) == 0) {
							changeChange();
						}
					} else {
						new Emschange().start();
					}
				}
			});

		}
		return btnChange;
	}

	/**
	 * 处理变更资料多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class Emschange extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在变更资料，请稍后...");
				EmsHeadH2k emsHead = (EmsHeadH2k) manualDeclearAction
						.findEmsHeadH2k(new Request(CommonVars.getCurrUser()))
						.get(0);
				EmsHeadH2k emsHeadH2k = manualDeclearAction.emsHeadH2kChange(
						new Request(CommonVars.getCurrUser()), "2", emsHead);
				tableModel.addRow(emsHeadH2k);
				setState();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "变更失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * This method initializes btnCustomDeclare
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCustomDeclare() {
		if (btnCustomDeclare == null) {
			btnCustomDeclare = new JButton();
			btnCustomDeclare.setText("海关申报");
			btnCustomDeclare.setPreferredSize(new Dimension(60, 30));
			btnCustomDeclare
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmEmsHeadH2k.this, "请选择你将要申报的记录",
										"提示！", 0);
								return;
							}
							EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel
									.getCurrentRow();
							EmsHeadH2k newEmsHeadH2k = manualDeclearAction.findEmsHeadH2kById(new Request(
									CommonVars.getCurrUser()), emsHeadH2k.getId());
							
							if (newEmsHeadH2k.getDeclareState()
									.equals(DeclareState.WAIT_EAA)) {
								JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
										"审批状态为“等待审批”，不允许再次申报", "提示！", 0);
								return;
							}
							EmsHeadH2k emsHeadValidate = (EmsHeadH2k) tableModel
									.getCurrentRow();

							if (emsHeadValidate.getLevyKind() == null) {
								JOptionPane.showMessageDialog(
										FmEmsHeadH2k.this, "征免性质不能为空", "警告",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							if (emsHeadValidate.getMachiningType() == null) {
								JOptionPane.showMessageDialog(
										FmEmsHeadH2k.this, "加工种类不能为空", "警告",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							// 检查成品修改标志与单耗修改标志是否一致
							String checkModifyMarkMsg = "<html><body>成品序号";
							checkModifyMarkMsg += manualDeclearAction
									.checkEmsExgBomModifyMarkIsUnit(
											new Request(CommonVars
													.getCurrUser()),
											emsHeadValidate);
							checkModifyMarkMsg += "<br/>修改标志与成品单耗【修改标志】不一致，确定继续申报吗?</body></html>";
							if (!checkModifyMarkMsg
									.equals("<html><body>成品序号<br/>修改标志与成品单耗【修改标志】不一致，确定继续申报吗?</body></html>")) {
								if (JOptionPane.showConfirmDialog(
										FmEmsHeadH2k.this, checkModifyMarkMsg,
										"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
							}
							if (JOptionPane.showConfirmDialog(
									FmEmsHeadH2k.this, "是否确定要将【电子帐册】向海关申报吗？",
									"确认", 0) == 0) {
								new Chelonian().start();

							}

						}
					});

		}
		return btnCustomDeclare;
	}

	/**
	 * 处理海关申报多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class Chelonian extends Thread {
		public void run() {
			try {
				emsHeadH2k = (EmsHeadH2k) tableModel.getCurrentRow();
				
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在进行海关申报，请稍后...");
				String messageName = null;
				Date now = new Date();
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String defaultDate = bartDateFormat.format(now);
				
				// String message = manualDeclearAction
				// .checkEmsHeadH2kUnitWasteAdd(new Request(CommonVars
				// .getCurrUser()), emsHeadH2k);
				// if (!"".equals(message)) {
				// CommonProgress.closeProgressDialog();
				// if (JOptionPane.showConfirmDialog(FmEmsHeadH2k.this,
				// message, "您确定要向海关申报吗？", JOptionPane.YES_NO_OPTION) !=
				// JOptionPane.YES_OPTION) {
				// return;
				// }
				// }
				
				
				
				
				emsHeadH2k.setDeclareDate(java.sql.Date.valueOf(defaultDate));// 申报日期
				emsHeadH2k.setDeclareTime(CommonVars.dateToTime(now)); // 申报时间
				emsHeadH2k = manualDeclearAction.saveEmsHeadH2k(new Request(
						CommonVars.getCurrUser()), emsHeadH2k);
				tableModel.updateRow(emsHeadH2k);
				List list = CustomBaseList.getInstance().getGbtobigs();
				emsHeadH2k = (EmsHeadH2k) tableModel.getCurrentRow();
				if (emsHeadH2k.getDeclareType().equals(DelcareType.APPLICATION)) {
					messageName = messageAction.exportMessage(new Request(
							CommonVars.getCurrUser()), emsHeadH2k, 1, list)[0];
					messageAction.saveMessageQuery(new Request(CommonVars
							.getCurrUser()), MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_H2K, DelcareType.APPLICATION,
							messageName, emsHeadH2k.getCopEmsNo(), "EMS213", 0);
				} else if (emsHeadH2k.getDeclareType().equals(
						DelcareType.MODIFY)) {
					messageName = messageAction.exportMessage(new Request(
							CommonVars.getCurrUser()), emsHeadH2k, 2, list)[0];
					messageAction.saveMessageQuery(new Request(CommonVars
							.getCurrUser()), MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_H2K, DelcareType.MODIFY,
							messageName, emsHeadH2k.getCopEmsNo(), "EMS223", 0);
				}
				emsHeadH2k.setDeclareState(DeclareState.WAIT_EAA); // 审批状态 1
				// 1，申请备案
				// 2，等待审批
				// 3，正在执行
				emsHeadH2k.setCheckMark("1"); // 审批标志
				emsHeadH2k = manualDeclearAction.saveEmsHeadH2k(new Request(
						CommonVars.getCurrUser()), emsHeadH2k);
				tableModel.updateRow(emsHeadH2k);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
						"报文已经生成，位置在中间服务器的：" + messageName, "报文已生成", 1);
				setState();

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "海关申报失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * This method initializes btnReceipt
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnReceipt() {
		if (btnReceipt == null) {
			btnReceipt = new JButton();
			btnReceipt.setText("回执处理");
			btnReceipt.setPreferredSize(new Dimension(60, 30));
			btnReceipt.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
					dgProcessMessage.setType("EmsHeadH2k");
					dgProcessMessage.setVisible(true);

					List dataSource = manualDeclearAction
							.findEmsHeadH2k(new Request(CommonVars
									.getCurrUser()));
					initTable(dataSource);
					setState();
				}
			});

		}
		return btnReceipt;
	}

	/**
	 * 
	 * This method initializes btnChecked
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnChecked() {
		if (btnChecked == null) {
			btnChecked = new JButton();
			btnChecked.setText("检查");
			btnChecked.setPreferredSize(new Dimension(60, 30));
			btnChecked.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					emsHeadH2k = (EmsHeadH2k) tableModel.getCurrentRow();
					String message = manualDeclearAction
							.checkEmsHeadH2kUnitWasteAdd(new Request(CommonVars
									.getCurrUser()), emsHeadH2k);
					if (!"".equals(message)) {
						JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
								message, "提示", 2);
					} else {
						JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
								"数据检查正常,单耗累加值不大于1!", "提示", 2);
					}
				}
			});

		}
		return btnChecked;
	}

//	/**
//	 * 修改帐册表头申报状态
//	 * 
//	 * @param emsHeadH2k
//	 */
//	private void turning(EmsHeadH2k emsHeadH2k) {
//		if (emsHeadH2k.getDeclareType().equals(DelcareType.MODIFY)) {
//			List list = manualDeclearAction.findEmsHeadH2k(new Request(
//					CommonVars.getCurrUser()));
//			for (int i = 0; i < list.size(); i++) {
//				if (((EmsHeadH2k) list.get(i)).getDeclareType().equals(
//						DelcareType.APPLICATION)) {
//					EmsHeadH2k emsHead = (EmsHeadH2k) list.get(i);
//					emsHead.setHistoryState(new Boolean(true));
//					emsHead = manualDeclearAction.saveEmsHeadH2k(new Request(
//							CommonVars.getCurrUser()), emsHead);
//					tableModel.deleteRow(emsHead);
//				}
//			}
//			emsHeadH2k.setDeclareType(DelcareType.APPLICATION);
//		}
//		emsHeadH2k.setDeclareState(DeclareState.PROCESS_EXE); // 审批状态
//	}

	/**
	 * 
	 * This method initializes btnExit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmEmsHeadH2k.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbEmsHeadH2k == null) {
			tbEmsHeadH2k = new JTable();
			tbEmsHeadH2k.setRowHeight(25);
			tbEmsHeadH2k.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;
							setState();
						}
					});
			tbEmsHeadH2k.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});
			tbEmsHeadH2k.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (!CommonVars.isManager()) {
						return;
					}
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						EmsHeadH2k h2k = (EmsHeadH2k) tableModel
								.getCurrentRow();
						if (h2k.getDeclareState()
								.equals(DeclareState.APPLY_POR)) {
							h2k.setDeclareState(DeclareState.WAIT_EAA);
						} else if (h2k.getDeclareState().equals(
								DeclareState.WAIT_EAA)) {
							h2k.setDeclareState(DeclareState.PROCESS_EXE);
						} else if (h2k.getDeclareState().equals(
								DeclareState.PROCESS_EXE)) {
							h2k.setDeclareState(DeclareState.APPLY_POR);
						}
						h2k = manualDeclearAction.saveEmsHeadH2k(new Request(
								CommonVars.getCurrUser()), h2k);
						tableModel.updateRow(h2k);
					}
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_D) {
						btnDelete.setVisible(true);
					}
				}
			});
		}
		return tbEmsHeadH2k;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes btnReportPrint
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnReportPrint() {
		if (btnReportPrint == null) {
			btnReportPrint = new JButton();
			btnReportPrint.setVisible(false);
			btnReportPrint.setText("打印");
		}
		return btnReportPrint;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * 设置控件的状态
	 */
	private void setState() {
		btnAdd.setEnabled(tableModel.getSize() == 0);// 新增
		btnAmend.setEnabled(isDeclareState());// 修改
		btnDelete.setEnabled(isDeclareState());// 删除
		btnChange
				.setEnabled((isChangeState() && isPROCESS_EXE())
						|| (exitPrepareChange() && isPROCESS_EXE() && !isChangeStateAfter()));// 变更
		btnCustomDeclare.setEnabled(!isPROCESS_EXE() && !isPrepareChange());
		btnChecked.setEnabled(!isPROCESS_EXE() && !isPrepareChange());
		btnReceipt.setEnabled((tableModel.getCurrentRow() != null));// 回执处理
		boolean isSend = EmsEdiMergerClientLogic.getIsEmsSend();
		btnChange.setVisible(!isSend);

		if (isSend) { // 分批发送
			btnDelete.setEnabled((tableModel.getCurrentRow() != null)
					&& ((EmsHeadH2k) tableModel.getCurrentRow())
							.getDeclareDate() == null);
		}
		EmsHeadH2k head = (EmsHeadH2k) tableModel.getCurrentRow();
		if (head != null) {
			if (head.getDeclareState().equals("1")
					|| head.getDeclareState().equals("4")) {
				btnUpdateInnerMerge.setEnabled(true);
			} else {
				btnUpdateInnerMerge.setEnabled(false);
			}
		}
	}

	/**
	 * 判断申报状态是否为正在执行（3）
	 * 
	 * @return
	 */
	private boolean isPrepareChange() {
		if (tableModel.getCurrentRow() != null
				&& ((EmsHeadH2k) tableModel.getCurrentRow()).getDeclareType()
						.equals("3")) {
			return true;
		}
		return false;
	}

	/**
	 * 查找电子帐册表头信息的记录数
	 * 
	 * @return
	 */
	private boolean isChangeState() {
		List list = null;
		list = manualDeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser()));
		if (list.size() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 查找电子帐册表头信息的记录个数
	 * 
	 * @return
	 */
	private boolean isChangeStateAfter() {
		List list = null;
		list = manualDeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser()));
		if (list.size() == 3) {
			return true;
		}
		return false;
	}

	/**
	 * 判断审批状态为：申请备案（1）
	 * 
	 * @return
	 */
	private boolean isDeclareState() { // 审批状态为：1
		if (tableModel.getCurrentRow() != null
				&& !((EmsHeadH2k) tableModel.getCurrentRow()).getDeclareState()
						.equals(DeclareState.PROCESS_EXE)
				&& !((EmsHeadH2k) tableModel.getCurrentRow()).getDeclareState()
						.equals(DeclareState.WAIT_EAA)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断审批状态为：正在执行（3）
	 * 
	 * @return
	 */
	private boolean isPROCESS_EXE() {
		if (tableModel.getCurrentRow() != null
				&& ((EmsHeadH2k) tableModel.getCurrentRow()).getDeclareState()
						.equals(DeclareState.PROCESS_EXE)) {
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnBeforehandChange
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBeforehandChange() {
		if (btnBeforehandChange == null) {
			btnBeforehandChange = new JButton();
			btnBeforehandChange.setText("预变更");
			btnBeforehandChange.setVisible(false);
			btnBeforehandChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							EmsHeadH2k emsHead = null;
							if (isChangeState()) {
								// 电子帐册变更
								emsHead = (EmsHeadH2k) manualDeclearAction
										.findEmsHeadH2k(
												new Request(CommonVars
														.getCurrUser())).get(0);
							} else {
								emsHead = (EmsHeadH2k) tableModel
										.getCurrentRow();
							}
							EmsHeadH2k emsHeadH2k = manualDeclearAction
									.emsHeadH2kChange(new Request(CommonVars
											.getCurrUser()), "3", emsHead);
							tableModel.addRow(emsHeadH2k);
							setState();
						}
					});
		}
		return btnBeforehandChange;
	}

	/**
	 * 查找电子帐册表头信息
	 * 
	 * @return
	 */
	private boolean exitPrepareChange() {
		List dataSource = null;
		dataSource = manualDeclearAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser()));
		for (int i = 0; i < dataSource.size(); i++) {
			EmsHeadH2k emsH2k = (EmsHeadH2k) dataSource.get(i);
			if (emsH2k.getDeclareType().equals("3")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查找电子帐册表头信息
	 */
	private void changeChange() {
		manualDeclearAction.changeChange(new Request(CommonVars.getCurrUser()));
		List dataSource = manualDeclearAction.findEmsHeadH2k(new Request(
				CommonVars.getCurrUser()));
		initTable(dataSource);
	}

	/**
	 * 初始化组件
	 * 
	 */
	// private void initUIComponents() {
	// // 初始化打印选项
	// this.cbbPrintParam.removeAllItems();
	// this.cbbPrintParam.addItem(" 打印完整电子帐册");
	// this.cbbPrintParam.addItem(" [按变更次数] 料件变更清单");
	// this.cbbPrintParam.addItem(" [按备案序号] 料件变更清单");
	// this.cbbPrintParam.addItem(" [按变更次数] 成品变更清单");
	// this.cbbPrintParam.addItem(" [按备案序号] 成品变更清单");
	// this.cbbPrintParam.addItem(" [按变更次数] 成品单耗清单");
	// this.cbbPrintParam.addItem(" [按备案序号] 成品单耗清单");
	// this.cbbPrintParam.addItem(" [按变更时间] 成品单耗清单");
	// this.cbbPrintParam.addItem(" 打印加工贸易电子帐册");
	// this.cbbPrintParam.setMaximumRowCount(8);
	// this.cbbPrintParam.setUI(new CustomBaseComboBoxUI(180));
	// cbbPrintParam.setSelectedIndex(-1);
	// }
//	/**
//	 * 打印报表
//	 * 
//	 */
//	private void printReport() {
//		if (this.cbbPrintParam.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(null, "请选择要打印的项目!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}
//		List list = new ArrayList();
//		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) tableModel.getCurrentRow();
//		if (emsHeadH2k != null) {
//			list.add(emsHeadH2k);
//		} else {
//			JOptionPane.showMessageDialog(this, "请选择电子帐册表头记录！", "提示",
//					JOptionPane.YES_NO_OPTION);
//			return;
//		}
//		// btnPrint.setEnabled(false);
//		switch (this.cbbPrintParam.getSelectedIndex()) {
//		case 0:// 打印完整电子帐册
//			printAllEms(emsHeadH2k);
//			break;
//		case 1:// [按变更次数] 料件变更清单
//			try {
//				DgEmsPrintTime dg = new DgEmsPrintTime();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				int time = dg.getTime();
//				if (time <= -1) {
//					return;
//				}
//				String paramterField = "变更次数";
//				String paramterValue = String.valueOf(time);
//				//
//				// 查询语句
//				//
//				List dataSource = manualDeclearAction
//						.findEmsHeadH2kImgChange(new Request(CommonVars
//								.getCurrUser()), emsHeadH2k, time);
//
//				CustomReportDataSource ds = new CustomReportDataSource(
//						dataSource);
//
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeMateriel.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("tradeName",
//						emsHeadH2k.getTradeName() == null ? "" : emsHeadH2k
//								.getTradeName());
//				parameters.put("tradeCode",
//						emsHeadH2k.getTradeCode() == null ? "" : emsHeadH2k
//								.getTradeCode());
//				parameters.put("machCode",
//						emsHeadH2k.getMachCode() == null ? "" : emsHeadH2k
//								.getMachCode());
//				parameters.put("machName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("copEmsNo",
//						emsHeadH2k.getCopEmsNo() == null ? "" : emsHeadH2k
//								.getCopEmsNo());
//				parameters.put("sancEmsNo",
//						emsHeadH2k.getSancEmsNo() == null ? "" : emsHeadH2k
//								.getSancEmsNo());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 2:// [按备案序号] 料件变更清单
//			try {
//				DgEmsPrintNo dg = new DgEmsPrintNo();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				boolean isSequencePrint = dg.isSequencePrint();
//				String paramterField = "备案序号";
//				String paramterValue = "";
//				List dataSource = new ArrayList();
//				if (isSequencePrint) {
//					int beginNo = dg.getBeginNo();
//					int endNo = dg.getEndNo();
//					if (beginNo <= -1 && endNo <= -1) {
//						return;
//					}
//					if (beginNo > 0 && endNo > 0) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kImgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										endNo, null);
//						paramterValue = "从 " + beginNo + " 到 " + endNo;
//					} else if (beginNo <= 0 && endNo > 0) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kImgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, null,
//										endNo, null);
//						paramterValue = "从 0 到 " + endNo;
//					} else if (beginNo > 0 && endNo <= -1) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kImgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										null, null);
//						paramterValue = "从 " + beginNo + " 到最后 ";
//					}
//				} else {
//					Integer[] seqNumArray = dg.getSeqNumArray();
//					dataSource = manualDeclearAction.findEmsHeadH2kImgChange(
//							new Request(CommonVars.getCurrUser()), emsHeadH2k,
//							null, null, seqNumArray);
//					paramterValue = dg.getSeqNum();
//				}
//				CustomReportDataSource ds = new CustomReportDataSource(
//						dataSource);
//
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeMateriel.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("tradeName",
//						emsHeadH2k.getTradeName() == null ? "" : emsHeadH2k
//								.getTradeName());
//				parameters.put("tradeCode",
//						emsHeadH2k.getTradeCode() == null ? "" : emsHeadH2k
//								.getTradeCode());
//				parameters.put("machCode",
//						emsHeadH2k.getMachCode() == null ? "" : emsHeadH2k
//								.getMachCode());
//				parameters.put("machName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("copEmsNo",
//						emsHeadH2k.getCopEmsNo() == null ? "" : emsHeadH2k
//								.getCopEmsNo());
//				parameters.put("sancEmsNo",
//						emsHeadH2k.getSancEmsNo() == null ? "" : emsHeadH2k
//								.getSancEmsNo());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 3:// [按变更次数] 成品变更清单
//			try {
//				DgEmsPrintTime dg = new DgEmsPrintTime();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				int time = dg.getTime();
//				if (time <= -1) {
//					return;
//				}
//				String paramterField = "变更次数";
//				String paramterValue = String.valueOf(time);
//				//
//				// 查询语句
//				//
//				List dataSource = manualDeclearAction
//						.findEmsHeadH2kExgChange(new Request(CommonVars
//								.getCurrUser()), emsHeadH2k, time);
//				CustomReportDataSource ds = new CustomReportDataSource(
//						dataSource);
//
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeProduct.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("tradeName",
//						emsHeadH2k.getTradeName() == null ? "" : emsHeadH2k
//								.getTradeName());
//				parameters.put("tradeCode",
//						emsHeadH2k.getTradeCode() == null ? "" : emsHeadH2k
//								.getTradeCode());
//				parameters.put("machCode",
//						emsHeadH2k.getMachCode() == null ? "" : emsHeadH2k
//								.getMachCode());
//				parameters.put("machName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("copEmsNo",
//						emsHeadH2k.getCopEmsNo() == null ? "" : emsHeadH2k
//								.getCopEmsNo());
//				parameters.put("sancEmsNo",
//						emsHeadH2k.getSancEmsNo() == null ? "" : emsHeadH2k
//								.getSancEmsNo());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 4:// [按备案序号] 成品变更清单
//			try {
//				DgEmsPrintNo dg = new DgEmsPrintNo();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				boolean isSequencePrint = dg.isSequencePrint();
//				String paramterField = "备案序号";
//				String paramterValue = "";
//				List dataSource = new ArrayList();
//				if (isSequencePrint) {
//					int beginNo = dg.getBeginNo();
//					int endNo = dg.getEndNo();
//					if (beginNo <= -1 && endNo <= -1) {
//						return;
//					}
//					if (beginNo > 0 && endNo > 0) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kExgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										endNo, null);
//						paramterValue = "从 " + beginNo + " 到 " + endNo;
//					} else if (beginNo <= 0 && endNo > 0) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kExgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, null,
//										endNo, null);
//						paramterValue = "从 0 到 " + endNo;
//					} else if (beginNo > 0 && endNo <= -1) {
//						dataSource = manualDeclearAction
//								.findEmsHeadH2kExgChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										null, null);
//						paramterValue = "从 " + beginNo + " 到最后 ";
//					}
//				} else {
//					Integer[] seqNumArray = dg.getSeqNumArray();
//					dataSource = manualDeclearAction.findEmsHeadH2kExgChange(
//							new Request(CommonVars.getCurrUser()), emsHeadH2k,
//							null, null, seqNumArray);
//					paramterValue = dg.getSeqNum();
//				}
//				CustomReportDataSource ds = new CustomReportDataSource(
//						dataSource);
//
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeProduct.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("tradeName",
//						emsHeadH2k.getTradeName() == null ? "" : emsHeadH2k
//								.getTradeName());
//				parameters.put("tradeCode",
//						emsHeadH2k.getTradeCode() == null ? "" : emsHeadH2k
//								.getTradeCode());
//				parameters.put("machCode",
//						emsHeadH2k.getMachCode() == null ? "" : emsHeadH2k
//								.getMachCode());
//				parameters.put("machName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("copEmsNo",
//						emsHeadH2k.getCopEmsNo() == null ? "" : emsHeadH2k
//								.getCopEmsNo());
//				parameters.put("sancEmsNo",
//						emsHeadH2k.getSancEmsNo() == null ? "" : emsHeadH2k
//								.getSancEmsNo());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 5:// [按变更次数] 成品单耗清单
//			try {
//				DgEmsPrintTime dg = new DgEmsPrintTime();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				int time = dg.getTime();
//				if (time <= -1) {
//					return;
//				}
//				String paramterField = "变更次数";
//				String paramterValue = String.valueOf(time);
//				//
//				// 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
//				//
//				List<List> dataSource = manualDeclearAction
//						.findEmsHeadH2kBomChange(new Request(CommonVars
//								.getCurrUser()), emsHeadH2k, time);
//
//				ManualdeclareReportVars.setEmsHeadH2kVersionList(dataSource
//						.get(0));
//				CustomReportDataSource ds = new CustomReportDataSource(
//						dataSource.get(1));
//				ds.setMaximumFractionDigits(9);
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeBom.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("companyName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("count", dataSource.size());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 6:// [按备案序号] 成品单耗清单
//			try {
//				DgEmsPrintNo dg = new DgEmsPrintNo();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				boolean isSequencePrint = dg.isSequencePrint();
//				List ls = new ArrayList();
//				List emsHeadH2kVersionList = new ArrayList();
//				String paramterField = "备案序号";
//				String paramterValue = "";
//				if (isSequencePrint) {
//					int beginNo = dg.getBeginNo();
//					int endNo = dg.getEndNo();
//					if (beginNo <= -1 && endNo <= -1) {
//						return;
//					}
//
//					if (beginNo > 0 && endNo > 0) {
//						paramterValue = "从 " + beginNo + " 到 " + endNo;
//						List<List> dataSource = manualDeclearAction
//								.findEmsHeadH2kBomChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										endNo, null);
//						emsHeadH2kVersionList = (List) dataSource.get(0);
//						ls = (List) dataSource.get(1);
//					} else if (beginNo <= 0 && endNo > 0) {
//						paramterValue = "从 0 到 " + endNo;
//						List<List> dataSource = manualDeclearAction
//								.findEmsHeadH2kBomChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, null,
//										endNo, null);
//						emsHeadH2kVersionList = (List) dataSource.get(0);
//						ls = (List) dataSource.get(1);
//					} else if (beginNo > 0 && endNo == -1) {
//						paramterValue = "从 " + beginNo + " 到最后 ";
//						List<List> dataSource = manualDeclearAction
//								.findEmsHeadH2kBomChange(new Request(CommonVars
//										.getCurrUser()), emsHeadH2k, beginNo,
//										null, null);
//						emsHeadH2kVersionList = (List) dataSource.get(0);
//						ls = (List) dataSource.get(1);
//					}
//				} else {
//					paramterValue = dg.getSeqNum();
//					Integer[] seqNumArray = dg.getSeqNumArray();
//					List<List> dataSource = manualDeclearAction
//							.findEmsHeadH2kBomChange(new Request(CommonVars
//									.getCurrUser()), emsHeadH2k, null, null,
//									seqNumArray);
//					emsHeadH2kVersionList = (List) dataSource.get(0);
//					ls = (List) dataSource.get(1);
//				}
//				ManualdeclareReportVars
//						.setEmsHeadH2kVersionList(emsHeadH2kVersionList);
//				CustomReportDataSource ds = new CustomReportDataSource(ls);
//				ds.setMaximumFractionDigits(9);
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeBom.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("companyName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("count", ls.size());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 7:// [按变更时间] 成品单耗清单
//			try {
//				DgEmsPrintDate dg = new DgEmsPrintDate();
//				dg.setVisible(true);
//				if (!dg.isOk()) {
//					return;
//				}
//				Date beginDate = dg.getBeginDate();
//				Date endDate = dg.getEndDate();
//				boolean isDeclared = dg.isDeclared();
//				String paramterField = "变更时间";
//				//
//				// 查询语句
//				//
//				List ls = new ArrayList();
//				List emsHeadH2kVersionList = new ArrayList();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				String begin = (beginDate == null ? "空" : dateFormat
//						.format(beginDate));
//				String end = (endDate == null ? "空" : dateFormat
//						.format(endDate));
//				String paramterValue = "从 " + begin + " 到 " + end;
//				// if (beginDate != null && endDate != null) {
//				// begin = dateFormat.format(beginDate);
//				// end = dateFormat.format(endDate);
//				// paramterValue = "从 " + begin + " 到 " + end;
//				//
//				// } else if (beginDate == null && endDate != null) {
//				// begin = dateFormat.format(beginDate);
//				// end = dateFormat.format(endDate);
//				// paramterValue = "从 空 " + " 到 " + end;
//				//
//				// } else if (beginDate != null && endDate == null) {
//				// begin = dateFormat.format(beginDate);
//				// end = dateFormat.format(endDate);
//				// paramterValue = "从 " + begin + " 到 空";
//				//
//				// } else if (beginDate == null && endDate == null) {
//				// begin = dateFormat.format(beginDate);
//				// end = dateFormat.format(endDate);
//				// paramterValue = "从 空" + " 到 空";
//				//
//				// }
//				List<List> dataSource = manualDeclearAction
//						.findEmsHeadH2kBomByDate(new Request(CommonVars
//								.getCurrUser()), emsHeadH2k, beginDate,
//								endDate, isDeclared);
//				emsHeadH2kVersionList = (List) dataSource.get(0);
//				ls = (List) dataSource.get(1);
//				ManualdeclareReportVars
//						.setEmsHeadH2kVersionList(emsHeadH2kVersionList);
//				CustomReportDataSource ds = new CustomReportDataSource(ls);
//				ds.setMaximumFractionDigits(9);
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsChangeBomByDate.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				parameters.put("paramterField", paramterField);
//				parameters.put("paramterValue", paramterValue);
//				parameters.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
//						: emsHeadH2k.getEmsNo());
//				parameters.put("companyName",
//						emsHeadH2k.getMachName() == null ? "" : emsHeadH2k
//								.getMachName());
//				parameters.put("count", ls.size());
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		case 8:// 打印加工贸电子帐册
//			try {
//				CustomReportDataSource ds = new CustomReportDataSource(list);
//				InputStream masterReportStream = FmEmsHeadH2k.class
//						.getResourceAsStream("report/EmsCover.jasper");
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				JasperPrint jasperPrint = JasperFillManager.fillReport(
//						masterReportStream, parameters, ds);
//				DgReportViewer viewer = new DgReportViewer(jasperPrint);
//				viewer.setVisible(true);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			break;
//		}
//	}

	/**
	 * 打印全部的电子帐册
	 * 
	 */
	private void printAllEms(EmsHeadH2k emsHeadH2k) {
		try {
			//
			// 表头和料件清单
			//
			InputStream emsMaterielStream = FmEmsHeadH2k.class
					.getResourceAsStream("report/EmsPutOnRecordDetail.jasper");
			List emsMaterielList = manualDeclearAction.findEmsHeadH2kImg(
					new Request(CommonVars.getCurrUser()), emsHeadH2k);
			CustomReportDataSource emsMaterielDs = new CustomReportDataSource(
					emsMaterielList);
			Map<String, Object> parameters1 = new HashMap<String, Object>();
			parameters1.put("address", emsHeadH2k.getAddress() == null ? ""
					: emsHeadH2k.getAddress());
			parameters1.put("tel", emsHeadH2k.getTel() == null ? ""
					: emsHeadH2k.getTel());
			parameters1.put("emsNo", emsHeadH2k.getEmsNo() == null ? ""
					: emsHeadH2k.getEmsNo());
			parameters1.put("outTradeCo",
					emsHeadH2k.getOutTradeCo() == null ? "" : emsHeadH2k
							.getOutTradeCo());
			parameters1.put("tradeName", emsHeadH2k.getTradeName() == null ? ""
					: emsHeadH2k.getTradeName());
			parameters1.put("tradeCode", emsHeadH2k.getTradeCode() == null ? ""
					: emsHeadH2k.getTradeCode());
			parameters1.put("machCode", emsHeadH2k.getMachCode() == null ? ""
					: emsHeadH2k.getMachCode());
			parameters1.put("machName", emsHeadH2k.getMachName() == null ? ""
					: emsHeadH2k.getMachName());
			parameters1.put("copEmsNo", emsHeadH2k.getCopEmsNo() == null ? ""
					: emsHeadH2k.getCopEmsNo());
			parameters1.put("sancEmsNo", emsHeadH2k.getSancEmsNo() == null ? ""
					: emsHeadH2k.getSancEmsNo());
			parameters1.put("machAbility",
					emsHeadH2k.getMachAbility() == null ? "0" : String
							.valueOf(emsHeadH2k.getMachAbility()));
			parameters1.put("maxTurnMoney",
					emsHeadH2k.getMaxTurnMoney() == null ? "0" : String
							.valueOf(emsHeadH2k.getMaxTurnMoney()));
			parameters1.put("emsApprNo", emsHeadH2k.getEmsApprNo() == null ? ""
					: emsHeadH2k.getEmsApprNo());

			JasperPrint masterJasperPrint = JasperFillManager.fillReport(
					emsMaterielStream, parameters1, emsMaterielDs);
			//
			// 成品清单
			//
			InputStream emsProductStream = FmEmsHeadH2k.class
					.getResourceAsStream("report/EmsPutOnRecordDetailProduct.jasper");
			List emsProductList = manualDeclearAction.findEmsHeadH2kExg(
					new Request(CommonVars.getCurrUser()), emsHeadH2k);
			CustomReportDataSource emsProductDs = new CustomReportDataSource(
					emsProductList);
			Map<String, Object> parameters2 = new HashMap<String, Object>();
			parameters2.put("beforePageCount", masterJasperPrint.getPages()
					.size());
			JasperPrint emsProductJasperPrint = JasperFillManager.fillReport(
					emsProductStream, parameters2, emsProductDs);
			int size = emsProductJasperPrint.getPages().size();
			for (int i = 0; i < size; i++) {
				masterJasperPrint.addPage((JRPrintPage) emsProductJasperPrint
						.getPages().get(i));
			}
			//
			// 成品清单
			//
			InputStream emsBomStream = FmEmsHeadH2k.class
					.getResourceAsStream("report/EmsPutOnRecordDetailBom.jasper");
			List emsBomList = manualDeclearAction.findEmsHeadH2kBomOrder(
					new Request(CommonVars.getCurrUser()), emsHeadH2k);
			CustomReportDataSource emsBomDs = new CustomReportDataSource(
					emsBomList);
			Map<String, Object> parameters3 = new HashMap<String, Object>();
			parameters3.put("beforePageCount", masterJasperPrint.getPages()
					.size());
			JasperPrint emsBomJasperPrint = JasperFillManager.fillReport(
					emsBomStream, parameters3, emsBomDs);

			size = emsBomJasperPrint.getPages().size();
			for (int i = 0; i < size; i++) {
				masterJasperPrint.addPage((JRPrintPage) emsBomJasperPrint
						.getPages().get(i));
			}
			//
			// 显示所有报表
			//
			DgReportViewer viewer = new DgReportViewer(masterJasperPrint);
			viewer.setVisible(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes btnUpdate 注：更新成品毛净重到版本表中
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setBounds(new java.awt.Rectangle(231, 4, 68, 25));
			btnUpdate.setText("更新");
			btnUpdate.setVisible(false);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TempExe().start();
				}
			});
		}
		return btnUpdate;
	}

	/**
	 * // * 处理更新数据多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class TempExe extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在进行更新数据，请稍后...");
				manualDeclearAction.dealEmsHistory2(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "更新失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnMove 注：迁移历史变更记录
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMove() {
		if (btnMove == null) {
			btnMove = new JButton();
			btnMove.setText("迁移");
			btnMove.setVisible(false);
			btnMove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new dealEmsHistory().start();
				}
			});
		}
		return btnMove;
	}

	/**
	 * 处理迁移历史记录数据多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class dealEmsHistory extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在进行迁移历史记录数据，请稍后...");
				manualDeclearAction.dealEmsHistory(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "迁移完毕！", "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "迁移失败！"
						+ e.getMessage(), "提示", 2);
			} finally {
				btnMove.setVisible(false);
				btnUpdating.setVisible(true);
			}
		}
	}

	/**
	 * This method initializes btnUpdating
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdating() {
		if (btnUpdating == null) {
			btnUpdating = new JButton();
			btnUpdating.setText("更新");
			btnUpdating.setVisible(false);
			btnUpdating.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 迁移一次后还做修改程式
					// new tempChange().start();
				}
			});
		}
		return btnUpdating;
	}

	/**
	 * 处理更新记录数据多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class tempChange extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在进行更新记录数据，请稍后...");
				manualDeclearAction.changeCustomsFromMateriel(new Request(
						CommonVars.getCurrUser()));
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "更新完毕！", "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "更新失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnInitialize
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInitialize() {
		if (btnInitialize == null) {
			btnInitialize = new JButton();
			btnInitialize.setText("初始化申报状态");
			btnInitialize.setVisible(false);
			btnInitialize
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JOptionPane
									.showMessageDialog(
											FmEmsHeadH2k.this,
											"初始化申报状态：\n"
													+ "将该帐册表体资料根据修改标记为【未修改】来设定申报状态为【已申报】",
											"提示！", 0);
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmEmsHeadH2k.this, "请选择你将要初始化申报的记录",
										"提示！", 0);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmEmsHeadH2k.this, "是否确定要将【电子帐册】初始化申报吗？",
									"确认", 0) == 0) {
								new InitSendState().start();
							}
						}
					});
		}
		return btnInitialize;
	}

	/**
	 * 处理要申报的资料多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class InitSendState extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
				CommonProgress.setMessage("系统正在初始化要申报的资料，请稍后...");
				EmsHeadH2k yy = (EmsHeadH2k) tableModel.getCurrentRow();
				manualDeclearAction.initEmsSendState(new Request(CommonVars
						.getCurrUser()), yy);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
						"系统初始化申报状态完成！", "提示", 1);
				setState();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsHeadH2k.this, "初始化失败！"
						+ e.getMessage(), "提示", 2);
			}
		}

	}

	/**
	 * This method initializes btnUpdateInnerMerge	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnUpdateInnerMerge() {
		if (btnUpdateInnerMerge == null) {
			btnUpdateInnerMerge = new JButton();
			btnUpdateInnerMerge.setText("同步内部归并");
			btnUpdateInnerMerge.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsHeadH2k.this,
								"请选择你将要同步的记录", "提示！", 0);
						return;
					}
					DgUpdateInnerMerge dg = new DgUpdateInnerMerge();
					dg.setEmsHead((EmsHeadH2k)tableModel.getCurrentRow());
					dg.setEms(true);
					dg.setVisible(true);
				}
			});
		}
		
		
		return btnUpdateInnerMerge;
	}

	private JButton getBtnCompareMessage() {
		if (btnCompareMessage == null) {
			btnCompareMessage = new JButton("对比回执单耗");
			btnCompareMessage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new CompareMessage().start();
				}
			});
		}
		return btnCompareMessage;
	}
	
	class CompareMessage extends Thread {
		@Override
		public void run() {
			CommonProgress.showProgressDialog(FmEmsHeadH2k.this);
			CommonProgress.setMessage("系统正在对比回执单耗，请稍后...");
			try {
				Map<String, String> bomKeys = messageAction.compareBomMessage(CommonVars.getRequst());
				List<Object[]> list = new ArrayList<Object[]>(bomKeys.size());
				Iterator<String> it = bomKeys.keySet().iterator();
				String[] tmps = null;
				String key = null;
				while(it.hasNext()) {
					key = it.next();
					tmps = key.split("/");
					list.add(new Object[]{Integer.valueOf(tmps[0]), Integer.valueOf(tmps[1]), Integer.valueOf(tmps[2]), bomKeys.get(key)});
				}
				Collections.sort(list, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						Integer x = (Integer) o1[0];
						Integer y = (Integer) o2[0];
						int i = x.compareTo(y);
						if(i == 0 ) {
							x = (Integer) o1[1];
							y = (Integer) o2[1];
							i = x.compareTo(y);
							if(i == 0 ) {
								x = (Integer) o1[2];
								y = (Integer) o2[2];
								return x.compareTo(y);
							} 
							return i;
						} 
						return i;
					}
				});
				
				CommonProgress.closeProgressDialog();
				
				List<JTableListColumn> columns = new ArrayList<JTableListColumn>();
				columns.add(new JTableListColumn("成品序号", 100));
				columns.add(new JTableListColumn("版本", 100));
				columns.add(new JTableListColumn("料件序号", 100));
				DgMessageAndUpdate.show("数据库中为 ‘未修改’ 而回执中不存在的 单耗！", columns, list);
			} catch (RuntimeException e) {
				CommonProgress.closeProgressDialog();
				throw e;
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
