package ass01.app;

import ass01.core.adapters.*;
import ass01.core.domain.ports.*;
import ass01.core.domain.services.*;

public class RunApp {
    public static final int PORT = 1234;
    public static void main(String[] args) {
        // database layer
        RentalServiceStorage storage = new HashMapStorage();
        RideSimulator simulator = RideSimulationImpl::new;
        // create server
        RentalService server = new RentalServiceServer(PORT, storage, simulator);

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
