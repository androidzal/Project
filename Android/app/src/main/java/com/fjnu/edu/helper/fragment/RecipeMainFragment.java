package com.fjnu.edu.helper.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.activity.RecipeActivity;
import com.fjnu.edu.helper.activity.RecipeDetailActivity;
import com.fjnu.edu.helper.adapter.RecipeListAdapter;
import com.fjnu.edu.helper.adapter.RecommendRecipeGridAdapter;
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

/**
 * Created by ZhouShiqiao on 2017/6/2 0002.
 */

public class RecipeMainFragment extends Fragment implements View.OnClickListener {
    private ImageButton search;
    private GridView grid;

    private Context context;
    private View view;

    private ArrayList<SimpleRecipe> lists;
    private ArrayList<Bitmap>bitmaps;
    private RecommendRecipeGridAdapter myadapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onResume(){
        super.onResume();
        new Thread(new SearchRecipeThread()).start();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_recipe_main, container, false);
        findview();
        initview();
        return view;
    }

    private void findview(){
        search=(ImageButton)view.findViewById(R.id.recipemain_button_search);
        grid=(GridView)view.findViewById(R.id.recipemain_gridview_recomend);
    }
    private void initview(){
        search.setOnClickListener(this);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleRecipe sr=(SimpleRecipe)myadapter.getItem(position);
                Intent intent=new Intent(context,RecipeDetailActivity.class);
                intent.putExtra("id",sr.getRno());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recipemain_button_search:
                Intent intent = new Intent(context,RecipeActivity.class);
                context.startActivity(intent);
                break;
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

    class SearchRecipeThread implements Runnable {
        public void run() {
            MyMessage msg = new MyMessage();
            msg.sethead(Client.QUERY_MANY);
            msg.setdetail(Json.ArraytoJson(Commonality.recommend(new String[] {""},15)));
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

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    lists = Json.JsontoRecipeList((String) msg.obj);
                    new Thread(new RecipeMainFragment.DownloadBitmapThread()).start();
                    break;
                case 2:
                    myadapter = new RecommendRecipeGridAdapter(context, lists, bitmaps);
                    grid.setAdapter(myadapter);
                    break;
            }
        }
    };
}
