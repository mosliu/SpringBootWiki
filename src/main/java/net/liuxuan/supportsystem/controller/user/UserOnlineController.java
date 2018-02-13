package net.liuxuan.supportsystem.controller.user;

import net.liuxuan.supportsystem.entity.labthink.Department;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import net.liuxuan.utils.identicon.DrawUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.user.UserOnlineController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/5 16:58
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/5  |    Moses       |     Created
 */
@Controller

public class UserOnlineController {
    private static Logger log = LoggerFactory.getLogger(UserOnlineController.class);
    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    UserDetailInfoService userDetailInfoService;

    @Value("${SprKi.avatar.storePath}")
    String storePath;
    @Value("${SprKi.avatar.accessUrlPath}")
    String accessUrlPath;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/online")
    @ResponseBody
    public List getOnlineNow() {
        List<HashMap<String, String>> rtn = new ArrayList();

        Date now = new Date();


        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipals) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                List<SessionInformation> allSessionsForUser = sessionRegistry.getAllSessions(userDetails, true);
                if (allSessionsForUser.size() > 0) {
                    String username = userDetails.getUsername();
                    Date lastRequest = allSessionsForUser.get(0).getLastRequest();
                    HashMap<String, String> map = new HashMap();
                    map.put("username", username);
                    UserDetailInfo userDetailInfoByUsername = userDetailInfoService.findUserDetailInfoByUsername(username);
                    if (userDetailInfoByUsername != null) {
                        map.put("usernameCN", userDetailInfoByUsername.getLastName() + userDetailInfoByUsername.getFirstName());
                        map.put("avatar",userDetailInfoByUsername.getAvatar());
                    } else {
                        map.put("usernameCN", "");
                        map.put("avatar","");
                    }
                    map.put("date", sdf.format(lastRequest));
                    map.put("online", "" + (now.getTime() - lastRequest.getTime()));
                    map.put("onlineStatus", "" + ((now.getTime() - lastRequest.getTime()) > 600000));
                    rtn.add(map);

                }
            }
        }

        return rtn;
    }

    /**
     * return contains a map which separate userdetailinfos into every department.
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping("/onlineAll")
    public String getALL(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        List<UserDetailInfo> userDetailInfoList = userDetailInfoService.listAllUserDetailInfos();

        Date now = new Date();
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        Map<String, SessionInformation> sessionInformationMap = allPrincipals.stream()
                .map(n -> sessionRegistry.getAllSessions(n, true))
                .flatMap(l -> l.stream())
                .collect(Collectors.toMap(
                        e -> ((UserDetails) e.getPrincipal()).getUsername(),
                        e -> e
                ));
        userDetailInfoList.stream().filter(u->sessionInformationMap.containsKey(sessionInformationMap)).forEach(
                e->e.setOnlinestatus((sessionInformationMap.get(e.getDbUser().getUsername()).getLastRequest().getTime()-now.getTime())>600000)
        );

        Map<Department, List<UserDetailInfo>> departmentListMap = userDetailInfoList.stream().collect(Collectors.groupingBy(UserDetailInfo::getDepartment));

//        Map<String, List<UserDetailInfo>> rtnmap = new HashMap<String, List<UserDetailInfo>>();
//        userDetailInfoList.forEach((UserDetailInfo udi) -> {
//            String departmentName = udi.getDepartment().getDepartmentName();
//            if (departmentName == null) {
//                departmentName = "";
//            }
//            List<UserDetailInfo> departUdiList = rtnmap.get(departmentName);
//            if (departUdiList == null) {
//                departUdiList = new ArrayList<UserDetailInfo>();
//                departUdiList.add(udi);
//            }
//            rtnmap.put(departmentName, departUdiList);
//        });
        model.put("DepartmentUserDetailMap", departmentListMap);


        return "user/user_online_show";
    }

    @RequestMapping(value = "/avatar/{username}", method = RequestMethod.GET)
    public String getMsg(
            @PathVariable String username,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        UserDetailInfo info = userDetailInfoService.findUserDetailInfoByUsername(username);
        String path ;
        if(info!=null){
            path = info.getAvatar();
        }else{
//            String storePath = "./static/uploaded/avatar/";
//            String accessUrlPrefix = "/uploaded/avatar/";
            String storePath = this.storePath;
            String accessUrlPrefix = this.accessUrlPath;
            String toMd5EncodeStr = username;
            //生成
            String filename = DrawUtils.generateAvatar(storePath, toMd5EncodeStr);
            //写入
            path = accessUrlPrefix+filename;
        }
        return "redirect:"+path;
    }


}
