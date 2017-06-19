package com.fjnu.edu.helper.message;

/**
 * Created by ZhouShiqiao on 2017/6/19 0019.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fjnu.edu.helper.recipe.SimpleRecipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Json {
    public static Gson gson = new Gson();

    public static String ObjecttoJson(Object object) {
        String jsonstr = null;
        jsonstr = gson.toJson(object);
        return jsonstr;
    }

    public static <T> Object JsontoObject(String JsonStr, Class<T> c) {
        Object object = null;
        object = gson.fromJson(JsonStr, c);
        return object;
    }

    public static <T> String ListtoJson(List<T> list) {
        String jsonstr = null;
        jsonstr = gson.toJson(list);
        return jsonstr;
    }

    public static <T> List<T> JsontoList(String JsonStr, List<T> l) {
        List<T> list = null;
        list = gson.fromJson(JsonStr, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <A, B> String MaptoJson(Map<A, B> map) {
        String jsonstr = null;
        jsonstr = gson.toJson(map);
        return jsonstr;
    }

    public static <A, B> Map<A, B> JsontoMap(String JsonStr, Map<A, B> m) {
        Map<A, B> map = null;
        map = gson.fromJson(JsonStr, new TypeToken<Map<A, B>>() {
        }.getType());
        return map;
    }

    public static String ArraytoJson(String[] args) {
        String jsonstr = gson.toJson(args);
        return jsonstr;
    }

    public static String[] JsontoArray(String JsonStr) {
        String[] array = new String[1];
        array = gson.fromJson(JsonStr, array.getClass());
        return array;
    }
    public static String RecipeListtoJson(ArrayList<SimpleRecipe> list){
        String jsonstr = null;
        jsonstr = gson.toJson(list);
        return jsonstr;
    }
    public static ArrayList<SimpleRecipe> JsontoRecipeList(String JsonStr){
        return gson.fromJson(JsonStr, new TypeToken<List<SimpleRecipe>>() {}.getType());
    }
}