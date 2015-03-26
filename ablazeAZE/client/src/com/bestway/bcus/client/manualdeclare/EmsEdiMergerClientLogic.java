/*
 * Created on 2004-7-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.TempBom;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiMergerClientLogic {

	private static NumberFormatter numberFormatter = null;
	private static CommonBaseCodeAction commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
			.getApplicationContext().getBean("commonBaseCodeAction");
	private static ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction");

	public static JTableListModel fillImgBefore(
			JTableListModel tableModelImgBefore,
			EmsEdiMergerImgAfter emsImgAfter, JTable table) {

		List list = manualdeclearAction.findEmsEdiMergerImgBefore(new Request(
				CommonVars.getCurrUser()), emsImgAfter);
		tableModelImgBefore.setList(list);
		EmsEdiMergerClientLogic.transModifyMark(table);
		return tableModelImgBefore;
	}

	public static void fillExgBefore(JTableListModel tableModelExgBefore,
			JTableListModel tableModelBomExg, EmsEdiMergerExgAfter emsExgAfter,
			JTable table, JTable tableExgBom) {
		// 归并前成品
		List list = manualdeclearAction.findEmsEdiMergerExgBefore(new Request(
				CommonVars.getCurrUser()), emsExgAfter);
		tableModelExgBefore.setList(list);
		//tableModelBomExg.setList(list);
		EmsEdiMergerClientLogic.transModifyMark(table);
		EmsEdiMergerClientLogic.transModifyMark(tableExgBom);
	}

	public static boolean getIsSend() {
		String isSend = manualdeclearAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsSEND);
		if (isSend != null && "1".equals(isSend)) {
			return true;
		}
		return false;
	}

	public static boolean getIsEmsSend() {
		String isSend = manualdeclearAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsEdiH2kSend);
		if (isSend != null && "1".equals(isSend)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	public static NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public static Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	public static String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 归并后料件
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	public static JTableListModel initTableImgAfter(final JTable table,
			final List list) {
		JTableListModel model = null;
		model = new JTableListModel(table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 60));
				if (getIsSend()) {
					list.add(addColumn("申报标志", "sendState", 80));
				}
				list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("计量单位", "unit.name", 80));
				list.add(addColumn("第一法定单位", "complex.firstUnit.name", 80));
				list.add(addColumn("第二法定单位", "complex.secondUnit.name", 80));
				list.add(addColumn("法定单位比例因子", "legalUnitGene", 80));
				list.add(addColumn("第二法定单位比例因子", "legalUnit2Gene", 80));
				list.add(addColumn("重量比例因子", "weigthUnitGene", 120));
				list.add(addColumn("是否主料", "isMainImg", 60));
				list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("单价", "price", 80));
				list.add(addColumn("批准最大余量", "maxApprSpace", 60));
				list.add(addColumn("备注", "note", 100));
				list.add(addColumn("变更日期", "changeDate", 100));
				return list;
			}
		});
		if (getIsSend())
			table.getColumnModel().getColumn(13).setCellRenderer(
					new TableCheckBoxRender());
		else
			table.getColumnModel().getColumn(12).setCellRenderer(
					new TableCheckBoxRender());
		return model;

	}

	public static void transModifyMark(JTable table) {
		table.getColumnModel().getColumn(1).setCellRenderer(
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
						if (value.equals("0")) {
							returnValue = "未修改";
						} else if (value.equals("1")) {
							returnValue = "已修改";
						} else if (value.equals("2")) {
							returnValue = "已删除";
						} else if (value.equals("3")) {
							returnValue = "新增";
						}
						return returnValue;
					}
				});
	}

	public static void transSendState(JTable table) {
		if (getIsSend()) {
			table.getColumnModel().getColumn(2).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "  ○  "
									: castValue1(value));
							return this;
						}

						private String castValue1(Object value) {
							String returnValue = "";
							if (String.valueOf(value).trim().equals("")) {
								return "";
							}
							if (value.equals("0")) {
								returnValue = "  ○  ";// "未申报";
							} else if (value.equals("1")) {
								returnValue = "  √  ";// "准备申报";
							} else if (value.equals("2")) {
								returnValue = "  ●  ";// "已申报";
							}
							return returnValue;
						}
					});
		}
	}

	// 显示料件状态
	public static void MaterielTypeState(JTable table) {
		table.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "  ○  "
								: castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(MaterielType.MATERIEL)) {
							returnValue = "料件";// "未申报";
						} else if (value.equals(MaterielType.FINISHED_PRODUCT)) {
							returnValue = "成品";// "准备申报";
						} else if (value.equals(MaterielType.BAD_PRODUCT)) {
							returnValue = "残次品";// "已申报";
						} else if (value.equals(MaterielType.REMAIN_MATERIEL)) {
							returnValue = "边角料";// "已申报";
						} else if (value.equals(MaterielType.MACHINE)) {
							returnValue = "设备";// "已申报";
						} else if (value
								.equals(MaterielType.ASSISTANT_MATERIAL)) {
							returnValue = "辅料";// "已申报";
						} else if (value.equals(MaterielType.WASTE_MATERIAL)) {
							returnValue = "消耗料";// "已申报";
						} else if (value
								.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
							returnValue = "半成品";// "已申报";
						}
						return returnValue;
					}
				});
	}

	// 电子帐册申报标记
	public static void transEmsSendState(JTable table) {
		if (getIsEmsSend()) {
			table.getColumnModel().getColumn(2).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "  ○  "
									: castValue1(value));
							return this;
						}

						private String castValue1(Object value) {
							String returnValue = "";
							if (String.valueOf(value).trim().equals("")) {
								return "";
							}
							if (value.equals("0")) {
								returnValue = "  ○  ";// "未申报";
							} else if (value.equals("1")) {
								returnValue = "  √  ";// "准备申报";
							} else if (value.equals("2")) {
								returnValue = "  ●  ";// "已申报";
							}
							return returnValue;
						}
					});
		}
	}

	/**
	 * 归并后成品
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	public static JTableListModel initTableExgAfter(final JTable table,
			final List list) {
		return new JTableListModel(table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 60));
				if (getIsSend()) {
					list.add(addColumn("申报标志", "sendState", 80));
				}
				list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("计量单位", "unit.name", 80));
				list.add(addColumn("第一法定单位", "complex.firstUnit.name", 80));
				list.add(addColumn("第二法定单位", "complex.secondUnit.name", 80));
				list.add(addColumn("法定单位比例因子", "legalUnitGene", 80));
				list.add(addColumn("第二法定单位比例因子", "legalUnit2Gene", 80));
				list.add(addColumn("重量比例因子", "weigthUnitGene", 120));
				list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("单价", "price", 80));
				list.add(addColumn("批准最大余量", "maxApprSpace", 60));
				list.add(addColumn("备注", "note", 100));
				list.add(addColumn("变更日期", "changeDate", 100));
				return list;
			}
		});
	}

	/**
	 * 归并前料件
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	public static JTableListModel initTableImgBefore(final JTable table,
			final List list) {

		return new JTableListModel(table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 60));
				if (getIsSend()) {
					list.add(addColumn("申报标志", "sendState", 80));
				}
				list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
				list.add(addColumn("料件货号", "ptNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("计量单位", "unit.name", 80));
				list.add(addColumn("第一法定单位", "complex.firstUnit.name", 80));
				list.add(addColumn("第二法定单位", "complex.secondUnit.name", 80));
				// list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("批准最大余量", "maxApprSpace", 60));
				list.add(addColumn("备注", "note", 100));
				list.add(addColumn("变更日期", "changeDate", 100));
				return list;
			}
		});
	}

	/**
	 * 归并前成品
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	public static JTableListModel initTableExgBefore(final JTable table,
			final List list) {
		return new JTableListModel(table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 60));
				if (getIsSend()) {
					list.add(addColumn("申报标志", "sendState", 80));
				}
				list.add(addColumn("归并序号", "seqNum", 60, Integer.class));
				list.add(addColumn("成品货号", "ptNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("计量单位", "unit.name", 80));
				list.add(addColumn("第一法定单位", "complex.firstUnit.name", 80));
				list.add(addColumn("第二法定单位", "complex.secondUnit.name", 80));
				// list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("单价", "price", 80));
				list.add(addColumn("批准最大余量", "maxApprSpace", 60));
				list.add(addColumn("备注", "note", 100));
				list.add(addColumn("变更日期", "changeDate", 100));
				list.add(addColumn("最大版本号", "maxVersion", 100));
				return list;
			}
		});

	}

	/**
	 * 成品BOM
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	public static JTableListModel initTableBom( JTable table,
			 List list) {
		 JTableListModel tablemodel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						if (getIsSend()) {
							list.add(addColumn("申报标志", "sendState", 80));
						}
						list.add(addColumn("料件货号", "ptNo", 100));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWaste", 80));
						list.add(addColumn("损耗率%", "waste", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("变更日期", "changeDate", 100));
						return list;
					}
				});

		return tablemodel;
	}

	public static JTableListModel initTableBomImg(final JTable table,
			final List list) {
		final JTableListModel tablemodel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件货号", "ptNo", 100));
						list.add(addColumn("货号名称", "name", 100));
						list.add(addColumn("货号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWaste", 80));
						list.add(addColumn("损耗率%", "waste", 80));
						list.add(addColumn("备案序号","emsEdiMergerImgAfter.seqNum", 80));
						list.add(addColumn("备案名称", "emsEdiMergerImgAfter.name",100));
						list.add(addColumn("备案规格", "emsEdiMergerImgAfter.spec",100));

						return list;
					}
				});
		table.getColumnModel().getColumn(5).setCellRenderer(
	                new DefaultTableCellRenderer() {

	                    public Component getTableCellRendererComponent(
	                            JTable table, Object value, boolean isSelected,
	                            boolean hasFocus, int row, int column) {
	                        super.getTableCellRendererComponent(table, value,
	                                isSelected, hasFocus, row, column);
	        				if (value != null) {// 设置Double数据的显示格式
	        					if (value instanceof Double) {
//	        						Double tmp = Double.parseDouble((String) value);
	        						DecimalFormat df = new DecimalFormat("######0.#########");
	        						this.setText("" + df.format(value));
	        					}
	        				}
	        				return this;
	                    }
	                });

		return tablemodel;
	}

	// protected static void selectTable(MouseEvent e, JTable table,
	// Integer seqNum, String ptNo) {
	// int row = table.rowAtPoint(e.getPoint());
	// if (row >= 0) {
	// table.setRowSelectionInterval(row, row);
	// table.setToolTipText("料号：" + ptNo + "对应的备案序号："
	// + Integer.valueOf(seqNum));
	// }
	//
	// }

	/**
	 * 归并关系--新增归并前料件 edit by xxm
	 * 
	 * @param emsEdiMergerHead
	 */
	public static void fillMergerImgBeforeData(EmsEdiMergerImgAfter imgAfter,
			EmsEdiMergerHead emsEdiMergerHead, List afters, String type,
			JTable table) {
		List beforesList = null; // 显示归并前数据
		Integer seqNum = imgAfter.getSeqNum();
		String isSendSign = manualdeclearAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsSEND_Sign);
		beforesList = commonBaseCodeAction.findInnerMergeDataAfterImgByType10(
				new Request(CommonVars.getCurrUser()), type, seqNum,
				emsEdiMergerHead, -1, -1, null, null, null);
		if (beforesList != null && !beforesList.isEmpty()) {
			for (int i = 0; i < beforesList.size(); i++) {
				InnerMergeData data = (InnerMergeData) beforesList.get(i);
				EmsEdiMergerImgBefore emsBefore = new EmsEdiMergerImgBefore();
				emsBefore.setSeqNum(data // 十码序号
						.getHsAfterTenMemoNo());
				emsBefore.setPtNo(data // 料号
						.getMateriel().getPtNo());
				emsBefore.setComplex(data
						.getHsAfterComplex());
				emsBefore.setName(data
						.getMateriel().getFactoryName()); // 报关名称
				emsBefore.setSpec(data
						.getMateriel().getFactorySpec());
				if (data
						.getHsBeforeEnterpriseUnit() != null) {
					emsBefore.setUnit(data
							.getHsAfterMemoUnit());// 计量单位
				}
				emsBefore.setCompany(CommonVars.getCurrUser().getCompany());
				emsBefore.setEmsEdiMergerImgAfter(imgAfter);
				emsBefore.setModifyMark(ModifyMarkState.ADDED);
				emsBefore.setMaxApprSpace(Double.valueOf(0)); // 批准最大余量
				if (isSendSign != null && "1".equals(isSendSign)) {
					emsBefore
							.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				}
				emsBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
				emsBefore.setChangeDate(new Date());
				emsBefore = manualdeclearAction.saveEmsEdiMergerImgBefore(
						new Request(CommonVars.getCurrUser()), emsBefore);
				EmsEdiMergerClientLogic.transModifyMark(table);
				
				data.setIsExistMerger(new Boolean(true));
				commonBaseCodeAction.saveInnerMergeData(new Request(CommonVars
						.getCurrUser()), data);

			}
		}
	}

	/**
	 * 归并关系--新增归并前成品 edit by xxm
	 * 
	 * @param emsEdiMergerHead
	 *            `````
	 */
	public static void fillMergerExgBeforeData(EmsEdiMergerExgAfter exgAfter,
			EmsEdiMergerHead emsEdiMergerHead, List afters, String type,
			JTable table) {
		List befores = null; // 显示归并前数据
		Integer seqNum = exgAfter.getSeqNum();
		String isSendSign = manualdeclearAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsSEND_Sign);
		befores = commonBaseCodeAction.findInnerMergeDataAfterExgByType10(
				new Request(CommonVars.getCurrUser()), type, seqNum,
				emsEdiMergerHead, -1, -1, null, null, null);
		if (befores != null && !befores.isEmpty()) {
			for (int i = 0; i < befores.size(); i++) {
				EmsEdiMergerExgBefore emsBefore = new EmsEdiMergerExgBefore();
				emsBefore.setSeqNum(((InnerMergeData) befores.get(i)) // 序号
						.getHsAfterTenMemoNo());
				emsBefore.setPtNo(((InnerMergeData) befores.get(i)) // 料号
						.getMateriel().getPtNo());
				emsBefore.setComplex(((InnerMergeData) befores.get(i))
						.getHsAfterComplex());
				emsBefore.setName(((InnerMergeData) befores.get(i))
						.getMateriel().getFactoryName()); // 报关名称
				emsBefore.setSpec(((InnerMergeData) befores.get(i))
						.getMateriel().getFactorySpec()); // 报关规格
				if (((InnerMergeData) befores.get(i))
						.getHsBeforeEnterpriseUnit() != null) {
					emsBefore.setUnit(((InnerMergeData) befores.get(i))
							.getHsAfterMemoUnit()); // 计量单位
				}
				if (isSendSign != null && "1".equals(isSendSign)) {
					emsBefore
							.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				}
				emsBefore.setCompany(CommonVars.getCurrUser().getCompany());
				emsBefore.setEmsEdiMergerExgAfter(exgAfter);
				emsBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
				emsBefore.setModifyMark(ModifyMarkState.ADDED);
				emsBefore.setMaxApprSpace(Double.valueOf(0)); // 批准最大余量
				emsBefore.setChangeDate(new Date());
				emsBefore = manualdeclearAction.saveEmsEdiMergerExgBefore(
						new Request(CommonVars.getCurrUser()), emsBefore);
				InnerMergeData data = (InnerMergeData) befores.get(i);
				data.setIsExistMerger(new Boolean(true));
				commonBaseCodeAction.saveInnerMergeData(new Request(CommonVars
						.getCurrUser()), data);
				EmsEdiMergerClientLogic.transModifyMark(table);
			}
		}
	}

	public static boolean fillBomFromMergerBeforeImg(
			EmsEdiMergerHead emsEdiMergerHead, JTableListModel tableModelBom,
			EmsEdiMergerExgBefore emsExgBefore, JTable table,
			EmsEdiMergerVersion emsEdiMergerVersion) {
		String isSendSign = manualdeclearAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsSEND_Sign);
		EmsEdiMergerExgBom emsExgBom = null;
		List list = (List) CommonQuery.getInstance().getMergerAfterImg(false,
				emsEdiMergerVersion, emsEdiMergerHead);
		if (list == null || list.isEmpty())
			return false;
		for (int i = 0; i < list.size(); i++) {
			emsExgBom = (EmsEdiMergerExgBom) list.get(i);
			// 有待修改
			emsExgBom.setEmsEdiMergerVersion(emsEdiMergerVersion);
			emsExgBom.setCompany(CommonVars.getCurrUser().getCompany());
			emsExgBom.setModifyMark(ModifyMarkState.ADDED);
			if (isSendSign != null && "1".equals(isSendSign)) {
				emsExgBom.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			emsExgBom.setModifyTimes(emsEdiMergerHead.getModifyTimes());// 变更次数
			emsExgBom.setMachinKind("2");// 加工性质
			emsExgBom.setChangeDate(new Date());
			EmsEdiMergerClientLogic.transModifyMark(table);
			EmsEdiMergerClientLogic.transSendState(table);
			emsExgBom = manualdeclearAction.saveEmsEdiMergerExgBom(new Request(
					CommonVars.getCurrUser()), emsExgBom);
			tableModelBom.addRow(emsExgBom);
		}
		return true;
	}

	// 增加归并关系Bom资料来自于产品单耗
	public static boolean fillBomFromBomManage(
			EmsEdiMergerHead emsEdiMergerHead, List list,
			EmsEdiMergerExgBefore emsExgBefore, JTableListModel tableModelBom,
			JTable table) {
		if (list.isEmpty()) {
			return false;
		}
		EmsEdiMergerExgBom exgBom = new EmsEdiMergerExgBom();
		for (int i = 0; i < list.size(); i++) {
			TempBom bom = (TempBom) list.get(i);
			// 有待修改
			// exgBom.setEmsEdiMergerExgBefore(emsExgBefore);
			exgBom.setName(bom.getFactoryName());
			exgBom.setPtNo(bom.getPtNo());
			exgBom.setSpec(bom.getFactorySpec());
			exgBom.setUnit(bom.getCalUnit().getUnit());
			exgBom.setUnitWaste(Double.valueOf(bom.getUnitWaste()));
			exgBom.setWaste(Double.valueOf(bom.getWaste()));
			/*
			 * if
			 * (emsEdiMergerHead.getDeclareType().equals(DelcareType.APPLICATION
			 * )) exgBom.setModifyMark(ModifyMarkState.UNCHANGE); else
			 */
			exgBom.setModifyMark(ModifyMarkState.ADDED);
			EmsEdiMergerClientLogic.transModifyMark(table);
			exgBom = manualdeclearAction.saveEmsEdiMergerExgBom(new Request(
					CommonVars.getCurrUser()), exgBom);
			tableModelBom.addRow(exgBom);
		}
		return true;
	}

}
