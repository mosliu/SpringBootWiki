package net.liuxuan.supportsystem.repository.utils;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.DefaultRepositoryFactory
 * 功能:The purpose of this class is to override the default behaviour of the spring JpaRepositoryFactory class.
 * It will produce a GenericRepositoryImpl object instead of SimpleJpaRepository.
 * 版本:	@version 1.0
 * 编制日期: 2016/3/15 9:05
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/15  |    Moses       |     Created
 */
public  class DefaultRepositoryFactory extends JpaRepositoryFactory {
    private boolean QUERY_DSL_PRESENT = false;

    private final EntityManager entityManager;
    private final QueryExtractor extractor;


    public DefaultRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);
    }

//    @SuppressWarnings({ 'unchecked', 'rawtypes' })
    protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(
            RepositoryMetadata metadata, EntityManager entityManager) {

        Class<?> repositoryInterface = metadata.getRepositoryInterface();

        JpaEntityInformation<?, Serializable> entityInformation =
                getEntityInformation(metadata.getDomainType());


        if (isQueryDslExecutor(repositoryInterface)) {
            return new QueryDslJpaRepository(entityInformation, entityManager);
        } else {
            return new GenericRepositoryImpl(entityInformation, entityManager, repositoryInterface); //custom implementation
        }
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

        if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
            return QueryDslJpaRepository.class;
        } else {
            return GenericRepositoryImpl.class;
        }
    }

    /**
     * Returns whether the given repository interface requires a QueryDsl
     * specific implementation to be chosen.
     *
     * @param repositoryInterface
     * @return
     */
    private boolean isQueryDslExecutor(Class<?> repositoryInterface) {

        return QUERY_DSL_PRESENT
                && QueryDslPredicateExecutor.class
                .isAssignableFrom(repositoryInterface);
    }
}
