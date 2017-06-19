package com.fjnu.edu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fjnu.edu.recipe.Recipe;
import com.fjnu.edu.recipe.SimpleRecipe;

public class DBHelper {
	private static String url = "jdbc:mysql://139.199.174.96:3306/testDB";
	private static String name = "com.mysql.jdbc.Driver";
	private static String user = "lin";
	private static String password = "lin";

	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return conn;
	}

	public static Recipe QueryDetail(String id) {
		Recipe recipe = null;
		String sql = "select rno," + "rname," + "cast(introduction as char) as introduction," + "picture, "
				+ "preparationtime," + "maketime," + "mealsnumbers,"
				+ "cast(remark as char) as remark from recipe where rno = '" + id + "'";
		try {
			Statement statement = getConn().createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				recipe = new Recipe();
				recipe.setRno(result.getString("rno"));
				recipe.setRname(result.getString("rname"));
				recipe.setIntroduction(result.getString("introduction"));
				recipe.setPicture(result.getString("picture"));
				recipe.setMaketime(result.getString("maketime"));
				recipe.setPreprationtime(result.getString("preparationtime"));
				recipe.setMealsnumbers(result.getString("mealsnumbers"));
			}
			recipe.setMaterials(QueryRecipeMaterial(id));
			recipe.setSteps(QueryRecipeStep(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipe;
	}

	public static ArrayList<SimpleRecipe> Query(String name, int raw) {
		ArrayList<SimpleRecipe> list = new ArrayList<SimpleRecipe>();
		String sql = "select rno,rname,picture from recipe where rname like '%" + name + "%' limit " + raw + ",10";
		try {
			Statement statement = getConn().createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				SimpleRecipe sr = new SimpleRecipe();
				sr.setRno(result.getString("rno"));
				sr.setRname(result.getString("rname"));
				sr.setPicture(result.getString("picture"));
				list.add(sr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<SimpleRecipe> Query(String[] id) {
		ArrayList<SimpleRecipe> list = new ArrayList<SimpleRecipe>();
		for (int i = 0; i < id.length; i++) {
			String no = id[i];
			String sql = "select rno,rname,picture from recipe where rno = '" + no + "'";
			try {
				Statement statement = getConn().createStatement();
				ResultSet result = statement.executeQuery(sql);
				while (result.next()) {
					SimpleRecipe recipe = new SimpleRecipe();
					recipe.setRno(result.getString("rno"));
					recipe.setRname(result.getString("rname"));
					recipe.setPicture(result.getString("picture"));
					list.add(recipe);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static ArrayList<Map<String, String>> QueryRecipeMaterial(String id) {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select material,amount from recipematerial where rno ='" + id + "'";
		Statement statement;
		try {
			statement = getConn().createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("material", result.getString("material"));
				map.put("amount", result.getString("amount"));
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<Map<String, String>> QueryRecipeStep(String id) {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select cast(stepdetail as char) as detail,steppicture from recipestep where rno ='" + id
				+ "' order by stepno";
		Statement statement;
		try {
			statement = getConn().createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("detail", result.getString("detail"));
				map.put("steppicture", result.getString("steppicture"));
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
