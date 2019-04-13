/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;

/**
 * @author uriigrao
 */
public class StarDao {

    Connection conexion;

    // ********************* Conectar / Desconectar ****************************
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/starstucom";
        String user = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

}
