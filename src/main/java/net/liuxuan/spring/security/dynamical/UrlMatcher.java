package net.liuxuan.spring.security.dynamical;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.dynamacal.UrlMatcher
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/15 9:26
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/15  |    Moses       |     Created
 */
public interface UrlMatcher {
    public abstract Object compile(String paramString);

    public abstract boolean pathMatchesUrl(Object paramObject, String paramString);

    public abstract String getUniversalMatchPattern();

    public abstract boolean requiresLowerCaseUrl();
}
