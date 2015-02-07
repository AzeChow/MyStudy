package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.DgSaveTableListToExcel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DgEmsEdiMergerImport extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private List list = null;
	private JButton jButton2 = null;
	private File txtFile = null;
	private JRadioButton jRadioButton = null;
	private EmsEdiMergerHead emsEdiMergerHead = null; // @jve:decl-index=0:
	private EmsEdiMergerExgBefore emsEdiMergerExgBefore; // @jve:decl-index=0:
	private EmsEdiMergerExgAfter exgAfter = null; // @jve:decl-index=0:
	private ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction"); // @jve:decl-index=0:
	private JLabel jLabel = null;
	private List afterList = null;
	private JCheckBox jCheckBox = null;
	private JCheckBox cbCheckTitle = null;
	private JButton btnColumn = null;
	private JToolBar jToolBar = null;
//	private JCheckBox cbHeBing = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JCheckBox cbbUnicode = null;
	private JLabel jLabel51 = null;
	private JCheckBox cbToUpCase = null;
	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMergerImport() {
		super();
		initialize();
		InputTableColumnSetUtils.putColumn(InputTableColumnSet.EMSIMGER_BOM_INPUT, this	.getDefaultBomTableColumnList());
		list = new Vector();
		initTable(list);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.EMSIMGER_BOM_INPUT);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
	}

	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品货号(不为空)", "ptNo", 120));
		list.add(new JTableListColumn("版本号(不为空)", "version", 120,	Integer.class));
		list.add(new JTableListColumn("料件货号(不为空)", "bom.ptNo", 120));
		list.add(new JTableListColumn("单耗(不为空)", "bom.unitWaste", 100));
		list.add(new JTableListColumn("损耗率(%)", "bom.waste", 100));
		list.add(new JTableListColumn("企业版本号", "cmpVersion", 120,Integer.class));
		return list;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("归并关系BOM导入");
		this.setBounds(new Rectangle(0, 0, 807, 600));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setBounds(new Rectangle(9, 71, 231, 18));
			jLabel.setText("注意：未备案与有错误信息数据不可以导入!");
		}
		return jLabel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开");
			jButton.setPreferredSize(new Dimension(50, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(	new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser	.showOpenDialog(DgEmsEdiMergerImport.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().execute();
				}
			});

		}
		return jButton;
	}

	// private void infTojHsTable() {
	// if (gbHash == null) {
	// gbHash = new Hashtable();
	// List list = CustomBaseList.getInstance().getGbtobigs();
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// gbHash.put(gb.getBigname(),gb.getName());
	// }
	// }
	// }
	//    
	//    
	// private String changeStr(String s) { // 繁转简
	// String yy = "";
	// char[] xxx = s.toCharArray();
	// for (int i = 0; i < xxx.length; i++) {
	// String z = String.valueOf(xxx[i]);
	// if (String.valueOf(xxx[i]).getBytes().length == 2) {
	// if (gbHash.get(String.valueOf(xxx[i])) != null) {
	// z = (String) gbHash.get(String.valueOf(xxx[i]));
	// }
	// }
	// yy = yy + z;
	// }
	// return yy;
	// }

	/**
	 * 导入文件线程
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgEmsEdiMergerImport.this);
				CommonProgress.setMessage("系统正在读取与检验文件资料，请稍后...");
				afterList = parseTxtFile();
				// CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
				// afterList = manualdeclearAction.bomImport1(new Request( //
				// 明门需求
				// CommonVars.getCurrUser()), emsEdiMergerHead, list);
				CommonProgress.closeProgressDialog();
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				ex.printStackTrace();
				JOptionPane.showMessageDialog(DgEmsEdiMergerImport.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return afterList;
		}

		@Override
		protected void done() {
			List listValues = null;
			try {
				listValues = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			initTable(listValues);
		}
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	// class ImportFileDataRunnable extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog(DgEmsEdiMergerImport1.this);
	// CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
	// String shunHao = manualdeclearAction.getBpara(new Request(
	// CommonVars.getCurrUser()),
	// BcusParameter.EMSBOM_WEAR_DIGITS);
	// String danuDiat = manualdeclearAction.getBpara(new Request(
	// CommonVars.getCurrUser()),
	// BcusParameter.EMSBOM_UNITWEAR_DIGITS);
	// if ("".equals(shunHao) || "".equals(danuDiat)) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(DgEmsEdiMergerImport1.this,
	// "请在电子帐册参数设置中设置单耗与损耗的小数位", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// afterList = new ArrayList();
	// } else {
	// List list = parseTxtFile(shunHao, danuDiat);
	// CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
	// afterList = manualdeclearAction.bomImport1(new Request( // 明门需求
	// CommonVars.getCurrUser()), emsEdiMergerHead, list);
	// CommonProgress.closeProgressDialog();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// initTable(afterList);
	// String tishi = "以下为损耗超过50%\n\n";
	// for (int i = 0; i < tlist.size(); i++) {
	// tishi = tishi + ((String) tlist.get(i)) + "\n";
	// }
	// if (!tishi.equals("以下为损耗超过50%\n\n")) {
	// JOptionPane.showMessageDialog(DgEmsEdiMergerImport1.this,
	// tishi, "提示", 2);
	// }
	//
	// }
	// }
	// }

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	/**
	 * 导入xls文件
	 * @return
	 */
	private List parseTxtFile() {
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//	
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			//
			// 导入txt
			//
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		List list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.EMSIMGER_BOM_INPUT);
		int zcount = lsIndex.size();// 5; // 字段数目

		Map<String, Integer> mapVersion = new HashMap<String, Integer>();
		Map<String, Integer> mapPtNoVersion = new HashMap<String, Integer>();
		Map<String, Integer> mapPtNo = new HashMap<String, Integer>(); //成品货号
		Map<String, String> mapCmPtNo = new HashMap<String, String>();
		// int wasteDigits = Integer.parseInt(shunHao);// 损耗率小数位数
		// int unitWasteDigits = Integer.parseInt(danuDiat);// 单耗小数位数
	
		Integer seqNum = null;
		Integer imgSeqNum =null;
		String seqPtNo = null;//成品货号
		String imgPtNo = null;
		
		for (int i = 0; i < lines.size(); i++) {
			List line = lines.get(i);
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
					+ i + "】行,请稍等...");
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = (obj == null ? "" : obj.toString().trim());
			}
			EmsEdiMergerBomFrom obj = new EmsEdiMergerBomFrom();
			EmsEdiMergerExgBom bom = new EmsEdiMergerExgBom();
			StringBuffer err = new StringBuffer();
			
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("ptNo".equals(columnField)) {
					if (value == null || value.equals("")) {
						err.append("[").append(value).append("] 成品货号不可为空！");
						continue;
					}
					
					try {
						if(cbToUpCase.isSelected()){
							obj.setPtNo(value.trim().toUpperCase());
						}else{
							obj.setPtNo(value.trim());
						}
						EmsEdiMergerExgBefore exg= (EmsEdiMergerExgBefore)manualdeclearAction
								.findEmsEdiMergerAfterByGNo(new Request(CommonVars.getCurrUser()), false, obj.getPtNo(), emsEdiMergerHead);
						if(exg!=null){
							seqNum = exg.getSeqNum();
							seqPtNo = exg.getPtNo();
						}
						
						
						if (seqPtNo == null||!seqPtNo.equals(obj.getPtNo())) {
							err.append("成品货号[").append(value).append("] 在归并关系中不存在！");
							continue;
						}
						
						
						
					} catch (Exception e) {
						err.append("[").append(value).append("] 成品货号不合法!");
						continue;
					}
					
					
				} else if ("version".equals(columnField)) {// 海关版本
					if (value == null || value.equals("")) {
						continue;
					}
					try {
						obj.setVersion(Integer.valueOf(value));
						mapPtNo.put(obj.getPtNo(), obj.getVersion());
					} catch (Exception e) {
						err.append("[").append(value).append("] 版本不正确！");
						continue;
					}
					if (obj.getPtNo() != null && !"".equals(obj.getPtNo())) {
						List mulVersionlist = manualdeclearAction
								.findExistVersionNew(new Request(CommonVars
										.getCurrUser()), obj.getPtNo(), value);
						if (mulVersionlist != null && mulVersionlist.size() > 0) {
							err.append("成品货号：【").append(obj.getPtNo()).append(
									"】海关版本号：【").append(obj.getVersion()).append(
									"】已经存在。");
							continue;
						}
						if (seqNum != null) {
							List lsit = manualdeclearAction
									.checkExgCommdityForbidBySeqNum(
											new Request(CommonVars
													.getCurrUser()),
											MaterielType.FINISHED_PRODUCT,
											seqNum, obj.getVersion());
							if (lsit != null && lsit.size() > 0) {
								err.append("成品货号[").append(value).append("]对应的备案号[").append(seqNum).append("]与版本号[")
								.append(obj.getVersion()).append("]已经禁用!");
								continue;
							}
						}
					}
					//如果存在海关版本就读取海关版本
					if(value !=null || !value.equals("")){
						List bomList =manualdeclearAction.findManualDescBom(new Request(CommonVars.getCurrUser()),seqPtNo,Integer.valueOf(value));
						if(bomList.size()>0){
							err.append("成品货号【").append(seqPtNo).append("】 导入归并前已备案，不允许新增或变更！");
							break;
						}
					}
					

				} else if ("cmpVersion".equals(columnField)) {// 企业版本
					obj.setCmpVersion(value == null ? "" : value.trim());
					
					// 检查同一个成品海关版本号与企业版本号不可同时存在
					if (mapPtNo.get(obj.getPtNo()) != null) {
						Integer ver = mapPtNo.get(obj.getPtNo());
						String cmver = (mapCmPtNo.get(obj.getPtNo()) == null || "".equals(mapCmPtNo.get(obj.getPtNo())) ? ""
								.equals(obj.getCmpVersion()) ? "" : obj
								.getCmpVersion() : mapCmPtNo.get(obj.getPtNo()));
						if (ver != null && !"".equals(cmver)) {
							err.append("[").append(obj.getPtNo()).append("] 同一个成品海关版本号与企业版本号不可同时存在！");
							continue;
						}
						
					}
										
					if (value != null && !"".equals(value)) {
						mapCmPtNo.put(obj.getPtNo(), obj.getCmpVersion());
					}									
					if (value != null && !"".equals(value)) {
						if (obj.getVersion() == null) {
							// 如果企业版本存在而海关版本不存在执行下面的，如果企业版本存在而海关版本也存在，则不执行下面
							if (obj.getPtNo() != null
									&& !"".equals(obj.getPtNo())) {
								Integer ver = manualdeclearAction.getVersionByCmpVersion(new Request(
												CommonVars.getCurrUser()), obj
												.getPtNo(), obj.getCmpVersion()
												.trim());
								if (ver == -1) {// 未找到
									if (mapPtNoVersion.get(obj.getPtNo()) == null) {// 再到临时的Map里面找(未找到)
										Integer maxVer = manualdeclearAction
												.getMaxVersion(
														new Request(CommonVars
																.getCurrUser()),
														obj.getPtNo());
										obj.setVersion(maxVer);
										mapPtNoVersion.put(obj.getPtNo(),
												maxVer);
										mapVersion.put(obj.getPtNo() + "/"
												+ obj.getCmpVersion(), maxVer);
									} else {
										if (mapVersion.get(obj.getPtNo() + "/"
												+ obj.getCmpVersion()) != null) {
											Integer ver1 = mapVersion
													.get(obj.getPtNo()
															+ "/"
															+ obj
																	.getCmpVersion());
											obj.setVersion(ver1);
										} else {
											Integer ver1 = mapPtNoVersion.get(obj.getPtNo()) + 1;
											obj.setVersion(ver1);
											mapPtNoVersion.put(obj.getPtNo(),	ver1);
											mapVersion.put(obj.getPtNo()+ "/"+ obj.getCmpVersion(),	ver1);
										}
									}
								} else {// 找到
									obj.setVersion(ver);
									List bomList =manualdeclearAction.findManualDescBom(new Request(CommonVars.getCurrUser()),seqPtNo,ver);
									if(bomList.size()>0){
										err.append("成品货号【").append(seqPtNo).append("】 导入归并前已备案，不允许新增或变更！");
										continue;
									}
								}
							} else {
								err.append("[").append(value).append("]  海关版本号不可为空！");
							}
							continue;
						}
					} else {
						if (obj.getVersion() == null) {
							err.append("[").append(value).append("] 海关版本号不可为空！");
							continue;
						}
					}
				} else if ("bom.ptNo".equals(columnField)) {
					if (value == null || value.equals("")) {
						err.append("[").append(value).append("] 料件货号不可为空！");
						continue;
					}
					try {
						if(cbToUpCase.isSelected()){
							bom.setPtNo(value.trim().toUpperCase());
						}else{
							bom.setPtNo(value.trim());
						}
						EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore)manualdeclearAction
								.findEmsEdiMergerAfterByGNo(new Request(
										CommonVars.getCurrUser()), true, bom
										.getPtNo(), emsEdiMergerHead);
						imgSeqNum = img.getSeqNum();
						imgPtNo = img.getPtNo();
						if(imgPtNo==null||!imgPtNo.equals(bom.getPtNo())){
								err.append("料件货号[").append(value).append("]  在归并关系中不存在！");
								
								continue;
						} else {
							List lsit = manualdeclearAction
									.checkImgCommdityForbidBySeqNum(
											new Request(CommonVars
													.getCurrUser()),
											MaterielType.MATERIEL, imgSeqNum);
							if (lsit != null && lsit.size() > 0) {
								err.append("料件货号[").append(value).append("]对应的备案号[").append(imgSeqNum).append("]已经禁用!");
								continue;
							}
						}
					} catch (Exception e) {
						err.append("[").append(value).append("]  料件货号不正确！");
						continue;
					}
				} else if ("bom.unitWaste".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					Double unitWaste = 0.0;
					try {
						unitWaste = Double.valueOf(value);
					} catch (Exception e) {
						err.append("[").append(value).append("]  单耗不正确！");
						continue;
					}

					if (cbbUnicode.isSelected()) {
						Double unitConvertExg = 0.0;
						Double unitConvert = 0.0;
						if (obj.getPtNo() != null && !"".equals(obj.getPtNo())) {
							Materiel materiel = manualdeclearAction
									.findInnerMergeDataByPtNo(new Request(
											CommonVars.getCurrUser()),
											MaterielType.FINISHED_PRODUCT, obj
													.getPtNo());
							Materiel materiel1 = manualdeclearAction
									.findInnerMergeDataByPtNo(new Request(
											CommonVars.getCurrUser()),
											MaterielType.MATERIEL, bom
													.getPtNo());
							if (materiel != null) {
								unitConvertExg = materiel.getUnitConvert() == null
										|| "".equals(materiel.getUnitConvert()) ? 0.0
										: materiel.getUnitConvert();
							}
							if (materiel1 != null) {
								unitConvert = materiel1.getUnitConvert() == null ? 0.0
										: materiel1.getUnitConvert();
							}
						}

						if (unitConvertExg == 0.0) {
							err.append("[").append(value).append("]  成品单位折算不能为零！");
							unitWaste = 0.0;// 单耗
						} else {
							unitWaste = unitWaste * unitConvert
									/ unitConvertExg;// 单耗
						}
					}

					bom
							.setUnitWaste(CommonUtils.getDoubleByDigit(
									unitWaste, 9));
					if (bom.getUnitWaste().doubleValue() == Double.valueOf(0)) {
						err.append("[").append(value).append("]  单耗不能为零！");
					}

				} else if ("bom.waste".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					try {
						bom.setWaste(CommonUtils.getDoubleByDigit(Double
								.valueOf(value), 5));
					} catch (Exception e) {
						err.append("[").append(value).append("]  损耗率不正确！");
						continue;
					}

				}

			}
			obj.setErrinfo(err.toString());
			obj.setBom(bom);
			list.add(obj);
		}
		
		//
		// 合并
		//
		list = heBingChongfu(list);
		
		return list;
	}
	/**
	 * This method initializes jButton1
	 * 保存功能
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存");
			jButton1.setPreferredSize(new Dimension(50, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (afterList == null) {
						return;
					}
					if (afterList.size() > 0) {
						new SaveFileDataRunnable(afterList).start();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存数据
	 * @author Administrator
	 *
	 */
	class SaveFileDataRunnable extends Thread {
		private List list = new ArrayList();

		public SaveFileDataRunnable(List list) {
			this.list = list;
		}

		public void run() {
			try {
				List tlist = new ArrayList();
				String err = "";
				for (int i = 0; i < list.size(); i++) {
					EmsEdiMergerBomFrom bom = (EmsEdiMergerBomFrom) list.get(i);
					if (bom.getErrinfo() != null
							&& !"".equals(bom.getErrinfo())) {
						err += bom.getErrinfo();
					}
					if (bom.getBom().getWaste() >= 50) {
						tlist.add("成品货号:" + bom.getPtNo() + "  损耗超过50%");
					}
				}
				if (!"".equals(err)) {
					JOptionPane.showMessageDialog(DgEmsEdiMergerImport.this,
							"数据存在错误，不能保存数据", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String tishi = "";
				for (int i = 0; i < tlist.size(); i++) {
					tishi = tishi + ((String) tlist.get(i)) + "\n";
				}
				if (!"".equals(tishi)) {
					if (JOptionPane.showConfirmDialog(
							DgEmsEdiMergerImport.this, tishi, "提示", 0) != 0) {
						return;
					}
				}

				CommonStepProgress.showStepProgressDialog();
				CommonStepProgress.setStepMessage("系统正在保存数据资料，请稍后...");
				boolean ischange = jCheckBox.isSelected();
				// 以上检查通过后执行下面
				int totalcount = 0; // 总记录数
				List results = new ArrayList();
				CommonStepProgress.setStepProgressMaximum(list.size());
				for (int i = 0; i < list.size(); i++) {
					EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) list.get(i);
					String cpNo = obj.getPtNo().trim();
					List lsExg = manualdeclearAction
							.findEmsEdiMergerImgBeforeByGNo(new Request(
									CommonVars.getCurrUser()), false, cpNo,
									emsEdiMergerHead);
					EmsEdiMergerExgBefore exg = null;
					if (lsExg != null && lsExg.size() > 0) {
						exg = (EmsEdiMergerExgBefore) lsExg.get(0);
					}
					CommonStepProgress.setStepMessage("正确数据总共有【" + list.size() + "】行数据,现保存第【"
							+ i + "】行数据,请稍等...");
					CommonStepProgress.setStepProgressValue(i);
					totalcount++;
					results.addAll(manualdeclearAction.saveToEmsMerger(new Request(
							CommonVars.getCurrUser()), exg, obj,
							emsEdiMergerHead, ischange));// 普通版本
				}

				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgEmsEdiMergerImport.this,
						"数据保存完成!\n\n" + "共导入总记录数：" +totalcount + "\n\n", "提示", 2);
				initTable(new Vector());

			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				CommonStepProgress.setStepMessage("系统保存资料失败！");
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
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
			jTable.setRowHeight(25);
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {

						List list = new ArrayList();
						for (int i = 0; i < afterList.size(); i++) {
							EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) afterList
									.get(i);
							if (obj.getIsMerger().equals("未备案")
									|| (obj.getErrinfo() != null && !obj
											.getErrinfo().equals(""))) {
								continue;
							}
							list.add(obj);
						}
						initTable(list);
						saveExcel(tableModel);
						initTable(afterList);
					}
				}
			});
		}
		return jTable;
	}

	private void saveExcel(JTableListModel tableModel) {

		String excelFileName = "";
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.setFileFilter(new ExampleFileFilter("xls"));
		if (excelFileName != null && !"".equals(excelFileName)) {
			File initFile = new File("./" + excelFileName + ".xls");
			fileChooser.setSelectedFile(initFile);
		}
		String fileName = "";
		int state = fileChooser.showSaveDialog(DgEmsEdiMergerImport.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			String suffix = description.substring(description.indexOf("."));
			if (f.getPath().indexOf(".") > 0) {
				fileName = f.getPath();
			} else {
				fileName = f.getPath() + suffix;
			}
		} else {
			return;
		}

		DgSaveTableListToExcel dgSave = new DgSaveTableListToExcel(
				DgEmsEdiMergerImport.this);
		dgSave.setTableModel(tableModel);
		dgSave.setFileName(fileName);
		dgSave.setTitle("保存("
				+ ((JDialog) DgEmsEdiMergerImport.this).getTitle() + ")到Excel");
		dgSave.setVisible(true);
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
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退出");
			jButton2.setPreferredSize(new Dimension(50, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiMergerImport.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("【开始导入】是否繁转简");
			jRadioButton.setVisible(false);
		}
		return jRadioButton;
	}

	public void setEmsEdiMergerHead(EmsEdiMergerHead emsEdiMergerHead) {
		this.emsEdiMergerHead = emsEdiMergerHead;
	}

	public EmsEdiMergerExgAfter getExgAfter() {
		return exgAfter;
	}

	public void setExgAfter(EmsEdiMergerExgAfter exgAfter) {
		this.exgAfter = exgAfter;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("覆盖导入");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setSelected(true);
			cbCheckTitle.setText("第一行为标题");
		}
		return cbCheckTitle;
	}

	/**
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("栏位设定");
			btnColumn.setPreferredSize(new Dimension(60, 30));
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.EMSIMGER_BOM_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}

				}
			});
		}
		return btnColumn;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(5, 5, 783, 35));
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJCheckBox());
			jToolBar.add(getCbCheckTitle());
//			jToolBar.add(getCbHeBing());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getCbbUnicode());
			jToolBar.add(getCbToUpCase());
		}
		return jToolBar;
	}

	public EmsEdiMergerExgBefore getEmsEdiMergerExgBefore() {
		return emsEdiMergerExgBefore;
	}

	public void setEmsEdiMergerExgBefore(
			EmsEdiMergerExgBefore emsEdiMergerExgBefore) {
		this.emsEdiMergerExgBefore = emsEdiMergerExgBefore;
	}

//	/**
//	 * This method initializes cbHeBing
//	 * 
//	 * @return javax.swing.JCheckBox
//	 */
//	private JCheckBox getCbHeBing() {
//		if (cbHeBing == null) {
//			cbHeBing = new JCheckBox();
//			cbHeBing.setSelected(true);
//			cbHeBing.setText("合并");
//			//2012-4-19 zyy 必须合并料件
//			cbHeBing.setEnabled(false);
//		}
//		return cbHeBing;
//	}

	/**
	 * 合并重复的物料
	 */
	private List<EmsEdiMergerBomFrom> heBingChongfu(List<EmsEdiMergerBomFrom> list) {
		try {
			for (EmsEdiMergerBomFrom from : list) {
				if(CommonUtils.notEmpty(from.getErrinfo())) {
					return list;
				}
			}
			
			// 如果成品货号+料件货号+版本号 相同时
			// 把其合并成一条记录
			Map<String, EmsEdiMergerBomFrom> map = new HashMap<String, EmsEdiMergerBomFrom>();
			CommonUtils.listToMap(list, map, new GetKeyValueImpl<EmsEdiMergerBomFrom>(){
				@Override
				public String getKey(EmsEdiMergerBomFrom e) {
					String producePtNo = e.getPtNo();
					String materialPtNo = e.getBom().getPtNo();
					String version = e.getVersion().toString();
					
					System.out.println(producePtNo + materialPtNo + version);
					return producePtNo + "/" + materialPtNo + "/" + version;
				}

				public void put(EmsEdiMergerBomFrom e, Map map) {
					String key = getKey(e);
					EmsEdiMergerBomFrom o = (EmsEdiMergerBomFrom) map.get(key);
					if(o == null) {
						map.put(key, e);
					} else {
						o.getBom().setUnitWaste(o.getBom().getUnitWaste() + e.getBom().getUnitWaste());
						Double waste = ((o.getBom().getUnitWaste() / (100 - o.getBom().getWaste())) * o.getBom().getWaste()
						+ (e.getBom().getUnitWaste() / (100 - e.getBom().getWaste())) * e.getBom().getWaste())
						/ ((o.getBom().getUnitWaste() / (100 - o.getBom().getWaste())) + (e.getBom().getUnitWaste() / (100 - e.getBom().getWaste())));
						System.out.println(o.getPtNo()+ "单耗" + o.getBom().getUnitWaste() + "损耗" + o.getBom().getWaste());
						System.out.println(e.getPtNo()+ "单耗" + e.getBom().getUnitWaste() + "损耗" + e.getBom().getWaste());
						System.out.println("0.waste" + waste);
						o.getBom().setWaste(waste);
	
					}
				}
			});
			
			return new ArrayList<EmsEdiMergerBomFrom>(map.values());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setPreferredSize(new Dimension(1, 130));
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 * hwy 2012-9-3
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(271, 37, 397, 18));
			jLabel51.setText("方法是导入的企业版本在版本表中不存在海关版本自动加1,第一次从0开始");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(255, 70, 279, 18));
			jLabel7.setText("4、海关版本与企业版本栏位的数据不允许同时为空");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(447, 55, 348, 18));
			jLabel6.setText("3、同一个成品海关版本号与企业版本号不可同时存在");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(256, 54, 208, 18));
			jLabel5.setText("2、企业版本为空，海关版本不为空");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(255, 21, 329, 18));
			jLabel4
					.setText("1、企业版本不为空，海关版本为空，系统自动转海关版本。");
			jLabel3 = new JLabel();
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(new Rectangle(11, 22, 242, 18));
			jLabel3.setText("关于导入海关、企业版本栏位数据注意事项：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(9, 3, 196, 17));
			// jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("说明：海关版本与企业版本的区别：");
			jLabel1 = new JLabel();
			// jLabel1.setForeground(java.awt.Color.blue);
			jLabel1
					.setText("海关版本指需要在海关备案的，海关所规定的版本号如：0，1，2....企业版本：指企业内部使用的，无需海关备案");
			jLabel1.setBounds(new Rectangle(197, 2, 608, 19));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(5, 46, 10, 10));
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(jLabel51, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbUnicode
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbUnicode() {
		if (cbbUnicode == null) {
			cbbUnicode = new JCheckBox();
			cbbUnicode.setText("企业单耗根据单位折算折成海关单耗");
		}
		return cbbUnicode;
	}

	/**
	 * This method initializes cbToUpCase	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbToUpCase() {
		if (cbToUpCase == null) {
			cbToUpCase = new JCheckBox();
			cbToUpCase.setText("尾部去空格转化为大写");
		}
		return cbToUpCase;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
