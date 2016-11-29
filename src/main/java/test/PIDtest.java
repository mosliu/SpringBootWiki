package test;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.PIDtest
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/9/28 8:59
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/9/28  |    Moses       |     Created
 */
public class PIDtest {
    Random rand = new Random();
    static int iTemp;
    static char uCounter;
    PID_Struct PID = new PID_Struct();
    boolean g_bPIDRunFlag = false;
    boolean ControlOut_disable = true; //true-->不加热


    /* ********************************************************
/* 函数名称：PID_Operation()
/* 函数功能：PID运算
/* 入口参数：无（隐形输入，系数、设定值等）
/* 出口参数：无（隐形输出，U(k)）
/* 函数说明：U(k)+KP*[E(k)-E(k-1)]+KI*E(k)+KD*[E(k)-2E(k-1)+E(k-2)]
******************************************************** */
    void PID_Operation() {
        //System.out.println("PID_Operation");
        long Temp[] = {0, 0, 0};   //中间临时变量
        long PostSum = 0;     //正数和
        long NegSum = 0;      //负数和
        if (PID.iSetVal > PID.getiCurVal())                //设定值大于实际值否？
        {
            if ((PID.iSetVal - PID.getiCurVal()) > 50)      //偏差大于10否？
                PID.iPriVal = 100;                  //偏差大于10为上限幅值输出(全速加热)
            else                                    //否则慢慢来
            {
                Temp[0] = PID.iSetVal - PID.getiCurVal();    //偏差<=10,计算E(k)
                PID.uEkFlag[1] = 0;                     //E(k)为正数,因为设定值大于实际值
            /* 数值进行移位，注意顺序，否则会覆盖掉前面的数值 */
                PID.liEkVal[2] = PID.liEkVal[1];
                PID.liEkVal[1] = PID.liEkVal[0];
                PID.liEkVal[0] = Temp[0];
            /* =================================================================== */
                if (PID.liEkVal[0] > PID.liEkVal[1])              //E(k)>E(k-1)否？
                {
                    Temp[0] = PID.liEkVal[0] - PID.liEkVal[1];  //E(k)>E(k-1)
                    PID.uEkFlag[0] = 0;                         //E(k)-E(k-1)为正数
                } else {
                    Temp[0] = PID.liEkVal[1] - PID.liEkVal[0];  //E(k)<E(k-1)
                    PID.uEkFlag[0] = 1;                         //E(k)-E(k-1)为负数
                }
            /* =================================================================== */
                Temp[2] = PID.liEkVal[1] * 2;                   //2E(k-1)
                if ((PID.liEkVal[0] + PID.liEkVal[2]) > Temp[2]) //E(k-2)+E(k)>2E(k-1)否？
                {
                    Temp[2] = (PID.liEkVal[0] + PID.liEkVal[2]) - Temp[2];
                    PID.uEkFlag[2] = 0;                           //E(k-2)+E(k)-2E(k-1)为正数
                } else                                            //E(k-2)+E(k)<2E(k-1)
                {
                    Temp[2] = Temp[2] - (PID.liEkVal[0] + PID.liEkVal[2]);
                    PID.uEkFlag[2] = 1;                         //E(k-2)+E(k)-2E(k-1)为负数
                }
            /* =================================================================== */
                Temp[0] = (long) PID.uKP_Coe * Temp[0];        //KP*[E(k)-E(k-1)]
                Temp[1] = (long) PID.uKI_Coe * PID.liEkVal[0]; //KI*E(k)
                Temp[2] = (long) PID.uKD_Coe * Temp[2];        //KD*[E(k-2)+E(k)-2E(k-1)]
            /* 以下部分代码是讲所有的正数项叠加，负数项叠加 */
            /* ========= 计算KP*[E(k)-E(k-1)]的值 ========= */
                if (PID.uEkFlag[0] == 0)
                    PostSum += Temp[0];                         //正数和
                else
                    NegSum += Temp[0];                          //负数和
            /* ========= 计算KI*E(k)的值 ========= */
                if (PID.uEkFlag[1] == 0)
                    PostSum += Temp[1];                         //正数和
                else
                    ;   /* 空操作。就是因为PID.iSetVal > PID.iCurVal（即E(K)>0）才进入if的，
                    那么就没可能为负，所以打个转回去就是了 */
            /* ========= 计算KD*[E(k-2)+E(k)-2E(k-1)]的值 ========= */
                if (PID.uEkFlag[2] == 0)
                    PostSum += Temp[2];             //正数和
                else
                    NegSum += Temp[2];              //负数和
            /* ========= 计算U(k) ========= */
                PostSum += (long) PID.iPriVal;
                if (PostSum > NegSum)                 //是否控制量为正数
                {
                    Temp[0] = PostSum - NegSum;
                    if (Temp[0] < 100)               //小于上限幅值则为计算值输出
                        PID.iPriVal = (int) Temp[0];
                    else PID.iPriVal = 100;         //否则为上限幅值输出
                } else                                //控制量输出为负数，则输出0(下限幅值输出)
                    PID.iPriVal = 0;
            }
        } else PID.iPriVal = 0;                       //同上，嘿嘿
    }

    /* ********************************************************
    /* 函数名称：PID_Output()
    /* 函数功能：PID输出控制
    /* 入口参数：无（隐形输入，U(k)）
    /* 出口参数：无（控制端）
    ******************************************************** */
    void PID_Output() {

        iTemp = PID.iPriVal;
        if (iTemp == 0) {
            ControlOut_disable = true;     //不加热
        } else {
            ControlOut_disable = false;    //加热
        }
//        if (g_bPIDRunFlag)   //定时中断为100ms(0.1S)，加热周期10S(100份*0.1S)
//        {
//            g_bPIDRunFlag = false;
            if (iTemp>0) iTemp--;      //只有iTemp>0，才有必要减“1”
            uCounter++;
            if ( uCounter%1 == 0) { //10次调用一次
                PID_Operation();    //每过0.1*100S调用一次PID运算。
                uCounter = 0;
            }
//        }
    }




    Timer timer;
    public void startTimer(int delay,int time){
        timer = new Timer();
        timer.schedule(new TimerTaskTest01(), delay,time );
    }
    public static void main(String[] args) {
        PIDtest pt = new PIDtest();
        System.out.println("Preset CV:23,SV:250;timer begin....");
        pt.PID.setiCurVal(23);
        pt.PID.iSetVal=250;
        pt.PID.uKP_Coe = 20;
        pt.PID.uKI_Coe = 4;
        pt.PID.uKD_Coe = 20;
        pt.startTimer(1000,50);
    }

    public class TimerTaskTest01 extends TimerTask {

        public void run() {
            double t = ((double)(PID.iSetVal-PID.getiCurVal()))/100.0;
            t=t*rand.nextDouble()+rand.nextDouble()-0.5;
            //t+=rand.nextDouble();
            PID.CV +=t;
            //PID.iCurVal += Math.round(t);
            PID_Output();

            Date date = new Date(this.scheduledExecutionTime());
            System.out.println(t+",本次执行该线程的时间为：" + date.toLocaleString() +",CV:"+PID.getiCurVal()+",PV:"+PID.iPriVal);
        }
    }
}
