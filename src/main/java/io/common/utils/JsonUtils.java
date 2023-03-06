package io.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * 
 */
public  class JsonUtils {


    public static List<String> JsonToList(String jsonString){
        return JSON.parseArray(jsonString, String.class);
    }
}
