/*
 * Created on 2005-5-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsUnitWear;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgShowEmsUnitWaste extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JToolBar jToolBar = null;
	private JButton btnUpBatch = null;
	private JButton btnNextBatch = null;
	private JButton btnOverprint = null;
	private JButton btnNotOverprint = null;
	private JButton btnExit = null;
	private ManualDeclareAction manualDeclearAction = null;
	private JTableListModel tableModel = null;

	private int pageNum = 0;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JButton jButton = null;
	List list = new Vector();
	private EmsHeadH2k head = null; // @jve:decl-index=0:
	private JButton jButton1 = null;
	private JCheckBox jCheckBox = null;
	private JCheckBox jCheckBox1 = null;
	private JButton jButton2 = null;

	private JButton buttonQuery2 = null;

	private String queryTable = "1";

	/**
	 * This is the default constructor
	 */
	public DgShowEmsUnitWaste() {
		super();
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		jScrollPane.setViewportView(jTable);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("成品对应料件单耗表");
		this.setSize(883, 520);
		this.setContentPane(getJContentPane());
	}

	public void setVisible(boolean isShow) {
		if (isShow) {
			initTable(new Vector());
		}
		super.setVisible(isShow);
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnUpBatch());
			jToolBar.add(getBtnNextBatch());
			jToolBar.add(getBtnOverprint());
			jToolBar.add(getBtnNotOverprint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpBatch() {
		if (btnUpBatch == null) {
			btnUpBatch = new JButton();
			btnUpBatch.setText("上一批成品");
			btnUpBatch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isBlankOrNotNumbericOrValueError(jTextField,
							jTextField1)) {

						return;
					}

					if (list != null && !list.isEmpty()) {

						if (list.get(0) instanceof EmsUnitWear) {

							EmsUnitWear obj = (EmsUnitWear) list.get(2);
							if (obj.getDm102() == null) {
								return;
							}

						}

					}

					pageNum = pageNum - 1;

					// 直接处理如果没有上一批
					if (pageNum == 0) {

						pageNum = 1;

						return;

					}

					// 判断使用 展示单耗方式
					if (queryTable.equals("1")) {

						new find(pageNum, 0).start();

					} else {

						new find(pageNum, 1).start();

					}
				}
			});
		}
		return btnUpBatch;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNextBatch() {
		if (btnNextBatch == null) {
			btnNextBatch = new JButton();
			btnNextBatch.setText("下一批成品");
			btnNextBatch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isBlankOrNotNumbericOrValueError(jTextField,
							jTextField1)) {

						return;
					}

					if (list != null && !list.isEmpty()) {

						if (list.get(0) instanceof EmsUnitWear) {

							EmsUnitWear obj = (EmsUnitWear) list.get(2);

							if (obj.getDm102() == null) {

								return;
							}

						}

					} else if (list.isEmpty()) {

						return;

					}

					pageNum = pageNum + 1;

					// 判断使用 展示单耗方式
					if (queryTable.equals("1")) {

						new find(pageNum, 0).start();

					} else {

						new find(pageNum, 1).start();

					}

				}
			});
		}
		return btnNextBatch;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOverprint() {
		if (btnOverprint == null) {
			btnOverprint = new JButton();
			btnOverprint.setVisible(false);
			btnOverprint.setText("套打单耗表");
			btnOverprint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// print(true);
				}
			});
		}
		return btnOverprint;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotOverprint() {
		if (btnNotOverprint == null) {
			btnNotOverprint = new JButton();
			btnNotOverprint.setVisible(false);
			btnNotOverprint.setText("非套打单耗表");
			btnNotOverprint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// print(false);
						}
					});
		}
		return btnNotOverprint;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgShowEmsUnitWaste.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化成品物料单耗表(新版)
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn(" ", "seqNum", 40));
						list.add(addColumn(" ", "name", 200));
						list.add(addColumn(" ", "dm11", 80));
						list.add(addColumn(" ", "dm12", 80));
						list.add(addColumn(" ", "dm13", 80));
						list.add(addColumn(" ", "dm14", 80));

						list.add(addColumn(" ", "dm21", 80));
						list.add(addColumn(" ", "dm22", 80));
						list.add(addColumn(" ", "dm23", 80));
						list.add(addColumn(" ", "dm24", 80));

						list.add(addColumn(" ", "dm31", 80));
						list.add(addColumn(" ", "dm32", 80));
						list.add(addColumn(" ", "dm33", 80));
						list.add(addColumn(" ", "dm34", 80));

						list.add(addColumn(" ", "dm41", 80));
						list.add(addColumn(" ", "dm42", 80));
						list.add(addColumn(" ", "dm43", 80));
						list.add(addColumn(" ", "dm44", 80));

						list.add(addColumn(" ", "dm51", 80));
						list.add(addColumn(" ", "dm52", 80));
						list.add(addColumn(" ", "dm53", 80));
						list.add(addColumn(" ", "dm54", 80));

						list.add(addColumn(" ", "dm61", 80));
						list.add(addColumn(" ", "dm62", 80));
						list.add(addColumn(" ", "dm63", 80));
						list.add(addColumn(" ", "dm64", 80));

						list.add(addColumn(" ", "dm71", 80));
						list.add(addColumn(" ", "dm72", 80));
						list.add(addColumn(" ", "dm73", 80));
						list.add(addColumn(" ", "dm74", 80));

						list.add(addColumn(" ", "dm81", 80));
						list.add(addColumn(" ", "dm82", 80));
						list.add(addColumn(" ", "dm83", 80));
						list.add(addColumn(" ", "dm84", 80));

						list.add(addColumn(" ", "dm91", 80));
						list.add(addColumn(" ", "dm92", 80));
						list.add(addColumn(" ", "dm93", 80));
						list.add(addColumn(" ", "dm94", 80));

						list.add(addColumn(" ", "dm101", 80));
						list.add(addColumn(" ", "dm102", 80));
						list.add(addColumn(" ", "dm103", 80));
						list.add(addColumn(" ", "dm104", 80));
						return list;
					}
				});

		// 删除 序列流水列
		jTable.getColumnModel().removeColumn(
				jTable.getColumnModel().getColumn(0));

	}

	/**
	 * 查询成品单耗表 经典版
	 * 
	 * @param list
	 */
	private void initTable2(List list) {

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {

					@Override
					public List<JTableListColumn> InitColumns() {

						List<JTableListColumn> columns = new Vector<JTableListColumn>();

						columns.add(addColumn("成品序号",
								"emsHeadH2kVersion.emsHeadH2kExg.seqNum", 50));

						columns.add(addColumn("版本号",
								"emsHeadH2kVersion.version", 50));

						columns.add(addColumn("成品名称",
								"emsHeadH2kVersion.emsHeadH2kExg.name", 120));

						columns.add(addColumn("料件序号", "seqNum", 50));

						columns.add(addColumn("料件名称", "name", 120));

						columns.add(addColumn("料件规格", "spec", 120));

						columns.add(addColumn("单耗", "unitWear", 80));

						columns.add(addColumn("损耗", "wear", 80));

						return columns;
					}
				});

	}

	/**
	 * 打印报表
	 * 
	 * @param isOverprint
	 */
	private void print(boolean isOverprint) {
		try {
			/*
			 * List list = new ArrayList(); String emsNo = ""; if (contract !=
			 * null) { String parentId = contract.getId(); list =
			 * contractAction.findContractUnitWaste(new Request(
			 * CommonVars.getCurrUser()), parentId, -1, -1); emsNo =
			 * contract.getExpContractNo() == null ? "" : contract
			 * .getExpContractNo(); } Map<String, Object> parameters = new
			 * HashMap<String, Object>(); parameters.put("emsNo", emsNo);
			 * parameters.put("isOverprint", new Boolean(isOverprint));
			 * parameters.put("companyName", contract.getMachName());
			 * parameters.put("count", count); CustomReportDataSource ds = new
			 * CustomReportDataSource(list); InputStream masterReportStream =
			 * DgContract.class
			 * .getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			 * JasperPrint jasperPrint = JasperFillManager.fillReport(
			 * masterReportStream, parameters, ds); DgReportViewer viewer = new
			 * DgReportViewer(jasperPrint); viewer.setVisible(true);
			 */
		} catch (Exception ex) {

		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(6, 5, 54, 20);
			jLabel.setText("成品序号");
			jLabel1.setBounds(126, 6, 19, 20);
			jLabel1.setText("止");
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButtonQuery1(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJCheckBox1(), null);
			jPanel.add(getJButton2(), null);

			// 查询单耗表 2 （旧版）
			jPanel.add(getJButtonQuery2(), null);

		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(61, 4, 58, 23);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(152, 4, 60, 23);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonQuery1() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setToolTipText("查询单耗表(新)");
			jButton.setBounds(218, 4, 66, 21);
			jButton.setText("查询1");

			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isBlankOrNotNumbericOrValueError(jTextField,
							jTextField1)) {

						return;
					}

					pageNum = 1;

					new find(pageNum, 0).start();

				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonQuery2() {
		if (buttonQuery2 == null) {
			buttonQuery2 = new JButton();
			buttonQuery2.setToolTipText("查询单耗表(经典)");
			buttonQuery2.setBounds(290, 4, 66, 21);
			buttonQuery2.setText("查询2");

			buttonQuery2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 判断
					if (isBlankOrNotNumbericOrValueError(jTextField,
							jTextField1)) {

						return;
					}

					String beginSeq = jTextField.getText();

					String endSeq = jTextField1.getText();

					// 差值
					int dvalue = Integer.valueOf(endSeq)
							- Integer.valueOf(beginSeq);

					// 开始序号 不能 大于等于 结束序号 否则就提示一下用户
					if (dvalue < 0) {

						return;

					}

					pageNum = 1;

					new find(pageNum, 1).start();

				}
			});
		}
		return buttonQuery2;
	}

	class find extends Thread {

		private int go;

		private int pageNum = 0;

		public find(int pageNum, int go) {
			this.pageNum = pageNum;
			this.go = go;
		}

		public void run() {

			try {

				CommonProgress.showProgressDialog(DgShowEmsUnitWaste.this);

				CommonProgress.setMessage("系统正获取数据，请稍后...");

				switch (go) {

				case 0:

					queryTable = "1";

					list = manualDeclearAction.getEmsUnitWearList(new Request(
							CommonVars.getCurrUser()), jTextField.getText(),
							jTextField1.getText(), pageNum, head);

					break;

				case 1:

					// 改变 展示方式
					queryTable = "2";

					String begin = jTextField.getText();

					String end = jTextField1.getText();

					list = manualDeclearAction
							.finEmsHeadH2KBomFromBeginSeqToEndSeq(new Request(
									CommonVars.getCurrUser()), begin, end,
									pageNum, head);

					if (list == null) {

						list = Collections.EMPTY_LIST;

					}

					break;

				// 默认使用 查询 一 的方法
				default:

					queryTable = "1";

					list = manualDeclearAction.getEmsUnitWearList(new Request(
							CommonVars.getCurrUser()), jTextField.getText(),
							jTextField1.getText(), pageNum, head);
					break;
				}

				CommonProgress.closeProgressDialog();

			} catch (Exception e) {

				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(DgShowEmsUnitWaste.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);

			} finally {

				if (go == 1) {

					initTable2(list);

				} else if (go == 0) {

					initTable(list);

				}
			}
		}
	}

	public void setHead(EmsHeadH2k head) {
		this.head = head;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(363, 4, 66, 21));
			jButton1.setText("下载");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTextField.getText().equals("")) {
						return;
					}
					if (jTextField1.getText().equals("")) {
						return;
					}
					new saveListToFile().start();
				}
			});
		}
		return jButton1;
	}

	class saveListToFile extends Thread {

		public void run() {
			String filePath = "";
			try {
				JFileChooser fileChooser = new JFileChooser("./");
				fileChooser.removeChoosableFileFilter(fileChooser
						.getFileFilter());
				fileChooser.setFileFilter(new ExampleFileFilter("xls"));
				// fileChooser
				// .addChoosableFileFilter(new ExampleFileFilter("xls"));
				// fileChooser
				// .addChoosableFileFilter(new ExampleFileFilter("txt"));
				int state = fileChooser.showSaveDialog(DgShowEmsUnitWaste.this);
				if (state == JFileChooser.APPROVE_OPTION) {
					CommonProgress.showProgressDialog(DgShowEmsUnitWaste.this);
					CommonProgress.setMessage("系统正在下载单耗表，请稍后...");
					File f = fileChooser.getSelectedFile();
					String description = fileChooser.getFileFilter()
							.getDescription();
					String suffix = description.substring(description
							.indexOf("."));
					boolean isftoj = true;
					if (jCheckBox.isSelected()) {
						isftoj = true;
					} else {
						isftoj = false;
					}
					List list = CustomBaseList.getInstance().getGbtobigs();
					List dataSource = manualDeclearAction.downLoadUnitWear(
							new Request(CommonVars.getCurrUser()), jTextField
									.getText().trim(), jTextField1.getText()
									.trim(), head, isftoj, list);

					filePath = f.getPath() + "_" + "UnitWear" + "_" + suffix;

					HSSFWorkbook wb = new HSSFWorkbook(); // 建立新HSSFWorkbook对象
					for (int k = 0; k < dataSource.size(); k++) {
						List lss = (List) dataSource.get(k);
						crtReceiptClassImportTemplate(lss, filePath,
								"成品对应料件单耗表" + "-" + k, null, wb, k);
					}
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
			} finally {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgShowEmsUnitWaste.this,
						"下载完成！\n路径：" + filePath + "\n"
								+ "打开方式请选用Microsoft Excel", "提示", 2);
			}
		}
	}

	// 导出Excel
	public boolean crtReceiptClassImportTemplate(List list, String filename,
			String sheetname, String interfacetype, HSSFWorkbook wb, int k) {
		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(k, sheetname, HSSFWorkbook.ENCODING_UTF_16);
		sheet.setDefaultColumnWidth((short) 10);
		// 样式2 宋体 写正文时用
		HSSFFont font2 = wb.createFont();
		font2.setColor(HSSFFont.COLOR_NORMAL);
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 12);
		HSSFCellStyle normal = wb.createCellStyle();
		normal.setFont(font2);
		normal.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP); // 垂直居中
		// normal.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
		HSSFRow row;
		HSSFCell cell;
		HSSFCell csCell;

		int maxColumn = 0;
		for (int tmpi = 0; tmpi < list.size(); tmpi++) {
			List ls = (List) list.get(tmpi);
			int maxValue = ls.size();
			System.out.println(maxValue);
			if (maxValue > maxColumn) {
				maxColumn = maxValue;
			}
		}

		for (int tmpi = 0; tmpi < list.size(); tmpi++) {
			try {
				row = sheet.createRow((short) tmpi);
				List ls = (List) list.get(tmpi);
				int column = ls.size();
				for (int ii = 0; ii < maxColumn; ii++) {
					if (ii < column) {
						cell = row.createCell((short) ii); // 建立新cell
						cell.setCellStyle(normal);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断

						String str = (String) ls.get(ii);

						if (tmpi > 3 && ii > 1) {
							if (str == null
									|| (str != null && str.trim().equals(""))) {
								str = "0.0";
							}
						}

						if (isDigit(str)) {
							Double strdouble = Double.valueOf(str);
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellValue(strdouble);
						} else {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(str);
						}
					} else {
						if (tmpi > 3 && ii > 1) {
							cell = row.createCell((short) ii); // 建立新cell
							cell.setCellStyle(normal);
							cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellValue(0.0);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e1) {
			return false;
		}
		return true;
	}

	// 检查是不是数字型
	private boolean isDigit(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		int d = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (!Character.isDigit(c[i])) {
				if ((c[i] + "").equals(".")) {
					d++;
				} else
					return false;
			}
		}
		if (d < 2) {
			return true;
		} else {
			return false;
		}
	}

	class ExampleFileFilter extends FileFilter {
		private List list = new ArrayList();

		ExampleFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}

	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(535, 5, 66, 21));
			jCheckBox.setText("繁转简");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(603, 5, 62, 21));
			jCheckBox1.setText("简转繁");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(436, 4, 93, 21));
			jButton2.setText("同步成品");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new reWriteCpPrice().start();
				}
			});
		}
		return jButton2;
	}

	class reWriteCpPrice extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgShowEmsUnitWaste.this);
				CommonProgress.setMessage("系统正反写成品单价到成品版本中，请稍后...");
				manualDeclearAction.rewriteEmsHeadH2k(
						new Request(CommonVars.getCurrUser()),
						tableModel.getList(), head);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgShowEmsUnitWaste.this,
						"计算完毕,请刷新版本！", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgShowEmsUnitWaste.this,
						"写数据失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 判断是否为 空 或者不是 数字型字符串 或者 起始序号 大于 结束序号
	 * 
	 * @param field1
	 * @param field2
	 * @return
	 */
	private boolean isBlankOrNotNumbericOrValueError(JTextField begin,
			JTextField end) {

		String beginSeq = begin.getText();

		String endSeq = end.getText();

		// 判断 : 不能是 空 或者 不是 数字
		if (StringUtils.isBlank(beginSeq) || StringUtils.isBlank(endSeq)
				|| !StringUtils.isNumeric(beginSeq)
				|| !StringUtils.isNumeric(endSeq)) {

			return true;

		}

		// 差值
		int dvalue = Integer.valueOf(endSeq) - Integer.valueOf(beginSeq);

		// 开始序号 不能 大于等于 结束序号 否则就提示一下用户
		if (dvalue < 0) {

			return true;

		}

		return false;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
