package ass01.database;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Data storage implementation based on HashMaps.
 * Its memory is persistent only during the application execution.
 */
public class HashMapStorage implements DataStorage {

    private final Map<String, Object> hashmap = new HashMap<>();

    @Override
    public <T> void save(String id, T data) {
        hashmap.put(id, data);
    }

    @Override
    public <T> Optional<T> find(String id, Class<T> type) {
        return (Optional<T>) Optional.ofNullable(hashmap.get(id)).filter(o -> o.getClass().equals(type));
    }

}
