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

    boolean running() {
        return running;
    }

    public DatagramLevel(int port, int bufferSize, String lang) {//Инициализация сервера начинается отсюда
        this.bufferSize = bufferSize;
        auth = new AuthorizationLevel(this, lang);
        try {
            socket = new DatagramSocket(port);
            receive();
            PingPong.socket = socket;
            PingPong.dl = this;
            PingPong.ping();
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
        Thread receive = new Thread(() -> {
                while(running) {
                    byte[] data = new byte[bufferSize];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);

                        //System.out.println("client: " + new String(packet.getData()));//Временная функция

                        if(new String(packet.getData()).trim().equals("pong")) {
                            new Thread(()-> PingPong.pong(packet)).start();
                            continue;
                        }
                        new Thread(() -> auth.receive(new String(packet.getData()), packet.getAddress(), packet.getPort())).start();
                    }   catch (IOException e) {
                        System.err.println("Receive error: " + e);
                    }
                }
        }, "receive");
        receive.start();
    }
}
