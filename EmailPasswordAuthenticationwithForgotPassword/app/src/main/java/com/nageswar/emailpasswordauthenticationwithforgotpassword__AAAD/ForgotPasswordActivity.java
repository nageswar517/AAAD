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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText et_email;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et_email = findViewById(R.id.forgotEmail);
        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        String email = et_email.getText().toString();
        mAuth.sendPasswordResetEmail(email).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Password Reset mail Sent to registered mail/", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backToLogin(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}