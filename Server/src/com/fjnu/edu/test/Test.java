package com.fjnu.edu.test;

import com.fjnu.edu.database.DBHelper;
import com.fjnu.edu.message.Json;
import com.fjnu.edu.recipe.Recipe;

public class Test {
public static void main(String[] args){
	String str=null;
	Recipe recipe =DBHelper.QueryDetail("1001302");
	str=Json.ObjecttoJson(recipe);
	System.out.println(str);
}
}
