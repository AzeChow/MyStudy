package com.bestway.ui.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 * 这里增加两种注释 第一种SQL行注释 -- 第二种SQL全 /*
 * 
 * @author root ls
 * 
 */
public class BaseEditorDocument extends DefaultStyledDocument {

	protected StringBuffer		stringBuffer			= new StringBuffer();
	private List<Annotate>		multiAnnotateRegion		= new ArrayList<Annotate>();
	private List<Annotate>		singleAnnotateRegion	= new ArrayList<Annotate>();
	private List<Annotate>		AllAnnotate				= new ArrayList<Annotate>();
	private List<Annotate>		multiStringRegion		= new ArrayList<Annotate>();
	private List<Annotate>		keyStringRegion			= new ArrayList<Annotate>();
	private Style				blackStyle				= null;
	private Style				keyStyle				= null;
	private Style				annotateStyle			= null;
	private Style				stringStyle				= null;
	private StyleControl		styleControl			= new StyleControl();

	//
	// key 为小写的字串
	//
	private Map<String, String>	keyMap					= new HashMap<String, String>();

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		super.insertString(offs, str, a);
		stringBuffer.insert(offs, str);
		SwingUtilities.invokeLater(styleControl);
	}

	public void remove(int offset, int length) throws BadLocationException {
		super.remove(offset, length);
		stringBuffer.delete(offset, offset + length);
		SwingUtilities.invokeLater(styleControl);		
	}

	/**
	 * editor 状态控制 分十步确保注释 OK
	 * 
	 */
	class StyleControl implements Runnable {
		public void run() {			
			multiAnnotateRegion.clear();
			singleAnnotateRegion.clear();
			AllAnnotate.clear();
			multiStringRegion.clear();
			keyStringRegion.clear();
			setCharacterAttributes(0, getLength(), getBackStyle(), false);

			StringBuffer sbText = new StringBuffer(stringBuffer);

			//
			// 设置单行SQL or HSQL 注释
			//
			setSingleAnnotateRegion1(sbText);
			//
			// repate 单行注释为空
			//
			repateAnnotateToEmpty(sbText, singleAnnotateRegion);
			// long beginDate = System.currentTimeMillis();
			// 多行
			while (sbText.indexOf("/*") > -1) {
				//
				// 设置多行注释 Style
				//

				setMultiAnnotateRegion(sbText);
				//
				// repate 多行注释为空
				//
				repateAnnotateToEmpty(sbText, multiAnnotateRegion);

			}
			// System.out.println(System.currentTimeMillis()-beginDate);
			//
			// 再一次设置单行SQL or HSQL 注释
			//
			setSingleAnnotateRegion2(sbText);
			//
			// 再一次 repate 单行注释为空
			//
			repateAnnotateToEmpty(sbText, singleAnnotateRegion);
			//
			// 整合所有的注释区域
			//
			setAllAnnotate();
			//
			// 设置多行字符串区域
			//				
			setMultiStringRegion(sbText);
			//
			// repate 多行注释为空
			//
			repateAnnotateToEmpty(sbText, multiStringRegion);
			//
			// 设置多行字符串区域 style
			//
			setAllStringStyle();
			//
			// 设置所有区域的 Anntate style
			//		
			setAllAnnotateStyle();
			//
			// repate SPECIAL_SYMBOL to " "
			// 最后格式化无注释的字串
			//
			StringBuffer noSymbolSbText = repateSpecialSymbolToEmpty(sbText);
			//
			// 设置Key区域的区域
			//		
			setKeyStringRegion(noSymbolSbText);
			//
			// 设置关键字符串区域 style
			//		
			setKeyStringStyle();
		}
	}

	//
	// 设置单行SQL or HSQL 注释
	//	
	private void setSingleAnnotateRegion1(StringBuffer stringBuffer) {

		String text = stringBuffer.toString();
		String[] lines = text.split("\n");
		int lineSize = 0;
		for (int i = 0, n = lines.length; i < n; i++) {
			int beginSingleAnnotateIndex = lines[i].indexOf(this
					.getAnnotatePrefix());
			int currentLine = lines[i].length() + 1;
			lineSize += currentLine;
			if (beginSingleAnnotateIndex > -1) {
				int beginMultiAnnotateIndex = lines[i].indexOf("/*");
				int endMultiAnnotateIndex = lines[i].indexOf("*/");
				if ((beginMultiAnnotateIndex == -1 || beginMultiAnnotateIndex > beginSingleAnnotateIndex)
						&& endMultiAnnotateIndex == -1) {
					int offset = lineSize - currentLine
							+ beginSingleAnnotateIndex;
					Annotate multiAnnotate = new Annotate(offset, lineSize);
					singleAnnotateRegion.add(multiAnnotate);
				}
			}
		}
	}

	/**
	 * -- stringBuffer 第三次 这样的注 Annnotate
	 * 
	 * @param stringBuffer
	 */
	private void setSingleAnnotateRegion2(StringBuffer stringBuffer) {
		String text = stringBuffer.toString();
		String[] lines = text.split("\n");
		int lineSize = 0;
		for (int i = 0, n = lines.length; i < n; i++) {
			int beginSingleAnnotateIndex = lines[i].indexOf(this
					.getAnnotatePrefix());
			int currentLine = lines[i].length() + 1;
			lineSize += currentLine;
			if (beginSingleAnnotateIndex > -1) {
				int offset = lineSize - currentLine + beginSingleAnnotateIndex;
				Annotate multiAnnotate = new Annotate(offset, lineSize);
				singleAnnotateRegion.add(multiAnnotate);
			}
		}
	}

	/**
	 * 设置多行SQL or HSQL /* 注释
	 * 
	 * @param stringBuffer
	 */
	private void setMultiAnnotateRegion(StringBuffer stringBuffer) {
		String text = stringBuffer.toString();
		String[] lines = text.split("\n");
		boolean hasBeginAnnotate = false;
		int lineSize = 0;
		int beginAnnotateOffset = 0;

		for (int i = 0, n = lines.length; i < n; i++) {

			if (hasBeginAnnotate == false) {
				int beginAnnotateIndex = lines[i].indexOf("/*");
				if (beginAnnotateIndex > -1) {
					hasBeginAnnotate = true;
					beginAnnotateOffset = lineSize + beginAnnotateIndex;
					//
					// 如果当前行有结束注释
					//
					int endAnnotateIndex = lines[i].indexOf("*/");

					if (endAnnotateIndex > -1
							&& endAnnotateIndex > beginAnnotateIndex) {
						endAnnotateIndex += lineSize + 2;
						hasBeginAnnotate = false;
						Annotate multiAnnotate = new Annotate(
								beginAnnotateOffset, endAnnotateIndex);
						multiAnnotateRegion.add(multiAnnotate);
					}
				}
			} else {
				int endAnnotateIndex = lines[i].indexOf("*/");
				if (endAnnotateIndex > -1) {
					endAnnotateIndex += lineSize + 2; // */
					hasBeginAnnotate = false;
					Annotate multiAnnotate = new Annotate(beginAnnotateOffset,
							endAnnotateIndex);
					multiAnnotateRegion.add(multiAnnotate);
				}
			}
			//
			// 总 offset
			//
			lineSize += lines[i].length() + 1;
		}
		if (hasBeginAnnotate == true) {
			Annotate multiAnnotate = new Annotate(beginAnnotateOffset, lineSize);
			multiAnnotateRegion.add(multiAnnotate);
		}
	}

	/**
	 * 设置多行 字符串 注释
	 * 
	 * @param stringBuffer
	 */
	private void setMultiStringRegion(StringBuffer stringBuffer) {

		char[] chars = new char[stringBuffer.length()];
		stringBuffer.getChars(0, stringBuffer.length(), chars, 0);
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < chars.length; i++) {
			if (String.valueOf(chars[i]).equals("'")) {
				list.add(i);
			}
		}
		if (list.size() % 2 != 0) {
			list.add(chars.length);
		}

		int beginStringOffset = -1;
		int endStringOffset = -1;
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0) {
				beginStringOffset = list.get(i);
			} else {
				endStringOffset = list.get(i);
				Annotate multiAnnotate = new Annotate(beginStringOffset,
						endStringOffset + 1); // ' 本身是一位
				multiStringRegion.add(multiAnnotate);
			}
		}
	}

	/**
	 * 整合所有的数据区域
	 * 
	 */
	private void setAllAnnotate() {
		AllAnnotate.addAll(singleAnnotateRegion);
		AllAnnotate.addAll(multiAnnotateRegion);
	}

	/**
	 * 设置所有区域的 Anntate style
	 * 
	 */
	private void setAllAnnotateStyle() {
		Style style = getAnnotateStyle();
		for (Annotate annotate : AllAnnotate) {
			int beginIndex = annotate.getBeginIndex();
			int endIndex = annotate.getEndIndex();
			int offset = beginIndex;
			int length = endIndex - offset;
			this.setCharacterAttributes(offset, length, style, false);
		}
	}

	/**
	 * 设置所有区域的 Anntate style
	 * 
	 */
	private void setAllStringStyle() {
		Style style = getStringStyle();
		for (Annotate annotate : multiStringRegion) {
			int beginIndex = annotate.getBeginIndex();
			int endIndex = annotate.getEndIndex();
			int offset = beginIndex;
			int length = endIndex - offset;
			this.setCharacterAttributes(offset, length, style, false);
		}
	}

	//
	// int 比o小时返回-1，相等时返回0，大于时返回1
	//
	class Annotate implements Comparable {
		private Integer	beginIndex, endIndex = 0;

		public Annotate(Integer beginIndex, Integer endIndex) {
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
		}

		public Integer getBeginIndex() {
			return beginIndex;
		}

		public Integer getEndIndex() {
			return endIndex;
		}

		public int compareTo(Object o) {
			Annotate a = (Annotate) o;
			if (a == null) {
				return 1;
			}
			//
			// DESC
			//
			if (this.beginIndex > a.getBeginIndex().intValue()) {
				return -1;
			} else if (this.beginIndex < a.getBeginIndex().intValue()) {
				return 1;
			}
			return 0;
		}

	}

	/**
	 * 
	 * repate 注释为空
	 * 
	 * @param stringBuffer
	 */
	private void repateAnnotateToEmpty(StringBuffer stringBuffer,
			List<Annotate> annotateRegion) {
		for (Annotate multiAnnotate : annotateRegion) {
			int beginIndex = multiAnnotate.getBeginIndex();
			int endIndex = multiAnnotate.getEndIndex();
			String emptyStr = "";
			for (int i = 0; i < endIndex - beginIndex; i++) {
				emptyStr += " ";
			}
			stringBuffer.replace(beginIndex, endIndex, emptyStr);
		}
	}

	/**
	 * 
	 * repate SPECIAL_SYMBOL to " "
	 * 
	 * @param stringBuffer
	 */
	private StringBuffer repateSpecialSymbolToEmpty(StringBuffer stringBuffer) {
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>(
				Utils.SPECIAL_SYMBOL.length);
		for (String specialSymbol : Utils.SPECIAL_SYMBOL) {
			map.put(specialSymbol, specialSymbol);
		}

		char[] chars = new char[stringBuffer.length()];
		stringBuffer.getChars(0, stringBuffer.length(), chars, 0);

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (map.containsKey(String.valueOf(c))) {
				sb.append(" ");
			} else {
				sb.append(String.valueOf(c));
			}
		}
		return sb;
	}

	/**
	 * 设置关键字高show
	 * 
	 * @param stringBuffer
	 */
	private void setKeyStringRegion(StringBuffer stringBuffer) {

		String text = stringBuffer.toString();
		String[] lines = text.split("\n");

		int offset = 0;
		int lineSize = 0;
		for (int i = 0, n = lines.length; i < n; i++) {

			String[] words = lines[i].split(" ");

			for (int j = 0, size = words.length; j < size; j++) {
				String word = words[j];
				int wordLength = word.length();
				if (word.trim().equals("")) {
					offset += 1;
					continue;
				}
				offset += wordLength;
				if (keyMap.containsKey(word.toLowerCase())) {
					int beginKeyOffset = offset - wordLength;
					int endKeyIndex = offset;
					Annotate multiAnnotate = new Annotate(beginKeyOffset,
							endKeyIndex);
					keyStringRegion.add(multiAnnotate);
				}
				if (size - 1 != j) {
					offset += 1;
				}
			}

			lineSize += lines[i].length() + 1;
			offset = lineSize;
		}
	}

	/**
	 * 设置所有区域的 Anntate style
	 * 
	 */
	private void setKeyStringStyle() {
		Style style = getKeyStyle();
		for (int i = 0; i < keyStringRegion.size(); i++) {
			Annotate annotate = keyStringRegion.get(i);
			int beginIndex = annotate.getBeginIndex();
			int endIndex = annotate.getEndIndex();
			int offset = beginIndex;
			int length = endIndex - offset;
			this.setCharacterAttributes(offset, length, style, false);
		}
	}

	// ----------------
	// style steup
	// ----------------
	private Style getKeyStyle() {
		if (keyStyle == null) {
			keyStyle = this.addStyle("keyStyle", null);
			StyleConstants.setForeground(keyStyle, Utils.KEY_COLOR);
		}
		return keyStyle;
	}

	private Style getAnnotateStyle() {
		if (annotateStyle == null) {
			annotateStyle = this.addStyle("annotateStyle", null);
			StyleConstants.setForeground(annotateStyle,
					Utils.SQL_ANNOTATE_COLOR);
		}
		return annotateStyle;
	}

	private Style getStringStyle() {
		if (stringStyle == null) {
			stringStyle = this.addStyle("stringStyle", null);
			StyleConstants.setForeground(stringStyle, Utils.STRING_COLOR);
		}
		return stringStyle;
	}

	private Style getBackStyle() {
		if (blackStyle == null) {
			blackStyle = this.addStyle("blackStyle", null);
			StyleConstants.setForeground(blackStyle, Color.black);
		}
		return blackStyle;
	}

	public Map<String, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(String[] strKeys) {
		for (String str : strKeys) {
			this.keyMap.put(str.toLowerCase().trim(), str);
		}
	}

	public void setKeyMap(Map<String, String> keyMap) {
		this.keyMap = keyMap;
	}

	// ----------------------------------
	// 获得文本
	// ----------------------------------
	/**
	 * 获得文本
	 * 
	 * @return
	 */
	public StringBuffer getSbText() {
		StringBuffer sb = new StringBuffer(stringBuffer.toString());
		removeAnnotateToEmpty(sb);

		String text = sb.toString();
		String[] lines = text.split("\n");

		//
		// 清除空行
		//
		StringBuffer removeEmptyLine = new StringBuffer();
		for (int i = 0, n = lines.length; i < n; i++) {
			String line = lines[i];
			if (line.trim().equals("")) {
				continue;
			}
			removeEmptyLine.append(line + "\n");
		}
		return removeEmptyLine;
	}

	/**
	 * 
	 * remove 注释为空
	 * 
	 * @param stringBuffer
	 */
	private void removeAnnotateToEmpty(StringBuffer sb) {
		Collections.sort(AllAnnotate);
		//
		// 重后面开始进行替换
		//
		for (int n = AllAnnotate.size(), i = 0; i < n; i++) {
			Annotate annotate = AllAnnotate.get(i);
			int beginIndex = annotate.getBeginIndex();
			int endIndex = annotate.getEndIndex();
			sb.replace(beginIndex, endIndex, "");
		}

	}

	/**
	 * 获得行注释前缀
	 * 
	 * @return
	 */
	protected String getAnnotatePrefix() {
		return Utils.JAVA_ROW_ANNOTATE_PREFIX;
	}
	
}