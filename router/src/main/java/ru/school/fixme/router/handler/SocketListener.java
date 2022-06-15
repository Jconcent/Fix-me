package ru.school.fixme.router.handler;

import ru.school.fixme.router.validation.MessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener extends Thread {

    private final int port;
    private MessageHandler messageHandler;

    public SocketListener(int port) {
        this.port = port;
        this.messageHandler = new MessageHandler();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket client = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            while (true) {
                String msg = in.readLine();
                boolean isValidMsg = messageHandler.validateAndMapMsg(msg);
                if (isValidMsg) {
                    var tags = messageHandler.getTagsMap();
                    System.out.println(tags.get(35));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
