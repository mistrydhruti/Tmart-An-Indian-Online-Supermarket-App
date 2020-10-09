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

public class Salt extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Salt ", "Sugar","Jaggery"};
    int images[] = {R.drawable.salt, R.drawable.sugar, R.drawable.jaggery};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salt);

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);
        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Intent i = new Intent(Salt.this,SaltSection.class);
                    startActivity(i);
                }
                if (position ==  1) {
                    Intent i = new Intent(Salt.this,Sugar.class);
                    startActivity(i);
                }
                if (position ==  2) {
                    Intent i = new Intent(Salt.this,Jaggery.class);
                    startActivity(i);
                }

            }
        });
        // so item click is done now check list view
    }
    public void back_button(View view) {
        Intent i = new Intent(Salt.this,ShopByCategories.class);
        startActivity(i);
    }

    public void Go_to_home(View view) {
        Intent i = new Intent(Salt.this,HomeActivity.class);
        startActivity(i);
        finish();
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        //String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[],  int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            //  this.rDescription = description;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            //TextView myDescription = row.findViewById(R.id.textView2);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            //  myDescription.setText(rDescription[position]);




            return row;
        }
    }
}


