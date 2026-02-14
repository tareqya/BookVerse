package com.example.bookverse.auth;

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
import com.example.bookverse.database.Account;
import com.example.bookverse.database.AccountController;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_phone;
    private TextInputLayout signup_TF_address;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;
    private AccountController accountController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        mainFunction();
    }



    private void findViews() {
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_phone = findViewById(R.id.signup_TF_phone);
        signup_TF_address = findViewById(R.id.signup_TF_address);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }
    private void mainFunction() {
        accountController = new AccountController();
        accountController.setAccountCallBack(new AccountCallBack() {
            @Override
            public void onAuthAccountComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // save user data to database
                    Account account = new Account();
                    account.setAddress(signup_TF_address.getEditText().getText().toString());
                    account.setEmail(signup_TF_email.getEditText().getText().toString());
                    account.setFirstName(signup_TF_firstName.getEditText().getText().toString());
                    account.setLastName(signup_TF_lastName.getEditText().getText().toString());
                    account.setPhone(signup_TF_phone.getEditText().getText().toString());
                    String uid = accountController.getCurrentUser().getUid();
                    account.setUid(uid);

                    accountController.createAccountInfo(account);

                }else{
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, "Error: "+err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCreateAccountInfoComplete(Task<Void> task) {
                signup_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    accountController.logout();
                    finish();

                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, "Error: "+err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoginAccountComplete(Task<AuthResult> task) {

            }
        });

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateInput()){
                    return;
                }

                // create user account
                signup_PB_loading.setVisibility(View.VISIBLE);
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                accountController.createAuthAccount(email, password);
            }
        });
    }

    public boolean validateInput(){
        String firstName = signup_TF_firstName.getEditText().getText().toString();
        String lastName = signup_TF_lastName.getEditText().getText().toString();
        String email = signup_TF_email.getEditText().getText().toString();
        String phone = signup_TF_phone.getEditText().getText().toString();
        String address = signup_TF_address.getEditText().getText().toString();
        String password = signup_TF_password.getEditText().getText().toString();
        String confirmPassword = signup_TF_confirmPassword.getEditText().getText().toString();

        if(firstName.isEmpty()){
            signup_TF_firstName.setError("First name is required");
            return false;
        }

        if(lastName.isEmpty()){
            signup_TF_lastName.setError("Last name is required");
            return false;
        }

        if(phone.isEmpty()){
            signup_TF_phone.setError("Phone is required");
            return false;
        }

        if(address.isEmpty()){
            signup_TF_address.setError("Address is required");
            return false;
        }

        if(password.isEmpty()){
            signup_TF_password.setError("Password is required");
            return false;
        }

        if(!confirmPassword.equals(password)){
            signup_TF_confirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}