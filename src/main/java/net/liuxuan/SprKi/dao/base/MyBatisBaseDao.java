/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.dao.base;

import net.liuxuan.utility.GenericsUtils;
import net.liuxuan.utility.MapResultHandler;
import net.liuxuan.utility.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Moses on 2016/2/5.
 */
public class MyBatisBaseDao<T> {
    private Class<T> type;
    private SqlSession session;

    @SuppressWarnings("unchecked")
    public MyBatisBaseDao(DataSource dataSource){

        type = (Class<T>) GenericsUtils.getActualReflectArgumentClass(this.getClass());
        System.out.println("------------- BaseMybatisDao initialize--------------------------");
        System.out.println("------------- T:" + type.toString());
        try {
            MyBatisUtil myBatisUtil = MyBatisUtil.getInstance(dataSource);
            session = myBatisUtil.getSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getMethodPath(String methodType){
        return getMethodPath(methodType, "");
    }

    private String getMethodPath(String methodType, String methodSuffix){
//        return Constants.MYBATIS_MAPPER_PRIX + methodType + type.getSimpleName() + methodSuffix;
        return "MyBatis_" + methodType + type.getSimpleName() + methodSuffix;
    }

    public void save(T obj) {
        session.insert(getMethodPath("save"), obj);
    }

    public void delete(T obj) {
        session.delete(getMethodPath("delete"), obj);
    }

    public void update(T obj) {
        session.update(getMethodPath("update"), obj);
        //HashMap<String,Object> map = null;
    }

    public T get(Integer id) {
        return session.selectOne(getMethodPath("get"),id);
    }

    public List<T> getList(T entity){
        return session.selectList(getMethodPath("get", "List"), entity);
    }

    public List<Map<String, Object>> getMapList(HashMap<String, Object> map){
        MapResultHandler mh = new MapResultHandler();
        session.select(getMethodPath("get", "MapList"), map, mh);
        return mh.getMappedResults();
    }

    public List<Map<String, Object>> getMapList(T entity){
        MapResultHandler mh = new MapResultHandler();
        session.select(getMethodPath("get", "MapList"), entity, mh);
        return mh.getMappedResults();
    }

    public Long getCount(){
        MapResultHandler mh = new MapResultHandler();
        session.select(getMethodPath("get", "Count"), mh);
        return mh.getCount();
    }
}
