package ass01.core.adapters;

import ass01.core.business.services.RentalServiceState;
import ass01.core.business.ports.RentalServiceStorage;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Implementation of storage for the rental service.
 * Uses a local volatile memory to store the state.
 */
public class HashMapStorage implements RentalServiceStorage {
    private RentalServiceState state = new RentalServiceState(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    @Override
    public void saveState(RentalServiceState state) {
        this.state = new RentalServiceState(
                state.users(),
                state.bikes(),
                state.rides(),
                state.simulator()
        );
    }

    @Override
    public Optional<RentalServiceState> readState() {
        return Optional.ofNullable(state);
    }
}
