package com.fjnu.edu.helper.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.adapter.AddFoodGridAdapter;
import com.fjnu.edu.helper.datebase.DBManager;
import com.fjnu.edu.helper.food.AddFoodInformation;
import com.fjnu.edu.helper.food.Food;
import com.fjnu.edu.helper.food.MyFood;
import com.fjnu.edu.helper.system.Commonality;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {
    private SearchView search;
    private GridView grid;
    private ImageButton back;

    private String unit;
    private String position;
    private int storagetime;

    private AddFoodGridAdapter adapter;

    private DBManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mgr = new DBManager(this);

        findview();
        initview();
    }

    private void findview() {
        search = (SearchView) findViewById(R.id.addfood_textview_search);
        grid = (GridView) findViewById(R.id.addfood_gridview_list);
        back=(ImageButton)findViewById(R.id.addfood_button_back);
    }

    private void initview() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setgrid(newText);
                return false;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddFoodInformation afi = (AddFoodInformation) adapter.getItem(position);
                showdialog(afi);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setgrid(String str) {
        if (str.length() > 0)
            adapter = new AddFoodGridAdapter(this, mgr.queryaddfoodinformation(str));
        else
            adapter = new AddFoodGridAdapter(this, new ArrayList<AddFoodInformation>());
        grid.setAdapter(adapter);
    }

    private void showdialog(AddFoodInformation afi) {
        final Dialog dialog = new Dialog(this, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_food, null);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setContentView(dialogView);

        Button confirm = (Button) dialogView.findViewById(R.id.add_food_button_confirm);
        Button cancel = (Button) dialogView.findViewById(R.id.add_food_button_cancel);
        final Spinner units = (Spinner) dialogView.findViewById(R.id.addfood_spinner_unit);
        final Spinner positions = (Spinner) dialogView.findViewById(R.id.addfood_spinner_location);
        TextView foodname = (TextView) dialogView.findViewById(R.id.addfood_textview_foodname);
        final TextView storageinf = (TextView) dialogView.findViewById(R.id.addfood_textview_storageinformaiton);
        final EditText quantity = (EditText) dialogView.findViewById(R.id.addfood_edit_amount);

        dialog.show();

        final Food food = mgr.QueryFoodByNo(afi.getId());

        foodname.setText(food.getName());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storagetime > 0) {
                    mgr.InsertMyfood(new MyFood(food.getMno(),
                            food.getName(),
                            positions.getSelectedItem().toString(),
                            Commonality.getCurrentTime(),
                            Double.valueOf(quantity.getText().toString()),
                            units.getSelectedItem().toString(),
                            null,
                            storagetime));
                    Toast.makeText(AddFoodActivity.this,getString(R.string.addfood_textview_addsucceeed),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(AddFoodActivity.this, getString(R.string.addfood_textview_inadequacy), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] units = getResources().getStringArray(R.array.measurement_unit);
                unit = units[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        positions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] positions = getResources().getStringArray(R.array.storage_position);
                AddFoodActivity.this.position = positions[position];
                switch (position) {
                    case 0:
                        storagetime = food.getStorage3();
                        break;
                    case 1:
                        storagetime = food.getStorage1();
                        break;
                    case 2:
                        storagetime = food.getStorage2();
                        break;
                }
                if (storagetime <= 0)
                    storageinf.setText(getString(R.string.addfood_textview_inadequacy));
                else
                    storageinf.setText(getString(R.string.addfood_textview_storage) + storagetime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

