package SocialLevel;

public class Money {
    private int green;
    private int gold;

    public Money() {
        this.green = 0;
        this.gold = 0;
    }

    void greenSend(Money pay, int count) {
        this.green -= count;
        pay.green += count;
    }

    void goldSend(Money pay, int count) {
        this.gold -= count;
        pay.gold += count;
    }
}
