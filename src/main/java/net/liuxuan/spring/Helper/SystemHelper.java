package net.liuxuan.spring.Helper;

import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.service.security.RoleService;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统帮助工具类
 */
@Component
public final class SystemHelper {

//    private SystemHelper() {
//        throw new Error("工具类不能实例化！");
//    }
//
//    @Resource
//    UserDetailInfoRepository userDetailInfoRepository1;
//
//    static UserDetailInfoRepository userDetailInfoRepository;

    static UserDetailInfoService userDetailInfoService;
    static RoleService roleService;
    @Resource
    UserDetailInfoService userDetailInfoService1;
    @Resource
    RoleService roleService1;

//    @Resource
//    UsersRepository usersRepository1;
//
//    static UsersRepository usersRepository;

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

//        Authentication authentication = null;
//        SecurityContextImpl securityContextImpl = (SecurityContextImpl) getSessionAttibute("SPRING_SECURITY_CONTEXT");
//        if (securityContextImpl != null) {
//            authentication = securityContextImpl.getAuthentication();
//        }
//        return authentication;
        return SecurityContextHolder.getContext().getAuthentication();
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

    public static UserDetailInfo getCurrentUserDetailInfo() {

        Object principal = SystemHelper.getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        UserDetailInfo udi = null;
//        Class<?>[] interfaces = principal.getClass().getInterfaces();
//        for (int i = 0; i < interfaces.length; i++) {
//
//
//        }
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
            udi = userDetailInfoService.findUserDetailInfoByUsername(userDetails.getUsername());
        }

//        DbUser u = usersRepository.findOne(userDetails.getUsername());
//        UserDetailInfo udi =userDetailInfoRepository.findByDbUser(u);


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

    public static DbUser getCurrentUser() {
        return getCurrentUsersFromDb();
    }

    public static DbUser getCurrentUsersFromDb() {
        User ui = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        DbUser u = userDetailInfoService.findDbUserByUsername(ui.getUsername());
        return u;
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

    /**
     * 获得角色信息
     */
    public static List<Role> getCurrentUserRoles() {
        List<Role> roleList = new ArrayList<>();
        Authentication authentication = getAuthentication();

        if (authentication != null) {
//            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
            Set<Authorities> authorities = getCurrentUser().getAuths();

//            roleList = authorities.stream().map(e -> roleService.findRoleById(e.getAuthority())).collect(Collectors.toList());
            roleList = authorities.stream().map(e -> e.getRolename()).collect(Collectors.toList());
        }

//        Authentication authentication = null;
//        SecurityContextImpl securityContextImpl = (SecurityContextImpl) getSessionAttibute("SPRING_SECURITY_CONTEXT");
//        if (securityContextImpl != null) {
//            authentication = securityContextImpl.getAuthentication();
//        }
//        return authentication;
        return roleList;
    }

    public static boolean isCurrentUserAdmin(){
        return  getCurrentUser().getAuths().stream().anyMatch(e-> e.getRolename().getRolename().equalsIgnoreCase("ROLE_ADMIN"));
    }

    public static WebApplicationContext getWebAppContext(HttpServletRequest request) {
        WebApplicationContext wac = RequestContextUtils.findWebApplicationContext(request);
//        WebApplicationContextUtils.
//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        wac.get
        return wac;
    }

    /**
     * 得到运行目录
     *
     * @return
     */
    public static String getRootPath() {
        File root = new File(SystemHelper.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        if (root.isFile()) {
            root = new File(root.getParent());
        }
//        System.out.println("root is file?:"+root.isFile());
//        System.out.println("root parent is :"+root.getParent());
//        System.out.println(f.getAbsolutePath());
//        return SystemHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return root.getAbsolutePath();
    }

    /**
     * 得到方法的调用者。
     */
    public static void getCaller() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement ste = stack[i];
            System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(...);");
            System.out.println(i + "--" + ste.getMethodName());
            System.out.println(i + "--" + ste.getFileName());
            System.out.println(i + "--" + ste.getLineNumber());
        }
    }

    @PostConstruct
    public void init() {
//        userDetailInfoRepository=userDetailInfoRepository1;
//        usersRepository=usersRepository1;
        userDetailInfoService = userDetailInfoService1;
        roleService = roleService1;
    }


}
