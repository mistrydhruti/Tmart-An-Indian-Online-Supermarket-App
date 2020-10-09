package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShippingDetailsActivity extends AppCompatActivity {

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    EditText addre, name, email,flatno;
    List<Address> addresses;
    Geocoder geocoder;
    private DatabaseReference userRef;
    private Button submit;
    private String Name, Email1, Addre, a,Delitiming,Paymethod,Flat,Address,postalcode,fulladdress;
    private RadioGroup rg,rgpay;
    private RadioButton radioButton,radioButton1;
    private TextView pindel;
    private String price="";
    private String orderId="";
    private String ID="";
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);

        //orderId=getIntent().getStringExtra("orderId");
        price=getIntent().getStringExtra("price");
       // ID=Settings.Secure.getString(getApplicationContext().getContentResolver(),
         //       Settings.Secure.ANDROID_ID);

        addre = findViewById(R.id.addre);
        name = findViewById(R.id.name);
        email = findViewById(R.id.phone);
       // pindel=findViewById(R.id.deliveryrestr);
        submit=findViewById(R.id.submit);
        flatno=findViewById(R.id.flatno);
        geocoder = new Geocoder(this, Locale.getDefault());
        userRef = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        rg=(RadioGroup)findViewById(R.id.rg);
        rgpay=(RadioGroup)findViewById(R.id.rgpay);
        loadingBar=new ProgressDialog(this);

        getLastLocation();
       // phone.setText(price);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rg.getCheckedRadioButtonId();
                int selectedid2=rgpay.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                radioButton1=(RadioButton)findViewById(selectedid2);


                a = addre.getText().toString();
                Name = name.getText().toString();
                Email1 = email.getText().toString().trim();
                Addre = addre.getText().toString();
                Delitiming=radioButton.getText().toString();
                Paymethod=radioButton1.getText().toString();
                Flat=flatno.getText().toString();

                String regex = "^[+]?[0-9]{10,13}$";

                if(selectedId==-1 && selectedid2==-1) {
                    Toast.makeText(ShippingDetailsActivity.this, "Nothing selected", Toast.LENGTH_LONG).show(); }
                if (TextUtils.isEmpty(Name)) {
                    name.setError("This field is required");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(Email1) ) {
                    email.setError("This field is required");
                    email.requestFocus();
                }/*else if(Phone.matches(regex))
                {
                    phone.setError("Enter Valid Phone Number");
                } */
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email1).matches())
                {
                    email.setError("Invalid Email!");
                    email.requestFocus();
                }

               else if (TextUtils.isEmpty(Addre)) {
                    addre.setError("This field is required");
                    addre.requestFocus();
                }
                else if(TextUtils.isEmpty(Flat)){
                     flatno.setError("This field is required");
                     flatno.requestFocus();
                 }
                     else {
                    saveInfo();
                }
            }
        });


    }

    private void displayInfo() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    String n=dataSnapshot.child("Name").getValue().toString();
                    String p=dataSnapshot.child("Email").getValue().toString();
                    String addr=dataSnapshot.child("GPS").getValue().toString();
                    String flat=dataSnapshot.child("Address").getValue().toString();


                    name.setText(n);
                    email.setText(p);
                    addre.setText(addr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        String address = addresses.get(0).getAddressLine(0);
                                        String area = addresses.get(0).getLocality();
                                        String city = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                         postalcode=addresses.get(0).getPostalCode();

                                         fulladdress = address;
                                        addre.setText(fulladdress);
                                       // pindel.setText(postalcode);


                                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                if (snapshot.hasChild(user.getUid())) {
                                                    displayInfo();
                                                    // run some code
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });



                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private void saveInfo() {
        loadingBar.setTitle("Making Your Receipt");
        loadingBar.setMessage("Please wait !");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Address=Flat+ fulladdress;
        String savecurrenttime,savecurrentdate;
        Calendar calfordate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=currentdate.format(calfordate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currentTime.format(calfordate.getTime());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Name", Name);
        userMap.put("Email", Email1);
        userMap.put("GPS", addre.getText().toString());
        userMap.put("Residential",Flat);
        userMap.put("Address",Address);
        userMap.put("Time",Delitiming);
        userMap.put("Payment",Paymethod);
        userMap.put("Price",price);
        userMap.put("UniqueNo",user.getUid());



        userRef.child(user.getUid()).updateChildren(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //if(postalcode.equals("400012") || postalcode.equals("400013")) {

                            //}
                            //else
                            //{
                               // pindel.setVisibility(View.VISIBLE);
                               // addre.setError("Sorry, delivery is not possible at this pincode");
                           // }

                            Intent i = new Intent(ShippingDetailsActivity.this, ConfirmFinalOrderActivity.class);
                            //i.putExtra("ID",ID);
                            // i.putExtra("orderId",orderId);
                            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            loadingBar.dismiss();

                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(ShippingDetailsActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }
                    }
                });
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            Location mLastLocation = locationResult.getLastLocation();
            try {
                addresses=geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
                String address=addresses.get(0).getAddressLine(0);
                String area=addresses.get(0).getLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();

                fulladdress=address+","+area+","+city+","+country+","+postalcode;
                addre.setText(fulladdress);


            } catch (IOException e) {
                e.printStackTrace();
            }
            // latTextView.setText(mLastLocation.getLatitude()+"");



        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
}
