package net.liuxuan.SprKi.entity.labthink;

import net.liuxuan.SprKi.repository.labthink.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.String2DevicesConverter
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 14:06
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
public class String2DevicesConverter implements Converter<String, Devices> {

    @Autowired
    DevicesRepository devicesRepository;

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Devices convert(String source) {
        Long id = Long.valueOf(source);
        Devices one = devicesRepository.findOne(id);
        return one;
//        return null;
    }
}
