package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    private EditText feedback_message;
    private Button send_feedback;
    private String emoji,message,savecurrentdate,savecurrentTime="";
    private DatabaseReference feedbackref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback_message=findViewById(R.id.feedback_message);
        send_feedback=findViewById(R.id.send_feedback);
        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        feedbackref= FirebaseDatabase.getInstance().getReference().child("App Feedback").child(user.getUid());

    }

    private void check() {
        message=feedback_message.getText().toString();

        if (TextUtils.isEmpty(message))
        {
            feedback_message.setError("This field is required");
            feedback_message.requestFocus();
        }
        else
        {
            addtodb();
        }
    }

    private void addtodb() {

        Calendar calendardate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MM dd,yyyy");
        savecurrentdate=currentdate.format(calendardate.getTime());

        Calendar calendartime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime=currentTime.format(calendartime.getTime());

        HashMap<String,Object> feedbackmap=new HashMap<>();
        feedbackmap.put("Date",savecurrentdate);
        feedbackmap.put("Time",savecurrentTime);
        feedbackmap.put("Reviews",emoji);
        feedbackmap.put("Message",message);

        feedbackref.updateChildren(feedbackmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Feedback.this);
                builder1.setMessage("Thank You !");
                builder1.setCancelable(true);
                builder1.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Feedback.this, "Response Recorded", Toast.LENGTH_SHORT).show();
                            }

                        });




                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });



    }

    public void excellent(View view) {
        emoji="Excellent";
        Toast.makeText(this, "You selected : Excellent", Toast.LENGTH_SHORT).show();
    }

    public void good(View view) {
        emoji="Good";
        Toast.makeText(this, "You selected : Good", Toast.LENGTH_SHORT).show();
    }

    public void average(View view) {
        emoji="Average";
        Toast.makeText(this, "You selected : Average", Toast.LENGTH_SHORT).show();
    }

    public void poor(View view) {
        emoji="Poor";
        Toast.makeText(this, "You selected : Poor", Toast.LENGTH_SHORT).show();
    }

    public void Very_poor(View view) {
        emoji="Very Poor";
        Toast.makeText(this, "You selected : Very poor ", Toast.LENGTH_SHORT).show();
    }

    public void Go_to_home(View view) {
        Intent i = new Intent(Feedback.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    public void go_back(View view) {
        Intent i = new Intent(Feedback.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

}
