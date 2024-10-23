package ass01.core.business.services;

import ass01.core.business.util.JsonConverter;
import ass01.core.business.util.PluginClassLoader;
import ass01.core.persistence.DataStorage;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rental service server, for http client service
 */
public class RentalServiceServer implements RentalService {

    private static final String STATE_ID = "state";
    private RentalServiceState state;
    private Map<String, RentalServicePlugin> plugins;
    private DataStorage storage;

    public RentalServiceServer(int port, DataStorage storage) {
        this.storage = storage;
        this.plugins = new HashMap<>();

        // fetch state from storage
        var startingState = storage
                .find(STATE_ID, RentalServiceState.class)
                .orElse(new RentalServiceState(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        this.state = startingState;
        storage.delete(STATE_ID);
        storage.save(STATE_ID, startingState);

        startServer(port);
    }

    private void startServer(int port) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        // make the server
        server.requestHandler(req -> {
            // handle requests
            if (req.method().name().equalsIgnoreCase("GET")) {
                // get state
                if (req.getParam("query").equals("state")) {
                    req.response()
                            .putHeader("content-type", "application/json")
                            .end(JsonConverter.encode(state).encodePrettily());
                }
                // get plugins
                else if (req.getParam("query").equals("plugins")) {
                    JsonArray jsonResponse = new JsonArray();
                    plugins.values().forEach(p -> jsonResponse.add(JsonConverter.encode(p)));
                    req.response()
                            .putHeader("content-type", "application/json")
                            .end(jsonResponse.encodePrettily());
                } else {
                    req.response().setStatusCode(400).end();
                }
                return;

            } else if (req.method().name().equalsIgnoreCase("POST")) {
                req.bodyHandler(body -> {
                    JsonObject entity = body.toJsonObject();
                    String type = entity.getString("type");

                    if ("apply".equals(type)) {
                        String plugin = entity.getString("plugin");
                        PluginParameter param = JsonConverter.asPluginParameter(entity.getJsonObject("param"));

                        applyPlugin(plugin, param);
                        req.response().setStatusCode(200).end();

                    } else if ("add".equals(type)) {
                        String plugin = entity.getString("plugin");
                        String jar = entity.getString("jar");

                        addPlugin(plugin, jar);
                        req.response().setStatusCode(200).end();

                    } else {
                        req.response()
                                .setStatusCode(400) // 400 Bad Request
                                .end("Bad request: use 'ebike', 'user', 'start-ride', 'end-ride'");
                    }
                });
                return;
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

    @Override
    public RentalServiceState getState() {
        return state;
    }

    private void setState(RentalServiceState state) {
        this.state = state;
        // save to db
        storage.update(STATE_ID, state);
    }

    @Override
    public List<RentalServicePlugin> getPlugins() {
        return plugins.values().stream().toList();
    }

    @Override
    public void addPlugin(String id, String pluginJar) {
        try {
            PluginClassLoader loader = new PluginClassLoader("plugins/"+pluginJar+".jar");
            String className = "ass01.plugins." + pluginJar.replaceFirst(".jar","");
            Class<?> pluginClass = loader.loadClass(className);
            RentalServicePlugin rsPlugin = (RentalServicePlugin) pluginClass.getDeclaredConstructor(null).newInstance(null);
            plugins.put(id, rsPlugin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void applyPlugin(String pluginId, PluginParameter parameters) {
        RentalServicePlugin plugin = plugins.get(pluginId);
        if (plugin != null) {
            // compute new state
            setState(plugin.apply(getState(), parameters));
        }
    }
    private static void log(String msg) {
        System.out.println("[RS Server] " + msg);
    }

}
