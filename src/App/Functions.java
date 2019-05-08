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

    public static void crearRunway() throws MiExcepcion {
        ArrayList<String> names = Functions.selectNameSpacesports();

        if (names.size() == 0) {
            throw new MiExcepcion("No hay Spaceport donde crear Runway.");
        }

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

    public static void crearSpaceShip() throws MiExcepcion {
        // Pedimos en que Spacesport crear Spaceship
        ArrayList<String> spaceports = Functions.selectNameSpacesports();
        if (spaceports.size() == 0) {
            throw new MiExcepcion("No Hay Spaceports disponibles.");
        }
        String nameSpaceport = EntradaDatos.askString("Elige nombre de Spaceport...", spaceports);

        //Debemos mirar si hay algun Runway vacio.
        ArrayList<Integer> runways = Functions.selectNameRunways(nameSpaceport);
        if (runways.size() == 0) {
            throw new MiExcepcion("No Hay Runways disponibles en este Planeta.");
        }
        int numRunway = EntradaDatos.askInt("Elige numero de Runway", runways);

        System.out.println("Spaceship dates:");
        String name = EntradaDatos.pedirCadena("name");
        int capacity = EntradaDatos.pedirEntero("capacity");

        Spaceship spaceship = new Spaceship(name, capacity);
        try {
            altaSpaceship(spaceship, numRunway);
            System.out.println(printPurple("Spaceship added successfully."));
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
    }

    public static void removeSpaceship() {
        ArrayList<String> names = Functions.selectNameSpaceships("");
        String name = EntradaDatos.askString("Elige Nombre de Spaceship a borrar...", names);
        borrarSpaceship(name);
    }

    public static void despegarSpaceShip() throws MiExcepcion {
        ArrayList<String> names = selectNameSpaceships("LANDED");
        if (names.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships para despegar."));
        }
        String nameSpaceship = EntradaDatos.askString(printBlue("Selecciona nave: "), names);

        despegarNave(nameSpaceship);
        System.out.println(printPurple("Nave despegada Correctamente!"));
    }

    public static void aterrizarSpaceShip() throws MiExcepcion {
        ArrayList<String> names = selectNameSpaceships("FLYING");
        if (names.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships para aterrizae."));
        }
        String nameSpaceship = EntradaDatos.askString("Selecciona nave: ", names);

        ArrayList<Integer> runways = selectNameAllRunways();
        if (runways.size() == 0) {
            throw new MiExcepcion(printRed("No hay Runways donde aterrizar."));
        }
        int runwayNumber = EntradaDatos.askInt("Selecciona runway: ", runways);

        aterrizarNave(nameSpaceship, runwayNumber);
        System.out.println(printPurple("Nave aterrizada Correctamente!"));
    }


    /**** QUERYS ****/
    private static void aterrizarNave(String nameSpaceship, int runwayNumber) {
        try {
            starDAO.conectar();
            starDAO.landSpaceship(nameSpaceship, runwayNumber);

        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    private static void despegarNave(String name) {
        try {
            starDAO.conectar();
            starDAO.flySpaceship(name);

        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    private static void borrarSpaceship(String name) {
        try {
            starDAO.conectar();
            starDAO.deleteSpaceship(name);
            System.out.println(printPurple("Spaceship Deleted Successfully! "));
        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    private static void altaSpaceship(Spaceship spaceship, int numRunway) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceship(spaceship, numRunway);
        } catch (DaoExcepion ex) {
            System.out.println(ex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    private static void altaRunway(Runway runway, String nameSP) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertRunway(runway, nameSP);
        } catch (DaoExcepion dex) {
            System.out.println(dex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    private static void altaSpaceport(Spaceport spaceport) throws SQLException {
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

    private static ArrayList<String> selectNameSpaceships(String status) {
        try {
            starDAO.conectar();
            ArrayList<String> names;
            if (status.equalsIgnoreCase("")) {
                names = starDAO.selectAllNameSpaceschip("");
            } else {
                names = starDAO.selectAllNameSpaceschip(status);
            }
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

    private static ArrayList<String> selectNameSpacesports() {
        try {
            starDAO.conectar();
            ArrayList<String> spacesportsNames = starDAO.selectAllNameSpacesport();
            return spacesportsNames;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {
                System.out.println(sx.getMessage());
            }
        }
        ArrayList<String> no = new ArrayList<>();
        return no;
    }

    private static ArrayList<Integer> selectNameRunways(String spacesport) {
        try {
            starDAO.conectar();
            ArrayList<Integer> runwayNames = starDAO.selectAllNameRunway(spacesport);
            return runwayNames;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        }
        ArrayList<Integer> no = new ArrayList<>();
        return no;
    }

    private static ArrayList<Integer> selectNameAllRunways() {
        try {
            starDAO.conectar();
            ArrayList<Integer> runwayNames = starDAO.selectAllNameRunway("");
            return runwayNames;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        }
        ArrayList<Integer> no = new ArrayList<>();
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
