package com.example.bestwall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperRVadapter extends RecyclerView.Adapter<WallpaperRVadapter.ViewHolder> {


    private ArrayList<String> wallpaperRVArrayList;
    private Context context;

    public WallpaperRVadapter(ArrayList<String> wallpaperRVArrayList, Context context) {
        this.wallpaperRVArrayList = wallpaperRVArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public WallpaperRVadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_layout,parent,false);
        return  new WallpaperRVadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperRVadapter.ViewHolder holder, int position) {
        Glide.with(context).load(wallpaperRVArrayList.get(position)).into(holder.wallpaperimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), (CharSequence) wallpaperRVArrayList.get(position) , Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context,WallpaperActivity.class);
                intent.putExtra("imgUrl",wallpaperRVArrayList.get(position));
                if(intent!=null)
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperRVArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
  private ImageView wallpaperimg;
  private CardView CDwallpaper;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CDwallpaper=itemView.findViewById(R.id.idCVwallpaper);
            wallpaperimg=itemView.findViewById(R.id.idimageWallpaper);
        }
    }
}
