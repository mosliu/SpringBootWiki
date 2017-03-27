package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.SliderPics;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.SliderPicsService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 13:42
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/
public interface SliderPicsService {
    void saveSliderPics(SliderPics sliderPics);

    SliderPics findSliderPicsById(Long id);

    boolean deleteSliderPicsById(Long id);

    boolean checkSliderPicsExists(String sliderPicsname);

    List<SliderPics> getAllSliderPics();

}