package com.rugacon.friendchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rugacon.friendchat.databinding.ActivityLoginBinding;
import com.rugacon.friendchat.databinding.ActivitySignupBinding;


public class ActivityLogin extends AppCompatActivity {
    ActivityLoginBinding binding;
Button boton_registro;
Button boton_entrar;
EditText editTextUser, editTextPass;
static Controlador c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Conexion.conectarBD();
        c=new Controlador();

        binding.botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagina_registro= new Intent(ActivityLogin.this, ActivitySignUp.class);
                startActivity(pagina_registro);
            }
        });

        binding.botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario= binding.editTextUsuario.getText().toString();
                String contrasenya= binding.editTextContrasenya.getText().toString();//Cifrar directamente obteniendo el valor del jTextField
                if (binding.editTextUsuario.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "El campo usuario no puede estar vacio",  Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    if (binding.editTextContrasenya.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "El campo contraseña no puede estar vacio",  Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        String passCifrada=c.cifrarContrasenya(contrasenya);
                        if(c.comprobarUsuario(usuario, passCifrada)==true) {
                            //c.rellenarNickAmigos();
                            Intent pagina_principal= new Intent(ActivityLogin.this, HomeActivity.class);
                            pagina_principal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(pagina_principal);
                            Toast toast = Toast.makeText(getApplicationContext(), "Para comenzar a chatear, pulsa sobre el menú superior",  Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Las credenciales no coinciden con los usuarios registrados",  Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
            }
        }


    });



}


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}

