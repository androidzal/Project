package com.fjnu.edu.helper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjnu.edu.helper.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ZhouShiqiao on 2017/6/20 0020.
 */

public class RecipeStepAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Map<String,String>> list;
    private ArrayList<Bitmap> bitmaps;

    public RecipeStepAdapter(Context context, ArrayList<Map<String, String>> list,ArrayList<Bitmap> bitmaps) {
        this.context = context;
        this.list = list;
        this.bitmaps=bitmaps;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_step, null);
            holder = new Holder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_step_image);
            holder.text = (TextView) convertView.findViewById(R.id.item_step_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.image.setImageBitmap(bitmaps.get(position));
        holder.text.setText(list.get(position).get("detail"));
        return convertView;
    }
    class Holder{
        ImageView image;
        TextView text;
    }
}
