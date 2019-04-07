package com.patrick.bitvilltenologies.gounihub;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ATENDANCE extends AppCompatActivity {

    String scannedData;
    Button scanBtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendance);


        final Activity activity = this;
        scanBtn = (Button) findViewById(R.id.scan_btn);
        editText=(EditText)findViewById(R.id.result);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            scannedData = result.getContents();

            if (scannedData != null) {

                // Here we need to handle scanned data...


                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                } else if (resultCode ==RESULT_OK){

                    editText.setText(scannedData);



                    Intent intent= new Intent(ATENDANCE.this,WEB.class);

                    intent.putExtra("first_name",editText.getText().toString());

                    startActivity(intent);

                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();





                }





                Toast.makeText(this, "OK OK", Toast.LENGTH_LONG).show();

            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }






}



