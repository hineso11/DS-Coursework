package models;

import java.io.Serializable;

public class MenuItem implements Serializable {

    private static final long serialVersionUID = 6529227634567857690L;

    private int id;
    private String name;
    private double price;

    public MenuItem(int id, String name, double price) {

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public double getPrice() {

        return this.price;
    }
}
