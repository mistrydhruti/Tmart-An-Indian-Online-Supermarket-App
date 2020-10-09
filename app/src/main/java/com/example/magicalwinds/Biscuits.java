package com.example.magicalwinds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Biscuits extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Cookies","Glucose Biscuits","Marie Biscuits","Salty BIscuits","Cream Biscuits","Wafer Biscuits","Khari and toast"};
    int images[] = {R.drawable.biscuits, R.drawable.glucose, R.drawable.marie, R.drawable.salty, R.drawable.cream,R.drawable.wafer,R.drawable.khari};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biscuits);
        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Intent i = new Intent(Biscuits.this,Cookies.class);
                    startActivity(i); }
                if (position ==  1) {
                    Intent i = new Intent(Biscuits.this,Glucose.class);
                    startActivity(i); }
                if (position ==  2) {
                    Intent i = new Intent(Biscuits.this,Marie.class);
                   startActivity(i); }
                if (position ==  3) {
                    Intent i = new Intent(Biscuits.this,Salty.class);
                    startActivity(i); }
                if (position ==  4) {
                    Intent i = new Intent(Biscuits.this,CreamBiscuits.class);
                    startActivity(i); }
                if (position ==  5) {
                    Intent i = new Intent(Biscuits.this,WaferBiscuits.class);
                    startActivity(i); }
                if (position ==  6) {
                    Intent i = new Intent(Biscuits.this,Khari.class);
                    startActivity(i); }
            }
        });
    }
    public void back_button(View view) {
        Intent i = new Intent(Biscuits.this,ShopByCategories.class);
        startActivity(i); }

    public void Go_to_home(View view) {
        Intent i = new Intent(Biscuits.this,HomeActivity.class);
        startActivity(i);
        finish(); }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter (Context c, String title[],  int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs; }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            return row;
        }
    }
}


