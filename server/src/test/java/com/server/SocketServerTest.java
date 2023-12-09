package com.server;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.Socket;
import com.server.configurations.SocketServer;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SocketServerTest {

    private Thread serverThread;

    @BeforeEach
    public void setup() {
        // Inicia o servidor em um thread separado para não bloquear o teste
        serverThread = new Thread(() -> SocketServer.main(new String[0]));
        serverThread.start();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Interrompe o servidor após cada teste
        serverThread.interrupt();
    }

    @Test
    public void testServerAcceptsConnection() {
        assertDoesNotThrow(() -> {
            // Tenta conectar ao servidor
            try (Socket clientSocket = new Socket("localhost", 7000)) {
                assertTrue(clientSocket.isConnected());
            }
        });
    }
}
