package com.example.harshalchaudhari.myapplication2.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.harshalchaudhari.myapplication2.Models.Songs;
import com.example.harshalchaudhari.myapplication2.R;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {
    ImageView imageView;
    ImageButton imageButton, pause,ibNext,ibForward;
    MediaPlayer mediaPlayer;
    Handler mHandler;
    SeekBar mSeekBar;

    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        imageView = findViewById(R.id.iv_play_image);
        imageButton = findViewById(R.id.id_play_song_btn);
        pause = findViewById(R.id.id_pause_song_btn);
        ibNext = findViewById(R.id.ibBackward);
        ibForward = findViewById(R.id.ibForward);

        mSeekBar = findViewById(R.id.seekBar);


        Intent intent = getIntent();
        final Songs songs = intent.getParcelableExtra("song");
        int position = intent.getIntExtra("position", 0);
        flag = intent.getIntExtra("flag",-1);

        Picasso.get().load(songs.getCover_image()).fit().into(imageView);

        mediaPlayer = MediaPlayer.create(PlayActivity.this, Uri.parse(songs.getUrl()));

        mediaPlayer.seekTo(position);

        if(flag==0) {
            mediaPlayer.start();
            imageButton.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
        }
        else {
            imageButton.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
        }
            mSeekBar.setProgress(position / 1000);







        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                flag=1;

                imageButton.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                flag = 0;

                imageButton.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            }
        });

        //mHandler.postDelayed(positionRunnable, 1000);

      /* Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer!=null) {
                    mSeekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                }
            }
        },0,1000);*/

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);

                }


            }

        });

       /* new Runnable() {
            @Override
            public void run() {

                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                mSeekBar.setProgress(mCurrentPosition);

                mHandler.postDelayed(this, 1000);
            }
        };*/


       ibForward.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if((mediaPlayer.getCurrentPosition()+5000)<mediaPlayer.getDuration()){
                   mSeekBar.setProgress((mediaPlayer.getCurrentPosition()+5000)/1000);
                   mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
               }
              else if(mediaPlayer!=null&&mediaPlayer.getCurrentPosition()!=mediaPlayer.getDuration()){
                   mediaPlayer.seekTo(mediaPlayer.getDuration());
               }
           }
       });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((mediaPlayer.getCurrentPosition()-5000)>0){
                    mSeekBar.setProgress((mediaPlayer.getCurrentPosition()-5000)/1000);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
                }
                else if(mediaPlayer!=null&&mediaPlayer.getCurrentPosition()!=0){
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });

    }

    Runnable positionRunnable = new Runnable() {
        @Override
        public void run() {

            int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
            mSeekBar.setProgress(mCurrentPosition);

            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            Intent intent = new Intent();

            mSeekBar = null;
            intent.putExtra("position", mediaPlayer.getCurrentPosition());
            intent.putExtra("flag",flag);
            setResult(RESULT_OK, intent);

            mediaPlayer.stop();
            mediaPlayer.release();
        }
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}