package com.example.magicalwinds.Service;

import android.os.Handler;
import android.os.Looper;

import com.example.magicalwinds.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseIdService extends FirebaseInstanceIdService {
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String tokenRefreshed=FirebaseInstanceId.getInstance().getToken();
        if (user.getUid()!=null) {
            updateTokentoFirebase(tokenRefreshed); }
    }

    private void updateTokentoFirebase(final String tokenrefreshed) {

       // FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                FirebaseDatabase db= FirebaseDatabase.getInstance();
                DatabaseReference tokens=db.getReference("Token");

                Token token = new Token(tokenrefreshed,false);
                tokens.child(user.getUid()).setValue(token);
            }
        });

    }
}
