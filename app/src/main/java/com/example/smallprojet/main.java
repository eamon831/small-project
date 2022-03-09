package com.example.smallprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main extends AppCompatActivity {
    RecyclerView list;
    DatabaseReference database;
    adapter ada;
    ArrayList<data>ls;
    Button backAction;
    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list=findViewById(R.id.list);
        database= FirebaseDatabase.getInstance().getReference("Users");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        ls=new ArrayList<>();
        ada=new adapter(this,ls);
        list.setAdapter(ada);



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    data d=dataSnapshot.getValue(data.class);
                    ls.add(d);
                }
                ada.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(main.this, "Error"+ error.getMessage(), Toast.LENGTH_LONG).show();
               // error.getDetails().toString();

            }
        });

    }
}