package com.rugacon.friendchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rugacon.friendchat.databinding.ActivityGruposBinding;
import com.rugacon.friendchat.databinding.ActivityParticipantesGrupoBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParticipantesGrupoActivity extends AppCompatActivity {
    ActivityParticipantesGrupoBinding binding;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Controlador c= ActivityLogin.c;
    public static ArrayList<String> participantes;
    public static ArrayList<String> noParticipantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityParticipantesGrupoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutManager=new LinearLayoutManager(this);
        binding.spinnerParticipantes.setVisibility(View.INVISIBLE);

        binding.RecyclerViewParticipantes.setLayoutManager(layoutManager);
        adapter= new ParticipantesGrupoAdapter();
        binding.RecyclerViewParticipantes.setAdapter(adapter);

        binding.TVParticipantesGrupo.setText(ChatGrupoActivity.nombreGrupo);

        if (!ParticipantesGrupoAdapter.admin.equals(c.usuarioConectado)){

            binding.bntBorrarGrupo.setVisibility(View.INVISIBLE);
            binding.btnAnyadirPart.setVisibility(View.INVISIBLE);
            binding.btnEliminarPart.setVisibility(View.INVISIBLE);
            binding.btnAceptarParti.setVisibility(View.INVISIBLE);
        }
        binding.btnAceptarParti.setVisibility(View.INVISIBLE);

        binding.bntBorrarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ParticipantesGrupoActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Seguro que quiere borrar el grupo?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        c.borrarGrupo(binding.TVParticipantesGrupo.getText().toString());
                        c.rellenarNombreGrupos();
                        c.rellenarIdGrupos();
                        Intent pantalla_grupos= new Intent(ParticipantesGrupoActivity.this, GruposActivity.class);
                        pantalla_grupos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(pantalla_grupos);
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

        binding.btnAnyadirPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.rellenarNoParticipantesSpinner(ChatGrupoActivity.idchat);
                if (noParticipantes.isEmpty()){
                    Toast.makeText(ParticipantesGrupoActivity.this, "Todos los usuarios posibles ya pertenecen al grupo", Toast.LENGTH_SHORT).show();
                }else{
                    if (binding.spinnerParticipantes.getVisibility()==View.INVISIBLE){
                        binding.btnAceptarParti.setText("Añadir");
                        binding.spinnerParticipantes.setVisibility(View.VISIBLE);
                        binding.btnAceptarParti.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, noParticipantes);
                        binding.spinnerParticipantes.setAdapter(adapter);
                    }else{
                        binding.spinnerParticipantes.setVisibility(View.INVISIBLE);
                        binding.btnAceptarParti.setVisibility(View.INVISIBLE);
                    }

                }



            }
        });

        binding.btnEliminarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.rellenarParticipantesSpinner(ChatGrupoActivity.idchat);
                if (participantes.isEmpty()){
                    Toast.makeText(ParticipantesGrupoActivity.this, "No hay participantes posibles para borrar", Toast.LENGTH_SHORT).show();
                }else{
                    if (binding.spinnerParticipantes.getVisibility()==View.INVISIBLE){
                        binding.btnAceptarParti.setText("Eliminar");
                        binding.spinnerParticipantes.setVisibility(View.VISIBLE);
                        binding.btnAceptarParti.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, participantes);
                        binding.spinnerParticipantes.setAdapter(adapter);
                    }else{
                        binding.spinnerParticipantes.setVisibility(View.INVISIBLE);
                        binding.btnAceptarParti.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        binding.btnAceptarParti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.btnAceptarParti.getText().equals("Añadir")){
                    c.anyadirParticipante(binding.spinnerParticipantes.getSelectedItem().toString(), ChatGrupoActivity.idchat);
                    c.rellenarParticipantesGrupo(ChatGrupoActivity.idchat);
                    binding.RecyclerViewParticipantes.setLayoutManager(layoutManager);
                    adapter= new ParticipantesGrupoAdapter();
                    binding.RecyclerViewParticipantes.setAdapter(adapter);

                    binding.spinnerParticipantes.setVisibility(View.INVISIBLE);
                    binding.btnAceptarParti.setVisibility(View.INVISIBLE);
                }else if (binding.btnAceptarParti.getText().equals("Eliminar")){
                    c.borrarParticipante(binding.spinnerParticipantes.getSelectedItem().toString(), ChatGrupoActivity.idchat);
                    c.rellenarParticipantesGrupo(ChatGrupoActivity.idchat);
                    binding.RecyclerViewParticipantes.setLayoutManager(layoutManager);
                    adapter= new ParticipantesGrupoAdapter();
                    binding.RecyclerViewParticipantes.setAdapter(adapter);

                    binding.spinnerParticipantes.setVisibility(View.INVISIBLE);
                    binding.btnAceptarParti.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
}