package net.liuxuan.SprKi.service.util;

import net.liuxuan.SprKi.entity.security.Users;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The type Search helper.
 */
public class SearchHelper {
    private static Logger log =  LoggerFactory.getLogger(SearchHelper.class);

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
    public static List<Predicate> buildEqualsAndPredicate(Root root, CriteriaBuilder cb, String[] sl, Map<String, Object> objectMap) {
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
        log.debug("===buildEqualsAndPredicate logged ,the value is : {}", pl.size());
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

        //获得某个类的所有的公共（public）的字段，包括父类。
        for (Field field : object.getClass().getFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                log.error("object to map error",e);
//                e.printStackTrace();
            }
        }
        //getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                log.error("object to map error",e);
//                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 对取值对象中的所有属性进行遍历，针对输入的需要处理的属性生产Like 比较方式的Predict
     * @param root
     * @param cb
     * @param likepaths
     * @param keyword
     * @return
     */
    public static List<Predicate> buildStringAndLikePredict(Root root, CriteriaBuilder cb, String[] likepaths, String keyword) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(keyword)) {
            for (String likepath : likepaths) {
                Path<String> titlePath = root.get(likepath);
                Predicate predicate = cb.like(titlePath, "%" + keyword + "%");
                predicateList.add(cb.or(predicate));
            }
        }
        log.debug("===buildStringAndLikePredict logged ,the value is : {}", predicateList.size());
        return predicateList;
    }

    /**
     * 将and比较的Predict转换为or方式的Predict
     * @param pl
     * @param cb
     * @return
     */
    public static List<Predicate> convertToOrPredict(List<Predicate> pl, CriteriaBuilder cb){
        if (pl.size() > 0) {
            Predicate[] predicates = pl.toArray(new Predicate[pl.size()]);
            pl.clear();
            pl.add(cb.or(predicates));
        }
        log.debug("===buildStringOrLikePredict logged ,the value is : {}", pl.size());
        return pl;
    }

    public static List<Predicate> buildDateComparePredicates(Root root, CriteriaBuilder cb, String dateComparefiled, Date from, Date to) {
        List<Predicate> predicateList = new ArrayList<Predicate>();
        Predicate p1 = null;
        if(from!=null&&to!=null&&from.after(to)){
            Date tmp = from;
            from=to;
            to = tmp;
        }


        if(from!=null){
            p1 = cb.greaterThanOrEqualTo(root.get(dateComparefiled),from);
            predicateList.add(cb.and(p1));
        }

        if(to!=null){
            //当天时间不是从0:0:0开始的 而是截止到23:59:59.999
            long ltime= to.getTime();
            ltime+=(1000*24*60*60-1);
            to = new Date(ltime);
            p1 = cb.lessThanOrEqualTo(root.get(dateComparefiled),to);
            predicateList.add(cb.and(p1));
        }
        return predicateList;
    }

    public static List<Predicate> buildUserEqualsPredicates(Root root, CriteriaBuilder cb, String[] sl_or, String username) {

        Map<String, Object> orMap = new LinkedHashMap<String, Object>();
        if (StringUtils.isNotBlank(username)) {
            Users users = new Users();
            users.setUsername(username);
            for (int i=0;i<sl_or.length;i++){
                orMap.put(sl_or[i], users);
            }
//            orMap.put("author", users);
//            orMap.put("lastUpdateUser", users);
        }

        return convertToOrPredict(buildEqualsAndPredicate(root, cb, sl_or, orMap),cb);
    }

}