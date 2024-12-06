package com.kushal.recipeapp.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kushal.recipeapp.R;
import com.kushal.recipeapp.route_path_network.LoginController;
import com.kushal.recipeapp.models.LoginRequestModel;
import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private LoginController loginController;
    private SharedPreferenceManager sharedPreferenceManager;
    TextView textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textRegister = findViewById(R.id.register);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginController = new LoginController(getApplicationContext());
        sharedPreferenceManager = new SharedPreferenceManager(this);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(email, password);
            }
        });
    }

    private void performLogin(String email, String password) {
        LoginRequestModel request = new LoginRequestModel(email, password);
        loginController.loginUser(request, new LoginController.LoginCallback() {
            @Override
            public void onSuccess(String message, String token) {
                sharedPreferenceManager.saveToken(token);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                navigateToHome();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Replace with your Home screen activity
        startActivity(intent);
        finish();
    }
}
