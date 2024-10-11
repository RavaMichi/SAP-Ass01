package ass01.core.database;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface for a general data storage
 */
public interface DataStorage {

    /**
     * Save a given data block, ad associate it with id.
     * @param id
     * @param data
     * @param <T>
     */
    <T> void save(String id, T data);

    /**
     * Get the data linked to the id. Returns an empty optional if there is no data.
     * @param id
     * @return
     * @param <T>
     */
    <T> Optional<T> find(String id, Class<T> type);

    /**
     * Get all the data of a given class
     * @param type
     * @return
     * @param <T>
     */
    <T> Collection<T> findAll(Class<T> type);
}
