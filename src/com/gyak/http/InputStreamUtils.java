package com.gyak.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.gyak.proterty.NotInitRequestProperties;

/**
 * <p><B>功能:</B> 接收gzip流
 * @author 作者 E-mail:guiyanakuang@gmail.com
 * @version 创建时间：2014年10月11日 下午7:16:26
 *
 */

public class InputStreamUtils {

	/**
	 * 接收gzip流，按不同编码输出网页源码
	 * @param in  输入流
	 * @param encoding 转化编码
	 * @return 网页源码
	 * @throws IOException
	 */
	private static String uncompress(InputStream in, String encoding) {   
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();    
			GZIPInputStream gunzip;
			gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];   
			int n;   
			while ((n = gunzip.read(buffer))>= 0) {   
				out.write(buffer, 0, n);   
			}     
			gunzip.close();
			return out.toString(encoding);   
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return "";
	  }   
	
	private static String InputStreamTOString(InputStream in, String encoding){
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int count = -1;
			while((count = in.read(data,0,1024)) != -1){
				outStream.write(data, 0, count);
			}
			in.close();
			return new String(outStream.toByteArray(),encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getHttp(Gurl gurl) throws NotInitRequestProperties{
		try {
			InputStream in = gurl.getInputStream();
			String encoding = gurl.getContentType();
			if(gurl.isGzip())
				return uncompress(in, encoding);
			else
				return InputStreamTOString(in, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
