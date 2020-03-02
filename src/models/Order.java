package models;

import java.util.HashMap;

public class Order {

    private static final long serialVersionUID = 8929221234561097690L;

    private int id;
    private HashMap<MenuItem, Integer> menuItemsByQuantity;
    private String name;
    private String postcode;

    public Order(int id, HashMap<MenuItem, Integer> menuItemsByQuantity, String name, String postcode) {

        this.id = id;
        this.menuItemsByQuantity = menuItemsByQuantity;
        this.name = name;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<MenuItem, Integer> getMenuItemsByQuantity() {
        return menuItemsByQuantity;
    }

    public String getPostcode() {
        return postcode;
    }
}
