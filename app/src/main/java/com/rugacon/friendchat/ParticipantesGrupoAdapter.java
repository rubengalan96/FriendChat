package com.rugacon.friendchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rugacon.friendchat.R.color;

import java.util.ArrayList;

public class ParticipantesGrupoAdapter extends RecyclerView.Adapter<ParticipantesGrupoAdapter.ViewHolder> {
    Controlador c = ActivityLogin.c;
    public static ArrayList<String> participantes;
    public static ArrayList<String> noParticipantes;
    public static String admin;

    @Override
    public ParticipantesGrupoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participantes_element, null);
        return new ParticipantesGrupoAdapter.ViewHolder(v);
    }



    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (participantes.get(position).equals(c.usuarioConectado)){
            viewHolder.TVParticipante.setText("Yo");
        }else{
            viewHolder.TVParticipante.setText(participantes.get(position));

        }
        if (participantes.get(position).equals(admin)){
            viewHolder.TVAdmin.setTextColor(Color.rgb(255, 0, 0));
            viewHolder.TVAdmin.setText("Administrador");
        }


    }

    @Override
    public int getItemCount() {
        return participantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVParticipante, TVAdmin;

        public ViewHolder(View itemView) {
            super(itemView);
            TVParticipante = itemView.findViewById(R.id.TVParticipante);
            TVAdmin = itemView.findViewById(R.id.TVAdmin);


        }
    }
}
