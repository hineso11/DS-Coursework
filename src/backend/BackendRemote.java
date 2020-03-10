package backend;

import requests.ClientRequest;
import responses.ClientResponse;
import server.BackendResponse;
import server.ServerState;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface BackendRemote extends Remote {

    public BackendResponse makeRequest(ClientRequest request, ServerState currentState, int id) throws RemoteException;
    public boolean updateState(StateUpdate stateUpdate) throws RemoteException;
}
