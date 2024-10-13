package ass01.core.domain.services;

import ass01.core.database.DataStorage;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RentalServiceClient implements RentalService{
    private final WebClient client;
    private final int port;
    private final String address = "localhost";
    public RentalServiceClient(final int port, DataStorage storage) {
        this.port = port;
        Vertx vertx = Vertx.vertx();
        // client
        this.client = WebClient.create(vertx);
        // start server if down
        new RentalServiceServer(port, storage);
    }
    @Override
    public RentalServiceState getState() {
        try {
            return client
                    .get(port, address, "/?query=state")
                    .send()
                    .map(res -> JsonConverter.asRentalServiceState(res.bodyAsJsonObject()))
                    .toCompletionStage().toCompletableFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    @Override
    public List<RentalServicePlugin> getPlugins() {
        try {
            return client
                    .get(port, address, "/?query=plugins")
                    .send()
                    .map(res -> res.bodyAsJsonArray().stream()
                            .map(obj -> {
                                var json = JsonObject.mapFrom(obj);
                                return JsonConverter.asPlugin(json);
                            })
                            .toList())
                    .toCompletionStage().toCompletableFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            return List.of();
        }
    }

    @Override
    public void addPlugin(String id, String pluginJar) {
        // TODO
    }


    @Override
    public void applyPlugin(String pluginId, PluginParameter parameters) {
        client
                .post(port, address, "/")
                .sendJsonObject(JsonObject.of("type", "apply", "plugin", pluginId, "param", JsonConverter.encode(parameters)));
    }
}
