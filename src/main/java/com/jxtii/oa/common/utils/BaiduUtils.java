package com.jxtii.oa.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import net.sf.json.JSONObject;

/**
 * BaiduAPI工具类
 * @author Fly
 * @version 2018-12-09
 */

public class BaiduUtils {
	
	/**
	 * 百度API KEY
	 */
	public static final String AK = "o8sN9QGYKGNZMQipXxS3il73Guu6eFDo";
	
	
	/**
	 * 调用百度地图API
	 * 根据地址，获取经纬度
	 * @param address
	 * @return
	 */
    public static String getCoordinate(String address) {
        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.fromObject(json);
                if ("0".equals(obj.getString("status"))) {
                    //经度
                	double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
                    //纬度
                	double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
                    //格式处理
                	DecimalFormat df = new DecimalFormat("#.######");
                    return df.format(lng) + "," + df.format(lat);
                }
            }
        }
        return null;
    }
    
    
    
    /**
     * 调用百度地图API
	 * 根据经纬度，获取地址
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static String getAddress(String lng, String lat){
    	String url = "http://api.map.baidu.com/geocoder/v2/?"
    			+ "ak="+ AK +"&location="+ lat + "," + lng +"&output=json&pois=1";
    	String json = loadJSON(url);
    	if (json != null && !"".equals(json)) {
    		JSONObject obj = JSONObject.fromObject(json);
    		//所有地址数据
    		System.out.println(obj.toString());
    		if ("0".equals(obj.getString("status"))) {
    			//格式化地址
    			String formatted_address = obj.getJSONObject("result").getString("formatted_address");
    			return formatted_address;
    		}
    	}
    	
    	return null;
	}
    
    
    
    /**
     * JSON数据处理
     * @param url 百度API地址
     * @return
     */
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

}
