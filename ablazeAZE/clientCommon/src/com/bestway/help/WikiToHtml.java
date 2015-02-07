/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author wangpian 语法： 空行表示 <br/> 开头空格 表示代码、段落、可以跨行 = ... = 表示
 *         <h1> ... </h1>
 *         不可以跨行 == ... == 表示
 *         <h2> ... </h2>
 *         不可以跨行 === ... === 表示
 *         <h3> ... </h3>
 *         不可以跨行 ==== ... ==== 表示
 *         <h4> ... </h4>
 *         不可以跨行 ===== ... ===== 表示
 *         <h5> ... </h5>
 *         不可以跨行 ====== ... ====== 表示
 *         <h6> ... </h6>
 *         不可以跨行 [[...]] 表示 <a href="...">...</a> 不可以跨行 [[... ....]] 表示 <a
 *         href="...">....</a> 不可以跨行 [[Image:...]] 表示图片地址 <img src="...">
 *         {{...}} 表示变量，暂时使用空白替换 * ... 开头表示
 *         <ul>
 *         <li>...</li>
 *         第一级列表 不可以跨行 ** ... 开头表示
 *         <ul>
 *         <li>...</li>
 *         第二级列表 不可以跨行 # ... 开头表示
 *         <ol>
 *         <li>...</li>
 *         第一级列表 不可以跨行 ## ... 开头表示
 *         <ol>
 *         <li>...</li>
 *         第一级列表 不可以跨行 : ... 开头表示缩进
 *         <dd></dd>
 *         不可以跨行 :: ... 开头表示缩进
 *         <dl>
 *         <dd></dd>
 *         </dl>
 *         不可以跨行 ::: ... 开头表示缩进
 *         <dl>
 *         <dd></dd>
 *         </dl>
 *         不可以跨行 ; .. : ... 开头表示缩进
 *         <dl>
 *         <dt>..</dt>
 *         <dd>...</dd>
 *         </dl>
 *         不可以跨行 ---- 表示
 *         <hr>
 *         ''...'' 表示斜体 <i>...</i> '''...''' 表示粗体 <b>...</b> ''''...'''' 表示粗斜体
 *         <i><b>...</b></i> '''''...''''' 表示粗斜体 <i><b>...</b></i>
 *         <strike>删除线</strike> 不变 <u>下划线</u> 不变 <center>使文字居中显示</center> 不变
 *         ~~~~~ 表示当前日期 {| 参数 表示 <table 参数> |} 参数 表示 </table> |单元1 表示
 *         <td>单元1</td>
 *         |参数|单元1 表示
 *         <td 参数>单元1</td>
 *         |单元1||单元2 表示
 *         <td>单元1</td>
 *         <td>单元2</td>
 *         !单元1 表示
 *         <th>单元1</th>
 *         !参数|单元1 表示
 *         <th 参数>单元1</th>
 *         !单元1!!单元2 表示
 *         <th>单元1</th>
 *         <th>单元2</th> |- 表示
 *         <tr> |+ 单元1 表示 <caption>单元1</caption> |+ 参数|单元1 表示 <caption 参数>单元1</caption>
 * 
 */
public class WikiToHtml {

	private List<String> pre;

	private List<String> nowiki;

	private String baseUrl = "";

	private String htmlHeadS = "<html><head>";

	// "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
	// +
	private String htmlHeadE = "</head><body>";

	private String htmlEnd = "</body></html>";

	public WikiToHtml() {
		pre = new ArrayList<String>();
		nowiki = new ArrayList<String>();
	}

	private String formatUL(StringBuffer text) {
		String[] lines = text.toString().split("\r\n");

		// 求出最深度
		int maxLevel = 0;
		Pattern p = Pattern.compile("^([#*]+)");
		for (String line : lines) {
			Matcher m = p.matcher(line);
			m.find();
			if (maxLevel < m.group(1).length()) {
				maxLevel = m.group(1).length();
			}
		}

		String key = null;
		for (int i = maxLevel; i > 0; i--) {
			boolean start = false;
			for (int j = 0; j < lines.length; j++) {
				Matcher m = p.matcher(lines[j]);
				if (m.find()) {
					if (i == m.group(1).length()) {
						if (!start) {
							if (lines[j].charAt(i - 1) == '*') {
								key = "ul";
							} else {
								key = "ol";
							}
							lines[j] = "<" + key + "><li>"
									+ lines[j].substring(i) + "</li>";
							start = true;
						} else {
							lines[j] = "<li>" + lines[j].substring(i) + "</li>";
						}
					} else {
						if (start) {
							lines[j - 1] = lines[j - 1] + "</" + key + ">";
							start = false;
						}
					}
				}
			}
			if (start) {
				lines[lines.length - 1] = lines[lines.length - 1] + "</" + key
						+ ">";
				start = false;
			}
		}

		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			sb.append(line);
			sb.append("\r\n");
		}
		return sb.toString();

	}

	private String formatDL(StringBuffer text) {
		String[] lines = text.toString().split("\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<dl>");
		for (String line : lines) {
			String[] dd = line.substring(1).split(":");
			for (int i = 0; i < dd.length; i++) {
				if (i == 0) {
					sb.append("<dt>");
					sb.append(dd[i]);
					sb.append("</dt>");
				} else {
					sb.append("<dd>");
					sb.append(dd[i]);
					sb.append("</dd>");
				}
			}
		}
		sb.append("</dl>");
		return sb.toString();
	}

	private String formatDD(StringBuffer text) {
		String[] lines = text.toString().split("\r\n");

		// 求出最深度
		int maxLevel = 0;
		Pattern p = Pattern.compile("^(:+)");
		for (String line : lines) {
			Matcher m = p.matcher(line);
			m.find();
			if (maxLevel < m.group(1).length()) {
				maxLevel = m.group(1).length();
			}
		}

		// 补齐差的行
		StringBuffer sb = new StringBuffer();
		int nextLevel = 0;
		String key = "";
		for (int i = 0; i < maxLevel; i++) {
			key = key + ":";
		}
		for (String line : lines) {
			Matcher m = p.matcher(line);
			m.find();
			int no = m.group(1).length() - nextLevel;
			for (int i = 1; i < no; i++) {
				sb.append(key.substring(0, i + 1));
				sb.append("\r\n");
			}
			sb.append(line);
			sb.append("\r\n");
			nextLevel = m.group(1).length();
		}

		lines = sb.toString().split("\r\n");

		for (int i = maxLevel; i > 0; i--) {
			boolean start = false;
			for (int j = 0; j < lines.length; j++) {
				Matcher m = p.matcher(lines[j]);
				if (m.find()) {
					if (i == m.group(1).length()) {
						if (!start) {
							lines[j] = "<dl><dd>" + lines[j].substring(i) + "";
							start = true;
						} else {
							lines[j] = "<dd>" + lines[j].substring(i) + "</dd>";
						}
					} else {
						if (start) {
							lines[j - 1] = lines[j - 1] + "</dd></dl>";
							start = false;
						}
					}
				}
			}
			if (start) {
				lines[lines.length - 1] = lines[lines.length - 1]
						+ "</dd></dl>";
				start = false;
			}
		}
		sb = new StringBuffer();
		for (String line : lines) {
			sb.append(line);
			sb.append("\r\n");
		}
		return sb.toString();
	}

	private String doBlock(String bType, StringBuffer text) {
		if (bType.equals(" ")) {
			return "<pre>" + formatHTML(text.toString()) + "</pre>";
		} else if (bType.equals("*") || bType.equals("#")) {
			return formatUL(text);
		} else if (bType.equals(":")) {
			return formatDD(text);
		} else if (bType.equals(";")) {
			return formatDL(text);
		}
		return text.toString();
	}

	private String formatHTML(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length() + 6);
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '\n') {
				buf.append("<br>");
				// } else if (ch == '\'') {
				// buf.append("&acute");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	private String urlConvert(String wiki) {
		String html = wiki;
		Pattern p = Pattern.compile("\\[\\[(.*?)\\[\\[(.*?)\\]\\]\\]\\]",
				Pattern.DOTALL);
		Matcher m = p.matcher(html);
		while (m.find()) {
			String src = m.group(1);
			String url = m.group(2);
			String re = url;
			int no = url.indexOf(":");
			if (no > 0) {
				url = url.substring(no);
			}
			re = "<a href='" + url + "'><img src='" + baseUrl + url + "'/></a>";
			html = html.replace(m.group(), re);
		}

		p = Pattern.compile("\\[\\[(.*?)\\]\\]", Pattern.DOTALL);
		m = p.matcher(html);
		while (m.find()) {
			String url = m.group(1);
			String re = url;
			int no = 0;
			if (url.toLowerCase().startsWith("image:")) {
				re = "<img src='" + baseUrl + url.substring(6) + "'/>";
			} else if ((no = url.indexOf("|")) > 0) {
				re = "<a href='" + baseUrl + url.substring(0, no) + "'>"
						+ url.substring(no + 1) + "</a>";
			} else if ((no = url.indexOf(" ")) > 0) {
				re = "<a href='" + baseUrl + url.substring(0, no) + "'>"
						+ url.substring(no + 1) + "</a>";
			} else {
				re = "<a href='" + baseUrl + url + "'>" + url + "</a>";
			}
			html = html.replace(m.group(), re);
		}
		html = html.replaceAll("\\{\\{.*?\\}\\}", "");
		return html;
	}

	private String endConvert(String wiki) {
		String html = wiki;

		String[] lines = html.split("\r\n");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("=")) {
				lines[i] = lines[i].replaceAll("^======(.*?)======",
						"<h6>$1</h6>");
				lines[i] = lines[i].replaceAll("^=====(.*?)=====",
						"<h5>$1</h5>");
				lines[i] = lines[i].replaceAll("^====(.*?)====", "<h4>$1</h4>");
				lines[i] = lines[i].replaceAll("^===(.*?)===", "<h3>$1</h3>");
				lines[i] = lines[i].replaceAll("^==(.*?)==", "<h2>$1</h2>");
				lines[i] = lines[i].replaceAll("^=(.*?)=", "<h1>$1</h1>");
			} else if ("".equals(lines[i].trim())) {
				lines[i] = "<br/>";
			}

			sb.append(lines[i]);
			sb.append("\r\n");
		}

		html = sb.toString();

		html = urlConvert(html);

		html = html.replaceAll("----", "<hr/>");
		html = html.replaceAll("'''''(.*?)'''''", "<i><b>$1</b></i>");
		html = html.replaceAll("''''(.*?)''''", "<i><b>$1</b></i>");
		html = html.replaceAll("'''(.*?)'''", "<b>$1</b>");
		html = html.replaceAll("''(.*?)''", "<i>$1</i>");
		html = html.replaceAll("~~~~~", (new Date()).toString());

		Pattern p = Pattern.compile("@@@pre@@@");
		Matcher m = p.matcher(html);
		while (m.find()) {
			String str = pre.get(0);
			str = "<pre>" + formatHTML(str) + "</pre>";
			html = html.replaceFirst(m.group(), str);
			pre.remove(0);
		}

		p = Pattern.compile("@@@nowiki@@@");
		m = p.matcher(html);
		while (m.find()) {
			String str = nowiki.get(0);
			str = formatHTML(str);
			html = html.replaceFirst(m.group(), str);
			nowiki.remove(0);
		}

		return html;
	}

	private String preConvert(String wiki) {
		String html = wiki;
		Pattern p = Pattern.compile("<pre>(.*?)</pre>", Pattern.DOTALL);
		Matcher m = p.matcher(html);
		while (m.find()) {
			pre.add(m.group(1));
			html = html.replace(m.group(), "@@@pre@@@");
		}

		p = Pattern.compile("<nowiki>(.*?)</nowiki>", Pattern.DOTALL);
		m = p.matcher(html);
		while (m.find()) {
			nowiki.add(m.group(1));
			html = html.replace(m.group(), "@@@nowiki@@@");
		}

		html = formatTable(html);
		return html;
	}

	private String doTable(String group) {
		String[] lines = group.split("\r\n");
		boolean trstart = false;
		boolean tdstart = false;
		boolean thstart = false;
		boolean castart = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("{|")) {
				if (lines[i].length() > 2) {
					sb.append("<table " + lines[i].substring(2).trim() + ">");
				} else {
					sb.append("<table border=\"1\">");
				}

			} else if (lines[i].startsWith("|}")) {
				if (thstart) {
					sb.append("</th>");
				}

				if (tdstart) {
					sb.append("</td>");
				}

				if (trstart) {
					sb.append("</tr>");
				}
				if (castart) {
					sb.append("</caption>");
				}
				sb.append("</table>");
			} else if (lines[i].startsWith("|-") || lines[i].startsWith("|+")) {
				if (thstart) {
					sb.append("</th>");
				}

				if (tdstart) {
					sb.append("</td>");
				}

				if (trstart) {
					sb.append("</tr>");
				}
				if (castart) {
					sb.append("</caption>");
				}
				if (lines[i].startsWith("|-")) {
					if (lines[i].length() > 2) {
						sb.append("<tr " + lines[i].substring(2) + ">");
					} else {
						sb.append("<tr>");
					}
					trstart = true;
				} else {
					String ca = urlConvert(lines[i].substring(2));
					int no = ca.indexOf("|");
					if (no > 0) {
						sb.append("<caption " + ca.substring(0, no) + ">"
								+ ca.substring(no + 1));
					} else {
						sb.append("<caption>" + ca);
					}
					castart = true;
				}
			} else if (lines[i].startsWith("|")) {
				if (castart) {
					sb.append("</caption>");
					castart = false;
				}

				if (tdstart) {
					sb.append("</td>");
				}

				tdstart = true;
				if (thstart) {
					sb.append("</th>");
					thstart = false;
				}

				String[] tds = lines[i].substring(1).split("\\|\\|");
				for (int j = 0; j < tds.length; j++) {
					String td = tds[j];
					td = urlConvert(td);
					int no = td.indexOf("|");
					if (no > 0) {
						sb.append("<td " + td.substring(0, no) + ">"
								+ td.substring(no + 1));
					} else {
						sb.append("<td>" + td);
					}
					if (j < tds.length - 1) {
						sb.append("</td>");
					}

				}
			} else if (lines[i].startsWith("!")) {
				if (castart) {
					sb.append("</caption>");
					castart = false;
				}

				if (thstart) {
					sb.append("</th>");
				}

				thstart = true;
				if (tdstart) {
					sb.append("</td>");
					tdstart = false;
				}

				String[] tds = lines[i].substring(1).split("!!");
				for (int j = 0; j < tds.length; j++) {
					String th = tds[j];
					th = urlConvert(th);
					int no = th.indexOf("|");
					if (no > 0) {
						sb.append("<th " + th.substring(0, no) + ">"
								+ th.substring(no + 1));
					} else {
						sb.append("<th>" + th);
					}
					if (j < tds.length - 1) {
						sb.append("</th>");
					}
				}
			} else {
				sb.append(lines[i]);
			}

			sb.append("\r\n");
		}

		return sb.toString().trim();
	}

	private String formatTable(String wiki) {
		String html = wiki;
		Pattern p = Pattern.compile("\\{\\|(.*?)\\|\\}", Pattern.DOTALL);
		while (true) {
			Matcher m = p.matcher(html);
			if (m.find()) {
				String source = m.group();
				int no = source.lastIndexOf("{|");
				if (no > 1) {
					source = source.substring(no);
				}
				String table = doTable(source);
				html = html.replace(source, table);
			} else {
				break;
			}
		}
		return html;
	}

	public String convert(String wiki) throws IOException {
		pre.clear();
		nowiki.clear();
		wiki = preConvert(wiki);
		BufferedReader bf = new BufferedReader(new StringReader(wiki));
		String line = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer block = new StringBuffer();
		boolean blockflag = false;
		String blockType = null;
		while ((line = bf.readLine()) != null) {
			if (line.startsWith(" ") || line.startsWith("*")
					|| line.startsWith("#") || line.startsWith(":")
					|| line.startsWith(";")) {
				String key = line.substring(0, 1);
				if (blockflag) {
					if (!key.equals(blockType)) {
						sb.append(doBlock(blockType, block));
						sb.append("\r\n");
						block = new StringBuffer();
						blockflag = true;
						blockType = key;
					}

					block.append(line + "\r\n");
				} else {
					block.append(line + "\r\n");
					blockflag = true;
					blockType = key;
				}

			} else {
				if (blockflag) {
					sb.append(doBlock(blockType, block));
					sb.append("\r\n");
					block = new StringBuffer();
					blockflag = false;
					blockType = null;
				}

				sb.append(line + "\r\n");
			}

		}
		if (blockflag) {
			sb.append(doBlock(blockType, block));
			sb.append("\r\n");
			block = new StringBuffer();
			blockflag = false;
			blockType = null;
		}

		return endConvert(sb.toString());
	}

	public String convert(URL url) {
		baseUrl = url.toString().substring(0,
				url.toString().lastIndexOf("/") + 1);
		InputStream fs = null;
		try {
			fs = url.openStream();
		} catch (Exception e) {
		}
		if (fs == null) {

		} else {
			StringBuffer buffer = new StringBuffer();
			readToBuffer(buffer, fs);
			try {
				fs.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
			try {
				String cssUrl = "/com/bestway/bcs/help/css/help.css";
				StringBuffer cssBuffer = new StringBuffer();
				readToBuffer(cssBuffer, this.getClass().getResource(cssUrl)
						.openStream());
				// System.out.println(htmlHead+"\r\n"+convert(buffer.toString())+"\r\n"+htmlEnd);
				return htmlHeadS + "\r\n"
						+ "<style type=\"text/css\" media=\"screen\">"
						+ cssBuffer.toString() + "</style>" + "\r\n"
						+ htmlHeadE + "\r\n" + convert(buffer.toString())
						+ "\r\n" + htmlEnd;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	public void readToBuffer(StringBuffer buffer, InputStream is) {
		String line = ""; // 用来保存每行读取的内容
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException(e1.getMessage());
		}
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} // 读取第一行
		while (line != null) {
			// 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\r\n"); // 添加换行符
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} // 读取下一行
		}
	}

}
