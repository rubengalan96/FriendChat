package com.rugacon.friendchat;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

        // La conexión a la base de datos a la que se referencia
        public static Connection cn;

        // Conectar a la base de datos
        public static void conectarBD()
        {
            try
            {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
            String ruta = "jdbc:postgresql://friendchatbd.cdp5nzfp36jk.eu-west-3.rds.amazonaws.com:5432/friendchatdb";


            String user = "userbd";
            String password = "userbd";


                Class.forName("org.postgresql.Driver");
                cn = DriverManager.getConnection(ruta,user,password);
            }
            catch (SQLException | ClassNotFoundException e)
            {
                System.out.println("Error de conexión a la base de datos.");
            }
        }

        // Cierra la conexión a la base de datos
        public static void cerrarConexionBD()
        {
            try
            {
                cn.close();
                cn = null;
            }
            catch (SQLException e)
            {
                System.out.println("Error al cerrar conexión de la base de datos");
            }
        }
    }

