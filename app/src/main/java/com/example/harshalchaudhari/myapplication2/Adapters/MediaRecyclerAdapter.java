package com.example.harshalchaudhari.myapplication2.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harshalchaudhari.myapplication2.Models.Songs;
import com.example.harshalchaudhari.myapplication2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaRecyclerAdapter extends RecyclerView.Adapter<MediaRecyclerAdapter.MyViewHolder> {
    private ArrayList<Songs>list;
    private static ClickListner clickListner;

    public MediaRecyclerAdapter(ArrayList<Songs> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView miv_Songs_Image;
        private TextView mtv_Title_Songs,mtv_Artist_Name;
        public MyViewHolder(View itemView) {
            super(itemView);
            miv_Songs_Image = (ImageView) itemView.findViewById(R.id.iv_Url_Image);
            mtv_Title_Songs = (TextView) itemView.findViewById(R.id.tv_Title_Song);
            mtv_Artist_Name=(TextView) itemView.findViewById(R.id.tv_Title_Artist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListner.onItemClick(getAdapterPosition(),view);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_media_recycler_view,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Songs songs = list.get(position);
        Picasso.get().load(songs.getCover_image()).fit().into(holder.miv_Songs_Image);
        holder.mtv_Title_Songs.setText(songs.getSong());
        holder.mtv_Artist_Name.setText(songs.getArtists());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListner(ClickListner clickListner1) {
            MediaRecyclerAdapter.clickListner = clickListner1;
    }

    public interface ClickListner {
        void onItemClick(int position, View v);
    }

}
