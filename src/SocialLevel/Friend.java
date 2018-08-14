package SocialLevel;

import AuthLevel.AuthorizationLevel;

import java.util.Properties;

class Friend {
    private static Properties lang = AuthorizationLevel.lang;

    private Player recipient;
    private Player sender;
    private boolean isRequest;
    private boolean isFriend;

    Friend(Player sender, Player recipient) {
        this.isRequest = true;
        this.isFriend = false;
        this.sender = sender;
        this.recipient = recipient;
        recipient.receiveFriends(this);
    }

    Player friend(Player requesting) {
        if(requesting.ID().equals(sender.ID())) {
            return recipient;
        }   else {
            return sender;
        }
    }

    boolean isFriend() {
        return isFriend;
    }

    boolean isRequest() {
        return isRequest;
    }

    void close(Player closing) {
        this.isFriend = false;
        this.isRequest = false;
        if(closing.ID().equals(sender.ID())) {
            recipient.closeFriends(this);
        }   else {
            sender.closeFriends(this);
        }
    }

    String recipientLogin() {
        return recipient.login();
    }

    String senderLogin() {
        return sender.login();
    }

    void accept(Player recipient) {
        if(recipient.ID().equals(this.recipient.ID())) {//Это действительно приемщик
            if(this.isRequest) {
                isFriend = true;
                //everything will be ok
                this.sender.write("friend_response", "accept", lang.getProperty("recipient_accept_friend"));
            }   else {
                isFriend = false;
                //everything will be bad
                this.sender.write("friend_response", "close", lang.getProperty("recipient_close_friend"));
                sender.closeFriends(this);
                recipient.closeFriends(this);
            }
        }
    }



}
