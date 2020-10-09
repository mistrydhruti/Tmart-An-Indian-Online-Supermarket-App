package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.magicalwinds.Model.ProductModel;
import com.example.magicalwinds.Model.SeeAllModel;
import com.example.magicalwinds.ViewHolder.ProductViewHolder;
import com.example.magicalwinds.ViewHolder.SeeAllViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SeeAllDisplayActivity extends AppCompatActivity {

    private DatabaseReference prodRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String Cat="";
    private String type ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_display);

        prodRef= FirebaseDatabase.getInstance().getReference().child("Best Selling");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        /*layoutManager = new GridLayoutManager(SeeAllDisplayActivity.this);
        recyclerView.setLayoutManager(layoutManager);*/

       // type=getIntent().getExtras().get("Name").toString();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<SeeAllModel> options=new FirebaseRecyclerOptions.Builder<SeeAllModel>()
                .setQuery(prodRef,SeeAllModel.class)
                .build();


        FirebaseRecyclerAdapter<SeeAllModel, SeeAllViewHolder> adapter=new FirebaseRecyclerAdapter<SeeAllModel, SeeAllViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SeeAllViewHolder holder, int i, @NonNull final SeeAllModel model) {

                holder.txt_prodname.setText(model.getPname());
                holder.txt_prodprice.setText(model.getPrice());
                holder.txt_prodsave.setText(model.getSavings());
                holder.txt_quantity.setText(model.getQuantity());
                holder.txt_mrp.setText(model.getMrp());

                Picasso.get().load(model.getImage()).into(holder.seeall_image);

                holder.txt_addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String savecurrenttime,savecurrentdate;
                        Calendar calfordate=Calendar.getInstance();
                        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
                        savecurrentdate=currentdate.format(calfordate.getTime());

                        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                        savecurrenttime=currentdate.format(calfordate.getTime());

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        final DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("Cart List");

                        final HashMap<String,Object> cartMap=new HashMap<>();
                        cartMap.put("category",model.getCategory());
                        cartMap.put("Pname",model.getPname());
                        cartMap.put("price",model.getPrice());
                        cartMap.put("date",savecurrentdate);
                        cartMap.put("time",savecurrenttime);
                        cartMap.put("quantity",model.getQuantity());
                        cartMap.put("amount","0");
                        cartMap.put("image",model.getImage());
                        cartMap.put("mrp",model.getMrp());
                        cartlistref.child("User View").child("Products").child(user.getUid()).child(model.getPname())
                                .updateChildren(cartMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {

                                            cartlistref.child("Admin View").child("Products").child(model.getPname())
                                                    .updateChildren(cartMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful())
                                                            {

                                                                Toast.makeText(SeeAllDisplayActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                                                            }
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
            public SeeAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_item_layout,parent,false);

                SeeAllViewHolder holder=new SeeAllViewHolder(view);
                return holder;
            }
        };

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        /*recyclerView.setAdapter(adapter);
        adapter.startListening();*/


    }



    public void back_button(View view) {
        Intent i = new Intent(SeeAllDisplayActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    public void Go_to_home(View view) {
        Intent i = new Intent(SeeAllDisplayActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SeeAllDisplayActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);

    }
}
