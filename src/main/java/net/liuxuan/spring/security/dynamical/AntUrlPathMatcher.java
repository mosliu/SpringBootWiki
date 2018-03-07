package net.liuxuan.spring.security.dynamical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.spring.security.dynamacal.AntUrlPathMatcher<br/>
 * 功能: 	<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2017/3/15 9:27 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2017/3/15  |    Moses       |     Created <br/>
 */
public class AntUrlPathMatcher implements UrlMatcher {
    private static Logger log = LoggerFactory.getLogger(AntUrlPathMatcher.class);

    private boolean requiresLowerCaseUrl;
    private PathMatcher pathMatcher;


    public AntUrlPathMatcher() {
        this(true);
    }

    public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = true;
        this.pathMatcher = new AntPathMatcher();

        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }


    @Override
    public Object compile(String path) {
        if (this.requiresLowerCaseUrl) {
            return path.toLowerCase();
        }
        return path;
    }

    @Override
    public boolean pathMatchesUrl(Object path, String url) {
        if (("/**".equals(path)) || ("**".equals(path))) {
            return true;
        }
        return this.pathMatcher.match((String) path, url);
    }

    @Override
    public String getUniversalMatchPattern() {
        return "/**";
    }

    @Override
    public boolean requiresLowerCaseUrl() {
        return this.requiresLowerCaseUrl;
    }

    public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }
}
