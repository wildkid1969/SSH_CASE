package com.rollingStones.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

public class GetHtml {
	public static String getHtml(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 【荔枝FM】使用Jsoup解析节目每页的html，输出节目标题和下载链接
	 * @param linkId 节目首页链接里的id
	 * @param maxPageNo 节目的总页数
	 * @throws IOException
	 */
	public static List<Map<String,Object>> getLizhiFMAudioAndTitle(String linkId,int maxPageNo) throws IOException{
		int page = 1;
		String urlAppend = "";
		boolean isParse = false;
		
		List<Map<String,Object>> film = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		while(page<=maxPageNo){
			Document doc = Jsoup.connect("http://www.lizhi.fm"+linkId+urlAppend).get();
//			String title = doc.title();
//			System.out.println(title);
			
			if(!isParse){
				//节目列表的标签块
//				Elements ListDiv = doc.getElementsByAttributeValue("class","frame");
				Elements ListDiv = doc.getElementsByAttributeValue("class","audioList fontYaHei js-audio-list");
				
			        for (Element element :ListDiv) {
			                Elements links = element.getElementsByTag("a");
			                for (Element link : links) {
			                    String linkHref = link.attr("href");
			                    
			                    //保留包含节目id的链接
			                    if(linkHref.contains(linkId) ){//<a href="/256461/2566139663675865606"
//				                    String linkText = link.text().trim();
			                	    String atitle = link.attr("title");
			                	    String dataUrl = link.attr("data-url");
			                	    
				                    //处理标题里的不标准字符
			                	    if(!StringUtils.isEmpty(atitle)){
			                		    //中文括号转成英文的
			                		    if(atitle.contains("（") || atitle.contains("）") ){
			                			    atitle = atitle.replace("（", "(").replace("）", ")");
			                		    }
			                		    
			                		    //如果标题里包含括号则添加上资源链接里的年份  如：054《蓝色骨头》(2014,6.4分)——新长征路上的电影
			                		    if(atitle.contains("(")){
			                			    atitle = atitle.split("\\(")[0]+"("+dataUrl.split("/")[4].trim()+","+atitle.split("\\(")[1];
			                		    }
			                		    
			                		    //下载链接里的文件名和标题绑定  如：2558520350889506822_hd=木玛正传：杜尚访谈录+离魂异客+尼尔·杨
			                		    map.put(dataUrl.split("/")[7].split(".mp3")[0], atitle);
			                		    film.add(map);//返回此集合 供批量修改文件名的方法调用
			                	    }
			                	    
			                	    //输出标题和下载链接
//				                    System.out.println(atitle);
//				                    System.out.println(dataUrl);
				                    
			                    }
			                  
			                }
			       }
			        isParse = true;
			}else{//翻页
				page++;
				urlAppend = "/p/"+page+".html";
				isParse = false;
			}
	   }
		
		System.out.println(film.size());
		return film;
	}
	
	
	/**
	 * 【荔枝FM】批量修改下载的节目名称
	 * @param linkId 节目首页链接里的id
	 * @param maxPageNo 节目的总页数
	 * @throws IOException
	 */
	public static void patchRenameLiZhiFMAudioFile(String linkId,int maxPageNo,String srcFolder,String desFolder) throws IOException{
		List<Map<String,Object>> films = getLizhiFMAudioAndTitle(linkId,maxPageNo);
		List<String> names = new ArrayList<String>();

		//取出下载好的所有文件
		File file = new File("D:\\"+srcFolder);
		File[] files = file.listFiles();
		String filename = "";
		
		for(File f:files){
			filename = f.getName().split(".mp3")[0];
			//匹配解析出来组装好的map
			for(int i=0;i<films.size();i++){
				if(!names.contains(filename) && films.get(i).get(filename)!=null){
					//有时候会重命名不成功，可能是名字里有特殊字符串
					f.renameTo(new File("D:\\"+desFolder+"\\"+films.get(i).get(filename).toString().replace(":", "-").replace("：", "-")+".mp3"));
					System.out.println(films.get(i).get(filename));
					names.add(filename);
				}
			}
		}
		
//		File f2 = new File("D:\\8557838576670598_hd.mp3");
//		f2.renameTo(new File("D:\\师傅_hd.mp3"));
		System.out.println(names.size());
	}
	
	
	/**
	 * 获取豆瓣某个用户在活动页面里参与的位置页码
	 * @throws IOException
	 */
	public static void getVV(String username) throws IOException{
		int page = 1;
		int pageSize = 90;
		
		for(int i=0;i<12;i++){
			
			int start = (page-1)*pageSize;
			String url = "https://www.douban.com/online/122899005/album/1623433216/?start="+start+"&sortby=time";
			
			Document doc = Jsoup.connect(url).get();
			
			if(doc.toString().contains(username)){
				System.out.println("页码："+page);//页码：9
			}
			
			page++;
			
		}
		
		
	}
	
	
	public static void getDoubanFilmTop250(int isSimple) throws IOException{
		int page=0;
		int index = 1;
		for(int i=0;i<10;i++){
			
			String url = "https://movie.douban.com/top250?start="+page;
			
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			
			Elements eles = doc.getElementsByClass("info");
//			System.out.println(eles);
			
			Elements artist = null;
			
			
			for(Element e:eles){
			    String titleName = "没有别名";
	            String otherName = "没有别名";
	            String artistList = "";
	            
				artist = e.getElementsByTag("p");
				
				if(e.getElementsByAttributeValue("class", "title").size()>1){
					titleName = e.getElementsByAttributeValue("class", "title").get(1).html();
				}
				
				otherName = e.getElementsByAttributeValue("class", "other").get(0).html();
				
				if(org.apache.commons.lang.StringUtils.isEmpty(otherName)){
					otherName = "没有别名";
				}
				
				if(artist.get(0).html().contains("主演:")){
				    artistList = artist.get(0).html()
				            .replace("&nbsp;", "").replace("导演:", "").replace("主演:", "|").replace("<br>", "|"); 
				}else{
				    artistList = artist.get(0).html()
				            .replace("&nbsp;", "").replace("导演:", "").replace("...<br>", "...|...<br>").replace("<br>", "|");
				}
				
				
				if(isSimple==0){
					System.out.println(index+"|"+ e.getElementsByAttributeValue("class", "title").get(0).html()
							+"|"+ titleName.replace("&nbsp;", "")
							+"|"+ otherName.replace("&nbsp;", "")
							+"|"+artistList
							+"|"+CommonUtils.getNumFromString(artist.get(0).html())
							+"|"+e.getElementsByAttributeValue("class", "rating_num").html()
							+"|"+e.getElementsByAttributeValue("class", "star").get(0)
							        .getElementsByTag("span").last().html().replace("人评价", "")
							+"|"+e.getElementsByAttributeValue("class", "inq").html());
				}else{
					System.out.println(index+" "+ e.getElementsByAttributeValue("class", "title").get(0).html()
							+"|"+ titleName.replace("&nbsp;", "")
							+"|"+ otherName.replace("&nbsp;", "")
							+"|"+artist.get(0).html().replace("&nbsp;", "")
							+"("+CommonUtils.getNumFromString(artist.get(0).html())+")"
							+"["+e.getElementsByAttributeValue("class", "rating_num").html()+"/"
							+e.getElementsByAttributeValue("class", "star").get(0).getElementsByTag("span").last().html()+"]"
							+"["+e.getElementsByAttributeValue("class", "inq").html()+"]");
				}
				
				
				
				index++;
			}
			
			page+=25;
		}
		
	}
	
	
	
		public static void main(String[] args) throws Exception {
			//下载荔枝FM里的节目
//			getLizhiFMAudioAndTitle("/256461",9);
			//批量改名字（下载的文件都是日期名字）
//			patchRenameLiZhiFMAudioFile("/256461",9, "making", "小凤直播室");
			
			//获取豆瓣电影Top250
//			String html = getHtml("https://movie.douban.com/top250?start=25&filter=");
//			System.out.println(html);
			
			//获取用户在那个页面发表的内容
//			getVV("苹果花");
			
			//获取豆瓣电影top250
//			getDoubanFilmTop250(0);
		    
			
	}
}