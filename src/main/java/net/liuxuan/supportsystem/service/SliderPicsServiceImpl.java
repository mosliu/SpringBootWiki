package net.liuxuan.supportsystem.service;

import net.liuxuan.supportsystem.entity.SliderPics;
import net.liuxuan.supportsystem.repository.SliderPicsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private SliderPicsRepository sliderPicsRepository;

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "sliderPics",key = "#sliderPics.id")
            },
            evict = {
                    @CacheEvict(cacheNames = "sliderPics",key = "'sliderPics_list'")
            }
    )
    public void saveSliderPics(SliderPics sliderPics){
        sliderPicsRepository.save(sliderPics);
    }

    @Override
    @Cacheable(cacheNames = "sliderPics",key = "#id")
    public SliderPics findSliderPicsById(Long id){
        SliderPics sliderPics = sliderPicsRepository.findOne(id);
        return sliderPics;
    }

    @Override

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "sliderPics",key = "#id"),
                    @CacheEvict(cacheNames = "sliderPics",key = "sliderPics_list")
            }
    )
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
        List<SliderPics> list = getSliderPicsByName(sliderPicsname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    @Cacheable(cacheNames = "sliderPics",key = "#projectProgressname")
    public List<SliderPics> getSliderPicsByName(String sliderPicsname) {
        return sliderPicsRepository.findBySliderPicsName(sliderPicsname);
    }

    @Override
    @Cacheable(cacheNames = "sliderPics",key = "'sliderPics_list'")
    public List<SliderPics> getAllSliderPics() {
        return sliderPicsRepository.findByDisabledFalse();
    }

}