package com.bestway.client.util.footer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.bestway.client.util.JTableListModel;

public class JTableFooter extends JPanel {

	private JTableHeader tableHeader = null;

	private List<Component> columnList = new ArrayList<Component>();

	private Map<Integer, TableFooterType> footerMap = new HashMap<Integer, TableFooterType>();

	private int gap = 0;

	private boolean isStatisticSelectedData = false;

	private ListSelectionListener listSelectionListener = null;

	public JTableFooter(JTableHeader tableHeader) {
		super();
		this.tableHeader = tableHeader;
		// this.setBackground(this.tableHeader.getTable().getBackground());
		setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, gap, gap));
		setPreferredSize(new Dimension(tableHeader.getPreferredSize().width,
				tableHeader.getTable().getRowHeight()));
		createFooterField();
	}

	private void createFooterField() {
		if (tableHeader != null) {
			int columnCount = tableHeader.getColumnModel().getColumnCount();
			createFooterField(columnCount);
			refreshFooterFiled();
		}
	}

	private void createFooterField(int columnCount) {
		columnList.clear();
		this.removeAll();
		for (int i = 0; i < columnCount; i++) {
			JLabel textField = new JLabel();
			textField.setOpaque(true);
			textField.setBackground(tableHeader.getBackground());
			// textField.setBorder(new
			// SoftBevelBorder(SoftBevelBorder.RAISED));
			textField.setHorizontalAlignment(SwingConstants.RIGHT);
			this.add(textField);
			columnList.add(textField);
		}
	}

	public void refreshFooterFiled() {
		if (tableHeader != null && getTableModel() != null) {
			int columnCount = tableHeader.getColumnModel().getColumnCount();
			if (columnCount > 0) {
				if (columnList.size() != columnCount) {
					createFooterField(columnCount);
				}
			}
			for (int i = 0; i < columnCount; i++) {
				TableColumn tableColumn = tableHeader.getColumnModel()
						.getColumn(i);
				JLabel textField = (JLabel) columnList.get(i);
				textField.setPreferredSize(new Dimension(tableColumn
						.getPreferredWidth()
						- gap, this.getPreferredSize().height));
			}
			this.revalidate();
		}
	}

	public void statisticTableFooterInfo() {
		if (footerMap == null || footerMap.isEmpty()) {
			return;
		}
		if (tableHeader != null && getTableModel() != null) {
			refreshFooterFiled();
			Map<Integer, Object> mapValue = new HashMap<Integer, Object>();
			int columnCount = tableHeader.getColumnModel().getColumnCount();
			Map<Integer, Set> tempMapSet = new HashMap<Integer, Set>();
			if (isStatisticSelectedData
					&& tableHeader.getTable().getSelectedRows().length > 0) {
				int[] rows = tableHeader.getTable().getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					for (int j = 0; j < columnCount; j++) {
						TableFooterType footerType = footerMap.get(j);
						statisticFooterValue(j, mapValue, tempMapSet, rows[i],
								footerType);
					}
				}
			} else {
				int rowCount = getTableModel().getRowCount();
				for (int i = 0; i < rowCount; i++) {
					for (int j = 0; j < columnCount; j++) {
						TableFooterType footerType = footerMap.get(j);
						statisticFooterValue(j, mapValue, tempMapSet, i,
								footerType);
					}
				}
			}
			for (int i = 0; i < columnList.size(); i++) {
				showFooterValue(mapValue, i);
			}
		}
		this.validate();
	}

	public void setStatisticSelectedData(boolean isStatisticSelectedData) {
		this.isStatisticSelectedData = isStatisticSelectedData;
		final JTable table = tableHeader.getTable();
		if (isStatisticSelectedData) {
			if (listSelectionListener == null) {
				listSelectionListener = new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) table
								.getModel();
						if (tableModel == null) {
							return;
						}

						try {
							if (tableModel.getCurrentRow() != null) {
								statisticTableFooterInfo();
							}
						} catch (Exception cx) {

						}
					}
				};
				table.getSelectionModel().addListSelectionListener(
						listSelectionListener);
			}
		} else {
			if (listSelectionListener != null) {
				table.getSelectionModel().removeListSelectionListener(
						listSelectionListener);
				listSelectionListener=null;
			}
		}
		statisticTableFooterInfo();
	}

	private void showFooterValue(Map<Integer, Object> mapValue, int i) {
		TableFooterType footerType = footerMap.get(i);
		JLabel textField = (JLabel) columnList.get(i);
		TableColumn tableColumn = tableHeader.getColumnModel().getColumn(i);
		String columnCaption = (tableColumn.getHeaderValue() == null ? ""
				: tableColumn.getHeaderValue().toString())
				+ "的";
		if (footerType != null) {
			if (footerType.getType() == TableFooterType.SUM) {
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix())
						+ (mapValue.get(i) == null ? "" : mapValue.get(i)
								.toString()));
				textField.setToolTipText(columnCaption
						+ "总计值："
						+ (mapValue.get(i) == null ? "" : mapValue.get(i)
								.toString()));
			} else if (footerType.getType() == TableFooterType.AVG) {
				double totalValue = mapValue.get(i) == null ? 0.0 : Double
						.parseDouble(mapValue.get(i).toString());
				double avgValue = 0.0;
				if (getTableModel().getRowCount() > 0) {
					avgValue = totalValue
							/ Double.valueOf(getTableModel().getRowCount());
				}
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix())
						+ avgValue);
				textField.setToolTipText(columnCaption + "平均值：" + avgValue);
			} else if (footerType.getType() == TableFooterType.COUNT) {
				int count = mapValue.get(i) == null ? 0 : Integer
						.parseInt(mapValue.get(i).toString());
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix())
						+ count);
				textField.setToolTipText(columnCaption + "个数(去掉重复值)：" + count);
			} else if (footerType.getType() == TableFooterType.MAX) {
				String obj = mapValue.get(i) == null ? "" : mapValue.get(i)
						.toString();
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix())
						+ obj);
				textField.setToolTipText(columnCaption + "最大值：" + obj);
			} else if (footerType.getType() == TableFooterType.MIN) {
				String obj = mapValue.get(i) == null ? "" : mapValue.get(i)
						.toString();
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix())
						+ obj);
				textField.setToolTipText(columnCaption + "最小值：" + obj);
			} else if (footerType.getType() == TableFooterType.CONSTANT) {
				textField.setText((footerType.getPreFix() == null ? ""
						: footerType.getPreFix()));
			}
		} else {
			textField.setText("");
		}
	}

	private void statisticTableFooterInfo(int columnNo) {
		if (footerMap == null || footerMap.isEmpty()) {
			return;
		}
		if (tableHeader != null && getTableModel() != null) {
			Map<Integer, Object> mapValue = new HashMap<Integer, Object>();
			Map<Integer, Set> tempMapSet = new HashMap<Integer, Set>();
			for (int i = 0; i < getTableModel().getRowCount(); i++) {
				TableFooterType footerType = footerMap.get(columnNo);
				statisticFooterValue(columnNo, mapValue, tempMapSet, i,
						footerType);
			}
			showFooterValue(mapValue, columnNo);
		}
	}

	/**
	 * 截取字符串中后面的0
	 * 
	 */
	public String getString(String str) {
		String s = ".";
		String xx = "";
		if (str.indexOf(s) > 0) {
			Integer h = str.indexOf(s);
			String yy = str.substring(h + 1, str.length());
			Integer ss = getInteger(yy);
			xx = str.substring(0, h) + "." + yy.substring(0, ss + 1);
		} else {
			xx = "0.0";
		}
		return xx;
	}

	public Integer getInteger(String str) {
		Integer hh = 0;
		char[] rr = str.toCharArray();
		for (int i = 1; i < rr.length + 1; i++) {
			if (rr[rr.length - i] != '0') {
				hh = rr.length - i;
				return hh;
			}
		}
		return hh;
	}

	private void statisticFooterValue(int columnNo,
			Map<Integer, Object> mapValue, Map<Integer, Set> tempMapSet, int i,
			TableFooterType footerType) {
		if (footerType != null) {
			int fractionCount = footerType.getFractionCount();
			Object value = getTableModel().getValueAt(i, columnNo);
			if (footerType.getType() == TableFooterType.SUM
					|| footerType.getType() == TableFooterType.AVG) {
				String num = "0.0";
				try {
					num = value.toString();
				} catch (Exception ex) {

				}
				if (num.equals("") || num == null) {
					num = "0.0";
				}
				String preNum = (mapValue.get(columnNo) == null ? "0.0"
						: mapValue.get(columnNo).toString());
				Double db = Double.parseDouble(num)
						+ Double.parseDouble(preNum);
				BigDecimal bdpreNum = new BigDecimal(db);
				String dbpreNumString = getString(bdpreNum.setScale(
						fractionCount, BigDecimal.ROUND_HALF_UP).toString());
				mapValue.put(columnNo, dbpreNumString);
			} else if (footerType.getType() == TableFooterType.COUNT) {
				Set tempSet = tempMapSet.get(columnNo);
				if (tempSet == null) {
					tempSet = new HashSet();
					tempMapSet.put(columnNo, tempSet);
				}
				tempSet.add(value);
				mapValue.put(columnNo, tempSet.size());
			} else if (footerType.getType() == TableFooterType.MAX) {
				if (value != null) {
					Object tempValue = mapValue.get(columnNo);
					if (tempValue == null) {
						tempValue = value;
					} else if (value instanceof Comparable) {
						if (((Comparable) value).compareTo(tempValue) > 0) {
							tempValue = value;
						}
					}
					mapValue.put(columnNo, tempValue);
				}
			} else if (footerType.getType() == TableFooterType.MIN) {
				if (value != null) {
					Object tempValue = mapValue.get(columnNo);
					if (tempValue == null) {
						tempValue = value;
					} else if (value instanceof Comparable) {
						if (((Comparable) value).compareTo(tempValue) < 0) {
							tempValue = value;
						}
					}
					mapValue.put(columnNo, tempValue);
				}
			}
		}
	}

	public Map getFooterTypeInfos() {
		return footerMap;
	}

	public void addFooterTypeInfo(TableFooterType footerType) {
		if (footerType != null && footerType.getColumnNo() != null) {
			footerMap.put(footerType.getColumnNo(), footerType);
			Container gp = tableHeader.getTable().getParent().getParent();
			gp.doLayout();
			statisticTableFooterInfo(footerType.getColumnNo());
		}
	}

	public void removeFooterTypeInfo(Integer columnNo) {
		if (columnNo != null) {
			footerMap.remove(columnNo);
			Container gp = tableHeader.getTable().getParent().getParent();
			gp.doLayout();
			JLabel textField = (JLabel) columnList.get(columnNo);
			if (textField != null) {
				textField.setText("");
			}
		}
	}

	public void clearFooterTypeInfo() {
		footerMap.clear();
		Container gp = tableHeader.getTable().getParent().getParent();
		gp.doLayout();
		for (int i = 0; i < columnList.size(); i++) {
			JLabel textField = (JLabel) columnList.get(i);
			if (textField != null) {
				textField.setText("");
			}
		}

	}

	private TableModel getTableModel() {
		return tableHeader.getTable().getModel();
	}

	// @Override
	// protected void paintComponent(Graphics g) {
	// //
	// super.paintComponent(g);
	// Graphics2D g2 = (Graphics2D) g;
	// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	// JTable table = (JTable) tableHeader.getTable();
	// table.getGridColor();
	// Color drawColor = table.getGridColor();
	// BasicStroke stroke = new BasicStroke(1f);
	// g2.setPaint(drawColor);
	// g2.setStroke(stroke);
	// paintLine(g2);
	// }
	//
	@Override
	public void paint(Graphics g) {
		//
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		JTable table = (JTable) tableHeader.getTable();
		// Color q =table.get();
		Color drawColor = table.getGridColor();
		BasicStroke stroke = new BasicStroke(1f);
		// ,BasicStroke.CAP_ROUND,
		// BasicStroke.JOIN_ROUND, 0, new float[]{0,6,0,6}, 0
		g2.setPaint(drawColor);
		g2.setStroke(stroke);
		paintLine(g2);
	}

	private void paintLine(Graphics2D g) {
		JTable table = tableHeader.getTable();
		Dimension d = getSize();
		// Point vp = ((JViewport) this.getParent()).getViewPosition();
		double lby = 0;
		double lbx = -1;// -vp.x - 1
		double lex = -1;// -vp.x - 1
		double ley = d.getHeight();
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(i);
			lbx += tableColumn.getPreferredWidth();
			lex += tableColumn.getPreferredWidth();
			g.draw(new Line2D.Double(lbx, lby, lex, ley));
		}
		g.draw(new Line2D.Double(0, 0, tableHeader.getWidth(), 0));
		g.draw(new Line2D.Double(0, d.getHeight(), tableHeader.getWidth(), d
				.getHeight()));
		g.setPaint(Color.RED);
		g.setStroke(new BasicStroke(2.5f));
		lbx = 0;// -vp.x - 1
		lex = -1;// -vp.x - 1
		lby = d.getHeight();
		ley = d.getHeight();
		if (footerMap != null && !footerMap.isEmpty()) {
			for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
				TableColumn tableColumn = table.getColumnModel().getColumn(i);
				if (footerMap.get(i) != null) {
					g.draw(new Line2D.Double(lbx, lby, lex
							+ tableColumn.getPreferredWidth() - 2, ley));
				}
				lbx += tableColumn.getPreferredWidth();
				lex += tableColumn.getPreferredWidth();
			}
		}
	}
}
