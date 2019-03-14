package com.example.amanpc.musicplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Playlist extends AppCompatActivity {

    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        final Player player=new Player();

        TextView textView=findViewById(R.id.numberOfSong);
        textView.setText(String.valueOf(Player.songList.size()));

        button=findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });

        ListView listView=findViewById(R.id.listview);
        MyAdapter myAdapter=new MyAdapter(Playlist.this,Player.songList,Player.imageList);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(Player.shuffleClicked){

                        Player.position=Player.shuffleList.get(position);
                        player.songPlay1(Player.shuffleList.get(position));
                    }
                    else {
                        Player.position=position;
                        player.songPlay1(position);
                    }
                    Intent intent=new Intent();
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(Playlist.this,e.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("error is ",e.toString());
                }
            }
        });
    }
}
