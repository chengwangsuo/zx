package com.zx.hoperun.zt.base;

import java.io.UnsupportedEncodingException;

/**
 * 描述: SHA-512 摘要算法实现类
 * 版权: Copyright (c) 2013  
 * 版本: 1.0 
 * 创建日期: 2013年7月29日 
 * 创建时间: 下午4:08:54
 */
public class SHA512
{
	//private static Logger logger = Logger.getLogger(SHA512.class);
	
	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private static String getSHA512(byte[] source)
	{
		String s = null;
		try
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
			md.update(source);
			byte tmp[] = md.digest(); // SHA-512 的计算结果是一个 64 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[64 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 64; i++)
			{ // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
			
		}
		catch (Exception e)
		{
			//logger.error("获取sha出错", e);
		}
		return s;
	}
	
	/**
	 * getMD5ofStr是类MD5最主要的公共方法，入口参数是你想要进行MD5变换的字符串
	 * 返回的是变换完的结果，这个结果是从公共成员digestHexStr取得的．
	 */
	public static String getSHA512ofStr(String inbuf)
	{
		String s="";
		try {
			s=getSHA512(inbuf.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//logger.error("上传数据转换异常"+e);
		}
		return s;
	}
	
	public static void main(String[] args) {
		 String toEncryptStr="123456789";
		System.out.println(SHA512.getSHA512ofStr(toEncryptStr));
	}
	
}
