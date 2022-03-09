package com.example.smallprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    Button login,regi;
    EditText username,pass;
    private FirebaseAuth mauth;
    ProgressBar bar;
    private Activity mActivity;
    public static final int k=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        //assign
        username=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);
        regi=findViewById(R.id.regi);
        bar=findViewById(R.id.bar);
        mauth=FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,regi.class));
                FirebaseUser user= mauth.getCurrentUser();


            }
        });
    }

    private void login() {
        String email=username.getText().toString();
        String password=pass.getText().toString();
        int tf=0;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tf=1;
            username.setError("Invalid email");
            username.requestFocus();

        }
        if(password.length()<6)
        {
            pass.setError("Invalid Password");
            pass.requestFocus();
        }
        if(tf==1)
        {
            return;
        }
        bar.setVisibility(View.VISIBLE);

        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {


                    startActivity(new Intent(login.this, main.class));
                    finish();
                }else{
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
}