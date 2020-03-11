package responses.errors;

import responses.errors.base.ErrorResponse;

import java.io.Serializable;

public class InternalServerErrorResponse extends ErrorResponse implements Serializable {

    private static final long serialVersionUID = 8929221331097857690L;

    public InternalServerErrorResponse() {
        super("An internal server error occurred", "Your request could not be completed. " +
                "Please try again later.");
    }
}
