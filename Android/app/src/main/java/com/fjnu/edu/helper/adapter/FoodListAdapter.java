package com.fjnu.edu.helper.adapter;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                showeditdialog(list.get(position),position);
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

    private void showeditdialog(MyFood food,int position) {
        final EditText et = new EditText(context);
        final MyFood mf = food;
        final int p =position;
        et.setHint(context.getString(R.string.foodmain_dialog_hint) + "(" + context.getString(R.string.foodmain_textview_foodresidue) + food.getResidue() + ")");
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(et.getText().toString())>mf.getResidue())
                    et.setText((int)mf.getResidue()+"");
            }
        });
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.foodmain_dialog_title))
                .setIcon(R.drawable.edit)
                .setView(et)
                .setPositiveButton(context.getString(R.string.foodmain_dialog_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(0<Double.valueOf(et.getText().toString())&&Double.valueOf(et.getText().toString())<mf.getResidue()){
                            mgr.UpdateMyfood(mf.getAddtime(),(int)(mf.getResidue()-Integer.parseInt(et.getText().toString())));
                            list.get(p).setResidue(mf.getResidue()-Integer.parseInt(et.getText().toString()));
                            notifyDataSetChanged();
                            Toast.makeText(context,context.getString(R.string.foodmain_dialog_used),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(Double.valueOf(et.getText().toString())==mf.getResidue()){
                            mgr.DeleteMyFood(mf.getAddtime());
                            list.remove(p);
                            notifyDataSetChanged();
                            Toast.makeText(context,context.getString(R.string.foodmain_dialog_used),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(context,context.getString(R.string.Parameters_of_the_abnormal),Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(context.getString(R.string.foodmain_dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
