package com.bestway.bcs.client.verification.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.client.verification.DgVFBaseStockExgOrImgEdit;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFStockOutSendExg;
import com.bestway.bcs.verification.entity.VFStockOutSendExgConvert;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
//import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
//import com.bestway.bcus.client.common.CustomBaseRender;

public class FmVFStockOutSendExg extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JToolBar jtoolBar;
	private JComboBox comboBox;
	/**
	 * 导入
	 */
	private JButton btnImport;
	/**
	 * 历史
	 */
	private JButton btnHistroy;
	/**
	 * 折算
	 */
	private JButton btnSwitch;
	/**
	 * 导出
	 */
	private JButton btnExport;
	/**
	 * 清除
	 */
	private JButton btnClean;

	private JTableListModel tableModePrimary;
	private JTableListModel tableModeHandle;

	private VFVerificationAction vfAction;

	private VFSection vfSection;
	public JTabbedPane tabbedPane;
	private JScrollPane scrollPanePrimary;
	private JScrollPane scrollPaneHandle;
	private JTable tablePrimary;
	private MultiSpanCellTable tableHandle;
	private JButton btnSwitchImg;
	private Request request;

	private JTableListModelAdapter adapterRawdata;
	private JTextField tfseqNum;
	private JLabel lblNewLabel;
	private VFVerificationAction vfVerificationAction;
	private JButton btnEdit;
	private JButton btnDelete;
	/**
	 * Create the frame.
	 */
	public FmVFStockOutSendExg() {
		this.setTitle("外发库存成品");
		request = new Request(CommonVars.getCurrUser());
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");	
		vfVerificationAction = (VFVerificationAction) CommonVars
		.getApplicationContext().getBean("vfVerificationAction");
		authority.checkStockOutSendExg(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * 初始化
	 */
	private void initialize() {

		getContentPane().add(getJToolBar(), BorderLayout.NORTH);
		getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
		initTablePrimary(null);
		initTableHandle(null);
	}

	/**
	 * 初始化表格 原始数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initTablePrimary(List<VFStockOutSendExg> list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
		}

		tableModePrimary = new JTableListModel(tablePrimary, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();

				list.add(addColumn("成品料号", "ptNo", 160));
				list.add(addColumn("工厂名称", "ptName", 160));
				list.add(addColumn("工厂规格", "ptSpec", 160));
				list.add(addColumn("工厂单位", "ptUnit", 80));
				list.add(addColumn("库存数量", "storeAmount", 100));
				list.add(addColumn("版本号", "version", 100));
				list.add(addColumn("仓库", "warehouse", 100));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		});
	}

	/**
	 * 初始化表格 折算料件 数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initTableHandle(List<VFStockOutSendExgConvert> list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
		}
		if (adapterRawdata == null) {
			adapterRawdata = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();

					list.add(addColumn("成品料号", "stockExg.ptNo", 100));
					list.add(addColumn("版本号", "stockExg.version", 100));
					list.add(addColumn("库存数量", "stockExg.storeAmount", 100));

					list.add(addColumn("料件料号", "ptNo", 100));
					list.add(addColumn("工厂名称", "ptName", 100));
					list.add(addColumn("工厂规格", "ptSpec", 100));
					list.add(addColumn("工厂单位", "ptUnit", 50));
					list.add(addColumn("库存数量", "storeAmount", 100));
					list.add(addColumn("单耗", "unitWaste", 100));
					list.add(addColumn("损耗", "waste", 100));
					list.add(addColumn("单项用量", "unitUsed", 100));
					list.add(addColumn("成品耗用量", "storeAmount", 100));

					list.add(addColumn("归并序号", "mergerNo", 100)); // mergerNo
					list.add(addColumn("报关商品名称", "hsName", 100));
					list.add(addColumn("报关商品规格", "hsSpec", 100));
					list.add(addColumn("计量单位", "hsUnit", 50));
					list.add(addColumn("折算报关数量", "hsAmount", 100));
					list.add(addColumn("折算系数", "unitConvert", 100));
					
					return list;
				}
			};
		}

		tableModeHandle = new AttributiveCellTableModel((MultiSpanCellTable) tableHandle, list, adapterRawdata);

		TableColumnModel cm = tableHandle.getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂成品】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		ColumnGroup ptImpGroup = new ColumnGroup("【工厂料件】");
		ptImpGroup.add(cm.getColumn(4));
		ptImpGroup.add(cm.getColumn(5));
		ptImpGroup.add(cm.getColumn(6));
		ptImpGroup.add(cm.getColumn(7));
		ptImpGroup.add(cm.getColumn(8));
		ptImpGroup.add(cm.getColumn(9));
		ptImpGroup.add(cm.getColumn(10));
		ptImpGroup.add(cm.getColumn(11));
		ptImpGroup.add(cm.getColumn(12));
		ColumnGroup hsImpGroup = new ColumnGroup("【报关料件】");
		hsImpGroup.add(cm.getColumn(13));
		hsImpGroup.add(cm.getColumn(14));
		hsImpGroup.add(cm.getColumn(15));
		hsImpGroup.add(cm.getColumn(16));
		hsImpGroup.add(cm.getColumn(17));
		// hsImpGroup.add(cm.getColumn(18));

		GroupableTableHeader header = (GroupableTableHeader) tableHandle.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(ptImpGroup);
		header.addColumnGroup(hsImpGroup);
	}

	/**
	 * 按钮
	 * 
	 * @return
	 */
	private JToolBar getJToolBar() {
		if (jtoolBar == null) {
			jtoolBar = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jtoolBar.setLayout(f);
			JLabel label = new JLabel("\u6279\u6B21\u9009\u62E9");
			jtoolBar.add(label);

			jtoolBar.add(getJComboBox());
			jtoolBar.add(getLblNewLabel());
			jtoolBar.add(getTfSection());

			jtoolBar.add(getBtnHistroy());

			jtoolBar.add(getBtnImport());

			jtoolBar.add(getBtnSwitchImg());

			jtoolBar.add(getBtnSwitch());
			jtoolBar.add(getBtnEdit());
			jtoolBar.add(getBtnDelete());
			jtoolBar.add(getBtnExport());

			jtoolBar.add(getBtnClean());
		}
		return jtoolBar;
	}

	/**
	 * 下拉框
	 * 
	 * @return
	 */
	public JComboBox getJComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setEditable(true);
			comboBox.setPreferredSize(new Dimension(120, 27));
			comboBox.setBounds(new Rectangle(65, 1, 60, 27));
			DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel();
			// 格式化时间
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<VFSection> dataSource = vfAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
			List<VFSection> manulist = new ArrayList<VFSection>();
			for (int i = 0; i < dataSource.size(); i++) {
				VFSection vfSection = (VFSection) dataSource.get(i);
				vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
				manulist.add(vfSection);
			}
			DfComboBox = new DefaultComboBoxModel(manulist.toArray());
			this.comboBox.setModel(DfComboBox);
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox, "verificationNo", "endDate", 100);
			this.comboBox.setRenderer(CustomBaseRender.getInstance().getRender("verificationNo", "endDate", 30, 100));
			this.comboBox.setSelectedItem(null);
		}
		return comboBox;
	}

	/**
	 * 导入
	 * 
	 * @return
	 */
	public JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("1.导入盘点数据");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 导入盘点数据
					if (checkData()) {
						final Request req = new Request(CommonVars.getCurrUser());
						if(vfAction.isExistsBySection(req, vfSection, VFStockOutSendExg.class)){						
							if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?","提示", JOptionPane.YES_NO_OPTION, 
									JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
								return;
							}
							vfAction.deleteVFStockOutSendExgsConvert(request, vfSection);
							vfAction.deleteVFStockOutSendExgs(request, vfSection);
							initTablePrimary(null);
							initTableHandle(null);
						}
						DgVFStockOutSendImg dgVFStockOutSendImg = new DgVFStockOutSendImg();
						dgVFStockOutSendImg.setVfSection(vfSection);
						dgVFStockOutSendImg.setFmVFStockOutSendImg(FmVFStockOutSendExg.this);
						dgVFStockOutSendImg.setVisible(true);
					
					}
				}
			});
		}
		return btnImport;
	}

	/**
	 * 折算料件
	 * 
	 * @return
	 */
	private JButton getBtnSwitchImg() {
		if (btnSwitchImg == null) {
			btnSwitchImg = new JButton(
					"2.\u6210\u54C1\u6298\u7B97\u6599\u4EF6\uFF08\u62A5\u5173\u5E38\u7528\u5DE5\u5382BOM\uFF09");
			btnSwitchImg.setEnabled(true);
			btnSwitchImg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkData()) {
						new SwitchImgWorker().execute();
					}
				}
			});
		}
		return btnSwitchImg;
	}

	/**
	 * 数据折算 工作线程
	 * 
	 * @author Administrator
	 * 
	 */
	@SuppressWarnings("rawtypes")
	class SwitchImgWorker extends SwingWorker {
		@Override
		protected List<VFStockOutSendExgConvert> doInBackground() throws Exception {
			List<VFStockOutSendExgConvert> list = null;
			try {
				CommonProgress.showProgressDialog(FmVFStockOutSendExg.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				setButtonState(false);
				list = vfAction.convertFactoryVFStockOutSendExgsConvert(request, vfSection);// 初始化查询条件
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				//保存BOM版本，用于查找该成品BOM是否存在
				Map<String, MaterialBomVersion> versionMap = new HashMap<String, MaterialBomVersion>();
				List<MaterialBomVersion> bom = vfVerificationAction.findMaterialBomVersions(request);
				for (int i = 0; i < bom.size(); i++) {
					MaterialBomVersion v = (MaterialBomVersion) bom.get(i);
					versionMap.put(v.getMaster().getMateriel().getPtNo()
							+ "," + v.getVersion(), v);
				}
				List<VFStockOutSendExg> exgs =vfVerificationAction.findVFStockOutSendExgs(request,vfSection);
				VFStockOutSendExg exgss = null;
				List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
				for(int i=0;i<exgs.size();i++){
					exgss=exgs.get(i);
					if(versionMap.get(exgss.getPtNo() + "," + exgss.getVersion()) == null){
						errs.add(new ErrorMessage(exgss.getPtNo() ,"版本号" +  exgss.getVersion() +"在报关常用工厂BOM不存在"));
					}
				}
				if(errs.size() > 0){
					DgErrorMessage.show(errs);
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFStockOutSendExg.this, "数据折算失败：！" + e.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return list;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void done() {
			List<VFStockOutSendExgConvert> li = null;
			try {
				// initTableHandle((List<VFStockOutSendExgConvert>)this.get());
				li = (List<VFStockOutSendExgConvert>) this.get();
				tabbedPane.setSelectedIndex(1);
				initTableHandle(li);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				setButtonState(true);
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 折算报关单
	 * 
	 * @return
	 */
	public JButton getBtnSwitch() {
		if (btnSwitch == null) {
			btnSwitch = new JButton("3.\u6298\u7B97\u62A5\u5173\u6570\u91CF");
			btnSwitch.setEnabled(true);
			btnSwitch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkData()) {
						new SwitchExgWorker().execute();
					}
				}
			});
		}
		return btnSwitch;
	}

public class SwitchExgWorker extends SwingWorker{
		
		public long startTime = 0;
		
		@Override
		protected Object doInBackground() throws Exception {
			startTime = System.currentTimeMillis();
			
			CommonProgress.showProgressDialog(FmVFStockOutSendExg.this);
			CommonProgress.setMessage("系统正在折算报关数量，请稍后...");
			List<VFStockOutSendExgConvert> ListExgConvert = new ArrayList<VFStockOutSendExgConvert>();
			String logg =null;
			try {
				logg = vfAction.convertHsVFStockOutSendExgsConvert(
						request, vfSection);
				if (logg != null && !logg.isEmpty()) {
					String[] ptNos = logg.toString().split(",");
					List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
					for (int i = 0; i < ptNos.length; i++) {
						errs.add(new ErrorMessage(ptNos[i],
								"在【物料与报关对应表】中不存在,或找不到对应关系"));
					}
					DgErrorMessage.show(errs);
				}
				ListExgConvert = vfAction.findVFStockOutSendExgsConvert(request, vfSection,null);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				
			}finally{
				CommonProgress.closeProgressDialog();
			}
			return ListExgConvert;
		}
		
		@Override
		protected void done() {
			List<VFStockOutSendExgConvert>  ListExgConvert;
			try {
				ListExgConvert = (List<VFStockOutSendExgConvert>) get();
				tabbedPane.setSelectedIndex(1);
				if(ListExgConvert != null && ListExgConvert.size()>0){
					initTableHandle(ListExgConvert);
				}
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(FmVFStockOutSendExg.this,e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(FmVFStockOutSendExg.this,e.getMessage());
			}finally{
				long finishTime = System.currentTimeMillis();
				System.out.println("3.折算报关数量共用时："+(finishTime-startTime)/1000+"秒,合计为"
						+((finishTime-startTime)/1000)/1000+"分钟");
			}
		}
	}
	/**
	 * 历史
	 * 
	 * @return
	 */
	public JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton("查询批次数据");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkData()) {
						setButtonState(false);
						String seqNum = tfseqNum.getText().trim();						
						int index = tabbedPane.getSelectedIndex();
						if (index == 0) {
							btnImport.setEnabled(true);
							List<VFStockOutSendExg> dataSource = vfAction.findVFStockOutSendExgs(request, vfSection);
							initTablePrimary(dataSource);
						} else if (index == 1) {
							btnImport.setEnabled(false);
							List<VFStockOutSendExgConvert> dataSource = vfAction.findVFStockOutSendExgsConvert(request,
									vfSection, (seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
							initTableHandle(dataSource);
						}
						setButtonState(true);
					}
				}
			});
		}
		return btnHistroy;
	}

	/**
	 * 导出
	 * 
	 * @return
	 */
	public JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton("\u5BFC\u51FAExcel");
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
					if (checkData()) {

						int index = tabbedPane.getSelectedIndex();
						if (index == 0 && tableModePrimary.getRowCount() > 0) {
							tableModePrimary.getMiSaveAllToExcel().doClick();
							// initTablePrimary(null);
						} else if (index == 1 && tableModeHandle.getRowCount() > 0) {
							tableModeHandle.getMiSaveAllToExcel().doClick();
							// initTableHandle(null);
						}

					}
				}
			});
		}
		return btnExport;
	}

	/**
	 * 清除
	 * 
	 * @return
	 */
	public JButton getBtnClean() {
		if (btnClean == null) {
			btnClean = new JButton("清空当前数据");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkData()) {
						if (JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(FmVFStockOutSendExg.this,
								"将删除本批次下的 历史数据！", "提示", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,
								null, new Object[] { "是", "否" }, "否")) {
							int index = tabbedPane.getSelectedIndex();
							if (index == 0 && tableModePrimary.getRowCount() > 0) {
								vfAction.deleteVFStockOutSendExgsConvert(request, vfSection);
								vfAction.deleteVFStockOutSendExgs(request, vfSection);
								initTablePrimary(null);
							} else if (index == 1 && tableModeHandle.getRowCount() > 0) {
								vfAction.deleteVFStockOutSendExgsConvert(request, vfSection);
								vfAction.deleteVFStockOutSendExgs(request, vfSection);
								initTableHandle(null);
							}
						}
					}
				}
			});
		}
		return btnClean;
	}

	/**
	 * 数据验证
	 */
	public boolean checkData() {
		vfSection = (VFSection) comboBox.getSelectedItem();
		if (vfSection == null) {
			JOptionPane.showMessageDialog(this, "批次号不能为空\r\n请选择批次号!", "提示!", 1);
			return false;
		}
		return true;
	}

	/**
	 * Table选项卡改变时
	 * 
	 * @return
	 */
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("导入原态", null, getScrollPanePrimary(), null);
			tabbedPane.addTab("折算数据", null, getScrollPaneHandle(), null);
			tabbedPane.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					int index = tabbedPane.getSelectedIndex();
					if (index == 1) {
						btnSwitchImg.setEnabled(true);
						btnSwitch.setEnabled(true);
						btnImport.setEnabled(false);
						
						lblNewLabel.setVisible(true);
						tfseqNum.setVisible(true);
						btnEdit.setVisible(false);
						btnDelete.setVisible(false);
					} else if (index == 0) {
						btnSwitchImg.setEnabled(true);
						btnSwitch.setEnabled(true);
						btnImport.setEnabled(true);
						
						lblNewLabel.setVisible(false);
						tfseqNum.setVisible(false);
						btnEdit.setVisible(true);
						btnDelete.setVisible(true);
					}
				}
			});

		}
		return tabbedPane;
	}

	private JScrollPane getScrollPanePrimary() {
		if (scrollPanePrimary == null) {
			scrollPanePrimary = new JScrollPane();
			scrollPanePrimary.setViewportView(getTablePrimary());
		}
		return scrollPanePrimary;
	}

	private JScrollPane getScrollPaneHandle() {
		if (scrollPaneHandle == null) {
			scrollPaneHandle = new JScrollPane();
			scrollPaneHandle.setViewportView(getTableHandle());
		}
		return scrollPaneHandle;
	}

	private JTable getTablePrimary() {
		if (tablePrimary == null) {
			tablePrimary = new JTable();
		}
		return tablePrimary;
	}

	private JTable getTableHandle() {
		if (tableHandle == null) {
			tableHandle = new MultiSpanCellTable();
		}
		return tableHandle;
	}

	private void setButtonState(boolean isEnabled) {
		comboBox.setEnabled(isEnabled);
		btnHistroy.setEnabled(isEnabled);
		btnImport.setEnabled(isEnabled);
		btnSwitchImg.setEnabled(isEnabled);
		btnSwitch.setEnabled(isEnabled);
		btnExport.setEnabled(isEnabled);
		btnClean.setEnabled(isEnabled);
	}

	private JTextField getTfSection() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setPreferredSize(new Dimension(6, 27));
			tfseqNum.setColumns(10);
			tfseqNum.setVisible(false);
			tfseqNum.setDocument(new PlainDocument (){
				  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {							             
					  char c = str.charAt(str.length()-1);
					  if(c >='0' && c <='9'){
						  super.insertString(offs, str, a);
					  }
			      }

			});
		}
		return tfseqNum;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("归并序号:");
			lblNewLabel.setVisible(false);
		}
		return lblNewLabel;
	}

	/**
	 * 穿透查询
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		comboBox.setSelectedItem(section);
		int count = comboBox.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = comboBox.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					comboBox.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfseqNum.setText(String.valueOf(mergerNo));
		tabbedPane.setSelectedIndex(1);
		btnHistroy.doClick();
	}	
	
	
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgVFBaseStockExgOrImgEdit edit = new DgVFBaseStockExgOrImgEdit();
					VFBaseStockExg baseStockExg = (VFBaseStockExg)tableModeHandle.getCurrentRow();
					if(baseStockExg==null){
						return;
					}
					edit.setBaseStockExg(baseStockExg);
					edit.setMaterielType(MaterielType.FINISHED_PRODUCT);
					edit.setVisible(true);
					if(edit.isOk){
						tableModeHandle.updateRow(baseStockExg);
					}
				}
			});
		}
		return btnEdit;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("删除");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new DeleteDataRunnable().execute();
				}
			});
		}
		return btnDelete;
	}
	
	/**
	 * 删除
	 * @author Administrator
	 */
	class DeleteDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				List<VFBaseStockExg> baseStockExgs = (List<VFBaseStockExg>)tableModePrimary.getCurrentRows();
				if(baseStockExgs==null){
					return null;
				}
				if (JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFStockOutSendExg.this, "确定要删除所选择的数据吗?",
						"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是",
								"否" }, "否")) {
					return null;
				} else {
					CommonProgress.showProgressDialog(FmVFStockOutSendExg.this);
					CommonProgress.setMessage("系统正在删除数据，请稍后...");
					vfVerificationAction.deleteVFBaseStockExgs(request, baseStockExgs);
					tableModePrimary.deleteRows(baseStockExgs);
					CommonProgress.closeProgressDialog();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFStockOutSendExg.this,
						"删除失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			CommonProgress.closeProgressDialog();
		}
	}
}
