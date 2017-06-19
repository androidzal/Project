package com.fjnu.edu.helper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.activity.RecipeActivity;
import com.fjnu.edu.helper.message.Json;
import com.fjnu.edu.helper.recipe.Recipe;
import com.fjnu.edu.helper.recipe.SimpleRecipe;
import com.fjnu.edu.helper.system.Commonality;

import java.util.ArrayList;

/**
 * Created by ZhouShiqiao on 2017/6/18 0018.
 */

public class RecipeListAdapter extends BaseAdapter {
    private ArrayList<SimpleRecipe> list;
    private ArrayList<Bitmap> bitmaps;
    private Context context;

    public RecipeListAdapter(ArrayList<SimpleRecipe> list, Context context, ArrayList<Bitmap> bitmaps) {
        this.list = list;
        this.context = context;
        this.bitmaps = bitmaps;
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
        final RecipeListAdapter.Holder holder = new RecipeListAdapter.Holder();
        convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_recipelist,
                null);
        holder.image = (ImageView) convertView.findViewById(R.id.item_recipelist_image);
        holder.name = (TextView) convertView.findViewById(R.id.item_recipelist_name);
        holder.image.setImageBitmap(bitmaps.get(position));
        holder.name.setText(list.get(position).getRname());
        return convertView;
    }

    class Holder {
        ImageView image;
        TextView name;
    }

}
