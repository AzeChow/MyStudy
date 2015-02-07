/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.RegexUtil;

/**
 * @author lxr 2008-10-25
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DgBcsImportDataFromFile extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

	private MaterialManageAction materialManageAction = null;

	private File txtFile = null;

	private boolean ok = false;

	private String materielType = MaterielType.FINISHED_PRODUCT; // @jve:decl-index=0:

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbCheckTitle = null;

	private JCheckBox cbUpdateMateriel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExg = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbImg = null;

	private JRadioButton rbMaterial;

	// private List<BcsInnerMergeFileData> afterList = null;
	private List afterList = null;

	private List<BcsInnerMergeFileData> list = null;

	private List entList = null;

	private List newList = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;
	List listData10 = new ArrayList(); // @jve:decl-index=0:

	private JPanel jPanel1 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = null;

	private JButton btnDel = null;

	private ButtonGroup bg = null;

	private Boolean importMerge = Boolean.TRUE;

	/**
	 * This method initializes
	 * 
	 */
	public DgBcsImportDataFromFile() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initTableImg(new ArrayList());
		initTableExg(new ArrayList());
	}

	private void initTableExg(List list) {
		tableModelExg = new JTableListModel(tbExg, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						if (importMerge) {
							return getMergeTableColumnList();
						} else {
							return getMaterialTableColumnList();
						}
					}
				});
		tbExg.getColumnModel().getColumn(tbExg.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		if (importMerge) {
			tbImg.getColumnModel().getColumn(13)
					.setCellRenderer(new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText(value == null || "".equals(value) ? "否"
									: (value.equals(Boolean.FALSE.toString()) ? "否"
											: "是"));
							return this;
						}
					});
		}
	}

	private void initTableImg(List list) {
		tableModelImg = new JTableListModel(tbImg, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						if (importMerge) {
							return getMergeTableColumnList();
						} else {
							return getMaterialTableColumnList();
						}
					}
				});
		tbImg.getColumnModel().getColumn(tbImg.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		if (importMerge) {
			tbImg.getColumnModel().getColumn(13)
					.setCellRenderer(new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText(value == null || "".equals(value) ? "否"
									: (value.equals(Boolean.FALSE.toString()) ? "否"
											: "是"));
							return this;
						}
					});
		}

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("物料与报关对应导入");
		this.setContentPane(getJContentPane());
		this.setSize(1326, 600);

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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 打开文件
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgBcsImportDataFromFile.this);
				CommonProgress.setMessage("系统正在打开文件资料，请稍后...");
				afterList = parseTxtFile(txtFile);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgBcsImportDataFromFile.this,
						"打开文件失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return afterList;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			if (jTabbedPane.getSelectedIndex() == 1) {
				if (rbMaterial.isSelected()) {
					initTableImg((List) list.get(0));
				} else {
					initTableImg(list);
				}
			} else if (jTabbedPane.getSelectedIndex() == 0) {
				if (rbMaterial.isSelected()) {
					initTableExg((List) list.get(0));
				} else {
					initTableExg(list);
				}
			}
		}
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	private List<JTableListColumn> getMergeTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号(不为空)", "beforeMaterielCode", 80));
		list.add(new JTableListColumn("工厂商品名称", "beforeMaterielName", 80));
		list.add(new JTableListColumn("工厂商品规格", "beforeMaterielSpec", 80));
		list.add(new JTableListColumn("工厂单位名称", "beforeEnterpriseUnit", 80));

		list.add(new JTableListColumn("归并序号(不为空)", "afterTenMemoNo", 100,Integer.class));
		list.add(new JTableListColumn("10位商品编码", "afterTenComplexCode", 80));
		list.add(new JTableListColumn("报关商品名称", "afterComplexlName", 80));
		list.add(new JTableListColumn("报关商品规格", "afterComplexSpec", 80));
		list.add(new JTableListColumn("报关单位名称", "afterUnit", 80));
		list.add(new JTableListColumn("工厂单重", "unitWeight", 60));
		list.add(new JTableListColumn("单位折算系数(不为空)", "unitConvert", 100));
		list.add(new JTableListColumn("工厂单价", "ptPrice", 60));
		list.add(new JTableListColumn("(是/否)当前使用", "isCount", 100));
		list.add(new JTableListColumn("错误信息", "errinfo", 300));
		return list;

	}

	private List<JTableListColumn> getMaterialTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号(不可为空)", "beforeMaterielCode", 150));
		list.add(new JTableListColumn("工厂商品名称", "beforeMaterielName", 150));
		list.add(new JTableListColumn("工厂商品规格", "beforeMaterielSpec", 150));
		list.add(new JTableListColumn("工厂单位名称", "beforeEnterpriseUnit", 150));
		if(cbUpdateMateriel.isSelected()){
			list.add(new JTableListColumn("单位折算率", "unitConvert", 150));
		}
		list.add(new JTableListColumn("错误信息", "errinfo", 300));
		return list;

	}

	// private List<BcsInnerMergeFileData> parseTxtFile(File file) {
	private List parseTxtFile(File file) {

		String suffix = getSuffix(file);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//
			lines = FileReading.readExcel(file, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(file, 0, "GBK");
		}

		// 获得计量单位
		List calUnitList = materialManageAction.findCalUnit(new Request(
				CommonVars.getCurrUser()));

		// 保存公司自用的计量单位
		Map<String, CalUnit> hsCalUnit = new HashMap<String, CalUnit>();
		for (int i = 0; i < calUnitList.size(); i++) {
			CalUnit cu = (CalUnit) calUnitList.get(i);
			hsCalUnit.put(cu.getName(), cu);
		}

		List<BcsInnerMergeFileData> list = null;
		if (importMerge) {

			list = checkBcsInnerMerge(lines);

		} else {
			if (getCbUpdateMateriel().isSelected()) {
				List<ScmCoi> scmCoiList = materialManageAction
						.findScmCoi(CommonVars.getRequst());
				Map<String, ScmCoi> scmCoiMap = new HashMap<String, ScmCoi>();
				ScmCoi scmCoi = null;
				for (int i = 0; i < scmCoiList.size(); i++) {
					scmCoi = scmCoiList.get(i);
					scmCoiMap.put(scmCoi.getCoiProperty(), scmCoi);
				}
				// if(scmCoiMap.get(scmCoi.getId()).toString() ==
				// "4028fb962949ad770129a5a9cbca186e"){
				// list = checkMaterial(lines, hsCalUnit,
				// scmCoiMap.get(scmCoi.getId()));
				// }
				// if(scmCoiMap.get(scmCoi.getId()).toString() ==
				// "4028fb962949ad770129a5a9f1d01871"){
				
				list = checkMaterial(lines, hsCalUnit,scmCoiMap.get(materielType));
				// }
			} else {
				list = oldCheckMaterial(lines, hsCalUnit);
				
			}
		}

		return list;
	}

	private List<BcsInnerMergeFileData> oldCheckMaterial(List<List> lines,
			Map<String, CalUnit> hsCalUnit) {

		// 物料主档
		Map<String, Materiel> materialMap = new HashMap<String, Materiel>();
		List oldList = new ArrayList();
		List<Materiel> materiels = materialManageAction.findMateriel(CommonVars
				.getRequst());
		Materiel m = null;
		for (int i = 0; i < materiels.size(); i++) {
			m = materiels.get(i);
			materialMap.put(m.getPtNo(), m);
		}

		Set<String> mergeSet = new HashSet<String>(
				bcsInnerMergeAction.findBcsInnerMergeKeys(
						CommonVars.getRequst(), materielType));

		List<BcsInnerMergeFileData> list = new ArrayList<BcsInnerMergeFileData>();
		Set<String> ptNoSet = new HashSet<String>();
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
					+ i + "】行,请稍等...");
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if (getCbJF().isSelected()) {
				strs = changStrs(strs);
			}
			String err = "";
			BcsInnerMergeFileData data = new BcsInnerMergeFileData();
			for (int j = 0; j < 5; j++) {
				String value = getFileColumnValue(strs, j);
				if (j == 0) {// 料号
					if (value != null && !value.equals("")) {
						m = materialMap.get(value);
						data.setBeforeMaterielCode(value);
//						if (mergeSet.contains(value + "/")) {
//							err = err + "[" + value + "]料号在对应关系中已经存在空对应记录。/";
//						}
						// 如果物料已经存在，显示已有物料信息。
						if (m != null) {
							data.setBeforeEnterpriseUnit(m.getCalUnit() == null ? null
									: m.getCalUnit().getName());
							data.setBeforeMaterielName(m.getFactoryName());
							data.setBeforeMaterielSpec(m.getFactorySpec());
						}
						
						String stu = checkType(m,materielType);
						if(stu!=null){
							err = err + stu;
						}
					} else {
						err = err + "[" + value + "]料号不可为空。/";
					}
				} else if (j == 1 && data.getBeforeMaterielName() == null) { // 工厂位商品名称
					if (value != null && !value.equals("")) {
						data.setBeforeMaterielName(value);
					} else {
						err = err + "[" + value + "]工厂位商品名称不可为空。/";
					}
				} else if (j == 2 && data.getBeforeMaterielSpec() == null) {// 工厂位商品规格
					if (value != null && !value.equals("")) {
						data.setBeforeMaterielSpec(value);
					}
				} else if (j == 3 && data.getBeforeEnterpriseUnit() == null) {// 工厂单位
					if (value != null && !value.equals("")) {
						CalUnit u = hsCalUnit.get(value);
						if (u != null) {
							data.setBeforeEnterpriseUnit(u.getName());
						} else {
							err = err + "[" + value + "]工厂单位在单位表中不存在。/";
						}
					} else {
						err = err + "[" + value + "]料号新增：工厂单位不允许为空。/";
					}
				}
			}

			// 成品做归并依照原来的Key值，料件Key改为归并序号+料号
			String ptNoKey = data.getBeforeMaterielCode();
			if (ptNoSet.contains(ptNoKey)) {
				err = err + "[" + ptNoKey + "]文件中料号重复。/";
			} else {
				ptNoSet.add(ptNoKey);
			}
			data.setErrinfo(err);
			list.add(data);
		}
	oldList.add(list);
	return oldList;
	}

	private List checkMaterial(List<List> lines,
			Map<String, CalUnit> hsCalUnit, ScmCoi scmCoi) {

		// 物料主档
		Map<String, Materiel> materialMap = new HashMap<String, Materiel>();
		List<Materiel> materiels = materialManageAction.findMateriel(CommonVars
				.getRequst());
		Materiel m = null;
		for (int i = 0; i < materiels.size(); i++) {
			m = materiels.get(i);
			materialMap.put(m.getPtNo(), m);
		}
		// 工厂物料主档
		Map<String, EnterpriseMaterial> enterpriseMaterialMap = new HashMap<String, EnterpriseMaterial>();
		List<EnterpriseMaterial> enterpriseMaterials = materialManageAction
				.findEnterpriseMaterials(CommonVars.getRequst());
		EnterpriseMaterial em = null;
		for (int i = 0; i < enterpriseMaterials.size(); i++) {
			em = enterpriseMaterials.get(i);
			enterpriseMaterialMap.put(em.getPtNo(), em);
		}

		Set<String> mergeSet = new HashSet<String>(
				bcsInnerMergeAction.findBcsInnerMergeKeys(
						CommonVars.getRequst(), materielType));

		List<BcsInnerMergeFileData> list = new ArrayList<BcsInnerMergeFileData>();
		Set<String> ptNoSet = new HashSet<String>();
		// List materielList = new ArrayList();
		List queryList = new ArrayList();
		List newList = new ArrayList();
		List entList = new ArrayList();
		List totalList = new ArrayList();
		Materiel materiel = null;
		EnterpriseMaterial enterpriseMaterial = null;
		Map<String, Materiel> map = new HashMap<String, Materiel>();
		Map<String, EnterpriseMaterial> enterpriseMap = new HashMap<String, EnterpriseMaterial>();

		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
					+ i + "】行,请稍等...");
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if (getCbJF().isSelected()) {
				strs = changStrs(strs);
			}
			String err = "";
			String ptNo = null;
			String ptName = null;
			String ptSpec = null;
			CalUnit ptUnit = null;

			BcsInnerMergeFileData data = new BcsInnerMergeFileData();
			for (int j = 0; j < 5; j++) {
				String value = getFileColumnValue(strs, j);
				if (j == 0) {// 料号
					if (value != null && !value.equals("")) {
						data.setBeforeMaterielCode(value);
						// 代表已经存在 需要修改的
						if (mergeSet.contains(value + "/")) {
							// err = err + "[" + value + "]料号在对应关系中已经存在空对应记录。/";
							queryList.add(value + "/");
							materiel = new Materiel();
							map.put(value, materiel);

							enterpriseMaterial = new EnterpriseMaterial();
							enterpriseMap.put(value, enterpriseMaterial);
						}

						// 如果物料已经存在，显示已有物料信息。
						Materiel mate = materialMap.get(value);
						if (mate != null) {
							ptNo = value;
						} else {
							// 不存在需要修改的
							materiel = new Materiel();
							newList.add(materiel);
						}
						// 工厂物料主档
						if (enterpriseMaterialMap.get(value) != null) {
							ptNo = value;
						} else {
							// 不存在需要修改的
							enterpriseMaterial = new EnterpriseMaterial();
							entList.add(enterpriseMaterial);
						}
						String stu = checkType(mate,materielType);
						if(stu!=null){
							err = err + stu;
						}
					} else {
						err = err + "[" + value + "]料号不可为空。/";
					}
				} else if (j == 1) { // 工厂位商品名称
					if (data.getBeforeMaterielName() == null) {
						if (value != null && !value.equals("")) {
							data.setBeforeMaterielName(value);
							ptName = value;
						} else {
							err = err + "[" + value + "]工厂位商品名称不可为空。/";
						}
					}
				} else if (j == 2) {// 工厂位商品规格
					if (data.getBeforeMaterielSpec() == null) {
						if (value != null && !value.equals("")) {
							data.setBeforeMaterielSpec(value);
							ptSpec = value;
						}
					}
				} else if (j == 3) {// 工厂单位

					if (data.getBeforeEnterpriseUnit() == null) {
						if (value != null && !value.equals("")) {
							CalUnit u = hsCalUnit.get(value);
							if (u != null) {
								data.setBeforeEnterpriseUnit(u.getName());
								ptUnit = u;
							} else {
								err = err + "[" + value + "]工厂单位在单位表中不存在。/";
							}
						} else {
							err = err + "[" + value + "]料号新增：工厂单位不允许为空。/";
						}
					}
				}else if(j == 4){//
					if (value != null && !value.trim().equals("")) {
						try {
							Double d = Double.parseDouble(value.trim().toString());
							data.setUnitConvert(d.toString());
						} catch (Exception e) {
							err = err + "[" + value + "]折算率只能是数字/";						}
					}
				}

				if (materiel != null) {
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + scmCoi);
					materiel.setScmCoi(scmCoi);

					materiel.setPtNo(ptNo);
					materiel.setFactoryName(ptName);
					materiel.setFactorySpec(ptSpec);
					materiel.setCalUnit(ptUnit);
				}
				if (enterpriseMaterial != null) {
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
					// scmCoi);
					enterpriseMaterial.setScmCoi(scmCoi);
					enterpriseMaterial.setPtNo(ptNo);
					enterpriseMaterial.setFactoryName(ptName);
					enterpriseMaterial.setFactorySpec(ptSpec);
					enterpriseMaterial.setCalUnit(ptUnit);
				}
			}

			// 成品做归并依照原来的Key值，料件Key改为归并序号+料号
			String ptNoKey = data.getBeforeMaterielCode();
			if (ptNoSet.contains(ptNoKey)) {
				err = err + "[" + ptNoKey + "]文件中料号重复。/";
			} else {
				ptNoSet.add(ptNoKey);
			}
			data.setErrinfo(err);
			list.add(data);
		}

		// 更新工厂常用物料主档
		if (queryList.size() > 0) {
			String strBcsInnerMergeKeys = " ";
			for (int k = 0; k < queryList.size(); k++) {
				if (k == 0) {
					strBcsInnerMergeKeys += "'" + queryList.get(k) + "'";
				} else if (k > 0 && k <= queryList.size()) {
					strBcsInnerMergeKeys += "," + "'" + queryList.get(k) + "'";
				}
			}
			List<Materiel> materielObject = bcsInnerMergeAction
					.findBcsInnerMergeObject(CommonVars.getRequst(),
							materielType, strBcsInnerMergeKeys);
			Materiel _materiel = null;
			for (int k = 0; k < materielObject.size(); k++) {
				materiel = map.get(materielObject.get(k).getPtNo());
				_materiel = materielObject.get(k);
				_materiel.setPtNo(materiel.getPtNo());
				_materiel.setFactoryName(materiel.getFactoryName());
				_materiel.setFactorySpec(materiel.getFactorySpec());
				_materiel.setCalUnit(materiel.getCalUnit());
				newList.add(_materiel);
			}
		}
		if (newList.size() > 0) {
			this.bcsInnerMergeAction.saveBcsInnerMergeAddOrUpdate(
					CommonVars.getRequst(), newList);
		}
		// 更新工厂物料主档
		if (queryList.size() > 0) {
			String strBcsInnerMergeEnterpriseKeys = " ";
			for (int k = 0; k < queryList.size(); k++) {
				if (k == 0) {
					strBcsInnerMergeEnterpriseKeys += "'" + queryList.get(k)
							+ "'";
				} else if (k > 0 && k <= queryList.size()) {
					strBcsInnerMergeEnterpriseKeys += "," + "'"
							+ queryList.get(k) + "'";
				}
			}
			List<EnterpriseMaterial> enterpriseMaterielObject = bcsInnerMergeAction
					.findBcsInnerMergeEnterpriseObject(CommonVars.getRequst(),
							strBcsInnerMergeEnterpriseKeys);
			EnterpriseMaterial _enterpriseMaterial = null;
			for (int k = 0; k < enterpriseMaterielObject.size(); k++) {
				_enterpriseMaterial = enterpriseMap
						.get(enterpriseMaterielObject.get(k).getPtNo());
				enterpriseMaterial = enterpriseMaterielObject.get(k);
				enterpriseMaterial.setPtNo(_enterpriseMaterial.getPtNo());
				enterpriseMaterial.setFactoryName(_enterpriseMaterial
						.getFactoryName());
				enterpriseMaterial.setFactorySpec(_enterpriseMaterial
						.getFactorySpec());
				enterpriseMaterial.setCalUnit(_enterpriseMaterial.getCalUnit());
				entList.add(enterpriseMaterial);
			}
			if (entList.size() > 0) {
				this.bcsInnerMergeAction.saveBcsInnerMergeAddOrUpdate(
						CommonVars.getRequst(), entList);
			}
		}
		totalList.add(list);
		totalList.add(newList);
		totalList.add(entList);
		return totalList;

	}
	
	/**
	 * 检查物料属性，防止成品对应关系导入的时候引用料件物料。或者反之。
	 * @param scmCoi
	 * @param materiel
	 * @param materielType
	 * @return
	 */
	private String checkType(Materiel materiel,String materielType){
		// 不为空的时候检查物料属性，防止成品对应关系导入的时候引用料件物料。或者反之。
		if (materiel!=null&&materiel.getScmCoi()!=null&&!materielType.equals(materiel.getScmCoi().getCoiProperty())) {
			String typeName = "";
			if (MaterielType.MATERIEL.equals(materiel.getScmCoi().getCoiProperty())) {
				typeName = "料件";
			} else if (MaterielType.FINISHED_PRODUCT
					.equals(materiel.getScmCoi().getCoiProperty())) {
				typeName = "成品";
			} else if (MaterielType.SEMI_FINISHED_PRODUCT
					.equals(materiel.getScmCoi().getCoiProperty())) {
				typeName = "半成品";
			}
			String stu = "此料号:"+materiel.getPtNo()+"在报关常用工厂物料中属性为：" + typeName+",无法导入!";
			return stu;
		}
		return null;
	}

	private List<BcsInnerMergeFileData> checkBcsInnerMerge(List<List> lines) {

		// 物料主档
		Map<String, Materiel> materialMap = new HashMap<String, Materiel>();
		List<Materiel> materiels = materialManageAction.findMateriel(CommonVars
				.getRequst());
		Materiel m = null;
		for (int i = 0; i < materiels.size(); i++) {
			m = materiels.get(i);
			materialMap.put(m.getPtNo(), m);
		}

		// 报关商品资料
		Map<Integer, BcsTenInnerMerge> tenMap = new HashMap<Integer, BcsTenInnerMerge>();
		List<BcsTenInnerMerge> tenInnerMerges = bcsInnerMergeAction
				.findBcsTenInnerMerge(CommonVars.getRequst(), materielType);
		BcsTenInnerMerge tenInnerMerge = null;
		for (int i = 0; i < tenInnerMerges.size(); i++) {
			tenInnerMerge = tenInnerMerges.get(i);
			tenMap.put(tenInnerMerge.getSeqNum(), tenInnerMerge);
		}

		Set<String> mergeSet = new HashSet<String>(
				bcsInnerMergeAction.findBcsInnerMergeKeys(
						CommonVars.getRequst(), materielType));

		List<BcsInnerMergeFileData> list = new ArrayList<BcsInnerMergeFileData>();
		Set<String> ptNoSeqNumSet = new HashSet<String>();
		String ptNoSeqNum = null;
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
					+ i + "】行,请稍等...");
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if (getCbJF().isSelected()) {
				strs = changStrs(strs);
			}
			String err = "";

			BcsInnerMergeFileData data = new BcsInnerMergeFileData();
			for (int j = 0; j < 13; j++) {
				String value = getFileColumnValue(strs, j);

				if (j == 0) {// 料号
					if (value != null && !value.equals("")) {
						m = materialMap.get(value);
						data.setBeforeMaterielCode(value);

						if (m != null) {
							System.out.println("----" + m.getCalUnit());
							data.setBeforeEnterpriseUnit(m.getCalUnit()
									.getName());
							data.setBeforeMaterielName(m.getFactoryName());
							data.setBeforeMaterielSpec(m.getFactorySpec());

						} else {
							err = err + "[" + value + "]料号不存在于报关常用工厂物料中。/";
						}

					} else {
						err = err + "[" + value + "]料号不可为空。/";
					}

				} else if (j == 4) {// 归并序号
					if (value != null && !value.equals("")) {
						if (!value.matches("\\d*")) {
							err = err + "[" + value + "]归并序号只能为整数。/";
						} else {
							tenInnerMerge = tenMap.get(Integer.valueOf(value));
							if (tenInnerMerge == null) {
								err = err + "[" + value + "]归并序号在报关资料中不存在。/";
							} else {
								data.setAfterTenMemoNo(value);
								data.setAfterTenComplexCode(tenInnerMerge
										.getComplex() == null ? ""
										: tenInnerMerge.getComplex().getCode());
								data.setAfterComplexlName(tenInnerMerge
										.getName());
								data.setAfterComplexSpec(tenInnerMerge
										.getSpec());
								data.setAfterUnit(CustomBaseEntity
										.getCode(tenInnerMerge.getComUnit()));
							}
						}
					} else {
						err = err + "[" + value + "]归并序号不能为空。/";
					}
				} else if (j == 9) {
					Double unitWeight = 0.0;
					if (value != null && !value.equals("")) {
						unitWeight = Double.parseDouble(value);
					}
//					String unitWeight = "0.0";
//					if(value != null && !value.equals("")){
//						unitWeight = value;
//					}
					if (new BigDecimal(unitWeight)
							.setScale(12, BigDecimal.ROUND_HALF_UP).toPlainString()
							.matches(RegexUtil.CHECK_FLOAT) || unitWeight.equals("") || unitWeight.equals(0.0)) {
						if(unitWeight.equals(0.0)){
							data.setUnitWeight("0.0");
						}
						if(!unitWeight.equals(0.0)){
						String strUnitWeight = Double.toString(unitWeight);
						data.setUnitWeight(new BigDecimal(strUnitWeight)
								.setScale(12, BigDecimal.ROUND_HALF_UP)
								.stripTrailingZeros().toPlainString());
						}
						if(value==null||value.equals("")){
							data.setUnitWeight("");
						}
						
							
						
					} else {
						err = err + "[" + value + "]单重不合法/";
					}
				} else if (j == 10) {
					String unitConvert = "0.0";
					if (value != null && !value.equals("")) {
						unitConvert = value;
					}

					if (new BigDecimal(unitConvert)
							.setScale(12, BigDecimal.ROUND_HALF_UP).toPlainString()
							.matches(RegexUtil.CHECK_FLOAT)) {
						if(NumberUtils.toDouble(unitConvert)==0.0){
							data.setUnitConvert("0.0");
						}else{
							data.setUnitConvert(new BigDecimal(unitConvert)
							.setScale(12, BigDecimal.ROUND_HALF_UP)
							.stripTrailingZeros().toPlainString());
						}
					} else {
						err = err + "[" + value + "]单位折算不合法。/";
					}
				} else if (j == 11) {
					Double ptPrice = 0.0;
					
					if (value != null && !value.equals("")) {
						ptPrice = Double.parseDouble(value);
					}
//					String ptPrice = "0.0";
//					if(value != null && !value.equals("")){
//						ptPrice = value;
//					}
					if (new BigDecimal(ptPrice)
							.setScale(12, BigDecimal.ROUND_HALF_UP).toPlainString()
							.matches(RegexUtil.CHECK_FLOAT) || ptPrice.equals(0.0) || ptPrice.equals("")) {
						if(ptPrice.equals(0.0)){
							data.setPtPrice("0.0");
						}
						if(!ptPrice.equals(0.0)){
							String strPtPrice = Double.toString(ptPrice);
						    data.setPtPrice(new BigDecimal(strPtPrice)
								.setScale(12, BigDecimal.ROUND_HALF_UP)
								.stripTrailingZeros().toPlainString());
						}
						if(value==null||value.equals("")){
							data.setPtPrice("");
						}
					} else {
						err = err + "[" + value + "]单价不合法。/";
					}
				} else if (j == 12) {
					// 是否统计使用
					if ("是".equals(value) || "1".equals(value)) {
						data.setIsCount(new Boolean(true));
					} else {
						data.setIsCount(new Boolean(false));
					}
				}

			}

			// 成品做归并依照原来的Key值，料件Key改为归并序号+料号，判断是否存在一笔对应关系
			ptNoSeqNum = data.getBeforeMaterielCode() + "/"
					+ data.getAfterTenMemoNo();
			if (ptNoSeqNumSet.contains(ptNoSeqNum)) {
				err = err + "[" + ptNoSeqNum + "]文件中料号+归并序号重复。/";
			} else {
				ptNoSeqNumSet.add(ptNoSeqNum);
			}

			if (mergeSet.contains(ptNoSeqNum)) {
				err = err + "[" + ptNoSeqNum + "]文件中料号+归并序号在物料与报关商品对应关系中已经存在。/";
			}

			data.setErrinfo(err);

			list.add(data);
		}

		return list;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		@Override
		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		@Override
		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
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
			jToolBar.setLayout(f);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnDel());
			jToolBar.add(getJButton2());

			rbMaterial = new JRadioButton("导入工厂资料");
			rbMaterial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getCbUpdateMateriel().setVisible(true);
					importMerge = Boolean.FALSE;
					initTableImg(new ArrayList());
					initTableExg(new ArrayList());
				}
			});
			jToolBar.add(rbMaterial);

			final JRadioButton rbBcsInnerMerge = new JRadioButton("导入对应关系");
			rbBcsInnerMerge.setSelected(true);
			rbBcsInnerMerge.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getCbUpdateMateriel().setVisible(false);
					importMerge = Boolean.TRUE;
					initTableImg(new ArrayList());
					initTableExg(new ArrayList());
				}
			});

			jToolBar.add(rbBcsInnerMerge);
			jToolBar.add(getCbJF());
			jToolBar.add(getCbCheckTitle());

			bg = new ButtonGroup();
			bg.add(rbMaterial);
			bg.add(rbBcsInnerMerge);
			
			
			jToolBar.add(getCbUpdateMateriel());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开文件");
			jButton.setPreferredSize(new Dimension(80, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgBcsImportDataFromFile.this);
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

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.setPreferredSize(new Dimension(80, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().start();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存数据
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileData extends Thread {
		@Override
		public void run() {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(DgBcsImportDataFromFile.this);
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				
				if (rbMaterial.isSelected()) {
					for (int i = 0; i < ((List) afterList.get(0)).size(); i++) {
						BcsInnerMergeFileData obj = (BcsInnerMergeFileData) ((List) afterList
								.get(0)).get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							CommonProgress.closeProgressDialog();
							JOptionPane.showMessageDialog(
									DgBcsImportDataFromFile.this,
									"有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
				}
				
				
				
				bcsInnerMergeAction.importDataFromTxtFile(new Request(
						CommonVars.getCurrUser()), afterList, materielType,
						importMerge,cbUpdateMateriel.isSelected());
				// bcsInnerMergeAction.chooseOldImportOrNewImport(new Request(
				// CommonVars.getCurrUser()), afterList, materielType,
				// importMerge);
				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;
				
				if(afterList!=null){
					if(afterList.get(0) instanceof List){
						JOptionPane.showMessageDialog(DgBcsImportDataFromFile.this,
								"导入数据成功！导入数据记录 " + ((List)afterList.get(0)).size() + " 条,共用时 "
										+ totalTime + " 毫秒", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(DgBcsImportDataFromFile.this,
								"导入数据成功！导入数据记录 " + afterList.size() + " 条,共用时 "
										+ totalTime + " 毫秒", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				
				// DgBcsImportDataFromFile.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgBcsImportDataFromFile.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
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
			jButton2.setText("退 出");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes cbJF
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox();
			cbJF.setText("\u7e41\u8f6c\u7b80");
		}
		return cbJF;
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898\u884c");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	private JCheckBox getCbUpdateMateriel() {
		if (cbUpdateMateriel == null) {
			cbUpdateMateriel = new JCheckBox();
			cbUpdateMateriel.setText("导入同时新增或更新物料主档资料");
			cbUpdateMateriel.setVisible(false);
			cbUpdateMateriel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initTableImg(new ArrayList());
					initTableExg(new ArrayList());
				}
			});
		}
		return cbUpdateMateriel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						materielType = MaterielType.FINISHED_PRODUCT;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						materielType = MaterielType.MATERIEL;
					}
				}
			});
			jTabbedPane.setTabPlacement(SwingConstants.TOP);
			jTabbedPane.addTab("1.成品", null, getJPanel(), null);
			jTabbedPane.addTab("2.料件", null, getJPanel2(), null);
		}
		return jTabbedPane;
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jScrollPane.setViewportView(getTbExg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbImg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel3(), BorderLayout.CENTER);

		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 10, 406, 18));
			jLabel.setText("导入方式说明：导入工厂资料；或者工厂资料与报关资料一起导入");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new Dimension(1, 30));
			jPanel3.add(jLabel, null);
		}
		return jPanel3;
	}

	/**
	 * 对倒入的错误数据进行删除 This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除错误");
			btnDel.setPreferredSize(new Dimension(60, 30));
			btnDel.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List ls = null;
					boolean isImg = false;
					List delList = new ArrayList();
					if (jTabbedPane.getSelectedIndex() == 0) {
						ls = tableModelExg.getList();
						isImg = false;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						ls = tableModelImg.getList();
						isImg = true;
					}
					if (ls != null && ls.size() > 0) {
						for (int i = 0; i < ls.size(); i++) {
							BcsInnerMergeFileData obj = (BcsInnerMergeFileData) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								if (isImg) {
									delList.add(obj);
								} else {
									delList.add(obj);
								}
							}
						}
					}
					// 删除错误数据
					if (isImg) {
						tableModelImg.deleteRows(delList);
					} else {
						tableModelExg.deleteRows(delList);
					}

				}
			});
		}
		return btnDel;
	}
} // @jve:decl-index=0:visual-constraint="38,13"

