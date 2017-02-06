package net.liuxuan.spring.mvc.views;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.HashMap;
import java.util.Map;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.views.CmsDialectProcessor
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 11:14
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
public class CmsDialectProcessor extends AbstractAttributeModifierAttrProcessor {

    public CmsDialectProcessor() {
        super("classforposition");
    }

    @Override
    public int getPrecedence() {
//        return 0;
        return 12000;
    }

    /**
     * org.thymeleaf.Arguments对象的执行参数包含上下文、局部变量、模板解析信息和其他一些DOM处理的有用的数据。
     * <p>
     * 处理器匹配上下文，其中包含有关执行处理器正在执行的条件的信息，实际上是匹配的。
     * <p>
     * 问题在于相同的处理器类可以被包含在多个方言，在一个相同的模板引起中执行。可能配置会有所不同。单这些方言可能使用不同的前缀。
     * 如果这，我们怎么知道处理器执行的是哪个方言？这就是ProcessorMatchingContext对象的功能。
     * <p>
     * node是处理器将会执行的节点。注意这个处理器是应用到了特定的节点。但是并没有阻止这个节点树的其他部分的修改。
     */


    @Override
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
        final Configuration configuration = arguments.getConfiguration();

                    /*
                     * 获得属性值
                     */
        final String attributeValue = element.getAttributeValue(attributeName);

                    /*
                     * 获得Thymeleaf的标准表达式解析器                      */
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

                    /*
                     * 以一个标准Thymeleaf表达式解析属性值                     */
        final IStandardExpression expression = parser.parseExpression(configuration, arguments, attributeValue);

                    /*
                     * 执行刚才解析到的表达式
                     */
        final Integer position = (Integer) expression.execute(configuration, arguments);

                    /*
                     * 获取联赛表格中对应该位置的备注信息                     */
//        final Remark remark = RemarkUtil.getRemarkForPosition(position);

                    /*
                     * 应用对应的CSS样式到元素中                             */
        final Map<String, String> values = new HashMap<String, String>();
//        if (remark != null) {
//            switch (remark) {
//                case WORLD_CHAMPIONS_LEAGUE:
//                    values.put("class", "wcl");
//                    break;
//                case CONTINENTAL_PLAYOFFS:
//                    values.put("class", "cpo");
//                    break;
//                case RELEGATION:
//                    values.put("class", "rel");
//                    break;
//            }
//        }

        return values;
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        // 万一该元素已经设置了Class属性，我们将把我们的新值拼接到后面（用空格隔开），或者简单的取代他
        return ModificationType.APPEND_WITH_SPACE;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        // 如果算出来的class属性是空则根本不显示出来
        //return true;
        return false;
    }

    @Override
    protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
        // 当这个元素被执行完成后不需要再重新计算
        return false;
    }
}
