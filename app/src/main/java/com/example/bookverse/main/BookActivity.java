package com.example.bookverse.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookverse.R;
import com.example.bookverse.Utils;
import com.example.bookverse.callback.CartCallBack;
import com.example.bookverse.database.AccountController;
import com.example.bookverse.database.Book;
import com.example.bookverse.database.BookCart;
import com.example.bookverse.database.Cart;
import com.example.bookverse.database.CartController;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class BookActivity extends AppCompatActivity {
    private Book book;
    private TextView bookActivity_TV_bookName;
    private TextView bookActivity_TV_bookPrice;
    private ImageView bookActivity_IV_bookImage;
    private TextView bookActivity_TV_bookDescription;
    private Button bookActivity_BTN_AddToCart;
    private CartController cartController;
    private AccountController accountController;
    private Cart cart;
    private boolean enable = false;
    private CircularProgressIndicator bookActivity_PB_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra(HomeFragment.BOOK_KEY);
        findViews();
        mainFunction();
    }

    private void findViews(){
        bookActivity_TV_bookName = findViewById(R.id.bookActivity_TV_bookName);
        bookActivity_TV_bookPrice = findViewById(R.id.bookActivity_TV_bookPrice);
        bookActivity_IV_bookImage = findViewById(R.id.bookActivity_IV_bookImage);
        bookActivity_TV_bookDescription = findViewById(R.id.bookActivity_TV_bookDescription);
        bookActivity_BTN_AddToCart = findViewById(R.id.bookActivity_BTN_AddToCart);
        bookActivity_PB_loading = findViewById(R.id.bookActivity_PB_loading);
    }

    private void mainFunction() {
        cart = new Cart();
        accountController = new AccountController();
        cartController = new CartController();
        cartController.setCartCallBack(new CartCallBack() {
            @Override
            public void onFetchCartComplete(Cart data) {
                cart = data;
                enable = true;
                bookActivity_PB_loading.setVisibility(View.INVISIBLE);
                Toast.makeText(BookActivity.this, "Cart fetched", Toast.LENGTH_SHORT).show();
            }
        });
        String uid = accountController.getCurrentUser().getUid();
        cartController.fetchUserCart(uid);

        bookActivity_TV_bookName.setText(book.getName());
        bookActivity_TV_bookPrice.setText(book.getPrice() + "₪");
        bookActivity_TV_bookDescription.setText(book.getDescription());
        Bitmap bitmap = Utils.base64ToBitmap(book.getImage());
        bookActivity_IV_bookImage.setImageBitmap(bitmap);

        bookActivity_BTN_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enable){
                    Toast.makeText(BookActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                    return;
                }
                enable = false;
                bookActivity_PB_loading.setVisibility(View.VISIBLE);
                for(int i = 0 ; i < cart.getBooks().size(); i++){
                    BookCart bookCart = cart.getBooks().get(i);
                    if(book.getUid().equals(bookCart.getBookId())){
                        bookCart.setQuantity(bookCart.getQuantity() + 1);
                        cartController.updateCart(cart);
                        Toast.makeText(BookActivity.this, "Book added to cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // add first book
                BookCart bookCart = new BookCart();
                bookCart.setUid(book.getUid());
                bookCart.setName(book.getName());
                bookCart.setImage(book.getImage());
                bookCart.setCategory(book.getCategory());
                bookCart.setPrice(book.getPrice());
                bookCart.setDescription(book.getDescription());
                bookCart.setQuantity(1);
                bookCart.setBookId(book.getUid());
                cart.addBook(bookCart);
                cart.setUserId(uid);
                cartController.updateCart(cart);
                Toast.makeText(BookActivity.this, "Book added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

}