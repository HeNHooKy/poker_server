package CommandLevel;

import AuthLevel.AuthorizationLevel;
import SocialLevel.Room;
import base.Player;

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
                String message = value.get("value");
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
            String message = value.get("value");
            Room room;
            if((room = sender.room())!=null) {
                room.write("chat", message);
            }
        });

        new Command("join", (p)-> {
            Map<String, String> value = p.getValue();
            Player sender = p.getSender();
            String name = value.get("value");
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

    }

    public void request(String command, HashMap<String, String> value, Player player) {
        CommandPacket packet = new CommandPacket(player, value);
        Command.emitter(command, packet);
    }
}
