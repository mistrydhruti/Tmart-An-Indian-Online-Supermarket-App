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
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.magicalwinds.Model.Cart;
import com.example.magicalwinds.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button next;
    private ImageView back,home;
    private TextView totamt;
    private int overtotalprice=0;
    FirebaseUser user;
    DatabaseReference cartlistref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        totamt=(TextView)findViewById(R.id.tot_price);
        next=(Button)findViewById(R.id.next);
        back=(ImageView)findViewById(R.id.backbtn);
        home=(ImageView)findViewById(R.id.home);

         user = FirebaseAuth.getInstance().getCurrentUser();
         cartlistref= FirebaseDatabase.getInstance().getReference().child("Cart List");

         cartlistref.child("User View").child(user.getUid()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 int a=(int)dataSnapshot.getChildrenCount();
                 //  tit.setText(String.valueOf(a));
                 if(a==0)
                 {

                     android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(CartActivity.this);
                     builder1.setMessage("Your cart is empty");
                     builder1.setCancelable(true);
                     builder1.setPositiveButton("ok",
                             new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {

                                     Intent i = new Intent(CartActivity.this,ShopByCategories.class);
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




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,ShopByCategories.class);
                startActivity(i);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CartActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartlistref
                .child("User View").child(user.getUid()),Cart.class)
                .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int i, @NonNull final Cart model) {
                holder.txt_prodamt.setText(model.getAmount());
                holder.txt_prodprice.setText(model.getPrice());
                holder.txt_prodname.setText(model.getPname());
                holder.txt_prodmrp.setText(model.getMrp());
                Picasso.get().load(model.getImage()).into(holder.prod_image);

                int onetypeproductprice=((Integer.valueOf(model.getPrice())))* Integer.valueOf(model.getAmount());
                overtotalprice=overtotalprice+onetypeproductprice;
                totamt.setText("TOTAL PRICE :" + String.valueOf(overtotalprice));
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
                       intent.putExtra("category",model.getCategory());
                       intent.putExtra("name",model.getPname());
                       intent.putExtra("image",model.getImage());
                       startActivity(intent);
                   }
               });
                holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cartlistref
                                .child("User View")
                                .child(user.getUid())
                                .child(model.getPname())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {


                                            cartlistref.child("Admin View").child(user.getUid()).child(model.getPname())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                                            Intent i = new Intent(CartActivity.this,CartActivity.class);
                                                                            startActivity(i);
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });
            }
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder; }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void nextbtn(View view) {
        if(overtotalprice>=500) {
            Intent i = new Intent(CartActivity.this, ShippingDetailsActivity.class);
            i.putExtra("price",String.valueOf(overtotalprice));
            startActivity(i);
            }
        else
        {
            Toast.makeText(this, "Your total price must be atleast Rs.500", Toast.LENGTH_LONG).show();
        }
    }

    private void CheckorderState()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference orderref;
        orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child("User Order Details").child(user.getUid());
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    String shippingstate=dataSnapshot.child("state").getValue().toString();
                    if(shippingstate.equals("not shipped"))
                    {
                        totamt.setError("Please wait for a while");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
