package com.rugacon.friendchat;

import static com.rugacon.friendchat.Conexion.cn;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controlador {
    public static String usuarioConectado;

    public String getNombre(){
        String nombre="";
            try {

                PreparedStatement pst = cn.prepareStatement("SELECT nombre_usu FROM usuario WHERE nickname=?");

                ResultSet rs = null;
                pst.setString(1, usuarioConectado);
                rs = pst.executeQuery();

                if (rs.next()) {
                    nombre = rs.getString("nombre_usu");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return nombre;
        }


    public void crearUsuario(String nick, String nombre, String password, Context context) {

        boolean b = true;
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT f_crear_usuario(?,?,?)");
            //PreparedStatement pst = cn.prepareStatement("INSERT INTO favoritos_producto VALUES (2, 1)");
            ResultSet rs = null;
            pst.setString(1, nick);
            pst.setString(2, nombre);
            pst.setString(3, password);
            pst.executeQuery();

        } catch (SQLException e) {
            b = false;
            Toast toast = Toast.makeText(context, "Ya existe un usuario con ese nickname", Toast.LENGTH_SHORT);
            toast.show();
            //JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nickname");
        }
        if (b == true) {
            Toast toast = Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_SHORT);
            toast.show();


            //JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
        }
    }

    public String cifrarContrasenya(String contrasenya) {
        String encryptedpassword = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(contrasenya.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /*
             * The bytes array has bytes in decimal form. Converting it into hexadecimal
             * format.
             */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;

    }

    public boolean comprobarUsuario(String use, String pas) {

        try {

            ResultSet rs;
            PreparedStatement pst = cn.prepareStatement("SELECT nickname,password,nombre_usu FROM usuario WHERE nickname=?");
            pst.setString(1, use);
            rs = pst.executeQuery();

            if (rs.next()) {// Si existe el usuario...
                String us = rs.getString("nickname");
                String pa = rs.getString("password");
                String nombreUsu = rs.getString("nombre_usu");

                if (pas.equals(pa)) {// Si la contrasenya coincide con la de la tabla...
                    usuarioConectado = us;

                    return true;

                } else {

                    return false;
                }
            } else {

                return false;
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }

    public int getIdByGroupName(String grupo) {
        int idchat = 0;
        try {

            PreparedStatement pst = cn.prepareStatement("SELECT id_chat FROM grupo WHERE nombre_grupo=?");

            ResultSet rs = null;
            pst.setString(1, grupo);
            rs = pst.executeQuery();

            if (rs.next()) {
                idchat = rs.getInt("id_chat");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idchat;
    }


    public void rellenarNickAmigos() {
        AmistadesAdapter.nicknames = new ArrayList<>();
        EliminarAmigoActivity.listaAmigos = new ArrayList<>();
        try {

            PreparedStatement pst = cn.prepareStatement("SELECT nickname2 FROM amistad WHERE nickname1=?");
            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            rs = pst.executeQuery();

            while (rs.next()) {
                AmistadesAdapter.nicknames.add(rs.getString(1));
                EliminarAmigoActivity.listaAmigos.add(rs.getString(1));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void rellenarNickNoAmigos() {
        AnyadirAmigoActivity.listaUsuarios = new ArrayList<>();

        try {

            PreparedStatement pst = cn.prepareStatement("SELECT nickname FROM usuario WHERE nickname NOT IN(SELECT nickname2 FROM amistad WHERE nickname1=?) and nickname<> ?");
            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            pst.setString(2, usuarioConectado);
            rs = pst.executeQuery();


            while (rs.next()) {
                AnyadirAmigoActivity.listaUsuarios.add(rs.getString(1));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }


    public void rellenarNombresConversaciones() {

        ConversacionesAdapter.nombreUsuarios = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT nickname2 FROM conversacion WHERE nickname1=?");
            pst.setString(1, usuarioConectado);

            rs = pst.executeQuery();


            while (rs.next()) {
                ConversacionesAdapter.nombreUsuarios.add(rs.getString(1));
            }

            rs = null;
            PreparedStatement pst2 = cn.prepareStatement("SELECT nickname1 FROM conversacion WHERE nickname2=?");
            pst2.setString(1, usuarioConectado);

            rs = pst2.executeQuery();


            while (rs.next()) {
                ConversacionesAdapter.nombreUsuarios.add(rs.getString(1));
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void rellenarIDConversaciones() {
        ConversacionesAdapter.idchats = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT id_chat FROM conversacion WHERE nickname1=?");
            pst.setString(1, usuarioConectado);

            rs = pst.executeQuery();


            while (rs.next()) {
                ConversacionesAdapter.idchats.add(Integer.parseInt(rs.getString(1)));
            }

            rs = null;
            PreparedStatement pst2 = cn.prepareStatement("SELECT id_chat FROM conversacion WHERE nickname2=?");
            pst2.setString(1, usuarioConectado);

            rs = pst2.executeQuery();


            while (rs.next()) {
                ConversacionesAdapter.idchats.add(Integer.parseInt(rs.getString(1)));
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }


    public void rellenarNombreGrupos() {
        GruposAdapter.grupos = new ArrayList<String>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT nombre_grupo FROM grupo WHERE id_chat IN(SELECT id_chat FROM participa WHERE nickname=?)");
            pst.setString(1, usuarioConectado);
            rs = pst.executeQuery();


            while (rs.next()) {
                GruposAdapter.grupos.add(rs.getString(1));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void rellenarIdGrupos() {
        GruposAdapter.id_grupos = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT id_chat FROM grupo WHERE id_chat IN(SELECT id_chat FROM participa WHERE nickname=?)");
            pst.setString(1, usuarioConectado);
            rs = pst.executeQuery();


            while (rs.next()) {
                GruposAdapter.id_grupos.add(Integer.parseInt(rs.getString(1)));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void borrarAmigo(String usuarioBorrar) {
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT f_borrar_amigo(?,?)");
            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            pst.setString(2, usuarioBorrar);
            rs = pst.executeQuery();


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


    }

    public void anyadirAmigo(String amigonuevo) {
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT f_anyadir_amigos(?,?)");

            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            pst.setString(2, amigonuevo);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<String> rellenarUsuariosNoAmigos() {
        ArrayList<String> nombres = new ArrayList<>();
        try {

            PreparedStatement pst = cn.prepareStatement("SELECT nombre_usu FROM usuario WHERE nickname NOT IN(SELECT nickname2 FROM amistad WHERE nickname1=?)");
            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            rs = pst.executeQuery();


            while (rs.next()) {
                nombres.add(rs.getString(1));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return nombres;
    }

    public void rellenarMensajes(int id) {
        MensajesAdapter.mensajes = new ArrayList<>();
        MensajesAdapter.usuarios = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT nickname, texto FROM mensaje WHERE id_chat=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();


            while (rs.next()) {
                MensajesAdapter.usuarios.add(rs.getString(1));
                MensajesAdapter.mensajes.add(rs.getString(2));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void rellenarMensajesGrupo(int id) {
        MensajesGrupoAdapter.mensajes = new ArrayList<>();
        MensajesGrupoAdapter.usuarios = new ArrayList<>();
        try {
            ResultSet rs = null;
            PreparedStatement pst = cn.prepareStatement("SELECT nickname, texto FROM mensaje WHERE id_chat=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();


            while (rs.next()) {
                MensajesGrupoAdapter.usuarios.add(rs.getString(1));
                MensajesGrupoAdapter.mensajes.add(rs.getString(2));
            }


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void enviarMensajeChat(String texto, int idchat) {
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT f_enviar_mensaje(?,?,?)");

            ResultSet rs = null;
            pst.setString(1, texto);
            pst.setString(2, usuarioConectado);
            pst.setInt(3, idchat);
            rs = pst.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void rellenarAdminGrupo(int idchat){
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT nickname FROM participa WHERE id_chat=? and es_admin=true");
            ResultSet rs = null;
            pst.setInt(1, idchat);
            rs = pst.executeQuery();

            if (rs.next()) {
                ChatGrupoActivity.admin=rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rellenarParticipantesGrupo(int idchat) {
        ParticipantesGrupoAdapter.participantes = new ArrayList<>();
        try {

            PreparedStatement pst = cn.prepareStatement("SELECT nickname, es_admin FROM participa WHERE id_chat=?");
            ResultSet rs = null;
            pst.setInt(1, idchat);
            rs = pst.executeQuery();

            while (rs.next()) {
                ParticipantesGrupoAdapter.participantes.add(rs.getString("nickname"));
                boolean admin = rs.getBoolean("es_admin");
                if (admin == true) {
                    ParticipantesGrupoAdapter.admin = rs.getString("nickname");
                }else{

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void rellenarNoParticipantesSpinner(int idchat) {
        ParticipantesGrupoActivity.noParticipantes = new ArrayList<>();
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT nickname FROM usuario WHERE nickname NOT IN(SELECT nickname FROM participa WHERE id_chat=?)");
            ResultSet rs = null;
            pst.setInt(1, idchat);
            rs = pst.executeQuery();

            while (rs.next()) {
                ParticipantesGrupoActivity.noParticipantes.add(rs.getString("nickname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void anyadirParticipante(String participanteNuevo, int numChat){

        try {
            PreparedStatement pst = cn.prepareStatement("SELECT f_anyadir_usuario_grupo(?,?)");
            ResultSet rs=null;
            pst.setString(1, participanteNuevo);
            pst.setInt(2, numChat);
            rs=pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void rellenarParticipantesSpinner(int idchat) {
        ParticipantesGrupoActivity.participantes = new ArrayList<>();
        try {
            PreparedStatement pst = cn.prepareStatement("SELECT nickname FROM participa WHERE id_chat=? and nickname !=?");
            ResultSet rs = null;
            pst.setInt(1, idchat);
            pst.setString(2,usuarioConectado);
            rs = pst.executeQuery();

            while (rs.next()) {
                ParticipantesGrupoActivity.participantes.add(rs.getString("nickname"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void borrarParticipante(String participanteEliminado, int numChat) {
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_eliminar_usuario_grupo(?,?)");

            ResultSet rs=null;
            pst.setString(1, participanteEliminado);
            pst.setInt(2, numChat);
            rs=pst.executeQuery();


        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void borrarGrupo(String nombre) {
        int id=getIdByGroupName(nombre);
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_eliminar_chat(?)");
            ResultSet rs = null;
            pst.setInt(1, id);
            rs=pst.executeQuery();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void crearGrupo(String nombre_grupo) {
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_crear_grupo(?,?)");
            ResultSet rs = null;
            pst.setString(1, nombre_grupo);
            pst.setString(2, usuarioConectado);
            rs=pst.executeQuery();


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rellenarUsuariosNoConversaciones() {
        //Otra Opcion de Select: SELECT  nickname2 FROM amistad WHERE nickname1=? and nickname2 NOT IN (SELECT nickname1 FROM conversacion WHERE nickname2=?) and nickname2 NOT IN(SELECT nickname2 FROM conversacion WHERE nickname1=?)
        AnyadirConversacionActivity.listaUsuarios=new ArrayList<>();
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT nickname FROM usuario WHERE nickname IN(SELECT nickname2 FROM amistad WHERE nickname1=?) and nickname NOT IN(SELECT nickname2 FROM conversacion WHERE nickname1=?)and nickname NOT IN(SELECT nickname1 FROM conversacion WHERE nickname2=?)");

            ResultSet rs=null;
            pst.setString(1, usuarioConectado);
            pst.setString(2, usuarioConectado);
            pst.setString(3, usuarioConectado);
            rs=pst.executeQuery();

            while(rs.next()) {
                AnyadirConversacionActivity.listaUsuarios.add(rs.getString("nickname"));
            }

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void crearConver(String usuario2) {
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_crear_conver(?,?)");

            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            pst.setString(2, usuario2);
            rs=pst.executeQuery();

        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void borrarConversacion(int idChat) {
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_eliminar_chat(?)");

            ResultSet rs = null;
            pst.setInt(1, idChat);
            rs=pst.executeQuery();

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void borrarCuenta() {
        try{
            PreparedStatement pst = cn.prepareStatement("SELECT f_eliminar_usuario(?)");
            ResultSet rs = null;
            pst.setString(1, usuarioConectado);
            rs=pst.executeQuery();

        }catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


}
