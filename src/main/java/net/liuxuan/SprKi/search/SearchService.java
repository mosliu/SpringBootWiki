package net.liuxuan.SprKi.search;

import net.liuxuan.SprKi.entity.NewsPage;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import org.apache.lucene.search.Query;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermTermination;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.search.SearchService
 * 功能: 抽象出了查询的过程，传入相应参数
 * 版本:	@version 1.0
 * 编制日期: 2017/7/5 14:31
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/7/5  |    Moses       |     Created
 */
@Repository
@Transactional
public class SearchService {
    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Search list.
     *
     * @param text the text
     * @return the list
     * @throws EmptyQueryException the empty query exception
     */
    public List FaqSearch(String text) throws EmptyQueryException {
        String[] searchFields = {"question", "answer", "categoryBy_name", "departmentBy_departmentName", "standard", "fullTitle", "subtitle", "title"};
        Class aClass = FAQContent.class;
        return search(text, searchFields, aClass);
    }

    public List NewPageSearch(String text) throws EmptyQueryException {
        String[] searchFields = {"fullTitle", "subtitle", "title","infoText",};
        Class aClass = NewsPage.class;
        return search(text, searchFields, aClass);
    }

    /**
     * Search list.
     *
     * @param text         搜索的关键字
     * @param searchFields 搜索使用的字段
     * @param aClass       搜索的对象类型
     * @return the list
     */
    public List search(String text, String[] searchFields, Class aClass) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(aClass).get();

        // a very basic query by keywords
        Query query;
        TermTermination matching = queryBuilder
                .keyword()
                .onFields(searchFields)
                .matching(text);
        FullTextQuery jpaQuery = null;
        try {
            query = matching.createQuery();
            // wrap Lucene query in an Hibernate Query object
            jpaQuery = fullTextEntityManager.createFullTextQuery(query, aClass);
        } catch (EmptyQueryException e) {
            throw e;
        }

        // execute search and return results (sorted by relevance as default)
        return jpaQuery == null ? new ArrayList() : jpaQuery.getResultList();
    }
}
