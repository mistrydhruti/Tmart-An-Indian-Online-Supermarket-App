package com.example.magicalwinds;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;


public class ViewOrders extends AppCompatActivity {

    private DatabaseReference orderref,delref,newref;
    private String unino="";
    private TextView orderdate,ordertime,orderid,state,totamunt,payment,showreceipt,delboyname,delboyphone,delboydetails;
    private LinearLayout linearLayout,linearLayout1;
    private ImageView next,photo,delboycall;
    private FirebaseUser user;

    private String shno="";
    String id,stat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        unino=getIntent().getStringExtra("unino");

        user = FirebaseAuth.getInstance().getCurrentUser();

        orderref= FirebaseDatabase.getInstance().getReference().child("Orders").child("User Order Details");
        delref=FirebaseDatabase.getInstance().getReference().child("Assigned Orders");
        newref=FirebaseDatabase.getInstance().getReference().child("OrderID and PhoneNo");

        orderdate=(TextView)findViewById(R.id.orderdate);
        ordertime=(TextView)findViewById(R.id.ordertime);
        delboydetails=(TextView)findViewById(R.id.delboydetails);
        linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
        orderid=(TextView)findViewById(R.id.orderId);
        state=(TextView)findViewById(R.id.state);
        totamunt=(TextView)findViewById(R.id.totamunt);
        payment=(TextView)findViewById(R.id.payment);
        showreceipt=(TextView)findViewById(R.id.showreceipt);
        delboyname=findViewById(R.id.delpersonname);
        delboyphone=findViewById(R.id.delpersonphone);
        photo=findViewById(R.id.photo);
        delboycall=findViewById(R.id.delboyphone);
        linearLayout1=findViewById(R.id.linearlayout1);

        next=(ImageView)findViewById(R.id.next);



        newref.child(unino).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    shno=dataSnapshot.child("ShipperPhone").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        showreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ViewOrders.this,ShowReceipt.class);
                i.putExtra("id",id);
                startActivity(i);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ViewOrders.this,ShowReceipt.class);
                i.putExtra("id",id);
                startActivity(i);

            }
        });
        displayInfor();

    }


    private void displayInfor() {

        orderref.child(unino).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String date = dataSnapshot.child("OrderedDate").getValue().toString();
                    String time = dataSnapshot.child("OrderedTime").getValue().toString();
                    id = dataSnapshot.child("OrderId").getValue().toString();
                    stat = dataSnapshot.child("state").getValue().toString();
                    String tot = dataSnapshot.child("Totalamount").getValue().toString();
                    String pay = dataSnapshot.child("Payment").getValue().toString();


                    orderdate.setText(date);
                    ordertime.setText(time);
                    orderid.setText(id);
                    state.setText(stat);
                    totamunt.setText(tot);
                    payment.setText(pay);

                    onDelivered(stat);
                    oncheck(stat);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void oncheck(String stat) {
        if (stat.matches("shipped")) {
            delboydetails.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            delboycall.setVisibility(View.VISIBLE);

            delref.child(shno).child(unino).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        String phot=dataSnapshot.child("ShipperImage").getValue().toString();
                        String n=dataSnapshot.child("ShipperName").getValue().toString();
                        String p=dataSnapshot.child("ShipperPhoneNo").getValue().toString();

                        Picasso.get().load(phot).into(photo);

                        delboyname.setText(n);
                        delboyphone.setText(p);
                        callingFeature(p);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void onDelivered(String stat) {
        if (stat.matches("delivered")) {
            delboydetails.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);

            delref.child(shno).child(unino).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        String phot=dataSnapshot.child("ShipperImage").getValue().toString();
                        String n=dataSnapshot.child("ShipperName").getValue().toString();
                        String p=dataSnapshot.child("ShipperPhoneNo").getValue().toString();

                        Picasso.get().load(phot).into(photo);

                        delboyname.setText(n);
                        delboyphone.setText(p);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    private void callingFeature(final String p) {
        delboycall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ViewOrders.this)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent i = new Intent();
                                i.setAction(i.ACTION_DIAL);
                                i.setData(Uri.parse(new StringBuilder("tel:").append(p).toString()));
                                startActivity(i);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(ViewOrders.this, "You must accept"+response.getPermissionName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();

            }
        });
    }


}
