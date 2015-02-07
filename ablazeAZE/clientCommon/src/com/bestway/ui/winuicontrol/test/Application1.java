package com.bestway.ui.winuicontrol.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.CellSpan;
import com.bestway.client.util.mutispan.MultiSpanCellTable;

public class Application1 extends JFrame {
	boolean packFrame = false;

	//Construct the application
	public Application1() {
		//    FmMain frame = FmMain.getInstance();
		//    ShowFormControl.showForm(frame);

		List lista = new ArrayList();
		lista.add(new ttest("a1", "foo", "1", null, "ko", "zh"));
		lista.add(new ttest("a2", "bar", "2", "2", "fr", "pt"));
		lista.add(new ttest("a3", "bar", "3", "2", "fr", "pt"));
		lista.add(new ttest("a4", "bar", "4", null, "ko", "pt"));
		lista.add(new ttest("a5", "bar", "5", "2", "fr", "pt"));
		lista.add(new ttest("a6", "bar", "6", "2", "fr", "pt"));
		lista.add(new ttest("a7", "bar", "7", null, "ko", "pt"));
		lista.add(new ttest("a8", "bar", "8", null, "ko", "pt"));

		final MultiSpanCellTable table = new MultiSpanCellTable();
		AttributiveCellTableModel ml = new AttributiveCellTableModel(table,
				lista, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("SNo", "aa", 100));
						list.add(addColumn("1", "bb", 100));
						list.add(addColumn("2", "cc", 100));
						list.add(addColumn("Native", "dd", 100));
						list.add(addColumn("3", "ee", 100));
						list.add(addColumn("4", "ff", 100));
						return list;
					}
				});

		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Name");
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		ColumnGroup g_lang = new ColumnGroup("Language");
		g_lang.add(cm.getColumn(3));
		ColumnGroup g_other = new ColumnGroup("Others");
		g_other.add(cm.getColumn(4));
		g_other.add(cm.getColumn(5));
		g_lang.add(g_other);
		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);
		JScrollPane scroll = new JScrollPane(table);
		final CellSpan cellAtt = (CellSpan) ml.getCellAttribute();
		JButton b_one = new JButton("Combine");
		b_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] columns = table.getSelectedColumns();
				int[] rows = table.getSelectedRows();
//				cellAtt.combine(rows, columns);
//				table.clearSelection();
//				table.revalidate();
//				table.repaint();
				for(int i=0;i<rows.length;i++)
				{
					System.out.println(rows[i]);
				}
			}
		});
		JButton b_split = new JButton("Split");
		b_split.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				cellAtt.split(row, column);
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		});
		JPanel p_buttons = new JPanel();
		p_buttons.setLayout(new GridLayout(2, 1));
		p_buttons.add(b_one);
		p_buttons.add(b_split);

		Box box = new Box(BoxLayout.X_AXIS);
		box.add(scroll);
		box.add(new JSeparator(SwingConstants.HORIZONTAL));
		box.add(p_buttons);
		getContentPane().add(box);
		table.combineRows(new int[]{4,3},new int[]{4,5});
		setSize(400, 120);
	}

	//Main method
	public static void main(String[] args) {
		//    try {
		//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//    }
		//    catch(Exception e) {
		//      e.printStackTrace();
		//    }
		Application1 frame = new Application1();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}

}
