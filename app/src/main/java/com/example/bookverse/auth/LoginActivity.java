package com.example.bookverse.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookverse.R;
import com.example.bookverse.callback.AccountCallBack;
import com.example.bookverse.database.AccountController;
import com.example.bookverse.main.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private Button login_BTN_login;
    private Button login_BTN_signup;
    private CircularProgressIndicator login_PB_loading;
    private AccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        mainFunction();

    }

    private void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_signup = findViewById(R.id.login_BTN_signup);
        login_PB_loading = findViewById(R.id.login_PB_loading);
    }

    private void mainFunction() {
        accountController = new AccountController();
        accountController.setAccountCallBack(new AccountCallBack() {
            @Override
            public void onAuthAccountComplete(Task<AuthResult> task) {

            }

            @Override
            public void onCreateAccountInfoComplete(Task<Void> task) {

            }

            @Override
            public void onLoginAccountComplete(Task<AuthResult> task) {
                login_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    // go to main screen
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                }
            }
        });

        login_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to signup screen
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();
                if(email.isEmpty()){
                    login_TF_email.setError("Email is required");
                    return;
                }
                if(password.isEmpty()){
                    login_TF_password.setError("Password is required");
                    return;
                }
                login_PB_loading.setVisibility(View.VISIBLE);
                accountController.loginUser(email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccountController accountController1 = new AccountController();
        if(accountController1.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}