package com.example.magicalwinds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchCategories extends AppCompatActivity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    private String type="";

    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_categories);

        // Listview Data
        final String products[] = {"Pulses and Dals", "Bread and Bakery", "Personal Care", "Snacks and Farsan", "Salt and Sugar and Jaggery",
                "Jams and Spreads", "Masala and Spices",
                "Ghee and Vanaspati", "Chocolates", "Biscuits and Cookies", "Baby and Kids", "Dry Fruits", "Cooking Oil", "Rice Products", "Flours and Grains", "Soups",
                "Breakfast and Cereals", "Home and Kitchen", "Pickles"
        };

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (products[position]== "Pulses and Dals")
                {
                    Intent i = new Intent(SearchCategories.this,Pulses.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Bread and Bakery"){
                    Intent i = new Intent(SearchCategories.this,Bread.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Personal Care"){
                    Intent i = new Intent(SearchCategories.this,PersonalCare.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Snacks and Farsan"){
                    Intent i = new Intent(SearchCategories.this,Snacks.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Salt and Sugar and Jaggery"){
                    Intent i = new Intent(SearchCategories.this,Salt.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Jams and Spreads"){
                    Intent i = new Intent(SearchCategories.this,Jams.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]==  "Masala and Spices") {
                    Intent i = new Intent(SearchCategories.this,Masala.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Ghee and Vanaspati"){
                    Intent i = new Intent(SearchCategories.this,Ghee.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Chocolates"){
                    Intent i = new Intent(SearchCategories.this,Chocolate.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]== "Biscuits and Cookies"){
                    Intent i = new Intent(SearchCategories.this,Biscuits.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]== "Baby and Kids"){
                    Intent i = new Intent(SearchCategories.this,BabyProducts.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]== "Dry Fruits"){
                    Intent i = new Intent(SearchCategories.this,DryFruits.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Cooking Oil"){
                    Intent i = new Intent(SearchCategories.this,Cooking.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]== "Rice Products"){
                    Intent i = new Intent(SearchCategories.this,Rice.class);
                    startActivity(i);
                    finish();

                }
                else if (products[position]== "Flours and Grains"){
                    Intent i = new Intent(SearchCategories.this,Flour.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Soups"){
                    Intent i = new Intent(SearchCategories.this,Soups.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Breakfast and Cereals"){
                    Intent i = new Intent(SearchCategories.this,Breakfast.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Home and Kitchen"){
                    Intent i = new Intent(SearchCategories.this,HomeKitchen.class);
                    startActivity(i);
                    finish();
                }
                else if (products[position]== "Pickles"){
                    Intent i = new Intent(SearchCategories.this,Pickles.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchCategories.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
}