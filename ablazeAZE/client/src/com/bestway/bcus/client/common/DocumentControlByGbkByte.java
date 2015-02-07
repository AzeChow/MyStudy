package com.bestway.bcus.client.common;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * 按照字节数控制字符串(含有中文)的输入,如果字符串的长度不超出字节数可输入,
 * 按照GBK的字符占用方式换算:每个汉字占2个字节,每个ASCII占1个字节.
 * 
 * @param length 要控制的字节数
 * @return PlainDocument
 */
public class DocumentControlByGbkByte extends PlainDocument {
	int bytesLength = 30;// 要控制的长度

	int strLength = 0;

	public DocumentControlByGbkByte(int length) {
		this.bytesLength = length;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if (str == null) {
			return;
		}
		strLength = countLengthByGBK(getText(0, getLength()));
		if (strLength >= bytesLength) {
			return;
		}
		checkInsertStrByGBK(offs, str, a, strLength);
	}

	/**
	 * 计算字符串的字节数(含有中文), 按照GBK的字符占用方式换算:每个汉字占2个字节,每个ASCII占1个字节.
	 * 
	 * @param str
	 *            要计算的字符串
	 * @return 计算后的字节数
	 */
	private int countLengthByGBK(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		char[] chars = str.toCharArray();
		int strLength = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] > 255) {
				strLength += 2;// 每个汉字当GBK的占2个字节算
				if (strLength == (bytesLength + 1))
					break;
			} else if (chars[i] < 255) {
				++strLength;// 每个ASCII占1个字节算
			}
			if (strLength >= bytesLength)
				break;
		}
		return strLength;
	}

	/**
	 * 插入字符串(含有中文)到已有的字符中,超出指定长度的自动截断,
	 * 按照GBK的字符占用方式换算:每个汉字占2个字节,每个ASCII占1个字节.
	 * 
	 * @param offset
	 * @param str
	 *            要插入的字符串
	 * @param att
	 * @param strLength
	 *            已有的长度
	 * @throws BadLocationException
	 */
	private void checkInsertStrByGBK(int offset, String str,
			AttributeSet att, int strLength) throws BadLocationException {
		String s ="";
		char[] chars = str.toCharArray();
		int k = strLength;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] > 255) {
				k += 2;// 每个汉字当GBK的占2个字节算
				if (k == (bytesLength + 1))
					break;
				s += chars[i];
			} else if (chars[i] < 255) {
				++k;// 每个ASCII占1个字节算
				s += chars[i];
			}
			if (k >= bytesLength)
				break;
		}
		super.insertString(offset, s, att);
	}
}