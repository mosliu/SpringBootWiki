/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.supportsystem.service.testHibernate;

import net.liuxuan.supportsystem.entity.Banner;

/**
 * Created by Moses on 2016/2/5.
 */
public interface BannerService {
    void saveBanner(Banner banner);

    Banner findBannerById(Long id);

    void updateBanner(String bannerName, Long id);
}
