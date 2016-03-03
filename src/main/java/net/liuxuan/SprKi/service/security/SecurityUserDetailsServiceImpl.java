package net.liuxuan.SprKi.service.security;

import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.security.SecurityUserDetailsServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/17 13:24
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/17 |    Moses       |     Created
 */
/**
 * 参考 {@link org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl }
 * 参考 {@link org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper }
 */

@Service(value="securityUserDetailsService")
public class SecurityUserDetailsServiceImpl implements SecurityUserDetailsService {


    private static Logger log =  LoggerFactory.getLogger(SecurityUserDetailsServiceImpl.class);


    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();
    
    //在java代码中使用@Autowired或@Resource注解方式进行装配，这两个注解的区别是：
    //@Autowired 默认按类型装配，@Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
    @Resource
    private UsersRepository usersRepository;
    
//    @Autowired
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
//        System.out.println("username is " + username);
        log.debug("username to login is {}",username);

        Users user = usersRepository.findOne(username);
        if(user == null){
            log.debug("no user got {}",username);
            throw new UsernameNotFoundException(messages.getMessage(
                    "JdbcDaoImpl.notFound", new Object[] { username },
                    "Username {0} not found"));
        }
//        List<GrantedAuthority> authorities = buildUserAuthority(oneuser.getAuths());
        return buildUserForAuthentication(user);
    }

    /**
     * 返回验证角色
     * @param auths
     * @return
     */
    private List<GrantedAuthority> buildUserAuthority(Set<Authorities> auths){
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        for(Authorities auth:auths){
            setAuths.add(new SimpleGrantedAuthority(auth.getAuthority()));
        }
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }

    /**
     * 返回验证用户
     * @param oneuser
     * @return
     */
    private User buildUserForAuthentication(Users oneuser){
//        return null;

        return new User(
                oneuser.getUsername(),
                oneuser.getPassword(),
                oneuser.isEnabled(),true,true,true,
                oneuser.getAuths()
        );
    }
    
    

    /**
     *
     */
}
