package com.example.project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.R;
import com.example.project.TrainerProfileFragment;
import com.example.project.UserListFragment;
import com.example.project.userTaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Trainer_home_Activity extends AppCompatActivity {

BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home_);
           bottomNavigationView=findViewById(R.id.trainer_nav);
          bottomNavigationView .setSelectedItemId(R.id.userListFragment);
          bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.userListFragment:
                        UserListFragment userListFragment=new UserListFragment();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout,userListFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.userTaskFragment:
                        userTaskFragment userTaskFragment=new userTaskFragment();
                        FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.frame_layout,userTaskFragment);
                        fragmentTransaction1.commit();
                        break;
                    case R.id.trainerProfileFragment:
                         TrainerProfileFragment trainerProfileFragment=new TrainerProfileFragment();
                        FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame_layout,trainerProfileFragment);
                        fragmentTransaction2.commit();
                        break;
                }
                  return false;
              }
          });

    }
}