package net.liuxuan.SprKi.controller.admin.labthink${subpackage};

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity${subpackage}.${model_name};
import net.liuxuan.SprKi.service${subpackage}.${model_name}Service;
import net.liuxuan.spring.Helper.ResponseHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink${subpackage}.${model_name}Repository
* 功能:
* 版本:	@version 1.0
* 编制日期: ${date?string("yyyy/MM/dd HH:mm")}
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* ${date?string("yyyy-MM-dd")}  |    ${author}        |     Created
*/

@Controller
@RequestMapping("/admin")
public class ${model_name}ManagementController {
    private static Logger log = LoggerFactory.getLogger(${model_name}ManagementController.class);

    @Autowired
    ${model_name}Service ${model_name_firstSmall}Service;

    @RequestMapping("${model_name_firstSmall}Manage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        ${model_name} ${model_name_firstSmall} = new ${model_name}();
        model.put("${model_name_firstSmall}", ${model_name_firstSmall});

        return "admin/" + "${model_name_firstSmall}Manage" + " :: middle";

    }

    @RequestMapping("${model_name_firstSmall}")
    public String ${model_name_firstSmall}Manage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===${model_name_firstSmall}Manage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                ${model_name} ${model_name_firstSmall};
                Long id = dto.getStr2LongID();
                
                ${model_name_firstSmall} = ${model_name_firstSmall}Service.find${model_name}ById(id);
                if (${model_name_firstSmall} != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("${model_name_firstSmall}", ${model_name_firstSmall});
                return "admin/snipplets/div_${model_name_firstSmall} :: ${model_name_firstSmall}edit";
            default:
                return "redirect:/admin/${model_name_firstSmall}_ajax";
//                break;
        }
    }


    @RequestMapping("${model_name_firstSmall}_ajax")
//    @ResponseBody
    public void ${model_name_firstSmall}ManageAjax(@ModelAttribute("dto") BaseDTO _dto, ${model_name} _${model_name_firstSmall}, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===${model_name_firstSmall}ManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String ${model_name_firstSmall}Name = request.getParameter("${model_name_firstSmall}Name");
                String ${model_name_firstSmall}NameCN = request.getParameter("${model_name_firstSmall}NameCN");
                String comment = request.getParameter("comment");
                boolean ${model_name_firstSmall}Exists = ${model_name_firstSmall}Service.check${model_name}Exists(${model_name_firstSmall}Name);
                if (${model_name_firstSmall}Exists) {
                    log.info("===${model_name_firstSmall}ManageAjax logged ,添加${model_name}已存在 : {}");
                    rtnData.put("error", "ERROR_${model_name}Exists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加${model_name}已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加${model_name}");
                    <#--${model_name} ${model_name_firstSmall} = new ${model_name}();-->
                    <#--${model_name_firstSmall}.set${model_name}Name(${model_name_firstSmall}Name);-->
                    <#--${model_name_firstSmall}.setComment(comment);-->
                    <#--${model_name_firstSmall}.set${model_name}NameCN(${model_name_firstSmall}NameCN);-->
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _${model_name_firstSmall}, "添加角色", "");
                    ${model_name_firstSmall}Service.save${model_name}(_${model_name_firstSmall});
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = ${model_name_firstSmall}Service.delete${model_name}ById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除${model_name}");
                } else {
                    rtnData.put("error", "ERROR_${model_name}NotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "${model_name}不存在，删除失败");
                }
                break;
            case "update":
                ${model_name_firstSmall}Service.save${model_name}(_${model_name_firstSmall});
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _${model_name_firstSmall}, "更新${model_name}", "");
                rtnData.put("status", "success");
                rtnData.put("msg", "更新成功");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("${model_name}_list")
    public List<${model_name}> ${model_name}list() {
        return ${model_name_firstSmall}Service.getAll${model_name}();
    }
}