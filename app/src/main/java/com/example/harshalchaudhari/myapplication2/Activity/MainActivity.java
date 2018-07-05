

    /*
    *
    *..... Splash Activity - Gaurav
    *..... RecyclerView & UI - pooja & harshal,Gaurav
    *..... Retrofit -Manav,shivangi, kalyani
    *..... Media Player - Swati , prerena
    *..... SeekBar - manav
    * */


package com.example.harshalchaudhari.myapplication2.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;


import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshalchaudhari.myapplication2.Adapters.MediaRecyclerAdapter;
import com.example.harshalchaudhari.myapplication2.Models.Songs;
import com.example.harshalchaudhari.myapplication2.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1552;
    private RecyclerView recyclerView;
    private MediaRecyclerAdapter adapter;
    private List<Songs> list;
    private RelativeLayout relativeLayout;
    private ImageView image_play;
    private TextView textView;
    private ImageButton imageButton, pause, previous, next;
    private int mPosition = -1;
    private MediaPlayer mediaPlayer;
    private int flag = 0;
    private int currentMediaPosition = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.frag);
        relativeLayout.setVisibility(View.GONE);
        image_play = findViewById(R.id.image_play);
        imageButton = findViewById(R.id.play_play);
        pause = findViewById(R.id.pause_play);
        previous = findViewById(R.id.ibPrevious);
        next = findViewById(R.id.ibNext);

        imageButton.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.rv_Songs_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView = findViewById(R.id.title_play);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api api = retrofit.create(Api.class);

        Call<List<Songs>> call = api.getSongs();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("wait..");
        dialog.show();

        call.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                dialog.dismiss();
                list = response.body();
                adapter = new MediaRecyclerAdapter((ArrayList<Songs>) list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnClickListner(new MediaRecyclerAdapter.ClickListner() {
                    @Override
                    public void onItemClick(int position, View v) {

                        //Get song at specific position ...........
                        final Songs songs = list.get(position);

                        //set position veriable for further use..........
                        mPosition = position;



                        //Animation
                        Animation animation = new AlphaAnimation(0.3f, 1.0f);
                        animation.setBackgroundColor(Color.alpha(0123));
                        v.startAnimation(animation);

                        relativeLayout.setVisibility(View.VISIBLE);

                        Picasso.get().load(Uri.parse(songs.getCover_image())).fit().into(image_play);
                        textView.setText(songs.getSong());
                        if (mediaPlayer == null) {
                            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(songs.getUrl()));
                            mediaPlayer.start();
                        } else {
                            mediaPlayer.stop();
                            imageButton.setVisibility(View.GONE);
                            pause.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(songs.getUrl()));
                            mediaPlayer.start();
                        }


                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                flag=0;
                                if (mediaPlayer != null) {
                                    mediaPlayer.stop();
                                    mediaPlayer.release();
                                    mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(songs.getUrl()));
                                    imageButton.setVisibility(View.VISIBLE);
                                    pause.setVisibility(View.GONE);
                                } else {
                                    mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(songs.getUrl()));
                                    mediaPlayer.start();
                                    imageButton.setVisibility(View.GONE);
                                    pause.setVisibility(View.VISIBLE);


                                }
                            }
                        });


                        pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flag=1;
                                mediaPlayer.pause();
                                imageButton.setVisibility(View.VISIBLE);
                                pause.setVisibility(View.GONE);
                            }
                        });

                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mediaPlayer.start();
                                flag=0;
                                imageButton.setVisibility(View.GONE);
                                pause.setVisibility(View.VISIBLE);
                            }
                        });


                    }
                });
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Open internet connection for fetching songs...", Toast.LENGTH_SHORT).show();

            }


        });


        //play next song.....
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "" + mPosition + " " + list.size(), Toast.LENGTH_SHORT).show();
                if (mPosition < list.size() - 1) {

                    mediaPlayer.stop();
                    mPosition = mPosition + 1;
                    Picasso.get().load(Uri.parse(list.get(mPosition).getCover_image())).fit().into(image_play);
                    textView.setText(list.get(mPosition).getSong());
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(list.get(mPosition).getUrl()));

                    if(flag==0) {
                        mediaPlayer.start();
                    }
                }
            }
        });

        //play previous song.....
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPosition > 0) {

                    mediaPlayer.stop();
                    mPosition = mPosition - 1;
                    Picasso.get().load(Uri.parse(list.get(mPosition).getCover_image())).fit().into(image_play);
                    textView.setText(list.get(mPosition).getSong());
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(list.get(mPosition).getUrl()));

                    if (flag==0){
                      mediaPlayer.start();
                    }
                }
            }
        });


        //Relative layout click listener..(show media player at bottom of listView)
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMediaPosition = mediaPlayer.getCurrentPosition();
                releasePlayer();
                Songs songs = list.get(mPosition);
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("song", songs);
                intent.putExtra("position", currentMediaPosition);
                intent.putExtra("flag", flag);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        //notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test");
        builder.setSmallIcon(R.drawable.index);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.index);
        builder.setLargeIcon(bitmap);

        builder.setContentTitle("afreen afreen");


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(3, notification);

        NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(2);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        currentMediaPosition = data.getIntExtra("position", -1);
        if (mPosition != -1 && mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(list.get(mPosition).getUrl()));
            flag = data.getIntExtra("flag",-1);
            if(flag==0) {
                mediaPlayer.start();
            }else {
                imageButton.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }

        }
        if (currentMediaPosition != -1)
            mediaPlayer.seekTo(currentMediaPosition);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPosition != -1 && mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(list.get(mPosition).getUrl()));
            /*if (currentMediaPosition != -1)
                mediaPlayer.seekTo(currentMediaPosition);*/
            if (flag==0)
            mediaPlayer.start();
        }
    }
}
