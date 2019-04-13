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
public class Spaceship {

    private String name;
    private int capacity;
    private String status; // FLYING, LANDED, BROKEN.
    // BROKEN: cada 15 vuelos se debe revisar avion.
    private int numFlights;

    /**
     *
     * @param name
     * @param capacity
     */
    public Spaceship(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.status = "LANDED";
        this.numFlights = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumFlights() {
        return numFlights;
    }

    public void setNumFlights(int numFlights) {
        this.numFlights = numFlights;
    }

}
