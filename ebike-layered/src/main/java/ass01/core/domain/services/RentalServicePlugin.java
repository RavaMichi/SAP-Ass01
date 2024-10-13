package ass01.core.domain.services;

public interface RentalServicePlugin {
    RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters);
    String operationName();
}
