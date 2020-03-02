package frontend;

import requests.Request;
import responses.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontendRemote extends Remote {

    public Response makeRequest(Request request) throws RemoteException;
}
