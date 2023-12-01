package clientserver;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClientServerTest {

    @Test
    public void testClientServerCommunication() {

        MyObject testObject = new MyObject("Test Message");

        Thread serverThread = new Thread(() -> Server.main(null));
        serverThread.start();

        try {
            Thread.sleep(1000);

            Client.main(null);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            serverThread.interrupt();
        }
    }
}
