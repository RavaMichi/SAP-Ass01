package ass01.core.domain.services;

import ass01.core.database.DataStorage;
import ass01.core.domain.entities.EBike;
import ass01.core.domain.entities.P2d;
import ass01.core.domain.entities.User;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

import java.util.List;

public class RentalServiceHTTPClient implements RentalService {

    public RentalServiceHTTPClient(int port, DataStorage s) {

    }
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
