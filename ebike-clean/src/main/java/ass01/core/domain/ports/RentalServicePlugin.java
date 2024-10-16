package ass01.core.domain.ports;

import ass01.core.domain.services.RentalServiceState;

/**
 * Plugin for the rental service.
 */
public interface RentalServicePlugin {
    RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters);
    String operationName();
}
