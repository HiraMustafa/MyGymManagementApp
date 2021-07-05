package com.example.project.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.Utills.MyConstants;
import com.example.project.Utills.Utills;
import com.example.project.adapter.TrainerAdapter;
import com.example.project.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserTrainerFragment extends Fragment {
    RecyclerView recyclerView;
    TrainerAdapter trainerAdapter;
    List<User> list=new ArrayList<>();
    DatabaseReference usersRef= FirebaseDatabase.getInstance().getReference().child(MyConstants.USERS);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user_trainer, container, false);
        recyclerView=inflate.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        getData();
        return inflate;
    }
    private void getData() {
        list.clear();
        Utills.showDialog(getContext());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot s:snapshot.getChildren()) {
                        User value = s.getValue(User.class);
                        if(value.user_type== MyConstants.TYPE_TRAINER) {
                            list.add(value);
                        }
                    }
                    trainerAdapter=new TrainerAdapter(getContext(),list,true);
                    recyclerView.setAdapter(trainerAdapter);
                }else {
                    Toast.makeText(getContext(), "Users does not exits", Toast.LENGTH_SHORT).show();
                }
                Utills.dissmiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utills.dissmiss();
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}