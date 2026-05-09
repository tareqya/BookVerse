package com.example.bookverse;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookverse.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView logo = findViewById(R.id.splash_IV_logo);
        TextView appName = findViewById(R.id.splash_TV_appName);

        // Start logo invisible and small
        logo.setAlpha(0f);
        logo.setScaleX(0.3f);
        logo.setScaleY(0.3f);

        // Logo animation: fade in + scale up with overshoot
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        fadeIn.setDuration(800);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logo, "scaleX", 0.3f, 1f);
        scaleX.setDuration(800);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logo, "scaleY", 0.3f, 1f);
        scaleY.setDuration(800);

        AnimatorSet logoAnim = new AnimatorSet();
        logoAnim.playTogether(fadeIn, scaleX, scaleY);
        logoAnim.setInterpolator(new OvershootInterpolator(1.5f));
        logoAnim.start();

        // App name fade in after logo
        ObjectAnimator nameFadeIn = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f);
        nameFadeIn.setDuration(600);
        nameFadeIn.setStartDelay(600);
        nameFadeIn.start();

        // Navigate to LoginActivity after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}

