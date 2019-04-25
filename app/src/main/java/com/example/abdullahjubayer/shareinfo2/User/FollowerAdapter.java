package com.example.abdullahjubayer.shareinfo2.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abdullahjubayer.shareinfo2.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class FollowerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> imageList;
    ArrayList<String> typeList;
    LayoutInflater layoutInflater;

    public FollowerAdapter(Context context,ArrayList<String> nameList, ArrayList<String> imageList, ArrayList<String> typeList) {
        this.context=context;
        this.nameList=nameList;
        this.imageList=imageList;
        this.typeList=typeList;
    }

    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.follower_item_design,parent,false);
        }

        TextView name=convertView.findViewById(R.id.follower_name);
        CircleImageView image=convertView.findViewById(R.id.follower_image);
        TextView type=convertView.findViewById(R.id.follower_type);

        name.setText(nameList.get(position));
        Glide.with(context).load(imageList.get(position)).into(image);
        type.setText(typeList.get(position));

        return convertView;
    }
}
