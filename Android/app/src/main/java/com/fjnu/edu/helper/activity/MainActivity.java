package com.fjnu.edu.helper.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.datebase.MySQLHelper;
import com.fjnu.edu.helper.datebase.MySQLManager;
import com.fjnu.edu.helper.datebase.Util;
import com.fjnu.edu.helper.fragment.FoodMainFragment;
import com.fjnu.edu.helper.fragment.NoteMainFragmnet;
import com.fjnu.edu.helper.fragment.RecipeMainFragment;
import com.fjnu.edu.helper.view.SlidingMenu;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button food;
    private Button recipe;
    private Button note;
    private Button person;

    private FoodMainFragment foodmain;
    private RecipeMainFragment recipemain;
    private NoteMainFragmnet notemain;

    private SlidingMenu menu;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        initview();
        setDefaultFragment();
    }

    private void findview() {
        menu = (SlidingMenu) findViewById(R.id.id_menu);
        food = (Button) findViewById(R.id.main_button_food);
        recipe = (Button) findViewById(R.id.main_button_recipe);
        note = (Button) findViewById(R.id.main_button_note);
    }

    private void initview() {
        food.setOnClickListener(this);
        recipe.setOnClickListener(this);
        note.setOnClickListener(this);
    }

    private void setDefaultFragment() {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        foodmain = new FoodMainFragment();
        transaction.replace(R.id.main_content, foodmain);
        transaction.commit();
    }

    public void toggleMenu(View view) {
        menu.toggle();
    }

    public void onClick(View v) {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.main_button_food:
                if (foodmain == null) {
                    foodmain = new FoodMainFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.main_content, foodmain);
                break;
            case R.id.main_button_recipe:
                if (recipemain == null) {
                    recipemain = new RecipeMainFragment();
                }
                transaction.replace(R.id.main_content, recipemain);
                break;
            case R.id.main_button_note:
                if (notemain == null) {
                    notemain = new NoteMainFragmnet();
                }
                transaction.replace(R.id.main_content, notemain);
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getString(R.string.exit_routine), Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
