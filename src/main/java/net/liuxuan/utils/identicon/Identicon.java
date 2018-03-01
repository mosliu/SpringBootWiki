package net.liuxuan.utils.identicon;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;



/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.avatar.draw.Identicon
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/10 15:15
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/10  |    Moses       |     Created
 */
public class Identicon {
    //MD5 hash
    private byte[] hash;
    //image imageWidth
    private int imageWidth;

    private Font charfont;

    /**
     * forbidden null init;
     */
    private Identicon() {
    }

    /**
     * Constructor
     * @param hash The md5 encode of something
     * @param imageWidth the image Width
     */
    public Identicon(byte[] hash, int imageWidth) {
        if (hash.length < 16) {
            System.out.println(hash.length);
            hash = DrawUtils.md5(hash.toString());

        }
        this.hash = hash;
        this.imageWidth = imageWidth;
        this.charfont = new Font("黑体", Font.BOLD, imageWidth * 2 / 9);
    }

    /**
     * create the image.
     * @param centerChar the char to show in the middle of the image
     * @return BufferedImage.
     */
    public BufferedImage create(String centerChar) {
        int cornerShapeNo = DrawUtils.gotInt(hash, 0, 1) & 0x0f;       // corner sprite shape
        int sideShapeNo = (DrawUtils.gotInt(hash, 0, 1) & 0xf0) >> 4;       // side sprite shape
        int centerShapeNo = DrawUtils.gotInt(hash, 1, 1) & 0x07;   // center sprite shape


        int cornerShapeStyle = DrawUtils.gotInt(hash, 2, 1) & 0x07; // corner sprite rotation
        int sideShapeStyle = (DrawUtils.gotInt(hash, 2, 1) & 0x70) >> 4; // side sprite rotation

//        cornerShapeNo = 0;       // corner sprite shape
//        sideShapeNo = 7;
//        centerShapeNo = 1;
//        cornerShapeStyle=2;
//        sideShapeStyle =2;
//        System.out.println(gotInt(hash, 0, 1) + ":" + gotInt(hash, 1, 1) + ":" + gotInt(hash, 2, 1));
//        System.out.println(cornerShapeNo + ":" + sideShapeNo + ":" + centerShapeNo+":"+cornerShapeStyle+":"+sideShapeStyle);

        /* corner sprite foreground color */
        Color cornerFillColor = new Color(hash[4] & 0xFF, hash[5] & 0xFF, hash[6] & 0xFF);
        Color cornerStrokeColor = new Color(hash[4] & 0xFF, hash[5] & 0xFF, hash[6] & 0xFF);
        Color sideFillColor = new Color(hash[7] & 0xFF, hash[8] & 0xFF, hash[9] & 0xFF);
        Color sideStrokeColor = new Color(hash[7] & 0xFF, hash[8] & 0xFF, hash[9] & 0xFF);
        Color centerFillColor = new Color(hash[10] & 0xFF, hash[11] & 0xFF, hash[12] & 0xFF);
        Color centerStrokeColor = new Color(hash[10] & 0xFF, hash[11] & 0xFF, hash[12] & 0xFF);
        Color fontColor = new Color(hash[13] & 0xFF, hash[14] & 0xFF, hash[15] & 0xFF);

        /* size of each sprite */
        double size = imageWidth / 3;

        BufferedImage targetImage = new BufferedImage(imageWidth, imageWidth, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = targetImage.createGraphics();
        g.setFont(charfont);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageWidth);




        /* start with blank 3x3 identicon */
        /* generate corner sprites */
        double[] corner;
        corner = IdenticonShape.getsprite(cornerShapeNo, size, cornerShapeStyle);

        GeneralPath polygon = generateShape(corner);
        //NorthWest
        drawRotatedPolygon(g, polygon, 0, 0, 0, size, cornerFillColor, cornerStrokeColor);
//        NorthEast
        drawRotatedPolygon(g, polygon, imageWidth, 0, 90, size, cornerFillColor, cornerStrokeColor);

        //SouthWest
        drawRotatedPolygon(g, polygon, imageWidth, imageWidth, 180, size, cornerFillColor, cornerStrokeColor);
        //SouthEast
        drawRotatedPolygon(g, polygon, 0, imageWidth, 270, size, cornerFillColor, cornerStrokeColor);


        /* draw sides */
        double[] side = IdenticonShape.getsprite(sideShapeNo, size, sideShapeStyle);
        GeneralPath polygon_side = generateShape(side);
        //West
        drawRotatedPolygon(g, polygon_side, 0, size, 0, size, sideFillColor, sideStrokeColor);
        //North
        drawRotatedPolygon(g, polygon_side, 2 * size, 0, 90, size, sideFillColor, sideStrokeColor);
        //East
        drawRotatedPolygon(g, polygon_side, imageWidth, 2 * size, 180, size, sideFillColor, sideStrokeColor);
        //South
        drawRotatedPolygon(g, polygon_side, size, imageWidth, 270, size, sideFillColor, sideStrokeColor);

        double[] center = IdenticonCenterShape.getcenter(centerShapeNo, size);

        GeneralPath polygon_center = generateShape(center);
        drawRotatedPolygon(g, polygon_center, size, size, 0, size, centerFillColor, centerStrokeColor);


        if (centerChar != null) {


            FontMetrics fm = g.getFontMetrics();
            Rectangle2D strBounds = fm.getStringBounds(centerChar, g);
            g.setColor(fontColor);
            double fontx = (imageWidth - strBounds.getWidth()) / 2;
            double fonty = (imageWidth - strBounds.getHeight()) / 2 + fm.getAscent();
//        double fonty = imageWidth/2+strBounds.getHeight();
            g.drawString(centerChar, (float) fontx, (float) fonty);
        }


        g.dispose();
        return targetImage;
    }


    private void drawRotatedPolygon(Graphics2D g, GeneralPath polygon, double x, double y,
                                    double angle, double blocksize, Color fillColor, Color strokeColor) {
//        double offset = blocksize / 2.0d;
//        AffineTransform savet = g.getTransform();
//        g.translate(x + offset, y + offset);
        g.translate(x, y);
        g.scale(1, 1);
        g.rotate(Math.toRadians(angle));
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.draw(polygon);
        }
        g.setColor(fillColor);
        g.fill(polygon);
        g.rotate(Math.toRadians(0 - angle));
        g.translate(0 - x, 0 - y);

    }

    private GeneralPath generateShape(double[] shape) {
        GeneralPath polygon;
        polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, shape.length / 2);
        polygon.moveTo(shape[0], shape[1]);
        for (int i = 2; i < shape.length; i = i + 2) {
            polygon.lineTo(shape[i], shape[i + 1]);
        }
        polygon.closePath();
        return polygon;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Font getCharfont() {
        return charfont;
    }

    public void setCharfont(Font charfont) {
        this.charfont = charfont;
    }
}
