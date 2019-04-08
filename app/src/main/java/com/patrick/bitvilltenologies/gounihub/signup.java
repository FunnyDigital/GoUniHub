package com.patrick.bitvilltenologies.gounihub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextpassword2,editTextpassword,userregno,username,userphone;
    Spinner userlevel;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;

Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

editTextpassword2=(EditText)findViewById(R.id.editTextpassword2);
editTextpassword =(EditText)findViewById(R.id.editTextpassword1);
editTextEmail=(EditText)findViewById(R.id.email1);


userregno=(EditText)findViewById(R.id.regnob);
username=(EditText)findViewById(R.id.name);
userphone=(EditText)findViewById(R.id.phone1);
userlevel=(Spinner)findViewById(R.id.spinner1);




progressBar=(ProgressBar)findViewById(R.id.progressBar);


 findViewById(R.id.signup).setOnClickListener(this);

        findViewById(R.id.login).setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();

    }

    private void registerUser(){
        final String email =editTextEmail.getText().toString().trim();
        String password= editTextpassword2.getText().toString().trim();
        String password1= editTextpassword.getText().toString().trim();
        final String regno=userregno.getText().toString().trim();
        final String name=username.getText().toString().trim();
        final String phone=userphone.getText().toString().trim();
        final String level=userlevel.getSelectedItem().toString().trim();






        if (email.isEmpty()){

            editTextEmail.setHint("EMAIL IS EMPTY");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setHint("type a vail email,eh computer science");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextpassword2.setHint("password is empty");
            editTextpassword2.requestFocus();
            return;
        }


        if (password.length()<6){
            editTextpassword2.setHint("minimum is 4 number");
            editTextpassword2.requestFocus();
            return;
        }





        if (password.matches(password1)){



            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){


                        User user = new User(

                                name,
                                phone,
                                regno,
                                level

                        );
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(signup.this,"registration completed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                        finish();

                       Intent intent = new Intent(signup.this,Home.class);
                       startActivity(intent);


                        Toast.makeText(getApplicationContext(),"USER IS REGISTERED",Toast.LENGTH_LONG).show();

                    }else {

                        if (task.getException()instanceof FirebaseAuthException){

                            Toast.makeText(getApplicationContext(),"YOU ARE ALREADY REGISTERED",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }else {

            Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_LONG).show();
        }









    }


    @Override
    public void onClick (View view){
       switch (view.getId()){
           case R.id.signup:

             registerUser();

             break;

           case R.id.login:
               finish();
               startActivity(new Intent(this, MainActivity.class));

               break;

       }

    }


}
