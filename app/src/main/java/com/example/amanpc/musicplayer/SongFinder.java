package com.example.amanpc.musicplayer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SongFinder {
    ArrayList<HashMap<String,String >> songList=new ArrayList<>();
    ArrayList<Drawable> imageList=new ArrayList<>();
    ArrayList<HashMap<String,String>> songList1=new ArrayList<>();

    public SongFinder(){}

    public ArrayList<HashMap<String, String>> getSongList(String an) {

        File filename=new File(an);
        if(filename!=null){
            File[] files=filename.listFiles();
            ArrayList<File> arrayList=new ArrayList<>();
            if(files!=null){
                for (File f:files){
                    arrayList.add(f);
                }
                for (int i=0;i<arrayList.size();i++){
                    File a=arrayList.get(i);
                    if(a.isDirectory()){
                        getSongList(a.getPath());
                    }
                    else {
                        if(a.getName().endsWith(".mp3")||a.getName().endsWith(".MP3")){
                            HashMap<String,String> song=new HashMap<String,String>();
                            song.put("name",a.getName().substring(0,a.getName().length()));
                            song.put("index",a.getPath());
                            songList.add(song);}
                    }
                }
            }
        }
        return this.songList;
    }

    public ArrayList<Drawable> getImageList() {

        Log.d("from song finder ", String.valueOf(Player.songList.size()));

        for(int i=0;i<Player.songList.size();i++){
            imageList.add(Player.imageReturner(Player.songList,i));
        }
        Player.songset=true;

        return imageList;
    }

    /*public ArrayList<HashMap<String,String>> getSongList(String a){
        File filename=new File(a);
        if(filename.exists()){
            System.out.println("YEs");
            ArrayList<File> arrayList=new ArrayList<>();
            File file[]=filename.listFiles();
            if(file!=null){
                for(File l:file)
                    arrayList.add(l);
                for (int i=0;i<arrayList.size();i++){
                    File a=arrayList.get(i);
                    if(a.isDirectory())
                        getSongList(a.getPath());
                    else {
                        if(a.getName().endsWith(".mp3")||a.getName().endsWith(".MP3")){
                            HashMap<String,String> song=new HashMap<String,String>();
                            song.put("Song Title",a.getName().substring(0,a.getName().length()));
                            song.put("Song Index",a.getPath());
                            songList.add(song);}

                    }
                }
            }
        }
        return songList;
    }*/
}
