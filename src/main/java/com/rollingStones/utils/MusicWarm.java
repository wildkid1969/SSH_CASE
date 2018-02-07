package com.rollingStones.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MusicWarm {
	public static void getXiamiArtist(){
		int index = 1;
		int count = 0;
		
		for(int j=1;j<5000;j++){
			for(int i=0;i<10;i++){
				String url = "http://www.xiami.com/genre/artists/gid/"+j+"/page/"+index;
				
				Document doc = null;
				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e) {
					break;
				}
				
				if(doc == null){
					break;
				}
				
				Elements eles = doc.getElementsByAttributeValue("id", "artists");
				Elements artist = eles.get(0).getElementsByTag("strong");
				
				for(Element ele : artist){
					count++;
					System.out.println(count+"-"+ele.text());
				}
				
				index++;
			}
		}
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
		getXiamiArtist();
	}
}
