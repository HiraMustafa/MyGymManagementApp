package com.example.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.project.R;
import com.example.project.fragments.TrainerFragment;
import com.example.project.fragments.UserFragment;

public class TrainerLoginActivity extends AppCompatActivity {
Button btnSignIn,btnApply;
FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);
        btnApply=findViewById(R.id.btnApply);
        btnSignIn=findViewById(R.id.btnSignIn);
        frameLayout =findViewById(R.id.framlyout);
        loadFragment(new UserFragment());
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new UserFragment());
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new TrainerFragment());
            }
        });
    }
    void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framlyout,fragment)
                .commit();
    }
}