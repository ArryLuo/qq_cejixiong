package com.example.qq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class HttpQQThread extends Thread {
	public static final String APPKEY = "3c64bb2d23180e5d01b84041cca80215";
	private String qq;
	private TextView name,content;
	private Handler handler;
	public HttpQQThread(String qq, TextView name, TextView content,
			Handler handler) {
		super();
		this.qq = qq;
		this.name = name;
		this.content = content;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String result = null;
		String url = "http://japi.juhe.cn/qqevaluate/qq";// 请求接口地址
		Map params = new HashMap();// 请求参数
		params.put("key", APPKEY);// 您申请的appKey
		params.put("qq", qq);// 需要测试的QQ号码
		result = net(url, params);
		System.out.println("------------");
		System.out.println(result);
	}

	public String net(String strUrl, Map params) {
		strUrl = strUrl + "?" + urlencode(params);
		try {
			URL url_path = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url_path
					.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("GET");
			if(con.getResponseCode()==200){
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String len;
				StringBuilder sb = new StringBuilder();
				while ((len = br.readLine()) != null) {
					sb.append(len);
				}
				Log.v("内容", sb.toString());
				final QQInfo qq=getjson(sb.toString());
				//System.out.println(qq.toString()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String sname=qq.getConclusion();
						String scontent=qq.getAnalysis();
						name.setText(sname+"\n"+"\n"+scontent);
						//content.setText(scontent);
					}
				});
				return sb.toString();
			}else{
				System.out.println("请检查网络");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public QQInfo getjson(String json){
		QQInfo info=null;
		try {
			JSONObject object=new JSONObject(json);
			Log.v("Object", object+"");
			JSONObject objres=object.getJSONObject("result");
			//System.out.println(array.length()+"+++++++++");
			JSONObject data = objres.getJSONObject("data");
			String conclusion = data.getString("conclusion");
			String analysis = data.getString("analysis");
			info = new QQInfo(conclusion, analysis);
			return info;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
