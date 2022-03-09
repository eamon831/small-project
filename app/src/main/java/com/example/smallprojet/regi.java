package com.example.smallprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class regi extends AppCompatActivity {
    EditText username,pass,c_pass;
    Button regi;
    private FirebaseAuth mAuth;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();


        //assign
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        c_pass=findViewById(R.id.confirm_pass);
        regi=findViewById(R.id.regi);
        bar=findViewById(R.id.bar);

        //on click button
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               regiuser();
            }
        });

    }

    private void regiuser() {
        int tf=0;
        String user=username.getText().toString();
        String password=pass.getText().toString();
        String c_password=c_pass.getText().toString();
        if(user.length()==0)
        {
            tf=1;
            username.setError("Invalid email");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches())
        {
            tf=1;
            username.setError("Invalid email");

        }
        if(!password.equals(c_password)||c_password.length()<6)
        {
            c_pass.setError("Invalid");
            tf=1;
        }
        if(password.length()<6)
        {
            pass.setError("At least 6 chracter");
            tf=1;
        }


        if(tf==1)
        {
            return;
        }
        bar.setVisibility(View.VISIBLE);

       mAuth.createUserWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())
               {
                   user u=new user(user,password);
                   FirebaseDatabase.getInstance().getReference("Users")
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()) {
                               Toast.makeText(regi.this, "Successfull", Toast.LENGTH_SHORT).show();
                               finishAffinity();
                               startActivity(new Intent(regi.this,main.class));
                               finish();
                           }
                           else {
                               Toast.makeText(regi.this, "Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }else{
                   bar.setVisibility(View.INVISIBLE);
                   Toast.makeText(regi.this, "Failed", Toast.LENGTH_SHORT).show();

               }
           }
       });

    }
}