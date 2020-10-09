package com.example.magicalwinds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magicalwinds.PaymentConfig.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Paypal extends AppCompatActivity {

    private String price,orderID="";
    private TextView edtamt,orderid;
    public static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    private Button btnpaynow;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        //start paypal service
        Intent i = new Intent(this,PayPalService.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(i);

        price=getIntent().getStringExtra("Amount");
        orderID=getIntent().getStringExtra("OrderID");

        edtamt=findViewById(R.id.edtaccount);
        orderid=findViewById(R.id.orderid);

        edtamt.setText(price);
        orderid.setText(orderID);
        btnpaynow=findViewById(R.id.btnpayNow);

        btnpaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment();
            }
        });

    }
    private void processpayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(price),"USD","Pay for your order",PayPalPayment.PAYMENT_INTENT_SALE);
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
                { try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetails.class)
                            .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("PaymentAmount",price)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace(); }
                } }
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode ==PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }
}
