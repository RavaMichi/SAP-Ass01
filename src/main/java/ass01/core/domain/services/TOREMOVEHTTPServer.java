package ass01.core.domain.services;

import ass01.core.database.DataStorage;
import ass01.core.domain.entities.EBike;
import ass01.core.domain.entities.P2d;
import ass01.core.domain.entities.Ride;
import ass01.core.domain.entities.User;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Rental service server, for http client service
 */
public class TOREMOVEHTTPServer implements RentalService {


    @Override
    public RentalServiceState getState() {
        return null;
    }

    @Override
    public List<RentalServicePlugin> getPlugins() {
        return null;
    }

    @Override
    public void addPlugin(String id, RentalServicePlugin plugin) {

    }

    @Override
    public void applyPlugin(String pluginId, PluginParameter parameters) {

    }
}
