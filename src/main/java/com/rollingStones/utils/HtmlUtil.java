package com.rollingStones.utils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 删除html标签
 *
 */
public class HtmlUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
    /**
     * 返回html长度， 英文返回字母数的一半
     * @param htmlStr
     * @param   isDelHtml
     * @return
     */
    public static  int  htmlLength(String htmlStr,boolean isDelHtml){
        if(isDelHtml){
            htmlStr = delHTMLTag(htmlStr);
        }
        if(htmlStr.length() < stringLength(htmlStr)){
            return  htmlStr.length();
        }
        else  if(htmlStr.length() >= stringLength(htmlStr))
           return  htmlStr.length() / 2;

        return -1;
    }
    /**
     * 截断html字符串，如果是英文按照length自动*2字母个数，如果是汉字按照汉字个数截断。并删除html标签
     * @param htmlStr   原来数据
     * @param length    汉字或字母个数
     * @param isDelHtml   是否删除html标签
     * @return  返回截断后的数据
     */
    public static String htmlSplit(String htmlStr,int length,boolean isDelHtml){
        if(isDelHtml){
            htmlStr = delHTMLTag2(htmlStr);
        }
        if(htmlStr.length()<length){
        	length = htmlStr.length();
        }

        if(htmlStr.length() < stringLength(htmlStr)){
            return   htmlStr.substring(0,length);
        }
        if(htmlStr.length() >= stringLength(htmlStr))
            length *=2;
        String[] rets = htmlStr.split("\\s|&nbsp;");
        StringBuffer ret = new StringBuffer();

        for(int i=0;i<rets.length;i++){
            if(stringLength(ret.toString()) >= length)
                break;

            if(rets[i].equals("")) continue;
            ret.append(" ");
            ret.append(rets[i].trim());
        }
        return ret.toString();
    }
    public static int stringLength(String str){
        String anotherString = null;
        try {
            anotherString = new String(str.getBytes("GBK"), "ISO8859_1");
        }
        catch (UnsupportedEncodingException ex) {
        }
        return      anotherString.length();
    }

    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String delAppointHTMLTag(String str,String tag){
    	 String regex="<"+tag+"[^>]*?>([\\s\\S]*?)<\\/"+tag+">";
         Pattern p =Pattern.compile(regex);
         if(str==null){
        	 str="";
         }
         Matcher m=p.matcher(str);
         while(m.find()){
        	 str = m.group(1);
         }
         return str;
    }
    
    /**
     * 根据带标签的字符串输出数组
     * @param htmlStr 带标签的字符串
     * @param tag 按标签分组
     * @param index 输出的角标
     * @return
     */
    public static String showMainContent(String htmlStr,String tag,int index){
    	String[] arr1 = htmlStr.split(tag);//</li>
    	List<String> strList = new ArrayList<String>();
		for(String a: arr1){
			strList.add(delHTMLTag(a));
		}
		
		if(!strList.isEmpty()&&strList.size()-1>=index){
			return strList.get(index);
		}else{
			return " ";
		}
		
    }
    
    /**
     * 删除标签 保留空格
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag2(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        
        Pattern p_space = Pattern.compile("\\t", Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  

        return htmlStr.trim(); // 返回文本字符串
    }
    
}
