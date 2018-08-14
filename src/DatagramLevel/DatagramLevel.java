package DatagramLevel;

import AuthLevel.AuthorizationLevel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DatagramLevel {
    private DatagramSocket socket;
    private AuthorizationLevel auth;
    private boolean running = true;
    private int bufferSize;

    public DatagramLevel(int port, int bufferSize, String lang) {//Инициализация сервера начинается отсюда
        this.bufferSize = bufferSize;
        auth = new AuthorizationLevel(this, lang);
        try {
            socket = new DatagramSocket(port);
            receive();
            System.out.println("Server started on port: " + port);
        }   catch (SocketException e) {
            System.out.println("Can't to open socket on port " + port + ":" + e);
        }
    }

    public void send(String str, InetAddress address, int port) throws IOException {//отправка пакетов клиенту через udp сокет
        //System.out.println(str); maybe

        byte[] data = str.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);

        packet.setAddress(address);
        packet.setPort(port);
        socket.send(packet);

    }

    private void receive() {//прослушиваем канал(сокет)
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                while(running) {
                    byte[] data = new byte[bufferSize];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                auth.receive(new String(packet.getData()), packet.getAddress(), packet.getPort());
                            }
                        }, "player").start();

                    }   catch (IOException e) {
                        System.err.println("Receive error: " + e);
                    }
                }
            }
        }, "receive");
        receive.start();
    }
}
