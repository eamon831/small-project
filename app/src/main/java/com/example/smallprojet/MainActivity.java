package com.example.smallprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.WindowManager;

import org.chromium.base.Callback;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        runJobWithDelay(3000);


    }
    private void runJobWithDelay(int delayTimeMillis){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo: you can call your method what you want.
                FirebaseUser user= auth.getCurrentUser();
                if(user==null)
                {
                    startActivity(new Intent(MainActivity.this,login.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,main.class));
                    finish();
                }

            }
        }, delayTimeMillis);

    }
}