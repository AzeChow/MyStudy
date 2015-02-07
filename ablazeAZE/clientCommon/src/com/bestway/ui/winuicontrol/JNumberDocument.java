package com.bestway.ui.winuicontrol;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JNumberDocument extends PlainDocument {

    /**
     * 指定小数点后面的长度
     *
     * @param docLenght
     */
    private Integer docLenght = null;

    private Double minValue = null;

    private Double maxValue = null;

    /**
     * 允许负数
     */
    private boolean allowNegative = false;

    public Integer getDocLenght() {
        return docLenght;
    }

    public void setDocLenght(Integer docLenght) {
        this.docLenght = docLenght;
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }

    public void setAllowNegative(boolean allowNegative) {
        this.allowNegative = allowNegative;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public JNumberDocument() {
        super();
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        StringBuilder sb = new StringBuilder(getText(0, getLength()));        
        sb.insert(offs, str);
        if(isMatches(sb.toString())){
            boolean canInsert = true;
            if (minValue != null && maxValue != null) {
                canInsert = (Double.valueOf(sb.toString()) - minValue >= 0.0) && (Double.valueOf(sb.toString()) - maxValue <= 0.0);
            } else if (minValue != null) {
                canInsert = (Double.valueOf(sb.toString()) - minValue >= 0.0);
            } else if (maxValue != null) {
                canInsert = (Double.valueOf(sb.toString()) - maxValue <= 0.0);
            }
            if(canInsert)
                super.insertString(offs, str, a);
        }
    } 
    /**
     * 判断是否满足条件
     * @param content
     * @return 
     */
    private boolean isMatches(String content){
        String regex = "\\d{1,}";
        if(docLenght == null){
            regex += "|(\\d{1,}(\\.\\d{0,}))";
        }else if(docLenght > 0){
            regex += "|(\\d{1,}\\.\\d{0,"+docLenght+"})";
        }
        if(Boolean.TRUE.equals(allowNegative))
            regex = "-?("+regex+")";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }    
}
