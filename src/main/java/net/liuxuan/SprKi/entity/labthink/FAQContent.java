package net.liuxuan.SprKi.entity.labthink;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.liuxuan.SprKi.entity.CMSContent;
import org.apdplat.word.lucene.ChineseWordAnalyzer;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.FAQContent
 * 功能: FAQ类型内容
 * 版本:	@version 1.0
 * 编制日期: 2016/3/8 10:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/8  |    Moses       |     Created
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@Indexed: 标明这个实体需要被Lucene创建索引，从而使之可以被检索
@Indexed
//@Analyzer: 告诉Hibernate Search来标记它的域以及更新Lucene索引的时候使用哪个Lucene分析器。
@Analyzer(impl = ChineseWordAnalyzer.class)
@Table(name = "Sprki_CMS_ContentFAQ")
@PrimaryKeyJoinColumn(name = "FAQ_ID")
public class FAQContent extends CMSContent{

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @IndexedEmbedded(depth = 1, prefix = "departmentBy_")
    @JoinColumn(name="department")
    protected Department department;//来源部门

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="devices")
    protected Devices devices;//设备

    @Lob
    @Field
    @Column(columnDefinition = "longtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String question =""; // 正文

    @Lob
    @Field
    @Column(columnDefinition = "longtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String answer =""; // 正文

    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date questionDate; // 问题的时间


}
