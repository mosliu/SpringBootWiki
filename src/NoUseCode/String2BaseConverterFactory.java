package net.liuxuan.SprKi.entity.labthink;

import net.liuxuan.SprKi.entity.Base;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.String2BaseConverterFactory
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 15:00
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
public class String2BaseConverterFactory implements ConverterFactory<String, Base> {
    public <T extends Base> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToBase(targetType);
    }

    private class StringToBase<T extends Base> implements Converter<String, T> {

        private final Class<T> BaseType;

        public StringToBase(Class<T> BaseType) {
            this.BaseType = BaseType;
        }

        public T convert(String source) {
            if (source.length() == 0) {
                // It's an empty Base identifier: reset the Base value to null.
                return null;
            }
            Long id =  Long.valueOf(source);
            T ttt = (T) new Base(id);
            return ttt;
        }
    }
}
