package server;

import backend.AddressInformation;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerState implements Serializable {

    private static final long serialVersionUID = 6529567838402857690L;

    private AddressInformation frontendAddress;
    private ArrayList<AddressInformation> backendAddresses;

    public ServerState(AddressInformation frontendAddress, ArrayList<AddressInformation> backendAddresses) {

        this.frontendAddress = frontendAddress;
        this.backendAddresses = backendAddresses;
    }

    public AddressInformation getFrontendAddress() {
        return frontendAddress;
    }

    public ArrayList<AddressInformation> getBackendAddresses() {
        return backendAddresses;
    }
}
