package frontend;

import requests.base.ClientRequest;
import responses.base.ClientResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontendRemote extends Remote {

    ClientResponse makeRequest(ClientRequest clientRequest) throws RemoteException;
}
