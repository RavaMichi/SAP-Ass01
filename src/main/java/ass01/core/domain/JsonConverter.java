package ass01.core.domain;

import io.vertx.core.json.JsonObject;

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
}
