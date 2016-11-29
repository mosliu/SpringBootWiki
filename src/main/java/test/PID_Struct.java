package test;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.PID
 * 功能:   存算法中要用到的各种数据
 * 版本:	@version 1.0
 * 编制日期: 2016/9/28 8:37
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/9/28  |    Moses       |     Created
 */
public class PID_Struct {
    long liEkVal[] = new long[3];          //差值保存，给定和反馈的差值
    char uEkFlag[] = new char[3];          //符号，1则对应的为负数，0为对应的为正数
    char uKP_Coe;             //比例系数
    char uKI_Coe;             //积分常数
    char uKD_Coe;             //微分常数
    int iPriVal;             //上一时刻值
    int iSetVal;             //设定值
    private int iCurVal;             //实际值
    double CV;
    public int getiCurVal(){
        return (int)Math.round(CV);
    }
    public void setiCurVal(int cv){
        iCurVal = cv;
        CV=cv;
    }

}
