package com.example.bookverse.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookverse.callback.AccountCallBack;
import com.example.bookverse.callback.AccountUpdateCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AccountController {


    public static final String ACCOUNT = "Accounts";
    private FirebaseAuth mAuth;
    private AccountCallBack accountCallBack;
    private FirebaseFirestore db ;

    public AccountController() {
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    public void setAccountCallBack(AccountCallBack accountCallBack){
        this.accountCallBack = accountCallBack;
    }

    public void createAuthAccount(String email, String password){
        this.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        accountCallBack.onAuthAccountComplete(task);
                    }
                });
    }

    public FirebaseUser getCurrentUser(){
        return this.mAuth.getCurrentUser();
    }

    public void logout(){
        this.mAuth.signOut();
    }

    public void createAccountInfo(Account account){
        this.db.collection(ACCOUNT).document(account.getUid()).set(account)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        accountCallBack.onCreateAccountInfoComplete(task);
                    }
                });
    }

    public void loginUser(String email, String password){
        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        accountCallBack.onLoginAccountComplete(task);
                    }
                });
    }

    public void fetchAccountInfo(String uid){
        this.db.collection(ACCOUNT).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                Account account = value.toObject(Account.class);
                account.setUid(value.getId());
                accountCallBack.onFetchAccountInfoComplete(account);
            }
        });
    }

    public void updateAccountInfo(Account account, AccountUpdateCallBack accountUpdateCallBack){
        this.db.collection(ACCOUNT).document(account.getUid()).set(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                accountUpdateCallBack.onUpdateAccountComplete(task);
            }
        });
    }
}
