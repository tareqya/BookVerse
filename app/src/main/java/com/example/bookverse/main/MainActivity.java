package com.example.bookverse.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookverse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView home_BN;
    private FrameLayout home_frame_home;
    private FrameLayout home_frame_cart;
    private FrameLayout home_frame_orders;
    private FrameLayout home_frame_profile;

    private HomeFragment homeFragment;
    private CartFragment cartFragment;
    private OrdersFragment ordersFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        mainFunction();
    }

    private void findViews() {
        home_BN = findViewById(R.id.home_BN);
        home_frame_home = findViewById(R.id.home_frame_home);
        home_frame_cart = findViewById(R.id.home_frame_cart);
        home_frame_orders = findViewById(R.id.home_frame_orders);
        home_frame_profile = findViewById(R.id.home_frame_profile);
    }

    private void mainFunction() {
        homeFragment = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();
        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        ordersFragment = new OrdersFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_orders, ordersFragment).commit();
        cartFragment = new CartFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_cart, cartFragment).commit();

        home_frame_cart.setVisibility(View.INVISIBLE);
        home_frame_profile.setVisibility(View.INVISIBLE);
        home_frame_orders.setVisibility(View.INVISIBLE);
        home_frame_home.setVisibility(View.VISIBLE);

        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.home){
                    home_frame_cart.setVisibility(View.INVISIBLE);
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_orders.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.VISIBLE);
                }else if(menuItem.getItemId() == R.id.cart){
                    home_frame_cart.setVisibility(View.VISIBLE);
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_orders.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }else if(menuItem.getItemId() == R.id.orders){
                    home_frame_cart.setVisibility(View.INVISIBLE);
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_orders.setVisibility(View.VISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }else if(menuItem.getItemId() == R.id.profile){
                    home_frame_cart.setVisibility(View.INVISIBLE);
                    home_frame_profile.setVisibility(View.VISIBLE);
                    home_frame_orders.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }
}