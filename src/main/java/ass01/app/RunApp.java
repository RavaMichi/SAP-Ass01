package ass01.app;

import ass01.core.database.*;
import ass01.core.domain.*;
import ass01.core.presentation.*;

public class RunApp {

    public static final int PORT = 1234;
    public static void main(String[] args) {
        // database layer
        DataStorage storage = new HashMapStorage();
        // domain layer
        EBikeRentalService service = new EBikeRentalHTTPClient(PORT, storage);
        setupDomain(service);
        // presentation layer
        AppView view = new AppView(service);

        view.display();
    }
    private static void setupDomain(EBikeRentalService service) {
        service.addUser("u1");
        service.addEBike("b1", new P2d(0,0));
    }
}
