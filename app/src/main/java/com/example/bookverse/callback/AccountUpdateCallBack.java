package com.example.bookverse.callback;

import com.google.android.gms.tasks.Task;

public interface AccountUpdateCallBack {
    void onUpdateAccountComplete(Task<Void> task);
}
