package com.androsoft.topwallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androsoft.topwallpaper.R;
import com.androsoft.topwallpaper.WallpaperShowActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AndroWallpaperRVAdapter extends RecyclerView.Adapter<AndroWallpaperRVAdapter.ViewHolder> {

    Context context;
    ArrayList<String> androWallpaperRVModelArrayList;

    public AndroWallpaperRVAdapter(Context context, ArrayList<String> androWallpaperRVModelArrayList) {
        this.context = context;
        this.androWallpaperRVModelArrayList = androWallpaperRVModelArrayList;
    }

    @NonNull
    @Override
    public AndroWallpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_andro_wallpaper_rv, parent, false);

        return new AndroWallpaperRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroWallpaperRVAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(androWallpaperRVModelArrayList.get(position)).into(holder.androWallpaperImgV);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperShowActivity.class);
                intent.putExtra("imageUrl", androWallpaperRVModelArrayList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return androWallpaperRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView androWallpaperImgV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            androWallpaperImgV = itemView.findViewById(R.id.androWallpaperImgV);
        }
    }
}
