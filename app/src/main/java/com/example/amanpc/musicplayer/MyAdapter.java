package com.example.amanpc.musicplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class MyAdapter extends BaseAdapter{

    Activity myActivity;
    ArrayList<HashMap<String,String>> songList;
    ArrayList<Drawable> imageList;

    MyAdapter(Activity myActivity,ArrayList<HashMap<String,String>> songList,ArrayList<Drawable> imageList){
        this.myActivity=myActivity;
        this.songList=songList;
        this.imageList=imageList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=myActivity.getLayoutInflater();
        View view=inflater.inflate(R.layout.customplaylist,null,false);

        int p;
        if(Player.shuffleClicked){
            p=Player.shuffleList.get(position);
        }
        else {
            p=position;
        }

        ImageView imageView=view.findViewById(R.id.songImage);
        TextView textView=view.findViewById(R.id.songName);


        //imageView.setImageDrawable(imageList.get(position));
        Drawable drawable=imageList.get(p);
        if (drawable!=null){
            imageView.setImageDrawable(drawable);
        }
        else imageView.setImageResource(R.drawable.songicon);

        textView.setText(songList.get(p).get("name"));
        return view;
    }
}
