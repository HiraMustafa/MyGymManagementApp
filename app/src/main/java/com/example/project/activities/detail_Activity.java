package com.example.project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.model.TraineeModel;
import com.example.project.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class detail_Activity extends AppCompatActivity {
   DatabaseReference reference;
   User user=new User();
   TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
       name= findViewById(R.id.tvName);
       email= findViewById(R.id.tvemail);
        String loadsPosition = getIntent().getStringExtra("userId");
        Log.e("mykey",""+loadsPosition);
        reference= FirebaseDatabase.getInstance().getReference("users");
        reference.child(loadsPosition).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot s:snapshot.getChildren()) {

                        user = s.getValue(User.class);
                        Log.e("myobj",""+new Gson().toJson(user));
                    }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        name.setText(user.name);
        email.setText(user.email);
    }
}