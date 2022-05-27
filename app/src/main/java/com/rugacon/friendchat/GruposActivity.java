package com.rugacon.friendchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rugacon.friendchat.databinding.ActivityAmistadesBinding;
import com.rugacon.friendchat.databinding.ActivityGruposBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GruposActivity extends AppCompatActivity {
    ActivityGruposBinding binding;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    Controlador c= ActivityLogin.c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGruposBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager=new LinearLayoutManager(this);
        setSupportActionBar(binding.toolbarGrupos.getRoot());
        binding.RecyclerViewGrupos.setLayoutManager(layoutManager);

        adapter= new GruposAdapter();
        binding.RecyclerViewGrupos.setAdapter(adapter);


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_grupos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();

        if (id==R.id.grupos_to_amistades){
            c.rellenarNickAmigos();
            Intent pantalla_grupos= new Intent(this, AmistadesActivity.class);
            pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_grupos);
        }else if(id ==R.id.grupos_to_conversaciones){
            c.rellenarNombresConversaciones();
            c.rellenarIDConversaciones();
            Intent pantalla_conversaciones= new Intent(this, ConversacionesActivity.class);
            pantalla_conversaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_conversaciones);
        }else if(id == R.id.anyadir_grupo){
            Intent pantalla_anyadir_grupo= new Intent(this, AnyadirGrupoActivity.class);
            startActivity(pantalla_anyadir_grupo);
        }else if(id==R.id.grupos_to_home){
            Intent pantalla_home= new Intent(GruposActivity.this, HomeActivity.class);
            pantalla_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_home);
        }

        return true;
    }


}