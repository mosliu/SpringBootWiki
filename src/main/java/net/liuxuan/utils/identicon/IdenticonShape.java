package net.liuxuan.utils.identicon;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.avatar.draw.IdenticonShape
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/10 120
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/10  |    Moses       |     Created
 */
public class IdenticonShape {
    //triangle
    private static final double[] shape1 = {
            0.5, 1,
            1, 0,
            1, 1};


    // parallelogram
    private static final double[] shape2 = {
            0.5, 0,
            1, 0,
            0.5, 1,
            0, 1};

    // mouse ears
    private static final double[] shape3 = {
            0.5, 0,
            1, 0,
            1, 1,
            0.5, 1,
            1, 0.5};

    // ribbon
    private static final double[] shape4 = {
            0, 0.5,
            0.5, 0,
            1, 0.5,
            0.5, 1,
            0.5, 0.5};

    // sails
    private static final double[] shape5 = {
            0, 0.5,
            1, 0,
            1, 1,
            0, 1,
            1, 0.5};

    // fins
    private static final double[] shape6 = {
            1, 0,
            1, 1,
            0.5, 1,
            1, 0.5,
            0.5, 0.5};

    // beak
    private static final double[] shape7 = {
            0, 0,
            1, 0,
            1, 0.5,
            0, 0,
            0.5, 1,
            0, 1};

    // chevron
    private static final double[] shape8 = {
            0, 0,
            0.5, 0,
            1, 0.5,
            0.5, 1,
            0, 1,
            0.5, 0.5};

    // fish
    private static final double[] shape9 = {
            0.5, 0,
            0.5, 0.5,
            1, 0.5,
            1, 1,
            0.5, 1,
            0.5, 0.5,
            0, 0.5};

    // kite
    private static final double[] shape10 = {
            0, 0,
            1, 0,
            0.5, 0.5,
            1, 0.5,
            0.5, 1,
            0.5, 0.5,
            0, 1};

    // trough
    private static final double[] shape11 = {
            0, 0.5,
            0.5, 1,
            1, 0.5,
            0.5, 0,
            1, 0,
            1, 1,
            0, 1};

    // rays
    private static final double[] shape12 = {
            0.5, 0,
            1, 0,
            1, 1,
            0.5, 1,
            1, 0.75,
            0.5, 0.5,
            1, 0.25};

    // double rhombus
    private static final double[] shape13 = {
            0, 0.5,
            0.5, 0,
            0.5, 0.5,
            1, 0,
            1, 0.5,
            0.5, 1,
            0.5, 0.5,
            0, 1};

    // crown
    private static final double[] shape14 = {
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            1, 0.5,
            0.5, 0.25,
            0.5, 0.75,
            0, 0.5,
            0.5, 0.25};

    // radioactive
    private static final double[] shape15 = {
            0, 0.5,
            0.5, 0.5,
            0.5, 0,
            1, 0,
            0.5, 0.5,
            1, 0.5,
            0.5, 1,
            0.5, 0.5,
            0, 1};

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
            shape3, shape4, shape5, shape6, shape7, shape8, shape9, shape10,
            shape11, shape12, shape13, shape14, shape15, shape_default};

    public static double[] getsprite(int prototypeNo, double size, int style) {
        double rtn[];
        rtn = getColonedDoubles(prototypeNo);
        if (style % 2 == 1) {
            mirrorMatrix(rtn);
        }
        rotateMatrix(rtn, (style / 2) % 4 * 90); //1,2 对应0度 3,4对应90度 etc
        shiftMatrix(rtn, (style / 2) % 4 * 90);
        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = 1 - rtn[i];
            rtn[i] = rtn[i] * size;
        }
        return rtn;
    }

//    public static double[] getsprite(int csh, double size) {
//        double[] rtn;
//        rtn = getColonedDoubles(csh);
//        for (int i = 0; i < rtn.length; i++) {
//            rtn[i] = rtn[i] * size;
//        }
//        return rtn;
//    }
//
//    public static double[] getspriteInvertX(int csh, double size) {
//        double[] rtn;
//        rtn = getColonedDoubles(csh);
//        for (int i = 0; i < rtn.length; i++) {
//            if (i % 2 == 0) {
//                rtn[i] = 1 - rtn[i];
//            }
//            rtn[i] = rtn[i] * size;
//        }
//        return rtn;
//    }
//
//    public static double[] getspriteInvertY(int csh, double size) {
//        double[] rtn;
//        rtn = getColonedDoubles(csh);
//        for (int i = 0; i < rtn.length; i++) {
//            if (i % 2 == 1) {
//                rtn[i] = 1 - rtn[i];
//            }
//            rtn[i] = rtn[i] * size;
//        }
//        return rtn;
//    }
//
//    public static double[] getspriteInvertCenter(int csh, double size) {
//        double[] rtn;
//        rtn = getColonedDoubles(csh);
//        for (int i = 0; i < rtn.length; i++) {
//            rtn[i] = 1 - rtn[i];
//            rtn[i] = rtn[i] * size;
//        }
//        return rtn;
//    }

    /**
     * X轴镜像
     *
     * @param rtn
     */
    private static void mirrorMatrix(double[] rtn) {
        for (int i = 0; i < rtn.length; i++) {
            if (i % 2 == 0) {
                rtn[i] = 1 - rtn[i];
            }
        }
    }

    /**
     * 旋转矩阵
     *
     * @param rtn
     * @param degree
     */
    private static void rotateMatrix(double[] rtn, int degree) {
        double cosV = Math.cos(Math.toRadians(degree));
        double sinV = Math.sin(Math.toRadians(degree));
        for (int i = 0; i < rtn.length; i = i + 2) {
            double x = rtn[i];
            double y = rtn[i + 1];
            rtn[i] = x * cosV - y * sinV;
            rtn[i + 1] = x * sinV + y * cosV;
        }
    }

    /**
     * 平移象限
     *
     * @param degree
     */
    private static void shiftMatrix(double[] rtn, int degree) {
        degree = degree % 360;
        int x;
        int y;
        if (degree < 45) {
            x = 0;
            y = 0;
        } else if (degree < 135) {
            x = 1;
            y = 0;
        } else if (degree < 225) {
            x = 1;
            y = 1;
        } else if (degree < 315) {
            x = 0;
            y = 1;
        } else {
            x = 0;
            y = 0;
        }
        for (int i = 0; i < rtn.length; i = i + 2) {
            rtn[i] += x;
            rtn[i + 1] += y;
        }
    }


    private static double[] getColonedDoubles(int csh) {
        double[] rtn;
        if (csh < shapeTypes.length) {
            rtn = shapeTypes[csh].clone();
        } else {
            rtn = shape_default.clone();
        }
        return rtn;
    }
}
