package ass01.core.adapters;

import ass01.core.domain.services.RentalServiceState;
import ass01.core.domain.ports.RentalServiceStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of storage for the rental service.
 * Uses a local volatile memory to store the state.
 */
public class HashMapStorage implements RentalServiceStorage {
    private RentalServiceState state = new RentalServiceState(List.of(), List.of(), List.of(), null);
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
    public RentalServiceState readState() {
        return state;
    }
}
