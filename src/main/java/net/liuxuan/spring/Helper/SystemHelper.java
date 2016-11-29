package net.liuxuan.spring.Helper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import net.liuxuan.SprKi.repository.user.UserDetailInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 系统帮助工具类
 */
public final class SystemHelper {

    private SystemHelper() {
        throw new Error("工具类不能实例化！");
    }

    @Resource
    static UserDetailInfoRepository userDetailInfoRepository;

    @Resource
    static UsersRepository usersRepository;

    /**
     * 退出系统并清空session
     */
    public static void logout() {
        HttpSession session = getSession();
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 得到request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        return request;
    }

    /**
     * 得到session
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            session = request.getSession(true);
        }
        return session;
    }

    /**
     * 保存信息到session
     */
    public static void setSessionAttibute(final String key, final Object value) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 从session获取属性
     */
    public static Object getSessionAttibute(final String key) {
        Object resutlt = null;
        HttpSession session = getSession();
        if (session != null) {
            resutlt = session.getAttribute(key);
        }
        return resutlt;
    }

    /**
     * 获取getCreationTime
     */
    public static long getCreationTime() {
        Long resutlt = 0L;
        HttpSession session = getSession();
        if (session != null) {
            resutlt = session.getCreationTime();
        }
        return resutlt;
    }

    /**
     * 获取getLastAccessedTime
     */
    public static long getLastAccessedTime() {
        Long resutlt = 0L;
        HttpSession session = getSession();
        if (session != null) {
            resutlt = session.getLastAccessedTime();
        }
        return resutlt;
    }

    /**
     * 检查用户是已认证
     */
    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        } else {
            return false;
        }
    }



    /**
     * 获得认证信息
     */
    public static Authentication getAuthentication() {
        Authentication authentication = null;
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) getSessionAttibute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl != null) {
            authentication = securityContextImpl.getAuthentication();
        }
        return authentication;
    }


    /**
     * 得到当前用户IP
     */
    public static String getCurrentUserIp() {
        String currentUserIp = "";
        HttpServletRequest request = getRequest();
        if (request != null) {
            currentUserIp = request.getRemoteAddr();
        }
        return currentUserIp;
    }

    public static UserDetailInfo getCurrentUserDetailInfo(){
        UserDetails userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        Users u = usersRepository.findOne(userDetails.getUsername());
        UserDetailInfo udi =userDetailInfoRepository.findByUsers(u);
        return udi;
    }


    /**
     * 得到当前SessionId
     */
    public static String getCurrentSessionId() {
        String currentSessionId = "";
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            currentSessionId = details.getSessionId();
        }
        return currentSessionId;
    }

    /**
     * 得到当前获得当前用户所拥有的权限
     */
    @SuppressWarnings("unchecked")
    public static List<GrantedAuthority> getCurrentUserAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        }
        return authorities;
    }

    public static WebApplicationContext getWebAppContext(HttpServletRequest request) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
//        WebApplicationContextUtils.
//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        wac.get
        return wac;
    }

}
