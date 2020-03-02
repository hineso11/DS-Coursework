package client;

import frontend.FrontendRemote;
import requests.GetMenuRequest;
import responses.MenuResponse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Registry frontendRegistry;
        FrontendRemote frontendRemote;

        try {

            frontendRegistry = LocateRegistry.getRegistry(3000);
            frontendRemote = (FrontendRemote) frontendRegistry.lookup("request");
//
//            stub.makeRequest(new GetMenuRequest());

        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean userHasQuit = false;

        System.out.println("Welcome to JustHungry");

        while (!userHasQuit) {

            System.out.println("Select one of the following options by entering the corresponding number");
            System.out.println("1. Display menu");
            System.out.println("2. Quit");

            int choiceStr = scanner.nextInt();
            try {
                switch (choiceStr) {

                    case 1:

                        MenuResponse response = (MenuResponse) frontendRemote.makeRequest(new GetMenuRequest());
                        System.out.println(response.getMenu().getMenuItems().size());
                        break;
                    case 2:
                        userHasQuit = true;
                        break;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }


    }
}
