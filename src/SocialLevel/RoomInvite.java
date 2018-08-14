package SocialLevel;

import AuthLevel.AuthorizationLevel;

import java.util.Properties;

class RoomInvite {
    private Properties lang = AuthorizationLevel.lang;
    private Room room;
    private Player recipient;
    private Player sender;

    RoomInvite(Room room, Player sender, Player recipient) {
        this.room = room;
        this.recipient = recipient;
        this.sender = sender;
        recipient.write("room_invite", "waiting", lang.getProperty("room_invite"));
    }

    void accept() {
        room.join(recipient);
    }

    void close() {
        sender.write("system", "error", lang.getProperty("invited_close_invite"));
    }
}
