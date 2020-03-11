import backend.AddressInformation;
import backend.BackendServer;
import client.Client;
import frontend.FrontendServer;
import server.ServerState;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Main {

    private static int NUMBER_BACKEND_SERVERS = 3;
    private static int STARTING_BACKEND_PORT = 3000;

    public static void main(String[] args) {

        System.out.println("Starting up the distributed system...\n");

        System.out.println(String.format("Attempting to startup %d backend servers", NUMBER_BACKEND_SERVERS));

        ArrayList<AddressInformation> backendAddresses = new ArrayList<>();

        for (int i = 0; i < NUMBER_BACKEND_SERVERS; i++) {

            AddressInformation backendAddress = new AddressInformation("127.0.0.1", STARTING_BACKEND_PORT + i);

            try {
                new BackendServer(backendAddress);
                backendAddresses.add(backendAddress);
                System.out.println(String.format("Successfully set up backend server at %s:%d",
                        backendAddress.getAddress(), backendAddress.getPort()));
            } catch (RemoteException | AlreadyBoundException e) {
                System.err.println(String.format("Unable to start up backend server at %s:%d",
                        backendAddress.getAddress(), backendAddress.getPort()));
                e.printStackTrace();
            }
        }

        System.out.println(String.format("Successfully set up %d backend servers\n", backendAddresses.size()));
        System.out.println("Attempting to set up frontend server");

        AddressInformation frontendAddress = new AddressInformation("127.0.0.1", STARTING_BACKEND_PORT +
                NUMBER_BACKEND_SERVERS);

        try {
            ServerState serverState = new ServerState(frontendAddress, backendAddresses);
            new FrontendServer(serverState);
            System.out.println(String.format("Successfully set up frontend server at %s:%d\n",
                    frontendAddress.getAddress(), frontendAddress.getPort()));
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println(String.format("Unable to start up frontend server at %s:%d",
                    frontendAddress.getAddress(), frontendAddress.getPort()));
            e.printStackTrace();
            return;
        }

        System.out.println("Attempting to start client program...");
        System.out.println();

        Client.runCommandLineProgram(frontendAddress);
    }
}
