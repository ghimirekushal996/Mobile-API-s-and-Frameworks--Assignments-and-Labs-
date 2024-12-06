package com.kushal.recipeapp.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kushal.recipeapp.R;
import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;

public class SplashActivity extends AppCompatActivity {

    // noew add here 2 second to laod the spalash screen

    private static final int SPLASH_DELAY = 2000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize SharedPreferenceManager
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this);

        // and delay the screen to proceed this function
        new Handler().postDelayed(() -> {
            String token = sharedPreferenceManager.getToken();
            Intent intent;
            if (token != null && !token.isEmpty()) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
