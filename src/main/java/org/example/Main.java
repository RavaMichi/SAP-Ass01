package org.example;

import ass01.core.database.DataStorage;
import ass01.core.database.HashMapStorage;
import ass01.core.domain.EBikeRentalHTTPClient;
import ass01.core.domain.EBikeRentalService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DataStorage s = new HashMapStorage();
        EBikeRentalService c = new EBikeRentalHTTPClient(222, s);

        System.out.println(c.getUsers());
    }
}