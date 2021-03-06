package server.backend;

import models.Menu;
import models.MenuItem;
import models.Order;
import requests.base.ClientRequest;
import requests.specific.GetMenuRequest;
import requests.specific.PlaceOrderRequest;
import responses.ResponseType;
import responses.base.ClientResponse;
import responses.errors.base.ErrorResponse;
import responses.success.MenuResponse;
import responses.success.OrderResponse;
import server.BackendResponse;
import server.ServerState;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class BackendServer implements BackendRemote {

    public static final String REGISTRY_NAME = "server/backend";

    private AddressInformation addressInformation;
    private HashMap<Integer, ClientResponse> responseByIdMap;
    private ArrayList<Order> orders;

    public BackendServer(AddressInformation addressInformation) throws AlreadyBoundException, RemoteException {

        this.addressInformation = addressInformation;
        this.responseByIdMap = new HashMap<>();
        this.orders = new ArrayList<>();

        BackendRemote stub = (BackendRemote) UnicastRemoteObject.exportObject(this,
                this.addressInformation.getPort());

        Registry registry = LocateRegistry.createRegistry(this.addressInformation.getPort());
        registry.bind(REGISTRY_NAME, stub);
    }

    public BackendResponse makeRequest(ClientRequest request, ServerState currentState, int id) throws RemoteException {

        // Check if we've seen the request before
        if (responseByIdMap.containsKey(id)) {
            // If we have then return the relevant response
            return new BackendResponse(responseByIdMap.get(id), currentState);
        }

        // If not then, we need to generate the appropriate response and propagate this to the other backend servers
        ClientResponse clientResponse;

        switch (request.getType()) {

            case GET_MENU:
                GetMenuRequest getMenuRequest = (GetMenuRequest) request;
                clientResponse = new MenuResponse(Menu.fromSampleData());
                break;
            case PLACE_ORDER:
                PlaceOrderRequest placeOrderRequest = (PlaceOrderRequest) request;

                // Loop through each of the ids provided in the request and check they exist in the menu items
                Menu menu = Menu.fromSampleData();
                HashMap<MenuItem, Integer> menuItemsByQuantity = new HashMap<>();

                for (int itemId : placeOrderRequest.getMenuIdsByQuantity().keySet()) {
                    MenuItem item = menu.getItemForId(itemId);
                    menuItemsByQuantity.put(item, placeOrderRequest.getMenuIdsByQuantity().get(itemId));
                }

                Order order = new Order(menuItemsByQuantity, placeOrderRequest.getName(), placeOrderRequest.getPostcode());
                clientResponse = new OrderResponse(order);
                break;
            default:
                clientResponse = new ErrorResponse("Invalid Request type", "The specified request " +
                        "type does not exist on this server");
                break;
        }

        // We now have a newly generated response for the given id
        // Store the response in our record
        responseByIdMap.put(id, clientResponse);

        // If this is an order response, then we need to store the result and include it in the state update
        Order order = null;

        if (clientResponse.getType() == ResponseType.ORDER) {

            OrderResponse orderResponse = (OrderResponse) clientResponse;
            order = orderResponse.getOrder();
        }

        // Propagate the state update to the other backend servers
        StateUpdate stateUpdate = new StateUpdate(id, clientResponse, order);

        ServerState updatedServerState = this.propagateStateUpdate(stateUpdate, currentState);

        return new BackendResponse(clientResponse, updatedServerState);
    }

    private ServerState propagateStateUpdate(StateUpdate stateUpdate, ServerState currentState) {

        ArrayList<AddressInformation> updatedBackendAddresses = new ArrayList<>();
        // The updated state obviously has the current backend server in it
        updatedBackendAddresses.add(this.addressInformation);

        // Loop through each of the backend servers
        for (AddressInformation backendAddress: currentState.getBackendAddresses()) {

            // As long as it's not the current server
            if (!backendAddress.equals(this.addressInformation)) {
                // Attempt to propagate the state to each backend server
                try {
                    Registry slaveRegistry = LocateRegistry.getRegistry(backendAddress.getAddress(),
                            backendAddress.getPort());
                    BackendRemote slaveRemote = (BackendRemote) slaveRegistry.lookup(BackendServer.REGISTRY_NAME);

                    boolean propagationResult = slaveRemote.updateState(stateUpdate);

                    // If the propagation was successful, then add the address to the list of updated addresses
                    if (propagationResult) {

                        updatedBackendAddresses.add(backendAddress);
                    }

                } catch (RemoteException | NotBoundException e) {
                    System.err.println(String.format("Could not propagate state to backend server at %s:%d",
                            backendAddress.getAddress(), backendAddress.getPort()));
                    e.printStackTrace();
                }
            }
        }

        return new ServerState(currentState.getFrontendAddress(), updatedBackendAddresses);
    }

    public boolean updateState(StateUpdate stateUpdate) {

        // Update the response record
        responseByIdMap.put(stateUpdate.getResponseId(), stateUpdate.getNewClientResponse());

        // If there is a new order, then add this to the orders list
        if (stateUpdate.getNewOrder() != null) {
            orders.add(stateUpdate.getNewOrder());
        }

        // Send the acknowledgement
        return true;
    }
}
