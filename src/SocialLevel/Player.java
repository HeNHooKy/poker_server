package SocialLevel;

import AuthLevel.Authorization;
import AuthLevel.AuthorizationLevel;
import DatagramLevel.DatagramLevel;
import base.DataParser;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Player {
    //code
    private static Properties lang = AuthorizationLevel.lang;

    //system
    private String login;
    private String password;
    private UUID ID;
    private InetAddress address;
    private int port;
    private DatagramLevel socket;

    //friends list
    private List<Friend> friends = new ArrayList<>();

    //game
    private String name;
    private Room room = null;
    private RoomInvite invite;

    public Player(String login, String password,InetAddress address, int port, DatagramLevel socket) {
        this.login = login;
        this.name = login;
        this.password = crypt(password);
        this.address = address;
        this.port = port;
        this.ID = UUID.randomUUID();
        this.socket = socket;
        //Authorization.registration(this);
    }

    public void receiveInvite(Room room, Player sender) {
        this.invite = new RoomInvite(room, sender, this);
    }

    public void acceptInvite() {
        if(invite != null)
            this.invite.accept();
    }

    public void closeInvite() {
        if(invite != null) {
            this.invite.close();
            this.invite = null;
        }
    }

    public Room room() {
        return room;
    }

    public boolean equalsPass(String password) {
        return this.password.equals(crypt(password));
    }

    public String login() {
        return login;
    }

    public UUID ID() {
        return this.ID;
    }

    public void connect(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void friend(String login) {
        Player recipient = Authorization.search(login);
        if(recipient!= null) {
            Friend friend = new Friend(this, recipient);//sender, recipient
            friends.add(friend);
            write("system", "complete", lang.getProperty("friend_request_sent"));
            recipient.write("friend_request", "request", lang.getProperty("received_friend_request") + ":" + this.login());
        }   else {
            this.write("system", "not-found", lang.getProperty("user_not_found"));
        }
    }

    public void acceptFriend(String login) {
        for(Friend friend : friends) {
            if(friend.recipientLogin().equals(login) || friend.senderLogin().equals(login)) {
                friend.accept(this);
                return;
            }
        }
        write("system", "not-found", lang.getProperty("user_not_found"));
    }

    public void closeFriend(String login) {
        for(Friend friend : friends) {
            if(friend.recipientLogin().equals(login) || friend.senderLogin().equals(login)) {
                friend.close(this);
                friends.remove(friend);
                return;
            }
        }
        write("system", "not-found", lang.getProperty("user_not_found"));
    }



    public void refreshFriendList() {
        for(Friend friend : friends) {
            boolean isFriend = friend.isFriend();
            boolean isRequest = friend.isRequest();
            String friendName = friend.friend(this).name();

            if(isFriend && isRequest) {
                write("friends_list", "friend", friendName);
            }   else if(isRequest) {
                if(friend.recipientLogin().equals(login)) {
                    write("friends_list", "need_accepted", friendName);
                }   else {
                    write("friends_list", "wait_accepted", friendName);
                }
            }

        }
    }

    public InetAddress address() {
        return this.address;
    }

    public int port() {
        return this.port;
    }

    public boolean isOnline() {
        return Authorization.onlineSearch(this.ID);
    }

    public void write(String type, String status, String message) {
        try {
            String[] resStr = {"name", name ,"id" ,ID+"" ,"type" ,type ,"message-type" ,status ,"value" , message};
            String str = DataParser.stringify(resStr);
            socket.send(str, this.address, this.port);
        }   catch (IOException e) {
            System.out.println("Can't to send message to close socket: " + e);
        }
    }

    private String crypt(String password) {
        return password;
    }

    void receiveFriends(Friend friend) {
        friends.add(friend);
    }

    String name() {
        return name;
    }

    void closeFriends(Friend friend) {
        friend.close(this);
        friends.remove(friend);
    }

    void leave() {
        this.room = null;
    }
}
