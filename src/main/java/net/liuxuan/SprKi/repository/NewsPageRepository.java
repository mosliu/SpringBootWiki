package net.liuxuan.SprKi.repository;

import java.util.List;
import net.liuxuan.SprKi.entity.NewsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.NewsPageRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/

public interface NewsPageRepository extends JpaRepository<NewsPage, Long>, JpaSpecificationExecutor<NewsPage> {

//    List<NewsPage> findByNewsPageName(String  name);
//    List<NewsPage> findByNewsPageNameNot(String  NotName);
    List<NewsPage> findByDisabledFalse();

    List<NewsPage> findTop20ByDisabledFalseOrderByLastUpdateDateDesc();

    Page<NewsPage> findAllByDisabledFalseOrderByLastUpdateDateDesc(Pageable pageable);
//    List<NewsPage> findByNewsPageNameNotOrderByNewsPageName(String roleNotName);

}


