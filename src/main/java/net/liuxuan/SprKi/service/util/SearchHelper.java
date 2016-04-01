package net.liuxuan.SprKi.service.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Search helper.
 */
public class SearchHelper {

    private static Logger log = LoggerFactory.getLogger(SearchHelper.class);

    /**
     * 对取值对象中的所有属性进行遍历，针对输入的需要处理的属性生产Predict
     * Equals predicate builder.
     *
     * @param root      the root
     * @param cb        the cb
     * @param sl        the 需要处理的属性名列表
     * @param objectMap the object map
     * @return the list<Predicate>  返回equal查询的Predict列表
     */
    public static List<Predicate> buildEqualsAndPredicateBuilder(Root root, CriteriaBuilder cb, String[] sl, Map<String, Object> objectMap) {
        List<Predicate> pl = new ArrayList<Predicate>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String name = entry.getKey();
            if (!ArrayUtils.contains(sl, name)) {
                continue;
            }
            if (entry.getValue() == null) {
                //跳过
            } else {
                Path p = root.get(name);
                Predicate predicateCategory = cb.equal(p, entry.getValue());
                pl.add(cb.and(predicateCategory));
            }
        }
        log.debug("===buildEqualsAndPredicateBuilder logged ,the value is : {}", pl.size());
        return pl;
    }




    /**
     * 将对象转换成一个一个Map对象
     *
     * @param object the object
     * @return the map
     */
    public static Map<String, Object> object2Map(Object object) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public static List<Predicate> buildStringAndLikePredict(Root root, CriteriaBuilder cb, String[] likepaths, String keyword) {
        List<Predicate> pl = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(keyword)) {
            for (String likepath : likepaths) {
                Path<String> titlePath = root.get(likepath);
                Predicate predicate = cb.like(titlePath, "%" + keyword + "%");
                pl.add(cb.or(predicate));
            }
        }
        log.debug("===buildStringAndLikePredict logged ,the value is : {}", pl.size());
        return pl;
    }

    public static List<Predicate> convertToOrPredict(List<Predicate> pl, CriteriaBuilder cb){
        if (pl.size() > 0) {
            Predicate[] predicates = pl.toArray(new Predicate[pl.size()]);
            pl.clear();
            pl.add(cb.or(predicates));
        }
        log.debug("===buildStringOrLikePredict logged ,the value is : {}", pl.size());
        return pl;
    }

}