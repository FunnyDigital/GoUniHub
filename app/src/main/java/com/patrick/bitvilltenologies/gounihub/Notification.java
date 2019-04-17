package com.patrick.bitvilltenologies.gounihub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.patrick.bitvilltenologies.gounihub.Config.Config;
import com.squareup.picasso.Picasso;

public class Notification extends AppCompatActivity {

ImageView imageViewno;
RecyclerView recyclerView;
FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
LinearLayoutManager   mLayoutManager;


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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("GoUni Hub");

        mLayoutManager = new LinearLayoutManager(Notification.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(mLayoutManager);



        firebaseDatabase =FirebaseDatabase.getInstance();
        reference= firebaseDatabase.getReference("Users/POSTS");


        //imageViewno=findViewById(R.id.imageViewno);
        if (!TextUtils.isEmpty(Config.imageLink))
            Picasso.get().load(Config.imageLink).into(imageViewno);
    }


    @Override
    protected void onStart() {
        super.onStart();
       FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerOptions=
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.customrow,
                        ViewHolder.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {

                viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());

                    }
                };

       recyclerView.setAdapter(firebaseRecyclerOptions);
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


    public void Portal(View view){
        Intent intent= new Intent(Notification.this,Portal.class);
        startActivity(intent);
    }
    public void ATENDANCE(View view){
        Intent intent= new Intent(Notification.this,ATENDANCE.class);
        startActivity(intent);
    }
    public void Achive(View view){

        Intent intent= new Intent(Notification.this,Achive.class);
        startActivity(intent);



    }

    public void My(View view){

        Intent intent= new Intent(Notification.this,Myclasses.class);
        Snackbar.make(view, "REQUESTING FOR SCHOOL PERMISSIONS.", Snackbar.LENGTH_SHORT).show();
        startActivity(intent);

    }

}
