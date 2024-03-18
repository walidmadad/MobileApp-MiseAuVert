package com.sio.miseauvert;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    String classes = "net.sourceforge.jtds.jdbc.Driver";
    protected String port = "3306";
    protected String ip = "127.0.0.1";
    protected String username = "root";
    protected String password = "";
    protected String db = "la_mise_au_vert";
    public Connection CONN(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;


        try{
            Class.forName(classes);

            String conUrl = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            connection = DriverManager.getConnection(conUrl,username,password);
        }catch(ClassNotFoundException | SQLException ex){
            throw new RuntimeException(ex);
        }

        return connection;
    }
}
