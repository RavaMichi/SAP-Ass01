package ass01.plugins;

import ass01.core.business.entities.EBike;
import ass01.core.business.services.*;
import ass01.core.business.ports.*;

public class AddBikePlugin implements RentalServicePlugin {
    @Override
    public RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters) {
        EBike bike = new EBike(parameters.bikeId());
        previousState.bikes().add(bike);
        return previousState;
    }

    @Override
    public String operationName() {
        return "Add EBike";
    }
}
