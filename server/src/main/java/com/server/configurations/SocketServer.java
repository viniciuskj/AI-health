package com.server.configurations;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final short PORT = 7000;
    private ServerSocket serverSocket;
    
    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado na porta " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    public void stopServer() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}
