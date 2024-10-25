package src.main.java;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<Socket> clientSockets = new HashSet<>();
    private static Set<String> clientNames = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                synchronized (clientSockets) {
                    clientSockets.add(clientSocket);
                }
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void broadcastMessage(String message, Socket excludeSocket) {
        synchronized (clientSockets) {
            for (Socket socket : clientSockets) {
                if (socket != excludeSocket) {
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void removeClient(Socket socket, String clientName) {
        synchronized (clientSockets) {
            clientSockets.remove(socket);
        }
        synchronized (clientNames) {
            clientNames.remove(clientName);
        }
        System.out.println("Client disconnected: " + clientName);
        broadcastMessage(clientName + " has left the chat", null);
    }

   
    private static class ClientHandler extends Thread {
        private Socket socket;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                
                out.println("Enter your name: ");
                clientName = in.readLine();
                synchronized (clientNames) {
                    if (clientNames.contains(clientName)) {
                        out.println("Name already taken. Disconnecting.");
                        socket.close();
                        return;
                    } else {
                        clientNames.add(clientName);
                    }
                }
                broadcastMessage(clientName + " has joined the chat!", socket);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(clientName + ": " + message);
                    broadcastMessage(clientName + ": " + message, socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                removeClient(socket, clientName);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
