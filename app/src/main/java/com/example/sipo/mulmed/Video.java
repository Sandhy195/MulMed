package com.example.sipo.mulmed;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;


public class Video extends ActionBarActivity {
    ImageButton btnvback,btnvprev,btnvnext;
            ImageButton btnvplay;
    ListView listV;
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    VideoView mVideoView;
    DisplayMetrics dm;
    static int index3gp=0;
    int stopPosition;
    final String[] contentvid = {"URBAN RUINS","Angger Dimas","Ultimate Goat"};
    final String[] PATH = {"storage/sdcard1/vids/urbanruins.3gp","storage/sdcard1/vids/anggerdimas.3gp","storage/sdcard1/vids/ultimategoat.3gp"};
    ArrayAdapter<String> adapter;
    static boolean but=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getSupportActionBar().hide();

        Toast.makeText(Video.this, "Choose your Video First", Toast.LENGTH_SHORT).show();

        btnvback = (ImageButton)findViewById(R.id.btnVback);
        listV = (ListView)findViewById(R.id.listViewVid);
        btnvnext = (ImageButton)findViewById(R.id.btnVNext);
        btnvplay = (ImageButton)findViewById(R.id.btnVPlay);
        btnvprev = (ImageButton)findViewById(R.id.btnVPrev);
        mVideoView = (VideoView)findViewById(R.id.videoView);
        dm = new DisplayMetrics();

        adapter = new ArrayAdapter<String>(Video.this, android.R.layout.simple_list_item_2,android.R.id.text1,contentvid);
        listV.setAdapter(adapter);
        mVideoView.requestFocus();

        btnvplay.setEnabled(but);
        btnvnext.setEnabled(but);
        btnvprev.setEnabled(but);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getInit(position);
                btnvplay.setImageResource(R.drawable.pause);
            }
        });

        btnvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index3gp = 0;
                but = false;
                Video.this.finish();
                //Intent intent = new Intent(getBaseContext(),Menumenu.class);
                //startActivity(intent);
            }
        });

        btnvplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mVideoView.isPlaying()){
                   onResume();
                    btnvplay.setImageResource(R.drawable.pause);
                }else if (mVideoView.isPlaying()){
                    onPause();
                    btnvplay.setImageResource(R.drawable.play);
                }

            }
        });

        btnvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index3gp==2){
                    getInit(index3gp-2);
                    index3gp = 0;
                }else if(index3gp==0){
                    getInit(index3gp+1);
                    index3gp = 1;
                }else if (index3gp==1){
                    getInit(index3gp+1);
                    index3gp =2;
                }
                btnvplay.setImageResource(R.drawable.pause);
            }
        });

        btnvprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index3gp==0){
                    getInit(index3gp+2);
                    index3gp = 2;
                }else if (index3gp==2){
                    getInit(index3gp-1);
                    index3gp = 1;
                }else if (index3gp==1){
                    getInit(index3gp-1);
                    index3gp = 0;
                }

                btnvplay.setImageResource(R.drawable.pause);
            }
        });

    }
    @SuppressLint("SdCardPath")
    public void getInit(int videoindx){
        but = true;
        btnvplay.setEnabled(but);
        btnvnext.setEnabled(but);
        btnvprev.setEnabled(but);
        mVideoView.isPlaying();
        index3gp = videoindx;
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        mVideoView.setVideoPath(PATH[index3gp]);
        mVideoView.setMinimumHeight(height);
        mVideoView.setMinimumWidth(width);
        mVideoView.start();
    }

    @Override
    public void onPause() {

        super.onPause();
        stopPosition = mVideoView.getCurrentPosition(); //stopPosition is an int
        mVideoView.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        mVideoView.seekTo(stopPosition);
        mVideoView.start(); //Or use resume() if it doesn't work. I'm not sure
    }


}
