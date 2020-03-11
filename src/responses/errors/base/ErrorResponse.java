package responses.errors.base;

import responses.ResponseType;
import responses.base.ClientResponse;

import java.io.Serializable;

public class ErrorResponse extends ClientResponse implements Serializable {

    private static final long serialVersionUID = 8929221281097857690L;

    private String reason;
    private String message;

    public ErrorResponse(String reason, String message) {
        super(ResponseType.ERROR);

        this.reason = reason;
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }
}
