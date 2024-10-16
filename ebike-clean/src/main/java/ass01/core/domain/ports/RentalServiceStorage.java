package ass01.core.domain.ports;

import ass01.core.domain.services.RentalServiceState;

/**
 * Database definition for the rental service
 */
public interface RentalServiceStorage {
    void saveState(RentalServiceState state);
    RentalServiceState readState();
}
