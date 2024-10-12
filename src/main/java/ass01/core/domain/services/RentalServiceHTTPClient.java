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

    private final WebClient client;
    private final int port;
    private final String address = "localhost";
    public RentalServiceHTTPClient(final int port, DataStorage storage) {
        this.port = port;
        Vertx vertx = Vertx.vertx();
        // client
        this.client = WebClient.create(vertx);
        // start server if down
        new TOREMOVEHTTPServer(port, storage);
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
        client
                .post(port, address, "/")
                .sendJsonObject(JsonObject.of("type", "start-ride", "userId", userId, "bikeId", bikeId));
    }

    @Override
    public void endRide(String userId, String bikeId) {
        client
                .post(port, address, "/")
                .sendJsonObject(JsonObject.of("type", "end-ride", "userId", userId, "bikeId", bikeId));
    }
}
