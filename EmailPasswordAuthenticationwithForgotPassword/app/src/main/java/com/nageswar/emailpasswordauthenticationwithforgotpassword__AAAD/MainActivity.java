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

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        et_username = findViewById(R.id.loginEmailId);
        et_password = findViewById(R.id.loginPassword);
    }

    public void login(View view) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        mAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,
                                    "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,
                                    "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openForgotActivity(View view) {
        Intent i = new Intent(this,ForgotPasswordActivity.class);
        startActivity(i);
    }

    public void openRegistrationActivity(View view) {
        Intent i = new Intent(this,RegistrationActivity.class);
        startActivity(i);
    }
}