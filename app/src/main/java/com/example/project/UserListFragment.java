package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.Utills.BaseHelper;
import com.example.project.Utills.MyConstants;
import com.example.project.Utills.Utills;
import com.example.project.activities.detail_Activity;
import com.example.project.adapter.TraineeAdapter;
import com.example.project.adapter.TrainerAdapter;
import com.example.project.model.TraineeModel;
import com.example.project.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    RecyclerView recyclerView;
    TraineeAdapter trainerAdapter;
    List<TraineeModel> list=new ArrayList<>();
    DatabaseReference usersRef= FirebaseDatabase.getInstance().getReference().child(TrainerAdapter.TRAINEE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView=root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();

    }

    private void getData() {
        list.clear();
        Utills.showDialog(getContext());
        usersRef.orderByChild("trainerId").equalTo(BaseHelper.currentUser.id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot s:snapshot.getChildren()) {
                        TraineeModel value = s.getValue(TraineeModel.class);
                            list.add(value);


                    }
                    trainerAdapter=new TraineeAdapter(getContext(),list, true);
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