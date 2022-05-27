package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.rugacon.friendchat.databinding.ActivityEliminarAmigoBinding;


import java.util.ArrayList;

public class EliminarAmigoActivity extends AppCompatActivity {
    ActivityEliminarAmigoBinding binding;
public static ArrayList<String> listaAmigos;
Controlador c=ActivityLogin.c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEliminarAmigoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        c.rellenarNickAmigos();
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item, listaAmigos);
        binding.SpinnerEliminarAmigos.setAdapter(adapter);

        binding.botonCancelarEliminarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagina_amigos= new Intent(EliminarAmigoActivity.this, AmistadesActivity.class);
                pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_amigos);
            }
        });

        binding.botonAceptarEliminarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EliminarAmigoActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Seguro que quiere eliminar esta amistad?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String amigo = binding.SpinnerEliminarAmigos.getSelectedItem().toString();
                        c.borrarAmigo(amigo);
                        c.rellenarNickAmigos();
                        Intent pagina_amigos= new Intent(EliminarAmigoActivity.this, AmistadesActivity.class);
                        pagina_amigos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(pagina_amigos);
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