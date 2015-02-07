package com.bestway.common;

import java.util.HashMap;
import java.util.Map;

public class ProcExeProgress {
	private static ProcExeProgress progress = new ProcExeProgress();

	private Map hmProgress = new HashMap();

	public static ProcExeProgress getInstance() {
		Math.random();
		return progress;
	}

	public ProgressInfo getProgressInfo(String id) {
		if(id==null||"".equals(id)){
			return null;
		}
		ProgressInfo info = (ProgressInfo) hmProgress.get(id);
		if (info != null) {
			info.setNowTime(System.currentTimeMillis());
			return info;
		} else {
			info = new ProgressInfo();
			info.setNowTime(System.currentTimeMillis());
			hmProgress.put(id, info);
			return info;
		}
	}

	public void removeProgressInfo(String id) {
		if (hmProgress.get(id) != null) {
			hmProgress.remove(id);
		}
	}
}
