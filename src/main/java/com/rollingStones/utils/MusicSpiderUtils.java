package com.rollingStones.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MusicSpiderUtils {

    /**
     * 获取虾米音乐专辑里的曲目列表
     * @param albumId
     * @throws IOException
     */
    public static void getXiamiSongTracks(String albumId) throws IOException{
        String url = "http://www.xiami.com/album/"+albumId;
        
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.toString());
        Element ele = doc.getElementById("track");
        System.out.println(ele.toString());
//      Elements eles = ele.getElementsByAttributeValue("class", "trackid");
//      for(Element e:eles){
//          System.out.println(e.html());
//      }
        
        Elements songNameEles = ele.getElementsByAttributeValue("class", "song_name");
        
        int index=1;
        for(Element e:songNameEles){
            System.out.println(index+" "+e.child(0).html());
            index++;
            
        }
        
    }
    
    public static void getXiamiSongLyrics(String albumId) throws IOException{
        String url = "http://www.xiami.com/album/"+albumId;
        
        Document doc = Jsoup.connect(url).get();
        
        Element ele = doc.getElementById("track");
        
        Elements songNameEles = ele.getElementsByAttributeValue("class", "song_name");
        String lrcUrl = "http://www.xiami.com/";
        
        int index=1;
        //获取每首歌的歌词
        for(Element e:songNameEles){
            
            Document lrcDoc = Jsoup.connect(lrcUrl+e.child(0).attr("href")).get();

            if(lrcDoc.getElementById("lrc").html().contains("此歌曲暂无歌词")){
                System.out.println("Sorry，此歌曲暂无歌词。");
            }else{
                String title = lrcDoc.getElementById("lrc").getElementsByTag("strong").html();
                
                if(title.contains("《")){
                    title = title.replace("《", "\n\n"+index+".《").replace("歌词：", "");
                }
                System.out.println(HtmlUtil.delHTMLTag2(title));
                System.out.println("  "+HtmlUtil.delHTMLTag2(lrcDoc.getElementById("lrc").getElementsByAttributeValue("class", "lrc_main").toString().replace("<br>", "")));
                System.out.println("\n");
            }
            
            index++;
            
        }
        
    }
    
    public static void getNeteaseCloudSongTracks(int albumId) throws IOException{
        String url = "http://music.163.com/album?id="+albumId;
                
        Document doc = Jsoup.connect(url).get();
        
        Elements content = doc.getElementsByAttributeValue("class", "f-hide");
        
        String introduce = content.get(0).html();
        
        System.out.println("专辑介绍：");
        System.out.println(HtmlUtil.delHTMLTag2(introduce).replace("&nbsp;", " ").replace("&amp;", "&"));
        
        Elements songList = content.get(1).getElementsByTag("a");
        
        System.out.println();
        System.out.println("专辑曲目：");
        int index = 1;
        for(Element e:songList){
            System.out.println(index+" "+e.text());
            index++;
        }
        
    }
    
    
    public static void main(String[] args) throws IOException {
        //获取虾米专辑曲目列表
        String albumId="2102741521";
        getXiamiSongTracks(albumId);
        System.out.println("\n\n\n");
        //获取虾米专辑歌词
        getXiamiSongLyrics(albumId);
        
        //获取网易云音乐的专辑介绍和歌曲列表（歌词通过token获取的TNND）
//        int albumId = 36715012;
//        getNeteaseCloudSongTracks(albumId);
    }
}
