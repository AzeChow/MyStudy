package com.bestway.client.common;

import java.security.*;

public class MyDigest {
	public static void main(String[] args) {
		MyDigest my = new MyDigest();
		my.testDigest();

	}

	public void testDigest() {
		try {
			String secType = "MD5"; // "MD5" "SHA-1" "SHA-256" "SHA-512"

			String myinfo = "0";
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance(secType);
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();
			System.out.println("本信息摘要是:{" + secType + "}" + byte2hex(digesta));
			System.out.println(("{" + secType + byte2hex(digesta) + "}")
					.length());
			// 通过某中方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
			java.security.MessageDigest algb = java.security.MessageDigest
					.getInstance(secType);
			algb.update(myinfo.getBytes());

			if (algb.isEqual(digesta, algb.digest())) {
				System.out.println("信息检查正常");
			} else {
				System.out.println("摘要不相同");
			}

		} catch (java.security.NoSuchAlgorithmException ex) {
			System.out.println("非法摘要算法");
		}

	}

	public String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

}