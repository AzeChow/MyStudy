/*
 * Created on 2005-5-31
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.util.Iterator;
import java.util.List;

import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public final class Container {

	/**
	 * 替换字符串的个数
	 */
	private static String repeatStr(String str, int count) {
		String returnStr = "";
		for (int i = 0; i < count; i++) {
			returnStr += str;
		}
		return returnStr;
	}

	/**
	 * 
	 * 获得所有集装箱号码
	 */
	public static String getAllContainerCode(List containers) {
		String returnStr = "";
		if (containers != null) {

			if (containers.size() > 0) {
				for (int i = 0; i < containers.size(); i++) {
					if (i > 10) {
						break;
					}
					BaseCustomsDeclarationContainer obj = (BaseCustomsDeclarationContainer) containers
							.get(i);
					int count = getStandardCaptionCountByContainer(obj);
					// 21位的字符串,集装箱编号+集装箱标识个数+集装箱代码+车架号如果不够21位补空字符
					String temp = obj.getContainerCode().trim()
							+ count
							+ (obj.getSrtJzx() == null ? "" : obj.getSrtJzx()
									.getCode().trim())
							+ (obj.getSrttj() == null ? "" : obj.getSrttj()
									.getCode().trim());
					temp += repeatStr(" ", 21 - temp.length()) + ",";
					returnStr += temp;
				}
				// Iterator iterator = containers.iterator();
				// while (iterator.hasNext()) {
				// BaseCustomsDeclarationContainer obj =
				// (BaseCustomsDeclarationContainer) iterator
				// .next();
				// int count = getStandardCaptionCountByContainer(obj);
				// // 21位的字符串,集装箱编号+集装箱标识个数+集装箱代码+车架号如果不够21位补空字符
				// String temp = obj.getContainerCode().trim()
				// + count
				// + obj.getSrtJzx().getCode().trim()
				// + (obj.getSrttj() == null ? "" : obj.getSrttj()
				// .getCode().trim());
				// temp += repeatStr(" ", 21 - temp.length()) + ",";
				// returnStr += temp;
				// }
				returnStr = returnStr.substring(0, returnStr.length() - 1);
			}
		}
		return returnStr;
	}

	/**
	 * 获得所有集装箱折合标准箱的个数的字符串
	 */
	public static String getAllConvertToContainerCode(List containers) {
		String returnStr = "";
		int containerCount = 0;
		int convertToCount = 0;
		if (containers != null) {
			containerCount = containers.size();
			if (containerCount > 0) {
				Iterator iterator = containers.iterator();
				int i = 0;
				while (iterator.hasNext()) {
					BaseCustomsDeclarationContainer obj = (BaseCustomsDeclarationContainer) iterator
							.next();
					convertToCount += getStandardContainerCountByContainer(obj);
					if (i == 0) {
						returnStr += obj.getContainerCode().trim();
						i = -1;
					}
				}
				returnStr += "*" + String.valueOf(containerCount) + "("
						+ String.valueOf(convertToCount) + ")";
			}
		}
		// System.out.println(":::::::::::"+returnStr);
		return returnStr.equals("") ? "0" : returnStr;
	}

	/**
	 * 获得标准箱标识个数
	 */
	private static int getStandardCaptionCountByContainer(
			BaseCustomsDeclarationContainer obj) {
		if (obj == null || obj.getSrtJzx() == null
				|| obj.getSrtJzx().getName() == null
				|| obj.getSrtJzx().getName().equals("")
				|| obj.getSrtJzx().getName().length() < 2) {
			return 0;
		}
		int temp = Integer.parseInt(obj.getSrtJzx().getName().trim().substring(
				0, 2));
		return temp / 20 + (temp % 20 > 0 ? 1 : 0);
	}

	/**
	 * 获得标准箱的个数来自集装箱
	 */
	private static int getStandardContainerCountByContainer(
			BaseCustomsDeclarationContainer obj) {
		if (obj == null || obj.getSrtJzx() == null
				|| obj.getSrtJzx().equals("")
				|| obj.getSrtJzx().getName() == null
				|| obj.getSrtJzx().getName().equals("")
				|| obj.getSrtJzx().getName().length() < 2) {
			return 0;
		}
		int temp = Integer.parseInt(obj.getSrtJzx().getName().trim().substring(
				0, 2));
		return temp / 20 /* + (temp % 20 > 0 ? 1 : 0) */;
	}

}
