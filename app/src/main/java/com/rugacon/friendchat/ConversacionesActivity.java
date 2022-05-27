package com.rugacon.friendchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rugacon.friendchat.databinding.ActivityConversacionesBinding;

import java.util.ArrayList;

public class ConversacionesActivity extends AppCompatActivity {
    ActivityConversacionesBinding binding;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Controlador c= ActivityLogin.c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityConversacionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager= new LinearLayoutManager(this);
        setSupportActionBar(binding.toolbarConversaciones.getRoot());
        binding.RecyclerViewConversaciones.setLayoutManager(layoutManager);

        adapter= new ConversacionesAdapter();
        binding.RecyclerViewConversaciones.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_conversaciones, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();

        if (id==R.id.conversaciones_to_grupos){
            c.rellenarNombreGrupos();
            c.rellenarIdGrupos();
            Intent pantalla_grupos= new Intent(ConversacionesActivity.this, GruposActivity.class);
            pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_grupos);
        }else if(id ==R.id.conversaciones_to_amistades){
            c.rellenarNickAmigos();
            Intent pantalla_conversaciones= new Intent(ConversacionesActivity.this, AmistadesActivity.class);
            pantalla_conversaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_conversaciones);
        }else if(id == R.id.anyadir_conversacion){
            c.rellenarUsuariosNoConversaciones();
            if (AnyadirConversacionActivity.listaUsuarios.isEmpty()){
                Toast.makeText(this, "Ya tienes todas las conversaciones posibles", Toast.LENGTH_SHORT).show();
            }else{
                Intent pantalla_anyadir_conversacion= new Intent(ConversacionesActivity.this, AnyadirConversacionActivity.class);
                startActivity(pantalla_anyadir_conversacion);
            }

        }else if(id == R.id.conversacioens_to_home){
            Intent pantalla_home= new Intent(ConversacionesActivity.this, HomeActivity.class);
            pantalla_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_home);
        }
        return true;
    }


}