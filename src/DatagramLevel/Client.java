package DatagramLevel;

import java.net.InetAddress;

class Client {
    private InetAddress address;
    private int port;
    private int attempt;

    InetAddress address() {
        return address;
    }

    int port() {
        return port;
    }

    Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.attempt = 0;
    }

    void refresh() {
        attempt = 0;
    }

    boolean attempt() {
        System.out.println(attempt);
        return (attempt++) < 3;
    }
}
