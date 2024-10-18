package ass01.core.business.services;

import java.util.List;

/**
 * Interface modeling a rental service for EBikes
 */
public interface RentalService {
    /**
     * Get the internal state of the service. It includes bikes, users and ongoing rides
     * @return
     */
    RentalServiceState getState();

    /**
     * Get all the plugins installed in the service
     * @return
     */
    List<RentalServicePlugin> getPlugins();

    /**
     * Install a new plugin, by giving a JarFile name and a unique id
     * @param id
     * @param pluginJar
     */
    void addPlugin(String id, String pluginJar);

    /**
     * Execute a plugin, called by its unique id and some configurable parameters
     * @param pluginId
     * @param parameters
     */
    void applyPlugin(String pluginId, PluginParameter parameters);
}
