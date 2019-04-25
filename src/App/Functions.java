package App;

import dao.StarDao;
import exeption.*;
import models.*;
import utils.Colors;
import utils.EntradaDatos;

import java.sql.SQLException;
import java.util.List;

public class Functions {

    private static StarDao starDAO = new StarDao();

    public static void test() {
        // TEST:
        System.out.println("************************************************************");
        System.out.println("Testeando conexión con la base de datos...");
        try {
            starDAO.conectar();
            System.out.println("Establecida la conexión.");
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
    }

    /**** Functions Menu ****/
    public static void crearSpacePort() {
        System.out.println("Spaceport dates:");
        String name = EntradaDatos.pedirCadena("name Spaceport");
        String planet = EntradaDatos.pedirCadena("name Planet");
        String galaxy = EntradaDatos.pedirCadena("name Galaxy");

        Spaceport spaceport = new Spaceport(name, planet, galaxy);
        try {
            altaSpaceport(spaceport);
            System.out.println(printPurple("Spaceport added successfully."));
        } catch (SQLException sqex) {

        }
    }

    public static void crearRunway() {

    }

    public static void crearSpaceShip() {
        System.out.println("Spaceship dates:");
        String name = EntradaDatos.pedirCadena("name");
        int capacity = EntradaDatos.pedirEntero("capacity");

        Spaceship spaceship = new Spaceship(name, capacity);
        try {
            altaSpaceship(spaceship);
            System.out.println(printPurple("Spaceship added successfully."));
        } catch (SQLException sqex) {

        }
    }


    /**** QUERYS ****/
    public static void altaSpaceship(Spaceship spaceship) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceship(spaceship);
        } catch (DaoExcepion ex) {
            System.out.println(ex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    public static void altaRunway(Runway runway) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertRunway(runway);
        } catch (DaoExcepion dex) {
            System.out.println(dex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    public static void altaSpaceport(Spaceport spaceport) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceport(spaceport);
        } catch (DaoExcepion dex) {
            System.out.println(dex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    public static List<Spaceport> allSpaceports(StarDao starDAO) {
        try {
            starDAO.conectar();
            List<Spaceport> spaceports = starDAO.selectAllSpaceport();
            return spaceports;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
        return null;
    }

    /***** COLORS *****/
    public static String printBlue(String text) {
        return Colors.BLUE + text + Colors.RESET;
    }

    public static String printRed(String text) {
        return Colors.RED + text + Colors.RESET;

    }

    public static String printPurple(String text) {
        return Colors.PURPLE + text + Colors.RESET;
    }


}
