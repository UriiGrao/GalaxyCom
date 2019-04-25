/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import utils.Colors;
import utils.EntradaDatos;


/**
 * @author uriigrao
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Functions.test();

        System.out.println(Colors.BLUE + "--> WELCOME TO START STUCOM <--" + Colors.RESET);

        //Opciones Menu
        boolean exit = true;
        while (exit) {
            switch (menu()) {
                case 1:
                    Functions.crearSpacePort();
                    break;
                case 2:
                    System.out.println(Functions.printRed("Mantenimiento..."));
                    // Functions.crearRunway();
                    break;
                case 3:
                    Functions.crearSpaceShip();
                    break;
                case 12:
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
        System.out.println("||------------- MENU! -------------||");
        System.out.println("|| 1. Crear Aeropuertos            ||");
        System.out.println("|| 2. Crear Pista Aterrizajes      ||");
        System.out.println("|| 3. Crear Naves                  ||");
        System.out.println("|| 4. Aterrizar Nave               ||");
        System.out.println("|| 5. Despegar Nave                ||");
        System.out.println("|| 6. Notificar Fin Limpieza       ||");
        System.out.println("|| 7. Notificar Fin Mantenimiento  ||");
        System.out.println("|| 8. Borrar Nave                  ||");
        System.out.println("|| 9. Ver informacion Naves        ||");
        System.out.println("|| 10. Ver informacion Aeropuertos ||");
        System.out.println("|| 11. Ver estado Aeropuertos      ||");
        System.out.println("|| 12. Salir                       ||");
        System.out.println(Colors.RESET);
        return EntradaDatos.pedirEntero("Elige Opcion: ");
    }

}