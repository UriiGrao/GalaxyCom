/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import dao.StarDao;
import utils.Colors;

import java.sql.*;

/**
 * @author uriigrao
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Colors.BLUE + "--> WELCOME TO START STUCOM <--" + Colors.RESET);

        StarDao stardao = new StarDao();
        // TEST:
        System.out.println("************************************************************");
        System.out.println("Testeando conexión con la base de datos...");
        try {
            stardao.conectar();
            System.out.println("Establecida la conexión.");
            System.out.println("************************************************************");
            System.out.println("Cerrando conexión con la base de datos");
            stardao.desconectar();
            System.out.println("Conexión cerrada.");
            System.out.println("************************************************************");
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
    }

}