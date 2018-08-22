package GameLevel;

import SocialLevel.Money;

public class Chip {
    private int chip;

    public void send(Money money, int count, int course) {//Переводит фишки в деньги по курсу, где count сумма перечисления в зеленых
        if(this.chip >= course*count) {
            this.chip -= course*count;
            money.greenGet(count);
        }
    }

    public void get(int count) {
        chip += count;
    }

    public void send(Chip chip, int count) {
        if(this.chip >= count) {
            this.chip -= count;
            chip.chip += count;
        }
    }

}
