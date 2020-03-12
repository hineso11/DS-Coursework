package server.backend;

import java.io.Serializable;

public class AddressInformation implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInformation that = (AddressInformation) o;
        return port == that.port &&
                address.equals(that.address);
    }
}
