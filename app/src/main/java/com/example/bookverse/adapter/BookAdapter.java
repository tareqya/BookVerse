package com.example.bookverse.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookverse.R;
import com.example.bookverse.Utils;
import com.example.bookverse.database.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface OnBookClickListener{
        void onBookClick(Book book);
    }

    private OnBookClickListener onBookClickListener;
    private ArrayList<Book> books;
    private Context context;
    public BookAdapter(Context context, ArrayList<Book> books){
        this.context = context;
        this.books = books;
    }
    public void OnBookClickListener(OnBookClickListener onBookClickListener){
        this.onBookClickListener = onBookClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = this.books.get(position);
        BookViewHolder bookViewHolder = (BookViewHolder) holder;

        bookViewHolder.bookItem_TV_name.setText(book.getName());
        bookViewHolder.bookItem_TV_category.setText(book.getCategory());
        bookViewHolder.bookItem_TV_price.setText(book.getPrice() + " ₪");
        Bitmap bitmap = Utils.base64ToBitmap(book.getImage());
        bookViewHolder.bookItem_IV_bookImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookItem_IV_bookImage;
        public TextView bookItem_TV_name;
        public TextView bookItem_TV_category;
        public TextView bookItem_TV_price;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookItem_IV_bookImage = itemView.findViewById(R.id.bookItem_IV_bookImage);
            bookItem_TV_name = itemView.findViewById(R.id.bookItem_TV_name);
            bookItem_TV_category = itemView.findViewById(R.id.bookItem_TV_category);
            bookItem_TV_price = itemView.findViewById(R.id.bookItem_TV_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    Book book = books.get(position);
                    onBookClickListener.onBookClick(book);
                }
            });
        }
    }
}
