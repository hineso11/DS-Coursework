package responses;

import models.Menu;

import java.io.Serializable;

public class MenuResponse extends ClientResponse implements Serializable {

    private static final long serialVersionUID = 8929221234567857690L;

    private Menu menu;

    public MenuResponse(Menu menu) {
        super(ResponseType.MENU);

        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
