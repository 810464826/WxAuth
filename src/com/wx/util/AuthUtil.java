package com.wx.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * @author ChenXb
 *
 * 2018年3月21日
 */
public class AuthUtil {
	public static final String APPID="您自己的APPID";
	public static final String APPSECRET="您自己的APPSECRET";
	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException{
		JSONObject jsonObject=null;
		//这个方法过时了 换成下面的方式了
		//DefaultHttpClient client=new DefaultHttpClient();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		HttpResponse response=httpclient.execute(httpGet);
		HttpEntity entity=response.getEntity();
		if(entity!=null){
			String result=EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		//处理完后，释放链接
		httpGet.releaseConnection();
		return jsonObject;
	}
}
