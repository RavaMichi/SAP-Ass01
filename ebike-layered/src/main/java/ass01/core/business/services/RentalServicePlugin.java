package ass01.core.business.services;

/**
 * Plugin for the rental service. It represents an operation over a rental service state.
 */
public interface RentalServicePlugin {
    /**
     * Apply the operation.
     * @param previousState
     *      the rental service state to use for computing the operation
     * @param parameters
     *      the possible parameters for the operation
     * @return
     *      the new computed rental service state
     */
    RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters);

    /**
     * The name of the operation
     * @return
     */
    String operationName();
}
