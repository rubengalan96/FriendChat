package com.rugacon.friendchat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rugacon.friendchat.databinding.ActivityAmistadesBinding;
import com.rugacon.friendchat.databinding.ActivitySignupBinding;


public class ActivitySignUp extends AppCompatActivity {
    ActivitySignupBinding binding;
    Controlador c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextNuevoUsuario.setText("");
        binding.editTextNuevaContrasenya.setText("");
        binding.editTextNuevoNombre.setText("");

        c = new Controlador();

        binding.botonCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextNuevoNombre.getText().toString().trim().isEmpty() || binding.editTextNuevoUsuario.getText().toString().trim().isEmpty() || binding.editTextNuevaContrasenya.getText().toString().trim().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "No puede haber ningún campo vacío", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    String contrasenya = c.cifrarContrasenya(binding.editTextNuevaContrasenya.getText().toString());
                    c.crearUsuario(binding.editTextNuevoUsuario.getText().toString(), binding.editTextNuevoNombre.getText().toString(), contrasenya, getApplicationContext());
                    Intent pagina_login= new Intent(ActivitySignUp.this, ActivityLogin.class);
                    pagina_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(pagina_login);
                }

            }
        });


    }
}