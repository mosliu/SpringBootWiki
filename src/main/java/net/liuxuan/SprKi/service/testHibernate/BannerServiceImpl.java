/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.service.testHibernate;

import net.liuxuan.SprKi.entity.Banner;
import net.liuxuan.SprKi.repository.BannerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Moses on 2016/2/5.
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerRepository bannerRepository;

    @Override
    public void saveBanner(Banner banner) {
        bannerRepository.save(banner);
    }



    @Override
    public Banner findBannerById(Long id) {
        return bannerRepository.getOne(id);
    }

    @Override
    public void updateBanner(String bannerName, Long id) {
        bannerRepository.update(bannerName, id);
    }
}
