package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.rugacon.friendchat.databinding.ActivityAnyadirAmigoBinding;
import com.rugacon.friendchat.databinding.ActivityAnyadirConversacionBinding;

import java.util.ArrayList;

public class AnyadirConversacionActivity extends AppCompatActivity {
    ActivityAnyadirConversacionBinding binding;
    public static ArrayList<String> listaUsuarios;
    Controlador c=ActivityLogin.c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAnyadirConversacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        c.rellenarUsuariosNoConversaciones();

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, listaUsuarios);

        binding.SpinnerAnyadirConversacion.setAdapter(adapter);

        binding.botonAceptarAnyadirConversacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = binding.SpinnerAnyadirConversacion.getSelectedItem().toString();
                c.crearConver(usuario);
                c.rellenarNombresConversaciones();
                c.rellenarIDConversaciones();
                Intent pagina_amigos= new Intent(AnyadirConversacionActivity.this, ConversacionesActivity.class);
                pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_amigos);
            }
        });

        binding.botonCancelarAnyadirConversacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagina_amigos= new Intent(AnyadirConversacionActivity.this, ConversacionesActivity.class);
                pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_amigos);
            }
        });
    }

    }
