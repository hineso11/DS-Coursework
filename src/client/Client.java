package client;

import backend.AddressInformation;
import frontend.FrontendRemote;
import frontend.FrontendServer;
import models.MenuItem;
import requests.specific.GetMenuRequest;
import requests.specific.PlaceOrderRequest;
import responses.errors.base.ErrorResponse;
import responses.success.MenuResponse;
import responses.base.ClientResponse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

public class Client {

    public static void runCommandLineProgram(AddressInformation frontendAddress) {

        Registry frontendRegistry;
        FrontendRemote frontendRemote;

        try {

            frontendRegistry = LocateRegistry.getRegistry(frontendAddress.getAddress(), frontendAddress.getPort());
            frontendRemote = (FrontendRemote) frontendRegistry.lookup(FrontendServer.REGISTRY_NAME);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error: Unable to connect to frontend registry");
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean userHasQuit = false;

        System.out.println("Welcome to JustHungry");

        while (!userHasQuit) {

            System.out.println("\nSelect one of the following options by entering the corresponding number");
            System.out.println("1. Display menu");
            System.out.println("2. Make an order");
            System.out.println("3. Quit");

            int choiceStr = scanner.nextInt();

            ClientResponse response;

            try {
                switch (choiceStr) {

                    case 1:
                        response = frontendRemote.makeRequest(new GetMenuRequest());
                        handleResponse(response);
                        break;
                    case 2:
                        System.out.println("First enter the items you want from the menu");
                        boolean orderFinished = false;
                        HashMap<Integer, Integer> menuIdsByQuantity = new HashMap<>();
                        while (!orderFinished) {

                            System.out.println("Enter the menu number of the item you wish to order or 0 to quit");
                            int choice = scanner.nextInt();
                            if (choice != 0) {

                                System.out.println("Enter the quantity of this item: ");
                                int quantity = scanner.nextInt();
                                menuIdsByQuantity.put(choice, quantity);
                            } else {
                                orderFinished = true;
                                scanner.nextLine();
                            }
                        }

                        System.out.print("Enter your name for the order: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your postcode for the order: ");
                        String postcode = scanner.nextLine();

                        response = frontendRemote.makeRequest(new PlaceOrderRequest(menuIdsByQuantity,
                                name, postcode));
                        handleResponse(response);
                    case 3:
                        userHasQuit = true;
                        break;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    private static void handleResponse(ClientResponse clientResponse) {

        switch (clientResponse.getType()) {

            case ERROR:
                ErrorResponse errorResponse = (ErrorResponse) clientResponse;
                handleError(errorResponse);
                break;
            case MENU:
                MenuResponse menuResponse = (MenuResponse) clientResponse;
                System.out.println("The menu is as follows, with item number in brackets\n");
                for (MenuItem item : menuResponse.getMenu().getMenuItems()) {

                    System.out.println(String.format("(%d) %s- £%.2f", item.getId(), item.getName(), item.getPrice()));
                }
                break;
        }
    }

    private static void handleError(ErrorResponse errorResponse) {

        System.out.println("Error: " + errorResponse.getReason());
        System.out.println(errorResponse.getMessage());
    }
}
