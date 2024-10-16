package ass01.app;

import ass01.core.database.*;
import ass01.core.domain.services.RentalService;
import ass01.core.domain.services.RentalServiceClient;
import ass01.core.domain.services.RentalServiceServer;
import ass01.core.presentation.*;

public class RunApp {

    public static final int PORT = 1234;
    public static void main(String[] args) {
        // database layer
        DataStorage storage = new HashMapStorage();
        // create server
        RentalService server = new RentalServiceServer(PORT, storage);

        // create client
        RentalService client = new RentalServiceClient(PORT);
        // presentation layer
        AppView view = new AppView(client);

        // setup plugins
        view.addPlugin("AddBikePlugin", "Add Bike");
        view.addPlugin("AddUserPlugin", "Add User");
        view.addPlugin("StartRidePlugin", "Start Ride");
        view.addPlugin("EndRidePlugin", "End Ride");

        view.display();
    }

}
