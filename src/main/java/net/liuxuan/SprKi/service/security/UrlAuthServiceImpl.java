package net.liuxuan.SprKi.service.security;

import java.util.List;

import net.liuxuan.SprKi.repository.security.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.security.UrlAuthRepository;
import net.liuxuan.SprKi.entity.security.UrlAuth;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.security.UrlAuthServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/22 14:41
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-22  |    Moses        |     Created
*/
@Service
@Transactional
public class UrlAuthServiceImpl implements UrlAuthService{

    private static Logger log = LoggerFactory.getLogger(UrlAuthServiceImpl.class);

    @Autowired
    UrlAuthRepository urlAuthRepository;

    @Override
    public void saveUrlAuth(UrlAuth urlAuth){
        urlAuthRepository.save(urlAuth);
    }

    @Override
    public UrlAuth findUrlAuthById(Long id){
        UrlAuth urlAuth = urlAuthRepository.findOne(id);
        return urlAuth;
    }

    @Override
    public boolean deleteUrlAuthById(Long id){
        UrlAuth urlAuth = urlAuthRepository.getOne(id);
        urlAuth.getRoles().forEach(role -> {
            role.getUrlAuths().remove(urlAuth);
        });
        if (urlAuth != null) {
            urlAuthRepository.delete(urlAuth);
//            urlAuth.setDisabled(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUrlAuthExists(String urlAuthname){
        List<UrlAuth> list = urlAuthRepository.findByUrlAuthName(urlAuthname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<UrlAuth> getAllUrlAuth() {
        return urlAuthRepository.findByDisabledFalse();
    }

}