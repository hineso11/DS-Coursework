package requests.specific;

import requests.RequestType;
import requests.base.ClientRequest;

import java.io.Serializable;
import java.util.HashMap;

public class PlaceOrderRequest extends ClientRequest implements Serializable {

    private static final long serialVersionUID = 6529681229613857690L;

    private HashMap<Integer, Integer> menuIdsByQuantity;
    private String name;
    private String postcode;

    public PlaceOrderRequest(HashMap<Integer, Integer> menuIdsByQuantity, String name, String postcode) {
        super(RequestType.PLACE_ORDER);

        this.menuIdsByQuantity = menuIdsByQuantity;
        this.name = name;
        this.postcode = postcode;
    }

    public HashMap<Integer, Integer> getMenuIdsByQuantity() {
        return menuIdsByQuantity;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }
}
