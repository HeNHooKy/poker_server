package AuthLevel;

import SocialLevel.Player;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Authorization {
    private static Properties lang = AuthorizationLevel.lang;
    private static List<Player> allPlayers = new ArrayList<>();//Заменяет бд на данном этапе разработки
    private static List<Player> playersList = new ArrayList<>();

    static Player search(InetAddress address, int port) {   //После подключения бд переписать
        for(Player player : allPlayers) {
            if(player.address().equals(address) && (player.port() == port)) {
                return player;
            }
        }
        return null;
    }

    public static Player search(String login) {  //После подключения бд переписать
        for(Player player : allPlayers) {
            if(player.login().equals(login)) {
                return player;
            }
        }
        return null;
    }

    public static void downByIp(InetAddress address, int port) {
        Player disconnected = searchOnline(address, port);
        if(disconnected != null) {
            playersList.remove(disconnected);
            disconnected.write("connection", "close", lang.getProperty("connection_break"));
        }
    }

    static void registration(Player player) {
        allPlayers.add(player);
        playersList.add(player);
    }

    public static boolean onlineSearch(UUID ID) {   //проверяет находится ли игрок в сети
        for(Player search : playersList) {
            if(search.ID().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    private static Player searchOnline(InetAddress address, int port) {  //находит игрока в сети по адресу
        for(Player search : playersList) {
            if(search.address().equals(address) && (search.port() == port)) {
                return search;
            }
        }
        return null;
    }
}
