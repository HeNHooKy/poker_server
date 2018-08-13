package CommandLevel;

import base.Player;

import AuthLevel.Authorization;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    public CommandHandler() {//initialization server commands
        new Command("personal", (p)->{
            try {
                Map<String, String> value = p.getValue();
                String message = value.get("message");
                Player sender = p.getSender();
                Player recipient = Authorization.search(value.get("send-to").trim());
                if(recipient!=null) {
                    recipient.write("personal", "ok", message);
                }   else {
                    sender.write("system", "not-found", "player " + value.get("send-to") + " not found");
                }
            }   catch (Exception e) {
                System.out.print("Recipient not found: " + e);
            }
        });

    }

    public void request(String command, HashMap<String, String> value, Player player) {
        CommandPacket packet = new CommandPacket(player, value);
        Command.emitter(command, packet);
    }
}
