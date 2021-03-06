import base.DataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class test {
    static DatagramSocket socket;

    static Thread listen = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    if(new String(packet.getData()).trim().equals("ping")) {
                        data = "pong".getBytes();
                        packet = new DatagramPacket(data, data.length);
                        socket.send(packet);
                        continue;
                    }
                    System.out.println(new String(packet.getData()));
                }   catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }, "listen");

    static void send(String message) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        try {
            socket = new DatagramSocket();
            socket.connect(InetAddress.getLocalHost(), 8000);
            listen.start();
            String[] message = {"name", args[0], "pass", args[1] ,"command", "login"};
            send(DataParser.stringify(message));
            Thread.sleep(2000);
            String[] message1 = {"name", args[0] ,"command", "personal", "send-to", "hanji", "value", "Hello me"};
            send(DataParser.stringify(message1));

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String[] preStr = {"name", args[0] ,"command", "personal", "send-to", "hanji", "value", reader.readLine()};
                send(DataParser.stringify(preStr));
            }

            /*
            Thread.sleep(1000);
            String[] message2 = {"name", args[0] ,"command", "create", "value", "Room_with_long_name"};
            send(DataParser.stringify(message2));
            Thread.sleep(1000);
            String[] message3 = {"name", args[0] ,"command", "join", "value", "Room_with_long_name"};
            send(DataParser.stringify(message3));
            */

        }   catch (Exception e) {
            System.out.println(e);
        }
    }

}
