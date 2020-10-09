package com.example.magicalwinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    EditText addre, name, phone;
    List<Address> addresses;
    Geocoder geocoder;
    private DatabaseReference userRef;
    private Button submit;
    private String Name, Phone, Addre, a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        addre = findViewById(R.id.addre);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        submit=findViewById(R.id.submit);
        geocoder = new Geocoder(this, Locale.getDefault());
        userRef = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = addre.getText().toString();
                Name = name.getText().toString();
                Phone = phone.getText().toString();
                Addre = addre.getText().toString();
                if (TextUtils.isEmpty(Name)) {
                    name.setError("This field is required");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(Phone)) {
                    phone.setError("This field is required");
                    phone.requestFocus();
                } else if (TextUtils.isEmpty(Addre)) {
                    addre.setError("This field is required");
                    addre.requestFocus();
                } else {
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
                    String p=dataSnapshot.child("Phone").getValue().toString();
                    String addr=dataSnapshot.child("Address").getValue().toString();

                    name.setText(n);
                    phone.setText(p);
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
                                        // String postalcode=addresses.get(0).getPostalCode();

                                        String fulladdress = address;
                                        addre.setText(fulladdress);
                                        displayInfo();


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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Name", Name);
        userMap.put("Phone", Phone);
        userMap.put("Address", a);

        userRef.child(user.getUid()).updateChildren(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LocationActivity.this, ShippingDetailsActivity.class);
                            startActivity(i);

                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(LocationActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();

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

                String fulladdress=address+","+area+","+city+","+country+","+postalcode;
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
