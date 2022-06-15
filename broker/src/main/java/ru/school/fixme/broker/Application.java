package ru.school.fixme.broker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Application {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(String.format("%s%c%s%c%s%c%s", "8=FIX", 1, "35=Hello.world", 1, "10=3716105013", 1, "\n"));
        out.flush();
    }
}
