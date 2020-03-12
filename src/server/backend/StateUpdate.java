package server.backend;

import models.Order;
import responses.base.ClientResponse;

import java.io.Serializable;

public class StateUpdate implements Serializable {

    private static final long serialVersionUID = 6529227638402857690L;

    private int responseId;
    private ClientResponse newClientResponse;
    private Order newOrder;

    public StateUpdate(int responseId, ClientResponse newClientResponse, Order newOrder) {

        this.responseId = responseId;
        this.newClientResponse = newClientResponse;
        this.newOrder = newOrder;
    }

    public int getResponseId() {
        return responseId;
    }

    public ClientResponse getNewClientResponse() {
        return newClientResponse;
    }

    public Order getNewOrder() {
        return newOrder;
    }
}
