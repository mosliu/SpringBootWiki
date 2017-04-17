package net.liuxuan.SprKi.service.security;


import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.entity.security.UrlAuth;
import net.liuxuan.SprKi.repository.security.RoleRepository;
import net.liuxuan.SprKi.repository.security.UrlAuthRepository;
import net.liuxuan.spring.security.dynamical.CustomInvocationSecurityMetadataSource;
import net.liuxuan.spring.security.dynamical.CustomInvocationSecurityMetadataSourceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.security.RoleServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/20 11:49
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-20  |    Moses        |     Created
 */
@Service
@Transactional
@CacheConfig(cacheNames = "roles")
public class RoleServiceImpl implements RoleService {

    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UrlAuthRepository urlAuthRepository;

    @Override
    @Cacheable
    public List<Role> getAllRole() {
        return roleRepository.findByDisabledFalse();
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
//        roleRepository.flush();
    }


    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    @Cacheable
    public Role findRoleById(String id) {
        Role role = roleRepository.findOne(id);
        return role;
    }

    @Cacheable
    public List<String> findAllRoleNames(){
        return roleRepository.findAllRoleNames();
    }

    public boolean deleteRoleById(String id) {
        Role role = roleRepository.getOne(id);
        if (role != null) {
            role.setDisabled(true);
            return true;
        }
        return false;


    }

    @Override
    public boolean checkRoleExists(String rolename) {
        List<Role> list = roleRepository.findByRolename(rolename);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, Object> updateAuths(String rolename, String[] authArrays) {
        Map<String, Object> map = new HashMap<String, Object>();

        Role role = roleRepository.findOne(rolename);
        Set<UrlAuth> old_auths= role.getUrlAuths();

        if(authArrays == null){
            //提交错误了啊。。
            map.put("error", "提交出错！请检查");
            return map;
        }
        old_auths.clear();
        for (int i = 0; i < authArrays.length; i++) {

            Long id = Long.parseLong(authArrays[i]);
            old_auths.add(urlAuthRepository.findOne(id));
        }

        roleRepository.save(role);

        CustomInvocationSecurityMetadataSourceImpl.reload();

        map.put("success", "权限处理成功");
        return map;
    }

}