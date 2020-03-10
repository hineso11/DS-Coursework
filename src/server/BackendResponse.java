package server;

import responses.ClientResponse;

public class BackendResponse {

    private ClientResponse clientResponse;
    private ServerState updatedState;

    public BackendResponse(ClientResponse clientResponse, ServerState updatedState) {

        this.clientResponse = clientResponse;
        this.updatedState = updatedState;
    }

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public ServerState getUpdatedState() {
        return updatedState;
    }
}
