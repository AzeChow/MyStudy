package com.bestway.bcs.client.verification.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcs.verification.entity.VFTransferDiffHsImg;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.bcus.client.ExceptionHandler;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.jptds.common.AppException;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.AsynSwingWorker;
import com.bestway.util.FileUtils;

/**
 * 导入成品、料件结转差额窗口
 * 
 * @author xc
 * @version 1.0
 * @since 2013-09-02
 */
public class DgImpDiffExgImg extends JDialogBase {
	private JToolBar tbarNorth;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnOpen;
	private JButton btnSave;
	private JButton btnDel;
	private JButton btnExit;
	private JCheckBox cbJF;
	private JCheckBox cbCheckTitle;
	private JTableListModel tbModel;
	private VFVerificationAction vfAction = null;
	private List<String> colLs = new ArrayList<String>();

	/**
	 * 数据发生变化回调
	 */
	private Runnable dataChangeCallback;

	/**
	 * 导入的文件
	 */
	private File txtFile = null;
	/**
	 * 批次号
	 */
	private VFSection section;

	/**
	 * 导入数据是否存在错误
	 */
	private boolean hasErr = false;
	/**
	 * 导入成品标记
	 */
	public static int IMP_DIFFEXG = 1;
	/**
	 * 导入料件标记
	 */
	public static int IMP_DIFFIMG = 2;
	/**
	 * 导入标记
	 */
	public int imp_flag = 1;
	private JButton btnDLTpl;

	public DgImpDiffExgImg(int imp_flag, String title, VFSection section) {
		this.imp_flag = imp_flag;
		this.section = section;
		initialize(title);
	}

	/**
	 * 初始化
	 * 
	 * @param title
	 */
	private void initialize(String title) {
		this.setTitle(title);
		this.setSize(901, 513);
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext()
				.getBean("vfVerificationAction");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanel(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		initCompontents();
	}

	/**
	 * 初始化控件默认之以及控件数据
	 */
	private void initCompontents() {

	}

	/**
	 * 保存数据
	 * 
	 * @param ls
	 */
	private boolean saveData(List<DynaBean> ls) {
		setButtonState(false);
		String taskId = CommonProgress.getExeTaskId();
		CommonStepProgress.showStepProgressDialog(taskId);
		CommonStepProgress.setStepMessage("系统保存请求，请稍后...");
		Request req = new Request(CommonVars.getCurrUser());
		req.setTaskId(taskId);

		final Request request = new Request(CommonVars.getCurrUser());
		if(!this.section.getIsImportHs()){
			// 先删除当前批次之前数据
			if (imp_flag == IMP_DIFFEXG) {
				if (vfAction.isExistVFTransferDiffExgs(request, section)) {
					CommonStepProgress.setStepMessage("正在删除本批次历史数据,请稍等...");
					vfAction.deleteVFTransferDiffExgs(request, section);
				}
			} else if (imp_flag == IMP_DIFFIMG) {
				if (vfAction.isExistVFTransferDiffImgs(request, section)) {
					CommonStepProgress.setStepMessage("正在删除本批次历史数据,请稍等...");
					vfAction.deleteVFTransferDiffImgs(request, section);
				}
			}
		}else{
			// 先删除当前批次之前数据
			if (imp_flag == IMP_DIFFEXG) {
				if (vfAction.isExistVFTransferDiffHsExgs(request, section)) {
					CommonStepProgress.setStepMessage("正在删除本批次历史数据,请稍等...");
					vfAction.deleteVFTransferDiffHsExgs(request, section);
				}
				
			} else if (imp_flag == IMP_DIFFIMG) {
				if (vfAction.isExistVFTransferDiffHsImgs(request, section)) {
					CommonStepProgress.setStepMessage("正在删除本批次历史数据,请稍等...");
					vfAction.deleteVFTransferDiffHsImgs(request, section);
				}
			}
		}
		
		try {
			// 保存导入成品原态数据
			if(!this.section.getIsImportHs()){
				if (imp_flag == IMP_DIFFEXG) {
					List<VFTransferDiffExg> exgLs = new ArrayList<VFTransferDiffExg>(
							ls.size());
					VFTransferDiffExg exg = null;
					for (DynaBean bean : ls) {
						exg = new VFTransferDiffExg();
						try {
							BeanUtils.copyProperties(exg, bean);
						} catch (Exception e) {
							e.printStackTrace();
						}
						exgLs.add(exg);
					}
					vfAction.saveVFTransferDiffExgs(req, section, exgLs);
				} else if (imp_flag == IMP_DIFFIMG) { // 保存导入料件原态数据
					List<VFTransferDiffImg> imgLs = new ArrayList<VFTransferDiffImg>(
							ls.size());
					VFTransferDiffImg img = null;
					for (DynaBean bean : ls) {
						img = new VFTransferDiffImg();
						try {
							BeanUtils.copyProperties(img, bean);
						} catch (Exception e) {
							e.printStackTrace();
						}
						imgLs.add(img);
					}
					vfAction.saveVFTransferDiffImgs(req, section, imgLs);
				}
			}else{//导入数据为编码级
				if (imp_flag == IMP_DIFFEXG) {
					List<VFTransferDiffHsExg> hsExgLs = new ArrayList<VFTransferDiffHsExg>(
							ls.size());
					VFTransferDiffHsExg hsExg = null;
					for (DynaBean bean : ls) {
						hsExg = new VFTransferDiffHsExg();
						try {
							BeanUtils.copyProperties(hsExg, bean);
						} catch (Exception e) {
							e.printStackTrace();
						}
						hsExgLs.add(hsExg);
					}
					vfAction.saveVFTransferDiffHsExgs(req, section, hsExgLs);
				} else if (imp_flag == IMP_DIFFIMG) { // 保存导入料件原态数据
					List<VFTransferDiffHsImg> hsImgLs = new ArrayList<VFTransferDiffHsImg>(
							ls.size());
					VFTransferDiffHsImg img = null;
					for (DynaBean bean : ls) {
						img = new VFTransferDiffHsImg();
						try {
							BeanUtils.copyProperties(img, bean);
						} catch (Exception e) {
							e.printStackTrace();
						}
						hsImgLs.add(img);
					}
					vfAction.saveVFTransferDiffHsImgs(req, section, hsImgLs);
				}
			}
			
		} finally {
			CommonStepProgress.closeStepProgressDialog();
			setButtonState(true);
		}
		return true;
	}

	private List<JTableListColumn> getTableColumns() {
		colLs.clear();
		List<JTableListColumn> cols = new ArrayList<JTableListColumn>();
		if(!Boolean.TRUE.equals(this.section.getIsImportHs())){
			colLs.add("ptNo");
			colLs.add("ptName");
			colLs.add("ptSpec");
			colLs.add("ptUnit");
			if (this.imp_flag == IMP_DIFFEXG) {
				cols.add(new JTableListColumn("成品料号(必填)", "ptNo", 100));
				cols.add(new JTableListColumn("品名", "ptName", 100));
				cols.add(new JTableListColumn("物料规格", "ptSpec", 100));
				cols.add(new JTableListColumn("计量单位", "ptUnit", 60));
				cols.add(new JTableListColumn("已送货未转厂(必填)", "unTransferNum", 110));
				cols.add(new JTableListColumn("已转厂未送货(必填)", "unSendferNum", 110));
				cols.add(new JTableListColumn("BOM版本(必填)", "bomVersion", 80));
				cols.add(new JTableListColumn("错误信息", "errMsg", 300));
				colLs.add("unTransferNum");
				colLs.add("unSendferNum");
				colLs.add("bomVersion");
			} else if (this.imp_flag == IMP_DIFFIMG) {
				cols.add(new JTableListColumn("料件料号(必填)", "ptNo", 100));
				cols.add(new JTableListColumn("品名", "ptName", 100));
				cols.add(new JTableListColumn("物料规格", "ptSpec", 100));
				cols.add(new JTableListColumn("计量单位", "ptUnit", 60));
				cols.add(new JTableListColumn("已收货未转厂(必填)", "unTransferNum", 110));
				cols.add(new JTableListColumn("已转厂未收货(必填)", "unReceiveNum", 110));
				cols.add(new JTableListColumn("错误信息", "errMsg", 300));
				colLs.add("unTransferNum");
				colLs.add("unReceiveNum");
			}
		}else{
			if (this.imp_flag == IMP_DIFFEXG) {
				//表格显示列
				cols.add(new JTableListColumn("成品备案序号（必填）","seqNum", 100));
				cols.add(new JTableListColumn("手册号（必填）", "emsNo", 100));
				cols.add(new JTableListColumn("已送货未转厂（必填）", "unTransHadSendNum", 120));
				cols.add(new JTableListColumn("已转厂未送货（必填）", "unSendHadTransNum", 120));
				cols.add(new JTableListColumn("报关品名", "hsName", 100));
				cols.add(new JTableListColumn("报关规格", "hsSpec", 100));
				cols.add(new JTableListColumn("计量单位", "hsUnit", 60));
				cols.add(new JTableListColumn("报关单数量", "customsBillNum", 80));
				cols.add(new JTableListColumn("发货单数量", "sendBillNum", 80));
				cols.add(new JTableListColumn("错误信息", "errMsg", 300));
				//导入数据
				colLs.add("seqNum");
				colLs.add("emsNo");
				colLs.add("unTransHadSendNum");
				colLs.add("unSendHadTransNum");
				colLs.add("hsName");
				colLs.add("hsSpec");
				colLs.add("hsUnit");
				colLs.add("customsBillNum");
				colLs.add("sendBillNum");
			} else if (this.imp_flag == IMP_DIFFIMG) {
				//表格显示列
				cols.add(new JTableListColumn("料件备案序号（必填）", "seqNum", 120));
				cols.add(new JTableListColumn("手册号（必填）", "emsNo", 100));
				cols.add(new JTableListColumn("已收货未转厂（必填）", "unTransHadReceiveNum", 120));
				cols.add(new JTableListColumn("已收货未转厂（必填）", "unReceiveHadTransNum", 120));
				cols.add(new JTableListColumn("报关品名", "hsName", 100));
				cols.add(new JTableListColumn("报关规格", "hsSpec", 100));
				cols.add(new JTableListColumn("计量单位", "hsUnit", 60));
				cols.add(new JTableListColumn("报关单数量", "customsBillNum", 80));
				cols.add(new JTableListColumn("收货单数量", "receiveBillNum", 80));
				cols.add(new JTableListColumn("归并序号", "mergerNo", 100));
				cols.add(new JTableListColumn("错误信息", "errMsg", 300));
				//导入数据
				colLs.add("seqNum");
				colLs.add("emsNo");
				colLs.add("unTransHadReceiveNum");
				colLs.add("unReceiveHadTransNum");
				colLs.add("hsName");
				colLs.add("hsSpec");
				colLs.add("hsUnit");
				colLs.add("customsBillNum");
				colLs.add("receiveBillNum");
			}
		}
		return cols;
	}

	private JToolBar getPanel() {
		if (tbarNorth == null) {
			tbarNorth = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setHgap(0);
			f.setVgap(0);
			tbarNorth.setLayout(f);
			tbarNorth.add(getBtnOpen());
			tbarNorth.add(getBtnSave());
			tbarNorth.add(getBtnDel());
			tbarNorth.add(getBtnDLTpl());
			tbarNorth.add(getBtnExit());
			tbarNorth.add(getCbJF());
			tbarNorth.add(getCbCheckTitle());
		}
		return tbarNorth;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(initTable(Collections.EMPTY_LIST));
		}
		return table;
	}

	private JTableListModel initTable(List data) {
		if (tbModel == null) {
			tbModel = new JTableListModel(table, data,
					new JTableListModelAdapter() {
						@Override
						public List<JTableListColumn> InitColumns() {
							return getTableColumns();
						}
					});
			/**
			 * 错误信息行颜色设置为黄色
			 */
			TableCellRenderer boolTcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
					if ("".equals(value) || value == null) {
						if (row == table.getSelectedRow()) {
							setForeground(table.getSelectionForeground());
							setBackground(table.getSelectionBackground());
						} else {
							setForeground(table.getForeground());
							setBackground(table.getBackground());
						}
					} else {
						this.setBackground(new java.awt.Color(255, 255, 150));
					}
					return this;
				}
			};
			table.getColumnModel().getColumn(table.getColumnCount() - 1)
					.setCellRenderer(boolTcr);
		} else {
			tbModel.setList(data);
		}
		return tbModel;
	}

	private JButton getBtnOpen() {
		if (btnOpen == null) {
			btnOpen = new JButton("1.打开文本");
			btnOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser.showOpenDialog(getContentPane());
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImpWorker().doWork();
				}
			});
			btnOpen.setPreferredSize(new Dimension(80, 30));
		}
		return btnOpen;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("2.保存数据");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final List<DynaBean> ls = tbModel.getList();
					if (ls == null || ls.isEmpty())
						return;
					if (hasErr) {
						JOptionPane.showMessageDialog(DgImpDiffExgImg.this,
								"存在错误，请查看列表最后一列的错误！", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					new AsynSwingWorker() {
						long t = 0;

						protected Object submit() {
							t = System.currentTimeMillis();
							
							saveData(ls); // 保存数据
							return null;
						}

						protected void success(Object rs) {
							initTable(Collections.EMPTY_LIST);
							JOptionPane.showMessageDialog(DgImpDiffExgImg.this,
									"保存成功!共使用"
											+ (System.currentTimeMillis() - t)
											+ "毫秒！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							DgImpDiffExgImg.this.dispose();
							if (dataChangeCallback != null) {
								dataChangeCallback.run();
							}
						}
					}.doWork();
				}
			});
			btnSave.setPreferredSize(new Dimension(80, 30));
		}
		return btnSave;
	}
	
	
	
	private String getExtension(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	/**
	 * 转换数据
	 * 
	 * @param file
	 * @return
	 */
	private List parseTxtFile(File file) {
		hasErr = false;
		List<DynaBean> rsLs = null;
		String suffix = getExtension(file);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			// 导入xls
			System.out.println("XSL 导入 >>>>>>>>>>>>>>>>>>>  ");
			lines = FileReading.readExcel(file, 0, null);
		} else {
			// 导入txt
			System.out.println("TXT 导入 >>>>>>>>>>>>>>>>>>>  ");
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		long t = System.currentTimeMillis();		
		System.out.println("准备数据耗时:" + (System.currentTimeMillis() - t) / 1000d+ "秒");
		t = System.currentTimeMillis();
		rsLs = new ArrayList<DynaBean>(cbCheckTitle.isSelected() ? lines.size() - 1 : lines.size());

		DynaBean bean = null;
		int zcount = colLs.size();
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			if (i % 100 == 0)
				CommonProgress.setMessage("文件总共有【" + lines.size()+ "】行数据,现导入数据【" + i + "】行,请稍等...");
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = (obj == null ? null : StringUtils.trimToEmpty(obj.toString()));
			}

			if (getCbJF().isSelected()) {
				strs = changStrs(strs);
			}
			bean = new LazyDynaBean(new LazyDynaClass());
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = colLs.get(j);
				if(value != null && !value.isEmpty())
					bean.set(columnField, value);
			}			
			rsLs.add(bean);
		}
		if(section.getIsImportHs()){
			processHsValues(rsLs);
		}else{
			processPtValues(rsLs);
		}
	
		System.out.println("匹配耗时:" + (System.currentTimeMillis() - t) / 1000d+ "秒");
		t = System.currentTimeMillis();
		return rsLs;
	}

	/**当导入的是编码级资料--当报关品名和报关规格，计量单位栏位没填时，
	 *根据手册号+备案号取来自于合同补充完整报关品名和报关规格，计量单位
	 * @param bean
	 * @param Object
	 */
	private void setHsDefaultValue(DynaBean bean, String haName,String hsSpec,Unit unit,Integer mergerNo) {
		bean.set("hsName", haName);
		bean.set("hsSpec", hsSpec);
		bean.set("hsUnit", unit==null?"":unit.getName());
		bean.set("mergerNo", mergerNo);
	}
	
	/**
	 * 验证并设置默认值（料号级别）
	 * 
	 * @param bean
	 *            行数据
	 * @param imMap
	 *            对应关系列表
	 * @param bomVersionMap
	 *            bom版本列表
	 */
	private void processPtValues(List<DynaBean> beans) {
		Request request = new Request(CommonVars.getCurrUser());
		// 对应关系表
		Map<String, BcsInnerMerge> imMap = new HashMap<String, BcsInnerMerge>();
		// 成品BomVersion
		Map<String, MaterialBomVersion> bomVersionMap = null;
		if (imp_flag == IMP_DIFFIMG) {
			List<BcsInnerMerge> merges = vfAction.findBcsInnerMerge(request,MaterielType.MATERIEL);
			if (merges != null && !merges.isEmpty()) {
				for (BcsInnerMerge merge : merges) {
					imMap.put(merge.getMateriel().getPtNo(), merge);
				}
			}
		} else if (imp_flag == IMP_DIFFEXG) {
			bomVersionMap = vfAction.getMaterielMasterBomVersion(request);
		}
		String errMsg = null;		
		for(DynaBean bean :beans){			
			errMsg = validPt(bean, imMap, bomVersionMap);
			setPtDefaultVal(bean, imMap, bomVersionMap);			
			if (StringUtils.isNotBlank(errMsg)) {
				bean.set("errMsg", errMsg);
				hasErr = true;
			}
		}
	}
	/**
	 * 处理编码级数据
	 * @param beans
	 */
	private void processHsValues(List<DynaBean> beans){
		/**初始化数据*/
		List<String> emsNos = new ArrayList<String>();
		for(int i= 0;i<beans.size();i++){
			String emsNo = (String) (beans.get(i)).get("emsNo");				
			emsNos.add(emsNo);
		}
		Map<String,Integer> mergeMap = new HashMap<String, Integer>();
		Map<String,Object> hsMap = new HashMap<String, Object>();
		String key = null;
		Object[] obj  = null;
		if (imp_flag == IMP_DIFFIMG) {
			List<Object[]> listImg = vfAction.findContractImgByEmsNo(new Request(CommonVars.getCurrUser()), emsNos);
			ContractImg img = null;
			for (int j = 0; j < listImg.size(); j++) {
				obj = listImg.get(j);
				img  =(ContractImg)obj[0];
				Integer mergerNo = (Integer)obj[1];
				key = img.getSeqNum()+"#"+img.getContract().getEmsNo();
				mergeMap.put(key, mergerNo);
				hsMap.put(key, img);					
			}
		}else{
			List<Object[]> listExg = vfAction.findContractExgByEmsNo(new Request(CommonVars.getCurrUser()), emsNos);
			ContractExg exg = null;
			for (int j = 0; j < listExg.size(); j++) {
				obj = listExg.get(j);
				exg  =(ContractExg)obj[0];
				Integer mergerNo = (Integer)obj[1];
				key = exg.getSeqNum()+"#"+exg.getContract().getEmsNo();
				mergeMap.put(key, mergerNo);
				hsMap.put(key,exg);					
			}
		}
		/**验证和设置默认值**/
		String errMsg = null;		
		for(DynaBean bean :beans){			
			errMsg = validHs(bean,mergeMap,hsMap);
			setHsDefaultVal(bean, mergeMap, hsMap);			
			if (StringUtils.isNotBlank(errMsg)) {
				bean.set("errMsg", errMsg);
				hasErr = true;
			}
		}
	}
	
	/**
	 * 设置编码级默认数据
	 * @param bean
	 * @param mergeMap
	 * @param hsMap
	 */
	private void setHsDefaultVal(DynaBean bean, Map<String, Integer> mergeMap,Map<String, Object> hsMap) {
		if (imp_flag == IMP_DIFFIMG) {
			String emsNo = (String) bean.get("emsNo");			
			String seqNum =  bean.get("seqNum").toString();
			String key = seqNum+"#"+emsNo;
			ContractImg exg  =(ContractImg)hsMap.get(key);				
			Integer mergerNo = mergeMap.get(key);
			if(exg != null)
				setHsDefaultValue(bean,exg.getName(),exg.getSpec(),exg.getUnit(),mergerNo);
			
			String tmp = StringUtils.trimToEmpty((String) bean.get("unTransHadReceiveNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unTransHadReceiveNum");
				bean.set("unTransHadReceiveNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("unReceiveHadTransNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unReceiveHadTransNum");
				bean.set("unReceiveHadTransNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("customsBillNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("customsBillNum");
				bean.set("customsBillNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("receiveBillNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("receiveBillNum");
				bean.set("receiveBillNum", NumberUtils.toDouble(tmp,0));
			}			
		} else if(imp_flag==IMP_DIFFEXG){							
			String emsNo = (String) bean.get("emsNo");
			String seqNum =  bean.get("seqNum").toString();
			String key = seqNum+"#"+emsNo;				
			ContractExg exg  =(ContractExg)hsMap.get(key);				
			Integer mergerNo = mergeMap.get(key);
			if(exg != null)
				setHsDefaultValue(bean,exg.getName(),exg.getSpec(),exg.getUnit(),mergerNo);
			
			String tmp = StringUtils.trimToEmpty((String) bean.get("unTransHadSendNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {	
				((LazyDynaClass) bean.getDynaClass()).remove("unTransHadSendNum");
				bean.set("unTransHadSendNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("unSendHadTransNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unSendHadTransNum");
				bean.set("unSendHadTransNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("customsBillNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("customsBillNum");
				bean.set("customsBillNum", NumberUtils.toDouble(tmp,0));
			}
			tmp = StringUtils.trimToEmpty((String) bean.get("sendBillNum"));
			if (NumberUtils.isNumber(tmp) || StringUtils.isEmpty(tmp)) {
				((LazyDynaClass) bean.getDynaClass()).remove("sendBillNum");
				bean.set("sendBillNum", NumberUtils.toDouble(tmp,0));
			}
		}
	}

	/**
	 * 料号级别设置默认值
	 * @param bean
	 * @param imMap
	 * @param bomVersionMap
	 */
	private void setPtDefaultVal(DynaBean bean,Map<String, BcsInnerMerge> imMap,Map<String, MaterialBomVersion> bomVersionMap){
		String ptNo = (String) bean.get("ptNo");
		if (imp_flag == IMP_DIFFEXG) {
			if (!StringUtils.isBlank(ptNo)) {				
				String key = ptNo+ "#"+ CommonUtils.getStringExceptNull(bean.get("bomVersion"));
				MaterialBomVersion mbv = getVersion(key, bomVersionMap);
				if(mbv != null){
				if (mbv != null && mbv.getMaster() != null|| mbv.getMaster().getMateriel() != null) {
					bean.set("bomVersion", String.valueOf(mbv.getVersion()));
					bean.set("ptName", mbv.getMaster().getMateriel().getFactoryName());
					bean.set("ptSpec", mbv.getMaster().getMateriel().getFactorySpec());
					if (mbv.getMaster().getMateriel().getCalUnit() != null) {
						bean.set("ptUnit", mbv.getMaster().getMateriel().getCalUnit().getName());
					}
				}
				}
			}
			String unTransferNum = StringUtils.trimToEmpty((String) bean.get("unTransferNum"));
			if (NumberUtils.isNumber(unTransferNum) || StringUtils.isEmpty(unTransferNum)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unTransferNum");
				bean.set("unTransferNum", NumberUtils.toDouble(unTransferNum,0));
			}
			String unSendferNum = StringUtils.trimToEmpty((String) bean.get("unSendferNum"));
			if (NumberUtils.isNumber(unSendferNum) || StringUtils.isEmpty(unSendferNum)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unSendferNum");
				bean.set("unSendferNum", NumberUtils.toDouble(unSendferNum,0));
			}
		} else if (imp_flag == IMP_DIFFIMG) { // 料件验证
			if (StringUtils.isNotBlank(ptNo)) {				
				BcsInnerMerge ie = imMap.get(ptNo);
				if (ie != null && ie.getBcsTenInnerMerge() != null) {					
					if (ie.getMateriel() != null) {
						bean.set("ptName", ie.getMateriel().getFactoryName());
						bean.set("ptSpec", ie.getMateriel().getFactorySpec());
					} 
					if (ie.getMateriel().getCalUnit() != null) {
						bean.set("ptUnit", ie.getMateriel().getCalUnit().getName());
					}
				}
			}
			String unTransferNum = StringUtils.trimToEmpty((String) bean.get("unTransferNum"));
			if (StringUtils.isEmpty(unTransferNum) || NumberUtils.isNumber(unTransferNum)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unTransferNum");
				bean.set("unTransferNum", NumberUtils.toDouble(unTransferNum,0));
			}
			String unReceiveNum = StringUtils.trimToEmpty((String) bean.get("unReceiveNum"));
			if (StringUtils.isEmpty(unReceiveNum) || NumberUtils.isNumber(unReceiveNum)) {
				((LazyDynaClass) bean.getDynaClass()).remove("unReceiveNum");
				bean.set("unReceiveNum", NumberUtils.toDouble(unReceiveNum,0));
			}
		}
	}
	
	/**
	 * 料件级验证
	 * @param bean
	 * @param imMap
	 * @param bomVersionMap
	 * @return
	 */
	private String validPt(DynaBean bean,Map<String, BcsInnerMerge> imMap,Map<String, MaterialBomVersion> bomVersionMap){
		String errMsg = "";
		String ptNo = (String) bean.get("ptNo");
		if (imp_flag == IMP_DIFFEXG) {
			if (StringUtils.isBlank(ptNo)) {
				errMsg += "【成品料号】不能为空！";
			} else {
				String key = ptNo+ "#"+ CommonUtils.getStringExceptNull(bean.get("bomVersion"));
				MaterialBomVersion mbv = getVersion(key, bomVersionMap);
				if (mbv == null || mbv.getMaster() == null|| mbv.getMaster().getMateriel() == null) {
					errMsg += "成品BOM在【报关常用工厂BOM】中不存在！";
				} else {
					if (mbv.getMaster().getMateriel().getCalUnit() == null) {
						errMsg += "成品BOM在【报关常用工厂BOM】的【工厂单位】不存在！";
					}
				}
			}
			String unTransferNum = StringUtils.trimToEmpty((String) bean.get("unTransferNum"));
			if (StringUtils.isNotEmpty(unTransferNum) && !NumberUtils.isNumber(unTransferNum)) {
				errMsg += "【已送货未转厂】必须是数字！";
			}
			String unSendferNum = StringUtils.trimToEmpty((String) bean.get("unSendferNum"));
			if (StringUtils.isNotEmpty(unSendferNum) && !NumberUtils.isNumber(unSendferNum)) {
				errMsg += "【已转厂未送货】必须是数字！";
			}
			if(StringUtils.isEmpty(unTransferNum) && StringUtils.isEmpty(unSendferNum)){
				errMsg += "【已送货未转厂】【已转厂未送货】两个栏位不能同时为空！";
			}
		} else if (imp_flag == IMP_DIFFIMG) { // 料件验证
			if (StringUtils.isBlank(ptNo)) {
				errMsg += "【料件料号】不能为空！";
			} else if (imMap.get(ptNo) == null) {
				errMsg += "【工厂料号】在【物料与报关对应表】中不存！";
			} else {
				BcsInnerMerge ie = imMap.get(ptNo);
				if (ie.getBcsTenInnerMerge() == null) {
					errMsg += "【工厂料号】在【物料与报关对应表】中的【报关商品资料】不能为空！";
				} else {
					if (ie.getMateriel() == null) {
						errMsg += "【工厂料号】在【物料与报关对应表】中的【物料】不存在！";
					} 
					if (ie.getMateriel().getCalUnit() == null) {						
						errMsg += "【工厂料号】在【物料与报关对应表】中的【物料工厂单位】不能为空！";
					}
				}
			}
			String unTransferNum = StringUtils.trimToEmpty((String) bean.get("unTransferNum"));
			if (StringUtils.isNotEmpty(unTransferNum) && !NumberUtils.isNumber(unTransferNum)) {
				errMsg += "【已收货未转厂】必须是数字！";
			}
			String unReceiveNum = StringUtils.trimToEmpty((String) bean.get("unReceiveNum"));
			if (StringUtils.isNotEmpty(unReceiveNum) &&!NumberUtils.isNumber(unReceiveNum)) {			
				errMsg += "【已转厂未收货】必须是数字！";
			}
			
			if(StringUtils.isEmpty(unTransferNum) && StringUtils.isEmpty(unReceiveNum)){
				errMsg += "【已收货未转厂】【已转厂未收货】两个栏位不能同时为空！";
			}
		}
		return errMsg;
	}
	/**
	 * 编码级验证
	 * @param bean
	 * @return
	 */
	private String validHs(DynaBean bean,Map<String, Integer> mergeMap,Map<String, Object> hsMap){
		String errMsg = "";
		String seqNum = StringUtils.trimToEmpty((String) bean.get("seqNum"));
		if(StringUtils.isEmpty(seqNum)){
			errMsg += "【备案序号】不能为空！";
		}else if(!NumberUtils.isNumber(seqNum)){
			errMsg += "【备案序号】必须是数字！";
		}
		String emsNo = StringUtils.trimToEmpty((String)bean.get("emsNo"));
		String key = seqNum+"#"+emsNo;
		if(StringUtils.isEmpty(emsNo)){
			errMsg += "【手册号】不能为空！";
		}else if(StringUtils.isNotEmpty(seqNum)){
			if(hsMap.get(key) == null)
			errMsg += "手册【"+emsNo+"】备案序号【"+seqNum+"】不存在！";
		}
		 
		if (imp_flag == IMP_DIFFEXG) {//验证成品			
			String unTransHadReceiveNum = StringUtils.trimToEmpty((String) bean.get("unTransHadSendNum"));
			if(StringUtils.isNotEmpty(unTransHadReceiveNum) && !NumberUtils.isNumber(unTransHadReceiveNum)){
				errMsg += "【已送货未转厂】必须是数字！";
			}
			String unReceiveHadTransNum = StringUtils.trimToEmpty((String) bean.get("unSendHadTransNum"));
			if(StringUtils.isNotEmpty(unReceiveHadTransNum) && !NumberUtils.isNumber(unReceiveHadTransNum)){
				errMsg += "【已转厂未送货】必须是数字！";
			}
			if(StringUtils.isEmpty(unTransHadReceiveNum) && StringUtils.isEmpty(unReceiveHadTransNum)){
				errMsg += "【已送货未转厂】【已转厂未送货】两个栏位不能同时为空！";
			}
			String customsBillNum = StringUtils.trimToEmpty((String) bean.get("customsBillNum"));
			if(StringUtils.isNotBlank(customsBillNum) && !NumberUtils.isNumber(customsBillNum)){
				errMsg += "【报关单数量】必须是数字！";
			}
			String sendBillNum = StringUtils.trimToEmpty((String) bean.get("sendBillNum"));
			if(StringUtils.isNotBlank(customsBillNum) && !NumberUtils.isNumber(sendBillNum)){
				errMsg += "【发货单数量】必须是数字！";
			}			
			
		}else if(imp_flag == IMP_DIFFIMG){//验证料件
			if(StringUtils.isNotEmpty(seqNum) && StringUtils.isNotEmpty(emsNo)){
				if(mergeMap.get(key) == null){
					errMsg += "无法找到归并序号！";					
				}
			}
			String unTransHadReceiveNum = StringUtils.trimToEmpty((String) bean.get("unTransHadReceiveNum"));
			if(StringUtils.isNotEmpty(unTransHadReceiveNum) && !NumberUtils.isNumber(unTransHadReceiveNum)){
				errMsg += "【已收货未转厂】必须是数字！";
			}
			String unReceiveHadTransNum = StringUtils.trimToEmpty((String) bean.get("unReceiveHadTransNum"));
			if(StringUtils.isNotEmpty(unReceiveHadTransNum) &&!NumberUtils.isNumber(unReceiveHadTransNum)){
				errMsg += "【已转厂未收货】必须是数字！";
			}
			if(StringUtils.isEmpty(unTransHadReceiveNum) && StringUtils.isEmpty(unReceiveHadTransNum)){
				errMsg += "【已收货未转厂】【已转厂未收货】两个栏位不能同时为空！";
			}
			String customsBillNum = StringUtils.trimToEmpty((String) bean.get("customsBillNum"));
			if(StringUtils.isNotBlank(customsBillNum) && !NumberUtils.isNumber(customsBillNum)){
				errMsg += "【报关单数量】必须是数字！";
			}
			String receiveBillNum = StringUtils.trimToEmpty((String) bean.get("receiveBillNum"));
			if(StringUtils.isNotBlank(receiveBillNum) && !NumberUtils.isNumber(receiveBillNum)){
				errMsg += "【收货单数量】必须是数字！";
			}			
		}
		return errMsg;
	}

	/**
	 * 检查成品料件成品版本是否存在
	 * 
	 * @param key
	 * @param ls
	 * @return
	 */
	private MaterialBomVersion getVersion(String key,Map<String, MaterialBomVersion> map) {
		Set<Entry<String, MaterialBomVersion>> set = map.entrySet();
		for (Entry<String, MaterialBomVersion> e : set) {
			if (e.getKey().startsWith(key)) {
				return e.getValue();
			}
		}
		return null;
	}

	/**
	 * 获取单元格数据
	 * 
	 * @param fileRow
	 * @param dataIndex
	 * @return
	 */
	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	/**
	 * 导入操作
	 * 
	 * @author xc
	 * 
	 */
	class ImpWorker extends AsynSwingWorker<List<DynaBean>> {
		@Override
		protected List<DynaBean> submit() {
			setButtonState(false);
			List rsLs = null;
			try {
				long beginTime = System.currentTimeMillis();
				String info = "";
				CommonProgress.showProgressDialog(DgImpDiffExgImg.this);
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				rsLs = parseTxtFile(txtFile);
				CommonProgress.closeProgressDialog();
				info += " 数据导入完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgImpDiffExgImg.this, info, "提示",
						2);
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				new ExceptionHandler().handle(new AppException("导入数据失败 ", ex));
			} finally {
				setButtonState(true);
			}
			return rsLs;
		}

		protected void success(List<DynaBean> result) {
			if (result == null) {
				result = Collections.EMPTY_LIST;
			}
			initTable(result);
		}
	}

	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("退出");
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnExit.setPreferredSize(new Dimension(40, 30));
		}
		return btnExit;
	}

	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox("繁转简");
		}
		return cbJF;
	}

	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox("第一行为标题");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	public void setDataChangeCallback(Runnable dataChangeCallback) {
		this.dataChangeCallback = dataChangeCallback;
	}

	/**
	 * 繁体转换成简体
	 * 
	 * @param source
	 * @return
	 */
	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private void setButtonState(boolean enable) {
		btnExit.setEnabled(enable);
		btnOpen.setEnabled(enable);
		btnSave.setEnabled(enable);
		btnDel.setEnabled(enable);
	}

	/**
	 * 删除错误按钮
	 * 
	 * @return
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton("3.删除错误");
			btnDel.setPreferredSize(new Dimension(80, 30));
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<LazyDynaBean> list = tbModel.getList();
					List li = new ArrayList();
					for (int i = 0; i < list.size(); i++) {
						LazyDynaBean bean = list.get(i);
						String error = (String) bean.get("errMsg");
						if (error == null || "".equals(error.trim())) {
							li.add(list.get(i));
						}
					}
					initTable(li);
					hasErr = false;
				}
			});
		}
		return btnDel;
	}
	private JButton getBtnDLTpl() {
		if (btnDLTpl == null) {
			btnDLTpl = new JButton("下载导入模版");
			btnDLTpl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
					FileUtils.exportTableToExcel(tbModel, getParent());					
				}
			});
		}
		return btnDLTpl;
	}
}
