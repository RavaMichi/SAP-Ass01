package ass01.core.business.ports;

import ass01.core.business.services.RentalServiceState;

import java.util.Optional;

/**
 * Database definition for the rental service
 */
public interface RentalServiceStorage {
    void saveState(RentalServiceState state);
    Optional<RentalServiceState> readState();
}
