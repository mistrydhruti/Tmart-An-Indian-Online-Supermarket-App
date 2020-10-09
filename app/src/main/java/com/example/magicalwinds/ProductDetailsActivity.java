package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.magicalwinds.Model.ProductModel;
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
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addtocart;
    private ImageView image;
    private TextView save,name,quantity,mrp,price,description,detail;
    private ElegantNumberButton numberButton;
    private String category="";
    private String Pname="";
    private String pimage="";
    private String pid="";
    private DatabaseReference cartlistref,anotherref;
    private FirebaseUser user;

    String ProductRandomKey="";
    String num,offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        category=getIntent().getStringExtra("category");
        Pname=getIntent().getStringExtra("name");
        pimage=getIntent().getStringExtra("image");
        pid=getIntent().getStringExtra("pid");

        detail=(TextView)findViewById(R.id.detail);
        image=(ImageView)findViewById(R.id.dis_product_image);
        save=(TextView)findViewById(R.id.dis_product_savings);
        name=(TextView)findViewById(R.id.dis_product_name);
        quantity=(TextView)findViewById(R.id.dis_product_quantity);
        mrp=(TextView)findViewById(R.id.dis_mrpprice);
        price=(TextView)findViewById(R.id.dis_tmartprice);
        description=(TextView)findViewById(R.id.dis_description);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        addtocart=(Button)findViewById(R.id.pd_add_cart);

          user = FirebaseAuth.getInstance().getCurrentUser();

          cartlistref=FirebaseDatabase.getInstance().getReference().child("Cart List");
          anotherref=FirebaseDatabase.getInstance().getReference().child("Cart List").child("Product Order List");

        getProductDetails(category);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingtocartlisr();
            }
        });

        detail.setText(Pname);

    }


    private void addingtocartlisr() {

        final String savecurrenttime,savecurrentdate;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currentTime.format(calfordate.getTime());

        ProductRandomKey=savecurrentdate+savecurrenttime;

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("category",category);
        cartMap.put("Pname",name.getText().toString());
        cartMap.put("price",price.getText().toString());
        cartMap.put("date",savecurrentdate);
        cartMap.put("time",savecurrenttime);
        cartMap.put("Savings",offer);
        cartMap.put("quantity",quantity.getText().toString());
        cartMap.put("amount",numberButton.getNumber());
        cartMap.put("image",pimage);
        cartMap.put("mrp",mrp.getText().toString());

        cartlistref.child("User View").child(user.getUid())
                .child(Pname)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            cartlistref.child("Admin View")
                                    .child(user.getUid())
                                    .child(Pname)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_LONG).show();


                                        }
                                    });
                        }
                    }
                });
    }

    private void getProductDetails(final String category) {

        final DatabaseReference prodreff= FirebaseDatabase.getInstance().getReference().child("Products").child(category);
        //DatabaseReference ref=prodreff.child(category);
        prodreff.child(Pname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if(dataSnapshot.exists())
                {
                    ProductModel products=dataSnapshot.getValue(ProductModel.class);
                    offer=products.getSavings();
                    name.setText(products.getPname());
                    quantity.setText(products.getQuantity());
                    save.setText(products.getSavings());
                    mrp.setText(products.getMrp());
                    price.setText(products.getPrice());
                    description.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void back_button(View view) {
        if(category.equals("Personal Care -> Skin Care"))
        {
            Intent i = new Intent(ProductDetailsActivity.this,Skin.class);
            startActivity(i);
            overridePendingTransition(0,0);
        }
        else if (category.equals("Ghee and Vanaspati -> Ghee and Vanaspati"))
        {
            Intent i = new Intent(ProductDetailsActivity.this,Ghee.class);
            startActivity(i);
            overridePendingTransition(0,0);
        }
        else if(category.equals("Home and Kitchen -> Home and Kitchen"))
        {
            Intent i = new Intent(ProductDetailsActivity.this,HomeKitchen.class);
            startActivity(i);
            overridePendingTransition(0,0);
        }
        else if(category.equals("Personal Care -> Oral Care"))
        {
            Intent i = new Intent(ProductDetailsActivity.this,Oral.class);
            startActivity(i);
            overridePendingTransition(0,0);
        }
       // Intent i = new Intent(ProductDetailsActivity.this,PersonalCare.class);
        //startActivity(i);

    }

    public void Go_to_home(View view) {
        Intent i = new Intent(ProductDetailsActivity.this,HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
