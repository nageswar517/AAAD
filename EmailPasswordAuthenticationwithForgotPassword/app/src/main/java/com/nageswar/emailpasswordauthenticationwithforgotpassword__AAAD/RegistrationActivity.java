package com.nageswar.emailpasswordauthenticationwithforgotpassword__AAAD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText et_regEmail,et_regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        et_regEmail = findViewById(R.id.registerEmailId);
        et_regPassword = findViewById(R.id.registerPassword);
    }

    public void register(View view) {
        String reg_u = et_regEmail.getText().toString();
        String reg_pass = et_regPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(reg_u,reg_pass).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this,
                                    "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openLoginPage(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}