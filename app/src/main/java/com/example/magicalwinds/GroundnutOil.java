package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magicalwinds.Model.ProductModel;
import com.example.magicalwinds.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class GroundnutOil extends AppCompatActivity {

    private DatabaseReference prodRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String Cat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groundnut_oil);
        prodRef= FirebaseDatabase.getInstance().getReference().child("Products").child("Cooking Oil -> Groundnut Oil");
        Cat="Cooking Oil -> Groundnut Oil";

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ProductModel> options=new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(prodRef,ProductModel.class)
                .build();

        FirebaseRecyclerAdapter<ProductModel, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final ProductModel model) {
                holder.txt_prodname.setText(model.getPname());
                holder.txt_prodprice.setText(model.getPrice());
                holder.txt_prodsave.setText(model.getSavings());
                holder.txt_quantity.setText(model.getQuantity());
                holder.txt_mrp.setText(model.getMrp());
                Picasso.get().load(model.getImage()).into(holder.prod_image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(GroundnutOil.this,ProductDetailsActivity.class);
                        i.putExtra("category",Cat);
                        i.putExtra("name",model.getPname());
                        i.putExtra("pid",model.getPid());
                        i.putExtra("image",model.getImage());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public void back_button(View view) {
        Intent i = new Intent(GroundnutOil.this,SearchActivity.class);
        i.putExtra("Category",Cat);
        startActivity(i);
        finish(); }
    public void Go_to_home(View view) {
        Intent i = new Intent(GroundnutOil.this, HomeActivity.class);
        startActivity(i);
        finish(); }
}
