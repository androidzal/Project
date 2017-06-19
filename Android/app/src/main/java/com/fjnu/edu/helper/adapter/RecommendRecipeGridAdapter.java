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
import com.fjnu.edu.helper.food.AddFoodInformation;
import com.fjnu.edu.helper.recipe.SimpleRecipe;
import com.fjnu.edu.helper.system.Commonality;

import java.util.ArrayList;

/**
 * Created by ZhouShiqiao on 2017/6/19 0019.
 */

public class RecommendRecipeGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SimpleRecipe> list;
    private ArrayList<Bitmap> bitmaps;

    public RecommendRecipeGridAdapter(Context context, ArrayList<SimpleRecipe> list, ArrayList<Bitmap> bitmaps) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendRecipeGridAdapter.Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_recommendrecipe, null);
            holder = new RecommendRecipeGridAdapter.Holder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_recommendrecipe_image);
            holder.text = (TextView) convertView.findViewById(R.id.item_recommendrecipe_text);
            convertView.setTag(holder);
        } else {
            holder = (RecommendRecipeGridAdapter.Holder) convertView.getTag();
        }
        holder.image.setImageBitmap(bitmaps.get(position));
        holder.text.setText(list.get(position).getRname());
        return convertView;
    }

    public class Holder {
        private ImageView image;
        private TextView text;

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }
    }
}
