package ass01.core.business.services;

import java.util.List;

/**
 * Interface modeling a rental service for EBikes
 */
public interface RentalService {
    RentalServiceState getState();
    List<RentalServicePlugin> getPlugins();
    void addPlugin(String id, String pluginJar);
    void applyPlugin(String pluginId, PluginParameter parameters);
}
