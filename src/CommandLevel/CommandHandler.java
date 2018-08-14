package CommandLevel;

import AuthLevel.AuthorizationLevel;
import SocialLevel.Room;
import SocialLevel.Player;

import AuthLevel.Authorization;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandHandler {
    private static Properties lang = AuthorizationLevel.lang;
    public CommandHandler() {//initialization server commands
        new Command("personal", (p)->{
            try {
                Map<String, String> value = p.getValue();
                String message = value.get("value").trim();
                Player sender = p.getSender();
                Player recipient = Authorization.search(value.get("send-to").trim());
                if(recipient != null) {
                    recipient.write("personal", "ok", message);
                }   else {
                    sender.write("system", "not-found", "player " + value.get("send-to") + " not found");
                }
            }   catch (Exception e) {
                System.out.print("Recipient not found: " + e);
            }
        });

        new Command("chat", (p)->{
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String message = value.get("value").trim();
            Room room;
            if((room = sender.room())!=null) {
                room.write("chat", message);
            }
        });

        new Command("join", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            if(sender.room() == null) {
                Room.join(name, sender);
            }   else {
                sender.write("system", "error", lang.getProperty("already_in_room"));
            }
        });

        new Command("leave", (p)->{
            Player sender = p.getSender();
            if(sender.room() != null) {
                Room.leave(sender);
            }   else {
                sender.write("system", "error", lang.getProperty("not_in_room"));
            }
        });

        new Command("create", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            if(sender.room()==null) {
                Room.create(name, sender);
            }   else {
                sender.write("system", "error", lang.getProperty("already_in_room"));
            }
        });

        new Command("add-friend", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            sender.friend(name);
        });

        new Command("accept-friend", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            sender.acceptFriend(name);
        });

        new Command("close-friend", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            sender.closeFriend(name);
        });

        new Command("refresh-friend-list", (p)-> {
            Player sender = p.getSender();
            sender.refreshFriendList();
        });

        new Command("send-room-invite", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value").trim();
            if(sender.room()!= null) {
                Player recipient = Authorization.search(name);
                if(recipient!=null) {
                    recipient.receiveInvite(sender.room(), sender);
                }   else {
                    sender.write("system", "error", lang.getProperty("user_not_found"));
                }
            }   else {
                sender.write("system", "error", lang.getProperty("invite_null_room"));
            }
        });

        new Command("accept-room-invite", (p)-> {
            Player sender = p.getSender();
            sender.acceptInvite();
        });

        new Command("close-room-invite", (p)-> {
            Player sender = p.getSender();
            sender.closeInvite();
        });
    }

    public void request(String command, HashMap<String, String> value, Player player) {
        CommandPacket packet = new CommandPacket(player, value);
        Command.emitter(command, packet);
    }
}
