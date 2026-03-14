package com.example.bookverse.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookverse.R;
import com.example.bookverse.database.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Order> orders;

    private Context context;
    public OrderAdapter(Context context, ArrayList<Order> order){
        this.context = context;
        this.orders = order;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order = this.orders.get(position);
        OrderViewHolder orderViewHolder = (OrderViewHolder) holder;

        String books = "";
        for (int i = 0; i < order.getCart().getBooks().size(); i++) {
            String bookName = order.getCart().getBooks().get(i).getName();
            int quantity = order.getCart().getBooks().get(i).getQuantity();
            books +=  bookName + " x" +  quantity + ", ";
        }
        orderViewHolder.orderItem_TV_books.setText(books);
        orderViewHolder.orderItem_TV_price.setText("Total Price: " + order.getCart().getTotalPrice() + "₪");
        orderViewHolder.orderItem_TV_status.setText(order.getStatus());
        try{
            // convert date to DD/MM/YYYY HH:MM
            Date date = order.getOrderDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String formattedDate = formatter.format(date);
            orderViewHolder.orderItem_TV_date.setText(formattedDate);
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        public TextView orderItem_TV_books;
        public TextView orderItem_TV_price;
        public TextView orderItem_TV_date;
        public TextView orderItem_TV_status;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderItem_TV_books = itemView.findViewById(R.id.orderItem_TV_books);
            orderItem_TV_price = itemView.findViewById(R.id.orderItem_TV_price);
            orderItem_TV_date = itemView.findViewById(R.id.orderItem_TV_date);
            orderItem_TV_status = itemView.findViewById(R.id.orderItem_TV_status);
        }
    }
}
