package com.example.authenfirebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenfirebase.R;
import com.example.authenfirebase.models.MyCartModel;
import com.example.authenfirebase.models.MyOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    Context context;
    List<MyOrderModel> orderModelList;

    public MyOrderAdapter(Context context, List<MyOrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(orderModelList.get(position).getProductName());
        holder.price.setText(orderModelList.get(position).getProductPrice());
        holder.date.setText(orderModelList.get(position).getCurrentDate());
        holder.time.setText(orderModelList.get(position).getCurrentTime());
        holder.quantity.setText(orderModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(orderModelList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,date,time,quantity,totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.product_date);
            time = itemView.findViewById(R.id.product_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
