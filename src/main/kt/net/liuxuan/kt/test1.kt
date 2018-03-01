package net.liuxuan.kt


/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 ****************************************************************************
 * 源文件名:  net.liuxuan.kt.test1
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/19 11:39
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 *  2017/5/19  |    Moses       |     Created
 */
class test1(val name: String) {

    fun greet() {
        println("Hello, ${name}");
    }
}

fun main(args: Array<String>) {
    //test1(args[0]).greet();
    test1("874").greet();
    val a:Int
    val b:Int
    var c=3123
}