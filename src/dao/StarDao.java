/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import App.Functions;
import exeption.DaoExcepion;
import models.*;

import java.sql.*;
import java.util.*;

/**
 * @author uriigrao
 */
public class StarDao {

    Connection conexion;


    // ********************* Selects ****************************
    public List<Spaceport> selectAllSpaceport() throws SQLException {
        String query = "select * from spaceport";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Spaceport> spaceports = new ArrayList<>();
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

    public ArrayList<Spaceship> selectAllSpaceschip() throws SQLException {
        String query = "select * from spaceship";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Spaceship> spaceships = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int capacity = rs.getInt("capacity");
            String status = rs.getString("status");
            int numFlights = rs.getInt("numflights");
            Spaceship spaceship = new Spaceship(name, capacity, status, numFlights);

            spaceships.add(spaceship);
        }

        rs.close();
        st.close();
        return spaceships;
    }

    public ArrayList<String> selectAllNameSpaceschip(String status) throws SQLException {
        String query = "";
        if (status.equalsIgnoreCase("")) {
            query = "select name from spaceship";
        } else {
            query = "select name from spaceship where status='" + status + "'";
        }
        return selectAllNames(query);
    }

    public ArrayList<Integer> selectAllNameRunway(String spaceport) throws SQLException {
        String query;
        if (spaceport.equals("")) {
            query = "select number from runway where status='FREE'";
        } else {
            query = "select number from runway where spaceport='" + spaceport + "' and status='FREE'";
        }
        Statement st = conexion.createStatement();
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

    public ArrayList<String> selectAllNameSpacesport() throws SQLException {
        String query = "select name from spaceport";
        return selectAllNames(query);
    }

    private ArrayList<String> selectAllNames(String select) throws SQLException {
        Statement st = conexion.createStatement();
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

    // ********************* Inserts / Updates ****************************
    public void insertSpaceship(Spaceship sp, int numRunway) throws DaoExcepion, SQLException {
        if (existSpaceship(sp)) {
            throw new DaoExcepion(Functions.printRed("ERROR: Exist one Spaceship with this name"));
        }

        String insert = "insert into spaceship values (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, sp.getName());
        ps.setInt(2, sp.getCapacity());
        ps.setString(3, sp.getStatus());
        ps.setInt(4, sp.getNumFlights());
        ps.executeUpdate();
        ps.close();

        Statement st = conexion.createStatement();
        String update = "update runway set spaceship='" + sp.getName() + "', status='BUSY"
                + "' where number='" + numRunway + "'";
        st.executeUpdate(update);
        st.close();


    }

    public void insertRunway(Runway ry, String nameSp) throws DaoExcepion, SQLException {
        if (existRunway(ry)) {
            throw new DaoExcepion(Functions.printRed("ERROR: Exist one Runway with this name"));
        }
        String insert = "insert into runway values(?,?,?,?,?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, nameSp);
        ps.setInt(2, ry.getNumberRunway());
        ps.setString(3, ry.getStatus());
        ps.setInt(4, ry.getNumLandindings());
        ps.setNull(5, Types.VARCHAR);
        ps.executeUpdate();
        ps.close();
    }

    public void insertSpaceport(Spaceport spaceport) throws DaoExcepion, SQLException {
        if (existSpaceport(spaceport)) {
            throw new DaoExcepion(Functions.printRed("ERROR: Exist one Spaceport with this name"));
        }

        String insert = "insert into spaceport values (?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, spaceport.getName());
        ps.setString(2, spaceport.getPlanet());
        ps.setString(3, spaceport.getGalaxy());
        ps.executeUpdate();
        ps.close();
    }

    private boolean existSpaceship(Spaceship spaceship) throws SQLException {
        String select = "select * from spaceship where name='" + spaceship.getName() + "'";
        return existS(select);
    }

    public boolean existRunway(Runway runway) throws SQLException {
        String select = "select * from runway where number='" + runway.getNumberRunway() + "'";
        return existS(select);
    }

    private boolean existSpaceport(Spaceport spacesport) throws SQLException {
        String select = "select * from spaceport where name='" + spacesport.getName() + "'";
        return existS(select);
    }

    private boolean existS(String select) throws SQLException {
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    public void landSpaceship(String nameSs, String status) {
        try {
            Statement st = conexion.createStatement();
            String update = "update spaceship set status='" + status
                    + "', numflights=numflights+1"
                    + " where name='" + nameSs + "'";
            st.executeUpdate(update);

            update = "update runway set spaceship=null, numlandings=numlandings+1," +
                    " status='FREE' where spaceship='" + nameSs + "'";
            st.executeUpdate(update);
            st.close();
        } catch (SQLException sx) {
            System.out.println(Functions.printRed(sx.getMessage()));
        }
    }

    // ********************* Deletes ****************************

    public void deleteSpaceship(String name) {
        try {

            Statement st = conexion.createStatement();
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
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/StarStucom?serverTimezone=UTC";
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
