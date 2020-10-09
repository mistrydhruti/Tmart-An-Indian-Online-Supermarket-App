package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.magicalwinds.Model.OrderModel;
import com.example.magicalwinds.Model.ProductModel;
import com.example.magicalwinds.ViewHolder.OrderViewHolder;
import com.example.magicalwinds.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Orders extends AppCompatActivity {

    private DatabaseReference order,newref;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseUser user;
    private String shno="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        user = FirebaseAuth.getInstance().getCurrentUser();

        order = FirebaseDatabase.getInstance().getReference().child("Orders").child("User Order Details");
        newref=FirebaseDatabase.getInstance().getReference().child("OrderID and PhoneNo");


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        order.orderByChild("UniqueNo").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a=(int)dataSnapshot.getChildrenCount();
                //  tit.setText(String.valueOf(a));
                if(a==0)
                {

                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(Orders.this);
                    builder1.setMessage("No orders placed !");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Orders.this,HomeActivity.class);
                                    startActivity(i);
                                    finish();

                                }

                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<OrderModel> options=new FirebaseRecyclerOptions.Builder<OrderModel>()
                .setQuery(order.orderByChild("UniqueNo").equalTo(user.getUid()),OrderModel.class)
                .build();

        FirebaseRecyclerAdapter<OrderModel, OrderViewHolder> adapter=new FirebaseRecyclerAdapter<OrderModel, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OrderViewHolder holder, int i, @NonNull final OrderModel model) {
                holder.txt_orderdate.setText(model.getOrderedDate());
                holder.txt_orderId.setText("OrderID: "+model.getOrderId());
                holder.txt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Orders.this,ViewOrders.class);
                        i.putExtra("unino",model.getOrderId());
                        startActivity(i);
                    }
                });

                if (model.getState().matches("not shipped"))
                {
                    holder.order_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Orders.this);
                            builder1.setMessage("Are you sure ? you want to cancel this order.");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            order.child(model.getOrderId()).removeValue();
                                            sendmsg(model.getEmail(),model.getOrderId());
                                            Toast.makeText(Orders.this, "Order Cancelled!!", Toast.LENGTH_SHORT).show();
                                        }

                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();


                        }
                    });

                }
                else {
                    holder.cancelorder.setVisibility(View.INVISIBLE);
                }


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Orders.this,ViewOrderProducts.class);
                        startActivity(i);
                    }
                });



                           }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders,parent,false);
                OrderViewHolder holder=new OrderViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void sendmsg(String email, String orderId) {
        String subject = "Order successfully cancelled";
        String message = "Thank you for using tmart. Your Order Id : "+orderId+" is cancelled. Keep using tmart for more exciting offers";

        //Creating SendMail object
        SendMail sm = new SendMail(Orders.this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Orders.this,HomeActivity.class);
        startActivity(i);
        finish();

    }

    public void Go_to_home(View view) {
        Intent i = new Intent(Orders.this,HomeActivity.class);
        startActivity(i);
    }

    public void back_button(View view) {
        Intent i = new Intent(Orders.this,HomeActivity.class);
        startActivity(i);
        finish();
    }
}
