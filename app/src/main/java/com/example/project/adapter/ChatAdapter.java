package com.example.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.ChatModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    List<ChatModel> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatModel trainer = list.get(position);



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
}
