package SocialLevel;

import AuthLevel.AuthorizationLevel;
import base.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Room {
    private static List<Room> rooms = new ArrayList<>();

    private String name;
    private Player admin;
    private List<Player> players = new ArrayList<>();
    private static Properties lang = AuthorizationLevel.lang;

    public Room(String name, Player admin) {
        players.add(admin);
        this.admin = admin;
        this.name = name;
    }

    public static void join(String name, Player sender) {
        for(Room room : rooms) {
            if(room.name.equals(name)) {
                room.join(sender);
                return;
            }
        }
        sender.write("system", "not-found", lang.getProperty("room_not_found"));
    }

    private void join(Player sender) {
        this.players.add((sender));
        sender.write("system", "complete", lang.getProperty("join_room"));
    }

    public void write(String type, String message) {
        for(Player player : players) {
            player.write(type, "ok", message);
        }
    }

    public static void leave(Player sender) {
        sender.room().players.remove(sender);
        if(sender.room().admin.ID().equals(sender.ID())) {
            if(sender.room().players.size() <= 0) {
                rooms.remove(sender.room());
            }   else {
                sender.room().admin = sender.room().players.get(0);
            }
        }
        sender.room().write("system", lang.getProperty("player_left_room"));
        sender.leave();
    }
}
