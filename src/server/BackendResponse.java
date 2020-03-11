package server;

import responses.base.ClientResponse;

import java.io.Serializable;

public class BackendResponse implements Serializable {

    private static final long serialVersionUID = 8929221234558327690L;

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
