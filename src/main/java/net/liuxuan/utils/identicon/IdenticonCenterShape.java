package net.liuxuan.utils.identicon;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.avatar.draw.IdenticonCenterShape
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/10 16:35
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/10  |    Moses       |     Created
 */
public class IdenticonCenterShape {
    // empty
    private static final double[] shape1 = {
            0,0,
            0,0
    };

    // fill
    private static final double[] shape2 = {
            0, 0,
            1, 0,
            1, 1,
            0, 1};

    // diamond
    private static final double[] shape3 = {
            0.5, 0,
            1, 0.5,
            0.5, 1,
            0, 0.5};

    // reverse diamond
    private static final double[] shape4 = {
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0.5,
            0.5, 1,
            1, 0.5,
            0.5, 0,
            0, 0.5};

    // cross
    private static final double[] shape5 = {
            0.25, 0,
            0.75, 0,
            0.5, 0.5,
            1, 0.25,
            1, 0.75,
            0.5, 0.5,
            0.75, 1,
            0.25, 1,
            0.5, 0.5,
            0, 0.75,
            0, 0.25,
            0.5, 0.5};

    // morning star
    private static final double[] shape6 = {
            0, 0,
            0.5, 0.25,
            1, 0,
            0.75, 0.5,
            1, 1,
            0.5, 0.75,
            0, 1,
            0.25, 0.5};

    // small square
    private static final double[] shape7 = {
            0.33, 0.33,
            0.67, 0.33,
            0.67, 0.67,
            0.33, 0.67};

    // checkerboard
    private static final double[] shape8 = {
            0, 0,
            0.33, 0,
            0.33, 0.33,
            0.66, 0.33,
            0.67, 0,
            1, 0,
            1, 0.33,
            0.67, 0.33,
            0.67, 0.67,
            1, 0.67,
            1, 1,
            0.67, 1,
            0.67, 0.67,
            0.33, 0.67,
            0.33, 1,
            0, 1,
            0, 0.67,
            0.33, 0.67,
            0.33, 0.33,
            0, 0.33};

    // tiles
    private static final double[] shape_default = {
            0, 0,
            1, 0,
            0.5, 0.5,
            0.5, 0,
            0, 0.5,
            1, 0.5,
            0.5, 1,
            0.5, 0.5,
            0, 1};
    private static final double[] shapeTypes[] = {shape1, shape2,
            shape3, shape4, shape5, shape6, shape7, shape8, shape_default};

    public static double[] getcenter(int csh, double size) {
        double[] rtn;
        if (csh < shapeTypes.length) {
            rtn = shapeTypes[csh].clone();
        } else {
            rtn = shape_default.clone();
        }
        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = rtn[i] * size;
        }
        return rtn;
    }


}
