package com.bestway.bcus.client.common;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.event.DocumentListener;

import com.bestway.client.util.JTableListModel;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 *         查询操作页面
 */
public abstract class PnMutiConditionQueryPage extends JPanelBase {

    public JTableListModel tableModel = null;
    public List dataSource = new ArrayList();
    private JButton btnUpPage = null;
    private JButton btnDownPage = null;
    private int index = -100;
    private int length = 100;
    private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:
    private boolean isFirst = true;
    private boolean isFirstHasDataInit = true;
    private DocumentListener documentListener = null;
    private Long count = 0L;
    private Integer pages = null;
    private JButton btnFirstPage = null;
    private JButton btnLastPage = null;
    private JButton lbPageInfo = null;
    private boolean isChange = true;

    /**
     * This method initializes
     *
     */
    public PnMutiConditionQueryPage() {
        super();
        initialize();

    // 加此句将执行两次
		/*
     * dataSource = this.getDataSource(0, length, item == null ? null : item
     * .getProperty(), getValue(), getIsLike());
     */
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isFirst) {
            setVisible(true);
            isFirst = false;
        }
    }


    // /**
    // * 显示
    // */
    // pubprivateid setVisible(boolean b) {
    // if (b) {
    // setInitState();
    // if(dataSource.size() <= 0){
    // initCbbQueryField();
    // }
    // }
    // super.setVisible(b);
    // }
 
    /**
     * 设置初始状态
     *
     */
    public void setInitState() {
        index = -length;
        isChange = true;
        searchPage(true);
        isChange = false;
    }

    /**
     * 设置初始状态
     *
     */
    public void reset() {
        setInitState();
    }

    /**
     * This method initializes this
     *
     */
    private void initialize() {
               FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		lbPageInfo = new JButton();
		lbPageInfo.setText("<html><boby>第<font color='red'>0</font>页,  共<font color='red'>0</font>页</body></html>");
//		gridBagConstraints4.ipadx = 188;
//		lbPageInfo.setPreferredSize(new Dimension(300, 20));
        lbPageInfo.setEnabled(false);
		this.setLayout(flowLayout);
//		gridBagConstraints4.weightx = 1.0;
//		this.setSize(new Dimension(852, 30));
		this.add(getBtnFirstPage(), null);
		this.add(getBtnUpPage(), null);
		this.add(getBtnDownPage(), null);
		this.add(getBtnLastPage(), null);
		this.add(lbPageInfo, null);
//        lbPageInfo.setPreferredSize(new Dimension(300, getBtnFirstPage().getPreferredSize().height));
        lbPageInfo.setHorizontalAlignment(SwingConstants.LEADING);
        this.setOpaque(false);
        this.setMaximumSize(new Dimension(852, 30));
    }

     /**
     * This method initializes btnUp
     *
     * @return javax.swing.JButton
     */
    public JButton getBtnUpPage() {
        if (btnUpPage == null) {
            btnUpPage = new JButton();
            btnUpPage.setText("上页");
            btnUpPage.setToolTipText("查询前 " + length + " 条记录!!");
            // btnUpPage.setMnemonic(java.awt.event.KeyEvent.VK_UP);
            btnUpPage.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
            btnUpPage.setEnabled(false);
            btnUpPage.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    searchPage(false);
                }
            });
        }
        return btnUpPage;
    }

    /**
     * This method initializes btnNext
     *
     * @return javax.swing.JButton
     */
    public JButton getBtnDownPage() {
        if (btnDownPage == null) {
            btnDownPage = new JButton();
            btnDownPage.setText("下页");
            btnDownPage.setToolTipText("查询后 " + length + " 条记录!!");
            btnDownPage.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
            // btnDownPage.setMnemonic(java.awt.event.KeyEvent.VK_DOWN);
            btnDownPage.setEnabled(false);
            btnDownPage.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    searchPage(true);
                }
            });
        }
        return btnDownPage;
    }


    public void setQueryState(boolean b) {
//		btnFirstPage.setVisible(b);
//		btnUpPage.setVisible(b);
//		btnDownPage.setVisible(b);
//		btnLastPage.setVisible(b);
//		lbPageInfo.setVisible(b);
//		btnQuery.setVisible(!b);
        btnFirstPage.setEnabled(b);
        btnUpPage.setEnabled(b);
        btnDownPage.setEnabled(b);
        btnLastPage.setEnabled(b);
        lbPageInfo.setEnabled(b);
        isChange = !b;
//		btnQuery.setEnabled(!b)
    }

    /**
     * 查询上下页
     *
     * @param materielType
     * @param isDownPage
     */
    private void searchPage(Boolean isDownPage) {
        if (isDownPage != null && isDownPage == false) {
            index -= length;
        } else if (isDownPage != null && isDownPage == true) {
            index += length;
        }
       dataSource = this.getDataSource(index, length);

        if (isChange) {
            count = this.getDataSourceCount();
        }

        if (count != 0) {
            pages = (count.intValue() / length) + (count % length == 0 ? 0 : 1);
            int currentPage = (index + length) / length;
            setPageInfo(currentPage, count);
//			this.lbPageInfo.setText("第"+currentPage+"页共"+pages+"页");
            if (pages == currentPage) {
                this.btnLastPage.setEnabled(false);
                this.btnDownPage.setEnabled(false);
            } else {
                this.btnLastPage.setEnabled(true);
                this.btnDownPage.setEnabled(true);
            }

        } else {
            setPageInfoIsNull();
        }
        this.tableModel = initTable(dataSource);
        if (isFirstHasDataInit) {
            isFirstHasDataInit = false;
        }
        if (tableModel != null && count == 0) {
            if (length > this.tableModel.getRowCount()) {
                this.btnLastPage.setEnabled(false);
                this.btnDownPage.setEnabled(false);                
            } else {
                this.btnLastPage.setEnabled(true);
                this.btnDownPage.setEnabled(true);
            }
        }
        if (tableModel != null) {
            if (index == 0) {
                this.btnUpPage.setEnabled(false);
                this.btnFirstPage.setEnabled(false);
            } else {
                this.btnUpPage.setEnabled(true);
                this.btnFirstPage.setEnabled(true);
            }
        }
    }

    /**
     * 刷新当前记录数据
     *
     */
    public void refreshData() {
        searchPage(null);
    }

    /**
     * 初始化表
     *
     * @param dataSource
     */
    public abstract JTableListModel initTable(List dataSource);

    /**
     * 获得数据源
     *
     * @param index
     * @param length
     * @param property
     * @param value
     * @param isLike
     * @return
     */
    public abstract List getDataSource(int index, int length);

    protected Long getDataSourceCount() {
        return 0L;
    }

    protected Boolean getIsLikeByInit() {
        return true;
    } 

    public boolean isFirstHasDataInit() {
        return isFirstHasDataInit;
    }

    public void setFirstHasDataInit(boolean isFirstHasDataInit) {
        this.isFirstHasDataInit = isFirstHasDataInit;
    }

    public DocumentListener getDocumentListener() {
        return documentListener;
    }

    public void setDocumentListener(DocumentListener documentListener) {
        this.documentListener = documentListener;
    }

    /**
     * This method initializes btnFirstPage
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnFirstPage() {
        if (btnFirstPage == null) {
            btnFirstPage = new JButton();
            btnFirstPage.setText("首页");
            btnFirstPage.setToolTipText("查询最前 " + length + " 条记录!!");
            // btnUpPage.setMnemonic(java.awt.event.KeyEvent.VK_UP);
            btnFirstPage.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
            btnFirstPage.setEnabled(false);
            btnFirstPage.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    index = -length;
                    searchPage(true);
                }
            });
        }
        return btnFirstPage;
    }

    /**
     * This method initializes btnLastPage
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnLastPage() {
        if (btnLastPage == null) {
            btnLastPage = new JButton();
            btnLastPage.setText("末页");
            btnLastPage.setToolTipText("查询最后 " + length + " 条记录!!");
            btnLastPage.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
            btnLastPage.setEnabled(false);
            btnLastPage.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (pages != null) {
                        index = (length * (pages - 2));
                        searchPage(true);
                    }
                }
            });
        }
        return btnLastPage;
    }

    private void setPageInfo(int currentPage, long count) {
        lbPageInfo.setText("<html><boby>第<font color='black'>" + currentPage + "</font>页,共<font color='black'>" + pages + "</font>页," +
                "每页<font color='black'>" + length + "</font>" + "条记录," +
                "共<font color='red'>" + count + "</font>" + "条记录</body></html>");
    }

    private void setPageInfoIsNull() {
        lbPageInfo.setText("<html><boby>记录为<font color='black'>0" + "</font>条</body></html>");
    }
}  