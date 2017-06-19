package com.fjnu.edu.helper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.activity.AddFoodActivity;
import com.fjnu.edu.helper.activity.RecipeActivity;
import com.fjnu.edu.helper.activity.RecipeDetailActivity;
import com.fjnu.edu.helper.adapter.FoodListAdapter;
import com.fjnu.edu.helper.datebase.DBManager;
import com.fjnu.edu.helper.food.MyFood;
import com.fjnu.edu.helper.recipe.SimpleRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhouShiqiao on 2017/5/29 0029.
 */

public class FoodMainFragment extends Fragment {
    private Context context;
    private View view;
    private ListView foodlist;

    private FloatingActionButton editbutton;

    private ArrayList<MyFood> list;
    private FoodListAdapter adapter;

    private DBManager mgr;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onResume() {
        super.onResume();
        list=mgr.QueryMyFood();
        adapter=new FoodListAdapter(context,list);
        foodlist.setAdapter(adapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_food_main, container, false);
        mgr= new DBManager(context);
        findview();
        initview();
        return view;
    }

    private void findview() {
        editbutton = (FloatingActionButton) view.findViewById(R.id.foodmain_button_edit);
        foodlist=(ListView)view.findViewById(R.id.foodmain_listview_myfood);
    }

    private void initview() {
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopupmenu(v);
            }
        });
    }

    private void showpopupmenu(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater()
                .inflate(R.menu.menu_food_main_edit, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.foodmain_edit_add:
                        Intent intent = new Intent(context, AddFoodActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.foodmain_edit_edit:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}
