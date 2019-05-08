package App;


import dao.StarDao;
import exeption.*;
import models.*;
import utils.Colors;
import utils.EntradaDatos;

import java.sql.SQLException;
import java.util.ArrayList;

public class Functions {
    // variable de DAO.
    private static StarDao starDAO = new StarDao();

    //function donde probamos la conexcion a la BD.
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

    /**** Functions Menu
     * SON FUNCIONES QUE INTERACTUAN CON EL USUARIO PIDIENDO DATOS O DEVOLVIENDO MENSAJES
     * ****/
    /**
     * Funcion donde interactuamos con usuario para crear SpacePort le pedimos sus datos
     * y llamamos a la funcion del DAO.
     */
    public static void crearSpacePort() {
        System.out.println("Spaceport dates:");
        String name = EntradaDatos.pedirCadena("name Spaceport");
        String planet = EntradaDatos.pedirCadena("name Planet");
        String galaxy = EntradaDatos.pedirCadena("name Galaxy");

        Spaceport spaceport = new Spaceport(name, planet, galaxy);
        try {
            altaSpaceport(spaceport);
            System.out.println(printPurple("*** Spaceport added successfully. ***"));
        } catch (SQLException sqex) {

        }
    }

    /**
     * Funcion donde creamos el runway le pedimos los datos y en que Spaceport lo queremos crear
     * si no tenemos spacesport salta un Exeption para decirle al usuario que no hay.
     *
     * @throws MiExcepcion
     */
    public static void crearRunway() throws MiExcepcion {
        ArrayList<String> names = Functions.selectNameSpacesports();

        if (names == null || names.size() == 0) {
            throw new MiExcepcion("No hay Spaceport donde crear Runway.");
        }

        String nameSpaceport = EntradaDatos.askString("Elige nombre de Spaceport...", names);
        System.out.println("Now Runway dates:");
        int numberRunway = EntradaDatos.pedirEntero("Numero de Runway");
        Runway runway = new Runway(numberRunway);
        try {
            altaRunway(runway, nameSpaceport);
            System.out.println(printPurple("*** Runway added successfully. *** "));
        } catch (SQLException sqex) {
            System.out.println(printRed(sqex.getMessage()));
        }
    }

    /**
     * En este insert a parte de spaceport tenemos que mirar a que runway insertar el Spaceship.
     * Si no creariamos un spaceship ya volando
     *
     * @throws MiExcepcion
     */
    public static void crearSpaceShip() throws MiExcepcion {
        // Pedimos en que Spacesport crear Spaceship
        ArrayList<String> spaceports = Functions.selectNameSpacesports();
        if (spaceports == null || spaceports.size() == 0) {
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
            System.out.println(printPurple("*** Spaceship added successfully. ***"));
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
    }

    /**
     * Esta funcion sirve para llamar a la funcion de eliminar Spaceship.
     * Si no hay Spaceships a borrar saltamos la exepcion con el mensaje.
     *
     * @throws MiExcepcion
     */
    public static void removeSpaceship() throws MiExcepcion {
        ArrayList<String> names = Functions.selectNameSpaceships("");
        if (names == null || names.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships a borrar!"));
        }
        String name = EntradaDatos.askString("Elige Nombre de Spaceship a borrar...", names);
        borrarSpaceship(name);
        System.out.println(printPurple("*** Spaceship Deleted Successfully! ***"));
    }

    /**
     * Funcion donde le mostramos al usuario todas las naves que pueden despejar.
     * Si no hay naves salta exepcion
     *
     * @throws MiExcepcion
     */
    public static void despegarSpaceShip() throws MiExcepcion {
        ArrayList<String> names = selectNameSpaceships("LANDED");
        if (names == null || names.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships para despegar."));
        }
        String nameSpaceship = EntradaDatos.askString(printBlue("Selecciona nave: "), names);

        despegarNave(nameSpaceship);
        System.out.println(printPurple("*** Nave despegada Correctamente! *** "));
    }

    /**
     * Funcion para decir que nave queremos que aterrize.
     *
     * @throws MiExcepcion
     */
    public static void aterrizarSpaceShip() throws MiExcepcion {
        ArrayList<String> names = selectNameSpaceships("FLYING");
        if (names == null || names.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships para aterrizae."));
        }
        String nameSpaceship = EntradaDatos.askString("Selecciona nave: ", names);

        ArrayList<Integer> runways = selectNameAllRunways();
        if (runways == null || runways.size() == 0) {
            throw new MiExcepcion(printRed("No hay Runways donde aterrizar."));
        }
        int runwayNumber = EntradaDatos.askInt("Selecciona runway: ", runways);

        aterrizarNave(nameSpaceship, runwayNumber);
        System.out.println(printPurple("*** Nave aterrizada Correctamente! ***"));
    }

    /**
     * Funcion donde le pedimos al usuario que runway quiere limpiar
     * SI no hay saltamos la exepcion.
     *
     * @throws MiExcepcion
     */
    public static void limpiezaRunway() throws MiExcepcion {
        ArrayList<Integer> runways = selectNameAllRunwaysClean();
        if (runways == null || runways.size() == 0) {
            throw new MiExcepcion(printRed("No hay Runways a limpiar."));
        }
        int runwayNumber = EntradaDatos.askInt("Selecciona runway: ", runways);

        limpiarRunway(runwayNumber);
        System.out.println(printPurple("*** Runway limpiada Correctamente! ***"));
    }

    /**
     * Funcion donde le pedimos al usuario que spaceship quiere arreglar
     * Si no hay spaceships rotos saltamos exepcion
     *
     * @throws MiExcepcion
     */
    public static void limpiezaSpaceship() throws MiExcepcion {
        ArrayList<String> spaceship = selectNameSpaceshipsClean();
        if (spaceship == null || spaceship.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships para limpiar."));
        }
        String spaceshipName = EntradaDatos.askString("Selecciona spaceship: ", spaceship);

        limpiarSpaceship(spaceshipName);
        System.out.println(printPurple("*** Spaceship limpiado Correctamente! ***"));

    }

    /**
     * funcion para mostrar todos los spaceships.
     * si no hay spaceships saltamos exepcion
     *
     * @throws MiExcepcion
     */
    public static void watchSpaceShips() throws MiExcepcion {
        System.out.println(printBlue("**** Vamos a ver todos los Spaceships: ****"));
        ArrayList<Spaceship> spaceships = allSpaceships();
        if (spaceships == null || spaceships.size() == 0) {
            throw new MiExcepcion(printRed("No hay Spaceships a mostrar!"));
        }

        for (Spaceship spaceship : spaceships) {
            System.out.println(printPurple("--> " + spaceship.toString() + " <--"));
        }
    }

    /**
     * funcion donde mostramos todos los Runway de un Spaceport.
     * Si no hay runways en ese spaceport saltamos exepcion
     *
     * @throws MiExcepcion
     */
    public static void watchRunways() throws MiExcepcion {
        ArrayList<String> names = Functions.selectNameSpacesports();

        if (names == null || names.size() == 0) {
            throw new MiExcepcion("No hay Spaceport donde elegir Runway.");
        }

        String nameSpaceport = EntradaDatos.askString("Elige nombre de Spaceport...", names);
        ArrayList<Runway> runways = allRunways(nameSpaceport);

        System.out.println(printBlue("**** Vamos a ver todos los Runways de " + nameSpaceport + ": ****"));

        for (Runway runway : runways) {
            System.out.println(printPurple("--> " + runway.toString() + " <--"));
        }
    }

    /**
     * funcion para mostrar todos los spaceport y su numero de Runways disponibles.
     * si no hay spaceports saltamos exepcion
     *
     * @throws MiExcepcion
     */
    public static void watchSpaceport() throws MiExcepcion {
        System.out.println(printBlue("**** Vamos a ver todos los Spaceports: ****"));
        ArrayList<Spaceport> spaceports = allSpaceports();
        if (spaceports == null) {
            throw new MiExcepcion(printRed("No hay Spaceports a mostrar"));
        }
        for (Spaceport spaceport : spaceports) {
            try {
                starDAO.conectar();
                ArrayList<Integer> runways = starDAO.selectAllNameRunway(spaceport.getName());
                System.out.println(printPurple("--> " + spaceport.toString()
                        + " Runways disponibles: " + runways.size() + " <--"));
            } catch (SQLException sq) {
                System.out.println(printRed(sq.getMessage()));
            } finally {
                try {
                    starDAO.desconectar();
                } catch (SQLException sx) {
                    System.out.println(sx.getMessage());
                }
            }
        }
    }

    /**** QUERYS
     * Aqui empiezan las funciones donde llamamos el DAO para hacer SELECT INSERTS y UPDATEs
     * ****/

    /**
     * funcion donde recibimos el numero del runway y llamamos funcion para cambiar su estado.
     *
     * @param numRunway
     */
    private static void limpiarRunway(int numRunway) {
        try {
            starDAO.conectar();
            starDAO.limpiarRunway(numRunway);
        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    /**
     * funcion donde recibimos el nombre de spaceship y llamamos a la funcion para cambiar su estado.
     *
     * @param spaceshipName
     */
    private static void limpiarSpaceship(String spaceshipName) {
        try {
            starDAO.conectar();
            starDAO.limpiarSpaceship(spaceshipName);
        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    /**
     * funcion donde recibimos el nombre spaceship y el numero de runway, aqui llamaremos la funcion para
     * aterrizar la nave a ese runway
     *
     * @param nameSpaceship
     * @param runwayNumber
     */
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

    /**
     * funcion donde recibimos el nombre de la nave a despegar, luego llamamos la funcion de despegar.
     *
     * @param name
     */
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

    /**
     * funcion donde recibimos el nombre del spaceship a borrar.
     *
     * @param name
     */
    private static void borrarSpaceship(String name) {
        try {
            starDAO.conectar();
            starDAO.deleteSpaceship(name);
        } catch (SQLException sx) {
            System.out.println(printRed(sx.getMessage()));
        } finally {
            try {
                starDAO.desconectar();
            } catch (SQLException sx) {

            }
        }
    }

    /**
     * funcion donde recibimos el spaceship y el numero de pista para hacer el insert de spaceship.
     *
     * @param spaceship
     * @param numRunway
     * @throws SQLException
     */
    private static void altaSpaceship(Spaceship spaceship, int numRunway) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceship(spaceship, numRunway);
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    /**
     * funcion donde recibimos un runway y el nombre de spaceport donde se guardara a la bd.
     *
     * @param runway
     * @param nameSP
     * @throws SQLException
     */
    private static void altaRunway(Runway runway, String nameSP) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertRunway(runway, nameSP);
        } catch (DaoException dex) {
            System.out.println(dex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    /**
     * funcion donde recibimos el spaceport y llamamos a la funcion para hacer el insert
     *
     * @param spaceport
     * @throws SQLException
     */
    private static void altaSpaceport(Spaceport spaceport) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceport(spaceport);
        } catch (DaoException dex) {
            System.out.println(dex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    /**
     * funcion donde devolvemos todos los spaceports.
     *
     * @return ArrayList de spaceports.
     */
    private static ArrayList<Spaceport> allSpaceports() {
        try {
            starDAO.conectar();
            ArrayList<Spaceport> spaceports = starDAO.selectAllSpaceport();
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

    /**
     * funcion donde llamamos la funcion de coger todos los runways a partir de ese nombre de spaceport.
     *
     * @param nameSpaceport
     * @return ArrayList runways.
     */
    private static ArrayList<Runway> allRunways(String nameSpaceport) {
        try {
            starDAO.conectar();
            ArrayList<Runway> runways = starDAO.selectAllRunway(nameSpaceport);
            return runways;
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

    /**
     * funcion donde llamamos a la funcion de DAO para recibir todos los spaceships.
     *
     * @return ArrayList spaceships
     */
    private static ArrayList<Spaceship> allSpaceships() {
        try {
            starDAO.conectar();
            ArrayList<Spaceship> spaceships = starDAO.selectAllSpaceship();
            return spaceships;
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

    /**
     * funcion para llamar al DAO la funcion de coger el nombre de los spaceships
     *
     * @param status el status de spaceship que queremos recoger
     * @return ArrayList nombre spaceships.
     */
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
        return null;
    }

    /**
     * FUNCION para llamar al DAO la funcion de coger el nombre de todos los spaceports
     *
     * @return ArrayList nombre de spaceports.
     */
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
        return null;
    }

    /**
     * Funcion para llamar al DAO la funcionde coger los numeros de todos los runways por el mismo spaceport.
     *
     * @param spacesport nombre de spaceport donde coger todos los runways.
     * @return number of runways.
     */
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

    /**
     * funcion para coger del DAO todos los numeros de runways.
     *
     * @return ArrayList de int de runways.
     */
    private static ArrayList<Integer> selectNameAllRunways() {
        try {
            starDAO.conectar();
            ArrayList<Integer> runwayNames = starDAO.selectAllNameRunway("");
            starDAO.desconectar();
            return runwayNames;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        }
        return null;
    }

    /**
     * funcion para coger del DAO todos los numero con estado Cleaning de runways.
     *
     * @return arraylist de int runway
     */
    private static ArrayList<Integer> selectNameAllRunwaysClean() {
        try {
            starDAO.conectar();
            ArrayList<Integer> runwayNames = starDAO.selectNameAllRunwaysClean();
            starDAO.desconectar();
            return runwayNames;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        }
        return null;
    }

    /**
     * funcion para coger del DAO todas las spaceships en estado BROKEN
     *
     * @return array List nombre spaceships
     */
    private static ArrayList<String> selectNameSpaceshipsClean() {
        try {
            starDAO.conectar();
            ArrayList<String> nameSpaceships = starDAO.selectNameAllSpaceshipsClean();
            starDAO.desconectar();
            return nameSpaceships;
        } catch (SQLException sx) {
            System.out.println(sx.getMessage());
        }
        return null;
    }

    /***** COLORS
     * funciones de mostrar en consola con color
     * *****/

    /**
     * Esta funcion recibe un texto y lo pinta de color Azul
     *
     * @param text a mostrar
     * @return text with color Blue
     */
    public static String printBlue(String text) {
        return Colors.BLUE + text + Colors.RESET;
    }

    /**
     * Esta funcion recibe un texto y lo pinta de color Rojo
     *
     * @param text a mostrar
     * @return text with color Red
     */
    public static String printRed(String text) {
        return Colors.RED + text + Colors.RESET;

    }

    /**
     * Esta funcion recibe un texto y lo pinta de color Lila
     *
     * @param text a mostrar
     * @return text with color purple
     */
    public static String printPurple(String text) {
        return Colors.PURPLE + text + Colors.RESET;
    }

}
