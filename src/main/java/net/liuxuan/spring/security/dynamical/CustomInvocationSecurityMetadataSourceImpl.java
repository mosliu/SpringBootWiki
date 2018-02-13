package net.liuxuan.spring.security.dynamical;

import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.entity.security.UrlAuth;
import net.liuxuan.supportsystem.service.security.RoleService;
import net.liuxuan.supportsystem.service.security.UrlAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.dynamacal.CustomInvocationSecurityMetadataSource
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/15 9:25
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/15  |    Moses       |     Created
 */
@Component("securityMetadataSource")
public class CustomInvocationSecurityMetadataSourceImpl implements CustomInvocationSecurityMetadataSource {

    private static Logger log =  LoggerFactory.getLogger(CustomInvocationSecurityMetadataSourceImpl.class);
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    private static boolean loaded = false;

    @Autowired
    RoleService roleService;

    @Autowired
    UrlAuthService urlAuthService;

    public CustomInvocationSecurityMetadataSourceImpl() {
        //loadResourceDefine();
    }

    /**
     * 从数据库读取权限配置信息
     *
     */
    private void loadResourceDefine() {

        //To store the relationship between url and role.
        //key is url
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        List<UrlAuth> allUrlAuth = urlAuthService.getAllUrlAuth();

        allUrlAuth.forEach(urlAuth->{
            if(!urlAuth.isDisabled()){
                String urlAuthName = urlAuth.getUrlAuthName();
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();

                Set<Role> roles = urlAuth.getRoles();
                roles.forEach(role -> {
                    if (!role.isDisabled()) {
                        ConfigAttribute ca = new SecurityConfig(role.getRolename());
                        atts.add(ca);
                    }
                });
                resourceMap.put(urlAuthName, atts);

            }
        });
        loaded = true;
//
//        List<Role> allRole = roleService.getAllRole();
//
//        allRole.stream().filter(role -> !role.isDisabled()).forEach(
//                role -> {
//                    Set<UrlAuth> urlAuths = role.getUrlAuths();
//
//                    resourceMap.get()
//                }
//        );
//
//
//        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//        ConfigAttribute ca = new SecurityConfig("ROLE_USER");
//        atts.add(ca);
//        resourceMap.put("/", atts);
//
//        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//        Collection<ConfigAttribute> atts11 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute ca11 = new SecurityConfig("ROLE_ADMIN");
//        atts11.add(ca11);
//        resourceMap.put("/faq/**", atts11);
////        resourceMap.put("/ticket/**", atts11);
//
//        Collection<ConfigAttribute> attsno12 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute cano12 = new SecurityConfig("ROLE_OTHER1");
////        attsno12.add(cano12);
//        resourceMap.put("/ui/**", attsno12);
//
//        Collection<ConfigAttribute> atts2 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute ca2 = new SecurityConfig("ROLE_ADMIN");
//        atts2.add(ca2);
//        resourceMap.put("/admin.jsp", atts2);
//
//        Collection<ConfigAttribute> attsno1 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute cano1 = new SecurityConfig("ROLE_OTHER1");
//        attsno1.add(cano1);
//        attsno1.add(ca);
//        resourceMap.put("/other1.jsp", attsno1);
//
//        Collection<ConfigAttribute> attsno2 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute cano2 = new SecurityConfig("ROLE_OTHER2");
//        attsno2.add(cano2);
//        resourceMap.put("/other2.jsp", attsno2);
//
//        Collection<ConfigAttribute> attsno3 = new ArrayList<ConfigAttribute>();
//        ConfigAttribute cano3 = new SecurityConfig("ROLE_OTHER3");
//        attsno3.add(cano3);
//        resourceMap.put("/other3.jsp", attsno3);

        log.info("security info load success!!");
    }

    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     *
     * @param object the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an
     * empty collection if there are no applicable attributes.
     * @throws IllegalArgumentException if the passed object is not of a type supported by
     *                                  the <code>SecurityMetadataSource</code> implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(!loaded){
            loaded = true;
            loadResourceDefine();
        }
        // guess object is a URL.
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    /**
     * If available, returns all of the {@code ConfigAttribute}s defined by the
     * implementing class.
     * <p>
     * This is used by the {@link AbstractSecurityInterceptor} to perform startup time
     * validation of each {@code ConfigAttribute} configured against it.
     *
     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public static boolean reload() {
        loaded = false;
        return true;
    }
}
