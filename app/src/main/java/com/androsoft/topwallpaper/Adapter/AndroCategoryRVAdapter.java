package com.androsoft.topwallpaper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androsoft.topwallpaper.Model.AndroCategoryRVModel;
import com.androsoft.topwallpaper.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AndroCategoryRVAdapter extends RecyclerView.Adapter<AndroCategoryRVAdapter.ViewHolder> {

    Context context;
    ArrayList<AndroCategoryRVModel> androCategoryRVModelArrayList;

    AndroCategoryClickInterface androCategoryClickInterface;

    public AndroCategoryRVAdapter(Context context, ArrayList<AndroCategoryRVModel> androCategoryRVModelArrayList, AndroCategoryClickInterface androCategoryClickInterface) {
        this.context = context;
        this.androCategoryRVModelArrayList = androCategoryRVModelArrayList;
        this.androCategoryClickInterface = androCategoryClickInterface;
    }

    @NonNull
    @Override
    public AndroCategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_andro_category_rv, parent, false);

        return new AndroCategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroCategoryRVAdapter.ViewHolder holder, int position) {

        AndroCategoryRVModel androCategoryRVModel = androCategoryRVModelArrayList.get(position);
        holder.androCategoryTextV.setText(androCategoryRVModel.getCategoryName());

        Glide.with(context).load(androCategoryRVModel.getCategoryUrl()).into(holder.androCategoryImgV);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androCategoryClickInterface.onAndroCatagoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return androCategoryRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView androCategoryImgV;
        TextView androCategoryTextV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            androCategoryImgV = itemView.findViewById(R.id.androCategoryImgV);
            androCategoryTextV = itemView.findViewById(R.id.androCategoryTextV);
        }
    }

    public interface AndroCategoryClickInterface{
        void onAndroCatagoryClick(int position);
    }
}
