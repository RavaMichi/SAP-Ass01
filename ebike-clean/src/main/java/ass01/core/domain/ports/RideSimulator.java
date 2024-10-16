package ass01.core.domain.ports;

import ass01.core.domain.entities.Ride;

public interface RideSimulator {
    RideSimulation createSimulation(Ride ride);
}
