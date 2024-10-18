package ass01.core.business.services;

import ass01.core.business.entities.*;
import ass01.core.business.ports.RideSimulator;

import java.util.List;

public record RentalServiceState(List<User> users, List<EBike> bikes, List<Ride> rides, RideSimulator simulator) {}
