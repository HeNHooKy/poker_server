package GameLevel;

class Limit {
    int minLimit;
    int maxLimit;
    boolean isLimited;
    boolean isPotLimited;
    boolean isNoLimited;
    private int betCounter = 0;

    int betCounter() {
        return betCounter++;
    }

    void betCounterRefresh() {
        betCounter = 0;
    }
}
