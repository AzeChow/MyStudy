package com.bestway.bls.client.blsinnermerge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings("serial")
public class DgCustomInnerMergeImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel pnFile = null; // @jve:decl-index=0:visual-constraint="596,19"

	private JTextField tfImportFile = null;

	private JButton jButton2 = null;

	private JPanel jPanel2 = null;

	private JLabel lbInfo = null;

	private JPanel pnNave = null;

	private JButton btnPre = null;

	private JButton btnNext = null;

	private JButton btnCancel = null;

	private JPanel pnContent = null;

	private JPanel pnCommInfo = null; // @jve:decl-index=0:visual-constraint="589,414"

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private int step = 0;

	private File file = null;

	private BcsInnerMergeAction bcsInnerMergeAction;

	private int fileColumnCount = 0;

	private JTableListModel tableModelCommInfo = null;
	
	private JTableListModel tableModelImportCommInfo = null;
	
	private JCheckBox cbIsTitle = null;

	private JLabel jLabel2 = null;
	
	private List listTemp = null;  //  @jve:decl-index=0:
	
	private List repeatList = null;  //  @jve:decl-index=0:
	
	private List<BcsInnerMerge> listResult = new ArrayList<BcsInnerMerge>();


	/**
	 * This method initializes
	 * 
	 */
	public DgCustomInnerMergeImport() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars.getApplicationContext()
				.getBean("bcsInnerMergeAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(562, 396));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("工厂单位折算导入");
		this.setContentPane(getJContentPane());
		//listTemp = tableModelCommInfo.getList();

	}

	public void setVisible(boolean b) {
		if (b) {
			step();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getPnNave(), null);
			jContentPane.add(getPnContent(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnFile() {
		if (pnFile == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(33, 107, 180, 22));
			jLabel2.setText("导入格式为:工厂料号|单位折算");
			pnFile = new JPanel();
			pnFile.setLayout(null);
			pnFile.setSize(new Dimension(442, 137));
			pnFile.add(getTfImportFile(), null);
			pnFile.add(getJButton2(), null);
			pnFile.add(getCbIsTitle(), null);
			pnFile.add(jLabel2, null);
		}
		return pnFile;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportFile() {
		if (tfImportFile == null) {
			tfImportFile = new JTextField();
			tfImportFile.setBounds(new Rectangle(33, 41, 306, 26));
		}
		return tfImportFile;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(340, 41, 24, 25));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgCustomInnerMergeImport.this);
					if (state == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						if (file.exists()) {
							tfImportFile.setText(file.getPath());
						}
						selectFile();
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(14, 19, 354, 40));
			lbInfo.setFont(new Font("Dialog", Font.BOLD, 18));
			lbInfo.setText("JLabel");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBackground(Color.white);
			jPanel2.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel2.setBounds(new Rectangle(1, 1, 552, 74));
			jPanel2.add(lbInfo, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNave() {
		if (pnNave == null) {
			pnNave = new JPanel();
			pnNave.setLayout(null);
			pnNave.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			pnNave.setBounds(new Rectangle(0, 308, 555, 53));
			pnNave.add(getBtnPre(), null);
			pnNave.add(getBtnNext(), null);
			pnNave.add(getBtnCancel(), null);
		}
		return pnNave;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPre() {
		if (btnPre == null) {
			btnPre = new JButton();
			btnPre.setBounds(new Rectangle(305, 13, 73, 27));
			btnPre.setText("上一步");
			btnPre.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
//					tableModelCommInfo.setList(listTemp);
					step();
				}
			});
		}
		return btnPre;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(381, 13, 74, 27));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (step == 1) {
//						tableModelImportCommInfo.setList(listTemp);
						List list = tableModelImportCommInfo.getList();
	
						Iterator itor = list.iterator();
						while(itor.hasNext()){
							BcsInnerMerge dimd = (BcsInnerMerge)itor.next();
							bcsInnerMergeAction.saveBcsInnerMerge(new Request(
								CommonVars.getCurrUser()), dimd);
						}
						dispose();
						JOptionPane.showMessageDialog(DgCustomInnerMergeImport.this,
								"资料已成功导入", "提示", JOptionPane.INFORMATION_MESSAGE);
//						tableModelCommInfo.updateRows(listSource);
					}
					step++;
					step();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(464, 13, 66, 27));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new JPanel();
			pnContent.setLayout(new BorderLayout());
			pnContent.setBounds(new Rectangle(1, 76, 554, 232));
		}
		return pnContent;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommInfo() {
		if (pnCommInfo == null) {
			pnCommInfo = new JPanel();
			pnCommInfo.setLayout(new BorderLayout());
			pnCommInfo.setSize(new Dimension(451, 182));
			pnCommInfo.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pnCommInfo;
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

	private void step() {
		this.btnNext.setText("下一步");
		pnContent.removeAll();
		if (step == 0) {
			lbInfo.setText("选择要导入的文件");
			pnContent.add(this.getPnFile(), BorderLayout.CENTER);
		} else if (step == 1) {
			if (file == null) {
				step--;
				step();
				return;
			}
			lbInfo.setText("导入数据预览");
			this.btnNext.setText("确定");
			pnContent.add(this.getPnCommInfo(), BorderLayout.CENTER);
			showCommInfoData();	
		}
		setState();
		pnContent.repaint();
	}

	private void setState() {
		this.btnPre.setEnabled(step > 0);
		this.btnNext.setEnabled(step < 3);
	}

	private void selectFile() {
		try {
			if (file.isDirectory() == true) {
				return;
			}
			String suffix = getSuffix(file);
			List list = new ArrayList();
			repeatList = new ArrayList();
			if (suffix != null) {
				if (suffix.toLowerCase().equals("txt")) {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String s = "";
					try {
						while((s = in.readLine())!=null){
							String[] strs = s.split(String.valueOf((char) 9));
							if(list.contains(strs[0])){
								if(!repeatList.contains(strs[0]))
									repeatList.add(strs[0]);
							}else{
								list.add(strs[0]);
							}
						}
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					// DgDzscCorrespondFile dialog = new DgDzscCorrespondFile();
					// dialog.setFileColumnCount(strs.length);
					// dialog.setSelectedValues(columnNo);
					// dialog.setVisible(true);
					// Hashtable ht = dialog
					// .getSelectedValues();
					// if (ht != null) {
					// columnNo = ht;
					// }
					try {
						in.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else if (suffix.toLowerCase().equals("xls")) {
					InputStream is = null;
					try {
						// 构建Workbook对象, 只读Workbook对象
						// 直接从本地文件创建Workbook
						// 从输入流创建Workbook
						is = new FileInputStream(file);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						int rowCount = rs.getRows();
						String[] strs = null;
						for (int i = 0; i < rowCount; i++) {
							if (cbIsTitle.isSelected() && i == 0) {
								continue;
							}
							Cell[] cell = rs.getRow(i);
							if (cell[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cell.length];
							for (int j = 0; j < cell.length; j++) {
								strs[j] = cell[j].getContents().trim();
							}
							if(list.contains(strs[0])){
								if(!repeatList.contains(strs[0]))
									repeatList.add(strs[0]);
							}else{
								list.add(strs[0]);
							}							
						}						
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					try {
						is.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
				if(repeatList.size()!=0){
					Iterator itor = repeatList.iterator();
					StringBuffer repeatNum = new StringBuffer();
					while(itor.hasNext()){
						repeatNum.append((String)itor.next()+",");
					}
					JOptionPane.showMessageDialog(DgCustomInnerMergeImport.this,
							"有如下料号在待导入资料中重复："+repeatNum.toString()+"请更改", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} else {
				return;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();
		return suffix;
	}

	private void showCommInfoData() {
		listResult.clear();
		parseFile(file);
		tableModelImportCommInfo = null;
		tableModelImportCommInfo = new JTableListModel(jTable, listResult,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("工厂料号", "materiel.ptNo", 100));
						list.add(addColumn("品名", "materiel.factoryName", 150));
						list.add(addColumn("规格型号", "materiel.factorySpec",150));
						list.add(addColumn("工厂单位", "materiel.calUnit.name",80));
						list.add(addColumn("申报单位", "materiel.ptUnit.name", 80));
						list.add(addColumn("单重", "materiel.ptNetWeight", 80));
						list.add(addColumn("单位折算", "unitConvert", 80));
						return list;
					}
				});
	}

	private void parseFile(File file) {
		List<BcsInnerMerge> list = new ArrayList<BcsInnerMerge>();
		//listTemp = tableModelTemp.getList();
		BufferedReader in;
		InputStream is = null;
		//ArrayList<TempImportAgreementCommInfo> list = new ArrayList<TempImportAgreementCommInfo>();
		String[] strs = null;
		String[] materielNameSpec;
		BcsInnerMerge bim = null;
		int row = 0;// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		try {
//			if (file.isDirectory() == true) {
//				return new ArrayList();
//			}
			String suffix = getSuffix(file);
			if (suffix != null) {
				if (suffix.toLowerCase().equals("txt")) {
					in = new BufferedReader(new FileReader(file));
					String s = new String();
					try {
						while ((s = in.readLine()) != null) {
							row++;
							if (cbIsTitle.isSelected() && row == 1) {
								continue;
							}
							if (s.trim().equals("")) {
								continue;
							}
							strs = s.split(String.valueOf((char) 9));
							convertToFileData(listTemp, strs);
							if(bim!=null){
								list.add(bim);
							}
						}
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					try {
						in.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else if (suffix.toLowerCase().equals("xls")) {
					try {
						// 构建Workbook对象, 只读Workbook对象
						// 直接从本地文件创建Workbook
						// 从输入流创建Workbook
						is = new FileInputStream(file);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						// int columnCount = rs.getColumns();
						int rowCount = rs.getRows();
						for (int i = 0; i < rowCount; i++) {
							if (cbIsTitle.isSelected() && i == 0) {
								continue;
							}
							Cell[] cell = rs.getRow(i);
							if (cell[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cell.length];
							for (int j = 0; j < cell.length; j++) {
								strs[j] = cell[j].getContents().trim();
						
							}
							//
							// to 简体
							//
						
							convertToFileData(listTemp, strs);

							
						}
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					try {
						is.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
//		return list;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(new Rectangle(32, 77, 145, 21));
			cbIsTitle.setText("第一行是否为标题行");
		}
		return cbIsTitle;
	}

	private BcsInnerMerge convertToFileData(List list,
			String[] strs) {
		// strs = changStrs(strs);
//		List<BcsInnerMerge> listResult = new ArrayList<BcsInnerMerge>();
		Iterator iterator = list.iterator();
		BcsInnerMerge dimd = null;
		while(iterator.hasNext()){
			dimd = (BcsInnerMerge)iterator.next();
		
			if(dimd.getMateriel().getPtNo().equals(strs[0]))//与工厂料号相等
				{
					
					dimd.setUnitConvert(Double.valueOf(strs[1]));
					listResult.add(dimd);
				}
//				if(dimd.getUnitConvert()!=0.0&&!repeatList.contains(strs[0])){			
//					if (JOptionPane.showConfirmDialog(
//							DgCustomInnerMergeImport.this, "工厂料号为 "+strs[0]+" 已存在单位折算，你确定要覆盖吗?",
//							"提示", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
//						dimd.setUnitConvert(Double.valueOf(strs[1]));
//						System.out.println(dimd.getMateriel().getPtNo());
//					}
//				}else{
//					dimd.setUnitConvert(Double.valueOf(strs[1]));
//					System.out.println(dimd.getMateriel().getPtNo());
//				}
//			listResult.add(dimd);
		}
		return dimd;
	}

	public JTableListModel getTableModelCommInfo() {
		return tableModelCommInfo;
	}

	public void setTableModelCommInfo(JTableListModel tableModelCommInfo) {
		this.tableModelCommInfo = tableModelCommInfo;
	}

	public List getListTemp() {
		return listTemp;
	}

	@SuppressWarnings("unchecked")
	public void setListTemp(List listTemp) {
		this.listTemp = new ArrayList();
		this.listTemp = listTemp;
	}

}
