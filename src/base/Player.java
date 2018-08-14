package base;

import DatagramLevel.DatagramLevel;
import SocialLevel.Room;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class Player {
    //system
    private String login;
    private String password;
    private UUID ID;
    private InetAddress address;
    private int port;
    private DatagramLevel socket;

    //game
    private String name;
    private Room room = null;

    public Player(String login, String password,InetAddress address, int port, DatagramLevel socket) {
        this.login = login;
        this.name = login;
        this.password = crypt(password);
        this.address = address;
        this.port = port;
        this.ID = UUID.randomUUID();
        this.socket = socket;
        //Authorization.registration(this);
    }

    private String crypt(String password) {
        return password;
    }

    public Room room() {
        return room;
    }

    public boolean equalsPass(String password) {
        return this.password.equals(crypt(password));
    }

    public String login() {
        return login;
    }

    public UUID ID() {
        return this.ID;
    }

    public void connect(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void leave() {
        this.room = null;
    }

    public InetAddress address() {
        return this.address;
    }

    public int port() {
        return this.port;
    }

    public void write(String type, String status, String message) {
        try {
            String[] resStr = {"name", name ,"id" ,ID+"" ,"type" ,type ,"message-type" ,status ,"value" , message};
            String str = DataParser.stringify(resStr);
            socket.send(str, this.address, this.port);
        }   catch (IOException e) {
            System.out.println("Can't to send message to close socket: " + e);
        }
    }
}
