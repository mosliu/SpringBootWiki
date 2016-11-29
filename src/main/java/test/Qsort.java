package test;

import java.util.Arrays;
import java.util.Random;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.Qsort
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/10/9 14:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/10/9  |    Moses       |     Created
 */
public class Qsort {
    private static Random rand = new Random();
    private static int RandomCount = 10000000;


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int[] data = genData(RandomCount);
        time = System.currentTimeMillis() - time;
        System.out.println("生成数量:" + RandomCount);
        System.out.println("生成时间:" + time + "ms");
        int[] data2Parse = data.clone();
        time = System.currentTimeMillis();
        QuickSort(data2Parse,0,data2Parse.length-1);
        time = System.currentTimeMillis() - time;
        System.out.println("快速排序时间:" + time + "ms");
        System.out.println("快速排序检查:" + checkData(data2Parse) + "");

        data2Parse = data.clone();
        time = System.currentTimeMillis();
        QuickSort2(data2Parse,0,data2Parse.length-1);
        time = System.currentTimeMillis() - time;
        System.out.println("快速排序2时间:" + time + "ms");
        System.out.println("快速排序2检查:" + checkData(data2Parse) + "");


        data2Parse = data.clone();
        time = System.currentTimeMillis();
        Arrays.sort(data2Parse);
        time = System.currentTimeMillis() - time;
        System.out.println("系统排序时间:" + time + "ms");



    }

    public static int[] genData(int num) {
        int[] rtn = new int[num];
        for (int i = 0; i < num; i++) {
            rtn[i] = rand.nextInt();
        }
        return rtn;
    }

    public static boolean checkData(int[] data) {
        for (int i = 1; i < data.length; i++) {
            if(data[i-1]>data[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * 快速排序函数。
     * @param data 数据
     * @param start 起点
     * @param end 终点
     * @return 结束位置
     */
    public static void QuickSort(int[] data,int start, int end) {
//        System.out.println("SORT BEGIN start:"+start+",end:"+end);
        if(start<end){
            int startP = start;
            int endP = end;
            int x = data[startP];

            while(startP<endP){
                //从后向前查找 小于x的值，放到startP位置
                while(startP<endP&&data[endP]>=x){
                    endP--;
                }
                if(startP<endP){
                    data[startP] =data[endP];
                    startP++;
                }
                //从前向后查找 大于x的值，放到endP位置
                while(startP<endP&&data[startP]<x){
                    startP++;
                }
                if(startP<endP){
                    data[endP] = data[startP];
                    endP--;
                }
            }
            //结束时，把x放到最后的位置
            data[startP]=x;

            QuickSort(data,start,startP);
            QuickSort(data,startP+1,end);
        }
//        System.out.println("SORT END start:"+start+",end:"+end);


    }

    /**
     * 快速排序函数。
     * @param data 数据
     * @param start 起点
     * @param end 终点
     * @return 结束位置
     */
    public static void QuickSort2(int[] data,int start, int end) {
//        System.out.println("SORT BEGIN start:"+start+",end:"+end);
        if(start<end){
            int startP = start;
            int endP = end;
            int x = data[startP];

            while(startP<endP){
                //从后向前查找 小于x的值，放到startP位置
                while(startP<endP&&data[endP]>=x){
                    endP--;
                }
                if(startP<endP){
                    data[startP++] =data[endP];
                }
                //从前向后查找 大于x的值，放到endP位置
                while(startP<endP&&data[startP]<x){
                    startP++;
                }
                if(startP<endP){
                    data[endP--] = data[startP];
                }
            }
            //结束时，把x放到最后的位置
            data[startP]=x;

            QuickSort(data,start,startP);
            QuickSort(data,startP+1,end);
        }
//        System.out.println("SORT END start:"+start+",end:"+end);


    }
}
