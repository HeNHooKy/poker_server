package AuthLevel;

import CommandLevel.CommandHandler;
import DatagramLevel.DatagramLevel;
import base.DataParser;
import SocialLevel.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Properties;
import DatagramLevel.PingPong;

public class AuthorizationLevel {
    public static Properties lang;
    private DatagramLevel socket;//for sending messages
    private CommandHandler commandHandler;

    //Скорее всего клиент не увидит прямых сообщений от сервера, но т.к. это возможно стоит установить язык хотябы на ту область, где запущен сервер
    public AuthorizationLevel(DatagramLevel socket, String langFile) {
        this.socket = socket;
        this.commandHandler = new CommandHandler();
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

        String pass;
        if((pass = data.get("pass")) != null){
            pass = pass.trim();
        }   else {
            pass = "";
        }

        String command = data.get("command").trim();
        Player player = Authorization.search(address, port);
        if (player == null) {

            if (command.equals("registration")) {
                if (!pass.equals("")) {
                    if (Authorization.search(name) == null) {
                        player = new Player(name, pass, address, port, socket);
                        Authorization.registration(player);
                        player.write("system","access",lang.getProperty("welcome_registration"));
                        PingPong.update(address, port, player.ID()); //Независимая регистрация нового подключения
                    } else {
                        try {
                            String[] preStr = {"name", name, "type", "system", "message-type", "error", "value", lang.getProperty("login_locked")};
                            socket.send(DataParser.stringify(preStr), address, port);
                        }   catch (IOException e) {
                            System.out.println("Can't send message in close socket: " + e);
                        }
                    }
                } else {
                    try {
                        String[] preStr = {"name", name, "type", "system", "message-type", "error", "value", lang.getProperty("empty_pass")};
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
                        player.write("system","access",lang.getProperty("welcome_login"));
                        PingPong.update(address, port, player.ID()); //Независимая регистрация нового подключения
                        return;
                    }
                }
                try {
                    String[] preStr = {"name", name, "type", "system", "message-type", "error", "value", lang.getProperty("wrong_log_pass")};
                    socket.send(DataParser.stringify(preStr), address, port);
                }   catch (IOException e) {
                    System.out.println("Can't send message in close socket: " + e);
                }
            }
        } else {
            commandHandler.request(command, data, player);
        }
    }
}
/*} else {
        String[] preStr = {"name", name, "type", "error", "message-type", "system", "value", "Wrong password or login"}
        socket.send();
        }

}   */
