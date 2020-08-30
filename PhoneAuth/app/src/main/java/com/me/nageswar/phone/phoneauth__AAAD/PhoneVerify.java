package com.me.nageswar.phone.phoneauth__AAAD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerify extends AppCompatActivity {

    private String verificationID;
    FirebaseAuth mAuth;
    private ProgressBar pb;
    private EditText et_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        mAuth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.progressBar);
        et_code = findViewById(R.id.codeet);
        String phoneNum = getIntent().getStringExtra("phonenumber");
        SendVerificationCode(phoneNum);
        findViewById(R.id.btn_SignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = et_code.getText().toString().trim();
                if(code.isEmpty()||code.length()<6){
                    et_code.setError("Enter Code...");
                    et_code.requestFocus();
                    return;
                }
                pb.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }
        });
    }
    private void VerifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(PhoneVerify.this,ProfileActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(PhoneVerify.this,
                                    "Verification Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void SendVerificationCode(String number){
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                        number,
                        2,
                        TimeUnit.MINUTES,
                        TaskExecutors.MAIN_THREAD,
                        mCallBack
                        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                VerifyCode(code);
                pb.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneVerify.this,
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}