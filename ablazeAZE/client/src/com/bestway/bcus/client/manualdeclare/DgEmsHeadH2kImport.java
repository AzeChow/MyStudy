package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JButton;

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiHeadH2kBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.client.util.DgSaveTableListToExcel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.DgDataTools;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 明门单耗导入
 * @author Administrator
 *
 */
public class DgEmsHeadH2kImport extends JDialogBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private List list = null;
	private JButton jButton2 = null;
	private File txtFile = null;
	private JRadioButton jRadioButton = null;
	private Hashtable        gbHash                = null;
	private EmsHeadH2k emsHeadH2k = null;
	private ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
	.getApplicationContext().getBean("manualdeclearAction");
	private JLabel jLabel = null;
	private List afterList = null;
	private List tlist = new Vector();
	private EmsHeadH2kVersion versionObj = null;
	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kImport() {
		super();
		initialize();
		list = new Vector();
		initTable(list);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("1.成品序号", "seqNum", 100));
						list.add(addColumn("2.版本号", "version", 60));
						list.add(addColumn("3.料件序号", "bom.seqNum", 100));
						list.add(addColumn("4.单耗", "bom.unitWear", 60));
						list.add(addColumn("5.损耗率(%)", "bom.wear", 70));
						list.add(addColumn("错误信息", "errInfo", 240));
						return list;
					}
				});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(745, 520);
		this.setContentPane(getJContentPane());
		this.setTitle("电子帐册单耗导入");
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(35);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(474,8,244,18));
			jLabel.setForeground(java.awt.Color.red);
			jLabel.setText("注意：未备案与有错误信息数据不可以导入");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(6,3,93,27));
			jButton.setText("1.打开文本");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					txtFile = getFile();
					if (txtFile == null){
						return;
					}
					tlist.clear();
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton;
	}

	 // 调出文件选择框
    private File getFile() {
        File txtFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());

        fileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                String suffix = getSuffix(f);
                if (f.isDirectory() == true) {
                    return true;
                }
                if (suffix != null) {
                    if (suffix.toLowerCase().equals("txt")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            public String getDescription() {
                return "*.txt";
            }

            private String getSuffix(File f) {
                String s = f.getPath(), suffix = null;
                int i = s.lastIndexOf('.');
                if (i > 0 && i < s.length() - 1)
                    suffix = s.substring(i + 1).toLowerCase();
                return suffix;
            }
        });
        int state = fileChooser.showOpenDialog(DgEmsHeadH2kImport.this);
        if (state == JFileChooser.APPROVE_OPTION) {
            txtFile = fileChooser.getSelectedFile();
        }
        return txtFile;
    }
//    
//    private void infTojHsTable() {
//        if (gbHash == null) {
//            gbHash = new Hashtable();
//		    List list = CustomBaseList.getInstance().getGbtobigs();
//		    for (int i = 0; i < list.size(); i++) {
//		        Gbtobig gb = (Gbtobig) list.get(i);
//		        gbHash.put(gb.getBigname(),gb.getName());
//		    }
//        }        
//    }
//    
//    
//   private String changeStr(String s) { // 繁转简
//        String yy = "";
//        char[] xxx = s.toCharArray();
//        for (int i = 0; i < xxx.length; i++) {
//            String z = String.valueOf(xxx[i]);
//            if (String.valueOf(xxx[i]).getBytes().length == 2) {
//                if (gbHash.get(String.valueOf(xxx[i])) != null) {
//                    z = (String) gbHash.get(String.valueOf(xxx[i]));
//                }
//            }
//            yy = yy + z;
//        }
//        return yy;
//    }       
//   
   public String getFileColumnValue(String[] fileRow, int dataIndex) {
	    if (dataIndex>fileRow.length-1){
	    	return null;
	    }
		return fileRow[dataIndex];
   }
            
	class ImportFileDataRunnable extends Thread {
		public void run() {
			
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2kImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				List list = parseTxtFile();
				afterList = manualdeclearAction.bomEmsImport(new Request(CommonVars.getCurrUser()),emsHeadH2k,list);//明门需求
				CommonProgress.closeProgressDialog();
			} catch (Exception e){
				
			} finally {
				initTable(afterList);
				String tishi = "以下为损耗超过50%\n\n";
				for (int i=0;i<tlist.size();i++){
					tishi = tishi + ((String) tlist.get(i)) +"\n";
				}
				if (!tishi.equals("以下为损耗超过50%\n\n")){
					JOptionPane.showMessageDialog(DgEmsHeadH2kImport.this,tishi,"提示",2);
				}
				
			}
		}
	}
		
	private List parseTxtFile(){    	
		int zcount = 5;//字段数目
    	boolean ischange = true;
    	if (getJRadioButton().isSelected()){
//    		infTojHsTable();
    	} else {
            ischange = false;
        }
    	BufferedReader in;
        ArrayList list = new ArrayList();
        String[] strs = null;
        try {
        	in = new BufferedReader(new FileReader(txtFile));
            String s = new String();
            try {
                while ((s = in.readLine()) != null) {
                	if (s.trim().equals("")) {
                        continue;
                    }
                    if (ischange) {
//                        s = changeStr(s);
                    	s=CommonClientBig5GBConverter.getInstance().big5ConvertToGB(s);
                    }
                    strs = s.split(String.valueOf((char) 9));
                   
                    EmsEdiHeadH2kBomFrom obj = new EmsEdiHeadH2kBomFrom();
                	EmsHeadH2kBom bom = new EmsHeadH2kBom();
                	String errinfo = "";
                    for (int j=0;j<zcount;j++){		                    	
                        String value = getFileColumnValue(strs, j);
                        if (j==0){
                        	try{
                                obj.setSeqNum(Integer.valueOf(value.trim()));
                        	} catch (Exception e){
                        		obj.setSeqNum(Integer.valueOf(0));
                        		errinfo = "成品序号格式不正确";
                        	}
                        } else if (j==1){
                        	try{
                    	        obj.setVersion(Integer.valueOf(value.trim())); 
                        	} catch (Exception e){
                        		obj.setVersion(Integer.valueOf(0));
                        		errinfo = errinfo +"/版本格式不正确";
                        	}
                        } else if (j==2){
                        	try{
                    	        bom.setSeqNum(Integer.valueOf(value.trim()));
                        	} catch (Exception e){
                        		errinfo = errinfo +"/料件序号格式不正确";
                        	}
                        } else if (j==3){
                        	if (value == null || "".equals(value)){
                        		continue;
                        	}
                        	try{
                    	        bom.setUnitWear(Double.valueOf(value));
                        	} catch (Exception e){
                        		errinfo = errinfo+"/单耗格式不正确";
                        	}
                        } else if (j==4){
                        	if (value == null || "".equals(value)){
                        		continue;
                        	}
                        	try{
                                bom.setWear(Double.valueOf(value));
                        	} catch (Exception e){
                        		errinfo = errinfo+"/损耗率格式不正确";
                        	}
                        	if (Double.parseDouble(value)>=50){
                        		tlist.add("成品序号:"+obj.getSeqNum()+"  版本号:"+obj.getVersion()+" 料件序号:"+bom.getSeqNum());
                        	}
                        }                        
                    }
                    String cpSeqNum = obj.getSeqNum().toString(); //文本成品序号
                    String cpVersion = obj.getVersion().toString();//文本成品版本
                    String cps = versionObj.getEmsHeadH2kExg().getSeqNum().toString();//系统成品序号
                    String cpv = versionObj.getVersion().toString();//系统成品版本
                    if (!cpSeqNum.equals(cps) || !cpVersion.equals(cpv)){
                    	errinfo = errinfo+"/与当前成品序号,版本号不一致";
                    }
                    obj.setErrinfo(errinfo);
                    obj.setBom(bom);
                    list.add(obj);
                }
            }  catch (IOException e1) {
                    e1.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
	
			
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(105,3,97,27));
			jButton1.setText("2.保存文本");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (afterList == null){
						return;
					}
					if (afterList.size()>0){
						new SaveFileDataRunnable(afterList).start();
					}
				}
			});
		}
		return jButton1;
	}

	
	class SaveFileDataRunnable extends Thread {
		List afterList = null;
		private SaveFileDataRunnable(List afterList){
			this.afterList = afterList;
		}
		public void run() {			
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2kImport.this);
				CommonProgress.setMessage("系统正在保存数据资料，请稍后...");
				int[] x = manualdeclearAction.saveToEmsHeadH2k(new Request(CommonVars.getCurrUser()),versionObj,afterList);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kImport.this,"导入结束!\n\n"
						+"总记录数："+x[4]+"\n\n"
						+"存在但无任何变化："+x[5]+"\n"
						+"由于未备案或有错误信息未导入数："+x[0]+"\n"
				        +"单耗存在修改记录数："+x[1]+"\n"
				        +"新增记录数："+x[2]+"","提示",2);
				initTable(new Vector());
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统保存资料失败！");
			} finally {
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
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.isControlDown() == true && e.getKeyCode() == KeyEvent.VK_L) {
						
						List list = new ArrayList();
						for (int i=0;i<afterList.size();i++){
							EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) afterList.get(i);
							if (obj.getErrinfo()!=null && !obj.getErrinfo().equals("")){
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

	
	private void saveExcel(JTableListModel tableModel){
	    
	    String excelFileName = "";
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.setFileFilter(new ExampleFileFilter("xls"));
		if (excelFileName != null && !"".equals(excelFileName)) {
			File initFile = new File("./" + excelFileName + ".xls");
			fileChooser.setSelectedFile(initFile);
		}
		String fileName = "";
		int state = fileChooser.showSaveDialog(DgEmsHeadH2kImport.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter()
					.getDescription();
			String suffix = description.substring(description.indexOf("."));
			if (f.getPath().indexOf(".") > 0) {
				fileName = f.getPath();
			} else {
				fileName = f.getPath() + suffix;
			}
		} else {
			return;
		}
		
		DgSaveTableListToExcel dgSave = new DgSaveTableListToExcel(DgEmsHeadH2kImport.this);
		dgSave.setTableModel(tableModel);
		dgSave.setFileName(fileName);
		dgSave.setTitle("保存(" + ((JDialog) DgEmsHeadH2kImport.this).getTitle()
				+ ")到Excel");
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
			jButton2.setBounds(new java.awt.Rectangle(206,3,97,27));
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					DgEmsHeadH2kImport.this.dispose();
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
			jRadioButton.setBounds(new java.awt.Rectangle(309,8,165,21));
			jRadioButton.setText("【开始导入】是否繁转简");
		}
		return jRadioButton;
	}

	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public void setVersionObj(EmsHeadH2kVersion versionObj) {
		this.versionObj = versionObj;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
