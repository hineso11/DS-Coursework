package responses.success;

import models.Order;
import responses.ResponseType;
import responses.base.ClientResponse;

import java.io.Serializable;

public class OrderResponse extends ClientResponse implements Serializable {

    private static final long serialVersionUID = 8929221268567857690L;

    private Order order;

    public OrderResponse(Order order) {
        super(ResponseType.ORDER);

        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
