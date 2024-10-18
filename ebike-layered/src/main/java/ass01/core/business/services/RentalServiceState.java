package ass01.core.business.services;

import ass01.core.business.entities.*;

import java.util.List;

public record RentalServiceState(List<User> users, List<EBike> bikes, List<Ride> rides) {}
