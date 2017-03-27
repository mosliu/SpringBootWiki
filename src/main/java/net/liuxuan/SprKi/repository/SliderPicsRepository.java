package net.liuxuan.SprKi.repository;

import java.util.List;
import net.liuxuan.SprKi.entity.SliderPics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.SliderPicsRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 13:42
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/

public interface SliderPicsRepository extends JpaRepository<SliderPics, Long>, JpaSpecificationExecutor<SliderPics> {
    List<SliderPics> findBySliderPicsName(String  name);

    List<SliderPics> findBySliderPicsNameNot(String  NotName);
    List<SliderPics> findByDisabledFalse();

    List<SliderPics> findBySliderPicsNameNotOrderBySliderPicsName(String roleNotName);

}


