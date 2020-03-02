package frontend;

import models.Menu;
import requests.GetMenuRequest;
import requests.Request;
import responses.MenuResponse;
import responses.Response;
import responses.ResponseType;

public class FrontendRemoteImpl implements FrontendRemote {

    public Response makeRequest(Request request) {

        System.out.println("Request received with following type: " + request.getType());

        switch (request.getType()) {

            case GET_MENU:
                GetMenuRequest getMenuRequest = (GetMenuRequest) request;
                return getMenuResponse(getMenuRequest);
        }

        return new Response(ResponseType.ERROR);
    }

    private MenuResponse getMenuResponse(GetMenuRequest getMenuRequest) {

        return new MenuResponse(Menu.fromSampleData());
    }
}
