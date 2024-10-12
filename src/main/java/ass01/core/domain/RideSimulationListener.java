package ass01.core.domain;

/**
 * Interface modeling a listener for the ride simulation.
 * The listener gets updated every time the riding information gets modified while ongoing.
 */
public interface RideSimulationListener {
    void update(Ride ride);
}
