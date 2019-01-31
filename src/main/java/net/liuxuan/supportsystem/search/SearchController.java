package net.liuxuan.supportsystem.search;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.service.labthink.FAQContentService;
import org.hibernate.search.exception.EmptyQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.search.SearchController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/12 9:43
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/12  |    Moses       |     Created
 */
@Controller
public class SearchController {
    private static Logger log = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private SearchService searchService;
    /**
     * The Faq content service.
     */
    @Autowired
    private FAQContentService faqContentService;
    /**
     * Show search results for the given query.
     *
     * @param q The search query.
     */
    @RequestMapping("/search")
    public String search(String q, Model model) {
        List faqResultList = null;
        List filteredFAQContents = null;
        List newsResultList = null;
        String errorText = null;
        try {
            faqResultList = searchService.FaqSearch(q);
            List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
            Set<String> rolenames = currentUserRoles.stream().map(e -> e.getRolename()).collect(Collectors.toSet());
            filteredFAQContents = (List<FAQContent>)faqResultList
                    .stream()
                    .filter(faq -> faqContentService.hasAccessRight(rolenames, (FAQContent) faq))
                    .collect(Collectors.toList());
            newsResultList = searchService.NewPageSearch(q);
            log.debug("FAQ Search results total number:{}" , faqResultList.size());
            log.debug("NEWS Search results total number:{}" , newsResultList.size());
        }catch (EmptyQueryException e){
            errorText =  "你所输入的词不具有明确意义，请检查更换！";
        }
        model.addAttribute("errorText", errorText);

        model.addAttribute("faqResultList", filteredFAQContents);
        model.addAttribute("newsResultList", newsResultList);
        return "search";
    }

}
