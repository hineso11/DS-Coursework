package backend;

import responses.ClientResponse;

public class StateUpdate {

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

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public void setNewClientResponse(ClientResponse newClientResponse) {
        this.newClientResponse = newClientResponse;
    }
}
