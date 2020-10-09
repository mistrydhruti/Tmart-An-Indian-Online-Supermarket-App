package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magicalwinds.Common.Common;
import com.example.magicalwinds.Model.MyResponse;
import com.example.magicalwinds.Model.Notification;
import com.example.magicalwinds.Model.ProductModel;
import com.example.magicalwinds.Model.Sender;
import com.example.magicalwinds.Model.Token;
import com.example.magicalwinds.PaymentConfig.Config;
import com.example.magicalwinds.Remote.APIService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private TextView name, name1, time, time1, finaltot, ordtot, address, paymethod,promocode,appliedpromocode,promocode_textview;
    private DatabaseReference userRef,orderref,cart,adminorder;
    private Button placeorder,btn_promo_code;
   private  String Pname, amount, category, image, price,mrp,displayprice;
    private String saveCurrentDate, saveCurrentTime;
    private String productRandomKey;
    private String email;
    private String email1;
    private RelativeLayout promo_code;
    String pay, t;
    private String orderId="";
    private String ID="";
   // private String unino="";

    int n1;

    public static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    FirebaseUser user;

    private static final String CHANNEL_ID="tmart";
    private static final String CHANNEL_NAME="tmart";
    private static final String CHANNEL_DESC="tmart";

    APIService mservice;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);


        mservice= Common.getFCMService();
        loadingBar=new ProgressDialog(this);

        //start paypal service
        Intent i = new Intent(this, PayPalService.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(i);

        name = findViewById(R.id.name);
        name1 = findViewById(R.id.name1);

        time = findViewById(R.id.time);
        time1 = findViewById(R.id.time1);
        finaltot = findViewById(R.id.finaltot);
        ordtot = findViewById(R.id.ordtot);
        address = findViewById(R.id.address);
        paymethod = findViewById(R.id.paymethod);
        placeorder = findViewById(R.id.placeorder);
        btn_promo_code=findViewById(R.id.btn_promo_code);
        promocode=findViewById(R.id.add_promo_code);
        appliedpromocode=findViewById(R.id.applied_promo_text);
        promo_code=findViewById(R.id.promo_code);
        promocode_textview=findViewById(R.id.promo_code_textview);
        Random r = new Random();
        n1=r.nextInt(1000000000);


        user = FirebaseAuth.getInstance().getCurrentUser();

        userRef = FirebaseDatabase.getInstance().getReference().child("UserInfo");
         orderref = FirebaseDatabase.getInstance().getReference()
                .child("Orders");

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if (dayOfTheWeek.matches("Thursday"))
        {
            promocode.setVisibility(View.VISIBLE);
            promo_code.setVisibility(View.VISIBLE);
            promocode_textview.setVisibility(View.VISIBLE);

            btn_promo_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplyPromoCode();
                }
            });

        }
        else
        {
            promocode.setVisibility(View.GONE);
            promo_code.setVisibility(View.GONE);
            promocode_textview.setVisibility(View.GONE);

        }



        cart= FirebaseDatabase.getInstance().getReference().child("Cart List");



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Your order Id is "+String.valueOf(n1)+". Your order will be delivered soon");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }



        displayinfo();

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

    }

    private void ApplyPromoCode() {
        String number=ordtot.getText().toString();
        int totalamount= Integer.parseInt(String.valueOf(number));
        if (number.matches(String.valueOf(3000)))
        {
             double savedprice = (25 / 100.0) * totalamount;
            double finalprice = totalamount - savedprice;
             displayprice=String.valueOf(finalprice);
             ordtot.setText(displayprice);
             promocode.setText("5AX08");
             appliedpromocode.setVisibility(View.VISIBLE);
             btn_promo_code.setEnabled(false);
        }
        else if (number.matches(String.valueOf(5000)))
        {
            double savedprice = (30 / 100.0) * totalamount;
            double finalprice = totalamount - savedprice;
            displayprice=String.valueOf(finalprice);
            ordtot.setText(displayprice);
            promocode.setText("7DB07");
            appliedpromocode.setVisibility(View.VISIBLE);
            btn_promo_code.setEnabled(false);
        }
        else if (number.matches(String.valueOf(10000)))
        {
            double savedprice = (50 / 100.0) * totalamount;
            double finalprice = totalamount - savedprice;
            displayprice=String.valueOf(finalprice);
            ordtot.setText(displayprice);
            promocode.setText("0JS02");
            appliedpromocode.setVisibility(View.VISIBLE);
            btn_promo_code.setEnabled(false);
        }
        else{
            Toast.makeText(this, "Check promo code offers ! ", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmOrder() {
        if (pay.matches("Cash On Delivery"))
        {
            cashonDelivery();
        }
        else if (pay.matches("PayPal"))
        {
            processpayment();
        }
    }

    private void processpayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(ordtot.getText().toString()),"USD","Pay for your order",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent i = new Intent(this, PaymentActivity.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        i.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(i,PAYPAL_REQUEST_CODE);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            { PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null)
                {
                    try {
                    String paymentDetails = confirmation.toJSONObject().toString(4);

                    startActivity(new Intent(this,PaymentDetails.class)
                            .putExtra("PaymentDetails",paymentDetails)
                            .putExtra("PaymentAmount",ordtot.getText().toString())
                    );
                    cashOnline();
                } catch (JSONException e) {
                    e.printStackTrace(); }
                } }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode ==PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    private void cashOnline() {
        loadingBar.setTitle("Please wait !");
        loadingBar.setMessage("Processing your order...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final String savecurrentdate, savecurrentTime;

        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime = currenttime.format(calfordate.getTime());


        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("Totalamount", ordtot.getText().toString());
        orderMap.put("Name", name.getText().toString());
        orderMap.put("Email", email);
        orderMap.put("Address", address.getText().toString());
        orderMap.put("OrderedDate", savecurrentdate);
        orderMap.put("OrderedTime", savecurrentTime);
        orderMap.put("UniqueNo",user.getUid());
        orderMap.put("OrderId",String.valueOf(n1));
        orderMap.put("ExTime",t);
        orderMap.put("Payment",pay);
        orderMap.put("state", "not shipped");

        orderref.child("User Order Details").child(String.valueOf(n1))
                .updateChildren(orderMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            sendEMail();
                            loadingBar.dismiss();

                        }

                    }
                });



    }

    private void sendEMail() {
        String subject = "Order successfully placed";
        String message = "Thank you for using tmart. Your Order is placed successfully.Your Order Id is "+String.valueOf(n1)+". Keep using tmart for more exciting offers";

        //Creating SendMail object
        SendMail sm = new SendMail(ConfirmFinalOrderActivity.this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();

        nextStep();

    }

    private void nextStep() {
        displayNotification();
        Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
        sendNotification(String.valueOf(n1));

    }


    private void cashonDelivery() {
        loadingBar.setTitle("Please wait !");
        loadingBar.setMessage("Processing your order...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final String savecurrentdate, savecurrentTime;

        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calfordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime = currenttime.format(calfordate.getTime());


        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("Totalamount", ordtot.getText().toString());
        orderMap.put("Name", name.getText().toString());
        orderMap.put("Email", email);
        orderMap.put("Address", address.getText().toString());
        orderMap.put("OrderedDate", savecurrentdate);
        orderMap.put("OrderedTime", savecurrentTime);
        orderMap.put("UniqueNo",user.getUid());
        orderMap.put("OrderId",String.valueOf(n1));
        orderMap.put("ExTime",t);
        orderMap.put("Payment",pay);
        orderMap.put("state", "not shipped");

        orderref.child("User Order Details").child(String.valueOf(n1))
                .updateChildren(orderMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            sendmsg();
                            loadingBar.dismiss();

                        }

                    }
                });




    }


    private void sendmsg() {
        String subject = "Order successfully placed";
        String message = "Thank you for using tmart. Your Order is placed successfully.Your Order Id is "+String.valueOf(n1)+". Keep using tmart for more exciting offers";

        //Creating SendMail object
        SendMail sm = new SendMail(ConfirmFinalOrderActivity.this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();

        nextactivity();


    }

    private void nextactivity() {


       displayNotification();



        Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                        startActivity(i);


                        sendNotification(String.valueOf(n1));


    }

    private void sendNotification(final String valueOf) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Token");
        Query data= tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Token servertoken1 = dataSnapshot1.getValue(Token.class);
                    Notification notification=new Notification("You have new order"+valueOf,"TMART");
                    Sender content=new Sender(servertoken1.getToken(),notification);
                    mservice.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.body().success==1)
                                    {//Toast.makeText(ConfirmFinalOrderActivity.this, "Thank you ! Order placed successfully!", Toast.LENGTH_SHORT).show();
                                        finish(); }
                                    else
                                    { Toast.makeText(ConfirmFinalOrderActivity.this, "Failed !!", Toast.LENGTH_SHORT).show(); }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.e("ERROR",t.getMessage());
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayNotification()
    {
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.tmart)
                .setContentTitle("Your order is placed")
                .setContentText("Your order ID is "+String.valueOf(n1))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notifi= NotificationManagerCompat.from(this);
        notifi.notify(1,mBuilder.build());

    }
    private void displayinfo() {

        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String n=dataSnapshot.child("Name").getValue().toString();
                    email =dataSnapshot.child("Email").getValue().toString();
                    String addr=dataSnapshot.child("Address").getValue().toString();
                    String flat=dataSnapshot.child("Residential").getValue().toString();
                    pay=dataSnapshot.child("Payment").getValue().toString();
                    t=dataSnapshot.child("Time").getValue().toString();
                    String price=dataSnapshot.child("Price").getValue().toString();


                    name.setText(n);
                    name1.setText(n);
                    time.setText(t);
                    time1.setText(t);
                    finaltot.setText(price);
                    ordtot.setText(price);
                    address.setText(addr);
                    paymethod.setText(pay);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(0,0);

    }
}

  