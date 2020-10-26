package com.rollingStones.utils;

import com.google.common.collect.Maps;
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
 * @Date: 2020-10-26 10:32
 */
public class DoubanBookWarm {

    public static void getMyDoubanBookList() throws IOException {
        Pattern notNum = Pattern.compile("[^0-9]");

        HSSFWorkbook wkb = new HSSFWorkbook();
        HSSFSheet sheet = wkb.createSheet("豆瓣读书");

        HSSFRow row2 = sheet.createRow(0);
        row2.createCell(0).setCellValue("book_id");
        row2.createCell(1).setCellValue("book_link");
        row2.createCell(2).setCellValue("book_name");
        row2.createCell(3).setCellValue("book_info");
        row2.createCell(4).setCellValue("book_author");
        row2.createCell(5).setCellValue("book_pub");
        row2.createCell(6).setCellValue("book_date");
        row2.createCell(7).setCellValue("book_price");
        row2.createCell(8).setCellValue("pic");
        row2.createCell(9).setCellValue("watch_time");
        row2.createCell(10).setCellValue("tags");
        row2.createCell(11).setCellValue("comments");

        int index = 0;
        for(int i = 0; i<14; i++) {
            String url = "https://book.douban.com/people/50733160/collect?start=" + index + "&sort=time&rating=all&filter=all&mode=grid";

            Document doc = null;
            try {
                Map<String,String> cookieMap = Maps.newHashMap();
                cookieMap.put("__utma","81379588.121653159.1603679927.1603679927.1603679927.1");
                cookieMap.put("__utmb","81379588.4.10.1603679927");
                cookieMap.put("__utmc","81379588");
                cookieMap.put("__utmt","1");
                cookieMap.put("__utmz","81379588.1603679927.1.1.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/passport/login");
                cookieMap.put("ap_v","0,6.0");
                cookieMap.put("bid","26MZzfL_NIQ");
                cookieMap.put("ck","BDhD");
                cookieMap.put("ct","y");
                doc = Jsoup.connect(url).cookies(cookieMap).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements eles = doc.getElementsByAttributeValue("class", "subject-item");

            for(int y = 0;y<eles.size();y++) {
                String bookFref = eles.get(y).getElementsByAttributeValue("class", "pic").first().getElementsByTag("a").attr("href");
                String title = eles.get(y).getElementsByAttributeValue("class", "info").first().getElementsByTag("a").attr("title");
                String pub = eles.get(y).getElementsByAttributeValue("class", "pub").text();
                String pic = eles.get(y).getElementsByAttributeValue("class","pic").first().getElementsByTag("img").attr("src");
                String date = eles.get(y).getElementsByAttributeValue("class", "date").text().replace(" 读过","");
                String tags = eles.get(y).getElementsByAttributeValue("class", "tags").text();
                String comment = eles.get(y).getElementsByAttributeValue("class", "comment").text();

                String[] pubArr = pub.split("/");

                System.out.println(notNum.matcher(bookFref).replaceAll(""));
                System.out.println(bookFref);
                System.out.println(title);
                System.out.println(pub);
                if(pubArr.length>3){
                    System.out.println(pubArr[0].trim());
                    System.out.println(pubArr[1].trim());
                    System.out.println(pubArr[2].trim());
                    System.out.println(pubArr[3].trim());
                }
                System.out.println(pic);
                System.out.println(date);
                System.out.println(tags);
                System.out.println(comment);


                //生成excel
                HSSFRow row3 = sheet.createRow((y + 1)+index);
                row3.createCell(0).setCellValue(notNum.matcher(bookFref).replaceAll(""));
                row3.createCell(1).setCellValue(bookFref);
                row3.createCell(2).setCellValue(title);
                row3.createCell(3).setCellValue(pub);
                if(pubArr.length == 3){
                    row3.createCell(6).setCellValue(pubArr[pubArr.length-2].trim());
                    row3.createCell(7).setCellValue(pubArr[pubArr.length-1].trim());
                }
                if(pubArr.length == 4){
                    row3.createCell(4).setCellValue(pubArr[0].trim());
                    row3.createCell(5).setCellValue(pubArr[1].trim());
                    row3.createCell(6).setCellValue(pubArr[2].trim());
                    row3.createCell(7).setCellValue(pubArr[3].trim());
                }
                if(pubArr.length == 5){
                    row3.createCell(4).setCellValue(pubArr[0].trim()+"/"+pubArr[1].trim());
                    row3.createCell(5).setCellValue(pubArr[2].trim());
                    row3.createCell(6).setCellValue(pubArr[3].trim());
                    row3.createCell(7).setCellValue(pubArr[4].trim());
                }
                if(pubArr.length == 6){
                    row3.createCell(4).setCellValue(pubArr[0].trim()+"/"+pubArr[1].trim()+"/"+pubArr[2].trim());
                    row3.createCell(5).setCellValue(pubArr[3].trim());
                    row3.createCell(6).setCellValue(pubArr[4].trim());
                    row3.createCell(7).setCellValue(pubArr[5].trim());
                }
                row3.createCell(8).setCellValue(pic);
                row3.createCell(9).setCellValue(date);
                row3.createCell(10).setCellValue(tags);
                row3.createCell(11).setCellValue(comment);

            }

            index += 15;
        }

        wkb.write(new File("d://doubanBook.xls"));
        System.out.println("creating file success!");
    }

    public static void main(String[] args) throws IOException {
        getMyDoubanBookList();
    }
}
