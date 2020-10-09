package com.example.magicalwinds;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;

import com.example.magicalwinds.Model.Token;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    FloatingActionButton fb;
    private DatabaseReference orderref;

    private String type ="";
    ViewFlipper viewFlipper,vf1;
    private String orderId="";
    private String orderID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        updateToken(FirebaseInstanceId.getInstance().getToken());



        fb =(FloatingActionButton)findViewById(R.id.fab);

        int images[] ={R.drawable.sale1,R.drawable.vf1,R.drawable.vf2,R.drawable.vf3,R.drawable.wild,R.drawable.sale2,R.drawable.vfessentials,R.drawable.banner,R.drawable.gir,R.drawable.offers,R.drawable.vfhomeele,R.drawable.vfsnack2,};
        int img[]={R.drawable.ny,R.drawable.vfhome,R.drawable.vfmons,R.drawable.vfsnack,R.drawable.seaa,R.drawable.vfsnack1,R.drawable.flav,R.drawable.vfsnack3,R.drawable.sav,R.drawable.vfmasala,R.drawable.uni,R.drawable.vfoil,R.drawable.vfmonsoon};
        ImageView img1 = (ImageView) findViewById(R.id.pulses_circleimage);
        ImageView img2 = (ImageView) findViewById(R.id.bread_circleimage);
        ImageView img3 = (ImageView) findViewById(R.id.personalcare_circleimage);
        ImageView img4 = (ImageView) findViewById(R.id.snacks_circleimage);
        ImageView img5 = (ImageView) findViewById(R.id.salt_circleimage);
        ImageView img6 = (ImageView) findViewById(R.id.jams_circleimage);
        ImageView img7 = (ImageView) findViewById(R.id.masala_circleimage);
        ImageView img8 = (ImageView) findViewById(R.id.ghee_circleimage);
        ImageView img9 = (ImageView) findViewById(R.id.chocolate_circleimage);
        ImageView img10 = (ImageView) findViewById(R.id.biscuits_circleimage);
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.dals);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.bread);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.personal_care);
        Bitmap bm4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.snacks);
        Bitmap bm5 = BitmapFactory.decodeResource(getResources(),
                R.drawable.rocksalt);
        Bitmap bm6 = BitmapFactory.decodeResource(getResources(),
                R.drawable.jams);
        Bitmap bm7 = BitmapFactory.decodeResource(getResources(),
                R.drawable.masala);
        Bitmap bm8 = BitmapFactory.decodeResource(getResources(),
                R.drawable.ghee);
        Bitmap bm9 = BitmapFactory.decodeResource(getResources(),
                R.drawable.chocolates);
        Bitmap bm10 = BitmapFactory.decodeResource(getResources(),
                R.drawable.biscuits);
        Bitmap resized = Bitmap.createScaledBitmap(bm1, 200, 200, true);
        Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
        img1.setImageBitmap(conv_bm);

        viewFlipper=findViewById(R.id.v_flipper);
        vf1=findViewById(R.id.view_flipper);

        for (int image: images)
        {
            flipperimages(image);
        }
        for (int ima: img)
        {
            flipperimages1(ima);
        }

        Paper.init(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome!");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                (this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        /*NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView =navigationView.getHeaderView(0);
        TextView userNameTextview=headerView.findViewById(R.id.user_profile_name);



    }

    private void updateToken(String token1) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference("Token");

        Token token = new Token(token1,false);
        tokens.child(user.getUid()).setValue(token);
    }

    private void flipperimages1(int ima) {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(ima);

        vf1.addView(imageView);
        vf1.setFlipInterval(3000);
        vf1.setAutoStart(true);

        vf1.setInAnimation(this,android.R.anim.slide_out_right);
        vf1.setOutAnimation(this,android.R.anim.slide_in_left);
    }

    public void flipperimages(int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
           // super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

      /* if (id==R.id.action_search)
        {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item)
    {

        int id= item.getItemId();
        if(id == R.id.mydiary)
        {

            Intent i = new Intent(HomeActivity.this,NotepadCreation.class);
            startActivity(i);
        }
        else if(id==R.id.nav_categories)
        {
            Intent i = new Intent(HomeActivity.this,ShopByCategories.class);
            startActivity(i);

        }
        else if(id==R.id.nav_cart)
        {
           Intent i = new Intent(HomeActivity.this,CartActivity.class);
            startActivity(i);

        }
        else if(id==R.id.nav_orders)
        {
            Intent i = new Intent(HomeActivity.this,Orders.class);
            startActivity(i);

        }
        else if(id==R.id.nav_feed)
        {
            Intent i = new Intent(HomeActivity.this,Feedback.class);
            startActivity(i);
        }
        else if(id==R.id.dailynews)
        {
            Intent i = new Intent(HomeActivity.this,DailyNews.class);
            startActivity(i);
        }
        else if(id==R.id.offers)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            if (dayOfTheWeek.matches("Thursday"))
            {
                Intent i = new Intent(HomeActivity.this,SpecialOffer.class);
                startActivity(i);
            }
            else
            {
                Intent i = new Intent(HomeActivity.this,SpecialVideo.class);
                startActivity(i);
            }

        }

        else if(id==R.id.nav_aboutus)
        {
            Intent i = new Intent(HomeActivity.this,AboutUs.class);
            startActivity(i);
        }

        else if(id==R.id.nav_signout)
        {
            Intent i = new Intent(HomeActivity.this,SignOut.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return true;
        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 100, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;


    }

    public void seeallproducts(View view) {
        Intent i = new Intent(HomeActivity.this,SeeAllDisplayActivity.class);
        startActivity(i);
        finish();
    }

    public void Biscuits(View view) {
        Intent i = new Intent(HomeActivity.this,Biscuits.class);
        startActivity(i);
        finish();
    }

    public void back_button(View view) {
        Intent i = new Intent(HomeActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void Go_to_home(View view) {
        Intent i = new Intent(HomeActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void chocolates(View view) {
        Intent i = new Intent(HomeActivity.this,Chocolate.class);
        startActivity(i);
        finish();
    }

    public void personalcare(View view) {
        Intent i = new Intent(HomeActivity.this,PersonalCare.class);
        startActivity(i);
        finish();
    }

    public void pulses(View view) {
        Intent i = new Intent(HomeActivity.this,Pulses.class);
        startActivity(i);
        finish();
    }

    public void bread(View view) {
        Intent i = new Intent(HomeActivity.this,Bread.class);
        startActivity(i);
        finish();
    }

    public void snacks(View view) {
        Intent i = new Intent(HomeActivity.this,Snacks.class);
        startActivity(i);
        finish();
    }

    public void salt(View view) {
        Intent i = new Intent(HomeActivity.this,Salt.class);
        startActivity(i);
        finish();
    }

    public void jams(View view) {
        Intent i = new Intent(HomeActivity.this,Jams.class);
        startActivity(i);
        finish();
    }

    public void masala(View view) {
        Intent i = new Intent(HomeActivity.this,Masala.class);
        startActivity(i);
        finish();
    }

    public void ghee(View view) {
        Intent i = new Intent(HomeActivity.this,Ghee.class);
        startActivity(i);
        finish();
    }

    public void bestselling(View view) {
        Intent i = new Intent(HomeActivity.this,SeeAllDisplayActivity.class);
        startActivity(i);
        finish();
    }

}