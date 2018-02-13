package net.liuxuan.supportsystem.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.supportsystem.repository.CMSVideoRepository;
import net.liuxuan.supportsystem.entity.CMSVideo;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.CMSVideoServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/09/29 13:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-09-29  |    Moses        |     Created
*/
@Service
@Transactional
public class CMSVideoServiceImpl implements CMSVideoService{

    private static Logger log = LoggerFactory.getLogger(CMSVideoServiceImpl.class);

    @Autowired
    CMSVideoRepository cmsVideoRepository;

    @Override
    public void saveCMSVideo(CMSVideo cMSVideo){
        if(cMSVideo.getCmsVideoFileType()==null||cMSVideo.getCmsVideoFileType().equals("")){
            String filepath = cMSVideo.getCmsVideoFilepath();
            int k = filepath.lastIndexOf(".");
            if(k>0){
                k=k+1;
                String type = filepath.substring(k);
                cMSVideo.setCmsVideoFileType(type);
            }else{
                cMSVideo.setCmsVideoFileType("flv");
            }

        }
        cmsVideoRepository.save(cMSVideo);
    }

    @Override
    public CMSVideo findCMSVideoById(Long id){
        CMSVideo cMSVideo = cmsVideoRepository.findOne(id);
        return cMSVideo;
    }

    @Override
    public boolean deleteCMSVideoById(Long id){
        if(id!=null) {
            cmsVideoRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCMSVideoExists(String cmsVideoname){
        List<CMSVideo> list = getCMSVideoByName(cmsVideoname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CMSVideo> getCMSVideoByName(String cmsVideoname) {
        return cmsVideoRepository.findByCmsVideoName(cmsVideoname);
    }

    @Override
    public List<CMSVideo> getAllCMSVideo() {
        return cmsVideoRepository.findAll();
    }

}