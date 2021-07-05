package com.example.project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.project.R;
import com.example.project.Utills.BaseHelper;
import com.example.project.Utills.MyConstants;
import com.example.project.Utills.PrefHelper;
import com.example.project.Utills.Utills;
import com.example.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
EditText etemail,etpassword;
TextView tvaccount;
 Button btnlogin;
 FirebaseAuth mAuth;
 String email , password;
 PrefHelper prefHelper;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(MyConstants.USERS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        btnlogin=findViewById(R.id.btnlogin);
        tvaccount=findViewById(R.id.tvaccount);
        prefHelper=PrefHelper.getInstance(this);
        //if (mAuth.getCurrentUser()!=null){
            //Intent intent = new Intent(MainActivity.this, Dashboard.class);
           // startActivity(intent);
         //   finish();

        //}
        User user = prefHelper.getObject(MyConstants.IS_LOGIN, User.class);
        if(user!=null){
            BaseHelper.currentUser=user;
            if(BaseHelper.currentUser.user_type==MyConstants.TYPE_ADMIN){
                Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
            else if(BaseHelper.currentUser.user_type==MyConstants.TYPE_USER){
                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                startActivity(intent);
                finish();                            }
            else if(BaseHelper.currentUser.user_type==MyConstants.TYPE_TRAINER){
                Intent intent= new Intent(MainActivity.this, Trainer_home_Activity.class);
                startActivity(intent);
                finish();
            }

        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              email=etemail.getText().toString();
              password=etpassword.getText().toString();
              if (TextUtils.isEmpty(email)){
                  etemail.setError("Email is Empty");
                  return;
              }
                if (TextUtils.isEmpty(password)){
                    etpassword.setError("Enter Password");
                    return;
                }
              if (password.length()<6){
                  etpassword.setError("Password must be >-6 Characters");
                return;
              }
                authenticate();
            }


                            });

        tvaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TrainerLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void authenticate() {
        Utills.showDialog(this);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.e("signInWith","signInWithEmailAndPassword");
                    checkLogin();
                   /* Toast.makeText(MainActivity.this, "successful created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
*/                        }
                else {
                    Utills.dissmiss();
                    Toast.makeText(MainActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkLogin() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("signInWith","signInWithEmailAndPassword");

                if(snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                        User value = snapshot1.getValue(User.class);
                        if(value.email.equals(email) && value.password.equals(password)){
                            if(value.user_type==MyConstants.TYPE_ADMIN){
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            prefHelper.saveObject(MyConstants.IS_LOGIN,value);
                            startActivity(intent);
                                finish();
                            }
                            else if(value.user_type==MyConstants.TYPE_USER){
                                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                                startActivity(intent);
                                prefHelper.saveObject(MyConstants.IS_LOGIN,value);
                                finish();                            }
                            else if(value.user_type==MyConstants.TYPE_TRAINER){
                                Intent intent= new Intent(MainActivity.this, Trainer_home_Activity.class);
                                startActivity(intent);
                                prefHelper.saveObject(MyConstants.IS_LOGIN,value);
                                finish();
                            }
                            break;
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "User does not exits", Toast.LENGTH_SHORT).show();
                }
                Utills.dissmiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utills.dissmiss();
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}


