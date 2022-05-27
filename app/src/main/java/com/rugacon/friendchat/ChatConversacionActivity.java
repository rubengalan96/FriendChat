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

import com.rugacon.friendchat.databinding.ActivityChatConversacionBinding;

public class ChatConversacionActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ActivityChatConversacionBinding binding;
    static int idchat;
    static String otroUsuario;
    Controlador c= ActivityLogin.c;
    final Handler handler= new Handler();
    private final int refresco= 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityChatConversacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager= new LinearLayoutManager(this);
        binding.RecyclerViewChatConversacion.setLayoutManager(layoutManager);

        adapter= new MensajesAdapter();
        binding.RecyclerViewChatConversacion.setAdapter(adapter);
        binding.TVOtroUsuario.setText(otroUsuario);

        binding.ETmensajeConver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnEnviarMensajeConversacion.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( binding.ETmensajeConver.getText().toString().trim().isEmpty()){
                    binding.btnEnviarMensajeConversacion.setEnabled(false);
                }else{
                    binding.btnEnviarMensajeConversacion.setEnabled(true);
                }
            }
        });

        binding.btnEnviarMensajeConversacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.enviarMensajeChat(binding.ETmensajeConver.getText().toString(),idchat);
                c.rellenarMensajes(idchat);
                adapter= new MensajesAdapter();
                binding.RecyclerViewChatConversacion.setAdapter(adapter);

                int posicionMensaje= MensajesAdapter.mensajes.size()-1;
                binding.RecyclerViewChatConversacion.scrollToPosition(posicionMensaje);
                binding.ETmensajeConver.setText("");

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c.rellenarMensajes(idchat);
                adapter= new MensajesAdapter();
                binding.RecyclerViewChatConversacion.setAdapter(adapter);
                handler.postDelayed(this,refresco);
                int posicionMensaje= MensajesAdapter.mensajes.size()-1;
                binding.RecyclerViewChatConversacion.scrollToPosition(posicionMensaje);
            }
        },refresco);

    binding.btnBorrarConver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ChatConversacionActivity.this);
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Seguro que quiere eliminar esta conversación. También será eliminada para el otro usuario?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    c.borrarConversacion(idchat);
                    c.rellenarNombresConversaciones();
                    c.rellenarIDConversaciones();
                    Intent pagina_conver= new Intent(ChatConversacionActivity.this, ConversacionesActivity.class);
                    pagina_conver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(pagina_conver);
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