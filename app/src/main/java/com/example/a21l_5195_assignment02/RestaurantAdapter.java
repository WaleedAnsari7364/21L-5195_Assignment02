package com.example.a21l_5195_assignment02;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    ArrayList<Restaurant> restaurants;
    ItemSelected parentActivity;

    public interface ItemSelected{
        public void onItemClicked(int index);
    }
    public RestaurantAdapter(Context context,ArrayList<Restaurant>list){
        parentActivity=(ItemSelected) context;
        restaurants=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        TextView tvphone;
        TextView tvlocation;
        TextView tvdescription;

        TextView tvRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvname);
            tvphone=itemView.findViewById(R.id.tvphone);
            tvlocation=itemView.findViewById(R.id.tvlocation);
            tvdescription=itemView.findViewById(R.id.tvdescription);
            tvRating=itemView.findViewById(R.id.tvRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onItemClicked(restaurants.indexOf((Restaurant) itemView.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(restaurants.get(position));
        holder.tvname.setText(restaurants.get(position).getName());
        holder.tvphone.setText(restaurants.get(position).getPhone());
        holder.tvlocation.setText(restaurants.get(position).getLocation());
        holder.tvdescription.setText(restaurants.get(position).getDescription());
        holder.tvRating.setText(restaurants.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
