/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import dao.StarDao;
import utils.Colors;
import utils.EntradaDatos;

import java.sql.*;
import java.util.Scanner;

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
        // testingDB(stardao);

        //Opciones Menu
        boolean exit = true;
        while (exit) {
            switch (menu()) {
                case 1:
                    break;
                case 4:
                    System.out.println(Functions.printPurple("See You!! :P"));
                    exit = false;
                    break;
                default:
                    exit = false;
            }
        }
    }

    /**
     * function de menu para poder realizar el juego y no llenar mucho el void
     * main.
     *
     * @return
     */
    private static int menu() {

        // MENU DONDE SE LLAMA QUE OPCION QUIERES.
        System.out.print(Colors.BLUE);
        System.out.println("||------- MENU! -------||");
        System.out.println("|| 1. Conseguir Cartas ||");
        System.out.println("|| 2. Batalla          ||");
        System.out.println("|| 3. Ranking          ||");
        System.out.println("|| 4. Salir.           ||");
        System.out.println(Colors.RESET);
        return EntradaDatos.pedirEntero("Elige Opcion: ");
    }

    private static void testingDB(StarDao dao) {

        System.out.println("************************************************************");
        System.out.println("Testeando conexión con la base de datos...");
        try {
            dao.conectar();
            System.out.println("Establecida la conexión.");
            System.out.println("************************************************************");
            System.out.println("Cerrando conexión con la base de datos");
            dao.desconectar();
            System.out.println("Conexión cerrada.");
            System.out.println("************************************************************");
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
    }
}