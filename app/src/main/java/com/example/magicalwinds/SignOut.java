package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.magicalwinds.Model.Token;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignOut extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
   // private Button sign_out;
    List<AuthUI.IdpConfig> providers;

   // private String orderId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);


        providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),  //Email builder
                new AuthUI.IdpConfig.PhoneBuilder().build(),  //Phone builder
                new AuthUI.IdpConfig.GoogleBuilder().build()   //Google Builder

        );
        ShowSignInOptions(); }


    private void ShowSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.tag)
                        .setIsSmartLockEnabled(false)
                         .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        ); }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode==RESULT_OK )
            {
                //orderId= UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

                //Get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //show email on toast
                //get button signout
                Intent i = new Intent(SignOut.this,HomeActivity.class);
                //i.putExtra("type","no");
               // i.putExtra("orderId",orderId);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
