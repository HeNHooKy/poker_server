package AuthLevel;

import DatagramLevel.DatagramLevel;
import base.DataParser;
import base.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Properties;

public class AuthorizationLevel {
    private DatagramLevel socket;//for sending messages
    private Properties lang;

    //Скорее всего клиент не увидит прямых сообщений от сервера, но т.к. это возможно стоит установить язык хотябы на ту область, где запущен сервер
    public AuthorizationLevel(DatagramLevel socket, String langFile) {
        this.socket = socket;
        try {
            lang = new Properties();
            lang.load(new FileInputStream(langFile));//для отправки
        }   catch (IOException e) {
            System.out.println("File " + langFile + " not found: " + e);
        }
    }
    //Принимаем сообщения и проверяем аунтентификацию ip/port клиента
    public void receive(String message, InetAddress address, int port) {
        HashMap<String, String> data = DataParser.parse(message);
        String name = data.get("name").trim();
        String pass = data.get("pass").trim();
        String command = data.get("command").trim();
        Player player = Authorization.search(address, port);

        if (player == null) {
            if (command.equals("registration")) {
                if (!pass.equals("")) {
                    if (Authorization.search(name) == null) {
                        player = new Player(name, address, port);
                        Authorization.registration(player);
                        String[] preStr = {"name", name, "type", "access", "message-type", "system", "value", lang.getProperty("welcome_registration")};
                        player.write(preStr);
                    } else {
                        try {
                            String[] preStr = {"name", name, "type", "error", "message-type", "system", "value", lang.getProperty("login_locked")};
                            socket.send(DataParser.stringify(preStr), address, port);
                        }   catch (IOException e) {
                            System.out.println("Can't send message in close socket: " + e);
                        }
                    }
                } else {
                    try {
                        String[] preStr = {"name", name, "type", "error", "message-type", "system", "value", lang.getProperty("empty_pass")};
                        socket.send(DataParser.stringify(preStr), address, port);
                    }   catch (IOException e) {
                        System.out.println("Can't send message in close socket: " + e);
                    }
                }
            }   else if (command.equals("login")) {
                player = Authorization.search(name);
                if(player != null) {
                    if (player.equalsPass(pass)) {
                        player.connect(address, port);
                        String[] preStr = {"name", name, "type", "access", "message-type", "system", "value", lang.getProperty("welcome_login")};
                        player.write(preStr);
                    }
                }
                try {
                    String[] preStr = {"name", name, "type", "error", "message-type", "system", "value", lang.getProperty("wrong_log_pass")};
                    socket.send(DataParser.stringify(preStr), address, port);
                }   catch (IOException e) {
                    System.out.println("Can't send message in close socket: " + e);
                }
            }
        } else {
            //TODO(hanji):send hashMap to commandHandler
        }
    }
}
/*} else {
        String[] preStr = {"name", name, "type", "error", "message-type", "system", "value", "Wrong password or login"}
        socket.send();
        }

}   */
