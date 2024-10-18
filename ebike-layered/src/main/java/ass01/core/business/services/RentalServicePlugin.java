package ass01.core.business.services;

public interface RentalServicePlugin {
    RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters);
    String operationName();
}
