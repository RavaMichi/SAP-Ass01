package ass01.domain;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class EBikeRentalServiceHTTPServer {

    public EBikeRentalServiceHTTPServer(final int port) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        // make the server
        server.requestHandler(req -> {
            // handle requests
            if (req.method().name().equalsIgnoreCase("GET")) {
                JsonArray jsonResponse = new JsonArray();
                String requested = req.getParam("query");

                // get all ebikes
                if (requested.equals("ebikes")) {
                    getEBikes().forEach(jsonResponse::add);
                }
                // get all users
                if (requested.equals("users")) {
                    getUsers().forEach(jsonResponse::add);
                }

                req.response()
                        .putHeader("content-type", "application/json")
                        .end(jsonResponse.encodePrettily());

            } else if (req.method().name().equalsIgnoreCase("POST")) {
                req.bodyHandler(body -> {
                    JsonObject entity = body.toJsonObject();
                    String type = entity.getString("type");

                    // add ebike
                    if ("ebike".equalsIgnoreCase(type)) {
                        String bikeId = entity.getString("id");
                        P2d position = entity.getJsonObject("pos").mapTo(P2d.class);
                        try {
                            addEBike(bikeId, position);
                            req.response()
                                    .setStatusCode(201)
                                    .end("EBike created");
                        } catch (Exception e) {
                            req.response()
                                    .setStatusCode(409)
                                    .end("EBike with id="+bikeId+" already exists");
                        }
                    }
                    // add user
                    else if ("user".equalsIgnoreCase(type)) {
                        String userId = entity.getString("id");
                        try {
                            addUser(userId);
                            req.response()
                                    .setStatusCode(201)
                                    .end("User created");
                        } catch (Exception e) {
                            req.response()
                                    .setStatusCode(409)
                                    .end("User with id="+userId+" already exists");
                        }
                    }
                    // start ride
                    else if ("start-ride".equalsIgnoreCase(type)) {
                        String userId = entity.getString("userId");
                        String bikeId = entity.getString("bikeId");
                        try {
                            startNewRide(userId, bikeId);
                            req.response()
                                    .setStatusCode(200)
                                    .end("User on a ride");
                        } catch (Exception e) {
                            req.response()
                                    .setStatusCode(409)
                                    .end("User already on a ride");
                        }
                    }
                    // end ride
                    else if ("end-ride".equalsIgnoreCase(type)) {
                        String userId = entity.getString("userId");
                        String bikeId = entity.getString("bikeId");
                        try {
                            endRide(userId, bikeId);
                            req.response()
                                    .setStatusCode(200)
                                    .end("End of the ride");
                        } catch (Exception e) {
                            req.response()
                                    .setStatusCode(409)
                                    .end("Ride does not exists");
                        }
                    }
                    else {
                        req.response()
                                .setStatusCode(400) // 400 Bad Request
                                .end("Bad request: use 'ebike', 'user', 'start-ride', 'end-ride'");
                    }
                });
            }

            req.response().setStatusCode(405).end("Wrong Command.");
        });

        // start
        server.listen(port, http -> {
            if (http.succeeded()) {
                log("HTTP server started on port " + port);
            } else {
                log("Failed to start HTTP server on port " + port + ": " + http.cause().getMessage());
            }
        });
    }
    private void addEBike(String id, P2d position) {

    }

    private void addUser(String id) {

    }

    private List<User> getUsers() {
        return null;
    }

    private List<EBike> getEBikes() {
        return null;
    }

    private void startNewRide(String userId, String bikeId) {

    }

    private void endRide(String userId, String bikeId) {

    }
    private static void log(String msg) {
        System.out.println("[RS Server] " + msg);
    }
}
