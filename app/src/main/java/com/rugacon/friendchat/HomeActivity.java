package com.rugacon.friendchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rugacon.friendchat.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding binding;
Controlador c= ActivityLogin.c;
private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarHome.getRoot());
        url = "https://drive.google.com/file/d/1cRyJIpAG6judWeVzXNMVgNQWzC5rlcKf/view?usp=sharing";


        binding.TVNombreHome.setText(c.getNombre());

        binding.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.usuarioConectado=null;
                Toast.makeText(HomeActivity.this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
                Intent pagina_login= new Intent(HomeActivity.this, ActivityLogin.class);
                pagina_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pagina_login);
            }
        });

        binding.btnBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(HomeActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Seguro que eliminar esta cuenta?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.borrarCuenta();
                        Conexion.cerrarConexionBD();
                        c.usuarioConectado=null;
                        Toast.makeText(HomeActivity.this, "Cuenta borrada correctamente", Toast.LENGTH_SHORT).show();
                        Intent pagina_login= new Intent(HomeActivity.this, ActivityLogin.class);
                        pagina_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(pagina_login);
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

        binding.btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();

        if (id==R.id.home_to_grupos){
            c.rellenarNombreGrupos();
            c.rellenarIdGrupos();
            Intent pantalla_grupos= new Intent(HomeActivity.this, GruposActivity.class);
            pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_grupos);
        }else if(id ==R.id.home_to_conversaciones){
            c.rellenarNombresConversaciones();
            c.rellenarIDConversaciones();
            Intent pantalla_conversaciones= new Intent(HomeActivity.this, ConversacionesActivity.class);
            pantalla_conversaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_conversaciones);
        }else if(id ==R.id.home_to_amistades){
            c.rellenarNickAmigos();
            Intent pantalla_conversaciones= new Intent(HomeActivity.this, AmistadesActivity.class);
            pantalla_conversaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pantalla_conversaciones);
            }
        return true;
    }
}