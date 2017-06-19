package com.fjnu.edu.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fjnu.edu.helper.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ZhouShiqiao on 2017/6/20 0020.
 */

public class RecipeMaterialAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Map<String,String>> list;

    public RecipeMaterialAdapter(Context context, ArrayList<Map<String, String>> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_material, null);
            holder = new Holder();
            holder.material = (TextView) convertView.findViewById(R.id.item_material_material);
            holder.quantity = (TextView) convertView.findViewById(R.id.item_material_quantity);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.material.setText(list.get(position).get("material"));
        holder.quantity.setText(list.get(position).get("amount"));
        return convertView;
    }
    class Holder{
        TextView material;
        TextView quantity;
    }
}
