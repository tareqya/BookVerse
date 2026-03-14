package com.example.bookverse.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookverse.R;
import com.example.bookverse.auth.LoginActivity;
import com.example.bookverse.callback.AccountCallBack;
import com.example.bookverse.database.Account;
import com.example.bookverse.database.AccountController;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class ProfileFragment extends Fragment {

    public static final String ACCOUNT_KEY = "account";
    private AppCompatActivity context;
    private TextView fragProfile_TV_name;
    private TextView fragProfile_TV_email;
    private CardView fragProfile_CV_editDetails;
    private CardView fragProfile_CV_logout;
    private AccountController accountController;
    private Account account;

    public ProfileFragment(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        mainFunction();
        return view;
    }

    private void findViews(View view) {
        fragProfile_TV_name = view.findViewById(R.id.fragProfile_TV_name);
        fragProfile_TV_email = view.findViewById(R.id.fragProfile_TV_email);
        fragProfile_CV_editDetails = view.findViewById(R.id.fragProfile_CV_editDetails);
        fragProfile_CV_logout = view.findViewById(R.id.fragProfile_CV_logout);
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

            }

            @Override
            public void onFetchAccountInfoComplete(Account account1) {
                account = account1;
                fragProfile_TV_name.setText(account.getFirstName() + " " + account.getLastName() );
                fragProfile_TV_email.setText(account.getEmail());
            }
        });

        String uid = accountController.getCurrentUser().getUid();
        accountController.fetchAccountInfo(uid);

        fragProfile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                accountController.logout();
                context.finish();
            }
        });

        fragProfile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                intent.putExtra(ACCOUNT_KEY, account);
                context.startActivity(intent);
            }
        });
    }

}