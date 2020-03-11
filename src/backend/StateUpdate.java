package backend;

import responses.base.ClientResponse;

import java.io.Serializable;

public class StateUpdate implements Serializable {

    private static final long serialVersionUID = 6529227638402857690L;

    private int responseId;
    private ClientResponse newClientResponse;

    public StateUpdate(int responseId, ClientResponse newClientResponse) {

        this.responseId = responseId;
        this.newClientResponse = newClientResponse;
    }

    public int getResponseId() {
        return responseId;
    }

    public ClientResponse getNewClientResponse() {
        return newClientResponse;
    }
}
