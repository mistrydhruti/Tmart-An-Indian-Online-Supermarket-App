package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyNews extends AppCompatActivity {

    private TextView header,contents,date;
    private int yy;
    private int mm,dd;
    private String month_name;
    private String saveCurrentDate,saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_news);

        header=findViewById(R.id.header);
        contents=findViewById(R.id.contents);
        date=findViewById(R.id.date);


        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        month_name = month_date.format(cal.getTime());

        final Calendar c = Calendar.getInstance();
        yy = c.get(Calendar.YEAR);
        mm = c.get(Calendar.MONTH);
        dd = c.get(Calendar.DAY_OF_MONTH);


        // set current date into textview
        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(dd).append(" ").append(month_name).append(",")
                .append(yy));

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currenttime.format(calendar.getTime());

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Daily News").child(saveCurrentDate);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String cont=dataSnapshot.child("Content").getValue().toString();
                    String title=dataSnapshot.child("Title").getValue().toString();

                    header.setText(title);
                    contents.setText(cont);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
