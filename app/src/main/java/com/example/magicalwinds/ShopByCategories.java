package com.example.magicalwinds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShopByCategories extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_by_categories);


        View card1 = findViewById(R.id.pulses);
        View card2 = findViewById(R.id.bread);
        View card3 = findViewById(R.id.personal_care);
        View card4 = findViewById(R.id.snacks);
        View card5 = findViewById(R.id.salt);
        View card6 = findViewById(R.id.jams);
        View card7 = findViewById(R.id.masala);
        View card8 = findViewById(R.id.ghee);
        View card9 = findViewById(R.id.chocolate);
        View card10 = findViewById(R.id.biscuits);
        View card11 = findViewById(R.id.baby_products);
        View card12 = findViewById(R.id.dry_fruits);
        View card13 = findViewById(R.id.oil);
        View card14 = findViewById(R.id.rice);
        View card15 = findViewById(R.id.flour);
        View card16 = findViewById(R.id.soups);
        View card17 = findViewById(R.id.breakfast);
        View card18 = findViewById(R.id.home_kitchen);
        View card19 = findViewById(R.id.pickles);

        card1.setOnClickListener((View.OnClickListener) this);
        card2.setOnClickListener((View.OnClickListener) this);
        card3.setOnClickListener((View.OnClickListener) this);
        card4.setOnClickListener((View.OnClickListener) this);
        card5.setOnClickListener((View.OnClickListener) this);
        card6.setOnClickListener((View.OnClickListener) this);
        card7.setOnClickListener((View.OnClickListener) this);
        card8.setOnClickListener((View.OnClickListener) this);
        card9.setOnClickListener((View.OnClickListener) this);
        card10.setOnClickListener((View.OnClickListener) this);
        card11.setOnClickListener((View.OnClickListener) this);
        card12.setOnClickListener((View.OnClickListener) this);
        card13.setOnClickListener((View.OnClickListener) this);
        card14.setOnClickListener((View.OnClickListener) this);
        card15.setOnClickListener((View.OnClickListener) this);
        card16.setOnClickListener((View.OnClickListener) this);
        card17.setOnClickListener((View.OnClickListener) this);
        card18.setOnClickListener((View.OnClickListener) this);
        card19.setOnClickListener((View.OnClickListener) this);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.pulses:
                Intent intent = new Intent(ShopByCategories.this, Pulses.class);
                startActivity(intent);
                break;
            case R.id.bread:
                Intent intent1 = new Intent(ShopByCategories.this, Bread.class);
                startActivity(intent1);
                break;
            case R.id.personal_care:
                Intent intent2 = new Intent(ShopByCategories.this, PersonalCare.class);
                startActivity(intent2);
                break;
            case R.id.snacks:
                Intent intent3 = new Intent(ShopByCategories.this, Snacks.class);
                startActivity(intent3);
                break;
            case R.id.salt:
                Intent intent4 = new Intent(ShopByCategories.this, Salt.class);
                startActivity(intent4);
                break;
            case R.id.jams:
                Intent intent5 = new Intent(ShopByCategories.this, Jams.class);
                startActivity(intent5);
                break;
            case R.id.masala:
                Intent intent6 = new Intent(ShopByCategories.this, Masala.class);
                startActivity(intent6);
                break;
            case R.id.ghee:
                Intent intent7 = new Intent(ShopByCategories.this, Ghee.class);
                startActivity(intent7);
                break;
            case R.id.chocolate:
                Intent intent8 = new Intent(ShopByCategories.this, Chocolate.class);
                startActivity(intent8);
                break;
            case R.id.baby_products:
                Intent intent9 = new Intent(ShopByCategories.this, BabyProducts.class);
                startActivity(intent9);
                break;
            case R.id.biscuits:
                Intent intent10 = new Intent(ShopByCategories.this, Biscuits.class);
                startActivity(intent10);
                break;
            case R.id.dry_fruits:
                Intent intent11 = new Intent(ShopByCategories.this, DryFruits.class);
                startActivity(intent11);
                break;
            case R.id.oil:
                Intent intent12 = new Intent(ShopByCategories.this, Oil.class);
                startActivity(intent12);
                break;
            case R.id.rice:
                Intent intent13 = new Intent(ShopByCategories.this, Rice.class);
                startActivity(intent13);
                break;
            case R.id.flour:
                Intent intent14 = new Intent(ShopByCategories.this, Flour.class);
                startActivity(intent14);
                break;
            case R.id.soups:
                Intent intent15 = new Intent(ShopByCategories.this, Soups.class);
                startActivity(intent15);
                break;
            case R.id.breakfast:
                Intent intent16 = new Intent(ShopByCategories.this, Breakfast.class);
                startActivity(intent16);
                break;
            case R.id.home_kitchen:
                Intent intent17 = new Intent(ShopByCategories.this, HomeKitchen.class);
                startActivity(intent17);
                break;
            case R.id.pickles:
                Intent intent18 = new Intent(ShopByCategories.this, Pickles.class);
                startActivity(intent18);
                break;
        }
    }

    public void Go_to_home(View view) {
        Intent i = new Intent(ShopByCategories.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    public void search(View view) {
        Intent i = new Intent(ShopByCategories.this,SearchCategories.class);
        startActivity(i);
        finish();
    }
}
