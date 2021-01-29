package com.rollingStones.utils;

/**
 * @Author: mengya
 * @Date: 2021/1/22 17:12
 */
public class FuzzQuery {
    public static boolean fuzzySearch(String query, String data){
        int allowCount = 0;// 允许改动数
        int counted = 0;// 已改动数
        int searchIndex = 0;// 搜索值的指针
        int dataIndex = 0;// 待匹配数据值的指针
        boolean flag = true;// 上一值是否确认
        int i;

        // 切割搜索值获取给定的允许改动数
        if ((i=query.indexOf("~")) > -1){
            if ((i+1) < query.length()){
                allowCount=Integer.valueOf(query.substring(i+1));
            }else{
                allowCount=2;
            }
        }

        // 搜索值数组
        char[] searchArrays = query.substring(0,i).toCharArray();
        // 待匹配的数据数组
        char[] dataArrays = data.toCharArray();

        // 开始处理
        A:
        while(true){
            if (counted>allowCount){
                return false;
            }
            if (dataIndex >= dataArrays.length){
                counted+=(searchArrays.length-searchIndex);
                break;
            }

            if (flag){
                boolean match = false;
                for (int j=0;j<=(allowCount-counted);j++){
                    if ((j+searchIndex)>=searchArrays.length){
                        counted+=(dataArrays.length-dataIndex);
                        break A;
                    }
                    if (dataArrays[dataIndex]==searchArrays[j+searchIndex]){
                        if (j>1){
                            break;
                        }
                        counted+=j;
                        searchIndex+=(j+1);
                        match = true;
                        break;
                    }
                }
                if (!match){
                    counted+=1;
                    flag = false;
                }
            } else{
                boolean match = false;
                for (int j=0;j<=(allowCount-counted+1);j++){
                    if ((j+searchIndex)>=searchArrays.length){
                        counted+=(dataArrays.length-dataIndex-1);
                        break A;
                    }
                    if (dataArrays[dataIndex]==searchArrays[j+searchIndex]){
                        if (j>2){
                            break;
                        }
                        if (j>1){
                            counted+=(j-1);
                        }
                        searchIndex+=(j+1);
                        match = true;
                        flag = true;
                        break;
                    }
                }
                if (!match){
                    counted+=1;
                    flag = false;
                }
            }
            dataIndex++;
        }
        if (counted>allowCount){
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        boolean flag = fuzzySearch("ellho~2","hello");
        System.out.println(flag);
    }
}
