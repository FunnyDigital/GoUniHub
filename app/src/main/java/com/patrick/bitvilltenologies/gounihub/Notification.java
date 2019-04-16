package com.patrick.bitvilltenologies.gounihub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.patrick.bitvilltenologies.gounihub.Config.Config;
import com.squareup.picasso.Picasso;

public class Notification extends AppCompatActivity {

ImageView imageViewno;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.notification:

                    return true;

                case R.id.profile:
                    Intent intent =new Intent(Notification.this,Home.class);
                    finish();
                    startActivity(intent);
                    return true;
            } 
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.notification);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //imageViewno=findViewById(R.id.imageViewno);

        if (!TextUtils.isEmpty(Config.imageLink))
            Picasso.with(this).load(Config.imageLink).into(imageViewno);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater MENU = getMenuInflater();
        MENU.inflate(R.menu.exitandabout,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.exit:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
        }
        return true;
    }

}
