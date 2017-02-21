package com.example.sipo.mulmed;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.R.layout;


public class Musik extends ActionBarActivity {

    ListView listview;
    final String[] listcontent = {"Arem Arem","Balen","Tukang Becak"};
    final int[] musiclist = {R.raw.aremarem,R.raw.balen,R.raw.tukangbecak};
    private MediaPlayer mp;
    ArrayAdapter<String> adapter;
    static int indexmp3 = 0;
    ImageButton btnplay,btnprev,btnnext,btnback;
    SeekBar seekbar;
    private double startTime = 0;
    private double finalTime = 0;
    TextView txtstart,txtfinal;
    private Handler myHandler = new Handler();;
    public static int oneTimeOnly = 0;
    static boolean but = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musik);
        getSupportActionBar().hide();

        Toast.makeText(Musik.this,"Choose your Music First",Toast.LENGTH_SHORT).show();

        mp = new MediaPlayer();
        mp.reset();
        btnplay = (ImageButton)findViewById(R.id.btnPlay);
        btnnext = (ImageButton)findViewById(R.id.btnNext);
        btnprev = (ImageButton)findViewById(R.id.btnPrev);
        listview = (ListView)findViewById(R.id.listView);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        txtstart = (TextView)findViewById(R.id.txtStart);
        txtfinal = (TextView)findViewById(R.id.txtFinal);
        btnback = (ImageButton)findViewById(R.id.btnBack);


        adapter = new ArrayAdapter<String>(Musik.this, layout.simple_list_item_1,listcontent);
        listview.setAdapter(adapter);

        seekbar.setClickable(false);


        btnplay.setEnabled(but);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                indexmp3=0;
                but = false;
                Musik.this.finish();
                //Intent intent = new Intent(getBaseContext(),Menumenu.class);
                //startActivity(intent);
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mp.isPlaying()){
                    try{
                        mp.prepare();
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    btnplay.setImageResource(R.drawable.pause);
                    mp.start();

                   // mp.pause();
                   // btnplay.setText("Pause");
                   // Startmp3();

                }else if (mp.isPlaying()){
                    btnplay.setImageResource(R.drawable.play);
                    mp.pause();
                }

            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.reset();
                if (indexmp3 == 2){
                    mp = MediaPlayer.create(Musik.this,musiclist[indexmp3-2]);
                    mp.start();
                    indexmp3 = 0;
                }else{
                    mp = MediaPlayer.create(Musik.this,musiclist[indexmp3+1]);
                    mp.start();
                    indexmp3++;
                }

            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.reset();
                if (indexmp3 == 0){
                    mp = MediaPlayer.create(Musik.this,musiclist[indexmp3+2]);
                    mp.start();
                    indexmp3 = 2;
                }else{
                    mp = MediaPlayer.create(Musik.this,musiclist[indexmp3-1]);
                    mp.start();
                    indexmp3--;
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playsong(position);
                btnplay.setImageResource(R.drawable.pause);

            }
        });

    }

    public  void Playsong(int Songindex){
        but = true;
        btnplay.setEnabled(but);
        indexmp3 = Songindex;
        mp.reset();
        mp = MediaPlayer.create(Musik.this,musiclist[indexmp3]);
        mp.start();

        finalTime = mp.getDuration();
        startTime = mp.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        txtfinal.setText(String.format("%d : %d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );
        txtstart.setText(String.format("%d : %d",
                        TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long)startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime)))
        );
        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);


    }
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mp.getCurrentPosition();
            txtstart.setText(String.format("%d : %d",
                            TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long)startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this,100);
        }
    };

    /*
    public void Startmp3(){
        mp = MediaPlayer.create(this,musiclist[indexmp3]);
        try{
            mp.prepare();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        mp.start();
    }
    */

    @Override
    public void onDestroy(){
        super.onDestroy();
        mp.release();
    }

    @Override
    public void onStart(){
        super.onStart();
        oneTimeOnly = 0;
    }


}
