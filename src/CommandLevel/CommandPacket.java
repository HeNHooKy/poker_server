package CommandLevel;

import base.Player;

import java.util.HashMap;

class CommandPacket {
    private Player sender;
    private HashMap<String, String> value;

    CommandPacket(Player sender, HashMap<String, String> value) {
        this.sender = sender;
        this.value = value;
    }

    Player getSender() {
        return this.sender;
    }

    HashMap<String, String> getValue() {
        return this.value;
    }


}
