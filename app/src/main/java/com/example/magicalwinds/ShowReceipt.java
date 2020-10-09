package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowReceipt extends AppCompatActivity {

    private TextView Name, name1, time, time1, totprice, finaltot, ordtot, address, paymethod,info;
    private DatabaseReference userRef, orderref;
    String id = "";

    String unino="";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_receipt);

        id = getIntent().getStringExtra("id");
        Name = findViewById(R.id.name);
        name1 = findViewById(R.id.name1);
        time = findViewById(R.id.time);
        time1 = findViewById(R.id.time1);
        totprice = findViewById(R.id.totprice);
        finaltot = findViewById(R.id.finaltot);
        ordtot = findViewById(R.id.ordtot);
        address = findViewById(R.id.address);
        paymethod = findViewById(R.id.paymethod);
        info=findViewById(R.id.info);

        user = FirebaseAuth.getInstance().getCurrentUser();

        orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child("User Order Details").child(id);

        orderref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String n = dataSnapshot.child("state").getValue().toString();
                    if (n.startsWith("delivered")) {
                        info.setText("DELIVERED SUCCESSFULLY !");
                    } else {
                        info.setText("YOUR ORDER HAS BEEN PLACED");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayDetails();

    }

    private void displayDetails() {
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    String name=dataSnapshot.child("Name").getValue().toString();
                    String ex=dataSnapshot.child("ExTime").getValue().toString();
                    String t=dataSnapshot.child("Totalamount").getValue().toString();
                    String a=dataSnapshot.child("Address").getValue().toString();
                    String pay=dataSnapshot.child("Payment").getValue().toString();
                    unino=dataSnapshot.child("UniqueNo").getValue().toString();

                    Name.setText(name);
                    name1.setText(name);
                    time.setText(ex);
                    time1.setText(ex);
                    totprice.setText(t);
                    finaltot.setText(t);
                    ordtot.setText(t);
                    address.setText(a);
                    paymethod.setText(pay);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void home(View view) {
        Intent i = new Intent(ShowReceipt.this,ShopByCategories.class);
        startActivity(i);
        finish();
    }
}