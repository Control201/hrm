package com.hrm.util;


import java.util.HashMap;
import java.util.List;

/**
 * @Description:封装list数据并返回layerUI数据
 */
public class Layui  extends HashMap<String, Object> {

    public static Layui data(Long count,List<?> data){
        Layui r = new Layui();
        r.put("code", 0);
        r.put("msg", "");
        r.put("count", count);
        r.put("data", data);
        return r;
    }
}