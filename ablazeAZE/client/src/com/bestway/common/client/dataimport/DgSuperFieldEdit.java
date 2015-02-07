package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.entity.SuperClassList;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;

import org.apache.commons.beanutils.PropertyUtils;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class DgSuperFieldEdit extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private Integer seqNum;
	private boolean isOk=false;
	private JLabel jLabel1 = null;
	private JCheckBox jCheckBox = null;
	private boolean isNull = true;
	private String fieldType;//字段类型
	private String fieldName;//字段名称
	private JLabel jLabel2 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JComboBox jComboBox = null;
	private JTextPane jTextPane = null;
	private String typeObj;
	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;
	private String childFieldName;
	private JCheckBox jCheckBox1 = null;
	private boolean isOnlyValue;
	/**
	 * This is the default constructor
	 */
	public DgSuperFieldEdit() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(549, 306);
		this.setContentPane(getJContentPane());
		this.setTitle("字段编辑");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				jCheckBox.setSelected(true);
				jTextField1.setText(fieldType);
				jLabel4.setText(fieldName);				
				
				jLabel5.setVisible(fieldType.equals("对象"));
				jComboBox.setVisible(fieldType.equals("对象"));
				jTextPane.setVisible(fieldType.equals("对象"));
				List objls = null;
				if (fieldType.equals("对象")){
					ToolsAction toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean("toolsAction");
					dataDictMap = toolsAction.getTableColumnMap();
					 for (java.util.Iterator it = dataDictMap.keySet().iterator(); it.hasNext();)  {  
						 TempNodeItem item = (TempNodeItem) it.next();
						 if (item.getClassName().equals(typeObj)){
							 objls = dataDictMap.get(item);
							 break;
						 }
					 }
					 for (int j=0;j<objls.size();j++){
							TempNodeItem item = (TempNodeItem) objls.get(j);
							String fieldName = getFieldName(item.getCnName());
							String name = getFieldString(item.getCnName());
							jComboBox.addItem(name+"------"+fieldName);
					}
					jTextPane.setText("关联表字段：表示"+fieldName+"关联到另一个表，那么导入当前表请选择使用哪个字段去关联？\n是否标识唯一值：当" +
							"选择更新数据时，将通过该字段进行查找来更新数据。");
				}
				
			}
		});
	}
    
	//得到字段名
	private String getFieldName(String value) {
		String[] strs = value.split("\n");
		if (strs.length > 0) {
			return strs[0].toString().substring(4);
		} else {
			return "";
		}
	}
	//得到字段备注
	protected String getFieldString(String value) {
		String[] strs = value.split("\n");
		StringBuffer strBuffer = new StringBuffer();
		int length = strs.length;
		for (int i = 0; i < length; i++) {
			if (i <= 2) {
				continue;
			}
			if (i == strs.length - 1) {
				strBuffer.append(strs[i]);
			} else {
				strBuffer.append(strs[i] + "\n");
			}
		}
		return strBuffer.toString().substring(5);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(207,76,65,25));
			jLabel5.setText("关联表字段");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(77,10,146,22));
			jLabel4.setText("");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(17,11,58,21));
			jLabel3.setText("字段名称:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(19,74,67,25));
			jLabel2.setText("字段类型");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(17,41,117,22));
			jLabel1.setText("列序号（必填项）");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(328,41,186,24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("指该字段在数据文件排列第几列？");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJTextPane(), null);
			jContentPane.add(getJCheckBox1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(139,37,185,29));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(74,221,106,32));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					try{
					    seqNum = Integer.valueOf(jTextField.getText().trim());
					} catch (Exception e1){
						JOptionPane.showMessageDialog(DgSuperFieldEdit.this,"列序号不正确！","提示",2);
						return;
					}
					DgSuperFieldEdit.this.setOk(true);
					if (jCheckBox.isSelected()){
						DgSuperFieldEdit.this.setNull(true);
					} else {
						DgSuperFieldEdit.this.setNull(false);
					}
					if (jCheckBox1.isSelected()){
						DgSuperFieldEdit.this.setOnlyValue(true);
					} else {
						DgSuperFieldEdit.this.setOnlyValue(false);
					}
					
					
					
					if (jComboBox.getSelectedItem() != null && !jComboBox.getSelectedItem().equals("")){
						childFieldName = jComboBox.getSelectedItem().toString();
						int x = childFieldName.indexOf("------");
						DgSuperFieldEdit.this.setChildFieldName(childFieldName.substring(x+6));
						}
					DgSuperFieldEdit.this.dispose();
					
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
			jButton1.setBounds(new java.awt.Rectangle(227,221,102,32));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSuperFieldEdit.this.setOk(false);
					DgSuperFieldEdit.this.dispose();
				}
			});
		}
		return jButton1;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(243,7,127,24));
			jCheckBox.setText("是否可为空");
		}
		return jCheckBox;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}


	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(83,75,117,25));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(272,76,176,25));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new java.awt.Rectangle(19,134,507,76));
		}
		return jTextPane;
	}

	public String getTypeObj() {
		return typeObj;
	}

	public void setTypeObj(String typeObj) {
		this.typeObj = typeObj;
	}

	public String getChildFieldName() {
		return childFieldName;
	}

	public void setChildFieldName(String childFieldName) {
		this.childFieldName = childFieldName;
	}

	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new java.awt.Rectangle(18,108,141,21));
			jCheckBox1.setText("是否标识唯一值");
		}
		return jCheckBox1;
	}

	public boolean isOnlyValue() {
		return isOnlyValue;
	}

	public void setOnlyValue(boolean isOnlyValue) {
		this.isOnlyValue = isOnlyValue;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
