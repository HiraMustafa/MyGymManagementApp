package com.example.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.Utills.BaseHelper;
import com.example.project.Utills.Utills;
import com.example.project.activities.detail_Activity;
import com.example.project.model.TraineeModel;
import com.example.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.MyViewHolder> {
    public static final String TRAINEE = "trainee";
    Context context;
    List<TraineeModel> list;
boolean isUser=false;
    public TraineeAdapter(Context context, List<TraineeModel> list, boolean b) {
        this.context = context;
        this.list = list;
        isUser=b;
    }public TraineeAdapter(Context context, List<TraineeModel> list) {
        this.context = context;
        this.list = list;
    }
    DatabaseReference trainee = FirebaseDatabase.getInstance().getReference().child(TRAINEE);

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trinee_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TraineeModel user = list.get(position);
        holder.tvName.setText(""+user.userName);
        holder.tvEmail.setText(""+user.userEmail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detail_Activity.class);
                Log.e("mykey",""+user.userId);
                intent.putExtra("userId",user.userId);
                context.startActivity(intent);
              //  showDetails(user);
            }
        });
        if(!user.accepted){
            holder.btnAccept.setVisibility(View.VISIBLE);
        }
        else {
            holder.btnAccept.setVisibility(View.GONE);

        }

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             inviteTrainer(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvEmail,btnAccept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            btnAccept=itemView.findViewById(R.id.btnAccept);
        }
    }
    void inviteTrainer(final TraineeModel user){
        Utills.showDialog(context);

        user.accepted=true;
        trainee.child(user.id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utills.dissmiss();
                    Toast.makeText(context, "Invitation has been Accepted sucessfully", Toast.LENGTH_SHORT).show();

                        notifyDataSetChanged();
                }else {
                    Utills.dissmiss();
                    Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
