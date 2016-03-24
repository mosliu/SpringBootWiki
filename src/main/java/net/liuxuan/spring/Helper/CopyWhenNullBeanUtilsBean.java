package net.liuxuan.spring.Helper;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class CopyWhenNullBeanUtilsBean extends BeanUtilsBean {

    /**
     * <p>Copy the specified property value to the specified destination bean,
     * performing any type conversion that is required.  If the specified
     * bean does not have a property of the specified name, or the property
     * is read only on the destination bean, return without
     * doing anything.  If you have custom destination property types, register
     * {@link Converter}s for them by calling the <code>register()</code>
     * method of {@link ConvertUtils}.</p>
     * <p>
     * <p><strong>IMPLEMENTATION RESTRICTIONS</strong>:</p>
     * <ul>
     * <li>Does not support destination properties that are indexed,
     * but only an indexed setter (as opposed to an array setter)
     * is available.</li>
     * <li>Does not support destination properties that are mapped,
     * but only a keyed setter (as opposed to a Map setter)
     * is available.</li>
     * <li>The desired property type of a mapped setter cannot be
     * determined (since Maps support any data type), so no conversion
     * will be performed.</li>
     * </ul>
     *
     * @param bean  Bean on which setting is to be performed
     * @param name  Property name (can be nested/indexed/mapped/combo)
     * @param value Value to be set
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *                                   throws an exception
     */
    @Override
    public void copyProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        try {
            Object destValue = PropertyUtils.getSimpleProperty(bean, name);
            if (destValue == null) {
                super.copyProperty(bean, name, value);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }
}