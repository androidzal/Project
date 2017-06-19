package com.fjnu.edu.helper.datebase;


import com.fjnu.edu.helper.recipe.Recipe;
import com.fjnu.edu.helper.recipe.SimpleRecipe;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ZhouShiqiao on 2017/6/18 0018.
 */

public class MySQLManager {
    public static ArrayList<SimpleRecipe> Query(String name) {
        ArrayList<SimpleRecipe> list = new ArrayList<SimpleRecipe>();
        String sql = "select rno,rname,introduction,picture from recipe where rname like '%"+name+"%'";
        try {
            Statement statement = MySQLHelper.openConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                SimpleRecipe sr = new SimpleRecipe();
                sr.setRno(result.getString("rno"));
                sr.setRname(result.getString("rname"));
                sr.setIntroduction(result.getString("introduction"));
                sr.setPicture(result.getString("picture"));
                list.add(sr);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<SimpleRecipe> Query(String[] id) {
        return null;
    }

    public static Recipe QueryDetail(String id) {
        return null;
    }


}
