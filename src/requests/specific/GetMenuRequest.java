package requests.specific;

import requests.RequestType;
import requests.base.ClientRequest;

import java.io.Serializable;

public class GetMenuRequest extends ClientRequest implements Serializable {

    private static final long serialVersionUID = 6529681234567857690L;

    public GetMenuRequest() {
        super(RequestType.GET_MENU);
    }
}
