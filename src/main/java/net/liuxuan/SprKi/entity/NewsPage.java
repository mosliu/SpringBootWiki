package net.liuxuan.SprKi.entity;


import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.apdplat.word.lucene.ChineseWordAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Indexed;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.entity.NewsPage
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity  //实体类
@PrimaryKeyJoinColumn(name = "NEWS_ID")
//@Indexed: 标明这个实体需要被Lucene创建索引，从而使之可以被检索
@Indexed
//@Analyzer: 告诉Hibernate Search来标记它的域以及更新Lucene索引的时候使用哪个Lucene分析器。
@Analyzer(impl = ChineseWordAnalyzer.class)
@Table(name = "Sprki_CMS_NewsPage")
@IgnoreSizeOf
public class NewsPage extends CMSContent{
    @Column(nullable = true,length=250)
    String picStr;
    @Column(nullable = true,length=250)
    String picType;
}