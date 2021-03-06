package net.liuxuan.supportsystem.search;

import net.liuxuan.supportsystem.entity.labthink.FAQContent;
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
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.search.FaqSearch
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/12 9:40
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/12  |    Moses       |     Created
 */
@Repository
@Transactional
public class FAQSearch {
    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;

//    @RequestMapping("/search")
    public List search(String text) throws EmptyQueryException {
        String[] faqField = {"question", "answer", "categoryBy_name", "departmentBy_departmentName", "standard", "fullTitle", "subtitle", "title"};
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(FAQContent.class).get();

        // a very basic query by keywords
        Query query ;
        TermTermination matching = queryBuilder
                .keyword()
//                .onFields("question", "answer","categoryBy_name","departmentBy_departmentName","standard","fullTitle","subtitle","title")
                .onFields(faqField)
                .matching(text);
        FullTextQuery jpaQuery = null;
        try {
            query = matching.createQuery();
            // wrap Lucene query in an Hibernate Query object
            jpaQuery = fullTextEntityManager.createFullTextQuery(query, FAQContent.class);
        } catch (EmptyQueryException e) {
            throw e;
//            e.printStackTrace();
        }


        // execute search and return results (sorted by relevance as default)
        List results = jpaQuery==null?new ArrayList():jpaQuery.getResultList();


        return results;
    } // method search


}
