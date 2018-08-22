package GameLevel;

import SocialLevel.Player;

public class Chair {

    //creator controls
    private Table table;
    private String name;
    private Player player;
    private boolean isBusy;


    //player controls
    private boolean ready;
    private int bankroll;
    int bet;

    Chair(String name, Table table) {
        this.name = name;
        this.table = table;
    }

    public void raise(int raise) {//must hard
        if(table.gameLimit.isLimited) {//Не очевидно: если не началась стадия флоп и достаточно денег в банкролле на малую ставку или наоборот
            if((!table.isFlop && (bankroll >= table.minRaise)) || (table.isFlop && (bankroll >= table.maxRaise))) {
                //Если было разыграно 3 или менее ставок до флопа и 3 ставки во время и после.
                if(table.gameLimit.betCounter() < 3) {//Следи внимательно за betCounter
                    //Если пре-флоп отнять минимальную ставку, иначе полную
                    if(table.isFlop) {
                        bet += table.minRaise;
                        bankroll -= table.minRaise;
                    }   else {
                        bet += table.maxRaise;
                        bankroll -= table.maxRaise;
                    }
                    //TODO(hanji): Передать ход другому игроку
                }
            }
        }   else if(table.gameLimit.isNoLimited) {

        }   else if(table.gameLimit.isPotLimited) {
            if(raise <= bankroll) {
                if ((table.bank + table.tableBet <= raise) && (raise <= (table.bank + table.tableBet + table.tableBet))) {

                }
            }
        }
    }

    public void sendBankroll(int count) {
        if((table.minBankroll <= count) && (count <= table.maxBankroll)) {
            player.money().greenGet(-count);
            bankroll += count * table.course;
        }
    }
}
