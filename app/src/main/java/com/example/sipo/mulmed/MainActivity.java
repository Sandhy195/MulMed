package com.example.sipo.mulmed;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class MainActivity extends Activity{

    protected boolean _active = true;
    protected int _splashtime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread splashTred = new Thread(){
            public void run(){
                try{
                    int waited = 0;
                    while(_active&&(waited<_splashtime)){
                        sleep(100);
                        if (_active){
                            waited+=100;
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    finish();
                    Intent newIntent = new Intent(MainActivity.this,Menumenu.class);
                    startActivityForResult(newIntent,0);
                }
            }

        };
        splashTred.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()== MotionEvent.ACTION_DOWN){
            _active = false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
