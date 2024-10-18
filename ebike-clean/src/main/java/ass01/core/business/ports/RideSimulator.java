package ass01.core.business.ports;

import ass01.core.business.entities.Ride;

public interface RideSimulator {
    RideSimulation createSimulation(Ride ride);
}
