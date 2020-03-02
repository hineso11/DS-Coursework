package requests;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 6529681234267757690L;

    private RequestType type;

    public Request(RequestType type) {

        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

}
