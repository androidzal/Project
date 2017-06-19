package com.fjnu.edu.helper.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.datebase.DBManager;
import com.fjnu.edu.helper.food.MyFood;
import com.fjnu.edu.helper.system.Commonality;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ZhouShiqiao on 2017/6/2 0002.
 */

public class FoodListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyFood> list = null;
    private DBManager mgr;

    public FoodListAdapter(Context context, ArrayList<MyFood> list) {
        this.context = context;
        this.list = list;
        mgr = new DBManager(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_myfoodlist,
                null);
        holder.foodimage = (ImageView) convertView.findViewById(R.id.item_myfoodlist_foodimage);
        holder.foodname = (TextView) convertView.findViewById(R.id.item_myfoodlist_foodname);
        holder.foodresidue = (TextView) convertView.findViewById(R.id.item_myfoodlist_foodresidue);
        holder.inputdate = (TextView) convertView.findViewById(R.id.item_myfoodlist_inputdate);
        holder.foodfreshness=(TextView)convertView.findViewById(R.id.item_myfoodlist_foodfreshness);
        holder.editbutton = (ImageButton) convertView.findViewById(R.id.item_myfoodlist_editbutton);
        holder.deletebutton = (ImageButton) convertView.findViewById(R.id.item_myfoodlist_deletebutton);

        holder.foodimage.setImageBitmap(Commonality.getImageFromAssetsFile(context, list.get(position).getImage()));
        holder.foodname.setText(list.get(position).getName());
        holder.foodresidue.setText(context.getString(R.string.foodmain_textview_foodresidue) +
                list.get(position).getResidue() + list.get(position).getUnit());
        holder.inputdate.setText(list.get(position).getAddtime());
        holder.foodfreshness.setText(list.get(position).getPosition()+","+
                context.getString(R.string.foodmain_textview_remainingtime)+list.get(position).getFreshness());

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showeditdialog();
            }
        });
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgr.DeleteMyFood(list.get(position).getAddtime());
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class Holder {
        private ImageView foodimage;
        private TextView foodname;
        private TextView foodresidue;
        private TextView inputdate;
        private TextView foodfreshness;
        private ImageButton editbutton;
        private ImageButton deletebutton;
    }

    private void showeditdialog(){

    }
}
