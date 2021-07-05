package com.example.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.project.model.TraineeModel;
import com.example.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.MyViewHolder> {
    public static final String TRAINEE = "trainee";
    Context context;
    List<User> list;
boolean isUser=false;
    public TrainerAdapter(Context context, List<User> list, boolean b) {
        this.context = context;
        this.list = list;
        isUser=b;
    }public TrainerAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }
    DatabaseReference trainee = FirebaseDatabase.getInstance().getReference().child(TRAINEE);

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.triner_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final User user = list.get(position);
        holder.tvName.setText(""+user.name);
        holder.tvEmail.setText(""+user.email);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvEmail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvEmail=itemView.findViewById(R.id.tvEmail);
        }
    }
    void showDetails(final User user){
        View view=LayoutInflater.from(context).inflate(R.layout.trainer_deteil_layout,null,false);
        TextView tvName=view.findViewById(R.id.tvName);
        TextView tvEmail=view.findViewById(R.id.tvEmail);
        TextView tvPhoneno =view.findViewById(R.id.tvphnno);
        TextView tvAge=view.findViewById(R.id.tvage);
        TextView tvExp=view.findViewById(R.id.tvexperience);
        TextView tvDesc=view.findViewById(R.id.tvdiscription);
        Button btnInvite=view.findViewById(R.id.btnInvite);
        Button btnCancel=view.findViewById(R.id.btnCancel);
        LinearLayout llUser=view.findViewById(R.id.llUser);
        LinearLayout llTrainer=view.findViewById(R.id.llTrainer);
        tvName.setText(""+user.name);
        tvEmail.setText(""+user.email);
        tvPhoneno.setText(""+user.phone);
        tvAge.setText(""+user.age);
        tvExp.setText(""+user.exp);
        tvDesc.setText(""+user.desc);
        if(isUser){
            llUser.setVisibility(View.VISIBLE);
            llTrainer.setVisibility(View.GONE);
        }else {
            llUser.setVisibility(View.GONE);
            llTrainer.setVisibility(View.VISIBLE);
        }
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteTrainer(user,alertDialog);
            }
        });
alertDialog.show();

    }
    void inviteTrainer(final User user, final AlertDialog dialog){
        Utills.showDialog(context);
        TraineeModel model=new TraineeModel();
        model.id=trainee.push().getKey();
        model.userEmail= BaseHelper.currentUser.email;
        model.userName= BaseHelper.currentUser.name;
        model.trainerEmail= user.email;
        model.trainerName= user.name;
        model.trainerId=user.id;
        model.userId=BaseHelper.currentUser.id;
        model.accepted=false;
        trainee.child(model.id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utills.dissmiss();
                    dialog.dismiss();
                    Toast.makeText(context, "Invitation has been sent sucessfully", Toast.LENGTH_SHORT).show();
                    if(list.contains(user)){
                        list.remove(user);
                        notifyDataSetChanged();
                    }
                }else {
                    Utills.dissmiss();
                    Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
