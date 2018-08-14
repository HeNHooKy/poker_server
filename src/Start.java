import DatagramLevel.DatagramLevel;

public class Start {
    public static void main(String args[]) {
        new DatagramLevel(8000,1024, "src/russian.lang");
    }
}
