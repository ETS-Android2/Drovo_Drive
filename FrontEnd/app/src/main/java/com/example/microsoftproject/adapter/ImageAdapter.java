package com.example.microsoftproject.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsoftproject.HomePage;
import com.example.microsoftproject.R;
import com.example.microsoftproject.ViewImageActivity;
import com.example.microsoftproject.entity.ImageEntity;
import com.example.microsoftproject.service.NetworkServiceClass;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHolder> {

    List<ImageEntity> imageList;
    Context context;

    public ImageAdapter(Context context, List<ImageEntity> imageList){
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_view , parent , false));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)  {
        ImageEntity entity = imageList.get(position);
        holder.imageSize.setText(String.valueOf(entity.getSize()));
        holder.imageName.setText(entity.getName());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView imageName;
        TextView imageSize;
        ImageButton deleteButton;
        ImageView item_image;
        CardView cardView;

        public MyHolder(@NonNull View itemView)  {
            super(itemView);
            RecyclerView recyclerView;

            itemView.setOnClickListener(this);
            imageName = itemView.findViewById(R.id.item_image_name);
            imageSize = itemView.findViewById(R.id.item_image_size);
            cardView =  itemView.findViewById(R.id.cardView);
            item_image =  itemView.findViewById(R.id.item_image);
            deleteButton =  itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int vid = v.getId();

            if (deleteButton.getId() == vid ){
                Toast.makeText(context, " image deleted", Toast.LENGTH_SHORT).show();
                ((HomePage) context).deleteImage(imageList.get(getAdapterPosition()).getName());
            }
            else {
                int position =  this.getAdapterPosition();
                ImageEntity imageEntity = imageList.get(position);

                String name = imageEntity.getName();

                Intent intent =  new Intent(context , ViewImageActivity.class);
                intent.putExtra("name" , name);
                context.startActivity(intent);
            }


        }
    }
}
