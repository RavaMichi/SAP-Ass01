package ass01.app;

import ass01.core.database.*;
import ass01.core.domain.entities.P2d;
import ass01.core.domain.services.RentalService;
import ass01.core.domain.services.RentalServiceHTTPClient;
import ass01.core.domain.services.RentalServiceServer;
import ass01.core.presentation.*;
import ass01.plugins.AddBikePlugin;
import ass01.plugins.AddUserPlugin;
import ass01.plugins.EndRidePlugin;
import ass01.plugins.StartRidePlugin;

public class RunApp {

    public static final int PORT = 1234;
    public static void main(String[] args) {
        // database layer
        DataStorage storage = new HashMapStorage();
        // domain layer
        RentalService service = new RentalServiceServer(PORT, storage);
        // presentation layer
        AppView view = new AppView(service);

        // setup plugins
        view.addPlugin(new AddUserPlugin());
        view.addPlugin(new AddBikePlugin());
        view.addPlugin(new StartRidePlugin());
        view.addPlugin(new EndRidePlugin());

        view.display();
    }
    private static void setupDomain(RentalService service) {
//        service.addUser("u1");
//        service.addEBike("b1", new P2d(0,0));
    }
}
