package com.example.bookverse.callback;

import com.example.bookverse.database.Account;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AccountCallBack {
    void onAuthAccountComplete(Task<AuthResult> task);
    void onCreateAccountInfoComplete(Task<Void> task);
    void onLoginAccountComplete(Task<AuthResult> task);
    void onFetchAccountInfoComplete(Account account);
}
