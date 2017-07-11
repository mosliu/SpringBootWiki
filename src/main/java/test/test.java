package test;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.test
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/8 10:57
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/8  |    Moses       |     Created
 */
public class test {
//    int arr[] = new int[10];
//    int count =0;
    public static void main(String[] args) {
//        System.out.println(String.class.isInstance(String.class));
        int arr[] = new int[10];
        test t = new test();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
//        Do(arr);
        solve(arr);

    }

    public static void Do(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j1 = i + 1; j1 < arr.length; j1++) {
                for (int j2 = j1 + 1; j2 < arr.length; j2++) {
                    for (int j3 = j2 + 1; j3 < arr.length; j3++) {
                        for (int j4 = j3 + 1; j4 < arr.length; j4++) {
                            count++;
                            System.out.println(arr[i] + " " + arr[j1] + " " + arr[j2] + " " + arr[j3] + " " + arr[j4]);

                        }
                    }
                }
            }
        }
        System.out.println(count);
    }

    public static int dfs(int[] arr,int count,int x,int deep,String before){
        for(int i=x+1;i<arr.length;i++){
            if(deep==4){
                count++;
                System.out.println(before + arr[i]);
            }else{
                count = dfs(arr,count,i,deep+1,before+arr[i]+" ");
            }
        }
        return count;
    }
    public static void solve(int[] arr){
        int count = dfs(arr,0,-1,0,"");
        System.out.println(count);
    }


}
