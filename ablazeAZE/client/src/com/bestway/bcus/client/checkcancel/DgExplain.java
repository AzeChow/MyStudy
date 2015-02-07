/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.ColorSet;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Parame;
import com.bestway.common.Request;
import com.bestway.common.constant.ApplyToCustomType;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DefaultFormatter;
import java.text.DecimalFormat;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExplain extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JTableListModel tableModel = null;

	private CommonBaseCodeAction commonBaseCodeObj = null;

	private boolean isAdd = true;

	private ParaSet obj = null; //

	private CheckCancelAction checkCancelAction = null;

	private JTextPane jTextPane = null;

	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public DgExplain() {
		super();
		initialize();
		checkCancelAction = (CheckCancelAction) CommonVars
		     .getApplicationContext().getBean("checkCancelAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(697, 506);
		this.setTitle("计算说明");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				
				
			}
		});
	}

	public void setVisible(boolean b){
		if(b){
			initUICompoments();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					obj = (ParaSet) tableModel.getCurrentRow();
				}
				fillWindow();
			}
		}
		super.setVisible(b);
	}
	private void initUICompoments() {
		
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(2,4,80,52));
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/gj.gif")));
			jLabel.setText("JLabel");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJTextPane(), null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定(S)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setBounds(575, 419, 75, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
                    DgExplain.this.dispose();
				}
			});

		}
		return jButton;
	}

	private void fillWindow() {
		
	}



	private void fillCurrCode(ParaSet obj) {
	  
	}

	private void clearData() {
		
		
	}

	class CustomDocument extends PlainDocument {
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 10 || str.length() > 10
					|| super.getLength() + str.length() > 10) {
				return;
			}
			super.insertString(offs, str, a);
		}
	}

	private void addData() {
		
	}

	private void modifyData() {
		
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the commonBaseCodeObj.
	 */
	public CommonBaseCodeAction getCommonBaseCodeObj() {
		return commonBaseCodeObj;
	}

	/**
	 * @param commonBaseCodeObj
	 *            The commonBaseCodeObj to set.
	 */
	public void setCommonBaseCodeObj(CommonBaseCodeAction commonBaseCodeObj) {
		this.commonBaseCodeObj = commonBaseCodeObj;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setEditable(false);
			jTextPane.setBounds(new java.awt.Rectangle(52,37,597,353));
			jTextPane.setText("-- 库存原材料\n" +
					          "   根据海关文件得到库存原材料公式:\n" +
					          "   库存原材料:统计本年度料件进仓单据 - 料件出仓单据（按折算报关数量计算）\n" +
					          "   料件进仓单据:直接进口+结转进口+国内购买+海关征税进口+车间返回+料件盘盈单+料件期初单+外发加工退回\n"+
					          "   料件+受托加工进库+其他来源+料件转仓入库\n" +
					          "   料件出仓单据:车间领用+受托加工领用+料件退换+料件复出+结转料件退货单+料件盘亏单+外发加工出库+受托\n" +
					          "   加工退回料件+其他使用+其他料件退货单+料件转仓出库\n\n" +
					          "-- 在途数量\n" +
					          "   在途数量：");
		}
		return jTextPane;
	}


	
}  //  @jve:decl-index=0:visual-constraint="82,23"
