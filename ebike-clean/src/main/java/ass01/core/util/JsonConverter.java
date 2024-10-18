package ass01.core.util;

import ass01.core.business.entities.*;
import ass01.core.business.ports.PluginParameter;
import ass01.core.business.ports.RentalServicePlugin;
import ass01.core.business.services.RentalServiceState;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Optional;

public class JsonConverter {

    public static JsonObject encode(User user) {
        return new JsonObject()
                .put("id", user.getId())
                .put("credit", user.getCredit());
    }
    public static JsonObject encode(P2d point) {
        return new JsonObject()
                .put("x", point.x())
                .put("y", point.y());
    }
    public static JsonObject encode(V2d point) {
        return new JsonObject()
                .put("x", point.x())
                .put("y", point.y());
    }
    public static JsonObject encode(EBike bike) {
        return new JsonObject()
                .put("id", bike.getId())
                .put("loc", encode(bike.getLocation()))
                .put("dir", encode(bike.getDirection()))
                .put("speed", bike.getSpeed())
                .put("state", bike.getState().ordinal())
                .put("battery", bike.getBatteryLevel());
    }
    public static JsonObject encode(Ride ride) {
        return new JsonObject()
                .put("id", ride.getId())
                .put("bike", encode(ride.getEBike()))
                .put("user", encode(ride.getUser()))
//                .put("startedDate", ride.getStartedDate())
//                .put("endDate", ride.getEndDate())
                .put("ongoing", ride.isOngoing());
    }
    public static JsonObject encode(RentalServiceState state) {
        JsonArray users = new JsonArray();
        JsonArray bikes = new JsonArray();
        JsonArray rides = new JsonArray();
        state.users().forEach(u -> users.add(JsonConverter.encode(u)));
        state.bikes().forEach(b -> bikes.add(JsonConverter.encode(b)));
        state.rides().forEach(r -> rides.add(JsonConverter.encode(r)));
        return new JsonObject()
                .put("users", users)
                .put("bikes", bikes)
                .put("rides", rides);
    }
    public static JsonObject encode(PluginParameter param) {
        return new JsonObject()
                .put("userId", param.userId())
                .put("bikeId", param.bikeId());
    }
    public static JsonObject encode(RentalServicePlugin plugin) {
        return new JsonObject().put("operationName", plugin.operationName());
    }

    public static User asUser(JsonObject json) {
        return new User(json.getString("id"), json.getInteger("credit"));
    }
    public static P2d asP2d(JsonObject json) {
        return new P2d(json.getDouble("x"), json.getDouble("y"));
    }
    public static V2d asV2d(JsonObject json) {
        return new V2d(json.getDouble("x"), json.getDouble("y"));
    }
    public static EBike asEBike(JsonObject json) {
        EBike bike = new EBike(json.getString("id"));
        bike.updateLocation(asP2d(json.getJsonObject("loc")));
        bike.updateDirection(asV2d(json.getJsonObject("dir")));
        bike.updateSpeed(json.getDouble("speed"));
        bike.setBatteryLevel(json.getInteger("battery"));
        bike.updateState(EBike.EBikeState.values()[json.getInteger("state")]);
        return bike;
    }
    public static Ride asRide(JsonObject json) {
        return new Ride(
                json.getString("id"),
                JsonConverter.asUser(json.getJsonObject("user")),
                JsonConverter.asEBike(json.getJsonObject("bike")),
                null,
                Optional.empty(),
                json.getBoolean("ongoing")
        );
    }
    public static RentalServiceState asRentalServiceState(JsonObject json) {
        return new RentalServiceState(
                json.getJsonArray("users").stream().map(o -> JsonConverter.asUser(JsonObject.mapFrom(o))).toList(),
                json.getJsonArray("bikes").stream().map(o -> JsonConverter.asEBike(JsonObject.mapFrom(o))).toList(),
                json.getJsonArray("rides").stream().map(o -> JsonConverter.asRide(JsonObject.mapFrom(o))).toList(),
                null
        );
    }
    public static PluginParameter asPluginParameter(JsonObject json) {
        return new PluginParameter(json.getString("userId"), json.getString("bikeId"));
    }
    public static RentalServicePlugin asPlugin(JsonObject json) {
        return new RentalServicePlugin() {
            @Override
            public RentalServiceState apply(RentalServiceState previousState, PluginParameter parameters) {
                return null;
            }

            @Override
            public String operationName() {
                return json.getString("operationName");
            }
        };
    }
}
