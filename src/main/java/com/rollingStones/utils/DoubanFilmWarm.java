package com.rollingStones.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: mengya
 * @Date: 2020-09-27 17:48
 */
public class DoubanFilmWarm {

    public static void getMyDoubanFileList() throws IOException {
        Pattern anyNum = Pattern.compile(".*\\d+.*");
        Pattern notNum = Pattern.compile("[^0-9]");

        HSSFWorkbook wkb = new HSSFWorkbook();
        HSSFSheet sheet = wkb.createSheet("豆瓣电影");

        HSSFRow row2 = sheet.createRow(0);
        row2.createCell(0).setCellValue("film_id");
        row2.createCell(1).setCellValue("film_link");
        row2.createCell(2).setCellValue("film_name");
        row2.createCell(3).setCellValue("other_name");
        row2.createCell(4).setCellValue("pic");
        row2.createCell(5).setCellValue("intro");
        row2.createCell(6).setCellValue("on_time");
        row2.createCell(7).setCellValue("area");
        row2.createCell(8).setCellValue("watch_time");
        row2.createCell(9).setCellValue("tags");
        row2.createCell(10).setCellValue("comments");

        int index = 0;
        for(int i = 0; i<82; i++){
            String url = "https://movie.douban.com/people/solodongdong/collect?start="+index+"&sort=time&rating=all&filter=all&mode=grid";

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
                        .header("Host", "movie.douban.com")
                        .header("Referer","https://www.douban.com/people/solodongdong/")
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
                String filmId = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("a").attr("href");
                String titleTemp = eles.get(y).getElementsByAttributeValue("class","title").text();
                String title = titleTemp.split("/")[0].replaceAll("\\[可播放\\]","");
                String otherName = null;
                if(titleTemp.contains("/")){
                    otherName = titleTemp.substring(titleTemp.indexOf("/")).replaceAll("\\[可播放\\]","").replaceFirst("/ ","");
                }else{
                    otherName = titleTemp.replaceAll("\\[可播放\\]","").replaceFirst("/ ","");
                }

                String pic = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("img").attr("src");
                String yearTemp = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[0];
                String year = "-";
                if(anyNum.matcher(yearTemp).matches()){
                    year = yearTemp.split("\\(")[0];
                }

                String areaTemp = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[0];
                String area = "-";
                if(areaTemp.contains("(") && areaTemp.split("\\(").length>1){
                    area = areaTemp.split("\\(")[1].replaceAll("\\)","");
                }

                String watchTime = eles.get(y).getElementsByAttributeValue("class","date").text();
                String tags = eles.get(y).getElementsByAttributeValue("class","tags").text().replaceAll("标签: ","");
                String comments = eles.get(y).getElementsByAttributeValue("class","comment").text();

                System.out.println(notNum.matcher(filmId).replaceAll(""));
                System.out.println(title);
                System.out.println(otherName);
                System.out.println(pic);
                System.out.println(eles.get(y).getElementsByAttributeValue("class","intro").text());
                System.out.println(year);
                if(area.contains("(") && area.split("\\(").length>1){
                    System.out.println(area.split("\\(")[1].replaceAll("\\)",""));
                }
                System.out.println(watchTime);
                System.out.println(tags);
                System.out.println(comments);

                //生成excel
                HSSFRow row3 = sheet.createRow((y + 1)+index);
                row3.createCell(0).setCellValue(notNum.matcher(filmId).replaceAll(""));
                row3.createCell(1).setCellValue(filmId);
                row3.createCell(2).setCellValue(title);
                row3.createCell(3).setCellValue(otherName);
                row3.createCell(4).setCellValue(pic);
                row3.createCell(5).setCellValue(eles.get(y).getElementsByAttributeValue("class","intro").text());
                row3.createCell(6).setCellValue(year);
                if(area.contains("(") && area.split("\\(").length>1){
                    row3.createCell(7).setCellValue(area.split("\\(")[1].replaceAll("\\)",""));
                }else{
                    row3.createCell(7).setCellValue("-");
                }

                row3.createCell(8).setCellValue(watchTime);
                if(StringUtils.isNotEmpty(tags)){
                    row3.createCell(9).setCellValue(tags);
                }else{
                    row3.createCell(9).setCellValue("-");
                }

                if(StringUtils.isNotEmpty(comments)){
                    row3.createCell(10).setCellValue(comments);
                }else{
                    row3.createCell(10).setCellValue("-");
                }

                //下载图片
//                HttpClientUtils.downloadNetPic(pic, watchTime+"-"+title.replace(":","：").split("/")[0]+"("+year+")"+"("+area+")"+".jpg", "E://MyDoubanFilmPic");

            }

            index+=15;
        }

        wkb.write(new File("d://doubanFilm.xls"));
        System.out.println("creating file success!");

    }

    public static void main(String[] args) throws IOException {
        getMyDoubanFileList();
    }
}
