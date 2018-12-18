package com.jxtii.demo;

import com.jxtii.oa.common.utils.BaiduUtils;

/**
 * 
 * @author Fly
 *
 */
public class Demo {

	public static void main(String[] args) {
		baiduDemo();

	}
	
	
	/**
	 * 百度经纬度测试
	 */
	public static void baiduDemo() {
		String dom = "云南省大理白族自治州大理市玉洱路131号 ";
        String coordinate = BaiduUtils.getCoordinate(dom);
        System.out.println("'" + dom + "'的经纬度为：" + coordinate);
        
        String lng = coordinate.split(",")[0];
    	String lat = coordinate.split(",")[1];
        String address = BaiduUtils.getAddress(lng, lat);
        System.out.println("地址："+address);
	}

}
