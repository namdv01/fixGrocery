package com.example.authenfirebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.authenfirebase.R;
import com.example.authenfirebase.activities.ViewAllActivity;
import com.example.authenfirebase.models.PopularModel;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {
    Context context;
    List<PopularModel> popularModelList;

    public PopularAdapters(Context context, List<PopularModel> popularModelList) {
        this.context = context;
        this.popularModelList = popularModelList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PopularModel> getPopularModelList() {
        return popularModelList;
    }

    public void setPopularModelList(List<PopularModel> popularModelList) {
        this.popularModelList = popularModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.popular_img);
        holder.name.setText(popularModelList.get(position).getName());
        holder.rating.setText(popularModelList.get(position).getRating());
        holder.discount.setText(popularModelList.get(position).getDiscount());
        holder.description.setText(popularModelList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",popularModelList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView popular_img;
        TextView name,description,rating,discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popular_img = itemView.findViewById(R.id.popular_img);
            name = itemView.findViewById(R.id.popular_name);
            description = itemView.findViewById(R.id.popular_description);
            rating = itemView.findViewById(R.id.popular_rating);
            discount = itemView.findViewById(R.id.popular_discount);

        }
    }
}
