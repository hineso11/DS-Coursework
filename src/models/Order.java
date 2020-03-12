package models;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {

    private static final long serialVersionUID = 8929221234561097690L;

    private HashMap<MenuItem, Integer> menuItemByQuantity;
    private String name;
    private String postcode;

    public Order(HashMap<MenuItem, Integer> menuItemByQuantity, String name, String postcode) {

        this.menuItemByQuantity = menuItemByQuantity;
        this.name = name;
        this.postcode = postcode;
    }

    public HashMap<MenuItem, Integer> getMenuItemByQuantity() {
        return menuItemByQuantity;
    }
}
