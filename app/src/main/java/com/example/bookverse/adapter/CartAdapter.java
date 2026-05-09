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
import com.example.bookverse.database.BookCart;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<BookCart> books;
    private Context context;
    private Map<String, String> imageCache = new HashMap<>();

    public CartAdapter(Context context, ArrayList<BookCart> books){
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cart_item, parent, false);
        return new BookCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookCart book = this.books.get(position);
        BookCartViewHolder bookViewHolder = (BookCartViewHolder) holder;
        bookViewHolder.bookItem_TV_name.setText(book.getName());
        bookViewHolder.bookItem_TV_category.setText(book.getCategory());
        bookViewHolder.bookItem_TV_price.setText(book.getPrice() + " ₪");
        bookViewHolder.bookItem_TV_quantity.setText("x" + book.getQuantity());

        String bookId = book.getBookId();
        if (imageCache.containsKey(bookId)) {
            String cachedImage = imageCache.get(bookId);
            if (cachedImage != null) {
                Bitmap bitmap = Utils.base64ToBitmap(cachedImage);
                bookViewHolder.bookItem_IV_bookImage.setImageBitmap(bitmap);
            }
        } else {
            bookViewHolder.bookItem_IV_bookImage.setImageBitmap(null);
            FirebaseFirestore.getInstance().collection("Books").document(bookId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Book fullBook = documentSnapshot.toObject(Book.class);
                        if (fullBook != null && fullBook.getImage() != null) {
                            imageCache.put(bookId, fullBook.getImage());
                            Bitmap bitmap = Utils.base64ToBitmap(fullBook.getImage());
                            bookViewHolder.bookItem_IV_bookImage.setImageBitmap(bitmap);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public class BookCartViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookItem_IV_bookImage;
        public TextView bookItem_TV_name;
        public TextView bookItem_TV_category;
        public TextView bookItem_TV_price;
        public TextView bookItem_TV_quantity;
        public BookCartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookItem_IV_bookImage = itemView.findViewById(R.id.bookItem_IV_bookImage);
            bookItem_TV_name = itemView.findViewById(R.id.bookItem_TV_name);
            bookItem_TV_category = itemView.findViewById(R.id.bookItem_TV_category);
            bookItem_TV_price = itemView.findViewById(R.id.bookItem_TV_price);
            bookItem_TV_quantity = itemView.findViewById(R.id.bookItem_TV_quantity);
        }
    }
}
