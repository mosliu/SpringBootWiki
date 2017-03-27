package net.liuxuan.SprKi.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.SliderPicsRepository;
import net.liuxuan.SprKi.entity.SliderPics;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.SliderPicsServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 13:42
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/
@Service
@Transactional
public class SliderPicsServiceImpl implements SliderPicsService{

    private static Logger log = LoggerFactory.getLogger(SliderPicsServiceImpl.class);

    @Autowired
    SliderPicsRepository sliderPicsRepository;

    @Override
    public void saveSliderPics(SliderPics sliderPics){
        sliderPicsRepository.save(sliderPics);
    }

    @Override
    public SliderPics findSliderPicsById(Long id){
        SliderPics sliderPics = sliderPicsRepository.findOne(id);
        return sliderPics;
    }

    @Override
    public boolean deleteSliderPicsById(Long id){
//        SliderPics sliderPics = sliderPicsRepository.getOne(id);
//        if (sliderPics != null) {
//            sliderPics.setDisabled(true);
//            return true;
//        }
        sliderPicsRepository.delete(id);
        return true;
    }

    @Override
    public boolean checkSliderPicsExists(String sliderPicsname){
        List<SliderPics> list = sliderPicsRepository.findBySliderPicsName(sliderPicsname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<SliderPics> getAllSliderPics() {
        return sliderPicsRepository.findByDisabledFalse();
    }

}