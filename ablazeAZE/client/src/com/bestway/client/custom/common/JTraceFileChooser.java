/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.client.custom.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */
public class JTraceFileChooser extends JFileChooser {

    private String uniquelyIdentifies;

//     public final boolean isWindowSystem = System.getProperty("os.name").toLowerCase().indexOf("windows") > -1;
    public JTraceFileChooser(String uniquelyIdentifies) {
        super();
        this.uniquelyIdentifies = uniquelyIdentifies;
        if (this.uniquelyIdentifies != null && !"".equals(this.uniquelyIdentifies.trim())) {
            String filePath = getFilePath();
            if (new File(filePath).exists()) {
                String currentDirectory = this.readValue(filePath, this.uniquelyIdentifies);
                if (currentDirectory != null && !"".equals(currentDirectory.trim())) {
                    this.setCurrentDirectory(new File(currentDirectory.trim()));
                }
//                System.out.println("--------------currentDirectory:" + currentDirectory);
            } else {
                System.out.println("文件：" + filePath + "不存在");
            }
        }
    }

    @Override
    public File getSelectedFile() {
        File selectedFile = super.getSelectedFile();
        if (selectedFile != null) {
            if (this.uniquelyIdentifies != null && !"".equals(this.uniquelyIdentifies.trim())) {
                String filePath = getFilePath();
                if (!(new File(filePath).exists())) {
                    try {
                        new File(filePath).createNewFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                String currentDirectory = selectedFile.getParent();
//                System.out.println("-----------currentDirectory:" + currentDirectory);
                writeProperties(filePath, this.uniquelyIdentifies.trim(), currentDirectory);
            }
        }
        return selectedFile;
    }

    private String readValue(String filePath, String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty(key);
//            System.out.println(key + value);
            if(value == null || value.isEmpty()){
                value = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //写入properties信息
    private void writeProperties(String filePath, String parameterName, String parameterValue) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            //从输入流中读取属性列表（键和元素对）
            prop.load(fis);
            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(filePath);
            if(parameterValue != null)
                prop.setProperty(parameterName, parameterValue);
            //以适合使用 load 方法加载到 Properties 表中的格式，
            //将此 Properties 表中的属性列表（键和元素对）写入输出流
            prop.store(fos, "Update '" + parameterName + "' value");
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
        }
    }

    private String getFilePath() {
        String filePath = System.getProperty("java.io.tmpdir") + File.separator+ "filechooser.properties";
//        System.out.println("-------------------filePath:" + filePath);
        return filePath;
    }
}
