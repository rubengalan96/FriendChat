package com.rugacon.friendchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AmistadesAdapter extends RecyclerView.Adapter<AmistadesAdapter.ViewHolder> {
    Controlador c= ActivityLogin.c;
    public static ArrayList<String> nicknames;




    @Override
    public AmistadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.amistad_element, null);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
       viewHolder.TVNicknameAmigo.setText(nicknames.get(position));

    }

    @Override
    public int getItemCount() {
        return nicknames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVNicknameAmigo;

        public ViewHolder(View itemView){
            super(itemView);
            TVNicknameAmigo=itemView.findViewById(R.id.TVNicknameAmigo);
        }

    }
}
