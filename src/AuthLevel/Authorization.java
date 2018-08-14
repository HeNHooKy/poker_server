package AuthLevel;

import SocialLevel.Player;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Authorization {
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

    static void registration(Player player) {
        allPlayers.add(player);
        playersList.add(player);
    }

    public static boolean onlineSearch(UUID ID) {
        for(Player search : playersList) {
            if(search.ID().equals(ID)) {
                return true;
            }
        }
        return false;
    }
}
