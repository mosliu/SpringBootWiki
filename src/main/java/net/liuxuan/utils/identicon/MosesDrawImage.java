package net.liuxuan.utils.identicon;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.avatar.MosesDrawImage
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/10 15:08
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/10  |    Moses       |     Created
 */
public class MosesDrawImage {


    public static void main(String[] args) throws IOException {
        byte[] md5 = DrawUtils.md5("mosliu@liuxuan.net");


        RenderedImage image = new Identicon(md5,128).create("M");
        ImageIO.write(image, "PNG", new File("test.png"));

        md5 = DrawUtils.md5("松松");
        image = new Identicon(md5,128).create(null);
        ImageIO.write(image, "PNG", new File("test1.png"));
    }



}
