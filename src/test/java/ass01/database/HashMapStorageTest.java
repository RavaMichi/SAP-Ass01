package ass01.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class HashMapStorageTest {

    @Test
    public void testSaveAndFind() {
        DataStorage storage = new HashMapStorage();
        storage.save("id-0", 99);
        storage.save("id-1", 3.14);
        storage.save("id-2", "hello world!");

        assertEquals(storage.find("id-0", Integer.class), Optional.of(99));
        assertEquals(storage.find("id-1", Double.class), Optional.of(3.14));
        assertEquals(storage.find("id-2", String.class), Optional.of("hello world!"));
    }

    @Test
    public void testSaveAndError() {
        DataStorage storage = new HashMapStorage();
        storage.save("id-0", 99);

        assertEquals(storage.find("wrong id", Integer.class), Optional.empty());
        assertEquals(storage.find("id-0", Double.class), Optional.empty());
    }
}
