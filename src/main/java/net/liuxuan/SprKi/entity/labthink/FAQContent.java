package net.liuxuan.SprKi.entity.labthink;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.liuxuan.SprKi.entity.CMSContent;
import javax.persistence.*;
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
@Table(name = "Sprki_CMS_ContentFAQ")
@PrimaryKeyJoinColumn(name = "FAQ_ID")
public class FAQContent extends CMSContent{

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    protected Department department;//来源部门

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    protected Devices devices;//设备

    @Lob
    @Column(columnDefinition = "mediumtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String question =""; // 正文

    @Lob
    @Column(columnDefinition = "mediumtext", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    protected String answer =""; // 正文


}
