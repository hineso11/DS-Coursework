package backend;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class AddressInformation {

    private static final long serialVersionUID = 6529592138402857690L;

    private String address;
    private int port;

    public AddressInformation(String address, int port) {

        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean propagateStateUpdate(StateUpdate stateUpdate) {

        try {

            Registry registry = LocateRegistry.getRegistry(this.address, this.port);
            BackendServer stub = (BackendServer) registry.lookup(BackendServer.REGISTRY_NAME);

            return stub.updateState(stateUpdate);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Unable to propagate state to slave at: " + this.address + ":" + this.port);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInformation that = (AddressInformation) o;
        return port == that.port &&
                address.equals(that.address);
    }
}
