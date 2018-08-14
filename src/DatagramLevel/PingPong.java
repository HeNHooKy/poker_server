package DatagramLevel;

import AuthLevel.Authorization;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class PingPong {//Проверяем подключен ли клиент. Если из 3 сообщений ни одно не вернет pong -> клиент отключен
    static DatagramSocket socket;
    static DatagramLevel dl;
    private static List<Client> clients = new ArrayList<>();
    private static int sleepTime = 5000;

    static void ping() {

        Thread ping = new Thread(()->{
            while(dl.running()) {
                for(Client client : clients) {
                    System.out.println(client.port());
                    new Thread(()-> {
                        if(client.attempt()) {
                            byte[] data = "ping".getBytes();
                            DatagramPacket packet = new DatagramPacket(data, data.length);
                            try {
                                packet.setAddress(client.address());
                                packet.setPort(client.port());
                                socket.send(packet);
                            }   catch (IOException e) {
                                System.out.println("ping-pong: socket is locked: " + e);
                            }
                        }   else {
                            System.out.println("server close connection with client: " + client.address() + ":" + client.port());
                            Authorization.downByIp(client.address(), client.port());
                            clients.remove(client);
                        }
                    }, "attempt").start();
                }
                try {
                    Thread.sleep(sleepTime);
                }   catch (InterruptedException e) {
                    System.out.println("ping timeout: " + e);
                }
            }
        } ,"ping");
        ping.start();
    }

    static void pong(DatagramPacket packet) {
        try {
            Client client = search(packet.getAddress(), packet.getPort());
            if (client != null) {
                client.refresh();
            } else {
                throw new Error("client is not exist");
            }
        }   catch (Exception e) {
            System.out.println("Client is not found: " + e);
        }
    }

    private static Client search(InetAddress address, int port) {
        for(Client client : clients) {
            if(client.address().equals(address) && (client.port() == port)) {
                return client;
            }
        }
        return null;
    }

    public static void update(InetAddress address, int port) {
        clients.add(new Client(address, port));
    }
}
