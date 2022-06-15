package ru.school.fixme.router;

import ru.school.fixme.router.handler.SocketListener;

public class Runner {

    private final static int BROKER_PORT = 5000;
    private final static int MARKET_PORT = 5001;

    public void run() {
        Thread brokerListener = new SocketListener(BROKER_PORT);
        brokerListener.start();
    }
}
