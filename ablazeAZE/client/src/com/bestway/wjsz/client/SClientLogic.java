package com.bestway.wjsz.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.waijing.action.WjszAction;

public class SClientLogic {

	private static WjszAction wjszAction = (WjszAction) CommonVars
			.getApplicationContext().getBean("wjszAction");

	/**
	 * 电子手册-合同备案-料件
	 * 
	 * @param jTable
	 * @param head
	 * @return
	 */
	public static JTableListModel initTableImgWj(JTable jTable,
			DzscEmsPorWjHead porHead) {
		List list = (List) wjszAction.findDzscEmsImgWj(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableImgWj(jTable, list);
		} else {
			return initTableImgWj(jTable, new Vector());
		}
	}

	/**
	 * 电子手册-合同备案-料件
	 * 
	 * @param jTable
	 * @param head
	 * @return
	 */
	public static JTableListModel initTableExgWj(JTable jTable,
			DzscEmsPorWjHead porHead) {
		List list = (List) wjszAction.findDzscEmsExgWj(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableImgWj(jTable, list);
		} else {
			return initTableImgWj(jTable, new Vector());
		}
	}

	// 填充料件清单Table----外经
	public static JTableListModel initTableImgWj(JTable jTable, final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("修改标志", "modifyMark", 80));
				list.add(addColumn("备案序号", "seqNum", 50));
				list.add(addColumn("外经凭证序号", "wjCode", 80));
				list.add(addColumn("归并序号", "fourSeqNum", 50));
				list.add(addColumn("商品编码", "fourComplex", 80));
				list.add(addColumn("商品名称", "fourName", 80));
				list.add(addColumn("规格型号", "fourSpec", 80));
				list.add(addColumn("单位", "fourUnit.name", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), true));

		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		return tableModel;
	}

	/**
	 * 经营范围
	 * 
	 * @param jTable
	 * @return
	 */
	public static JTableListModel initTableImg(JTable jTable, EmsEdiTrHead head) {
		List list = (List) wjszAction.findEmsEdiTrImg(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableImg(jTable, list);
		} else {
			return initTableImg(jTable, new Vector());
		}
	}

	public static JTableListModel initTableExg(JTable jTable, EmsEdiTrHead head) {
		List list = (List) wjszAction.findEmsEdiTrExg(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableExg(jTable, list);
		} else {
			return initTableExg(jTable, new Vector());
		}
	}

	// 经营范围
	private static JTableListModel initTableImg(JTable jTable, final List list) {
		// JTableListModel tableModel = new JTableListModel(jTable, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List <JTableListColumn>list = new Vector<JTableListColumn>();
		// list.add(addColumn("选择", "isSelected", 80));
		// list.add(addColumn("修改标志","modifyMark",60));
		// list.add(addColumn("料件序号", "ptNo", 80, Integer.class));
		// list.add(addColumn("商品编码", "complex", 100));
		// list.add(addColumn("商品名称", "name", 120));
		// list.add(addColumn("备注", "note", 150));
		// list.add(addColumn("变更日期", "changeDate", 100));
		// return list;
		// }
		// });
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("修改标志", "modifyMark", 60));
				list.add(addColumn("料件序号", "ptNo", 80, Integer.class));
				list.add(addColumn("商品编码", "complex", 100));
				list.add(addColumn("商品名称", "name", 120));
				list.add(addColumn("备注", "note", 150));
				list.add(addColumn("变更日期", "changeDate", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), true));
		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
		return tableModel;
	}

	// 经营范围
	private static JTableListModel initTableExg(JTable jTable, final List list) {
		// JTableListModel tableModel = new JTableListModel(jTable, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List <JTableListColumn>list = new Vector<JTableListColumn>();
		// list.add(addColumn("选择", "isSelected", 80));
		// list.add(addColumn("修改标志","modifyMark",60));
		// list.add(addColumn("料件序号", "ptNo", 80, Integer.class));
		// list.add(addColumn("商品编码", "complex", 100));
		// list.add(addColumn("商品名称", "name", 120));
		// list.add(addColumn("备注", "note", 150));
		// list.add(addColumn("变更日期", "changeDate", 100));
		// return list;
		// }
		// });
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("修改标志", "modifyMark", 60));
				list.add(addColumn("料件序号", "ptNo", 80, Integer.class));
				list.add(addColumn("商品编码", "complex", 100));
				list.add(addColumn("商品名称", "name", 120));
				list.add(addColumn("备注", "note", 150));
				list.add(addColumn("变更日期", "changeDate", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), true));
		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
		return tableModel;
	}

	/**
	 * 初始化数据Table
	 */
	public static JTableListModel initTableImgDictPor(JTable jTable,
			final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("修改标志", "modifyMark", 80));
				list.add(addColumn("备案序号", "seqNum", 80, Integer.class));
				list.add(addColumn("外经凭证序号", "wjCode", 80));
				list.add(addColumn("归并序号", "innerMergeSeqNum", 80,
						Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 60));
				list.add(addColumn("计量单位", "comUnit.name", 80));
				list.add(addColumn("主料标志", "isMainImg", 80));
				list.add(addColumn("申报单价", "unitPrice", 80));
				list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), true));

		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new TableCheckBoxRender());
		return tableModel;
	}

	public static JTableListModel initTableExgDictPor(JTable jTable,
			final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("修改标志", "modifyMark", 80));
				list.add(addColumn("备案序号", "seqNum", 80, Integer.class));
				list.add(addColumn("外经凭证序号", "wjCode", 80));
				list.add(addColumn("归并序号", "innerMergeSeqNum", 80,
						Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 60));
				list.add(addColumn("计量单位", "comUnit.name", 80));
				list.add(addColumn("申报单价", "unitPrice", 80));
				list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		};

		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), false));

		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		return tableModel;
	}

	/**
	 * 电子化手册
	 * 
	 * @param jTable
	 * @return
	 */
	public static JTableListModel initTableImgDictPor(JTable jTable,
			BcsDictPorHead porHead) {
		List list = (List) wjszAction.findBcsDictPorImg(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableImgDictPor(jTable, list);
		} else {
			return initTableImgDictPor(jTable, new Vector());
		}
	}

	public static JTableListModel initTableExgDictPor(JTable jTable,
			BcsDictPorHead porHead) {
		List list = (List) wjszAction.findBcsDictPorExg(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableExgDictPor(jTable, list);
		} else {
			return initTableExgDictPor(jTable, new Vector());
		}
	}

	/**
	 * 电子化手册备案料件
	 */
	public static JTableListModel initTableContractImg(JTable jTable,
			Contract head) {
		if (head == null) {
			return initTableContractImg(jTable, new Vector());
		}
		List list = (List) wjszAction.findContractImgByContract(new Request(
				CommonVars.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableContractImg(jTable, list);
		} else {
			return initTableContractImg(jTable, new Vector());
		}
	}

	private static JTableListModel initTableContractImg(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 100));
						list
								.add(addColumn("料件序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("记录号", "credenceNo", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("损耗数量", "wasteAmount", 100));
						list.add(addColumn("总金额", "totalPrice", 100));
						list.add(addColumn("主料/辅料", "isMainImg", 100));
						list.add(addColumn("法定单位", "complex.firstUnit.name",
								100));
						list.add(addColumn("单位重量", "unitWeight", 100));
						list.add(addColumn("总重量", "totalWeight", 100));
						list.add(addColumn("原产地", "country.name", 100));

						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		return tableModel;
	}

	/**
	 * 电子化手册备案成品
	 */
	public static JTableListModel initTableContractExg(JTable jTable,
			Contract head) {
		if (head == null) {
			return initTableDictExg(jTable, new Vector());
		}
		List list = (List) wjszAction.findContractExgByContract(new Request(
				CommonVars.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableDictExg(jTable, list);
		} else {
			return initTableDictExg(jTable, new Vector());
		}
	}

	/**
	 * 电子化手册备案单耗
	 */
	public static JTableListModel initTableContractBom(JTable jTable,
			ContractExg exg) {
		if (exg == null) {
			return initTableDictBom(jTable, new Vector());
		}
		List list = (List) wjszAction.findContractBomByExgId(new Request(
				CommonVars.getCurrUser()), exg);
		if (list != null && list.size() > 0) {
			return initTableDictBom(jTable, list);
		} else {
			return initTableDictBom(jTable, new Vector());
		}
	}

	/**
	 * 初始化单耗表
	 */
	private static JTableListModel initTableDictBom(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 100));
						list.add(addColumn("料件总表序号", "contractImgSeqNum", 100,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("净耗", "unitWaste", 100));
						list.add(addColumn("损耗率%", "waste", 100));
						list.add(addColumn("单耗", "unitDosage", 100));
						list.add(addColumn("单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("主料/辅料", "isMainImg", 100));
						list.add(addColumn("原产地", "country.name", 100));
						list.add(addColumn("料件归并序号", "imgCredenceNo", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		return tableModel;
	}

	private static JTableListModel initTableDictExg(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 100));
						list
								.add(addColumn("成品序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("记录号", "credenceNo", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("出口数量", "exportAmount", 100));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("总额", "totalPrice", 100));
						list.add(addColumn("法定单位", "complex.firstUnit.name",
								100));
						list.add(addColumn("原料费", "materialFee", 100));
						list.add(addColumn("消费国", "country.name", 100));
						list.add(addColumn("加工费单价", "processUnitPrice", 100));
						list.add(addColumn("加工费总价", "processTotalPrice", 100));
						list.add(addColumn("单位毛重", "unitGrossWeight", 100));
						list.add(addColumn("单位净重", "unitNetWeight", 100));
						list.add(addColumn("征减免税方式", "levyMode.name", 100));

						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		return tableModel;

	}

	/**
	 * 电子手册通关备案料件总表
	 * 
	 * @return
	 */
	public static JTableListModel initTableDzscPorImg(JTable jTable,
			DzscEmsPorHead porHead) {
		if (porHead == null) {
			return initTableImgBill(jTable, new Vector());
		}
		List list = (List) wjszAction.findDzscImg(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableImgBill(jTable, list);
		} else {
			return initTableImgBill(jTable, new Vector());
		}
	}

	/**
	 * 电子手册通关备案成品表
	 * 
	 * @return
	 */
	public static JTableListModel initTableDzscPorExg(JTable jTable,
			DzscEmsPorHead porHead) {
		if (porHead == null) {
			return initTableExgBill(jTable, new Vector());
		}
		List list = (List) wjszAction.findDzscExg(new Request(CommonVars
				.getCurrUser()), porHead);
		if (list != null && list.size() > 0) {
			return initTableExgBill(jTable, list);
		} else {
			return initTableExgBill(jTable, new Vector());
		}
	}

	/**
	 * 电子手册通关备案单耗表
	 * 
	 * @return
	 */
	public static JTableListModel initTableDzscPorBom(JTable jTable,
			DzscEmsExgBill exg) {
		if (exg == null) {
			return initTableExgBill(jTable, new Vector());
		}
		List list = (List) wjszAction.findEmsPorBomBill(new Request(CommonVars
				.getCurrUser()), exg);
		if (list != null && list.size() > 0) {
			return initTableBomBill(jTable, list);
		} else {
			return initTableBomBill(jTable, new Vector());
		}
	}

	// 填充BOM清单Table
	public static JTableListModel initTableBomBill(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list.add(addColumn("料件总表序号", "imgSeqNum", 80,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单耗", "unitWare", 70));
						list.add(addColumn("损耗率%", "ware", 70));
						list.add(addColumn("单项用量", "unitDosage", 80));
						list.add(addColumn("单价", "price", 70));
						list.add(addColumn("数量", "amount", 70));
						list.add(addColumn("总额", "money", 70));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("主料/辅料", "isMainImg", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		return tableModel;
	}

	// 填充成品清单Table
	private static JTableListModel initTableExgBill(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list
								.add(addColumn("备案序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("合同序号", "wjSeqNum", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("单价", "price", 70));
						list.add(addColumn("出口数量", "amount", 80));
						list.add(addColumn("总额", "money", 70));
						list.add(addColumn("原料费", "imgMoney", 50));
						list.add(addColumn("消费国", "country.name", 70));
						list.add(addColumn("加工费单价", "machinPrice", 100));
						list.add(addColumn("补充说明", "note", 80));
						list.add(addColumn("单位净重", "unitNetWeight", 80));
						list.add(addColumn("单位毛重", "unitGrossWeight", 80));
						// list.add(addColumn("合同序号", "seqNum", 80));
						list.add(addColumn("征免方式", "levyMode.name", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		return tableModel;
	}

	private static JTableListModel initTableImgBill(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list
								.add(addColumn("备案序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("合同序号", "wjSeqNum", 60,
								Integer.class));
						list.add(addColumn("海关编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单价", "price", 70));
						list.add(addColumn("数量", "amount", 70));
						list.add(addColumn("金额", "money", 70));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("总重量", "weight", 70));
						list.add(addColumn("原产地", "country.name", 70));
						// list.add(addColumn("合同序号", "seqNum", 80));
						list.add(addColumn("征免方式", "levyMode.name", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		return tableModel;
	}

}

/**
 * render table JchcckBox 列
 */
class MultiRenderer extends DefaultTableCellRenderer {
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			value = false;
		}
		if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
		String str = (value == null) ? "" : value.toString();
		return super.getTableCellRendererComponent(table, str, isSelected,
				hasFocus, row, column);
	}
}

/**
 * 成品编辑列
 */
class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
	private JCheckBox cb;

	private JTable table = null;
	private boolean isLj = true;

	public CheckBoxEditor(JCheckBox checkBox, boolean isLj) {
		super(checkBox);
		this.isLj = isLj;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null) {
			value = false;
		}
		if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			cb = new JCheckBox();
			cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
		}
		cb.setHorizontalAlignment(JLabel.CENTER);
		cb.addActionListener(this);
		this.table = table;
		return cb;
	}

	public Object getCellEditorValue() {
		cb.removeActionListener(this);
		return cb;
	}

	public void actionPerformed(ActionEvent e) {
		JTableListModel tableModel = (JTableListModel) this.table.getModel();
		Object obj = tableModel.getCurrentRow();
		if (isLj) {// 料件
			if (obj instanceof BcsDictPorImg) {
				BcsDictPorImg temp = (BcsDictPorImg) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof DzscEmsImgWj) {
				DzscEmsImgWj temp = (DzscEmsImgWj) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
		} else {
			if (obj instanceof BcsDictPorExg) {
				BcsDictPorExg temp = (BcsDictPorExg) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			if (obj instanceof DzscEmsExgWj) {
				DzscEmsExgWj temp = (DzscEmsExgWj) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
		}
		fireEditingStopped();
	}
}
