package com.rollingStones.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PicWarm {

	/**
	 * 花瓣网是
	 * @param url
	 * @throws IOException
	 */
	public static void getHuabanPicAblum(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        
        Element ele = doc.getElementById("waterfall");
        
        Elements list = ele.getElementsByAttributeValue("class", "pin wfc ");
        
        int index = 1;
        for(Element e : list){
        	Elements pic = e.getElementsByTag("img");
        	String picUrl = "http:" + pic.get(1).attr("src").split("_fw236")[0];
        	System.out.println(picUrl);
        	HttpClientUtils.downloadNetPic(picUrl, index+".jpg", "d://Cyber Punk");
        	index++;
        	
        }
	}
	
	public static void main(String[] args) throws IOException {
		getHuabanPicAblum("http://huaban.com/boards/45324469/");
	}
}
