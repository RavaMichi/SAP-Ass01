package ass01.core.domain;

import ass01.core.database.DataStorage;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EBikeRentalHTTPClient implements EBikeRentalService {

    private final WebClient client;
    private final int port;
    private final String address = "localhost";
    public EBikeRentalHTTPClient(final int port, DataStorage storage) {
        this.port = port;
        Vertx vertx = Vertx.vertx();
        // client
        this.client = WebClient.create(vertx);
        // start server if down
        new EBikeRentalHTTPServer(port, storage);
    }
    @Override
    public void addEBike(String id, P2d position) {
        client
                .post(port, address, "/")
                .sendJsonObject(JsonObject.of("type", "ebike", "id", id, "pos", JsonConverter.encode(position)));
    }

    @Override
    public void addUser(String id) {
        client
                .post(port, address, "/")
                .sendJsonObject(JsonObject.of("type", "user", "id", id));
    }

    @Override
    public List<User> getUsers() {
        try {
            return client
                    .get(port, address, "/?query=users")
                    .send()
                    .map(res -> res.bodyAsJsonArray().stream()
                            .map(obj -> {
                                var json = JsonObject.mapFrom(obj);
                                return JsonConverter.asUser(json);
                            })
                            .toList())
                    .toCompletionStage().toCompletableFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<EBike> getEBikes() {
        try {
            return client
                    .get(port, address, "/?query=ebikes")
                    .send()
                    .map(res -> res.bodyAsJsonArray().stream()
                            .map(obj -> {
                                var json = JsonObject.mapFrom(obj);
                                return JsonConverter.asEBike(json);
                            })
                            .toList())
                    .toCompletionStage().toCompletableFuture().get();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void startNewRide(String userId, String bikeId) {

    }

    @Override
    public void endRide(String userId, String bikeId) {

    }
}
