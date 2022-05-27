package com.rugacon.friendchat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConversacionesAdapter extends RecyclerView.Adapter<ConversacionesAdapter.ViewHolder> {
    Controlador c= ActivityLogin.c;
    public static ArrayList<String> nombreUsuarios;
    public static ArrayList<Integer> idchats;


    @Override
    public ConversacionesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.conversacion_element,null);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        viewHolder.TVnombreusuario.setText(nombreUsuarios.get(position));
        viewHolder.TVId_Conver.setText(String.valueOf(idchats.get(position)));

    }

    @Override
    public int getItemCount() {
        return nombreUsuarios.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVnombreusuario, TVId_Conver;

        public ViewHolder(View itemView){
            super(itemView);

            TVnombreusuario= itemView.findViewById(R.id.TVnombreUsuario);
            TVId_Conver= itemView.findViewById(R.id.TVIdConver);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //c.rellenarNickAmigos();
                    int position=getAdapterPosition();
                    c.rellenarMensajes(idchats.get(position));
                    ChatConversacionActivity.idchat=idchats.get(position);
                    ChatConversacionActivity.otroUsuario=nombreUsuarios.get(position);
                    Intent chatConver= new Intent(v.getContext(), ChatConversacionActivity.class);
                    v.getContext().startActivity(chatConver);
                }
            });

        }




    }
}
