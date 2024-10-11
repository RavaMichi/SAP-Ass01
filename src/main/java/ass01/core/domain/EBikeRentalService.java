package ass01.core.domain;

import ass01.core.domain.EBike;
import ass01.core.domain.P2d;
import ass01.core.domain.User;

import java.util.List;

/**
 * Interface modeling a rental service for EBikes
 */
public interface EBikeRentalService {
    void addEBike(String id, P2d position);
    void addUser(String id);
    List<User> getUsers();
    List<EBike> getEBikes();

    void startNewRide(String userId, String bikeId);
    void endRide(String userId, String bikeId);
}
