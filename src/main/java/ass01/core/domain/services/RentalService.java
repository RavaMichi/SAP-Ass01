package ass01.core.domain.services;

import ass01.core.domain.entities.EBike;
import ass01.core.domain.entities.P2d;
import ass01.core.domain.entities.User;

import java.util.List;

/**
 * Interface modeling a rental service for EBikes
 */
public interface RentalService {
    RentalServiceState getState();
//    void setState(RentalServiceState state);
    List<RentalServicePlugin> getPlugins();
    void addPlugin(String id, String pluginJar);
    void applyPlugin(String pluginId, PluginParameter parameters);
}
