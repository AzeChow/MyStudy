package com.bestway.common.client.fpt;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 转厂管理 公共 Frame
 *  
 */
public class FmCommon extends JInternalFrameBase {
    public FptManageAction fptManageAction = null;
    public CommonBaseCodeAction        commonBaseCodeAction        = null;
    public MaterialManageAction materialManageAction = null;
    public FmCommon() {
        super();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        try {
            this.fptManageAction = (FptManageAction) CommonVars
                    .getApplicationContext().getBean(
                            "fptManageAction");
            this.commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                    .getApplicationContext().getBean("commonBaseCodeAction");
            materialManageAction = (MaterialManageAction) CommonVars
    		.getApplicationContext().getBean("materialManageAction");
        } catch (Exception ex) {

        }

    }

    @Override
	public void setVisible(boolean isFlag) {
        super.setVisible(isFlag);
    }

    /**
     * 获得是客户的对象列表
     */
    public List getCustomer() {
        List customerList = this.materialManageAction.findScmCocIsCustomer(new Request(
                CommonVars.getCurrUser()));
        return customerList;
    }
    
  

    /**
     * 获得是供应商的对象列表
     */
    public List getManufacturer() {
    	  List manufacturerList = this.materialManageAction.findScmCocIsManufacturer(new Request(
                  CommonVars.getCurrUser()));
        return manufacturerList;
    }

    /**
     * 生成客户列表树
     */
    public JTree getManufacturerJTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("供应商");
        List list = this.getManufacturer();
        for (int i = 0; i < list.size(); i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(list
                    .get(i));
            root.add(node);
        }
        JTree jTree = new JTree(root);
        jTree.setCellRenderer(new MyTreeCellRenderer());
        return jTree;
    }

    /**
     * 生成供应商列表树
     */
    public JTree getCustomerJTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("客户");
        List list = this.getCustomer();
        for (int i = 0; i < list.size(); i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(list
                    .get(i));
            root.add(node);
        }
        JTree jTree = new JTree(root);
        jTree.setCellRenderer(new MyTreeCellRenderer());
        return jTree;
    }

    /**
     * 树Cell呈现
     */
    class MyTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel, expanded,
                    leaf, row, hasFocus);
            Object object = ((DefaultMutableTreeNode) value).getUserObject();
            if (object instanceof ScmCoc) {
                ScmCoc scmCoc = (ScmCoc) object;
                tree.setToolTipText("编号: " + scmCoc.getCode());
                setText(scmCoc.getCode()+"-"+scmCoc.getName());
            } else {
                setText(value.toString());
                setToolTipText(null);
            }           
            return this;
        }
    }

}