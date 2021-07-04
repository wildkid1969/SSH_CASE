package com.rollingStones.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: mengya
 * @Date: 2020-09-27 17:48
 */
public class DoubanMusicWarm {

    public static void getMyDoubanMusicList() throws IOException {
        Pattern notNum = Pattern.compile("[^0-9]");

        HSSFWorkbook wkb = new HSSFWorkbook();
        HSSFSheet sheet = wkb.createSheet("豆瓣音乐");

        HSSFRow row2 = sheet.createRow(0);
        row2.createCell(0).setCellValue("ablum_id");
        row2.createCell(1).setCellValue("ablum_link");
        row2.createCell(2).setCellValue("ablum_name");
        row2.createCell(3).setCellValue("other_name");
        row2.createCell(4).setCellValue("musician");
        row2.createCell(5).setCellValue("pic");
        row2.createCell(6).setCellValue("intro");
        row2.createCell(7).setCellValue("on_time");
        row2.createCell(8).setCellValue("listen_time");
        row2.createCell(9).setCellValue("tags");
        row2.createCell(10).setCellValue("comments");

        int index = 0;
        for(int i = 0; i<2; i++){
            String url = "https://music.douban.com/people/50733160/collect?start="+index+"&sort=time&rating=all&filter=all&mode=grid";

            Document doc = null;
            try {
                Map<String,String> cookieMap = Maps.newHashMap();
                cookieMap.put("dbcl2","\"50733160:JY12QebKYeY\"");
                cookieMap.put("douban-fav-remind","1");
                cookieMap.put("gr_user_id","764728cc-9b64-4843-ab72-d351c7a36822");
                cookieMap.put("ll","\"108288\"");
                cookieMap.put("viewed","\"1475933\"");
                cookieMap.put("_pk_id.100001.afe6","c02eb0ceedc8ddf2.1607496350.44.1611896042.1611892417.");
                cookieMap.put("_pk_ref.100001.afe6","%5B%22%22%2C%22%22%2C1611896042%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DPbZIZDtTT2HzNZjWsUNS5UyPN2m8HPzxiVRJDqw8hRrAkoTafkkiR_Gen6D9oE8JlvP7AZtvY1hqonic6eXDY_%26wd%3D%26eqid%3De74e7bf50001f1b5000000036007a30e%22%5D");
                cookieMap.put("_pk_ses.100001.afe6","*");
                doc = Jsoup.connect(url)
                        .header("Host", "music.douban.com")
                        .header("Referer","https://music.douban.com/people/50733160/collect")
                        .header("Sec-Fetch-Dest","document")
                        .header("Sec-Fetch-Mode","navigate")
                        .header("Sec-Fetch-Site","same-origin")
                        .header("Sec-Fetch-User","?1")
                        .header("Upgrade-Insecure-Requests","1")
                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36")
                        .cookies(cookieMap).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements eles = doc.getElementsByAttributeValue("class", "item");

            for(int y = 0;y<eles.size();y++){
                String musicId = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("a").attr("href");
                String titleTemp = eles.get(y).getElementsByAttributeValue("class","title").text();
                String title = titleTemp.split("/")[0];
                String otherName = null;
                if(titleTemp.contains("/")){
                    otherName = titleTemp.substring(titleTemp.indexOf("/")).replaceFirst("/ ","");
                }else{
                    otherName = titleTemp.replaceFirst("/ ","");
                }

                String musician = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[0].trim();
                String pic = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("img").attr("src");
                String[] yearArr = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/");
                String year = null;
                if(yearArr.length>1){
                    year = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[1].trim();
                }else{
                    year = "-";
                }

                String watchTime = eles.get(y).getElementsByAttributeValue("class","date").text();
                String tags = eles.get(y).getElementsByAttributeValue("class","tags").text().replaceAll("标签: ","");
                String comments = eles.get(y).getElementsByAttributeValue("class","info").get(0).getElementsByTag("li").eq(3).text();

                System.out.println(notNum.matcher(musicId).replaceAll(""));
                System.out.println(title);
                System.out.println(otherName);
                System.out.println(musician);
                System.out.println(pic);
                System.out.println(eles.get(y).getElementsByAttributeValue("class","intro").text());
                System.out.println(year);
                System.out.println(watchTime);
                System.out.println(tags);
                System.out.println(comments.replaceAll("修改","").replaceAll("删除",""));

                //生成excel
                HSSFRow row3 = sheet.createRow((y + 1)+index);
                row3.createCell(0).setCellValue(notNum.matcher(musicId).replaceAll(""));
                row3.createCell(1).setCellValue(musicId);
                row3.createCell(2).setCellValue(title);
                row3.createCell(3).setCellValue(otherName);
                row3.createCell(4).setCellValue(musician);
                row3.createCell(5).setCellValue(pic);
                row3.createCell(6).setCellValue(eles.get(y).getElementsByAttributeValue("class","intro").text());
                row3.createCell(7).setCellValue(year);
                row3.createCell(8).setCellValue(watchTime);
                if(StringUtils.isNotEmpty(tags)){
                    row3.createCell(9).setCellValue(tags);
                }else{
                    row3.createCell(9).setCellValue("-");
                }

                if(StringUtils.isNotEmpty(comments.replaceAll("修改","").replaceAll("删除",""))){
                    row3.createCell(10).setCellValue(comments.replaceAll("修改","").replaceAll("删除",""));
                }else{
                    row3.createCell(10).setCellValue("-");
                }

                pic = pic.replaceAll("img2","img9");

                //下载图片
//                HttpClientUtils.downloadNetPic(pic, watchTime+"-"+musician+"-"+title.replace(":","：").split("/")[0]+".jpg", "E://MyDoubanMusicPic");

            }

            index+=15;
        }

//        wkb.write(new File("d://doubanMusic.xls"));
        System.out.println("creating file success!");

    }

    public static void main(String[] args) throws IOException {
        getMyDoubanMusicList();
    }
}
