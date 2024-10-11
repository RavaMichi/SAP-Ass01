package ass01.core.database;

import java.util.HashMap;
import java.util.List;
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
        if (hashmap.containsKey(id)) throw new IllegalArgumentException();
        hashmap.put(id, data);
    }

    @Override
    public <T> void update(String id, T data) {
        if (!hashmap.containsKey(id)) throw new IllegalArgumentException();
        hashmap.put(id, data);
    }

    @Override
    public <T> void delete(String id) {
        hashmap.remove(id);
    }

    @Override
    public <T> Optional<T> find(String id, Class<T> type) {
        return (Optional<T>) Optional.ofNullable(hashmap.get(id)).filter(o -> o.getClass().equals(type));
    }

    @Override
    public <T> List<T> findAll(Class<T> type) {
        return (List<T>) hashmap.values().stream().filter(o -> o.getClass().equals(type)).toList();
    }

}
