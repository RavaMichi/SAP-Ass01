package ass01.core.persistence;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a general data storage
 */
public interface DataStorage {

    /**
     * Save a given data block, ad associate it with id.
     * If already present, throws an error.
     * @param id
     * @param data
     * @param <T>
     */
    <T> void save(String id, T data);

    /**
     * Updates a given data block by id.
     * If not present, throws an error.
     * @param id
     * @param data
     * @param <T>
     */
    <T> void update(String id, T data);

    /**
     * Deletes a data of given id. If there is no data found, does nothing
     * @param id
     * @param <T>
     */
    <T> void delete(String id);

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
    <T> List<T> findAll(Class<T> type);
}
