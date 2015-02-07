package com.bestway.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 文件工具类
 * @author xc
 *
 */
public class IOUtils {

	public static final char DIR_SEPARATOR_UNIX = 47;
	public static final char DIR_SEPARATOR_WINDOWS = 92;
	public static final char DIR_SEPARATOR = File.separatorChar;
	public static final String LINE_SEPARATOR_UNIX = "\n";
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
	public static final String LINE_SEPARATOR;
	
	static {
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}
	public static char[] toCharArray(InputStream is) throws IOException {
		CharArrayWriter output = new CharArrayWriter();
		copy(is, output);
		return output.toCharArray();
	}

	public static char[] toCharArray(InputStream is, String encoding)
			throws IOException {
		CharArrayWriter output = new CharArrayWriter();
		copy(is, output, encoding);
		return output.toCharArray();
	}

	public static char[] toCharArray(Reader input) throws IOException {
		CharArrayWriter sw = new CharArrayWriter();
		copy(input, sw);
		return sw.toCharArray();
	}

	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	public static String toString(Reader input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}
	/**
	 * 复制
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}
	
	public static List readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	public static List readLines(InputStream input, String encoding)
			throws IOException {
		if (encoding == null) {
			return readLines(input);
		}
		InputStreamReader reader = new InputStreamReader(input, encoding);
		return readLines(reader);
	}

	public static List readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List list = new ArrayList();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	public static InputStream toInputStream(String input) {
		byte[] bytes = input.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	public static InputStream toInputStream(String input, String encoding)
			throws IOException {
		byte[] bytes = (encoding != null) ? input.getBytes(encoding) : input
				.getBytes();
		return new ByteArrayInputStream(bytes);
	}

	public static void write(byte[] data, OutputStream output)
			throws IOException {
		if (data != null)
			output.write(data);
	}

	public static void write(byte[] data, Writer output) throws IOException {
		if (data != null)
			output.write(new String(data));
	}

	public static void write(byte[] data, Writer output, String encoding)
			throws IOException {
		if (data != null)
			if (encoding == null)
				write(data, output);
			else
				output.write(new String(data, encoding));
	}

	public static void write(char[] data, Writer output) throws IOException {
		if (data != null)
			output.write(data);
	}

	public static void write(char[] data, OutputStream output)
			throws IOException {
		if (data != null)
			output.write(new String(data).getBytes());
	}

	public static void write(char[] data, OutputStream output, String encoding)
			throws IOException {
		if (data != null)
			if (encoding == null)
				write(data, output);
			else
				output.write(new String(data).getBytes(encoding));
	}

	public static void write(String data, Writer output) throws IOException {
		if (data != null)
			output.write(data);
	}

	public static void write(String data, OutputStream output)
			throws IOException {
		if (data != null)
			output.write(data.getBytes());
	}

	public static void write(String data, OutputStream output, String encoding)
			throws IOException {
		if (data != null)
			if (encoding == null)
				write(data, output);
			else
				output.write(data.getBytes(encoding));
	}

	public static void write(StringBuffer data, Writer output)
			throws IOException {
		if (data != null)
			output.write(data.toString());
	}

	public static void write(StringBuffer data, OutputStream output)
			throws IOException {
		if (data != null)
			output.write(data.toString().getBytes());
	}

	public static void write(StringBuffer data, OutputStream output,
			String encoding) throws IOException {
		if (data != null)
			if (encoding == null)
				write(data, output);
			else
				output.write(data.toString().getBytes(encoding));
	}

	public static void writeLines(Collection lines, String lineEnding,
			OutputStream output) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				output.write(line.toString().getBytes());
			}
			output.write(lineEnding.getBytes());
		}
	}

	public static void writeLines(Collection lines, String lineEnding,
			OutputStream output, String encoding) throws IOException {
		if (encoding == null) {
			writeLines(lines, lineEnding, output);
		} else {
			if (lines == null) {
				return;
			}
			if (lineEnding == null) {
				lineEnding = LINE_SEPARATOR;
			}
			for (Iterator it = lines.iterator(); it.hasNext();) {
				Object line = it.next();
				if (line != null) {
					output.write(line.toString().getBytes(encoding));
				}
				output.write(lineEnding.getBytes(encoding));
			}
		}
	}

	public static void writeLines(Collection lines, String lineEnding,
			Writer writer) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				writer.write(line.toString());
			}
			writer.write(lineEnding);
		}
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	public static int copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[4096];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static void copy(Reader input, OutputStream output)
			throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(output);
		copy(input, out);

		out.flush();
	}

	public static void copy(Reader input, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			OutputStreamWriter out = new OutputStreamWriter(output, encoding);
			copy(input, out);

			out.flush();
		}
	}

	public static boolean contentEquals(InputStream input1, InputStream input2)
			throws IOException {
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}

	public static boolean contentEquals(Reader input1, Reader input2)
			throws IOException {
		if (!(input1 instanceof BufferedReader)) {
			input1 = new BufferedReader(input1);
		}
		if (!(input2 instanceof BufferedReader)) {
			input2 = new BufferedReader(input2);
		}

		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return (ch2 == -1);
	}

	public static void closeQuietly(Closeable q){
		if(q != null)
			try {
				q.close();
			} catch (IOException e) {}
	}
	
}
