package backend;

import requests.base.ClientRequest;
import server.BackendResponse;
import server.ServerState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BackendRemote extends Remote {

    BackendResponse makeRequest(ClientRequest request, ServerState currentState, int id) throws RemoteException;
    boolean updateState(StateUpdate stateUpdate) throws RemoteException;
}
