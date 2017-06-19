package com.fjnu.edu.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.food.AddFoodInformation;
import com.fjnu.edu.helper.system.Commonality;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by ZhouShiqiao on 2017/6/6 0006.
 */

public class AddFoodGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AddFoodInformation> list;

    public AddFoodGridAdapter(Context context, ArrayList<AddFoodInformation> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_addfood, null);
            holder = new Holder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_addfood_image);
            holder.text = (TextView) convertView.findViewById(R.id.item_addfood_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.image.setImageBitmap(Commonality.getImageFromAssetsFile(context, list.get(position).getPicture()));
        holder.text.setText(list.get(position).getName());
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
