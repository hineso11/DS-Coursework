package frontend;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FrontendServer extends FrontendRemoteImpl {

    public static void main(String[] args) {
        try {
            FrontendRemoteImpl remoteImpl = new FrontendRemoteImpl();

            FrontendRemote stub = (FrontendRemote) UnicastRemoteObject.exportObject(remoteImpl, 3000);

            Registry registry = LocateRegistry.createRegistry(3000);

            registry.bind("request", stub);
        } catch (RemoteException | AlreadyBoundException e) {

            System.err.println("Frontend Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
