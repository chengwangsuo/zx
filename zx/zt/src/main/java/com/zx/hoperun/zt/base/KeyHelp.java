package com.zx.hoperun.zt.base;

import com.thinkive.base.util.Base64;

import java.net.URLEncoder;

/**
 * @√Ë ˆ π´‘øº”√‹
 * @author tiger
 *
 */
public class KeyHelp {

	public static String  getStrByPublic(String publickey,String data){		
		String reData = "";
		byte[] encryData = null;
		try {
			encryData = Coder.encryptByPublicKey(data.getBytes(), publickey);
			reData = URLEncoder.encode(Base64.encodeBytes(encryData, Base64.DONT_BREAK_LINES),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return  Base64.encodeBytes(reData.getBytes(), Base64.DONT_BREAK_LINES);
	}
	public static void main(String[] args) {
		System.out.println(KeyHelp.getStrByPublic("PublicKey", "ptyacct=ptyacct&ptypwd=ptypwd&encrykey=encrykey"));
	}
}
