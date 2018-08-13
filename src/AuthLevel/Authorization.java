package AuthLevel;

import base.Player;

import java.net.InetAddress;
import java.util.List;

public class Authorization {
    private static List<Player> allPlayers;//Заменяет бд на данном этапе разработки
    private static List<Player> playersList;

    public static Player search(InetAddress address, int port) {   //После подключения бд переписать
        for(Player player : allPlayers) {
            if(player.address().equals(address) && player.port() == port) {
                return player;
            }
        }
        return null;
    }

    public static Player search(String name) {  //После подключения бд переписать
        for(Player player : allPlayers) {
            if(player.name().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public static void registration(Player player) {
        allPlayers.add(player);
        playersList.add(player);
    }
}
