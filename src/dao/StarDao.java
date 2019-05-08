/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import App.Functions;
import exeption.DaoException;
import models.*;

import java.sql.*;
import java.util.*;

/**
 * @author uriigrao
 */
public class StarDao {

    private Connection connection;

    // ********************* Selects ****************************

    /**
     * Function to take from bd all runways from spaceport .
     *
     * @param nameSpaceport the name of spaceport
     * @return ArrayList from runways in this spaceport
     * @throws SQLException exception SQL
     */
    public ArrayList<Runway> selectAllRunway(String nameSpaceport) throws SQLException {
        String query = "select * from runway where spaceport='" + nameSpaceport + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Runway> runways = new ArrayList<>();
        while (rs.next()) {
            int number = rs.getInt("number");
            String status = rs.getString("status");
            int numlandings = rs.getInt("numlandings");
            String spaceshipName = rs.getString("spaceship");
            Spaceship spaceship = selectSpaceship(spaceshipName);
            Runway runway = new Runway(number, status, numlandings, spaceship);
            runways.add(runway);
        }
        rs.close();
        st.close();
        return runways;
    }

    /**
     * Function to take from bd one spaceship with name.
     *
     * @param spaceshipName name of spaceship to take
     * @return all date of spaceship with this name.
     * @throws SQLException exception SQl
     */
    private Spaceship selectSpaceship(String spaceshipName) throws SQLException {
        String query = "select * from spaceship where name='" + spaceshipName + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        Spaceship spaceship = null;
        while (rs.next()) {
            String name = rs.getString("name");
            int capacity = rs.getInt("capacity");
            String status = rs.getString("status");
            int numflights = rs.getInt("numflights");
            spaceship = new Spaceship(name, capacity, status, numflights);
        }
        rs.close();
        st.close();
        return spaceship;
    }

    /**
     * Function to take from BD all spaceports.
     *
     * @return ArrayList with all spaceports
     * @throws SQLException SQL exception
     */
    public ArrayList<Spaceport> selectAllSpaceport() throws SQLException {
        String query = "select * from spaceport";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Spaceport> spaceports = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            String planet = rs.getString("planet");
            String galaxy = rs.getString("galaxy");
            Spaceport spaceport = new Spaceport(name, planet, galaxy);

            spaceports.add(spaceport);
        }
        rs.close();
        st.close();
        return spaceports;
    }

    /**
     * Function to take from BD all spaceships.
     *
     * @return ArrayList with all spaceships
     * @throws SQLException SQL exception
     */
    public ArrayList<Spaceship> selectAllSpaceship() throws SQLException {
        String query = "select * from spaceship";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Spaceship> spaceships = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int capacity = rs.getInt("capacity");
            String status = rs.getString("status");
            int numflights = rs.getInt("numflights");
            Spaceship spaceship = new Spaceship(name, capacity, status, numflights);

            spaceships.add(spaceship);
        }
        rs.close();
        st.close();
        return spaceships;
    }

    /**
     * Function to take from BD all names spaceships.
     *
     * @param status of spaceships want take
     * @return arrayList with names of spaceships
     * @throws SQLException SQl exception
     */
    public ArrayList<String> selectAllNameSpaceschip(String status) throws SQLException {
        String query = "";
        if (status.equalsIgnoreCase("")) {
            query = "select name from spaceship";
        } else {
            query = "select name from spaceship where status='" + status + "'";
        }
        return selectAllNames(query);
    }

    /**
     * Function to take query and do the query
     *
     * @param query to run
     * @return the result of query
     * @throws SQLException SQL exception
     */
    private ArrayList<Integer> selectNamesRunways(String query) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Integer> names = new ArrayList<>();
        while (rs.next()) {
            Integer name = rs.getInt("number");
            names.add(name);
        }
        rs.close();
        st.close();
        return names;
    }

    /**
     * Function to take from BD all runways with status Cleaning
     *
     * @return ArrayList from numbers runways.
     * @throws SQLException SQL exception
     */
    public ArrayList<Integer> selectNameAllRunwaysClean() throws SQLException {
        String query = "select number from runway where status='CLEANING'";
        return selectNamesRunways(query);
    }

    /**
     * Function to take from BD all spaceships broken
     *
     * @return ArrayList with name about spaceships broken
     * @throws SQLException SQL exception
     */
    public ArrayList<String> selectNameAllSpaceshipsClean() throws SQLException {
        String query = "select name from spaceship where status='BROKEN'";
        return selectAllNames(query);
    }

    /**
     * Function to take from BD all numbers of runways in this spaceport.
     *
     * @param spaceport name
     * @return ArrayList about int runways
     * @throws SQLException SQL exception
     */
    public ArrayList<Integer> selectAllNameRunway(String spaceport) throws SQLException {
        String query;
        if (spaceport.equals("")) {
            query = "select number from runway where status='FREE'";
        } else {
            query = "select number from runway where spaceport='" + spaceport + "' and status='FREE'";
        }
        return selectNamesRunways(query);
    }

    /**
     * Function to take from BD all spaceports names.
     *
     * @return ArrayList from Strings names
     * @throws SQLException SQL exception
     */
    public ArrayList<String> selectAllNamespaceport() throws SQLException {
        String query = "select name from spaceport";
        return selectAllNames(query);
    }

    /**
     * Function for do all query to take names.
     *
     * @param select query to do
     * @return the result of query
     * @throws SQLException SQL exception
     */
    private ArrayList<String> selectAllNames(String select) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
        ArrayList<String> names = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            names.add(name);
        }

        rs.close();
        st.close();
        return names;
    }

    /**
     * Function to take from BD the runway number landings with this spaceship.
     *
     * @param spName the name of spaceship
     * @return the number landings of runway
     */
    private int selectRunwayNL(String spName) {
        int number = 0;

        try {
            String query = "select numlandings from runway where spaceship='" + spName + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                number = rs.getInt("number");
            }
            rs.close();
            st.close();
        } catch (SQLException sq) {

        }
        return number;

    }

    /**
     * Function to take from BD the runway number with this spaceship
     *
     * @param spName the name of spaceship
     * @return the number of runway
     */
    private int selectRunwayNumber(String spName) {
        int number = 0;

        try {
            String query = "select number from runway where spaceship='" + spName + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                number = rs.getInt("number");
            }
            rs.close();
            st.close();
        } catch (SQLException sq) {
            System.out.println(Functions.printRed(sq.getMessage()));
        }
        return number;

    }

    /**
     * Function to take from BD the number of flying have this spaceship
     *
     * @param nameSpaceship the name of spaceship
     * @return the number of flying from this spaceship
     */
    private int selectSpaceshipFly(String nameSpaceship) {
        int number = 0;

        try {
            String query = "select numflights from spaceship where name='" + nameSpaceship + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                number = rs.getInt("numflights");
            }
        } catch (SQLException sq) {
            System.out.println(Functions.printRed(sq.getMessage()));
        }
        return number;
    }

    // ********************* Inserts / Updates ****************************

    /**
     * Function to insert into BD the spaceship
     *
     * @param sp        spaceship to insert
     * @param numRunway the number of runway to insert spaceship
     * @throws DaoException Exception DAO
     * @throws SQLException exception  SQL
     */
    public void insertSpaceship(Spaceship sp, int numRunway) throws DaoException, SQLException {
        if (existSpaceship(sp)) {
            throw new DaoException(Functions.printRed("ERROR: Exist one Spaceship with this name"));
        }

        String insert = "insert into spaceship values (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        ps.setString(1, sp.getName());
        ps.setInt(2, sp.getCapacity());
        ps.setString(3, sp.getStatus());
        ps.setInt(4, sp.getNumFlights());
        ps.executeUpdate();
        ps.close();

        Statement st = connection.createStatement();
        String update = "update runway set spaceship='" + sp.getName() + "', status='BUSY"
                + "' where number='" + numRunway + "'";
        st.executeUpdate(update);
        st.close();


    }

    /**
     * Function to insert into BD the runway
     *
     * @param ry     the runway to insert
     * @param nameSp the name of spaceport
     * @throws DaoException exception DAO
     * @throws SQLException exception SQL
     */
    public void insertRunway(Runway ry, String nameSp) throws DaoException, SQLException {
        if (existRunway(ry)) {
            throw new DaoException(Functions.printRed("ERROR: Exist one Runway with this name"));
        }
        String insert = "insert into runway values(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        ps.setString(1, nameSp);
        ps.setInt(2, ry.getNumberRunway());
        ps.setString(3, ry.getStatus());
        ps.setInt(4, ry.getNumLandindings());
        ps.setNull(5, Types.VARCHAR);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Function to insert into BD the spaceport
     *
     * @param spaceport the spaceport to insert
     * @throws DaoException exception DAO
     * @throws SQLException exception SQL
     */
    public void insertSpaceport(Spaceport spaceport) throws DaoException, SQLException {
        if (existSpaceport(spaceport)) {
            throw new DaoException(Functions.printRed("ERROR: Exist one Spaceport with this name"));
        }

        String insert = "insert into spaceport values (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insert);
        ps.setString(1, spaceport.getName());
        ps.setString(2, spaceport.getPlanet());
        ps.setString(3, spaceport.getGalaxy());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Function to see if spaceship exist in BD.
     *
     * @param spaceship the spaceship to see if exist
     * @return if true or false
     * @throws SQLException exception SQL
     */
    private boolean existSpaceship(Spaceship spaceship) throws SQLException {
        String select = "select * from spaceship where name='" + spaceship.getName() + "'";
        return existS(select);
    }

    /**
     * Function to see if runway exist in BD
     *
     * @param runway the runway to see
     * @return if true or false
     * @throws SQLException exception SQL
     */
    public boolean existRunway(Runway runway) throws SQLException {
        String select = "select * from runway where number='" + runway.getNumberRunway() + "'";
        return existS(select);
    }

    /**
     * Function to see if spaceport exist in BD
     *
     * @param spaceport to see if exist
     * @return true or false
     * @throws SQLException exception SQL
     */
    private boolean existSpaceport(Spaceport spaceport) throws SQLException {
        String select = "select * from spaceport where name='" + spaceport.getName() + "'";
        return existS(select);
    }

    /**
     * Function to see if exist in BD
     *
     * @param select the query
     * @return true or false
     * @throws SQLException sql exception
     */
    private boolean existS(String select) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(select);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    /**
     * In this function we do a update in bd Runway, Spaceship:
     * in spaceship change status to Flying and in Runway change a FREE or CLEANING if this is multiple to 5.
     * Then we put 1 more fly in spaceship and 1 more landing in runway.
     *
     * @param nameSs the name of spaceship
     */
    public void flySpaceship(String nameSs) {
        try {

            int runwayNL = selectRunwayNL(nameSs);
            int runwayNumber = selectRunwayNumber(nameSs);

            Statement st = connection.createStatement();
            String update = "update spaceship set status='FLYING', numflights=numflights+1"
                    + " where name='" + nameSs + "'";
            st.executeUpdate(update);
            update = "update runway set spaceship=null, numlandings=numlandings+1," +
                    " status='FREE' where spaceship='" + nameSs + "'";

            if ((runwayNL + 1) % 5 == 0) {
                update = "update runway set status='CLEANING' where number='" + runwayNL + "'";
            }
            st.executeUpdate(update);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }

    /**
     * In this function we do a update in bd Runway, spaceship:
     * First we update de status for BUSY in runway and BROKEN or LANDED in spaceship
     * if num flying are multiple to 15.
     *
     * @param nameSpaceship name of spaceship
     * @param runwayNumber  name of runway
     */
    public void landSpaceship(String nameSpaceship, int runwayNumber) {
        try {
            Statement st = connection.createStatement();
            String update = "update runway set spaceship='" + nameSpaceship + "', status='BUSY' where number='" + runwayNumber + "'";
            st.executeUpdate(update);

            int numFlights = selectSpaceshipFly(nameSpaceship);
            if (numFlights % 15 == 0) {
                update = "update spaceship set status='BROKEN' where name='" + nameSpaceship + "'";
            } else {
                update = "update spaceship set status='LANDED' where name='" + nameSpaceship + "'";
            }
            st.executeUpdate(update);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }

    /**
     * Function to change status to Free in runway
     *
     * @param numRunway the number of runway
     */
    public void limpiarRunway(int numRunway) {
        try {
            Statement st = connection.createStatement();
            String update = "update runway set status='FREE' where number='" + numRunway + "'";
            st.executeUpdate(update);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }

    /**
     * Function to change status to BUSY in spaceship
     *
     * @param spaceshipName the name of spaceship
     */
    public void limpiarSpaceship(String spaceshipName) {
        try {
            Statement st = connection.createStatement();
            String update = "update spaceship set status='LANDED' where name='" + spaceshipName + "'";
            st.executeUpdate(update);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }

    // ********************* Deletes ****************************

    /**
     * Function to delete from BD the spaceship
     *
     * @param name of spaceship
     */
    public void deleteSpaceship(String name) {
        try {

            Statement st = connection.createStatement();
            String update = "update runway set status='FREE"
                    + "' where spaceship='" + name + "'";
            st.executeUpdate(update);

            String delete = "delete from spaceship where name='" + name + "'";
            st.executeUpdate(delete);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }


    // ********************* Conectar / Desconectar ****************************

    /**
     * Function to connect to BD
     *
     * @throws SQLException exception SQL
     */
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/StarStucom?serverTimezone=UTC";
        String user = "root";
        String pass = "";
        connection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * Function to disconnect to BD
     *
     * @throws SQLException SQL exception
     */
    public void desconectar() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
