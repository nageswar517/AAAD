package com.me.nageswar.phone.phoneauth__AAAD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public class MainActivity extends AppCompatActivity {
    EditText et_phoneNum;
    Button btn_cont;
    Spinner spinner;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        et_phoneNum = findViewById(R.id.phoneNum);
        spinner = findViewById(R.id.sc);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryName));
        btn_cont = findViewById(R.id.btnCont);
        btn_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = CountryData.countryAreaCode[spinner.getSelectedItemPosition()];
                String phoneNum = et_phoneNum.getText().toString().trim();
                if(phoneNum.isEmpty() || phoneNum.length()<10){
                    et_phoneNum.setError("Enter A Valid Phone Number");
                    et_phoneNum.requestFocus();
                    return;
                }
                String num = "+" + code + phoneNum;
                Intent i = new Intent(MainActivity.this,PhoneVerify.class);
                i.putExtra("phonenumber",num);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ProfileActivity.class));
        }
    }
}