package ass01.plugins;

import ass01.core.domain.entities.EBike;
import ass01.core.domain.entities.Ride;
import ass01.core.domain.services.*;
import ass01.core.domain.ports.*;

public class StartRidePlugin implements RentalServicePlugin {
    @Override
    public RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters) {
        var user = previousState.users().stream().filter(u -> u.getId().equals(parameters.userId())).findFirst();
        var bike = previousState.bikes().stream().filter(b -> b.getId().equals(parameters.bikeId())).findFirst();
        if (bike.isPresent() && user.isPresent()) {
            var ride = new Ride(user.get().getId() + "+" + bike.get().getId(), user.get(), bike.get());
            bike.get().updateState(EBike.EBikeState.IN_USE);

            // start simulation
            ride.start(previousState.simulator());

            previousState.rides().add(ride);
        }
        return previousState;
    }

    @Override
    public String operationName() {
        return "Start Ride";
    }
}
