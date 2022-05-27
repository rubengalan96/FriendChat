package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;


import com.rugacon.friendchat.databinding.ActivityChatGrupoBinding;

public class ChatGrupoActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ActivityChatGrupoBinding binding;
    static int idchat;
    static String admin;
    static String nombreGrupo;
    Controlador c= ActivityLogin.c;
    final Handler handler= new Handler();
    private final int refresco= 1250;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupo);

        binding=ActivityChatGrupoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager= new LinearLayoutManager(this);
        binding.RecyclerViewChatGrupo.setLayoutManager(layoutManager);

        adapter= new MensajesGrupoAdapter();
        binding.RecyclerViewChatGrupo.setAdapter(adapter);
        binding.TVGrupoNombre.setText(nombreGrupo);

        binding.ETMensajeGrupo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnEnviarMensajeGrupo.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( binding.ETMensajeGrupo.getText().toString().trim().isEmpty()){
                    binding.btnEnviarMensajeGrupo.setEnabled(false);
                }else{
                    binding.btnEnviarMensajeGrupo.setEnabled(true);
                }
            }
        });
        binding.btnEnviarMensajeGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.enviarMensajeChat(binding.ETMensajeGrupo.getText().toString(), idchat);
                c.rellenarMensajesGrupo(idchat);
                adapter= new MensajesGrupoAdapter();
                binding.RecyclerViewChatGrupo.setAdapter(adapter);

                int posicionMensaje= MensajesGrupoAdapter.mensajes.size()-1;
                binding.RecyclerViewChatGrupo.scrollToPosition(posicionMensaje);
                binding.ETMensajeGrupo.setText("");
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c.rellenarMensajesGrupo(idchat);
                adapter=new MensajesGrupoAdapter();
                binding.RecyclerViewChatGrupo.setAdapter(adapter);
                handler.postDelayed(this,refresco);
                int posicionMensaje= MensajesGrupoAdapter.mensajes.size()-1;
                binding.RecyclerViewChatGrupo.scrollToPosition(posicionMensaje);
            }
        },refresco);

        binding.TVGrupoNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            c.rellenarParticipantesGrupo(idchat);
                Intent participantes_grupo= new Intent(v.getContext(), ParticipantesGrupoActivity.class);
                v.getContext().startActivity(participantes_grupo);
            }
        });
        c.rellenarAdminGrupo(idchat);
        if (c.usuarioConectado.equals(admin)){
            binding.btnDejarGrupo.setVisibility(View.INVISIBLE);
        }


        binding.btnDejarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ChatGrupoActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Seguro que quiere salir del grupo?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.borrarParticipante(c.usuarioConectado, idchat);
                        c.rellenarNombreGrupos();
                        c.rellenarIdGrupos();
                        Intent pantalla_grupos= new Intent(v.getContext(), GruposActivity.class);
                        v.getContext().startActivity(pantalla_grupos);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.cancel();
                    }
                });
                dialogo1.show();

            }
        });
    }
}