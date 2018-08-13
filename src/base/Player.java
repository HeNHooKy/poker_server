package base;

import DatagramLevel.DatagramLevel;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class Player {
    //system
    String login;
    String password;
    UUID ID;
    InetAddress address;
    int port;
    DatagramLevel socket;

    //game
    String name;

    public Player(String login, InetAddress address, int port) {
        this.login = login;
        this.name = login;
        this.password = crypt(password);
        this.address = address;
        this.port = port;
        this.ID = UUID.randomUUID();

    }

    private String crypt(String password) {
        return password;
    }

    public boolean equalsPass(String password) {
        return this.password.equals(crypt(password));
    }

    public String name() {
        return name;
    }

    public void connect(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress address() {
        return this.address;
    }

    public int port() {
        return this.port;
    }

    public void write(String[] message) {
        try {
            String str = DataParser.stringify(message);
            socket.send(str, this.address, this.port);
        }   catch (IOException e) {
            System.out.println("Can't to send message to close socket: " + e);
        }
    }
}
