package SocialLevel;

import GameLevel.Chip;

public class Money {
    private int green;

    public Money() {
        this.green = 0;
    }

    public void greenGet(int count) {
        this.green += count;
    }

    public void greenSend(Money money, int count) {
        if(this.green >= count) {
            this.green -= count;
            money.green += count;
        }
    }

    public void greenSend(Chip chip, int count, int course) {//переводит зеленые в фишки по курсу, где count сумма перечисления в зеленых
        if(this.green >= count) {
            this.green -= count;
            chip.get(count*course);
        }
    }
}
