package ass01.app;

import ass01.core.database.*;
import ass01.core.domain.services.RentalService;
import ass01.core.domain.services.RentalServiceClient;
import ass01.core.presentation.*;

public class RunApp {

    public static final int PORT = 1234;
    public static void main(String[] args) {
        // database layer
        DataStorage storage = new HashMapStorage();
        // domain layer
        RentalService service = new RentalServiceClient(PORT, storage);
        // presentation layer
        AppView view = new AppView(service);

        // setup plugins
        view.addPlugin("AddBikePlugin", "Add Bike");
        view.addPlugin("AddUserPlugin", "Add User");
        view.addPlugin("StartRidePlugin", "Start Ride");
        view.addPlugin("EndRidePlugin", "End Ride");

        view.display();
    }

}
