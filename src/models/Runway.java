/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * @author uriigrao
 */

public class Runway {

    private Spaceship spaceship;
    private int numberRunway; // numero de pista.
    private String status; // FREE, BUSY, CLEANING.
    // BUSY: cuando una nave la este ocupando. 
    // CLEANING: limpiando, cada 5 aterrizajes la pista debera limpiarse
    private int numLandindings;

    /**
     * @param number
     */
    public Runway(int number) {
        this.numberRunway = number;
        this.status = "FREE";
        this.numLandindings = 0;
    }

    public Runway(int number, String status, int numLandings, Spaceship spaceship) {
        this.spaceship = spaceship;
        this.numberRunway = number;
        this.numLandindings = numLandings;
        this.status = status;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public int getNumberRunway() {
        return numberRunway;
    }

    public void setNumberRunway(int numberRunway) {
        this.numberRunway = numberRunway;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumLandindings() {
        return numLandindings;
    }

    public void setNumLandindings(int numLandindings) {
        this.numLandindings = numLandindings;
    }

    @Override
    public String toString() {
        return " numberRunway=" + numberRunway +
                ", status='" + status + '\'' +
                ", numLandindings=" + numLandindings;
    }
}
