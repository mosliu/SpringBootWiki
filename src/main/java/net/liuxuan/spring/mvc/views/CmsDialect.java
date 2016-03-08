package net.liuxuan.spring.mvc.views;

import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.doctype.resolution.IDocTypeResolutionEntry;
import org.thymeleaf.doctype.translation.IDocTypeTranslation;
import org.thymeleaf.processor.IProcessor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.views.CmsDialect
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 10:11
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
public class CmsDialect implements IExpressionEnhancingDialect {

    public static final String DEFAULT_PREFIX = "giPlug";
    public static final String RESUME_OBJECT_NAME = "resume";
//
//    @Autowired
//    private ResumeDirective directive;

    /**
     * <p>
     * Returns the objects that should be added to expression evaluation contexts.
     * </p>
     * <p>
     * This means, for example, that a dialect could add a <tt>util</tt> object so that it could be
     * used in OGNL or SpringEL expression evaluations like <tt>${#util.doThis(obj)}</tt>.
     * </p>
     *
     * @param processingContext the processing context on which the expression evaluation will be performed.
     * @return the Map of objects to be added to the expression evaluation context.
     */
    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        final IContext context = processingContext.getContext();
        final IWebContext webContext = (context instanceof IWebContext ? (IWebContext) context : null);
        final Map<String, Object> objects = new HashMap<String, Object>(3, 1.0f);
        // objects
        if (webContext != null) {
            final HttpServletRequest request = webContext.getHttpServletRequest();
            final HttpServletResponse response = webContext.getHttpServletResponse();
            final ServletContext servletContext = webContext.getServletContext();
//            if (request != null && response != null && servletContext != null) {
//                objects.put(RESUME_OBJECT_NAME, directive); // resume
//            }
        }
        return objects;
    }

    /**
     * <p>
     * Returns the default dialect prefix (the one that will be used if none is explicitly
     * specified during dialect configuration).
     * </p>
     * <p>
     * If <tt>null</tt> is returned, then every attribute
     * and/or element is considered processable by the processors in the dialect that apply
     * to that kind of node (elements with their attributes), and not only those that start
     * with a specific prefix.
     * </p>
     * <p>
     * Prefixes are <b>not</b> exclusive to a dialect: several dialects can declare the same
     * prefix, effectively acting as an aggregate dialect.
     * </p>
     *
     * @return the dialect prefix.
     */
    //这是你方言的tag和attribute的前缀，一种命名空间(它在添加到模板引擎时可以被改变）。
    // 如果你添加了一个attribute 为 earth 而你的方言的前缀是planets,你在模板里将你的attribute可以写成planets:earth.
    @Override
    public String getPrefix() {
        return DEFAULT_PREFIX;
    }

    /**
     * <p>
     * Returns the set of processors.
     * </p>
     *
     * @return the set of processors.
     */

    //处理器是主要在DOM节点上执行和进行变化的对象
    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();
        return processors;
    }

    /**
     * <p>
     * Returns the execution attributes that will be set during executions
     * of the template engine.
     * </p>
     * <p>
     * If more than one dialect are set, all of their execution attributes
     * will be added to the available execution attributes map.
     * </p>
     *
     * @return the execution attributes for this dialect.
     * @since 1.1
     */
    //执行属性是在模板处理过程中为方言提供执行参数的一些对象。
    // 这些对象（usually utility objects通常是通用对象）将在执行器执行时可以使用。
    // 注意这些对象不会存在变量上下文中，只能在内部可见。
    @Override
    public Map<String, Object> getExecutionAttributes() {
        return Collections.emptyMap();
    }

    /**
     * <p>
     * Returns the set of DOCTYPE translations.
     * </p>
     *
     * @return the set of DOCTYPE translations.
     */
    //Thymeleaf可以处理一系列DOCTYPE的转换.这样允许你为你的模板指定一个转换，将你的DOCTYPE在输出时转换为另一个DOCTYPE .
    @Override
    public Set<IDocTypeTranslation> getDocTypeTranslations() {
        return Collections.emptySet();
    }

    /**
     * <p>
     * Returns the set of DOCTYPE resolution entries.
     * </p>
     *
     * @return the set of DOCTYPE resolution entries.
     */
    @Override
    public Set<IDocTypeResolutionEntry> getDocTypeResolutionEntries() {
        return Collections.emptySet();
    }
}
