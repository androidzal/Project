package com.fjnu.edu.helper.datebase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ZhouShiqiao on 2017/6/18 0018.
 */

public class MySQLHelper {
    private static final String REMOTE_IP = "139.199.174.96:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/testDB";
    private static final String USER = "lin";
    private static final String PASSWORD = "lin";

    public static Connection openConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://139.199.174.96:3306/testDB?user=lin&password=lin&useUnicode=true&characterEncoding=UTF-8";//链接数据库语句
            conn = (Connection) DriverManager.getConnection(url); //链接数据库
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }

        return conn;
    }
}
