package App;

import com.mysql.cj.xdevapi.SqlDataResult;
import dao.StarDao;
import exeption.*;
import models.*;
import utils.Colors;
import utils.EntradaDatos;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<String> names = Functions.selectNameSpacesports();
        String nameSpaceport = EntradaDatos.askString("Elige nombre de Spaceport...", names);
        System.out.println("Now Runway dates:");
        int numberRunway = EntradaDatos.pedirEntero("Numero de Runway");
        Runway runway = new Runway(numberRunway);
        try {
            altaRunway(runway, nameSpaceport);
            System.out.println(printPurple("Runway added successfully."));
        } catch (SQLException sqex) {
            System.out.println(printRed(sqex.getMessage()));
        }
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

    public static void removeSpaceship() {
        ArrayList<String> names = Functions.selectNameSpaceships();
        String name = EntradaDatos.askString("Elige Nombre de Spaceship a borrar...", names);
        borrarSpaceship(name);
    }


    /**** QUERYS ****/
    public static void borrarSpaceship(String name) {
        try {
            starDAO.conectar();
            starDAO.deleteSpaceship(name);
            System.out.println(printPurple("Spaceship Deleted Successfully! "));
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

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

    public static void altaRunway(Runway runway, String nameSP) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertRunway(runway, nameSP);
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

    public static ArrayList<String> selectNameSpaceships() {
        try {
            starDAO.conectar();
            ArrayList<String> names = starDAO.selectAllNameSpaceschip();
            return names;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
        ArrayList<String> no = new ArrayList<>();
        return no;
    }

    public static ArrayList<String> selectNameSpacesports() {
        try {
            starDAO.conectar();
            ArrayList<String> names = starDAO.selectAllNameSpacesport();
            return names;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
        ArrayList<String> no = new ArrayList<>();
        return no;
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
