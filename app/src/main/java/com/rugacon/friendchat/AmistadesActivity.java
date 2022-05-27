package com.rugacon.friendchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rugacon.friendchat.databinding.ActivityAmistadesBinding;

public class AmistadesActivity extends AppCompatActivity {
    ActivityAmistadesBinding binding;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Controlador c=ActivityLogin.c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAmistadesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager=new LinearLayoutManager(this);
        setSupportActionBar(binding.toolbarAmigos.getRoot());
        binding.RecyclerViewAmistades.setLayoutManager(layoutManager);

        adapter= new AmistadesAdapter();
        binding.RecyclerViewAmistades.setAdapter(adapter);

    }

    @Override
    public void onBackPressed(){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_amistades, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();

        if (id==R.id.amistades_to_grupos){
            c.rellenarNombreGrupos();
            c.rellenarIdGrupos();
            Intent pantalla_grupos= new Intent(AmistadesActivity.this, GruposActivity.class);
            pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_grupos);
        }else if(id ==R.id.amistades_to_conversaciones){
            c.rellenarNombresConversaciones();
            c.rellenarIDConversaciones();
            Intent pantalla_conversaciones= new Intent(AmistadesActivity.this, ConversacionesActivity.class);
            pantalla_conversaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_conversaciones);
        }else if(id ==R.id.eliminar_amigos){
            c.rellenarNickAmigos();
            if (EliminarAmigoActivity.listaAmigos.isEmpty()){
                Toast.makeText(this, "Todavía no tienes usuarios agregados como amigos", Toast.LENGTH_SHORT).show();
            }else{
                Intent pantalla_eliminar_amigos= new Intent(AmistadesActivity.this, EliminarAmigoActivity.class);
                startActivity(pantalla_eliminar_amigos);
            }

        }else if(id == R.id.anyadir_amigos){
            c.rellenarNickNoAmigos();
            if (AnyadirAmigoActivity.listaUsuarios.isEmpty()){
                Toast.makeText(this, "Ya tienes a todos los usuarios agregados como amigos", Toast.LENGTH_SHORT).show();
            }else{
                Intent pantalla_anyadir_amigos= new Intent(AmistadesActivity.this, AnyadirAmigoActivity.class);
                startActivity(pantalla_anyadir_amigos);
            }

        }else if(id == R.id.amistades_to_home){
            Intent pantalla_home= new Intent(AmistadesActivity.this, HomeActivity.class);
            pantalla_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_home);
        }

        return true;
    }


}