package com.example.amanpc.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity {


    int progr;
    static boolean OneTimeOnly;
    static boolean playClicked,repeatClicked;
    static int position,cposition;
    static double currentTime=0;
    static boolean songset;
    static boolean shuffleClicked;
    static MediaPlayer mediaPlayer=new MediaPlayer();
    static ImageView imageView;
    SongFinder songFinder=new SongFinder();
    static ArrayList<HashMap<String,String>> songListTemp=new ArrayList<>();
    static ArrayList<HashMap<String,String>> songList=new ArrayList<>();
    static ArrayList<Drawable> imageList=new ArrayList<>();
    static TextView textView,currentTimeText,totalTimeText;
    ImageButton imageButton;
    Button previous,play,next;
    static Handler handler=new Handler();
    static SeekBar seekBar;
    static ArrayList<Integer> shuffleList=new ArrayList<>();
    ImageView shuffleButton;
    ImageView repeat;
    public void shuffleGenerator(){
        Random random=new Random();

        int size=songList.size();

        //int lsize=0;
        for (int i=0;i<size;i++){
            int r=random.nextInt(size);
            if(!shuffleList.contains(r)){
                shuffleList.add(r);

            }
            else {
                i--;
            }
        }

        /*while (lsize<=size){
            int r=random.nextInt(size);
            if(!shuffleList.contains(r)){
                shuffleList.add(r);
                lsize++;
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==Activity.RESULT_OK){
                textView.setText(songList.get(position).get("name"));
                Drawable drawable=imageList.get(position);
                if (drawable!=null){
                    imageView.setImageDrawable(drawable);
                }
                else imageView.setImageResource(R.drawable.songicon);
                Player.timeSet();
                play.setBackgroundResource(R.mipmap.pause);
                playClicked=true;
            }
            if(resultCode==Activity.RESULT_CANCELED){

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //shuffleGenerator();

        shuffleButton=findViewById(R.id.shuffleButton);
        imageView=findViewById(R.id.image);
        imageButton=findViewById(R.id.playlistButton);
        textView=findViewById(R.id.songName);
        previous=findViewById(R.id.previous);
        //rewind=findViewById(R.id.rewind);
        play=findViewById(R.id.play);
        //forward=findViewById(R.id.forward);
        next=findViewById(R.id.next);
        currentTimeText=findViewById(R.id.currentTimeText);
        totalTimeText=findViewById(R.id.totalTimeText);
        seekBar=findViewById(R.id.seekBarId);
        repeat=findViewById(R.id.repeat);




        textView.setText(songList.get(position).get("name"));
        imageView.setImageDrawable(imageList.get(0));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(Player.this,Playlist.class),1);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playClicked==false){
                songPlay(position);

            }
            else
                {
                    currentTime=mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    play.setBackgroundResource(R.mipmap.play);
                    playClicked=false;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position+1>songList.size()-1){
                    position=0;
                }
                else
                    position++;
                currentTime=0;
                if(shuffleClicked){
                    cposition=shuffleList.get(position);
                }
                else {
                    cposition=position;
                }
                songPlay(cposition);
                textView.setText(songList.get(cposition).get("name"));
                Drawable drawable=imageList.get(cposition);
                if (drawable!=null){
                    imageView.setImageDrawable(drawable);
                }
                else imageView.setImageResource(R.drawable.songicon);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position-1<0){
                    position=songList.size()-1;
                }
                else position--;
                currentTime=0;
                if(shuffleClicked){
                    cposition=shuffleList.get(position);
                }
                else {
                    cposition=position;
                }
                songPlay(cposition);
                textView.setText(songList.get(cposition).get("name"));
                Drawable drawable=imageList.get(cposition);
                if (drawable!=null){
                    imageView.setImageDrawable(drawable);
                }
                else imageView.setImageResource(R.drawable.songicon);
            }
        });

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleClicked){
                    shuffleClicked=false;
                    shuffleButton.setImageResource(R.mipmap.shuffle);
                    shuffleList.clear();
                }
                else {shuffleClicked=true;
                shuffleButton.setImageResource(R.mipmap.shufflecolored);
                shuffleGenerator();
            }

            }
        }

        );

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(repeatClicked){
                    repeatClicked=false;
                    repeat.setImageResource(R.mipmap.repeat);
                }
                else {
                    repeatClicked=true;
                    repeat.setImageResource(R.mipmap.repeatcolored);
                }

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progr=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(progr);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(repeatClicked){
                    mediaPlayer.start();
                }
                else if(shuffleClicked){
                    position++;
                    cposition=shuffleList.get(position);
                    songPlay(cposition);
                }
                else {
                    position++;
                    cposition=position;
                    songPlay(cposition);
                }
            }
        });


    }
    public static Drawable imageReturner(ArrayList<HashMap<String,String>>songList,int index){
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        byte[] rawArt;
        Bitmap art=null;

        BitmapFactory.Options bfo=new BitmapFactory.Options();


        mediaMetadataRetriever.setDataSource(songList.get(index).get("index"));
        rawArt=mediaMetadataRetriever.getEmbeddedPicture();

        if(null!=rawArt){
            art=BitmapFactory.decodeByteArray(rawArt,0,rawArt.length,bfo);
            Bitmap a=Bitmap.createScaledBitmap(art,250,250,false);
            art=null;
            Drawable d=new BitmapDrawable(Resources.getSystem(),a);
            a=null;
            return d;
        }

        return null;
    }
    public void songPlay(int position){
        try {
            handler.removeCallbacks(updateSongTime);
            textView.setText(songList.get(position).get("name"));
            Drawable drawable=imageList.get(position);
            if (drawable!=null){
                imageView.setImageDrawable(drawable);
            }
            else imageView.setImageResource(R.drawable.songicon);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songList.get(position).get("index"));
            mediaPlayer.prepare();
            mediaPlayer.seekTo((int)currentTime);
            mediaPlayer.start();
            timeSet();
            play.setBackgroundResource(R.mipmap.pause);
            playClicked=true;
        } catch (IOException e) {
            Toast.makeText(Player.this,e.toString() ,Toast.LENGTH_LONG).show();
        }
    }

    public void songPlay1(int position){
        try {
            handler.removeCallbacks(updateSongTime);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songList.get(position).get("index"));
            mediaPlayer.prepare();
            mediaPlayer.seekTo((int)currentTime);
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(Player.this,e.toString() ,Toast.LENGTH_LONG).show();
        }
    }

    public static void timeSet(){
        double stime=mediaPlayer.getCurrentPosition();
        double finalTime=mediaPlayer.getDuration();

        seekBar.setMax((int)finalTime);


        currentTimeText.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) stime),
                TimeUnit.MILLISECONDS.toSeconds((long) stime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                stime))));

        totalTimeText.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime))));


        //seekBar.setProgress((int)stime);
        handler.postDelayed(updateSongTime,100);

    }

    public static Runnable updateSongTime=new Runnable() {
        @Override
        public void run() {
            double startTime = mediaPlayer.getCurrentPosition();
            currentTimeText.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime);
            Log.d("time is ",String.valueOf((int)startTime));
            handler.postDelayed(updateSongTime,100);

        }

        };
    }




class MyTask extends Thread{
    public void run(){

        SongFinder songFinder=new SongFinder();
        SongFinder songFinder1=new SongFinder();
        File file=new File("/storage");
        if(file.exists()){
            Player.songListTemp=songFinder.getSongList("/storage");
        }
        for(int i=0;i<Player.songListTemp.size();i++){
            HashMap<String,String> local=Player.songListTemp.get(i);
            Player.songList.add(local);
        }
        Log.d("song list size ", String.valueOf(Player.songList.size()));

        ArrayList<HashMap<String,String>> list2=new ArrayList<>();
        File file1=new File("/sdcard");
        if(file1.exists()){
            list2=songFinder1.getSongList("/sdcard");
        }
        Log.d("song list size ", String.valueOf(list2.size()));
        for (int i=0;i<list2.size();i++)
        {
            HashMap<String,String> local=list2.get(i);
            Player.songList.add(local);

        }
        Log.d("song list size2 ", String.valueOf(Player.songList.size()));
        Player.imageList=songFinder.getImageList();
        Player.OneTimeOnly=true;
    }
}




