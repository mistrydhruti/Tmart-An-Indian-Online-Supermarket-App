package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.magicalwinds.Model.ProductModel;
import com.example.magicalwinds.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private ImageView searchbtn;
    private RecyclerView searchlist;
    private EditText searchinput;
    private String seainput="";
    private String Cat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Cat=getIntent().getStringExtra("Category");
       searchbtn=(ImageView) findViewById(R.id.search_btn);
       searchinput=(EditText)findViewById(R.id.search_product_name);
       searchlist=(RecyclerView)findViewById(R.id.search_list);
       searchlist.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

       searchbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               seainput=searchinput.getText().toString();
               onStart();
           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<ProductModel> options=new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(reference.child(Cat).orderByChild("Pname").startAt(seainput),ProductModel.class)
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
                        Intent i = new Intent(SearchActivity.this,ProductDetailsActivity.class);
                        i.putExtra("category",model.getCategory());
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
        searchlist.setAdapter(adapter);
        adapter.startListening();
    }
}
