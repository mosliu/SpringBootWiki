package net.liuxuan.supportsystem.repository.utils;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.GenericRepositoryImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/15 8:56
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/15  |    Moses       |     Created
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings('unchecked')
@NoRepositoryBean
public class GenericRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericRepository<T, ID>, Serializable {
    private static final long serialVersionUID = 1L;

    private static Logger log =  LoggerFactory.getLogger(GenericRepositoryImpl.class);

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    private Class<?> springDataRepositoryInterface;

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given
     * {@link JpaEntityInformation}.
     *
     * @param entityInformation
     * @param entityManager
     */
    public GenericRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, Class<?> springDataRepositoryInterface) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.springDataRepositoryInterface = springDataRepositoryInterface;
    }

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given
     * domain type.
     *
     * @param domainClass
     * @param em
     */
    public GenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em,null);
//        this(JpaEntityInformationSupport.getMetadata(domainClass, em), em, null);
    }

    public Class<?> getSpringDataRepositoryInterface() {
        return springDataRepositoryInterface;
    }

    public void setSpringDataRepositoryInterface(
            Class<?> springDataRepositoryInterface) {
        this.springDataRepositoryInterface = springDataRepositoryInterface;
    }

    public <S extends T> S save(S entity) {
        if (this.entityInformation.isNew(entity)) {
            this.em.persist(entity);
            flush();
            return entity;
        }
        entity = this.em.merge(entity);
        flush();
        return entity;
    }


    public T saveWithoutFlush(T entity) {
        return
                super.save(entity);
    }

    public List<T> saveWithoutFlush(Iterable<? extends T> entities) {
        List<T> result = new ArrayList<T>();
        if (entities == null) {
            return result;
        }

        for (T entity : entities) {
            result.add(saveWithoutFlush(entity));
        }
        return result;
    }
}
