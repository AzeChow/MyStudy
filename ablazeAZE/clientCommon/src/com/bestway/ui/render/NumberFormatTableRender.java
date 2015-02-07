/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.ui.render;

import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author kangbo
 */
public class NumberFormatTableRender extends DefaultTableCellRenderer {

    int pointLen = 2;

    public NumberFormatTableRender(int pointLen) {
        this.pointLen = pointLen;
        this.setHorizontalTextPosition(JLabel.RIGHT);
        this.setHorizontalAlignment(JLabel.RIGHT);
    }

    @Override
    protected void setValue(Object value) {
        String val = value == null ? "0.00" : value.toString();
        Double dou = null;
        try {
            dou = Double.valueOf(val);
        } catch (Exception ex) {
            System.out.println("NumberFormatTableRender 有错误，值不是数字！");
//            throw new RuntimeException("NumberFormatTableRender 有错误，值不是数字！");
        }
        setText(formatNumber(dou));
    }

    private String formatNumber(Double dou) {
        if (dou == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(pointLen);
        nf.setMinimumFractionDigits(pointLen);
        nf.setMaximumIntegerDigits(99);
        return nf.format(dou.doubleValue());
    }
}
