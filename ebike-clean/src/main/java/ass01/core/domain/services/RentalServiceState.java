package ass01.core.domain.services;

import ass01.core.domain.entities.*;

import java.util.List;

public record RentalServiceState(List<User> users, List<EBike> bikes, List<Ride> rides) {}