package ass01.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ass01.core.database.DataStorage;
import ass01.core.database.HashMapStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HTTPClientTest {

    public static int port = 222;
    @Test
    public void clientCreation() {
        DataStorage storage = new HashMapStorage();
        EBikeRentalService client = new EBikeRentalHTTPClient(port, storage);

        assertTrue(client.getUsers().isEmpty());
        assertTrue(client.getEBikes().isEmpty());
    }
    @Test
    public void clientUserInsertion() {
        DataStorage storage = new HashMapStorage();
        EBikeRentalService client = new EBikeRentalHTTPClient(port, storage);

        client.addUser("bob");

        assertEquals(client.getUsers().stream().map(User::getId).toList(), List.of("bob"));
    }
    @Test
    public void clientBikeInsertion() {
        DataStorage storage = new HashMapStorage();
        EBikeRentalService client = new EBikeRentalHTTPClient(port, storage);

        client.addEBike("bike", new P2d(0, 0.5));

        assertEquals(client.getEBikes().stream().map(EBike::getId).toList(), List.of("bike"));
    }
}
