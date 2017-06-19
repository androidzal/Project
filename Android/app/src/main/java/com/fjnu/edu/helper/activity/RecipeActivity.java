package com.fjnu.edu.helper.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.adapter.RecipeListAdapter;
import com.fjnu.edu.helper.datebase.MySQLManager;
import com.fjnu.edu.helper.message.Client;
import com.fjnu.edu.helper.message.Json;
import com.fjnu.edu.helper.message.MyMessage;
import com.fjnu.edu.helper.recipe.SimpleRecipe;
import com.fjnu.edu.helper.system.Commonality;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private SearchView search;
    private Button confirm;
    private ListView recipelist;
    private ImageButton back;

    private ArrayList<SimpleRecipe> lists ;
    private ArrayList<Bitmap> bitmaps;
    private RecipeListAdapter myadapter;

    private String searchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        findview();
        initview();
    }

    private void findview() {
        search = (SearchView) findViewById(R.id.recipemain_searchview_search);
        confirm = (Button) findViewById(R.id.recipemain_button_search);
        recipelist = (ListView) findViewById(R.id.recipemain_listview_recipe);
        back=(ImageButton)findViewById(R.id.recipemain_button_back);
    }

    private void initview() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchtext = newText;
                return false;
            }
        });
        confirm.setOnClickListener(this);
        recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleRecipe sr=(SimpleRecipe)myadapter.getItem(position);
                Intent intent=new Intent(RecipeActivity.this,RecipeDetailActivity.class);
                intent.putExtra("id",sr.getRno());
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recipemain_button_search:
                if (searchtext != null | search.equals("")) {
                    Thread thread = new Thread(new SearchRecipeThread());
                    thread.start();
                }
                break;
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    lists = Json.JsontoRecipeList((String) msg.obj);
                    new Thread(new DownloadBitmapThread()).start();
                    break;
                case 2:
                    myadapter = new RecipeListAdapter(lists, RecipeActivity.this, bitmaps);
                    recipelist.setAdapter(myadapter);
                    break;
            }
        }
    };

    class SearchRecipeThread implements Runnable {
        public void run() {
            MyMessage msg = new MyMessage();
            msg.sethead(Client.QUERY);
            msg.setdetail(searchtext);
            if(lists==null)
                msg.setRaw(0);
            else
            msg.setRaw(lists.size());
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

    class DownloadBitmapThread implements Runnable {
        public void run() {
            bitmaps = new ArrayList<Bitmap>();
            for (int i = 0; i < lists.size(); i++) {
                Bitmap bitmap = Commonality.dowmloadBitmap(lists.get(i).getPicture());
                bitmaps.add(bitmap);
            }
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    }
}
