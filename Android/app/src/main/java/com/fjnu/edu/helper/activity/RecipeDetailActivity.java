package com.fjnu.edu.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.adapter.RecipeMaterialAdapter;
import com.fjnu.edu.helper.adapter.RecipeStepAdapter;
import com.fjnu.edu.helper.message.Client;
import com.fjnu.edu.helper.message.Json;
import com.fjnu.edu.helper.message.MyMessage;
import com.fjnu.edu.helper.recipe.Recipe;
import com.fjnu.edu.helper.system.Commonality;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private Recipe recipe = new Recipe();

    private TextView title;

    private ImageView image;
    private TextView simpleinfo;
    private TextView introduction;
    private ListView materialslist;
    private ListView stepslist;
    private TextView remark;

    private ImageButton back;

    private String id;

    private ArrayList<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        findview();
        initview();
        new Thread(new MyThread()).start();
    }

    private void findview() {
        image = (ImageView) findViewById(R.id.recipedetail_imageview_image);
        simpleinfo = (TextView) findViewById(R.id.recipedetial_textview_simpleinfo);
        introduction = (TextView) findViewById(R.id.recipedetail_textview_introduction);
        materialslist = (ListView) findViewById(R.id.recipedetail_listview_material);
        stepslist = (ListView) findViewById(R.id.recipedetail_listview_steps);
        remark = (TextView) findViewById(R.id.recipedetail_textview_remark);
        title = (TextView) findViewById(R.id.recipedetail_textview_title);
        back=(ImageButton)findViewById(R.id.recipedetail_button_back);
    }

    private void initview() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recipe = (Recipe) Json.JsontoObject((String) msg.obj, recipe.getClass());
                    title.setText(recipe.getRname());
                    introduction.setText(recipe.getIntroduction());
                    remark.setText(recipe.getRemark());
                    String simple = null;
                    simple = recipe.getRname() + "\n";
                    simple = simple + getString(R.string.recipedetail_textview_readytime) + recipe.getPreprationtime() + "\n";
                    simple = simple + getString(R.string.recipedetail_textview_maketime) + recipe.getMaketime() + "\n";
                    simple = simple + getString(R.string.recipedetail_textciew_mealnums) + recipe.getMealsnumbers();
                    simpleinfo.setText(simple);
                    materialslist.setAdapter(new RecipeMaterialAdapter(RecipeDetailActivity.this, recipe.getMaterials()));
                    fixListViewHeight(materialslist);
                    new Thread(new setImageThread()).start();
                    break;
                case 2:
                    image.setImageBitmap((Bitmap) msg.obj);
                    stepslist.setAdapter(new RecipeStepAdapter(RecipeDetailActivity.this, recipe.getSteps(), bitmaps));
                    fixListViewHeight(stepslist);
                    break;
            }
        }

    };

    class MyThread implements Runnable {

        public void run() {
            MyMessage msg = new MyMessage();
            msg.sethead(Client.QUERY_DETAIL);
            msg.setdetail(id);
            String JsonStr = Json.ObjecttoJson(msg);
            try {
                Socket socket = new Socket(getString(R.string.server_ip), getResources().getInteger(R.integer.server_port));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                out.writeUTF(JsonStr);
                out.flush();
                String reJsonStr = in.readUTF();
                MyMessage remsg = (MyMessage) Json.JsontoObject(reJsonStr, msg.getClass());
                Message message = new Message();
                message.what = 1;
                message.obj = remsg.getdetail();
                handler.sendMessage(message);
                msg.sethead(Client.FINISH);
                msg.setdetail(null);
                out.writeUTF(Json.ObjecttoJson(msg));
                out.flush();
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class setImageThread implements Runnable {
        public void run() {
            bitmaps = new ArrayList<Bitmap>();
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                bitmaps.add(Commonality.dowmloadBitmap(recipe.getSteps().get(i).get("steppicture")));
            }
            Message message = new Message();
            message.what = 2;
            message.obj = Commonality.dowmloadBitmap(recipe.getPicture());
            handler.sendMessage(message);
        }
    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

