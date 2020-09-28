package com.rollingStones.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
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
            String url = "https://movie.douban.com/people/50733160/collect?start="+index+"&sort=time&rating=all&filter=all&mode=grid";

            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
            }

            Elements eles = doc.getElementsByAttributeValue("class", "item");

            for(int y = 0;y<eles.size();y++){
                String filmId = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("a").attr("href");
                String title = eles.get(y).getElementsByAttributeValue("class","title").text();
                String otherName = null;
                if(title.contains("/")){
                    otherName = title.substring(title.indexOf("/")).replaceAll("\\[可播放\\]","").replaceFirst("/ ","");
                }else{
                    otherName = title.replaceAll("\\[可播放\\]","").replaceFirst("/ ","");
                }

                String yearTemp = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[0];
                String year = "-";
                if(anyNum.matcher(yearTemp).matches()){
                    year = yearTemp.split("\\(")[0];
                }

                String area = eles.get(y).getElementsByAttributeValue("class","intro").text().split("/")[0];
                String tags = eles.get(y).getElementsByAttributeValue("class","tags").text().replaceAll("标签: ","");
                String comments = eles.get(y).getElementsByAttributeValue("class","comment").text();

                System.out.println(notNum.matcher(filmId).replaceAll(""));
                System.out.println(title.split("/")[0]);
                System.out.println(otherName);
                System.out.println(eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("img").attr("src"));
                System.out.println(eles.get(y).getElementsByAttributeValue("class","intro").text());
                System.out.println(year);
                if(area.contains("(") && area.split("\\(").length>1){
                    System.out.println(area.split("\\(")[1].replaceAll("\\)",""));
                }
                System.out.println(eles.get(y).getElementsByAttributeValue("class","date").text());
                System.out.println(tags);
                System.out.println(comments);

                //生成excel
                HSSFRow row3 = sheet.createRow((y + 1)+index);
                row3.createCell(0).setCellValue(notNum.matcher(filmId).replaceAll(""));
                row3.createCell(1).setCellValue(filmId);
                row3.createCell(2).setCellValue(title.split("/")[0]);
                row3.createCell(3).setCellValue(otherName);
                row3.createCell(4).setCellValue(eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("img").attr("src"));
                row3.createCell(5).setCellValue(eles.get(y).getElementsByAttributeValue("class","intro").text());
                row3.createCell(6).setCellValue(year);
                if(area.contains("(") && area.split("\\(").length>1){
                    row3.createCell(7).setCellValue(area.split("\\(")[1].replaceAll("\\)",""));
                }else{
                    row3.createCell(7).setCellValue("-");
                }

                row3.createCell(8).setCellValue(eles.get(y).getElementsByAttributeValue("class","date").text());
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
