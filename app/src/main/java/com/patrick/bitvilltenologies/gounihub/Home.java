package com.patrick.bitvilltenologies.gounihub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class Home extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101;
    ImageView imageViewz;
    EditText name,PHONE,Regno;
    TextView textView;
    Spinner spinner;

    ProgressBar progressBarz;

    Uri uriProfileImage;

    String profileImageurl;

    FirebaseUser mAuth;

    DatabaseReference databaseReference;

    String uid;

///bottom navbar///////////////////////////////////////////////////////////////////////////////////


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.notification:
                  Intent intent =new Intent(Home.this,Notification.class);
                  startActivity(intent);
                    return true;

                case R.id.profile:

                    return true;

            }
            return false;
        }
    };


//////end butom navebar//////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//bootom nabe callingher
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//////////////////////////
      android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance().getCurrentUser();


        imageViewz=(ImageView)findViewById(R.id.imageView1);
        name = (EditText) findViewById(R.id.name1);
        PHONE=(EditText)findViewById(R.id.phone1);
        textView=(TextView)findViewById(R.id.EV);
        Regno=(EditText)findViewById(R.id.regno);
        spinner=(Spinner)findViewById(R.id.spinner1);
        progressBarz=(ProgressBar)findViewById(R.id.progressBar2);

        uid=mAuth.getUid();






databaseReference=FirebaseDatabase.getInstance().getReference();

databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


//////////////////////////////////////////////////////////////////////////////

        imageViewz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showaImageChooser();

            }
        });

        loadUserInformation();

        findViewById(R.id.save1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }


        });



    }





    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth==null){
            finish();
            startActivity(new Intent(this,MainActivity .class));
        }
    }


//////////////PROBlem HERE //////////////////////////////


    private void loadUserInformation() {

final FirebaseUser firebaseUser = mAuth;



    if (firebaseUser.getPhotoUrl() != null) {

        Glide.with(Home.this.getApplicationContext()).load(firebaseUser.getPhotoUrl().toString()).into(imageViewz);

    }

    if (firebaseUser.getDisplayName() != null) {
        name.setText(firebaseUser.getDisplayName());

    }




    if (firebaseUser.isEmailVerified()){
        textView.setText("Emali AND REGNO  not verified");

    }else {
        textView.setText(" YOUR EMAIL and REGNO HAS BEEN verified ");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
Toast.makeText(Home.this,"DATA SENT FOR VERIFICATION",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    }
//////////////////////////////////////////////////////////
    private void saveUserInformation() {

        String displayname = name.getText().toString();
        String Phone=PHONE.getText().toString();
        String Spinner = spinner.getSelectedItem().toString();
        String regno= Regno.getText().toString();




        if (displayname.isEmpty()&& regno.isEmpty()&&Phone.isEmpty()&&Spinner.isEmpty()) {
            name.setHint("PLEASE FILL FIELDS TO COMPLETE PROFILE");
            PHONE.setHint("PLEASE FILL FIELDS TO COMPLETE PROFILE");
            spinner.setPrompt("PLEASE FILL FIELDS TO COMPLETE PROFILE");
            Regno.setHint("PLEASE FILL FIELDS TO COMPLETE PROFILE");


        }




        FirebaseUser firebaseUser = mAuth;

        progressBarz.setVisibility(View.VISIBLE);

        if (firebaseUser != null && profileImageurl != null ) {



            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname)
                    .setPhotoUri(Uri.parse(profileImageurl))
                    .build();






            firebaseUser.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressBarz.setVisibility(View.GONE);

                            if (task.isSuccessful()) {

                                Toast.makeText(Home.this, "PROFILE UPDATED", Toast.LENGTH_LONG).show();
                            }


                        }

                    });

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK   && data!=null&&data.getData()!=null){
            uriProfileImage= data.getData();




            try {
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage );
                imageViewz.setImageBitmap(bitmap);

                uploadImagetofirebasestorage();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImagetofirebasestorage() {
        progressBarz.setVisibility(View.VISIBLE);
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profile/"+System.currentTimeMillis()+".jpg");


        if(uriProfileImage != null){



            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBarz.setVisibility(View.GONE);
                    profileImageurl =taskSnapshot.getDownloadUrl().toString();

                }
                //TO HANDEL FAILUR OF UPLAED PIC
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBarz.setVisibility(View.GONE);
                    Toast.makeText(Home.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

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

    private void showaImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT DP"),CHOOSE_IMAGE);

    }








    public void Portal(View view){
        Intent intent= new Intent(Home.this,Portal.class);
        startActivity(intent);
    }
    public void ATENDANCE(View view){
        Intent intent= new Intent(Home.this,ATENDANCE.class);
        startActivity(intent);
    }
    public void notofication(View view){


        Snackbar.make(view, "REQUESTING FOR SCHOOL PERMISSIONS.", Snackbar.LENGTH_SHORT).show();
    }

    public void My(View view){

        Intent intent= new Intent(Home.this,Myclasses.class);
        startActivity(intent);

    }


}

