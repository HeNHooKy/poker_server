package DatagramLevel;

import java.net.InetAddress;
import java.util.UUID;

class Client {
    private InetAddress address;
    private int port;
    private int attempt;
    private UUID ID;

    InetAddress address() {
        return address;
    }

    int port() {
        return port;
    }

    UUID ID() {
        return ID;
    }

    Client(InetAddress address, int port, UUID ID) {
        this.address = address;
        this.port = port;
        this.ID = ID;
        this.attempt = 0;
    }

    void refresh() {
        attempt = 0;
    }

    boolean attempt() {
        return (attempt++) < 3;
    }
}
