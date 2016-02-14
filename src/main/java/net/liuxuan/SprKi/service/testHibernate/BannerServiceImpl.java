/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.service.testHibernate;

import net.liuxuan.SprKi.entity.Banner;
import net.liuxuan.SprKi.repository.BannerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by Moses on 2016/2/5.
 */
@Service
public class BannerServiceImpl implements BannerService {
    //在java代码中使用@Autowired或@Resource注解方式进行装配，这两个注解的区别是：
    //@Autowired 默认按类型装配，@Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
    @Resource
    private BannerRepository bannerRepository; // inject the bean named 'bannerRepository'

    @Override
    @Transactional
    public void saveBanner(Banner banner) {
        bannerRepository.save(banner);
    }



    @Override
    public Banner findBannerById(Long id) {
        return bannerRepository.getOne(id);
    }

    @Override
    @Transactional
    public void updateBanner(String bannerName, Long id) {
        bannerRepository.update(bannerName, id);
    }
}
