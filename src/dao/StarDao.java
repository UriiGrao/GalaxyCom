/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import App.Functions;
import exeption.DaoExcepion;
import models.*;
import App.Functions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        st.close();
        return spaceports;
    }

    // ********************* Inserts ****************************
    public void insertSpaceship(Spaceship sp) throws DaoExcepion, SQLException {
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

    // ********************* Conectar / Desconectar ****************************
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/starstucom?serverTimezone=UTC";
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
