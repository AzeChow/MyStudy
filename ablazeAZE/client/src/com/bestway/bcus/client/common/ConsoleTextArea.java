/*
 * Created on 2005-5-11
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConsoleTextArea extends JTextArea {
    
    public class LoopedStreams {
        private PipedOutputStream pipedOS = new PipedOutputStream();

        private boolean keepRunning = true;

        private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
            public void close() {
                keepRunning = false;
                try {
                    super.close();
                    pipedOS.close();
                } catch (IOException e) {
                    //   记录错误或其他处理
                    //   为简单计，此处我们直接结束
                    System.exit(1);
                }
            }
        };

        private PipedInputStream pipedIS = new PipedInputStream() {
            public void close() {
                keepRunning = false;
                try {
                    super.close();
                } catch (IOException e) {
                    //   记录错误或其他处理
                    //   为简单计，此处我们直接结束
                    System.exit(1);
                }
            }
        };

        public LoopedStreams() throws IOException {
            pipedOS.connect(pipedIS);
            startByteArrayReaderThread();
        } // LoopedStreams()

        public InputStream getInputStream() {
            return pipedIS;
        } // getInputStream()

        public OutputStream getOutputStream() {
            return byteArrayOS;
        } // getOutputStream()

        private void startByteArrayReaderThread() {
            new Thread(new Runnable() {
                public void run() {
                    while (keepRunning) {
                        //   检查流里面的字节数
                        if (byteArrayOS.size() > 0) {
                            byte[] buffer = null;
                            synchronized (byteArrayOS) {
                                buffer = byteArrayOS.toByteArray();
                                byteArrayOS.reset(); // 清除缓冲区
                            }
                            try {
                                //   把提取到的数据发送给PipedOutputStream
                                pipedOS.write(buffer, 0, buffer.length);
                            } catch (IOException e) {
                                //   记录错误或其他处理
                                //   为简单计，此处我们直接结束
                                System.exit(1);
                            }
                        } else
                            // 没有数据可用，线程进入睡眠状态
                            try {
                                //   每隔1秒查看ByteArrayOutputStream检查新数据
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                    }
                }
            }).start();
        } // startByteArrayReaderThread()
    } // LoopedStreams


    public ConsoleTextArea(InputStream[] inStreams) {
        for (int i = 0; i < inStreams.length; ++i)
            startConsoleReaderThread(inStreams[i]);
    } // ConsoleTextArea()

    public ConsoleTextArea() throws IOException {
        final LoopedStreams ls = new LoopedStreams();

        //       重定向System.out和System.err
        PrintStream ps = new PrintStream(ls.getOutputStream());
        System.setOut(ps);
        System.setErr(ps);

        startConsoleReaderThread(ls.getInputStream());
    } // ConsoleTextArea()

    private void startConsoleReaderThread(InputStream inStream) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(
                inStream));
        new Thread(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    String s;
                    Document doc = getDocument();
                    while ((s = br.readLine()) != null) {
                        boolean caretAtEnd = false;
                        caretAtEnd = getCaretPosition() == doc.getLength() ? true
                                : false;
                        sb.setLength(0);
                        append(sb.append(s).append("").toString());
                        append("\n");
                        if (caretAtEnd)
                            setCaretPosition(doc.getLength());
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "从BufferedReader读取错误："
                            + e);
                    System.exit(1);
                }
            }
        }).start();
    } // startConsoleReaderThread() 

}