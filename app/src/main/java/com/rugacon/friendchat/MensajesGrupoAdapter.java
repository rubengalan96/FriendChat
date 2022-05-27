package com.rugacon.friendchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MensajesGrupoAdapter extends RecyclerView.Adapter<MensajesGrupoAdapter.ViewHolder> {

    static ArrayList<String> usuarios;
    static ArrayList<String> mensajes;
    private LayoutInflater mInflater;
    private Context context;
    Controlador c=ActivityLogin.c;





    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mensajes_element, viewGroup, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        viewHolder.TVmensaje.setText(mensajes.get(position));
        viewHolder.TVusumensaje.setText(usuarios.get(position));

        if (viewHolder.TVusumensaje.getText().toString().equals(c.usuarioConectado)){
            viewHolder.TVusumensaje.setText("Yo");
            viewHolder.TVusumensaje.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            viewHolder.TVmensaje.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }else{
            viewHolder.TVusumensaje.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            viewHolder.TVmensaje.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVusumensaje, TVmensaje;

        public ViewHolder(View itemView){
            super(itemView);
            TVusumensaje= itemView.findViewById(R.id.TVNicknameAmigo);
            TVmensaje= itemView.findViewById(R.id.TVNombreAmigo);

        }

    }
}