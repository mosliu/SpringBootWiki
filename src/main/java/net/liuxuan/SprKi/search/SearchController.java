package net.liuxuan.SprKi.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    private FAQSearch faqSearch;

    /**
     * Show search results for the given query.
     *
     * @param q The search query.
     */
    @RequestMapping("/search")
    public String search(String q, Model model) {
        List searchResults = null;
//        try {
        searchResults = faqSearch.search(q);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            // here you should handle unexpected errors
//            // ...
//            // throw ex;
//        }
        log.debug("Search results total number:{}" , searchResults.size());
        model.addAttribute("searchResults", searchResults);
        return "search";
    }

}
