package com.rugacon.friendchat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GruposAdapter extends RecyclerView.Adapter<GruposAdapter.ViewHolder> {
    Controlador c = ActivityLogin.c;
    public static ArrayList<String> grupos;
    public static ArrayList<Integer> id_grupos;





    @Override
    public GruposAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grupo_element, null);
        return new GruposAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        viewHolder.TVnombregrupo.setText(grupos.get(position));
        viewHolder.TVIdGrupo.setText(String.valueOf(id_grupos.get(position)));
        
    }

    @Override
    public int getItemCount() {
        return grupos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVnombregrupo, TVIdGrupo;

        public ViewHolder(View itemView){
            super(itemView);
            TVnombregrupo= itemView.findViewById(R.id.TVnombreGrupo);
            TVIdGrupo=itemView.findViewById(R.id.TVIdGrupo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    c.rellenarMensajesGrupo(id_grupos.get(position));
                    ChatGrupoActivity.idchat=id_grupos.get(position);
                    ChatGrupoActivity.nombreGrupo=grupos.get(position);
                    Intent chatConver= new Intent(v.getContext(), ChatGrupoActivity.class);
                    v.getContext().startActivity(chatConver);
                }
            });

        }




    }
}
