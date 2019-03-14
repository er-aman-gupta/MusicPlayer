/*
package com.example.amanpc.musicplayer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SongFinder songFinder=new SongFinder();

        ArrayList<HashMap<String,String >> songList=new ArrayList<>();
        ArrayList<Bitmap> imageList=new ArrayList<>();

        View view=inflater.inflate(R.layout.playlist,container,false);
        ListView listView=view.findViewById(R.id.playlist);
        ArrayList<HashMap<String,String >> songListTemp = new ArrayList<>();
        songListTemp=songFinder.getSongList("/storage/EC94-7BC1/Music");
        for(int i=0;i<songListTemp.size();i++){
            HashMap<String ,String> local=songListTemp.get(i);
            songList.add(local);
        }
        */
/*imageList=songFinder.getImageList();
        MyAdapter myAdapter=new MyAdapter(getActivity(),songList,imageList);
        listView.setAdapter(myAdapter);*//*

        return view;
    }
}
*/
