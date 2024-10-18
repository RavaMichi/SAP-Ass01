package ass01.plugins;

import ass01.core.business.services.PluginParameter;
import ass01.core.business.services.RentalServicePlugin;
import ass01.core.business.services.RentalServiceState;

public class EndRidePlugin implements RentalServicePlugin {
    @Override
    public RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters) {
        previousState.rides().stream()
                .filter(r -> r.getId().equals(parameters.userId() + "+" + parameters.bikeId()))
                .findFirst()
                .ifPresent(ride -> {
                    previousState.rides().remove(ride);
                    ride.end();
                });
        return previousState;
    }

    @Override
    public String operationName() {
        return "End Ride";
    }
}
