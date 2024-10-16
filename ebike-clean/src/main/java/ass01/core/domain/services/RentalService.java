package ass01.core.domain.services;

import ass01.core.domain.ports.*;

import java.util.List;

/**
 * Interface modeling a rental service for EBikes
 */
public interface RentalService {
    /**
     * Get the current state of the service
     * @return
     */
    RentalServiceState getState();

    /**
     * Get all attached plugins
     * @return
     */
    List<RentalServicePlugin> getPlugins();

    /**
     * Add a plugin, giving the identifier and the JAR file name of the plugin
     * @param id
     * @param pluginJar
     */
    void addPlugin(String id, String pluginJar);

    /**
     * Eecute the action of a given plugin. Parameters can be passed.
     * @param pluginId
     * @param parameters
     */
    void applyPlugin(String pluginId, PluginParameter parameters);
}
