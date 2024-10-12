package org.example;

import ass01.core.database.DataStorage;
import ass01.core.database.HashMapStorage;
import ass01.core.domain.services.PluginParameter;
import ass01.core.domain.services.RentalServiceHTTPClient;
import ass01.core.domain.services.RentalService;
import ass01.core.domain.services.RentalServiceServer;
import ass01.plugins.AddUserPlugin;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DataStorage s = new HashMapStorage();
        RentalService c = new RentalServiceServer(222, s);
        c.addPlugin("add-u", new AddUserPlugin());

        System.out.println(c.getState().users());

        c.applyPlugin("add-u", new PluginParameter("bob", null));

        System.out.println(c.getState().users());
        System.exit(0);
    }
}