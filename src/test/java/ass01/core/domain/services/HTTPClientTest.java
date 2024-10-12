package ass01.core.domain.services;

import ass01.core.database.DataStorage;
import ass01.core.database.HashMapStorage;
import ass01.core.domain.entities.EBike;
import ass01.core.domain.entities.P2d;
import ass01.core.domain.entities.User;
import ass01.core.domain.services.RentalService;
import ass01.core.domain.services.RentalServiceHTTPClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPClientTest {

    public static int port = 2220;
    @Test
    public void clientCreation() {
        DataStorage storage = new HashMapStorage();
        RentalService client = new RentalServiceHTTPClient(port, storage);

        assertTrue(client.getUsers().isEmpty());
        assertTrue(client.getEBikes().isEmpty());
    }
    @Test
    public void clientUserInsertion() {
        DataStorage storage = new HashMapStorage();
        RentalService client = new RentalServiceHTTPClient(port, storage);

        client.addUser("bob");

        assertEquals(client.getUsers().stream().map(User::getId).toList(), List.of("bob"));
    }
    @Test
    public void clientBikeInsertion() {
        DataStorage storage = new HashMapStorage();
        RentalService client = new RentalServiceHTTPClient(port, storage);

        client.addEBike("bike", new P2d(0, 0.5));

        assertEquals(client.getEBikes().stream().map(EBike::getId).toList(), List.of("bike"));
    }
    @Test
    public void clientRide() throws InterruptedException {
        DataStorage storage = new HashMapStorage();
        RentalService client = new RentalServiceHTTPClient(port, storage);

        client.addUser("u1");
        client.addEBike("b1", new P2d(0, 0));
        client.startNewRide("u1", "b1");

        Thread.sleep(3000);

        client.endRide("u1", "b1");

        var user = client.getUsers().stream().filter(u -> u.getId().equals("u1")).findFirst().get();
        assertNotEquals(100, user.getCredit());
    }
}
