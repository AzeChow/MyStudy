/*
 * Created on 2004-8-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.bestway.jptds.client.common.CommonVars;

/**
 * @author chp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyFile extends File{
	private File file;
	private String name;
	private long length;
	private String lastModified;

	public MyFile(File file){
		super(file.getAbsolutePath());
		this.file = file;		
		name = file.getName();
		length = file.length();
		lastModified = new SimpleDateFormat("yyyy-MM-dd HH:dd").format(file.lastModified());
	}

    public File getFile() {
        return file;
    }
    public String getLastModified() {
        return lastModified;
    }
    public long getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
}
