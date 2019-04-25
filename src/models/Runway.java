/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
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
     *
     * @param spaceship
     * @param number
     */
    public Runway(Spaceship spaceship, int number) {
        this.spaceship = spaceship;
        this.numberRunway = number;
        this.status = "FREE";
        this.numLandindings = 0;
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

}
