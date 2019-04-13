/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.HashMap;

/**
 *
 * @author uriigrao
 */
public class Spaceport {

    private String name;
    private String planet;
    private String galaxy;
    public HashMap<Integer, Runway> runways;

    public Spaceport(String name, String planet, String galaxy) {
        this.name = name;
        this.planet = planet;
        this.galaxy = galaxy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public HashMap<Integer, Runway> getRunways() {
        return runways;
    }

    public void setRunways(HashMap<Integer, Runway> runways) {
        this.runways = runways;
    }

    public void setOneRunway(Runway runway) {
        this.runways.put(runway.getNumberRunway(), runway);
    }
}
