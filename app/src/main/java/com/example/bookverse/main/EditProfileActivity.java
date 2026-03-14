package com.example.bookverse.main;

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
import com.example.bookverse.database.Account;
import com.example.bookverse.database.AccountController;
import com.example.bookverse.callback.AccountUpdateCallBack;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {
    private TextInputLayout profileEdit_TF_firstName;
    private TextInputLayout profileEdit_TF_lastName;
    private TextInputLayout profileEdit_TF_address;
    private Button editProfile_BTN_update;
    private CircularProgressIndicator editProfile_PB_loading;
    private Account account;
    private AccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra(ProfileFragment.ACCOUNT_KEY);
        findViews();
        mainFunction();
    }

    private void mainFunction(){
        accountController = new AccountController();
        profileEdit_TF_firstName.getEditText().setText(account.getFirstName());
        profileEdit_TF_lastName.getEditText().setText(account.getLastName());
        profileEdit_TF_address.getEditText().setText(account.getAddress());

        editProfile_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = profileEdit_TF_firstName.getEditText().getText().toString();
                String lastName = profileEdit_TF_lastName.getEditText().getText().toString();
                String address = profileEdit_TF_address.getEditText().getText().toString();

                account.setAddress(address);
                account.setFirstName(firstName);
                account.setLastName(lastName);
                editProfile_PB_loading.setVisibility(View.VISIBLE);
                accountController.updateAccountInfo(account, new AccountUpdateCallBack() {
                    @Override
                    public void onUpdateAccountComplete(Task<Void> task) {
                        if(task.isSuccessful()){
                            editProfile_PB_loading.setVisibility(View.INVISIBLE);
                            EditProfileActivity.this.finish();
                        }else{
                            String err = task.getException().getMessage().toString();
                            editProfile_PB_loading.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditProfileActivity.this, "Error: "+err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void findViews() {
        editProfile_PB_loading = findViewById(R.id.editProfile_PB_loading);
        editProfile_BTN_update = findViewById(R.id.editProfile_BTN_update);
        profileEdit_TF_address = findViewById(R.id.profileEdit_TF_address);
        profileEdit_TF_firstName  = findViewById(R.id.profileEdit_TF_firstName);
        profileEdit_TF_lastName = findViewById(R.id.profileEdit_TF_lastName);
    }
}