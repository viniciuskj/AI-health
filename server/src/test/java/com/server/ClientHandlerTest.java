package com.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.net.Socket;
import static org.mockito.Mockito.*;
import com.server.configurations.ClientHandler;

@SpringBootTest
class ClientHandlerTest {

    @Test
    void testValidCredentials() throws IOException {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("CRED:test@example.com:StrongP@ss1!".getBytes());

        when(mockSocket.getOutputStream()).thenReturn(byteArrayOutputStream);
        when(mockSocket.getInputStream()).thenReturn(byteArrayInputStream);

        ClientHandler clientHandler = new ClientHandler(mockSocket);
        clientHandler.run();

        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("valido"));
    }
}