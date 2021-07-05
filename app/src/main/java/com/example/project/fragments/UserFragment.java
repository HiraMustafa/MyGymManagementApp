package com.example.project.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.project.R;
import com.example.project.Utills.MyConstants;
import com.example.project.activities.AdminHomeActivity;
import com.example.project.activities.Trainer_home_Activity;
import com.example.project.activities.UserHomeActivity;
import com.example.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserFragment extends Fragment {
EditText etname ,etusername ,etemail,etpassword,etphoneno,etage;
User user = new User();
Button btnapply;
String key;
FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(MyConstants.USERS);
Context thiscontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user, container, false);
      etusername=view.findViewById(R.id.etuusername);
        etname=view.findViewById(R.id.etuname);
        etemail=view.findViewById(R.id.etuemail);
        mAuth=FirebaseAuth.getInstance();
        etpassword=view.findViewById(R.id.etupassowrd);
        etphoneno=view.findViewById(R.id.etuphoneno);
        etage=view.findViewById(R.id.etuage);
        btnapply=view.findViewById(R.id.btnuapply);
        thiscontext=container.getContext();
        key=myRef.push().getKey();


         btnapply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 if (TextUtils.isEmpty(etusername.getText().toString())){
                     etusername.setError("UserName is Empty");
                     return;
                 }

                 if (TextUtils.isEmpty(etname.getText().toString())){
                     etname.setError("Name is Empty");
                     return;
                 }
                 if (TextUtils.isEmpty(etemail.getText().toString())){
                     etemail.setError("Email is Empty");
                     return;
                 }
                 if (TextUtils.isEmpty(etpassword.getText().toString())){
                     etpassword.setError("Password is Empty");
                     return;
                 }
                 if (TextUtils.isEmpty(etphoneno.getText().toString())){
                     etphoneno.setError("Phone no is Empty");
                     return;
                 }
                 if (TextUtils.isEmpty(etage.getText().toString())){
                     etage.setError("Age is Empty");
                     return;
                 }
                 if (etpassword.getText().toString().length()<6){
                     etpassword.setError("Password must be >-6 Characters");
                     return;
                 }
                 user.username=etusername.getText().toString();
                 user.name=etname.getText().toString();
                 user.email=etemail.getText().toString();
                 user.password=etpassword.getText().toString();
                 user.phone =etphoneno.getText().toString();
                 user.age=etage.getText().toString();
                 user.user_type= MyConstants.TYPE_USER;
                 final String email=etemail.getText().toString();
                 final String password=etpassword.getText().toString();
                 mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                             myRef.child(key).setValue(user);
                             if(user.user_type==MyConstants.TYPE_USER){
                             Toast.makeText(thiscontext, "Registered Successfuly", Toast.LENGTH_SHORT).show();
                               Intent intent= new Intent(thiscontext, UserHomeActivity.class);
                               startActivity(intent);
                             }

                         }
                         else {
                             Toast.makeText(thiscontext, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
         });
        return view;
    }


}