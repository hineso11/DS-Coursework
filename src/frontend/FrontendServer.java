package frontend;

import server.backend.AddressInformation;
import server.backend.BackendRemote;
import server.backend.BackendServer;
import requests.base.ClientRequest;
import responses.base.ClientResponse;
import responses.errors.InternalServerErrorResponse;
import server.BackendResponse;
import server.ServerState;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FrontendServer implements FrontendRemote {

    public static final String REGISTRY_NAME = "request";

    private ServerState serverState;
    private int idCount;

    public FrontendServer(ServerState serverState) throws AlreadyBoundException, RemoteException {

        this.serverState = serverState;
        this.idCount = 0;

        FrontendRemote stub = (FrontendRemote) UnicastRemoteObject.exportObject(this,
                this.serverState.getFrontendAddress().getPort());

        Registry registry = LocateRegistry.createRegistry(this.serverState.getFrontendAddress().getPort());
        registry.bind(REGISTRY_NAME, stub);
    }

    public ClientResponse makeRequest(ClientRequest clientRequest) throws RemoteException {

        AddressInformation primaryAddress = this.getPrimaryBackendServer();

        // Attempt to find a backend server to act as the primary
        while (primaryAddress != null) {

            // Attempt to get the response from this primary
            try {

                Registry backendRegistry = LocateRegistry.getRegistry(primaryAddress.getAddress(),
                        primaryAddress.getPort());

                BackendRemote primaryRemote = (BackendRemote) backendRegistry
                        .lookup(BackendServer.REGISTRY_NAME);

                BackendResponse backendResponse = primaryRemote.makeRequest(clientRequest, this.serverState,
                        this.idCount);

                this.serverState = backendResponse.getUpdatedState();
                this.idCount ++;
                // If we could get a response, then return it
                return backendResponse.getClientResponse();
            } catch (NotBoundException | RemoteException e) {
                // Else remove this backend server from the server state
                this.serverState.getBackendAddresses().remove(primaryAddress);
            }

            primaryAddress = this.getPrimaryBackendServer();
        }

        // there was no primary server to handle the request
        // return some internal server error
        return new InternalServerErrorResponse();
    }

    private AddressInformation getPrimaryBackendServer() {

        if (serverState.getBackendAddresses().size() != 0) {

            return serverState.getBackendAddresses().get(0);
        } else {

            return null;
        }
    }
}
