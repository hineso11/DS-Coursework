package responses;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 6529221234567857690L;

    private ResponseType type;

    public Response(ResponseType type) {

        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }
}
