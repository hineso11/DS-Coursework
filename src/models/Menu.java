package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {

    private static final long serialVersionUID = 8984741234567857690L;

    private ArrayList<MenuItem> menuItems;

    public Menu(ArrayList<MenuItem> menuItems) {

        this.menuItems = menuItems;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public static Menu fromSampleData() {

        ArrayList<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(1, "Crispy Pork", 7.8));
        menuItems.add(new MenuItem(2, "Thai Green Curry", 8.4));
        menuItems.add(new MenuItem(3, "Pad Thai", 6.4));
        menuItems.add(new MenuItem(4, "Jasmine rice", 2.4));
        menuItems.add(new MenuItem(5, "River Prawns", 12.2));

        return new Menu(menuItems);
    }

    public MenuItem getItemForId(int id) {

        for (MenuItem item : this.menuItems) {

            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }
}
