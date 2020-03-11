package responses.base;

import responses.ResponseType;

import java.io.Serializable;

public class ClientResponse implements Serializable {

    private static final long serialVersionUID = 6529221234567857690L;

    private ResponseType type;

    public ClientResponse(ResponseType type) {

        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }
}
