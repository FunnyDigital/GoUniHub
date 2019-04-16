package com.patrick.bitvilltenologies.gounihub;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Profileupd extends AppCompatActivity {

    EditText name1,regno1,phone1;
    Spinner level1;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileupd);

        name1=findViewById(R.id.name);
        regno1=findViewById(R.id.regno);
        phone1=findViewById(R.id.phone);
        level1=(Spinner)findViewById(R.id.spinner);
        update=findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData(){
        final  String name = name1.getText().toString().trim();
        final  String regno = regno1.getText().toString().trim();
        final  String phone = phone1.getText().toString().trim();
        final  String level = level1.getSelectedItem().toString().trim();


       User user  = new User(name,regno,phone,level
        );
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Profileupd.this,"YOUR PROFILE HAS BEEN UPDATED",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}
