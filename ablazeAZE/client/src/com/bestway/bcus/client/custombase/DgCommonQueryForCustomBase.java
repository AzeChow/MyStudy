package com.bestway.bcus.client.custombase;

import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.Item;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import java.awt.Rectangle;

@SuppressWarnings("unchecked")
public class DgCommonQueryForCustomBase extends DgCommonQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox cbbShow = null;  //  @jve:decl-index=0:visual-constraint="580,10"
	
	
	private List backList;

	public DgCommonQueryForCustomBase() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(580, 527));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowOpened(java.awt.event.WindowEvent e) {
        		backList = dataSource;
        	}
        });
		this.getJPanel().add(this.getCbbShow());
		this.tfQueryValue.addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				reQuery();
			}
		});
	}
	
	private void reQuery() {
		if(cbbShow.isSelected()) {
			List tmpList = new ArrayList();
			Complex complex = null;
			for (int i = 0; i < dataSource.size(); i++) {
				complex = (Complex) dataSource.get(i);
				System.out.println(complex.getCcontrol());
				if(complex.getCcontrol() != null && !"".equals(complex.getCcontrol())) {
					tmpList.add(complex);
				}
			}
			tableModel.setList(tmpList);
			dataSource = tmpList;
		} else {
			dataSource = backList;
			tableModel.setList(dataSource);
		}
		
		Integer col = null;
		Object obj = cbbQueryField.getSelectedItem();
		if (obj == null || !(obj instanceof Item)) {
			return;
		}
		Item itm = (Item) obj;
		if (itm == null || itm.getName() == null) {
			return;
		}
		String pro = itm.getName();
		List<JTableListColumn> list = ((JTableListModel) tableModel)
				.getColumns();
		for (int i = 0; i < list.size(); i++) {
			JTableListColumn data = list.get(i);
			if (data.getCaption() == null) {
				continue;
			}
			if (data.getCaption().equals(pro))
				col = i;
		}
		boolean isEqual = true;
		if (jRadioButton.isSelected()) {
			isEqual = true;
		} else {
			isEqual = false;
		}
		String value = tfQueryValue.getText();
		if (value == null || value.equals("")) {
			return;
		}
		if (tableModel instanceof JTableListModel
				&& col != null) {
			((JTableListModel) tableModel).filter(col,
					value, isEqual, 0);
		}
	}

	/**
	 * This method initializes cbbShow	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbbShow() {
		if (cbbShow == null) {
			cbbShow = new JCheckBox();
			cbbShow.setBounds(350, 4, 98, 20);
			cbbShow.setText("显示商检商品");
			cbbShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					reQuery();
				}
			});
		}
		return cbbShow;
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
