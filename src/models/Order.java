package models;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {

    private static final long serialVersionUID = 8929221234561097690L;

    private int id;
    private HashMap<Integer, Integer> menuIdByQuantity;
    private String name;
    private String postcode;

    public Order(int id, HashMap<Integer, Integer> menuIdsByQuantity, String name, String postcode) {

        this.id = id;
        this.menuIdByQuantity = menuIdsByQuantity;
        this.name = name;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, Integer> getMenuIdByQuantity() {
        return menuIdByQuantity;
    }

    public String getPostcode() {
        return postcode;
    }
}
