package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rugacon.friendchat.databinding.ActivityAnyadirGrupoBinding;
import com.rugacon.friendchat.databinding.ActivityEliminarAmigoBinding;

public class AnyadirGrupoActivity extends AppCompatActivity {
ActivityAnyadirGrupoBinding binding;
Controlador c= ActivityLogin.c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= ActivityAnyadirGrupoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAceptarNuevoGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.ETNomGrupoNuevo.getText().toString().trim().isEmpty()){
                    Toast.makeText(AnyadirGrupoActivity.this, "No puedes dejar el nombre en blanco, prueba de nuevo", Toast.LENGTH_SHORT).show();
                }else{
                    String nuevoGrupo= binding.ETNomGrupoNuevo.getText().toString();
                    c.crearGrupo(nuevoGrupo);
                    c.rellenarNombreGrupos();
                    c.rellenarIdGrupos();
                    Intent pantalla_grupos= new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                    pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(pantalla_grupos);
                }
            }
        });

        binding.btnCancelarNuevoGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantalla_grupos= new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pantalla_grupos);
            }
        });
    }
}