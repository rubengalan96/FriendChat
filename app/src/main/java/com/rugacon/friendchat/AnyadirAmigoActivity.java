package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;


import com.rugacon.friendchat.databinding.ActivityAnyadirAmigoBinding;

import java.util.ArrayList;

public class AnyadirAmigoActivity extends AppCompatActivity {
    ActivityAnyadirAmigoBinding binding;
    public static ArrayList<String> listaUsuarios;
    Controlador c=ActivityLogin.c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAnyadirAmigoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, listaUsuarios);

        binding.SpinnerAnyadirAmigos.setAdapter(adapter);


        binding.botonAceptarAnyadirAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amigo = binding.SpinnerAnyadirAmigos.getSelectedItem().toString();
                c.anyadirAmigo(amigo);
                c.rellenarNickAmigos();
                Intent pagina_amigos= new Intent(AnyadirAmigoActivity.this, AmistadesActivity.class);
                pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_amigos);
            }
        });

        binding.botonCancelarAnyadirAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagina_amigos= new Intent(AnyadirAmigoActivity.this, AmistadesActivity.class);
                pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_amigos);
            }
        });
    }


}