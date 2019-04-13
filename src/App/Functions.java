package App;

import dao.StarDao;
import exeption.*;
import models.*;
import utils.Colors;

import java.sql.SQLException;
import java.util.List;

public class Functions {

    /**** QUERYS ****/
    public static void altaSpaceship(StarDao starDAO, Spaceship spaceship) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceship(spaceship);
            System.out.println("Spaceship added successfully.");
        } catch (DaoExcepion ex) {
            System.out.println(ex.getMessage());
        } finally {
            starDAO.desconectar();
        }
    }

    public static void altaSpaceport(StarDao starDAO, Spaceport spaceport) throws SQLException {
        try {
            starDAO.conectar();
            starDAO.insertSpaceport(spaceport);
            System.out.println("Spaceport added successfully.");
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


}
