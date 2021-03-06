package com.me.nageswar.realtimedatabasetest__AAAD;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //FirebaseDatabase
    //DataBase Reference to use realtime database create objects for the above
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText et_rollno,et_name,et_mobile,et_email,et_sp_roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        et_rollno = findViewById(R.id.rollNo);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobileNo);
        et_email = findViewById(R.id.email);
        et_sp_roll = findViewById(R.id.sp_rollno);
    }

    public void save(View view) {
        String rollNo = et_rollno.getText().toString();
        String name = et_name.getText().toString();
        String mobile = et_mobile.getText().toString();
        String email = et_email.getText().toString();
        Student s = new Student();
        s.setRollNo(rollNo);
        s.setName(name);
        s.setMobile(mobile);
        s.setEmail(email);
        reference.child("Student").push().setValue(s)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,
                            "Data Saved", Toast.LENGTH_LONG).show();
                    et_rollno.setText("");
                    et_name.setText("");
                    et_mobile.setText("");
                    et_email.setText("");
                }
            }
        });
    }

    public void read(View view) {
        Intent i = new Intent(this,DataActivity.class);
        startActivity(i);
    }

    public void update(View view) {
        final String u_rollno = et_sp_roll.getText().toString();
        final String u_name = et_name.getText().toString();
        final String u_mobile = et_mobile.getText().toString();
        final String u_email = et_email.getText().toString();
        reference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Student s = dataSnapshot.getValue(Student.class);
                    if(u_rollno.equals(s.getRollNo())){
                        Toast.makeText(MainActivity.this,
                                "Data Exists", Toast.LENGTH_SHORT).show();
                        String key = dataSnapshot.getKey();
                        s.setName(u_name);
                        s.setMobile(u_mobile);
                        s.setEmail(u_email);
                        reference.child("Student").child(key).setValue(s);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delete(View view) {
        final String d_rollno = et_sp_roll.getText().toString();
        reference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Student s = snapshot1.getValue(Student.class);
                    if(d_rollno.equals(s.getRollNo())){
                        String key = snapshot1.getKey();
                        reference.child("Student").child(key).removeValue();
                        Intent i = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
